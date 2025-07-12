import java.util.*;
import org.stringtemplate.v4.*;

@SuppressWarnings("CheckReturnValue")

public class CSVC extends CSVBaseVisitor<String> {

   private STGroup templates = new STGroupFile("html.stg");

   @Override public String visitFile(CSVParser.FileContext ctx) {
      
      ST res = templates.getInstanceOf("module");
      res.add("header", visit(ctx.header()).render());

      for (CSVParser.RowContext row : ctx.row()) {
         res.add("row", visit(row).render());
      }
      
      return res;
   }

   @Override public String visitCabecalho(CSVParser.CabecalhoContext ctx) {
      String res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public String visitRow(CSVParser.RowContext ctx) {
      String res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public String visitCvalue(CSVParser.CvalueContext ctx) {
      String res = null;
      return visitChildren(ctx);
      //return res;
   }

   @Override public String visitRvalue(CSVParser.RvalueContext ctx) {
      String res = null;
      return visitChildren(ctx);
      //return res;
   }
}
