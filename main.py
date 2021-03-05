from antlr4 import *
from GrammarLexer import GrammarLexer
from GrammarListener import GrammarListener
from GrammarParser import GrammarParser
import sys


class GrammarPrintListener(GrammarListener):
    def enterPalavra_chave(self, ctx):
        print(f"<{ctx.getText()},{ctx.getText()}>")
    def enterIdent(self, ctx):
        print(f"<{ctx.getText()},IDENT>")


def main(argv):
    input_stream = FileStream(argv[1])
    lexer = GrammarLexer(input_stream)
    stream = CommonTokenStream(lexer)
    parser = GrammarParser(stream)
    tree = parser.prog()
    printer = GrammarPrintListener()
    walker = ParseTreeWalker()
    walker.walk(printer, tree)


if __name__ == '__main__':
    main(sys.argv)
