/*     */ package com.sun.scenario;
/*     */ 
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ 
/*     */ public class StandaloneAccessor extends ToolkitAccessor
/*     */ {
/*     */   private final Map<Object, Object> map;
/*     */   private final ScheduledExecutorService executor;
/*     */   private AtomicReference<Future<?>> refFuture;
/*     */   private StandaloneMasterTimer standaloneMasterTimer;
/*     */ 
/*     */   public StandaloneAccessor()
/*     */   {
/*  43 */     this.map = new HashMap();
/*  44 */     this.executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory()
/*     */     {
/*  46 */       private final ThreadFactory factory = Executors.defaultThreadFactory();
/*     */ 
/*     */       public Thread newThread(Runnable paramAnonymousRunnable)
/*     */       {
/*  50 */         Thread localThread = this.factory.newThread(paramAnonymousRunnable);
/*  51 */         localThread.setDaemon(true);
/*  52 */         return localThread;
/*     */       }
/*     */     });
/*  56 */     this.refFuture = new AtomicReference();
/*  57 */     this.standaloneMasterTimer = new StandaloneMasterTimer(null);
/*     */   }
/*     */ 
/*     */   public Map<Object, Object> getContextMapImpl() {
/*  61 */     return this.map;
/*     */   }
/*     */ 
/*     */   public AbstractMasterTimer getMasterTimerImpl()
/*     */   {
/*  69 */     return this.standaloneMasterTimer;
/*     */   }
/*     */   private class StandaloneMasterTimer extends AbstractMasterTimer {
/*     */     private StandaloneMasterTimer() {
/*     */     }
/*     */     protected boolean shouldUseNanoTime() {
/*  75 */       return true;
/*     */     }
/*     */ 
/*     */     protected void postUpdateAnimationRunnable(final DelayedRunnable paramDelayedRunnable)
/*     */     {
/*  81 */       if (paramDelayedRunnable == null) {
/*  82 */         localObject = (Future)StandaloneAccessor.this.refFuture.get();
/*  83 */         if (localObject != null) {
/*  84 */           ((Future)localObject).cancel(false);
/*  85 */           StandaloneAccessor.this.refFuture.set(null);
/*     */         }
/*  87 */         return;
/*     */       }
/*  89 */       if (StandaloneAccessor.this.refFuture.get() != null) {
/*  90 */         return;
/*     */       }
/*  92 */       Object localObject = StandaloneAccessor.this.executor.schedule(new Runnable() {
/*     */         public void run() {
/*  94 */           StandaloneAccessor.this.refFuture.set(null);
/*  95 */           paramDelayedRunnable.run();
/*     */         }
/*     */       }
/*     */       , paramDelayedRunnable.getDelay(), TimeUnit.MILLISECONDS);
/*     */ 
/*  98 */       StandaloneAccessor.this.refFuture.set(localObject);
/*     */     }
/*     */ 
/*     */     protected int getPulseDuration(int paramInt)
/*     */     {
/* 103 */       int i = paramInt / 60;
/* 104 */       return i;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.StandaloneAccessor
 * JD-Core Version:    0.6.2
 */