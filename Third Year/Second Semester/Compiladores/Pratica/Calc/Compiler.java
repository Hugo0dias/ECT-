import java.io.File;
import org.stringtemplate.v4.*;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.ParserRuleContext;

public class Compiler extends CalcBaseVisitor<ST> {

   public boolean validTarget(String target) {
      File f = new File(target+".stg");

      return ("java".equalsIgnoreCase(target) || "c".equalsIgnoreCase(target)) &&
             f.exists() && f.isFile() && f.canRead();
   }

   public void setTarget(String target) {
      assert validTarget(target);

      this.target = target;
   }

   @Override public ST visitMain(CalcParser.MainContext ctx) {
      assert validTarget(target);

      stg = new STGroupFile(target+".stg");
      ST res = stg.getInstanceOf("module");
      res.add("stat", visit(ctx.statList()));
      return res;
   }

   @Override public ST visitStatList(CalcParser.StatListContext ctx) {
      ST res = stg.getInstanceOf("stats");
      for(CalcParser.StatContext sc: ctx.stat())
         res.add("stat", visit(sc));
      return res;
   }

   @Override public ST visitShow(CalcParser.ShowContext ctx) {
      ST res = stg.getInstanceOf("show");
      res.add("stat", visit(ctx.expr()));
      res.add("type", ctx.expr().eType.name());
      res.add("expr",ctx.expr().varName);
      return res;
   }

   @Override public ST visitDeclaration(CalcParser.DeclarationContext ctx) {
      ST res = stg.getInstanceOf("stats");
      for(TerminalNode t: ctx.idList().ID())
      {
         String id = t.getText();
         Symbol s = CalcParser.symbolTable.get(id);
         s.setVarName(newVarName());
         ST decl = stg.getInstanceOf("decl");
         decl.add("type", s.type().name());
         decl.add("var",s.varName());
         res.add("stat", decl.render());
      }
      return res;
   }

   @Override public ST visitAssignment(CalcParser.AssignmentContext ctx) {
      ST res = stg.getInstanceOf("assign");
      String id = ctx.ID().getText();
      Symbol s = CalcParser.symbolTable.get(id);
      res.add("stat", visit(ctx.expr()).render());
      res.add("var", s.varName());
      res.add("value", ctx.expr().varName);
      return res;
   }

   @Override public ST visitConditional(CalcParser.ConditionalContext ctx) {
      ST res = stg.getInstanceOf("conditional");
      res.add("stat", visit(ctx.expr()).render());
      res.add("var", ctx.expr().varName);
      res.add("stat_true", visit(ctx.trueSL).render());
      if (ctx.falseSL != null)
         res.add("stat_false", visit(ctx.falseSL).render());
      return res;
   }

   @Override public ST visitExprInteger(CalcParser.ExprIntegerContext ctx) {
      ST res = stg.getInstanceOf("decl");
      ctx.varName = newVarName();
      res.add("type", "integer");
      res.add("var", ctx.varName);
      res.add("value", ctx.INTEGER().getText());
      return res;
   }

   @Override public ST visitExprReal(CalcParser.ExprRealContext ctx) {
      ST res = stg.getInstanceOf("decl");
      ctx.varName = newVarName();
      res.add("type", "real");
      res.add("var", ctx.varName);
      res.add("value", ctx.REAL().getText());
      return res;
   }

   @Override public ST visitExprBoolean(CalcParser.ExprBooleanContext ctx) {
      ST res = stg.getInstanceOf("decl");
      ctx.varName = newVarName();
      res.add("type", "boolean");
      res.add("var", ctx.varName);
      ST lb = stg.getInstanceOf("literalBoolean");
      lb.add("value", ctx.BOOLEAN().getText());
      res.add("value", lb.render());
      return res;
   }

   @Override public ST visitExprId(CalcParser.ExprIdContext ctx) {
      ST res = stg.getInstanceOf("stats");
      ST decl = stg.getInstanceOf("decl");
      String id = ctx.ID().getText();
      ctx.varName = newVarName();
      decl.add("type", ctx.eType.name());
      decl.add("var", ctx.varName);
      decl.add("value", CalcParser.symbolTable.get(id).varName());
      res.add("stat", decl.render());
      return res;
   }

   @Override public ST visitExprUnary(CalcParser.ExprUnaryContext ctx) {
      ST res = stg.getInstanceOf("stats");
      res.add("stat", visit(ctx.expr()).render());
      ST decl = stg.getInstanceOf("decl");
      ctx.varName = newVarName();
      decl.add("type", ctx.eType.name());
      decl.add("var", ctx.varName);
      decl.add("value", ctx.sign.getText()+ctx.expr().varName);
      res.add("stat", decl.render());
      return res;
   }

   @Override public ST visitExprBinary(CalcParser.ExprBinaryContext ctx) {
      ST res;
      ctx.varName = newVarName();
      if ("^".equals(ctx.op.getText())) {
         res = stg.getInstanceOf("stats");
         res.add("stat", visit(ctx.e1).render());
         res.add("stat", visit(ctx.e2).render());
         ST pe = stg.getInstanceOf("powerExpression");
         pe.add("type", ctx.eType.name());
         pe.add("var", ctx.varName);
         pe.add("e1", ctx.e1.varName);
         pe.add("e2", ctx.e2.varName);
         res.add("stat", pe.render());
      }
      else {
         res = stg.getInstanceOf("binaryExpression");
         res.add("stat", visit(ctx.e1).render());
         res.add("stat", visit(ctx.e2).render());
         res.add("type", ctx.eType.name());
         res.add("var", ctx.varName);
         res.add("e1", ctx.e1.varName);
         res.add("op", translateOp(ctx.op.getText()));
         res.add("e2", ctx.e2.varName);
      }
      return res;
   }

   @Override public ST visitExprParenthesis(CalcParser.ExprParenthesisContext ctx) {
      ST res = visit(ctx.expr());
      ctx.varName = ctx.expr().varName;
      return res;
   }

   protected String newVarName() {
      varCount++;
      return "v"+varCount;
   }

   protected String translateOp(String op) {
      String res = op;
      switch(op) {
         case "//":
            res = "/";
            break;
         case "\\\\":
            res = "%";
            break;
         case "=":
            res = "==";
            break;
         case "/=":
            res = "!=";
            break;
      }
      return res;
   }

   protected int varCount = 0;
   protected String target = "java"; // default
   protected STGroup stg = null;
}
