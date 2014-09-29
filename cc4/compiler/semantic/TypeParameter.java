package compiler.semantic;

public class TypeParameter {
	private String name;
	private String type;

	public TypeParameter(String id, String typeVar) {
		name = id;
		type = typeVar;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
}
