package compiler.scanner;
import compiler.lib.Debug;
import compiler.lib.Configuration;
import java.util.Hashtable;
import java.util.ArrayList;
import org.antlr.v4.runtime.*;

/**
 * Esta clase represetna el nivel de analisis,
 * el cual se encargara de tokenizar el source-file.
 */

public class Scanner
{
    public static final int level = 1;
    private ArrayList<String> tokens;
    
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
	    while (ld.nextToken().getType() != Token.EOF) {}
	    ArrayList<String[]> rt = ld.getRecognizedTokens();
	    ArrayList<String[]> errors = ld.getErrors();
	    for (String[] data: errors) {
		for (int i = 0; i < data.length; i++) {
		    System.out.print(data[i] + "\t");
		}
		System.out.println("");
	    }
        }
    }
}
