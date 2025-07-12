import java.util.Map;

@SuppressWarnings("CheckReturnValue")
public class Execute extends CalcBaseVisitor<Double> {

   public Execute(Map<String,Symbol<Double>> symbolTable) {
      this.symbolTable = symbolTable;
   }

   @Override public Double visitShow(CalcParser.ShowContext ctx) {
      Double res = visit(ctx.expr());
      //if (ctx.expr().t.name().equals("integer"))
         //System.out.println((Integer)res);
      //else
         System.out.println(res);
      return res;
   }

   @Override public Double visitAssignment(CalcParser.AssignmentContext ctx) {
      Double res = visit(ctx.expr());
      String v = ctx.ID().getText();
      symbolTable.get(v).setValue(res);
      return res;
   }

   @Override public Double visitDeclaration(CalcParser.DeclarationContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitIdList(CalcParser.IdListContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitIntegerType(CalcParser.IntegerTypeContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitRealType(CalcParser.RealTypeContext ctx) {
      Double res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Double visitExprBinary(CalcParser.ExprBinaryContext ctx) {
      double res = 0;
      double e1 = visit(ctx.e1);
      double e2 = visit(ctx.e2);
      switch(ctx.op.getText())
      {
         case "*":
            res = e1*e2;
            break;
         case "/":
            res = e1/e2;
            break;
         case "//":
            res = (double)((int)e1/(int)e2);
            break;
         case "\\\\":
            res = (double)((int)e1%(int)e2);
            break;
         case "+":
            res = e1+e2;
            break;
         case "-":
            res = e1-e2;
            break;
      }
      return res;
   }

   @Override public Double visitExprInt(CalcParser.ExprIntContext ctx) {
      double sign = ctx.sign != null && ctx.sign.getText().equals("-") ? -1.0 : 1.0;
      return sign*(double)Integer.parseInt(ctx.INT().getText());
   }

   @Override public Double visitExprReal(CalcParser.ExprRealContext ctx) {
      double sign = ctx.sign != null && ctx.sign.getText().equals("-") ? -1.0 : 1.0;
      return sign*Double.parseDouble(ctx.REAL().getText());
   }

   @Override public Double visitExprID(CalcParser.ExprIDContext ctx) {
      String v = ctx.ID().getText();
      return (Double)symbolTable.get(v).value();
   }

   @Override public Double visitExprParen(CalcParser.ExprParenContext ctx) {
      return visit(ctx.expr());
   }

   private final Map<String,Symbol<Double>> symbolTable;
}
