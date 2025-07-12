import java.awt.image.RescaleOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.time.Instant;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import org.stringtemplate.v4.*;

@SuppressWarnings("CheckReturnValue")
public class IMLCompiler extends GrammarIMLBaseVisitor<ST> {
    
    public static int programCount = 0;
   private STGroup templates = new STGroupFile("IMLCompiler.stg");
   Map<String, String> symbolTable = new HashMap<>(); //TODO String-String ou String-Type

    public static void main(String[] args) throws Exception {
        CharStream input;

        if (args.length > 0) {
            input = CharStreams.fromFileName(args[0]);
        } else {
            input = CharStreams.fromStream(System.in);
        }

        GrammarIMLLexer lexer = new GrammarIMLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarIMLParser parser = new GrammarIMLParser(tokens);

        ParseTree tree = parser.program();

        IMLCompiler visitor = new IMLCompiler();      
        ST result = visitor.visit(tree);              

        String output = result.render();

        // Gerar nome de arquivo com timestamp
        int uniqueNumber = loadAndIncrementCounter();
        String filename = "IML_" + uniqueNumber + ".java";
        Files.write(Paths.get(filename), output.getBytes());
        programCount++;
        System.out.println("Arquivo salvo como: " + filename);
    }


    private static int loadAndIncrementCounter() throws IOException {
        Path counterPath = Paths.get("counter.txt");

        int count = 0;
        if (Files.exists(counterPath)) {
            count = Integer.parseInt(Files.readString(counterPath).trim());
        }

        Files.writeString(counterPath, String.valueOf(count + 1));
        return count;
    }

    private static int loadCounter() throws IOException {
        Path counterPath = Paths.get("counter.txt");

        int count = 0;
        if (Files.exists(counterPath)) {
            count = Integer.parseInt(Files.readString(counterPath).trim());
        }
        return count;
    }

    private String indent(String code, int level) {
        String indent = "    ".repeat(level);
        return Arrays.stream(code.split("\n"))
                    .map(line -> indent + line)
                    .collect(Collectors.joining("\n"));
    }

    private String capitalizeType(String type) {
        if (type == null || type.isEmpty()) return type;
        return type.substring(0, 1).toUpperCase() + type.substring(1);
    }

    private String mapListType(GrammarIMLParser.TypeSpecContext ctx) {
        if (ctx instanceof GrammarIMLParser.DeclareTypeContext) {
            String imlType = ((GrammarIMLParser.DeclareTypeContext) ctx).TYPE().getText();
            return mapType(imlType);
        } else if (ctx instanceof GrammarIMLParser.ListTypeSpecContext) {
            GrammarIMLParser.ListTypeSpecContext listCtx = (GrammarIMLParser.ListTypeSpecContext) ctx;
            return "List<" + mapListType(listCtx.typeSpec()) + ">";
        } else {
            return mapType(ctx.getText());
        }
    }


    private String mapType(String imlType) {
        switch (imlType) {
            case "number": return "Integer";
            case "percentage": return "Double";
            case "string": return "String";
            case "boolean": return "Boolean";
            case "image": return "ImagePlus";
            default: return capitalizeType(imlType);
        }
    }

    private String mapIMLTypesToJava(String typeStr) {
        typeStr = typeStr.replaceAll("\\bnumber\\b", "Integer");
        typeStr = typeStr.replaceAll("\\bpercentage\\b", "Double");
        typeStr = typeStr.replaceAll("\\bstring\\b", "String");
        typeStr = typeStr.replaceAll("\\bboolean\\b", "Boolean");
        typeStr = typeStr.replaceAll("\\bimage\\b", "Image");
        return typeStr;
    }

    // Método auxiliar para obter o tipo base de uma lista aninhada
    private String getBaseType(GrammarIMLParser.TypeSpecContext ctx) {
        if (ctx.getChildCount() == 4 && ctx.getChild(0).getText().equals("list")) {
            // "list of typeSpec"
            GrammarIMLParser.TypeSpecContext inner = (GrammarIMLParser.TypeSpecContext) ctx.getChild(3);
            return getBaseType(inner);
        } else {
            return ctx.getText();
        }
    }
        
