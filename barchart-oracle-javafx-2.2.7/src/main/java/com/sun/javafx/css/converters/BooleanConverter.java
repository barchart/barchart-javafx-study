/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class BooleanConverter extends StyleConverter<String, Boolean>
/*    */ {
/*    */   public static BooleanConverter getInstance()
/*    */   {
/* 40 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Boolean convert(ParsedValue<String, Boolean> paramParsedValue, Font paramFont)
/*    */   {
/* 49 */     String str = (String)paramParsedValue.getValue();
/* 50 */     return Boolean.valueOf(str);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 55 */     return "BooleanConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 36 */     static BooleanConverter INSTANCE = new BooleanConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.BooleanConverter
 * JD-Core Version:    0.6.2
 */