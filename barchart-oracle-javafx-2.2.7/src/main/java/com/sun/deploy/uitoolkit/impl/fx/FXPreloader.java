/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.applet2.Applet2;
/*     */ import com.sun.applet2.Applet2Context;
/*     */ import com.sun.applet2.preloader.CancelException;
/*     */ import com.sun.applet2.preloader.event.AppletInitEvent;
/*     */ import com.sun.applet2.preloader.event.DownloadEvent;
/*     */ import com.sun.applet2.preloader.event.ErrorEvent;
/*     */ import com.sun.applet2.preloader.event.PreloaderEvent;
/*     */ import com.sun.applet2.preloader.event.UserDeclinedEvent;
/*     */ import com.sun.deploy.trace.Trace;
/*     */ import com.sun.deploy.uitoolkit.Applet2Adapter;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.FXDefaultPreloader;
/*     */ import com.sun.javafx.applet.ExperimentalExtensions;
/*     */ import com.sun.javafx.applet.FXApplet2;
/*     */ import com.sun.javafx.applet.Splash;
/*     */ import com.sun.javafx.application.ParametersImpl;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import javafx.application.Application;
/*     */ import javafx.application.Preloader.ErrorNotification;
/*     */ import javafx.application.Preloader.PreloaderNotification;
/*     */ import javafx.application.Preloader.ProgressNotification;
/*     */ import javafx.application.Preloader.StateChangeNotification;
/*     */ import javafx.application.Preloader.StateChangeNotification.Type;
/*     */ import javafx.stage.Stage;
/*     */ 
/*     */ public class FXPreloader extends com.sun.applet2.preloader.Preloader
/*     */ {
/*  44 */   private static final Object lock = new Object();
/*  45 */   private static FXPreloader defaultPreloader = null;
/*     */ 
/*  48 */   private javafx.application.Preloader fxPreview = null;
/*  49 */   private FXWindow window = null;
/*     */ 
/*  53 */   private boolean seenFatalError = false;
/*     */ 
/*     */   FXPreloader()
/*     */   {
/*  60 */     DeployPerfLogger.timestamp("(start) Construct preloader [default]");
/*  61 */     this.fxPreview = new FXDefaultPreloader();
/*  62 */     DeployPerfLogger.timestamp("(done) Construct preloader [default]");
/*     */   }
/*     */ 
/*     */   FXPreloader(Applet2Context a2c, FXWindow window)
/*     */   {
/*  67 */     DeployPerfLogger.timestamp("(start) Construct preloader [default]");
/*  68 */     this.window = window;
/*  69 */     this.fxPreview = new FXDefaultPreloader();
/*  70 */     String[] unnamed = Utils.getUnnamed(a2c);
/*  71 */     Map named = Utils.getNamedParameters(a2c);
/*  72 */     ParametersImpl.registerParameters(this.fxPreview, new ParametersImpl(named, unnamed));
/*     */ 
/*  74 */     DeployPerfLogger.timestamp("(done) Construct preloader [default]");
/*     */   }
/*     */ 
/*     */   FXPreloader(Class<javafx.application.Preloader> clz, Applet2Context a2c, FXWindow window)
/*     */     throws InstantiationException, IllegalAccessException
/*     */   {
/*  80 */     DeployPerfLogger.timestamp("(start) Construct preloader [" + clz.getName() + "]");
/*  81 */     this.window = window;
/*  82 */     this.fxPreview = ((javafx.application.Preloader)clz.newInstance());
/*  83 */     String[] unnamed = Utils.getUnnamed(a2c);
/*  84 */     Map named = Utils.getNamedParameters(a2c);
/*  85 */     ParametersImpl.registerParameters(this.fxPreview, new ParametersImpl(named, unnamed));
/*     */ 
/*  87 */     DeployPerfLogger.timestamp("(done) Construct preloader [" + clz.getName() + "]");
/*     */   }
/*     */ 
/*     */   static FXPreloader getDefaultPreloader()
/*     */   {
/*  93 */     synchronized (lock) {
/*  94 */       if (defaultPreloader != null) {
/*  95 */         defaultPreloader = new FXPreloader();
/*     */       }
/*     */     }
/*     */ 
/*  99 */     return defaultPreloader;
/*     */   }
/*     */ 
/*     */   public static void notifyCurrentPreloaderOnError(ErrorEvent pe)
/*     */   {
/* 118 */     Notifier.send(pe);
/*     */   }
/*     */ 
/*     */   public static void notifyCurrentPreloader(Preloader.PreloaderNotification pe) {
/* 122 */     Notifier.send(new UserEvent(pe));
/*     */   }
/*     */ 
/*     */   public Object getOwner()
/*     */   {
/* 179 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean handleEvent(PreloaderEvent pe) throws CancelException {
/* 183 */     Boolean ret = Boolean.valueOf(false);
/*     */ 
/* 185 */     if ((pe instanceof ErrorEvent))
/*     */     {
/* 190 */       FXApplet2Adapter adapter = (FXApplet2Adapter)FXApplet2Adapter.get();
/* 191 */       adapter.abortApplet();
/*     */     }
/*     */ 
/* 195 */     FXDispatcher fxDispatcher = new FXDispatcher(pe);
/*     */     try
/*     */     {
/* 200 */       ret = (Boolean)FXPluginToolkit.callAndWait(fxDispatcher);
/*     */     } catch (CancelException ce) {
/* 202 */       throw ce;
/*     */     } catch (Exception e) {
/* 204 */       e.printStackTrace();
/*     */     }
/* 206 */     return ret.booleanValue();
/*     */   }
/*     */ 
/*     */   void start()
/*     */     throws Exception
/*     */   {
/* 329 */     DeployPerfLogger.timestamp("(start) Preloader.init()");
/*     */ 
/* 332 */     this.fxPreview.init();
/* 333 */     DeployPerfLogger.timestamp("(done) Preloader.init()");
/*     */ 
/* 338 */     FXPluginToolkit.callAndWait(new Callable() {
/*     */       public Object call() throws Exception {
/* 340 */         DeployPerfLogger.timestamp("(start) Preloader create stage");
/*     */         Stage previewStage;
/* 342 */         if (FXPreloader.this.window == null)
/*     */         {
/* 344 */           Stage previewStage = new Stage();
/* 345 */           previewStage.impl_setPrimary(true);
/*     */         }
/*     */         else {
/* 348 */           previewStage = FXPreloader.this.window.getPreloaderStage();
/*     */         }
/* 350 */         DeployPerfLogger.timestamp("(start) Preloader.start()");
/* 351 */         FXPreloader.this.fxPreview.start(previewStage);
/* 352 */         DeployPerfLogger.timestamp("(done) Preloader.start()");
/*     */ 
/* 374 */         if (!(FXPreloader.this.fxPreview instanceof FXDefaultPreloader)) {
/* 375 */           FXPreloader.hideSplash();
/*     */         }
/* 377 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public static void hideSplash()
/*     */   {
/* 384 */     ExperimentalExtensions ext = ExperimentalExtensions.get();
/* 385 */     if (ext != null)
/*     */     {
/* 387 */       ext.getSplash().hide();
/*     */     }
/*     */   }
/*     */ 
/*     */   void stop() throws Exception {
/* 392 */     if (this.fxPreview != null)
/* 393 */       this.fxPreview.stop();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 138 */     Class junk = Notifier.class;
/* 139 */     Class junk2 = UserEvent.class;
/*     */   }
/*     */ 
/*     */   class FXDispatcher
/*     */     implements Callable<Boolean>
/*     */   {
/*     */     PreloaderEvent pe;
/*     */ 
/*     */     FXDispatcher(PreloaderEvent pe)
/*     */     {
/* 223 */       this.pe = pe;
/*     */     }
/*     */ 
/*     */     private void gotFatalError() {
/* 227 */       FXPreloader.this.seenFatalError = true;
/*     */ 
/* 233 */       FXApplet2Adapter adapter = (FXApplet2Adapter)FXApplet2Adapter.get();
/* 234 */       adapter.setExitOnIdle(true);
/*     */     }
/*     */ 
/*     */     public Boolean call()
/*     */       throws Exception
/*     */     {
/* 240 */       if (FXPreloader.this.seenFatalError) {
/* 241 */         throw new CancelException("Cancel launch after fatal error");
/*     */       }
/*     */ 
/* 244 */       switch (this.pe.getType()) {
/*     */       case 5:
/* 246 */         AppletInitEvent aie = (AppletInitEvent)this.pe;
/* 247 */         Application app = null;
/* 248 */         Applet2 applet2 = aie.getApplet();
/* 249 */         if ((applet2 != null) && ((applet2 instanceof FXApplet2))) {
/* 250 */           app = ((FXApplet2)applet2).getApplication();
/*     */         }
/* 252 */         switch (aie.getSubtype())
/*     */         {
/*     */         case 2:
/* 255 */           DeployPerfLogger.timestamp("(start) Preloader.onAppLoad()");
/* 256 */           FXPreloader.this.fxPreview.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_LOAD));
/*     */ 
/* 259 */           DeployPerfLogger.timestamp("(done) Preloader.onAppLoad()");
/* 260 */           break;
/*     */         case 3:
/* 262 */           DeployPerfLogger.timestamp("(start) Preloader.onAppInit()");
/* 263 */           FXPreloader.this.fxPreview.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_INIT, app));
/*     */ 
/* 267 */           DeployPerfLogger.timestamp("(done) Preloader.onAppInit()");
/* 268 */           break;
/*     */         case 4:
/* 270 */           DeployPerfLogger.timestamp("(start) Preloader.onAppStart()");
/* 271 */           FXPreloader.this.fxPreview.handleStateChangeNotification(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START, app));
/*     */ 
/* 275 */           DeployPerfLogger.timestamp("(done) Preloader.onAppStart()");
/* 276 */           break;
/*     */         }
/*     */ 
/* 281 */         return Boolean.valueOf(true);
/*     */       case 1:
/* 284 */         return Boolean.valueOf(true);
/*     */       case 1000:
/* 286 */         Preloader.PreloaderNotification pev = ((FXPreloader.UserEvent)this.pe).get();
/* 287 */         FXPreloader.this.fxPreview.handleApplicationNotification(pev);
/* 288 */         return Boolean.valueOf(true);
/*     */       case 3:
/* 290 */         DownloadEvent de = (DownloadEvent)this.pe;
/* 291 */         double progress = de.getOverallPercentage() / 100.0D;
/* 292 */         FXPreloader.this.fxPreview.handleProgressNotification(new Preloader.ProgressNotification(progress));
/*     */ 
/* 294 */         return Boolean.valueOf(true);
/*     */       case 6:
/* 296 */         ErrorEvent ee = (ErrorEvent)this.pe;
/* 297 */         String location = ee.getLocation() != null ? ee.getLocation().toString() : null;
/*     */ 
/* 300 */         Throwable t = ee.getException();
/* 301 */         String details = ee.getValue();
/* 302 */         if (details == null) {
/* 303 */           details = t != null ? t.getMessage() : "unknown error";
/*     */         }
/* 305 */         gotFatalError();
/* 306 */         return Boolean.valueOf(FXPreloader.this.fxPreview.handleErrorNotification(new Preloader.ErrorNotification(location, details, t)));
/*     */       case 7:
/* 310 */         UserDeclinedEvent ue = (UserDeclinedEvent)this.pe;
/*     */ 
/* 312 */         String l = null;
/* 313 */         gotFatalError();
/* 314 */         return Boolean.valueOf(FXPreloader.this.fxPreview.handleErrorNotification(new FXPreloader.UserDeclinedNotification(FXPreloader.this, l)));
/*     */       }
/*     */ 
/* 319 */       return Boolean.valueOf(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   class UserDeclinedNotification extends Preloader.ErrorNotification
/*     */   {
/*     */     public UserDeclinedNotification(String url)
/*     */     {
/* 213 */       super("User declined to grant permissions to the application.", null);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Notifier
/*     */     implements PrivilegedExceptionAction<Void>
/*     */   {
/*     */     PreloaderEvent pe;
/*     */ 
/*     */     Notifier(PreloaderEvent pe)
/*     */     {
/* 146 */       this.pe = pe;
/*     */     }
/*     */ 
/*     */     static void send(PreloaderEvent pe) {
/*     */       try {
/* 151 */         AccessController.doPrivileged(new Notifier(pe));
/*     */       } catch (Exception e) {
/* 153 */         Trace.ignoredException(e);
/*     */       }
/*     */     }
/*     */ 
/*     */     public Void run()
/*     */       throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CancelException
/*     */     {
/* 162 */       Applet2Adapter adapter = FXApplet2Adapter.get();
/* 163 */       if (adapter != null) {
/* 164 */         Class c = Class.forName("com.sun.javaws.progress.Progress");
/* 165 */         Method m = c.getMethod("get", new Class[] { Applet2Adapter.class });
/*     */ 
/* 168 */         com.sun.applet2.preloader.Preloader p = (com.sun.applet2.preloader.Preloader)m.invoke(null, new Object[] { adapter });
/* 169 */         p.handleEvent(this.pe);
/*     */       }
/* 171 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   static class UserEvent extends PreloaderEvent
/*     */   {
/*     */     public static final int CUSTOM_USER_EVENT = 1000;
/*     */     Preloader.PreloaderNotification pe;
/*     */ 
/*     */     UserEvent(Preloader.PreloaderNotification pe)
/*     */     {
/* 107 */       super();
/* 108 */       this.pe = pe;
/*     */     }
/*     */ 
/*     */     Preloader.PreloaderNotification get() {
/* 112 */       return this.pe;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXPreloader
 * JD-Core Version:    0.6.2
 */