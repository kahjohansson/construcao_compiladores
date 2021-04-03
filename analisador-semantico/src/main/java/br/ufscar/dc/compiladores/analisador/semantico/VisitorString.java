package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import java.util.ArrayList;
import java.util.List;

public class VisitorString extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);

    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.tipo_basico() != null) {
            if (!escopoAtual.existe(ctx.IDENT().getText())) {
                TipoLA tipoLa = escopoAtual.getTipo(ctx.tipo_basico().getText());
                escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, TipoETS.CONSTANTE, null, false);
            }
        } else if (ctx.tipo() != null) {
            if (!escopoAtual.existeTipoEstendido(ctx.IDENT().getText())) {

                escopos.criarNovoEscopo();
                visitRegistro(ctx.tipo().registro());
                TabelaSimbolos escopoRegistro = escopos.obterEscopoAtual();
                escopos.abandonarEscopo();
                escopoAtual.adicionar(ctx.IDENT().getText(), TipoLA.REGISTRO, TipoETS.TIPO, escopoRegistro, false);
            }
        }

        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        TabelaSimbolos escopoParametros = null;
        List<TipoLA> tiposParametros = new ArrayList<>();
        TipoLA tipoLa;

        //declaracao de funcao
        if (ctx.tipo_estendido() != null) {
            if (!escopoAtual.existe(ctx.IDENT().getText())) {

                //adiciona parametros da funcao
                if (ctx.parametros() != null) {
                    escopos.criarNovoEscopo();
                    escopoParametros = escopos.obterEscopoAtual();
                    for (GramaticaParser.ParametroContext pctx : ctx.parametros().parametro()) {
                        for (GramaticaParser.IdentificadorContext ictx : pctx.identificador()) {
                            if (pctx.tipo_estendido().tipo_basico_ident().tipo_basico() != null) { //tipo basico
                                tipoLa = escopoAtual.getTipo(pctx.tipo_estendido().tipo_basico_ident().tipo_basico().getText());
                            } else { //tipo estendido
                                tipoLa = TipoLA.TIPOESTENDIDO;
                            }
                            escopoParametros.adicionar(ictx.getText(), tipoLa, TipoETS.VARIAVEL, null, false);
                            tiposParametros.add(tipoLa);
                        }
                    }

                }

                //adiciona funcao à tabela de simbolos
                if (ctx.tipo_estendido().tipo_basico_ident().tipo_basico() != null) { //tipo basico
                    tipoLa = escopoAtual.getTipo(ctx.tipo_estendido().tipo_basico_ident().tipo_basico().getText());
                } else { //tipo estendido
                    tipoLa = TipoLA.TIPOESTENDIDO;
                }
                if (tiposParametros.isEmpty()) {
                    escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, TipoETS.FUNCAO, null, false);
                } else {
                    escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, TipoETS.FUNCAO, escopoParametros, tiposParametros, false);
                }

                //realiza chamada de declaracao local
                ctx.declaracao_local().forEach(lctx -> {
                    visitDeclaracao_local(lctx);
                });
