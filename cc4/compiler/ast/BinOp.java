package compiler.ast;

public class BinOp extends Node {
	private String operator;
	private Node leftOperator;
	private Node rightOperator;

	public BinOp(String o, Node lo, Node ro) {
		this.operator = o;
		this.leftOperator = lo;
		this.rightOperator = ro;
	}

	public void print() {
		this.print("");
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

	public void print(String padding) {
		System.out.println(padding + this.operator + " -> ");
		System.out.println(padding + "Left operator");
		this.leftOperator.print(padding + "\t");
		System.out.println(padding + "Right operator");
		this.rightOperator.print(padding + "\t");		
	}
}