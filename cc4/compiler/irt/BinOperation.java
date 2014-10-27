public package compiler.irt;

public class BinOperation extends Instruction {
	private String first;
	private String second;
	private String operator;

	public BinOperation(String first, String second, String op) {
		this.first = first;
		this.second = second;
		this.operator = op;
	}		
}