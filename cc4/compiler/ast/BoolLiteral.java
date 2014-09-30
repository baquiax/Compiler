package compiler.ast;

public class BoolLiteral extends Node {
	private String value;

	public BoolLiteral(String v) {
		this.value = v;
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "Boolean Value: " + this.value);
	}
}