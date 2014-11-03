package compiler.semantic;
import compiler.ast.MethodDecl;
import compiler.ast.Node;
import compiler.ast.Var;
import compiler.ast.Array;

public class MethodSymbol extends Symbol {
    private MethodScope scope;
    private MethodDecl node;
    
    public MethodSymbol(MethodDecl method) {
	   this.node = method;
	   this.scope = new MethodScope(Semantic.currentScope);
    }
        
    public MethodDecl getMethodDecl() {
	   return this.node;
    }
        
    public String getType() {
	   return this.node.getReturnType();
    }

    public Node getNode() {
        return this.node;
    }

    public MethodScope getScope() {
	   return this.scope;
    }
}