   @Override public ST visitProgram(GrammarIMLParser.ProgramContext ctx) {
        ST res = templates.getInstanceOf("main");
        
        int uniqueNumber = 0;
        try {
            uniqueNumber = loadCounter();
        } catch (IOException ex) {
        }

        String uniqueName = "IML_" + (uniqueNumber);
        res.add("name", uniqueName);
        // iterate all stat*
        if (ctx.statement() != null) {
         for(GrammarIMLParser.StatementContext stat: ctx.statement())
            res.add("stat", visit(stat));
        }
        return res; 

   }

   @Override
   public ST visitStatement(GrammarIMLParser.StatementContext ctx) {
       ST res = null;

       if (ctx.varDecl() != null) {
           res = visit(ctx.varDecl());
       } else if (ctx.assignment() != null) {
           res = visit(ctx.assignment());
       } else if (ctx.imageIO() != null) {
           res = visit(ctx.imageIO());
       } else if (ctx.readStmt() != null) {
           res = visit(ctx.readStmt());
       } else if (ctx.morphStmt() != null) {
           res = visit(ctx.morphStmt());
       } else if (ctx.drawStmt() != null) {
           res = visit(ctx.drawStmt());
       } else if (ctx.runSecondary() != null) {
           res = visit(ctx.runSecondary());
       } else if (ctx.outputStmt() != null) {
           res = visit(ctx.outputStmt());
       } else if (ctx.ifStmt() != null) {
           res = visit(ctx.ifStmt());
       } else if (ctx.forStmt() != null) {
           res = visit(ctx.forStmt());
       } else if (ctx.untilStmt() != null) {
           res = visit(ctx.untilStmt());
       } else if (ctx.listOperation() != null) {
           res = visit(ctx.listOperation());
       } else if (ctx.listDecl() != null) {
           res = visit(ctx.listDecl());
       } else {
           throw new RuntimeException("Unknown statement type at line " + ctx.getStart().getLine());
       }

       return res;
   }

    @Override
    public ST visitVarDecl(GrammarIMLParser.VarDeclContext ctx) {
        ST res = templates.getInstanceOf("varDecl");
        String imlType = ctx.typeSpec().getText();
        String javaType = mapType(imlType); 
        res.add("type", javaType);
        res.add("id", ctx.ID().getText());
        res.add("expr", visit(ctx.expr()).render());
        return res;
    }

    @Override
    public ST visitListDecl(GrammarIMLParser.ListDeclContext ctx) {
        ST res = templates.getInstanceOf("listDecl");
        ST typeStr = visit(ctx.typeSpec());
        res.add("type", typeStr);
        res.add("id", ctx.ID().getText());
        res.add("Initializer", visit(ctx.listInitializer()));

        return res;
    }

    @Override
    public ST visitDeclareType(GrammarIMLParser.DeclareTypeContext ctx) {
        ST res = templates.getInstanceOf("declareType");
        String typeStr = (ctx.TYPE()).getText();
        typeStr = mapType(typeStr);
        res.add("type", typeStr); 
        return res;
    }


   @Override public ST visitListTypeSpec(GrammarIMLParser.ListTypeSpecContext ctx) {
      ST res = templates.getInstanceOf("ListTypeSpec");
      res.add ("type", visit(ctx.typeSpec()));
      return res;
   }

   @Override public ST visitEmptyList(GrammarIMLParser.EmptyListContext ctx) {
      ST res = null;
      return visitChildren(ctx);
      //return res;
   }

    @Override
    public ST visitPopulatedList(GrammarIMLParser.PopulatedListContext ctx) {
        ST res = templates.getInstanceOf("ListInitializer");
        if (res == null) {
            throw new RuntimeException("Template 'ListInitializer' not found.");
        }

        for (GrammarIMLParser.ExprContext e : ctx.expr()) {
            ST exprST = visit(e);
            if (exprST != null) {
                res.add("expr", exprST);
            } else {
                throw new RuntimeException("Null expression in populated list.");
            }
        }

        return res;
    }

   @Override public ST visitAssignment(GrammarIMLParser.AssignmentContext ctx) {
        ST res = templates.getInstanceOf("assignment");
        res.add("id", ctx.ID().getText());
        res.add("expr", visit(ctx.expr()));
        return res;
   }

