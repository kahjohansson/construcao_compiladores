# Generated from Gramatica.g4 by ANTLR 4.9.2
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .GramaticaParser import GramaticaParser
else:
    from GramaticaParser import GramaticaParser

# This class defines a complete generic visitor for a parse tree produced by GramaticaParser.

class GramaticaVisitor(ParseTreeVisitor):

    # Visit a parse tree produced by GramaticaParser#casamento.
    def visitCasamento(self, ctx:GramaticaParser.CasamentoContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#nome_noiva.
    def visitNome_noiva(self, ctx:GramaticaParser.Nome_noivaContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#nome_noivo.
    def visitNome_noivo(self, ctx:GramaticaParser.Nome_noivoContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#listas_convidados.
    def visitListas_convidados(self, ctx:GramaticaParser.Listas_convidadosContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#lista_convidados.
    def visitLista_convidados(self, ctx:GramaticaParser.Lista_convidadosContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#nome.
    def visitNome(self, ctx:GramaticaParser.NomeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#lado.
    def visitLado(self, ctx:GramaticaParser.LadoContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#listas_padrinhos.
    def visitListas_padrinhos(self, ctx:GramaticaParser.Listas_padrinhosContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#lista_madrinhas.
    def visitLista_madrinhas(self, ctx:GramaticaParser.Lista_madrinhasContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#lista_padrinhos.
    def visitLista_padrinhos(self, ctx:GramaticaParser.Lista_padrinhosContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#servicos.
    def visitServicos(self, ctx:GramaticaParser.ServicosContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#servico.
    def visitServico(self, ctx:GramaticaParser.ServicoContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#modalidade.
    def visitModalidade(self, ctx:GramaticaParser.ModalidadeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#nome_servico.
    def visitNome_servico(self, ctx:GramaticaParser.Nome_servicoContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#valor_disponivel.
    def visitValor_disponivel(self, ctx:GramaticaParser.Valor_disponivelContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by GramaticaParser#valor.
    def visitValor(self, ctx:GramaticaParser.ValorContext):
        return self.visitChildren(ctx)



del GramaticaParser