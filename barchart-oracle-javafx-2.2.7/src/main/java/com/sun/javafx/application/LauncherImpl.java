/*     */ package com.sun.javafx.application;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.jmx.MXExtension;
/*     */ import com.sun.javafx.runtime.SystemProperties;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javafx.application.Application;
/*     */ import javafx.application.ConditionalFeature;
/*     */ import javafx.application.Preloader;
/*     */ import javafx.application.Preloader.ErrorNotification;
/*     */ import javafx.application.Preloader.PreloaderNotification;
/*     */ import javafx.application.Preloader.ProgressNotification;
/*     */ import javafx.application.Preloader.StateChangeNotification;
/*     */ import javafx.application.Preloader.StateChangeNotification.Type;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class LauncherImpl
/*     */ {
/*     */   private static final boolean simulateSlowProgress = false;
/*  53 */   private static AtomicBoolean launchCalled = new AtomicBoolean(false);
/*     */ 
/*  56 */   private static volatile RuntimeException launchException = null;
/*     */ 
/*  60 */   private static Preloader currentPreloader = null;
/*     */ 
/* 144 */   private static volatile boolean error = false;
/* 145 */   private static volatile Throwable pConstructorError = null;
/* 146 */   private static volatile Throwable pInitError = null;
/* 147 */   private static volatile Throwable pStartError = null;
/* 148 */   private static volatile Throwable pStopError = null;
/* 149 */   private static volatile Throwable constructorError = null;
/* 150 */   private static volatile Throwable initError = null;
/* 151 */   private static volatile Throwable startError = null;
/* 152 */   private static volatile Throwable stopError = null;
/*     */ 
/* 476 */   private static Method notifyMethod = null;
/*     */ 
/*     */   public static void launchApplication(Class<? extends Application> paramClass, String[] paramArrayOfString)
/*     */   {
/*  75 */     launchApplication(paramClass, null, paramArrayOfString);
/*     */   }
/*     */ 
/*     */   public static void launchApplication(Class<? extends Application> paramClass, final Class<? extends Preloader> paramClass1, final String[] paramArrayOfString)
/*     */   {
/*  93 */     if (launchCalled.getAndSet(true)) {
/*  94 */       throw new IllegalStateException("Application launch must not be called more than once");
/*     */     }
/*     */ 
/*  97 */     if (!Application.class.isAssignableFrom(paramClass)) {
/*  98 */       throw new IllegalArgumentException("Error: " + paramClass.getName() + " is not a subclass of javafx.application.Application");
/*     */     }
/*     */ 
/* 102 */     if ((paramClass1 != null) && (!Preloader.class.isAssignableFrom(paramClass1))) {
/* 103 */       throw new IllegalArgumentException("Error: " + paramClass1.getName() + " is not a subclass of javafx.application.Preloader");
/*     */     }
/*     */ 
/* 111 */     final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/* 112 */     Thread localThread = new Thread(new Runnable() {
/*     */       public void run() {
/*     */         try {
/* 115 */           LauncherImpl.launchApplication1(this.val$appClass, paramClass1, paramArrayOfString);
/*     */         } catch (RuntimeException localRuntimeException) {
/* 117 */           LauncherImpl.access$102(localRuntimeException);
/*     */         } catch (Exception localException) {
/* 119 */           LauncherImpl.access$102(new RuntimeException("Application launch exception", localException));
/*     */         }
/*     */         catch (Error localError) {
/* 122 */           LauncherImpl.access$102(new RuntimeException("Application launch error", localError));
/*     */         }
/*     */         finally {
/* 125 */           localCountDownLatch.countDown();
/*     */         }
/*     */       }
/*     */     });
/* 129 */     localThread.setName("JavaFX-Launcher");
/* 130 */     localThread.start();
/*     */     try
/*     */     {
/* 134 */       localCountDownLatch.await();
/*     */     } catch (InterruptedException localInterruptedException) {
/* 136 */       throw new RuntimeException("Unexpected exception: ", localInterruptedException);
/*     */     }
/*     */ 
/* 139 */     if (launchException != null)
/* 140 */       throw launchException;
/*     */   }
/*     */ 
/*     */   private static void launchApplication1(Class<? extends Application> paramClass, Class<? extends Preloader> paramClass1, String[] paramArrayOfString)
/*     */     throws Exception
/*     */   {
/* 158 */     if (SystemProperties.isDebug()) {
/* 159 */       MXExtension.initializeIfAvailable();
/*     */     }
/*     */ 
/* 162 */     CountDownLatch localCountDownLatch1 = new CountDownLatch(1);
/* 163 */     PlatformImpl.startup(new Runnable()
/*     */     {
/*     */       public void run() {
/* 166 */         this.val$startupLatch.countDown();
/*     */       }
/*     */     });
/* 171 */     localCountDownLatch1.await();
/*     */ 
/* 173 */     final AtomicBoolean localAtomicBoolean1 = new AtomicBoolean(false);
/* 174 */     AtomicBoolean localAtomicBoolean2 = new AtomicBoolean(false);
/* 175 */     final AtomicBoolean localAtomicBoolean3 = new AtomicBoolean(false);
/* 176 */     AtomicBoolean localAtomicBoolean4 = new AtomicBoolean(false);
/* 177 */     final CountDownLatch localCountDownLatch2 = new CountDownLatch(1);
/* 178 */     final CountDownLatch localCountDownLatch3 = new CountDownLatch(1);
/*     */ 
/* 180 */     PlatformImpl.FinishListener local3 = new PlatformImpl.FinishListener() {
/*     */       public void idle(boolean paramAnonymousBoolean) {
/* 182 */         if (!paramAnonymousBoolean) {
/* 183 */           return;
/*     */         }
/*     */ 
/* 187 */         if (this.val$startCalled.get())
/* 188 */           localCountDownLatch2.countDown();
/* 189 */         else if (localAtomicBoolean1.get())
/* 190 */           localCountDownLatch3.countDown();
/*     */       }
/*     */ 
/*     */       public void exitCalled()
/*     */       {
/* 196 */         localAtomicBoolean3.set(true);
/* 197 */         localCountDownLatch2.countDown();
/*     */       }
/*     */     };
/* 200 */     PlatformImpl.addListener(local3);
/*     */     try
/*     */     {
/* 203 */       Preloader localPreloader = null;
/* 204 */       if (paramClass1 != null)
/*     */       {
/*     */         try
/*     */         {
/* 208 */           Constructor localConstructor1 = paramClass1.getConstructor(new Class[0]);
/* 209 */           localPreloader = (Preloader)localConstructor1.newInstance(new Object[0]);
/*     */ 
/* 211 */           ParametersImpl.registerParameters(localPreloader, new ParametersImpl(paramArrayOfString));
/*     */         } catch (Throwable localThrowable1) {
/* 213 */           System.err.println("Exception in Preloader constructor");
/* 214 */           pConstructorError = localThrowable1;
/* 215 */           error = true;
/*     */         }
/*     */       }
/* 218 */       currentPreloader = localPreloader;
/*     */ 
/* 221 */       if ((currentPreloader != null) && (!error) && (!localAtomicBoolean3.get())) {
/*     */         try
/*     */         {
/* 224 */           currentPreloader.init();
/*     */         } catch (Throwable localThrowable2) {
/* 226 */           System.err.println("Exception in Preloader init method");
/* 227 */           pInitError = localThrowable2;
/* 228 */           error = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 233 */       if ((currentPreloader != null) && (!error) && (!localAtomicBoolean3.get()))
/*     */       {
/* 235 */         PlatformImpl.runAndWait(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 238 */               this.val$pStartCalled.set(true);
/*     */ 
/* 241 */               Stage localStage = new Stage();
/* 242 */               localStage.impl_setPrimary(true);
/* 243 */               LauncherImpl.currentPreloader.start(localStage);
/*     */             } catch (Throwable localThrowable) {
/* 245 */               System.err.println("Exception in Preloader start method");
/* 246 */               LauncherImpl.access$302(localThrowable);
/* 247 */               LauncherImpl.access$402(true);
/*     */             }
/*     */           }
/*     */         });
/* 253 */         if ((!error) && (!localAtomicBoolean3.get())) {
/* 254 */           notifyProgress(currentPreloader, 0.0D);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 260 */       Application localApplication1 = null;
/* 261 */       if ((!error) && (!localAtomicBoolean3.get())) {
/* 262 */         if (currentPreloader != null)
/*     */         {
/* 269 */           notifyProgress(currentPreloader, 1.0D);
/* 270 */           notifyStateChange(currentPreloader, Preloader.StateChangeNotification.Type.BEFORE_LOAD, null);
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 275 */           Constructor localConstructor2 = paramClass.getConstructor(new Class[0]);
/* 276 */           localApplication1 = (Application)localConstructor2.newInstance(new Object[0]);
/*     */ 
/* 278 */           ParametersImpl.registerParameters(localApplication1, new ParametersImpl(paramArrayOfString));
/*     */         } catch (Throwable localThrowable3) {
/* 280 */           System.err.println("Exception in Application constructor");
/* 281 */           constructorError = localThrowable3;
/* 282 */           error = true;
/*     */         }
/*     */       }
/* 285 */       final Application localApplication2 = localApplication1;
/*     */ 
/* 288 */       if ((!error) && (!localAtomicBoolean3.get())) {
/* 289 */         if (currentPreloader != null) {
/* 290 */           notifyStateChange(currentPreloader, Preloader.StateChangeNotification.Type.BEFORE_INIT, localApplication2);
/*     */         }
/*     */ 
/*     */         try
/*     */         {
/* 296 */           localApplication2.init();
/*     */         } catch (Throwable localThrowable4) {
/* 298 */           System.err.println("Exception in Application init method");
/* 299 */           initError = localThrowable4;
/* 300 */           error = true;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 305 */       if ((!error) && (!localAtomicBoolean3.get())) {
/* 306 */         if (currentPreloader != null) {
/* 307 */           notifyStateChange(currentPreloader, Preloader.StateChangeNotification.Type.BEFORE_START, localApplication2);
/*     */         }
/*     */ 
/* 311 */         PlatformImpl.runAndWait(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 314 */               this.val$startCalled.set(true);
/*     */ 
/* 317 */               Stage localStage = new Stage();
/* 318 */               localStage.impl_setPrimary(true);
/* 319 */               localApplication2.start(localStage);
/*     */             } catch (Throwable localThrowable) {
/* 321 */               System.err.println("Exception in Application start method");
/* 322 */               LauncherImpl.access$502(localThrowable);
/* 323 */               LauncherImpl.access$402(true);
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */ 
/* 329 */       if (!error) {
/* 330 */         localCountDownLatch2.await();
/*     */       }
/*     */ 
/* 335 */       if (localAtomicBoolean2.get())
/*     */       {
/* 337 */         PlatformImpl.runAndWait(new Runnable() {
/*     */           public void run() {
/*     */             try {
/* 340 */               this.val$theApp.stop();
/*     */             } catch (Throwable localThrowable) {
/* 342 */               System.err.println("Exception in Application stop method");
/* 343 */               LauncherImpl.access$602(localThrowable);
/* 344 */               LauncherImpl.access$402(true);
/*     */             }
/*     */           }
/*     */ 
/*     */         });
/*     */       }
/*     */ 
/* 351 */       boolean bool1 = PlatformUtil.isMac();
/* 352 */       if (bool1)
/*     */       {
/* 355 */         int j = !PlatformImpl.isSupported(ConditionalFeature.SCENE3D) ? 1 : 0;
/* 356 */         boolean bool2 = ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*     */           public Boolean run() {
/* 358 */             return Boolean.valueOf(Boolean.getBoolean("javafx.keepalive"));
/*     */           }
/*     */         })).booleanValue();
/*     */ 
/* 361 */         if ((j != 0) && (!bool2)) {
/* 362 */           if (constructorError != null)
/* 363 */             constructorError.printStackTrace();
/* 364 */           else if (initError != null)
/* 365 */             initError.printStackTrace();
/* 366 */           else if (startError != null)
/* 367 */             startError.printStackTrace();
/* 368 */           else if (stopError != null) {
/* 369 */             stopError.printStackTrace();
/*     */           }
/* 371 */           System.err.println("JavaFX application launcher: calling System.exit");
/* 372 */           System.exit(0);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 377 */       if (error) {
/* 378 */         if (pConstructorError != null) {
/* 379 */           throw new RuntimeException("Unable to construct Preloader instance: " + paramClass, pConstructorError);
/*     */         }
/* 381 */         if (pInitError != null) {
/* 382 */           throw new RuntimeException("Exception in Preloader init method", pInitError);
/*     */         }
/* 384 */         if (pStartError != null) {
/* 385 */           throw new RuntimeException("Exception in Preloader start method", pStartError);
/*     */         }
/* 387 */         if (pStopError != null)
/* 388 */           throw new RuntimeException("Exception in Preloader stop method", pStopError);
/*     */         String str;
/* 390 */         if (constructorError != null) {
/* 391 */           str = "Unable to construct Application instance: " + paramClass;
/* 392 */           if (!notifyError(str, constructorError))
/* 393 */             throw new RuntimeException(str, constructorError);
/*     */         }
/* 395 */         else if (initError != null) {
/* 396 */           str = "Exception in Application init method";
/* 397 */           if (!notifyError(str, initError))
/* 398 */             throw new RuntimeException(str, initError);
/*     */         }
/* 400 */         else if (startError != null) {
/* 401 */           str = "Exception in Application start method";
/* 402 */           if (!notifyError(str, startError))
/* 403 */             throw new RuntimeException(str, startError);
/*     */         }
/* 405 */         else if (stopError != null) {
/* 406 */           str = "Exception in Application stop method";
/* 407 */           if (!notifyError(str, stopError))
/* 408 */             throw new RuntimeException(str, stopError);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       int i;
/* 413 */       PlatformImpl.removeListener(local3);
/*     */ 
/* 417 */       int k = System.getSecurityManager() != null ? 1 : 0;
/* 418 */       if ((error) && (k != 0))
/* 419 */         System.err.println("Workaround until RT-13281 is implemented: keep toolkit alive");
/*     */       else
/* 421 */         PlatformImpl.tkExit();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void notifyStateChange(Preloader paramPreloader, final Preloader.StateChangeNotification.Type paramType, final Application paramApplication)
/*     */   {
/* 430 */     PlatformImpl.runAndWait(new Runnable() {
/*     */       public void run() {
/* 432 */         this.val$preloader.handleStateChangeNotification(new Preloader.StateChangeNotification(paramType, paramApplication));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static void notifyProgress(Preloader paramPreloader, final double paramDouble)
/*     */   {
/* 439 */     PlatformImpl.runAndWait(new Runnable() {
/*     */       public void run() {
/* 441 */         this.val$preloader.handleProgressNotification(new Preloader.ProgressNotification(paramDouble));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static boolean notifyError(String paramString, final Throwable paramThrowable)
/*     */   {
/* 448 */     final AtomicBoolean localAtomicBoolean = new AtomicBoolean(false);
/* 449 */     PlatformImpl.runAndWait(new Runnable() {
/*     */       public void run() {
/* 451 */         if (LauncherImpl.currentPreloader != null)
/*     */           try {
/* 453 */             Preloader.ErrorNotification localErrorNotification = new Preloader.ErrorNotification(null, this.val$msg, paramThrowable);
/* 454 */             boolean bool = LauncherImpl.currentPreloader.handleErrorNotification(localErrorNotification);
/* 455 */             localAtomicBoolean.set(bool);
/*     */           } catch (Throwable localThrowable) {
/* 457 */             localThrowable.printStackTrace();
/*     */           }
/*     */       }
/*     */     });
/* 463 */     return localAtomicBoolean.get();
/*     */   }
/*     */ 
/*     */   private static void notifyCurrentPreloader(Preloader.PreloaderNotification paramPreloaderNotification) {
/* 467 */     PlatformImpl.runAndWait(new Runnable() {
/*     */       public void run() {
/* 469 */         if (LauncherImpl.currentPreloader != null)
/* 470 */           LauncherImpl.currentPreloader.handleApplicationNotification(this.val$pe);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void notifyPreloader(Application paramApplication, Preloader.PreloaderNotification paramPreloaderNotification)
/*     */   {
/* 479 */     if (launchCalled.get())
/*     */     {
/* 481 */       notifyCurrentPreloader(paramPreloaderNotification);
/* 482 */       return;
/*     */     }
/*     */ 
/* 485 */     synchronized (LauncherImpl.class) {
/* 486 */       if (notifyMethod == null)
/*     */       {
/*     */         try
/*     */         {
/* 490 */           Class localClass = Class.forName("com.sun.deploy.uitoolkit.impl.fx.FXPreloader");
/* 491 */           notifyMethod = localClass.getMethod("notifyCurrentPreloader", new Class[] { Preloader.PreloaderNotification.class });
/*     */         }
/*     */         catch (Exception localException2) {
/* 494 */           localException2.printStackTrace();
/* 495 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 502 */       notifyMethod.invoke(null, new Object[] { paramPreloaderNotification });
/*     */     } catch (Exception localException1) {
/* 504 */       localException1.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private LauncherImpl()
/*     */   {
/* 511 */     throw new InternalError();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.application.LauncherImpl
 * JD-Core Version:    0.6.2
 */