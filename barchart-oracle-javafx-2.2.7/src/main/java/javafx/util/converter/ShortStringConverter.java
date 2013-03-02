/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ShortStringConverter extends StringConverter<Short>
/*    */ {
/*    */   public Short fromString(String paramString)
/*    */   {
/* 38 */     if (paramString == null) {
/* 39 */       return null;
/*    */     }
/*    */ 
/* 42 */     paramString = paramString.trim();
/*    */ 
/* 44 */     if (paramString.length() < 1) {
/* 45 */       return null;
/*    */     }
/*    */ 
/* 48 */     return Short.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Short paramShort)
/*    */   {
/* 55 */     if (paramShort == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Short.toString(paramShort.shortValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.ShortStringConverter
 * JD-Core Version:    0.6.2
 */