    @Override
    public ST visitLoadImage(GrammarIMLParser.LoadImageContext ctx) {
        ST res = templates.getInstanceOf("loadImage");
        String imlType = ctx.TYPE().getText();
        String javaType = imlType.equals("image") ? "Image" : imlType;
        res.add("type", javaType);
        res.add("id", ctx.ID().getText());
        res.add("source", ctx.STRING() != null ? ctx.STRING().getText() : visit(ctx.readStmt()));
        return res;
    }

   @Override public ST visitStoreImage(GrammarIMLParser.StoreImageContext ctx) {
        ST res = templates.getInstanceOf("StoreImage");
        res.add("id", ctx.ID().getText());
        res.add("expr", ctx.STRING().getText());
        return res;
   }

   @Override public ST visitReadStmt(GrammarIMLParser.ReadStmtContext ctx) {
      ST res = templates.getInstanceOf("ReadStmt");
      res.add("input", ctx.STRING().getText());
      return res;
   }

   @Override public ST visitDirectMorph(GrammarIMLParser.DirectMorphContext ctx) {
        ST res = templates.getInstanceOf("morphStmt");
        res.add("id", ctx.ID().getText());
        res.add("operation", ctx.morphOp().getText());
        res.add("value", visit(ctx.expr()));
        return res;
   }

   @Override public ST visitDrawImage(GrammarIMLParser.DrawImageContext ctx) {
        
      String id = ctx.ID().getText();
      ST res = templates.getInstanceOf("drawImage");
      res.add("id", ctx.ID().getText());
      return res;
   }

   @Override public ST visitDrawFigure(GrammarIMLParser.DrawFigureContext ctx) {
        ST res = templates.getInstanceOf("drawFigure");
        res.add("figure", ctx.FIGURE().getText());
        res.add("args", ctx.args() != null ? visit(ctx.args()) : null);
        return res;
   }

   // Tudo feito para cima 


    @Override
    public ST visitRunSecondary(GrammarIMLParser.RunSecondaryContext ctx) {
        //ST st = templates.getInstanceOf("varDecl");
        //String imlType = ctx.TYPE().getText();
        //String javaType = mapType(imlType);
        //String id = ctx.ID().getText();

        ST runSec = templates.getInstanceOf("RunSecondary");

        // Se for "run from read ..."
        //if (ctx.readStmt() != null) {
        //    String source = visit(ctx.readStmt()).render();
        //    String source2 = "MorphologyWithImageJ.run(" + source + ")";
        //    st.add("type", javaType);
        //    st.add("id", id);
        //    st.add("expr", source2); // apenas a chamada ao read
        //    
        //    return st;
        //}

        // Caso contrário, usa runSecondary normalmente
        String source = visit(ctx.readStmt()).render();
        String imlType = ctx.TYPE().getText();
        String javaType = mapType(imlType);
        runSec.add("type", javaType);
        runSec.add("ID", ctx.ID().getText());
        runSec.add("Path", visit(ctx.readStmt()));

        //st.add("type", javaType);
        //st.add("id", id);
        //st.add("expr", runSec.render());
        return runSec;
    }


    // Done
    @Override
    public ST visitOutputStmt(GrammarIMLParser.OutputStmtContext ctx) {
        ST st = new ST("System.out.println(<msg>);");
        ST msg = visit(ctx.expr()); // ou usa visit(ctx.expr()).render() se expr pode ser mais complexo
        st.add("msg", msg);
        return st;
    }

    // Done
    @Override
    public ST visitIfStmt(GrammarIMLParser.IfStmtContext ctx) {
        ST st = templates.getInstanceOf("ifStmt");
        String cond = visit(ctx.expr()).render();
        if (cond.startsWith("(") && cond.endsWith(")")) {
            cond = cond.substring(1, cond.length() - 1);
        }
        st.add("cond", cond);

        List<String> thenBranch = new ArrayList<>();
        int thenCount = ctx.ELSE() == null ? ctx.statement().size() : ctx.statement().size() / 2;
        for (int i = 0; i < thenCount; i++) {
            thenBranch.add(indent(visit(ctx.statement(i)).render(), 1));
        }
        st.add("thenBranch", thenBranch);

        if (ctx.ELSE() != null) {
            List<String> elseBranch = new ArrayList<>();
            for (int i = thenCount; i < ctx.statement().size(); i++) {
                elseBranch.add(indent(visit(ctx.statement(i)).render(), 1));
            }
            st.add("elseBranch", elseBranch);
        }

        return st;
    }


