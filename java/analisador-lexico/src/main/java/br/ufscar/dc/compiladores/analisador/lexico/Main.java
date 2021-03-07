package br.ufscar.dc.compiladores.analisador.lexico;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Main {
    public static void main(String args[]) {
        
        
        try {
            
            File myObj = new File(args[1]); //cria arquivo, se não existir
            FileWriter writer = new FileWriter(args[1]); //writer do arquivo
            
            CharStream cs = CharStreams.fromFileName(args[0]);
            Gramatica gram = new Gramatica(cs);
            ArrayList<String> labels = new ArrayList<String>(Arrays.asList("PALAVRA_CHAVE", "SIMBOLO", 
            "OPERADOR_LOG_PALAVRA", "OPERADOR_MAT", "OPERADOR_LOG", "OPERADOR_OUTROS"));

            Token t = null;
            int line_count = 0;
            while ((t = gram.nextToken()).getType() != Token.EOF) {
                String tipo = Gramatica.VOCABULARY.getDisplayName(t.getType());
                String valor = t.getText();
               
                if (labels.contains(tipo)){
                    writer.write("<'"+ valor + "','" + valor +"'>\n");
                } else if(tipo == "ERRO_GERAL") {
                    writer.write("Linha " + t.getLine() + ": " + valor + " - simbolo nao identificado\n");
                    break;
                } else{
                    writer.write("<'"+ valor + "'," + tipo +">\n");
                }
            }
            
            writer.close();
        } catch (IOException ex) {
        }
    }
}
