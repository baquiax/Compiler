package compiler.ast;
import java.util.List;
import java.util.LinkedList;

public class Var extends Node implements ILineNumber{
    private String type;
    private String varName;
    private int line;
    
    public Var(String varName) {
    	this.type = null;
	this.varName = varName;
	this.line = -1;
    }
    
    public Var(String type, String varName) {
    	this.type = type;
	this.varName = varName;
    }

    public String getName() { 
       return this.varName;
    }

    public void setType(String t) {
    	this.type = t;
    }

    public String getType() {
	return this.type;
    }    
    
    public void setLineNumber(int l) {
	this.line = l;
    }
    
    public int getLineNumber() {
	return this.line;
    }

    public void print(String padding) {
        if (this.type == null) 
            System.out.println(padding + this.varName);
        else
            System.out.println(padding + "(" + this.type + "):"+ this.varName );
    }   

    public String toString() {	
	return varName;
    }
}