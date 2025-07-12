import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class GrammarIIMLMain {
   public static void main(String[] args) {
      try {
         InputStream fileInput = new FileInputStream(args[0]);
         CharStream input = CharStreams.fromStream(fileInput);
         // create a CharStream that reads from standard input:
         // CharStream input = CharStreams.fromStream(System.in);
         // create a lexer that feeds off of input CharStream:
         GrammarIIMLLexer lexer = new GrammarIIMLLexer(input);
         // create a buffer of tokens pulled from the lexer:
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         // create a parser that feeds off the tokens buffer:
         GrammarIIMLParser parser = new GrammarIIMLParser(tokens);
         // replace error listener:
         //parser.removeErrorListeners(); // remove ConsoleErrorListener
         //parser.addErrorListener(new ErrorHandlingListener());
         // begin parsing at program rule:
         ParseTree tree = parser.program();
         if (parser.getNumberOfSyntaxErrors() == 0) {
            // print LISP-style tree:
            // System.out.println(tree.toStringTree(parser));
            IIMLImageGenerator visitor0 = new IIMLImageGenerator();
            visitor0.visit(tree);
         }
      }
      catch(IOException e) {
         e.printStackTrace();
         System.exit(1);
      }
      catch(RecognitionException e) {
         e.printStackTrace();
         System.exit(1);
      }
   }
}
