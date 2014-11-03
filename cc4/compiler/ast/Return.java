package compiler.ast;

public class Return extends Node  implements ILineNumber {
    private Node expr;
    private int line;
    
    public Return() {
	   this.line = -1;
	   this.expr = null;
    }
    
    public Return(Node expr) {
	   this.line = -1;
	   this.expr = expr;		
    }
    
    public Node getExpr() {
	   return this.expr;
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
	   System.out.println(padding + "RETURN");
	   if (this.expr != null) {
	        this.expr.print(padding + "\t");
	   }
    }

    public String toString() {
        if (expr != null) {
	       return "return " + expr + ";"; 
        } else {
            return "return;";
        }    
    }
}