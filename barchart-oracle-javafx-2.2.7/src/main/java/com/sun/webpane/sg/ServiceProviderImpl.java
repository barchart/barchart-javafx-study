/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.Pasteboard;
/*    */ import com.sun.webpane.platform.ServiceProvider;
/*    */ 
/*    */ public class ServiceProviderImpl extends ServiceProvider
/*    */ {
/*    */   public Pasteboard createPasteboard()
/*    */   {
/* 14 */     return new PasteboardImpl();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.ServiceProviderImpl
 * JD-Core Version:    0.6.2
 */