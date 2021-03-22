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
        SEMTIPO,
        TIPOESTENDIDO,
        REGISTRO
    }
    
    public enum TipoETS {
        VARIAVEL,
        FUNCAO,
        PROCEDIMENTO,
        TIPO,
        CONSTANTE
    }
    
    class EntradaTabelaSimbolos {
        String nome;
        String tipoLaEstendido;
        TipoLA tipoLa;
        TipoETS tipoEts;
        TabelaSimbolos subtabela;

        private EntradaTabelaSimbolos(String nome, String tipoLaEstendido, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela) {
            this.nome = nome;
            this.tipoLaEstendido = tipoLaEstendido;
            this.tipoLa = tipoLa;
            this.tipoEts = tipoEts;
            this.subtabela = subtabela;
            //TODO adicionar lista de parametros para procedimentos e funções
        }
        
        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts) {
            this(nome, null, tipoLa, tipoEts, null);
        }
        
        
    }
    
    private final Map<String, EntradaTabelaSimbolos> tabela;
    
    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, String tipoLaEstendido, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela) {
        tabela.put(nome, new EntradaTabelaSimbolos(nome, tipoLaEstendido, tipoLa, tipoEts, subtabela));
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
    
    public TipoLA verificar(String nome) {
        return tabela.get(nome).tipoLa;
    }
    
    // retorna tipo TipoLA dada uma string
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
