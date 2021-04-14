class AnalisadorSemanticoLib:

    def __init__(self):
        self.erros = []

    def getErros(self):
        return self.erros


    def adicionaErro(self, erro):
        self.erros.append(erro)


    def setQuantidadeConvidados(self, quantidade):
        self.quantidadeConvidados = quantidade


    def getQuantidadeConvidados(self):
        return self.quantidadeConvidados


    def setValorCusto(self, valor):
        self.valorCusto = valor


    def getValorCusto(self):
        return self.valorCusto
        