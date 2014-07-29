package compiler.semantic;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.ast.Ast;
import compiler.irt.Irt;
import compiler.lib.Configuration;

/**
 * Esta clase se encarga de verificar la coherencia de las operaciones.
 * Necesita un AST.
 */

public class Semantic {	

	public static final int level = 4;
	private Ast ast;	
	
	public Semantic(Ast ast) {
		this.ast = ast;
		
		System.out.println("stage: SEMANTIC");
        if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");
	}	
	
	public void checkSemantic() {
		if (Scanner.stopStage > Semantic.level) {
        	Irt irt = new Irt(this);
        	irt.translateAst();
        } else {
        	System.out.println("El proceso se ha detenido.");
        }		
	}
}