    //Done
    @Override
    public ST visitForStmt(GrammarIMLParser.ForStmtContext ctx) {
        ST st = templates.getInstanceOf("forStmt");

        String imlType = ctx.TYPE().getText();
        String javaType;
        switch (imlType) {
            case "percentage":
                javaType = "double";
                break;
            case "number":
                javaType = "int";
                break;
            case "string":
                javaType = "String";
                break;
            case "boolean":
                javaType = "boolean";
                break;
            default:
                javaType = imlType;
        }
        st.add("type", javaType);
        st.add("id", ctx.ID().getText());
        st.add("expr", visit(ctx.expr()).render());

        List<String> body = new ArrayList<>();
        for (GrammarIMLParser.StatementContext statCtx : ctx.statement()) {
            body.add(indent(visit(statCtx).render(), 1)); // indentação de 1 nível
        }
        st.add("body", body);

        return st;
    }


   @Override public ST visitUntilStmt(GrammarIMLParser.UntilStmtContext ctx) {
        ST st = templates.getInstanceOf("UntilStmt");
        // A condição do until deve ser negada para o while
        String cond = visit(ctx.expr()).render();
        st.add("cond", cond);

        List<String> body = new ArrayList<>();
        for (GrammarIMLParser.StatementContext statCtx : ctx.statement()) {
            body.add(indent(visit(statCtx).render(), 1)); // indentação de 1 nível
        }
        st.add("body", body);

        return st;
    }

    //Done
    @Override
    public ST visitListAppend(GrammarIMLParser.ListAppendContext ctx) {
        ST st = new ST("<id>.add(<value>);");
        st.add("id", ctx.ID().getText());
        ST valueST = visit(ctx.expr());
        String value = valueST != null ? valueST.render() : "null";
        st.add("value", value);
        return st;
    }


   @Override public ST visitListRemove(GrammarIMLParser.ListRemoveContext ctx) {
      ST res = templates.getInstanceOf("listRemove");
      res.add("id", ctx.ID().getText());
      res.add("value", visit(ctx.expr()));
      return res;
   }


   //TODO
    @Override
    public ST visitTypeConversionExpr(GrammarIMLParser.TypeConversionExprContext ctx) {
        
        ST res = templates.getInstanceOf("typeConversion");
        String Type = ctx.TYPE().getText();

        switch (Type) {
            case "string":
                res.add("type", "MorphologyWithImageJ.ToString");
                break;
            case "number":
                res.add("type", "MorphologyWithImageJ.toNumber");
                break;
            case "percentage":
                res.add("type", "MorphologyWithImageJ.toDouble");
                break;
            case "boolean":
                res.add("type", "MorphologyWithImageJ.toBoolean");
                break;
            case "image":
                res.add("type", "MorphologyWithImageJ.toImagePlus");
                break;
            default:
                throw new AssertionError();
        }

        res.add("expr", visit(ctx.expr()));
        return res;
    }

    // Done
   @Override public ST visitNumberExpr(GrammarIMLParser.NumberExprContext ctx) {
      ST res = templates.getInstanceOf("literal");
      res.add("value", ctx.NUMBER().getText());
      return res;
   }

   // Done
    @Override
    public ST visitScalingOperation(GrammarIMLParser.ScalingOperationContext ctx) {
        
        ST res = templates.getInstanceOf("Scale");

        switch (ctx.op.getText()) {
            case "-*":
                res.add("method", "adjustHorizontalScale");
                break;
            case "|*":
                res.add("method", "adjustVerticalScale");
                break;
            case "+*":
                res.add("method", "adjustVerticalAndHorizontalScale");
                break;
            default:
                throw new AssertionError();
        }
        
        res.add("left", visit(ctx.expr(0)).render());
        res.add("right", visit(ctx.expr(1)).render());
        return res;
    }

    // Done
    @Override
    public ST visitParenExpr(GrammarIMLParser.ParenExprContext ctx) {
        ST res = templates.getInstanceOf("ParenExpr");
        res.add("expr", visit(ctx.expr()));
        return res;
    }

