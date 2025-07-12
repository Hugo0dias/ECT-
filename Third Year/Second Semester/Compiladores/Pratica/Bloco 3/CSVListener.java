// Generated from CSV.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CSVParser}.
 */
public interface CSVListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CSVParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(CSVParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSVParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(CSVParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSVParser#cabecalho}.
	 * @param ctx the parse tree
	 */
	void enterCabecalho(CSVParser.CabecalhoContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSVParser#cabecalho}.
	 * @param ctx the parse tree
	 */
	void exitCabecalho(CSVParser.CabecalhoContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSVParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(CSVParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSVParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(CSVParser.RowContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSVParser#cvalue}.
	 * @param ctx the parse tree
	 */
	void enterCvalue(CSVParser.CvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSVParser#cvalue}.
	 * @param ctx the parse tree
	 */
	void exitCvalue(CSVParser.CvalueContext ctx);
	/**
	 * Enter a parse tree produced by {@link CSVParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void enterRvalue(CSVParser.RvalueContext ctx);
	/**
	 * Exit a parse tree produced by {@link CSVParser#rvalue}.
	 * @param ctx the parse tree
	 */
	void exitRvalue(CSVParser.RvalueContext ctx);
}