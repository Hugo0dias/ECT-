grammar numbers;

main: stat* EOF;
stat: assignment;

assignment: NUM '-' TEXT;

NUM : [0-9]+ ;
TEXT : [a-z]+ ;
WS : [ \t\r\n]+ -> skip;