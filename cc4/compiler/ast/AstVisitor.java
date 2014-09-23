package compiler.ast;
import compiler.parser.ParserDecaf;
import compiler.parser.ParserDecafBaseVisitor;
import java.util.List;

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
	for (ParserDecaf.Field_decl_derivContext e: decls) {
	    fd.add(visit(e));
	}
	return fd;
    }

    @Override
    public Node visitMethodDecl(ParserDecaf.MethodDeclContext ctx) {
	MethodDecl m = new MethodDecl(ctx.id().getText());
	m.setReturnType((ctx.VOID() == null) ? visit(ctx.type()) : visit(ctx.VOID()));
	return m;
    }
}