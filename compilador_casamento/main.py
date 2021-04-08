import sys
from antlr4 import *
from GramaticaLexer import GramaticaLexer
from GramaticaParser import GramaticaParser
from GramaticaListener import GramaticaListener

from AnalisadorSemantico import AnalisadorSemantico
from AnalisadorSemanticoLib import getErros

def main(argv):
    istream = FileStream(argv[1])
    lexer = GramaticaLexer(istream)
    stream = CommonTokenStream(lexer)
    parser = GramaticaParser(stream)
    tree = parser.casamento()

    #análise semântica
    semantic_analyser = AnalisadorSemantico()
    semantic_analyser.visitCasamento(tree)

    erros = getErros()
    for erro in erros:
        print(erro)

if __name__ == '__main__':
    main(sys.argv)