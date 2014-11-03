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
    	    program.addField((FieldDecl)visit(e));
        }

    	for (ParserDecaf.Method_declContext e : methods) {
    	    program.addMethod((MethodDecl)visit(e));
    	}
    	return program;
    }

    @Override
    public Node visitFieldDecl(ParserDecaf.FieldDeclContext ctx) {
    	FieldDecl fd = new FieldDecl(ctx.type().getText());
	fd.setLineNumber(ctx.start.getLine());
    	List<ParserDecaf.Field_decl_derivContext> decls = ctx.field_decl_deriv();        
    	for (ParserDecaf.Field_decl_derivContext e : decls) {
            Node n = visit(e);            
            if (n.getClass().getName().equals(Var.class.getName()))   {
                Var v = (Var)visit(e);
                v.setType(ctx.type().getText());
                fd.add(v);
            } else {
                Array a = (Array)visit(e);
                a.setType(ctx.type().getText());
                fd.add(a);
            }            
    	}
    	return fd;
    }



    @Override
    public Node visitMethodDecl(ParserDecaf.MethodDeclContext ctx) {
        String type = (ctx.VOID() == null) ? ctx.type().getText() : ctx.VOID().getText();
    	MethodDecl m = new MethodDecl(ctx.ID().getText(), type, (Block)visit(ctx.block()));
        m.setLineNumber(ctx.start.getLine());
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
	v.setLineNumber(ctx.start.getLine());
    	return v;
    }

    @Override
    public Node visitArrayDecl(ParserDecaf.ArrayDeclContext ctx) {
    	Array v = new Array(ctx.ID().getText(), ctx.int_lit().getText());
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
	v.setLineNumber(ctx.start.getLine());
        return v;
    }

    @Override
    public Node visitLocationAssign(ParserDecaf.LocationAssignContext ctx) {
        String operator = ctx.assign_op().getText();
        Node location = visit(ctx.location());        
        Assign a = new Assign(operator, location, visit(ctx.expr()));
	a.setLineNumber(ctx.start.getLine());
        return a;
    }

    @Override
    public Node visitIntLiteral(ParserDecaf.IntLiteralContext ctx) {
        return new IntLiteral(ctx.INT_UNSIGNED().getText());
    }

    @Override
    public Node visitHexLiteral(ParserDecaf.HexLiteralContext ctx) {
        return new HexLiteral(ctx.HEX_LITERAL().getText());
    }

    @Override
    public Node visitBoolLiteral(ParserDecaf.BoolLiteralContext ctx) {
        return new BoolLiteral(ctx.BOOL_LITERAL().getText());
    }

    @Override
    public Node visitCharLiteral(ParserDecaf.CharLiteralContext ctx) {
        return new CharLiteral(ctx.CHAR_LITERAL().getText());
    }

    @Override
    public Node visitExprBinOp(ParserDecaf.ExprBinOpContext ctx) {
        Node lo = visit(ctx.expr(0));
        Node ro = visit(ctx.expr(1));
	BinOp op = new BinOp(ctx.bin_op().getText(), lo, ro);
	op.setLineNumber(ctx.start.getLine());
        return op;
    }

    @Override
    public Node visitExprArithAdd(ParserDecaf.ExprArithAddContext ctx) {
        Node lo = visit(ctx.expr(0));
        Node ro = visit(ctx.expr(1));
	BinOp op = new BinOp(ctx.arith_add().getText(), lo, ro);
	op.setLineNumber(ctx.start.getLine());
        return op;
    }

    @Override
    public Node visitExprArithMult(ParserDecaf.ExprArithMultContext ctx) {
        Node lo = visit(ctx.expr(0));
        Node ro = visit(ctx.expr(1));
	BinOp op = new BinOp(ctx.arith_mult().getText(), lo, ro); 
	op.setLineNumber(ctx.start.getLine());
        return op;
    }

    @Override
    public Node visitExprLiteral(ParserDecaf.ExprLiteralContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public Node visitExprEnclosed(ParserDecaf.ExprEnclosedContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitIf(ParserDecaf.IfContext ctx) {
        Node con = visit(ctx.expr());
        Node then = visit(ctx.block());
        Node ifElse = null;
        if (ctx.if_else() != null) {
            ifElse = visit(ctx.if_else());
        }
	If ifR = new If(con,then, ifElse);
	ifR.setLineNumber(ctx.start.getLine());
        return ifR;
    }

    @Override
    public Node visitIfElse(ParserDecaf.IfElseContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public Node visitFor(ParserDecaf.ForContext ctx) {
        Assign init = new Assign("=", new Var(ctx.ID().getText()), visit(ctx.expr().get(0)));
	   init.setLineNumber(ctx.start.getLine());
	   init.setLineNumber(ctx.start.getLine());
        Node cond = visit(ctx.expr().get(1));
        return new For(init, cond, visit(ctx.block())); 
    }

    @Override
    public Node visitSubBlock(ParserDecaf.SubBlockContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public Node visitBreak(ParserDecaf.BreakContext ctx) {
        ReservedWord rw = new ReservedWord(ctx.BREAK().getText());
	rw.setLineNumber(ctx.start.getLine());
	return rw;
    }

    @Override
    public Node visitContinue(ParserDecaf.ContinueContext ctx) {
	ReservedWord rw = new ReservedWord(ctx.CONTINUE().getText());
	rw.setLineNumber(ctx.start.getLine());
	return rw;
    }

    @Override
    public Node visitReturn(ParserDecaf.ReturnContext ctx) {
	Return r;
        if (ctx.expr() != null)
            r = new Return(visit(ctx.expr()));
        else
            r = new Return();
	r.setLineNumber(ctx.start.getLine());
	return r;
    }    

    @Override
    public Node visitExprNegative(ParserDecaf.ExprNegativeContext ctx) {	
        Negation n =  new Negation(visit(ctx.expr()));
	n.setLineNumber(ctx.start.getLine());
	return n;
    }

    @Override
    public Node visitExprNegation(ParserDecaf.ExprNegationContext ctx) {
        Negation n =  new Negation(visit(ctx.expr()));
	n.setLineNumber(ctx.start.getLine());
	return n;
    }
 
    @Override
    public Node visitCalloutArgExpr(ParserDecaf.CalloutArgExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Node visitCalloutArgStringLit(ParserDecaf.CalloutArgStringLitContext ctx) {
        return visit(ctx.string_literal());
    }

    @Override
    public Node visitStringLit(ParserDecaf.StringLitContext ctx) {
        return new StringLiteral(ctx.STRING_LITERAL().getText());
    }

    @Override
    public Node visitExprMethodCall(ParserDecaf.ExprMethodCallContext ctx) {
        return visit(ctx.method_call());
    }

    @Override
    public Node visitCalloutExpr(ParserDecaf.CalloutExprContext ctx) {
        Args a = new Args();
        for (ParserDecaf.ExprContext n : ctx.expr()) {
            a.addArg(visit(n));
        }
        return a;
    }

    @Override    
    public Node visitMethodCallStat(ParserDecaf.MethodCallStatContext ctx) {
        return visit(ctx.method_call());
    }

    @Override
    public Node visitMethodCall(ParserDecaf.MethodCallContext ctx) {
        CallMethod c = new CallMethod(ctx.ID().getText());
	c.setLineNumber(ctx.start.getLine());
        if(ctx.callout_expr() != null) {            
            c.setArgs(visit(ctx.callout_expr()));
        }
        return c;
    }

    @Override
    public Node visitCalloutCall(ParserDecaf.CalloutCallContext ctx) {
        CallCallout c = new CallCallout(ctx.string_literal().getText());
        if (ctx.callout_args() != null)
            c.setArgs(visit(ctx.callout_args()));
        return c;
    }

    @Override
    public Node visitCalloutArgs(ParserDecaf.CalloutArgsContext ctx) {
        Args a = new Args();
        for (ParserDecaf.Callout_argContext n : ctx.callout_arg()) {
            a.addArg(visit(n));
        }
        return a;
    }
}