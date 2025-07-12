grammar Calc;

program: stat* EOF;

stat: expression #StatExpression;

expression returns[String varName]:
     expression op=('*'|'/') expression # ExprOp
   | expression op=('+'|'-') expression # ExprOp
   | '(' expression ')'                 # ExprPar
   | Number                             # ExprNumber
   ;

Number: [0-9]+;
WS: [ \t\r\n]+ -> skip;
ERROR: .;

