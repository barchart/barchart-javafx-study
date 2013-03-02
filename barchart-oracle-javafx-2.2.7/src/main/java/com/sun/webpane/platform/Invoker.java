/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.perf.PerfLogger;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ public abstract class Invoker
/*    */ {
/*    */   private static Invoker instance;
/* 13 */   private static PerfLogger locksLog = PerfLogger.getLogger("Locks");
/*    */ 
/*    */   public static synchronized void setInvoker(Invoker paramInvoker) {
/* 16 */     instance = paramInvoker;
/*    */   }
/*    */ 
/*    */   public static synchronized Invoker getInvoker() {
/* 20 */     return instance;
/*    */   }
/*    */ 
/*    */   public boolean lock(ReentrantLock paramReentrantLock)
/*    */   {
/* 29 */     if (paramReentrantLock.getHoldCount() == 0)
/*    */     {
/* 31 */       paramReentrantLock.lock();
/* 32 */       locksLog.resumeCount(isEventThread() ? "EventThread" : "RenderThread");
/* 33 */       return true;
/*    */     }
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean unlock(ReentrantLock paramReentrantLock)
/*    */   {
/* 44 */     if (paramReentrantLock.getHoldCount() != 0)
/*    */     {
/* 46 */       locksLog.suspendCount(isEventThread() ? "EventThread" : "RenderThread");
/* 47 */       paramReentrantLock.unlock();
/* 48 */       return true;
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   public abstract boolean isEventThread();
/*    */ 
/*    */   public void checkEventThread()
/*    */   {
/* 62 */     if (!isEventThread())
/* 63 */       throw new IllegalStateException("Current thread is not event thread, current thread: " + Thread.currentThread().getName());
/*    */   }
/*    */ 
/*    */   public abstract void invokeOnEventThread(Runnable paramRunnable);
/*    */ 
/*    */   public abstract void postOnEventThread(Runnable paramRunnable);
/*    */ 
/*    */   public abstract void invokeOnRenderThread(Runnable paramRunnable);
/*    */ 
/*    */   public abstract void runOnRenderThread(Runnable paramRunnable);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.Invoker
 * JD-Core Version:    0.6.2
 */