    // Done
    @Override
    public ST visitPixelMulDiv(GrammarIMLParser.PixelMulDivContext ctx) {
        ST res = templates.getInstanceOf("PixelMulDiv");
    
        String method = switch (ctx.op.getText()) {
            case ".*" -> "multiply";
            case "./" -> "divide";
            default -> throw new AssertionError();
        };
    
        String leftStr = visit(ctx.expr(0)).render();
        String rightStr = visit(ctx.expr(1)).render();
    
        boolean leftIsScalar = leftStr.matches("\\d+(\\.\\d+)?");
        boolean rightIsScalar = rightStr.matches("\\d+(\\.\\d+)?");
    
        res.add("method", method);
    
        if (leftIsScalar || rightIsScalar) {
            // Garante que o escalar está no primeiro argumento
            String scalar = leftIsScalar ? leftStr : rightStr;
            String image = leftIsScalar ? rightStr : leftStr;
            res.add("left", scalar);
            res.add("right", image);
        } else {
            // Nenhum é escalar — usa ordem original
            res.add("left", leftStr);
            res.add("right", rightStr);
        }
    
        return res;
    }
    
    // Done
    @Override
    public ST visitPixelAddSub(GrammarIMLParser.PixelAddSubContext ctx) {
        ST res = templates.getInstanceOf("PixelAddSub");
    
        String method = switch (ctx.op.getText()) {
            case ".+" -> "add";
            case ".-" -> "sub";
            default -> throw new AssertionError();
        };
    
        String leftStr = visit(ctx.expr(0)).render();
        String rightStr = visit(ctx.expr(1)).render();
    
        boolean leftIsScalar = leftStr.matches("\\d+(\\.\\d+)?");
        boolean rightIsScalar = rightStr.matches("\\d+(\\.\\d+)?");
    
        res.add("method", method);
    
        if (leftIsScalar || rightIsScalar) {
            String scalar = leftIsScalar ? leftStr : rightStr;
            String image = leftIsScalar ? rightStr : leftStr;
            res.add("left", scalar);
            res.add("right", image);
        } else {
            res.add("left", leftStr);
            res.add("right", rightStr);
        }
    
        return res;
    }


    //Done
   @Override
   public ST visitStringExpr(GrammarIMLParser.StringExprContext ctx) {
       ST res = templates.getInstanceOf("literal");
       res.add("value", ctx.STRING().getText());
       return res;
   }

   // Done
   @Override public ST visitMultDivArithmetic(GrammarIMLParser.MultDivArithmeticContext ctx) {
        ST res = templates.getInstanceOf("MultDivArithmetic");
        
        res.add("left", visit(ctx.expr(0)).render());
        res.add("right", visit(ctx.expr(1)).render());

        switch (ctx.op.getText()) {
            case "*":
                res.add("op", "*");
                break;
            case "/":
                res.add("op", "/");
                break;
            default:
                throw new AssertionError();
        }
        // Só adiciona parênteses se algum dos lados for uma expressão composta (opcional)
        return res;
   }
   
   //Done
    @Override
    public ST visitMorphChainExpr(GrammarIMLParser.MorphChainExprContext ctx) {
        String left = visit(ctx.expr(0)).render();
        String op = ctx.morphOp().getText().replace(" ", "");
        String right = visit(ctx.expr(1)).render();

        String method;
        switch (op) {
            case "erode": method = "erode"; break;
            case "dilate": method = "dilate"; break;
            case "open": method = "open"; break;
            case "close": method = "close"; break;
            case "tophat": method = "topHat"; break;
            case "blackhat": method = "blackHat"; break;
            default: method = op;
        }

        ST st;
        
        st = new ST("MorphologyWithImageJ.<method>(<left>, <right>)");
        st.add("method", method);
        st.add("left", left);
        st.add("right", right);
        
        return st;
    }

    //Done
   @Override public ST visitLogicalOperation(GrammarIMLParser.LogicalOperationContext ctx) {
      ST res = templates.getInstanceOf("binaryExpr");

    switch (ctx.op.getText()) {
        case "and":
            res.add("op", "&&");
            break;
        case "or":
            res.add("op", "||");
            break;
        default:
            throw new AssertionError();
    }   

      res.add("left", visit(ctx.expr(0)));
      res.add("right", visit(ctx.expr(1)));
      return res;
   }

