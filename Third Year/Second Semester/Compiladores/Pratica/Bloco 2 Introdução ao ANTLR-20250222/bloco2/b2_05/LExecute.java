import java.util.HashMap;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings("CheckReturnValue")

public class LExecute extends numbersBaseListener {

   @Override public void exitStat(numbersParser.StatContext ctx) {
      System.out.println("Hash map criado!");
   }

   @Override public void exitAssignment(numbersParser.AssignmentContext ctx) {
      Mapa_Correspondencias.put(ctx.TEXT().getText(), Integer.parseInt(ctx.NUM().getText()));
   }

   HashMap<String, Integer> Mapa_Correspondencias = new HashMap<>();

}
