import static java.lang.System.*;

public class Interpreter extends CalcBaseVisitor<Value> {
	
	@Override public Value visitShow(CalcParser.ShowContext ctx) {
      Value res = visit(ctx.expr());
      out.println(res);
      return res;
   }
	
	@Override public Value visitAssignment(CalcParser.AssignmentContext ctx) {
      Value res = visit(ctx.expr());
      String id = ctx.ID().getText();
      CalcParser.symbolTable.get(id).setValue(res);
      return res;
   }
	
	@Override public Value visitConditional(CalcParser.ConditionalContext ctx) {
      Value res = visit(ctx.expr());
      if (res.boolValue())
         visit(ctx.trueSL);
      else if (ctx.falseSL != null)
         visit(ctx.falseSL);
      return res;
   }
	
	@Override public Value visitExprId(CalcParser.ExprIdContext ctx) {
      String id = ctx.ID().getText();
      return CalcParser.symbolTable.get(id).value();
   }

	@Override public Value visitExprUnary(CalcParser.ExprUnaryContext ctx) {
      Value res = null;
      Value r = visit(ctx.e);
      switch(ctx.sign.getText())
      {
         case "+":
            res = r;
            break;
         case "-":
            res = mulValues(r, new IntegerValue(-1));
            break;
      }
      return res;
   }
	
	@Override public Value visitExprBinary(CalcParser.ExprBinaryContext ctx) {
      Value res = null;
      Value r1 = visit(ctx.e1);
      Value r2 = visit(ctx.e2);
      switch(ctx.op.getText())
      {
         case "^":
            res = powValues(r1, r2);
            break;
         case "*":
            res = mulValues(r1, r2);
            break;
         case "/":
            res = divValues(r1, r2);
            break;
         case "//":
            res = quoValues(r1, r2);
            break;
         case "\\\\":
            res = remValues(r1, r2);
            break;
         case "+":
            res = addValues(r1, r2);
            break;
         case "-":
            res = subValues(r1, r2);
            break;
         case "=":
         case "/=":
            res = compValues(r1, r2, ctx.op.getText().equals("="));
            break;
      }
      return res;
   }
	
	@Override public Value visitExprReal(CalcParser.ExprRealContext ctx) {
      return new RealValue(Double.parseDouble(ctx.REAL().getText()));
   }
	
	@Override public Value visitExprInteger(CalcParser.ExprIntegerContext ctx) {
      return new IntegerValue(Integer.parseInt(ctx.INTEGER().getText()));
   }
	
	@Override public Value visitExprBoolean(CalcParser.ExprBooleanContext ctx) {
      return new BooleanValue(ctx.BOOLEAN().getText().equals("true"));
   }
	
	@Override public Value visitExprParenthesis(CalcParser.ExprParenthesisContext ctx) {
      return visit(ctx.e);
   }

   protected Value addValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      //out.println("v1: \""+v1.type()+"\"");
      //out.println("v2: \""+v2.type()+"\"");
      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.realValue() + v2.realValue());
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue(v1.realValue() + v2.intValue());
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.intValue() + v2.realValue());
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new IntegerValue(v1.intValue() + v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value subValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.realValue() - v2.realValue());
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue(v1.realValue() - v2.intValue());
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.intValue() - v2.realValue());
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new IntegerValue(v1.intValue() - v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value mulValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.realValue() * v2.realValue());
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue(v1.realValue() * v2.intValue());
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.intValue() * v2.realValue());
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new IntegerValue(v1.intValue() * v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value divValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.realValue() / v2.realValue());
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue(v1.realValue() / v2.intValue());
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(v1.intValue() / v2.realValue());
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue((double)v1.intValue() / v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value quoValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new IntegerValue(v1.intValue() / v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value remValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new IntegerValue(v1.intValue() % v2.intValue());
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value powValues(Value v1, Value v2)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(Math.pow(v1.realValue(), v2.realValue()));
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new RealValue(Math.pow(v1.realValue(), v2.intValue()));
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new RealValue(Math.pow(v1.intValue(), v2.realValue()));
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
      {
         if (v2.intValue() >= 0)
         {
            int r = 1;
            for(int i = 0; i < v2.intValue(); i++)
               r *= v1.intValue();
            res = new IntegerValue(r);
         }
         else
            res = new RealValue(Math.pow(v1.intValue(), v2.intValue()));
      }
      else
         assert false: "missing semantic error check!";

      return res;
   }

   protected Value compValues(Value v1, Value v2, boolean equal)
   {
      assert v1 != null;
      assert v2 != null;

      Value res = null;

      if ("real".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new BooleanValue(v1.realValue() == v2.realValue());
      else if ("real".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new BooleanValue(v1.realValue() == v2.intValue());
      else if ("integer".equals(""+v1.type()) && "real".equals(""+v2.type()))
         res = new BooleanValue(v1.intValue() == v2.realValue());
      else if ("integer".equals(""+v1.type()) && "integer".equals(""+v2.type()))
         res = new BooleanValue(v1.intValue() == v2.intValue());
      else if ("boolean".equals(""+v1.type()) && "boolean".equals(""+v2.type()))
         res = new BooleanValue(v1.boolValue() == v2.boolValue());
      else
         assert false: "missing semantic error check!";

      if (!equal)
         res.setBoolValue(!res.boolValue());

      return res;
   }
}
