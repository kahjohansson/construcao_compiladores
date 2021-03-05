# Generated from Grammar.g4 by ANTLR 4.9.1
from antlr4 import *
from io import StringIO
from typing.io import TextIO
import sys



def serializedATN():
    with StringIO() as buf:
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\3")
        buf.write("\17\b\1\4\2\t\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3")
        buf.write("\2\2\2\3\3\3\3\2\2\2\16\2\3\3\2\2\2\3\5\3\2\2\2\5\6\7")
        buf.write("c\2\2\6\7\7n\2\2\7\b\7i\2\2\b\t\7q\2\2\t\n\7t\2\2\n\13")
        buf.write("\7k\2\2\13\f\7v\2\2\f\r\7o\2\2\r\16\7q\2\2\16\4\3\2\2")
        buf.write("\2\3\2\2")
        return buf.getvalue()


class GrammarLexer(Lexer):

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    T__0 = 1

    channelNames = [ u"DEFAULT_TOKEN_CHANNEL", u"HIDDEN" ]

    modeNames = [ "DEFAULT_MODE" ]

    literalNames = [ "<INVALID>",
            "'algoritmo'" ]

    symbolicNames = [ "<INVALID>",
 ]

    ruleNames = [ "T__0" ]

    grammarFileName = "Grammar.g4"

    def __init__(self, input=None, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.9.1")
        self._interp = LexerATNSimulator(self, self.atn, self.decisionsToDFA, PredictionContextCache())
        self._actions = None
        self._predicates = None


