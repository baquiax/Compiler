package compiler.ast;

public class Negation extends Node implements ILineNumber {
    private Node expr;
    private int line;

    public Negation(Node expr){
	this.expr = expr;
	this.line = -1;
    }
    
    public Node getExpr() {
	return this.expr;
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
	System.out.println(padding + "Negation -> ");
	expr.print(padding + "\t");
    }

    public String toString() {
	if (this.expr.getClass().getName().equals(IntLiteral.class.getName())) {
	    return "-" + String.valueOf(this.expr);
	} else {
	    return "!" + String.valueOf(this.expr);
	}	
    }
}