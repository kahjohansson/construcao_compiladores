from GramaticaVisitor import GramaticaVisitor
from GramaticaParser import GramaticaParser
from AnalisadorSemanticoLib import *

class AnalisadorSemantico(GramaticaVisitor):

    def visitLista_convidados(self, ctx:GramaticaParser.Lista_convidadosContext):
        #retorna lado e quantidade de convidados da lista
        return ctx.lado().getText(), len(ctx.nome())


    def visitListas_convidados(self, ctx:GramaticaParser.Listas_convidadosContext):
        quantidadeNoiva = 0
        quantidadeNoivo = 0
        quantidadeAmbos = 0
        for lctx in ctx.lista_convidados():
            lado, quantidade = self.visitLista_convidados(lctx)
            if lado == 'NOIVA':
                quantidadeNoiva = quantidade
            elif lado == 'NOIVO':
                quantidadeNoivo = quantidade
            else:
                quantidadeAmbos = quantidade

        if quantidadeNoiva > quantidadeNoivo*1.2:
            adicionaErro("Erro: Lista noiva mais do que 20% maior que a da noiva")
        elif quantidadeNoivo > quantidadeNoiva*1.2:
            adicionaErro("Erro: Lista noivo mais do que 20% maior que a da noivo")

        quantidadeConvidados = quantidadeNoiva + quantidadeNoivo + quantidadeAmbos
        setQuantidadeConvidados(quantidadeConvidados)


    def visitLista_padrinhos(self, ctx:GramaticaParser.Lista_padrinhosContext):
        return len(ctx.nome())


    def visitLista_madrinhas(self, ctx:GramaticaParser.Lista_madrinhasContext):
        return len(ctx.nome())


    def visitListas_padrinhos(self, ctx:GramaticaParser.Listas_padrinhosContext):
        qtdMadrinhas = self.visitLista_madrinhas(ctx.lista_madrinhas())
        qtdPadrinhos = self.visitLista_padrinhos(ctx.lista_padrinhos())
        
        if qtdMadrinhas != qtdPadrinhos:
            adicionaErro("Erro: Quantidade de madrinhas e padrinhos diferentes entre si")


    def visitServico(self, ctx:GramaticaParser.ServicoContext):
        return ctx.valor().getText(), ctx.modalidade().getText()


    def visitServicos(self, ctx:GramaticaParser.ServicosContext):
        valorTotal = 0
        quantidadeConvidados = getQuantidadeConvidados()
        print(f'quantidade de convidados: {quantidadeConvidados}')
        for sctx in ctx.servico():
            valorServico, modalidade = self.visitServico(sctx)
            print(f'modalidade: {modalidade}')
            if modalidade == 'TOTAL':
                multiplicador = 1
            else:
                multiplicador = quantidadeConvidados

            valorTotal += int(valorServico) * multiplicador
            print(f'valor do multiplicador: {multiplicador}')
        
        setValorCusto(valorTotal)


    def visitValor_disponivel(self, ctx:GramaticaParser.Valor_disponivelContext):
        print(f'valor total do casamento: {getValorCusto()}')
        if int(ctx.valor().getText()) < getValorCusto():
           adicionaErro("Erro: Custo do casamento maior do que o valor disponível")
