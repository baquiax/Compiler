package compiler.codegen;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DataArea {
	String output;
	File outputFile;
	FileWriter fwriter;
	PrintWriter pwriter;

	public DataArea(String outputFile) throws Exception {
		this.outputFile=new File(outputFile);
		fwriter = new FileWriter(output);
		pwriter = new PrintWriter(fwriter);
	}

	public void writeData(File file) {
		pwriter.println(".data\n");
	}
}
