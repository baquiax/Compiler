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

var_decl		: 	ID | 
					ID COMA var_decl | 
					ID O_BRACE INT_UNSIGNED C_BRACE COMA var_decl | 
					ID O_BRACE INT_UNSIGNED C_BRACE COMA;
field_decl    	: 	INT var_decl END_LINE |
					BOOLEAN var_decl END_LINE;
method_decl   	: 	RETURN_TYPE ID O_PAR C_PAR;
start			: 	CLASS_PROGRAM O_BRACE field_decl method_decl C_BRACE EOF;
