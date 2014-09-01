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
					# inicio
				|	error_brace_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '{' '}' de mas"});}
					# errorMoreBrace
				|	error_brace_mi
					{err++;errors.add(new String[] {"Error"+err+":Hay '{' '}' faltantes"});}
					# errorMissingBrace
				|	error_sintax
					{err++;errors.add(new String[] {"Error"+err+":Valor no reconocido"});}
					# errorSintax
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
				|	err_brack_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '[' ']' de mas"});}
					# errorBracketMoreVar
				|	err_brack_mi
					{err++;errors.add(new String[] {"Error"+err+":Hay '[' ']' faltantes"});}
					# errorBracketMissingVar
				;

field_decl    	: 	type var_deriv EOL
					{derivations.add(new String[] {"field_decl: type var_deriv EOL"});}
					# varDeclaration
				|	error_type
					{err++;errors.add(new String[] {"Error"+err+":Error en declaracion de variable"});}
					# errorTypeField
				|	error_assign
					{err++;errors.add(new String[] {"Error"+err+":Declaracion invalida"});}
					# errorAsignField
				|	err_init_assign
					{err++;errors.add(new String[] {"Error"+err+":Asignacion no permitida"});}
					# errorAssignInvalid
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# errorFieldEol
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
				|	error_par_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '(' ')' de mas"});}
					# errorParMethod
				;

block			:	O_BRACE var_decl* statement* C_BRACE
					{derivations.add(new String[] {"block: O_BRACE var_decl statement C_BRACE"});}
					# blockConstruction
				|	error_brace_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '{' '}' de mas"});}
					# errorBlockBraceMore
				|	error_brace_mi
					{err++;errors.add(new String[] {"Error"+err+":Hay '{' '}' faltantes"});}
					# errorBlockBraceMissing
				|	error_sintax
					{err++;errors.add(new String[] {"Error"+err+":Valor no reconocido"});}
					# errorSintaxBlock
				;

var_decl		:	type var_deriv EOL
					{derivations.add(new String[] {"var_decl: type var_deriv EOL"});}
					# simpleVarDecl
				|	error_type
					{err++;errors.add(new String[] {"Error"+err+":Error en declaracion de variable"});}
					# errorMethod
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# errorVarEol
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
					# ErrorAsignMethod
				|	error_eol
					{err++;errors.add(new String[] {"Error"+err+":Instruccion ';' repetida"});}
					# errorStatementEol
				|	error_par_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '(' ')' de mas"});}
					# errorParStatement
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
				|	error_par_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '(' ')' de mas"});}
					# errorParMethodCall
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
				|	error_par_mo
					{err++;errors.add(new String[] {"Error"+err+":Hay '(' ')' de mas"});}
					# errorParExpr
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
error_type		:	type type var_deriv EOL
					{this.errors.add(new String[] {$type.text +" "+ $type.text +" "+ $var_deriv.text +" "+ $EOL.text, String.valueOf($EOL.line), String.valueOf($EOL.pos -1)});}
				;

error_assign	:	id ASSIGN* EOL
                    {this.errors.add(new String[] {$id.text + " " + $ASSIGN.text + " " + $EOL.text, String.valueOf($ASSIGN.line),String.valueOf($EOL.pos - 1)});}
				|	id ADD_ASSIGN EOL
                    {this.errors.add(new String[] {$id.text + " " + $ADD_ASSIGN.text + " " + $EOL.text, String.valueOf($ADD_ASSIGN.line),String.valueOf($EOL.pos - 1)});}
				|	id SUB_ASSIGN EOL
                    {this.errors.add(new String[] {$id.text + " " + $SUB_ASSIGN.text + " " + $EOL.text, String.valueOf($SUB_ASSIGN.line),String.valueOf($EOL.pos - 1)});}
				;

err_init_assign	:	type* var_deriv ASSIGN expr EOL
					{this.errors.add(new String[] {$type.text +" "+ $var_deriv.text +" "+ $ASSIGN.text +" "+ $expr.text +" "+ $EOL.text, String.valueOf($ASSIGN.line), String.valueOf($EOL.pos -1)});}
				|	type* var_deriv ADD_ASSIGN expr EOL
					{this.errors.add(new String[] {$type.text +" "+ $var_deriv.text +" "+ $ADD_ASSIGN.text +" "+ $expr.text +" "+ $EOL.text, String.valueOf($ADD_ASSIGN.line), String.valueOf($EOL.pos -1)});}
				|	type* var_deriv SUB_ASSIGN expr EOL
					{this.errors.add(new String[] {$type.text +" "+ $var_deriv.text +" "+ $SUB_ASSIGN.text +" "+ $expr.text +" "+ $EOL.text, String.valueOf($SUB_ASSIGN.line), String.valueOf($EOL.pos -1)});}
				;

