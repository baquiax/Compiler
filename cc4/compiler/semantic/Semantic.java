package compiler.semantic;
import compiler.lib.Debug;
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
	private ErrorType error;
	private Scope globalScope;
	private Scope currentScope;	
	
	public Semantic(Ast ast) {
		this.ast = ast;
		error = new ErrorType();
		this.globalScope = new ProgramScope();
		this.currentScope = this.globalScope;
	}
	
	public void check() {
		if (Configuration.stopStage >= Semantic.level) {
        	System.out.println("stage: SEMANTIC");
        	if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");
        	checkProgram(ast.getProgram());
        }
	}

	public void checkProgram(Program p) {
		List<FieldDecl> fieldDecls = p.getFields();
		for (FieldDecl f : fieldDecls) {
			for (Var v : f) {
				this.currentScope.insertSymbol(v.getName(), v);	
			}			
		}

		List<MethodDecl> methodsDecls =	p.getMethods();
		for (MethodDecl m : methodsDecls) {

		}
	}

	public void checkAssign() {

	}

	public void checkType() {

	}

	public void checkIf() {

	}

	public void checkFor() {

	}

	public void checkArray() {

	}

	public void checkVars() {
		
	}
}
