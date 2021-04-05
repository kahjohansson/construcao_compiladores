package br.ufscar.dc.compiladores.analisador.semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaSimbolos {
  
    //tipos LA
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
        CONSTANTE,
        PARAMETRO
    }
        
    class EntradaTabelaSimbolos {
        
        String nome;
        TipoLA tipoLa;
        TipoETS tipoEts;
        TabelaSimbolos subtabela;
        public List<TipoLA> tiposParametros;
        boolean ponteiro;

        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, List<TipoLA> tiposParametros, boolean ponteiro) {
            this.nome = nome;
            this.tipoLa = tipoLa;
            this.tipoEts = tipoEts;
            this.subtabela = subtabela;
            this.tiposParametros = tiposParametros;
            this.ponteiro = ponteiro;
        }
        
        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts, boolean ponteiro) {
            this(nome, tipoLa, tipoEts, null, null, ponteiro);
        }
        
        private EntradaTabelaSimbolos(String nome, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, boolean ponteiro) {
            this(nome, tipoLa, tipoEts, subtabela, null, ponteiro);
        }
        
    }
    
    private final Map<String, EntradaTabelaSimbolos> tabela;
    
    //construtor
    public TabelaSimbolos() {
        this.tabela = new HashMap<>();
    }
    
    //adiciona entrada da tabela de símbolos
    public void adicionar(String nome, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, boolean ponteiro) {
        tabela.put(nome, new EntradaTabelaSimbolos(nome, tipoLa, tipoEts, subtabela, ponteiro));
    }
    
    //adiciona entrada da tabela de símbolos
    public void adicionar(String nome, TipoLA tipoLa, TipoETS tipoEts, TabelaSimbolos subtabela, List<TipoLA> tiposParametros, boolean ponteiro) {
        tabela.put(nome, new EntradaTabelaSimbolos(nome, tipoLa, tipoEts, subtabela, tiposParametros, ponteiro));
    }
    
    //verifica se existe entrada da tabela de símbolos com chave nome
    public boolean existe(String nome) {
        return tabela.containsKey(nome);
    }
        
    //verifica tipo LA de entrada da tabela de símbolos
    public TipoLA verificar(String nome) {
        return tabela.get(nome).tipoLa;
    }
    
    //retorna subtabela da entrada da tabela de símbolos
    public TabelaSimbolos getSubTabela(String nome){
        return tabela.get(nome).subtabela;
    }
    
    //retorna lista de tipos de parâmetros de função/procedimento
    public List<TipoLA> getTiposParametros(String nome){
        return tabela.get(nome).tiposParametros;
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
    
    //verifica se variável é um ponteiro
    public boolean ponteiro(String texto){
        if (texto.startsWith("^") || texto.startsWith("&")){
            return true;
        }
        return false;
    }
    
    //verifica se variável é um ponteiro
    public boolean verificarPonteiro(String nome){
        return tabela.get(nome).ponteiro;
    }
    
    //verifica se variável é um ponteiro
    public boolean ponteiro(boolean simboloPonteiro, boolean identificador){
        if(simboloPonteiro && identificador) { return false; }
        else if(simboloPonteiro && !identificador) { return true;}
        else if(!simboloPonteiro && identificador) { return true;}
        return false;
    }
    
    //verifica se existe tipo estendido
    public boolean existeTipoEstendido(String tipoEstendido){
        if(! tabela.containsKey(tipoEstendido))
            return false;
        return tabela.get(tipoEstendido).tipoEts == TipoETS.TIPO;
    }
}
