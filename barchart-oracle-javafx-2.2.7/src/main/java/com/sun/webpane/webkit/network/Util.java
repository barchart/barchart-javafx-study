/*    */ package com.sun.webpane.webkit.network;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ 
/*    */ public final class Util
/*    */ {
/*    */   private Util()
/*    */   {
/* 18 */     throw new AssertionError();
/*    */   }
/*    */ 
/*    */   public static String adjustUrlForWebKit(String paramString)
/*    */     throws MalformedURLException
/*    */   {
/* 32 */     if (URLs.newURL(paramString).getProtocol().equals("file"))
/*    */     {
/* 42 */       int i = "file:".length();
/* 43 */       if ((i < paramString.length()) && (paramString.charAt(i) != '/')) {
/* 44 */         paramString = paramString.substring(0, i) + "///" + paramString.substring(i);
/*    */       }
/*    */     }
/* 47 */     return paramString;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.Util
 * JD-Core Version:    0.6.2
 */