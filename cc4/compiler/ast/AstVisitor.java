package compiler.ast;
import compiler.parser.ParserDecaf;
import compiler.parser.ParserDecafVisitor;

public class AstVisitor extends ParserDecafVisitor<Node> {

	@Override
	public Node visitProgram(ParserDecaf.ProgramContext ctx) {
		return null;
	}
}