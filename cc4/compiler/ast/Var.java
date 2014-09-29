package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Var extends Node {
	private String type;
    private String varName;
    
    public Var(String type, String varName) {
    	this.type = type;
		this.varName = varName;
    }

    public void print(String padding) {
		System.out.println(padding + this.varName + "[" + this.type + "]");
    }
}