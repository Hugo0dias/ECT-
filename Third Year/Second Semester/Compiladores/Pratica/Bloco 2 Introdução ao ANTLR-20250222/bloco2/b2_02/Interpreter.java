import java.util.Iterator;

@SuppressWarnings("CheckReturnValue")
public class Interpreter extends SuffixCalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(SuffixCalculatorParser.ProgramContext ctx) {
      Iterator<SuffixCalculatorParser.StatContext> iterator = ctx.stat().iterator();
      while(iterator.hasNext()) {
         System.out.println(visit(iterator.next()));
      }
      return null;
   }

   @Override public Double visitStat(SuffixCalculatorParser.StatContext ctx) {
      if (ctx.expr() != null){
         Double res = visit(ctx.expr());
         System.out.println(res);
         return res;
      }
      return null;
   }

   @Override public Double visitExprNumber(SuffixCalculatorParser.ExprNumberContext ctx) {
      Double res = Double.parseDouble(ctx.Number().getText());
      return res;
   }

   @Override public Double visitExprSuffix(SuffixCalculatorParser.ExprSuffixContext ctx) {
      Double exp1 = visit(ctx.expr(0));
      Double exp2 = visit(ctx.expr(1));
      String op = ctx.op.getText();
      switch (op) {
         case "*":
            return exp1 * exp2;
         case "/":
            if (exp2 == 0) {
               return null;
            }
            return exp1 / exp2;
         case "+":
            return exp1 + exp2;
         case "-":
            return exp1 - exp2;
         default:
            return null;
      }
      //return res;
   }
}
