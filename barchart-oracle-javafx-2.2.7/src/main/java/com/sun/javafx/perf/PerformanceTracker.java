/*     */ package com.sun.javafx.perf;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.scene.Scene;
/*     */ 
/*     */ public abstract class PerformanceTracker
/*     */ {
/*     */   private static SceneAccessor sceneAccessor;
/*     */   private boolean perfLoggingEnabled;
/* 148 */   private boolean firstPulse = true;
/*     */   private float instantFPS;
/*     */   private int instantFPSFrames;
/*     */   private long instantFPSStartTime;
/*     */   private long avgStartTime;
/*     */   private int avgFramesTotal;
/*     */   private float instantPulses;
/*     */   private int instantPulsesFrames;
/*     */   private long instantPulsesStartTime;
/*     */   private long avgPulsesStartTime;
/*     */   private int avgPulsesTotal;
/*     */   private Runnable onPulse;
/*     */   private Runnable onFirstPulse;
/*     */ 
/*     */   public static boolean isLoggingEnabled()
/*     */   {
/*  70 */     return Toolkit.getToolkit().getPerformanceTracker().perfLoggingEnabled;
/*     */   }
/*     */ 
/*     */   public static PerformanceTracker getSceneTracker(Scene paramScene)
/*     */   {
/*  91 */     PerformanceTracker localPerformanceTracker = null;
/*  92 */     if (sceneAccessor != null) {
/*  93 */       localPerformanceTracker = sceneAccessor.getPerfTracker(paramScene);
/*  94 */       if (localPerformanceTracker == null) {
/*  95 */         localPerformanceTracker = Toolkit.getToolkit().createPerformanceTracker();
/*     */       }
/*  97 */       sceneAccessor.setPerfTracker(paramScene, localPerformanceTracker);
/*     */     }
/*  99 */     return localPerformanceTracker;
/*     */   }
/*     */ 
/*     */   public static void releaseSceneTracker(Scene paramScene)
/*     */   {
/* 106 */     if (sceneAccessor != null)
/* 107 */       sceneAccessor.setPerfTracker(paramScene, null);
/*     */   }
/*     */ 
/*     */   public static void setSceneAccessor(SceneAccessor paramSceneAccessor)
/*     */   {
/* 115 */     sceneAccessor = paramSceneAccessor;
/*     */   }
/*     */ 
/*     */   public static void logEvent(String paramString)
/*     */   {
/* 129 */     Toolkit.getToolkit().getPerformanceTracker().doLogEvent(paramString);
/*     */   }
/*     */ 
/*     */   public static void outputLog()
/*     */   {
/* 138 */     Toolkit.getToolkit().getPerformanceTracker().doOutputLog();
/*     */   }
/*     */ 
/*     */   protected boolean isPerfLoggingEnabled()
/*     */   {
/* 145 */     return this.perfLoggingEnabled; } 
/* 146 */   protected void setPerfLoggingEnabled(boolean paramBoolean) { this.perfLoggingEnabled = paramBoolean; }
/*     */ 
/*     */ 
/*     */   protected abstract long nanoTime();
/*     */ 
/*     */   public abstract void doOutputLog();
/*     */ 
/*     */   public abstract void doLogEvent(String paramString);
/*     */ 
/*     */   public synchronized float getInstantFPS()
/*     */   {
/* 173 */     return this.instantFPS;
/*     */   }
/*     */ 
/*     */   public synchronized float getAverageFPS()
/*     */   {
/* 180 */     long l = nanoTime() - this.avgStartTime;
/* 181 */     if (l > 0L) {
/* 182 */       return this.avgFramesTotal * 1.0E+09F / (float)l;
/*     */     }
/* 184 */     return getInstantFPS();
/*     */   }
/*     */ 
/*     */   public synchronized void resetAverageFPS() {
/* 188 */     this.avgStartTime = nanoTime();
/* 189 */     this.avgFramesTotal = 0;
/*     */   }
/*     */ 
/*     */   public float getInstantPulses()
/*     */   {
/* 195 */     return this.instantPulses;
/*     */   }
/*     */ 
/*     */   public float getAveragePulses()
/*     */   {
/* 202 */     long l = nanoTime() - this.avgPulsesStartTime;
/* 203 */     if (l > 0L) {
/* 204 */       return this.avgPulsesTotal * 1.0E+09F / (float)l;
/*     */     }
/* 206 */     return getInstantPulses();
/*     */   }
/*     */ 
/*     */   public void resetAveragePulses() {
/* 210 */     this.avgPulsesStartTime = nanoTime();
/* 211 */     this.avgPulsesTotal = 0;
/*     */   }
/*     */ 
/*     */   public void pulse()
/*     */   {
/* 218 */     calcPulses();
/* 219 */     updateInstantFps();
/* 220 */     if (this.firstPulse) {
/* 221 */       doLogEvent("first repaint");
/* 222 */       this.firstPulse = false;
/* 223 */       resetAverageFPS();
/* 224 */       resetAveragePulses();
/* 225 */       if (this.onFirstPulse != null) {
/* 226 */         this.onFirstPulse.run();
/*     */       }
/*     */     }
/*     */ 
/* 230 */     if (this.onPulse != null) this.onPulse.run(); 
/*     */   }
/*     */ 
/*     */   public void frameRendered()
/*     */   {
/* 234 */     calcFPS();
/*     */   }
/*     */ 
/*     */   private void calcPulses() {
/* 238 */     this.avgPulsesTotal += 1;
/* 239 */     this.instantPulsesFrames += 1;
/* 240 */     updateInstantPulses();
/*     */   }
/*     */ 
/*     */   private synchronized void calcFPS() {
/* 244 */     this.avgFramesTotal += 1;
/* 245 */     this.instantFPSFrames += 1;
/* 246 */     updateInstantFps();
/*     */   }
/*     */ 
/*     */   private synchronized void updateInstantFps() {
/* 250 */     long l = nanoTime() - this.instantFPSStartTime;
/* 251 */     if (l > 1000000000L) {
/* 252 */       this.instantFPS = (1.0E+09F * this.instantFPSFrames / (float)l);
/* 253 */       this.instantFPSFrames = 0;
/* 254 */       this.instantFPSStartTime = nanoTime();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateInstantPulses() {
/* 259 */     long l = nanoTime() - this.instantPulsesStartTime;
/* 260 */     if (l > 1000000000L) {
/* 261 */       this.instantPulses = (1.0E+09F * this.instantPulsesFrames / (float)l);
/* 262 */       this.instantPulsesFrames = 0;
/* 263 */       this.instantPulsesStartTime = nanoTime();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setOnPulse(Runnable paramRunnable)
/*     */   {
/* 271 */     this.onPulse = paramRunnable; } 
/* 272 */   public Runnable getOnPulse() { return this.onPulse; }
/*     */ 
/*     */ 
/*     */   public void setOnFirstPulse(Runnable paramRunnable)
/*     */   {
/* 278 */     this.onFirstPulse = paramRunnable; } 
/* 279 */   public Runnable getOnFirstPulse() { return this.onFirstPulse; }
/*     */ 
/*     */ 
/*     */   public static abstract class SceneAccessor
/*     */   {
/*     */     public abstract void setPerfTracker(Scene paramScene, PerformanceTracker paramPerformanceTracker);
/*     */ 
/*     */     public abstract PerformanceTracker getPerfTracker(Scene paramScene);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.perf.PerformanceTracker
 * JD-Core Version:    0.6.2
 */