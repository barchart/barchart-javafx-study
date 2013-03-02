/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class DoubleStringConverter extends StringConverter<Double>
/*    */ {
/*    */   public Double fromString(String paramString)
/*    */   {
/* 39 */     if (paramString == null) {
/* 40 */       return null;
/*    */     }
/*    */ 
/* 43 */     paramString = paramString.trim();
/*    */ 
/* 45 */     if (paramString.length() < 1) {
/* 46 */       return null;
/*    */     }
/*    */ 
/* 49 */     return Double.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Double paramDouble)
/*    */   {
/* 55 */     if (paramDouble == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Double.toString(paramDouble.doubleValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.DoubleStringConverter
 * JD-Core Version:    0.6.2
 */