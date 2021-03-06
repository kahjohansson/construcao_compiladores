package br.ufscar.dc.compiladores.analisador.lexico;

import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Main {
    public static void main(String args[]) {
        try {
            CharStream cs = CharStreams.fromFileName(args[0]);
            Gramatica gram = new Gramatica(cs);

            Token t = null;
            while ((t = gram.nextToken()).getType() != Token.EOF) {
                System.out.println("<"+ Gramatica.VOCABULARY.getDisplayName(t.getType()) + "," + t.getText()+">");
            }
        } catch (IOException ex) {
        }
    }
}
