package compiler.lib;
import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import java.util.Hashtable;

/**
 * Esta clase es tuilizada para debugear el proceoso de compilacion.
 * Hagran metodos destinados a formatear los mensajes, e incluso se
 * podra especificar un archivo de salida para almacenar la informacion
 * como log.
 */

public class Debug {

	private String logPath;
		
	public Debug(){
		
	}

	public void printError(String parameter, String parameter2) {
		// TODO implement me	
	}
	

	public void printWarning(String parameter, String parameter2) {
		// TODO implement me	
	}
		
	
	public String setLogPath() {
		// TODO implement me
		return "";	
	}

	public static boolean debugEnabled(String level) {	
		Hashtable<String, String> flags = Configuration.flags;
		if (flags.get("-debug") != null) {
			for (String l : flags.get("-debug").split(":")) {
				if (l.equals(level)) return true;
			}
		}
		return false;
	}
	
}
