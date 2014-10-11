package compiler.semantic;
import compiler.lib.Debug;
//import compiler.parser.AST;
import compiler.ast.Ast;
import compiler.lib.Configuration;
import compiler.ast.*;
import java.util.List;

/**
 * Esta clase se encarga de verificar la coherencia de las operaciones.
 * Necesita un AST.
 */

public class Semantic {	

	public static final int level = 4;
	private Ast ast;
	private Scope globalScope;
	private Scope currentScope;	
	
	public Semantic(Ast ast) {
		this.ast = ast;
		//this.globalScope = new ProgramScope();
		//this.currentScope = this.globalScope();
	}
	
	public void check() {
		if (Configuration.stopStage >= Semantic.level) {
        	System.out.println("stage: SEMANTIC");
        	if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");
        	checkProgram(ast.getProgram());
        }

	}

	public void checkProgram(Node n) {
		
	}
}
