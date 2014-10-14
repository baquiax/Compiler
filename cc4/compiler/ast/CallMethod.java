package compiler.ast;

public class CallMethod extends Node {
    private String methodName;
    private Node args;
    
    public CallMethod(String n) {
	this.methodName = n;
    }
    
    public String getMethodName() {
	return this.methodName;
    }
    
    public void setArgs(Node a) {
	this.args = a;
    }
    
    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + "Call method -> " + this.methodName);
	if (this.args != null) {
	    System.out.println(padding + "Arguments -> ");
	    this.args.print(padding + "\t");
	}
    }	

    public String toString() {
	return this.methodName + "(" + this.args + ")";
    }
}