//                realiza chamada de cmd
                ctx.cmd().forEach(cctx -> {
                    super.visitCmd(cctx);
                });
                escopos.abandonarEscopo();
            }
        }

        return super.visitDeclaracao_global(ctx);
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        //verificacao de tipo
        TipoLA variavelTipo = TipoLA.INVALIDO;
        TabelaSimbolos escopoRegistro = null;
        boolean ponteiro = false;
        boolean tipoEstendidoExiste = false;

        if (ctx.tipo().registro() != null) { // tipo registro
            escopos.criarNovoEscopo();
            visitRegistro(ctx.tipo().registro());
            escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            variavelTipo = TipoLA.REGISTRO;

        } else { //tipo estendido
//            ponteiro = escopoAtual.ponteiro(ctx.tipo().tipo_estendido().getText());

            if (ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null) { // tipo basico
                variavelTipo = escopoAtual.getTipo(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText());

            } else { // tipo IDENT (estendido)
                variavelTipo = TipoLA.TIPOESTENDIDO;
                String tipoIdent = ctx.tipo().tipo_estendido().tipo_basico_ident().IDENT().getText();
                tipoEstendidoExiste = escopoAtual.existeTipoEstendido(tipoIdent);
                escopoRegistro = escopoAtual.getSubTabela(tipoIdent);
                if (!tipoEstendidoExiste) {

                    String mensagem = String.format("Linha %d: tipo %s nao declarado",
                            ctx.getStart().getLine(),
                            tipoIdent);
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);

//                    variavelTipo = escopoAtual.getTipo(tipoIdent);
                }
//                if (variavelTipo == TipoLA.INVALIDO) {
//                        
//                }
            }
        }

        //verificacao de nome de variavel e gravação da variável na tabela
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText()) || !tipoEstendidoExiste) {
                escopoAtual.adicionar(ictx.getText(), variavelTipo, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
            } else if (escopoAtual.existe(ictx.getText())) {
                String mensagem = String.format("Linha %d: identificador %s ja declarado anteriormente",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitVariavel(ctx);

    }

    //precisa dessa função?
//    @Override
//    public Void visitRegistro(GramaticaParser.RegistroContext ctx) {
//        
//        for(GramaticaParser.VariavelContext vctx: ctx.variavel()){
//            visitVariavel(vctx);
//        }
//
//
//        return super.visitRegistro(ctx);
//    }
    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(AnalisadorSemanticoLib.getNomeVariavel(ictx.getText()))) {
                String mensagem = String.format("Linha %d: identificador %s nao declarado LEIA",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitCmdEscreva(GramaticaParser.CmdEscrevaContext ctx) {

        TabelaSimbolos escopoAtual;
        TabelaSimbolos escopoRegistro = null;
        String nomeRegistro = null;
        String nomeVariavel;
        boolean registroExiste = true;

        for (GramaticaParser.ExpressaoContext ectx : ctx.expressao()) {
            if (!ectx.getText().contains("\"")) { //não é cadeia

                escopoAtual = escopos.obterEscopoAtual();

                nomeVariavel = ectx.getText();

                if (ectx.getText().contains(".")) { //é registro
                    nomeRegistro = AnalisadorSemanticoLib.getNomeRegistro(ectx.getText());
                    nomeVariavel = AnalisadorSemanticoLib.getNomeVariavel(ectx.getText());
                    registroExiste = escopoAtual.existe(nomeRegistro);
                    if (registroExiste) {
                        escopoRegistro = escopoAtual.getSubTabela(nomeRegistro);
                    }
                    if (escopoRegistro != null) {
                        escopoAtual = escopoRegistro;
                    }
                }

                if (!escopoAtual.existe(nomeVariavel) || (nomeRegistro != null && !registroExiste)) {
                    String mensagem = String.format("Linha %d: identificador %s nao declarado ESCREVA",
                            ectx.getStart().getLine(),
                            ectx.getText());
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            }
        }

        return super.visitCmdEscreva(ctx);
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.identificador() != null) {
            if (!escopoAtual.existe(AnalisadorSemanticoLib.getNomeVariavel(ctx.identificador().getText()))) { //TODO mudar para pegar separadamente parte 0 e 1
                String mensagem = String.format("Linha %d: identificador %s nao declarado NAO UNARIO",
                        ctx.identificador().getStart().getLine(),
                        ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitParcela_nao_unario(ctx);
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        if (ctx.identificador() != null) {
            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if (ts.existe(AnalisadorSemanticoLib.getNomeVariavel(ctx.identificador().getText()))) { //TODO mudar para pegar separadamente parte 0 e 1
                    return super.visitParcela_unario(ctx);
                }
            }
            //dispara erro de identificador não declarado
            String mensagem = String.format("Linha %d: identificador %s nao declarado UNARIO",
                    ctx.identificador().getStart().getLine(),
                    ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);

            //chamada de função ou procedimento
        } else if (ctx.ident != null) {
            TabelaSimbolos parametros;
            List<TipoLA> tiposParametros = new ArrayList<>();
            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if (ts.existe(ctx.IDENT().getText())) {
                    tiposParametros = ts.getTiposParametros(ctx.IDENT().getText());
                    //erro de quantidade de parametros diferente
                    if (tiposParametros.size() != ctx.expressao().size()) {
                        String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
                                ctx.getStart().getLine(), ctx.IDENT().getText());
                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                    } else {
                        for (int i = 0; i < tiposParametros.size(); i++) {
                            if (tiposParametros.get(i) != ts.getTipo(ctx.expressao(i).getText())) {
                                String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
                                        ctx.getStart().getLine(), ctx.IDENT().getText());
                                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                            }
                        }
                    }
                }
            }
        }

        return super.visitParcela_unario(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

//        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
//        TabelaSimbolos subTabela = null;
//
//        TipoLA tipoExpressao = verificadorTipo.verificaTipo(ctx.expressao());
//        TipoLA tipoIdentificador = verificadorTipo.verificaTipo(ctx.identificador());
//        
//        String nomeUltimoIdentificador = ctx.identificador().getText();
//        String nomeIdentificador = ctx.identificador().getText();
//        if(ctx.simbolo != null){
//            nomeIdentificador = ctx.simbolo.getText() + nomeIdentificador;
//        }
//
//        if (tipoExpressao == TipoLA.INVALIDO) {
//            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
//                    ctx.getStart().getLine(), nomeIdentificador);
//            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
//        } else if (tipoIdentificador != tipoExpressao) { //TODO modificar para novo tipo registro
//  
//            //talvez redundante
////            if(ctx.identificador().identLista != null){ // verifica se é registro, se for, modifica o nome da variavel a ser analisada e o escopo
////                escopoAtual = escopoAtual.getSubTabela(ctx.identificador().ident1.getText());
////                nomeUltimoIdentificador = ctx.identificador().identLista.get(0).getText();
////            }
//            
////            verificação se identificador nesse contexto é ponteiro ou não
//            boolean identificadorPonteiro = escopoAtual.ponteiro(ctx.getText().startsWith("^"), escopoAtual.verificarPonteiro(nomeUltimoIdentificador));
////            
//            if (!(escopoAtual.verificar(nomeUltimoIdentificador) == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO)
//                    || !(identificadorPonteiro && ctx.expressao().getText().startsWith("&"))) {
//                String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
//                        ctx.getStart().getLine(), ctx.identificador().getText());
//                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
//            }
//        }
        //--------------------------------------------------------------------------------------------
        TipoLA tipoIdentificador = verificadorTipo.verificaTipo(ctx.identificador());
        TipoLA tipoExpressao = verificadorTipo.verificaTipo(ctx.expressao());

//        AnalisadorSemanticoLib.adicionarErroSemantico("tipo identificador: " + tipoIdentificador);
//        AnalisadorSemanticoLib.adicionarErroSemantico("tipo expressao: " + tipoExpressao);
        boolean condicao;

        condicao = (tipoIdentificador == TipoLA.INVALIDO || tipoExpressao == TipoLA.INVALIDO)
                || //identificador e expressao invalidos
                (tipoIdentificador != tipoExpressao
                && //tipos diferentes
                !(tipoIdentificador == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO) //identificador real e expressao inteira
                );

        if (condicao) {
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                    ctx.getStart().getLine(), ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        }

        return super.visitCmdAtribuicao(ctx);
    }

//    @Override
//    public Void visitCmdChamada(GramaticaParser.CmdChamadaContext ctx) {
//        
//        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
//        TabelaSimbolos parametros;
//        List<TipoLA> tiposParametros = new ArrayList<>();
//        
//        if(escopoAtual.existe(ctx.IDENT().getText())){
//            tiposParametros = escopoAtual.getTiposParametros(ctx.IDENT().getText());
//            //erro de quantidade de parametros diferente
//            if(tiposParametros.size() != ctx.expressao().size()){
//                String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
//                    ctx.getStart().getLine(), ctx.IDENT().getText());
//                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
//            }else{
//                for (int i = 0; i < tiposParametros.size(); i++) {
//                    if(tiposParametros.get(i) != escopoAtual.getTipo(ctx.expressao(i).getText())){
//                        String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
//                            ctx.getStart().getLine(), ctx.IDENT().getText());
//                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
//                    }
//                }
//            }
//        }
//        
//        return super.visitCmdChamada(ctx);
//    }
}
