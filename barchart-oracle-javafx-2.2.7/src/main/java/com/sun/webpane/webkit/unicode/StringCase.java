/*    */ package com.sun.webpane.webkit.unicode;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StringCase
/*    */ {
/*    */   public static String toLowerCase(String paramString)
/*    */   {
/* 14 */     return paramString.toLowerCase(Locale.ROOT);
/*    */   }
/*    */ 
/*    */   public static String toUpperCase(String paramString) {
/* 18 */     return paramString.toUpperCase(Locale.ROOT);
/*    */   }
/*    */ 
/*    */   public static String foldCase(String paramString) {
/* 22 */     return paramString.toUpperCase(Locale.ROOT).toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.unicode.StringCase
 * JD-Core Version:    0.6.2
 */