    // Done
    @Override
    public ST visitFlipOperation(GrammarIMLParser.FlipOperationContext ctx) {
        ST res = templates.getInstanceOf("FlipExpr");
    
        switch (ctx.op.getText()) {
            case "-":
                res.add("method", "FlipHorizontal");
                break;
            case "|":
                res.add("method", "FlipVertical");
                break;
            case "+":
                res.add("method", "FlipVerticalAndHorizontal");
                break;
            default:
                throw new AssertionError();
        }
    
        res.add("expr", visit(ctx.expr()));

        return res;
    }

    //Done
    @Override
    public ST visitPropertyExpr(GrammarIMLParser.PropertyExprContext ctx) {
        ST st = templates.getInstanceOf("propertyAccessExpr");

        // Pega o primeiro token da regra: columns, rows ou length
        st.add("property", visit(ctx.propertyAccess()));

        return st;
    }

    //Done
   @Override public ST visitListIndexExpr(GrammarIMLParser.ListIndexExprContext ctx) {
      ST res = templates.getInstanceOf("listAccess");
      res.add("list", visit(ctx.expr(0)));
      res.add("index", visit(ctx.expr(1)));
      return res;
   }


    @Override
    public ST visitListExpr(GrammarIMLParser.ListExprContext ctx) {
        GrammarIMLParser.ListInitializerContext initCtx = ctx.listInitializer();
        if (initCtx instanceof GrammarIMLParser.PopulatedListContext) {
            GrammarIMLParser.PopulatedListContext popCtx = (GrammarIMLParser.PopulatedListContext) initCtx;
            List<String> values = new ArrayList<>();
            for (GrammarIMLParser.ExprContext exprCtx : popCtx.expr()) {
                values.add(visit(exprCtx).render());
            }
            ST st = new ST("Arrays.asList(<values; separator=\", \">)");
            st.add("values", values);
            return st;
        } else {
            // lista vazia
            return new ST("Arrays.asList()");
        }
    }

    // Done
    @Override
    public ST visitAddSubArithmetic(GrammarIMLParser.AddSubArithmeticContext ctx) {
        
        ST res = templates.getInstanceOf("AddSubArithmetic");
        
        res.add("left", visit(ctx.expr(0)).render());
        res.add("right", visit(ctx.expr(1)).render());

        switch (ctx.op.getText()) {
            case "+":
                res.add("op", "+");
                break;
            case "-":
                res.add("op", "-");
                break;
            default:
                throw new AssertionError();
        }
        // Só adiciona parênteses se algum dos lados for uma expressão composta (opcional)
        return res;
    }

   @Override
   public ST visitComparison(GrammarIMLParser.ComparisonContext ctx) {
       ST res = templates.getInstanceOf("binaryExpr");
       res.add("left", visit(ctx.expr(0)));
       res.add("op", ctx.op.getText());
       res.add("right", visit(ctx.expr(1)));
       return res;
   }

    // Done
    @Override
    public ST visitAllPixelExpr(GrammarIMLParser.AllPixelExprContext ctx) {
        ST res = templates.getInstanceOf("AllPixelExpr");

        res.add("id", ctx.ID().getText());
        res.add("expr", visit(ctx.pixelComparison()));

        String op = (ctx.pixelComparison().getChild(0).getText());
        switch (op) {
            case ".>": 
                res.add("op", "AllPixelgreaterThan"); 
                break;
            case ".<": 
                res.add("op", "AllPixellessThan");
                break;
            case ".==": 
                res.add("op", "AllPixelequalTo");
                break;
            case ".!=": 
                res.add("op", "AllPixeldifferentThan");
                break;
            case ".<=": 
                res.add("op", "AllPixellessOrEqualThan");
                break;
            case ".>=": 
                res.add("op", "AllPixelgreaterOrEqualThan");
                break;
        }

        return res;
    }

    //Done
   @Override
   public ST visitBooleanExpr(GrammarIMLParser.BooleanExprContext ctx) {
       ST res = templates.getInstanceOf("literal");
       res.add("value", ctx.BOOLEAN().getText());
       return res;
   }

   //Done
   @Override public ST visitReadExpr(GrammarIMLParser.ReadExprContext ctx) {
      return visit(ctx.readStmt()); // Delega para visitReadStmt
   }

