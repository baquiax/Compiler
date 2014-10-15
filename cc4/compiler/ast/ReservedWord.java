package compiler.ast;

public class ReservedWord extends Node implements ILineNumber {
    private String word;
    private int line;
    public ReservedWord(String w) {
	this.word = w;
	this.line = -1;
    }
 
    public void setLineNumber(int l) {
	this.line = l;
    }
    
    public int getLineNumber() {
	return this.line;
    }
    
    public void print() {
	this.print("");
    }
    
    public void print(String padding) {
	System.out.println(padding + "RESERVED WORD: " + this.word);
    }

    public String toString() {
	return this.word;
    }
}