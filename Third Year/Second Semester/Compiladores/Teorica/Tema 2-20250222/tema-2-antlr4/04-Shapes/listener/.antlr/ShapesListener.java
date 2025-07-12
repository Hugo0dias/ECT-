// Generated from /home/hugo/Desktop/Compiladores/Teorica/Tema 2-20250222/tema-2-antlr4/04-Shapes/listener/Shapes.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ShapesParser}.
 */
public interface ShapesListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ShapesParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(ShapesParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(ShapesParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShapesParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(ShapesParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(ShapesParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShapesParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(ShapesParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(ShapesParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShapesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(ShapesParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(ShapesParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprDistance}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprDistance(ShapesParser.ExprDistanceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprDistance}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprDistance(ShapesParser.ExprDistanceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprNumber}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprNumber(ShapesParser.ExprNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprNumber}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprNumber(ShapesParser.ExprNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExprIdentifier(ShapesParser.ExprIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link ShapesParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExprIdentifier(ShapesParser.ExprIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShapesParser#distance}.
	 * @param ctx the parse tree
	 */
	void enterDistance(ShapesParser.DistanceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#distance}.
	 * @param ctx the parse tree
	 */
	void exitDistance(ShapesParser.DistanceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ShapesParser#point}.
	 * @param ctx the parse tree
	 */
	void enterPoint(ShapesParser.PointContext ctx);
	/**
	 * Exit a parse tree produced by {@link ShapesParser#point}.
	 * @param ctx the parse tree
	 */
	void exitPoint(ShapesParser.PointContext ctx);
}