package compiler.irt;
import compiler.lib.Debug;
import compiler.semantic.Semantic;
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
        }/* else {
        	System.out.println("El proceso se ha detenido.");
        }*/		
	}	
	
}
