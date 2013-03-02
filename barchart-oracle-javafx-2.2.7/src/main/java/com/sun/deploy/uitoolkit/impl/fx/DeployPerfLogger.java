/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.applet2.Applet2Context;
/*     */ import com.sun.applet2.Applet2Host;
/*     */ import com.sun.deploy.trace.Trace;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import netscape.javascript.JSObject;
/*     */ import sun.plugin2.applet2.Plugin2Host;
/*     */ 
/*     */ public class DeployPerfLogger
/*     */ {
/*     */   private static Applet2Context ctx;
/*  21 */   private static boolean gotError = false;
/*  22 */   private static final List<Record> queue = new LinkedList();
/*  23 */   public static boolean PERF_LOG_ENABLED = false;
/*  24 */   private static JSObject js = null;
/*  25 */   private static PerfLoggerThread logger = null;
/*     */ 
/*  46 */   public static long lastReported = 0L;
/*     */ 
/* 176 */   private static long firstReported = 0L;
/*     */ 
/*     */   private static synchronized JSObject getJSObject()
/*     */   {
/* 125 */     if (js != null) {
/* 126 */       return js;
/*     */     }
/*     */ 
/* 129 */     if (ctx != null) {
/* 130 */       Applet2Host host = ctx.getHost();
/* 131 */       if ((host instanceof Plugin2Host)) {
/*     */         try
/*     */         {
/* 134 */           js = ((Plugin2Host)host).getOneWayJSObject();
/*     */         }
/*     */         catch (Exception e) {
/* 137 */           ctx = null;
/*     */         }
/*     */       }
/*     */     }
/* 141 */     return js;
/*     */   }
/*     */ 
/*     */   public static void setContext(Applet2Context c) {
/* 145 */     if (!PERF_LOG_ENABLED) {
/* 146 */       return;
/*     */     }
/*     */ 
/* 149 */     ctx = c;
/*     */   }
/*     */ 
/*     */   private static void flushQueue() {
/* 153 */     boolean repeat = true;
/*     */ 
/* 155 */     JSObject js = getJSObject();
/*     */ 
/* 157 */     if (js == null) {
/* 158 */       return;
/*     */     }
/*     */ 
/* 161 */     while (repeat)
/*     */     {
/*     */       Record r;
/* 162 */       synchronized (queue)
/*     */       {
/*     */         Record r;
/* 163 */         if (!queue.isEmpty())
/* 164 */           r = (Record)queue.remove(0);
/*     */         else {
/* 166 */           r = null;
/*     */         }
/*     */       }
/* 169 */       if (r != null)
/* 170 */         report(js, r);
/*     */       else
/* 172 */         repeat = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void report(JSObject js, Record r)
/*     */   {
/* 179 */     long now = System.nanoTime();
/* 180 */     long offset = (r.tm - now) / 1000000L;
/* 181 */     String label = r.label;
/*     */ 
/* 183 */     if (lastReported == 0L) {
/* 184 */       firstReported = now;
/*     */     }
/*     */ 
/* 188 */     if (lastReported != 0L) {
/* 189 */       label = "[" + (now - firstReported + 500000L) / 1000000L + " ms" + "(delta=" + (now - lastReported + 500000L) / 1000000L + ")] " + label;
/*     */     }
/*     */ 
/* 196 */     Trace.println("PERFLOG: report [" + label + "]");
/* 197 */     js.eval("if (typeof perfLog == 'function') {    perfLog(" + offset + ", '" + label + "');}");
/*     */ 
/* 199 */     lastReported = now;
/*     */   }
/*     */ 
/*     */   public static void timestamp(String label) {
/* 203 */     long tm = System.nanoTime();
/* 204 */     if ((PERF_LOG_ENABLED) && (logger != null) && (logger.isActive()))
/* 205 */       synchronized (queue) {
/* 206 */         queue.add(new Record(tm, label));
/* 207 */         queue.notifyAll();
/*     */       }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     PERF_LOG_ENABLED = false;
/*     */     try {
/*  30 */       PERF_LOG_ENABLED = ((Boolean)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public Boolean run()
/*     */         {
/*  35 */           return Boolean.valueOf("true".equals(System.getProperty("jnlp.fx.perf")));
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*  39 */       if (PERF_LOG_ENABLED)
/*  40 */         logger = new PerfLoggerThread();
/*     */     }
/*     */     catch (PrivilegedActionException pae) {
/*  43 */       Trace.ignored(pae);
/*     */     }
/*     */   }
/*     */ 
/*     */   static class PerfLoggerThread extends Thread
/*     */   {
/*  61 */     private boolean isActive = true;
/*     */ 
/*     */     synchronized boolean isActive() {
/*  64 */       return this.isActive;
/*     */     }
/*     */ 
/*     */     public PerfLoggerThread() {
/*  68 */       super();
/*  69 */       setDaemon(true);
/*  70 */       if (DeployPerfLogger.PERF_LOG_ENABLED) {
/*  71 */         Trace.println("Starting perf tracing thread");
/*  72 */         start();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void run() {
/*     */       try {
/*  78 */         boolean doRun = true;
/*  79 */         long tm = System.currentTimeMillis();
/*  80 */         while (doRun) {
/*  81 */           if (DeployPerfLogger.access$000() != null) {
/*     */             try {
/*  83 */               DeployPerfLogger.access$100();
/*  84 */               DeployPerfLogger.access$202(false);
/*     */             } catch (Exception e) {
/*  86 */               if (DeployPerfLogger.gotError)
/*     */               {
/*  88 */                 doRun = false;
/*  89 */                 Trace.ignoredException(e);
/*     */               } else {
/*  91 */                 DeployPerfLogger.access$202(true);
/*     */               }
/*  93 */               synchronized (DeployPerfLogger.class) {
/*  94 */                 DeployPerfLogger.access$302(null);
/*     */               }
/*     */             }
/*     */           }
/*  98 */           else if (System.currentTimeMillis() - tm > 5000L) {
/*  99 */             Trace.println("Stop perf tracing as could not get JS object");
/*     */ 
/* 101 */             doRun = false;
/*     */           }
/*     */ 
/* 104 */           synchronized (DeployPerfLogger.queue)
/*     */           {
/*     */             try {
/* 107 */               DeployPerfLogger.queue.wait(10000L);
/*     */             } catch (InterruptedException ie) {
/* 109 */               doRun = false;
/*     */             }
/*     */           }
/*     */         }
/*     */       } catch (Throwable t) {
/* 114 */         Trace.ignored(t);
/*     */       } finally {
/* 116 */         synchronized (this) {
/* 117 */           this.isActive = false;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class Record
/*     */   {
/*     */     long tm;
/*     */     String label;
/*     */ 
/*     */     public Record(long t, String l)
/*     */     {
/*  54 */       this.tm = t;
/*  55 */       this.label = l;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.DeployPerfLogger
 * JD-Core Version:    0.6.2
 */