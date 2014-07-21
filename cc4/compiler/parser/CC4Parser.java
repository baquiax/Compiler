package compiler.parser;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.ast.Ast;

/**
 * El parser se encarga de validar la sintaxis de las tokens.
 */

public class CC4Parser
{
	public static final int level = 2;
	private Scanner scanner;
	
	public CC4Parser(Scanner scanner) {
		this.scanner = scanner;

		System.out.println("stage: PARSE");
        if (Debug.debugEnabled("parse")) System.out.println("debugging: PARSE");
	}		
	
	/**
	 * Inicia el proceso del parser.
	 */
	public void parse() {        
        if (Scanner.stopStage > CC4Parser.level) {
        	Ast ast = new Ast(this);
        	ast.makeTree();
        } else {
        	System.out.println("El proceso se ha detenido.");
        }
	}
			
	private boolean validateTokens(String tokens) {
		// TODO implement me
		return false;
	}
		
	public void makeSintaxisTree() {
		// TODO implement me
		
	}
}
