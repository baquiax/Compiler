package compiler.irt;

public class BinOperation extends Instruction {
	private String first;
	private String second;
	private String operator;
	private String fate;
	private String aux;
	private String add;
	private String sub;
	private String mult;
	private String div;

	public BinOperation() {
		fate="fate";
		aux="aux";
		add="add";
		sub="sub";
		mult="mult";
		div="div";
	}

	public void convertInstruction(String first, String second, String operator, String acumulator) {
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
		} else if (operator.equals("+=")) {
			accumulatorInstruction(acumulator, operator);
		} else if (operator.equals("-=")) {
			accumulatorInstruction(acumulator, operator);
		}
	}

	public void addInstruction(String element) {
		Irt.listSintaxis.addLast(element);
	}

	public void plusInstruction(String first, String second) {
		addInstruction(add);
		addInstruction(fate);
		operators(first, second);
	}

	public void minusInstruction(String first, String second) {
		addInstruction(sub);
		addInstruction(fate);
		operators(first, second);
	}

	public void multInstruction(String first, String second) {
		addInstruction(mult);
		addInstruction(fate);
		operators(first, second);
	}

	public void divInstruction(String first, String second) {
		addInstruction(div);
		addInstruction(fate);
		operators(first, second);
	}

	public void modInstruction() {

	}

	public void accumulatorInstruction(String value, String acumulator) {
		String val=null;
		if (acumulator.equals("+="))
			val = add;
		else if (acumulator.equals("-="))
			val = sub;

		addInstruction(val);
		addInstruction(aux);
		addInstruction(first);
		addInstruction(val);
		addInstruction(first);
		addInstruction(aux);
		addInstruction(value);
	}

	public void operators(String first, String second) {
		addInstruction(first);
		addInstruction(second);
	}
}
