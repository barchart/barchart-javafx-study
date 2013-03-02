/*    */ package com.sun.webpane.webkit;
/*    */ 
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import com.sun.webpane.platform.WebPageClient;
/*    */ import com.sun.webpane.platform.graphics.WCRectangle;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class WCWidget
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(WCWidget.class.getName());
/*    */   private int x;
/*    */   private int y;
/*    */   private int width;
/*    */   private int height;
/*    */   private WebPage page;
/*    */ 
/*    */   public WCWidget(WebPage paramWebPage)
/*    */   {
/* 27 */     this.page = paramWebPage;
/*    */   }
/*    */ 
/*    */   public WebPage getPage() {
/* 31 */     return this.page;
/*    */   }
/*    */ 
/*    */   public WCRectangle getBounds()
/*    */   {
/* 36 */     return new WCRectangle(this.x, this.y, this.width, this.height);
/*    */   }
/*    */ 
/*    */   public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 40 */     this.x = paramInt1;
/* 41 */     this.y = paramInt2;
/* 42 */     this.width = paramInt3;
/* 43 */     this.height = paramInt4;
/*    */   }
/*    */   protected void destroy() {
/*    */   }
/*    */   protected void requestFocus() {
/*    */   }
/*    */   protected void setCursor(long paramLong) {
/*    */   }
/*    */   protected void setVisible(boolean paramBoolean) {
/*    */   }
/*    */ 
/*    */   private void fwkDestroy() {
/* 55 */     log.log(Level.FINER, "destroy");
/* 56 */     destroy();
/*    */   }
/*    */ 
/*    */   private void fwkSetBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 60 */     if (log.isLoggable(Level.FINER)) {
/* 61 */       log.log(Level.FINER, "setBounds({0}, {1}, {2}, {3})", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/*    */     }
/*    */ 
/* 64 */     setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ 
/*    */   private void fwkRequestFocus() {
/* 68 */     log.log(Level.FINER, "requestFocus");
/* 69 */     requestFocus();
/*    */   }
/*    */ 
/*    */   private void fwkSetCursor(long paramLong) {
/* 73 */     if (log.isLoggable(Level.FINER)) {
/* 74 */       log.log(Level.FINER, "setCursor({0})", Long.valueOf(paramLong));
/*    */     }
/* 76 */     setCursor(paramLong);
/*    */   }
/*    */ 
/*    */   private void fwkSetVisible(boolean paramBoolean) {
/* 80 */     if (log.isLoggable(Level.FINER)) {
/* 81 */       log.log(Level.FINER, "setVisible({0})", Boolean.valueOf(paramBoolean));
/*    */     }
/* 83 */     setVisible(paramBoolean);
/*    */   }
/*    */ 
/*    */   protected int fwkGetScreenDepth() {
/* 87 */     log.log(Level.FINER, "getScreenDepth");
/* 88 */     WebPageClient localWebPageClient = this.page.getPageClient();
/* 89 */     return localWebPageClient != null ? localWebPageClient.getScreenDepth() : 24;
/*    */   }
/*    */ 
/*    */   protected WCRectangle fwkGetScreenRect(boolean paramBoolean)
/*    */   {
/* 95 */     if (log.isLoggable(Level.FINER)) {
/* 96 */       log.log(Level.FINER, "getScreenRect({0})", Boolean.valueOf(paramBoolean));
/*    */     }
/* 98 */     WebPageClient localWebPageClient = this.page.getPageClient();
/* 99 */     return localWebPageClient != null ? localWebPageClient.getScreenBounds(paramBoolean) : null;
/*    */   }
/*    */ 
/*    */   private static native void initIDs();
/*    */ 
/*    */   static
/*    */   {
/* 17 */     initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.WCWidget
 * JD-Core Version:    0.6.2
 */