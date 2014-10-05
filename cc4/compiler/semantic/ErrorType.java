package compiler.semantic;

import java.util.LinkedList;

public class ErrorType {
	LinkedList<String> errorList;

	public ErrorType(LinkedList<String> list) {
		errorList = new LinkedList<String>();
	}

	public void addError(String error) {
		errorList.add(error);
	}

	public void printError() {

	}

	public void varField(String name) {
		System.out.println("Variable " + name + " no definida");
	}

	public void varMethod(String name, String nameMethod) {
		System.out.println("Variable "+name+" no definida en método "+nameMethod);
	}

	public void callMisingMethod(String nameMethod) {
		System.out.println("Parametros faltantes en "+nameMethod);
	}

	public void callMethod(String nameMethod) {
		System.out.println("Parametros de mas en "+nameMethod);
	}

	public void conditional() {
		System.out.println("Valor de comparacion no booleano");
	}

	public void array(String arreglo) {
		System.out.println("Posicion fuera de rango en "+arreglo);
	}

	public void returnFunction(String nameMethod) {
		System.out.println("Valor de retorno de "+nameMethod+ " invalido");
	}

	public void assign(String var) {
		System.out.println("Valor asignado a "+var+" invalido");
	}
}