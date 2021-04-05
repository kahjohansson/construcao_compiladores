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

            if (tipoVar.contains("^")) {
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

                if (tipoEstendido) {
                    escopoRegistro = escopoAtual.getSubTabela(tipoVar);
                    escopoAtual.adicionar(nomeVar, TipoLA.REGISTRO, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
                } else {
                    escopoAtual.adicionar(nomeVar, tipoVarLa, TipoETS.VARIAVEL, null, false);
                }

                if (tipoVarLa == TipoLA.LITERAL) {
                    saida.append(tipoVar + " " + nomeVar + "[80];\n");
                } else {
                    saida.append(tipoVar + " " + nomeVar + ";\n");
                }

            }

            //tipo registro
        } else {
            escopos.criarNovoEscopo();
            escopoRegistro = escopos.obterEscopoAtual();

            saida.append("struct{\n");
            for (GramaticaParser.VariavelContext vctx : ctx.tipo().registro().variavel()) {
                visitVariavel(vctx);
            }
            saida.append("}" + ctx.identificador(0).getText() + ";\n");

            escopos.abandonarEscopo();
            escopoAtual.adicionar(ctx.identificador(0).getText(), TipoLA.REGISTRO, null, escopoRegistro, false);
        }

        return null;
    }

    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        String nomeVar;
        TipoLA tipoLa = TipoLA.INVALIDO;
        String codigoTipo;

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            nomeVar = ictx.getText();
            tipoLa = escopoAtual.verificar(nomeVar);
            codigoTipo = geradorLib.getCodigoTipo(tipoLa);
            if (tipoLa == TipoLA.LITERAL) {
                saida.append("gets(" + nomeVar + ");\n");
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

        //só possui uma expressão como argumento
        if (ctx.expLista.isEmpty()) { 
            escreva(ctx.exp1);
        //possui mais de um argumento
        } else {
            for (GramaticaParser.ExpressaoContext ectx : ctx.expressao()) {
                escreva(ectx);
            }
        }

        return null;
    }

    public Void escreva(GramaticaParser.ExpressaoContext ctx) {

        saida.append("printf(\"");

        //se for literal passada explicitamente
        if (ctx.getText().contains("\"")) {
            saida.append(ctx.getText().replace("\"", "") + "\");\n");
        } //se for variavel
        else {

            TipoLA tipoLaExp = verificadorTipo.verificaTipo(ctx);
            String codTipoExp;

            //literal
            if (tipoLaExp == TipoLA.LITERAL) {
                saida.append("%s" + "\", " + ctx.getText() + ");\n");
            } //inteiro, real ou lógico
            else {
                codTipoExp = geradorLib.getCodigoTipo(tipoLaExp);
                saida.append("%" + codTipoExp + "\", " + ctx.getText() + ");\n");
            }

        }

        return null;
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        if (ctx.identificador() != null) {
            saida.append(ctx.identificador().getText());
        }

        super.visitParcela_nao_unario(ctx);

        return null;
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        if (ctx.expParenteses == null) {
            saida.append(ctx.getText());
        } else {
            saida.append("(");
            super.visitParcela_unario(ctx);
            saida.append(")");
        }
        return null;
    }

    @Override
    public Void visitOp_relacional(GramaticaParser.Op_relacionalContext ctx) {

        String texto = ctx.getText();

        if (ctx.igual != null) {
            texto = "==";
        }

        saida.append(texto);

        super.visitOp_relacional(ctx);

        return null;
    }

    @Override
    public Void visitFator_logico(GramaticaParser.Fator_logicoContext ctx) {

        if (ctx.nao != null) {
            saida.append("!");
        }

        super.visitFator_logico(ctx);

        return null;
    }

    @Override
    public Void visitOp2(GramaticaParser.Op2Context ctx) {

        saida.append(ctx.getText());

        super.visitOp2(ctx);

        return null;
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

        //é um ponteiro
        if (ctx.simbolo != null) {
            saida.append("*" + ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        } //é um registro
        else if (ctx.identificador().getText().contains(".") && verificadorTipo.verificaTipo(ctx.identificador()) == TipoLA.LITERAL) {
            saida.append("strcpy(" + ctx.identificador().getText() + "," + ctx.expressao().getText() + ");\n");
        } else {
            saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        }

        return null;
    }

    @Override
    public Void visitCmdSe(GramaticaParser.CmdSeContext ctx) {

        //if
        //normalizacoes
        String textoExpressao;
        textoExpressao = ctx.expressao().getText().replace("e", "&&");
        textoExpressao = textoExpressao.replace("=", "==");

        saida.append("if (" + textoExpressao + "){\n");

        for (GramaticaParser.CmdContext cctx : ctx.cmdEntao) {
            super.visitCmd(cctx);
        }
        saida.append("}\n");

        //else
        if (!ctx.cmdSenao.isEmpty()) {
            saida.append("else{\n");
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

        saida.append("switch (" + ctx.exp_aritmetica().getText() + "){\n");

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

        saida.append("default:\n");
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }
        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitCmdEnquanto(GramaticaParser.CmdEnquantoContext ctx) {

        saida.append("while(");
        super.visitExpressao(ctx.expressao());
        saida.append("){\n");
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            super.visitCmd(cctx);
        }
        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitCmdFaca(GramaticaParser.CmdFacaContext ctx) {

        saida.append("do{\n");
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            super.visitCmd(cctx);
        }
        saida.append("}while(");
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

        saida.append("for(" + nomeVariavel + " = " + limitanteEsquerda + "; " + nomeVariavel + " <= " + limitanteDireita
                + "; " + nomeVariavel + "++){\n");

        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }

        saida.append("}\n");

        return null;
    }

    @Override
    public Void visitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        escopos.criarNovoEscopo();
        TabelaSimbolos escopoParametros = escopos.obterEscopoAtual();
        String tipo, nomeVariaveis;

        if (ctx.tipo_estendido() != null) {
            saida.append(geradorLib.getTipo(ctx.tipo_estendido().getText()));
        } else {
            saida.append("void");
        }

        saida.append(" " + ctx.IDENT().getText() + "(");

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

        //função
        if (ctx.tipo_estendido() != null) {
            escopoAtual.adicionar(ctx.IDENT().getText(), geradorLib.getTipoLa(ctx.tipo_estendido().getText()), TipoETS.FUNCAO, escopoParametros, false);
        } //procedimento
        else {
            escopoAtual.adicionar(ctx.IDENT().getText(), TipoLA.SEMTIPO, TipoETS.PROCEDIMENTO, escopoParametros, false);
        }

        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }

        saida.append("}\n");

        escopos.abandonarEscopo();

        return null;
    }

    @Override
    public Void visitCmdChamada(GramaticaParser.CmdChamadaContext ctx) {

        saida.append(ctx.IDENT().getText() + "(");

        int count = 0;

        for (GramaticaParser.ExpressaoContext ectx : ctx.expressao()) {
            if (count >= 1) {
                saida.append(", ");
            }
            saida.append(ectx.getText());
            count += 1;
        }

        saida.append(");\n");

        return null;
    }

    @Override
    public Void visitCmdRetorne(GramaticaParser.CmdRetorneContext ctx) {

        saida.append("return ");
        super.visitExpressao(ctx.expressao());
        saida.append(";\n");

        return null;
    }
}
