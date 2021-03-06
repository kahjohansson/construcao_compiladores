# Generated from Grammar.g4 by ANTLR 4.9.1
from antlr4 import *
from io import StringIO
from typing.io import TextIO
import sys



def serializedATN():
    with StringIO() as buf:
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\b")
        buf.write("?\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7")
        buf.write("\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3")
        buf.write("\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\7\5)\n")
        buf.write("\5\f\5\16\5,\13\5\3\6\6\6/\n\6\r\6\16\6\60\3\6\3\6\6\6")
        buf.write("\65\n\6\r\6\16\6\66\3\7\6\7:\n\7\r\7\16\7;\3\7\3\7\2\2")
        buf.write("\b\3\3\5\4\7\5\t\6\13\7\r\b\3\2\7\5\2C\\aac|\6\2\62;C")
        buf.write("\\aac|\3\2\62;\3\2\60\60\5\2\13\f\17\17\"\"\2B\2\3\3\2")
        buf.write("\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2")
        buf.write("\2\r\3\2\2\2\3\17\3\2\2\2\5\31\3\2\2\2\7\37\3\2\2\2\t")
        buf.write("&\3\2\2\2\13.\3\2\2\2\r9\3\2\2\2\17\20\7c\2\2\20\21\7")
        buf.write("n\2\2\21\22\7i\2\2\22\23\7q\2\2\23\24\7t\2\2\24\25\7k")
        buf.write("\2\2\25\26\7v\2\2\26\27\7o\2\2\27\30\7q\2\2\30\4\3\2\2")
        buf.write("\2\31\32\7u\2\2\32\33\7c\2\2\33\34\7k\2\2\34\35\7f\2\2")
        buf.write("\35\36\7c\2\2\36\6\3\2\2\2\37 \7f\2\2 !\7g\2\2!\"\7e\2")
        buf.write("\2\"#\7k\2\2#$\7f\2\2$%\7g\2\2%\b\3\2\2\2&*\t\2\2\2\'")
        buf.write(")\t\3\2\2(\'\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\n")
        buf.write("\3\2\2\2,*\3\2\2\2-/\t\4\2\2.-\3\2\2\2/\60\3\2\2\2\60")
        buf.write(".\3\2\2\2\60\61\3\2\2\2\61\62\3\2\2\2\62\64\t\5\2\2\63")
        buf.write("\65\t\4\2\2\64\63\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2")
        buf.write("\66\67\3\2\2\2\67\f\3\2\2\28:\t\6\2\298\3\2\2\2:;\3\2")
        buf.write("\2\2;9\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\b\7\2\2>\16\3\2\2")
        buf.write("\2\7\2*\60\66;\3\b\2\2")
        return buf.getvalue()


class GrammarLexer(Lexer):

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    ALGORITMO = 1
    SAIDA = 2
    DECIDE = 3
    IDENT = 4
    NUM_REAL = 5
    WHITE_SPACE = 6

    channelNames = [ u"DEFAULT_TOKEN_CHANNEL", u"HIDDEN" ]

    modeNames = [ "DEFAULT_MODE" ]

    literalNames = [ "<INVALID>",
            "'algoritmo'", "'saida'", "'decide'" ]

    symbolicNames = [ "<INVALID>",
            "ALGORITMO", "SAIDA", "DECIDE", "IDENT", "NUM_REAL", "WHITE_SPACE" ]

    ruleNames = [ "ALGORITMO", "SAIDA", "DECIDE", "IDENT", "NUM_REAL", "WHITE_SPACE" ]

    grammarFileName = "Grammar.g4"

    def __init__(self, input=None, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.9.1")
        self._interp = LexerATNSimulator(self, self.atn, self.decisionsToDFA, PredictionContextCache())
        self._actions = None
        self._predicates = None


