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
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\b")
        buf.write("\"\4\2\t\2\4\3\t\3\4\4\t\4\3\2\7\2\n\n\2\f\2\16\2\r\13")
        buf.write("\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3\2\7\2\26\n\2\f\2")
        buf.write("\16\2\31\13\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\2\2\5\2")
        buf.write("\4\6\2\3\3\2\3\5\2!\2\13\3\2\2\2\4\35\3\2\2\2\6\37\3\2")
        buf.write("\2\2\b\n\5\6\4\2\t\b\3\2\2\2\n\r\3\2\2\2\13\t\3\2\2\2")
        buf.write("\13\f\3\2\2\2\f\21\3\2\2\2\r\13\3\2\2\2\16\20\5\4\3\2")
        buf.write("\17\16\3\2\2\2\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2\2")
        buf.write("\2\22\27\3\2\2\2\23\21\3\2\2\2\24\26\5\6\4\2\25\24\3\2")
        buf.write("\2\2\26\31\3\2\2\2\27\25\3\2\2\2\27\30\3\2\2\2\30\32\3")
        buf.write("\2\2\2\31\27\3\2\2\2\32\33\7\7\2\2\33\34\7\2\2\3\34\3")
        buf.write("\3\2\2\2\35\36\t\2\2\2\36\5\3\2\2\2\37 \7\6\2\2 \7\3\2")
        buf.write("\2\2\5\13\21\27")
        return buf.getvalue()


class GrammarParser ( Parser ):

    grammarFileName = "Grammar.g4"

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    sharedContextCache = PredictionContextCache()

    literalNames = [ "<INVALID>", "'algoritmo'", "'saida'", "'decide'" ]

    symbolicNames = [ "<INVALID>", "ALGORITMO", "SAIDA", "DECIDE", "IDENT", 
                      "NUM_REAL", "WHITE_SPACE" ]

    RULE_prog = 0
    RULE_palavra_chave = 1
    RULE_ident = 2

    ruleNames =  [ "prog", "palavra_chave", "ident" ]

    EOF = Token.EOF
    ALGORITMO=1
    SAIDA=2
    DECIDE=3
    IDENT=4
    NUM_REAL=5
    WHITE_SPACE=6

    def __init__(self, input:TokenStream, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.9.1")
        self._interp = ParserATNSimulator(self, self.atn, self.decisionsToDFA, self.sharedContextCache)
        self._predicates = None




    class ProgContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def NUM_REAL(self):
            return self.getToken(GrammarParser.NUM_REAL, 0)

        def EOF(self):
            return self.getToken(GrammarParser.EOF, 0)

        def ident(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GrammarParser.IdentContext)
            else:
                return self.getTypedRuleContext(GrammarParser.IdentContext,i)


        def palavra_chave(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GrammarParser.Palavra_chaveContext)
            else:
                return self.getTypedRuleContext(GrammarParser.Palavra_chaveContext,i)


        def getRuleIndex(self):
            return GrammarParser.RULE_prog

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterProg" ):
                listener.enterProg(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitProg" ):
                listener.exitProg(self)




    def prog(self):

        localctx = GrammarParser.ProgContext(self, self._ctx, self.state)
        self.enterRule(localctx, 0, self.RULE_prog)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 9
            self._errHandler.sync(self)
            _alt = self._interp.adaptivePredict(self._input,0,self._ctx)
            while _alt!=2 and _alt!=ATN.INVALID_ALT_NUMBER:
                if _alt==1:
                    self.state = 6
                    self.ident() 
                self.state = 11
                self._errHandler.sync(self)
                _alt = self._interp.adaptivePredict(self._input,0,self._ctx)

            self.state = 15
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while (((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << GrammarParser.ALGORITMO) | (1 << GrammarParser.SAIDA) | (1 << GrammarParser.DECIDE))) != 0):
                self.state = 12
                self.palavra_chave()
                self.state = 17
                self._errHandler.sync(self)
                _la = self._input.LA(1)

            self.state = 21
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while _la==GrammarParser.IDENT:
                self.state = 18
                self.ident()
                self.state = 23
                self._errHandler.sync(self)
                _la = self._input.LA(1)

            self.state = 24
            self.match(GrammarParser.NUM_REAL)
            self.state = 25
            self.match(GrammarParser.EOF)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Palavra_chaveContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def SAIDA(self):
            return self.getToken(GrammarParser.SAIDA, 0)

        def DECIDE(self):
            return self.getToken(GrammarParser.DECIDE, 0)

        def ALGORITMO(self):
            return self.getToken(GrammarParser.ALGORITMO, 0)

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
        self.enterRule(localctx, 2, self.RULE_palavra_chave)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 27
            _la = self._input.LA(1)
            if not((((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << GrammarParser.ALGORITMO) | (1 << GrammarParser.SAIDA) | (1 << GrammarParser.DECIDE))) != 0)):
                self._errHandler.recoverInline(self)
            else:
                self._errHandler.reportMatch(self)
                self.consume()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class IdentContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def IDENT(self):
            return self.getToken(GrammarParser.IDENT, 0)

        def getRuleIndex(self):
            return GrammarParser.RULE_ident

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterIdent" ):
                listener.enterIdent(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitIdent" ):
                listener.exitIdent(self)




    def ident(self):

        localctx = GrammarParser.IdentContext(self, self._ctx, self.state)
        self.enterRule(localctx, 4, self.RULE_ident)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 29
            self.match(GrammarParser.IDENT)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx





