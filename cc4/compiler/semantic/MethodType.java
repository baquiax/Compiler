package compiler.semantic;
import compiler.ast.MethodDecl;
import compiler.ast.Node;

public class MethodType extends Type {
    private MethodScope scope;
    private MethodDecl node;
    
    public MethodType(MethodDecl method) {
	this.node = method;
	this.scope = new MethodScope(Semantic.currentScope, node);
    }
     
    @Override
    public Node getNode() {
	return this.node;
    }
    
    @Override
    public String getType() {
	return this.node.getReturnType();
    }

    public MethodScope getScope() {
	return this.scope;
    }
}
