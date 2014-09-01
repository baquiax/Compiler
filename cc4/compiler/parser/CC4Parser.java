package compiler.parser;
import compiler.lib.Debug;
import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import org.antlr.v4.runtime.*;
import compiler.scanner.LexerDecaf;
import compiler.parser.ParserDecaf;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
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
        	ArrayList<String[]> errors = this.parser.getErrorsDerivation();
        	this.saveProductions("derivations.context", derivations);
        	/*for (String[] list : derivations) {
        		System.out.println(">>>" + list[0]);
        	}*/
        	if (errors.size()!=0) {
        		saveProductions("derivations.errors", errors);
        		for (String[] list : errors) {
        			System.out.println(">>>" + list[0]);
        		}	
        	}
        	else
        		System.out.println("PARSER EJECUTADO EXITOSAMENTE");
        }
	}
	
	private void saveProductions(String fileName, ArrayList<String[]> productions) {
		fileName = Configuration.getCurrentFolderPath() + "/" + fileName;
		File outputFile = new File(fileName);
		try {
		    outputFile.createNewFile();
		    FileWriter fwriter = new FileWriter(fileName);
		    PrintWriter pwriter = new PrintWriter(fwriter);
		    for (String[] data : productions) {
				for (int i = 0; i < data.length; i++) { 
					if (Debug.debugEnabled("parse")) System.out.print(data[i] + "\t");					
				    pwriter.print(data[i] + "\t");
				}
				pwriter.println("");
				if (Debug.debugEnabled("parse")) System.out.println("");
		    }	    
		    fwriter.close();	
		} catch (Exception e) {
		    System.err.println("No se ha podido guardar el archivo de salida.");
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
