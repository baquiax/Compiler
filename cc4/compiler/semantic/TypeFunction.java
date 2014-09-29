package compiler.semantic;

public class TypeFunction {
	private String name;
	private String typeDecl;
	private String typeReturn;

	public TypeFunction(String id, String tDecl, String tReturn) {
		name = id;
		typeDecl = tDecl;
		typeReturn = tReturn;
	}

	public String getName() {
		return name;
	}

	public String getTypeDeclaration() {
		return typeDecl;
	}

	public String getTypeReturn() {
		return typeReturn;
	}

	public boolean acceptReturnType() {
		if (typeDecl.equals(typeReturn)) 
			return true;
		else 
			return false;
	}
}
