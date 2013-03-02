/*     */ package com.sun.webpane.sg;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.webpane.platform.CursorManager;
/*     */ import com.sun.webpane.platform.WebPageClient;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*     */ import com.sun.webpane.platform.graphics.WCPageBackBuffer;
/*     */ import com.sun.webpane.platform.graphics.WCPoint;
/*     */ import com.sun.webpane.platform.graphics.WCRectangle;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Tooltip;
/*     */ import javafx.scene.web.WebView;
/*     */ import javafx.stage.Screen;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class WebPageClientImpl
/*     */   implements WebPageClient<WebView>
/*     */ {
/*     */   private Accessor accessor;
/*     */   private Tooltip tooltip;
/*  56 */   private boolean isTooltipRegistered = false;
/*     */ 
/*     */   public WebPageClientImpl(Accessor paramAccessor)
/*     */   {
/*  33 */     this.accessor = paramAccessor;
/*     */   }
/*     */ 
/*     */   public void repaint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setFocus(boolean paramBoolean) {
/*  41 */     WebView localWebView = this.accessor.getView();
/*  42 */     if ((localWebView != null) && (paramBoolean))
/*  43 */       localWebView.requestFocus();
/*     */   }
/*     */ 
/*     */   public void setCursor(long paramLong)
/*     */   {
/*  48 */     WebView localWebView = this.accessor.getView();
/*  49 */     if (localWebView != null) {
/*  50 */       Object localObject = CursorManager.getCursorManager().getCursor(paramLong);
/*  51 */       localWebView.setCursor((localObject instanceof Cursor) ? (Cursor)localObject : Cursor.DEFAULT);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTooltip(String paramString)
/*     */   {
/*  58 */     WebView localWebView = this.accessor.getView();
/*  59 */     if (paramString != null) {
/*  60 */       if (this.tooltip == null)
/*  61 */         this.tooltip = new Tooltip(paramString);
/*     */       else {
/*  63 */         this.tooltip.setText(paramString);
/*     */       }
/*  65 */       if (!this.isTooltipRegistered) {
/*  66 */         Tooltip.install(localWebView, this.tooltip);
/*  67 */         this.isTooltipRegistered = true;
/*     */       }
/*  69 */     } else if (this.isTooltipRegistered) {
/*  70 */       Tooltip.uninstall(localWebView, this.tooltip);
/*  71 */       this.isTooltipRegistered = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transferFocus(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public WCRectangle getScreenBounds(boolean paramBoolean) {
/*  80 */     WebView localWebView = this.accessor.getView();
/*     */ 
/*  82 */     Screen localScreen = Utils.getScreen(localWebView);
/*  83 */     if (localScreen != null) {
/*  84 */       Rectangle2D localRectangle2D = paramBoolean ? localScreen.getVisualBounds() : localScreen.getBounds();
/*     */ 
/*  87 */       return new WCRectangle((float)localRectangle2D.getMinX(), (float)localRectangle2D.getMinY(), (float)localRectangle2D.getWidth(), (float)localRectangle2D.getHeight());
/*     */     }
/*     */ 
/*  91 */     return null;
/*     */   }
/*     */ 
/*     */   public int getScreenDepth()
/*     */   {
/*  96 */     return 24;
/*     */   }
/*     */ 
/*     */   public WCPoint getLocationOnScreen() {
/* 100 */     return new WCPoint(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public WebView getContainer() {
/* 104 */     return this.accessor.getView();
/*     */   }
/*     */ 
/*     */   public WCPoint screenToWindow(WCPoint paramWCPoint) {
/* 108 */     WebView localWebView = this.accessor.getView();
/* 109 */     Scene localScene = localWebView.getScene();
/* 110 */     Window localWindow = null;
/*     */ 
/* 112 */     if ((localScene != null) && ((localWindow = localScene.getWindow()) != null))
/*     */     {
/* 115 */       Point2D localPoint2D = localWebView.sceneToLocal(paramWCPoint.getX() - localWindow.getX() - localScene.getX(), paramWCPoint.getY() - localWindow.getY() - localScene.getY());
/*     */ 
/* 118 */       return new WCPoint((float)localPoint2D.getX(), (float)localPoint2D.getY());
/*     */     }
/* 120 */     return new WCPoint(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public WCPoint windowToScreen(WCPoint paramWCPoint)
/*     */   {
/* 125 */     WebView localWebView = this.accessor.getView();
/* 126 */     Scene localScene = localWebView.getScene();
/* 127 */     Window localWindow = null;
/*     */ 
/* 129 */     if ((localScene != null) && ((localWindow = localScene.getWindow()) != null))
/*     */     {
/* 132 */       Point2D localPoint2D = localWebView.localToScene(paramWCPoint.getX(), paramWCPoint.getY());
/* 133 */       return new WCPoint((float)(localPoint2D.getX() + localScene.getX() + localWindow.getX()), (float)(localPoint2D.getY() + localScene.getY() + localWindow.getY()));
/*     */     }
/*     */ 
/* 136 */     return new WCPoint(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public WCPageBackBuffer createBackBuffer()
/*     */   {
/* 141 */     if (isBackBufferSupported()) {
/* 142 */       return WCGraphicsManager.getGraphicsManager().createPageBackBuffer();
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isBackBufferSupported() {
/* 148 */     return Boolean.valueOf((String)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public String run()
/*     */       {
/* 152 */         return System.getProperty("com.sun.webpane.pagebackbuffer", "true");
/*     */       }
/*     */     })).booleanValue();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.WebPageClientImpl
 * JD-Core Version:    0.6.2
 */