   //Done
   @Override public ST visitCountPixelExpr(GrammarIMLParser.CountPixelExprContext ctx) {
      ST res = templates.getInstanceOf("countPixel");
      res.add("value", visit(ctx.expr()));
      res.add("image", ctx.ID().getText());
      return res;
   }

   //Done
   @Override
   public ST visitUnaryOperation(GrammarIMLParser.UnaryOperationContext ctx) {
       ST res = templates.getInstanceOf("unaryExpr");
       res.add("op", "InverseOf");
       res.add("expr", visit(ctx.expr()));
       return res;
   }

    //Done
    @Override
    public ST visitAnyPixelExpr(GrammarIMLParser.AnyPixelExprContext ctx) {
        
        ST res = templates.getInstanceOf("AnyPixelExpr");

        res.add("id", ctx.ID().getText());
        res.add("expr", visit(ctx.pixelComparison()));

        String op = (ctx.pixelComparison().getChild(0).getText());
        switch (op) {
            case ".>": 
                res.add("op", "AnyPixelgreaterThan"); 
                break;
            case ".<": 
                res.add("op", "AnyPixellessThan");
                break;
            case ".==": 
                res.add("op", "AnyPixelequalTo");
                break;
            case ".!=": 
                res.add("op", "AnyPixeldifferentThan");
                break;
            case ".<=": 
                res.add("op", "AnyPixellessOrEqualThan");
                break;
            case ".>=": 
                res.add("op", "AnyPixelgreaterOrEqualThan");
                break;
        }

        return res;
    }

    //Done
   @Override public ST visitNotExpr(GrammarIMLParser.NotExprContext ctx) {
      ST res = templates.getInstanceOf("NotExpr");
      res.add("op", "not");
      res.add("expr", visit(ctx.expr()));
      return res;
   }

   // Done
   @Override public ST visitMorphOperationParenExpr(GrammarIMLParser.MorphOperationParenExprContext ctx) {
      ST res = templates.getInstanceOf("morphOpChain");
      res.add("left", visit(ctx.morphOperation().expr(0)));
      res.add("op", ctx.morphOperation().morphOp().getText());
      res.add("right", visit(ctx.morphOperation().expr(1)));
      return res;
   }


   // Done
   @Override
   public ST visitPercentExpr(GrammarIMLParser.PercentExprContext ctx) {
       ST res = templates.getInstanceOf("literal");
       // Converte "30%" → 0.3 para Java
       String raw = ctx.PERCENT().getText().replace("%", "");
       double value = Double.parseDouble(raw) / 100.0;
       res.add("value", value);
       return res;
   }

   //Done
   @Override
   public ST visitIdExpr(GrammarIMLParser.IdExprContext ctx) {
       ST res = templates.getInstanceOf("identifier");
       res.add("name", ctx.ID().getText());
       return res;
   }

   //Done
   @Override
   public ST visitPixelComparison(GrammarIMLParser.PixelComparisonContext ctx) {
       ST res = templates.getInstanceOf("pixelComparison");

       res.add("expr", visit(ctx.expr()));
       return res;
   }


    @Override
    public ST visitPropertyAccess(GrammarIMLParser.PropertyAccessContext ctx) {
        ST res = templates.getInstanceOf("propertyAccess");

        String property = ctx.getChild(0).getText(); // "columns", "rows" ou "length"
        String id = ctx.getChild(2).getText();       // "i0", "i1", etc.

        res.add("property", property);
        res.add("id", id);
        return res;
    }



    //Done
   @Override
   public ST visitArgs(GrammarIMLParser.ArgsContext ctx) {
       ST res = templates.getInstanceOf("args");
       for (var arg : ctx.expr()) {
           res.add("args", visit(arg));
       }
       return res;
   }

    //Done (Unecessary ..)
   @Override
   public ST visitMorphOp(GrammarIMLParser.MorphOpContext ctx) {
       ST res = templates.getInstanceOf("literal");
       res.add("value", ctx.getText());
       return res;
   }

    //Done
   @Override
   public ST visitMorphOperation(GrammarIMLParser.MorphOperationContext ctx) {
       ST res = templates.getInstanceOf("morphOpChain");
       res.add("left", visit(ctx.expr(0)));
       res.add("op", ctx.morphOp().getText());
       res.add("right", visit(ctx.expr(1)));
       return res;
   }

}
