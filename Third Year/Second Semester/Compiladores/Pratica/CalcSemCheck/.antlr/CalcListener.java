// Generated from /home/hugo/Desktop/Compiladores/Pratica/CalcSemCheck/Calc.g4 by ANTLR 4.13.1
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
	 * Enter a parse tree produced by the {@code IntegerType}
	 * labeled alternative in {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void enterIntegerType(CalcParser.IntegerTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntegerType}
	 * labeled alternative in {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void exitIntegerType(CalcParser.IntegerTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void enterRealType(CalcParser.RealTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RealType}
	 * labeled alternative in {@link CalcParser#type}.
	 * @param ctx the parse tree
	 */
	void exitRealType(CalcParser.RealTypeContext ctx);
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
	 * Enter a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprParen(CalcParser.ExprParenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprParen}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprParen(CalcParser.ExprParenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprInt}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprInt(CalcParser.ExprIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprInt}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprInt(CalcParser.ExprIntContext ctx);
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
	 * Enter a parse tree produced by the {@code ExprID}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprID(CalcParser.ExprIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprID}
	 * labeled alternative in {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprID(CalcParser.ExprIDContext ctx);
}