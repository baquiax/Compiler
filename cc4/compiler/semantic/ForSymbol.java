package compiler.semantic;
import compiler.ast.MethodDecl;
import compiler.ast.For;
import compiler.ast.Node;

public class ForSymbol extends Symbol {
    private ForScope scope;
    private For node;
    
    public ForSymbol(For forStat) {
	this.node = forStat;
	this.scope = new ForScope(Semantic.currentScope);
    }
     
    @Override
    public Node getNode() {
	return this.node;
    }
    
    @Override
    public String getType() {
	return "void";
    }

    public ForScope getScope() {
	return this.scope;
    }
}