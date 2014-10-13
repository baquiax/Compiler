package compiler.semantic;
import java.util.Hashtable;

public class ProgramScope extends Scope {
    private Hashtable<String, Type> table;
    
    public ProgramScope() {
	this.table = new Hashtable<String, Type>();
	Scope.scopes++;
    }
    
    @Override
    public int getId() {
        return 1;
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
	return null;
    }

    public void print() {
	System.out.println(this.toString(""));
    }

    public String toString(String padding) {
	String result = "---Scope #" + this.getId() + "---\n";
	for (String k: this.table.keySet()) {
	    Type t = this.table.get(k);
	    result += k + "\t " + t.getClass().getName() + "\t" + t.getType() + "\n";
	    if (t.getClass().getName().equals(MethodType.class.getName())) {
		MethodType m = (MethodType) t;
		result += m.getScope().toString(padding + "\t");
	    }
	}
	return result;
    }
}