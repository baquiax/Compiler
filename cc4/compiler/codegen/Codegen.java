package compiler.codegen;
import compiler.lib.Debug;
import compiler.irt.Irt;
import compiler.lib.Configuration;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Codegen {
	public static final int level = 6;
	File outputFile;
	Irt irt;
	DataArea dataArea;

	public Codegen(Irt irt) {
		String output = Configuration.flags.get("-o");
		this.irt=irt;
		if (output == null) {
			output = Configuration.flags.get("inputFile");			
			output += ".s";
		}

		try {
			outputFile = new File(output);
			outputFile.createNewFile();
			FileWriter fwriter = new FileWriter(outputFile);
			PrintWriter pwriter = new PrintWriter(fwriter);
			pwriter.println(".data\n\n");
			pwriter.println(".text\n\n");

			fwriter.close();
		} catch (Exception e) {
			System.err.println("No se ha podido guardar el archivo de salida.");
		}
	}

	public void generate() {
		if (Configuration.stopStage >= Codegen.level) {
			System.out.println("stage: CODEGEN");
        	if (Debug.debugEnabled("codegen")) System.out.println("debugging: CODEGEN");
		}	
	}
}
