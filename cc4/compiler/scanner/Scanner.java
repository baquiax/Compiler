package compiler.scanner;
import compiler.parser.CC4Parser;
import compiler.lib.Debug;
import compiler.lib.Configuration;
import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Esta clase represetna el nivel de analisis,
 * el cual se encargara de tokenizar el source-file.
 */

public class Scanner
{
    public static final int level = 1;
    public static Hashtable < String, String > flags;
    public static int stopStage;
    private ArrayList<String> tokens;
        
    public Scanner(Hashtable < String, String > flags, int stopStage) {
        this.tokens = new ArrayList<String>();
        Scanner.stopStage = stopStage;
        Scanner.flags = flags;

        System.out.println("stage: SCAN");
        if (Debug.debugEnabled("scan")) System.out.println("debugging: SCAN");
    }
    
    /**
     * Inicia el proceso del Scanner. El Compiler se encarga de validar si el source-file    
     * existe, por lo que si llega aca se puede cargar el archivo en memoria con confianza.     
     */
    
    public void scan() {          
        if (Configuration.stopStage == Scanner.level) {
            System.out.println("stage: SCAN");
            if (Debug.debugEnabled("scan")) System.out.println("debugging: SCAN");
        } else {
            System.out.println("El proceso se ha detenido.");
        }
    }
}
