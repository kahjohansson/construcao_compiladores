package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;


public class AnalisadorSemantico extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    
    @Override
    public Void visitPrograma(GramaticaParser.ProgramaContext ctx){
        AnalisadorSemanticoLib.adicionarErroSemantico(ctx.declaracoes().getStart(), "chegou em programa");
        return super.visitPrograma(ctx);
    }
    
    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {
        
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        if(ctx.variavel() != null){
            if (escopoAtual.existe(ctx.variavel().getText())) {
                AnalisadorSemanticoLib.adicionarErroSemantico(ctx.variavel().getStart(), "variável já declarada anteriormente"); //TODO alterar linha
            }
        } else if(ctx.tipo_basico() != null){
            if(escopoAtual.existe(ctx.IDENT().getText())) {
                AnalisadorSemanticoLib.adicionarErroSemantico(ctx.IDENT().getSymbol(), "identificador já declarado anteriormente");
            }else{
                TipoLA tipo = TipoLA.INVALIDO;
                switch(ctx.tipo_basico().getText()){
                    case "literal":
                        tipo = TipoLA.LITERAL;
                        break;
                    case "inteiro":
                        tipo = TipoLA.INTEIRO;
                    case "real":
                        tipo = TipoLA.REAL;
                    case "logico":
                        tipo = TipoLA.LOGICO;
                    default:
                        break;
                }
                escopoAtual.adicionar(ctx.variavel().getText(), tipo);
            }
        }else if(ctx.tipo() != null){
        }
        
        return super.visitDeclaracao_local(ctx);
    }
    
    
}
