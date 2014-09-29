package compiler.ast;
import compiler.parser.ParserDecaf;
import compiler.parser.ParserDecafBaseVisitor;
import java.util.List;
import org.antlr.v4.runtime.tree.*;

public class AstVisitor extends ParserDecafBaseVisitor<Node> {

    @Override
    public Node visitProgram(ParserDecaf.ProgramContext ctx) {
    	Program program = new Program();
    	List<ParserDecaf.Field_declContext> fields = ctx.field_decl();
    	List<ParserDecaf.Method_declContext> methods = ctx.method_decl();

    	for (ParserDecaf.Field_declContext e : fields) {
    	    program.add(visit(e));
        }

    	for (ParserDecaf.Method_declContext e : methods) {
    	    program.add(visit(e));
    	}
    	return program;
    }

    @Override
    public Node visitFieldDecl(ParserDecaf.FieldDeclContext ctx) {
    	FieldDecl fd = new FieldDecl(ctx.type().getText());
    	List<ParserDecaf.Field_decl_derivContext> decls = ctx.field_decl_deriv();        
    	for (ParserDecaf.Field_decl_derivContext e : decls) {
            Node n = visit(e);            
            if (n.getClass().getName().equals("compiler.ast.Var"))   {
                Var v = (Var)visit(e);
                v.setType(ctx.type().getText());
                fd.add(v);
            } else {
                Array a = (Array)visit(e);
                a.setType(ctx.type().getText());
                fd.add((Node)a);
            }            
    	}
    	return fd;
    }

    @Override
    public Node visitMethodDecl(ParserDecaf.MethodDeclContext ctx) {
        String type = (ctx.VOID() == null) ? ctx.type().getText() : ctx.VOID().getText();
    	MethodDecl m = new MethodDecl(ctx.ID().getText(), type, visit(ctx.block()));
        ParserDecaf.MethodParamContext  mp = (ParserDecaf.MethodParamContext)ctx.method_param();
        if (mp != null) {                        
            List<TerminalNode> ids = mp.ID();
            int i = 0;
            for (ParserDecaf.TypeContext t : mp.type()) {
                if (t.getText().equals("int")) {
                    m.addParameter(new Var("int", ids.get(i).getText()));
                } else {
                    m.addParameter(new Var("boolean", ids.get(i).getText()));
                }
                i++;
            }
        }
    	return m;
    }

    @Override
    public Node visitVarDecl(ParserDecaf.VarDeclContext ctx) {
    	Var v = new Var(ctx.ID().getText());
    	return v;
    }

    @Override
    public Node visitArrayDecl(ParserDecaf.ArrayDeclContext ctx) {
    	Array v = new Array(ctx.ID().getText(), ctx.int_literal().getText());
    	return v;
    }

    @Override
    public Node visitVarDecls(ParserDecaf.VarDeclsContext ctx) {
        FieldDecl fd = new FieldDecl(ctx.type().getText());
        for (TerminalNode n : ctx.ID()) {
            fd.add(new Var(ctx.type().getText(), n.getText()));
        }
        return fd;
    }

    @Override
    public Node visitBlockDecl(ParserDecaf.BlockDeclContext ctx) {
        Block b = new Block();
        for(ParserDecaf.Var_declsContext e : ctx.var_decls()) {
            b.addVarDecl(visit(e));
        }
        for(ParserDecaf.StatementsContext e : ctx.statements()) {
            b.addStatement(visit(e));
        }
        return b;
    }

    @Override
    public Node visitArrayLocation(ParserDecaf.ArrayLocationContext ctx) {
        String id = ctx.ID().getText();
        Array a = new Array(id);
        a.setIndex(visit(ctx.expr()));
        return a;
    }

    @Override
    public Node visitIdLocation(ParserDecaf.IdLocationContext ctx) {
        String id = ctx.ID().getText();
        Var v = new Var(id);
        return v;
    }

    @Override
    public Node visitLocationAssign(ParserDecaf.LocationAssignContext ctx) {
        String operator = ctx.assign_op().getText();
        Node location = visit(ctx.location());        
        Assign a = new Assign(operator, location, visit(ctx.expr()));
        return a;
    }

    @Override
    public Node visitExprLiteral(ParserDecaf.ExprLiteralContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public Node visitIntLiteral(ParserDecaf.IntLiteralContext ctx) {
        return new IntLiteral(ctx.int_literal().getText());
    }
}