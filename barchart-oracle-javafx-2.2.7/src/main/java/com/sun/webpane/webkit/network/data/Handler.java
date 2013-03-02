/*    */ package com.sun.webpane.webkit.network.data;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
/*    */ 
/*    */ public class Handler extends URLStreamHandler
/*    */ {
/*    */   protected URLConnection openConnection(URL paramURL)
/*    */     throws IOException
/*    */   {
/* 19 */     return new DataURLConnection(paramURL);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.data.Handler
 * JD-Core Version:    0.6.2
 */