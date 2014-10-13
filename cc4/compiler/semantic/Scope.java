package compiler.semantic;
import compiler.ast.*;
import java.util.List;

public abstract class Scope {	
	public static int scopes = 0;
	public abstract int getId();
    public abstract boolean insertSymbol(String k, Node v);
    public abstract Node getSymbol(String k);
    public abstract void addScope(Scope s);
    public abstract List<Scope> getScopes();
}