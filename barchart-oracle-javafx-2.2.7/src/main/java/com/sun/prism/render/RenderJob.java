/*    */ package com.sun.prism.render;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.concurrent.CancellationException;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ 
/*    */ public class RenderJob extends FutureTask
/*    */ {
/*    */   private CompletionListener listener;
/*    */   private Object futureReturn;
/*    */ 
/*    */   public RenderJob(Runnable paramRunnable)
/*    */   {
/* 24 */     super(paramRunnable, null);
/*    */   }
/*    */ 
/*    */   public RenderJob(Runnable paramRunnable, CompletionListener paramCompletionListener) {
/* 28 */     super(paramRunnable, null);
/* 29 */     setCompletionListener(paramCompletionListener);
/*    */   }
/*    */ 
/*    */   public void setCompletionListener(CompletionListener paramCompletionListener) {
/* 33 */     this.listener = paramCompletionListener;
/*    */   }
/*    */ 
/*    */   public void run() {
/* 37 */     if (!super.runAndReset()) {
/*    */       try
/*    */       {
/* 40 */         Object localObject = super.get();
/* 41 */         System.err.println("RenderJob.run: failed no exception: " + localObject);
/*    */       } catch (CancellationException localCancellationException) {
/* 43 */         System.err.println("RenderJob.run: task cancelled");
/*    */       } catch (ExecutionException localExecutionException) {
/* 45 */         System.err.println("RenderJob.run: internal exception");
/* 46 */         localExecutionException.getCause().printStackTrace();
/*    */       } catch (Throwable localThrowable1) {
/* 48 */         localThrowable1.printStackTrace();
/*    */       }
/*    */ 
/*    */     }
/* 54 */     else if (this.listener != null)
/*    */       try {
/* 56 */         this.listener.done(this);
/*    */       } catch (Throwable localThrowable2) {
/* 58 */         localThrowable2.printStackTrace();
/*    */       }
/*    */   }
/*    */ 
/*    */   public Object get()
/*    */   {
/* 65 */     return this.futureReturn;
/*    */   }
/*    */ 
/*    */   public void setFutureReturn(Object paramObject) {
/* 69 */     this.futureReturn = paramObject;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.render.RenderJob
 * JD-Core Version:    0.6.2
 */