package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;

public class VisitorString extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);

    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.tipo_basico() != null) {
            if (!escopoAtual.existe(ctx.IDENT().getText())) {
                TipoLA tipoLa = escopoAtual.getTipo(ctx.tipo_basico().getText());
                escopoAtual.adicionar(ctx.IDENT().getText(), null, tipoLa, TipoETS.CONSTANTE, null, false);
            }
        }else if(ctx.tipo() != null){
            
        }
        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        //verificacao de tipo
        TipoLA variavelTipo = TipoLA.INVALIDO;
        TabelaSimbolos escopoRegistro = null;
        String tipoEstendido = null;
        boolean ponteiro = false;
        if (ctx.tipo().registro() != null) {
            escopos.criarNovoEscopo();
            visitRegistro(ctx.tipo().registro());
            escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            variavelTipo = TipoLA.REGISTRO;
        }else{
            ponteiro = escopoAtual.ponteiro(ctx.tipo().tipo_estendido().getText());
            
            if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
                variavelTipo = escopoAtual.getTipo(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText());
            }else{
                variavelTipo = TipoLA.TIPOESTENDIDO;
                String tipoIdent = ctx.tipo().tipo_estendido().tipo_basico_ident().IDENT().getText();

                //TODO verificar se tipo é estendido, para isso precisa-se saber dado um escopo, quais tipos estendidos ele contem
                //talvez criar lista com tipos entendidos do escopo
                variavelTipo = escopoAtual.getTipo(tipoIdent);
                if (variavelTipo == TipoLA.INVALIDO) {
                        String mensagem = String.format("Linha %d: tipo %s nao declarado",
                                ctx.getStart().getLine(),
                                tipoIdent);
                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            }
        }
        
        //verificacao de nome de variavel
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                escopoAtual.adicionar(ictx.getText(), tipoEstendido, variavelTipo, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
            } else {
                String mensagem = String.format("Linha %d: identificador %s ja declarado anteriormente",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitVariavel(ctx);

    }

    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                String mensagem = String.format("Linha %d: identificador %s não declarado",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.identificador() != null) {
            if (!escopoAtual.existe(ctx.identificador().getText())) { //TODO mudar para pegar separadamente parte 0 e 1
                String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ctx.identificador().getStart().getLine(),
                        ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitParcela_nao_unario(ctx);
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.identificador() != null) {
            if (!escopoAtual.existe(ctx.identificador().getText())) { //TODO mudar para pegar separadamente parte 0 e 1
                String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ctx.identificador().getStart().getLine(),
                        ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitParcela_unario(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        TipoLA tipoExpressao = verificadorTipo.verificaTipo(ctx.expressao());
        
        String nomeIdentificador = ctx.identificador().getText();
        if(ctx.simbolo != null){
            nomeIdentificador = ctx.simbolo.getText() + nomeIdentificador;
        }

        if (tipoExpressao == TipoLA.INVALIDO) {
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                    ctx.getStart().getLine(), nomeIdentificador);
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        } else if (escopoAtual.verificar(ctx.identificador().getText()) != tipoExpressao) { //TODO modificar para novo tipo registro
            
            //verificação se identificador nesse contexto é ponteiro ou não
            boolean identificadorPonteiro = escopoAtual.ponteiro(ctx.getText().startsWith("^"), escopoAtual.verificarPonteiro(ctx.identificador().getText()));
            
            if (!(escopoAtual.verificar(ctx.identificador().getText()) == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO)
                    || !(identificadorPonteiro && ctx.expressao().getText().startsWith("&"))) {
                String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                        ctx.getStart().getLine(), nomeIdentificador);
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdAtribuicao(ctx);
    }

}
