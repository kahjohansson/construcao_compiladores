lexer grammar Gramatica;

PALAVRA_CHAVE: 'algoritmo' | 'declare' | 'fim_algoritmo' | 'leia' | 'escreva' | 'inteiro' | 'literal';

IDENT: LETRA(LETRA|NUM_INT)*;
CADEIA: '"' ~('\n' | '\t' | '\r' | '"')* '"';

NUM_INT: ('+'|'-')? ('0'..'9');


SIMBOLO: '(' | ')' | ',' | ':';
OPERADOR_MAT: '+';
OPERADOR_LOG: 'e' | 'ou' | 'nao' | '<>' | '=';
OPERADOR_OUTROS: '<-' | '^';

COMENTARIO: '{' ~('\n')* '}' -> skip;
ESPACO: (' ' | '\t' | '\r' | '\n' ) -> skip;

fragment
LETRA: 'a'..'z' | 'A'..'Z';
