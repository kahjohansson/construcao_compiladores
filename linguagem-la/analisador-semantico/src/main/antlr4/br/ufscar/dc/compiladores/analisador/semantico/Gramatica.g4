grammar Gramatica;

/* 
REGRAS PARA O ANALISADOR LEXICO E SINTATICO SEGUNDO A GRAMATICA LA
*/

// ----------------------ANALISE LEXICA-----------------------------------------

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

NUM_REAL: NUMERO+ '.' NUMERO+;
NUM_INT: NUMERO+;

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


//---------------------ANALISE SINTATICA----------------------------------------

// REGRA SINTATICA PRINCIPAL
programa: declaracoes 'algoritmo' corpo 'fim_algoritmo';

// DECLARACOES
declaracoes: decl_local_global*;
decl_local_global: declaracao_local |declaracao_global;
declaracao_local: 'declare' variavel | 
                  'constante' IDENT ':' tipo_basico '=' valor_constante |
                  'tipo' IDENT ':' tipo;

// VARIAVEL E IDENTIFICADOR
variavel: identificador (',' identificador)* ':' tipo;
identificador: ident1=IDENT ( '.' identLista+=IDENT)* dimensao;

// DIMENSAO
dimensao: ('[' exp_aritmetica ']')*;

// REGRAS SOBRE TIPO E VALOR DE CONSTANTE
tipo: registro | tipo_estendido;
tipo_basico: 'literal' | 'inteiro' | 'real' | 'logico';
tipo_basico_ident: tipo_basico | IDENT;
tipo_estendido: ('^')? tipo_basico_ident;
valor_constante: CADEIA | NUM_INT | NUM_REAL | 'verdadeiro' | 'falso';
registro: 'registro' variavel* 'fim_registro';

// DECLARACAO GLOBAL
declaracao_global: procedimento='procedimento' IDENT '(' parametros? ')' declaracao_local* cmd* 'fim_procedimento'
		           | funcao='funcao' IDENT '(' parametros? ')' ':' tipo_estendido declaracao_local* cmd* 'fim_funcao' ;

// PARAMETROS
parametro: 'var'? identificador ( ',' identificador)* ':' tipo_estendido;
parametros: parametro (',' parametro)*;

// CORPO
corpo: declaracao_local* cmd*;

// COMANDOS
cmd: cmdLeia | cmdEscreva | cmdSe | cmdCaso | cmdPara | cmdEnquanto | cmdFaca | cmdAtribuicao | cmdChamada | cmdRetorne;
cmdLeia: 'leia' '(' '^'? identificador ( ',' '^'? identificador)* ')';
cmdEscreva: 'escreva' '(' exp1=expressao ( ',' expLista+=expressao)* ')';
cmdSe: 'se' expressao 'entao' cmdEntao+=cmd* ('senao' cmdSenao+=cmd*)? 'fim_se';
cmdCaso: 'caso' exp_aritmetica 'seja' selecao ('senao' cmd*)? 'fim_caso';
cmdPara: 'para' IDENT '<-' exp_aritmetica 'ate' exp_aritmetica 'faca' cmd* 'fim_para';
cmdEnquanto: 'enquanto' expressao 'faca' cmd* 'fim_enquanto';
cmdFaca: 'faca' cmd* 'ate' expressao ;
cmdAtribuicao: simbolo='^'? identificador '<-' expressao;
cmdChamada: IDENT '(' expressao (',' expressao)* ')';
cmdRetorne: 'retorne' expressao;

// SELECAO
selecao: item_selecao*;
item_selecao: constantes ':' cmd*;

// EXPRESSOES E OPERADORES
constantes: limiteEsq=numero_intervalo ('..' limiteDireita+=numero_intervalo)*;
numero_intervalo: op_unario? NUM_INT ('. .' op_unario? NUM_INT)?;
op_unario: '-';
exp_aritmetica: termo (op1 termo)*;
termo: fator (op2 fator)*;
fator: parcela (op3 parcela)*;
op1: '+' | '-';
op2: '*' | '/';
op3: '%';

// PARCELA
parcela: op_unario? parcela_unario | parcela_nao_unario;
parcela_unario: '^'? identificador |
                ident=IDENT '(' expressao (',' expressao)? ')' |
                NUM_INT |
                NUM_REAL |
                '(' expParenteses=expressao ')';
parcela_nao_unario: '&' identificador | CADEIA;

// EXPRESSOES E OPERADORES RELACIONAIS
exp_relacional: exp_aritmetica (op_relacional exp_aritmetica)?;
op_relacional: igual='=' | '<>' | '>=' | '<=' | '>' | '<';
expressao: termo_logico (op_logico_1 termo_logico)*;
termo_logico: fator_logico (op_logico_2 fator_logico)*;
fator_logico: nao='nao'? parcela_logica;
parcela_logica: ( 'verdadeiro' | 'falso' ) | exp_relacional;
op_logico_1: 'ou';
op_logico_2: 'e';
