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
    public static ProgramScope globalScope;

    private Ast ast;
    private ErrorType error;

    
    
    public Semantic(Ast ast) {
	this.ast = ast;
	Semantic.globalScope = new ProgramScope();
	Semantic.currentScope = Semantic.globalScope;
    }
    
    public void check() {
	if (Configuration.stopStage >= Semantic.level) {
	    System.out.println("stage: SEMANTIC");
	    if (Debug.debugEnabled("semantic")) System.out.println("debugging: SEMANTIC");
	    checkProgram(ast.getProgram());
        }
    }

    private boolean addSymbol(String k, Type v) {
	//Check if already defined
	if (Semantic.currentScope.getSymbol(k) == null) {
	    Semantic.currentScope.insertSymbol(k, v);
	    return true;
	} else {
	    return false;	    	    
	}
    }
    
    private Type getSymbol(String k) {
	Type r = Semantic.currentScope.getSymbol(k);
	if (r == null) {
	    r = Semantic.globalScope.getSymbol(k);
	}
	return r;
    }
    
    public void checkProgram(Program p) {
	List<FieldDecl> fieldDecls = p.getFields();
	for (FieldDecl f : fieldDecls) {
	    for (Node n : f.getFields()) {
		if (n.getClass().getName().equals(Var.class.getName())) {
		    Var v = (Var) n;
		    if (!this.addSymbol(v.getName(), new VarType(v))) {
			System.err.println(v.getName() + " ya esta definido!");
			if (f instanceof ILineNumber) {
			    System.err.println("[L:" + f.getLineNumber() +  "] " + f + "\n");
			}
		    }		    
		} else {
		    //It's an array.
		    Array a = (Array) n;
		    this.addSymbol(a.getName(), new ArrayType(a));
		}
	    }			
	}
	
	List<MethodDecl> methodsDecls =	p.getMethods();
	for (MethodDecl n : methodsDecls) {
	    MethodDecl m = (MethodDecl) n;
	    MethodType mt = new MethodType(m);	
	    this.addSymbol(m.getName(), mt);
	    Semantic.currentScope = mt.getScope();
	    this.checkMethod(m);
	    Semantic.currentScope = mt.getScope().getParent();
	}
	Semantic.globalScope.print();
    }
    
    public void checkMethod(MethodDecl m) {		
	for (Node n : m.getParameters()) {
	    if (n.getClass().getName().equals(Var.class.getName())) {
		Var v = (Var) n;
		this.addSymbol(v.getName(), new VarType(v));
	    } else {
		//It's an array.
		Array a = (Array) n;
		this.addSymbol(a.getName(), new ArrayType(a));
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
			this.addSymbol(v.getName(), new VarType(v));
		    }
		}					
	    }
	}

	for (Node n: b.getStatements()) {
	    String st = n.getClass().getName();
	    if (st.equals(Assign.class.getName())) {
		this.checkAssign((Assign)n);
	    } else if (st.equals(CallMethod.class.getName())) {		
	    } else if (st.equals(If.class.getName())) {
	    } else if (st.equals(For.class.getName())) {
	    } else if (st.equals(ReservedWord.class.getName())) { 
	    } else if (st.equals(Return.class.getName())) { 
	    } else if (st.equals(Block.class.getName())) { 		
	    }
	}
    }
    
    public void checkAssign(Assign as) {
	Type location = null;
	//Check if location is defined
	if (as.getLocation().getClass().getName().equals(Var.class.getName())) {
	    Var v = (Var) as.getLocation();
	    location = this.getSymbol(v.getName());
	    if (location == null) {
		System.err.println(v.getName() + " no está definido.");
		System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
	    }	    
	} else if (as.getLocation().getClass().getName().equals(Array.class.getName())) {
	    //Array is only defined in global scope.
	    Array a = (Array) as.getLocation();
	    location = Semantic.globalScope.getSymbol(a.getName());
	    if (location == null) {
		System.err.println(a.getName() + " no está definido.");
		System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
	    }	    
	}
	
	//Ceck Types!
	if (location != null) {
	    Node e = as.getE();
	    String locationType = location.getType();
	    String eType = this.getRealType(e);
	    
	    if (!locationType.equals(eType)) {
		System.err.println(eType + " no es asignable para " + locationType);
		System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
	    } 
	}
    }

    public String getRealType(Node n) {
	if (n.getClass().getName().equals(Var.class.getName())) {
	    Var v = (Var) n;
	    Type t = this.getSymbol(v.getName());
	    if (t != null)
		return t.getType();	    
	} else if (n.getClass().getName().equals(Array.class.getName())) {
	    Array a = (Array) n;
	    Type t = this.getSymbol(a.getName());
	    if (t != null)
		return t.getType();	    
	} else if (n.getClass().getName().equals(CallMethod.class.getName())) {
	    CallMethod cm = (CallMethod)n;
	    Type t = Semantic.globalScope.getSymbol(cm.getMethodName());
	    if (t != null)
		return t.getType();
	} else if (n.getClass().getName().equals(IntLiteral.class.getName())) {
	} else if (n.getClass().getName().equals(HexLiteral.class.getName())) {
	} else if (n.getClass().getName().equals(BoolLiteral.class.getName())) {
	    
	} else if (n.getClass().getName().equals(CharLiteral.class.getName())) {
	    
	} else if (n.getClass().getName().equals(BinOp.class.getName())) {
	    
	} else if (n.getClass().getName().equals(Negation.class.getName())) {
	} else if (n.getClass().getName().equals(Negation.class.getName())) {
	}

	//No match.
	return "error";
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
