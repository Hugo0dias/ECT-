// Generated from /home/hugo/Desktop/Compiladores/Pratica/SimpleCalc/Calc.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalcParser}.
 */
public interface CalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalcParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(CalcParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(CalcParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StatExpression}
	 * labeled alternative in {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void enterStatExpression(CalcParser.StatExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StatExpression}
	 * labeled alternative in {@link CalcParser#stat}.
	 * @param ctx the parse tree
	 */
	void exitStatExpression(CalcParser.StatExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprOp}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprOp(CalcParser.ExprOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprOp}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprOp(CalcParser.ExprOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprNumber}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprNumber(CalcParser.ExprNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprNumber}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprNumber(CalcParser.ExprNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprPar}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprPar(CalcParser.ExprParContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprPar}
	 * labeled alternative in {@link CalcParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprPar(CalcParser.ExprParContext ctx);
}