package compiler.codegen;
import compiler.lib.Debug;
import compiler.irt.Irt;
import compiler.scanner.Scanner;
import compiler.lib.Configuration;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Codegen {
	File create;	
	
	public Codegen(Irt parameter) {

		System.out.println("stage: CODEGEN");
        if (Debug.debugEnabled("codegen")) System.out.println("debugging: CODEGEN");		
	}
			
	public void generate() {
		String output = Scanner.flags.get("-o");
		if (output == null) {
			output = Scanner.flags.get("inputFile");			
			output += ".s";
		}
		File outputFile = new File(output);
		try {
			outputFile.createNewFile();			
			FileWriter fwriter = new FileWriter(output);
			PrintWriter pwriter = new PrintWriter(fwriter);
			pwriter.println("Fin de compilacion.");
			fwriter.close();

		} catch (Exception e) {
			System.err.println("No se ha podido guardar el archivo de salida.");
		}
	}
}
