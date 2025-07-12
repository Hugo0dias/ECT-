grammar GrammarIML;

program 
    : statement* EOF
    ;

statement 
    : listDecl
    | varDecl
    | assignment
    | imageIO
    | readStmt
    | morphStmt
    | drawStmt
    | runSecondary
    | outputStmt
    | ifStmt
    | forStmt
    | untilStmt
    | listOperation
    ;

varDecl returns [Type type]
    : typeSpec ID IS expr
    ;

listDecl 
    : 'list' 'of' typeSpec ID IS listInitializer
    ;

typeSpec returns [Type type]
    : TYPE                            # declareType
    | 'list' 'of' typeSpec            # listTypeSpec
    ;

listInitializer 
    : '[' ']'                         # emptyList
    | '[' expr (',' expr)* ']'        # populatedList
    ;

assignment 
    : ID IS expr
    ;

imageIO 
    : TYPE ID IS LOAD FROM (STRING | readStmt)     # loadImage
    | ID STORE INTO STRING                         # storeImage
    ;

readStmt returns [Type type]
    : READ STRING
    ;

morphStmt 
    : ID IS morphOp BY expr              # directMorph
    ;

drawStmt 
    : DRAW ID                        # drawImage
    | DRAW FIGURE '(' args? ')'      # drawFigure
    ;

runSecondary 
    : TYPE ID IS RUN FROM readStmt
    ;

outputStmt 
    : OUTPUT expr
    ;

ifStmt 
    : IF expr THEN statement+ (ELSE statement+)? DONE
    ;

forStmt 
    : FOR TYPE ID WITHIN expr DO statement+ DONE
    ;

untilStmt
    : UNTIL expr DO statement+ DONE
    ;

listOperation 
    : ID APPEND expr                 # listAppend
    | ID REMOVE expr                # listRemove
    ;

expr returns [Type type]
    : '(' expr ')'                                  # parenExpr
    | '(' morphOperation ')'                        # morphOperationParenExpr
    | listInitializer                               # listExpr
    | expr '[' expr ']'                             # listIndexExpr
    | TYPE '(' expr ')'                             # typeConversionExpr
    | 'not' expr                                    # notExpr
    | 'any' 'pixel' ID pixelComparison              # anyPixelExpr
    | 'all' 'pixel' ID pixelComparison              # allPixelExpr
    | 'count' 'pixel' expr 'in' ID                  # countPixelExpr
    | op='.-' expr                                   # unaryOperation        
    | op=('-' | '+' | '|') expr                     # flipOperation        
    | expr op=('.*' | './') expr                    # pixelMulDiv  
    | expr op=('.+' | '.-') expr                    # pixelAddSub                      
    | expr op=('*' | '/') expr                      # multDivArithmetic      
    | expr op=('+' | '-') expr                      # addSubArithmetic      
    | expr op=('+*' | '-*' | '|*') expr             # scalingOperation  
    | expr morphOp BY expr                          # morphChainExpr   
    | expr op=('<' | '>' | '<=' | '>=' | '==' | '!=') expr # comparison
    | expr op=('and' | 'or') expr                   # logicalOperation
    | propertyAccess                                # propertyExpr
    | ID                                            # idExpr
    | NUMBER                                        # numberExpr
    | PERCENT                                       # percentExpr
    | STRING                                        # stringExpr
    | BOOLEAN                                       # booleanExpr
    | readStmt                                      # readExpr
    ;

pixelComparison 
    : ('.>' | '.<' | '.==' | '.!=' | '.<=' | '.>=') expr
    ;

propertyAccess returns [Type type]
    : ( 'columns' | 'rows' | 'length' ) OF ID
    ;

args
    : expr (',' expr)*
    ;

morphOp 
    : 'erode' | 'dilate' | 'open' | 'close' | 'top hat' | 'black hat'
    ;

morphOperation
    : expr IS morphOp BY expr
    ;

TYPE        : 'image' | 'string' | 'number' | 'percentage' | 'boolean';
FIGURE      : 'circle' | 'line' | 'rectangle' | 'cross' | 'plus';
DRAW        : 'draw';
LOAD        : 'load';
STORE       : 'store';
FROM        : 'from';
INTO        : 'into';
IS          : 'is';
READ        : 'read';
RUN         : 'run';
BY          : 'by';
OUTPUT      : 'output';
IF          : 'if';
THEN        : 'then';
ELSE        : 'else';
DONE        : 'done';
OF          : 'of';
FOR         : 'for';
WITHIN      : 'within';
DO          : 'do';
UNTIL       : 'until';
APPEND      : 'append';
REMOVE      : 'remove';

BOOLEAN     : 'true' | 'false';
ID          : [a-zA-Z_][a-zA-Z0-9_]*;
NUMBER      : [0-9]+ ('.' [0-9]+)?;
PERCENT     : [0-9]+ '%';
STRING      : '"' (~["\r\n])* '"';

WS          : [ \t\r\n]+ -> skip;
COMMENT     : '//' ~[\r\n]* -> skip;