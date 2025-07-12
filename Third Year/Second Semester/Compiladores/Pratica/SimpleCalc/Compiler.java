import org.stringtemplate.v4.*;

@SuppressWarnings("CheckReturnValue")
public class Compiler extends CalcBaseVisitor<ST> {

   @Override public ST visitProgram(CalcParser.ProgramContext ctx) {
      ST res = allTemplates.getInstanceOf("main");
      res.add("name", "Output");
      if (ctx.stat() != null) {
         for(CalcParser.StatContext stat: ctx.stat())
            res.add("stat", visit(stat));
      }
      return res;
   }

   @Override public ST visitStatExpression(CalcParser.StatExpressionContext ctx) {
      ST res = allTemplates.getInstanceOf("print");
      res.add("stat", visit(ctx.expression()));
      res.add("value", ctx.expression().varName);
      return res;
   }

   @Override public ST visitExprOp(CalcParser.ExprOpContext ctx) {
      ST res = allTemplates.getInstanceOf("binaryExpression");
      ctx.varName = newVarName();
      res.add("type", "double");
      res.add("var", ctx.varName);
      res.add("stat", visit(ctx.expression(0)));
      res.add("stat", visit(ctx.expression(1)));
      res.add("e1", ctx.expression(0).varName);
      res.add("op", ctx.op.getText());
      res.add("e2", ctx.expression(1).varName);
      return res;
   }

   @Override public ST visitExprNumber(CalcParser.ExprNumberContext ctx) {
      ST res = allTemplates.getInstanceOf("decl");
      ctx.varName = newVarName();
      res.add("type", "double");
      res.add("var", ctx.varName);
      res.add("value", ctx.Number().getText());
      return res;
   }

   @Override public ST visitExprPar(CalcParser.ExprParContext ctx) {
      ST res = visit(ctx.expression());
      ctx.varName = ctx.expression().varName;
      return res;
   }

   private String newVarName() {
      varCount++;
      return "v"+varCount;
   }

   private int varCount = 0;
   private STGroup allTemplates = new STGroupFile("java.stg");
}
