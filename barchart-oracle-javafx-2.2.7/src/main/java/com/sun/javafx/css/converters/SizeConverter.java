/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.Size;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class SizeConverter extends StyleConverter<ParsedValue<?, Size>, Double>
/*    */ {
/*    */   public static SizeConverter getInstance()
/*    */   {
/* 43 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Double convert(ParsedValue<ParsedValue<?, Size>, Double> paramParsedValue, Font paramFont)
/*    */   {
/* 52 */     ParsedValue localParsedValue = (ParsedValue)paramParsedValue.getValue();
/* 53 */     return Double.valueOf(((Size)localParsedValue.convert(paramFont)).pixels(paramFont));
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 58 */     return "SizeConverter";
/*    */   }
/*    */ 
/*    */   public static final class SequenceConverter extends StyleConverter<ParsedValue<?, Size>[], Double[]>
/*    */   {
/*    */     public static SequenceConverter getInstance()
/*    */     {
/* 67 */       return SizeConverter.Holder.SEQUENCE_INSTANCE;
/*    */     }
/*    */ 
/*    */     public Double[] convert(ParsedValue<ParsedValue<?, Size>[], Double[]> paramParsedValue, Font paramFont)
/*    */     {
/* 76 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 77 */       Double[] arrayOfDouble = new Double[arrayOfParsedValue.length];
/* 78 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/* 79 */         arrayOfDouble[i] = Double.valueOf(((Size)arrayOfParsedValue[i].convert(paramFont)).pixels(paramFont));
/*    */       }
/* 81 */       return arrayOfDouble;
/*    */     }
/*    */ 
/*    */     public String toString()
/*    */     {
/* 86 */       return "Size.SequenceConverter";
/*    */     }
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 38 */     static SizeConverter INSTANCE = new SizeConverter(null);
/* 39 */     static SizeConverter.SequenceConverter SEQUENCE_INSTANCE = new SizeConverter.SequenceConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.SizeConverter
 * JD-Core Version:    0.6.2
 */