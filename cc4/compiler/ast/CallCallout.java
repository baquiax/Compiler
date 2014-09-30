package compiler.ast;

public class CallCallout extends Node {
	private String first;
	private Node args;

	public CallCallout(String s) {
		this.first = s;
	}

	public void setArgs(Node n) {
		this.args = n;
	}

	public void print() {
		this.print("");
	}	

	public void print(String padding) {
		System.out.println(padding + "Callout -> " + this.first);
		if (this.args != null) {
			System.out.println(padding + "Args: ");
			this.args.print(padding + "\t");
		}
	}
}