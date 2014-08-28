parser grammar ParserDecaf;

options {
    tokenVocab=LexerDecaf;
}

@parser::header{
  package compiler.parser;
}

/*-----------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

start			: 	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE EOF	# inicio
				;

var_decl		: 	id 															# oneId
				|	id O_BRACKET int_literal C_BRACKET 							# array
				| 	id COMMA var_decl											# variousId
				|	id O_BRACKET int_literal C_BRACKET COMMA var_decl			# variousArray
				;

field_decl    	: 	type var_decl EOL 											# varDeclaration
				;

method_deriv	:	INT id 														# integerType
				|	BOOLEAN id 													# booleanType
				|	INT id COMMA method_deriv 									# integerVariousType
				|	BOOLEAN id COMMA method_deriv								# booleanVariousType
				;

method_decl   	: 	type id O_PAR C_PAR block 									# methodSimple
				|	type id O_PAR method_deriv C_PAR block 						# methodComposed
				|	VOID id O_PAR C_PAR block									# methodVoidSimple
				|	VOID id O_PAR method_deriv C_PAR block						# methodVoidComposed
				;

block			:	O_BRACE var_decl* statement* C_BRACE						# blockConstruction
				;

type			:	INT 														# typeInt
				|	BOOLEAN														# typeBoolean
				;

statement		: 	location assign_op expr EOL 								# locationAsign
				|	method_call EOL												# callMethod
				|	IF O_PAR expr C_PAR block 									# ifSimple
				| 	IF O_PAR expr C_PAR block ELSE block 						# ifComposed
				|	FOR id ASSIGN expr COMMA expr block 						# forBucle
				|	RETURN EOL 													# returnEmpty
				|	RETURN expr EOL 											# returnValue
				|	BREAK EOL													# exitBreak
				|	CONTINUE EOL												# continueInstruction
				| 	block 														# constructionBlock
				;

assign_op		:	ASSIGN 														# asignValue
				|	ADD_ASSIGN 													# asignValuePlus
				|	SUB_ASSIGN 													# asignValueMinus
				;

expr_deriv		:	expr COMMA expr_deriv 										# variousExpresion
				|	expr 														# oneExpresion
				;

callout_deriv	:	callout_arg COMMA callout_deriv 							# variousCallout
				|	callout_arg													# oneCallout
				;

method_call		:	method_name O_PAR expr_deriv C_PAR							# methodCallComposed
				|	CALLOUT O_PAR string_literal C_PAR							# calloutCallSimple
				|	CALLOUT O_PAR string_literal COMMA callout_deriv C_PAR		# calloutCallComposed
				;

method_name		:	id 															# methodName
				;

location		: 	id 															# idLocation
				|	id O_BRACKET expr C_BRACKET 								# idLocationComposed
				;

expr			:	location 													# exprLocation
				|	method_call													# exprCallMethod
				|	literal 													# exprLiteral
				|	expr bin_op expr 											# exprBinOp
				|	SUB expr 													# exprSub
				|	NEGATION expr 												# exprNegation
				|	O_PAR expr C_PAR 											# exprEnclosed
				;

callout_arg		:	expr 														# calloutExpr
				|	string_literal 												# calloutStringLit
				;

bin_op			:	arith_op 													# opArithmetic
				|	rel_op 														# opRelational
				|	eq_op 														# opEqual
				|	cond_op 													# opConditional
				;

arith_op		:	ADD 														# aPlus
				|	SUB 														# aMinus
				|	MULT 														# aMult
				|	DIV 														# aDiv
				| 	MOD 														# aModule
				;

rel_op			:	GREAT_THAN 													# relGreatThan
				|	LESS_THAN 													# relLessThan
				|	GREAT_EQUAL_THAN 											# relGreatEqual
				|	LESS_EQUAL_THAN												# relLessEqual
				;

eq_op			: 	EQUAL 														# equalValue
				|	NOT_EQUAL 													# equalNotValue
				;

cond_op			:	AND															# condAmperson
				|	OR															# condPipe
				;

literal 		:	int_literal 												# litInt
				|	char_literal 												# litChar
				|	bool_literal 												# litBool
				;

id				:	ID 															# identifier
				;

hex_literal		:	HEX_LITERAL 												# hexaLit
				;

int_literal		:	decimal_literal												# intDecimal
				|	hex_literal 												# intHexa
				;

decimal_literal	:	INT_UNSIGNED 												# numPositiveLit
				;

bool_literal	:	BOOL_LITERAL												# booleanLit
				;

char_literal	:	CHAR_LITERAL												# charLit
				;

string_literal	:	STRING_LITERAL												# stringLit
				;
