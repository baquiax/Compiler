package compiler.irt;
import compiler.lib.Debug;
import compiler.semantic.Semantic;
import compiler.opt.Algebraic;
import compiler.opt.ConstantFolding;
import compiler.lib.Configuration;
import compiler.ast.*;
import java.util.LinkedList;
import java.util.List;

public class Irt {	
	public static final int level = 5;
	private Semantic semantic;
	public static LinkedList<String> listSintaxis;
	public static LinkedList<String> listInstruction;
	
	public Irt(Semantic semantic) {
		this.semantic = semantic;
		listSintaxis = new LinkedList<String>();
		listInstruction = new LinkedList<String>();
	}
	
	public void translateAst() {
		//VERIFICAR EL FLAG -opt
		if (Configuration.flags.get("-opt") != null) {
			if (Configuration.flags.get("-opt").equals("algebraic")) {
				Algebraic.optimize(this);
			} else {
				ConstantFolding.optimize(this);
			}
		}

		if (Configuration.stopStage >= Irt.level) {
        	System.out.println("stage: IRT");
        	if (Debug.debugEnabled("irt")) System.out.println("debugging: IRT");
        }
        
        
	}
	
	public void initTranslation() {
        Program p = this.semantic.getAst().getProgram();
		List<FieldDecl> fieldDecls = p.getFields();
		for (FieldDecl f : fieldDecls) {
		    for (Node n : f.getFields()) {
				if (n.getClass().getName().equals(Var.class.getName())) {
				    this.translateVar((Var) n);
				} else {
				    this.translateArray((Array) n);
				}
            }
		}
		
		List<MethodDecl> methodsDecls =	p.getMethods();
		for (MethodDecl n : methodsDecls) {
		    this.translateMethod(n);
		}
	}
	
	public void translateVar(Var v) {
	    
	}
	
	public void translateArray(Array a) {
	    
	}
	
	public void translateMethod(MethodDecl m) {
	    
	}
	
}
