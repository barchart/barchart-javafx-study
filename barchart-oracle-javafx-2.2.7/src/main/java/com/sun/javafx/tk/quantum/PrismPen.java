/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.View.Capability;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.camera.PrismParallelCameraImpl;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ import com.sun.prism.render.ToolkitInterface;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javafx.stage.StageStyle;
/*     */ 
/*     */ public final class PrismPen extends Pen
/*     */ {
/*  31 */   private static PrismCameraImpl DEFAULT_CAMERA = PrismParallelCameraImpl.getInstance();
/*  32 */   private static PaintCollector collector = PaintCollector.getInstance();
/*     */   Future paintRunnableFuture;
/*     */   boolean valid;
/*     */   PrismCameraImpl camera;
/*     */   ViewScene scene;
/*  40 */   HashMap caps = new HashMap();
/*     */   AtomicBoolean painting;
/*     */   private ViewPainter painter;
/*     */   protected PaintRenderJob paintRenderJob;
/*     */   private boolean depthBuffer;
/*     */ 
/*     */   public PrismPen(ViewScene paramViewScene, boolean paramBoolean)
/*     */   {
/*  52 */     this.scene = paramViewScene;
/*  53 */     this.valid = false;
/*  54 */     this.depthBuffer = paramBoolean;
/*  55 */     this.painting = new AtomicBoolean(false);
/*     */ 
/*  57 */     paramViewScene.setFillPaint(Color.WHITE);
/*     */   }
/*     */ 
/*     */   boolean getDepthBuffer() {
/*  61 */     return this.depthBuffer;
/*     */   }
/*     */ 
/*     */   public Map getCapabilities()
/*     */   {
/*  66 */     Boolean localBoolean = Boolean.TRUE;
/*     */ 
/*  70 */     if (PlatformUtil.isMac()) {
/*  71 */       GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/*  72 */       if ((localGraphicsPipeline != null) && (localGraphicsPipeline.getClass().getName().endsWith("J2DPipeline"))) {
/*  73 */         localBoolean = Boolean.FALSE;
/*     */       }
/*     */     }
/*  76 */     if (!this.caps.containsKey(View.Capability.k3dKey)) {
/*  77 */       addCapability(this.caps, View.Capability.k3dKey, localBoolean);
/*     */ 
/*  79 */       addCapability(this.caps, View.Capability.k3dDepthKey, new Integer(this.depthBuffer ? 16 : 0));
/*     */     }
/*  81 */     return this.caps;
/*     */   }
/*     */ 
/*     */   public void repaint() {
/*  85 */     View localView = this.scene.getPlatformView();
/*  86 */     if (localView == null) {
/*  87 */       return;
/*     */     }
/*     */ 
/*  90 */     if (!this.painting.getAndSet(true)) {
/*  91 */       Toolkit localToolkit = Toolkit.getToolkit();
/*  92 */       ToolkitInterface localToolkitInterface = (ToolkitInterface)localToolkit;
/*  93 */       this.paintRunnableFuture = localToolkitInterface.addRenderJob(this.paintRenderJob);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Future getFuture() {
/*  98 */     return this.paintRunnableFuture;
/*     */   }
/*     */ 
/*     */   protected AtomicBoolean getPainting() {
/* 102 */     return this.painting;
/*     */   }
/*     */ 
/*     */   protected ViewPainter getPainter() {
/* 106 */     return this.painter;
/*     */   }
/*     */ 
/*     */   protected void setPainter(ViewPainter paramViewPainter) {
/* 110 */     this.painter = paramViewPainter;
/*     */ 
/* 112 */     this.paintRenderJob = new PaintRenderJob(this.scene, collector.getRendered(), (Runnable)this.painter);
/*     */   }
/*     */ 
/*     */   protected Color getClearColor() {
/* 116 */     Object localObject = this.scene != null ? this.scene.getWindowStage() : null;
/* 117 */     if ((localObject != null) && (localObject.getStyle() == StageStyle.TRANSPARENT)) {
/* 118 */       return Color.TRANSPARENT;
/*     */     }
/* 120 */     if (this.scene.fillPaint == null)
/* 121 */       return Color.WHITE;
/* 122 */     if (this.scene.fillPaint.isOpaque()) {
/* 123 */       if (this.scene.fillPaint.getType() == Paint.Type.COLOR)
/* 124 */         return (Color)this.scene.fillPaint;
/* 125 */       if (this.depthBuffer)
/*     */       {
/* 127 */         return Color.TRANSPARENT;
/*     */       }
/* 129 */       return null;
/*     */     }
/*     */ 
/* 132 */     return Color.WHITE;
/*     */   }
/*     */ 
/*     */   protected Paint getCurrentPaint()
/*     */   {
/* 138 */     Object localObject = this.scene != null ? this.scene.getWindowStage() : null;
/* 139 */     if ((localObject != null) && (localObject.getStyle() == StageStyle.TRANSPARENT)) {
/* 140 */       return Color.TRANSPARENT.equals(this.scene.fillPaint) ? null : this.scene.fillPaint;
/*     */     }
/* 142 */     if (this.scene.fillPaint == null)
/* 143 */       return null;
/* 144 */     if (this.scene.fillPaint.isOpaque()) {
/* 145 */       if (this.scene.fillPaint.getType() == Paint.Type.COLOR) {
/* 146 */         return null;
/*     */       }
/* 148 */       return this.scene.fillPaint;
/*     */     }
/* 150 */     return this.scene.fillPaint;
/*     */   }
/*     */ 
/*     */   public void paint(long paramLong, int paramInt1, int paramInt2)
/*     */   {
/*     */   }
/*     */ 
/*     */   void setCamera(CameraImpl paramCameraImpl)
/*     */   {
/* 168 */     if (paramCameraImpl != null)
/* 169 */       this.camera = ((PrismCameraImpl)paramCameraImpl);
/*     */     else
/* 171 */       this.camera = DEFAULT_CAMERA;
/*     */   }
/*     */ 
/*     */   protected CameraImpl getCamera()
/*     */   {
/* 176 */     return this.camera;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PrismPen
 * JD-Core Version:    0.6.2
 */