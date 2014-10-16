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

field_decl_deriv    :   ID #varDecl | ID O_BRACKET int_lit C_BRACKET #arrayDecl;

method_decl		:	(type | VOID) ID O_PAR method_param? C_PAR block
					# methodDecl;

method_param	: 	type ID (COMMA type ID)* #methodParam;

block			:	O_BRACE var_decls* statements* C_BRACE
					# blockDecl;

var_decls		:	type ID (COMMA ID)* EOL
					# varDecls;

type			:	INT
					# typeInt
				|	BOOLEAN
					# typeBoolean;

statements		: 	location assign_op expr EOL
					# locationAssign
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

if_else			:	ELSE block #ifElse;

assign_op		:	ASSIGN
				|	ADD_ASSIGN 
				|	SUB_ASSIGN;

method_call		:	ID O_PAR callout_expr? C_PAR
					# methodCall
				|	CALLOUT O_PAR string_literal callout_args? C_PAR
					# calloutCall;

callout_expr	:	expr (COMMA expr)*
                    #calloutExpr;

callout_args	:	(COMMA callout_arg)+
                    #calloutArgs;

location		: 	ID # idLocation
				|	ID O_BRACKET expr C_BRACKET	# arrayLocation;

expr			:	location
					# exprLocation
				|	method_call
					# exprMethodCall
				|	literal
					# exprLiteral
				|	expr arith_mult expr
					# exprArithMult
				|	expr arith_add expr
					# exprArithAdd
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

bin_op			:	rel_op					
					# relationalOp
				|	eq_op					
					# equalOp
				|	cond_op					
					# conditionOp
				;

arith_add		:   ADD
					# add
				|	SUB
					# sub;

arith_mult		:	DIV
					# div
                |   MULT
					# mult
				| 	MOD
					# mod;

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

literal 		:	int_lit
					# intLiteralList
				|	CHAR_LITERAL
					# charLiteral
				|	bool_literal
					# boolnLiteral;

int_lit			:	INT_UNSIGNED
					# intLiteral
				|	HEX_LITERAL
					# hexLiteral;

bool_literal	:	BOOL_LITERAL					
					# boolLiteral;

string_literal	:	STRING_LITERAL
					# stringLit;
