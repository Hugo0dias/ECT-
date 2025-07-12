grammar Calculator;

program: stat* EOF;
stat: (expr | assignment)? NEWLINE ; 

assignment: Var  '=' expr;

expr: 
    op=('+'|'-') e2 = expr            #UnaryInteger
|   expr op=('*'|'/'|'%') expr   #ExprNultDivMod
|   expr op=('+'|'-') expr        #ExprAddSub
|   Var                             #VarExpr
|   Integer                         #ExprInteger
|   '(' expr ')'                    #ExprParent
;

Integer : [0-9]+;
Var: [a-zA-Z_][a-zA-Z_0-9]*;
NEWLINE : '\r'? '\n';
WS : [ \t]+ -> skip;
COMMENT: '#' .*? '\n' -> skip;