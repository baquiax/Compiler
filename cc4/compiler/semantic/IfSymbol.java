package compiler.semantic;
import compiler.ast.Node;
import compiler.ast.If;

public class IfSymbol extends Symbol {
	private BlockScope consecuentScope;
	private BlockScope alternativeScope;
	private If ifStatement;

	public IfSymbol(If ifStat) {
		this.ifStatement = ifStat;
		this.consecuentScope = new BlockScope(Semantic.currentScope, "if");
		if (ifStat.getAlternative() != null) {
			this.alternativeScope = new BlockScope(Semantic.currentScope,"if-else");
		}
	}

	public BlockScope getConsecuentScope() {
		return this.consecuentScope;
	}

	public BlockScope getAlternativeScope() {
		return this.alternativeScope;
	}

	@Override    
    public Node getNode() {
	   return ifStatement;
    }
    
    public String getType() {
	   return "void";
    }
}