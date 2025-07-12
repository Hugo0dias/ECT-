grammar CSV;

file: cabecalho row* EOF;

cabecalho: cvalue(','cvalue)* NEWLINE;
row: rvalue(','rvalue)*;

cvalue: String;
rvalue: Number | String;

Number: [0-9]+('.'[0-9]+)?;
String: [a-zA-Z0-9._]+;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;
