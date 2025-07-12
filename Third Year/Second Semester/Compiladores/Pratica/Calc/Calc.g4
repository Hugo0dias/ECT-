grammar Calc;

@parser::header {
import java.util.Map;
import java.util.HashMap;
}

@parser::members {
static protected Map<String,Symbol> symbolTable = new HashMap<>();
}

main: statList EOF;

statList: (stat? ';')*;

stat: show
    | declaration
    | assignment
    | conditional
    ;

show: 'show' expr;

declaration: idList ':' type;

assignment: expr '->' ID;

conditional: 'if' expr 'then' trueSL=statList ('else' falseSL=statList)? 'end';

idList:
    ID (',' ID)*;

type returns[Type res]:
     'integer' {$res = new IntegerType();}
   | 'real'    {$res = new RealType();}
   | 'boolean' {$res = new BooleanType();}
   ;

expr returns[Type eType,String varName]:
      sign=('+'|'-') e=expr                         #ExprUnary
    | <assoc=right> e1=expr op='^' e2=expr          #ExprBinary
    | e1=expr op=('*'| '/' | '//' | '\\\\') e2=expr #ExprBinary
    | e1=expr op=('+' | '-') e2=expr                #ExprBinary
    | e1=expr op=('=' | '/=') e2=expr               #ExprBinary
    | '(' e=expr ')'                                #ExprParenthesis
    | REAL                                          #ExprReal
    | INTEGER                                       #ExprInteger
    | BOOLEAN                                       #ExprBoolean
    | ID                                            #ExprId
    ;

BOOLEAN: 'true' | 'false';
ID: [a-zA-Z_][a-zA-Z_0-9]*;
REAL: [0-9]+ '.' [0-9]*;
INTEGER: [0-9]+;
WS: [ \t\r\n]+ -> skip;
LINE_COMMENT: '#' .*? '\n';
ERROR: .;

