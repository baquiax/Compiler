package compiler.semantic;
import compiler.ast.MethodDecl;
import compiler.ast.Node;
import compiler.ast.Var;
import compiler.ast.Array;

public class MethodSymbol extends Symbol {
    private MethodScope scope;
    private MethodDecl node;
    
    public MethodSymbol(MethodDecl method) {
	   this.node = method;
	   this.scope = new MethodScope(Semantic.currentScope, node);
    }
    
    @Override
    public Node getNode() {
	   return this.node;
    }
    
    @Override
    public String getType() {
	   return this.node.getReturnType();
    }

    public void check() {
        Semantic.currentScope = this.getScope();
        
        //Do check
        for (Node n : m.getParameters()) {
            if (n.getClass().getName().equals(Var.class.getName())) {
                Var v = (Var) n;
                Semantic.currentScope.addSymbol(v.getName(), new VarSymbol(v));
            } else {
                //It's an array.
                Array a = (Array) n;
                Semantic.currentScope.addSymbol(a.getName(), new ArraySymbol(a));
            }
        }
        
        //Check block method        
        this.checkBlock(b);
        
        //Check 
        MethodScope ms = (MethodScope) Semantic.currentScope;
        if (!ms.isReturnFound() && !m.getReturnType().equals("void")) {
            System.err.println("No se ha indicado valor de retorno!. " + m.getReturnType() + " es requerido.");
            System.err.println("[L:" + m.getLineNumber() + "] " + m + "\n");
        }

        Semantic.currentScope = this.getScope().getParent();
    }

    public MethodScope getScope() {
	   return this.scope;
    }
}
