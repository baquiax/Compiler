package compiler.semantic;
import java.util.Hashtable;
import compiler.ast.Node;

public class SymbolTable {
    private final static Hashtable<String, Node> definedSymbols = new Hashtable<String, Node>();       
    
    public static boolean insertSymbol(String symbol, Node n) {	
	if (SymbolTable.findSymbol(symbol) != null) {
	    return false;
	} else {
	    SymbolTable.definedSymbols.put(symbol, n);
	    return true;
	}
    }
    
    public static Node findSymbol(String symbol) {
	return SymbolTable.definedSymbols.get(symbol);
    }
}
