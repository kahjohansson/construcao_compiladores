# Generated from Gramatica.g4 by ANTLR 4.9.2
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
        buf.write("\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\26")
        buf.write("v\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b")
        buf.write("\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t")
        buf.write("\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3\2\3\2\3\2")
        buf.write("\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\6\5\64")
        buf.write("\n\5\r\5\16\5\65\3\6\3\6\3\6\3\6\3\6\7\6=\n\6\f\6\16\6")
        buf.write("@\13\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\n\7\n")
        buf.write("M\n\n\f\n\16\nP\13\n\3\13\3\13\3\13\3\13\7\13V\n\13\f")
        buf.write("\13\16\13Y\13\13\3\f\6\f\\\n\f\r\f\16\f]\3\r\3\r\3\r\3")
        buf.write("\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\6\21m\n")
        buf.write("\21\r\21\16\21n\3\21\3\21\3\21\5\21t\n\21\3\21\2\2\22")
        buf.write("\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\4\4\2\5\6\b")
        buf.write("\b\3\2\f\r\2l\2\"\3\2\2\2\4*\3\2\2\2\6.\3\2\2\2\b\63\3")
        buf.write("\2\2\2\n\67\3\2\2\2\fA\3\2\2\2\16C\3\2\2\2\20E\3\2\2\2")
        buf.write("\22H\3\2\2\2\24Q\3\2\2\2\26[\3\2\2\2\30_\3\2\2\2\32d\3")
        buf.write("\2\2\2\34f\3\2\2\2\36h\3\2\2\2 l\3\2\2\2\"#\7\3\2\2#$")
        buf.write("\5\4\3\2$%\5\6\4\2%&\5\b\5\2&\'\5\20\t\2\'(\5\26\f\2(")
        buf.write(")\5\36\20\2)\3\3\2\2\2*+\7\4\2\2+,\7\5\2\2,-\5\f\7\2-")
        buf.write("\5\3\2\2\2./\7\4\2\2/\60\7\6\2\2\60\61\5\f\7\2\61\7\3")
        buf.write("\2\2\2\62\64\5\n\6\2\63\62\3\2\2\2\64\65\3\2\2\2\65\63")
        buf.write("\3\2\2\2\65\66\3\2\2\2\66\t\3\2\2\2\678\7\7\2\289\5\16")
        buf.write("\b\29>\5\f\7\2:;\7\25\2\2;=\5\f\7\2<:\3\2\2\2=@\3\2\2")
        buf.write("\2><\3\2\2\2>?\3\2\2\2?\13\3\2\2\2@>\3\2\2\2AB\7\20\2")
        buf.write("\2B\r\3\2\2\2CD\t\2\2\2D\17\3\2\2\2EF\5\22\n\2FG\5\24")
        buf.write("\13\2G\21\3\2\2\2HI\7\t\2\2IN\5\f\7\2JK\7\25\2\2KM\5\f")
        buf.write("\7\2LJ\3\2\2\2MP\3\2\2\2NL\3\2\2\2NO\3\2\2\2O\23\3\2\2")
        buf.write("\2PN\3\2\2\2QR\7\n\2\2RW\5\f\7\2ST\7\25\2\2TV\5\f\7\2")
        buf.write("US\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X\25\3\2\2\2Y")
        buf.write("W\3\2\2\2Z\\\5\30\r\2[Z\3\2\2\2\\]\3\2\2\2][\3\2\2\2]")
        buf.write("^\3\2\2\2^\27\3\2\2\2_`\7\13\2\2`a\5\34\17\2ab\5\32\16")
        buf.write("\2bc\5 \21\2c\31\3\2\2\2de\t\3\2\2e\33\3\2\2\2fg\7\20")
        buf.write("\2\2g\35\3\2\2\2hi\7\16\2\2ij\5 \21\2j\37\3\2\2\2km\7")
        buf.write("\23\2\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2os\3\2")
        buf.write("\2\2pq\7\17\2\2qr\7\23\2\2rt\7\23\2\2sp\3\2\2\2st\3\2")
        buf.write("\2\2t!\3\2\2\2\t\65>NW]ns")
        return buf.getvalue()


