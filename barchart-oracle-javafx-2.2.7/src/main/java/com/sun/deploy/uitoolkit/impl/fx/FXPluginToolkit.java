/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.applet2.Applet2Context;
/*     */ import com.sun.applet2.preloader.Preloader;
/*     */ import com.sun.deploy.appcontext.AppContext;
/*     */ import com.sun.deploy.trace.Trace;
/*     */ import com.sun.deploy.uitoolkit.Applet2Adapter;
/*     */ import com.sun.deploy.uitoolkit.DragContext;
/*     */ import com.sun.deploy.uitoolkit.DragHelper;
/*     */ import com.sun.deploy.uitoolkit.DragListener;
/*     */ import com.sun.deploy.uitoolkit.PluginUIToolkit;
/*     */ import com.sun.deploy.uitoolkit.PluginWindowFactory;
/*     */ import com.sun.deploy.uitoolkit.UIToolkit;
/*     */ import com.sun.deploy.uitoolkit.Window;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.FXAppContext;
/*     */ import com.sun.deploy.uitoolkit.impl.fx.ui.FXUIFactory;
/*     */ import com.sun.deploy.uitoolkit.ui.UIFactory;
/*     */ import com.sun.deploy.util.Waiter;
/*     */ import com.sun.deploy.util.Waiter.WaiterTask;
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.javafx.tk.desktop.DesktopToolkit;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.Callable;
/*     */ import javafx.application.Platform;
/*     */ import sun.plugin2.applet.Plugin2Manager;
/*     */ import sun.plugin2.message.Pipe;
/*     */ 
/*     */ public class FXPluginToolkit extends PluginUIToolkit
/*     */ {
/*  35 */   static PluginWindowFactory windowFactory = null;
/*     */   private final Object initializedLock;
/*     */   private boolean initialized;
/* 130 */   public static final DragHelper noOpDragHelper = new DragHelper() { public void register(DragContext ctx, DragListener listener) {  } 
/*     */     public void makeDisconnected(DragContext ctx, Window container) {  } 
/*     */     public void restore(DragContext ctx) {  } 
/* 130 */     public void unregister(DragContext ctx) {  }  } ;
/*     */ 
/* 207 */   static FXUIFactory fxUIFactory = new FXUIFactory();
/*     */ 
/*     */   public FXPluginToolkit()
/*     */   {
/*  36 */     this.initializedLock = new Object();
/*  37 */     this.initialized = false;
/*     */   }
/*     */ 
/*     */   public PluginWindowFactory getWindowFactory() {
/*  41 */     if (windowFactory == null) {
/*  42 */       doInitIfNeeded();
/*  43 */       windowFactory = new FXWindowFactory();
/*     */     }
/*  45 */     return windowFactory;
/*     */   }
/*     */ 
/*     */   public Preloader getDefaultPreloader()
/*     */   {
/*  55 */     doInitIfNeeded();
/*  56 */     return FXPreloader.getDefaultPreloader();
/*     */   }
/*     */ 
/*     */   public Applet2Adapter getApplet2Adapter(Applet2Context ctx)
/*     */   {
/*  61 */     doInitIfNeeded();
/*  62 */     DeployPerfLogger.timestamp("FXToolkit.getAppletAdapter()");
/*  63 */     return FXApplet2Adapter.getFXApplet2Adapter(ctx);
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  72 */     Waiter.set(new FxWaiter());
/*     */   }
/*     */ 
/*     */   private void doInitIfNeeded() {
/*  76 */     synchronized (this.initializedLock) {
/*  77 */       if (!this.initialized)
/*     */       {
/*  82 */         Boolean isPluginObj = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/*     */             try {
/*  86 */               ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*  87 */               Class tkClass = Class.forName("com.sun.deploy.uitoolkit.ToolkitStore", false, cl);
/*  88 */               Field isPluginField = tkClass.getDeclaredField("isPlugin");
/*  89 */               isPluginField.setAccessible(true);
/*  90 */               return Boolean.valueOf(isPluginField.getBoolean(tkClass));
/*     */             } catch (Throwable ex) {
/*  92 */               Trace.ignored(ex, true);
/*     */             }
/*     */ 
/*  95 */             return Boolean.FALSE;
/*     */           }
/*     */         });
/*  99 */         boolean isPlugin = isPluginObj.booleanValue();
/* 100 */         DeployPerfLogger.timestamp("(start) FXToolkit.init()");
/* 101 */         DesktopToolkit.setPluginMode(isPlugin);
/*     */ 
/* 104 */         PlatformImpl.setTaskbarApplication(!isPlugin);
/* 105 */         PlatformImpl.startup(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */           }
/*     */         });
/* 109 */         this.initialized = true;
/* 110 */         DeployPerfLogger.timestamp("(done) FXToolkit.init()");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean printApplet(Plugin2Manager pm, int i, Pipe pipe, long l, boolean bln, int i1, int i2, int i3, int i4)
/*     */   {
/* 120 */     doInitIfNeeded();
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */   public DragHelper getDragHelper()
/*     */   {
/* 127 */     doInitIfNeeded();
/* 128 */     return noOpDragHelper;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */     throws Exception
/*     */   {
/* 144 */     PlatformImpl.tkExit();
/*     */   }
/*     */ 
/*     */   public boolean isHeadless()
/*     */   {
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   public void setContextClassLoader(final ClassLoader cl)
/*     */   {
/* 154 */     if (cl == null) return;
/*     */ 
/* 158 */     Platform.runLater(new Runnable() {
/*     */       public void run() {
/* 160 */         Thread.currentThread().setContextClassLoader(cl);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void warmup()
/*     */   {
/* 167 */     DeployPerfLogger.timestamp("(start) FXToolkit.warmup()");
/* 168 */     doInitIfNeeded();
/* 169 */     DeployPerfLogger.timestamp("(done) FXToolkit.warmup()");
/*     */   }
/*     */ 
/*     */   public AppContext getAppContext()
/*     */   {
/* 174 */     return FXAppContext.getInstance();
/*     */   }
/*     */ 
/*     */   public AppContext createAppContext()
/*     */   {
/* 179 */     return FXAppContext.getInstance();
/*     */   }
/*     */ 
/*     */   public SecurityManager getSecurityManager()
/*     */   {
/* 184 */     SecurityManager sm = null;
/*     */     try
/*     */     {
/* 188 */       Class factoryClass = Class.forName("sun.plugin2.applet.FXAppletSecurityManager", false, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 191 */       Constructor factory = factoryClass.getDeclaredConstructor(new Class[0]);
/*     */ 
/* 193 */       sm = (SecurityManager)factory.newInstance(new Object[0]);
/*     */     } catch (Exception e) {
/* 195 */       Trace.ignoredException(e);
/*     */     }
/* 197 */     return sm;
/*     */   }
/*     */ 
/*     */   public UIToolkit changeMode(int i)
/*     */   {
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   public UIFactory getUIFactory()
/*     */   {
/* 211 */     doInitIfNeeded();
/* 212 */     return fxUIFactory;
/*     */   }
/*     */ 
/*     */   public static <T> T callAndWait(Callable<T> callable)
/*     */     throws Exception
/*     */   {
/* 285 */     Caller caller = new Caller(callable);
/* 286 */     if (Platform.isFxApplicationThread()) {
/* 287 */       caller.run();
/*     */     } else {
/* 289 */       Platform.runLater(caller);
/*     */ 
/* 291 */       if (FXApplet2Adapter.isPlatformDestroyed())
/*     */       {
/* 296 */         return null;
/*     */       }
/*     */     }
/* 299 */     return caller.waitForReturn();
/*     */   }
/*     */ 
/*     */   private static class Caller<T> implements Runnable
/*     */   {
/* 304 */     private T returnValue = null;
/* 305 */     Exception exception = null;
/*     */     private Callable<T> returnable;
/* 307 */     private Boolean returned = Boolean.FALSE;
/* 308 */     private final Object lock = new Object();
/*     */ 
/*     */     Caller(Callable<T> r) {
/* 311 */       this.returnable = r;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       try {
/* 317 */         this.returnValue = this.returnable.call();
/*     */       } catch (Throwable e) {
/* 319 */         if ((e instanceof Exception))
/* 320 */           this.exception = ((Exception)e);
/*     */         else
/* 322 */           this.exception = new RuntimeException("Problem in callAndWait()", e);
/*     */       }
/*     */       finally {
/* 325 */         synchronized (this.lock) {
/* 326 */           this.returned = Boolean.valueOf(true);
/* 327 */           this.lock.notifyAll();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     T waitForReturn() throws Exception {
/* 333 */       synchronized (this.lock)
/*     */       {
/* 336 */         while ((!this.returned.booleanValue()) && (!FXApplet2Adapter.isPlatformDestroyed())) {
/*     */           try {
/* 338 */             this.lock.wait(500L);
/*     */           } catch (InterruptedException ie) {
/* 340 */             System.out.println("Interrupted wait" + ie.getStackTrace());
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 345 */       if (this.exception != null) {
/* 346 */         throw this.exception;
/*     */       }
/* 348 */       return this.returnValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class FxWaiter extends Waiter
/*     */   {
/*     */     private final Toolkit tk;
/*     */ 
/*     */     FxWaiter()
/*     */     {
/* 248 */       this.tk = Toolkit.getToolkit();
/*     */ 
/* 250 */       Class cls = FXPluginToolkit.TaskThread.class;
/*     */     }
/*     */ 
/*     */     public Object wait(Waiter.WaiterTask task) throws Exception
/*     */     {
/* 255 */       if (!this.tk.isFxUserThread()) {
/* 256 */         return task.run();
/*     */       }
/*     */ 
/* 259 */       final Object taskKey = new Object();
/* 260 */       Runnable cleanup = new Runnable() {
/*     */         public void run() {
/* 262 */           FXPluginToolkit.FxWaiter.this.tk.exitNestedEventLoop(taskKey, null);
/*     */         }
/*     */       };
/* 266 */       FXPluginToolkit.TaskThread worker = new FXPluginToolkit.TaskThread(task, cleanup);
/* 267 */       worker.start();
/*     */ 
/* 269 */       this.tk.enterNestedEventLoop(taskKey);
/*     */ 
/* 271 */       return worker.getResult();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TaskThread extends Thread
/*     */   {
/*     */     final Waiter.WaiterTask task;
/*     */     final Runnable cleanup;
/*     */     Object rv;
/*     */ 
/*     */     TaskThread(Waiter.WaiterTask task, Runnable r)
/*     */     {
/* 222 */       this.task = task;
/* 223 */       this.cleanup = r;
/*     */     }
/*     */ 
/*     */     public Object getResult() throws Exception {
/* 227 */       if ((this.rv instanceof Exception)) {
/* 228 */         throw ((Exception)this.rv);
/*     */       }
/* 230 */       return this.rv;
/*     */     }
/*     */ 
/*     */     public void run() {
/*     */       try {
/* 235 */         this.rv = this.task.run();
/*     */       } catch (Exception ex) {
/* 237 */         this.rv = ex;
/*     */       } finally {
/* 239 */         Platform.runLater(this.cleanup);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXPluginToolkit
 * JD-Core Version:    0.6.2
 */