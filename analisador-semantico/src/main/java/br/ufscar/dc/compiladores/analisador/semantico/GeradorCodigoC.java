package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

public class GeradorCodigoC extends GramaticaBaseVisitor<Void> {
    
    StringBuilder saida;
    Escopos escopos = new Escopos();
    VerificadorTipo verificadorTipo = new VerificadorTipo(escopos);
    GeradorCodigoCLib geradorLib = new GeradorCodigoCLib(); 
    
    public GeradorCodigoC(){
        saida = new StringBuilder();
    }
    
    @Override
    public Void visitPrograma(GramaticaParser.ProgramaContext ctx){
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        saida.append("\n");
        visitDeclaracoes(ctx.declaracoes());
        saida.append("\n");
        saida.append("int main() {\n");
        visitCorpo(ctx.corpo());
        saida.append("return 0;\n");
        saida.append("}");
        return null;
    }
    
    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx){
        
        if(ctx.valor_constante() != null){
            saida.append("#define " + ctx.IDENT().getText() + " "+ ctx.valor_constante().getText() + "\n");
        }
        
        super.visitDeclaracao_local(ctx);
        
        return null;
    }
    
    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx){
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        String nomeVar;
        String tipoVar = ctx.tipo().getText();
        TipoLA tipoVarLa = TipoLA.INVALIDO;
        
        tipoVarLa = escopoAtual.getTipo(tipoVar);
        tipoVar = geradorLib.getTipo(tipoVarLa);
        
        for(GramaticaParser.IdentificadorContext ictx : ctx.identificador()){
            nomeVar = ictx.getText();
            
            escopoAtual.adicionar(nomeVar, null, tipoVarLa, TipoETS.VARIAVEL, null, false);
            
            if(tipoVarLa == TipoLA.LITERAL){
                saida.append(tipoVar + " " + nomeVar + "[80];\n");
            }else{
                saida.append(tipoVar + " " + nomeVar + ";\n");
            }
            
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdLeia(GramaticaParser.CmdLeiaContext ctx){
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        String nomeVar;
        TipoLA tipoLa = TipoLA.INVALIDO;
        String codigoTipo;

        for(GramaticaParser.IdentificadorContext ictx : ctx.identificador()){
            nomeVar = ictx.getText();
            tipoLa = escopoAtual.verificar(nomeVar);
            codigoTipo = geradorLib.getCodigoTipo(tipoLa);
            if (tipoLa == TipoLA.LITERAL){
                saida.append("gets(" + nomeVar + ");\n");
            }else{
            saida.append("scanf(\"%" + codigoTipo + "\",&" + nomeVar + ");\n");
            }
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdEscreva(GramaticaParser.CmdEscrevaContext ctx){
        
        TipoLA tipoLaExp = verificadorTipo.verificaTipo(ctx.expressao(0));
        String codTipoExp;
        String nomeVariaveis = "";
         
        saida.append("printf(\"");
        
        if(ctx.expLista.isEmpty()){ //só possui uma expressão como argumento
            
            if(tipoLaExp == TipoLA.LITERAL){
                saida.append(ctx.exp1.getText().replace("\"", "") + "\");\n");
            }else{
            codTipoExp = geradorLib.getCodigoTipo(tipoLaExp);
            saida.append("%" + codTipoExp + "\", " + ctx.exp1.getText() + ");\n");
            }
        
        }else{
            TipoLA tipoSegundaExpressao = verificadorTipo.verificaTipo(ctx.expressao(1));
            
            if((tipoLaExp == TipoLA.INTEIRO || tipoLaExp == TipoLA.REAL) && (tipoSegundaExpressao == TipoLA.LITERAL)){
                codTipoExp = geradorLib.getCodigoTipo(tipoLaExp);
                saida.append("%" + codTipoExp + "\", " + ctx.exp1.getText() + ");\n");
                saida.append("printf(" + ctx.expressao(1).getText() + ");\n");
            }else{
                saida.append(ctx.exp1.getText().replace("\"", ""));
                for(GramaticaParser.ExpressaoContext ectx : ctx.expLista){
                    tipoLaExp = verificadorTipo.verificaTipo(ectx);
                    codTipoExp = geradorLib.getCodigoTipo(tipoLaExp);
                    nomeVariaveis += " , " + ectx.getText();
                    saida.append(" %" + codTipoExp + " \"");
                }
                saida.append(nomeVariaveis + ");\n");
            }
            
        }
        
        return null;
    }
    
    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx){
        
        if(ctx.identificador() != null){
            saida.append(ctx.identificador().getText());
        }
        
        return null;
    }
    
    @Override
    public Void visitParcela_unario(GramaticaParser.Parcela_unarioContext ctx){
        
        if(ctx.identificador() != null){
            saida.append(ctx.identificador().getText());
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdAtribuicao(GramaticaParser.CmdAtribuicaoContext ctx){
        
        saida.append("chegou no atribuicao\n");
        
        //saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + "\n");
        
        return null;
    }
    
    @Override
    public Void visitCmdSe(GramaticaParser.CmdSeContext ctx){
        
        //if
        //normalizacoes
        String textoExpressao;
        textoExpressao = ctx.expressao().getText().replace("e", "&&");
        textoExpressao = textoExpressao.replace("=", "==");
                
        saida.append("if (" + textoExpressao + "){\n");
        
        for(GramaticaParser.CmdContext cctx : ctx.cmdEntao){
            super.visitCmd(cctx);
        }
        saida.append("}\n");
        
        //else
        if(!ctx.cmdSenao.isEmpty()){
            saida.append("else{\n");
            for(GramaticaParser.CmdContext cctx : ctx.cmdSenao){
                super.visitCmd(cctx);
            }
            saida.append("}\n");
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdCaso(GramaticaParser.CmdCasoContext ctx){
        
        String limitanteEsquerda, limitanteDireita;
        
        saida.append("switch (" + ctx.exp_aritmetica().getText() + "){\n");
        
        for(GramaticaParser.Item_selecaoContext sctx : ctx.selecao().item_selecao()){
            limitanteEsquerda = sctx.constantes().numero_intervalo(0).getText();
            if (!sctx.constantes().limiteDireita.isEmpty()){
                limitanteDireita = sctx.constantes().numero_intervalo(1).getText();
                
                for (int i = Integer.parseInt(limitanteEsquerda); i <= Integer.parseInt(limitanteDireita); i++) {
                    saida.append("case " + Integer.toString(i) + ":\n");
                }
            }else{
                saida.append("case " + limitanteEsquerda + ":\n");
            }
            for(GramaticaParser.CmdContext cctx: sctx.cmd()){
                visitCmd(cctx);
            }
            saida.append("break;\n");
        }
        
        saida.append("default:\n");
        for (GramaticaParser.CmdContext cctx : ctx.cmd()) {
            visitCmd(cctx);
        }
        saida.append("}\n");
        
        return null;
    }
    
    @Override
    public Void visitCmdPara(GramaticaParser.CmdParaContext ctx){
        
        String nomeVariavel, limitanteEsquerda, limitanteDireita;
        
        nomeVariavel = ctx.IDENT().getText();
        limitanteEsquerda = ctx.exp_aritmetica(0).getText();
        limitanteDireita = ctx.exp_aritmetica(1).getText();
        
        saida.append("for(" + nomeVariavel + " = " + limitanteEsquerda + "; " + nomeVariavel + " <= " + limitanteDireita +
                "; " + nomeVariavel + "++){\n");
        
        for(GramaticaParser.CmdContext cctx : ctx.cmd()){
            visitCmd(cctx);
        }
        
        saida.append("}\n");
        
        return null;
    }
    
    @Override
    public Void visitDeclaracao_global(GramaticaParser.Declaracao_globalContext ctx){
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        String tipo, nomeVariaveis;
        
        
        saida.append("void " + ctx.IDENT().getText());
        
        if(ctx.parametros() != null){
            for(GramaticaParser.ParametroContext pctx : ctx.parametros().parametro()){
                tipo = geradorLib.getTipo(pctx.tipo_estendido().getText());
                nomeVariaveis = "";
                for(GramaticaParser.IdentificadorContext ictx : pctx.identificador()){
                    nomeVariaveis += ictx.getText();
                }
                saida.append(nomeVariaveis + " : " + tipo);
            }
        }
        
        saida.append("({\n");
        
        for(GramaticaParser.CmdContext cctx : ctx.cmd()){
            visitCmd(cctx);
        }
        
        saida.append("}\n");
        
        return null;
    }
}
