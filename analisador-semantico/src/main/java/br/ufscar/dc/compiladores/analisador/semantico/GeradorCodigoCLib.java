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
    
    public String getCodigoTipo(String tipo){
        if(tipo.equals("int")){
            return "d";
        }else if(tipo.equals("float")){
            return "f";
        }
        return null;
    }
    
    public String getCodigoTipo(TipoLA tipoLa){
        if(tipoLa == TipoLA.INTEIRO){
            return "d";
        }else if(tipoLa == TipoLA.REAL){
            return "f";
        }
        return null;
    }
}
