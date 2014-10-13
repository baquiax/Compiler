package compiler.semantic;
import java.util.Hashtable;

public class MethodScope extends Scope {
    private int scopeId;
    private Scope parent;
    private Hashtable<String, Type> table;

    public MethodScope(Scope parent) {
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
	String result = padding + "Scope #" + this.getId() + "\n";
	return result;
    }
}