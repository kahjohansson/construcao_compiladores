// Generated from br/ufscar/dc/compiladores/analisador/semantico/Gramatica.g4 by ANTLR 4.9.1
package br.ufscar.dc.compiladores.analisador.semantico;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GramaticaParser}.
 */
public interface GramaticaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(GramaticaParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(GramaticaParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracoes(GramaticaParser.DeclaracoesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declaracoes}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracoes(GramaticaParser.DeclaracoesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void enterDecl_local_global(GramaticaParser.Decl_local_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#decl_local_global}.
	 * @param ctx the parse tree
	 */
	void exitDecl_local_global(GramaticaParser.Decl_local_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_local(GramaticaParser.Declaracao_localContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declaracao_local}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#variavel}.
	 * @param ctx the parse tree
	 */
	void enterVariavel(GramaticaParser.VariavelContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#variavel}.
	 * @param ctx the parse tree
	 */
	void exitVariavel(GramaticaParser.VariavelContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#identificador}.
	 * @param ctx the parse tree
	 */
	void enterIdentificador(GramaticaParser.IdentificadorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#identificador}.
	 * @param ctx the parse tree
	 */
	void exitIdentificador(GramaticaParser.IdentificadorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void enterDimensao(GramaticaParser.DimensaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#dimensao}.
	 * @param ctx the parse tree
	 */
	void exitDimensao(GramaticaParser.DimensaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(GramaticaParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(GramaticaParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico(GramaticaParser.Tipo_basicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tipo_basico}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico(GramaticaParser.Tipo_basicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void enterTipo_basico_ident(GramaticaParser.Tipo_basico_identContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tipo_basico_ident}.
	 * @param ctx the parse tree
	 */
	void exitTipo_basico_ident(GramaticaParser.Tipo_basico_identContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void enterTipo_estendido(GramaticaParser.Tipo_estendidoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#tipo_estendido}.
	 * @param ctx the parse tree
	 */
	void exitTipo_estendido(GramaticaParser.Tipo_estendidoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void enterValor_constante(GramaticaParser.Valor_constanteContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#valor_constante}.
	 * @param ctx the parse tree
	 */
	void exitValor_constante(GramaticaParser.Valor_constanteContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#registro}.
	 * @param ctx the parse tree
	 */
	void enterRegistro(GramaticaParser.RegistroContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#registro}.
	 * @param ctx the parse tree
	 */
	void exitRegistro(GramaticaParser.RegistroContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#declaracao_global}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(GramaticaParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(GramaticaParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(GramaticaParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(GramaticaParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#corpo}.
	 * @param ctx the parse tree
	 */
	void enterCorpo(GramaticaParser.CorpoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#corpo}.
	 * @param ctx the parse tree
	 */
	void exitCorpo(GramaticaParser.CorpoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmd}.
	 * @param ctx the parse tree
	 */
	void enterCmd(GramaticaParser.CmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmd}.
	 * @param ctx the parse tree
	 */
	void exitCmd(GramaticaParser.CmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdLeia}.
	 * @param ctx the parse tree
	 */
	void enterCmdLeia(GramaticaParser.CmdLeiaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdLeia}.
	 * @param ctx the parse tree
	 */
	void exitCmdLeia(GramaticaParser.CmdLeiaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdEscreva}.
	 * @param ctx the parse tree
	 */
	void enterCmdEscreva(GramaticaParser.CmdEscrevaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdEscreva}.
	 * @param ctx the parse tree
	 */
	void exitCmdEscreva(GramaticaParser.CmdEscrevaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdSe}.
	 * @param ctx the parse tree
	 */
	void enterCmdSe(GramaticaParser.CmdSeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdSe}.
	 * @param ctx the parse tree
	 */
	void exitCmdSe(GramaticaParser.CmdSeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdCaso}.
	 * @param ctx the parse tree
	 */
	void enterCmdCaso(GramaticaParser.CmdCasoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdCaso}.
	 * @param ctx the parse tree
	 */
	void exitCmdCaso(GramaticaParser.CmdCasoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdPara}.
	 * @param ctx the parse tree
	 */
	void enterCmdPara(GramaticaParser.CmdParaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdPara}.
	 * @param ctx the parse tree
	 */
	void exitCmdPara(GramaticaParser.CmdParaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdEnquanto}.
	 * @param ctx the parse tree
	 */
	void enterCmdEnquanto(GramaticaParser.CmdEnquantoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdEnquanto}.
	 * @param ctx the parse tree
	 */
	void exitCmdEnquanto(GramaticaParser.CmdEnquantoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdFaca}.
	 * @param ctx the parse tree
	 */
	void enterCmdFaca(GramaticaParser.CmdFacaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdFaca}.
	 * @param ctx the parse tree
	 */
	void exitCmdFaca(GramaticaParser.CmdFacaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void enterCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdAtribuicao}.
	 * @param ctx the parse tree
	 */
	void exitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdChamada}.
	 * @param ctx the parse tree
	 */
	void enterCmdChamada(GramaticaParser.CmdChamadaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdChamada}.
	 * @param ctx the parse tree
	 */
	void exitCmdChamada(GramaticaParser.CmdChamadaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#cmdRetorne}.
	 * @param ctx the parse tree
	 */
	void enterCmdRetorne(GramaticaParser.CmdRetorneContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#cmdRetorne}.
	 * @param ctx the parse tree
	 */
	void exitCmdRetorne(GramaticaParser.CmdRetorneContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#selecao}.
	 * @param ctx the parse tree
	 */
	void enterSelecao(GramaticaParser.SelecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#selecao}.
	 * @param ctx the parse tree
	 */
	void exitSelecao(GramaticaParser.SelecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#item_selecao}.
	 * @param ctx the parse tree
	 */
	void enterItem_selecao(GramaticaParser.Item_selecaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#item_selecao}.
	 * @param ctx the parse tree
	 */
	void exitItem_selecao(GramaticaParser.Item_selecaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#constantes}.
	 * @param ctx the parse tree
	 */
	void enterConstantes(GramaticaParser.ConstantesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#constantes}.
	 * @param ctx the parse tree
	 */
	void exitConstantes(GramaticaParser.ConstantesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void enterNumero_intervalo(GramaticaParser.Numero_intervaloContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#numero_intervalo}.
	 * @param ctx the parse tree
	 */
	void exitNumero_intervalo(GramaticaParser.Numero_intervaloContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void enterOp_unario(GramaticaParser.Op_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op_unario}.
	 * @param ctx the parse tree
	 */
	void exitOp_unario(GramaticaParser.Op_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void enterExp_aritmetica(GramaticaParser.Exp_aritmeticaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#exp_aritmetica}.
	 * @param ctx the parse tree
	 */
	void exitExp_aritmetica(GramaticaParser.Exp_aritmeticaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#termo}.
	 * @param ctx the parse tree
	 */
	void enterTermo(GramaticaParser.TermoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#termo}.
	 * @param ctx the parse tree
	 */
	void exitTermo(GramaticaParser.TermoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#fator}.
	 * @param ctx the parse tree
	 */
	void enterFator(GramaticaParser.FatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#fator}.
	 * @param ctx the parse tree
	 */
	void exitFator(GramaticaParser.FatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op1}.
	 * @param ctx the parse tree
	 */
	void enterOp1(GramaticaParser.Op1Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op1}.
	 * @param ctx the parse tree
	 */
	void exitOp1(GramaticaParser.Op1Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op2}.
	 * @param ctx the parse tree
	 */
	void enterOp2(GramaticaParser.Op2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op2}.
	 * @param ctx the parse tree
	 */
	void exitOp2(GramaticaParser.Op2Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op3}.
	 * @param ctx the parse tree
	 */
	void enterOp3(GramaticaParser.Op3Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op3}.
	 * @param ctx the parse tree
	 */
	void exitOp3(GramaticaParser.Op3Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parcela}.
	 * @param ctx the parse tree
	 */
	void enterParcela(GramaticaParser.ParcelaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parcela}.
	 * @param ctx the parse tree
	 */
	void exitParcela(GramaticaParser.ParcelaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_unario(GramaticaParser.Parcela_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parcela_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_unario(GramaticaParser.Parcela_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void enterParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parcela_nao_unario}.
	 * @param ctx the parse tree
	 */
	void exitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void enterExp_relacional(GramaticaParser.Exp_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#exp_relacional}.
	 * @param ctx the parse tree
	 */
	void exitExp_relacional(GramaticaParser.Exp_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void enterOp_relacional(GramaticaParser.Op_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op_relacional}.
	 * @param ctx the parse tree
	 */
	void exitOp_relacional(GramaticaParser.Op_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#expressao}.
	 * @param ctx the parse tree
	 */
	void enterExpressao(GramaticaParser.ExpressaoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#expressao}.
	 * @param ctx the parse tree
	 */
	void exitExpressao(GramaticaParser.ExpressaoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void enterTermo_logico(GramaticaParser.Termo_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#termo_logico}.
	 * @param ctx the parse tree
	 */
	void exitTermo_logico(GramaticaParser.Termo_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void enterFator_logico(GramaticaParser.Fator_logicoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#fator_logico}.
	 * @param ctx the parse tree
	 */
	void exitFator_logico(GramaticaParser.Fator_logicoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void enterParcela_logica(GramaticaParser.Parcela_logicaContext ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#parcela_logica}.
	 * @param ctx the parse tree
	 */
	void exitParcela_logica(GramaticaParser.Parcela_logicaContext ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op_logico_1}.
	 * @param ctx the parse tree
	 */
	void enterOp_logico_1(GramaticaParser.Op_logico_1Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op_logico_1}.
	 * @param ctx the parse tree
	 */
	void exitOp_logico_1(GramaticaParser.Op_logico_1Context ctx);
	/**
	 * Enter a parse tree produced by {@link GramaticaParser#op_logico_2}.
	 * @param ctx the parse tree
	 */
	void enterOp_logico_2(GramaticaParser.Op_logico_2Context ctx);
	/**
	 * Exit a parse tree produced by {@link GramaticaParser#op_logico_2}.
	 * @param ctx the parse tree
	 */
	void exitOp_logico_2(GramaticaParser.Op_logico_2Context ctx);
}