package compiler.semantic;
import java.util.List;

public abstract class Scope {	
    public static int scopes = 0;
    public abstract int getId();
    public abstract boolean insertSymbol(String k, Symbol t);
    public abstract Symbol getSymbol(String k);
    public abstract Scope getParent();
    public abstract String toString(String padding);
}