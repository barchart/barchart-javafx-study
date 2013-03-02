/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class LongStringConverter extends StringConverter<Long>
/*    */ {
/*    */   public Long fromString(String paramString)
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
/* 49 */     return Long.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Long paramLong)
/*    */   {
/* 55 */     if (paramLong == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Long.toString(paramLong.longValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.LongStringConverter
 * JD-Core Version:    0.6.2
 */