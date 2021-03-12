package br.ufscar.dc.compiladores.analisador.sintatico;

import java.io.PrintWriter;
import java.util.BitSet;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;


/* classe de tratamento de erros sintáticos */
public class MyCustomErrorListener implements ANTLRErrorListener {
    
    // printer para preencher log com erros sintáticos
    PrintWriter pw;
    public MyCustomErrorListener(PrintWriter pw){
        this.pw = pw;
    }
    
    @Override
    public void	reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        
    }
    
    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
       
    }

    /* método para tratar os erros sintáticos */
    @Override
    public void	syntaxError(Recognizer<?,?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        
        Token t = (Token) offendingSymbol;
        String token = t.getText();
        
        // caso especial para erro de EOF para adequar à saída esperada
        if (token.equals("<EOF>")){
            token = "EOF";
            pw.println("Linha "+ line +": erro sintatico proximo a "+token);
        }else{ // exceção gerada para erros sintáticos exceto para erro de EOF
            throw new RuntimeException("Linha "+line+": erro sintatico proximo a "+token);
        }
    }
}
