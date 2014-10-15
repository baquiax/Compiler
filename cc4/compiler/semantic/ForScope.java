package compiler.semantic;
import java.util.Hashtable;

public class ForScope extends Scope {
    private int scopeId;
    private Scope parent;
    private Hashtable<String, Type> table;

    public ForScope(Scope parent) {
	this.parent = parent;
	this.table = new Hashtable<String, Type>();
	this.scopeId = ++Scope.scopes;
    }

    @Override
    public int getId() {
	return this.scopeId;
    }

    @Override
    public boolean insertSymbol(String n, Type t) {
	if (this.getSymbol(n) != null) {
	    return false;
	} else {
	    this.table.put(n, t);
	    return true;
	}
    }
    
    @Override
    public Type getSymbol(String n) {
	return this.table.get(n);
    }

    @Override
    public Scope getParent() {
	return this.parent;
    }

    public String toString(String padding) {
	String result = "\n" + padding + "---Scope #" + this.getId() + "---\n";
	for (String k: this.table.keySet()) {
	    Type t = this.table.get(k);
	    result += padding + k + "\t " + t.getClass().getName() + "\t" + t.getType() + "\n";
	    if (t.getClass().getName().equals(BlockType.class.getName())) {
		BlockType b = (BlockType) t;
		result += b.getScope().toString(padding + "\t");
	    }
	}
	return result + "\n";
    }
}