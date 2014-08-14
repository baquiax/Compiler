lexer grammar DecafScanner;

@lexer::header{
  package compiler.scanner;
}

CLASS   		:   'class';
CLASS_PROGRAM   :   CLASS 'Program';
COMMENT     	:   \/\/ ~[\n] \n { skip(); } ;
ID		    	:   [a-zA-Z_] [a-zA-Z0-9]*;
STRING_LITERAL  :   '"' .* '"';
CHAR_LITERAL    :   '\'' .? '\'';
INT_POSITIVE	:	[0-9]+;
INT_LITERAL     :   '-'? INT_POSITIVE;
HEX_LITERAL     :   '0x' [0-9A-Za-z]+;
BOOL_LITERAL    :   'true' | 'false';
DATA_TYPE       :   'boolean' | 'int';
IF 			    :   'if';
FOR 			:	'for';
RETURN 		 	:   'return';
BREAK 			:	'break';
CONTINUE		:	'continue';
CALLOUT 		:	'callout';
O_BRACE         :   '{';
C_BRACE         :   '}';
PLUS 			: 	'+';
MINUS 			: 	'-';
MULT 			: 	'*';
DIV  			: 	'/';
ASIGN  			: 	'=';
COMA  			: 	','; 
END_LINE		: 	';';
O_PAR 			: 	'(';
C_PAR 			: 	')';
PLUS_EQ			:	'+=';
MINUS_EQ		:	'-=';
AMP  			: 	'&';
PIPE  			: 	'|';
MOD				: 	'%';
G_THAN			:	'>';
G_EQUAL_THAN	:	'>=';
L_THAN			:	'<';
L_EQUAL_THAN	:	'<=';
SC_AMP			: 	'&&';
SC_PIPE			:	'||';
EQUAL			: 	'==';
NEGATION		: 	'!';
OS_BRACE		:	'[';
CS_BRACE		:	']';
VOID			:	'void';
RETURN_TYPE		: 	DATA_TYPE | VOID

WHITESPACE  	:   ( '\t' | ' ' | '\r' | '\n')+ { skip(); } ;