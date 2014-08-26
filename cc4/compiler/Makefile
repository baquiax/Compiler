make: scanner/LexerDecaf.java parser/ParserDecaf.java lib/Debug.class lib/ErrorHandler.class scanner/Scanner.class parser/CC4Parser.class ast/Ast.class semantic/Semantic.class irt/Irt.class opt/Algebraic.class opt/ConstantFolding.class codegen/Codegen.class Compiler.class lib/Configuration.class scanner/LexerDecaf.class  parser/ParserDecaf.class

Compiler.class: Compiler.java
	javac Compiler.java

lib/Debug.class: lib/Debug.java
	javac lib/Debug.java

lib/ErrorHandler.class: lib/ErrorHandler.java
	javac lib/ErrorHandler.java

scanner/Scanner.class: scanner/Scanner.java
	javac scanner/Scanner.java

parser/CC4Parser.class: parser/CC4Parser.java
	javac parser/CC4Parser.java

ast/Ast.class: ast/Ast.java
	javac ast/Ast.java

semantic/Semantic.class: semantic/Semantic.java
	javac semantic/Semantic.java

irt/Irt.class: irt/Irt.java
	javac irt/Irt.java

opt/Algebraic.class: opt/Algebraic.java
	javac opt/Algebraic.java

opt/ConstantFolding.class: opt/ConstantFolding.java
	javac opt/ConstantFolding.java

codegen/Codegen.class: codegen/Codegen.java
	javac codegen/Codegen.java

lib/Configuration.class: lib/Configuration.java
	javac lib/Configuration.java

scanner/LexerDecaf.class: scanner/LexerDecaf.java
	javac scanner/LexerDecaf.java

scanner/LexerDecaf.java: scanner/LexerDecaf.g
	java org.antlr.v4.Tool scanner/LexerDecaf.g

parser/ParserDecaf.class: parser/ParserDecaf.java
	javac parser/ParserDecaf.java

parser/ParserDecaf.java: parser/ParserDecaf.g
	java org.antlr.v4.Tool -lib scanner parser/ParserDecaf.g

clean:
	rm -f Compiler.class
	rm -f scanner/Scanner.class
	rm -f parser/CC4Parser.class
	rm -f ast/Ast.class
	rm -f semantic/Semantic.class
	rm -f irt/Irt.class
	rm -f opt/Algebraic.class
	rm -f opt/ConstantFolding.class
	rm -f codegen/Codegen.class
	rm -f lib/Debug.class
	rm -f lib/ErrorHandler.class
	rm -f lib/Configuration.class
	rm -rf scanner/*.class scanner/*~ scanner/*.tokens
	rm -rf parser/*.class parser/*~ parser/*.tokens
	rm -rf scanner/LexerDecaf.java
	rm -rf parser/ParserDecaf.java
	rm -rf parser/ParserDecafBaseListener.java
	rm -rf parser/ParserDecafListener.java
