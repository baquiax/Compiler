package compiler.semantic;

import java.util.LinkedList;

public class CallMethod {
	private LinkedList<String> typeParam;
	private LinkedList<String> nameParam;
	private MethodParameter function;
	private ErrorType error;
	private int cantParameter;
	private String nameMethod;

	public CallMethod(String nameMethod, MethodParameter externFunction) {
		nameMethod=nameMethod;
		function=externFunction;
		error = new ErrorType();
	}

	public void addParameterType(String nameType) {
		typeParam.add(nameType);
	}

	public void addParameterName(String nameParameter) {
		nameParam.add(nameParameter);
	}

	public boolean countParameters() {
		int countCallMethod = nameParam.size();
		int countFunction = function.cantParameters();
		boolean value=false;

		if (nameMethod.equals(function.returnNameMethod())) {
			if (countCallMethod==countFunction) {
				for (int i=0;i<nameParam.size();i++) {
					if ((nameParam.get(i).equals(function.returnNameParam().get(i))) && (typeParam.get(i).equals(function.returnTypeParam().get(i)))) {
						value=true;
					} else {
						error.addError(5);
					}
				}
			}
		}

		return value;
	}
}
