lexer grammar LexerDecaf;

@lexer::header{
  package compiler.scanner;
}

WHITESPACE  	:   ( '\t' | ' ' | '\r' | '\n')+ { skip(); } ;
COMMENT     	:   '//' ~['\n'] '\n' { skip(); } ;

/**
 * TODO
 * En el ACTION de cada regla agregaré algo como
 * this.listOfTokens.put("<LINE>", {<TEXT>, <TYPE_TOKEN>})
 */

// FRAGMENTS ;)
fragment DIGIT	: [0-9];
fragment CHAR	: [\u0020-\u007F];

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
ID		    	:   [a-zA-Z_]+ [a-zA-Z0-9_]*;


//ERRORES
WHITHOUT_S_QUOTE    :   '\'' ~['\''] '\n';
WHITHOUT_D_QUOTE    :   '\"' ~['\"'] '\n';
MULTIPLE_DOT        :   DOT(DOT)+;
MULTIPLE_COMMA      :   COMA(COMA)+;
MULTIPLE_EOL        :   EOL(EOL)+;
INVALID_ID          :   [0-9]+ [a-zA-Z0-9_]*;
INVALID_ID_CHAR     :   ([a-zA-Z_] ~[CHAR]+ [a-zA-Z0-9_]*)+;

// MISC
O_BRACE         :   '{';
C_BRACE         :   '}';
O_PAR 			: 	'(';
C_PAR 			: 	')';
O_BRACKET		:	'[';
C_BRACKET		:	']';
EOL 	    	: 	';';
COMA  			: 	',';
RETURN_TYPE		: 	INT | BOOLEAN | VOID;
DOT             :   '.';



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


//LAST CATCH ERRORS
INVALID_CHAR        :   ~[CHAR];