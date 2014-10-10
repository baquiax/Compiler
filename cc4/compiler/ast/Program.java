package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Program extends Node {
    private List<FieldDecl> fields;
    private List<MethodDecl> methods;
        
    public Program () {
	   this.fields = new LinkedList<FieldDecl>();
       this.methods = new LinkedList<MethodDecl>();
    }
    
    public void addField(FieldDecl node) {
        this.fields.add(node);
    }
    
    public List<FieldDecl> getFields() {
        return this.fields;
    }

    public void addMethod(MethodDecl node) {
        this.methods.add(node);
    }

    public List<MethodDecl> getMethods() {
        return this.methods;    
    }

    public void print(String padding) {
	   System.out.println("PROGRAM -> ");
	   for (Node n: this.fields) {
           n.print(padding + "\t");
       }
       for (Node n: this.methods) {
	       n.print(padding + "\t");
	   }
    } 
    
    public void print() {	   
	   this.print("");	
    }
}