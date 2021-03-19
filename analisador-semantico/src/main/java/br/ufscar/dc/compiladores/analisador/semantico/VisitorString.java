package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

public class VisitorString extends GramaticaBaseVisitor<Void> {
    
    Escopos escopos = new Escopos();
    
//    @Override
//    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {
//        
//        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
//        
//        if (ctx.variavel() != null){
//            visitVariavel(ctx.variavel());
//        };
//        
//        return super;
//    }
    
    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        for(GramaticaParser.IdentificadorContext ictx : ctx.identificador()){
            if(! escopoAtual.existe(ictx.getText())) {
                TipoLA variavelTipo = escopoAtual.getTipo(ctx.tipo().getText());
                if(!(variavelTipo == TipoLA.INVALIDO)){
                    escopoAtual.adicionar(ictx.getText(), variavelTipo);
                }else{
                    AnalisadorSemanticoLib.adicionarErroSemantico(ictx.getStart().getLine(), 
                        "tipo", ctx.tipo().getText(), "não declarado"); 
                }
                
            }else{ // se existir
                AnalisadorSemanticoLib.adicionarErroSemantico(ictx.getStart().getLine(), 
                    "identificador", ictx.getText(), "ja declarado anteriormente"); 
            }
        }
        
        return super.visitVariavel(ctx);
        
    }
    
    @Override
    public Void visitIdentificador(GramaticaParser.IdentificadorContext ctx) {
        return null;
    }
    
}