error_eol		:	type* var_deriv MULTIPLE_EOL
					{this.errors.add(new String[] {$type.text +" "+ $var_deriv.text +" "+ $MULTIPLE_EOL.text, String.valueOf($MULTIPLE_EOL.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	location assign_op expr MULTIPLE_EOL
					{this.errors.add(new String[] {$location.text +" "+ $assign_op.text +" "+ $expr.text +" "+ $MULTIPLE_EOL.text, String.valueOf($MULTIPLE_EOL.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	method_call MULTIPLE_EOL
					{this.errors.add(new String[] {$method_call.text +" "+ $MULTIPLE_EOL.text, String.valueOf($MULTIPLE_EOL.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	RETURN MULTIPLE_EOL
					{this.errors.add(new String[] {$RETURN.text +" "+ $MULTIPLE_EOL.text, String.valueOf($RETURN.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	RETURN expr MULTIPLE_EOL
					{this.errors.add(new String[] {$RETURN.text +" "+  $expr.text +" "+ $MULTIPLE_EOL.text, String.valueOf($RETURN.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	BREAK MULTIPLE_EOL
					{this.errors.add(new String[] {$BREAK.text +" "+ $MULTIPLE_EOL.text, String.valueOf($BREAK.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				|	CONTINUE MULTIPLE_EOL
					{this.errors.add(new String[] {$CONTINUE.text +" "+ $MULTIPLE_EOL.text, String.valueOf($CONTINUE.line), String.valueOf($MULTIPLE_EOL.pos -1)});}
				;

error_brace_mo	:	CLASS_PROGRAM O_BRACE O_BRACE+ field_decl* method_decl* C_BRACE EOF
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE C_BRACE+ EOF
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE O_BRACE+ var_decl* statement* C_BRACE
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE var_decl* statement* C_BRACE C_BRACE+
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				;

error_brace_mi	:	CLASS_PROGRAM O_BRACE field_decl* method_decl*
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text, String.valueOf($O_BRACE.line), String.valueOf($O_BRACE.pos -1)});}
				|	CLASS_PROGRAM field_decl* method_decl* C_BRACE
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text, String.valueOf($CLASS_PROGRAM.line), String.valueOf($C_BRACE.pos -1)});}
				|	var_decl statement C_BRACE
					{this.errors.add(new String[] {$var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text, String.valueOf($C_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE var_decl statement
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text, String.valueOf($O_BRACE.line), String.valueOf($O_BRACE.pos -1)});}
				;

error_sintax	:	CLASS_PROGRAM O_BRACE char_literal field_decl* method_decl* C_BRACE
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $char_literal.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE string_literal field_decl* method_decl* C_BRACE
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $string_literal.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE id field_decl* method_decl* C_BRACE
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $id.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE char_literal
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text +" "+ $char_literal.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE string_literal
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text +" "+ $string_literal.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	CLASS_PROGRAM O_BRACE field_decl* method_decl* C_BRACE id
					{this.errors.add(new String[] {$CLASS_PROGRAM.text +" "+ $O_BRACE.text +" "+ $field_decl.text +" "+ $method_decl.text +" "+ $C_BRACE.text +" "+ $id.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE char_literal var_decl* statement* C_BRACE
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $char_literal.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE string_literal var_decl* statement* C_BRACE
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $string_literal.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE id var_decl* statement* C_BRACE
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $id.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE var_decl* statement* C_BRACE char_literal
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text +" "+ $char_literal.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE var_decl* statement* C_BRACE string_literal
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text +" "+ $string_literal.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				|	O_BRACE var_decl* statement* C_BRACE id
					{this.errors.add(new String[] {$O_BRACE.text +" "+ $var_decl.text +" "+ $statement.text +" "+ $C_BRACE.text +" "+ $id.text, String.valueOf($O_BRACE.line), String.valueOf($C_BRACE.pos -1)});}
				;

err_brack_mo	:	id O_BRACKET O_BRACKET+ int_literal C_BRACKET
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $O_BRACKET.text +" "+ $int_literal.text +" "+ $C_BRACKET.text, String.valueOf($O_BRACKET.line), String.valueOf($O_BRACKET.pos -1)});}
				|	id O_BRACKET int_literal C_BRACKET C_BRACKET+
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $int_literal.text +" "+ $C_BRACKET.text +" "+ $C_BRACKET.text, String.valueOf($O_BRACKET.line), String.valueOf($C_BRACKET.pos -1)});}
				|	id O_BRACKET O_BRACKET+ int_literal C_BRACKET COMMA var_deriv
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $O_BRACKET.text +" "+ $int_literal.text +" "+ $C_BRACKET.text +" "+ $COMMA.text +" "+ $var_deriv.text, String.valueOf($O_BRACKET.line), String.valueOf($O_BRACKET.pos -1)});}
				|	id O_BRACKET int_literal C_BRACKET C_BRACKET+ COMMA var_deriv
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $int_literal.text +" "+ $C_BRACKET.text +" "+ $C_BRACKET.text +" "+ $COMMA.text +" "+ $var_deriv.text, String.valueOf($O_BRACKET.line), String.valueOf($C_BRACKET.pos -1)});}
				;

err_brack_mi	:	id O_BRACKET int_literal
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $int_literal.text, String.valueOf($O_BRACKET.line), String.valueOf($O_BRACKET.pos -1)});}
				|	id C_BRACKET
					{this.errors.add(new String[] {$id.text +" "+ $C_BRACKET.text, String.valueOf($C_BRACKET.line), String.valueOf($C_BRACKET.pos -1)});}
				|	id C_BRACKET COMMA var_deriv
					{this.errors.add(new String[] {$id.text +" "+ $C_BRACKET.text +" "+ $COMMA.text +" "+ $var_deriv.text, String.valueOf($C_BRACKET.line), String.valueOf($COMMA.pos -1)});}
				|	id O_BRACKET int_literal COMMA var_deriv
					{this.errors.add(new String[] {$id.text +" "+ $O_BRACKET.text +" "+ $int_literal.text +" "+ $COMMA.text +" "+ $var_deriv.text, String.valueOf($O_BRACKET.line), String.valueOf($COMMA.pos -1)});}
				;

error_par_mo	:	type id O_PAR O_PAR+ C_PAR block
					{this.errors.add(new String[] {$type.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	type id O_PAR C_PAR C_PAR+ block
					{this.errors.add(new String[] {$type.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	type id O_PAR O_PAR+ method_deriv C_PAR block
					{this.errors.add(new String[] {$type.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $method_deriv.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	type id O_PAR method_deriv C_PAR C_PAR+ block
					{this.errors.add(new String[] {$type.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $method_deriv.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	VOID id O_PAR O_PAR+ C_PAR block
					{this.errors.add(new String[] {$VOID.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	VOID id O_PAR C_PAR C_PAR+ block
					{this.errors.add(new String[] {$VOID.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	VOID id O_PAR O_PAR+ method_deriv C_PAR block
					{this.errors.add(new String[] {$VOID.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $method_deriv.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	VOID id O_PAR method_deriv C_PAR C_PAR+ block
					{this.errors.add(new String[] {$VOID.text +" "+ $id.text +" "+ $O_PAR.text +" "+ $method_deriv.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	IF O_PAR O_PAR+ expr C_PAR block
					{this.errors.add(new String[] {$IF.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	IF O_PAR expr C_PAR C_PAR+ block
					{this.errors.add(new String[] {$IF.text +" "+ $O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	IF O_PAR O_PAR+ expr C_PAR block ELSE block
					{this.errors.add(new String[] {$IF.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text +" "+ $block.text +" "+ $ELSE.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	IF O_PAR expr C_PAR C_PAR+ block ELSE block
					{this.errors.add(new String[] {$IF.text +" "+ $O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text +" "+ $C_PAR.text +" "+ $block.text +" "+ $ELSE.text +" "+ $block.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	method_name O_PAR O_PAR+ expr_deriv C_PAR
					{this.errors.add(new String[] {$method_name.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $expr_deriv.text +" "+ $C_PAR.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	method_name O_PAR expr_deriv C_PAR C_PAR+
					{this.errors.add(new String[] {$method_name.text +" "+ $O_PAR.text +" "+ $expr_deriv.text +" "+ $C_PAR.text +" "+ $C_PAR.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				|	CALLOUT O_PAR O_PAR+ string_literal COMMA callout_deriv C_PAR
					{this.errors.add(new String[] {$CALLOUT.text +" "+ $O_PAR.text +" "+ $O_PAR.text +" "+ $string_literal.text +" "+ $COMMA.text +" "+ $callout_deriv.text +" "+ $C_PAR.text, String.valueOf($CALLOUT.line), String.valueOf($O_PAR.pos -1)});}
				|	CALLOUT O_PAR string_literal COMMA callout_deriv C_PAR C_PAR+
					{this.errors.add(new String[] {$CALLOUT.text +" "+ $O_PAR.text +" "+ $string_literal.text +" "+ $COMMA.text +" "+ $callout_deriv.text +" "+ $C_PAR.text +" "+ $C_PAR.text, String.valueOf($CALLOUT.line), String.valueOf($C_PAR.pos -1)});}
				|	O_PAR O_PAR+ expr C_PAR
					{this.errors.add(new String[] {$O_PAR.text +" "+ $O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text, String.valueOf($O_PAR.line), String.valueOf($O_PAR.pos -1)});}
				|	O_PAR expr C_PAR C_PAR+
					{this.errors.add(new String[] {$O_PAR.text +" "+ $expr.text +" "+ $C_PAR.text +" "+ $C_PAR.text, String.valueOf($O_PAR.line), String.valueOf($C_PAR.pos -1)});}
				;

//PENDIENTE
error_par_mi	:	type id C_PAR block	
				|	type id O_PAR block
				;
