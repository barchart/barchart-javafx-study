/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.web.WebEngine;
/*    */ import javafx.scene.web.WebView;
/*    */ 
/*    */ public abstract class Accessor
/*    */ {
/*    */   private static PageAccessor pageAccessor;
/*    */ 
/*    */   public static void setPageAccessor(PageAccessor paramPageAccessor)
/*    */   {
/* 22 */     pageAccessor = paramPageAccessor;
/*    */   }
/*    */ 
/*    */   public static WebPage getPageFor(WebEngine paramWebEngine) {
/* 26 */     return pageAccessor.getPage(paramWebEngine);
/*    */   }
/*    */ 
/*    */   public abstract WebEngine getEngine();
/*    */ 
/*    */   public abstract WebView getView();
/*    */ 
/*    */   public abstract PGWebView getPGView();
/*    */ 
/*    */   public abstract WebPage getPage();
/*    */ 
/*    */   public abstract void addChild(Node paramNode);
/*    */ 
/*    */   public abstract void removeChild(Node paramNode);
/*    */ 
/*    */   public abstract void addViewListener(InvalidationListener paramInvalidationListener);
/*    */ 
/*    */   public static abstract interface PageAccessor
/*    */   {
/*    */     public abstract WebPage getPage(WebEngine paramWebEngine);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.Accessor
 * JD-Core Version:    0.6.2
 */