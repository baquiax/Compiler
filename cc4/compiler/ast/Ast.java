package compiler.ast;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.semantic.Semantic;
import compiler.parser.CC4Parser;

/**
 * Esta clase representa al Arbol de sintaxis.
 */

public class Ast
{	
	public static final int level = 3;
	private CC4Parser parser;
	
	public Ast(CC4Parser parser) {
		this.parser = parser;

		System.out.println("stage: AST");
        if (Debug.debugEnabled("ast")) System.out.println("debugging: AST");
	}
			
	public void makeTree() {
		if (Scanner.stopStage > Ast.level) {
        	Semantic semantic = new Semantic(this);
        	semantic.checkSemantic();
        } else {
        	System.out.println("El proceso se ha detenido.");
        }		
	}
	
}
