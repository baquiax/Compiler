package compiler.ast;

public class IntLiteral extends Node {
    private int value;
    
    public IntLiteral(String v) {
	this.value = Integer.parseInt(v);
    }
    
    public void print(String padding) {
	System.out.println(padding + "Integer value: " + this.value);
    }
    
    public void print() {
	this.print("");
    }
    
    public String getType() {
	return "int";
    }

    public String toString() {
	return String.valueOf(this.value);
    }
}