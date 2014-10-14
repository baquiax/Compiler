package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Args extends Node {	
    private List<Node> args;
    
    public Args() {
	this.args = new LinkedList<Node>();
    }
    
    public void addArg(Node a) {
	this.args.add(a);
    }
    
    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + "Args -> ");
	for (Node n : this.args) {
	    n.print(padding + "\t");
	}
    }	

    public String toString() {
	String result = "";
	for (int i = 0 ; i < this.args.size(); i++) {
	    result += this.args.get(i);
	    if (i < (this.args.size() - 1)) {
		result += ", ";
	    }
	}	
	return result;
    }
}