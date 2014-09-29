package compiler.ast;

public class Array extends Node {
	private String type;
    private String name;
    private String size;

    public Array(String n, String s) {
    	this.type = "";
		this.name = n;
		this.size = s;
    }

	public Array(String t, String n, String s) {
		this.type = t;
		this.name = n;
		this.size = s;
    }    

    public void setType(String t) {
    	this.type = t;
    }

    public void print(String padding) {
		System.out.println(padding + this.name + "[" + this.size + "]");
    }
}