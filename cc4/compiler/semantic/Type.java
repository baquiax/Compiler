package compiler.semantic;
import compiler.ast.Node;

public abstract class Type {
    public abstract Node getNode();
    public abstract String getType();
}