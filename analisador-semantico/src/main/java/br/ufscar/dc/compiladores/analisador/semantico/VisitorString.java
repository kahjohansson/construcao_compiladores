package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import org.antlr.v4.runtime.Token;

public class VisitorString extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);

    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.tipo_basico() != null) {
            if (!escopoAtual.existe(ctx.IDENT().getText())) {
                TipoLA tipoLa = escopoAtual.getTipo(ctx.tipo_basico().getText());
                escopoAtual.adicionar(ctx.IDENT().getText(), null, tipoLa, TipoETS.CONSTANTE, null, false);
            }
        }else if(ctx.tipo() != null){
            if(! escopoAtual.existeTipoEstendido(ctx.IDENT().getText())){
                escopoAtual.adicionaTipoEstendido(ctx.IDENT().getText());
            }
        }
        
        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        //verificacao de tipo
        TipoLA variavelTipo = TipoLA.INVALIDO;
        TabelaSimbolos escopoRegistro = null;
        String tipoEstendido = null;
        boolean ponteiro = false;
        
        if (ctx.tipo().registro() != null) { // tipo registro
            escopos.criarNovoEscopo();
            visitRegistro(ctx.tipo().registro());
            escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            variavelTipo = TipoLA.REGISTRO;
            
            
        }else{ //tipo estendido
            ponteiro = escopoAtual.ponteiro(ctx.tipo().tipo_estendido().getText());
            
            if(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null){ // tipo basico
                variavelTipo = escopoAtual.getTipo(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText());
            }else{ // tipo IDENT (estendido)
                variavelTipo = TipoLA.TIPOESTENDIDO;
                String tipoIdent = ctx.tipo().tipo_estendido().tipo_basico_ident().IDENT().getText();
                boolean tipoEstendidoExiste = escopoAtual.existeTipoEstendido(tipoIdent);
                if (! tipoEstendidoExiste){
                    variavelTipo = escopoAtual.getTipo(tipoIdent);
                }
                if (variavelTipo == TipoLA.INVALIDO) {
                        String mensagem = String.format("Linha %d: tipo %s nao declarado",
                                ctx.getStart().getLine(),
                                tipoIdent);
                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            }
        }
        
        //verificacao de nome de variavel e gravação da variável na tabela
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                escopoAtual.adicionar(ictx.getText(), tipoEstendido, variavelTipo, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
            } else {
                String mensagem = String.format("Linha %d: identificador %s ja declarado anteriormente",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitVariavel(ctx);

    }
    
    //precisa dessa função?
//    @Override
//    public Void visitRegistro(GramaticaParser.RegistroContext ctx) {
//
//        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
//        
//        for(GramaticaParser.VariavelContext vctx: ctx.variavel()){
//            visitVariavel(vctx);
//        }
//
//
//        return super.visitRegistro(ctx);
//    }
    
    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {
            if (!escopoAtual.existe(ictx.getText())) {
                String mensagem = String.format("Linha %d: identificador %s não declarado",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.identificador() != null) {
            if (!escopoAtual.existe(ctx.identificador().getText())) { //TODO mudar para pegar separadamente parte 0 e 1
                String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ctx.identificador().getStart().getLine(),
                        ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitParcela_nao_unario(ctx);
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        if (ctx.identificador() != null) {
            if (!escopoAtual.existe(ctx.identificador().getText())) { //TODO mudar para pegar separadamente parte 0 e 1
                String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ctx.identificador().getStart().getLine(),
                        ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitParcela_unario(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        TabelaSimbolos subTabela = null;

        TipoLA tipoExpressao = verificadorTipo.verificaTipo(ctx.expressao());
        TipoLA tipoIdentificador = verificadorTipo.verificaTipo(ctx.identificador());
        
        String nomeUltimoIdentificador = ctx.identificador().getText();
        String nomeIdentificador = ctx.identificador().getText();
        if(ctx.simbolo != null){
            nomeIdentificador = ctx.simbolo.getText() + nomeIdentificador;
        }

        if (tipoExpressao == TipoLA.INVALIDO) {
            String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                    ctx.getStart().getLine(), nomeIdentificador);
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        } else if (tipoIdentificador != tipoExpressao) { //TODO modificar para novo tipo registro
            
            if(ctx.identificador().identLista != null){ // verifica se é registro, se for, modifica o nome da variavel a ser analisada e o escopo
                escopoAtual = escopoAtual.getSubTabela(ctx.identificador().ident1.getText());
                nomeUltimoIdentificador = ctx.identificador().identLista.get(0).getText();
            }
            
//            verificação se identificador nesse contexto é ponteiro ou não
            boolean identificadorPonteiro = escopoAtual.ponteiro(ctx.getText().startsWith("^"), escopoAtual.verificarPonteiro(nomeUltimoIdentificador));
//            
            if (!(escopoAtual.verificar(nomeUltimoIdentificador) == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO)
                    || !(identificadorPonteiro && ctx.expressao().getText().startsWith("&"))) {
                String mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                        ctx.getStart().getLine(), ctx.identificador().getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdAtribuicao(ctx);
    }

}
