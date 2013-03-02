/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.View;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.RTTexture;
/*    */ import com.sun.prism.RenderingContext;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.impl.BufferUtil;
/*    */ import com.sun.prism.impl.Disposer;
/*    */ import com.sun.prism.impl.PrismSettings;
/*    */ import java.io.PrintStream;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ public class UploadingPainter extends ViewPainter
/*    */   implements Runnable
/*    */ {
/* 26 */   private Application app = Application.GetApplication();
/*    */   private Pixels pix;
/*    */   private Pixels pixRefCopy;
/*    */   private View viewRefCopy;
/*    */   private IntBuffer textureBits;
/*    */   private RTTexture rttexture;
/* 37 */   private final Runnable upload = new Runnable() {
/*    */     public void run() {
/* 39 */       if (!UploadingPainter.this.viewRefCopy.isClosed())
/* 40 */         UploadingPainter.this.viewRefCopy.uploadPixels(UploadingPainter.this.pixRefCopy);
/*    */     }
/* 37 */   };
/*    */ 
/*    */   protected UploadingPainter(ViewScene paramViewScene, PrismPen paramPrismPen)
/*    */   {
/* 34 */     super(paramViewScene, paramPrismPen);
/*    */   }
/*    */ 
/*    */   void disposeRTTexture()
/*    */   {
/* 46 */     if (this.rttexture != null) {
/* 47 */       this.rttexture.dispose();
/* 48 */       this.rttexture = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void run() {
/*    */     try {
/* 54 */       if (PrismSettings.threadCheck) {
/* 55 */         assert (!renderLock.isHeldByCurrentThread());
/* 56 */         renderLock.lock();
/*    */       }
/*    */ 
/* 59 */       this.valid = validateStageGraphics();
/*    */ 
/* 61 */       if (!this.valid) {
/* 62 */         if (verbose) {
/* 63 */           System.err.println("UploadingPainter: validateStageGraphics failed");
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 68 */         if (this.view != null)
/*    */         {
/* 73 */           this.pen.begin();
/*    */         }
/*    */ 
/* 76 */         int i = (this.rttexture == null) || (this.viewWidth != this.penWidth) || (this.viewHeight != this.penHeight) ? 1 : 0;
/*    */ 
/* 78 */         if (i != 0) {
/* 79 */           this.context = this.factory.createRenderingContext(null);
/*    */         }
/*    */ 
/* 82 */         this.context.begin();
/*    */ 
/* 84 */         if (i != 0) {
/* 85 */           disposeRTTexture();
/* 86 */           this.rttexture = this.factory.createRTTexture(this.viewWidth, this.viewHeight);
/* 87 */           if (this.rttexture == null) { this.context.end();
/*    */             return;
/*    */           }
/* 91 */           this.penWidth = this.viewWidth;
/* 92 */           this.penHeight = this.viewHeight;
/* 93 */           this.textureBits = null;
/*    */         }
/* 95 */         Graphics localGraphics = this.rttexture.createGraphics();
/* 96 */         if (localGraphics == null) {
/* 97 */           disposeRTTexture();
/* 98 */           this.scene.entireSceneNeedsRepaint();
/*    */         }
/*    */         else {
/* 101 */           paintImpl(localGraphics);
/*    */ 
/* 103 */           if (!this.liveRepaint.get()) {
/* 104 */             collector.releaseScene(this.scene);
/*    */           }
/*    */ 
/* 107 */           int[] arrayOfInt = this.rttexture.getPixels();
/*    */ 
/* 109 */           if (arrayOfInt != null) {
/* 110 */             this.pix = this.app.createPixels(this.viewWidth, this.viewHeight, IntBuffer.wrap(arrayOfInt));
/*    */           } else {
/* 112 */             if (this.textureBits == null) {
/* 113 */               this.textureBits = BufferUtil.newIntBuffer(this.viewWidth * this.viewHeight);
/*    */             }
/*    */ 
/* 116 */             if (this.textureBits != null) {
/* 117 */               if (this.rttexture.readPixels(this.textureBits)) {
/* 118 */                 this.pix = this.app.createPixels(this.viewWidth, this.viewHeight, this.textureBits);
/*    */               }
/*    */               else {
/* 121 */                 this.scene.entireSceneNeedsRepaint();
/* 122 */                 disposeRTTexture();
/* 123 */                 this.pix = null;
/*    */               }
/*    */             }
/*    */           }
/*    */ 
/* 128 */           if (this.pix != null)
/*    */           {
/* 132 */             this.pixRefCopy = this.pix;
/* 133 */             this.viewRefCopy = this.view;
/* 134 */             Application.postOnEventQueue(this.upload);
/*    */           }
/*    */         }
/*    */       }
/*    */     } catch (Throwable localThrowable) { localThrowable.printStackTrace(System.err);
/*    */     } finally {
/* 140 */       if ((this.valid) && (this.context != null)) {
/* 141 */         Disposer.cleanUp();
/* 142 */         this.context.end();
/*    */       }
/* 144 */       if ((this.valid) && (this.view != null) && (!this.view.isClosed())) {
/* 145 */         this.pen.end();
/*    */       }
/*    */ 
/* 148 */       this.pen.getPainting().set(false);
/*    */ 
/* 150 */       if (PrismSettings.threadCheck)
/* 151 */         renderLock.unlock();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.UploadingPainter
 * JD-Core Version:    0.6.2
 */