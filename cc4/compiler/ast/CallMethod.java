package compiler.ast;

public class CallMethod extends Node  implements ILineNumber  {
    private String methodName;
    private Node args;
    private int line;

    public CallMethod(String n) {
	this.methodName = n;
	this.args = new Args();
	this.line = -1;
    }

    public void setLineNumber(int l) {
	this.line = l;
    }

    public int getLineNumber() {
	return this.line;
    }    

    public String getMethodName() {
	return this.methodName;
    }
    
    public void setArgs(Node a) {
	this.args = a;
    }
    
    public Args getArgs() {
	return (Args) this.args;
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