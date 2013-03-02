/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ public class ServiceProvider
/*    */ {
/*    */   private static ServiceProvider instance;
/*    */ 
/*    */   public static synchronized ServiceProvider getInstance()
/*    */   {
/* 11 */     if (instance == null) {
/* 12 */       instance = new ServiceProvider();
/*    */     }
/* 14 */     return instance;
/*    */   }
/*    */ 
/*    */   public static synchronized void setServiceProvider(ServiceProvider paramServiceProvider)
/*    */   {
/* 19 */     instance = paramServiceProvider;
/*    */   }
/*    */ 
/*    */   public Pasteboard createPasteboard()
/*    */   {
/* 26 */     return new PasteboardSimple();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.ServiceProvider
 * JD-Core Version:    0.6.2
 */