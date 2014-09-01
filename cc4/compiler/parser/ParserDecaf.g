parser grammar ParserDecaf;

options {
    tokenVocab=LexerDecaf;
}

@parser::header{
  package compiler.parser;
  import java.util.ArrayList;
}

@parser::members {
	private ArrayList<String[]> derivations = new ArrayList<String[]>();
	private ArrayList<String[]> errors = new ArrayList<String[]>();
	private int err=0;

	public ArrayList<String[]> getDerivations() {
		return this.derivations;
	}

	public ArrayList<String[]> getErrorsDerivation() {
		return this.errors;
	}
}

/*-----------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

start			: 	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE EOF
					{derivations.add(new String[] {"start: CLASS_PROGRAM O_BRACE field_decl method_decl C_BRACE"});}
					#inicio
				;

var_deriv		: 	id 															
					{derivations.add(new String[] {"var_deriv: id"});}
					# oneId
				|	id O_BRACKET int_literal C_BRACKET 							
					{derivations.add(new String[] {"var_deriv: id O_BRACKET int_literal C_BRACKET"});}
					# array
				| 	id COMMA var_deriv											
					{derivations.add(new String[] {"var_deriv: id COMMA var_deriv"});}
					# variousId
				|	id O_BRACKET int_literal C_BRACKET COMMA var_deriv			
					{derivations.add(new String[] {"var_deriv: id O_BRACKET int_literal C_BRACKET COMMA var_deriv"});}
					# variousArray
				;

field_decl    	: 	type var_deriv EOL
					{derivations.add(new String[] {"field_decl: type var_deriv EOL"});}
					# varDeclaration
				|	error_type
					{err++;errors.add(new String[] {"Error"+err+":Error en declaracion de variable"});}
					#errorTypeField
				|	error_assign
					{err++;errors.add(new String[] {"Error"+err+":Declaracion invalida"});}
					# asignErrorField
				|	err_init_assign
					{err++;errors.add(new String[] {"Error"+err+":Asignacion no permitida"});}
					# assignInvalid
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# field_eol_error
				;

method_deriv	:	type id 													
					{derivations.add(new String[] {"method_deriv: type id"});}
					# simpleType
				|	type id COMMA method_deriv 									
					{derivations.add(new String[] {"method_deriv: type id COMMA method_deriv"});}
					# composedType
				|	type var_deriv
					{derivations.add(new String[] {"method_deriv: type var_deriv"});}
					# simpleArrayType
				|	type var_deriv COMMA method_deriv
					{derivations.add(new String[] {"method_deriv: type var_deriv COMMA method_deriv"});}
					# composedArrayType
				;

method_decl   	: 	type id O_PAR C_PAR block
					{derivations.add(new String[] {"method_decl: type id O_PAR C_PAR block"});}
					# methodSimple
				|	type id O_PAR method_deriv C_PAR block
					{derivations.add(new String[] {"method_decl: type id O_PAR method_deriv C_PAR block"});}
					# methodComposed
				|	VOID id O_PAR C_PAR block
					{derivations.add(new String[] {"method_decl: VOID id O_PAR C_PAR block"});}
					# methodVoidSimple
				|	VOID id O_PAR method_deriv C_PAR block
					{derivations.add(new String[] {"method_decl: VOID id O_PAR method_deriv C_PAR block"});}
					# methodVoidComposed
				;

block			:	O_BRACE var_decl* statement* C_BRACE
					{derivations.add(new String[] {"block: O_BRACE var_decl statement C_BRACE"});}
					# blockConstruction
				;

var_decl		:	type var_deriv EOL
					{derivations.add(new String[] {"var_decl: type var_deriv EOL"});}
					# simpleVarDecl
				|	error_type
					{err++;errors.add(new String[] {"Error"+err+":Error en declaracion de variable"});}
					# errorMethod
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# var_eol_error
				;

type			:	INT
					{derivations.add(new String[] {"type: INT"});}
					# typeInt
				|	BOOLEAN
					{derivations.add(new String[] {"type: BOOLEAN"});}
					# typeBoolean
				;

statement		: 	location assign_op expr EOL
					{derivations.add(new String[] {"statement: location assign_op expr EOL"});}
					# locationAsign
				|	method_call EOL
					{derivations.add(new String[] {"statement: method_call EOL"});}
					# callMethod
				|	IF O_PAR expr C_PAR block	
					{derivations.add(new String[] {"statement: IF O_PAR expr C_PAR block"});}
					# ifSimple
				| 	IF O_PAR expr C_PAR block ELSE block
					{derivations.add(new String[] {"statement: IF O_PAR expr C_PAR block ELSE block"});}
					# ifComposed
				|	FOR id ASSIGN expr COMMA expr block
					{derivations.add(new String[] {"statement: FOR id ASSIGN expr COMMA expr block"});}
					# forBucle
				|	RETURN EOL
					{derivations.add(new String[] {"statement: RETURN EOL"});}
					# returnEmpty
				|	RETURN expr EOL
					{derivations.add(new String[] {"statement: RETURN expr EOL"});}
					# returnValue
				|	BREAK EOL
					{derivations.add(new String[] {"statement: BREAK EOL"});}
					# exitBreak
				|	CONTINUE EOL
					{derivations.add(new String[] {"statement: CONTINUE EOL"});}
					# continueInstruction
				| 	block	
					{derivations.add(new String[] {"statement: block"});}
					# constructionBlock
				|	error_assign
					{err++;errors.add(new String[] {"Error"+err+":Asignacion de valor invalida"});}
					# asignErrorMethod
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# statement_eol_error
				;

assign_op		:	ASSIGN
					{derivations.add(new String[] {"assign_op: ASSIGN"});}
					# asignValue
				|	ADD_ASSIGN 													
					{derivations.add(new String[] {"assign_op: ADD_ASSIGN"});}
					# asignValuePlus
				|	SUB_ASSIGN
					{derivations.add(new String[] {"assign_op: SUB_ASSIGN"});}
					# asignValueMinus
				;

expr_deriv		:	expr COMMA expr_deriv
					{derivations.add(new String[] {"expr_deriv: expr COMMA expr_deriv"});}
					# variousExpresion
				|	expr
					{derivations.add(new String[] {"expr_deriv: expr"});}
					# oneExpresion
				;

callout_deriv	:	callout_arg COMMA callout_deriv
					{derivations.add(new String[] {"callout_deriv: callout_arg COMMA callout_deriv"});}
					# variousCallout
				|	callout_arg
					{derivations.add(new String[] {"callout_deriv: callout_arg"});}
					# oneCallout
				;

method_call		:	method_name O_PAR expr_deriv C_PAR
					{derivations.add(new String[] {"method_call: method_name O_PAR expr_deriv C_PAR"});}
					# methodCallComposed
				|	CALLOUT O_PAR string_literal COMMA callout_deriv C_PAR
					{derivations.add(new String[] {"method_call: CALLOUT O_PAR string_literal COMMA callout_deriv C_PAR"});}
					# calloutCallComposed
				;

method_name		:	id
					{derivations.add(new String[] {"method_name: id"});}
					# methodName
				;

location		: 	id
					{derivations.add(new String[] {"location: id"});}
					# idLocation
				|	id O_BRACKET expr C_BRACKET
					{derivations.add(new String[] {"location: id O_BRACKET expr C_BRACKET"});}
					# idLocationComposed
				;

expr			:	location
					{derivations.add(new String[] {"expr: location"});}
					# exprLocation
				|	method_call				
					{derivations.add(new String[] {"expr: method_call"});}
					# exprCallMethod
				|	literal
					{derivations.add(new String[] {"expr: literal"});}
					# exprLiteral
				|	expr bin_op expr
					{derivations.add(new String[] {"expr: expr bin_op expr"});}
					# exprBinOp
				|	SUB expr
					{derivations.add(new String[] {"expr: SUB expr"});}
					# exprSub
				|	NEGATION expr
					{derivations.add(new String[] {"expr: NEGATION expr"});}
					# exprNegation
				|	O_PAR expr C_PAR
					{derivations.add(new String[] {"expr: O_PAR expr C_PAR"});}
					# exprEnclosed
				;

callout_arg		:	expr
					{derivations.add(new String[] {"callout_arg: expr"});}
					# calloutExpr
				|	string_literal			
					{derivations.add(new String[] {"callout_arg: string_literal"});}
					# calloutStringLit
				;

bin_op			:	arith_op
					{derivations.add(new String[] {"bin_op: arith_op"});}
					# opArithmetic
				|	rel_op
					{derivations.add(new String[] {"bin_op: rel_op"});}
					# opRelational
				|	eq_op
					{derivations.add(new String[] {"bin_op: eq_op"});}
					# opEqual
				|	cond_op
					{derivations.add(new String[] {"bin_op: cond_op"});}
					# opConditional
				;

arith_op		:	ADD
					{derivations.add(new String[] {"arith_op: ADD"});}
					# aPlus
				|	SUB
					{derivations.add(new String[] {"arith_op: SUB"});}
					# aMinus
				|	MULT 														
					{derivations.add(new String[] {"arith_op: MULT"});}
					# aMult
				|	DIV 														
					{derivations.add(new String[] {"arith_op: DIV"});}
					# aDiv
				| 	MOD 														
					{derivations.add(new String[] {"arith_op: MOD"});}
					# aModule
				;

rel_op			:	GREAT_THAN
					{derivations.add(new String[] {"rel_op: GREAT_THAN"});}
					# relGreatThan
				|	LESS_THAN			
					{derivations.add(new String[] {"rel_op: LESS_THAN"});}
					# relLessThan
				|	GREAT_EQUAL_THAN
					{derivations.add(new String[] {"rel_op: GREAT_EQUAL_THAN"});}
					# relGreatEqual
				|	LESS_EQUAL_THAN
					{derivations.add(new String[] {"rel_op: LESS_EQUAL_THAN"});}
					# relLessEqual
				;

eq_op			: 	EQUAL
					{derivations.add(new String[] {"eq_op: EQUAL"});}
					# equalValue
				|	NOT_EQUAL				
					{derivations.add(new String[] {"eq_op: NOT_EQUAL"});}
					# equalNotValue
				;

cond_op			:	AND															
					{derivations.add(new String[] {"cond_op: AND"});}
					# condAmperson
				|	OR															
					{derivations.add(new String[] {"cond_op: OR"});}
					# condPipe
				;

literal 		:	int_literal				
					{derivations.add(new String[] {"literal: int_literal"});}
					# litInt
				|	char_literal		
					{derivations.add(new String[] {"literal: char_literal"});}
					# litChar
				|	bool_literal		
					{derivations.add(new String[] {"literal: bool_literal"});}
					# litBool
				;

id				:	ID		
					{derivations.add(new String[] {"id: ID"});}
					# identifier
				;

hex_literal		:	HEX_LITERAL
					{derivations.add(new String[] {"hex_literal: HEX_LITERAL"});}
					# hexaLit
				;

int_literal		:	decimal_literal
					{derivations.add(new String[] {"int_literal: decimal_literal"});}
					# intDecimal
				|	hex_literal											
					{derivations.add(new String[] {"int_literal: hex_literal"});}
					# intHexa
				;

decimal_literal	:	INT_UNSIGNED
					{derivations.add(new String[] {"decimal_literal: INT_UNSIGNED"});}
					# numPositiveLit
				;

bool_literal	:	BOOL_LITERAL
					{derivations.add(new String[] {"bool_literal: BOOL_LITERAL"});}
					# booleanLit
				;

char_literal	:	CHAR_LITERAL	
					{derivations.add(new String[] {"char_literal: CHAR_LITERAL"});}
					# charLit
				;

string_literal	:	STRING_LITERAL
					{derivations.add(new String[] {"string_literal: STRING_LITERAL"});}
					# stringLit
				;

//CAPTURA DE ERRORES
error_type		:	type type var_deriv EOL;

error_assign	:	id ASSIGN EOL
					# errorAsign
				|	id ADD_ASSIGN EOL
					# errorAddAssign
				|	id SUB_ASSIGN EOL
					# errorSubAssign
				;

err_init_assign	:	type* var_deriv ASSIGN expr EOL
					# errorInitAsign
				|	type* var_deriv ADD_ASSIGN expr EOL
					# errorInitAddAsign
				|	type* var_deriv SUB_ASSIGN expr EOL
					# errorInitSubAsign
				;

error_eol		:	type* var_deriv MULTIPLE_EOL
				|	location assign_op expr MULTIPLE_EOL
				|	method_call MULTIPLE_EOL
				|	RETURN MULTIPLE_EOL
				|	RETURN expr MULTIPLE_EOL
				|	BREAK MULTIPLE_EOL
				|	CONTINUE MULTIPLE_EOL
				;
