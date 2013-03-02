/*     */ package com.sun.javafx.runtime.async;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import javafx.application.Platform;
/*     */ 
/*     */ public abstract class AbstractAsyncOperation<V>
/*     */   implements AsyncOperation, Callable<V>
/*     */ {
/*     */   private final FutureTask<V> future;
/*     */   protected final AsyncOperationListener listener;
/*  51 */   private int progressGranularity = 100;
/*     */   private int progressMax;
/*     */   private int lastProgress;
/*     */   private int progressIncrement;
/*     */   private int nextProgress;
/*     */   private int bytesRead;
/*     */ 
/*     */   protected AbstractAsyncOperation(final AsyncOperationListener<V> paramAsyncOperationListener)
/*     */   {
/*  55 */     this.listener = paramAsyncOperationListener;
/*     */ 
/*  57 */     Callable local1 = new Callable() {
/*     */       public V call() throws Exception {
/*  59 */         return AbstractAsyncOperation.this.call();
/*     */       }
/*     */     };
/*  63 */     final Runnable local2 = new Runnable() {
/*     */       public void run() {
/*  65 */         if (AbstractAsyncOperation.this.future.isCancelled())
/*  66 */           paramAsyncOperationListener.onCancel();
/*     */         else
/*     */           try
/*     */           {
/*  70 */             paramAsyncOperationListener.onCompletion(AbstractAsyncOperation.this.future.get());
/*     */           }
/*     */           catch (InterruptedException localInterruptedException) {
/*  73 */             paramAsyncOperationListener.onCancel();
/*     */           }
/*     */           catch (ExecutionException localExecutionException) {
/*  76 */             paramAsyncOperationListener.onException(localExecutionException);
/*     */           }
/*     */       }
/*     */     };
/*  81 */     this.future = new FutureTask(local1)
/*     */     {
/*     */       protected void done() {
/*     */         try {
/*  85 */           Platform.runLater(local2);
/*     */         }
/*     */         finally {
/*  88 */           super.done();
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public boolean isCancelled() {
/*  95 */     return this.future.isCancelled();
/*     */   }
/*     */ 
/*     */   public boolean isDone() {
/*  99 */     return this.future.isDone();
/*     */   }
/*     */ 
/*     */   public void cancel() {
/* 103 */     this.future.cancel(true);
/*     */   }
/*     */ 
/*     */   public void start() {
/* 107 */     BackgroundExecutor.getExecutor().execute(this.future);
/*     */   }
/*     */ 
/*     */   protected void notifyProgress() {
/* 111 */     final int i = this.lastProgress;
/* 112 */     final int j = this.progressMax;
/* 113 */     Platform.runLater(new Runnable() {
/*     */       public void run() {
/* 115 */         AbstractAsyncOperation.this.listener.onProgress(i, j);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void addProgress(int paramInt) {
/* 121 */     this.bytesRead += paramInt;
/* 122 */     if (this.bytesRead > this.nextProgress) {
/* 123 */       this.lastProgress = this.bytesRead;
/* 124 */       notifyProgress();
/* 125 */       this.nextProgress = ((this.lastProgress / this.progressIncrement + 1) * this.progressIncrement);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getProgressMax() {
/* 130 */     return this.progressMax;
/*     */   }
/*     */ 
/*     */   protected void setProgressMax(int paramInt) {
/* 134 */     if (paramInt == 0) {
/* 135 */       this.progressIncrement = this.progressGranularity;
/*     */     }
/* 137 */     else if (paramInt == -1) {
/* 138 */       this.progressIncrement = this.progressGranularity;
/*     */     }
/*     */     else {
/* 141 */       this.progressMax = paramInt;
/* 142 */       this.progressIncrement = (paramInt / this.progressGranularity);
/* 143 */       if (this.progressIncrement < 1) {
/* 144 */         this.progressIncrement = 1;
/*     */       }
/*     */     }
/* 147 */     this.nextProgress = ((this.lastProgress / this.progressIncrement + 1) * this.progressIncrement);
/* 148 */     notifyProgress();
/*     */   }
/*     */ 
/*     */   protected int getProgressGranularity() {
/* 152 */     return this.progressGranularity;
/*     */   }
/*     */ 
/*     */   protected void setProgressGranularity(int paramInt) {
/* 156 */     this.progressGranularity = paramInt;
/* 157 */     this.progressIncrement = (this.progressMax / paramInt);
/* 158 */     this.nextProgress = ((this.lastProgress / this.progressIncrement + 1) * this.progressIncrement);
/* 159 */     notifyProgress();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.async.AbstractAsyncOperation
 * JD-Core Version:    0.6.2
 */