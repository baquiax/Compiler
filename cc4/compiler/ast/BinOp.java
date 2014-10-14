package compiler.ast;

public class BinOp extends Node implements ILineNumber{
    private String operator;
    private Node leftOperator;
    private Node rightOperator;
    private int line;
    
    public BinOp(String o, Node lo, Node ro) {
	this.operator = o;
	this.leftOperator = lo;
	this.rightOperator = ro;
	this.line = -1;
    }
    
    public void setLineNumber(int l) {
	this.line = l;
    }

    public int getLineNumber() {
	return this.line;
    }        
    
    public Node getFirst() {
	return this.leftOperator;
    }
    
    public Node getSecond() {
	return this.rightOperator;
    }
    
    public String getOperator() {
        return this.operator;
    }
    
    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + this.operator + " -> ");
	System.out.println(padding + "Left operator");
	this.leftOperator.print(padding + "\t");
	System.out.println(padding + "Right operator");
	this.rightOperator.print(padding + "\t");		
    }

    public String toString() {
	return this.leftOperator + " " + this.operator + " " + this.rightOperator;
    }
}