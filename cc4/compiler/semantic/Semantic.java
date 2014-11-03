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
				    	this.reportError(v.getName() + " ya esta definido!", f);
				    }
				} else {
				    //It's an array.
				    Array a = (Array) n;
				    if (!this.addSymbol(a.getName(), new ArraySymbol(a))) {
				    	this.reportError(a.getName() + " ya esta definido!", f);
				    }
				}
	    	}			
		}
	
		List<MethodDecl> methodsDecls =	p.getMethods();
		for (MethodDecl n : methodsDecls) {
		    MethodDecl m = (MethodDecl) n;
		    MethodSymbol ms = new MethodSymbol(m);		    
		    if(!this.addSymbol(m.getName(), ms)) {
				//Not valid scope
				Scope.scopes--;
				this.reportError(m.getName() + " ya esta definido!", m);
		    }
		    this.checkMethod(ms);		    
		}

		//Verify if void main() is defined
		this.checkMainMethod();
    }
    
    public void checkMainMethod() {
    	if(this.globalScope.getSymbol("main()") == null) {
			System.err.println("No ha definido un metodo main para el programa.");
    	}
    }

    public void checkMethod(MethodSymbol ms) {
		Semantic.currentScope = ms.getScope();
        MethodDecl md = (MethodDecl)ms.getNode();
        for (Node n : md.getParameters()) {
            if (n.getClass().getName().equals(Var.class.getName())) {
                Var v = (Var) n;
                Semantic.currentScope.insertSymbol(v.getName(), new VarSymbol(v));
            } else {
                //It's an array.
                Array a = (Array) n;
                Semantic.currentScope.insertSymbol(a.getName(), new ArraySymbol(a));
            }
        }        
        //Check block method        
        this.checkBlock(md.getBlock());        

        //Check return
        if (ms.getScope().getReturnStatements().size() > 0) {
        	for (Return r : ms.getScope().getReturnStatements()) {
        		String returnType = "void";
        		if (r.getExpr() != null) {
        			returnType = this.checkExpr(r.getExpr());
        		}         		
        		if (!returnType.equals(md.getReturnType())) {
		    		this.reportError("Se retorna " +  returnType + " cuando se espera " + md.getReturnType(), r);
		    	}
        	}
        } else if (!md.getReturnType().equals("void")) {
        	this.reportError("No se ha indicado valor de retorno!. " + md.getReturnType() + " es requerido.", md);
        }

        Semantic.currentScope = Semantic.currentScope.getParent();
    }

    public void checkBlock(Block b) {
		//Cehck var decls
		for(Node vd : b.getVarDecl()) {	    	
			FieldDecl fd = (FieldDecl) vd;
			for (Node n : fd.getFields()) {
	    		if (n.getClass().getName().equals(Var.class.getName())) {
					Var v = (Var) n;
					if (!this.addSymbol(v.getName(), new VarSymbol(v))) {
						this.reportError(v.getName() + " ya esta definido.", fd);		    			
					}
	    		}
			}						    	
		}

		//Check statements
		for(Node st : b.getStatements()) {
			this.checkStatement(st);
		}
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
		   this.checkReserverWord((ReservedWord)n);
		} else if (st.equals(Return.class.getName())) {
		   this.checkReturn((Return)n);
		} else if (st.equals(Block.class.getName())) { 		
		    this.checkBlock((Block)n);
		}
    }

    public void checkReserverWord(ReservedWord rw) {
 		//break and continue ontly in for context.	    
	    Scope s = Semantic.currentScope;
	    while(s != null) {
			if (s.getScopeType().equals("for"))
			    break;
			s = s.getParent();
	    }
	    
	    if (s == null || !s.getScopeType().equals("for")) {
	    	this.reportError(rw + " es usado unicamente en bloques de iteracion.", rw);
	    }
    }

    public void checkReturn(Return r) { 		 
    	Scope scope = Semantic.currentScope;
	    while(scope != null) {
			if (scope.getClass().getName().equals(MethodScope.class.getName()))
			    break;
			scope = scope.getParent();
	    }
	    
	    if ( scope != null ) {
			MethodScope s = (MethodScope)scope;
			s.returnFound(r);
	    }
    }

    public String checkAssign(Assign as) {
		Symbol location = null;
		//Check if location is defined
		if (as.getLocation().getClass().getName().equals(Var.class.getName())) {
		    Var v = (Var) as.getLocation();
		    location = this.getSymbolInAll(v.getName());
		    if (location == null) {
		    	this.reportError(v.getName() + " no esta definido.", as);
		    }
		} else if (as.getLocation().getClass().getName().equals(Array.class.getName())) {
		    //Array is only defined in global scope.
		    Array a = (Array) as.getLocation();
		    location = Semantic.globalScope.getSymbol(a.getName());
		    if (location == null) {
		    	this.reportError(a.getName() + " no esta definido.", as);				
		    }
		    String arrayIndexType = this.checkExpr(a.getIndex());
		    if (!arrayIndexType.equals("int")) {
		    	this.reportError("Los indices de un Array deben ser int", as);				
		    } else if(a.getIndex().getClass().getName().equals(IntLiteral.class.getName())) {
				int intArrayIndex = Integer.parseInt(((IntLiteral)a.getIndex()).toString());
				if (intArrayIndex < 0) {
					this.reportError("Indice fuera de rango", as);
				}
		    } else if(a.getIndex().getClass().getName().equals(Negation.class.getName())) { 
				Negation n = (Negation) a.getIndex();
				if (n.getExpr().getClass().getName().equals(IntLiteral.class.getName())) {
					this.reportError("Indice fuera de rango. Validos unicamente 0...N", as);				    
				}
		    }
		}
	
		//Check Types!
		Node e = as.getE();
		String eType = this.checkExpr(e);
		
		if (location != null) {	    
		    String locationType = location.getType();	    	    
		    //Assignation type.
		    if (!locationType.equals(eType)) {
		    	this.reportError(eType + " no es asignable para " + locationType, as);
		    } else {
				return eType;
		    }
		}
		return "error";
    }

    public void checkIf(If ifStat) {
		String conditionType = this.checkExpr(ifStat.getCondition());
		if (!conditionType.equals("boolean")) {
			this.reportError("Condicion de IF debe usar valores booleanos", ifStat);		    
		}		
		IfSymbol is = new IfSymbol(ifStat);		
		this.addSymbol("if (" + ifStat.getCondition() + ")", is);
		Semantic.currentScope = is.getConsecuentScope();
		this.checkBlock((Block)ifStat.getConsecuent());
		Semantic.currentScope = Semantic.currentScope.getParent();
		if (ifStat.getAlternative() != null) {		    
		    Semantic.currentScope = is.getAlternativeScope();
		    this.checkBlock((Block)ifStat.getAlternative());
		    Semantic.currentScope = Semantic.currentScope.getParent();
		}
    }

    public void checkFor(For forStat) {	
		//Initialization is a declaration type 'int'
		Var location = (Var)forStat.getInit().getLocation();
		location.setType("int");
		
		ForSymbol ft = new ForSymbol((For)forStat);
		this.addSymbol("for " + forStat.getInit() + ", " + forStat.getCondition(), ft);
		Semantic.currentScope = ft.getScope();
		this.addSymbol(location.getName(), new VarSymbol(location));			
		this.checkStatement(forStat.getInit());

		String conditionType = this.checkExpr(forStat.getCondition());
		if (!conditionType.equals("int")) {			
		    if (forStat.getInit() instanceof ILineNumber) {		    	
				ILineNumber ln = (ILineNumber) forStat.getInit();
				this.reportError("La condicion del FOR debe ser un valor de alcance int", ln);
		    }
		}
		
		this.checkBlock((Block)forStat.getBlock());
		Semantic.currentScope = Semantic.currentScope.getParent();
    }

    public String checkExpr(Node n) {
		if (n.getClass().getName().equals(Var.class.getName())) {
		    Var v = (Var) n;
		    Symbol t = this.getSymbolInAll(v.getName());
		    if (t != null)
				return t.getType();
		    else
		    	this.reportError(v.getName() + " no esta definido.", v);
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
		    	this.reportError("Operación invalida. No posible con el tipo dado: " + eType, neg);				
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

		MethodSymbol ms = (MethodSymbol) Semantic.globalScope.getSymbol(methodKey);
		if (ms == null) {		    
		    this.reportError("Metodo " + methodKey + " no definido", cm);
		    return "error";
		} else {
		    MethodDecl md = (MethodDecl) ms.getNode();
		    return md.getReturnType();
		}
    }

    public String checkBinOp(BinOp op) {
		String operator = op.getOperator();	
		String firstType = this.checkExpr(op.getFirst());
		String secondType = this.checkExpr(op.getSecond());
		String resultType = TypeManager.checkType(firstType, secondType, operator);
		if (resultType.equals("error")) {
			this.reportError("Operacion " + operator + " entre tipos " + firstType + " y " + secondType + ", no soportados", op);
		}		
		return resultType;
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
		Scope scope = Semantic.currentScope;
		while(scope != null) {
		    Symbol symbol = scope.getSymbol(k);
		    if (symbol != null) {
				return symbol;
		    }
		    scope = scope.getParent();
		}
		return null;
    }

    private void reportError(String message, ILineNumber node) {
		System.err.println("ERROR: " + message);
		System.err.println("[L:" + node.getLineNumber() + "] " + node + "\n");
    }
}
