# Compilador de lista de detalhes de casamento

Compilador de lista de lista de detalhes de casamento e gerador de página HTML.

A lista contém convidados, padrinhos, valor disponível e serviços contratados e seus valores.

O compilador realiza análise léxica e sintática seguindo a [gramática](Gramatica.g4) e a análise semântica segue 3 regras:
- O número de convidados de um lado não pode ser 20% maior que do outro;
- O número de padrinhos do sexo masculino e feminino têm que ser igual e têm que ser par;
- O custo total da festa tem que ser menor que o valor disponível.


Implementado na linguagem Python utilizando a biblioteca ANTLR4.

## Dependências
- [ANTLR4](https://www.antlr.org/download.html) 
  - [(Tutorial de instalação do ANTLR4)](https://github.com/antlr/antlr4/blob/master/doc/getting-started.md)

### Como compilar a gramática

Para gerar os arquivos específicos do ANTLR4:

```
cd construcao_compiladores/lista-casamento/
antl4 Gramatica.g4 -Dlanguage=Python3 -visitor 
```

### Como rodar o programa

```
python3 main.py <arquivo-entrada> <arquivo-saida>
```

### Casos de teste

Os casos de teste estão disponíveis em [`casos-de-teste`](lista-casamento/casos-de-teste).

Em [`casos-de-teste/entrada`](lista-casamento/casos-de-teste/entrada), estão os arquivos de entrada.

Em [`casos-de-teste/saida`](lista-casamento/casos-de-teste/saida), estão os arquivos de saída.

Estão inclusos casos de teste sem erros, que geram uma página HTML, e também casos com os erros semânticos, que geram arquivo de erro. 
