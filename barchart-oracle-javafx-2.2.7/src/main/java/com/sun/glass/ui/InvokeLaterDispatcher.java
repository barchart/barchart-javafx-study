/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.util.concurrent.BlockingDeque;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ 
/*     */ public final class InvokeLaterDispatcher extends Thread
/*     */ {
/*  27 */   private final BlockingDeque<Runnable> deque = new LinkedBlockingDeque();
/*     */ 
/*  31 */   private final InvokeLaterLock LOCK = new InvokeLaterLock(null);
/*     */ 
/*  34 */   private boolean nestedEventLoopEntered = false;
/*     */ 
/*  37 */   private volatile boolean leavingNestedEventLoop = false;
/*     */   private final InvokeLaterSubmitter invokeLaterSubmitter;
/*     */ 
/*     */   public InvokeLaterDispatcher(InvokeLaterSubmitter invokeLaterSubmitter)
/*     */   {
/*  53 */     setDaemon(true);
/*     */ 
/*  55 */     this.invokeLaterSubmitter = invokeLaterSubmitter;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try
/*     */     {
/*     */       while (true)
/*     */       {
/*  87 */         Runnable r = (Runnable)this.deque.takeFirst();
/*     */ 
/*  89 */         if (this.leavingNestedEventLoop)
/*     */         {
/*  92 */           this.deque.addFirst(r);
/*  93 */           synchronized (this.LOCK) {
/*  94 */             while (this.leavingNestedEventLoop)
/*  95 */               this.LOCK.wait();
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 100 */           Future future = new Future(r);
/* 101 */           this.invokeLaterSubmitter.submitForLaterInvocation(future);
/* 102 */           synchronized (this.LOCK) {
/*     */             try {
/* 104 */               while ((!future.isDone()) && (!this.nestedEventLoopEntered)) {
/* 105 */                 this.LOCK.wait();
/*     */               }
/*     */             }
/*     */             finally
/*     */             {
/* 110 */               this.nestedEventLoopEntered = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (InterruptedException ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void invokeLater(Runnable command)
/*     */   {
/* 126 */     this.deque.addLast(command);
/*     */   }
/*     */ 
/*     */   public void notifyEnteringNestedEventLoop()
/*     */   {
/* 137 */     synchronized (this.LOCK) {
/* 138 */       this.nestedEventLoopEntered = true;
/* 139 */       this.LOCK.notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyLeavingNestedEventLoop()
/*     */   {
/* 147 */     synchronized (this.LOCK) {
/* 148 */       this.leavingNestedEventLoop = true;
/* 149 */       this.LOCK.notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyLeftNestedEventLoop()
/*     */   {
/* 157 */     synchronized (this.LOCK) {
/* 158 */       this.leavingNestedEventLoop = false;
/* 159 */       this.LOCK.notifyAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class Future
/*     */     implements Runnable
/*     */   {
/*  59 */     private boolean done = false;
/*     */     private final Runnable runnable;
/*     */ 
/*     */     public Future(Runnable r)
/*     */     {
/*  63 */       this.runnable = r;
/*     */     }
/*     */ 
/*     */     public boolean isDone()
/*     */     {
/*  72 */       return this.done;
/*     */     }
/*     */ 
/*     */     public void run() {
/*  76 */       this.runnable.run();
/*  77 */       synchronized (InvokeLaterDispatcher.this.LOCK) {
/*  78 */         this.done = true;
/*  79 */         InvokeLaterDispatcher.this.LOCK.notifyAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface InvokeLaterSubmitter
/*     */   {
/*     */     public abstract void submitForLaterInvocation(Runnable paramRunnable);
/*     */   }
/*     */ 
/*     */   private static final class InvokeLaterLock
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.InvokeLaterDispatcher
 * JD-Core Version:    0.6.2
 */