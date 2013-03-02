/*     */ package com.sun.scenario.animation;
/*     */ 
/*     */ import com.sun.scenario.DelayedRunnable;
/*     */ import com.sun.scenario.Settings;
/*     */ import com.sun.scenario.animation.shared.CurrentTime;
/*     */ import com.sun.scenario.animation.shared.PulseReceiver;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import javafx.animation.AnimationTimer;
/*     */ 
/*     */ public abstract class AbstractMasterTimer
/*     */ {
/*  41 */   protected final boolean useNanoTime = shouldUseNanoTime();
/*     */   protected static final String NOGAPS_PROP = "com.sun.scenario.animation.nogaps";
/*  44 */   protected static boolean nogaps = false;
/*     */   protected static final String FULLSPEED_PROP = "javafx.animation.fullspeed";
/*  47 */   protected static boolean fullspeed = false;
/*     */   protected static final String ADAPTIVE_PULSE_PROP = "com.sun.scenario.animation.adaptivepulse";
/*  52 */   protected static boolean useAdaptivePulse = false;
/*     */   protected static final String PULSE_PROP = "javafx.animation.pulse";
/*     */   protected static final String FRAMERATE_PROP = "javafx.animation.framerate";
/*     */   protected static final String ANIMATION_MBEAN_ENABLED = "com.sun.scenario.animation.AnimationMBean.enabled";
/*     */   protected static final boolean enableAnimationMBean = false;
/*     */   private static final double NANO_2_TICKS = 6.E-06D;
/*  76 */   protected final int PULSE_DURATION = getPulseDuration(1000);
/*  77 */   protected final int PULSE_DURATION_NS = getPulseDuration(1000000000);
/*  78 */   protected final int PULSE_DURATION_TICKS = getPulseDuration(6000);
/*     */ 
/*  80 */   private static final AnimationTimer[] emptyAnimationTimers = new AnimationTimer[0];
/*     */ 
/*  82 */   private boolean paused = false;
/*     */   private long totalPausedTime;
/*     */   private long startPauseTime;
/*  97 */   private final CurrentTime currentTime = createCurrentTime();
/*     */ 
/* 139 */   private final MainLoop theMaster = new MainLoop(null);
/*     */ 
/* 141 */   private final Set<PulseReceiver> receiverSet = new HashSet();
/* 142 */   private ArrayList<PulseReceiver> receiverList = new ArrayList();
/*     */   private boolean receiverListLocked;
/* 147 */   private final ArrayList<AnimationTimer> animationTimerList = new ArrayList();
/* 148 */   private AnimationTimer[] animationTimers = emptyAnimationTimers;
/*     */   private Runnable activeAnimationHandler;
/*     */ 
/*     */   public int getDefaultResolution()
/*     */   {
/*  70 */     return this.PULSE_DURATION_TICKS;
/*     */   }
/*     */ 
/*     */   protected CurrentTime createCurrentTime()
/*     */   {
/*  88 */     return shouldUseNanoTime() ? new NanoCurrentTime() : new MilliCurrentTime();
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */   {
/* 104 */     if (!this.paused) {
/* 105 */       this.startPauseTime = nanos();
/* 106 */       this.paused = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resume() {
/* 111 */     if (this.paused) {
/* 112 */       this.paused = false;
/* 113 */       this.totalPausedTime += nanos() - this.startPauseTime;
/*     */     }
/*     */   }
/*     */ 
/*     */   public long nanos() {
/* 118 */     return this.paused ? this.startPauseTime : this.currentTime.nanos() - this.totalPausedTime;
/*     */   }
/*     */ 
/*     */   public boolean isFullspeed() {
/* 122 */     return fullspeed;
/*     */   }
/*     */ 
/*     */   protected abstract boolean shouldUseNanoTime();
/*     */ 
/*     */   public void animationTrigger()
/*     */   {
/* 162 */     this.theMaster.run();
/*     */   }
/*     */ 
/*     */   public long getNextPulseTime()
/*     */   {
/* 167 */     return this.theMaster.getDelay();
/*     */   }
/*     */ 
/*     */   public boolean isActive() {
/* 171 */     return !this.theMaster.inactive;
/*     */   }
/*     */ 
/*     */   public void setActiveAnimationHandler(Runnable paramRunnable) {
/* 175 */     this.activeAnimationHandler = paramRunnable;
/*     */   }
/*     */ 
/*     */   public void addPulseReceiver(PulseReceiver paramPulseReceiver)
/*     */   {
/* 206 */     if (this.receiverSet.add(paramPulseReceiver)) {
/* 207 */       if (this.receiverListLocked) {
/* 208 */         this.receiverList = new ArrayList(this.receiverList);
/* 209 */         this.receiverListLocked = false;
/*     */       }
/* 211 */       this.receiverList.add(paramPulseReceiver);
/*     */     }
/* 213 */     this.theMaster.updateAnimationRunnable();
/*     */   }
/*     */ 
/*     */   public void removePulseReceiver(PulseReceiver paramPulseReceiver) {
/* 217 */     if (this.receiverSet.remove(paramPulseReceiver)) {
/* 218 */       if (this.receiverListLocked) {
/* 219 */         this.receiverList = new ArrayList(this.receiverList);
/* 220 */         this.receiverListLocked = false;
/*     */       }
/* 222 */       this.receiverList.remove(paramPulseReceiver);
/*     */     }
/* 224 */     if (this.receiverList.isEmpty())
/* 225 */       this.theMaster.updateAnimationRunnable();
/*     */   }
/*     */ 
/*     */   public synchronized void addAnimationTimer(AnimationTimer paramAnimationTimer)
/*     */   {
/* 230 */     this.animationTimerList.add(paramAnimationTimer);
/* 231 */     updateAnimationTimers();
/* 232 */     this.theMaster.updateAnimationRunnable();
/*     */   }
/*     */ 
/*     */   private void updateAnimationTimers()
/*     */   {
/* 239 */     this.animationTimers = new AnimationTimer[this.animationTimerList.size()];
/* 240 */     System.arraycopy(this.animationTimerList.toArray(), 0, this.animationTimers, 0, this.animationTimers.length);
/*     */   }
/*     */ 
/*     */   public synchronized void removeAnimationTimer(AnimationTimer paramAnimationTimer)
/*     */   {
/* 245 */     if (this.animationTimerList.remove(paramAnimationTimer)) {
/* 246 */       updateAnimationTimers();
/* 247 */       if (this.animationTimerList.isEmpty())
/* 248 */         this.theMaster.updateAnimationRunnable();
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void notifyJobsReady()
/*     */   {
/* 255 */     postUpdateAnimationRunnable(this.theMaster);
/*     */   }
/*     */ 
/*     */   protected void recordStart(long paramLong)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void recordEnd()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void recordAnimationEnd()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected abstract void postUpdateAnimationRunnable(DelayedRunnable paramDelayedRunnable);
/*     */ 
/*     */   protected abstract int getPulseDuration(int paramInt);
/*     */ 
/*     */   protected void timePulseImpl(long paramLong)
/*     */   {
/* 361 */     ArrayList localArrayList = this.receiverList;
/*     */     try {
/* 363 */       this.receiverListLocked = true;
/* 364 */       int i = localArrayList.size();
/* 365 */       for (j = 0; j < i; j++)
/* 366 */         ((PulseReceiver)localArrayList.get(j)).timePulse(Math.round(paramLong * 6.E-06D));
/*     */     }
/*     */     finally {
/* 369 */       this.receiverListLocked = false;
/*     */     }
/* 371 */     recordAnimationEnd();
/*     */ 
/* 374 */     AnimationTimer[] arrayOfAnimationTimer = this.animationTimers;
/* 375 */     for (int j = 0; j < arrayOfAnimationTimer.length; j++)
/* 376 */       arrayOfAnimationTimer[j].handle(paramLong);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 126 */     nogaps = Settings.getBoolean("com.sun.scenario.animation.nogaps");
/* 127 */     fullspeed = Settings.getBoolean("javafx.animation.fullspeed");
/* 128 */     useAdaptivePulse = Settings.getBoolean("com.sun.scenario.animation.adaptivepulse", useAdaptivePulse);
/*     */ 
/* 131 */     int i = Settings.getInt("javafx.animation.pulse", -1);
/* 132 */     if (i != -1)
/* 133 */       System.err.println("Setting PULSE_DURATION to " + i + " hz");
/*     */   }
/*     */ 
/*     */   private final class MainLoop
/*     */     implements DelayedRunnable
/*     */   {
/* 277 */     private boolean inactive = true;
/*     */ 
/* 279 */     private long nextPulseTime = AbstractMasterTimer.this.nanos();
/* 280 */     private long lastPulseDuration = -2147483648L;
/*     */ 
/*     */     private MainLoop() {
/*     */     }
/* 284 */     public void run() { if (AbstractMasterTimer.this.paused) {
/* 285 */         return;
/*     */       }
/* 287 */       long l = AbstractMasterTimer.this.nanos();
/* 288 */       AbstractMasterTimer.this.recordStart((this.nextPulseTime - l) / 1000000L);
/* 289 */       AbstractMasterTimer.this.timePulseImpl(l);
/* 290 */       AbstractMasterTimer.this.recordEnd();
/* 291 */       updateNextPulseTime(l);
/*     */ 
/* 293 */       updateAnimationRunnable();
/*     */     }
/*     */ 
/*     */     public long getDelay()
/*     */     {
/* 298 */       long l1 = AbstractMasterTimer.this.nanos();
/* 299 */       long l2 = (this.nextPulseTime - l1) / 1000000L;
/* 300 */       return Math.max(0L, l2);
/*     */     }
/*     */ 
/*     */     private void updateNextPulseTime(long paramLong) {
/* 304 */       long l1 = AbstractMasterTimer.this.nanos();
/* 305 */       if (AbstractMasterTimer.fullspeed) {
/* 306 */         this.nextPulseTime = l1;
/*     */       }
/* 308 */       else if (AbstractMasterTimer.useAdaptivePulse)
/*     */       {
/* 313 */         this.nextPulseTime += AbstractMasterTimer.this.PULSE_DURATION_NS;
/* 314 */         long l2 = l1 - paramLong;
/*     */ 
/* 321 */         if (l2 - this.lastPulseDuration > 500000L) {
/* 322 */           l2 /= 2L;
/*     */         }
/* 324 */         if (l2 < 2000000L) {
/* 325 */           l2 = 2000000L;
/*     */         }
/*     */ 
/* 329 */         if (l2 >= AbstractMasterTimer.this.PULSE_DURATION_NS) {
/* 330 */           l2 = 3 * AbstractMasterTimer.this.PULSE_DURATION_NS / 4;
/*     */         }
/* 332 */         this.lastPulseDuration = l2;
/* 333 */         this.nextPulseTime -= l2;
/*     */       } else {
/* 335 */         this.nextPulseTime = ((this.nextPulseTime + AbstractMasterTimer.this.PULSE_DURATION_NS) / AbstractMasterTimer.this.PULSE_DURATION_NS * AbstractMasterTimer.this.PULSE_DURATION_NS);
/*     */       }
/*     */     }
/*     */ 
/*     */     private synchronized void updateAnimationRunnable()
/*     */     {
/* 342 */       boolean bool = (AbstractMasterTimer.this.animationTimerList.isEmpty()) && (AbstractMasterTimer.this.receiverList.isEmpty());
/* 343 */       if (this.inactive != bool) {
/* 344 */         this.inactive = bool;
/* 345 */         MainLoop localMainLoop = this.inactive ? null : this;
/* 346 */         if (AbstractMasterTimer.this.activeAnimationHandler != null) {
/* 347 */           AbstractMasterTimer.this.activeAnimationHandler.run();
/*     */         }
/*     */ 
/* 350 */         AbstractMasterTimer.this.postUpdateAnimationRunnable(localMainLoop);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.AbstractMasterTimer
 * JD-Core Version:    0.6.2
 */