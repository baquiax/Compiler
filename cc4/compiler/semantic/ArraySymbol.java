package compiler.semantic;
import compiler.ast.Array;
import compiler.ast.Node;

public class ArraySymbol extends Symbol {
    private Array array;
    
    public ArraySymbol(Array a) {
	   this.array = a;
    }
    
    public Node getNode() {
	   return this.array;
    }
    
    public String getType() {
	   return this.array.getSymbol();
    }
}