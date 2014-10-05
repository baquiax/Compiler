package compiler.semantic;

public class TypeIf {
	private VarTypeDeclaration value1;
	private VarTypeDeclaration value2;
	private String operator;

	public TypeIf(VarTypeDeclaration value1, VarTypeDeclaration value2, String operator) {
		this.value1 = value1;
		this.value2 = value2;
		this.operator = operator;
	}

	public boolean compare() {
		boolean accept=false;

		if 	(value1.getTypeDeclaration().equals(value2.getTypeDeclaration())) {
			if (value1.getTypeDeclaration().equals("int")) {
				if (operator.equals("+")||operator.equals("-")||operator.equals("*")||operator.equals("/")||operator.equals("%")||operator.equals("=")||operator.equals("+=")||operator.equals("-=")||operator.equals("<")||operator.equals(">")||operator.equals(">=")||operator.equals("<=")) {
					accept=true;
				}	
			}
		}

		if (value1.getTypeDeclaration().equals("boolean")||value2.getTypeDeclaration().equals("boolean")) {
			accept=true;
		}

		return accept;
	}
}
