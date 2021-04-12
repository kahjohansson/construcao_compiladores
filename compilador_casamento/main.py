import sys
from antlr4 import *
from GramaticaLexer import GramaticaLexer
from GramaticaParser import GramaticaParser
from GramaticaListener import GramaticaListener

from AnalisadorSemantico import AnalisadorSemantico
from AnalisadorSemanticoLib import AnalisadorSemanticoLib
from GeradorHtml import GeradorHtml


def main(argv):
    istream = FileStream(argv[1])
    lexer = GramaticaLexer(istream)
    stream = CommonTokenStream(lexer)
    parser = GramaticaParser(stream)
    tree = parser.casamento()

    #análise semântica
    semantic_analyser = AnalisadorSemantico()
    semantic_analyser.visitCasamento(tree)
    # erros semânticos
    erros = semantic_analyser.lib.getErros()
    output_file = open(argv[2], 'w+')
    for erro in erros:
        output_file.write(erro + '\n')
    output_file.close()

    #geração de código
    if len(erros) == 0:
        code_generator = GeradorHtml()
        code_generator.visitCasamento(tree)
        output_file = open(argv[2], 'w+')
        data = code_generator.saida
        for line in data:
            output_file.write(line)
        output_file.close()

if __name__ == '__main__':
    main(sys.argv)