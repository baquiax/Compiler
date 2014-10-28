package compiler.semantic;
import compiler.ast.Array;
import compiler.ast.Node;

public class ArraySymbol extends Symbol {
    private Array array;
    
    public ArraySymbol(Array a) {
	   this.array = a;
    }

    @Override    
    public Node getNode() {
	   return this.array;
    }

    @Override
    public String getSymbol() {
	   return this.array.getSymbol();
    }
}