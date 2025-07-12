// Generated from /home/hugo/Desktop/Compiladores/Pratica/Calc/Calc.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalcParser}.
 */
public interface CalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalcParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(CalcParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(CalcParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#statList}.
	 * @param ctx the parse tree
	 */
	void enterStatList(CalcParser.StatListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#statList}.
	 * @param ctx the parse tree
	 */
	void exitStatList(CalcParser.StatListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStat(CalcParser.StatContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStat(CalcParser.StatContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#show}.
	 * @param ctx the parse tree
	 */
	void enterShow(CalcParser.ShowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#show}.
	 * @param ctx the parse tree
	 */
	void exitShow(CalcParser.ShowContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(CalcParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(CalcParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(CalcParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(CalcParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#conditional}.
	 * @param ctx the parse tree
	 */
	void enterConditional(CalcParser.ConditionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#conditional}.
	 * @param ctx the parse tree
	 */
	void exitConditional(CalcParser.ConditionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#idList}.
	 * @param ctx the parse tree
	 */
	void enterIdList(CalcParser.IdListContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#idList}.
	 * @param ctx the parse tree
	 */
	void exitIdList(CalcParser.IdListContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CalcParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CalcParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprBinary}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprBinary(CalcParser.ExprBinaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprBinary}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprBinary(CalcParser.ExprBinaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprUnary(CalcParser.ExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprUnary}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprUnary(CalcParser.ExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprBoolean}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprBoolean(CalcParser.ExprBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprBoolean}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprBoolean(CalcParser.ExprBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprReal}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprReal(CalcParser.ExprRealContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprReal}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprReal(CalcParser.ExprRealContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprParenthesis}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprParenthesis(CalcParser.ExprParenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprParenthesis}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprParenthesis(CalcParser.ExprParenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprInteger}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprInteger(CalcParser.ExprIntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprInteger}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprInteger(CalcParser.ExprIntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprId}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprId(CalcParser.ExprIdContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprId}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprId(CalcParser.ExprIdContext ctx);
}