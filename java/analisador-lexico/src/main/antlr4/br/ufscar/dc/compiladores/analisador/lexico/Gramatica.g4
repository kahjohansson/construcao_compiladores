lexer grammar Gramatica;

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

NUM_INT: NUMERO+;
NUM_REAL: NUMERO+ '.' NUMERO+;


SIMBOLO: '(' | ')' | ',' | ':' | '..' | '[' | ']';
OPERADOR_MAT: '+' | '-' | '/' | '*' | '%';
OPERADOR_OUTROS: '<-' | '<=' | '^' | '&' | '.';
OPERADOR_LOG: '<>' | '=' | '<' | '>' | '>=';

COMENTARIO: '{' ~('\n'|'}')* '}' -> skip;
ESPACO: (' ' | '\n' | '\r' | '\t' ) -> skip;


fragment
LETRA: 'a'..'z' | 'A'..'Z';
fragment
NUMERO: '0'..'9';
fragment
SUBLINHA: '_';


COMENTARIO_NAO_FECHADO: '{' ~('}')* '\n';
CADEIA_NAO_FECHADA: '"' ~('"')* '\n';
ERRO_GERAL: .;