class GramaticaParser ( Parser ):

    grammarFileName = "Gramatica.g4"

    atn = ATNDeserializer().deserialize(serializedATN())

    decisionsToDFA = [ DFA(ds, i) for i, ds in enumerate(atn.decisionToState) ]

    sharedContextCache = PredictionContextCache()

    literalNames = [ "<INVALID>", "'CASAMENTO'", "'NOME'", "'NOIVA'", "'NOIVO'", 
                     "'CONVIDADOS'", "'AMBOS'", "'MADRINHAS'", "'PADRINHOS'", 
                     "'SERVICO'", "'POR PESSOA'", "'TOTAL'", "'VALOR DISPONIVEL'", 
                     "'.'", "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>", 
                     "<INVALID>", "','" ]

    symbolicNames = [ "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>", 
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>", 
                      "<INVALID>", "<INVALID>", "<INVALID>", "<INVALID>", 
                      "<INVALID>", "<INVALID>", "CADEIA", "LETRA_MINUSCULA", 
                      "LETRA_MAIUSCULA", "ALGARISMO", "SKIP_WS", "SIMBOLOS", 
                      "ERRO_GERAL" ]

    RULE_casamento = 0
    RULE_nome_noiva = 1
    RULE_nome_noivo = 2
    RULE_listas_convidados = 3
    RULE_lista_convidados = 4
    RULE_nome = 5
    RULE_lado = 6
    RULE_listas_padrinhos = 7
    RULE_lista_madrinhas = 8
    RULE_lista_padrinhos = 9
    RULE_servicos = 10
    RULE_servico = 11
    RULE_modalidade = 12
    RULE_nome_servico = 13
    RULE_valor_disponivel = 14
    RULE_valor = 15

    ruleNames =  [ "casamento", "nome_noiva", "nome_noivo", "listas_convidados", 
                   "lista_convidados", "nome", "lado", "listas_padrinhos", 
                   "lista_madrinhas", "lista_padrinhos", "servicos", "servico", 
                   "modalidade", "nome_servico", "valor_disponivel", "valor" ]

    EOF = Token.EOF
    T__0=1
    T__1=2
    T__2=3
    T__3=4
    T__4=5
    T__5=6
    T__6=7
    T__7=8
    T__8=9
    T__9=10
    T__10=11
    T__11=12
    T__12=13
    CADEIA=14
    LETRA_MINUSCULA=15
    LETRA_MAIUSCULA=16
    ALGARISMO=17
    SKIP_WS=18
    SIMBOLOS=19
    ERRO_GERAL=20

    def __init__(self, input:TokenStream, output:TextIO = sys.stdout):
        super().__init__(input, output)
        self.checkVersion("4.9.2")
        self._interp = ParserATNSimulator(self, self.atn, self.decisionsToDFA, self.sharedContextCache)
        self._predicates = None




    class CasamentoContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome_noiva(self):
            return self.getTypedRuleContext(GramaticaParser.Nome_noivaContext,0)


        def nome_noivo(self):
            return self.getTypedRuleContext(GramaticaParser.Nome_noivoContext,0)


        def listas_convidados(self):
            return self.getTypedRuleContext(GramaticaParser.Listas_convidadosContext,0)


        def listas_padrinhos(self):
            return self.getTypedRuleContext(GramaticaParser.Listas_padrinhosContext,0)


        def servicos(self):
            return self.getTypedRuleContext(GramaticaParser.ServicosContext,0)


        def valor_disponivel(self):
            return self.getTypedRuleContext(GramaticaParser.Valor_disponivelContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_casamento

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterCasamento" ):
                listener.enterCasamento(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitCasamento" ):
                listener.exitCasamento(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitCasamento" ):
                return visitor.visitCasamento(self)
            else:
                return visitor.visitChildren(self)




    def casamento(self):

        localctx = GramaticaParser.CasamentoContext(self, self._ctx, self.state)
        self.enterRule(localctx, 0, self.RULE_casamento)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 32
            self.match(GramaticaParser.T__0)
            self.state = 33
            self.nome_noiva()
            self.state = 34
            self.nome_noivo()
            self.state = 35
            self.listas_convidados()
            self.state = 36
            self.listas_padrinhos()
            self.state = 37
            self.servicos()
            self.state = 38
            self.valor_disponivel()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Nome_noivaContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome(self):
            return self.getTypedRuleContext(GramaticaParser.NomeContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_nome_noiva

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterNome_noiva" ):
                listener.enterNome_noiva(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitNome_noiva" ):
                listener.exitNome_noiva(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitNome_noiva" ):
                return visitor.visitNome_noiva(self)
            else:
                return visitor.visitChildren(self)




    def nome_noiva(self):

        localctx = GramaticaParser.Nome_noivaContext(self, self._ctx, self.state)
        self.enterRule(localctx, 2, self.RULE_nome_noiva)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 40
            self.match(GramaticaParser.T__1)
            self.state = 41
            self.match(GramaticaParser.T__2)
            self.state = 42
            self.nome()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Nome_noivoContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome(self):
            return self.getTypedRuleContext(GramaticaParser.NomeContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_nome_noivo

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterNome_noivo" ):
                listener.enterNome_noivo(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitNome_noivo" ):
                listener.exitNome_noivo(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitNome_noivo" ):
                return visitor.visitNome_noivo(self)
            else:
                return visitor.visitChildren(self)




    def nome_noivo(self):

        localctx = GramaticaParser.Nome_noivoContext(self, self._ctx, self.state)
        self.enterRule(localctx, 4, self.RULE_nome_noivo)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 44
            self.match(GramaticaParser.T__1)
            self.state = 45
            self.match(GramaticaParser.T__3)
            self.state = 46
            self.nome()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Listas_convidadosContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def lista_convidados(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GramaticaParser.Lista_convidadosContext)
            else:
                return self.getTypedRuleContext(GramaticaParser.Lista_convidadosContext,i)


        def getRuleIndex(self):
            return GramaticaParser.RULE_listas_convidados

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterListas_convidados" ):
                listener.enterListas_convidados(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitListas_convidados" ):
                listener.exitListas_convidados(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitListas_convidados" ):
                return visitor.visitListas_convidados(self)
            else:
                return visitor.visitChildren(self)




    def listas_convidados(self):

        localctx = GramaticaParser.Listas_convidadosContext(self, self._ctx, self.state)
        self.enterRule(localctx, 6, self.RULE_listas_convidados)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 49 
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 48
                self.lista_convidados()
                self.state = 51 
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==GramaticaParser.T__4):
                    break

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Lista_convidadosContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def lado(self):
            return self.getTypedRuleContext(GramaticaParser.LadoContext,0)


        def nome(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GramaticaParser.NomeContext)
            else:
                return self.getTypedRuleContext(GramaticaParser.NomeContext,i)


        def SIMBOLOS(self, i:int=None):
            if i is None:
                return self.getTokens(GramaticaParser.SIMBOLOS)
            else:
                return self.getToken(GramaticaParser.SIMBOLOS, i)

        def getRuleIndex(self):
            return GramaticaParser.RULE_lista_convidados

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterLista_convidados" ):
                listener.enterLista_convidados(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitLista_convidados" ):
                listener.exitLista_convidados(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitLista_convidados" ):
                return visitor.visitLista_convidados(self)
            else:
                return visitor.visitChildren(self)




    def lista_convidados(self):

        localctx = GramaticaParser.Lista_convidadosContext(self, self._ctx, self.state)
        self.enterRule(localctx, 8, self.RULE_lista_convidados)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 53
            self.match(GramaticaParser.T__4)
            self.state = 54
            self.lado()
            self.state = 55
            self.nome()
            self.state = 60
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while _la==GramaticaParser.SIMBOLOS:
                self.state = 56
                self.match(GramaticaParser.SIMBOLOS)
                self.state = 57
                self.nome()
                self.state = 62
                self._errHandler.sync(self)
                _la = self._input.LA(1)

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class NomeContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def CADEIA(self):
            return self.getToken(GramaticaParser.CADEIA, 0)

        def getRuleIndex(self):
            return GramaticaParser.RULE_nome

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterNome" ):
                listener.enterNome(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitNome" ):
                listener.exitNome(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitNome" ):
                return visitor.visitNome(self)
            else:
                return visitor.visitChildren(self)




    def nome(self):

        localctx = GramaticaParser.NomeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 10, self.RULE_nome)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 63
            self.match(GramaticaParser.CADEIA)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class LadoContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser


        def getRuleIndex(self):
            return GramaticaParser.RULE_lado

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterLado" ):
                listener.enterLado(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitLado" ):
                listener.exitLado(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitLado" ):
                return visitor.visitLado(self)
            else:
                return visitor.visitChildren(self)




    def lado(self):

        localctx = GramaticaParser.LadoContext(self, self._ctx, self.state)
        self.enterRule(localctx, 12, self.RULE_lado)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 65
            _la = self._input.LA(1)
            if not((((_la) & ~0x3f) == 0 and ((1 << _la) & ((1 << GramaticaParser.T__2) | (1 << GramaticaParser.T__3) | (1 << GramaticaParser.T__5))) != 0)):
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


    class Listas_padrinhosContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def lista_madrinhas(self):
            return self.getTypedRuleContext(GramaticaParser.Lista_madrinhasContext,0)


        def lista_padrinhos(self):
            return self.getTypedRuleContext(GramaticaParser.Lista_padrinhosContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_listas_padrinhos

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterListas_padrinhos" ):
                listener.enterListas_padrinhos(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitListas_padrinhos" ):
                listener.exitListas_padrinhos(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitListas_padrinhos" ):
                return visitor.visitListas_padrinhos(self)
            else:
                return visitor.visitChildren(self)




    def listas_padrinhos(self):

        localctx = GramaticaParser.Listas_padrinhosContext(self, self._ctx, self.state)
        self.enterRule(localctx, 14, self.RULE_listas_padrinhos)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 67
            self.lista_madrinhas()
            self.state = 68
            self.lista_padrinhos()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Lista_madrinhasContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GramaticaParser.NomeContext)
            else:
                return self.getTypedRuleContext(GramaticaParser.NomeContext,i)


        def SIMBOLOS(self, i:int=None):
            if i is None:
                return self.getTokens(GramaticaParser.SIMBOLOS)
            else:
                return self.getToken(GramaticaParser.SIMBOLOS, i)

        def getRuleIndex(self):
            return GramaticaParser.RULE_lista_madrinhas

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterLista_madrinhas" ):
                listener.enterLista_madrinhas(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitLista_madrinhas" ):
                listener.exitLista_madrinhas(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitLista_madrinhas" ):
                return visitor.visitLista_madrinhas(self)
            else:
                return visitor.visitChildren(self)




    def lista_madrinhas(self):

        localctx = GramaticaParser.Lista_madrinhasContext(self, self._ctx, self.state)
        self.enterRule(localctx, 16, self.RULE_lista_madrinhas)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 70
            self.match(GramaticaParser.T__6)
            self.state = 71
            self.nome()
            self.state = 76
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while _la==GramaticaParser.SIMBOLOS:
                self.state = 72
                self.match(GramaticaParser.SIMBOLOS)
                self.state = 73
                self.nome()
                self.state = 78
                self._errHandler.sync(self)
                _la = self._input.LA(1)

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Lista_padrinhosContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GramaticaParser.NomeContext)
            else:
                return self.getTypedRuleContext(GramaticaParser.NomeContext,i)


        def SIMBOLOS(self, i:int=None):
            if i is None:
                return self.getTokens(GramaticaParser.SIMBOLOS)
            else:
                return self.getToken(GramaticaParser.SIMBOLOS, i)

        def getRuleIndex(self):
            return GramaticaParser.RULE_lista_padrinhos

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterLista_padrinhos" ):
                listener.enterLista_padrinhos(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitLista_padrinhos" ):
                listener.exitLista_padrinhos(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitLista_padrinhos" ):
                return visitor.visitLista_padrinhos(self)
            else:
                return visitor.visitChildren(self)




    def lista_padrinhos(self):

        localctx = GramaticaParser.Lista_padrinhosContext(self, self._ctx, self.state)
        self.enterRule(localctx, 18, self.RULE_lista_padrinhos)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 79
            self.match(GramaticaParser.T__7)
            self.state = 80
            self.nome()
            self.state = 85
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while _la==GramaticaParser.SIMBOLOS:
                self.state = 81
                self.match(GramaticaParser.SIMBOLOS)
                self.state = 82
                self.nome()
                self.state = 87
                self._errHandler.sync(self)
                _la = self._input.LA(1)

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ServicosContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def servico(self, i:int=None):
            if i is None:
                return self.getTypedRuleContexts(GramaticaParser.ServicoContext)
            else:
                return self.getTypedRuleContext(GramaticaParser.ServicoContext,i)


        def getRuleIndex(self):
            return GramaticaParser.RULE_servicos

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterServicos" ):
                listener.enterServicos(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitServicos" ):
                listener.exitServicos(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitServicos" ):
                return visitor.visitServicos(self)
            else:
                return visitor.visitChildren(self)




    def servicos(self):

        localctx = GramaticaParser.ServicosContext(self, self._ctx, self.state)
        self.enterRule(localctx, 20, self.RULE_servicos)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 89 
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 88
                self.servico()
                self.state = 91 
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==GramaticaParser.T__8):
                    break

        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ServicoContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def nome_servico(self):
            return self.getTypedRuleContext(GramaticaParser.Nome_servicoContext,0)


        def modalidade(self):
            return self.getTypedRuleContext(GramaticaParser.ModalidadeContext,0)


        def valor(self):
            return self.getTypedRuleContext(GramaticaParser.ValorContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_servico

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterServico" ):
                listener.enterServico(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitServico" ):
                listener.exitServico(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitServico" ):
                return visitor.visitServico(self)
            else:
                return visitor.visitChildren(self)




    def servico(self):

        localctx = GramaticaParser.ServicoContext(self, self._ctx, self.state)
        self.enterRule(localctx, 22, self.RULE_servico)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 93
            self.match(GramaticaParser.T__8)
            self.state = 94
            self.nome_servico()
            self.state = 95
            self.modalidade()
            self.state = 96
            self.valor()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ModalidadeContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser


        def getRuleIndex(self):
            return GramaticaParser.RULE_modalidade

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterModalidade" ):
                listener.enterModalidade(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitModalidade" ):
                listener.exitModalidade(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitModalidade" ):
                return visitor.visitModalidade(self)
            else:
                return visitor.visitChildren(self)




    def modalidade(self):

        localctx = GramaticaParser.ModalidadeContext(self, self._ctx, self.state)
        self.enterRule(localctx, 24, self.RULE_modalidade)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 98
            _la = self._input.LA(1)
            if not(_la==GramaticaParser.T__9 or _la==GramaticaParser.T__10):
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


    class Nome_servicoContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def CADEIA(self):
            return self.getToken(GramaticaParser.CADEIA, 0)

        def getRuleIndex(self):
            return GramaticaParser.RULE_nome_servico

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterNome_servico" ):
                listener.enterNome_servico(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitNome_servico" ):
                listener.exitNome_servico(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitNome_servico" ):
                return visitor.visitNome_servico(self)
            else:
                return visitor.visitChildren(self)




    def nome_servico(self):

        localctx = GramaticaParser.Nome_servicoContext(self, self._ctx, self.state)
        self.enterRule(localctx, 26, self.RULE_nome_servico)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 100
            self.match(GramaticaParser.CADEIA)
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class Valor_disponivelContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def valor(self):
            return self.getTypedRuleContext(GramaticaParser.ValorContext,0)


        def getRuleIndex(self):
            return GramaticaParser.RULE_valor_disponivel

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterValor_disponivel" ):
                listener.enterValor_disponivel(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitValor_disponivel" ):
                listener.exitValor_disponivel(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitValor_disponivel" ):
                return visitor.visitValor_disponivel(self)
            else:
                return visitor.visitChildren(self)




    def valor_disponivel(self):

        localctx = GramaticaParser.Valor_disponivelContext(self, self._ctx, self.state)
        self.enterRule(localctx, 28, self.RULE_valor_disponivel)
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 102
            self.match(GramaticaParser.T__11)
            self.state = 103
            self.valor()
        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx


    class ValorContext(ParserRuleContext):
        __slots__ = 'parser'

        def __init__(self, parser, parent:ParserRuleContext=None, invokingState:int=-1):
            super().__init__(parent, invokingState)
            self.parser = parser

        def ALGARISMO(self, i:int=None):
            if i is None:
                return self.getTokens(GramaticaParser.ALGARISMO)
            else:
                return self.getToken(GramaticaParser.ALGARISMO, i)

        def getRuleIndex(self):
            return GramaticaParser.RULE_valor

        def enterRule(self, listener:ParseTreeListener):
            if hasattr( listener, "enterValor" ):
                listener.enterValor(self)

        def exitRule(self, listener:ParseTreeListener):
            if hasattr( listener, "exitValor" ):
                listener.exitValor(self)

        def accept(self, visitor:ParseTreeVisitor):
            if hasattr( visitor, "visitValor" ):
                return visitor.visitValor(self)
            else:
                return visitor.visitChildren(self)




    def valor(self):

        localctx = GramaticaParser.ValorContext(self, self._ctx, self.state)
        self.enterRule(localctx, 30, self.RULE_valor)
        self._la = 0 # Token type
        try:
            self.enterOuterAlt(localctx, 1)
            self.state = 106 
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            while True:
                self.state = 105
                self.match(GramaticaParser.ALGARISMO)
                self.state = 108 
                self._errHandler.sync(self)
                _la = self._input.LA(1)
                if not (_la==GramaticaParser.ALGARISMO):
                    break

            self.state = 113
            self._errHandler.sync(self)
            _la = self._input.LA(1)
            if _la==GramaticaParser.T__12:
                self.state = 110
                self.match(GramaticaParser.T__12)
                self.state = 111
                self.match(GramaticaParser.ALGARISMO)
                self.state = 112
                self.match(GramaticaParser.ALGARISMO)


        except RecognitionException as re:
            localctx.exception = re
            self._errHandler.reportError(self, re)
            self._errHandler.recover(self, re)
        finally:
            self.exitRule()
        return localctx





