package compiler.ast;

public class Return extends Node {
	private Node expr;

	public Return() {		
	}

	public Return(Node expr) {
		this.expr = expr;		
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "RETURN");
		if (this.expr != null) {
			this.expr.print(padding + "\t");
		}
	}
}