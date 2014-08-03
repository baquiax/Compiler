package compiler.parser;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.ast.Ast;
import compiler.lib.Configuration;

/**
 * El parser se encarga de validar la sintaxis de las tokens.
 */

public class CC4Parser
{
	public static final int level = 2;
	private Scanner scanner;
	
	public CC4Parser(Scanner scanner) {
		this.scanner = scanner;
	}		
	
	/**
	 * Inicia el proceso del parser.
	 */
	public void parse() {        
        if (Configuration.stopStage >= CC4Parser.level) {
        	System.out.println("stage: PARSE");
        	if (Debug.debugEnabled("parse")) System.out.println("debugging: PARSE");
        }/* else {
        	System.out.println("El proceso se ha detenido.");
        }*/
	}
			
	private boolean validateTokens(String tokens) {
		// TODO implement me
		return false;
	}
		
	public void makeSintaxisTree() {
		// TODO implement me
	}
}
