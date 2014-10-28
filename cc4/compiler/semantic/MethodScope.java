package compiler.semantic;
import java.util.Hashtable;
import compiler.ast.MethodDecl;

public class MethodScope extends Scope {
    private int scopeId;
    private Scope parent;
    private MethodDecl method;
    private Hashtable<String, Symbol> table;
    private boolean hasReturn;
    
    public MethodScope(Scope parent, MethodDecl m) {
	this.parent = parent;
	this.table = new Hashtable<String, Symbol>();
	this.method = m;
	this.scopeId = ++Scope.scopes;
    }

    @Override
    public int getId() {
	return this.scopeId;
    }

    @Override
    public boolean insertSymbol(String n, Symbol t) {
	if (this.getSymbol(n) != null) {
	    return false;
	} else {
	    this.table.put(n, t);
	    return true;
	}
    }
    
    @Override
    public Symbol getSymbol(String n) {
	return this.table.get(n);
    }

    @Override
    public Scope getParent() {
	return this.parent;
    }

    public void returnFound(boolean b) {
	this.hasReturn = b;
    }
    
    public boolean isReturnFound() {
	return this.hasReturn;
    }

    public MethodDecl getMethod() {
	return this.method;
    }

    public String toString(String padding) {
	String result = "\n" + padding + "---Scope #" + this.getId() + "---\n";
	for (String k: this.table.keySet()) {
	    Symbol t = this.table.get(k);
	    result += padding + k + "\t " + t.getClass().getName() + "\t" + t.getType() + "\n";

	    if (t.getClass().getName().equals(ForSymbol.class.getName())) {
		ForSymbol f = (ForSymbol) t;
		result += f.getScope().toString(padding + "\t");
	    } else if (t.getClass().getName().equals(BlockType.class.getName())) {
		BlockType b = (BlockType) t;
		result += b.getScope().toString(padding + "\t");
	    }
	}
	return result + "\n";
    }
}