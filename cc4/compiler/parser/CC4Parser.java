package compiler.parser;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import org.antlr.v4.runtime.*;
import compiler.scanner.LexerDecaf;
import compiler.parser.ParserDecaf;
import java.util.ArrayList;
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
	        

        	/*try {     
  				(new ParserDecaf(new CommonTokenStream(new LexerDecaf(new ANTLRFileStream(Configuration.flags.get("inputFile")))))).start();
    		} catch (ArrayIndexOutOfBoundsException aiobe) {
      			System.err.println("usage: java Main <file>\nwhere file is the path to the filename with the tokens");
      			System.exit(1);
    		} catch (Exception e) {
      			System.err.println("usage: jaca Main <file>\nwhere file is the path to the filename with the tokens");
      			System.exit(1);
    		}*/
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
