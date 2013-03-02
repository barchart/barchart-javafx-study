/*    */ package com.sun.webpane.webkit.network;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.net.URLStreamHandler;
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class URLs
/*    */ {
/* 28 */   private static final Map<String, URLStreamHandler> handlerMap = Collections.unmodifiableMap(localHashMap);
/*    */ 
/*    */   private URLs()
/*    */   {
/* 36 */     throw new AssertionError();
/*    */   }
/*    */ 
/*    */   public static URL newURL(String paramString)
/*    */     throws MalformedURLException
/*    */   {
/* 49 */     return newURL(null, paramString);
/*    */   }
/*    */ 
/*    */   public static URL newURL(URL paramURL, String paramString)
/*    */     throws MalformedURLException
/*    */   {
/*    */     try
/*    */     {
/* 67 */       return new URL(paramURL, paramString);
/*    */     }
/*    */     catch (MalformedURLException localMalformedURLException) {
/* 70 */       URLStreamHandler localURLStreamHandler = null;
/* 71 */       int i = paramString.indexOf(':');
/* 72 */       if (i != -1) {
/* 73 */         localURLStreamHandler = (URLStreamHandler)handlerMap.get(paramString.substring(0, i).toLowerCase());
/*    */       }
/*    */ 
/* 76 */       if (localURLStreamHandler == null) {
/* 77 */         throw localMalformedURLException;
/*    */       }
/* 79 */       return new URL(paramURL, paramString, localURLStreamHandler);
/*    */     }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 24 */     HashMap localHashMap = new HashMap(2);
/*    */ 
/* 26 */     localHashMap.put("about", new com.sun.webpane.webkit.network.about.Handler());
/* 27 */     localHashMap.put("data", new com.sun.webpane.webkit.network.data.Handler());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.URLs
 * JD-Core Version:    0.6.2
 */