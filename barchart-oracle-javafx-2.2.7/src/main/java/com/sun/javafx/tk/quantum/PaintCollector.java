/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.prism.render.CompletionListener;
/*     */ import com.sun.prism.render.RenderJob;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BrokenBarrierException;
/*     */ import java.util.concurrent.CyclicBarrier;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ 
/*     */ public class PaintCollector
/*     */ {
/*  44 */   private static final boolean verbose = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Boolean run() {
/*  47 */       return Boolean.valueOf(Boolean.getBoolean("quantum.verbose"));
/*     */     }
/*     */   })).booleanValue();
/*     */ 
/*  51 */   private static final boolean pulseDebug = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Boolean run() {
/*  54 */       return Boolean.valueOf(Boolean.getBoolean("quantum.pulse"));
/*     */     }
/*     */   })).booleanValue();
/*     */ 
/*  74 */   private final CyclicBarrier pulseBarrier = new CyclicBarrier(2);
/*     */ 
/*  83 */   private final List<GlassScene> uploadScenes = new ArrayList();
/*     */ 
/*  90 */   private final List<GlassScene> syncScenes = new ArrayList();
/*     */ 
/*  95 */   private final List<GlassScene> dirtyScenes = new ArrayList();
/*  96 */   private final List<GlassScene> dirtyMarker = new ArrayList();
/*  97 */   private final List<GlassScene> dirtyWork = new ArrayList();
/*     */ 
/* 106 */   private final AtomicBoolean hasDirty = new AtomicBoolean(false);
/*     */ 
/* 113 */   private final AtomicBoolean released = new AtomicBoolean(false);
/*     */   private final QuantumToolkit toolkit;
/*     */   private final QuantumRenderer renderer;
/*     */   private ViewPainter viewPainter;
/*     */   private boolean needsHint;
/*     */   private static PaintCollector collector;
/* 275 */   private final CompletionListener rendered = new Object() {
/*     */     public void done(RenderJob paramAnonymousRenderJob) {
/* 277 */       PaintCollector.this.checkThreads();
/*     */ 
/* 279 */       PaintRenderJob localPaintRenderJob = (PaintRenderJob)paramAnonymousRenderJob;
/* 280 */       GlassScene localGlassScene1 = localPaintRenderJob.getScene();
/* 281 */       if (PaintCollector.pulseDebug) {
/* 282 */         System.err.println("PC: rendered: now: " + System.nanoTime() + PaintCollector.sceneSize(localGlassScene1));
/*     */       }
/* 284 */       synchronized (PaintCollector.this) {
/* 285 */         PaintCollector.this.dirtyMarker.remove(localGlassScene1);
/* 286 */         localGlassScene1.clearDirty();
/* 287 */         localGlassScene1.frameRendered();
/*     */       }
/* 289 */       if (PaintCollector.pulseDebug) {
/* 290 */         System.err.println("PC: rendered: remain: " + PaintCollector.this.dirtyMarker.size());
/* 291 */         for (??? = PaintCollector.this.dirtyMarker.iterator(); ???.hasNext(); ) { GlassScene localGlassScene2 = (GlassScene)???.next();
/* 292 */           System.err.println("REMAIN: " + PaintCollector.sceneSize(localGlassScene2));
/*     */         }
/*     */       }
/*     */ 
/* 296 */       synchronized (PaintCollector.this) {
/* 297 */         if (PaintCollector.this.dirtyMarker.isEmpty())
/*     */         {
/* 299 */           PaintCollector.this.toolkit.endPulseRunning();
/*     */ 
/* 301 */           if (PaintCollector.this.needsHint) {
/* 302 */             PaintCollector.this.toolkit.vsyncHint();
/*     */           }
/*     */ 
/* 305 */           if (!PaintCollector.this.released.get()) {
/* 306 */             PaintCollector.this.releaseBarrier();
/*     */           }
/*     */ 
/* 309 */           if (PaintCollector.pulseDebug)
/* 310 */             System.err.println("PC: rendered: renderAll: " + System.nanoTime());
/*     */         }
/*     */       }
/*     */     }
/* 275 */   };
/*     */ 
/* 321 */   private final CompletionListener viewRepainted = new CompletionListener() {
/*     */     public void done(RenderJob paramAnonymousRenderJob) {
/* 323 */       PaintCollector.this.viewPainter.liveRepaint.set(false);
/* 324 */       if (PaintCollector.verbose)
/* 325 */         System.err.println("LR.end: " + System.nanoTime());
/*     */     }
/* 321 */   };
/*     */ 
/*     */   private PaintCollector(QuantumToolkit paramQuantumToolkit, QuantumRenderer paramQuantumRenderer)
/*     */   {
/* 143 */     this.toolkit = paramQuantumToolkit;
/* 144 */     this.renderer = paramQuantumRenderer;
/*     */   }
/*     */ 
/*     */   protected static PaintCollector createInstance(QuantumToolkit paramQuantumToolkit)
/*     */   {
/* 150 */     collector = new PaintCollector(paramQuantumToolkit, QuantumRenderer.getInstance());
/*     */ 
/* 152 */     return collector;
/*     */   }
/*     */ 
/*     */   public static PaintCollector getInstance() {
/* 156 */     return collector;
/*     */   }
/*     */ 
/*     */   public AtomicBoolean hasDirty()
/*     */   {
/* 166 */     return this.hasDirty;
/*     */   }
/*     */ 
/*     */   public boolean isReleased()
/*     */   {
/* 175 */     return this.released.get();
/*     */   }
/*     */ 
/*     */   protected QuantumToolkit toolkit() {
/* 179 */     return this.toolkit;
/*     */   }
/*     */ 
/*     */   protected static String sceneSize(GlassScene paramGlassScene) {
/* 183 */     if ((paramGlassScene instanceof ViewScene)) {
/* 184 */       localObject = ((ViewScene)paramGlassScene).getPlatformView();
/* 185 */       return " scene: " + paramGlassScene.hashCode() + " @ (" + ((View)localObject).getWidth() + "," + ((View)localObject).getHeight() + ")";
/*     */     }
/* 187 */     Object localObject = paramGlassScene.glassStage;
/* 188 */     EmbeddedStage localEmbeddedStage = (EmbeddedStage)localObject;
/* 189 */     return " scene: " + paramGlassScene.hashCode() + ")";
/*     */   }
/*     */ 
/*     */   void addDirtyScene(GlassScene paramGlassScene)
/*     */   {
/* 206 */     if (verbose) {
/* 207 */       System.err.println("PC.addDirtyScene: " + System.nanoTime() + sceneSize(paramGlassScene));
/*     */     }
/* 209 */     synchronized (this) {
/* 210 */       if (!this.dirtyScenes.contains(paramGlassScene)) {
/* 211 */         this.dirtyScenes.add(paramGlassScene);
/*     */       }
/* 213 */       this.hasDirty.set(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   void removeDirtyScene(GlassScene paramGlassScene)
/*     */   {
/* 233 */     if (verbose) {
/* 234 */       System.err.println("PC.removeDirtyScene: " + sceneSize(paramGlassScene));
/*     */     }
/* 236 */     synchronized (this) {
/* 237 */       this.dirtyScenes.remove(paramGlassScene);
/* 238 */       this.hasDirty.set(!this.dirtyScenes.isEmpty());
/*     */     }
/*     */   }
/*     */ 
/*     */   void checkThreads() {
/* 243 */     assert (!Thread.currentThread().equals(QuantumToolkit.getFxUserThread()));
/*     */   }
/*     */ 
/*     */   void releaseScene(GlassScene paramGlassScene)
/*     */   {
/* 255 */     synchronized (this) {
/* 256 */       if ((this.dirtyMarker.remove(paramGlassScene)) && 
/* 257 */         (this.dirtyMarker.isEmpty())) {
/* 258 */         releaseBarrier();
/* 259 */         this.released.set(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void releaseBarrier()
/*     */   {
/*     */     try {
/* 267 */       this.pulseBarrier.await();
/*     */     } catch (InterruptedException localInterruptedException) {
/* 269 */       localInterruptedException.printStackTrace();
/*     */     } catch (BrokenBarrierException localBrokenBarrierException) {
/* 271 */       localBrokenBarrierException.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected CompletionListener getRendered()
/*     */   {
/* 318 */     return this.rendered;
/*     */   }
/*     */ 
/*     */   public void liveRepaintRenderJob(GlassScene paramGlassScene)
/*     */   {
/* 336 */     if ((paramGlassScene instanceof ViewScene)) {
/* 337 */       ViewScene localViewScene = (ViewScene)paramGlassScene;
/*     */ 
/* 339 */       synchronized (this) {
/* 340 */         this.viewPainter = localViewScene.getPen().getPainter();
/*     */ 
/* 342 */         if (!this.viewPainter.liveRepaint.getAndSet(true)) {
/* 343 */           this.renderer.submit(new RenderJob((Runnable)this.viewPainter, this.viewRepainted));
/*     */ 
/* 345 */           if (verbose)
/* 346 */             System.err.println("LR.start: " + System.nanoTime() + sceneSize(paramGlassScene));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void renderAll()
/*     */   {
/* 354 */     if (pulseDebug) {
/* 355 */       System.err.println("PC.renderAll(" + this.dirtyScenes.size() + "): " + System.nanoTime());
/*     */     }
/*     */ 
/* 361 */     if (!this.hasDirty.get()) {
/* 362 */       this.toolkit.endPulseRunning();
/* 363 */       return;
/*     */     }
/*     */ 
/* 366 */     GlassScene localGlassScene = null;
/* 367 */     synchronized (this) {
/* 368 */       this.pulseBarrier.reset();
/* 369 */       this.released.set(false);
/* 370 */       localObject1 = 0; for (Object localObject3 = this.dirtyScenes.size(); localObject1 < localObject3; localObject1++) {
/* 371 */         localGlassScene = (GlassScene)this.dirtyScenes.get(localObject1);
/* 372 */         this.dirtyMarker.add(localGlassScene);
/* 373 */         this.dirtyWork.add(localGlassScene);
/*     */       }
/* 375 */       this.dirtyScenes.clear();
/* 376 */       this.uploadScenes.clear();
/* 377 */       this.syncScenes.clear();
/* 378 */       localObject1 = 0; for (Object localObject4 = this.dirtyWork.size(); localObject1 < localObject4; localObject1++) {
/* 379 */         localGlassScene = (GlassScene)this.dirtyWork.get(localObject1);
/*     */ 
/* 381 */         if (localGlassScene.isSynchronous())
/*     */         {
/* 383 */           this.syncScenes.add(localGlassScene);
/*     */         }
/*     */         else {
/* 386 */           this.uploadScenes.add(localGlassScene);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 392 */     if ((this.uploadScenes.isEmpty()) && (this.syncScenes.isEmpty()))
/*     */     {
/* 394 */       this.toolkit.endPulseRunning();
/* 395 */       return;
/*     */     }
/*     */ 
/* 401 */     this.needsHint = (!this.syncScenes.isEmpty());
/*     */ 
/* 407 */     ??? = 0; for (Object localObject1 = this.uploadScenes.size(); ??? < localObject1; ???++) {
/* 408 */       localGlassScene = (GlassScene)this.uploadScenes.get(???);
/*     */       try {
/* 410 */         localGlassScene.repaint();
/*     */       } catch (Throwable localThrowable1) {
/* 412 */         localThrowable1.printStackTrace();
/*     */       }
/*     */     }
/* 415 */     ??? = 0; for (Object localObject2 = this.syncScenes.size(); ??? < localObject2; ???++) {
/* 416 */       localGlassScene = (GlassScene)this.syncScenes.get(???);
/*     */       try {
/* 418 */         localGlassScene.repaint();
/*     */       } catch (Throwable localThrowable2) {
/* 420 */         localThrowable2.printStackTrace();
/*     */       }
/*     */     }
/*     */     try {
/* 424 */       releaseBarrier();
/*     */     } finally {
/* 426 */       synchronized (this) {
/* 427 */         this.dirtyWork.clear();
/* 428 */         assert (this.dirtyMarker.isEmpty());
/* 429 */         this.hasDirty.set(false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PaintCollector
 * JD-Core Version:    0.6.2
 */