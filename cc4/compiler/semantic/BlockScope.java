package compiler.semantic;
import compiler.ast.Node;
import java.util.List;

public class BlockScope extends Scope {

    public BlockScope() {
	
    }
    
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void addScope(Scope s) {
        
    }

    @Override
    public List<Scope> getScopes() {
        return null;
    }

    @Override
    public Node getSymbol(String key) {
    	return null;
    }

    @Override
    public boolean insertSymbol(String k, Node n) {
    	return true;
    }
}