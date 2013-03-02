/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.sg.prism.NGNode;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.RenderingContext;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.impl.Disposer;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ 
/*     */ public class EmbeddedPainter extends AbstractPainter
/*     */   implements Runnable
/*     */ {
/*     */   private RTTexture texture;
/*     */   private EmbeddedScene escene;
/*     */ 
/*     */   protected EmbeddedPainter(EmbeddedScene paramEmbeddedScene)
/*     */   {
/*  29 */     super(paramEmbeddedScene);
/*  30 */     setRoot(this.scene.root);
/*  31 */     this.escene = paramEmbeddedScene;
/*     */   }
/*     */ 
/*     */   protected boolean validateStageGraphics() {
/*  35 */     boolean bool = super.validateStageGraphics();
/*     */ 
/*  37 */     if (!bool) {
/*  38 */       return false;
/*     */     }
/*     */ 
/*  41 */     if (this.escene.host == null) {
/*  42 */       return false;
/*     */     }
/*     */ 
/*  45 */     Screen localScreen = Screen.getMainScreen();
/*  46 */     GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/*  47 */     this.factory = localGraphicsPipeline.getResourceFactory(localScreen);
/*     */ 
/*  49 */     if (!this.factory.isDeviceReady()) {
/*  50 */       return false;
/*     */     }
/*     */ 
/*  53 */     if ((this.escene.width <= 0) || (this.escene.height <= 0)) {
/*  54 */       return false;
/*     */     }
/*     */ 
/*  57 */     setPaintBounds(this.escene.width, this.escene.height);
/*     */ 
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   public void run() {
/*  63 */     if (!validateStageGraphics()) {
/*  64 */       return;
/*     */     }
/*     */ 
/*  67 */     this.context = this.factory.createRenderingContext(null);
/*  68 */     this.escene.sizeLock.lock();
/*     */     try
/*     */     {
/*  71 */       this.context.begin();
/*     */ 
/*  73 */       if ((this.texture == null) || (this.escene.textureBits == null) || (this.escene.needsReset)) {
/*  74 */         this.texture = this.factory.createRTTexture(this.escene.width, this.escene.height);
/*  75 */         if (this.texture == null) {
/*     */           return;
/*     */         }
/*  78 */         this.escene.textureBits = IntBuffer.allocate(this.escene.width * this.escene.height);
/*  79 */         if (this.escene.textureBits == null) {
/*     */           return;
/*     */         }
/*  82 */         this.escene.needsReset = false;
/*     */       }
/*     */ 
/*  85 */       Graphics localGraphics = this.texture.createGraphics();
/*  86 */       if (localGraphics == null) {
/*  87 */         this.escene.needsReset = true;
/*  88 */         this.scene.entireSceneNeedsRepaint();
/*     */       }
/*     */       else {
/*  91 */         paintImpl(localGraphics);
/*     */ 
/*  93 */         this.escene.textureBits.rewind();
/*  94 */         this.texture.readPixels(this.escene.textureBits);
/*     */ 
/*  96 */         this.escene.host.repaint();
/*     */       }
/*     */     } catch (Throwable localThrowable) { localThrowable.printStackTrace(System.err);
/*     */     } finally {
/* 100 */       Disposer.cleanUp();
/* 101 */       this.context.end();
/* 102 */       this.escene.sizeLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doPaint(Graphics paramGraphics) {
/* 107 */     this.scene.clearEntireSceneDirty();
/* 108 */     paramGraphics.setDepthBuffer(this.scene.hasDepthBuffer());
/* 109 */     paramGraphics.clear(Color.TRANSPARENT);
/* 110 */     if (this.scene.fillPaint != null) {
/* 111 */       paramGraphics.getRenderTarget().setOpaque(this.scene.fillPaint.isOpaque());
/* 112 */       paramGraphics.setPaint(this.scene.fillPaint);
/* 113 */       paramGraphics.fillQuad(0.0F, 0.0F, this.escene.width, this.escene.height);
/*     */     }
/* 115 */     paramGraphics.setCamera(this.scene.camera);
/* 116 */     this.scene.root.render(paramGraphics);
/*     */   }
/*     */ 
/*     */   protected CameraImpl getCamera() {
/* 120 */     if (this.escene != null) {
/* 121 */       return this.escene.camera;
/*     */     }
/* 123 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedPainter
 * JD-Core Version:    0.6.2
 */