package br.ufscar.dc.compiladores.analisador.semantico;

import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoETS;
import br.ufscar.dc.compiladores.analisador.semantico.TabelaSimbolos.TipoLA;

public class GeradorCodigoC extends GramaticaBaseVisitor<Void> {
    
    StringBuilder saida;
    Escopos escopos = new Escopos();
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
        saida.append("\n}");
        return null;
    }
    
    @Override
    public Void visitDeclaracoes(GramaticaParser.DeclaracoesContext ctx){
        ctx.decl_local_global().forEach(decl -> visitDecl_local_global(decl));
        return null;
    }
    
    @Override
    public Void visitDecl_local_global(GramaticaParser.Decl_local_globalContext ctx){
        if(ctx.declaracao_local() != null){
            visitDeclaracao_local(ctx.declaracao_local());
        }else{
            visitDeclaracao_global(ctx.declaracao_global());
        }
        return null;
    }
    
    @Override
    public Void visitDeclaracao_local(GramaticaParser.Declaracao_localContext ctx){
        if(ctx.variavel() != null){
            visitVariavel(ctx.variavel());
        }
        return null;
    }
    
    @Override
    public Void visitVariavel(GramaticaParser.VariavelContext ctx){
        
        TabelaSimbolos escopoAtual = escopos.obterEscopoAtual();
        
        String nomeVar;
        String tipoVar = ctx.tipo().getText();
        TipoLA tipoVarLa = TipoLA.INVALIDO;
        
        for(GramaticaParser.IdentificadorContext ictx : ctx.identificador()){
            nomeVar = ictx.getText();
            tipoVarLa = escopoAtual.getTipo(tipoVar);
            tipoVar = geradorLib.getTipo(tipoVarLa);
            
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
    public Void visitCorpo(GramaticaParser.CorpoContext ctx){
        
        if(ctx.declaracao_local() != null){
            ctx.declaracao_local().forEach(decl -> visitDeclaracao_local(decl));
        }
        if(ctx.cmd() != null){
            ctx.cmd().forEach(cmd -> visitCmd(cmd));
        }
        
        return null;
    }
    
    @Override
    public Void visitCmd(GramaticaParser.CmdContext ctx){
        
        if(ctx.cmdLeia() != null){
            visitCmdLeia(ctx.cmdLeia());
        }else if(ctx.cmdEscreva() != null){
            visitCmdEscreva(ctx.cmdEscreva());
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
            saida.append("scanf(%\"" + codigoTipo + "\",&" + nomeVar + ");");
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdEscreva(GramaticaParser.CmdEscrevaContext ctx){
        ctx.expressao().forEach(exp -> super.visitExpressao(exp));
        return null;
    }
    
    @Override
    public Void visitParcela_nao_unario(GramaticaParser.Parcela_nao_unarioContext ctx){
        
        if(ctx.identificador() != null){
            saida.append(ctx.identificador().getText());
        }
        
        return null;
    }
    
}
