package compiler.semantic;

public class TypeFunction {
	private String name;
	private String typeDecl;
	private String typeReturn;
	private ErrorType error;

	public TypeFunction(String id, String tDecl, String tReturn) {
		name = id;
		typeDecl = tDecl;
		typeReturn = tReturn;
		error = new ErrorType();
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
		else {
			error.addError(1);
			return false;
		}
	}
}
