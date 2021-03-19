package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AnalisadorSemanticoLib {
    
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(String mensagem) {
        errosSemanticos.add(mensagem);
    }
    
    public static TabelaSimbolos.TipoLA verificarTipo(TabelaSimbolos tabela, String nomeVar) {
        return tabela.verificar(nomeVar);
    }
    
}
