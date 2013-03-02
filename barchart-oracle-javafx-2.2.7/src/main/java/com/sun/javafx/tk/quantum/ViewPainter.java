/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ 
/*     */ public class ViewPainter extends AbstractPainter
/*     */ {
/*     */   private WindowStage windowStage;
/*     */   private Window window;
/*     */   protected View view;
/*     */   protected PrismPen pen;
/*  34 */   protected int penWidth = -1;
/*  35 */   protected int penHeight = -1;
/*     */   protected int viewWidth;
/*     */   protected int viewHeight;
/*     */   protected boolean valid;
/*     */ 
/*     */   protected ViewPainter(GlassScene paramGlassScene, PrismPen paramPrismPen)
/*     */   {
/*  42 */     super(paramGlassScene);
/*     */ 
/*  44 */     this.pen = paramPrismPen;
/*     */   }
/*     */ 
/*     */   protected boolean validateStageGraphics() {
/*  48 */     this.valid = super.validateStageGraphics();
/*     */ 
/*  50 */     if (!this.valid) {
/*  51 */       return false;
/*     */     }
/*     */ 
/*  54 */     synchronized (this) {
/*  55 */       ViewScene localViewScene = (ViewScene)this.scene;
/*  56 */       this.view = localViewScene.getPlatformView();
/*     */ 
/*  58 */       if (this.view == null) {
/*  59 */         return false;
/*     */       }
/*  61 */       if (this.view.isClosed()) {
/*  62 */         return false;
/*     */       }
/*     */ 
/*  65 */       this.viewWidth = this.view.getWidth();
/*  66 */       this.viewHeight = this.view.getHeight();
/*     */ 
/*  68 */       if ((this.viewWidth <= 0) || (this.viewHeight <= 0)) {
/*  69 */         return false;
/*     */       }
/*  71 */       setPaintBounds(this.viewWidth, this.viewHeight);
/*     */     }
/*     */ 
/*  74 */     this.windowStage = ((WindowStage)this.scene.glassStage);
/*  75 */     this.window = this.windowStage.platformWindow;
/*     */ 
/*  77 */     if (this.window == null) {
/*  78 */       return false;
/*     */     }
/*     */ 
/*  81 */     if (this.factory == null) {
/*     */       try {
/*  83 */         this.view.lock();
/*  84 */         this.factory = GraphicsPipeline.getDefaultResourceFactory();
/*     */       }
/*     */       finally
/*     */       {
/*  90 */         this.view.unlock(this.factory != null);
/*     */       }
/*  92 */       return (this.factory != null) && (this.factory.isDeviceReady());
/*     */     }
/*  94 */     return (this.window.isVisible()) && (!this.window.isMinimized()) && (this.factory.isDeviceReady());
/*     */   }
/*     */ 
/*     */   protected void doPaint(Graphics paramGraphics)
/*     */   {
/*  99 */     if (PrismSettings.showDirtyRegions) {
/* 100 */       paramGraphics.setClipRect(null);
/*     */     }
/* 102 */     this.scene.clearEntireSceneDirty();
/* 103 */     paramGraphics.setDepthBuffer(this.pen.getDepthBuffer());
/* 104 */     Color localColor = this.pen.getClearColor();
/* 105 */     if (localColor != null) {
/* 106 */       paramGraphics.clear(localColor);
/*     */     }
/* 108 */     Paint localPaint = this.pen.getCurrentPaint();
/* 109 */     if (localPaint != null) {
/* 110 */       if (localPaint.getType() != Paint.Type.COLOR) {
/* 111 */         paramGraphics.getRenderTarget().setOpaque(localPaint.isOpaque());
/*     */       }
/* 113 */       paramGraphics.setPaint(localPaint);
/* 114 */       paramGraphics.fillQuad(0.0F, 0.0F, this.width, this.height);
/*     */     }
/* 116 */     paramGraphics.setCamera((PrismCameraImpl)this.pen.getCamera());
/* 117 */     this.root.render(paramGraphics);
/*     */   }
/*     */ 
/*     */   protected CameraImpl getCamera() {
/* 121 */     if (this.pen != null) {
/* 122 */       return this.pen.getCamera();
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.ViewPainter
 * JD-Core Version:    0.6.2
 */