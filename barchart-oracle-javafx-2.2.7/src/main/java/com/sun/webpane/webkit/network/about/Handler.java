/*    */ package com.sun.webpane.webkit.network.about;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class Handler extends URLStreamHandler
/*    */ {
/*    */   protected URLConnection openConnection(URL paramURL)
/*    */   {
/* 18 */     return new AboutURLConnection(paramURL);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.about.Handler
 * JD-Core Version:    0.6.2
 */