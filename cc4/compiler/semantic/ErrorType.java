package compiler.semantic;

import java.util.LinkedList;

public class ErrorType {
	LinkedList<Integer> errorId;

	public ErrorType() {
		errorId = new LinkedList<Integer>();
	}

	public void addError(int error) {
		errorId.add(error);
	}

	public void printError() {
		for (int i=0;i<errorId.size();i++) {
			if (errorId.get(i)==1) returnFunction();
			else if (errorId.get(i)==2) conditional();
			else if (errorId.get(i)==3) varMethod();
			else if (errorId.get(i)==4) varField();
			else if (errorId.get(i)==5) varDefined();
			else if (errorId.get(i)==6) callMisingMethod();
			else if (errorId.get(i)==7) array();
			else if (errorId.get(i)==8) assign();
			else if (errorId.get(i)==9) typeParam();
		}
	}

	/*ERRORID = 1*/
	public void returnFunction() {
		System.out.println("Invalid return value method");
	}

	/*ERRORID = 2*/
	public void conditional() {
		System.out.println("No boolean condition");
	}

	/*ERRORID = 3*/
	public void varMethod() {
		System.out.println("Variable not defined in method");
	}

	/*ERRORID = 4*/
	public void varField() {
		System.out.println("Variable not defined");
	}

	/*ERRORID = 5*/
	public void varDefined() {
		System.out.println("Variable already defined");
	}

	/*ERRORID = 6*/
	public void callMisingMethod() {
		System.out.println("Missing parameters");
	}

	/*ERRORID = 7*/
	public void array() {
		System.out.println("Position out of range");
	}

	/*ERRORID = 8*/
	public void assign() {
		System.out.println("Invalid value assigned");
	}

	/*ERRORID = 9*/
	public void typeParam() {
		System.out.println("Incorrect parameters for method");
	}		
}
