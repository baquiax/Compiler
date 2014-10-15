package compiler.ast;

import java.util.List;
import java.util.LinkedList;

public class MethodDecl extends Node {
    private String methodName;
    private String returnType;
    private List<Var> parameters;
    private Node block;
    
    public MethodDecl(String n, String type, Node block) {
	this.methodName = n;
	this.returnType = type;
	this.block = block;
	this.parameters = new LinkedList<Var>();
    }    
    
    public String getName() {
	String name = this.methodName + "(" ;	
	//Allowing overload
	for (int i = 0; i < this.parameters.size() ; i++) {
	    name += this.parameters.get(i).getType();
	    if ((i + 1) < this.parameters.size()) {
		name += ", ";
	    }
	}
	name += ")";
	return name;
    }
    
    public void addParameter(Var parameter) {
        this.parameters.add(parameter);
    }

    public List<Var> getParameters() {
	return this.parameters;
    }

    public Node getBlock() {
	return this.block;
    }
    
    public String getReturnType() {
	return this.returnType;
    }

    public void print(String padding) {
        System.out.println(padding + "Method (Name: " + this.methodName + ")[" + this.returnType + "] -> ");
        System.out.println(padding + "Parameters: ");
        for(Node p : this.parameters) {
            p.print(padding + "\t");
        }
        System.out.println(padding + "Block: ");
        block.print(padding + "\t");
    }
}