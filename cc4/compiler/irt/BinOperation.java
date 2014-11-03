package compiler.irt;

public class BinOperation extends Instruction {
	private String first;
	private String second;
	private String operator;

	public BinOperation() {
	}

	public void convertInstruction(String first, String second, String operator) {
		if (operator.equals("+")) {
			plusInstruction(first, second);
		} else if (operator.equals("-")) {
			minusInstruction(first, second);
		} else if (operator.equals("*")) {
			multInstruction(first, second);
		} else if (operator.equals("/")) {
			divInstruction(first, second);
		} else if (operator.equals("%")) {
			modInstruction();
		}
	}

	public void plusInstruction(String first, String second) {
		Irt.listSintaxis.add("add");
		operators(first, second);
	}

	public void minusInstruction(String first, String second) {
		Irt.listSintaxis.add("sub");
		operators(first, second);
	}

	public void multInstruction(String first, String second) {
		Irt.listSintaxis.add("mult");
		operators(first, second);
	}

	public void divInstruction(String first, String second) {
		Irt.listSintaxis.add("div");
		operators(first, second);
	}

	public void modInstruction() {

	}

	public void operators(String first, String second) {
		Irt.listSintaxis.add(first);
		Irt.listSintaxis.add(second);
	}
}
