lexer grammar LexerDecaf;

@lexer::header{
  package compiler.scanner;
}

WHITESPACE  	:   ( '\t' | ' ' | '\r' | '\n')+ { skip(); } ;
COMMENT     	:   '//' ~['\n'] '\n' { skip(); } ;

// FRAGMENTS ;)
fragment DIGIT	: [0-9];
fragment CHAR	: [\u0020-\u0073];

// KEYWORDS
BOOLEAN 		:	'boolean';
BREAK			:	'break';
CALLOUT			:	'callout';
CLASS 			:   'class';
CONTINUE 		:	'continue';
ELSE			:	'else';
BOOL_LITERAL	:   'true' | 'false';
FOR 			:	'for';
IF 			    :   'if';
INT 			:   'int';
RETURN 		 	:   'return';
VOID			:	'void';
CLASS_PROGRAM   :   CLASS 'Program';

// MISC
ID		    	:   [a-zA-Z_]+ [a-zA-Z0-9]*;
O_BRACE         :   '{';
C_BRACE         :   '}';
O_PAR 			: 	'(';
C_PAR 			: 	')';
O_BRACKET		:	'[';
C_BRACKET		:	']';
END_LINE		: 	';';
COMA  			: 	',';
RETURN_TYPE		: 	INT | BOOLEAN | VOID;

// LITERALS
ESCAPED_CHAR	:	'\\n' | '\\\"' | '\\\'' | '\\\\' | '\\t' ;
STRING_LITERAL  :   '"' ( CHAR | ESCAPED_CHAR )* '"';
CHAR_LITERAL    :   '\'' ( CHAR | ESCAPED_CHAR )? '\'';
INT_UNSIGNED	:	DIGIT+;
INT_SIGNED     	:   '-'? INT_UNSIGNED;
HEX_LITERAL     :   '0x' [0-9A-Fa-f]+;

// ARITHMETIC OPERATORS
ADD 			: 	'+';
SUB 			: 	'-';
MULT 			: 	'*';
DIV 			: 	'/';
MOD				: 	'%';
ASIGN  			: 	'=';
ADD_ASSIGN		:	'+=';
SUB_ASSIGN		:	'-=';

// LOGICAL OPERATOR
AND  			: 	'&';
OR  			: 	'|';
EQUAL			: 	'==';
NEGATION		: 	'!';
NOT_EQUAL		:	'!=';
SC_AMP			: 	'&&';
SC_PIPE			:	'||';

// ARITHMETIC-LOGIC OPERATOR
GREAT_THAN			:	'>';
GREAT_EQUAL_THAN	:	'>=';
LESS_THAN			:	'<';
LESS_EQUAL_THAN		:	'<=';
