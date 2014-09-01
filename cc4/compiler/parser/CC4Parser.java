package compiler.parser;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import org.antlr.v4.runtime.*;
import compiler.scanner.LexerDecaf;
import compiler.parser.ParserDecaf;
import java.util.ArrayList;
import java.util.List;
/**
 * El parser se encarga de validar la sintaxis de las tokens.
 */

public class CC4Parser
{
	public static final int level = 2;
	private Scanner scanner;
	private ParserDecaf parser;
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

        	
        	this.parser = new ParserDecaf(new CommonTokenStream(this.scanner.getLexer()));
        	this.parser.start();
        	ArrayList<String[]> derivations = this.parser.getDerivations();
        	for (String[] list : derivations) {
        		System.out.println(">>>" + list[0]);
        	}
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
