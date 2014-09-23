package compiler.ast;

import java.util.List;
import java.util.LinkedList;

public class MethodDecl extends Node {
    private String methodName;
    private Node returnType;

    public MethodDecl(String n) {
	this.methodName = n;
    }

    public void setReturnType(Node n) {
	this.returnType = n;
    }

    public void print(String padding) {
	System.out.println(padding + "Method (Name: " + this.methodName + ") -> ");
    }
}