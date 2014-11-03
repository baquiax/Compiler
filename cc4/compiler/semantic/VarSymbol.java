package compiler.semantic;
import compiler.ast.Var;
import compiler.ast.Node;

public class VarSymbol extends Symbol {
    private Var var;
    
    public VarSymbol(Var v) {
	   this.var = v;
    }

    @Override    
    public Node getNode() {
	   return this.var;
    }
    
    public String getType() {
	   return this.var.getType();
    }
}