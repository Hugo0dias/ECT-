grammar Calculator;

program: stat* EOF;
stat: expr? NEWLINE ;           

expr returns [Double res]: 
    expr op=('*'|'/'|'%') expr   #ExprNultDivMod
|   expr op=('+'|'-') expr        #ExprAddSub
|    op=('+'|'-') e2 = expr            #UnaryInteger
|   Integer                         #ExprInteger
|   '(' expr ')'                    #ExprParent
;

Integer : [0-9]+;
NEWLINE : '\r'? '\n';
WS : [ \t]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;