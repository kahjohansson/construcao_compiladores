grammar Gramatica;

CADEIA: '"' (LETRA_MINUSCULA | LETRA_MAIUSCULA | ' ')* '"';

LETRA_MINUSCULA: [a-z];

LETRA_MAIUSCULA: [A-Z];

ALGARISMO: [0-9];

SKIP_WS: [ \t\r\n]+ -> skip;

SIMBOLOS: ',';

ERRO_GERAL: .;

casamento: 'CASAMENTO' nome_noiva nome_noivo listas_convidados listas_padrinhos servicos valor_disponivel <EOF>;

nome_noiva: 'NOME' 'NOIVA' nome;
nome_noivo: 'NOME' 'NOIVO' nome; 
listas_convidados: lista_convidados+;
lista_convidados: 'CONVIDADOS' lado nome (',' nome)* ;
nome: CADEIA;
lado: 'NOIVA' | 'NOIVO' | 'AMBOS';

listas_padrinhos: lista_madrinhas lista_padrinhos;
lista_madrinhas: 'MADRINHAS' nome (',' nome)* ;
lista_padrinhos: 'PADRINHOS' nome (',' nome)* ;

servicos: servico+;
servico: 'SERVICO' nome_servico modalidade valor;
modalidade: 'POR PESSOA' | 'TOTAL';
nome_servico: CADEIA;
valor_disponivel: 'VALOR DISPONIVEL' valor;
valor: ALGARISMO+ ('.' ALGARISMO ALGARISMO)?;