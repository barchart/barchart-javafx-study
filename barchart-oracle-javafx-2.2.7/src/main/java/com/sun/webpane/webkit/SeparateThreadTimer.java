/*     */ package com.sun.webpane.webkit;
/*     */ 
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ 
/*     */ class SeparateThreadTimer extends Timer
/*     */   implements Runnable
/*     */ {
/*     */   private Invoker invoker;
/*     */   private FireRunner fireRunner;
/*     */   private Thread thread;
/*     */ 
/*     */   SeparateThreadTimer()
/*     */   {
/*  98 */     this.invoker = Invoker.getInvoker();
/*  99 */     this.fireRunner = new FireRunner(null);
/* 100 */     this.thread = new Thread(this, "WebPane-Timer");
/* 101 */     this.thread.setDaemon(true);
/*     */   }
/*     */ 
/*     */   synchronized void setFireTime(long paramLong)
/*     */   {
/* 120 */     super.setFireTime(paramLong);
/* 121 */     if (this.thread.getState() == Thread.State.NEW) {
/* 122 */       this.thread.start();
/*     */     }
/* 124 */     notifyAll();
/*     */   }
/*     */ 
/*     */   public synchronized void run()
/*     */   {
/*     */     try {
/*     */       while (true) {
/* 131 */         if (this.fireTime > 0L) {
/* 132 */           long l = System.currentTimeMillis();
/* 133 */           while (this.fireTime > l) {
/* 134 */             wait(this.fireTime - l);
/* 135 */             l = System.currentTimeMillis();
/*     */           }
/* 137 */           if (this.fireTime > 0L) {
/* 138 */             this.invoker.invokeOnEventThread(this.fireRunner.forTime(this.fireTime));
/*     */           }
/*     */         }
/* 141 */         wait();
/*     */       }
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyTick() {
/* 150 */     if (!$assertionsDisabled) throw new AssertionError();
/*     */   }
/*     */ 
/*     */   private class FireRunner
/*     */     implements Runnable
/*     */   {
/*     */     private volatile long time;
/*     */ 
/*     */     private FireRunner()
/*     */     {
/*     */     }
/*     */ 
/*     */     Runnable forTime(long paramLong)
/*     */     {
/* 108 */       this.time = paramLong;
/* 109 */       return this;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/* 114 */       SeparateThreadTimer.this.fireTimerEvent(this.time);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.SeparateThreadTimer
 * JD-Core Version:    0.6.2
 */