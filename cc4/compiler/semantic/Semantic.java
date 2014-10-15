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
       
    public Semantic(Ast ast) {
	this.ast = ast;
	Semantic.globalScope = new ProgramScope();
	Semantic.currentScope = Semantic.globalScope;
    }        
    
    public void check() {
	if (Configuration.stopStage >= Semantic.level) {
	    System.out.println("stage: SEMANTIC");
	    checkProgram(ast.getProgram());
	    if (Debug.debugEnabled("semantic")) {
		System.out.println("debugging: SEMANTIC");
		Semantic.globalScope.print();
	    }
        }
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
	this.checkBlock(b);
    }

    public void checkStatement(Node n) {
	String st = n.getClass().getName();
	if (st.equals(Assign.class.getName())) {
	    this.checkAssign((Assign)n);
	} else if (st.equals(CallMethod.class.getName())) {
	    this.checkCallMethod((CallMethod)n);
	} else if (st.equals(If.class.getName())) {
	    this.checkIf((If)n);
	} else if (st.equals(For.class.getName())) {
	} else if (st.equals(ReservedWord.class.getName())) { 
	} else if (st.equals(Return.class.getName())) { 
	} else if (st.equals(Block.class.getName())) { 		
	}
    }

    public void checkBlock(Block b) {
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
	    this.checkStatement(n);	    
	}
    }

    public String checkAssign(Assign as) {
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
	Node e = as.getE();
	String eType = this.checkExpr(e);
	
	if (location != null) {	    
	    String locationType = location.getType();	    	    
	    //Assignation type.
	    if (!locationType.equals(eType)) {
		System.err.println(eType + " no es asignable para " + locationType);
		System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
	    } else {
		return eType;
	    }
	}

	return "error";
    }

    public void checkIf(If ifStat) {
	String conditionType = this.checkExpr(ifStat.getCondition());
	if (!conditionType.equals("boolean")) {
	    System.err.println("Condicion de IF debe usar valores booleanos");
	    if (ifStat.getCondition() instanceof ILineNumber) {
		ILineNumber ln = (ILineNumber) ifStat.getCondition();
		System.err.println("[L:" + ln.getLineNumber() +  "] " + ifStat.getCondition() + "\n");
	    }
	}
	BlockType bs = new BlockType((Block)ifStat.getConsecuent());
	this.addSymbol("Block#" + bs.getScope().getId() + ": "+ ifStat.getCondition(), bs);
	Semantic.currentScope = bs.getScope();
	this.checkBlock((Block)ifStat.getConsecuent());
	Semantic.currentScope = bs.getScope().getParent();
    }

    public String checkExpr(Node n) {
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
	    return this.checkCallMethod(cm);
	} else if (n.getClass().getName().equals(IntLiteral.class.getName())) {
	    IntLiteral il = (IntLiteral)n;
	    return il.getType();
	} else if (n.getClass().getName().equals(HexLiteral.class.getName())) {
	    HexLiteral hl = (HexLiteral)n;
	    return hl.getType();
	} else if (n.getClass().getName().equals(BoolLiteral.class.getName())) {
	    BoolLiteral bl = (BoolLiteral)n;
	    return bl.getType();
	} else if (n.getClass().getName().equals(CharLiteral.class.getName())) {
	    CharLiteral cl = (CharLiteral)n;
	    return cl.getType();
	} else if (n.getClass().getName().equals(BinOp.class.getName())) {
	    BinOp bo = (BinOp)n;
	    return checkBinOp(bo);
	} else if (n.getClass().getName().equals(Negation.class.getName())) {
	} else if (n.getClass().getName().equals(Negation.class.getName())) {
	}

	//No match.
	return "error";
    }
    
    public String checkCallMethod(CallMethod cm) {
	String methodKey = cm.getMethodName() + "(";
	List<Node> args = cm.getArgs().getList();
	for (int i = 0; i < args.size(); i++) {
	    methodKey += this.checkExpr(args.get(i));
	    if((i + 1) < args.size()) {
		methodKey += ", ";
	    }
	}
	methodKey += ")";

	MethodType mt = (MethodType) Semantic.globalScope.getSymbol(methodKey);
	if (mt == null) {
	    ILineNumber ln = (ILineNumber)cm;
	    System.err.println("Metodo " + methodKey + " no definido");
	    System.err.println("[L:" + ln.getLineNumber() + "] " + cm + "\n");
	    return "error";
	} else {
	    MethodDecl md = (MethodDecl) mt.getNode();
	    return md.getReturnType();
	}
    }

    public String checkBinOp(BinOp op) {
	String operator = op.getOperator();	
	String firstType = this.checkExpr(op.getFirst());
	String secondType = this.checkExpr(op.getSecond());
	String returnType = "error";
	
	if (operator.equals("+") || operator.equals("-") || operator.equals("*") || 
	    operator.equals("/") || operator.equals("%")) {	    
	    //Arithmetic Op. (int op int) -> int
	    if (firstType.equals(secondType)) {
		if (!firstType.equals("int")) {
		    System.err.println(operator + " solo debe ser usando con int" );
		    System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
		} else {
		    returnType = "int";
		}
	    } else {
		System.err.println(firstType + " no es operable con " + secondType + ", usando " + operator);
		System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
	    }
	} else if (operator.equals(">=") || operator.equals(">") || operator.equals("<=") || 
		   operator.equals("<")) {
	    //Relational operator. (int op int ) -> boolean	    
	    if (firstType.equals(secondType)) {
		if (!firstType.equals("int")) {
		    System.err.println(operator + " solo debe ser usando con int" );
		    System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
		} else {
		    returnType = "boolean";
		}
	    } else {
		System.err.println(firstType + " no es operable con " + secondType + ", usando " + operator);
		System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
	    }
	    
	}  else if (operator.equals("&&") || operator.equals("||")) {
	    //Logical operator (boolean op boolean) -> boolean
	    if (firstType.equals(secondType)) {
		if (!firstType.equals("boolean")) {
		    System.err.println(operator + " solo opera con boolean");
		    System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
		} else {
		    returnType = "boolean";
		}
	    } else {
		System.err.println(firstType + " no es operable con " + secondType + ", usando " + operator);
		System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
	    }
	} else if (operator.equals("==") || operator.equals("!=")) {
	    if (firstType.equals(secondType)) {
		if (!firstType.equals("int") && !firstType.equals("boolean")) {
		    System.err.println(operator + " solo opera con boolean or int");
		    System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
		} else {
		    returnType = "boolean";
		}
	    } else {
		System.err.println(firstType + " no es operable con " + secondType + ", usando " + operator);
		System.err.println("[L:" + op.getLineNumber() + "] " + op + "\n");
	    }
	}
	
	return returnType;
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
}
