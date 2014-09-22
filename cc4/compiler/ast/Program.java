package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Program extends Node {
    private List<Node> fieldAndMethod;
        
    public Program () {
	   this.fieldAndMethod = new LinkedList<Node>();       
    }

    public void add(Node node) {
        this.method.add(node);
    }

    public void print(String padding) {
	   System.out.println("PROGRAM -> ");
	   for (Node n: this.list) {
	       n.print(padding + "\t");
	   }
    } 

    public void print() {	   
       this.print("");
    }
}