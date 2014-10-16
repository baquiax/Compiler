package compiler.ast;

public class If extends Node implements ILineNumber {
    private Node condition;
    private Node consecuent;
    private Node alternative;
    private int line;
    
    public If(Node c, Node con) {
	this.condition = c;
	this.consecuent = con;		
	this.line = -1;
    }
    
    public If(Node c, Node con, Node alt ) {
	this.condition = c;
	this.consecuent = con;
	this.alternative = alt;
    }

    public Node getCondition() {
	return this.condition;
    }
    
    public Node getConsecuent() {
	return this.consecuent;
    }

    public Node getAlternative() {
	return this.alternative;
    }

    public void setLineNumber(int l) {
	this.line = l;
    }

    public int getLineNumber() {
	return this.line;
    }

    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + "IF ->");
	this.condition.print(padding + "\t");
	System.out.println(padding + "THEN ->");
	this.consecuent.print(padding + "\t");
	if (this.alternative != null) {
	    System.out.println(padding + "ELSE ->");
	    this.alternative.print(padding + "\t");
	}
    }

    public String toString() {
	return "if("  + this.condition + ")";
    }
}