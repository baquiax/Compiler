package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class FieldDecl extends Node {
    private String type;
    private List<Node> fields;
    
    public FieldDecl(String type) {
    	this.type = type;
    	this.fields = new LinkedList<Node>();
    }

    public void add(Node n) {
	   this.fields.add(n);
    }
    
    public void print(String padding) {
    	System.out.println(padding + "FieldsDecl (Type: " + this.type + ") -> ");
    	for (Node n : fields) {
    	    n.print(padding + "\t");
    	}
    }
}