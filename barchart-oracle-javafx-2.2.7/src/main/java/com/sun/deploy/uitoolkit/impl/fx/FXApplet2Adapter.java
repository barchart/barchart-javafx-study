/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.applet2.Applet2;
/*     */ import com.sun.applet2.Applet2Context;
/*     */ import com.sun.applet2.preloader.Preloader;
/*     */ import com.sun.deploy.uitoolkit.Applet2Adapter;
/*     */ import com.sun.deploy.uitoolkit.Window;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.ErrorPane;
/*     */ import com.sun.javafx.applet.ExperimentalExtensions;
/*     */ import com.sun.javafx.applet.FXApplet2;
/*     */ import com.sun.javafx.applet.HostServicesImpl;
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ import com.sun.javafx.application.PlatformImpl.FinishListener;
/*     */ import javafx.application.Platform;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class FXApplet2Adapter extends Applet2Adapter
/*     */ {
/*  24 */   FXApplet2 applet2 = null;
/*  25 */   FXPreloader preLoader = null;
/*     */   private PlatformImpl.FinishListener finishListener;
/*  27 */   private boolean exitOnIdle = false;
/*  28 */   private static boolean platformDestroyed = false;
/*     */ 
/* 121 */   private static FXApplet2Adapter adapter = null;
/*     */ 
/* 127 */   private FXWindow window = null;
/*     */ 
/*     */   FXApplet2Adapter(Applet2Context ctx)
/*     */   {
/*  31 */     super(ctx);
/*  32 */     DeployPerfLogger.setContext(ctx);
/*  33 */     synchronized (FXApplet2Adapter.class) {
/*  34 */       adapter = this;
/*     */     }
/*  36 */     ExperimentalExtensions.init(ctx);
/*  37 */     HostServicesImpl.init(ctx);
/*  38 */     installFinishListener();
/*     */   }
/*     */ 
/*     */   void abortApplet() {
/*  42 */     if (this.applet2 != null)
/*  43 */       this.applet2.abortLaunch();
/*     */   }
/*     */ 
/*     */   synchronized void setExitOnIdle(boolean val) {
/*  47 */     this.exitOnIdle = val;
/*     */   }
/*     */ 
/*     */   synchronized boolean isExitOnIdle() {
/*  51 */     return this.exitOnIdle;
/*     */   }
/*     */ 
/*     */   static synchronized boolean isPlatformDestroyed() {
/*  55 */     return platformDestroyed;
/*     */   }
/*     */ 
/*     */   private static synchronized void platformExit() {
/*  59 */     platformDestroyed = true;
/*  60 */     PlatformImpl.tkExit();
/*     */   }
/*     */ 
/*     */   private void installFinishListener() {
/*  64 */     this.finishListener = new PlatformImpl.FinishListener()
/*     */     {
/*     */       private void destroyIfNeeded()
/*     */       {
/*  68 */         if (FXApplet2Adapter.this.window == null)
/*     */         {
/*     */           try
/*     */           {
/*  73 */             if (FXApplet2Adapter.this.applet2 != null) {
/*  74 */               FXApplet2Adapter.this.applet2.stop();
/*     */             }
/*  76 */             if (FXApplet2Adapter.this.preLoader != null) {
/*  77 */               FXApplet2Adapter.this.preLoader.stop();
/*     */             }
/*  79 */             if (FXApplet2Adapter.this.applet2 != null) {
/*  80 */               FXApplet2Adapter.this.applet2.destroy();
/*     */             }
/*  82 */             FXApplet2Adapter.this.applet2 = null;
/*     */           } catch (Exception e) {
/*  84 */             e.printStackTrace();
/*     */           } finally {
/*  86 */             PlatformImpl.removeListener(FXApplet2Adapter.this.finishListener);
/*     */ 
/*  90 */             FXApplet2Adapter.access$200();
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */       public void idle(boolean implicitExit)
/*     */       {
/*  99 */         if ((implicitExit) && (FXApplet2Adapter.this.isExitOnIdle()))
/* 100 */           destroyIfNeeded();
/*     */       }
/*     */ 
/*     */       public void exitCalled()
/*     */       {
/* 107 */         destroyIfNeeded();
/*     */       }
/*     */     };
/* 110 */     PlatformImpl.addListener(this.finishListener);
/*     */   }
/*     */ 
/*     */   public static FXApplet2Adapter getFXApplet2Adapter(Applet2Context ctx) {
/* 114 */     DeployPerfLogger.timestamp("Created FXApplet2Adapter");
/* 115 */     return new FXApplet2Adapter(ctx);
/*     */   }
/*     */ 
/*     */   static synchronized Applet2Adapter get()
/*     */   {
/* 124 */     return adapter;
/*     */   }
/*     */ 
/*     */   public void setParentContainer(Window window)
/*     */   {
/* 131 */     if ((window instanceof FXWindow))
/* 132 */       this.window = ((FXWindow)window);
/*     */   }
/*     */ 
/*     */   public void instantiateApplet(Class clz)
/*     */     throws InstantiationException, IllegalAccessException
/*     */   {
/* 141 */     DeployPerfLogger.timestamp("Before create applet");
/* 142 */     this.applet2 = new FXApplet2(clz, this.window);
/* 143 */     DeployPerfLogger.timestamp("After create applet");
/*     */   }
/*     */ 
/*     */   public void instantiateSerialApplet(ClassLoader cl, String string)
/*     */   {
/* 148 */     throw new RuntimeException("Serial applets are not supported with FX toolkit");
/*     */   }
/*     */ 
/*     */   public Object getLiveConnectObject()
/*     */   {
/* 156 */     return this.applet2 == null ? null : this.applet2.getApplication();
/*     */   }
/*     */ 
/*     */   public Applet2 getApplet2()
/*     */   {
/* 161 */     return this.applet2;
/*     */   }
/*     */ 
/*     */   public Preloader instantiatePreloader(Class clz)
/*     */   {
/* 171 */     DeployPerfLogger.timestamp("Before create preloader");
/*     */     try
/*     */     {
/* 174 */       if (clz != null)
/* 175 */         this.preLoader = new FXPreloader(clz, getApplet2Context(), this.window);
/*     */       else {
/* 177 */         this.preLoader = new FXPreloader(getApplet2Context(), this.window);
/*     */       }
/* 179 */       this.preLoader.start();
/*     */     } catch (Exception e) {
/* 181 */       this.preLoader = null;
/* 182 */       throw new RuntimeException(e);
/*     */     }
/*     */ 
/* 191 */     DeployPerfLogger.timestamp("After create preloader");
/* 192 */     return this.preLoader;
/*     */   }
/*     */ 
/*     */   public Preloader getPreloader()
/*     */   {
/* 197 */     return this.preLoader;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 206 */     if (this.applet2 != null)
/* 207 */       this.applet2.init(getApplet2Context());
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 213 */     if (this.applet2 != null) {
/* 214 */       this.applet2.start();
/*     */     }
/* 216 */     setExitOnIdle(true);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 221 */     if (this.applet2 != null)
/* 222 */       this.applet2.stop();
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/* 228 */     if (this.applet2 != null)
/* 229 */       this.applet2.destroy();
/*     */   }
/*     */ 
/*     */   public void abort()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void resize(int i, int i1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doShowApplet()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doShowPreloader()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void doShowError(String string, final Throwable reason, final boolean doReload)
/*     */   {
/* 258 */     FXPreloader.hideSplash();
/*     */ 
/* 260 */     Platform.runLater(new Runnable() {
/*     */       public void run() {
/* 262 */         Stage s = FXApplet2Adapter.this.window != null ? FXApplet2Adapter.this.window.getErrorStage() : new Stage();
/* 263 */         ErrorPane epane = new ErrorPane(FXApplet2Adapter.this.getApplet2Context(), reason, doReload);
/*     */ 
/* 265 */         s.setScene(new Scene(epane));
/* 266 */         s.show();
/* 267 */         s.toFront();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void doClearAppletArea()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isInstantiated()
/*     */   {
/* 279 */     return this.applet2 != null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXApplet2Adapter
 * JD-Core Version:    0.6.2
 */