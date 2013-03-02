/*    */ package com.sun.webpane.webkit;
/*    */ 
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import com.sun.webpane.platform.WebPageClient;
/*    */ 
/*    */ public class WCFrameView extends WCWidget
/*    */ {
/*    */   public WCFrameView(WebPage paramWebPage)
/*    */   {
/* 13 */     super(paramWebPage);
/*    */   }
/*    */ 
/*    */   protected void requestFocus() {
/* 17 */     WebPageClient localWebPageClient = getPage().getPageClient();
/* 18 */     if (localWebPageClient != null)
/* 19 */       localWebPageClient.setFocus(true);
/*    */   }
/*    */ 
/*    */   protected void setCursor(long paramLong)
/*    */   {
/* 24 */     WebPageClient localWebPageClient = getPage().getPageClient();
/* 25 */     if (localWebPageClient != null)
/* 26 */       localWebPageClient.setCursor(paramLong);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.WCFrameView
 * JD-Core Version:    0.6.2
 */