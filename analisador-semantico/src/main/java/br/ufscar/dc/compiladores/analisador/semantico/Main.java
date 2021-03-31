package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.GramaticaParser.ProgramaContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

/*
Classe principal e única do analisador léxico e sintático
input: arquivo a ser analisado léxicamente e sintáticamente segundo a gramática LA
output: arquivo com erros léxicos e sintáticos, se houverem
 */
public class Main {

    public static boolean analiseLexica(String args[], PrintWriter pw) throws IOException {

        CharStream cs = CharStreams.fromFileName(args[0]); //manipula arquivo de entrada
        GramaticaLexer lexer = new GramaticaLexer(cs);

        ArrayList<String> labels = new ArrayList<String>(Arrays.asList(
                "PALAVRA_CHAVE", "SIMBOLO", "OPERADOR_LOG_PALAVRA",
                "OPERADOR_MAT", "OPERADOR_LOG", "OPERADOR_OUTROS")); //listagem dos padrões com token do formato <lexema,lexema>

        boolean error = false; // variável que indica se houve erro léxico

        Token t = null; //instancia variável do token
        while ((t = lexer.nextToken()).getType() != Token.EOF) { //laço que itera os tokens até o fim do arquivo
            String tipo = GramaticaLexer.VOCABULARY.getDisplayName(t.getType()); //recupera tipo do token
            String valor = t.getText(); //recupera valor do token

            if ("COMENTARIO_NAO_FECHADO".equals(tipo)) {
                pw.println("Linha " + t.getLine() + ": comentario nao fechado"); //saída para erro de comentário não fechado na mesma linha
                error = true;
                break;
            } else if ("CADEIA_NAO_FECHADA".equals(tipo)) {
                pw.println("Linha " + t.getLine() + ": cadeia literal nao fechada"); //saída para erro de cadeia não fechada na mesma linha
                error = true;
                break;
            } else if ("ERRO_GERAL".equals(tipo)) {
                pw.println("Linha " + t.getLine() + ": " + valor + " - simbolo nao identificado"); //saída para símbolos que não fazem parte da gramática LA
                error = true;
                break;
            }

        }

        return error;
    }

    public static void analiseSintatica(String args[], PrintWriter pw) throws IOException {

        CharStream cs = CharStreams.fromFileName(args[0]);
        GramaticaLexer lexer = new GramaticaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GramaticaParser parser = new GramaticaParser(tokens); // instância do parser

        ErrorListener mcel = new ErrorListener(pw); // instância do tratador de erros
        parser.addErrorListener(mcel);

        try {
            parser.programa(); // tenta fazer a análise do programa
        } catch (Exception e) {
            pw.println(e.getMessage()); // recupera a mensagem de erro causada pela exceção do tratador de erros
        }
    }

    public static ProgramaContext analiseSemantica(String args[], PrintWriter pw) throws IOException {

        CharStream cs = CharStreams.fromFileName(args[0]);
        GramaticaLexer lexer = new GramaticaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GramaticaParser parser = new GramaticaParser(tokens);
        ProgramaContext arvore = parser.programa();
        VisitorString analisador = new VisitorString();
        analisador.visitPrograma(arvore);
        AnalisadorSemanticoLib.errosSemanticos.forEach((s) -> pw.println(s));
        
        return arvore;
    }
    
    public static void geracaoCodigoC(String args[], PrintWriter pw, ProgramaContext arvore) throws IOException {
        GeradorCodigoC gerador = new GeradorCodigoC();
        gerador.visitPrograma(arvore);
        pw.print(gerador.saida.toString());
    }

    public static void main(String args[]) {

        try (PrintWriter pw = new PrintWriter(new File(args[1]))) { // instância do escritor do arquivo de log

            boolean error;
            error = analiseLexica(args, pw);

            if (!error) { // se não houve erro léxico, é feita a análise sintática
                analiseSintatica(args, pw);
            }
            
            ProgramaContext arvore = analiseSemantica(args, pw);
            
            geracaoCodigoC(args, pw, arvore);

            pw.println("Fim da compilacao"); // mensagem final do arquivo de log de análise léxica e sintática

        } catch (Exception e) {

        }
    }
}
