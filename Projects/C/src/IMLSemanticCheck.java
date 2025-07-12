import java.util.*;



@SuppressWarnings("CheckReturnValue")
public class IMLSemanticCheck extends GrammarIMLBaseVisitor<Boolean> {

   // Type instances
   private final BooleanType booleanType = new BooleanType();
   private final NumberType numberType = new NumberType();
   private final StringType stringType = new StringType();
   private final ImageType imageType = new ImageType();
   private final PercentageType percentageType = new PercentageType();

   // Symbol table to track declared variables and their types
   private final Map<String, Type> symbolTable = new HashMap<>();
   
   // Stack for nested scopes
   private final Stack<Map<String, Type>> scopeStack = new Stack<>();

   public IMLSemanticCheck() {
      // Initialize
      scopeStack.push(symbolTable);
   }

   @Override public Boolean visitProgram(GrammarIMLParser.ProgramContext ctx) {
      Boolean res = true;
      
      for (GrammarIMLParser.StatementContext stat : ctx.statement()) {
         res = visit(stat);
         if (!res || res == null) {
            return false;
         }
      }
      return true;
   }

   @Override 
   public Boolean visitStatement(GrammarIMLParser.StatementContext ctx) {
      Boolean valid = true;
      
      if (ctx.varDecl() != null) {
         valid = visitVarDecl(ctx.varDecl());
      } 
      else if (ctx.assignment() != null) {
         valid = visitAssignment(ctx.assignment());
      } 
      else if (ctx.imageIO() != null) {           
         valid = visit(ctx.imageIO());
      } 
      else if (ctx.readStmt() != null) {
         valid = visitReadStmt(ctx.readStmt());
      } 
      else if (ctx.morphStmt() != null) {         
         valid = visit(ctx.morphStmt());
      } 
      else if (ctx.drawStmt() != null) {         
         valid = visit(ctx.drawStmt());
      } 
      else if (ctx.runSecondary() != null) {
         valid = visitRunSecondary(ctx.runSecondary());
      } 
      else if (ctx.outputStmt() != null) {
         valid = visitOutputStmt(ctx.outputStmt());
      } 
      else if (ctx.ifStmt() != null) {
         valid = visitIfStmt(ctx.ifStmt());
      } 
      else if (ctx.forStmt() != null) {
         valid = visitForStmt(ctx.forStmt());
      } 
      else if (ctx.untilStmt() != null) {
         valid = visitUntilStmt(ctx.untilStmt());
      }
      else if (ctx.listOperation() != null) {     
         valid = visit(ctx.listOperation());
      } 
      else if (ctx.listDecl() != null) {
         valid = visitListDecl(ctx.listDecl());
      }
      else {
         ErrorHandling.printError(ctx, "Unknown statement type");
         valid = false;
      }
      
      return valid;
   }

   
   @Override 
   public Boolean visitVarDecl(GrammarIMLParser.VarDeclContext ctx) {
      // Get variable name
      String varName = ctx.ID().getText();
      
      // Check if variable already declared
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit type specification
      Boolean typeValid = visit(ctx.typeSpec());
      if (!typeValid) {
         return false;
      }
      
      // Get the resolved type from typeSpec
      Type declaredType = ctx.typeSpec().type;
      if (declaredType == null) {
         ErrorHandling.printError(ctx, "Could not resolve type for variable '" + varName + "'");
         return false;
      }
      
      // Check literal value compatibility before visiting expression
      String exprText = ctx.expr().getText();
      if (ctx.expr() instanceof GrammarIMLParser.StringExprContext) {
         // Remove quotes from string literal
         String stringValue = exprText.substring(1, exprText.length() - 1);
         
         if (declaredType.equals(numberType) && !((NumberType)numberType).isNumber(stringValue)) {
            ErrorHandling.printError(ctx, "Invalid number literal: " + exprText);
            return false;
         }
         else if (declaredType.equals(percentageType) && !((PercentageType)percentageType).isPercentage(stringValue)) {
            ErrorHandling.printError(ctx, "Invalid percentage literal: " + exprText);
            return false;
         }
         else if (declaredType.equals(booleanType) && !((BooleanType)booleanType).isBoolean(stringValue)) {
            ErrorHandling.printError(ctx, "Invalid boolean literal: " + exprText);
            return false;
         }
         else if (declaredType.equals(imageType) && !((ImageType)imageType).isImage(stringValue)) {
            ErrorHandling.printError(ctx, "Invalid image path: " + exprText);
            return false;
         }
      }
      else if (ctx.expr() instanceof GrammarIMLParser.NumberExprContext) {
         if (!declaredType.equals(numberType) && !declaredType.equals(percentageType)) {
            // Additional check: ensure numeric literal matches declared type
            if (declaredType.equals(stringType) || declaredType.equals(booleanType) || declaredType.equals(imageType)) {
               ErrorHandling.printError(ctx, "Cannot assign numeric literal to " + declaredType);
               return false;
            }
         }
      }
      else if (ctx.expr() instanceof GrammarIMLParser.PercentExprContext) {
         if (!declaredType.equals(percentageType) && !declaredType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Cannot assign percentage literal to " + declaredType);
            return false;
         }
      }
      else if (ctx.expr() instanceof GrammarIMLParser.BooleanExprContext) {
         if (!declaredType.equals(booleanType)) {
            ErrorHandling.printError(ctx, "Cannot assign boolean literal to " + declaredType);
            return false;
         }
      }
      
      // Visit expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Get expression type
      Type exprType = ctx.expr().type;
      if (exprType == null) {
         ErrorHandling.printError(ctx, "Could not determine type of initialization expression");
         return false;
      }
      
      // Check type compatibility
      if (!isTypeCompatible(declaredType, exprType)) {
         ErrorHandling.printError(ctx, "Type mismatch: cannot assign " + exprType + " to " + declaredType);
         return false;
      }
      
      // Add variable to symbol table
      symbolTable.put(varName, declaredType);
      
      return true;
   }


