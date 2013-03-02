/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class FloatStringConverter extends StringConverter<Float>
/*    */ {
/*    */   public Float fromString(String paramString)
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
/* 49 */     return Float.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Float paramFloat)
/*    */   {
/* 55 */     if (paramFloat == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Float.toString(paramFloat.floatValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.FloatStringConverter
 * JD-Core Version:    0.6.2
 */