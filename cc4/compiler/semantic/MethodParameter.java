package compiler.semantic;

import java.util.LinkedList;

public class MethodParameter {
	private String nameMethod;
	private LinkedList<String> typeParam;
	private LinkedList<String> nameParam;

	public MethodParameter(String method) {
		nameMethod = method;
		typeParam = new LinkedList<String>();
		nameParam = new LinkedList<String>();
	}

	public void addType(String nameType) {
		typeParam.add(nameType);
	}

	public void addName(String nameParameter) {
		nameParam.add(nameParameter);
	}

	public int cantParameters() {
		return nameParam.size();
	}

	public String returnNameMethod() {
		return nameMethod;
	}

	public LinkedList<String> returnTypeParam() {
		return typeParam;
	}

	public LinkedList<String> returnNameParam() {
		return nameParam;
	}
}
