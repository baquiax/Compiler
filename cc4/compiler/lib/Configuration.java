package compiler.lib;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Configuration {

    public static Hashtable < String, String > flags;
    public static int stopStage;
    
    public static String getCurrentFolderPath() {
	String folder = "./";
	if (Configuration.flags.get("inputFile") != null) {
	    String inputFile = Configuration.flags.get("inputFile");
	    int hasSlash = inputFile.lastIndexOf("/");
	    if (hasSlash != -1) {
		folder = inputFile.substring(0,hasSlash);
	    }
	}
	return folder;
    } 

    public static void makeOutput(String fileName, String out) {
	File outputFile = new File(fileName);
	try {
	    outputFile.createNewFile();			
	    FileWriter fwriter = new FileWriter(fileName);
	    PrintWriter pwriter = new PrintWriter(fwriter);
	    pwriter.println(out);
	    fwriter.close();
	    
	} catch (Exception e) {
	    System.err.println("No se ha podido guardar el archivo de salida.");
	}
    }
}