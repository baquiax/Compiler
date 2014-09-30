package compiler.ast;

public class HexLiteral extends Node {
	private String value;

	public HexLiteral(String v) {
		this.value = v;
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "Hexadecimal Value: " + this.value);
	}
}