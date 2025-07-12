@SuppressWarnings("CheckReturnValue")
public class Interpreter3 extends CalculatorBaseVisitor<Double> {

   @Override public Double visitProgram(CalculatorParser.ProgramContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitStat(CalculatorParser.StatContext ctx) {
      Double res = (Double)visit(ctx.expr());
      if(res != null){
         System.out.println(res);
      }
      return res;
   }

   @Override public Double visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      Double res = null;
      Double e1 = visit(ctx.expr(0));
      Double e2 = visit(ctx.expr(1));
      if (e1 != null && e2 != null) {
         switch(ctx.op.getText()) {
            case "+":
               res = e1 + e2;
               break;
            case "-":
               res = e1 - e2;
               break;
         }
      }
      return res;
   }

   @Override public Double visitUnaryInteger(CalculatorParser.UnaryIntegerContext ctx) {
      Double res = null;
      Double e2 = visit(ctx.e2);
      switch (ctx.op.getText()) {
         case "-":
            res = -e2;
         case "+":
            break;
      }
      return res;
   }

   @Override public Double visitExprParent(CalculatorParser.ExprParentContext ctx) {
      return (Double)visit(ctx.expr());
   }

   @Override public Double visitExprNultDivMod(CalculatorParser.ExprNultDivModContext ctx) {
      Double res = null;
      Double e1 = visit(ctx.expr(0));
      Double e2 = visit(ctx.expr(1));
      if (e1 != null && e2 != null) {
         switch(ctx.op.getText()) {
            case "/":
               if (e1 == 0 | e2 == 0) {
                  System.err.println("ERROR: Divided by zero");
               }
               res = e1 / e2;
               break;
            case "*":
               res = e1 * e2;
               break;
         }
      }
      return res;
   }

   @Override public Double visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      return Double.parseDouble(ctx.Integer().getText());
   }
}
