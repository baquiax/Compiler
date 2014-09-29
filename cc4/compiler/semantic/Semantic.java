package compiler.semantic;
import compiler.lib.Debug;
import compiler.parser.CC4Parser;
import compiler.lib.Configuration;

/**
 * Esta clase se encarga de verificar la coherencia de las operaciones.
 * Necesita un AST.
 */

public class Semantic {	

	public static final int level = 4;
	private CC4Parser parser;	
	
	public Semantic(CC4Parser parser) {
		this.parser = parser;
	}	
	
	public void checkSemantic() {
		if (Configuration.stopStage >= Semantic.level) {
        	System.out.println("stage: SEMANTIC");
        	if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");	
        }/* else {
        	System.out.println("El proceso se ha detenido.");
        }*/		
	}
}
