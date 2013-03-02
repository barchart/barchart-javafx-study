/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.javafx.tk.Toolkit;
/*    */ import com.sun.webpane.platform.EventLoop;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javafx.application.Platform;
/*    */ 
/*    */ class EventLoopImpl extends EventLoop
/*    */ {
/*    */   private static final long DELAY = 20L;
/* 17 */   private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
/*    */ 
/*    */   protected void cycle()
/*    */   {
/* 28 */     final Object localObject = new Object();
/* 29 */     executor.schedule(new Runnable()
/*    */     {
/*    */       public void run() {
/* 32 */         Platform.runLater(new Runnable()
/*    */         {
/*    */           public void run() {
/* 35 */             Toolkit.getToolkit().exitNestedEventLoop(EventLoopImpl.1.this.val$key, null);
/*    */           }
/*    */         });
/*    */       }
/*    */     }
/*    */     , 20L, TimeUnit.MILLISECONDS);
/*    */ 
/* 40 */     Toolkit.getToolkit().enterNestedEventLoop(localObject);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.EventLoopImpl
 * JD-Core Version:    0.6.2
 */