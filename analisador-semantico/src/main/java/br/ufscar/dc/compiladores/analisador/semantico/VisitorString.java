package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

public class VisitorString extends GramaticaBaseVisitor<String> {
    
    Escopos escopos = new Escopos();
    
    @Override
    public String visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        if (ctx.variavel() != null){
            String returnVariavel = visitVariavel(ctx.variavel());
        }
        
        return null;
    }
    
    @Override
    public String visitVariavel(GramaticaParser.VariavelContext ctx) {
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        
        String nomeIdent = visitIdentificador(ctx.ident1);
        if(! escopoAtual.existe(nomeIdent)) { // se não existir na tabela de símbolos do escopo atual
            TipoLA variavelTipo = escopoAtual.getTipo(ctx.tipo().getText()); //TODO: PARTE ERRADA AINDA, POIS VERIFICACAO DEVE SER FEITA EM TIPO BASICO
            escopoAtual.adicionar(nomeIdent, variavelTipo);
        }else{ // se existir
            AnalisadorSemanticoLib.adicionarErroSemantico(ctx.ident1.getStart().getLine(), "identificador já declarado anteriormente"); // POSSO FAZER ESSA GAMBIARRA PRA PEGAR A LINHA?
        }
        
        return null;
        
    }
    
    @Override
    public String visitIdentificador(GramaticaParser.IdentificadorContext ctx) {
        
        return ctx.getText();
    }
    
}
