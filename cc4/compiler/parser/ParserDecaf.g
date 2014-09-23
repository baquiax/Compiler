parser grammar ParserDecaf;

options {
	tokenVocab=LexerDecaf;
}

@parser::header{
  package compiler.parser;
  import java.util.ArrayList;
}   

/*-----------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

start			: 	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE EOF
					# program;

field_decl    	: 	type (field_decl_deriv) (COMMA (field_decl_deriv))* EOL
                    # fieldDecl;

field_decl_deriv    :   ID #varDeclFD | ID O_BRACKET int_literal C_BRACKET #arrayDeclFD;

method_decl		:	(type | VOID) ID O_PAR method_param C_PAR block
					# methodDecl;

method_param	: 	type ID (COMMA type ID)* #methodParam | #nothing;

block			:	O_BRACE var_decl* statement* C_BRACE
					# blockDecl;

var_decl		:	type ID (COMMA ID)* EOL
					# varDecl;

type			:	INT
					# typeInt
				|	BOOLEAN
					# typeBoolean;

statement		: 	location assign_op expr EOL
					# locationAsign
				|	method_call EOL
					# methodCallStat
				|	IF O_PAR expr C_PAR block if_else?
					# if
				|	FOR ID ASSIGN expr COMMA expr block
					# for
				|	RETURN expr? EOL
					# return
				|	BREAK EOL
					# break
				|	CONTINUE EOL
					# continue
				|	block
					# subBlock;

if_else			:	ELSE block;

assign_op		:	ASSIGN
				|	ADD_ASSIGN 
				|	SUB_ASSIGN;

method_call		:	method_name O_PAR callout_expr? C_PAR
					# methodCall
				|	CALLOUT O_PAR string_literal callout_args? C_PAR
					# calloutCall;

callout_expr	:	expr (COMMA expr)*
                    #calloutExpr;

callout_args	:	(COMMA callout_arg)+
                    #calloutArgs;

method_name		:	ID
					# methodName;

location		: 	ID
				|	ID O_BRACKET expr C_BRACKET	;

expr			:	location
					# exprLocation
				|	method_call
					# exprMethodCall
				|	literal
					# exprLiteral
				|	expr bin_op expr
					# exprBinOp
				|	SUB expr
					# exprNegative
				|	NEGATION expr
					# exprNegation
				|	O_PAR expr C_PAR
					# exprEnclosed;

callout_arg		:	expr
					# calloutArgExpr
				|	string_literal
					# calloutArgStringLit;

bin_op			:	arith_op					
					# arithmeticOp
				|	rel_op					
					# relationalOp
				|	eq_op					
					# equalOp
				|	cond_op					
					# conditionOp
				;

arith_op		:   DIV
					# div
                |   MULT
					# mult
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
					# boolnLiteral;

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
