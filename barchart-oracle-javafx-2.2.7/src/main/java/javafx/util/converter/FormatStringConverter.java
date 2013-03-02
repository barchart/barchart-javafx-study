/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.text.Format;
/*    */ import java.text.ParseException;
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class FormatStringConverter<T> extends StringConverter<T>
/*    */ {
/*    */   final Format format;
/*    */ 
/*    */   public FormatStringConverter(Format paramFormat)
/*    */   {
/* 47 */     this.format = paramFormat;
/*    */   }
/*    */ 
/*    */   public T fromString(String paramString)
/*    */   {
/*    */     try
/*    */     {
/* 56 */       if (paramString == null) {
/* 57 */         return null;
/*    */       }
/*    */ 
/* 60 */       paramString = paramString.trim();
/*    */ 
/* 62 */       if (paramString.length() < 1) {
/* 63 */         return null;
/*    */       }
/*    */ 
/* 67 */       Format localFormat = getFormat();
/*    */ 
/* 71 */       return localFormat.parseObject(paramString);
/*    */     } catch (ParseException localParseException) {
/* 73 */       throw new RuntimeException(localParseException);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String toString(T paramT)
/*    */   {
/* 80 */     if (paramT == null) {
/* 81 */       return "";
/*    */     }
/*    */ 
/* 85 */     Format localFormat = getFormat();
/*    */ 
/* 88 */     return localFormat.format(paramT);
/*    */   }
/*    */ 
/*    */   protected Format getFormat()
/*    */   {
/* 96 */     return this.format;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.FormatStringConverter
 * JD-Core Version:    0.6.2
 */