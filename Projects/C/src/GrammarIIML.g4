grammar GrammarIIML;

program: statement* EOF;

statement:
    imageDeclaration
    | placeFigure
    | numberDeclaration
    | listDeclaration
    | forLoop
    | assignment
    ;

numberDeclaration:
    NUMBER_TYPE ID IS expr
    ;

listDeclaration:
    LIST (OF LIST)* OF baseType ID IS expr
    ;

baseType: NUMBER_TYPE;  // Caso seja necessário expandir para outros tipos de dados

forLoop:
    FOR LIST ID WITHIN ID statement
    ;

imageDeclaration:
    IMAGE SIZE imageSize BACKGROUND expr
    ;

imageSize:
    expr BY expr |
    expr
    ; // 1) for different width and height, 2) for single dimension

placeFigure:
    PLACE figureType figureSize AT location WITH INTENSITY expr
    ;

figureType:
    CIRCLE
    | RECT
    | CROSS
    | PLUS
    ;

figureSize:
    RADIUS expr                             # circleFigSize
    | WIDTH expr HEIGHT expr                # otherFigSize
    ;

location:
    expr expr;  // x y coordinates

expr
    : <assoc=left> expr op=('*' | '/') expr # multiplicationExpr
    | <assoc=left> expr op=('+' | '-') expr # additionExpr
    | <assoc=right> op=('-' | '+') expr     # unaryExpr
    | term                                  # termExpr
    ;

term
    : primary                               # primaryTerm
    | term LBRACKET expr RBRACKET           # listElementAccessTerm
    ;

primary
    : NUMBER_TYPE '(' expr ')'              # typeConversionPrimary
    | '(' expr ')'                          # parenthesisPrimary
    | listLiteral                           # listLiteralPrimary
    | ID                                    # variablePrimary
    | NUMBER                                # numberPrimary
    | READ STRING                           # readStringPrimary
    ;

listLiteral
    : LBRACKET (expr (COMMA expr)*)? RBRACKET
    ;

assignment:
    ID IS expr
    ;

IMAGE       : 'image';
SIZE        : 'size';
BY          : 'by';
BACKGROUND  : 'background';
PLACE       : 'place';
CIRCLE      : 'circle';
RECT        : 'rect';
CROSS       : 'cross';
PLUS        : 'plus';
RADIUS      : 'radius';
WIDTH       : 'width';
HEIGHT      : 'height';
AT          : 'at';
WITH        : 'with';
INTENSITY   : 'intensity';
NUMBER_TYPE : 'number';
IS          : 'is';
READ        : 'read';
LIST        : 'list';
OF          : 'of';
FOR         : 'for';
WITHIN      : 'within';

LBRACKET    : '[';
RBRACKET    : ']';
COMMA       : ',';

ID          : [a-zA-Z_] [a-zA-Z0-9_]*; 
NUMBER      : [0-9]+ ('.' [0-9]+)? ;
STRING      : '"' ( ~["\\] | '\\' . )* '"' ;


WS          : [ \t\r\n]+ -> skip;
COMMENT     : '//' ~[\r\n]* -> skip;