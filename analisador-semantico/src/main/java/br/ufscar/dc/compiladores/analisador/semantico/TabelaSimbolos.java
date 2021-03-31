package br.ufscar.dc.compiladores.analisador.semantico;

import java.util.ArrayList;
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
    
    //tipo entrada tabela de simbolos
    public enum TipoETS {
        VARIAVEL,
        FUNCAO,
        PROCEDIMENTO,
        TIPO,
        CONSTANTE
    }
    
    ArrayList<String> tiposEstendidos = new ArrayList<String>();
    
    class EntradaTabelaSimbolos {
        String nome;
        String tipoLaEstendido;
        TipoLA tipoLa;
        TipoETS tipoEts;
        TabelaSimbolos subtabela;
        boolean ponteiro;

        private EntradaTabelaSimbolos(String nome, String tipoLaEstendido, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, boolean ponteiro) {
            this.nome = nome;
            this.tipoLaEstendido = tipoLaEstendido;
            this.tipoLa = tipoLa;
            this.tipoEts = tipoEts;
            this.subtabela = subtabela;
            this.ponteiro = ponteiro;
            //TODO adicionar lista de parametros para procedimentos e funções
        }
        
        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts, boolean ponteiro) {
            this(nome, null, tipoLa, tipoEts, null, ponteiro);
        }
        
        
    }
    
    private final Map<String, EntradaTabelaSimbolos> tabela;
    
    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String nome, String tipoLaEstendido, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, boolean ponteiro) {
        tabela.put(nome, new EntradaTabelaSimbolos(nome, tipoLaEstendido, tipoLa, tipoEts, subtabela, ponteiro));
    }
    
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
        
    public TipoLA verificar(String nome) {
        return tabela.get(nome).tipoLa;
    }
    
    public String verificarTipoLaEstendido(String nome){
        return tabela.get(nome).tipoLaEstendido;
    }
    
    public TabelaSimbolos getSubTabela(String nome){
        return tabela.get(nome).subtabela;
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
    
    public boolean ponteiro(String texto){
        if (texto.startsWith("^") || texto.startsWith("&")){
            return true;
        }
        return false;
    }
    
    public boolean verificarPonteiro(String nome){
        return tabela.get(nome).ponteiro;
    }
    
    public boolean ponteiro(boolean simboloPonteiro, boolean identificador){
        if(simboloPonteiro && identificador) { return false; }
        else if(simboloPonteiro && !identificador) { return true;}
        else if(!simboloPonteiro && identificador) { return true;}
        return false;
    }
    
    public void adicionaTipoEstendido(String tipoEstendido){
        tiposEstendidos.add(tipoEstendido);
    }
    
    public boolean existeTipoEstendido(String tipoEstendido){
        return tiposEstendidos.contains(tipoEstendido);
    }
}
