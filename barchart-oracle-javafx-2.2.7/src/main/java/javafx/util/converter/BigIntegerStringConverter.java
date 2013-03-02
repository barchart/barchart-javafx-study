/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class BigIntegerStringConverter extends StringConverter<BigInteger>
/*    */ {
/*    */   public BigInteger fromString(String paramString)
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
/* 49 */     return new BigInteger(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(BigInteger paramBigInteger)
/*    */   {
/* 55 */     if (paramBigInteger == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return paramBigInteger.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.BigIntegerStringConverter
 * JD-Core Version:    0.6.2
 */