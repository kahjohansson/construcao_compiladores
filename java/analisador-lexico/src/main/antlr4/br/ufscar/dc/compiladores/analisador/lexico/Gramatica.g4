lexer grammar Gramatica;

PALAVRA_CHAVE: 'algoritmo' | 'declare' | 'fim_algoritmo' | 'leia' | 'escreva' | 
                'inteiro' | 'literal' | 'real' | 'logico' | 'se'| 'fim_se' |
                'entao' | 'senao' | 'caso' | 'fim_caso' | 'seja';

OPERADOR_LOG_PALAVRA: 'e' | 'ou' | 'nao';

IDENT: LETRA(LETRA|NUM_INT)*;
CADEIA: '"' ~('\n' | '\t' | '\r' | '"')* '"';

NUM_INT: ('+'|'-')? NUMERO+;
NUM_REAL: ('+'|'-')? NUMERO+ '.' NUMERO+;


SIMBOLO: '(' | ')' | ',' | ':' | '..';
OPERADOR_MAT: '+' | '-' | '/' | '*';
OPERADOR_OUTROS: '<-' | '<=' | '^';
OPERADOR_LOG: '<>' | '=' | '<' | '>' | '>=';

COMENTARIO: '{' ~('\n')* '}' -> skip;
ESPACO: (' ' | '\t' | '\r' | '\n' ) -> skip;

fragment
LETRA: 'a'..'z' | 'A'..'Z';
fragment
NUMERO: '0'..'9';
