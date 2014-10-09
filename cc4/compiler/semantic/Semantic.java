package compiler.semantic;
import compiler.lib.Debug;
import compiler.parser.AST;
import compiler.lib.Configuration;
import compiler.ast.*;
import java.util.List;

/**
 * Esta clase se encarga de verificar la coherencia de las operaciones.
 * Necesita un AST.
 */

public class Semantic {	

	public static final int level = 4;
	private AST ast;
	private Scope globalScope;
	private Scope currentScope;	
	
	public Semantic(AST parser) {
		this.parser = parser;
		this.globalScope = new ProgramScope();
		this.currentScope = this.globalScope();
	}
	
	public void check(Node n) {
		if (Configuration.stopStage >= Semantic.level) {
        	System.out.println("stage: SEMANTIC");
        	if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");	
        } 

        if (Program.class.getName(n.getClass().getName)) {

        } else {

        }
	}

	public void checkProgram(Program p) {
		for(FieldDecl f : p.getFields()) {
			this.checkFieldDecl(f);
		}

		for(MethodDecl m : p.getMethods()) {
			this.checkMethodDecl(m);
		}
	}
}
