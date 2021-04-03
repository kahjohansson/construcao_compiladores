package br.ufscar.dc.compiladores.analisador.semantico;


import java.util.LinkedList;
import java.util.List;

public class Escopos {

    private LinkedList<TabelaSimbolos> pilhaDeTabelas;

    public Escopos() {
        pilhaDeTabelas = new LinkedList<>();
        criarNovoEscopo();
    }

    public void criarNovoEscopo() {
        pilhaDeTabelas.push(new TabelaSimbolos());
    }

    public TabelaSimbolos obterEscopoAtual() {
        return pilhaDeTabelas.peek();
    }

    public List<TabelaSimbolos> percorrerEscoposAninhados() {
        return pilhaDeTabelas;
    }

    public void abandonarEscopo() {
        pilhaDeTabelas.pop();
    }
    
    public void adicionarEscopo(TabelaSimbolos tabela){
        pilhaDeTabelas.push(tabela);
    }
}