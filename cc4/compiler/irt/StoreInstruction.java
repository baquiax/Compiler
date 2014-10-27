public package compiler.irt;

public class StoreInstruction extends Instruction {
	private String rs;
	private String dir;

	public StoreInstruction(String rs, String dir) {
		this.rs = rs;
		this.dir = dir;
	}
	
}