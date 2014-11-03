package compiler.semantic;
import java.util.Hashtable;

public class ProgramScope extends Scope {
    private Hashtable<String, Symbol> table;
    
    public ProgramScope() {
		this.table = new Hashtable<String, Symbol>();
		Scope.scopes++;
    }
    
    @Override
    public int getId() {
        return 1;
    }
    
    @Override
    public boolean insertSymbol(String k, Symbol t) {
		if (this.getSymbol(k) == null) {
		    this.table.put(k, t);
		    return true;
		} else {
		    return false;
		}
    }
    
    @Override
    public Symbol getSymbol(String k) {
		return this.table.get(k);
    }    

    @Override
    public Scope getParent() {
		//It's the first ancestor.
		return null;
    }

    public String getScopeType() {
        return "program";
    }

    public void print() {
		System.out.println(this.toString(""));
    }

    public String toString(String padding) {
		String result = "---Scope #" + this.getId() + "---\n";
		for (String k: this.table.keySet()) {
		    Symbol s = this.table.get(k);
		    result += k + "\t " + s.getType() + "\n";
		    if (s.getClass().getName().equals(MethodSymbol.class.getName())) {
				MethodSymbol m = (MethodSymbol) s;
				result += m.getScope().toString(padding + "\t");
		    }
		}
		return result;
    }
}