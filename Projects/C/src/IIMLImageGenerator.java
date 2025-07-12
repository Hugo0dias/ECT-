import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.io.FileSaver;

@SuppressWarnings("CheckReturnValue")
public class IIMLImageGenerator extends GrammarIIMLBaseVisitor<Object> {

   private Map<String, Double> variaveis = new HashMap<>();
   private Map<String, List<Object>> listas = new HashMap<>();
   Scanner scanner = new Scanner(System.in);
   private FloatProcessor imagem;  // onde será desenhado
   private String outputPath = "output.png"; // caminho para guardar a imagem final



   private Double toDouble(Object value) {
      if (value instanceof Double) {
          return (Double) value;
      }
      throw new RuntimeException("Expected numeric value");
   }

   public void guardarComoPGM(FloatProcessor imagem, String filename) {
      try (PrintWriter out = new PrintWriter(filename)) {
         int width = imagem.getWidth();
         int height = imagem.getHeight();
         out.println("P2");
         out.printf("%d %d\n", width, height);
         out.println("255"); // intensidade máxima

         for (int y = 0; y < height; y++) {
               for (int x = 0; x < width; x++) {
                  float val = imagem.getf(x, y);
                  int gray = Math.round(val * 255); // assume que val ∈ [0,1]
                  out.print(gray + " ");
               }
               out.println();
         }

         System.out.println("Imagem PGM guardada em: " + filename);
      } catch (IOException e) {
         System.err.println("Erro ao guardar imagem PGM: " + e.getMessage());
      }
   }


   @Override public Object visitProgram(GrammarIIMLParser.ProgramContext ctx) {
      System.out.println("VisitProgram");
      Object result = visitChildren(ctx);
   
      if (imagem != null) {
            ImagePlus outputImage = new ImagePlus("IIML Image", imagem);
            FileSaver fs = new FileSaver(outputImage);
            fs.saveAsPng(outputPath);
            guardarComoPGM(imagem, "saida.pgm");
            System.out.println("Imagem guardada em: " + outputPath);
      }
   
      return result;
   }


