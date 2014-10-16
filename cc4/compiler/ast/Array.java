package compiler.ast;

public class Array extends Node {
    private String type;
    private String name;
    private String size;
    private Node currentIndex;

    public Array(String n) {
        this.type = null;
        this.size = null;
        this.name = n;        
        this.currentIndex = null;
    }
    
    public Array(String n, String s) {
    	this.type = null;
	this.name = n;
	this.size = s;
        this.currentIndex = null;
    }
    
    public Array(String t, String n, String s) {
	this.type = t;
	this.name = n;
	this.size = s;
    }    
    
    public String getName() {
	return this.name;
    }
    
    public void setType(String t) {
    	this.type = t;
    }
    
    public String getType() {
	return this.type;
    }
    
    public void setIndex(Node n) {
        this.currentIndex = n;
    }

    public Node getIndex() {
	return this.currentIndex;
    }

    public String getSize() {
	return this.size;
    }
    
    public void print(String padding) {
        if (this.size != null) {
            System.out.println(padding + this.name);
        } else {
            System.out.println(padding + this.name + "[" + this.size + "]");
        }		
    }
    
    public String toString() {
	if (this.currentIndex == null) {
	    return this.name;
	} else {
	    return this.name + "[" + currentIndex +  "]";
	}
    }
}