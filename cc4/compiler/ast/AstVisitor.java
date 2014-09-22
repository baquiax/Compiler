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
}