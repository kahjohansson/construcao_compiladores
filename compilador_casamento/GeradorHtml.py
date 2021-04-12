from GramaticaVisitor import GramaticaVisitor
from GramaticaParser import GramaticaParser

from GeradorHtmlLib import formatName


class GeradorHtml(GramaticaVisitor):

    def __init__(self):
        self.saida = []
        self.quantidadeConvidados = 0
        self.valorTotal = 0

    def visitCasamento(self, ctx: GramaticaParser.CasamentoContext):

        # cabeçalho
        self.saida.append("<html>\n")
        self.saida.append("<head>\n")
        self.saida.append("<meta charset='utf-8'>\n")
        self.saida.append(
            "<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>\n")
        self.saida.append("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>\n")
        self.saida.append("</head>\n")
        # corpo
        self.saida.append("<body>\n")
        # div do titulo
        self.saida.append("<div class='jumbotron'>\n")
        self.saida.append("<div class='container'>\n")
        self.saida.append("<h1 class='display-6 text-center'>Casamento ")
        self.visitNome_noiva(ctx.nome_noiva())
        self.saida.append(" & ")
        self.visitNome_noivo(ctx.nome_noivo())
        self.saida.append("</h1>\n")
        self.saida.append("</div>\n")
        self.saida.append("</div>\n")
        # div do corpo
        self.saida.append("<div class='container pb-5'>\n")
        self.saida.append("<h2>Lista de convidados</h2>\n")
        self.visitListas_convidados(ctx.listas_convidados())
        self.saida.append("<hr class='my-4'>\n")
        self.saida.append("<h2>Madrinhas</h2>\n")
        self.visitLista_madrinhas(ctx.listas_padrinhos().lista_madrinhas())
        self.saida.append("<hr class='my-4'>\n")
        self.saida.append("<h2>Padrinhos</h2>\n")
        self.visitLista_padrinhos(ctx.listas_padrinhos().lista_padrinhos())
        self.saida.append("<hr class='my-4'>\n")
        self.saida.append("<h2>Serviços Contratados</h2>\n")
        self.visitServicos(ctx.servicos())
        self.saida.append("<hr class='my-4'>\n")
        self.saida.append("<h2>Orçamento</h2>\n")
        self.saida.append("<table class='table'>\n")
        self.saida.append("<tr>\n")
        self.saida.append("<td>Custo do casamento</td>\n")
        self.saida.append(f"<td>R$ {int(self.valorTotal) if int(self.valorTotal) == self.valorTotal else self.valorTotal}</td>\n")
        self.saida.append("</tr>\n")
        self.saida.append("<tr>\n")
        self.saida.append("<td>Valor disponível</td>\n")
        self.saida.append(f"<td>R$ {ctx.valor_disponivel().valor().getText()}</td>\n")
        self.saida.append("</tr>\n")
        self.saida.append("</table>\n")
        self.saida.append("</div>\n")
        self.saida.append("</body>\n")
        self.saida.append("</html>")

    def visitNome_noiva(self, ctx: GramaticaParser.Nome_noivaContext):
        self.saida.append(formatName(ctx.nome().getText()))

    def visitNome_noivo(self, ctx: GramaticaParser.Nome_noivoContext):
        self.saida.append(formatName(ctx.nome().getText()))

    def visitLista_convidados(self, ctx: GramaticaParser.Lista_convidadosContext):
        contador = 0
        for nome in ctx.nome():
            self.saida.append(f"{formatName(nome.getText())}<br>\n")
            contador += 1
        self.quantidadeConvidados += contador

    def visitLista_madrinhas(self, ctx: GramaticaParser.Lista_madrinhasContext):
        for nome in ctx.nome():
            self.saida.append(f"{formatName(nome.getText())}<br>\n")

    def visitLista_padrinhos(self, ctx: GramaticaParser.Lista_padrinhosContext):
        for nome in ctx.nome():
            self.saida.append(f"{formatName(nome.getText())}<br>\n")

    def visitServicos(self, ctx: GramaticaParser.ServicosContext):
        self.saida.append("<table class='table'>\n")
        self.saida.append("<tr>\n")
        self.saida.append("<th>Serviço</th>\n")
        self.saida.append("<th>Valor</th>\n")
        self.saida.append("</tr>\n")
        for servico in ctx.servico():
            self.visitServico(servico)
        self.saida.append("</table>\n")

    def visitServico(self, ctx: GramaticaParser.ServicoContext):
        self.saida.append("<tr>\n")
        self.saida.append(
            f"<td>{formatName(ctx.nome_servico().getText())}</td>\n")
        if ctx.modalidade().getText() == 'POR PESSOA':
            valor = float(ctx.valor().getText())*self.quantidadeConvidados
            self.saida.append(f"<td>R$ {formatName(str(int(valor) if int(valor) == valor else valor))}</td>\n")
        else:
            valor = float(ctx.valor().getText())
            self.saida.append(
                f"<td>R$ {formatName(ctx.valor().getText())}</td>\n")
        self.saida.append("</tr>\n")
        self.valorTotal += valor
