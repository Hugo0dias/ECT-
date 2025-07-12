import java.util.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings("CheckReturnValue")
public class IIMLSemanticCheck extends GrammarIIMLBaseVisitor<Boolean> {

   // Type instances
   private final NumberType numberType = new NumberType();

   // Figure type instances for validation
   private static final Map<String, Figure> VALID_FIGURES = Map.of(
      "circle", new CircleFigure(),
      "rect", new RectangleFigure(),
      "cross", new CrossFigure(),
      "plus", new PlusFigure(),
      "line", new LineFigure()
   );

   // Symbol table to track declared variables and their types
   private final Map<String, Type> symbolTable = new HashMap<>();
   
   // Stack for nested scopes
   private final Stack<Map<String, Type>> scopeStack = new Stack<>();

   // Map to store types of expressions
   private final Map<ParseTree, Type> exprTypes = new HashMap<>();

   // Global image dimensions for current image being created
   private Integer globalImageWidth = null;
   private Integer globalImageHeight = null;

   // Reserved keywords for warning detection
   private static final Set<String> RESERVED_KEYWORDS = Set.of(
      "image", "size", "by", "background", "place", "circle", "rect", "cross", "plus",
      "radius", "width", "height", "at", "with", "intensity", "number", "is", "read",
      "list", "of", "for", "within"
   );

   // Additional quality of life fields
   private int variableCount = 0;
   private int listVariableCount = 0;
   private boolean hasImageDeclaration = false;
   private boolean hasFigurePlacement = false;
   private Set<String> usedVariables = new HashSet<>();
   private Set<String> unusedVariables = new HashSet<>();

   public IIMLSemanticCheck() {
      // Initialize global scope
      scopeStack.push(symbolTable);
   }

   // Helper method to set type for a parse tree node
   private void setType(ParseTree node, Type type) {
      exprTypes.put(node, type);
   }

   // Helper method to get type for a parse tree node
   private Type getType(ParseTree node) {
      return exprTypes.get(node);
   }

   // Helper method to track variable usage
   private void trackVariableUsage(String varName) {
      usedVariables.add(varName);
   }

   // Helper method to detect unused variables
   private void checkUnusedVariables() {
      unusedVariables.clear();
      for (String varName : symbolTable.keySet()) {
         if (!usedVariables.contains(varName)) {
            unusedVariables.add(varName);
         }
      }
   }

   // Enhanced variable name checking with more detailed warnings
   private void checkConfusingVariableName(ParserRuleContext ctx, String varName) {
      String lowerVarName = varName.toLowerCase();
      
      // Check for reserved keyword conflicts
      if (RESERVED_KEYWORDS.contains(lowerVarName) && !lowerVarName.equals(varName)) {
         ErrorHandling.printWarning(ctx, "Variable name '" + varName + "' is confusingly similar to reserved keyword '" + lowerVarName + "'");
      }
      
      // Check for single character variable names (poor practice)
      if (varName.length() == 1) {
         ErrorHandling.printWarning(ctx, "Single character variable name '" + varName + "' may be unclear - consider using descriptive names");
      }
      
      // Check for all uppercase names (unless it's a constant pattern)
      if (varName.equals(varName.toUpperCase()) && varName.length() > 1) {
         ErrorHandling.printWarning(ctx, "All-uppercase variable name '" + varName + "' - consider using camelCase convention");
      }
      
      // Check for numbers at the start (while legal, can be confusing)
      if (varName.matches("^[0-9].*")) {
         ErrorHandling.printWarning(ctx, "Variable name '" + varName + "' starts with a number - consider using letters first");
      }
   }

   @Override 
   public Boolean visitProgram(GrammarIIMLParser.ProgramContext ctx) {
      Boolean allValid = true;
      for (int i = 0; i < ctx.getChildCount(); i++) {
         if (ctx.getChild(i) instanceof GrammarIIMLParser.StatementContext) {
            Boolean valid = visit(ctx.getChild(i));
            if (valid == null || !valid) {
               allValid = false;
            }
         }
      }
      return allValid;
   }

   @Override 
   public Boolean visitStatement(GrammarIIMLParser.StatementContext ctx) {
      return visitChildren(ctx);
   }

