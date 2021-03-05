grammar Grammar;

//raiz da árvore
prog: ident* palavra_chave* ident* NUM_REAL EOF;

//palavras-chave
palavra_chave: SAIDA | DECIDE | ALGORITMO;
ALGORITMO: 'algoritmo';
SAIDA: 'saida';
DECIDE: 'decide';

//indentificador
ident: IDENT;
IDENT: [a-zA-Z_][a-zA-Z_0-9]* ;

NUM_REAL: [0-9]+[.][0-9]+ ;

//espaços em branco
WHITE_SPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
