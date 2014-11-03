package compiler.semantic;
import compiler.ast.Node;

public abstract class Symbol {    
    public abstract String getType();
    public abstract Node getNode();
}