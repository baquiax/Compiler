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

	public void callMisingMethod(String nameMethod) {
		System.out.println("Parametros faltantes en "+nameMethod);
	}

	public void callMethod(String nameMethod) {
		System.out.println("Parametros de mas en "+nameMethod);
	}

	public void array(String arreglo) {
		System.out.println("Posicion fuera de rango en "+arreglo);
	}

	public void assign(String var) {
		System.out.println("Valor asignado a "+var+" invalido");
	}
}
