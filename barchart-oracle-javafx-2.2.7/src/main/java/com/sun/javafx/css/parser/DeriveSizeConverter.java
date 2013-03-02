/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.Size;
/*    */ import com.sun.javafx.css.SizeUnits;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class DeriveSizeConverter extends StyleConverter<ParsedValue<Size, Size>[], Size>
/*    */ {
/*    */   public static DeriveSizeConverter getInstance()
/*    */   {
/* 46 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Size convert(ParsedValue<ParsedValue<Size, Size>[], Size> paramParsedValue, Font paramFont)
/*    */   {
/* 55 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 56 */     double d1 = ((Size)arrayOfParsedValue[0].convert(paramFont)).pixels(paramFont);
/* 57 */     double d2 = ((Size)arrayOfParsedValue[1].convert(paramFont)).pixels(paramFont);
/* 58 */     return new Size(d1 + d2, SizeUnits.PX);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 63 */     return "DeriveSizeConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 42 */     static DeriveSizeConverter INSTANCE = new DeriveSizeConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.DeriveSizeConverter
 * JD-Core Version:    0.6.2
 */