   @Override public Object visitStatement(GrammarIIMLParser.StatementContext ctx) {
      System.out.println("VisitStatement");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitNumberDeclaration(GrammarIIMLParser.NumberDeclarationContext ctx) {
      System.out.println("VisitNumberDeclaration");
      String varName = ctx.ID().getText();
      Double value = toDouble(visit(ctx.expr()));
      variaveis.put(varName, value);
      return value;
   }

   @Override public Object visitListDeclaration(GrammarIIMLParser.ListDeclarationContext ctx) {
      System.out.println("VisitListDeclaration");
      String varName = ctx.ID().getText();
      Object value = visit(ctx.expr());

      if (value instanceof List) {
         listas.put(varName, (List<Object>) value);
         System.out.printf("Lista %s declarada com %d elementos%n", varName, ((List<Object>) value).size());
      } else {
         throw new RuntimeException("Valor de lista deve ser uma lista");
      }
      return value;
   }

   @Override public Object visitBaseType(GrammarIIMLParser.BaseTypeContext ctx) {
      System.out.println("VisitBaseType");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitForLoop(GrammarIIMLParser.ForLoopContext ctx) {
      System.out.println("VisitForLoop");
      String loopVar = ctx.ID(0).getText();
      String listVar = ctx.ID(1).getText();
      if (!listas.containsKey(listVar)) {
         throw new RuntimeException("Lista '" + listVar + "' não foi declarada.");
      }
      List<Object> list = listas.get(listVar);
      Object previous= null;
      boolean existedAsList = listas.containsKey(loopVar);
      boolean existedAsVar = variaveis.containsKey(loopVar);

      if(existedAsList) {
         previous = listas.get(loopVar);
      } else if (existedAsVar) {
         previous = variaveis.get(loopVar);
      }

      for (Object element : list) {
         // Each element should be a list (because we're iterating over a list of lists)
         if (element instanceof List) {
            // Store the current element as the loop variable
            listas.put(loopVar, (List<Object>) element);
         } else {
            // If it's not a list, treat it as a scalar value
            variaveis.put(loopVar, toDouble(element));
         }
         
         // Execute the body of the loop
         visit(ctx.statement());
      }
   
      // Restore the previous value of the loop variable
      if (existedAsList) {
            listas.put(loopVar, (List<Object>) previous);
      } else if (existedAsVar) {
            variaveis.put(loopVar, (Double) previous);
      } else {
            // Remove the loop variable if it didn't exist before
            listas.remove(loopVar);
            variaveis.remove(loopVar);
      }
   
      return null;
 }

   @Override public Object visitImageDeclaration(GrammarIIMLParser.ImageDeclarationContext ctx) {
      System.out.println("VisitImageDeclaration");
      int width = ((Double) visit(ctx.imageSize().expr(0))).intValue();
      int height = ((Double) visit(ctx.imageSize().expr(1))).intValue();
      float background = ((Double) visit(ctx.expr())).floatValue();

      // Cria imagem com valor de fundo
      imagem = new FloatProcessor(width, height);
      for (int y = 0; y < height; y++) {
         for (int x = 0; x < width; x++) {
            imagem.setf(x, y, background);
         }
      }

      System.out.printf("Imagem %d por %d com cor %f%n", width, height, background);
      return null;
   }

   @Override public Object visitImageSize(GrammarIIMLParser.ImageSizeContext ctx) {
      System.out.println("VisitImageSize");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitPlaceFigure(GrammarIIMLParser.PlaceFigureContext ctx) {
      System.out.println("VisitPlaceFigure");
      GrammarIIMLParser.FigureSizeContext sizeCtx = ctx.figureSize();
      System.out.println("Figura: " + ctx.figureType().getText());


      double x = toDouble(visit(ctx.location().expr(0)));
      double y = toDouble(visit(ctx.location().expr(1)));
      double intensity = toDouble(visit(ctx.expr()));
      imagem.setValue(intensity);

      if (ctx.figureType().CIRCLE() != null && sizeCtx instanceof GrammarIIMLParser.CircleFigSizeContext circleSize) {
         System.out.println("é um circulo");
         double radius = toDouble(visit(circleSize.expr()));
         imagem.fillOval((int)(x - radius), (int)(y - radius), (int)(2 * radius), (int)(2 * radius));

      } else if (sizeCtx instanceof GrammarIIMLParser.OtherFigSizeContext otherSize) {
         System.out.println("é outra forma");
         double width = toDouble(visit(otherSize.expr(0)));
         System.out.println("largura: " + width);
         double height = toDouble(visit(otherSize.expr(1)));
         System.out.println("altura: " + height);

         if (ctx.figureType().RECT() != null) {
               System.out.println("sou um retangulo");
               imagem.fillRect((int)(x - width/2), (int)(y - height/2), (int)width, (int)height);
         }else if (ctx.figureType().CROSS() != null) {
            int cx = (int) x;
            int cy = (int) y;
            int halfW = (int)(width/2);
            int halfH = (int)(height/2);
            
            // Desenha um "X" manualmente
            for (int i = -halfW; i <= halfW; i++) {
                int px1 = cx + i;
                int py1 = cy + (i * halfH) / halfW;
                
                int px2 = cx + i;
                int py2 = cy - (i * halfH) / halfW;
                
                if (px1 >= 0 && px1 < imagem.getWidth() && 
                    py1 >= 0 && py1 < imagem.getHeight()) {
                    imagem.putPixelValue(px1, py1, intensity);
                }
                
                if (px2 >= 0 && px2 < imagem.getWidth() && 
                    py2 >= 0 && py2 < imagem.getHeight()) {
                    imagem.putPixelValue(px2, py2, intensity);
                }
            }
         } 
         else if (ctx.figureType().PLUS() != null) {
            System.out.println("sou um plus (+)");
            int cx = (int) x;
            int cy = (int) y;
            int halfW = (int)(width/2);
            int halfH = (int)(height/2);
            
            // Barra horizontal
            imagem.setRoi(cx - halfW, cy, (int)width, 1);
            imagem.fill();
            
            // Barra vertical
            imagem.setRoi(cx, cy - halfH, 1, (int)height);
            imagem.fill();
            imagem.resetRoi();
         }
      }

      return null;
}

   @Override public Object visitFigureType(GrammarIIMLParser.FigureTypeContext ctx) {
      System.out.println("VisitFigureType");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitCircleFigSize(GrammarIIMLParser.CircleFigSizeContext ctx) {
      System.out.println("VisitCircleFigSize");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitOtherFigSize(GrammarIIMLParser.OtherFigSizeContext ctx) {
      System.out.println("VisitOtherFigSize");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitLocation(GrammarIIMLParser.LocationContext ctx) {
      System.out.println("VisitLocation");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitUnaryExpr(GrammarIIMLParser.UnaryExprContext ctx) {
      System.out.println("VisitUnaryExpr");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitTermExpr(GrammarIIMLParser.TermExprContext ctx) {
      System.out.println("VisitTermExpr");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitAdditionExpr(GrammarIIMLParser.AdditionExprContext ctx) {
      System.out.println("VisitAdditionExpr");
      double left = (Double)visit(ctx.expr(0));
      double right = (Double)visit(ctx.expr(1));
      return ctx.op.getText().equals("+") ? left + right : left - right;
   }

   @Override public Object visitMultiplicationExpr(GrammarIIMLParser.MultiplicationExprContext ctx) {
      System.out.println("VisitMultiplicationExpr");
      double left = (Double)visit(ctx.expr(0));
      double right = (Double)visit(ctx.expr(1));
      return ctx.op.getText().equals("*") ? left * right : left / right;
   }

   @Override public Object visitPrimaryTerm(GrammarIIMLParser.PrimaryTermContext ctx) {
      System.out.println("VisitPrimaryTerm");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitListElementAccessTerm(GrammarIIMLParser.ListElementAccessTermContext ctx) {
      System.out.println("VisitListElementAccessTerm");

      Object base = visit(ctx.term());
      if (!(base instanceof List)) {
          throw new RuntimeException("Tentativa de indexar uma variável que não é uma lista.");
      }
  
      List<Object> list = (List<Object>) base;
      int index = toDouble(visit(ctx.expr())).intValue();
  
      if (index < 0 || index >= list.size()) {
          throw new RuntimeException("Índice fora dos limites da lista.");
      }
  
      return list.get(index);
  }

   @Override public Object visitTypeConversionPrimary(GrammarIIMLParser.TypeConversionPrimaryContext ctx) {
      System.out.println("VisitTypeConversionPrimary");
      return visit(ctx.expr());
   }

   @Override public Object visitParenthesisPrimary(GrammarIIMLParser.ParenthesisPrimaryContext ctx) {
      System.out.println("VisitParenthesisPrimary");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitListLiteralPrimary(GrammarIIMLParser.ListLiteralPrimaryContext ctx) {
      System.out.println("VisitListLiteralPrimary");
      Object res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public Object visitVariablePrimary(GrammarIIMLParser.VariablePrimaryContext ctx) {
      System.out.println("VisitVariablePrimary");
      String var = ctx.ID().getText();
      if( variaveis.containsKey(var) ) {
         return variaveis.get(var);
      } else if (listas.containsKey(var)) {
         return listas.get(var);
      } else {
         throw new RuntimeException("Variável '" + var + "' não foi declarada.");
      }
   }

   @Override public Object visitNumberPrimary(GrammarIIMLParser.NumberPrimaryContext ctx) {
      System.out.println("VisitNumberPrimary");
      return Double.parseDouble(ctx.NUMBER().getText());
   }

   @Override public Object visitReadStringPrimary(GrammarIIMLParser.ReadStringPrimaryContext ctx) {
      System.out.println("VisitReadStringPrimary");
      String message = ctx.STRING().getText().replaceAll("^\"|\"$", ""); // tira as aspas
      System.out.print(message + " ");
      double val = scanner.nextDouble();
      return val;
  }

   @Override public Object visitListLiteral(GrammarIIMLParser.ListLiteralContext ctx) {
      System.out.println("VisitListLiteral");
      List<Object> list = new ArrayList<>();
      for (GrammarIIMLParser.ExprContext exprCtx : ctx.expr()) {
         Object value = visit(exprCtx);
         if (value instanceof Double || value instanceof Integer) {
            // Converte para Double se for Integer
            if (value instanceof Integer) {
               value = ((Integer) value).doubleValue();
            }
            list.add(value);
         }else if(value instanceof List){
            list.add(value);
         }else {
            throw new RuntimeException("Lista deve conter apenas valores numéricos ou sublistas.");
         }
      }
      return list;
   }
}
