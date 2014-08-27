import  org.antlr.runtime.*;
import  org.antlr.runtime.tree.*;
import compiler.parser.ParserDecaf;
import compiler.scanner.LexerDecaf;
 
public  class TestParser {
    public static void main(String[] args)  {
     try {
           LexerDecaf lex = new LexerDecaf(new ANTLRFileStream("test.txt"));
           CommonTokenStream tokens = new  CommonTokenStream(lex);
 
           ParserDecaf parser = new ParserDecaf(tokens);
           parser.document();
       }  catch(Throwable t) {
           System.out.println("exception: "+t);
           t.printStackTrace();
       }
    }
}
