package compiler.ast;

public class Negation extends Node {
	private Node expr;

	public Negation(Node expr){
		this.expr = expr;
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "Negation -> ");
		expr.print(padding + "\t");
	}
}