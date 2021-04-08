import sys
from antlr4 import *
from GramaticaLexer import GramaticaLexer
from GramaticaParser import GramaticaParser
from GramaticaListener import GramaticaListener

def main(argv):
    istream = FileStream(argv[1])
    lexer = GramaticaLexer(istream)
    stream = CommonTokenStream(lexer)
    parser = GramaticaParser(stream)
    tree = parser.casamento()
    print(tree.toStringTree(recog=parser))

    walker = ParseTreeWalker()
    walker.walk(GramaticaListener(), tree)
    print()

if __name__ == '__main__':
    main(sys.argv)