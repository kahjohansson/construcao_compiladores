package br.ufscar.dc.compiladores.analisador.lexico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Main {
    public static void main(String args[]) {
        try {
            CharStream cs = CharStreams.fromFileName(args[0]);
            Gramatica gram = new Gramatica(cs);
            ArrayList<String> labels = new ArrayList<String>(Arrays.asList("PALAVRA_CHAVE", "SIMBOLO"));

            Token t = null;
            while ((t = gram.nextToken()).getType() != Token.EOF) {
                String tipo = Gramatica.VOCABULARY.getDisplayName(t.getType());
                String valor = t.getText();
                
                if (labels.contains(tipo)){
                    System.out.println("<'"+ valor + "','" + valor +"'>");
                } else{
                    System.out.println("<'"+ valor + "'," + tipo +">");
                }
            }
        } catch (IOException ex) {
        }
    }
}
