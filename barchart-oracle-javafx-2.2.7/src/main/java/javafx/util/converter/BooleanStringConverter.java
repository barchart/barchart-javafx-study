/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class BooleanStringConverter extends StringConverter<Boolean>
/*    */ {
/*    */   public Boolean fromString(String paramString)
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
/* 49 */     return Boolean.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Boolean paramBoolean)
/*    */   {
/* 55 */     if (paramBoolean == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return paramBoolean.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.BooleanStringConverter
 * JD-Core Version:    0.6.2
 */