/*     */ package com.sun.javafx.applet;
/*     */ 
/*     */ import com.sun.applet2.Applet2;
/*     */ import com.sun.applet2.Applet2Context;
/*     */ import com.sun.applet2.preloader.event.ErrorEvent;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.DeployPerfLogger;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.FXPreloader;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.FXWindow;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.Utils;
/*     */ import com.sun.javafx.application.ParametersImpl;
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ import com.sun.javafx.perf.PerformanceTracker;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AllPermission;
/*     */ import java.security.PermissionCollection;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import javafx.application.Application;
/*     */ import javafx.application.Application.Parameters;
/*     */ import javafx.application.Platform;
/*     */ import javafx.stage.Stage;
/*     */ import sun.misc.PerformanceLogger;
/*     */ 
/*     */ public class FXApplet2
/*     */   implements Applet2
/*     */ {
/*     */   static final int SANDBOXAPP_DESTROY_TIMEOUT = 200;
/*     */   static final String JAVFX_APPLICATION_PARAM = "javafx.application";
/*     */   private Application application;
/*     */   private Applet2Context a2c;
/*     */   private FXWindow window;
/*  39 */   private Class<? extends Application> applicationClass = null;
/*     */ 
/*  99 */   private boolean isAborted = false;
/*     */ 
/*     */   public FXApplet2(Class<? extends Application> mainClass, FXWindow window)
/*     */   {
/*  44 */     this.applicationClass = mainClass;
/*  45 */     this.window = window;
/*     */   }
/*     */ 
/*     */   public void init(Applet2Context ac)
/*     */   {
/*  54 */     synchronized (this) {
/*  55 */       if (this.isAborted) {
/*  56 */         return;
/*     */       }
/*     */     }
/*     */ 
/*  60 */     DeployPerfLogger.timestamp("(start) application constructor");
/*     */     try
/*     */     {
/*  63 */       this.application = ((Application)this.applicationClass.newInstance());
/*     */     } catch (Throwable e) {
/*  65 */       e.printStackTrace();
/*  66 */       FXPreloader.notifyCurrentPreloaderOnError(new ErrorEvent(null, "Failed to instantiate application.", e));
/*     */ 
/*  69 */       if ((e instanceof ClassCastException)) {
/*  70 */         throw new UnsupportedOperationException("In the JavaFX mode we only support applications extending JavaFX Application class.", e);
/*     */       }
/*     */ 
/*  75 */       return;
/*     */     }
/*  77 */     this.a2c = ac;
/*  78 */     DeployPerfLogger.timestamp("(done) application constructor");
/*     */ 
/*  80 */     String[] unnamed = Utils.getUnnamed(this.a2c);
/*  81 */     Map named = Utils.getNamedParameters(this.a2c);
/*  82 */     ParametersImpl.registerParameters(this.application, new ParametersImpl(named, unnamed));
/*     */ 
/*  85 */     DeployPerfLogger.timestamp("(start) application.init()");
/*     */     try {
/*  87 */       this.application.init();
/*     */     } catch (Throwable e) {
/*  89 */       e.printStackTrace();
/*  90 */       FXPreloader.notifyCurrentPreloaderOnError(new ErrorEvent(null, "Failed to init application.", e));
/*     */     }
/*     */ 
/*  93 */     DeployPerfLogger.timestamp("(done) application.init()");
/*     */   }
/*     */ 
/*     */   public Application getApplication() {
/*  97 */     return this.application;
/*     */   }
/*     */ 
/*     */   public synchronized void abortLaunch()
/*     */   {
/* 105 */     this.isAborted = true;
/*     */   }
/*     */ 
/*     */   public void start() {
/* 109 */     synchronized (this) {
/* 110 */       if (this.isAborted) {
/* 111 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 115 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 119 */         if (FXApplet2.this.application != null) {
/* 120 */           DeployPerfLogger.timestamp("(start) application stage");
/*     */           Stage appletStage;
/*     */           Stage appletStage;
/* 122 */           if (FXApplet2.this.window == null)
/* 123 */             appletStage = new Stage();
/*     */           else {
/* 125 */             appletStage = FXApplet2.this.window.getAppletStage();
/*     */           }
/* 127 */           DeployPerfLogger.timestamp("(done) application stage");
/*     */ 
/* 129 */           DeployPerfLogger.timestamp("(start) application.start()");
/*     */           try {
/* 131 */             FXApplet2.this.application.start(appletStage);
/*     */ 
/* 134 */             if (PerformanceTracker.isLoggingEnabled()) {
/* 135 */               String str = (String)FXApplet2.this.application.getParameters().getNamed().get("sun_perflog_fx_launchtime");
/*     */ 
/* 137 */               if ((str != null) && (!str.equals(""))) {
/* 138 */                 long launchT = Long.parseLong(str);
/* 139 */                 PerformanceLogger.setStartTime("LaunchTime", launchT);
/*     */               }
/*     */             }
/*     */           } catch (Throwable e) {
/* 143 */             e.printStackTrace();
/* 144 */             FXPreloader.notifyCurrentPreloaderOnError(new ErrorEvent(null, "Failed to start application.", e));
/*     */           }
/*     */ 
/* 148 */           DeployPerfLogger.timestamp("(done) application.start()");
/*     */         } else {
/* 150 */           System.err.println("application is null?");
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*     */   }
/*     */ 
/*     */   private boolean isSandboxApplication() {
/*     */     try {
/* 162 */       ProtectionDomain pd = this.applicationClass.getProtectionDomain();
/* 163 */       if (pd.getPermissions().implies(new AllPermission()))
/*     */       {
/* 165 */         return false;
/*     */       }
/*     */     } catch (SecurityException se) {
/* 168 */       se.printStackTrace();
/*     */     }
/*     */ 
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */   public void destroy() {
/* 175 */     synchronized (this) {
/* 176 */       if (this.isAborted) {
/* 177 */         return;
/*     */       }
/*     */     }
/* 180 */     PlatformImpl.runAndWait(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 184 */         if (FXApplet2.this.isSandboxApplication())
/*     */         {
/* 187 */           Timer exitTimer = new Timer("Exit timer", true);
/* 188 */           TimerTask exitTask = new TimerTask()
/*     */           {
/*     */             public void run() {
/* 191 */               System.exit(0);
/*     */             }
/*     */           };
/* 194 */           exitTimer.schedule(exitTask, 200L);
/*     */         }
/*     */         try
/*     */         {
/* 198 */           FXApplet2.this.application.stop();
/*     */         } catch (Throwable e) {
/* 200 */           e.printStackTrace();
/* 201 */           FXPreloader.notifyCurrentPreloaderOnError(new ErrorEvent(null, "Failed to stop application.", e));
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.applet.FXApplet2
 * JD-Core Version:    0.6.2
 */