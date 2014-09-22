package compiler.ast;
import compiler.lib.Debug;
import compiler.parser.CC4Parser;
import compiler.lib.Configuration;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * Esta clase representa al Arbol de sintaxis.
 */

public class Ast {	
    public static final int level = 3;
    private CC4Parser parser;
    
    public Ast(CC4Parser parser) {
	this.parser = parser;
    }
    
    public void makeTree() {
	if (Configuration.stopStage >= Ast.level) {
	    System.out.println("stage: AST");
	    if (Debug.debugEnabled("ast")) System.out.println("debugging: AST");
	    ParseTree tree = this.parser.getParser().start();
	    AstVisitor visitor = new AstVisitor();
	    Program program = (Program) visitor.visit(tree);
	    program.print();
	}
	
    }
}
