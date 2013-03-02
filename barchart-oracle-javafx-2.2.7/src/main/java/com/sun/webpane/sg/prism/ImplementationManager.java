/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.sg.prism.NGWebView;
/*    */ import com.sun.webpane.platform.Invoker;
/*    */ import com.sun.webpane.platform.Utilities;
/*    */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*    */ import com.sun.webpane.sg.PGWebView;
/*    */ import com.sun.webpane.sg.prism.theme.RendererImpl;
/*    */ import com.sun.webpane.sg.theme.Renderer;
/*    */ import javafx.scene.web.WebView;
/*    */ 
/*    */ public class ImplementationManager extends com.sun.webpane.sg.ImplementationManager
/*    */ {
/*    */   public PGWebView createViewImpl(WebView paramWebView)
/*    */   {
/* 29 */     return new NGWebView();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 22 */     WCGraphicsManager.setGraphicsManager(new FXGraphicsManager());
/* 23 */     Renderer.setRenderer(new RendererImpl());
/* 24 */     Invoker.setInvoker(new InvokerImpl());
/* 25 */     Utilities.setUtilities(new UtilitiesImpl());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.ImplementationManager
 * JD-Core Version:    0.6.2
 */