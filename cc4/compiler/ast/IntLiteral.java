package compiler.ast;

public class IntLiteral extends Node {
	private int value;

	public IntLiteral(String v) {
		this.value = Integer.parseInt(v);
	}

	public void print(String padding) {
		System.out.println(padding + "(INT)" + this.value);
	}

	public void print() {
		this.print("");
	}
}