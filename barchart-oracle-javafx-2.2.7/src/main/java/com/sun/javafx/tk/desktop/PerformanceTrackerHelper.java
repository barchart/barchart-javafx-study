/*     */ package com.sun.javafx.tk.desktop;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public abstract class PerformanceTrackerHelper
/*     */ {
/*  37 */   private static final PerformanceTrackerHelper instance = createInstance();
/*     */ 
/*     */   public static PerformanceTrackerHelper getInstance() {
/*  40 */     return instance;
/*     */   }
/*     */ 
/*     */   private static PerformanceTrackerHelper createInstance()
/*     */   {
/*  47 */     return (PerformanceTrackerHelper)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public PerformanceTrackerHelper run()
/*     */       {
/*     */         try {
/*  52 */           if (System.getProperty("sun.perflog") != null) {
/*  53 */             final PerformanceTrackerHelper.PerformanceTrackerDefaultImpl localPerformanceTrackerDefaultImpl = new PerformanceTrackerHelper.PerformanceTrackerDefaultImpl();
/*     */ 
/*  56 */             if (System.getProperty("sun.perflog.fx.exitflush") != null)
/*     */             {
/*  58 */               Runtime.getRuntime().addShutdownHook(new Thread()
/*     */               {
/*     */                 public void run()
/*     */                 {
/*  62 */                   localPerformanceTrackerDefaultImpl.outputLog();
/*     */                 }
/*     */               });
/*     */             }
/*     */ 
/*  67 */             return localPerformanceTrackerDefaultImpl;
/*     */           }
/*     */         }
/*     */         catch (Throwable localThrowable) {
/*     */         }
/*  72 */         return new PerformanceTrackerHelper.PerformanceTrackerDummyImpl(null);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public abstract void logEvent(String paramString);
/*     */ 
/*     */   public abstract void outputLog();
/*     */ 
/*     */   public abstract boolean isPerfLoggingEnabled();
/*     */ 
/*     */   public final long nanoTime() {
/*  84 */     return Toolkit.getToolkit().getMasterTimer().nanos();
/*     */   }
/*     */ 
/*     */   private static final class PerformanceTrackerDummyImpl extends PerformanceTrackerHelper
/*     */   {
/*     */     private PerformanceTrackerDummyImpl()
/*     */     {
/* 182 */       super();
/*     */     }
/*     */ 
/*     */     public void logEvent(String paramString)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void outputLog()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean isPerfLoggingEnabled() {
/* 194 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class PerformanceTrackerDefaultImpl extends PerformanceTrackerHelper
/*     */   {
/*     */     private long firstTime;
/*     */     private long lastTime;
/*     */     private final Method logEventMethod;
/*     */     private final Method outputLogMethod;
/*     */     private final Method getStartTimeMethod;
/*     */     private final Method setStartTimeMethod;
/*     */ 
/*     */     public PerformanceTrackerDefaultImpl()
/*     */       throws ClassNotFoundException, NoSuchMethodException
/*     */     {
/*  98 */       super();
/*  99 */       Class localClass = Class.forName("sun.misc.PerformanceLogger", true, null);
/*     */ 
/* 102 */       this.logEventMethod = localClass.getMethod("setTime", new Class[] { String.class });
/*     */ 
/* 104 */       this.outputLogMethod = localClass.getMethod("outputLog", new Class[0]);
/*     */ 
/* 106 */       this.getStartTimeMethod = localClass.getMethod("getStartTime", new Class[0]);
/*     */ 
/* 108 */       this.setStartTimeMethod = localClass.getMethod("setStartTime", new Class[] { String.class, Long.TYPE });
/*     */     }
/*     */ 
/*     */     public void logEvent(final String paramString)
/*     */     {
/* 115 */       final long l = System.currentTimeMillis();
/* 116 */       if (this.firstTime == 0L) {
/* 117 */         this.firstTime = l;
/*     */       }
/* 119 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Object run()
/*     */         {
/*     */           try {
/* 124 */             PerformanceTrackerHelper.PerformanceTrackerDefaultImpl.this.logEventMethod.invoke(null, new Object[] { "JavaFX> " + paramString + " (" + (l - PerformanceTrackerHelper.PerformanceTrackerDefaultImpl.this.firstTime) + "ms total, " + (l - PerformanceTrackerHelper.PerformanceTrackerDefaultImpl.this.lastTime) + "ms)" });
/*     */           }
/*     */           catch (Exception localException)
/*     */           {
/*     */           }
/*     */ 
/* 132 */           return null;
/*     */         }
/*     */       });
/* 136 */       this.lastTime = l;
/*     */     }
/*     */ 
/*     */     public void outputLog()
/*     */     {
/* 141 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Object run()
/*     */         {
/* 145 */           PerformanceTrackerHelper.PerformanceTrackerDefaultImpl.this.logLaunchTime();
/*     */           try
/*     */           {
/* 149 */             PerformanceTrackerHelper.PerformanceTrackerDefaultImpl.this.outputLogMethod.invoke(null, new Object[0]);
/*     */           }
/*     */           catch (Exception localException) {
/*     */           }
/* 153 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     public boolean isPerfLoggingEnabled()
/*     */     {
/* 160 */       return true;
/*     */     }
/*     */ 
/*     */     private void logLaunchTime()
/*     */     {
/*     */       try {
/* 166 */         if (((Long)this.getStartTimeMethod.invoke(null, new Object[0])).longValue() <= 0L)
/*     */         {
/* 168 */           String str = System.getProperty("launchTime");
/* 169 */           if ((str != null) && (!str.equals("")))
/*     */           {
/* 171 */             long l = Long.parseLong(str);
/* 172 */             this.setStartTimeMethod.invoke(null, new Object[] { "LaunchTime", Long.valueOf(l) });
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Throwable localThrowable) {
/* 177 */         localThrowable.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.PerformanceTrackerHelper
 * JD-Core Version:    0.6.2
 */