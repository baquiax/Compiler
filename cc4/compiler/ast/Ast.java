package compiler.ast;
import compiler.lib.Debug;
import compiler.semantic.Semantic;
import compiler.lib.Configuration;

/**
 * Esta clase representa al Arbol de sintaxis.
 */

public class Ast
{	
	public static final int level = 4;
	private Semantic semantic;
	
	public Ast(Semantic semantic) {
		this.semantic = semantic;
	}
			
	public void makeTree() {
		if (Configuration.stopStage >= Ast.level) {
        	System.out.println("stage: AST");
        	if (Debug.debugEnabled("ast")) System.out.println("debugging: AST");
        } /*else {
        	System.out.println("El proceso se ha detenido.");
        }*/		
	}
}
