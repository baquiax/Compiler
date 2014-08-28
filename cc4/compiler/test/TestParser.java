import org.antlr.v4.runtime.*;
import compiler.scanner.LexerDecaf;
import compiler.parser.ParserDecaf;

public class TestParser {
  public static void main(String[] args) throws Exception {
    try {     
  (new ParserDecaf(new CommonTokenStream(new LexerDecaf(new ANTLRFileStream("test2.decaf"))))).start();
    } catch (ArrayIndexOutOfBoundsException aiobe) {
      System.err.println("usage: java Main <file>\nwhere file is the path to the filename with the tokens");
      System.exit(1);
    } catch (Exception e) {
      System.err.println("usage: jaca Main <file>\nwhere file is the path to the filename with the tokens");
      System.exit(1);
    } 
  }
}