   @Override 
   public Boolean visitNumberDeclaration(GrammarIIMLParser.NumberDeclarationContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check for confusing variable name
      checkConfusingVariableName(ctx, varName);
      
      // Check if variable already declared in current scope
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit the initialization expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Get expression type
      Type exprType = getType(ctx.expr());
      if (exprType == null) {
         ErrorHandling.printError(ctx, "Could not determine type of initialization expression");
         return false;
      }
      
      // Check type compatibility - number declaration should receive number type
      if (!exprType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Type mismatch: cannot assign " + exprType + " to number");
         return false;
      }
      
      // Add variable to symbol table
      symbolTable.put(varName, numberType);
      variableCount++;
      
      return true;
   }

   @Override 
   public Boolean visitListDeclaration(GrammarIIMLParser.ListDeclarationContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check for confusing variable name
      checkConfusingVariableName(ctx, varName);
      
      // Check if variable already declared in current scope
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit the initialization expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Get expression type
      Type exprType = getType(ctx.expr());
      if (exprType == null) {
         ErrorHandling.printError(ctx, "Could not determine type of initialization expression");
         return false;
      }
      
      // Check if it's a list type
      if (!(exprType instanceof ListType)) {
         ErrorHandling.printError(ctx, "List variable must be initialized with a list");
         return false;
      }
      
      // Add variable to symbol table
      symbolTable.put(varName, exprType);
      variableCount++;
      listVariableCount++;
      
      return true;
   }

   @Override 
   public Boolean visitAssignment(GrammarIIMLParser.AssignmentContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is already declared
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      // Visit the assignment expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Get expression type
      Type exprType = getType(ctx.expr());
      if (exprType == null) {
         ErrorHandling.printError(ctx, "Could not determine type of assignment expression");
         return false;
      }
      
      // Get the variable's declared type
      Type varType = symbolTable.get(varName);
      
      // Check type compatibility
      if (!exprType.equals(varType)) {
         ErrorHandling.printError(ctx, "Type mismatch: cannot assign " + exprType + " to " + varType + " variable '" + varName + "'");
         return false;
      }
      
      // Assignment is valid - variable keeps its original type
      return true;
   }

   @Override 
   public Boolean visitBaseType(GrammarIIMLParser.BaseTypeContext ctx) {
      // Base types are always valid for IIML (currently only number)
      return true;
   }

   @Override 
   public Boolean visitForLoop(GrammarIIMLParser.ForLoopContext ctx) {
      String iterVar = ctx.ID(0).getText(); // Loop variable
      String listVar = ctx.ID(1).getText(); // List to iterate over
      
      // Check for confusing variable names
      checkConfusingVariableName(ctx, iterVar);
      
      // Check if list variable is declared
      if (!symbolTable.containsKey(listVar)) {
         ErrorHandling.printError(ctx, "Variable '" + listVar + "' not declared");
         return false;
      }
      
      Type listType = symbolTable.get(listVar);
      if (!(listType instanceof ListType)) {
         ErrorHandling.printError(ctx, "For loop can only iterate over lists");
         return false;
      }
      
      // Create new scope for loop
      Map<String, Type> loopScope = new HashMap<>(symbolTable);
      
      // Add iterator variable to loop scope 
      // For IIML, when iterating over a list of lists, the iterator should be a list
      loopScope.put(iterVar, new ListType("[number, number]")); // Assuming 2D coordinates
      
      // Push new scope and update current symbol table
      scopeStack.push(symbolTable);
      symbolTable.clear();
      symbolTable.putAll(loopScope);
      
      // Visit loop body
      Boolean bodyValid = visit(ctx.statement());
      
      // Restore previous scope
      symbolTable.clear();
      symbolTable.putAll(scopeStack.pop());
      
      return bodyValid;
   }

