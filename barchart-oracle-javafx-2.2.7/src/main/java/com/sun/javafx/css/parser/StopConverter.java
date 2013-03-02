/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.Size;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.paint.Stop;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class StopConverter extends StyleConverter<ParsedValue[], Stop>
/*    */ {
/*    */   public static StopConverter getInstance()
/*    */   {
/* 46 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Stop convert(ParsedValue<ParsedValue[], Stop> paramParsedValue, Font paramFont)
/*    */   {
/* 55 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 56 */     Double localDouble = Double.valueOf(((Size)arrayOfParsedValue[0].convert(paramFont)).pixels(paramFont));
/* 57 */     Color localColor = (Color)arrayOfParsedValue[1].convert(paramFont);
/* 58 */     return new Stop(localDouble.doubleValue(), localColor);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 63 */     return "StopConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 42 */     static StopConverter INSTANCE = new StopConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.StopConverter
 * JD-Core Version:    0.6.2
 */