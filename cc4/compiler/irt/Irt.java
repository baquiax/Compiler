package compiler.irt;
import compiler.lib.Debug;
import compiler.semantic.Semantic;
import compiler.scanner.Scanner;
import compiler.codegen.Codegen;
import compiler.opt.Algebraic;
import compiler.opt.ConstantFolding;
import compiler.lib.Configuration;
import java.util.ArrayList;

public class Irt {	
	
	public static final int level = 5;
	private Semantic semantic;
	private ArrayList<String>  listSintaxis;
	
	public Irt(Semantic semantic) {
		this.semantic = semantic;
		
		System.out.println("stage: IRT");
        if (Debug.debugEnabled("irt")) System.out.println("debugging: IRT");		
	}
	
	public void translateAst() {
		//VERIFICAR EL FLAG -opt
		if (Scanner.flags.get("-opt") != null) {
			if (Scanner.flags.get("-opt").equals("algebraic")) {
				Algebraic.optimize(this);
			} else {
				ConstantFolding.optimize(this);
			}
		}

		if (Scanner.stopStage > Irt.level) {
        	Codegen codegen = new Codegen(this);
        	codegen.generate();
        } else {
        	System.out.println("El proceso se ha detenido.");
        }		
	}	
	
}
