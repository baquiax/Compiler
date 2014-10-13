package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Block extends Node {
    private List<Node> varDecls;
    private List<Node> statements;
    
    public Block() {
	this.varDecls = new LinkedList<Node>();
	this.statements = new LinkedList<Node>();
    }
    
    public List<Node> getVarDecl() {
	return this.varDecls;
    }

    public List<Node> getStatements() {
	return this.statements;
    }
    
    public void print(String padding) {		
	System.out.println(padding + "Block[Decls] ->");
	for(Node n : this.varDecls) {
	    n.print(padding + "\t");			
	}
	System.out.println(padding + "Block[Statements] ->");
	for(Node n : this.statements) {
	    n.print(padding + "\t");
	}
    }
    
    public void addVarDecl(Node n) {
	this.varDecls.add(n);
    }
    
    public void addStatement(Node n) {
	this.statements.add(n);
    }
    
    public void print() {
	this.print("");
    }
}