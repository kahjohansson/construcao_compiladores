/* 
regras para o analisador lexico segundo a gramatica LA 
*/
lexer grammar Gramatica;

/* 
BLOCO DE PADRÕES DE LEXEMAS FORMADOS POR LETRAS OU QUE PODEM CONTER LETRAS
- palavras-chave (PALAVRA_CHAVE): são palavras reservadas, e por isso, são 
    listadas primeiro para que não sejam confundidas com identificadores
- operadores lógicos por escrito (OPERADOR_LOG_PALAVRA): palavras reservadas que 
representam operadores lógicos
- identificadores (IDENT): são lexemas formados iniciados por letra ou sublinha e que
    podem ser seguidos por letra, algarismo ou sublinha
- cadeia (CADEIA): lexemas envoltos por aspas duplas que não possuem quebra de 
    linha, tabulação
 */
PALAVRA_CHAVE: 'algoritmo' | 'declare' | 'fim_algoritmo' | 'leia' | 'escreva' | 
                'inteiro' | 'literal' | 'real' | 'logico' | 'se'| 'fim_se' |
                'entao' | 'senao' | 'caso' | 'fim_caso' | 'seja' | 'para' |
                'fim_para' | 'ate' | 'faca' | 'enquanto' | 'fim_enquanto' |
                'registro' | 'fim_registro' | 'tipo' | 'procedimento' |
                'fim_procedimento' | 'var' | 'funcao' | 'fim_funcao' | 
                'retorne' | 'constante'| 'falso' | 'verdadeiro';
OPERADOR_LOG_PALAVRA: 'e' | 'ou' | 'nao';
IDENT: (LETRA|SUBLINHA)(LETRA|NUM_INT|SUBLINHA)*;
CADEIA: '"' ~('\n' | '\t' | '\r' | '"')* '"';

/*
BLOCO DE PADRÕES DE LEXEMAS FORMADOS POR ALGARISMOS NUMÉRICOS
- número inteiro (NUM_INT): números inteiros positivos sem sinal
- número real (NUM_REAL): números reais positivos com pelo menos uma casa
    decimal sem sinal
*/
NUM_INT: NUMERO+;
NUM_REAL: NUMERO+ '.' NUMERO+;

/*
BLOCO DE PADRÕES DE LEXEMAS FORMADOS POR SÍMBOLOS DIVERSOS OU OPERADORES
    DESCRITOS NA LINGUAGEM LA
*/
SIMBOLO: '(' | ')' | ',' | ':' | '..' | '[' | ']';
OPERADOR_MAT: '+' | '-' | '/' | '*' | '%';
OPERADOR_OUTROS: '<-' | '<=' | '^' | '&' | '.';
OPERADOR_LOG: '<>' | '=' | '<' | '>' | '>=';

/* 
BLOCO DE PADRÕES DE LEXEMAS IGNORADOS PELA LINGUAGEM
- comentário (COMENTARIO): lexemas envoltos por chaves que devem ser ignorados
    pelo analisador léxico
- espaços em branco (ESPACO): espaço em branco, quebra de linha, retorno e
    tabulação, que devem ser ignorados pelo analisador léxico
*/
COMENTARIO: '{' ~('\n'|'}')* '}' -> skip;
ESPACO: (' ' | '\n' | '\r' | '\t' ) -> skip;

/*
REGRAS AUXILIARES PARA OS PADRÕES
*/
fragment
LETRA: 'a'..'z' | 'A'..'Z';
fragment
NUMERO: '0'..'9';
fragment
SUBLINHA: '_';

/* BLOCO DE ERROS DA LINGUAGEM LA
- comentários e cadeias não fechadas na mesma linha (COMENTARIO_NAO_FECHADO e 
    CADEIA_NAO_FECHADA) devem ser relatados como erro
- demais erros são englobados pela regra curinga ERRO_GERAL
*/
COMENTARIO_NAO_FECHADO: '{' ~('}')* '\n';
CADEIA_NAO_FECHADA: '"' ~('"')* '\n';
ERRO_GERAL: .;
