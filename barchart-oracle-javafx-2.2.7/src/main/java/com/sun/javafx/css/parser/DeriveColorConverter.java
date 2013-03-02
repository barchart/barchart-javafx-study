/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ import com.sun.javafx.Utils;
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.Size;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class DeriveColorConverter extends StyleConverter<ParsedValue[], Color>
/*    */ {
/*    */   public static DeriveColorConverter getInstance()
/*    */   {
/* 45 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Color convert(ParsedValue<ParsedValue[], Color> paramParsedValue, Font paramFont)
/*    */   {
/* 54 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 55 */     Color localColor = (Color)arrayOfParsedValue[0].convert(paramFont);
/* 56 */     Size localSize = (Size)arrayOfParsedValue[1].convert(paramFont);
/* 57 */     return Utils.deriveColor(localColor, localSize.pixels(paramFont));
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 62 */     return "DeriveColorConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 41 */     static DeriveColorConverter INSTANCE = new DeriveColorConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.DeriveColorConverter
 * JD-Core Version:    0.6.2
 */