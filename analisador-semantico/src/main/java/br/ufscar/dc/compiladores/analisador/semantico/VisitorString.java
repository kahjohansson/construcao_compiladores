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
                escopoAtual.adicionar(ctx.IDENT().getText(), null, tipoLa, TipoETS.CONSTANTE, null);
            }
        }else if(ctx.tipo() != null){
            
        }
        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        TipoLA variavelTipo = TipoLA.INVALIDO;
        TabelaSimbolos escopoRegistro = null;
        String tipoLaEstendido = null;
        if (ctx.tipo().registro() != null) {
            escopos.criarNovoEscopo();
            visitRegistro(ctx.tipo().registro());
            escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            variavelTipo = TipoLA.REGISTRO;
        }else if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){
            variavelTipo = escopoAtual.getTipo(ctx.tipo().getText());
        }else{
            variavelTipo = TipoLA.TIPOESTENDIDO;
            tipoLaEstendido = ctx.tipo().tipo_estendido().tipo_basico_ident().IDENT().getText();
        }
        

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                
                escopoAtual.adicionar(ictx.getText(), tipoLaEstendido, variavelTipo, TipoETS.VARIAVEL, escopoRegistro);
                if (variavelTipo == TipoLA.INVALIDO) {
                    String mensagem = String.format("Linha %d: tipo %s nao declarado",
                            ictx.getStart().getLine(),
                            ctx.tipo().getText());
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            } else {
                String mensagem = String.format("Linha %d: tipo %s nao declarado",
                        ictx.getStart().getLine(),
                        ctx.getText());
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

        if (tipoExpressao == TipoLA.INVALIDO) {
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                    ctx.getStart().getLine(), ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        } else if (escopoAtual.verificar(ctx.identificador().getText()) != tipoExpressao) { //TODO modificar para novo tipo registro
            if (!(escopoAtual.verificar(ctx.identificador().getText()) == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO)) {
                String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                        ctx.getStart().getLine(), ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdAtribuicao(ctx);
    }

}
