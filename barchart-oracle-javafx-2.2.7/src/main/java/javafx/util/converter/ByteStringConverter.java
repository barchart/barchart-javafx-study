/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class ByteStringConverter extends StringConverter<Byte>
/*    */ {
/*    */   public Byte fromString(String paramString)
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
/* 49 */     return Byte.valueOf(paramString);
/*    */   }
/*    */ 
/*    */   public String toString(Byte paramByte)
/*    */   {
/* 55 */     if (paramByte == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return Byte.toString(paramByte.byteValue());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.ByteStringConverter
 * JD-Core Version:    0.6.2
 */