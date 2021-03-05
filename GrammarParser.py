# Generated from Grammar.g4 by ANTLR 4.9.1
# encoding: utf-8
from antlr4 import *
from io import StringIO
import sys
if sys.version_info[1] > 5:
	from typing import TextIO
else:
	from typing.io import TextIO


def serializedATN():
    with StringIO() as buf:
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\3")
        buf.write("\7\4\2\t\2\3\2\3\2\3\2\2\2\3\2\2\2\2\5\2\4\3\2\2\2\4\5")
        buf.write("\7\3\2\2\5\3\3\2\2\2\2")
        return buf.getvalue()


class GrammarParser ( Parser ):

    grammarFileName = "Grammar.g4"

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    sharedContextCache = PredictionContextCache()

    literalNames = [ "<INVALID>", "'algoritmo'" ]

    symbolicNames = [  ]

    RULE_palavra_chave = 0

    ruleNames =  [ "palavra_chave" ]

    EOF = Token.EOF
    T__0=1

    def __init__(self, input:TokenStream, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.9.1")
        self._interp = ParserATNSimulator(self, self.atn, self.decisionsToDFA, self.sharedContextCache)
        self._predicates = None




    class Palavra_chaveContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser


        def getRuleIndex(self):
            return GrammarParser.RULE_palavra_chave

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterPalavra_chave" ):
                listener.enterPalavra_chave(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitPalavra_chave" ):
                listener.exitPalavra_chave(self)




    def palavra_chave(self):

        localctx = GrammarParser.Palavra_chaveContext(self, self._ctx, self.state)
        self.enterRule(localctx, 0, self.RULE_palavra_chave)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 2
            self.match(GrammarParser.T__0)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx





