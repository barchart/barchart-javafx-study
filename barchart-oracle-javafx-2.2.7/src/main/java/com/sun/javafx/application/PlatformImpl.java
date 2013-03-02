/*     */ package com.sun.javafx.application;
/*     */ 
/*     */ import com.sun.javafx.runtime.SystemProperties;
/*     */ import com.sun.javafx.tk.TKListener;
/*     */ import com.sun.javafx.tk.TKStage;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javafx.application.ConditionalFeature;
/*     */ 
/*     */ public class PlatformImpl
/*     */ {
/*  48 */   private static AtomicBoolean initialized = new AtomicBoolean(false);
/*  49 */   private static AtomicBoolean platformExit = new AtomicBoolean(false);
/*  50 */   private static AtomicBoolean toolkitExit = new AtomicBoolean(false);
/*  51 */   private static CountDownLatch startupLatch = new CountDownLatch(1);
/*  52 */   private static AtomicBoolean listenersRegistered = new AtomicBoolean(false);
/*  53 */   private static TKListener toolkitListener = null;
/*  54 */   private static volatile boolean implicitExit = true;
/*  55 */   private static boolean taskbarApplication = true;
/*  56 */   private static AtomicInteger pendingRunnables = new AtomicInteger(0);
/*  57 */   private static AtomicInteger numWindows = new AtomicInteger(0);
/*  58 */   private static volatile boolean firstWindowShown = false;
/*  59 */   private static volatile boolean lastWindowClosed = false;
/*  60 */   private static AtomicBoolean reallyIdle = new AtomicBoolean(false);
/*  61 */   private static Set<FinishListener> finishListeners = new CopyOnWriteArraySet();
/*     */ 
/*  63 */   private static final Object runLaterLock = new Object();
/*     */ 
/* 307 */   private static final CountDownLatch platformExitLatch = new CountDownLatch(1);
/*     */ 
/*     */   public static void setTaskbarApplication(boolean paramBoolean)
/*     */   {
/*  72 */     taskbarApplication = paramBoolean;
/*     */   }
/*     */ 
/*     */   public static boolean isTaskbarApplication()
/*     */   {
/*  81 */     return taskbarApplication;
/*     */   }
/*     */ 
/*     */   public static void startup(Runnable paramRunnable)
/*     */   {
/*  95 */     if (platformExit.get()) {
/*  96 */       throw new IllegalStateException("Platform.exit has been called");
/*     */     }
/*     */ 
/*  99 */     if (initialized.getAndSet(true))
/*     */     {
/* 101 */       runLater(paramRunnable);
/* 102 */       return;
/*     */     }
/*     */ 
/* 105 */     if (!taskbarApplication) {
/* 106 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Void run() {
/* 108 */           System.setProperty("com.sun.glass.taskbarApplication", "false");
/* 109 */           return null;
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/* 116 */     toolkitListener = new TKListener() {
/*     */       public void changedTopLevelWindows(List<TKStage> paramAnonymousList) {
/* 118 */         PlatformImpl.numWindows.set(paramAnonymousList.size());
/* 119 */         PlatformImpl.access$100();
/*     */       }
/*     */     };
/* 122 */     Toolkit.getToolkit().addTkListener(toolkitListener);
/*     */ 
/* 124 */     Toolkit.getToolkit().startup(new Runnable() {
/*     */       public void run() {
/* 126 */         PlatformImpl.startupLatch.countDown();
/* 127 */         this.val$r.run();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static void waitForStart()
/*     */   {
/* 136 */     if (startupLatch.getCount() > 0L)
/*     */       try {
/* 138 */         startupLatch.await();
/*     */       } catch (InterruptedException localInterruptedException) {
/* 140 */         localInterruptedException.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public static boolean isFxApplicationThread()
/*     */   {
/* 146 */     return Toolkit.getToolkit().isFxUserThread();
/*     */   }
/*     */ 
/*     */   public static void runLater(Runnable paramRunnable) {
/* 150 */     runLater(paramRunnable, false);
/*     */   }
/*     */ 
/*     */   private static void runLater(Runnable paramRunnable, boolean paramBoolean) {
/* 154 */     if (!initialized.get()) {
/* 155 */       throw new IllegalStateException("Toolkit not initialized");
/*     */     }
/*     */ 
/* 158 */     pendingRunnables.incrementAndGet();
/* 159 */     waitForStart();
/*     */ 
/* 161 */     if (SystemProperties.isDebug()) {
/* 162 */       Toolkit.getToolkit().pauseCurrentThread();
/*     */     }
/*     */ 
/* 165 */     synchronized (runLaterLock) {
/* 166 */       if ((!paramBoolean) && (toolkitExit.get()))
/*     */       {
/* 168 */         pendingRunnables.decrementAndGet();
/* 169 */         return;
/*     */       }
/*     */ 
/* 172 */       final AccessControlContext localAccessControlContext = AccessController.getContext();
/* 173 */       Toolkit.getToolkit().defer(new Runnable() {
/*     */         public void run() {
/*     */           try {
/* 176 */             AccessController.doPrivileged(new PrivilegedAction()
/*     */             {
/*     */               public Void run() {
/* 179 */                 PlatformImpl.4.this.val$r.run();
/* 180 */                 return null;
/*     */               }
/*     */             }
/*     */             , localAccessControlContext);
/*     */ 
/* 183 */             PlatformImpl.pendingRunnables.decrementAndGet();
/* 184 */             PlatformImpl.access$100();
/*     */           } catch (Throwable localThrowable) {
/* 186 */             System.err.println("Exception in runnable");
/* 187 */             localThrowable.printStackTrace();
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void runAndWait(Runnable paramRunnable) {
/* 195 */     runAndWait(paramRunnable, false);
/*     */   }
/*     */ 
/*     */   private static void runAndWait(Runnable paramRunnable, boolean paramBoolean) {
/* 199 */     if (SystemProperties.isDebug()) {
/* 200 */       Toolkit.getToolkit().pauseCurrentThread();
/*     */     }
/*     */ 
/* 203 */     if (isFxApplicationThread()) {
/*     */       try {
/* 205 */         paramRunnable.run();
/*     */       } catch (Throwable localThrowable) {
/* 207 */         System.err.println("Exception in runnable");
/* 208 */         localThrowable.printStackTrace();
/*     */       }
/*     */     } else {
/* 211 */       final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/* 212 */       runLater(new Runnable() {
/*     */         public void run() {
/*     */           try {
/* 215 */             this.val$r.run();
/*     */           } catch (Throwable localThrowable) {
/* 217 */             System.err.println("Exception in runnable");
/* 218 */             localThrowable.printStackTrace();
/*     */           } finally {
/* 220 */             localCountDownLatch.countDown();
/*     */           }
/*     */         }
/*     */       }
/*     */       , paramBoolean);
/*     */ 
/* 225 */       if ((!paramBoolean) && (toolkitExit.get())) {
/* 226 */         throw new IllegalStateException("Toolkit has exited");
/*     */       }
/*     */       try
/*     */       {
/* 230 */         localCountDownLatch.await();
/*     */       } catch (InterruptedException localInterruptedException) {
/* 232 */         localInterruptedException.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setImplicitExit(boolean paramBoolean) {
/* 238 */     implicitExit = paramBoolean;
/* 239 */     checkIdle();
/*     */   }
/*     */ 
/*     */   public static boolean isImplicitExit() {
/* 243 */     return implicitExit;
/*     */   }
/*     */ 
/*     */   public static void addListener(FinishListener paramFinishListener) {
/* 247 */     listenersRegistered.set(true);
/* 248 */     finishListeners.add(paramFinishListener);
/*     */   }
/*     */ 
/*     */   public static void removeListener(FinishListener paramFinishListener) {
/* 252 */     finishListeners.remove(paramFinishListener);
/* 253 */     listenersRegistered.set(!finishListeners.isEmpty());
/*     */   }
/*     */ 
/*     */   private static void notifyFinishListeners(boolean paramBoolean) {
/* 257 */     for (FinishListener localFinishListener : finishListeners)
/* 258 */       if (paramBoolean)
/* 259 */         localFinishListener.exitCalled();
/*     */       else
/* 261 */         localFinishListener.idle(implicitExit);
/*     */   }
/*     */ 
/*     */   private static void checkIdle()
/*     */   {
/* 269 */     int i = 0;
/*     */ 
/* 271 */     synchronized (PlatformImpl.class) {
/* 272 */       int j = numWindows.get();
/* 273 */       if (j > 0) {
/* 274 */         firstWindowShown = true;
/* 275 */         lastWindowClosed = false;
/* 276 */         reallyIdle.set(false);
/* 277 */       } else if ((j == 0) && (firstWindowShown)) {
/* 278 */         lastWindowClosed = true;
/*     */       }
/*     */ 
/* 284 */       if ((lastWindowClosed) && (pendingRunnables.get() == 0))
/*     */       {
/* 286 */         if (reallyIdle.getAndSet(true))
/*     */         {
/* 288 */           i = 1;
/* 289 */           lastWindowClosed = false;
/*     */         }
/*     */         else {
/* 292 */           runLater(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     }
/* 301 */     if (i != 0)
/* 302 */       notifyFinishListeners(false);
/*     */   }
/*     */ 
/*     */   static CountDownLatch test_getPlatformExitLatch()
/*     */   {
/* 309 */     return platformExitLatch;
/*     */   }
/*     */ 
/*     */   public static void tkExit() {
/* 313 */     if (toolkitExit.getAndSet(true)) {
/* 314 */       return;
/*     */     }
/*     */ 
/* 317 */     if (initialized.get())
/*     */     {
/* 320 */       runAndWait(new Runnable()
/*     */       {
/*     */         public void run() {
/* 323 */           Toolkit.getToolkit().exit();
/*     */         }
/*     */       }
/*     */       , true);
/*     */ 
/* 327 */       Toolkit.getToolkit().removeTkListener(toolkitListener);
/* 328 */       toolkitListener = null;
/* 329 */       platformExitLatch.countDown();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void exit()
/*     */   {
/* 335 */     platformExit.set(true);
/*     */ 
/* 338 */     if (listenersRegistered.get()) {
/* 339 */       notifyFinishListeners(true);
/*     */     }
/*     */     else
/* 342 */       tkExit();
/*     */   }
/*     */ 
/*     */   public static boolean isSupported(ConditionalFeature paramConditionalFeature)
/*     */   {
/* 347 */     return Toolkit.getToolkit().isSupported(paramConditionalFeature);
/*     */   }
/*     */ 
/*     */   public static abstract interface FinishListener
/*     */   {
/*     */     public abstract void idle(boolean paramBoolean);
/*     */ 
/*     */     public abstract void exitCalled();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.application.PlatformImpl
 * JD-Core Version:    0.6.2
 */