package compiler.lib;
import java.util.Hashtable;

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
}