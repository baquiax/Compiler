package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class FieldDecl extends Node implements ILineNumber {
    private String type;
    private List<Node> fields;
    private int line;
    
    public FieldDecl(String type) {
    	this.type = type;
    	this.fields = new LinkedList<Node>();
	this.line = -1;
    }

    public void add(Node v) {
	this.fields.add(v);
    }
    
    public List<Node> getFields() {
        return fields;
    }

    public void setLineNumber(int l) {
	this.line = l;
    }

    public int getLineNumber() {
	return this.line;
    }

    public void print(String padding) {
    	System.out.println(padding + "FieldsDecl (Type: " + this.type + ") -> ");
    	for (Node n : fields) {
    	    n.print(padding + "\t");
    	}
    }

    public String toString() {
	String result = type + " ";
	for (int i = 0 ; i < this.fields.size(); i++) {
	    result += this.fields.get(i);
	    if (i < (this.fields.size() - 1)) {
		result += ", ";
	    }
	}	
	return result + ";";
    }
}