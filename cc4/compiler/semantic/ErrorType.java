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
			else if (errorId.get(i)==7) callMethod();
			else if (errorId.get(i)==8) array();
			else if (errorId.get(i)==9) assign();
			else if (errorId.get(i)==10) typeParam();
		}
	}

	/*ERRORID = 1*/
	public void returnFunction() {
		System.out.println("Valor de retorno de método invalido");
	}

	/*ERRORID = 2*/
	public void conditional() {
		System.out.println("Condicion no booleana");
	}

	/*ERRORID = 3*/
	public void varMethod() {
		System.out.println("Variable no definida en metodo");
	}

	/*ERRORID = 4*/
	public void varField() {
		System.out.println("Variable no definida");
	}

	/*ERRORID = 5*/
	public void varDefined() {
		System.out.println("Variable ya fue definida");
	}

	/*ERRORID = 6*/
	public void callMisingMethod() {
		System.out.println("Parametros faltantes");
	}

	/*ERRORID = 7*/
	public void callMethod() {
		System.out.println("Parametros de mas");
	}

	/*ERRORID = 8*/
	public void array() {
		System.out.println("Posicion fuera de rango");
	}

	/*ERRORID = 9*/
	public void assign() {
		System.out.println("Valor asignado invalido");
	}

	/*ERRORID = 10*/
	public void typeParam() {
		System.out.println("Parametros incorrectos para método");
	}		
}
