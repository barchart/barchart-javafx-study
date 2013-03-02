/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import java.io.PrintStream;
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class ColorConverter extends StyleConverter<String, Color>
/*    */ {
/*    */   public static ColorConverter getInstance()
/*    */   {
/* 41 */     return Holder.COLOR_INSTANCE;
/*    */   }
/*    */ 
/*    */   public Color convert(ParsedValue<String, Color> paramParsedValue, Font paramFont)
/*    */   {
/* 50 */     String str = (String)paramParsedValue.getValue();
/* 51 */     if ((str == null) || (str.isEmpty()) || ("null".equals(str)))
/* 52 */       return null;
/*    */     try
/*    */     {
/* 55 */       return Color.web(str);
/*    */     }
/*    */     catch (IllegalArgumentException localIllegalArgumentException) {
/* 58 */       System.err.println("not a color: " + paramParsedValue);
/* 59 */     }return Color.BLACK;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 65 */     return "ColorConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 36 */     static ColorConverter COLOR_INSTANCE = new ColorConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.ColorConverter
 * JD-Core Version:    0.6.2
 */