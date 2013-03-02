/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.View;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.Presentable;
/*    */ import com.sun.prism.RenderingContext;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.impl.Disposer;
/*    */ import java.io.PrintStream;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ public class PresentingPainter extends ViewPainter
/*    */   implements Runnable
/*    */ {
/*    */   private ViewScene vs;
/*    */ 
/*    */   protected PresentingPainter(ViewScene paramViewScene, PrismPen paramPrismPen)
/*    */   {
/* 21 */     super(paramViewScene, paramPrismPen);
/* 22 */     this.vs = paramViewScene;
/*    */   }
/*    */ 
/*    */   public void run() {
/*    */     try {
/* 27 */       assert (!renderLock.isHeldByCurrentThread());
/* 28 */       renderLock.lock();
/*    */ 
/* 30 */       this.valid = validateStageGraphics();
/*    */ 
/* 32 */       if (!this.valid) {
/* 33 */         if (verbose) {
/* 34 */           System.err.println("PresentingPainter: validateStageGraphics failed");
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 39 */         if (this.view != null)
/*    */         {
/* 44 */           this.pen.begin();
/*    */         }
/*    */ 
/* 47 */         int i = (this.presentable == null) || (this.penWidth != this.viewWidth) || (this.penHeight != this.viewHeight) ? 1 : 0;
/* 48 */         if ((i != 0) && (
/* 49 */           (this.presentable == null) || (this.presentable.recreateOnResize()))) {
/* 50 */           this.context = this.factory.createRenderingContext(this.vs.getPlatformView());
/*    */         }
/*    */ 
/* 54 */         this.context.begin();
/*    */ 
/* 56 */         if (i != 0) {
/* 57 */           if ((this.presentable == null) || (this.presentable.recreateOnResize())) {
/* 58 */             disposePresentable();
/* 59 */             this.presentable = this.factory.createPresentable(this.vs.getPlatformView());
/* 60 */             i = 0;
/*    */           }
/* 62 */           this.penWidth = this.viewWidth;
/* 63 */           this.penHeight = this.viewHeight;
/*    */         }
/*    */ 
/* 66 */         if (this.presentable != null) {
/* 67 */           Graphics localGraphics = this.presentable.createGraphics();
/*    */ 
/* 69 */           if (localGraphics != null) {
/* 70 */             if (i != 0) {
/* 71 */               localGraphics.reset();
/*    */             }
/* 73 */             paintImpl(localGraphics);
/*    */           }
/*    */ 
/* 76 */           if (!this.presentable.prepare(null)) { disposePresentable();
/* 78 */             this.scene.entireSceneNeedsRepaint();
/*    */             return;
/*    */           }
/* 82 */           if ((!this.liveRepaint.get()) && (collector.toolkit().isVsyncEnabled())) {
/* 83 */             collector.releaseScene(this.scene);
/*    */           }
/*    */ 
/* 87 */           if (!this.presentable.present()) {
/* 88 */             disposePresentable();
/* 89 */             this.scene.entireSceneNeedsRepaint();
/*    */           }
/*    */         }
/*    */       }
/*    */     } catch (Throwable localThrowable) { localThrowable.printStackTrace(System.err);
/*    */     } finally {
/* 95 */       if ((this.valid) && (this.context != null)) {
/* 96 */         Disposer.cleanUp();
/* 97 */         this.context.end();
/*    */       }
/* 99 */       if ((this.valid) && (this.view != null) && (!this.view.isClosed())) {
/* 100 */         this.pen.end();
/*    */       }
/*    */ 
/* 103 */       this.pen.getPainting().set(false);
/*    */ 
/* 105 */       renderLock.unlock();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PresentingPainter
 * JD-Core Version:    0.6.2
 */