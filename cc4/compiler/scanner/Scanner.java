package compiler.scanner;
import compiler.lib.Debug;
import compiler.lib.Configuration;
import java.util.Hashtable;
import java.util.ArrayList;
import org.antlr.v4.runtime.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Esta clase represetna el nivel de analisis,
 * el cual se encargara de tokenizar el source-file.
 */

public class Scanner
{
    public static final int level = 1;
    private ArrayList<String> tokens;
    private LexerDecaf ld;

    public Scanner() {
        this.tokens = new ArrayList<String>();
    }
    
    /**
     * Inicia el proceso del Scanner. El Compiler se encarga de validar si el source-file    
     * existe, por lo que si llega aca se puede cargar el archivo en memoria con confianza.     
     */
    
    public void scan() throws Exception {          
        if (Configuration.stopStage >= Scanner.level) {
            System.out.println("stage: SCAN");
            if (Debug.debugEnabled("scan")) System.out.println("debugging: SCAN");
		    LexerDecaf ld = new LexerDecaf(new ANTLRFileStream(Configuration.flags.get("inputFile")));
		    this.ld = ld;
		    while (this.ld.nextToken().getType() != Token.EOF) {}
		    this.ld.reset();
			ArrayList<String[]> rt = this.ld.getRecognizedTokens();
    		this.saveTokensRecognized("recognized.tokens", rt);				
		    ArrayList<String[]> errors = this.ld.getErrors();
		    for (String[] data: errors) {
				System.out.println("LINE " + data[0] + "\t" + data[1]);
				System.out.println(data[2]);
				int size = String.valueOf(data[2]).length() - 1;
				size = (size > 0) ? size : 1;
				System.out.println(String.format("%" + size + "s\n", "^"));
		    }
        }
    }

    private void saveTokensRecognized(String fileName, ArrayList<String[]> tokens) {
		fileName = Configuration.getCurrentFolderPath() + "/" + fileName;
		File outputFile = new File(fileName);
		try {
		    outputFile.createNewFile();
		    FileWriter fwriter = new FileWriter(fileName);
		    PrintWriter pwriter = new PrintWriter(fwriter);
		    for (String[] data : tokens) {
				for (int i = 0; i < data.length; i++) { 
					if (Debug.debugEnabled("scan")) System.out.print(data[i] + "\t");					
				    pwriter.print(data[i] + "\t");
				}
				pwriter.println("");
				if (Debug.debugEnabled("scan")) System.out.println("");
		    }	    
		    fwriter.close();	
		} catch (Exception e) {
		    System.err.println("No se ha podido guardar el archivo de salida.");
		}
    }

    public LexerDecaf getLexer() {
    	return this.ld;
    }
}
