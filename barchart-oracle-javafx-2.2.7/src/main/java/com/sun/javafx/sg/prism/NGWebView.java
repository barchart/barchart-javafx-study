/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.RectBounds;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*    */ import com.sun.webpane.platform.graphics.WCRectangle;
/*    */ import com.sun.webpane.sg.PGWebView;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class NGWebView extends NGGroup
/*    */   implements PGWebView
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(NGWebView.class.getName());
/*    */   private volatile WebPage page;
/*    */   private volatile float width;
/*    */   private volatile float height;
/*    */ 
/*    */   public void setPage(WebPage paramWebPage)
/*    */   {
/* 32 */     this.page = paramWebPage;
/*    */   }
/*    */ 
/*    */   public void resize(float paramFloat1, float paramFloat2) {
/* 36 */     if ((this.width != paramFloat1) || (this.height != paramFloat2)) {
/* 37 */       this.width = paramFloat1;
/* 38 */       this.height = paramFloat2;
/* 39 */       geometryChanged();
/* 40 */       if (this.page != null)
/* 41 */         this.page.setBounds(0, 0, (int)paramFloat1, (int)paramFloat2);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void update()
/*    */   {
/* 48 */     if (this.page != null) {
/* 49 */       BaseBounds localBaseBounds = getClippedBounds(new RectBounds(), BaseTransform.IDENTITY_TRANSFORM);
/* 50 */       if (!localBaseBounds.isEmpty()) {
/* 51 */         log.log(Level.FINEST, "updating rectangle: {0}", localBaseBounds);
/* 52 */         this.page.updateContent(new WCRectangle(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getWidth(), localBaseBounds.getHeight()));
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void requestRender()
/*    */   {
/* 59 */     visualsChanged();
/*    */   }
/*    */ 
/*    */   protected void renderContent(Graphics paramGraphics)
/*    */   {
/* 64 */     log.log(Level.FINEST, "rendering into {0}", paramGraphics);
/* 65 */     if ((paramGraphics == null) || (this.page == null) || (this.width <= 0.0F) || (this.height <= 0.0F)) {
/* 66 */       return;
/*    */     }
/* 68 */     WCGraphicsContext localWCGraphicsContext = WCGraphicsManager.getGraphicsManager().createGraphicsContext(paramGraphics);
/*    */     try
/*    */     {
/* 71 */       this.page.paint(localWCGraphicsContext, new WCRectangle(0.0F, 0.0F, this.width, this.height));
/*    */     } finally {
/* 73 */       localWCGraphicsContext.dispose();
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean hasOverlappingContents()
/*    */   {
/* 79 */     return false;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGWebView
 * JD-Core Version:    0.6.2
 */