/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.Size;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.geometry.Insets;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class InsetsConverter extends StyleConverter<ParsedValue<?, Size>[], Insets>
/*    */ {
/*    */   public static InsetsConverter getInstance()
/*    */   {
/* 50 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Insets convert(ParsedValue<ParsedValue<?, Size>[], Insets> paramParsedValue, Font paramFont)
/*    */   {
/* 59 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 60 */     double d1 = ((Size)arrayOfParsedValue[0].convert(paramFont)).pixels(paramFont);
/* 61 */     double d2 = arrayOfParsedValue.length > 1 ? ((Size)arrayOfParsedValue[1].convert(paramFont)).pixels(paramFont) : d1;
/* 62 */     double d3 = arrayOfParsedValue.length > 2 ? ((Size)arrayOfParsedValue[2].convert(paramFont)).pixels(paramFont) : d1;
/* 63 */     double d4 = arrayOfParsedValue.length > 3 ? ((Size)arrayOfParsedValue[3].convert(paramFont)).pixels(paramFont) : d2;
/* 64 */     return new Insets(d1, d2, d3, d4);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 69 */     return "InsetsConverter";
/*    */   }
/*    */ 
/*    */   public static final class SequenceConverter extends StyleConverter<ParsedValue<ParsedValue<?, Size>[], Insets>[], Insets[]>
/*    */   {
/*    */     public static SequenceConverter getInstance()
/*    */     {
/* 78 */       return InsetsConverter.Holder.SEQUENCE_INSTANCE;
/*    */     }
/*    */ 
/*    */     public Insets[] convert(ParsedValue<ParsedValue<ParsedValue<?, Size>[], Insets>[], Insets[]> paramParsedValue, Font paramFont)
/*    */     {
/* 87 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 88 */       Insets[] arrayOfInsets = new Insets[arrayOfParsedValue.length];
/* 89 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/* 90 */         arrayOfInsets[i] = InsetsConverter.getInstance().convert(arrayOfParsedValue[i], paramFont);
/*    */       }
/* 92 */       return arrayOfInsets;
/*    */     }
/*    */ 
/*    */     public String toString()
/*    */     {
/* 97 */       return "InsetsSequenceConverter";
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 45 */     static InsetsConverter INSTANCE = new InsetsConverter(null);
/* 46 */     static InsetsConverter.SequenceConverter SEQUENCE_INSTANCE = new InsetsConverter.SequenceConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.InsetsConverter
 * JD-Core Version:    0.6.2
 */