package compiler.semantic;
import compiler.ast.Var;
import compiler.ast.Node;

public class VarType extends Type {
    private Var var;
    
    public VarType(Var v) {
	this.var = v;
    }

    @Override    
    public Node getNode() {
	return this.var;
    }

    @Override
    public String getType() {
	return this.var.getType();
    }
}