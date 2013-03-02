/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.CountDownLatch;
/*    */ 
/*    */ public abstract class AbstractEventLoop
/*    */ {
/*    */   private Object curCallableValue;
/*    */ 
/*    */   public void send(Runnable paramRunnable)
/*    */   {
/* 78 */     RunnableTask localRunnableTask = new RunnableTask(paramRunnable);
/* 79 */     await(localRunnableTask);
/*    */   }
/*    */ 
/*    */   public <V> V send(Callable<V> paramCallable) {
/* 83 */     CallableTask localCallableTask = new CallableTask(paramCallable);
/* 84 */     await(localCallableTask);
/* 85 */     return localCallableTask.getResult();
/*    */   }
/*    */ 
/*    */   protected abstract void schedule(Runnable paramRunnable);
/*    */ 
/*    */   public abstract void start();
/*    */ 
/*    */   public abstract void stop();
/*    */ 
/*    */   private void await(Task paramTask) {
/* 95 */     schedule(paramTask);
/*    */     try {
/* 97 */       paramTask.await();
/*    */     } catch (InterruptedException localInterruptedException) {
/* 99 */       throw new RuntimeException(localInterruptedException);
/*    */     }
/*    */   }
/*    */ 
/*    */   static final class RunnableTask extends AbstractEventLoop.Task
/*    */   {
/*    */     private final Runnable r;
/*    */ 
/*    */     RunnableTask(Runnable paramRunnable)
/*    */     {
/* 65 */       this.r = paramRunnable;
/*    */     }
/*    */ 
/*    */     public void doRun()
/*    */     {
/* 70 */       this.r.run();
/*    */     }
/*    */   }
/*    */ 
/*    */   static final class CallableTask<V> extends AbstractEventLoop.Task
/*    */   {
/*    */     private V retValue;
/*    */     private final Callable<V> c;
/*    */     private Exception e;
/*    */ 
/*    */     CallableTask(Callable<V> paramCallable)
/*    */     {
/* 40 */       this.c = paramCallable;
/*    */     }
/*    */ 
/*    */     public void doRun()
/*    */     {
/*    */       try {
/* 46 */         this.retValue = this.c.call();
/*    */       } catch (Exception localException) {
/* 48 */         this.e = localException;
/*    */       }
/*    */     }
/*    */ 
/*    */     V getResult() {
/* 53 */       if (this.e == null) {
/* 54 */         return this.retValue;
/*    */       }
/* 56 */       throw new RuntimeException(this.e);
/*    */     }
/*    */   }
/*    */ 
/*    */   static abstract class Task
/*    */     implements Runnable
/*    */   {
/* 15 */     private final CountDownLatch latch = new CountDownLatch(1);
/*    */ 
/*    */     public void run()
/*    */     {
/*    */       try {
/* 20 */         doRun();
/*    */       } finally {
/* 22 */         this.latch.countDown();
/*    */       }
/*    */     }
/*    */ 
/*    */     protected abstract void doRun();
/*    */ 
/*    */     void await() throws InterruptedException {
/* 29 */       this.latch.await();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.AbstractEventLoop
 * JD-Core Version:    0.6.2
 */