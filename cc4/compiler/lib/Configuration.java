package compiler.lib;
import java.util.Hashtable;

public class Configuration {

	public static Hashtable flags;
	public static int stopStage;

	public Configuration(Hashtable < String, String > flags, int stopStage) {
		this.flags=flags;
		this.stopStage=stopStage;
	}
}