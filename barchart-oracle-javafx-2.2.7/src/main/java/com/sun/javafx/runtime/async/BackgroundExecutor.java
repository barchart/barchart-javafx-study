/*    */ package com.sun.javafx.runtime.async;
/*    */ 
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class BackgroundExecutor
/*    */ {
/*    */   private static ExecutorService instance;
/*    */   private static ScheduledExecutorService timerInstance;
/*    */ 
/*    */   public static synchronized ExecutorService getExecutor()
/*    */   {
/* 49 */     if (instance == null) {
/* 50 */       instance = Executors.newCachedThreadPool(new ThreadFactory() {
/*    */         public Thread newThread(Runnable paramAnonymousRunnable) {
/* 52 */           Thread localThread = new Thread(paramAnonymousRunnable);
/* 53 */           localThread.setPriority(1);
/* 54 */           return localThread;
/*    */         }
/*    */       });
/* 57 */       ((ThreadPoolExecutor)instance).setKeepAliveTime(1L, TimeUnit.SECONDS);
/*    */     }
/*    */ 
/* 60 */     return instance;
/*    */   }
/*    */ 
/*    */   public static synchronized ScheduledExecutorService getTimer() {
/* 64 */     if (timerInstance == null)
/*    */     {
/* 66 */       timerInstance = new ScheduledThreadPoolExecutor(1, new ThreadFactory()
/*    */       {
/*    */         public Thread newThread(Runnable paramAnonymousRunnable) {
/* 69 */           Thread localThread = new Thread(paramAnonymousRunnable);
/* 70 */           localThread.setDaemon(true);
/* 71 */           return localThread;
/*    */         }
/*    */       });
/*    */     }
/*    */ 
/* 76 */     return timerInstance;
/*    */   }
/*    */ 
/*    */   private static synchronized void shutdown() {
/* 80 */     if (instance != null) {
/* 81 */       instance.shutdown();
/* 82 */       instance = null;
/*    */     }
/* 84 */     if (timerInstance != null) {
/* 85 */       timerInstance.shutdown();
/* 86 */       timerInstance = null;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.async.BackgroundExecutor
 * JD-Core Version:    0.6.2
 */