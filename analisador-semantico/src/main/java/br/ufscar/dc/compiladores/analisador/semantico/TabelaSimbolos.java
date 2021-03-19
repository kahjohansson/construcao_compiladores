package br.ufscar.dc.compiladores.analisador.semantico;

import java.util.HashMap;
import java.util.Map;

public class TabelaSimbolos {
  
    public enum TipoLA {
        LITERAL,
        INTEIRO,
        REAL,
        LOGICO,
        INVALIDO,
        SEMTIPO
    }
    
    public enum TipoETS {
        VARIAVEL,
        FUNCAO,
        PROCEDIMENTO,
        TIPO,
        REGISTRO,
        CONSTANTE
    }
    //TODO modificar metodos pra adicionar TipoETS
    
    class EntradaTabelaSimbolos {
        String nome;
        TipoLA tipoLa;
        TipoETS tipoEts;

        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts) {
            this.nome = nome;
            this.tipoLa = tipoLa;
            this.tipoEts = tipoEts;
        }
    }
    
    private final Map<String, EntradaTabelaSimbolos> tabela;
    
    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, TipoLA tipoLa, TipoETS tipoEts) {
        tabela.put(nome, new EntradaTabelaSimbolos(nome, tipoLa, tipoEts));
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoLA verificar(String nome) {
        return tabela.get(nome).tipoLa;
    }
    
    // retorna tipo TipoLA a partir de uma string
    public TipoLA getTipo(String tipo) {
        
        TipoLA tipoLa = TipoLA.INVALIDO;
        
        switch (tipo){
            case "literal":
                tipoLa = TipoLA.LITERAL;
                break;
            case "inteiro":
                tipoLa = TipoLA.INTEIRO;
                break;
            case "real":
                tipoLa = TipoLA.REAL;
                break;
            case "logico":
                tipoLa = TipoLA.LOGICO;
                break;
            default:
                break;
        }
        
        return tipoLa;
    }
    
}
