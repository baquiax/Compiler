package compiler.irt;

public class BranchInstruction extends Instruction {
	private String condition;
	private String consecuentLabel;
	private String alternativeLabel;
	private String[] regs;

	public BranchInstruction(String cond, String cons, String alt, String regs[]) {
		this.condition = cond;
		this.consecuentLabel = cons;
		this.alternativeLabel = alt;
		this.regs = regs;
	}
}