package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import java.util.ArrayList;
import java.util.List;


public class AnalisadorSemantico extends GramaticaBaseVisitor<Void> {

    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);

    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        //declaração de constante
        if (ctx.tipo_basico() != null) {
            if (!escopoAtual.existe(ctx.IDENT().getText())) {
                TipoLA tipoLa = escopoAtual.getTipo(ctx.tipo_basico().getText());
                escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, TipoETS.CONSTANTE, null, false);
            }
        //declaração de tipo
        } else if (ctx.tipo() != null) {
            if (!escopoAtual.existeTipoEstendido(ctx.IDENT().getText())) {

                escopos.criarNovoEscopo();
                visitRegistro(ctx.tipo().registro());
                TabelaSimbolos escopoRegistro = escopos.obterEscopoAtual();
                escopos.abandonarEscopo();
                escopoAtual.adicionar(ctx.IDENT().getText(), TipoLA.REGISTRO, TipoETS.TIPO, escopoRegistro, false);
            }
        }

        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        TabelaSimbolos escopoRegistroParam = null;
        List<TipoLA> tiposParametros = new ArrayList<>();
        TipoLA tipoLa;
        TipoETS tipoEts;
        String tipoDeclaracao;
        boolean tipoEstendidoExiste = false;

        //escopo dos parâmetros e declarações locais de funções e procedimentos
        escopos.criarNovoEscopo();
        TabelaSimbolos subEscopo = escopos.obterEscopoAtual();

        //determinação se é função ou procedimento
        if (ctx.funcao != null) {
            tipoDeclaracao = "funcao";
        } else {
            tipoDeclaracao = "procedimento";
        }

        if (!escopoAtual.existe(ctx.IDENT().getText())) {

            //adiciona parametros da funcao
            if (ctx.parametros() != null) {

                for (GramaticaParser.ParametroContext pctx : ctx.parametros().parametro()) {
                    //verifica tipo dos identificadores
                    if (pctx.tipo_estendido().tipo_basico_ident().tipo_basico() != null) { //tipo basico
                        tipoLa = escopoAtual.getTipo(pctx.tipo_estendido().tipo_basico_ident().tipo_basico().getText());
                        escopoRegistroParam = null;
                    } else { //tipo estendido
                        tipoLa = TipoLA.TIPOESTENDIDO;
                        tipoEstendidoExiste = escopoAtual.existeTipoEstendido(pctx.tipo_estendido().getText());
                        if (tipoEstendidoExiste) {
                            escopoRegistroParam = escopoAtual.getSubTabela(pctx.tipo_estendido().getText());
                        }
                    }
                    //adiciona identificadores na tabela de simbolos
                    for (GramaticaParser.IdentificadorContext ictx : pctx.identificador()) {

                        subEscopo.adicionar(ictx.getText(), tipoLa, TipoETS.VARIAVEL, escopoRegistroParam, false);
                        tiposParametros.add(tipoLa);
                    }
                }
            }

            //adiciona funcao à tabela de simbolos
            if (tipoDeclaracao.equals("funcao")) {
                //recupera tipo da funcao
                if (ctx.tipo_estendido().tipo_basico_ident().tipo_basico() != null) { //tipo basico
                    tipoLa = escopoAtual.getTipo(ctx.tipo_estendido().tipo_basico_ident().tipo_basico().getText());
                } else { //tipo estendido
                    tipoLa = TipoLA.TIPOESTENDIDO;
                }
                tipoEts = TipoETS.FUNCAO;
            } else {
                tipoLa = TipoLA.SEMTIPO;
                tipoEts = TipoETS.PROCEDIMENTO;
            }

            //cria função/procedimento
            if (tiposParametros.isEmpty()) { //se possuir parametros
                escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, tipoEts, null, false);
            } else { //se não possuir parâmetros
                escopoAtual.adicionar(ctx.IDENT().getText(), tipoLa, tipoEts, subEscopo, tiposParametros, false);
            }

            //realiza chamada de declaracao local
            ctx.declaracao_local().forEach(lctx -> {
                visitDeclaracao_local(lctx);
            });

            //realiza chamada de comandos
            for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
                //tratamento de erro de retorno em procedimento
                if (tipoDeclaracao.equals("procedimento") && cctx.cmdRetorne() != null) {
                    String mensagem = String.format("Linha %d: comando retorne nao permitido nesse escopo", cctx.getStart().getLine());
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                } else {
                    super.visitCmd(cctx);
                }
            }

            escopos.abandonarEscopo();

        }
        return null;
    }

    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx) {

        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();

        //verificacao de tipo
        TipoLA variavelTipo = TipoLA.INVALIDO;
        TabelaSimbolos escopoRegistro = null;
        boolean ponteiro = false;
        boolean tipoEstendidoExiste = false;

        // tipo registro
        if (ctx.tipo().registro() != null) {
            escopos.criarNovoEscopo();
            visitRegistro(ctx.tipo().registro());
            escopoRegistro = escopos.obterEscopoAtual();
            escopos.abandonarEscopo();
            variavelTipo = TipoLA.REGISTRO;

        //tipo estendido
        } else {

            // tipo basico
            if (ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico() != null) {
                variavelTipo = escopoAtual.getTipo(ctx.tipo().tipo_estendido().tipo_basico_ident().tipo_basico().getText());

            // tipo IDENT (estendido)
            } else {
                variavelTipo = TipoLA.TIPOESTENDIDO;
                String tipoIdent = ctx.tipo().tipo_estendido().tipo_basico_ident().IDENT().getText();
                tipoEstendidoExiste = escopoAtual.existeTipoEstendido(tipoIdent);
                if (tipoEstendidoExiste) {
                    escopoRegistro = escopoAtual.getSubTabela(tipoIdent);
                } else { //mensagem de erro de tipo estendido não declarado
                    String mensagem = String.format("Linha %d: tipo %s nao declarado",
                            ctx.getStart().getLine(),
                            tipoIdent);
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            }
        }

        boolean jaDeclarado;

        //verificacao de nome de variavel e gravação da variável na tabela
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {

            jaDeclarado = false;

            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if (ts.existe(ictx.getText())) { //mensagem de erro para identificador já declarado anteriormente
                    jaDeclarado = true;
                    String mensagem = String.format("Linha %d: identificador %s ja declarado anteriormente",
                            ictx.getStart().getLine(),
                            ictx.getText());
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }
            }
            //gravação da variável no escopo
            if (!jaDeclarado) {
                escopoAtual.adicionar(AnalisadorSemanticoLib.getNomeVariavel(ictx), variavelTipo, TipoETS.VARIAVEL, escopoRegistro, ponteiro);
            }
        }

        return super.visitVariavel(ctx);

    }

    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx) {

        //mensagem de erro se identificador já tiver sido declarado anteriormente
        for (GramaticaParser.IdentificadorContext ictx : ctx.identificador()) {

            boolean erro = true;

            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {
                if (ts.existe(AnalisadorSemanticoLib.getNomeVariavel(ictx))) {
                    erro = false;
                }
            }

            if (erro) {
                String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ictx.getStart().getLine(),
                        ictx.getText());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCmdLeia(ctx);
    }

    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx) {

        TabelaSimbolos subtabela;
        boolean erro = true;

        //parcela unaria é identificador
        if (ctx.identificador() != null) {

            String nomeRegistro = AnalisadorSemanticoLib.getNomeRegistro(ctx.identificador());
            String nomeVariavel = AnalisadorSemanticoLib.getNomeVariavel(ctx.identificador());

            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {

                //não é registro
                if (nomeRegistro == null) {
                    if (ts.existe(nomeVariavel)) {
                        erro = false;
                    }
                } //é registro
                else {
                    if (ts.existe(nomeRegistro)) {
                        subtabela = ts.getSubTabela(nomeRegistro);
                        if (subtabela.existe(nomeVariavel)) {
                            erro = false;
                        }
                    }
                }
            }

            //dispara erro de identificador não declarado
            if (erro) {
                if (nomeRegistro != null) {
                    String mensagem = String.format("Linha %d: identificador %s nao declarado",
                            ctx.identificador().getStart().getLine(),
                            nomeRegistro + "." + nomeVariavel);
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }else{
                    String mensagem = String.format("Linha %d: identificador %s nao declarado",
                            ctx.identificador().getStart().getLine(),
                            nomeVariavel);
                    AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                }

            }

        //chamada de função ou procedimento
        } else if (ctx.ident != null) {
            
            List<TipoLA> tiposParametros;

            for (TabelaSimbolos ts : escopos.percorrerEscoposAninhados()) {

                if (ts.existe(ctx.IDENT().getText())) {
                    tiposParametros = ts.getTiposParametros(ctx.IDENT().getText());

                    //erro de quantidade de parametros diferente
                    if (tiposParametros.size() != ctx.expressao().size()) {
                        String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
                                ctx.getStart().getLine(), ctx.IDENT().getText());
                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);

                    } else {

                        for (TabelaSimbolos ts2 : escopos.percorrerEscoposAninhados()) {
                            for (int i = 0; i < tiposParametros.size(); i++) {
                                if (ts2.existe(ctx.expressao(i).getText())) {
                                    //erro de tipo de parâmetro definido diferente do tipo de parâmetro passado
                                    if (tiposParametros.get(i) != verificadorTipo.verificaTipo(ctx.expressao(i))) {
                                        String mensagem = String.format("Linha %d: incompatibilidade de parametros na chamada de %s",
                                                ctx.getStart().getLine(), ctx.IDENT().getText());
                                        AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return super.visitParcela_unario(ctx);
    }

    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx) {

        //verificação de tipos de identificador e expressão
        TipoLA tipoIdentificador = verificadorTipo.verificaTipo(ctx.identificador());
        TipoLA tipoExpressao = verificadorTipo.verificaTipo(ctx.expressao());

        boolean condicao;
        boolean identificadorInvalido = tipoIdentificador == TipoLA.INVALIDO;
        
        //erro de identificador invalido
        if(identificadorInvalido){
            String mensagem = String.format("Linha %d: identificador %s nao declarado",
                        ctx.getStart().getLine(), ctx.identificador().getText());
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        }
        
        condicao = (tipoExpressao == TipoLA.INVALIDO) //expressao invalidos
                || (tipoIdentificador != tipoExpressao //tipos diferentes
                && !(tipoIdentificador == TipoLA.REAL && tipoExpressao == TipoLA.INTEIRO) //identificador real e expressao inteira
                );

        if (!identificadorInvalido && condicao) { //dispara erros listados em 'condicao'
            String mensagem;
            if (ctx.simbolo == null) {
                mensagem = String.format("Linha %d: atribuicao nao compativel para %s",
                        ctx.getStart().getLine(), ctx.identificador().getText());
            } else {
                mensagem = String.format("Linha %d: atribuicao nao compativel para ^%s",
                        ctx.getStart().getLine(), ctx.identificador().getText());
            }
            AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
        }

        return super.visitCmdAtribuicao(ctx);
    }

    @Override
    public Void visitCorpo(GramaticaParser.CorpoContext ctx) {

        //erro de retorno não permitido em corpo do algoritmo
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            if (cctx.cmdRetorne() != null) {
                String mensagem = String.format("Linha %d: comando retorne nao permitido nesse escopo",
                        cctx.getStart().getLine());
                AnalisadorSemanticoLib.adicionarErroSemantico(mensagem);
            }
        }

        return super.visitCorpo(ctx);
    }

}
