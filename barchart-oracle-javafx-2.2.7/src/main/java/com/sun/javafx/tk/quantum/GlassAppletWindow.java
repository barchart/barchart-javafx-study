/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.tk.TKStage;
/*     */ import com.sun.javafx.tk.desktop.AppletWindow;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class GlassAppletWindow
/*     */   implements AppletWindow
/*     */ {
/*     */   private final Window glassWindow;
/*     */   private WeakReference<Stage> topStage;
/*     */   private String serverName;
/*     */ 
/*     */   GlassAppletWindow(long paramLong, String paramString)
/*     */   {
/*  27 */     if (0L == paramLong) {
/*  28 */       if (paramString != null) {
/*  29 */         throw new RuntimeException("GlassAppletWindow constructor used incorrectly.");
/*     */       }
/*  31 */       this.glassWindow = Application.GetApplication().createWindow(null, 0);
/*     */     } else {
/*  33 */       this.serverName = paramString;
/*  34 */       this.glassWindow = Application.GetApplication().createWindow(paramLong);
/*     */     }
/*     */   }
/*     */ 
/*     */   Window getGlassWindow() {
/*  39 */     return this.glassWindow;
/*     */   }
/*     */ 
/*     */   public void setBackgroundColor(int paramInt)
/*     */   {
/*  44 */     float f1 = (paramInt >> 16 & 0xFF) / 255.0F;
/*  45 */     float f2 = (paramInt >> 8 & 0xFF) / 255.0F;
/*  46 */     float f3 = (paramInt & 0xFF) / 255.0F;
/*  47 */     this.glassWindow.setBackground(f1, f2, f3);
/*     */   }
/*     */ 
/*     */   public void setForegroundColor(int paramInt)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean)
/*     */   {
/*  57 */     this.glassWindow.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setSize(int paramInt1, int paramInt2)
/*     */   {
/*  62 */     this.glassWindow.setSize(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  67 */     return this.glassWindow.getWidth();
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  72 */     return this.glassWindow.getHeight();
/*     */   }
/*     */ 
/*     */   public void setPosition(int paramInt1, int paramInt2)
/*     */   {
/*  77 */     this.glassWindow.setPosition(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public int getPositionX()
/*     */   {
/*  82 */     return this.glassWindow.getX();
/*     */   }
/*     */ 
/*     */   public int getPositionY()
/*     */   {
/*  87 */     return this.glassWindow.getY();
/*     */   }
/*     */ 
/*     */   void dispose() {
/*  91 */     this.glassWindow.close();
/*     */   }
/*     */ 
/*     */   public void setStageOnTop(Stage paramStage)
/*     */   {
/*  96 */     if (null != paramStage)
/*  97 */       this.topStage = new WeakReference(paramStage);
/*     */     else
/*  99 */       this.topStage = null;
/*     */   }
/*     */ 
/*     */   public int getRemoteLayerId()
/*     */   {
/* 105 */     View localView = this.glassWindow.getView();
/* 106 */     if (localView != null) {
/* 107 */       return localView.getNativeRemoteLayerId(this.serverName);
/*     */     }
/* 109 */     return -1;
/*     */   }
/*     */ 
/*     */   public void dispatchEvent(Map paramMap)
/*     */   {
/* 115 */     this.glassWindow.dispatchNpapiEvent(paramMap);
/*     */   }
/*     */ 
/*     */   void assertStageOrder()
/*     */   {
/* 123 */     if (null != this.topStage) {
/* 124 */       Stage localStage = (Stage)this.topStage.get();
/* 125 */       if (null != localStage) {
/* 126 */         TKStage localTKStage = localStage.impl_getPeer();
/* 127 */         if (((localTKStage instanceof WindowStage)) && (((WindowStage)localTKStage).isVisible()))
/*     */         {
/* 131 */           Window localWindow = ((WindowStage)localTKStage).getPlatformWindow();
/* 132 */           if (null != localWindow)
/* 133 */             localWindow.toFront();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassAppletWindow
 * JD-Core Version:    0.6.2
 */