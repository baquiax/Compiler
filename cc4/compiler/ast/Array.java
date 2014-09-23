package compiler.ast;

public class Array extends Node {
    private String arrayName;
    private String size;

    public Array(String n, String s) {
	this.arrayName = n;
	this.size = s;
    }

    public void print(String padding) {
	System.out.println(padding + this.arrayName + "[" + this.size + "]");
    }
}