   @Override 
   public Boolean visitImageDeclaration(GrammarIIMLParser.ImageDeclarationContext ctx) {
      hasImageDeclaration = true;
      
      // Check if multiple image declarations exist
      if (hasImageDeclaration && variableCount > 0) {
         // This is not the first image declaration in the context
         ErrorHandling.printWarning(ctx, "Multiple image declarations may overwrite previous image settings");
      }
      
      // Visit image size specification
      Boolean sizeValid = visit(ctx.imageSize());
      if (!sizeValid) {
         return false;
      }
      
      // Visit background expression
      Boolean bgValid = visit(ctx.expr());
      if (!bgValid) {
         return false;
      }
      
      // Check that background is a number (intensity value)
      Type bgType = getType(ctx.expr());
      if (bgType != null && !bgType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Image background must be a numeric intensity value");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitImageSize(GrammarIIMLParser.ImageSizeContext ctx) {
      if (ctx.getChildCount() == 3) { // expr BY expr
         Boolean widthValid = visit(ctx.expr(0));
         Boolean heightValid = visit(ctx.expr(1));
         
         if (!widthValid || !heightValid) {
            return false;
         }
         
         // Check that both dimensions are numbers
         Type widthType = getType(ctx.expr(0));
         Type heightType = getType(ctx.expr(1));
         
         if (widthType != null && !widthType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Image width must be numeric");
            return false;
         }
         
         if (heightType != null && !heightType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Image height must be numeric");
            return false;
         }
         
         // Store dimensions for figure placement validation
         try {
            if (ctx.expr(0).getText().matches("\\d+")) {
               globalImageWidth = Integer.parseInt(ctx.expr(0).getText());
            }
            if (ctx.expr(1).getText().matches("\\d+")) {
               globalImageHeight = Integer.parseInt(ctx.expr(1).getText());
            }
         } catch (NumberFormatException e) {
            // Dimensions are variables, can't validate statically
         }
         
      } else { // Single expr (square image)
         Boolean sizeValid = visit(ctx.expr(0));
         if (!sizeValid) {
            return false;
         }
         
         Type sizeType = getType(ctx.expr(0));
         if (sizeType != null && !sizeType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Image size must be numeric");
            return false;
         }
         
         // Store dimensions for figure placement validation
         try {
            if (ctx.expr(0).getText().matches("\\d+")) {
               int size = Integer.parseInt(ctx.expr(0).getText());
               globalImageWidth = size;
               globalImageHeight = size;
            }
         } catch (NumberFormatException e) {
            // Size is a variable, can't validate statically
         }
      }
      
      return true;
   }

   @Override 
   public Boolean visitPlaceFigure(GrammarIIMLParser.PlaceFigureContext ctx) {
      hasFigurePlacement = true;
      
      // Check if placing figure without image declaration
      if (!hasImageDeclaration) {
         ErrorHandling.printError(ctx, "Cannot place figure without first declaring an image");
         return false;
      }
      
      // Visit figure type
      Boolean typeValid = visit(ctx.figureType());
      if (!typeValid) {
         return false;
      }
      
      // Visit figure size
      Boolean sizeValid = visit(ctx.figureSize());
      if (!sizeValid) {
         return false;
      }
      
      // Visit location
      Boolean locationValid = visit(ctx.location());
      if (!locationValid) {
         return false;
      }
      
      // Visit intensity expression
      Boolean intensityValid = visit(ctx.expr());
      if (!intensityValid) {
         return false;
      }
      
      // Check that intensity is numeric
      Type intensityType = getType(ctx.expr());
      if (intensityType != null && !intensityType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Figure intensity must be numeric");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitNumberPrimary(GrammarIIMLParser.NumberPrimaryContext ctx) {
      String numberText = ctx.NUMBER().getText();
      
      // Use NumberType validation
      if (!numberType.isNumber(numberText)) {
         ErrorHandling.printError(ctx, "Invalid number format: " + numberText);
         return false;
      }
      
      setType(ctx, numberType);
      return true;
   }

   @Override 
   public Boolean visitListLiteral(GrammarIIMLParser.ListLiteralContext ctx) {
      if (ctx.expr().isEmpty()) {
         // Empty list
         setType(ctx, new ListType("[]"));
         return true;
      }
      
      // Visit all expressions in the list
      Boolean allValid = true;
      for (GrammarIIMLParser.ExprContext expr : ctx.expr()) {
         Boolean exprValid = visit(expr);
         if (!exprValid) {
            allValid = false;
         }
      }
      
      if (!allValid) {
         return false;
      }
      
      // Create list type based on the actual elements
      StringBuilder listString = new StringBuilder("[");
      for (int i = 0; i < ctx.expr().size(); i++) {
         if (i > 0) listString.append(", ");
         listString.append(ctx.expr(i).getText());
      }
      listString.append("]");
      
      ListType listType = new ListType(listString.toString());
      
      // Use ListType's built-in validation
      if (!listType.hasUniformType()) {
         ErrorHandling.printError(ctx, "List elements must have uniform type");
         return false;
      }
      
      setType(ctx, listType);
      return true;
   }

   @Override 
   public Boolean visitFigureType(GrammarIIMLParser.FigureTypeContext ctx) {
      String figureTypeName = ctx.getText().toLowerCase();
      
      // Validate figure type using Figure classes
      if (VALID_FIGURES.containsKey(figureTypeName)) {
         Figure figure = VALID_FIGURES.get(figureTypeName);
         // Store figure type for potential later use
         return true;
      } else {
         ErrorHandling.printError(ctx, "Unknown figure type: " + figureTypeName + 
            ". Valid types are: " + String.join(", ", VALID_FIGURES.keySet()));
         return false;
      }
   }

   @Override 
   public Boolean visitCircleFigSize(GrammarIIMLParser.CircleFigSizeContext ctx) {
      // Visit radius expression
      Boolean radiusValid = visit(ctx.expr());
      if (!radiusValid) {
         return false;
      }
      
      // Check that radius is numeric and positive
      Type radiusType = getType(ctx.expr());
      if (radiusType != null && !radiusType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Circle radius must be numeric");
         return false;
      }
      
      // Additional validation for positive values if it's a constant
      String radiusText = ctx.expr().getText();
      if (numberType.isNumber(radiusText)) {
         try {
            double radiusValue = Double.parseDouble(radiusText);
            if (radiusValue <= 0) {
               ErrorHandling.printWarning(ctx, "Circle radius should be positive, got: " + radiusValue);
            }
         } catch (NumberFormatException e) {
            // Not a constant, skip static validation
         }
      }
      
      return true;
   }

   @Override 
   public Boolean visitOtherFigSize(GrammarIIMLParser.OtherFigSizeContext ctx) {
      // Visit width expression
      Boolean widthValid = visit(ctx.expr(0));
      if (!widthValid) {
         return false;
      }
      
      // Visit height expression
      Boolean heightValid = visit(ctx.expr(1));
      if (!heightValid) {
         return false;
      }
      
      // Check that both dimensions are numeric and positive
      Type widthType = getType(ctx.expr(0));
      Type heightType = getType(ctx.expr(1));
      
      if (widthType != null && !widthType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Figure width must be numeric");
         return false;
      }
      
      if (heightType != null && !heightType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Figure height must be numeric");
         return false;
      }
      
      // Additional validation for positive values if they're constants
      String widthText = ctx.expr(0).getText();
      String heightText = ctx.expr(1).getText();
      
      if (numberType.isNumber(widthText)) {
         try {
            double widthValue = Double.parseDouble(widthText);
            if (widthValue <= 0) {
               ErrorHandling.printWarning(ctx, "Figure width should be positive, got: " + widthValue);
            }
         } catch (NumberFormatException e) {
            // Not a constant, skip static validation
         }
      }
      
      if (numberType.isNumber(heightText)) {
         try {
            double heightValue = Double.parseDouble(heightText);
            if (heightValue <= 0) {
               ErrorHandling.printWarning(ctx, "Figure height should be positive, got: " + heightValue);
            }
         } catch (NumberFormatException e) {
            // Not a constant, skip static validation
         }
      }
      
      return true;
   }

   @Override 
   public Boolean visitLocation(GrammarIIMLParser.LocationContext ctx) {
      // Visit x coordinate
      Boolean xValid = visit(ctx.expr(0));
      if (!xValid) {
         return false;
      }
      
      // Visit y coordinate
      Boolean yValid = visit(ctx.expr(1));
      if (!yValid) {
         return false;
      }
      
      // Check that both coordinates are numeric
      Type xType = getType(ctx.expr(0));
      Type yType = getType(ctx.expr(1));
      
      if (xType != null && !xType.equals(numberType)) {
         ErrorHandling.printError(ctx, "X coordinate must be numeric");
         return false;
      }
      
      if (yType != null && !yType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Y coordinate must be numeric");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitMultiplicationExpr(GrammarIIMLParser.MultiplicationExprContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = getType(ctx.expr(0));
      Type rightType = getType(ctx.expr(1));
      String op = ctx.op.getText();
      
      // Both operands should be numbers for arithmetic operations
      if (leftType != null && rightType != null) {
         if (!leftType.equals(numberType) || !rightType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Arithmetic operations require numeric operands");
            return false;
         }
         
         // Check for division by zero
         if (op.equals("/")) {
            String rightText = ctx.expr(1).getText();
            try {
               double rightValue = Double.parseDouble(rightText);
               if (rightValue == 0.0) {
                  ErrorHandling.printError(ctx, "Division by zero");
                  return false;
               }
            } catch (NumberFormatException e) {
               // Not a constant number, can't check statically
            }
         }
      }
      
      setType(ctx, numberType);
      return true;
   }

   @Override 
   public Boolean visitAdditionExpr(GrammarIIMLParser.AdditionExprContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = getType(ctx.expr(0));
      Type rightType = getType(ctx.expr(1));
      
      // Both operands should be numbers for arithmetic operations
      if (leftType != null && rightType != null) {
         if (!leftType.equals(numberType) || !rightType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Arithmetic operations require numeric operands");
            return false;
         }
      }
      
      setType(ctx, numberType);
      return true;
   }

   @Override 
   public Boolean visitUnaryExpr(GrammarIIMLParser.UnaryExprContext ctx) {
      Boolean exprValid = visit(ctx.expr());
      
      if (!exprValid) {
         return false;
      }
      
      Type exprType = getType(ctx.expr());
      String op = ctx.op.getText();
      
      if (op.equals("-") || op.equals("+")) {
         // Unary arithmetic operations require numbers
         if (exprType != null && !exprType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Unary arithmetic operations require numeric operands");
            return false;
         }
         setType(ctx, numberType);
      }
      
      return true;
   }

   @Override 
   public Boolean visitTermExpr(GrammarIIMLParser.TermExprContext ctx) {
      Boolean termValid = visit(ctx.term());
      if (termValid) {
         setType(ctx, getType(ctx.term()));
      }
      return termValid;
   }

   @Override 
   public Boolean visitPrimaryTerm(GrammarIIMLParser.PrimaryTermContext ctx) {
      Boolean primaryValid = visit(ctx.primary());
      if (primaryValid) {
         setType(ctx, getType(ctx.primary()));
      }
      return primaryValid;
   }

   @Override 
   public Boolean visitListElementAccessTerm(GrammarIIMLParser.ListElementAccessTermContext ctx) {
      Boolean termValid = visit(ctx.term());
      Boolean indexValid = visit(ctx.expr());
      
      if (!termValid || !indexValid) {
         return false;
      }
      
      Type termType = getType(ctx.term());
      Type indexType = getType(ctx.expr());
      
      // Check that we're accessing a list
      if (!(termType instanceof ListType)) {
         ErrorHandling.printError(ctx, "List access can only be applied to list variables");
         return false;
      }
      
      // Check that index is numeric
      if (indexType != null && !indexType.equals(numberType)) {
         ErrorHandling.printError(ctx, "List index must be numeric");
         return false;
      }
      
      // For IIML, assume list elements are numbers
      setType(ctx, numberType);
      return true;
   }

   @Override 
   public Boolean visitTypeConversionPrimary(GrammarIIMLParser.TypeConversionPrimaryContext ctx) {
      String typeName = ctx.NUMBER_TYPE().getText();
      
      // Visit the expression to convert
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // For IIML, only number conversions are supported
      if (!"number".equals(typeName)) {
         ErrorHandling.printError(ctx, "Only number type conversions are supported in IIML");
         return false;
      }
      
      setType(ctx, numberType);
      return true;
   }

   @Override 
   public Boolean visitParenthesisPrimary(GrammarIIMLParser.ParenthesisPrimaryContext ctx) {
      Boolean exprValid = visit(ctx.expr());
      if (exprValid) {
         setType(ctx, getType(ctx.expr()));
      }
      return exprValid;
   }

   @Override 
   public Boolean visitListLiteralPrimary(GrammarIIMLParser.ListLiteralPrimaryContext ctx) {
      Boolean listValid = visit(ctx.listLiteral());
      if (listValid) {
         setType(ctx, getType(ctx.listLiteral()));
      }
      return listValid;
   }

   @Override 
   public Boolean visitVariablePrimary(GrammarIIMLParser.VariablePrimaryContext ctx) {
      String varName = ctx.ID().getText();
      
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      // Track variable usage
      trackVariableUsage(varName);
      
      setType(ctx, symbolTable.get(varName));
      return true;
   }

   @Override 
   public Boolean visitReadStringPrimary(GrammarIIMLParser.ReadStringPrimaryContext ctx) {
      // For IIML, read operations return numbers
      setType(ctx, numberType);
      return true;
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         System.err.println("Usage: java IIMLSemanticCheck <iiml-file>");
         System.err.println("       java IIMLSemanticCheck --help");
         System.exit(1);
      }
      
      // Handle help flag
      if (args[0].equals("--help") || args[0].equals("-h")) {
         printHelp();
         System.exit(0);
      }
      
      String inputFile = args[0];
      
      // Reset error/warning counts for fresh analysis
      ErrorHandling.reset();
      
      System.out.println("Analyzing IIML file: " + inputFile);
      System.out.println("==========================================");
      
      ANTLRInputStream input = new ANTLRInputStream(new java.io.FileInputStream(inputFile));
      GrammarIIMLLexer lexer = new GrammarIIMLLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      GrammarIIMLParser parser = new GrammarIIMLParser(tokens);
      
      // Parse the program
      ParseTree tree = parser.program();
      
      // Run semantic analysis
      IIMLSemanticCheck semanticChecker = new IIMLSemanticCheck();
      boolean isValid = semanticChecker.visit(tree);
      
      // Check for unused variables
      semanticChecker.checkUnusedVariables();
      
      // Print unused variable warnings
      for (String unused : semanticChecker.unusedVariables) {
         System.out.println("[" + ErrorHandling.YELLOW + "WARNING" + ErrorHandling.RESET + "] Variable '" + unused + "' declared but never used");
      }
      
      // Print comprehensive summary
      System.out.println("==========================================");
      printAnalysisSummary(inputFile, isValid, semanticChecker);
   }
   
   private static void printHelp() {
      System.out.println("IIML Semantic Checker");
      System.out.println("====================");
      System.out.println("Usage: java IIMLSemanticCheck <iiml-file>");
      System.out.println();
      System.out.println("Options:");
      System.out.println("  --help, -h    Show this help message");
      System.out.println();
      System.out.println("This tool performs semantic analysis on IIML files.");
      System.out.println("It checks for:");
      System.out.println("  - Variable declarations and usage");
      System.out.println("  - Type compatibility");
      System.out.println("  - Scope rules");
      System.out.println("  - Arithmetic operations validity");
      System.out.println("  - Image and figure placement rules");
      System.out.println("  - Style warnings for variable names");
      System.out.println("  - Unused variable detection");
   }
   
   private static void printAnalysisSummary(String inputFile, boolean isValid, IIMLSemanticCheck checker) {
      int errors = ErrorHandling.errorCount();
      int warnings = ErrorHandling.warningCount();
      
      System.out.println("Analysis Summary for: " + inputFile);
      System.out.println("   Variables declared: " + checker.variableCount);
      System.out.println("   List variables: " + checker.listVariableCount);
      System.out.println("   Variables used: " + checker.usedVariables.size());
      System.out.println("   Has image declaration: " + (checker.hasImageDeclaration ? "Yes" : "No"));
      System.out.println("   Has figure placement: " + (checker.hasFigurePlacement ? "Yes" : "No"));
      System.out.println("   Errors found: " + errors);
      System.out.println("   Warnings issued: " + warnings);
      System.out.println();
      
      if (isValid && errors == 0) {
         System.out.println("[" + ErrorHandling.GREEN + "SUCCESS" + ErrorHandling.RESET + "] Semantic analysis passed!");
         if (warnings > 0) {
            System.out.println("[" + ErrorHandling.YELLOW + "NOTE" + ErrorHandling.RESET + "] " + warnings + " style warning(s) found - consider reviewing");
         }
      } else {
         System.out.println("[" + ErrorHandling.RED + "FAILED" + ErrorHandling.RESET + "] Semantic analysis failed with " + errors + " error(s)");
         if (warnings > 0) {
            System.out.println("[" + ErrorHandling.YELLOW + "NOTE" + ErrorHandling.RESET + "] Additional warnings: " + warnings);
         }
      }
      
      // Print unused variables warning
      if (!checker.unusedVariables.isEmpty()) {
         System.out.println();
         System.out.println("[" + ErrorHandling.YELLOW + "UNUSED VARIABLES" + ErrorHandling.RESET + "]:");
         for (String unused : checker.unusedVariables) {
            System.out.println("   - " + unused);
         }
      }
      
      // Print variable summary if successful
      if (isValid && checker.symbolTable.size() > 0) {
         System.out.println();
         System.out.println("Declared Variables:");
         checker.symbolTable.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
               String status = checker.usedVariables.contains(entry.getKey()) ? 
                  "[" + ErrorHandling.GREEN + "USED" + ErrorHandling.RESET + "]" : 
                  "[" + ErrorHandling.YELLOW + "UNUSED" + ErrorHandling.RESET + "]";
               System.out.println("   " + entry.getKey() + " : " + entry.getValue() + " " + status);
            });
      }
   }
}
