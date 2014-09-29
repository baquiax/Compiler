package compiler.ast;

public class Assign extends Node {
	private String operator;
	private Node location;
	private Node expr;

	public Assign(String operator, Node v , Node expr) {
		this.operator = operator;
		this.location = v;
		this.expr = expr;
	}

	public void print(String padding) {
		System.out.println(padding +  this.operator + " -> ");
		System.out.println(padding + "OP1:");
		location.print(padding + "\t");
		System.out.println(padding + "OP2:");
		expr.print(padding + "\t");
	}

	public void print() {
		this.print("");
	}
}