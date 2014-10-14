package compiler.ast;

public class CharLiteral extends Node {
    private String value;
    
    public CharLiteral(String v) {
	this.value = v;
    }
    
    public String getType() {
	return "char";
    }

    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + "Char Value: " + this.value);
    }

    public String toString() {
	return this.value;
    }
}