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
		return false;
	}
}
