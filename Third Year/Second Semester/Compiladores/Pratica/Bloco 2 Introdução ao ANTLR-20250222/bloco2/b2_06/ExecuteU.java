import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("CheckReturnValue")
public class ExecuteU extends CalculatorBaseVisitor<Double> {

   
   @Override public Double visitStat(CalculatorParser.StatContext ctx) {
      Double res = (Double)visit(ctx.expr());   // Atribuicao do tipo Double ao Object
      if (res != null){
         System.out.println("Res : "+ res);
      }   
      return res;
   }

   @Override public Double visitAssignment(CalculatorParser.AssignmentContext ctx) {
      String id = ctx.Var().getText();
      Double value = visit(ctx.expr());
      ArrayAssociativo.put(id, value);
      if (id != null && value != null) {
         System.out.println(id + " = " + value);
      }
		return visitChildren(ctx);
   }

   @Override public Double visitExprAddSub(CalculatorParser.ExprAddSubContext ctx) {
      Double res = null;
      Double e1 = visit(ctx.expr(0));
      Double e2 = visit(ctx.expr(1));

      if (e1 != null | e2 != null) {
         switch (ctx.op.getText()) {
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
      Double res = visit(ctx.expr());
      System.out.println(res);
      if (res != null) {
         switch (ctx.op.getText()) {
            case "+":
                break;
            case "-":
                res = -res;
                break;
         }
         
      }
      return res;
   }

   @Override public Double visitExprParent(CalculatorParser.ExprParentContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitExprNultDivMod(CalculatorParser.ExprNultDivModContext ctx) {
      Double res = null;
      Double e1 = visit(ctx.expr(0));
      Double e2 = visit(ctx.expr(1));
      if (e1 != null && e2 != null) {
         switch(ctx.op.getText()) {
            case "*":
               res = e1 * e2;
               break;
            case "/":
               if (e2 != 0) {
                  res = e1 / e2;
               } else { System.err.println("ERRO : E2 == 0"); }
               break;
         }
      }
      return res;
   }

   @Override public Double visitVarExpr(CalculatorParser.VarExprContext ctx) {
      String ID = ctx.Var().getText();
      if (ArrayAssociativo.containsKey(ID)) {
         return ArrayAssociativo.get(ID);
      } 
      System.out.println("ERRO: variavel " + ID + " não encontrada!");
      return null;
   }

   @Override public Double visitExprInteger(CalculatorParser.ExprIntegerContext ctx) {
      Double res = Double.parseDouble(ctx.getText());
      return res;
   }

   private final Map<String, Double> ArrayAssociativo = new HashMap<>();
}
