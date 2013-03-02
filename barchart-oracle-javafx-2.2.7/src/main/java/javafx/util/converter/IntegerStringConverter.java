/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class IntegerStringConverter extends StringConverter<Integer>
/*    */ {
/*    */   public Integer fromString(String paramString)
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
/* 49 */     return Integer.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Integer paramInteger)
/*    */   {
/* 55 */     if (paramInteger == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Integer.toString(paramInteger.intValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.IntegerStringConverter
 * JD-Core Version:    0.6.2
 */