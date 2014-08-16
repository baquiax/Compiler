import org.antlr.v4.runtime.*;
import compiler.scanner.LexerDecaf;

public class Test {
  public static void main(String[] args) throws Exception {
  	LexerDecaf lexer = new LexerDecaf(new ANTLRFileStream("test.txt"));
  	Token t = lexer.nextToken();
	while (t.getType() != Token.EOF) {
	    System.out.println(t.getType() + " " + t.getText());
	    t = lexer.nextToken();
	}
  }
}
