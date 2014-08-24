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

start			: 	CLASS_PROGRAM O_BRACE field_decl method_decl C_BRACE EOF;

var_decl		: 	id
				|	id O_BRACKET int_literal C_BRACKET 	
				| 	id COMA var_decl
				|	id O_BRACKET int_literal C_BRACKET COMA var_decl
				|	;

field_decl    	: 	type var_decl END_LINE
				|	;

method_deriv	:	INT id
				|	BOOLEAN id
				|	INT id COMA method_deriv
				|	BOOLEAN id COMA method_deriv;

method_decl   	: 	type id O_PAR C_PAR block
				|	type id O_PAR method_deriv C_PAR block
				|	VOID id O_PAR C_PAR block
				|	VOID id O_PAR method_deriv C_PAR block
				|	;

block			:	O_BRACE var_decl statement C_BRACE;

type			:	INT
				|	BOOLEAN;

statement		: 	location assign_op expr END_LINE
				|	method_call END_LINE
				|	IF O_PAR expr C_PAR block
				| 	IF O_PAR expr C_PAR block ELSE block
				|	FOR id ASIGN expr COMA expr block
				|	RETURN END_LINE
				|	RETURN expr END_LINE
				|	BREAK END_LINE
				|	CONTINUE END_LINE
				| 	block
				|	;

assign_op		:	ASIGN
				|	ADD_ASSIGN
				|	SUB_ASSIGN;

expr_deriv		:	expr COMA expr_deriv
				|	expr
				|	;

callout_deriv	:	callout_arg COMA callout_deriv
				|	callout_arg;

method_call		:	method_name O_PAR expr_deriv C_PAR
				|	CALLOUT O_PAR string_literal C_PAR
				|	CALLOUT O_PAR string_literal COMA callout_deriv C_PAR;

method_name		:	id;

location		: 	id
				|	id O_BRACKET expr C_BRACKET;

expr			:	location
				|	method_call
				|	literal
				|	expr bin_op expr
				|	SUB expr
				|	NEGATION expr
				|	O_PAR expr C_PAR;

callout_arg		:	expr
				|	string_literal;

bin_op			:	arith_op
				|	rel_op
				|	eq_op
				|	cond_op;

arith_op		:	ADD
				|	SUB
				|	MULT
				|	DIV
				| 	MOD;

rel_op			:	GREAT_THAN
				|	LESS_THAN
				|	GREAT_EQUAL_THAN
				|	LESS_EQUAL_THAN;

eq_op			: 	EQUAL
				|	NOT_EQUAL;

cond_op			:	SC_AMP
				|	SC_PIPE;

literal 		:	int_literal
				|	char_literal
				|	bool_literal;

id				:	ID;

hex_literal		:	HEX_LITERAL;

int_literal		:	decimal_literal
				|	hex_literal;

decimal_literal	:	INT_UNSIGNED;

bool_literal	:	BOOL_LITERAL;

char_literal	:	CHAR_LITERAL;

string_literal	:	STRING_LITERAL;
