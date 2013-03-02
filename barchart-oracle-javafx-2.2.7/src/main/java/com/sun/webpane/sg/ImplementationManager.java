/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import com.sun.webpane.platform.CursorManager;
/*    */ import com.sun.webpane.platform.EventLoop;
/*    */ import com.sun.webpane.platform.LocalizedStrings;
/*    */ import com.sun.webpane.platform.MenuManager;
/*    */ import com.sun.webpane.platform.ServiceProvider;
/*    */ import com.sun.webpane.platform.ThemeClient;
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import com.sun.webpane.sg.theme.RenderThemeImpl;
/*    */ import javafx.scene.web.WebEngine;
/*    */ import javafx.scene.web.WebView;
/*    */ 
/*    */ public abstract class ImplementationManager
/*    */ {
/*    */   private static ImplementationManager instance;
/*    */ 
/*    */   public static ImplementationManager getInstance()
/*    */   {
/* 23 */     if (instance == null) {
/* 24 */       String str1 = Toolkit.getToolkit().getClass().getName();
/*    */       String str2;
/* 26 */       if (str1.endsWith("QuantumToolkit"))
/* 27 */         str2 = "com.sun.webpane.sg.prism.ImplementationManager";
/* 28 */       else if (str1.endsWith("SwingToolkit"))
/* 29 */         str2 = "com.sun.webpane.sg.swing.ImplementationManager";
/*    */       else {
/* 31 */         throw new RuntimeException("Unsupported toolkit: " + str1);
/*    */       }
/*    */       try
/*    */       {
/* 35 */         Class localClass = Class.forName(str2);
/* 36 */         instance = (ImplementationManager)localClass.newInstance();
/*    */       } catch (Exception localException) {
/* 38 */         throw new RuntimeException(localException);
/*    */       }
/* 40 */       CursorManager.setCursorManager(new CursorManagerImpl());
/* 41 */       MenuManager.setMenuManager(new MenuManagerImpl());
/* 42 */       ServiceProvider.setServiceProvider(new ServiceProviderImpl());
/* 43 */       ThemeClient.setDefaultRenderTheme(new RenderThemeImpl());
/* 44 */       EventLoop.setEventLoop(new EventLoopImpl());
/* 45 */       LocalizedStrings.setResourceBundleBaseName("javafx.scene.web.LocalizedStrings");
/*    */     }
/* 47 */     return instance;
/*    */   }
/*    */ 
/*    */   public static WebPage createPage(Accessor paramAccessor) {
/* 51 */     return createPage(paramAccessor, false);
/*    */   }
/*    */ 
/*    */   public static WebPage createPage(Accessor paramAccessor, boolean paramBoolean) {
/* 55 */     getInstance();
/* 56 */     WebEngine localWebEngine = paramAccessor.getEngine();
/* 57 */     WebPage localWebPage = new WebPage(new WebPageClientImpl(paramAccessor), new UIClientImpl(paramAccessor), new PolicyClientImpl(), new ThemeClientImpl(paramAccessor), paramBoolean);
/*    */ 
/* 63 */     return localWebPage;
/*    */   }
/*    */ 
/*    */   public static PGWebView createView(WebView paramWebView) {
/* 67 */     return getInstance().createViewImpl(paramWebView);
/*    */   }
/*    */ 
/*    */   public abstract PGWebView createViewImpl(WebView paramWebView);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.ImplementationManager
 * JD-Core Version:    0.6.2
 */