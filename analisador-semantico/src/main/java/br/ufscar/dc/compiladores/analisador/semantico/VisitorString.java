package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;

public class VisitorString extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    
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
                    AnalisadorSemanticoLib.adicionarErroSemantico(ictx.getStart().getLine(),
                            "tipo", ctx.tipo().getText(), "não declarado");
                }
            } else {
                AnalisadorSemanticoLib.adicionarErroSemantico(ictx.getStart().getLine(),
                        "identificador", ictx.getText(), "ja declarado anteriormente");
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
            AnalisadorSemanticoLib.adicionarErroSemantico(ctx.identificador().getStart().getLine(),
                    "identificador", ctx.identificador().getText(), "nao declarado");
            }
        }
        
        return super.visitParcela_nao_unario(ctx);
    }
    
    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if(ctx.identificador() != null){
            if (! escopoAtual.existe(ctx.identificador().getText())) {
                AnalisadorSemanticoLib.adicionarErroSemantico(ctx.identificador().getStart().getLine(),
                        "identificador", ctx.identificador().getText(), "nao declarado");
            }
        }

        return super.visitParcela_unario(ctx);
    }
    
}
