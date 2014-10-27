package compiler.irt;

public class JumpInstruction extends Instruction {
	private String dir;	

	public JumpInstruction(String dir) {
		this.dir = dir;
	}
}