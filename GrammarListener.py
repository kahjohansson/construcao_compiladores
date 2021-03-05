# Generated from Grammar.g4 by ANTLR 4.9.1
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .GrammarParser import GrammarParser
else:
    from GrammarParser import GrammarParser

# This class defines a complete listener for a parse tree produced by GrammarParser.
class GrammarListener(ParseTreeListener):

    # Enter a parse tree produced by GrammarParser#prog.
    def enterProg(self, ctx:GrammarParser.ProgContext):
        pass

    # Exit a parse tree produced by GrammarParser#prog.
    def exitProg(self, ctx:GrammarParser.ProgContext):
        pass


    # Enter a parse tree produced by GrammarParser#palavra_chave.
    def enterPalavra_chave(self, ctx:GrammarParser.Palavra_chaveContext):
        pass

    # Exit a parse tree produced by GrammarParser#palavra_chave.
    def exitPalavra_chave(self, ctx:GrammarParser.Palavra_chaveContext):
        pass


    # Enter a parse tree produced by GrammarParser#ident.
    def enterIdent(self, ctx:GrammarParser.IdentContext):
        pass

    # Exit a parse tree produced by GrammarParser#ident.
    def exitIdent(self, ctx:GrammarParser.IdentContext):
        pass



del GrammarParser