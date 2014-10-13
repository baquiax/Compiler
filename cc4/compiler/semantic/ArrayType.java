package compiler.semantic;
import compiler.ast.Array;
import compiler.ast.Node;

public class ArrayType extends Type {
    private Array array;
    
    public ArrayType(Array a) {
	this.array = a;
    }

    @Override    
    public Node getNode() {
	return this.array;
    }

    @Override
    public String getType() {
	return this.array.getType();
    }

    
}