   @Override 
   public Boolean visitListDecl(GrammarIMLParser.ListDeclContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable already declared
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit type specification
      Boolean typeValid = visit(ctx.typeSpec());
      if (!typeValid) {
         return false;
      }

      Type declaredType = ctx.typeSpec().type;
      if (declaredType == null) {
         ErrorHandling.printError(ctx, "Could not resolve declared type");
         return false;
      }
      
      Type declaredElementType = ctx.typeSpec().type;
      ListType listType = new ListType("[]"); // Create empty list type
      
      // For populated lists, check type compatibility
      if (ctx.listInitializer() instanceof GrammarIMLParser.PopulatedListContext) {
         GrammarIMLParser.PopulatedListContext populatedCtx = 
               (GrammarIMLParser.PopulatedListContext) ctx.listInitializer();
         
         // Build the actual list string representation for type checking
         StringBuilder listBuilder = new StringBuilder("[");
         for (int i = 0; i < populatedCtx.expr().size(); i++) {
               if (i > 0) listBuilder.append(",");
               listBuilder.append(populatedCtx.expr(i).getText());
         }
         listBuilder.append("]");
         
         listType = new ListType(listBuilder.toString());
         
         // Check if list has uniform type
         if (!listType.hasUniformType()) {
               ErrorHandling.printError(ctx, "List contains elements of different types");
               return false;
         }
         
         // For nested lists, check compatibility differently
         if (declaredElementType instanceof ListType) {
               // Both declared and actual should be list types
               if (!listType.hasUniformType()) {
                  ErrorHandling.printError(ctx, "List contains elements of different types");
                  return false;
               }
               // Accept any uniformly typed nested list for now
         } else {
               // Check if elements match declared primitive type
               String actualElementType = listType.getUniformElementType();
               if (actualElementType == null || 
                  !isTypeCompatible(declaredElementType, stringToType(actualElementType))) {
                  ErrorHandling.printError(ctx, 
                     "List element type mismatch: declared " + declaredElementType + 
                     " but got " + actualElementType);
                  return false;
               }
         }
      }
      
      symbolTable.put(varName, listType);
      return true;
   }

   public Boolean visitDeclareType(GrammarIMLParser.DeclareTypeContext ctx) {
      String typeName = ctx.TYPE().getText();
      
      Type type;
      switch (typeName) {
         case "image":
            type = imageType;
            break;
         case "number":
            type = numberType;
            break;
         case "string":
            type = stringType;
            break;
         case "percentage":
            type = percentageType;
            break;
         case "boolean":
            type = booleanType;
            break;
         default:
            ErrorHandling.printError(ctx, "Unknown type: " + typeName);
            return false;
      }
      
      ctx.type = type;
      return true;
   }

   @Override 
   public Boolean visitListTypeSpec(GrammarIMLParser.ListTypeSpecContext ctx) {
      Boolean valid = visit(ctx.typeSpec());
      if (!valid) {
         return false;
      }
      
      // Create a list type with the inner type
      ctx.type = new ListType("[]");
      return true;
   }

   @Override 
   public Boolean visitEmptyList(GrammarIMLParser.EmptyListContext ctx) {
      return true;
   }

   @Override 
   public Boolean visitPopulatedList(GrammarIMLParser.PopulatedListContext ctx) {
      Boolean valid = true;
      
      for (GrammarIMLParser.ExprContext exprCtx : ctx.expr()) {
         Boolean exprValid = visit(exprCtx);
         if (!exprValid) {
            valid = false;
         }
      }
      
      return valid;
   }

