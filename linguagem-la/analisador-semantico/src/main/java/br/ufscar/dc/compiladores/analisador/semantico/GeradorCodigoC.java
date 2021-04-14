package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

public class GeradorCodigoC extends GramaticaBaseVisitor<Void> {

    StringBuilder saida;
    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);
    GeradorCodigoCLib geradorLib = new GeradorCodigoCLib();

    public GeradorCodigoC() {
        saida = new StringBuilder();
    }

    @Override
    public Void visitPrograma(GramaticaParser.ProgramaContext ctx) {
        //impressão do cabeçalho e da estrutura do método main
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("#include <string.h>\n");
        saida.append("\n");
        visitDeclaracoes(ctx.declaracoes());
        saida.append("\n");
        saida.append("int main() {\n");
        visitCorpo(ctx.corpo());
        saida.append("return 0;\n");
        saida.append("}\n");
        return null;
    }

    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        //declaração de constante
        if (ctx.valor_constante() != null) {
            saida.append("#define " + ctx.IDENT().getText() + " " + ctx.valor_constante().getText() + "\n");
        } //criação de tipo registro
        else if (ctx.tipo() != null) {

            TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
            escopos.criarNovoEscopo();

            saida.append("typedef struct {\n");
            super.visitRegistro(ctx.tipo().registro());
            TabelaSimbolos escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            escopoAtual.adicionar(ctx.IDENT().getText(), TipoLA.REGISTRO, TipoETS.TIPO, escopoRegistro, false);
            saida.append("} " + ctx.IDENT().getText() + ";\n");

        } else if (ctx.variavel() != null) {
            visitVariavel(ctx.variavel());
        }

        return null;
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        TabelaSimbolos escopoRegistro;
        boolean tipoEstendido = false;

        //qualquer tipo menos registro
        if (ctx.tipo().tipo_estendido() != null) {

            String nomeVar;
            String tipoVar = ctx.tipo().getText();
            TipoLA tipoVarLa = TipoLA.INVALIDO;
            boolean ponteiro = false;

            if (tipoVar.contains("^")) { //ponteiro
                ponteiro = true;
                tipoVar = tipoVar.substring(1); //remove caractere de ponteiro
            }

            if (escopoAtual.existeTipoEstendido(tipoVar)) {
                tipoEstendido = true;
                tipoVarLa = TipoLA.TIPOESTENDIDO;

            } else {
                tipoVarLa = escopoAtual.getTipo(tipoVar);
                tipoVar = geradorLib.getTipo(tipoVarLa);
            }

            if (ponteiro == true) {
                tipoVar += "*";
            }

            for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
                nomeVar = ictx.getText();

                //adiciona variavel no escopo
                if (tipoEstendido) {
                    escopoRegistro = escopoAtual.getSubTabela(tipoVar);
                    escopoAtual.adicionar(nomeVar, TipoLA.REGISTRO, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
                } else {
                    escopoAtual.adicionar(nomeVar, tipoVarLa, TipoETS.VARIAVEL, null, false);
                }

                //imprime declaração da variável
                if (tipoVarLa == TipoLA.LITERAL) {
                    saida.append(tipoVar + " " + nomeVar + "[80];\n");
                } else {
                    saida.append(tipoVar + " " + nomeVar + ";\n");
                }

            }

        //tipo registro
        } else {
            //criação de escopo para as variáveis do registro
            escopos.criarNovoEscopo();
            escopoRegistro = escopos.obterEscopoAtual();

            //impressão da struct e gravação de suas variáveis no escopo
            saida.append("struct{\n");
            for (GramaticaParser.VariavelContext vctx : ctx.tipo().registro().variavel()) {
                visitVariavel(vctx);
            }
            saida.append("}" + ctx.identificador(0).getText() + ";\n");

            //remoção do escopo da pilha e gravação do escopo do registro no escopo atual
            escopos.abandonarEscopo();
            escopoAtual.adicionar(ctx.identificador(0).getText(), TipoLA.REGISTRO, null, escopoRegistro, false);
        }

        return null;
    }
    
        @Override
    public Void visitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        escopos.criarNovoEscopo();
        //cria escopo dos parâmetros do procedimento/função
        TabelaSimbolos escopoParametros = escopos.obterEscopoAtual();
        String tipo, nomeVariaveis;

        //imprime tipo da função
        if (ctx.tipo_estendido() != null) {
            saida.append(geradorLib.getTipo(ctx.tipo_estendido().getText()));
        } else { //caso contrário, imprime void
            saida.append("void");
        }

        //imprime nome do procedimento/função
        saida.append(" " + ctx.IDENT().getText() + "(");

        //itera sobre parâmetros, se existirem, e os adiciona no escopo dos parâmetros e os imprime
        if (ctx.parametros() != null) {
            for (GramaticaParser.ParametroContext pctx : ctx.parametros().parametro()) {
                tipo = geradorLib.getTipo(pctx.tipo_estendido().getText());
                nomeVariaveis = "";
                for (GramaticaParser.IdentificadorContext ictx : pctx.identificador()) {
                    nomeVariaveis += ictx.getText();
                    escopoParametros.adicionar(ictx.getText(), geradorLib.getTipoLa(pctx.tipo_estendido().getText()), TipoETS.VARIAVEL, null, false);
                }
                if (tipo == "char") {
                    tipo = "char*";
                }
                saida.append(tipo + " " + nomeVariaveis);
            }
        }

        saida.append(") {\n");

        //adiciona no escopo o procedimento/função
        if (ctx.tipo_estendido() != null) { //função
            escopoAtual.adicionar(ctx.IDENT().getText(), geradorLib.getTipoLa(ctx.tipo_estendido().getText()), TipoETS.FUNCAO, escopoParametros, false);
        }
        else { //procedimento
            escopoAtual.adicionar(ctx.IDENT().getText(), TipoLA.SEMTIPO, TipoETS.PROCEDIMENTO, escopoParametros, false);
        }

        //itera sobre comandos e realiza chamadas
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }

        //imprime fechamento da declaração do procedimento/função
        saida.append("}\n");

        //abandona escopo dos parâmetros
        escopos.abandonarEscopo();

        return null;
    }

    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        String nomeVar;
        TipoLA tipoLa;
        String codigoTipo;

        //itera sobre os identificadores passados como parâmetro
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            nomeVar = ictx.getText();
            tipoLa = escopoAtual.verificar(nomeVar);
            codigoTipo = geradorLib.getCodigoTipo(tipoLa);
            //impressão de comando leia para parâmetro do tipo literal
            if (tipoLa == TipoLA.LITERAL) {
                saida.append("gets(" + nomeVar + ");\n");
            //impressão de comando leia para parâmetro de tipos real, inteiro e lógico
            } else {
                saida.append("scanf(\"%" + codigoTipo + "\",&" + nomeVar + ");\n");
            }
        }

        return null;
    }

    @Override
    public Void visitCmdEscreva(GramaticaParser.CmdEscrevaContext ctx) {

        TipoLA tipoLaExp = verificadorTipo.verificaTipo(ctx.expressao(0));
        String codTipoExp;
        String nomeVariaveis = "";

        //se parâmetro só possuie uma expressão como argumento
        if (ctx.expLista.isEmpty()) {
            escreva(ctx.exp1);
        //se parâmetro possuir mais de um argumento
        } else {
            //itera sobre as expressões e realiza uma chamada de escreva para cada expressão
            for (GramaticaParser.ExpressaoContext ectx : ctx.expressao()) {
                escreva(ectx);
            }
        }

        return null;
    }

    //função auxiliar a visitEscreva que recebe uma expressão e realiza a impressão
    public Void escreva(GramaticaParser.ExpressaoContext ctx) {

        saida.append("printf(\"");

        //se o parâmetro for um literal passado explicitamente
        if (ctx.getText().contains("\"")) {
            saida.append(ctx.getText().replace("\"", "") + "\");\n");
        } 
        //se o parâmetro for uma variável
        else {
            TipoLA tipoLaExp = verificadorTipo.verificaTipo(ctx);
            String codTipoExp;

            //variável é um literal
            if (tipoLaExp == TipoLA.LITERAL) {
                saida.append("%s" + "\", " + ctx.getText() + ");\n");
            } 
            //variável é inteiro, real ou lógico
            else {
                codTipoExp = geradorLib.getCodigoTipo(tipoLaExp);
                saida.append("%" + codTipoExp + "\", " + ctx.getText() + ");\n");
            }
        }
        
        return null;
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        //imprime indentificador
        if (ctx.identificador() != null) {
            saida.append(ctx.identificador().getText());
        }

        super.visitParcela_nao_unario(ctx);

        return null;
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        //imprime expressao se não for uma expressão entre parênteses
        if (ctx.expParenteses == null) {
            saida.append(ctx.getText());
        } else { //caso contrário, realiza a chama de recursiva passando essa expressão
            //e imprimindo os parênteses que a envolvem
            saida.append("(");
            super.visitParcela_unario(ctx);
            saida.append(")");
        }
        return null;
    }

    @Override
    public Void visitOp_relacional(GramaticaParser.Op_relacionalContext ctx) {

        //recupera o operador
        String texto = ctx.getText();

        //se operador for '=', substituir por '=='
        if (ctx.igual != null) {
            texto = "==";
        }

        //imprime operador
        saida.append(texto);

        super.visitOp_relacional(ctx);

        return null;
    }

    @Override
    public Void visitFator_logico(GramaticaParser.Fator_logicoContext ctx) {

        //se operador for 'não', imprime '!' 
        if (ctx.nao != null) {
            saida.append("!");
        }

        super.visitFator_logico(ctx);

        return null;
    }

    @Override
    public Void visitOp2(GramaticaParser.Op2Context ctx) {

        //imprime operador
        saida.append(ctx.getText());

        super.visitOp2(ctx);

        return null;
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

        //identificador é um ponteiro
        if (ctx.simbolo != null) {
            saida.append("*" + ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        } //identificador é um registro
        else if (ctx.identificador().getText().contains(".") && verificadorTipo.verificaTipo(ctx.identificador()) == TipoLA.LITERAL) {
            saida.append("strcpy(" + ctx.identificador().getText() + "," + ctx.expressao().getText() + ");\n");
        } else { //identificador é uma variável do tipo básico
            saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        }

        return null;
    }

    @Override
    public Void visitCmdSe(GramaticaParser.CmdSeContext ctx) {

        //realiza normalizações de operadores lógicos
        String textoExpressao;
        textoExpressao = ctx.expressao().getText().replace("e", "&&");
        textoExpressao = textoExpressao.replace("=", "==");

        //imprime comando if
        saida.append("if (" + textoExpressao + "){\n");

        //itera sobre os comandos dentro de if e realiza as chamadas
        for (GramaticaParser.CmdContext cctx : ctx.cmdEntao) {
            super.visitCmd(cctx);
        }
        //imprime fechamento do comando if
        saida.append("}\n");

        //imprime comando else
        if (!ctx.cmdSenao.isEmpty()) {
            //imprime cabeçalho do comando else
            saida.append("else{\n");
            //itera sobre os comandos dentro de else e realiza as chamadas
            for (GramaticaParser.CmdContext cctx : ctx.cmdSenao) {
                super.visitCmd(cctx);
            }
            saida.append("}\n");
        }

        return null;
    }

    @Override
    public Void visitCmdCaso(GramaticaParser.CmdCasoContext ctx) {

        String limitanteEsquerda, limitanteDireita;

        //imprime comando switch
        saida.append("switch (" + ctx.exp_aritmetica().getText() + "){\n");

        //itera sobre itens do switch e os imprime
        for (GramaticaParser.Item_selecaoContext sctx : ctx.selecao().item_selecao()) {
            limitanteEsquerda = sctx.constantes().numero_intervalo(0).getText();
            if (!sctx.constantes().limiteDireita.isEmpty()) {
                limitanteDireita = sctx.constantes().numero_intervalo(1).getText();

                for (int i = Integer.parseInt(limitanteEsquerda); i <= Integer.parseInt(limitanteDireita); i++) {
                    saida.append("case " + Integer.toString(i) + ":\n");
                }
            } else {
                saida.append("case " + limitanteEsquerda + ":\n");
            }
            for (GramaticaParser.CmdContext cctx : sctx.cmd()) {
                visitCmd(cctx);
            }
            saida.append("break;\n");
        }

        //imprime opção default do switch
        saida.append("default:\n");
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }
        //imprime fechamento do switch
        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitCmdEnquanto(GramaticaParser.CmdEnquantoContext ctx) {

        //imprime cabeçalho da função while
        saida.append("while(");
        //imprime parâmetro da função while
        super.visitExpressao(ctx.expressao());
        saida.append("){\n");
        //itera sobre os comandos dentro de while e realiza as chamadas
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            super.visitCmd(cctx);
        }
        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitCmdFaca(GramaticaParser.CmdFacaContext ctx) {

        //imprime cabeçalho da função do
        saida.append("do{\n");
        //itera sobre os comandos dentro de do e realiza as chamadas
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            super.visitCmd(cctx);
        }
        //imprime cabeçalho da função while
        saida.append("}while(");
        //imprime parâmetro da função while
        super.visitExpressao(ctx.expressao());
        saida.append(");\n");

        return null;
    }

    @Override
    public Void visitCmdPara(GramaticaParser.CmdParaContext ctx) {

        String nomeVariavel, limitanteEsquerda, limitanteDireita;

        nomeVariavel = ctx.IDENT().getText();
        limitanteEsquerda = ctx.exp_aritmetica(0).getText();
        limitanteDireita = ctx.exp_aritmetica(1).getText();

        //imprime comando for
        saida.append("for(" + nomeVariavel + " = " + limitanteEsquerda + "; " + nomeVariavel + " <= " + limitanteDireita
                + "; " + nomeVariavel + "++){\n");

        //itera sobre comandos dentro do for e realiza as chamadas
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }

        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitCmdChamada(GramaticaParser.CmdChamadaContext ctx) {

        //imprime cabeçalho da chamada de função/procedimento
        saida.append(ctx.IDENT().getText() + "(");

        int count = 0;

        //imprime parâmetros da chamada
        for (GramaticaParser.ExpressaoContext ectx : ctx.expressao()) {
            if (count >= 1) { 
                saida.append(", ");
            }
            saida.append(ectx.getText());
            count += 1;
        }

        //imprime fechamento da chamada de função/procedimento
        saida.append(");\n");

        return null;
    }

    @Override
    public Void visitCmdRetorne(GramaticaParser.CmdRetorneContext ctx) {

        //imprime comando palavra-chave do comando retorno
        saida.append("return ");
        //visita a expressão a ser retornada
        super.visitExpressao(ctx.expressao());
        saida.append(";\n");

        return null;
    }
}
