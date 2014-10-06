package compiler.semantic;

public class TypeIf {
	private VarTypeDeclaration value1;
	private VarTypeDeclaration value2;
	private String operator;
	private ErrorType error;

	public TypeIf(VarTypeDeclaration value1, VarTypeDeclaration value2, String operator) {
		this.value1 = value1;
		this.value2 = value2;
		this.operator = operator;
		error = new ErrorType();
	}

	public boolean compare() {
		boolean accept=false;

		if 	(value1.getTypeDeclaration().equals(value2.getTypeDeclaration())) {
			if (value1.getTypeDeclaration().equals("int")) {
				if (operator.equals("+")||operator.equals("-")||operator.equals("*")||operator.equals("/")||operator.equals("%")||operator.equals("=")||operator.equals("+=")||operator.equals("-=")||operator.equals("<")||operator.equals(">")||operator.equals(">=")||operator.equals("<=")) {
					accept=true;
				}
			} else if (value1.getTypeDeclaration().equals("boolean")||value2.getTypeDeclaration().equals("boolean")) {
				accept=true;	
			} else {
				error.addError(2);
			}
		}

		return accept;
	}
}
