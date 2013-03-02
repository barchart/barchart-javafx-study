/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ public abstract class Timer
/*    */ {
/*    */   private static final int UNSET_PERIOD = -1;
/*    */   private final Runnable runnable;
/*    */   private long ptr;
/* 22 */   private int period = -1;
/*    */ 
/*    */   protected abstract long _start(Runnable paramRunnable, int paramInt);
/*    */ 
/*    */   protected abstract void _stop(long paramLong);
/*    */ 
/*    */   protected Timer(Runnable runnable)
/*    */   {
/* 34 */     if (runnable == null) {
/* 35 */       throw new IllegalArgumentException("runnable shouldn't be null");
/*    */     }
/* 37 */     this.runnable = runnable;
/*    */   }
/*    */ 
/*    */   public static int getMinPeriod()
/*    */   {
/* 44 */     return Application.GetApplication().staticTimer_getMinPeriod();
/*    */   }
/*    */ 
/*    */   public static int getMaxPeriod()
/*    */   {
/* 51 */     return Application.GetApplication().staticTimer_getMaxPeriod();
/*    */   }
/*    */ 
/*    */   public synchronized void start(int period)
/*    */   {
/* 61 */     if ((period < getMinPeriod()) || (period > getMaxPeriod())) {
/* 62 */       throw new IllegalArgumentException("period is out of range");
/*    */     }
/*    */ 
/* 65 */     if (this.ptr != 0L) {
/* 66 */       stop();
/*    */     }
/*    */ 
/* 69 */     this.ptr = _start(this.runnable, period);
/* 70 */     if (this.ptr == 0L) {
/* 71 */       this.period = -1;
/* 72 */       throw new RuntimeException("Failed to start the timer");
/*    */     }
/* 74 */     this.period = period;
/*    */   }
/*    */ 
/*    */   public synchronized void stop()
/*    */   {
/* 82 */     if (this.ptr != 0L) {
/* 83 */       _stop(this.ptr);
/* 84 */       this.ptr = 0L;
/* 85 */       this.period = -1;
/*    */     }
/*    */   }
/*    */ 
/*    */   public synchronized boolean isRunning()
/*    */   {
/* 94 */     return this.period != -1;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Timer
 * JD-Core Version:    0.6.2
 */