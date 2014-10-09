package compiler.semantic;
import compiler.ast;

public abstract class Scope {
    public abstract boolean insertSymbol(String k, Node v);
    public abstract Node getSymbol(String k);
}