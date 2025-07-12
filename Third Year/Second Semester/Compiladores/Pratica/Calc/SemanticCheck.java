import static java.lang.System.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import error.*;

public class SemanticCheck extends CalcBaseVisitor<Boolean> {
	@Override public Boolean visitExprReal(CalcParser.ExprRealContext ctx)
   {
      ctx.eType = realType;
      return true;
   }
	
	@Override public Boolean visitExprBoolean(CalcParser.ExprBooleanContext ctx)
   {
      ctx.eType = booleanType;
      return true;
   }
	
	@Override public Boolean visitExprInteger(CalcParser.ExprIntegerContext ctx)
   {
      ctx.eType = integerType;
      return true;
   }
	
	@Override public Boolean visitExprParenthesis(CalcParser.ExprParenthesisContext ctx)
   {
      Boolean res = visit(ctx.e);
      if (res)
         ctx.eType = ctx.e.eType;
      return res;
   }
	
	@Override public Boolean visitExprUnary(CalcParser.ExprUnaryContext ctx)
   {
      Boolean res = visit(ctx.e) && checkNumericType(ctx, ctx.e.eType);
      if (res)
         ctx.eType = ctx.e.eType;
      return res;
   }
	
	@Override public Boolean visitDeclaration(CalcParser.DeclarationContext ctx) {
      Boolean res = true;
      //visit(ctx.type());
      for(TerminalNode t: ctx.idList().ID())
      {
         String id = t.getText();
         //out.println(t.getText());

         if (CalcParser.symbolTable.containsKey(id))
         {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" already declared!");
            res = false;
         }
         else
            CalcParser.symbolTable.put(id, new VariableSymbol(id, ctx.type().res));
      }
      return res;
   }
	
	@Override public Boolean visitAssignment(CalcParser.AssignmentContext ctx) {
      Boolean res = visit(ctx.expr());
      String id = ctx.ID().getText();
      if (res)
      {
         if (!CalcParser.symbolTable.containsKey(id))
         {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" does not exists!");
            res = false;
         }
         else
         {
            Symbol sym = CalcParser.symbolTable.get(id);
            if (!ctx.expr().eType.conformsTo(sym.type()))
            {
               ErrorHandling.printError(ctx, "Expression type does not conform to variable \""+id+"\" type!");
               res = false;
            }
            else
               sym.setValueDefined();
         }
      }
      
      return res;
   }
	
	@Override public Boolean visitConditional(CalcParser.ConditionalContext ctx) {
      Boolean res = visit(ctx.expr());
      visit(ctx.trueSL); // ignores result on purpose (to avoid override of all visit*)!
      if (ctx.falseSL != null)
         visit(ctx.falseSL); // ignores result on purpose (to avoid override of all visit*)!
      if (res)
      {
         if (!"boolean".equals(ctx.expr().eType.name()))
         {
            ErrorHandling.printError(ctx, "Boolean expression required in conditional instruction!");
            res = false;
         }
      }
      return res;
   }
	
	@Override public Boolean visitExprBinary(CalcParser.ExprBinaryContext ctx) {
      Boolean res = visit(ctx.e1) && checkNumericType(ctx, ctx.e1.eType) &&
                    visit(ctx.e2) && checkNumericType(ctx, ctx.e2.eType);
      if (res)
      {
         ctx.eType = fetchType(ctx.e1.eType, ctx.e2.eType);
         if (integerOperator(ctx.op.getText()) && !"integer".equals(ctx.eType.name()))
         {
            ErrorHandling.printError(ctx, "The integer operator "+ctx.op.getText()+" requires integer operands!");
            res = false;
         }
         else if (comparisonOperator(ctx.op.getText())) {
            if (fetchType(ctx.e1.eType, ctx.e2.eType) == null)
            {
               ErrorHandling.printError(ctx, "Comparison operator applied to invalid operands!");
               res = false;
            }
            else
               ctx.eType = booleanType;
         }
      }
      return res;
   }
	
	@Override public Boolean visitExprId(CalcParser.ExprIdContext ctx) {
      Boolean res = true;
      String id = ctx.ID().getText();
      if (!CalcParser.symbolTable.containsKey(id))
      {
         ErrorHandling.printError(ctx, "Variable \""+id+"\" does not exists!");
         res = false;
      }
      else
      {
         Symbol sym = CalcParser.symbolTable.get(id);
         if (!sym.valueDefined())
         {
            ErrorHandling.printError(ctx, "Variable \""+id+"\" not defined!");
            res = false;
         }
         else
            ctx.eType = sym.type();
      }
      return res;
   }

	private Boolean checkNumericType(ParserRuleContext ctx, Type t)
   {
      Boolean res = true;
      if (!t.isNumeric())
      {
         ErrorHandling.printError(ctx, "Numeric operator applied to a non-numeric operand!");
         res = false;
      }
      return res;
   }

   private Type fetchType(Type t1, Type t2)
   {
      Type res = null;
      if (t1.isNumeric() && t2.isNumeric())
      {
         if ("real".equals(t1.name()))
            res = t1;
         else if ("real".equals(t2.name()))
            res = t2;
         else
            res = t1;
      }
      else if ("boolean".equals(t1.name()) && "boolean".equals(t2.name()))
         res = t1;
      return res;
   }

   private boolean integerOperator(String op)
   {
      return "//".equals(op) || "\\\\".equals(op);
   }
	
   private boolean comparisonOperator(String op)
   {
      return "=".equals(op) || "/=".equals(op);
   }
	
   private final RealType realType = new RealType();
   private final IntegerType integerType = new IntegerType();
   private final BooleanType booleanType = new BooleanType();
}
