parser grammar DecafParser;

options {
    tokenVocab=DecafScanner;
}

@parser::header{
  package compiler.parser;
}

/*-----------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

var_decl		: 	ID | 
					ID COMA var_decl | 
					ID OS_BRACE INT_POSITIVE CS_BRACE COMA var_decl | 
					ID OS_BRACE INT_POSITIVE CS_BRACE COMA;
field_decl    	: 	DATA_TYPE var_decl END_LINE;
method_decl   	: 	RETURN_TYPE ID O_PAR  C_PAR;
start			: 	CLASS_PROGRAM O_BRACE field_decl method_decl C_BRACE EOF;
