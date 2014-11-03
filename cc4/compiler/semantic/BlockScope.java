package compiler.semantic;
import java.util.Hashtable;

public class BlockScope extends Scope {
    private int scopeId;
    private Scope parent;
    private String scopeType;
    private Hashtable<String, Symbol> table;
    
    public BlockScope(Scope parent, String scopeType) {
		this.parent = parent;
		this.scopeType = scopeType;
		this.table = new Hashtable<String, Symbol>();
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

    public String getScopeType() {
        return this.scopeType;
    }

    public String toString(String padding) {
		String result = "\n" + padding + "---Scope #" + this.getId() + "---\n";
		for (String k: this.table.keySet()) {
		    Symbol s = this.table.get(k);
		    result += padding + k + "\t " + s.getClass().getName() + "\t" + s.getType() + "\n";
		    if (s.getClass().getName().equals(ForSymbol.class.getName())) {
				ForSymbol f = (ForSymbol) s;
				result += f.getScope().toString(padding + "\t");
		    } else if (s.getClass().getName().equals(IfSymbol.class.getName())) {
				IfSymbol i = (IfSymbol) s;
				result += i.getConsecuentScope().toString(padding + "\t");
				if (i.getAlternativeScope() != null) {
					result += i.getAlternativeScope().toString(padding + "\t");	
				}				
		    }
		}
		return result + "\n";
    }
}