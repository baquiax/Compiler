package compiler.semantic;
import compiler.ast.MethodDecl;
import compiler.ast.For;
import compiler.ast.Node;

public class ForSymbol extends Symbol {
    private BlockScope scope;
    private For node;
    
    public ForSymbol(For forStat) {
	   this.node = forStat;
	   this.scope = new BlockScope(Semantic.currentScope,"for");
    }
     
    @Override
    public Node getNode() {
	   return this.node;
    }
        
    public String getType() {
	   return "void";
    }

    public BlockScope getScope() {
	   return this.scope;
    }
}