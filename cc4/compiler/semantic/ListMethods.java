package compiler.semantic;

import java.util.LinkedList;  

public class ListMethods {
	private MethodParameter methods;
	private LinkedList<String> nameMethods = new LinkedList<String>();
	private LinkedList<LinkedList<String>> nameParameters = new LinkedList<LinkedList<String>>();
	private LinkedList<LinkedList<String>> typeParameters = new LinkedList<LinkedList<String>>();

	public ListMethods(MethodParameter methods) {
		this.methods=methods;
		nameMethods.add(methods.returnNameMethod());
		nameParameters.add(methods.returnNameParam());
		typeParameters.add(methods.returnTypeParam());
	}

	
}