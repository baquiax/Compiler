package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Program extends Node {
    private List<Node> fieldsAndMethods;
        
    public Program () {
	this.fieldsAndMethods = new LinkedList<Node>();       
    }
    
    public void add(Node node) {
        this.fieldsAndMethods.add(node);
    }
    
    public void print(String padding) {
	System.out.println("PROGRAM -> ");
	for (Node n: this.fieldsAndMethods) {
	    //ADD DECL FIELD and METHOD
	}
    } 
    
    public void print() {	   
	this.print("");
    }
}