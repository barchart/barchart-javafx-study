/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.GraphicsResource;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.render.CompletionListener;
/*     */ import com.sun.prism.render.RenderJob;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.prism.PrFilterContext;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RunnableFuture;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public final class QuantumRenderer extends ThreadPoolExecutor
/*     */ {
/*  39 */   private static boolean usePurgatory = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Boolean run() {
/*  42 */       return Boolean.valueOf(Boolean.getBoolean("decora.purgatory")); }  } )).booleanValue();
/*     */   private Thread _renderer;
/*  48 */   private Throwable _initThrowable = null;
/*  49 */   private CountDownLatch initLatch = new CountDownLatch(1);
/*     */   private static QuantumRenderer theInstance;
/*     */ 
/*  52 */   private QuantumRenderer() { super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
/*  53 */     setThreadFactory(new QuantumThreadFactory(null)); }
/*     */ 
/*     */   protected Throwable initThrowable()
/*     */   {
/*  57 */     return this._initThrowable;
/*     */   }
/*     */ 
/*     */   private void setInitThrowable(Throwable paramThrowable) {
/*  61 */     this._initThrowable = paramThrowable;
/*     */   }
/*     */ 
/*     */   protected void createResourceFactory()
/*     */   {
/* 133 */     final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/*     */ 
/* 135 */     CompletionListener local2 = new CompletionListener() {
/*     */       public void done(RenderJob paramAnonymousRenderJob) {
/* 137 */         localCountDownLatch.countDown();
/*     */       }
/*     */     };
/* 141 */     Runnable local3 = new Runnable() {
/*     */       public void run() {
/* 143 */         ResourceFactory localResourceFactory = GraphicsPipeline.getDefaultResourceFactory();
/*     */       }
/*     */     };
/* 147 */     RenderJob localRenderJob = new RenderJob(local3, local2);
/*     */ 
/* 149 */     submit(localRenderJob);
/*     */     try
/*     */     {
/* 152 */       localCountDownLatch.await();
/*     */     } catch (Throwable localThrowable) {
/* 154 */       localThrowable.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void disposePresentable(Presentable paramPresentable)
/*     */   {
/* 163 */     assert (!Thread.currentThread().equals(this._renderer));
/*     */ 
/* 165 */     if ((paramPresentable instanceof GraphicsResource)) {
/* 166 */       final GraphicsResource localGraphicsResource = (GraphicsResource)paramPresentable;
/*     */ 
/* 168 */       Runnable local4 = new Runnable() {
/*     */         public void run() {
/* 170 */           localGraphicsResource.dispose();
/*     */         }
/*     */       };
/* 174 */       RenderJob localRenderJob = new RenderJob(local4, null);
/*     */ 
/* 176 */       submit(localRenderJob);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void stopRenderer() {
/* 181 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 183 */         QuantumRenderer.this.shutdown();
/* 184 */         return null;
/*     */       }
/*     */     });
/* 187 */     if (PrismSettings.verbose) {
/* 188 */       System.out.println("QuantumRenderer: shutdown");
/*     */     }
/*     */ 
/* 191 */     assert (isShutdown());
/*     */ 
/* 198 */     theInstance = null;
/*     */   }
/*     */ 
/*     */   protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT) {
/* 202 */     return (RenderJob)paramRunnable;
/*     */   }
/*     */ 
/*     */   protected Future submitRenderJob(RenderJob paramRenderJob) {
/* 206 */     return submit(paramRenderJob);
/*     */   }
/*     */ 
/*     */   public void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
/*     */   {
/* 212 */     super.afterExecute(paramRunnable, paramThrowable);
/*     */ 
/* 219 */     if (usePurgatory) {
/* 220 */       Screen localScreen = Screen.getMainScreen();
/* 221 */       Renderer localRenderer = Renderer.getRenderer(PrFilterContext.getInstance(localScreen));
/* 222 */       localRenderer.releasePurgatory();
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean checkRendererIdle() {
/* 227 */     PaintCollector localPaintCollector = PaintCollector.getInstance();
/* 228 */     int i = (AbstractPainter.renderLock.isLocked()) && (!AbstractPainter.renderLock.isHeldByCurrentThread()) && (!localPaintCollector.isReleased()) ? 1 : 0;
/*     */ 
/* 232 */     if ((PrismSettings.threadCheck) && (i != 0)) {
/* 233 */       System.err.println("ERROR: PrismPen / FX threads co-running: DIRTY: " + localPaintCollector.hasDirty().get());
/*     */ 
/* 235 */       QuantumToolkit localQuantumToolkit = (QuantumToolkit)QuantumToolkit.getToolkit();
/*     */       StackTraceElement localStackTraceElement;
/* 236 */       for (localStackTraceElement : QuantumToolkit.getFxUserThread().getStackTrace()) {
/* 237 */         System.err.println("FX: " + localStackTraceElement);
/*     */       }
/* 239 */       for (localStackTraceElement : this._renderer.getStackTrace()) {
/* 240 */         System.err.println("QR: " + localStackTraceElement);
/*     */       }
/*     */     }
/* 243 */     return i == 0;
/*     */   }
/*     */ 
/*     */   public static synchronized QuantumRenderer getInstance()
/*     */   {
/* 248 */     if (theInstance == null) {
/* 249 */       synchronized (QuantumRenderer.class) {
/* 250 */         QuantumRenderer localQuantumRenderer = null;
/*     */         try {
/* 252 */           localQuantumRenderer = new QuantumRenderer();
/* 253 */           localQuantumRenderer.prestartCoreThread();
/*     */ 
/* 255 */           localQuantumRenderer.initLatch.await();
/*     */         } catch (Throwable localThrowable) {
/* 257 */           localQuantumRenderer.setInitThrowable(localThrowable);
/* 258 */           if (PrismSettings.verbose) {
/* 259 */             localThrowable.printStackTrace();
/*     */           }
/*     */         }
/* 262 */         if (localQuantumRenderer.initThrowable() != null)
/*     */         {
/* 266 */           if (PrismSettings.noFallback) {
/* 267 */             System.err.println("Cannot initialize a graphics pipeline, and Prism fallback is disabled");
/* 268 */             throw new InternalError("Could not initialize prism toolkit, and the fallback is disabled.");
/*     */           }
/*     */ 
/* 271 */           throw new RuntimeException(localQuantumRenderer.initThrowable());
/*     */         }
/*     */ 
/* 274 */         theInstance = localQuantumRenderer;
/*     */       }
/*     */     }
/* 277 */     return theInstance;
/*     */   }
/*     */ 
/*     */   private class QuantumThreadFactory
/*     */     implements ThreadFactory
/*     */   {
/* 106 */     final AtomicInteger threadNumber = new AtomicInteger(0);
/*     */ 
/*     */     private QuantumThreadFactory() {  } 
/* 109 */     public Thread newThread(Runnable paramRunnable) { final QuantumRenderer.PipelineRunnable localPipelineRunnable = new QuantumRenderer.PipelineRunnable(QuantumRenderer.this, paramRunnable);
/* 110 */       QuantumRenderer.this._renderer = ((Thread)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Thread run() {
/* 113 */           Thread localThread = new Thread(localPipelineRunnable);
/* 114 */           localThread.setName("QuantumRenderer-" + QuantumRenderer.QuantumThreadFactory.this.threadNumber.getAndIncrement());
/* 115 */           localThread.setDaemon(true);
/* 116 */           localThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
/*     */             public void uncaughtException(Thread paramAnonymous2Thread, Throwable paramAnonymous2Throwable) {
/* 118 */               System.err.println(paramAnonymous2Thread.getName() + " uncaught: " + paramAnonymous2Throwable.getClass().getName());
/* 119 */               paramAnonymous2Throwable.printStackTrace();
/*     */             }
/*     */           });
/* 122 */           return localThread;
/*     */         }
/*     */       }));
/* 126 */       assert (this.threadNumber.get() == 1);
/*     */ 
/* 128 */       return QuantumRenderer.this._renderer;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PipelineRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private Runnable work;
/*     */ 
/*     */     public PipelineRunnable(Runnable arg2)
/*     */     {
/*     */       Object localObject;
/*  68 */       this.work = localObject;
/*     */     }
/*     */ 
/*     */     public void init() {
/*     */       try {
/*  73 */         if (GraphicsPipeline.createPipeline() == null) {
/*  74 */           localObject1 = "Error initializing QuantumRenderer: no suitable pipeline found";
/*  75 */           System.err.println((String)localObject1);
/*  76 */           throw new RuntimeException((String)localObject1);
/*     */         }
/*  78 */         Object localObject1 = GraphicsPipeline.getPipeline().getDeviceDetails();
/*  79 */         Application.setDeviceDetails((Map)localObject1);
/*     */       }
/*     */       catch (Throwable localThrowable) {
/*  82 */         QuantumRenderer.this.setInitThrowable(localThrowable);
/*     */       } finally {
/*  84 */         QuantumRenderer.this.initLatch.countDown();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void cleanup() {
/*  89 */       GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/*  90 */       if (localGraphicsPipeline != null)
/*  91 */         localGraphicsPipeline.dispose();
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       try {
/*  97 */         init();
/*  98 */         this.work.run();
/*     */       } finally {
/* 100 */         cleanup();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.QuantumRenderer
 * JD-Core Version:    0.6.2
 */