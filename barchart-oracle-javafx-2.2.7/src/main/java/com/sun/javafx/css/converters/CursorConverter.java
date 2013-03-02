/*    */ package com.sun.javafx.css.converters;
/*    */ 
/*    */ import com.sun.javafx.css.ParsedValue;
/*    */ import com.sun.javafx.css.StyleConverter;
/*    */ import javafx.scene.Cursor;
/*    */ import javafx.scene.text.Font;
/*    */ 
/*    */ public final class CursorConverter extends StyleConverter<String, Cursor>
/*    */ {
/*    */   public static CursorConverter getInstance()
/*    */   {
/* 41 */     return Holder.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Cursor convert(ParsedValue<String, Cursor> paramParsedValue, Font paramFont)
/*    */   {
/* 51 */     String str = (String)paramParsedValue.getValue();
/* 52 */     if (str.startsWith("Cursor.")) {
/* 53 */       str = str.substring("Cursor.".length());
/*    */     }
/* 55 */     return Cursor.cursor(str);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 60 */     return "CursorConverter";
/*    */   }
/*    */ 
/*    */   private static class Holder
/*    */   {
/* 37 */     static CursorConverter INSTANCE = new CursorConverter(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.CursorConverter
 * JD-Core Version:    0.6.2
 */