package compiler.ast;

public class For extends Node {
    private Assign init;
    private Node condition;
    private Node block;
    
    public For(Assign i, Node con, Node block) {
	   this.init = i;
	   this.condition = con;
	   this.block = block;
    }
    
    public Assign getInit() {
	   return this.init;
    }

    public Node getCondition() {
	   return this.condition;
    }    

    public Node getBlock() {
	   return this.block;
    }
    
    public void print() {
	   this.print("");
    }
    
    public void print(String padding) {
    	System.out.println(padding + "FOR -> ");
    	System.out.println(padding + "INIT -> ");
    	this.init.print(padding + "\t");
    	System.out.println(padding + "CONDITION -> ");
    	this.condition.print(padding + "\t");
    	System.out.println(padding + "TO DO -> ");
    	this.block.print(padding + "\t");
    }
}