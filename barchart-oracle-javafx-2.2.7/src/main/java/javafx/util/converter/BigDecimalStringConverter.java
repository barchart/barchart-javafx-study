/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class BigDecimalStringConverter extends StringConverter<BigDecimal>
/*    */ {
/*    */   public BigDecimal fromString(String paramString)
/*    */   {
/* 40 */     if (paramString == null) {
/* 41 */       return null;
/*    */     }
/*    */ 
/* 44 */     paramString = paramString.trim();
/*    */ 
/* 46 */     if (paramString.length() < 1) {
/* 47 */       return null;
/*    */     }
/*    */ 
/* 50 */     return new BigDecimal(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(BigDecimal paramBigDecimal)
/*    */   {
/* 56 */     if (paramBigDecimal == null) {
/* 57 */       return "";
/*    */     }
/*    */ 
/* 60 */     return paramBigDecimal.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.BigDecimalStringConverter
 * JD-Core Version:    0.6.2
 */