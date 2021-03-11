package br.ufscar.dc.compiladores.analisador.sintatico;

import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.RecognitionException;

/*
Classe principal e única do analisador léxico
input: arquivo a ser analisado léxicamente segundo a gramática LA
output: arquivo com o resultado da análise léxica, cada linha possui um token
*/
public class Main {
    public static void main(String args[]) {
                
        try(PrintWriter pw = new PrintWriter(new File(args[1]))) {
            
            CharStream cs = CharStreams.fromFileName(args[0]); //manipula arquivo de entrada
            GramaticaLexer lexer = new GramaticaLexer(cs);
            
            ArrayList<String> labels = new ArrayList<String>(Arrays.asList(
                    "PALAVRA_CHAVE", "SIMBOLO", "OPERADOR_LOG_PALAVRA", 
                    "OPERADOR_MAT", "OPERADOR_LOG", "OPERADOR_OUTROS")); //listagem dos padrões com token do formato <lexema,lexema>

            boolean error = false;
            
            Token t = null; //instancia variável do token
            while ((t = lexer.nextToken()).getType() != Token.EOF) { //laço que itera os tokens até o fim do arquivo
                String tipo = GramaticaLexer.VOCABULARY.getDisplayName(t.getType()); //recupera tipo do token
                String valor = t.getText(); //recupera valor do token
               
                if("COMENTARIO_NAO_FECHADO".equals(tipo)) {
                    pw.println("Linha " + t.getLine() + ": comentario nao fechado"); //saída para erro de comentário não fechado na mesma linha
                    pw.println("Fim da compilacao");
                    error = true;
                    break;
                } else if("CADEIA_NAO_FECHADA".equals(tipo)) {
                    pw.println("Linha " + t.getLine() + ": cadeia literal nao fechada"); //saída para erro de cadeia não fechada na mesma linha
                    pw.println("Fim da compilacao");
                    error = true;
                    break;
                } else if("ERRO_GERAL".equals(tipo)) {
                    pw.println("Linha " + t.getLine() + ": " + valor + " - simbolo nao identificado"); //saída para símbolos que não fazem parte da gramática LA
                    pw.println("Fim da compilacao");
                    error = true;
                    break;
                }
            }
     
            if (! error){
            
                CharStream cs1 = CharStreams.fromFileName(args[0]);
                GramaticaLexer lexer1 = new GramaticaLexer(cs1);
                CommonTokenStream tokens = new CommonTokenStream(lexer1);
                GramaticaParser parser = new GramaticaParser(tokens);

                MyCustomErrorListener mcel = new MyCustomErrorListener(pw);
                parser.addErrorListener(mcel);

                try{
                    parser.programa();
                } catch (Exception e){
                    pw.println(e.getMessage());
                }
                pw.println("Fim da compilacao");
            }
            
        } catch (Exception e){
            
        }
    }
}
