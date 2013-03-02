/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.util.concurrent.Callable;
/*     */ import javafx.application.Platform;
/*     */ 
/*     */ public final class FxEventLoop
/*     */ {
/*  93 */   private static final Impl impl = new Impl(null);
/*     */ 
/*     */   public static void enterNestedLoop() {
/*  96 */     impl.start();
/*     */   }
/*     */ 
/*     */   public static void leaveNestedLoop() {
/* 100 */     impl.stop();
/*     */   }
/*     */ 
/*     */   public static void sendEvent(Runnable paramRunnable) {
/* 104 */     impl.send(paramRunnable);
/*     */   }
/*     */ 
/*     */   public static <V> V sendEvent(Callable<V> paramCallable) {
/* 108 */     return impl.send(paramCallable);
/*     */   }
/*     */ 
/*     */   public static boolean isNestedLoopRunning() {
/* 112 */     return impl.isNestedLoopRunning();
/*     */   }
/*     */ 
/*     */   private static final class Impl extends AbstractEventLoop
/*     */   {
/*  15 */     private final Object NESTED_EVENT_LOOP_KEY = new Object();
/*     */     private Object nestedEventLoopToken;
/*     */     private int depth;
/*     */ 
/*     */     public void send(Runnable paramRunnable)
/*     */     {
/*  22 */       if (Platform.isFxApplicationThread()) {
/*  23 */         paramRunnable.run();
/*  24 */         return;
/*     */       }
/*  26 */       super.send(paramRunnable);
/*     */     }
/*     */ 
/*     */     public <V> V send(Callable<V> paramCallable)
/*     */     {
/*  31 */       if (Platform.isFxApplicationThread()) {
/*     */         try {
/*  33 */           return paramCallable.call();
/*     */         } catch (Exception localException) {
/*  35 */           throw new RuntimeException(localException);
/*     */         }
/*     */       }
/*  38 */       return super.send(paramCallable);
/*     */     }
/*     */ 
/*     */     public void start()
/*     */     {
/*  43 */       if (Platform.isFxApplicationThread())
/*     */       {
/*  45 */         assert (this.depth == 0);
/*     */ 
/*  47 */         this.depth += 1;
/*     */         try {
/*  49 */           this.nestedEventLoopToken = Toolkit.getToolkit().enterNestedEventLoop(this.NESTED_EVENT_LOOP_KEY);
/*     */         }
/*     */         finally {
/*  52 */           this.depth -= 1;
/*     */         }
/*     */       } else {
/*  55 */         Platform.runLater(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  59 */             FxEventLoop.enterNestedLoop();
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */ 
/*     */     public void stop()
/*     */     {
/*  67 */       if (Platform.isFxApplicationThread()) {
/*  68 */         Toolkit.getToolkit().exitNestedEventLoop(this.NESTED_EVENT_LOOP_KEY, this.nestedEventLoopToken);
/*     */ 
/*  70 */         this.nestedEventLoopToken = null;
/*     */       } else {
/*  72 */         send(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  76 */             FxEventLoop.leaveNestedLoop();
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void schedule(Runnable paramRunnable)
/*     */     {
/*  84 */       Platform.runLater(paramRunnable);
/*     */     }
/*     */ 
/*     */     boolean isNestedLoopRunning() {
/*  88 */       assert (Platform.isFxApplicationThread());
/*     */ 
/*  90 */       return this.depth != 0;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.FxEventLoop
 * JD-Core Version:    0.6.2
 */