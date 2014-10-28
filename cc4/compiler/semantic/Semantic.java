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
		    //Check now!
		    checkProgram(ast.getProgram());
		    if (Debug.debugEnabled("semantic")) {
				System.out.println("debugging: SEMANTIC");
				Semantic.globalScope.print();		
		    }
		    if(Configuration.stopStage == Semantic.level) {
				String fileName = Configuration.flags.get("-o");
				if (fileName == null) {
				    fileName = Configuration.flags.get("inputFile");
				    fileName += ".semantic";
				}
				Configuration.makeOutput(fileName, Semantic.globalScope.toString(""));
		    }
        }
    }
    
    public void checkProgram(Program p) {
		List<FieldDecl> fieldDecls = p.getFields();
		for (FieldDecl f : fieldDecls) {
		    for (Node n : f.getFields()) {
				if (n.getClass().getName().equals(Var.class.getName())) {
				    Var v = (Var) n;
				    if (!this.addSymbol(v.getName(), new VarSymbol(v))) {
						System.err.println(v.getName() + " ya esta definido!");
					    System.err.println("[L:" + f.getLineNumber() +  "] " + f + "\n");	
				    }
				} else {
				    //It's an array.
				    Array a = (Array) n;
				    if (!this.addSymbol(a.getName(), new ArraySymbol(a))) {
						System.err.println(a.getName() + " ya esta definido!");	
						System.err.println("[L:" + f.getLineNumber() +  "] " + f + "\n");
				    }
				}
	    	}			
		}
	
		List<MethodDecl> methodsDecls =	p.getMethods();
		for (MethodDecl n : methodsDecls) {
		    MethodDecl m = (MethodDecl) n;
		    MethodSymbol mt = new MethodSymbol(m);
		    
		    if(!this.addSymbol(m.getName(), mt)) {
				//Not valid scope
				Scope.scopes--;
				System.err.println(m.getName() + " ya esta definido!");
				System.err.println("[L:" + m.getLineNumber() +  "] " + m + "\n");	    
		    }
		    mt.check();
		}
		this.checkMainMethod();

    }
    
    public void checkMainMethod() {
    	if(this.globalScope.getSymbol("main()") == null) {
			System.err.println("No ha definido un metodo main para el programa.");
    	}
    }

    public void checkMethod(MethodDecl m) {		
	
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
	    this.checkFor((For)n);
	} else if (st.equals(ReservedWord.class.getName())) {
	    //break and continue ontly in for context.
	    ReservedWord rw = (ReservedWord)n;	    
	    Scope s = Semantic.currentScope;
	    while(s != null) {
		if (s.getClass().getName().equals(ForScope.class.getName()))
		    break;
		s = s.getParent();
	    }
	    
	    if (s == null || !s.getClass().getName().equals(ForScope.class.getName())) {
		System.err.println(((ReservedWord)n) + " es usado unicamente en bloques de iteracion.");
		System.err.println("[L:" + rw.getLineNumber() + "] " + rw + "\n");
	    }
	    
	} else if (st.equals(Return.class.getName())) {
	    Return r = (Return)n;
	    Scope scope = Semantic.currentScope;
	    while(scope != null) {
		if (scope.getClass().getName().equals(MethodScope.class.getName()))
		    break;
		scope = scope.getParent();
	    }
	    
	    if ( scope != null ) {
		MethodScope s = (MethodScope)scope;
		s.returnFound(true);
		MethodDecl md = s.getMethod();		
		if (r.getExpr() == null && !md.getReturnType().equals("void")) {
		    //void
		    System.err.println("Se retorna void cuando se espera " + md.getReturnType());
		    System.err.println("[L:" + r.getLineNumber() + "] " + r + "\n");
		} else if (r.getExpr() != null) {
		    String returnType = this.checkExpr(r.getExpr());
		    if (!returnType.equals(md.getReturnType())) {
			System.err.println("Se retorna " +  returnType + " cuando se espera " + md.getReturnType());
			System.err.println("[L:" + r.getLineNumber() + "] " + r + "\n");
		    }
		}
	    }
	    
	} else if (st.equals(Block.class.getName())) { 		
	    this.checkBlock((Block)n);
	}
    }

    public void checkBlock(Block b) {
	for(Node vd : b.getVarDecl()) {
	    if (vd.getClass().getName().equals(FieldDecl.class.getName())) {
		FieldDecl fd = (FieldDecl) vd;
		for (Node n : fd.getFields()) {
		    if (n.getClass().getName().equals(Var.class.getName())) {
			Var v = (Var) n;
			if (!this.addSymbol(v.getName(), new VarSymbol(v))) {
			    System.err.println(v.getName() + " ya está definido.");
			    System.err.println("[L:" + fd.getLineNumber() + "] " + v + "\n");
			}
		    }
		}					
	    }
	}
	
	for (Node n: b.getStatements()) {
	    this.checkStatement(n);	    
	}
    }

    public String checkAssign(Assign as) {
	Symbol location = null;
	//Check if location is defined
	if (as.getLocation().getClass().getName().equals(Var.class.getName())) {
	    Var v = (Var) as.getLocation();
	    location = this.getSymbolInAll(v.getName());
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

	    String arrayIndexType = this.checkExpr(a.getIndex());
	    if (!arrayIndexType.equals("int")) {
		System.err.println("Los indices de un Array deben ser int");
		System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
	    } else if(a.getIndex().getClass().getName().equals(IntLiteral.class.getName())) {
		int intArrayIndex = Integer.parseInt(((IntLiteral)a.getIndex()).toString());
		if (intArrayIndex < 0) {
		    System.err.println("Indice fuera de rango");
		    System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
		}
	    } else if(a.getIndex().getClass().getName().equals(Negation.class.getName())) { 
		Negation n = (Negation) a.getIndex();
		if (n.getExpr().getClass().getName().equals(IntLiteral.class.getName())) {
		    System.err.println("Indice fuera de rango. Validos unicamente 0...N");
		    System.err.println("[L:" + as.getLineNumber() + "] " + as + "\n");
		}
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
	    System.err.println("[L:" + ifStat.getLineNumber() +  "] " + ifStat + "\n");
	}
	BlockType bt = new BlockType((Block)ifStat.getConsecuent());
	this.addSymbol("if (" + ifStat.getCondition() + ")", bt);
	Semantic.currentScope = bt.getScope();
	this.checkBlock((Block)ifStat.getConsecuent());
	Semantic.currentScope = bt.getScope().getParent();
	if (ifStat.getAlternative() != null) {
	    BlockType bta = new BlockType((Block)ifStat.getAlternative());
	    this.addSymbol("if (" + ifStat.getCondition() + "):else", bta);
	    Semantic.currentScope = bta.getScope();
	    this.checkBlock((Block)ifStat.getAlternative());
	    Semantic.currentScope = bta.getScope().getParent();
	}
    }

    public void checkFor(For forStat) {	
	//It's too a declaration.
	Var location = (Var)forStat.getInit().getLocation();
	location.setType("int");
	
	ForSymbol ft = new ForSymbol((For)forStat);
	this.addSymbol("for " + forStat.getInit() + ", " + forStat.getCondition(), ft);
	Semantic.currentScope = ft.getScope();
	this.addSymbol(location.getName(), new VarSymbol(location));	
	
	this.checkStatement(forStat.getInit());
	String conditionType = this.checkExpr(forStat.getCondition());
	if (!conditionType.equals("int")) {
	    System.err.println("La condicion del FOR debe ser un valor de alcance int");
	    if (forStat.getInit() instanceof ILineNumber) {
		ILineNumber ln = (ILineNumber) forStat.getInit();
		System.err.println("[L:" + ln.getLineNumber() +  "] " + forStat.getCondition() + "\n");
	    }
	}
	
	this.checkBlock((Block)forStat.getBlock());
	Semantic.currentScope = ft.getScope().getParent();
    }

    public String checkExpr(Node n) {
	if (n.getClass().getName().equals(Var.class.getName())) {
	    Var v = (Var) n;
	    Symbol t = this.getSymbolInAll(v.getName());
	    if (t != null)
		return t.getType();
	    else {
		System.err.println(v.getName() + " no está definido.");
		System.err.println("[L:" + v.getLineNumber() + "] " + v + "\n");
	    }
	} else if (n.getClass().getName().equals(Array.class.getName())) {
	    Array a = (Array) n;
	    Symbol t = this.getSymbolInAll(a.getName());
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
	    Negation neg = (Negation)n;
	    String eType = this.checkExpr(neg.getExpr());
	    if (!eType.equals("int") && !eType.equals("boolean")) {
		System.err.println("Operación invalida. No posible con el tipo dado: " + eType);
		System.err.println("[L:" + neg.getLineNumber() + "] " + neg + "\n");
	    } else {
		return eType;
	    }
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

	MethodSymbol mt = (MethodSymbol) Semantic.globalScope.getSymbol(methodKey);
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
    
    private boolean addSymbol(String k, Symbol v) {
	//Check if already defined
	if (Semantic.currentScope.getSymbol(k) == null) {
	    Semantic.currentScope.insertSymbol(k, v);
	    return true;
	} else {
	    return false;	    	    
	}
    }
        
    private Symbol getSymbolInAll(String k) {
	Scope s = Semantic.currentScope;
	while(s != null) {
	    Symbol r = s.getSymbol(k);
	    if (r != null) {
		return r;
	    }
	    s = s.getParent();
	}
	return null;
    }    
}
