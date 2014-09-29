package compiler.ast;

import java.util.List;
import java.util.LinkedList;

public class MethodDecl extends Node {
    private String methodName;
    private String returnType;
    private List<Node> parameters;
    private Node block;

    public MethodDecl(String n, String type, Node block) {
	   this.methodName = n;
       this.returnType = type;
       this.block = block;
       this.parameters = new LinkedList<Node>();
    }    

    public void addParameter(Node parameter) {
        this.parameters.add(parameter);
    }

    public void print(String padding) {
        System.out.println(padding + "Method (Name: " + this.methodName + ") -> ");
        for(Node p : this.parameters) {
            p.print(padding);
        }       
    }
}