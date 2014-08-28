lexer grammar LexerDecaf;

@lexer::header {
  package compiler.scanner;
  import java.util.ArrayList;
}
@lexer::members {
  private  ArrayList<String[]> recognizedTokens = new ArrayList<String[]>();
  private  ArrayList<String[]> errors = new ArrayList<String[]>();

  public ArrayList<String[]> getRecognizedTokens() {
      return this.recognizedTokens;
  }

  public ArrayList<String[]> getErrors() {
      return this.errors;
  }

  private String getErrorDesc(CharStream cs, int index) {
      int si = index - 1;
      return cs.getText(new Interval(((si >= 0) ? si : 0), index));
  }
}

WHITESPACE  	:   ( '\t' | ' ' | '\r' | '\n')+ { skip(); } ;
COMMENT     	:   '//' ~['\n'] '\n' { skip(); } ;

/**
 * TODO
 * En el ACTION de cada regla agregaré algo como
 * this.listOfTokens.put("<LINE>", {<TEXT>, <TYPE_TOKEN>})
 */

// FRAGMENTS ;)
fragment DIGIT	: [0-9];
fragment CHAR	  : [\u0020-\u007F];

// KEYWORDS
BOOLEAN 		  :	'boolean' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "BOOLEAN", getText()});};
BREAK			    :	'break' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "BREAK", getText()});};
CALLOUT			  :	'callout' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "CALLOUT", getText()});};
CLASS 			  :   'class' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "CLASS", getText()});} ;
CONTINUE 		  :	'continue' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "CONTINUE", getText()});} ;
ELSE			    :	'else' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ELSE", getText()});} ;
BOOL_LITERAL	:   'true' | 'false' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "BOOL_LITERAL", getText()});} ;
FOR 			    :	'for' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "FOR", getText()});} ;
IF 			      :   'if' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "IF", getText()});} ;
INT 			    :   'int' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "INT", getText()});} ;
RETURN 		 	  :   'return' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "RETURN", getText()});} ;
VOID			    :	'void' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "VOID", getText()});} ;
CLASS_PROGRAM :   CLASS [' ']+ 'Program' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "CLASS_PROGRAM", getText()});} ;
ID		    	  :   [a-zA-Z_]+ [a-zA-Z0-9_]* { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ID", getText()});} ;


// MISC
O_BRACE       :   '{' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "O_BRACE", getText()});};
C_BRACE       :   '}' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "C_BRACE", getText()});};
O_PAR 			  : 	'(' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "O_PAR", getText()});};
C_PAR 			  : 	')' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "C_PAR", getText()});};
O_BRACKET		  :	  '[' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "O_BRACKET", getText()});};
C_BRACKET		  :	  ']' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "C_BRACKET", getText()});};
EOL 	    	  : 	';' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "EOL", getText()});};
COMMA  			  : 	',' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "COMMA", getText()});};
RETURN_TYPE		: 	INT | BOOLEAN | VOID { recognizedTokens.add(new String[] {String.valueOf(getLine()), "RETURN_TYPE", getText()});};
DOT           :   '.' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "DOT", getText()});};



// LITERALS
ESCAPED_CHAR	:	'\\n' | '\\\"' | '\\\'' | '\\\\' | '\\t' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ESCAPED_CHAR", getText()});} ;
STRING_LITERAL:   '"' ( CHAR | ESCAPED_CHAR )* '"' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "STRING_LITERAL", getText()});} ;
CHAR_LITERAL  :   '\'' ( CHAR | ESCAPED_CHAR )? '\'' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "CHAR_LITERAL", getText()});} ;
INT_UNSIGNED	:	DIGIT+ { recognizedTokens.add(new String[] {String.valueOf(getLine()), "INT_UNSIGNED", getText()});} ;
INT_SIGNED    :   '-'? INT_UNSIGNED { recognizedTokens.add(new String[] {String.valueOf(getLine()), "INT_SIGNED", getText()});} ;
HEX_LITERAL   :   '0x' [0-9A-Fa-f]+ { recognizedTokens.add(new String[] {String.valueOf(getLine()), "HEX_LITERAL", getText()});} ;

// ARITHMETIC OPERATORS
ADD 			    : 	'+' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ADD", getText()});};
SUB 			    : 	'-' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "SUB", getText()});};
MULT 			    : 	'*' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "MULT", getText()});};
DIV 			    : 	'/' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "DIV", getText()});};
MOD				    : 	'%' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "MOD", getText()});};
ASSIGN  	    : 	'=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ASSIGN", getText()});};
ADD_ASSIGN		:	'+=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "ADD_ASSIGN", getText()});};
SUB_ASSIGN		:	'-=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "SUB_ASSIGN", getText()});};

// LOGICAL OPERATOR
EQUAL			    : 	'==' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "EQUAL", getText()});};
NEGATION		  : 	'!' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "NEGATION", getText()});};
NOT_EQUAL		  :	'!=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "NOT_EQUAL", getText()});};
AND	    		  : 	'&&' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "AND", getText()});};
OR		    	  :	'||' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "OR", getText()});};
fragment ALL_L_OPERATOR  :   AND | OR | EQUAL | NEGATION | NOT_EQUAL;

// ARITHMETIC-LOGIC OPERATOR
GREAT_THAN		:	'>' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "GREAT_THAN", getText()});};
GREAT_EQUAL_THAN	:	'>=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "GREAT_EQUAL_THAN", getText()});};
LESS_THAN			:	'<' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "LESS_THAN", getText()});};
LESS_EQUAL_THAN		:	'<=' { recognizedTokens.add(new String[] {String.valueOf(getLine()), "LESS_EQUAL_THAN", getText()});};

//ERRORS
INVALID_CHAR        :   ~[CHAR] {errors.add(new String[] {String.valueOf(getLine()), "UNRECOGNIZED CHAR", getErrorDesc(getInputStream(), getCharIndex())});};
WHITHOUT_S_QUOTE    :   '\'' ~['\'']* '\n' {errors.add(new String[] {String.valueOf(getLine()), "WITHOUT_SIMPLE_QUOTE", getErrorDesc(getInputStream(), getCharIndex())});};
WHITHOUT_D_QUOTE    :   '\"' ~['\"']* '\n' {errors.add(new String[] {String.valueOf(getLine()), "WITHOUT_DOUBLE_QUOTE", getErrorDesc(getInputStream(), getCharIndex())});};
MULTIPLE_DOT        :   DOT(DOT)+ {errors.add(new String[] {String.valueOf(getLine()), "MULTIPLE DOT", getErrorDesc(getInputStream(), getCharIndex())});};
MULTIPLE_COMMA      :   COMMA(COMMA)+ {errors.add(new String[] {String.valueOf(getLine()), "MULTIPLE COMMA", getErrorDesc(getInputStream(), getCharIndex())});};
MULTIPLE_EOL        :   EOL(EOL)+ {errors.add(new String[] {String.valueOf(getLine()), "UNESPECTED END OF LINE", getErrorDesc(getInputStream(), getCharIndex())});};
INVALID_ID          :   [0-9]+ [a-zA-Z0-9_]* {errors.add(new String[] {String.valueOf(getLine()), "INVALID VAR NAME", getText(), getErrorDesc(getInputStream(), getCharIndex())});};
INVALID_OPERATOR    :   ALL_L_OPERATOR[ALL_L_OPERATOR]+ {errors.add(new String[] {String.valueOf(getLine()), "INVALID OPERATOR", getErrorDesc(getInputStream(), getCharIndex())});};
