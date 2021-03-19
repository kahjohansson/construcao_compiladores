package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;

public class VisitorString extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);
    
    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if(ctx.tipo_basico() != null) {
            if(! escopoAtual.existe(ctx.IDENT().getText())) {
                TipoLA tipoLa = escopoAtual.getTipo(ctx.tipo_basico().getText());
                escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, TipoETS.CONSTANTE);
            }
        }
        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                TipoLA variavelTipo = escopoAtual.getTipo(ctx.tipo().getText());
                escopoAtual.adicionar(ictx.getText(), variavelTipo, TipoETS.VARIAVEL);
                if (variavelTipo == TipoLA.INVALIDO) {
                    String mensagem = String.format("Linha %d: tipo %s não declarado",
                            ictx.getStart().getLine(),
                            ctx.tipo().getText());
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            } else {
                String mensagem = String.format("Linha %d: tipo %s não declarado",
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
                AnalisadorSemanticoLib.adicionarErroSemantico(ictx.getStart().getLine(),
                        "identificador", ictx.getText(), "nao declarado");
            }
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        if(ctx.identificador() != null) {
            if (! escopoAtual.existe(ctx.identificador().getText())) {
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

        if(ctx.identificador() != null){
            if (! escopoAtual.existe(ctx.identificador().getText())) {
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

        if(tipoExpressao == TipoLA.INVALIDO) {
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s", 
                    ctx.getStart().getLine(), ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        } else if(escopoAtual.verificar(ctx.identificador().getText()) != tipoExpressao){
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s", 
                    ctx.getStart().getLine(), ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        }

        return super.visitCmdAtribuicao(ctx);
    }
    
}
