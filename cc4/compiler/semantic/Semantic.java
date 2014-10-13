package compiler.semantic;
import compiler.lib.Debug;
import compiler.ast.Ast;
import compiler.lib.Configuration;
import compiler.ast.*;
import java.util.List;

/**
 * Esta clase se encarga de verificar la coherencia de las operaciones.
 * Necesita un AST.
 */

public class Semantic {	
    public static final int level = 4;
    public static Scope currentScope;	
    
    private Ast ast;
    private ErrorType error;
    private ProgramScope globalScope;
    
    
    public Semantic(Ast ast) {
	this.ast = ast;
	this.globalScope = new ProgramScope();
	Semantic.currentScope = this.globalScope;
    }
    
    public void check() {
	if (Configuration.stopStage >= Semantic.level) {
	    System.out.println("stage: SEMANTIC");
	    if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");
	    checkProgram(ast.getProgram());
        }
    }
    
    public void checkProgram(Program p) {
	List<FieldDecl> fieldDecls = p.getFields();
	for (FieldDecl f : fieldDecls) {
	    for (Node n : f.getFields()) {
		if (n.getClass().getName().equals(Var.class.getName())) {
		    Var v = (Var) n;
		    Semantic.currentScope.insertSymbol(v.getName(), new VarType(v));
		} else {
		    //It's an array.
		    Array a = (Array) n;
		    Semantic.currentScope.insertSymbol(a.getName(), new ArrayType(a));
		}
	    }			
	}
	
	List<MethodDecl> methodsDecls =	p.getMethods();
	for (MethodDecl n : methodsDecls) {
	    MethodDecl m = (MethodDecl) n;
	    MethodType mt = new MethodType(m);	
	    Semantic.currentScope.insertSymbol(m.getName(), mt);
	    Semantic.currentScope = mt.getScope();
	    this.checkMethod(m);
	    Semantic.currentScope = mt.getScope().getParent();
	}
	this.globalScope.print();
    }
    
    public void checkMethod(MethodDecl m) {		
	for (Node n : m.getParameters()) {
	    if (n.getClass().getName().equals(Var.class.getName())) {
		Var v = (Var) n;
		Semantic.currentScope.insertSymbol(v.getName(), new VarType(v));
	    } else {
		//It's an array.
		Array a = (Array) n;
		Semantic.currentScope.insertSymbol(a.getName(), new ArrayType(a));
	    }
	}
	
	//Check block method
	Block b = (Block)m.getBlock();
	for(Node vd : b.getVarDecl()) {
	    if (vd.getClass().getName().equals(FieldDecl.class.getName())) {
		FieldDecl fd = (FieldDecl) vd;
		for (Node n : fd.getFields()) {
		    if (n.getClass().getName().equals(Var.class.getName())) {
			Var v = (Var) n;
			Semantic.currentScope.insertSymbol(v.getName(), new VarType(v));
		    }
		}					
	    }
	}
	
    }
    
    public void checkAssign() {
	
    }
    
    public void checkType() {
	
    }
    
    public void checkIf() {

    }
    
    public void checkFor() {
	
    }
    
    public void checkArray() {
	
    }
    
    public void checkVars() {
	
    }
}
