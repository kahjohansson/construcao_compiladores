from GramaticaVisitor import GramaticaVisitor
from GramaticaParser import GramaticaParser
from AnalisadorSemanticoLib import AnalisadorSemanticoLib

class AnalisadorSemantico(GramaticaVisitor):
    '''
    Realiza a análise semântica, seguindo 3 regras:
    1. o número de convidados de um lado não pode ser 20% maior que do outro
    2. o número de padrinhos do sexo masculino e feminino têm que ser igual e têm que ser par
    3. o custo total da festa tem que ser menor que o valor disponível
    '''

    def __init__(self):
        self.lib = AnalisadorSemanticoLib()

    def visitLista_convidados(self, ctx:GramaticaParser.Lista_convidadosContext):
        '''
        retorna lado e quantidade de convidados da lista
        '''
        return ctx.lado().getText(), len(ctx.nome())


    def visitListas_convidados(self, ctx:GramaticaParser.Listas_convidadosContext):
        '''
        se houver, dispara erro 1 (tamanho de lista de convidados)
        calcula e salva a quantidade de convidados
        '''
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
            self.lib.adicionaErro("Erro: Lista de convidados da noiva mais do que 20% maior que a da noiva")
        elif quantidadeNoivo > quantidadeNoiva*1.2:
            self.lib.adicionaErro("Erro: Lista de convidados do noivo mais do que 20% maior que a da noivo")

        quantidadeConvidados = quantidadeNoiva + quantidadeNoivo + quantidadeAmbos
        self.lib.setQuantidadeConvidados(quantidadeConvidados)


    def visitLista_padrinhos(self, ctx:GramaticaParser.Lista_padrinhosContext):
        '''
        retorna quantidade de padrinhos
        '''
        return len(ctx.nome())


    def visitLista_madrinhas(self, ctx:GramaticaParser.Lista_madrinhasContext):
        '''
        retorna quantidade de madrinhas
        '''
        return len(ctx.nome())


    def visitListas_padrinhos(self, ctx:GramaticaParser.Listas_padrinhosContext):
        '''
        se houver, dispara erro 2 (quantidade de madrinhas e padrinhos diferente)
        '''
        qtdMadrinhas = self.visitLista_madrinhas(ctx.lista_madrinhas())
        qtdPadrinhos = self.visitLista_padrinhos(ctx.lista_padrinhos())
        
        if qtdMadrinhas != qtdPadrinhos:
            self.lib.adicionaErro("Erro: Quantidade de madrinhas e padrinhos diferentes entre si")


    def visitServico(self, ctx:GramaticaParser.ServicoContext):
        '''
        retorna dupla: valor do serviço (str) e modalidade do serviço (str)
        '''
        return ctx.valor().getText(), ctx.modalidade().getText()


    def visitServicos(self, ctx:GramaticaParser.ServicosContext):
        '''
        calcula o valor total do casamento
        '''
        valorTotal = 0
        quantidadeConvidados = self.lib.getQuantidadeConvidados()
        for sctx in ctx.servico():
            valorServico, modalidade = self.visitServico(sctx)
            if modalidade == 'TOTAL':
                multiplicador = 1
            else:
                multiplicador = quantidadeConvidados

            valorTotal += int(valorServico) * multiplicador
        
        self.lib.setValorCusto(valorTotal)


    def visitValor_disponivel(self, ctx:GramaticaParser.Valor_disponivelContext):
        '''
        dispara erro 3 (custo do casamento maior do que valor disponível)
        '''
        if int(ctx.valor().getText()) < self.lib.getValorCusto():
           self.lib.adicionaErro("Erro: Custo do casamento maior do que o valor disponível")
