# Analisador semântico para a Gramática LA e gerador de código para a linguagem de programação C

Autores:
- Camila Manara Ribeiro
- Karina Mayumi Johansson

## Dependências
- [ANTLR4](https://www.antlr.org/download.html)
- [Maven](https://maven.apache.org/download.cgi)

## Como compilar o projeto

Clone o projeto. Utilizando a IDE Netbeans, abra o projeto `analisador-semantico` e construa o projeto. Será gerado o arquivo executável `target/analisador-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar`.

## Para rodar o projeto

Neste repositório, está disponível o arquivo executável `target/analisador-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar`, que também pode ser gerado seguindo as instruções da seção anterior.

Para rodar o projeto, estando na raiz do projeto (`analisador-semantico`), execute como apresentado a seguir:
```
java -jar target/analisador-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar <arquivo-entrada> <arquivo-saida>
```
