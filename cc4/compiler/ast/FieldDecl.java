package compiler.ast;
import compiler.ast.Var;
import java.util.List;
import java.util.LinkedList;

public class FieldDecl extends Node {
    private String type;
    private List<Var> fields;
    
    public FieldDecl(String type) {
    	this.type = type;
    	this.fields = new LinkedList<Node>();
    }

    public void add(Var v) {
	   this.fields.add(v);
    }
    
    public List<Node> getFields() {
        return fields;
    }

    public void print(String padding) {
    	System.out.println(padding + "FieldsDecl (Type: " + this.type + ") -> ");
    	for (Node n : fields) {
    	    n.print(padding + "\t");
    	}
    }
}