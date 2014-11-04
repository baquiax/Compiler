package compiler.codegen;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class TextArea {
	String output;
	File outputFile;
	FileWriter fwriter;
	PrintWriter pwriter;

	public TextArea(String outputFile) throws Exception {
		this.outputFile=new File(outputFile);
		fwriter = new FileWriter(output);
		pwriter = new PrintWriter(fwriter);
	}

	public void writeText(File file) {
		pwriter.println(".text\n");
	}
}
