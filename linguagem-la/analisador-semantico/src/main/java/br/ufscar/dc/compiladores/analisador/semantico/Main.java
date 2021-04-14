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
    

    public static boolean analiseSintatica(String args[], PrintWriter pw) throws IOException {
        
        boolean erro = false;

        CharStream cs = CharStreams.fromFileName(args[0]);
        GramaticaLexer lexer = new GramaticaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GramaticaParser parser = new GramaticaParser(tokens); // instância do parser

        AnalisadorSintatico mcel = new AnalisadorSintatico(pw); // instância do tratador de erros
        parser.addErrorListener(mcel);

        try {
            parser.programa(); // tenta fazer a análise do programa
        } catch (Exception e) {
            pw.println(e.getMessage()); // recupera a mensagem de erro causada pela exceção do tratador de erros
            erro = true;
        }
        
        return erro;
    }

    public static boolean analiseSemantica(String args[], PrintWriter pw) throws IOException {

        CharStream cs = CharStreams.fromFileName(args[0]);
        GramaticaLexer lexer = new GramaticaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GramaticaParser parser = new GramaticaParser(tokens);
        ProgramaContext arvore = parser.programa();
        AnalisadorSemantico analisador = new AnalisadorSemantico();
        analisador.visitPrograma(arvore);
        AnalisadorSemanticoLib.errosSemanticos.forEach((s) -> pw.println(s));
        
        return ! AnalisadorSemanticoLib.errosSemanticos.isEmpty();
    }
    
    public static void geracaoCodigoC(String args[], PrintWriter pw) throws IOException {
        GeradorCodigoC gerador = new GeradorCodigoC();
        CharStream cs = CharStreams.fromFileName(args[0]);
        GramaticaLexer lexer = new GramaticaLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GramaticaParser parser = new GramaticaParser(tokens);
        ProgramaContext arvore = parser.programa();
        gerador.visitPrograma(arvore);
        pw.print(gerador.saida.toString());
    }

    public static void main(String args[]) {

        try (PrintWriter pw = new PrintWriter(new File(args[1]))) { // instância do escritor do arquivo de log

            boolean erroLexico = false, erroSintatico=false, erroSemantico=false;
            
            erroLexico = analiseLexica(args, pw);

            if (!erroLexico) { // se não houve erro léxico, é feita a análise sintática
                erroSintatico = analiseSintatica(args, pw);
            }
            
            if(!erroLexico && !erroSintatico){ // se não houve erro léxico ou sintático, é feita a análise sintática
                erroSemantico = analiseSemantica(args, pw);
            }
            
            if (erroLexico || erroSintatico || erroSemantico){ //se houve algum erro, mensagem final do log de erros
                pw.println("Fim da compilacao");
            }else{ //senão é gerado código em C
                geracaoCodigoC(args, pw);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
