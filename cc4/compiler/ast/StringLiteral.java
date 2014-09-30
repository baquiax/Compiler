package compiler.ast;

public class StringLiteral extends Node {
	private String value;

	public StringLiteral(String v) {
		this.value = v;
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "String literal: " + this.value);
	}
}