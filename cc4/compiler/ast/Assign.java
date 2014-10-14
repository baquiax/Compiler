package compiler.ast;

public class Assign extends Node implements ILineNumber{
    private String operator;
    private Node location;
    private Node expr;
    private int line;
    public Assign(String operator, Node v , Node expr) {
	this.operator = operator;
	this.location = v;
	this.expr = expr;
    }
 
    public String getOperator() {
	return this.operator;
    }

    public Node getLocation() {
	return this.location;
    }        
    
    public Node getE() {
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
	System.out.println(padding +  this.operator + " -> ");
	System.out.println(padding + "OP1:");
	location.print(padding + "\t");
	System.out.println(padding + "OP2:");
	expr.print(padding + "\t");
    }
    
    public String toString() {
	return location + " " + operator + " " + expr + ";";
    }
}