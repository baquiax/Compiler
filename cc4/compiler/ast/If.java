package compiler.ast;

public class If extends Node {
	private Node condition;
	private Node consecuent;
	private Node alternative;

	public If(Node c, Node con) {
		this.condition = c;
		this.consecuent = con;		
	}

	public If(Node c, Node con, Node alt ) {
		this.condition = c;
		this.consecuent = con;
		this.alternative = alt;
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
}