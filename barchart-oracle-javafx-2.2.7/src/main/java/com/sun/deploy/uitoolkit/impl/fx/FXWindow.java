/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.deploy.uitoolkit.SynthesizedEventListener;
/*     */ import com.sun.deploy.uitoolkit.Window;
/*     */ import com.sun.deploy.uitoolkit.Window.DisposeListener;
/*     */ import com.sun.deploy.uitoolkit.Window.WindowSize;
/*     */ import com.sun.javafx.tk.desktop.AppletWindow;
/*     */ import com.sun.javafx.tk.desktop.DesktopToolkit;
/*     */ import java.util.Map;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class FXWindow extends Window
/*     */   implements AppletStageManager, SynthesizedEventListener
/*     */ {
/*     */   private AppletWindow appletWindow;
/*  19 */   private int width = 0; private int height = 0;
/*     */   private Stage appletStage;
/*     */   private Stage preloaderStage;
/*     */   private Stage errorStage;
/*     */ 
/*     */   FXWindow(long parent, String caRenderServer)
/*     */   {
/*  22 */     DeployPerfLogger.timestamp("(start) FXWindow Constructor");
/*     */ 
/*  24 */     this.appletWindow = DesktopToolkit.getDesktopToolkit().createAppletWindow(parent, caRenderServer);
/*     */ 
/*  26 */     DeployPerfLogger.timestamp("  FXWindow got toolkit window");
/*     */ 
/*  28 */     this.width = this.appletWindow.getWidth();
/*  29 */     this.height = this.appletWindow.getHeight();
/*  30 */     DeployPerfLogger.timestamp("(done) FXWindow Constructor");
/*     */   }
/*     */ 
/*     */   public Object getWindowObject()
/*     */   {
/*  35 */     return this.appletWindow;
/*     */   }
/*     */ 
/*     */   public void synthesizeEvent(Map eventData)
/*     */   {
/*  40 */     this.appletWindow.dispatchEvent(eventData);
/*     */   }
/*     */ 
/*     */   public void requestFocus()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getWindowLayerID()
/*     */   {
/*  50 */     return this.appletWindow.getRemoteLayerId();
/*     */   }
/*     */ 
/*     */   public void setBackground(int i)
/*     */   {
/*  55 */     this.appletWindow.setBackgroundColor(i);
/*     */   }
/*     */ 
/*     */   public void setForeground(int i)
/*     */   {
/*  60 */     this.appletWindow.setForegroundColor(i);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean visible)
/*     */   {
/*  65 */     this.appletWindow.setVisible(visible);
/*     */   }
/*     */ 
/*     */   public void setSize(int width, int height)
/*     */   {
/*  70 */     this.width = width;
/*  71 */     this.height = height;
/*  72 */     this.appletWindow.setSize(width, height);
/*  73 */     if (this.appletStage != null) {
/*  74 */       this.appletStage.setWidth(width);
/*  75 */       this.appletStage.setHeight(height);
/*     */     }
/*  77 */     if (this.preloaderStage != null) {
/*  78 */       this.preloaderStage.setWidth(width);
/*  79 */       this.preloaderStage.setHeight(height);
/*     */     }
/*  81 */     if (this.errorStage != null) {
/*  82 */       this.errorStage.setWidth(width);
/*  83 */       this.errorStage.setHeight(height);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Window.WindowSize getSize()
/*     */   {
/*  90 */     this.width = this.appletWindow.getWidth();
/*  91 */     this.height = this.appletWindow.getHeight();
/*  92 */     return new Window.WindowSize(this, this.width, this.height);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  98 */     if (null != this.appletStage) {
/*  99 */       this.appletStage.close();
/* 100 */       this.appletStage = null;
/*     */     }
/*     */ 
/* 103 */     if (null != this.preloaderStage) {
/* 104 */       this.preloaderStage.close();
/* 105 */       this.preloaderStage = null;
/*     */     }
/*     */ 
/* 108 */     if (null != this.errorStage) {
/* 109 */       this.errorStage.close();
/* 110 */       this.errorStage = null;
/*     */     }
/*     */ 
/* 114 */     this.appletWindow = null;
/* 115 */     DesktopToolkit.getDesktopToolkit().closeAppletWindow();
/*     */   }
/*     */ 
/*     */   public void dispose(Window.DisposeListener dl, long l)
/*     */   {
/* 120 */     dispose();
/*     */   }
/*     */ 
/*     */   public void setPosition(int x, int y)
/*     */   {
/* 125 */     this.appletWindow.setPosition(x, y);
/*     */   }
/*     */ 
/*     */   public synchronized Stage getAppletStage()
/*     */   {
/* 139 */     if (this.appletStage == null) {
/* 140 */       this.appletStage = createNewStage();
/*     */     }
/* 142 */     return this.appletStage;
/*     */   }
/*     */ 
/*     */   public Stage getPreloaderStage()
/*     */   {
/* 147 */     if (this.preloaderStage == null) {
/* 148 */       this.preloaderStage = createNewStage();
/*     */ 
/* 150 */       if (null == this.errorStage) {
/* 151 */         this.appletWindow.setStageOnTop(this.preloaderStage);
/*     */       }
/*     */     }
/* 154 */     return this.preloaderStage;
/*     */   }
/*     */ 
/*     */   public Stage getErrorStage()
/*     */   {
/* 159 */     if (this.errorStage == null) {
/* 160 */       this.errorStage = createNewStage();
/* 161 */       this.appletWindow.setStageOnTop(this.errorStage);
/*     */     }
/* 163 */     return this.errorStage;
/*     */   }
/*     */ 
/*     */   private Stage createNewStage() {
/* 167 */     Stage stage = new Stage();
/* 168 */     stage.impl_setPrimary(true);
/* 169 */     stage.setWidth(this.width);
/* 170 */     stage.setHeight(this.height);
/* 171 */     stage.setResizable(false);
/* 172 */     return stage;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXWindow
 * JD-Core Version:    0.6.2
 */