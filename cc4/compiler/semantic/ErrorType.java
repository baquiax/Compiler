package compiler.semantic;
import compiler.ast.Node;

public class ErrorType extends Symbol {
    Node node;
    
    public ErrorType(Node n) {
	   this.node = n;
    }        
        
    @Override
    public Node getNode() {
	   return this.node;
    }    

    
    public String getType() {
	   return "error";
    }
}