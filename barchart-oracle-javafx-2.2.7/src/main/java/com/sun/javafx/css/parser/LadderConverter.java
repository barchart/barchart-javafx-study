/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ import com.sun.javafx.Utils;
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.paint.Stop;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class LadderConverter extends StyleConverter<ParsedValue[], Color>
/*    */ {
/*    */   public static LadderConverter getInstance()
/*    */   {
/* 42 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Color convert(ParsedValue<ParsedValue[], Color> paramParsedValue, Font paramFont)
/*    */   {
/* 51 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 52 */     Color localColor = (Color)arrayOfParsedValue[0].convert(paramFont);
/* 53 */     Stop[] arrayOfStop = new Stop[arrayOfParsedValue.length - 1];
/* 54 */     for (int i = 1; i < arrayOfParsedValue.length; i++) {
/* 55 */       arrayOfStop[(i - 1)] = ((Stop)arrayOfParsedValue[i].convert(paramFont));
/*    */     }
/* 57 */     return Utils.ladder(localColor, arrayOfStop);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 62 */     return "LadderConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 38 */     static LadderConverter INSTANCE = new LadderConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.LadderConverter
 * JD-Core Version:    0.6.2
 */