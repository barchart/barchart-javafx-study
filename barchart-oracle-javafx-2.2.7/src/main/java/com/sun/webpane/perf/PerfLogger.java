/*     */ package com.sun.webpane.perf;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class PerfLogger
/*     */ {
/*     */   private static Thread shutdownHook;
/*     */   private static Map<Logger, PerfLogger> loggers;
/*  18 */   private HashMap<String, ProbeStat> probes = new HashMap();
/*     */   private Logger log;
/*     */   private boolean isEnabled;
/* 135 */   private Comparator timeComparator = new Comparator() {
/*     */     public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 137 */       long l1 = ((PerfLogger.ProbeStat)PerfLogger.access$200(PerfLogger.this).get((String)paramAnonymousObject1)).totalTime;
/* 138 */       long l2 = ((PerfLogger.ProbeStat)PerfLogger.access$200(PerfLogger.this).get((String)paramAnonymousObject2)).totalTime;
/* 139 */       if (l1 > l2)
/* 140 */         return 1;
/* 141 */       if (l1 < l2) {
/* 142 */         return -1;
/*     */       }
/* 144 */       return 0;
/*     */     }
/* 135 */   };
/*     */ 
/* 148 */   private Comparator countComparator = new Comparator() {
/*     */     public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2) {
/* 150 */       long l1 = ((PerfLogger.ProbeStat)PerfLogger.access$200(PerfLogger.this).get((String)paramAnonymousObject1)).count;
/* 151 */       long l2 = ((PerfLogger.ProbeStat)PerfLogger.access$200(PerfLogger.this).get((String)paramAnonymousObject2)).count;
/* 152 */       if (l1 > l2)
/* 153 */         return 1;
/* 154 */       if (l1 < l2) {
/* 155 */         return -1;
/*     */       }
/* 157 */       return 0;
/*     */     }
/* 148 */   };
/*     */ 
/*     */   public static synchronized PerfLogger getLogger(Logger paramLogger)
/*     */   {
/*  29 */     if (loggers == null) {
/*  30 */       loggers = new HashMap();
/*     */     }
/*  32 */     PerfLogger localPerfLogger = (PerfLogger)loggers.get(paramLogger);
/*  33 */     if (localPerfLogger == null) {
/*  34 */       localPerfLogger = new PerfLogger(paramLogger);
/*  35 */       loggers.put(paramLogger, localPerfLogger);
/*     */     }
/*     */ 
/*  38 */     if ((localPerfLogger.isEnabled()) && (shutdownHook == null)) {
/*  39 */       shutdownHook = new Thread()
/*     */       {
/*     */         public void run() {
/*  42 */           for (PerfLogger localPerfLogger : PerfLogger.loggers.values())
/*  43 */             if (localPerfLogger.isEnabled())
/*     */             {
/*  45 */               localPerfLogger.log(false);
/*     */             }
/*     */         }
/*     */       };
/*  49 */       Runtime.getRuntime().addShutdownHook(shutdownHook);
/*     */     }
/*  51 */     return localPerfLogger;
/*     */   }
/*     */ 
/*     */   public static synchronized PerfLogger getLogger(String paramString)
/*     */   {
/*  61 */     return getLogger(Logger.getLogger("com.sun.webpane.perf." + paramString));
/*     */   }
/*     */ 
/*     */   private PerfLogger(Logger paramLogger) {
/*  65 */     this.log = paramLogger;
/*  66 */     this.isEnabled = paramLogger.isLoggable(Level.FINE);
/*  67 */     startCount("TOTALTIME");
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/* 128 */     return this.isEnabled;
/*     */   }
/*     */ 
/*     */   private synchronized String fullName(String paramString) {
/* 132 */     return this.log.getName() + "." + paramString;
/*     */   }
/*     */ 
/*     */   public synchronized void reset()
/*     */   {
/* 165 */     for (Map.Entry localEntry : this.probes.entrySet()) {
/* 166 */       ((ProbeStat)localEntry.getValue()).reset();
/*     */     }
/* 168 */     startCount("TOTALTIME");
/*     */   }
/*     */ 
/*     */   public static synchronized void resetAll() {
/* 172 */     for (PerfLogger localPerfLogger : loggers.values())
/* 173 */       localPerfLogger.reset();
/*     */   }
/*     */ 
/*     */   private synchronized ProbeStat registerProbe(String paramString)
/*     */   {
/* 178 */     String str = paramString.intern();
/* 179 */     if (this.probes.containsKey(str))
/* 180 */       this.log.fine("Warning: \"" + fullName(str) + "\" probe already exists");
/*     */     else {
/* 182 */       this.log.fine("Registering \"" + fullName(str) + "\" probe");
/*     */     }
/* 184 */     ProbeStat localProbeStat = new ProbeStat(str, null);
/* 185 */     this.probes.put(str, localProbeStat);
/* 186 */     return localProbeStat;
/*     */   }
/*     */ 
/*     */   public synchronized ProbeStat getProbeStat(String paramString) {
/* 190 */     String str = paramString.intern();
/* 191 */     ProbeStat localProbeStat = (ProbeStat)this.probes.get(str);
/* 192 */     if (localProbeStat != null) {
/* 193 */       localProbeStat.snapshot();
/*     */     }
/* 195 */     return localProbeStat;
/*     */   }
/*     */ 
/*     */   public synchronized void startCount(String paramString)
/*     */   {
/* 202 */     if (!isEnabled()) {
/* 203 */       return;
/*     */     }
/* 205 */     String str = paramString.intern();
/* 206 */     ProbeStat localProbeStat = (ProbeStat)this.probes.get(str);
/* 207 */     if (localProbeStat == null) {
/* 208 */       localProbeStat = registerProbe(str);
/*     */     }
/* 210 */     localProbeStat.reset();
/* 211 */     localProbeStat.resume();
/*     */   }
/*     */ 
/*     */   public synchronized void suspendCount(String paramString)
/*     */   {
/* 218 */     if (!isEnabled()) {
/* 219 */       return;
/*     */     }
/* 221 */     String str = paramString.intern();
/* 222 */     ProbeStat localProbeStat = (ProbeStat)this.probes.get(str);
/* 223 */     if (localProbeStat != null)
/* 224 */       localProbeStat.suspend();
/*     */     else
/* 226 */       this.log.fine("Warning: \"" + fullName(str) + "\" probe is not registered");
/*     */   }
/*     */ 
/*     */   public synchronized void resumeCount(String paramString)
/*     */   {
/* 234 */     if (!isEnabled()) {
/* 235 */       return;
/*     */     }
/* 237 */     String str = paramString.intern();
/* 238 */     ProbeStat localProbeStat = (ProbeStat)this.probes.get(str);
/* 239 */     if (localProbeStat == null) {
/* 240 */       localProbeStat = registerProbe(str);
/*     */     }
/* 242 */     localProbeStat.resume();
/*     */   }
/*     */ 
/*     */   public synchronized void log(StringBuffer paramStringBuffer)
/*     */   {
/* 249 */     if (!isEnabled()) {
/* 250 */       return;
/*     */     }
/* 252 */     paramStringBuffer.append("=========== Performance Statistics =============\n");
/*     */ 
/* 254 */     ProbeStat localProbeStat1 = getProbeStat("TOTALTIME");
/*     */ 
/* 256 */     ArrayList localArrayList = new ArrayList();
/* 257 */     localArrayList.addAll(this.probes.keySet());
/*     */ 
/* 259 */     paramStringBuffer.append("\nTime:\n");
/* 260 */     Collections.sort(localArrayList, this.timeComparator);
/* 261 */     for (Iterator localIterator = localArrayList.iterator(); localIterator.hasNext(); ) { str = (String)localIterator.next();
/* 262 */       ProbeStat localProbeStat2 = getProbeStat(str);
/* 263 */       paramStringBuffer.append(String.format("%s: %dms", new Object[] { fullName(str), Long.valueOf(localProbeStat2.totalTime) }));
/* 264 */       if (localProbeStat1.totalTime > 0L)
/* 265 */         paramStringBuffer.append(String.format(", %.2f%%\n", new Object[] { Float.valueOf(100.0F * (float)localProbeStat2.totalTime / (float)localProbeStat1.totalTime) }));
/*     */       else
/* 267 */         paramStringBuffer.append("\n");
/*     */     }
/* 273 */     String str;
/* 271 */     paramStringBuffer.append("\nInvocations count:\n");
/* 272 */     Collections.sort(localArrayList, this.countComparator);
/* 273 */     for (localIterator = localArrayList.iterator(); localIterator.hasNext(); ) { str = (String)localIterator.next();
/* 274 */       paramStringBuffer.append(String.format("%s: %d\n", new Object[] { fullName(str), Integer.valueOf(getProbeStat(str).count) }));
/*     */     }
/* 276 */     paramStringBuffer.append("================================================\n");
/*     */   }
/*     */ 
/*     */   public synchronized void log()
/*     */   {
/* 283 */     log(true);
/*     */   }
/*     */ 
/*     */   private synchronized void log(boolean paramBoolean) {
/* 287 */     StringBuffer localStringBuffer = new StringBuffer();
/* 288 */     log(localStringBuffer);
/* 289 */     if (paramBoolean) {
/* 290 */       this.log.fine(localStringBuffer.toString());
/*     */     } else {
/* 292 */       System.out.println(localStringBuffer.toString());
/* 293 */       System.out.flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized void logAll()
/*     */   {
/* 301 */     StringBuffer localStringBuffer = new StringBuffer();
/* 302 */     for (PerfLogger localPerfLogger : loggers.values())
/* 303 */       localPerfLogger.log();
/*     */   }
/*     */ 
/*     */   public static class ProbeStat
/*     */   {
/*     */     private String probe;
/*     */     private int count;
/*     */     private long totalTime;
/*     */     private long startTime;
/*  78 */     private boolean isRunning = false;
/*     */ 
/*     */     private ProbeStat(String paramString) {
/*  81 */       this.probe = paramString;
/*     */     }
/*     */ 
/*     */     public String getProbe() {
/*  85 */       return this.probe;
/*     */     }
/*     */ 
/*     */     public int getCount() {
/*  89 */       return this.count;
/*     */     }
/*     */ 
/*     */     public long getTotalTime() {
/*  93 */       return this.totalTime;
/*     */     }
/*     */ 
/*     */     private void reset() {
/*  97 */       this.count = 0;
/*  98 */       this.totalTime = (this.startTime = 0L);
/*     */     }
/*     */ 
/*     */     private void suspend() {
/* 102 */       if (this.isRunning) {
/* 103 */         this.totalTime += System.currentTimeMillis() - this.startTime;
/* 104 */         this.isRunning = false;
/*     */       }
/*     */     }
/*     */ 
/*     */     private void resume() {
/* 109 */       this.isRunning = true;
/* 110 */       this.count += 1;
/* 111 */       this.startTime = System.currentTimeMillis();
/*     */     }
/*     */ 
/*     */     private void snapshot() {
/* 115 */       if (this.isRunning) {
/* 116 */         this.totalTime += System.currentTimeMillis() - this.startTime;
/* 117 */         this.startTime = System.currentTimeMillis();
/*     */       }
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 123 */       return super.toString() + "[count=" + this.count + ", time=" + this.totalTime + "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.perf.PerfLogger
 * JD-Core Version:    0.6.2
 */