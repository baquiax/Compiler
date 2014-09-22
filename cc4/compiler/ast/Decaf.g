grammar Decaf;

@header {
	package compiler.ast;
}

start			: 	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE EOF
					# program;

field_decl    	: 	type field_deriv EOL
					# fieldDecl;

field_deriv		: 	id
					# oneId
				|	id O_BRACKET int_literal C_BRACKET
					# array
				| 	field_deriv COMMA field_deriv
					# multipleVarDecl;

method_decl		:	return_type_method id O_PAR method_param C_PAR block
					# methodDecl;

return_type_method	:	type | VOID;

method_param	: 	list_method_param 	|	;

list_method_param	:	type id
					|	list_method_param COMMA list_method_param;

block			:	O_BRACE var_decl* statement* C_BRACE
					# blockDef;

var_decl		:	type var_deriv EOL
					# varDecl;

var_deriv		:	id
				|	var_deriv COMMA	 var_deriv;

type			:	INT
					# typeInt
				|	BOOLEAN
					# typeBoolean;

statement		: 	location assign_op expr EOL
					# locationAsign
				|	method_call EOL
					# callMethod
				|	IF O_PAR expr C_PAR block if_else?
					# if
				|	FOR id ASSIGN expr COMMA expr block
					# for
				|	RETURN expr? EOL
					# return
				|	BREAK EOL
					# brek
				|	CONTINUE EOL
					# continue
				|	block
					# subBlock;

if_else			:	ELSE block;

assign_op		:	ASSIGN
				|	ADD_ASSIGN 
				|	SUB_ASSIGN;

method_call		:	method_name O_PAR expr_deriv? C_PAR
					# methodCall
				|	CALLOUT O_PAR string_literal callout_deriv? C_PAR
					# calloutCall;

expr_deriv		:	expr
				|	expr COMMA expr_deriv;

callout_deriv	:	COMMA callout_arg_list;

callout_arg_list	:	callout_arg
					|	callout_arg COMMA callout_arg_list;

callout_arg		:	expr
					# calloutExpr
				|	string_literal
					# calloutStringLit;

method_name		:	id
					# methodName;

location		: 	id
				|	id O_BRACKET expr C_BRACKET	;

expr			:	location
					# exprLocation
				|	method_call
					# exprCallMethod
				|	literal
					# exprLiteral
				|	expr bin_op expr
					# exprBinOp
				|	SUB expr
					# exprSub
				|	NEGATION expr
					# exprNegation
				|	O_PAR expr C_PAR
					# exprEnclosed;

bin_op			:	arith_op					
					# arithmeticOp
				|	rel_op					
					# relationalOp
				|	eq_op					
					# equalOp
				|	cond_op					
					# conditionOp
				;

arith_op		:	MULT
					# mult
				|	DIV
					# div
				| 	MOD
					# mod
				|	ADD
					# add
				|	SUB
					# sub;

rel_op			:	GREAT_THAN					
					# relGreatThan
				|	LESS_THAN					
					# relLessThan
				|	GREAT_EQUAL_THAN					
					# relGreatEqual
				|	LESS_EQUAL_THAN					
					# relLessEqual;

eq_op			: 	EQUAL					
					# equalValue
				|	NOT_EQUAL					
					# equalNotValue;

cond_op			:	AND					
					# condAmperson
				|	OR					
					# condPipe;

literal 		:	int_literal
					# intLiteral
				|	char_literal
					# charLiteral
				|	bool_literal
					# booleanLiteral;

id				:	ID					
					# identifier;

hex_literal		:	HEX_LITERAL					
					# hexaLit
				;

int_literal		:	decimal_literal
					# intDecimal
				|	hex_literal
					# intHexa;

decimal_literal	:	INT_UNSIGNED					
					# numPositiveLit
				;

bool_literal	:	BOOL_LITERAL					
					# booleanLit
				;

char_literal	:	CHAR_LITERAL					
					# charLit
				;

string_literal	:	STRING_LITERAL
					# stringLit;



fragment DIGIT      :   [0-9];
fragment CHAR       :   [\u0020-\u007F];

// KEYWORDS
BOOLEAN             :   'boolean';
BREAK               :   'break';
CALLOUT             :   'callout';
CLASS               :   'class';
CONTINUE            :   'continue';
ELSE                :   'else';
BOOL_LITERAL	    :   'true' | 'false';
FOR                 :   'for';
IF                  :   'if';
INT                 :   'int';
RETURN              :   'return';
VOID                :   'void';
CLASS_PROGRAM       :   CLASS [' ']+ 'Program';
ID                  :   [a-zA-Z_]+ [a-zA-Z0-9_]*;

// MISC
O_BRACE     :   '{';
C_BRACE     :   '}';
O_PAR       : 	'(';
C_PAR       : 	')';
O_BRACKET   :	'[';
C_BRACKET   :	']';
EOL         : 	';';
COMMA       : 	',';
RETURN_TYPE : 	INT | BOOLEAN | VOID;
DOT         :   '.';


// LITERALS
ESCAPED_CHAR    :   '\\n' | '\\\"' | '\\\'' | '\\\\' | '\\t';
STRING_LITERAL  :   '"' ( CHAR | ESCAPED_CHAR )* '"';
CHAR_LITERAL    :   '\'' ( CHAR | ESCAPED_CHAR )? '\'';
INT_UNSIGNED    :   DIGIT+;
INT_SIGNED      :   '-'? INT_UNSIGNED;
HEX_LITERAL     :   '0x' [0-9A-Fa-f]+;

// ARITHMETIC OPERATORS
ADD         : 	'+';
SUB         : 	'-';
MULT        : 	'*';
DIV         : 	'/';
MOD         : 	'%';
ASSIGN      : 	'=';
ADD_ASSIGN  :	'+=';
SUB_ASSIGN  :	'-=';

// LOGICAL OPERATOR
EQUAL       : 	'==';
NEGATION    : 	'!';
NOT_EQUAL   :	'!=';
AND         : 	'&&';
OR          :	'||';
fragment ALL_L_OPERATOR  :   AND | OR | EQUAL | NEGATION | NOT_EQUAL;

// ARITHMETIC-LOGIC OPERATOR
GREAT_THAN          :	'>';
GREAT_EQUAL_THAN    :	'>=';
LESS_THAN           :	'<';
LESS_EQUAL_THAN     :	'<=';
