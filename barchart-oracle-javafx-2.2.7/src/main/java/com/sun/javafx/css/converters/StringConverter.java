/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.Utils;
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class StringConverter extends StyleConverter<String, String>
/*    */ {
/*    */   public static StringConverter getInstance()
/*    */   {
/* 45 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public String convert(ParsedValue<String, String> paramParsedValue, Font paramFont)
/*    */   {
/* 54 */     String str = (String)paramParsedValue.getValue();
/* 55 */     if (str == null) {
/* 56 */       return null;
/*    */     }
/* 58 */     return Utils.convertUnicode(str);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 63 */     return "StringConverter";
/*    */   }
/*    */ 
/*    */   public static final class SequenceConverter extends StyleConverter<ParsedValue<String, String>[], String[]>
/*    */   {
/*    */     public static SequenceConverter getInstance()
/*    */     {
/* 70 */       return StringConverter.Holder.SEQUENCE_INSTANCE;
/*    */     }
/*    */ 
/*    */     public String[] convert(ParsedValue<ParsedValue<String, String>[], String[]> paramParsedValue, Font paramFont)
/*    */     {
/* 79 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 80 */       String[] arrayOfString = new String[arrayOfParsedValue.length];
/* 81 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/* 82 */         arrayOfString[i] = StringConverter.getInstance().convert(arrayOfParsedValue[i], paramFont);
/*    */       }
/* 84 */       return arrayOfString;
/*    */     }
/*    */ 
/*    */     public String toString()
/*    */     {
/* 89 */       return "String.SequenceConverter";
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 40 */     static StringConverter INSTANCE = new StringConverter(null);
/* 41 */     static StringConverter.SequenceConverter SEQUENCE_INSTANCE = new StringConverter.SequenceConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.StringConverter
 * JD-Core Version:    0.6.2
 */