/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class DefaultStringConverter extends StringConverter<String>
/*    */ {
/*    */   public String toString(String paramString)
/*    */   {
/* 37 */     return paramString != null ? paramString : "";
/*    */   }
/*    */ 
/*    */   public String fromString(String paramString)
/*    */   {
/* 42 */     return paramString;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.DefaultStringConverter
 * JD-Core Version:    0.6.2
 */