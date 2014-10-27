package compiler.irt;

public class LoadInstruction extends Instruction {
	private String rd;
	private String dir;

	public LoadInstruction(String rd, String dir) {
		this.rd = rd;
		this.dir = dir;
	}

	
}