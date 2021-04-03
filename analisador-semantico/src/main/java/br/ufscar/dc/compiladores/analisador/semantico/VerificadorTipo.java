package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import org.antlr.v4.runtime.Token;

public class VerificadorTipo {
    
    Escopos escopos;

    public VerificadorTipo(Escopos escopos) {
        this.escopos = escopos;
    }
    
    public TipoLA verificaTipo(GramaticaParser.ExpressaoContext ctx){
        TipoLA ret = verificaTipo(ctx.termo_logico(0));
        
        for (GramaticaParser.Termo_logicoContext tctx : ctx.termo_logico()) {
            TipoLA tipo = verificaTipo(tctx);
            if (tipo != ret){
                return TipoLA.INVALIDO;
                //TODO tratar erro semântico
            }
        }
        
        return ret;
    }
    
    public TipoLA verificaTipo(GramaticaParser.Termo_logicoContext ctx){
        TipoLA ret = verificaTipo(ctx.fator_logico(0));
        
        for (GramaticaParser.Fator_logicoContext fctx : ctx.fator_logico()) {
            TipoLA tipo = verificaTipo(fctx);
            if (tipo != ret){
                return TipoLA.INVALIDO;
                //TODO tratar erro semântico
            }
        }
        
        return ret;
    }
    
    public TipoLA verificaTipo(GramaticaParser.Fator_logicoContext ctx){
        
        return verificaTipo(ctx.parcela_logica());
    }
    
    public TipoLA verificaTipo(GramaticaParser.Parcela_logicaContext ctx){
        
        if(ctx.exp_relacional() != null){
            return verificaTipo(ctx.exp_relacional());
        }
        
        return TipoLA.LOGICO;
    }
    
    public TipoLA verificaTipo(GramaticaParser.Exp_relacionalContext ctx){
        
        if(ctx.op_relacional() != null) {
            return TipoLA.LOGICO;
        }
        
        return verificaTipo(ctx.exp_aritmetica(0));
    }
    
    public TipoLA verificaTipo(GramaticaParser.Exp_aritmeticaContext ctx){
        TipoLA ret = verificaTipo(ctx.termo(0));
        
        for (GramaticaParser.TermoContext tctx : ctx.termo()) {
            TipoLA tipo = verificaTipo(tctx);
            if (tipo != ret && !((ret == TipoLA.INTEIRO && tipo == TipoLA.REAL) || (ret == TipoLA.REAL && tipo == TipoLA.INTEIRO))){ //TODO caso necessário, testar a compatibilidade
                return TipoLA.INVALIDO;
                //TODO tratar erro semântico
            }
        }
        
        return ret;
    }
    
    public TipoLA verificaTipo(GramaticaParser.TermoContext ctx){
        TipoLA ret = verificaTipo(ctx.fator(0));
        
        for (GramaticaParser.FatorContext fctx : ctx.fator()) {
            TipoLA tipo = verificaTipo(fctx);
            if (tipo != ret && !((ret == TipoLA.INTEIRO && tipo == TipoLA.REAL) || (ret == TipoLA.REAL && tipo == TipoLA.INTEIRO))){ //TODO caso necessário, testar a compatibilidade
                return TipoLA.INVALIDO;
                //TODO tratar erro semântico
            }
        }
        
        return ret;
    }
    
    public TipoLA verificaTipo(GramaticaParser.FatorContext ctx){
        TipoLA ret = verificaTipo(ctx.parcela(0));
        
        for (GramaticaParser.ParcelaContext pctx : ctx.parcela()) {
            TipoLA tipo = verificaTipo(pctx);
            if (tipo != ret){ //TODO caso necessário, testar a compatibilidade
                return TipoLA.INVALIDO;
                //TODO tratar erro semântico
            }
        }
        
        return ret;
    }
    
    public TipoLA verificaTipo(GramaticaParser.ParcelaContext ctx){
        
        if(ctx.parcela_unario() != null){
            return verificaTipo(ctx.parcela_unario());
        }
        
        return verificaTipo(ctx.parcela_nao_unario());
    }

    private TipoLA verificaTipo(GramaticaParser.Parcela_unarioContext ctx) {
        
        if(ctx.identificador() != null) {
            //TODO buscar nas tabelas de símbolos
            return verificaTipo(ctx.identificador());
        } else if(ctx.IDENT() != null) {
            //TODO buscar o tipo de retorno nas tabelas de símbolo
        }else if(ctx.NUM_INT() != null) {
            return TipoLA.INTEIRO;
        }else if(ctx.NUM_REAL() != null) {
            return TipoLA.REAL;
        }
        return verificaTipo(ctx.expParenteses);
    }

    private TipoLA verificaTipo(GramaticaParser.Parcela_nao_unarioContext ctx) {
        
        if(ctx.identificador() != null) {
            return TipoLA.INTEIRO;
        }
        
        return TipoLA.LITERAL;
    }
    
    public TipoLA verificaTipo(GramaticaParser.IdentificadorContext ctx) {
       
        TabelaSimbolos subtabela = null;
        TipoLA tipoLa = TipoLA.INVALIDO;
        
        //se é uma variável do tipo básico
        if(ctx.identLista != null && !ctx.identLista.isEmpty() ){
            for(TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if(ts.existe(ctx.ident1.getText())){
                    subtabela = ts.getSubTabela(ctx.ident1.getText());
                    if(subtabela != null){
                        tipoLa = subtabela.verificar(ctx.identLista.get(0).getText());
                    }
                }
            }
            
        //é um registro
        }else{
            for(TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if(ts.existe(ctx.ident1.getText())){
                    tipoLa = ts.verificar(ctx.ident1.getText());
                }
            }
        }
        
        return tipoLa;
    }
}
