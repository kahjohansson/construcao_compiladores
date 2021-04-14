# Compilador de lista de detalhes de casamento

Compilador de lista de lista de detalhes de casamento e gerador de página HTML

Implementado na linguagem Python utilizando a biblioteca ANTLR4

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
