package compiler.semantic;

import java.util.LinkedList;

public class VarExistence {
	private LinkedList<String> localVar;
	private LinkedList<String> globalVar;

	public VarExistence() {
		localVar= new LinkedList<String>();
		globalVar = new LinkedList<String>();
	}

	public void addLocalVar(String localVariable) {
		localVar.add(localVariable);
	}

	public void addGlobalVar(String globalVariable) {
		globalVar.add(globalVariable);
	}

	public boolean searchLocalVar(String nameVar) {
		boolean value=false;

		for (int i=0; i<localVar.size();i++) {
			if (localVar.get(i).equals(nameVar))
				value = true;
			else 
				value = false;
		}

		return value;
	}

	public boolean searchGlobalVar(String nameVar) {
		boolean value=false;

		for (int i=0; i<globalVar.size();i++) {
			if (globalVar.get(i).equals(nameVar))
				value = true;
			else 
				value = false;
		}

		return value;
	}
}
