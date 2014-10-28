package compiler.semantic;
import compiler.ast.Node;

public abstract class Symbol {
    public abstract Node getNode();
    public abstract String getType();
}