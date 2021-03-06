lexer grammar Gramatica;


ALGORITMO: 'algoritmo';
DECLARE: 'declare';
FIM_ALGORITMO: 'fim_algoritmo';
LEIA: 'leia';
ESCREVA: 'escreva';

IDENT: LETRA(LETRA|NUM_INT)*;
NUM_INT: ('+'|'-')? ('0'..'9');


fragment
LETRA: 'a'..'z' | 'A'..'Z';