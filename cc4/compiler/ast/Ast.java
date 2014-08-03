package compiler.ast;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.semantic.Semantic;
import compiler.parser.CC4Parser;
import compiler.lib.Configuration;

/**
 * Esta clase representa al Arbol de sintaxis.
 */

public class Ast
{	
	public static final int level = 3;
	private CC4Parser parser;
	
	public Ast(CC4Parser parser) {
		this.parser = parser;
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
