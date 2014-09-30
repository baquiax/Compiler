package compiler.ast;

public class ReservedWord extends Node {
	private String word;

	public ReservedWord(String w) {
		this.word = w;
	}

	public void print() {
		this.print("");
	}

	public void print(String padding) {
		System.out.println(padding + "RESERVED WORD: " + this.word);
	}
}