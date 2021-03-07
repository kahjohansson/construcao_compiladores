package br.ufscar.dc.compiladores.analisador.lexico;

import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

/*
Classe principal e única do analisador léxico
input: arquivo a ser analisado léxicamente segundo a gramática LA
output: arquivo com o resultado da análise léxica, cada linha possui um token
*/
public class Main {
    public static void main(String args[]) {
                
        try {
            
            File myObj = new File(args[1]); //cria arquivo, se não existir
            FileWriter writer = new FileWriter(args[1]); //escritor do arquivo
            
            CharStream cs = CharStreams.fromFileName(args[0]); //manipula arquivo de entrada
            Gramatica gram = new Gramatica(cs);
            ArrayList<String> labels = new ArrayList<String>(Arrays.asList(
                    "PALAVRA_CHAVE", "SIMBOLO", "OPERADOR_LOG_PALAVRA", 
                    "OPERADOR_MAT", "OPERADOR_LOG", "OPERADOR_OUTROS")); //listagem dos padrões com token do formato <lexema,lexema>

            Token t = null; //instancia variável do token
            while ((t = gram.nextToken()).getType() != Token.EOF) { //laço que itera os tokens até o fim do arquivo
                String tipo = Gramatica.VOCABULARY.getDisplayName(t.getType()); //recupera tipo do token
                String valor = t.getText(); //recupera valor do token
               
                if (labels.contains(tipo)){ 
                    writer.write("<'"+ valor + "','" + valor +"'>\n"); //saída para tipos contidos na lista labels, do formato <lexema,lexema>
                } else if("COMENTARIO_NAO_FECHADO".equals(tipo)) {
                    writer.write("Linha " + t.getLine() + ": comentario nao fechado\n"); //saída para erro de comentário não fechado na mesma linha
                    break;
                } else if("CADEIA_NAO_FECHADA".equals(tipo)) {
                    writer.write("Linha " + t.getLine() + ": cadeia literal nao fechada\n"); //saída para erro de cadeia não fechada na mesma linha
                    break;
                } else if("ERRO_GERAL".equals(tipo)) {
                    writer.write("Linha " + t.getLine() + ": " + valor + " - simbolo nao identificado\n"); //saída para símbolos que não fazem parte da gramática LA
                    break;
                } else{
                    writer.write("<'"+ valor + "'," + tipo +">\n"); //saída do tipo <lexema,padrão>
                }
            }
            
            writer.close(); //fecha arquivo de escrita
        } catch (IOException ex) {
        }
    }
}
