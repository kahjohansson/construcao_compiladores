package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

public class AnalisadorSemanticoLib {

    public static List<String> errosSemanticos = new ArrayList<>();

    //adiciona erro semantico na lista
    public static void adicionarErroSemantico(String mensagem) {
        errosSemanticos.add(mensagem);
    }

    //verifica tipo de entrada da tabela
    public static TabelaSimbolos.TipoLA verificarTipo(TabelaSimbolos tabela, String nomeVar) {
        return tabela.verificar(nomeVar);
    }

    //retorna nome de registro se for o caso, caso contrário retorna null
    public static String getNomeRegistro(String nomeVariavelCompleto) {
        if (nomeVariavelCompleto.contains(".")) {
            return nomeVariavelCompleto.substring(0, nomeVariavelCompleto.indexOf("."));
        }
        return null;
    }

    //retorna o nome de variável
    public static String getNomeVariavel(String nomeVariavelCompleto) {
        if (nomeVariavelCompleto.contains(".")) {
            return nomeVariavelCompleto.substring(nomeVariavelCompleto.indexOf(".") + 1);
        }
        return nomeVariavelCompleto;
    }

    //retorna nome de registro se for o caso, caso contrário retorna null
    public static String getNomeRegistro(GramaticaParser.IdentificadorContext ctx) {
        if(ctx.identLista.size() > 0){
            return ctx.ident1.getText();
        }
        
        return null;
    }

    //retorna o nome de variável
    public static String getNomeVariavel(GramaticaParser.IdentificadorContext ctx) {
        
        if(ctx.identLista.size() > 0){
            return ctx.identLista.get(0).getText();
        }
        
        return ctx.ident1.getText();
    }

    // retorna tipo TipoLA dada uma string
    public TipoLA getTipoLa(String tipo) {

        TipoLA tipoLa = TipoLA.INVALIDO;

        switch (tipo) {
            case "literal":
                tipoLa = TipoLA.LITERAL;
                break;
            case "inteiro":
                tipoLa = TipoLA.INTEIRO;
                break;
            case "real":
                tipoLa = TipoLA.REAL;
                break;
            case "logico":
                tipoLa = TipoLA.LOGICO;
                break;
            default:
                break;
        }

        return tipoLa;
    }
}
