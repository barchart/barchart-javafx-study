/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import com.sun.prism.render.RenderJob;
/*    */ import com.sun.prism.render.ToolkitInterface;
/*    */ import com.sun.webpane.platform.Invoker;
/*    */ import java.io.PrintStream;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ public class InvokerImpl extends Invoker
/*    */ {
/*    */   public boolean lock(ReentrantLock paramReentrantLock)
/*    */   {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean unlock(ReentrantLock paramReentrantLock) {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isEventThread() {
/* 29 */     return Toolkit.getToolkit().isFxUserThread();
/*    */   }
/*    */ 
/*    */   public void checkEventThread() {
/* 33 */     Toolkit.getToolkit().checkFxUserThread();
/*    */   }
/*    */ 
/*    */   public void invokeOnEventThread(Runnable paramRunnable) {
/* 37 */     if (isEventThread())
/* 38 */       paramRunnable.run();
/*    */     else
/* 40 */       Toolkit.getToolkit().defer(paramRunnable);
/*    */   }
/*    */ 
/*    */   public void postOnEventThread(Runnable paramRunnable)
/*    */   {
/* 45 */     Toolkit.getToolkit().defer(paramRunnable);
/*    */   }
/*    */ 
/*    */   public void invokeOnRenderThread(Runnable paramRunnable) {
/* 49 */     ToolkitInterface localToolkitInterface = (ToolkitInterface)Toolkit.getToolkit();
/* 50 */     localToolkitInterface.addRenderJob(new RenderJob(paramRunnable));
/*    */   }
/*    */ 
/*    */   public void runOnRenderThread(Runnable paramRunnable) {
/* 54 */     String str = Thread.currentThread().getName();
/* 55 */     if ((!str.equals("JavaFX Application Thread")) && (!str.equals("QuantumRenderer-0"))) {
/* 56 */       System.err.println("runOnRT called from " + str);
/* 57 */       Thread.dumpStack();
/*    */     }
/* 59 */     if (!isEventThread())
/*    */     {
/* 62 */       paramRunnable.run();
/*    */     } else {
/* 64 */       ToolkitInterface localToolkitInterface = (ToolkitInterface)Toolkit.getToolkit();
/* 65 */       FutureTask localFutureTask = new FutureTask(paramRunnable, null);
/* 66 */       localToolkitInterface.addRenderJob(new RenderJob(localFutureTask));
/*    */       try
/*    */       {
/* 69 */         localFutureTask.get();
/*    */       } catch (ExecutionException localExecutionException) {
/* 71 */         throw new AssertionError(localExecutionException);
/*    */       }
/*    */       catch (InterruptedException localInterruptedException)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.InvokerImpl
 * JD-Core Version:    0.6.2
 */