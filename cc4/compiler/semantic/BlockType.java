package compiler.semantic;
import compiler.ast.Block;
import compiler.ast.Node;

public class BlockType extends Type {
    private BlockScope scope;
    private Block node;
    
    public BlockType(Block b) {
	this.node = b;
	this.scope = new BlockScope(Semantic.currentScope);	
    }
     
    @Override
    public Node getNode() {
	return this.node;
    }
    
    @Override
    public String getType() {
	return "void";
    }

    public BlockScope getScope() {
	return this.scope;
    }
}
