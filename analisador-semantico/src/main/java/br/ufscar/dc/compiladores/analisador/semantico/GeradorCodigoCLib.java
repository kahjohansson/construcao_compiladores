package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

class GeradorCodigoCLib {
    
    public String getTipo(TipoLA tipoLa){
        if(tipoLa == TipoLA.INTEIRO){
            return "int";
        }else if(tipoLa == TipoLA.LITERAL){
            return "char";
        }else if(tipoLa == TipoLA.REAL){
            return "float";
        }
        return null;
    }
    
    public String getTipo(String tipo){
        if(tipo.equals("inteiro")){
            return "int";
        }else if(tipo.equals("literal")){
            return "char";
        }else if(tipo.equals("real")){
            return "float";
        }
        return null;
    }
    
    public String getCodigoTipo(String tipo){
        if(tipo.equals("int")){
            return "d";
        }else if(tipo.equals("float")){
            return "f";
        }else if(tipo.equals("char")){
            return "s";
        }
        
        return null;
    }
    
    public String getCodigoTipo(TipoLA tipoLa){
        if(tipoLa == TipoLA.INTEIRO){
            return "d";
        }else if(tipoLa == TipoLA.REAL){
            return "f";
        }else if(tipoLa == TipoLA.LITERAL){
            return "s";
        }
        return null;
    }
}
