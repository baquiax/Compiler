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

start			: 	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE EOF;
var_decl		: 	id COMA var_decl 
				| 	id O_BRACE int_literal C_BRACE COMA var_decl 
				| 	id O_BRACE int_literal C_BRACE 
				|	id;
field_decl    	: 	INT var_decl END_LINE 
				|	BOOLEAN var_decl END_LINE;
method_deriv	:	INT id COMA method_deriv 
				|	BOOLEAN id COMA method_deriv 
				|	INT id 
				|	BOOLEAN id;
method_decl   	: 	RETURN_TYPE id O_PAR C_PAR block 
				|	RETURN_TYPE id O_PAR method_deriv C_PAR block;
block			:	O_BRACE var_decl* statement* C_BRACE;
statement		: 	location assign_op expr END_LINE
				|	method_call END_LINE
				| 	IF O_PAR expr C_PAR block ELSE block
				|	FOR id ASIGN expr COMA expr block
				|	RETURN expr END_LINE
				|	BREAK END_LINE
				|	CONTINUE END_LINE
				| 	block;
assign_op		:	ASIGN
				|	ADD_ASSIGN
				|	SUB_ASSIGN;
method_call		:	method_name O_PAR expr+ C_PAR
				|	CALLOUT O_PAR string_literal COMA callout_arg+ C_PAR;
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
				|	GREAT_EQUAL_THAN
				|	LESS_THAN
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