   @Override 
   public Boolean visitAssignment(GrammarIMLParser.AssignmentContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      // Visit expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Check type compatibility
      Type varType = symbolTable.get(varName);
      Type exprType = ctx.expr().type;
      
      if (exprType != null && !isTypeCompatible(varType, exprType)) {
         ErrorHandling.printError(ctx, "Type mismatch in assignment: cannot assign " + 
            exprType + " to " + varType);
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitLoadImage(GrammarIMLParser.LoadImageContext ctx) {
      String varName = ctx.ID().getText();
      String typeName = ctx.TYPE().getText();
      
      // Check if type is image
      if (!"image".equals(typeName)) {
         ErrorHandling.printError(ctx, "Load operation can only be used with image type");
         return false;
      }
      
      // Check if variable already declared
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit the source (STRING or readStmt)
      Boolean sourceValid = true;
      if (ctx.STRING() != null) {
        // Direct string path - should be valid image path
        String path = ctx.STRING().getText();
        path = path.substring(1, path.length()-1); // Remove quotes
        
        // Check for .pgm extension
        if (!path.toLowerCase().endsWith(".pgm")) {
            ErrorHandling.printError(ctx, "Image file must be a .pgm file");
            return false;
        }
        
        if (!imageType.isImage(path)) { 
            ErrorHandling.printWarning(ctx, "Path may not be a valid PGM image file: " + path);
        }
      } else if (ctx.readStmt() != null) {
         sourceValid = visit(ctx.readStmt());
         // Assuming readStmt will return a .pgm file 
      }
      
      if (sourceValid) {
         symbolTable.put(varName, imageType);
      }
      
      return sourceValid;
   }

   public Boolean visitStoreImage(GrammarIMLParser.StoreImageContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      
      // Allow both images and lists (permite GIF)
      if (!varType.equals(imageType) && !(varType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Store operation can only be used with image or list variables");
         return false;
      }
      
      // Check destination path
      String path = ctx.STRING().getText();
      String cleanPath = path.substring(1, path.length()-1); 
      
      // For images, enforce .pgm extension
      if (varType.equals(imageType)) {
         String extension = cleanPath.substring(cleanPath.lastIndexOf('.') + 1).toLowerCase();
         if (!extension.equals("pgm")) {
            ErrorHandling.printError(ctx, "Image files must be stored as .pgm files");
            return false;
         }
      }
      
      // For lists, check if destination supports animation (like GIF)
      if (varType instanceof ListType) {
         String extension = cleanPath.substring(cleanPath.lastIndexOf('.') + 1).toLowerCase();
         if (!extension.equals("gif")) {
            ErrorHandling.printWarning(ctx, "Storing list to non-animated format may only save first frame");
         }
      }
      
      return true;
   }

   @Override 
   public Boolean visitReadStmt(GrammarIMLParser.ReadStmtContext ctx) {
      // Read statements are always valid
      ctx.type = stringType; 
      return true;
   }  

   @Override 
   public Boolean visitDirectMorph(GrammarIMLParser.DirectMorphContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared and is an image
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      if (!varType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Morphological operations can only be applied to images");
         return false;
      }
      
      // Visit the structuring element expression
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // The structuring element can be an image OR a list (kernel)
      Type exprType = ctx.expr().type;
      if (exprType != null && !exprType.equals(imageType) && !(exprType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Structuring element must be an image or list");
         return false;
      }
      
      return true;
   }


   @Override 
   public Boolean visitDrawImage(GrammarIMLParser.DrawImageContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared and is an image
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      if (!varType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Draw statement can only be used with image variables");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitDrawFigure(GrammarIMLParser.DrawFigureContext ctx) {
      // Visit arguments if present
      if (ctx.args() != null) {
         return visit(ctx.args());
      }
      return true;
   }

   @Override 
   public Boolean visitRunSecondary(GrammarIMLParser.RunSecondaryContext ctx) {
      String varName = ctx.ID().getText();
      String typeName = ctx.TYPE().getText();
      
      // Check if variable already declared
      if (symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' already declared");
         return false;
      }
      
      // Visit read statement
      Boolean readValid = visit(ctx.readStmt());
      if (!readValid) {
         return false;
      }
      
      // Map type name to type object
      Type type;
      switch (typeName) {
         case "image":
            type = imageType;
            break;
         case "number":
            type = numberType;
            break;
         case "string":
            type = stringType;
            break;
         case "percentage":
            type = percentageType;
            break;
         case "boolean":
            type = booleanType;
            break;
         default:
            ErrorHandling.printError(ctx, "Unknown type: " + typeName);
            return false;
      }
      
      symbolTable.put(varName, type);
      return true;
   }

   @Override 
   public Boolean visitOutputStmt(GrammarIMLParser.OutputStmtContext ctx) {
      return visit(ctx.expr());
   }

   @Override 
   public Boolean visitIfStmt(GrammarIMLParser.IfStmtContext ctx) {
      // Visit condition
      Boolean condValid = visit(ctx.expr());
      if (!condValid) {
         return false;
      }
      
      // Check if condition is boolean
      Type condType = ctx.expr().type;
      if (condType != null && !condType.equals(booleanType)) {
         ErrorHandling.printError(ctx, "If condition must be boolean, got " + condType);
         return false;
      }
      
      // Visit statements in THEN block
      Boolean thenValid = true;
      for (GrammarIMLParser.StatementContext stmt : ctx.statement()) {
         if (stmt != null) {
            Boolean stmtValid = visit(stmt);
            if (!stmtValid) {
               thenValid = false;
            }
         }
      }
      
      return thenValid;
   }

   @Override 
   public Boolean visitForStmt(GrammarIMLParser.ForStmtContext ctx) {
      String iterVar = ctx.ID().getText();
      String typeName = ctx.TYPE().getText();
      
      // Map type name to type object
      Type iterType;
      switch (typeName) {
         case "image":
            iterType = imageType;
            break;
         case "number":
            iterType = numberType;
            break;
         case "string":
            iterType = stringType;
            break;
         case "percentage":
            iterType = percentageType;
            break;
         case "boolean":
            iterType = booleanType;
            break;
         default:
            ErrorHandling.printError(ctx, "Unknown type: " + typeName);
            return false;
      }
      
      // Visit the iterable expression
      Boolean iterableValid = visit(ctx.expr());
      if (!iterableValid) {
         return false;
      }
      
      Type iterableType = ctx.expr().type;
      
      // Check if iterable is compatible (should be a list or string for iteration)
      if (iterableType != null && !(iterableType instanceof ListType) && !iterableType.equals(stringType)) {
         ErrorHandling.printError(ctx, "For loop can only iterate over lists or strings");
         return false;
      }
      
      // Create new scope for loop variable
      Map<String, Type> loopScope = new HashMap<>(symbolTable);
      loopScope.put(iterVar, iterType);
      scopeStack.push(loopScope);
      
      // Temporarily update symbol table
      Map<String, Type> oldSymbolTable = new HashMap<>(symbolTable);
      symbolTable.put(iterVar, iterType);
      
      // Visit statements in loop body
      Boolean bodyValid = true;
      for (GrammarIMLParser.StatementContext stmt : ctx.statement()) {
         Boolean stmtValid = visit(stmt);
         if (!stmtValid) {
            bodyValid = false;
         }
      }
      
      // Restore symbol table
      symbolTable.clear();
      symbolTable.putAll(oldSymbolTable);
      scopeStack.pop();
      
      return bodyValid;
   }

   @Override 
   public Boolean visitUntilStmt(GrammarIMLParser.UntilStmtContext ctx) {
      // Visit condition
      Boolean condValid = visit(ctx.expr());
      if (!condValid) {
         return false;
      }
      
      // Check if condition is boolean
      Type condType = ctx.expr().type;
      if (condType != null && !condType.equals(booleanType)) {
         ErrorHandling.printError(ctx, "Until condition must be boolean, got " + condType);
         return false;
      }
      
      // Visit statements in loop body
      Boolean bodyValid = true;
      for (GrammarIMLParser.StatementContext stmt : ctx.statement()) {
         Boolean stmtValid = visit(stmt);
         if (!stmtValid) {
            bodyValid = false;
         }
      }
      
      return bodyValid;
   }

   @Override 
   public Boolean visitListAppend(GrammarIMLParser.ListAppendContext ctx) {
      String listName = ctx.ID().getText();
      
      // Check if list variable is declared
      if (!symbolTable.containsKey(listName)) {
         ErrorHandling.printError(ctx, "Variable '" + listName + "' not declared");
         return false;
      }
      
      Type listType = symbolTable.get(listName);
      if (!(listType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Append operation can only be used with list variables");
         return false;
      }
      
      // Visit expression to append
      return visit(ctx.expr());
   }

   public Boolean visitListRemove(GrammarIMLParser.ListRemoveContext ctx) {
      String listName = ctx.ID().getText();
      
      // Check if list variable is declared
      if (!symbolTable.containsKey(listName)) {
         ErrorHandling.printError(ctx, "Variable '" + listName + "' not declared");
         return false;
      }
      
      Type listType = symbolTable.get(listName);
      if (!(listType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Remove operation can only be used with list variables");
         return false;
      }
      
      // Visit expression (index to remove)
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // Index should be a number
      Type exprType = ctx.expr().type;
      if (exprType != null && !exprType.equals(numberType)) {
         ErrorHandling.printError(ctx, "List index must be a number");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitTypeConversionExpr(GrammarIMLParser.TypeConversionExprContext ctx) {
      Boolean valid = visit(ctx.expr());
      if (!valid) return false;
      
      String typeName = ctx.TYPE().getText();
      Type exprType = ctx.expr().type;
      
      // If converting from string, validate its content (only for literal strings)
      if (exprType != null && exprType.equals(stringType)) {
         String rawText = ctx.expr().getText();
         
         // Only perform content validation if it's a literal string (enclosed in quotes)
         // Allow runtime strings (like from read statements) without validation
         if (rawText.length() >= 2 && rawText.startsWith("\"") && rawText.endsWith("\"")) {
               String stringValue = rawText.substring(1, rawText.length() - 1); // Remove quotes
               
               switch (typeName) {
                  case "number":
                     if (!isValidNumber(stringValue)) {
                           ErrorHandling.printError(ctx, "Invalid number string: " + rawText);
                           return false;
                     }
                     break;
                  case "boolean":
                     if (!stringValue.equals("true") && !stringValue.equals("false")) {
                           ErrorHandling.printError(ctx, "Boolean must be \"true\" or \"false\"");
                           return false;
                     }
                     break;
                  case "percentage":
                     if (!stringValue.endsWith("%") || !isValidNumber(stringValue.substring(0, stringValue.length() - 1))) {
                           ErrorHandling.printError(ctx, "Invalid percentage format (e.g., \"50%\")");
                           return false;
                     }
                     break;
                  // Other types (image, string) don't need content validation
               }
         }
         // For non-literal strings (variables, read statements, etc.), 
         // we trust that the conversion will be handled at runtime
      }
      
      // Set the resulting type
      switch (typeName) {
         case "image":     ctx.type = imageType; break;
         case "number":   ctx.type = numberType; break;
         case "string":   ctx.type = stringType; break;
         case "percentage": ctx.type = percentageType; break;
         case "boolean":  ctx.type = booleanType; break;
         default:
               ErrorHandling.printError(ctx, "Unknown type: " + typeName);
               return false;
      }
      return true;
   }

   @Override 
   public Boolean visitNumberExpr(GrammarIMLParser.NumberExprContext ctx) {
      ctx.type = numberType;
      return true;
   }

   @Override 
   public Boolean visitScalingOperation(GrammarIMLParser.ScalingOperationContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      String op = ctx.op.getText();
      
      // Left operand should be an image for scaling operations
      if (leftType != null && !leftType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Scaling operations require image as left operand");
         return false;
      }
      
      // Right operand should be a number or percentage depending on operation
      if (rightType != null) {
         if (op.equals("+*") || op.equals("-*")) {
               // Addition/subtraction scaling requires number
               if (!rightType.equals(numberType)) {
                  ErrorHandling.printError(ctx, "Scaling operation " + op + " requires numeric operand");
                  return false;
               }
         } else if (op.equals("|*")) {
               // Multiplication scaling can accept number or percentage
               if (!rightType.equals(numberType) && !rightType.equals(percentageType)) {
                  ErrorHandling.printError(ctx, "Scaling operation " + op + " requires numeric or percentage operand");
                  return false;
               }
         }
      }

      ctx.type = imageType; // Scaling operations return images
      return true;
   }

   @Override 
   public Boolean visitParenExpr(GrammarIMLParser.ParenExprContext ctx) {
      Boolean valid = visit(ctx.expr());
      if (valid) {
         ctx.type = ctx.expr().type;
      }
      return valid;
   }

   @Override 
   public Boolean visitPixelMulDiv(GrammarIMLParser.PixelMulDivContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      String op = ctx.op.getText();
      
      // Pixel operations can work with:
      // - image op image
      // - image op number/percentage  
      // - number/percentage op image
      boolean leftIsImageOrNumeric = leftType != null && 
         (leftType.equals(imageType) || leftType.equals(numberType) || leftType.equals(percentageType));
      boolean rightIsImageOrNumeric = rightType != null && 
         (rightType.equals(imageType) || rightType.equals(numberType) || rightType.equals(percentageType));
      
      if (!leftIsImageOrNumeric || !rightIsImageOrNumeric) {
         ErrorHandling.printError(ctx, "Pixel operations require image, number, or percentage operands");
         return false;
      }
      
      // At least one operand should be an image for pixel operations
      if (leftType != null && rightType != null && 
         !leftType.equals(imageType) && !rightType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Pixel operations require at least one image operand");
         return false;
      }
      
      ctx.type = imageType; // Pixel operations return images
      return true;
   }

   @Override 
   public Boolean visitPixelAddSub(GrammarIMLParser.PixelAddSubContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      String op = ctx.op.getText();
      
      // Pixel operations can work with:
      // - image op image
      // - image op number/percentage  
      // - number/percentage op image
      boolean leftIsImageOrNumeric = leftType != null && 
         (leftType.equals(imageType) || leftType.equals(numberType) || leftType.equals(percentageType));
      boolean rightIsImageOrNumeric = rightType != null && 
         (rightType.equals(imageType) || rightType.equals(numberType) || rightType.equals(percentageType));
      
      if (!leftIsImageOrNumeric || !rightIsImageOrNumeric) {
         ErrorHandling.printError(ctx, "Pixel operations require image, number, or percentage operands");
         return false;
      }
      
      // At least one operand should be an image for pixel operations
      if (leftType != null && rightType != null && 
         !leftType.equals(imageType) && !rightType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Pixel operations require at least one image operand");
         return false;
      }
      
      ctx.type = imageType; // Pixel operations return images
      return true;
   }


   @Override 
   public Boolean visitStringExpr(GrammarIMLParser.StringExprContext ctx) {
      ctx.type = stringType;
      return true;
   }

   @Override 
   public Boolean visitMultDivArithmetic(GrammarIMLParser.MultDivArithmeticContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      String op = ctx.op.getText();
      
      // Allow number-number, number-percentage, or percentage-number arithmetic
      boolean leftIsNumeric = leftType != null && 
         (leftType.equals(numberType) || leftType.equals(percentageType));
      boolean rightIsNumeric = rightType != null && 
         (rightType.equals(numberType) || rightType.equals(percentageType));
      
      if (!leftIsNumeric || !rightIsNumeric) {
         ErrorHandling.printError(ctx, "Arithmetic operations require numeric or percentage operands");
         return false;
      }
      
      // Check for division by zero
      if (op.equals("/")) {
         if (ctx.expr(1) instanceof GrammarIMLParser.NumberExprContext) {
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
         } else if (ctx.expr(1) instanceof GrammarIMLParser.PercentExprContext) {
            String rightText = ctx.expr(1).getText();
            try {
               // Remove the % and check if the numeric part is zero
               String numericPart = rightText.substring(0, rightText.length() - 1);
               double rightValue = Double.parseDouble(numericPart);
               if (rightValue == 0.0) {
                  ErrorHandling.printError(ctx, "Division by zero");
                  return false;
               }
            } catch (NumberFormatException e) {
               // Not a constant percentage, can't check statically
            }
         }
      }
      
      // Determine result type based on operand types
      // If both are numbers, result is number
      // If either is percentage, result is percentage (to maintain precision context)
      if (leftType != null && rightType != null) {
         if (leftType.equals(percentageType) || rightType.equals(percentageType)) {
            ctx.type = percentageType;
         } else {
            ctx.type = numberType;
         }
      } else {
         ctx.type = numberType; // Default fallback
      }
      
      return true;
   }

   private Boolean visitBinaryArithmetic(GrammarIMLParser.ExprContext ctx, GrammarIMLParser.ExprContext left, GrammarIMLParser.ExprContext right, String op) {
      Boolean leftValid = visit(left);
      Boolean rightValid = visit(right);
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = left.type;
      Type rightType = right.type;
      
      // Both operands should be numbers for arithmetic
      if (leftType != null && rightType != null) {
         if (!leftType.equals(numberType) || !rightType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Arithmetic operations require numeric operands");
            return false;
         }
      }
      
      ctx.type = numberType;
      return true;
   }

   @Override 
   public Boolean visitMorphChainExpr(GrammarIMLParser.MorphChainExprContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      
      // Left operand should be an image (the image to be morphed)
      if (leftType != null && !leftType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Morphological operations require image as left operand");
         return false;
      }
      
      // Right operand can be an image OR a list (structuring element/kernel)
      if (rightType != null && !rightType.equals(imageType) && !(rightType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Structuring element must be an image or list");
         return false;
      }
      
      ctx.type = imageType; // Morphological operations return images
      return true;
   }

   @Override 
   public Boolean visitLogicalOperation(GrammarIMLParser.LogicalOperationContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      
      // Both operands should be boolean
      if (leftType != null && !leftType.equals(booleanType)) {
         ErrorHandling.printError(ctx, "Logical operation requires boolean operands");
         return false;
      }
      if (rightType != null && !rightType.equals(booleanType)) {
         ErrorHandling.printError(ctx, "Logical operation requires boolean operands");
         return false;
      }
      
      ctx.type = booleanType;
      return true;
   }

   @Override 
   public Boolean visitFlipOperation(GrammarIMLParser.FlipOperationContext ctx) {
      Boolean exprValid = visit(ctx.expr());
      
      if (!exprValid) {
         return false;
      }
      
      Type exprType = ctx.expr().type;
      String op = ctx.op.getText();
      
      // Flip operations should work on images
      if (exprType != null && !exprType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Flip operations can only be applied to images");
         return false;
      }
      
      // Validate the flip operator is one of: -, +, |
      if (!op.equals("-") && !op.equals("+") && !op.equals("|")) {
         ErrorHandling.printError(ctx, "Invalid flip operator: " + op);
         return false;
      }
      
      ctx.type = imageType; // Flip operations return images
      return true;
   }

   @Override 
   public Boolean visitPropertyExpr(GrammarIMLParser.PropertyExprContext ctx) {
      Boolean valid = visit(ctx.propertyAccess());
      if (valid) {
         // Propagate the type from propertyAccess to this expression
         ctx.type = ctx.propertyAccess().type;
      }
      return valid;
   }

   @Override 
   public Boolean visitListIndexExpr(GrammarIMLParser.ListIndexExprContext ctx) {
      Boolean listValid = visit(ctx.expr(0));
      Boolean indexValid = visit(ctx.expr(1));
      
      if (!listValid || !indexValid) {
         return false;
      }
      
      Type listType = ctx.expr(0).type;
      Type indexType = ctx.expr(1).type;
      
      if (listType != null && !(listType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Index operation can only be used with lists");
         return false;
      }
      
      if (indexType != null && !indexType.equals(numberType)) {
         ErrorHandling.printError(ctx, "List index must be a number");
         return false;
      }
      
      // Type of indexed element depends on list element type
      ctx.type = numberType; // Simplified - should derive from list element type
      return true;
   }

   @Override 
   public Boolean visitListExpr(GrammarIMLParser.ListExprContext ctx) {
      Boolean valid = visit(ctx.listInitializer());
      if (valid) {
         ctx.type = new ListType("[]");
      }
      return valid;
   }

   @Override 
   public Boolean visitAddSubArithmetic(GrammarIMLParser.AddSubArithmeticContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      String op = ctx.op.getText();
      
      // Handle string concatenation with + operator
      if (op.equals("+")) {
         // Allow string concatenation: string + string
         if ((leftType != null && leftType.equals(stringType)) && 
            (rightType != null && rightType.equals(stringType))) {
            ctx.type = stringType;
            return true;
         }
      }
      
      // Original arithmetic logic for numbers
      if (leftType != null && rightType != null) {
         if (!leftType.equals(numberType) || !rightType.equals(numberType)) {
            ErrorHandling.printError(ctx, "Arithmetic operations require numeric operands");
            return false;
         }
      }
      
      ctx.type = numberType;
      return true;
   }

   @Override 
   public Boolean visitComparison(GrammarIMLParser.ComparisonContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      
      // Types should be compatible for comparison
      if (leftType != null && rightType != null && !isTypeCompatible(leftType, rightType)) {
         ErrorHandling.printError(ctx, "Cannot compare incompatible types: " + 
            leftType + " and " + rightType);
         return false;
      }
      
      ctx.type = booleanType;
      return true;
   }
   
   @Override 
   public Boolean visitAllPixelExpr(GrammarIMLParser.AllPixelExprContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared and is an image
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      if (!varType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Pixel operations can only be applied to images");
         return false;
      }
      
      // Visit pixel comparison
      Boolean compValid = visit(ctx.pixelComparison());
      if (!compValid) {
         return false;
      }
      
      ctx.type = booleanType; // 'all pixel' operations return boolean
      return true;
   }

   @Override 
   public Boolean visitBooleanExpr(GrammarIMLParser.BooleanExprContext ctx) {
      ctx.type = booleanType;
      return true;
   }

   @Override 
   public Boolean visitReadExpr(GrammarIMLParser.ReadExprContext ctx) {
      Boolean valid = visit(ctx.readStmt());
      if (valid) {
         ctx.type = stringType; 
      }
      return valid;
   }

   @Override 
   public Boolean visitCountPixelExpr(GrammarIMLParser.CountPixelExprContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared and is an image
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      if (!varType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Count pixel operation can only be applied to images");
         return false;
      }
      
      // Visit the expression (the pixel value to count)
      Boolean exprValid = visit(ctx.expr());
      if (!exprValid) {
         return false;
      }
      
      // The expression should be a number (pixel value)
      Type exprType = ctx.expr().type;
      if (exprType != null && !exprType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Count pixel operation requires numeric pixel value");
         return false;
      }
      
      ctx.type = numberType; // Count operations return numbers
      return true;
   }

   @Override 
   public Boolean visitUnaryOperation(GrammarIMLParser.UnaryOperationContext ctx) {
      Boolean exprValid = visit(ctx.expr());
      
      if (!exprValid) {
         return false;
      }
      
      Type exprType = ctx.expr().type;
      String op = ctx.op.getText();
      
      // Handle image negation operator (.-)
      if (op.equals(".-")) {
         if (exprType != null && !exprType.equals(imageType)) {
               ErrorHandling.printError(ctx, "Image negation operator (.-) requires an image operand");
               return false;
         }
         ctx.type = imageType;
         return true;
      }
      
      ErrorHandling.printError(ctx, "Invalid unary operator: " + op);
      return false;
   }

   @Override 
   public Boolean visitAnyPixelExpr(GrammarIMLParser.AnyPixelExprContext ctx) {
      String varName = ctx.ID().getText();
      
      // Check if variable is declared and is an image
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      if (!varType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Pixel operations can only be applied to images");
         return false;
      }
      
      // Visit pixel comparison
      Boolean compValid = visit(ctx.pixelComparison());
      if (!compValid) {
         return false;
      }
      
      ctx.type = booleanType; // 'any pixel' operations return boolean
      return true;
   }

   @Override 
   public Boolean visitNotExpr(GrammarIMLParser.NotExprContext ctx) {
      Boolean valid = visit(ctx.expr());
      if (!valid) {
         return false;
      }
      
      Type exprType = ctx.expr().type;
      if (exprType != null && !exprType.equals(booleanType)) {
         ErrorHandling.printError(ctx, "Not operation requires boolean operand");
         return false;
      }
      
      ctx.type = booleanType;
      return true;
   }

   @Override 
   public Boolean visitMorphOperationParenExpr(GrammarIMLParser.MorphOperationParenExprContext ctx) {
      Boolean valid = visit(ctx.morphOperation());
      if (valid) {
         ctx.type = imageType; // Morphological operations return images
      }
      return valid;
   }

   @Override 
   public Boolean visitPercentExpr(GrammarIMLParser.PercentExprContext ctx) {
      ctx.type = percentageType;
      return true;
   }

   @Override 
   public Boolean visitIdExpr(GrammarIMLParser.IdExprContext ctx) {
      String varName = ctx.ID().getText();
      
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      ctx.type = symbolTable.get(varName);
      return true;
   }

   @Override 
   public Boolean visitPixelComparison(GrammarIMLParser.PixelComparisonContext ctx) {
      Boolean exprValid = visit(ctx.expr());
      
      if (!exprValid) {
         return false;
      }
      
      // The expression should be a number (pixel value to compare)
      Type exprType = ctx.expr().type;
      if (exprType != null && !exprType.equals(numberType)) {
         ErrorHandling.printError(ctx, "Pixel comparison requires numeric value");
         return false;
      }
      
      return true;
   }

   @Override 
   public Boolean visitPropertyAccess(GrammarIMLParser.PropertyAccessContext ctx) {
      String varName = ctx.ID().getText();
      
      if (!symbolTable.containsKey(varName)) {
         ErrorHandling.printError(ctx, "Variable '" + varName + "' not declared");
         return false;
      }
      
      Type varType = symbolTable.get(varName);
      String property = ctx.getChild(0).getText(); // 'columns', 'rows', or 'length'
      
      // Validate property access based on variable type
      if ("length".equals(property)) {
         if (!(varType instanceof ListType) && !varType.equals(stringType)) {
            ErrorHandling.printError(ctx, "Length property can only be used with lists or strings");
            return false;
         }
      } else if ("columns".equals(property) || "rows".equals(property)) {
         if (!varType.equals(imageType)) {
            ErrorHandling.printError(ctx, property + " property can only be used with images");
            return false;
         }
      }
      
      ctx.type = numberType; // All properties return numbers
      return true;
   }

   @Override 
   public Boolean visitArgs(GrammarIMLParser.ArgsContext ctx) {
      Boolean valid = true;
      for (GrammarIMLParser.ExprContext expr : ctx.expr()) {
         Boolean exprValid = visit(expr);
         if (!exprValid) {
            valid = false;
         }
      }
      return valid;
   }

   @Override 
   public Boolean visitMorphOp(GrammarIMLParser.MorphOpContext ctx) {
      // Morph operations are always syntactically valid
      // Semantic validation happens at the operation level
      return true;
   }

   @Override 
   public Boolean visitMorphOperation(GrammarIMLParser.MorphOperationContext ctx) {
      Boolean leftValid = visit(ctx.expr(0));
      Boolean rightValid = visit(ctx.expr(1));
      
      if (!leftValid || !rightValid) {
         return false;
      }
      
      Type leftType = ctx.expr(0).type;
      Type rightType = ctx.expr(1).type;
      
      // Left operand should be an image (the image to be morphed)
      if (leftType != null && !leftType.equals(imageType)) {
         ErrorHandling.printError(ctx, "Morphological operations require image as left operand");
         return false;
      }
      
      // Right operand can be an image OR a list (structuring element)
      if (rightType != null && !rightType.equals(imageType) && !(rightType instanceof ListType)) {
         ErrorHandling.printError(ctx, "Structuring element must be an image or list");
         return false;
      }
      
      // Visit morphological operator
      Boolean morphOpValid = visit(ctx.morphOp());
      if (!morphOpValid) {
         return false;
      }
      
      return true;
   }

   // Helper function
   private Boolean isTypeCompatible(Type declared, Type actual) {
    if (declared == null || actual == null) {
        return false;
    }
    
    // 1. Exact type match always works
    if (declared.equals(actual)) {
        return true;
    }
    
    // 2. Allow specific safe conversions
    // Number <-> Percentage
    if ((declared.equals(numberType) && actual.equals(percentageType)) ||
        (declared.equals(percentageType) && actual.equals(numberType))) {
        return true;
    }
    
    // 3. No other implicit conversions allowed
    return false;
   }

   private Type stringToType(String typeName) {
    switch (typeName.toLowerCase()) {
        case "boolean": return booleanType;
        case "number": return numberType;
        case "string": return stringType;
        case "image": return imageType;
        case "percentage": return percentageType;
        default: return null;
    }
   }

   // Helper: Check if a string is a valid number
   private boolean isValidNumber(String s) {
      try {
         Double.parseDouble(s);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}
