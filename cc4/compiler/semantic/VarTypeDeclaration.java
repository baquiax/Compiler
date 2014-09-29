package compiler.semantic;

public class VarTypeDeclaration {
	private String name;
	private String type;

	public VarTypeDeclaration(String id, String tDecl) {
		name = id;
		type = tDecl;
	}

	public String getName() {
		return name;
	}

	public String getTypeDeclaration() {
		return type;
	}
}
