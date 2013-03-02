/*     */ package com.sun.scenario.animation;
/*     */ 
/*     */ import com.sun.scenario.ToolkitAccessor;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ public class AnimationPulse
/*     */   implements AnimationPulseMBean
/*     */ {
/* 213 */   private final Queue<PulseData> pulseDataQueue = new ConcurrentLinkedQueue();
/*     */ 
/* 216 */   private PulseData pulseData = null;
/*     */ 
/* 220 */   private volatile boolean isEnabled = false;
/*     */ 
/* 274 */   private final AtomicLong pulseCounter = new AtomicLong();
/*     */ 
/* 276 */   private final AtomicLong startMax = new AtomicLong();
/* 277 */   private final AtomicLong startSum = new AtomicLong();
/* 278 */   private final AtomicLong startAv = new AtomicLong();
/*     */ 
/* 280 */   private final AtomicLong endMax = new AtomicLong();
/* 281 */   private final AtomicLong endSum = new AtomicLong();
/* 282 */   private final AtomicLong endAv = new AtomicLong();
/*     */ 
/* 284 */   private final AtomicLong animationDurationMax = new AtomicLong();
/* 285 */   private final AtomicLong animationDurationSum = new AtomicLong();
/* 286 */   private final AtomicLong animationDurationAv = new AtomicLong();
/*     */ 
/* 288 */   private final AtomicLong paintingDurationMax = new AtomicLong();
/* 289 */   private final AtomicLong paintingDurationSum = new AtomicLong();
/* 290 */   private final AtomicLong paintingDurationAv = new AtomicLong();
/*     */ 
/* 292 */   private final AtomicLong pulseDurationMax = new AtomicLong();
/* 293 */   private final AtomicLong pulseDurationSum = new AtomicLong();
/* 294 */   private final AtomicLong pulseDurationAv = new AtomicLong();
/*     */ 
/* 296 */   private final AtomicLong[] maxAndAv = { this.startMax, this.startSum, this.startAv, this.endMax, this.endSum, this.endAv, this.animationDurationMax, this.animationDurationSum, this.animationDurationAv, this.paintingDurationMax, this.paintingDurationSum, this.paintingDurationAv, this.pulseDurationMax, this.pulseDurationSum, this.pulseDurationAv };
/*     */ 
/* 303 */   private final AnimationPulse.PulseData.Accessor[] maxAndAvAccessors = { PulseData.PulseStartAccessor, PulseData.PulseEndAccessor, PulseData.AnimationDurationAccessor, PulseData.PaintingDurationAccessor, PulseData.PulseDurationAccessor };
/*     */ 
/* 322 */   private final AtomicLong skippedPulses = new AtomicLong();
/*     */ 
/* 324 */   private int skipPulses = 100;
/*     */ 
/*     */   public static AnimationPulse getDefaultBean()
/*     */   {
/*  39 */     return AnimationPulseHolder.holder;
/*     */   }
/*     */ 
/*     */   public boolean getEnabled()
/*     */   {
/* 223 */     return this.isEnabled;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean paramBoolean)
/*     */   {
/* 228 */     if (paramBoolean == this.isEnabled) {
/* 229 */       return;
/*     */     }
/* 231 */     this.isEnabled = paramBoolean;
/*     */   }
/*     */ 
/*     */   public long getPULSE_DURATION()
/*     */   {
/* 237 */     return ToolkitAccessor.getMasterTimer().getPulseDuration(1000);
/*     */   }
/*     */ 
/*     */   public long getSkippedPulses()
/*     */   {
/* 243 */     return this.skippedPulses.get();
/*     */   }
/*     */ 
/*     */   public long getSkippedPulsesIn1Sec()
/*     */   {
/* 248 */     long l = 0L;
/* 249 */     for (PulseData localPulseData : this.pulseDataQueue) {
/* 250 */       if (localPulseData.getPulseStartFromNow(TimeUnit.SECONDS) == 0L) {
/* 251 */         l += localPulseData.getSkippedPulses();
/*     */       }
/*     */     }
/* 254 */     return l;
/*     */   }
/*     */ 
/*     */   public void recordStart(long paramLong)
/*     */   {
/* 259 */     if (!getEnabled()) {
/* 260 */       return;
/*     */     }
/* 262 */     this.pulseData = new PulseData(TimeUnit.MILLISECONDS.toNanos(paramLong));
/*     */   }
/*     */ 
/*     */   private void purgeOldPulseData()
/*     */   {
/* 267 */     Iterator localIterator = this.pulseDataQueue.iterator();
/*     */ 
/* 269 */     while ((localIterator.hasNext()) && (((PulseData)localIterator.next()).getPulseStartFromNow(TimeUnit.SECONDS) > 1L))
/* 270 */       localIterator.remove();
/*     */   }
/*     */ 
/*     */   private void updateMaxAndAv()
/*     */   {
/* 312 */     long l1 = this.pulseCounter.incrementAndGet();
/* 313 */     for (int i = 0; i < this.maxAndAvAccessors.length; i++) {
/* 314 */       int j = i * 3;
/* 315 */       long l2 = this.maxAndAvAccessors[i].get(this.pulseData, TimeUnit.MILLISECONDS);
/* 316 */       this.maxAndAv[j].set(Math.max(this.maxAndAv[j].get(), l2));
/* 317 */       this.maxAndAv[(j + 1)].addAndGet(l2);
/* 318 */       this.maxAndAv[(j + 2)].set(this.maxAndAv[(j + 1)].get() / l1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void recordEnd()
/*     */   {
/* 326 */     if (!getEnabled()) {
/* 327 */       return;
/*     */     }
/* 329 */     if (this.skipPulses > 0)
/*     */     {
/* 332 */       this.skipPulses -= 1;
/* 333 */       this.pulseData = null;
/* 334 */       return;
/*     */     }
/* 336 */     this.pulseData.recordEnd();
/* 337 */     purgeOldPulseData();
/* 338 */     updateMaxAndAv();
/* 339 */     this.skippedPulses.addAndGet(this.pulseData.getSkippedPulses());
/* 340 */     this.pulseDataQueue.add(this.pulseData);
/* 341 */     this.pulseData = null;
/*     */   }
/*     */ 
/*     */   private long getAv(AnimationPulse.PulseData.Accessor paramAccessor, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 349 */     if (!getEnabled()) {
/* 350 */       return 0L;
/*     */     }
/* 352 */     long l1 = 0L;
/* 353 */     long l2 = 0L;
/* 354 */     for (PulseData localPulseData : this.pulseDataQueue) {
/* 355 */       if (localPulseData.getPulseStartFromNow(paramTimeUnit) <= paramLong) {
/* 356 */         l1 += paramAccessor.get(localPulseData, paramTimeUnit);
/* 357 */         l2 += 1L;
/*     */       }
/*     */     }
/* 360 */     return l2 == 0L ? 0L : l1 / l2;
/*     */   }
/*     */ 
/*     */   private long getMax(AnimationPulse.PulseData.Accessor paramAccessor, long paramLong, TimeUnit paramTimeUnit) {
/* 364 */     if (!getEnabled()) {
/* 365 */       return 0L;
/*     */     }
/* 367 */     long l = 0L;
/* 368 */     for (PulseData localPulseData : this.pulseDataQueue) {
/* 369 */       if (localPulseData.getPulseStartFromNow(paramTimeUnit) <= paramLong) {
/* 370 */         l = Math.max(paramAccessor.get(localPulseData, paramTimeUnit), l);
/*     */       }
/*     */     }
/* 373 */     return l;
/*     */   }
/*     */ 
/*     */   public long getStartMax()
/*     */   {
/* 378 */     return this.startMax.get();
/*     */   }
/*     */ 
/*     */   public long getStartAv()
/*     */   {
/* 383 */     return this.startAv.get();
/*     */   }
/*     */ 
/*     */   public long getStartMaxIn1Sec()
/*     */   {
/* 388 */     return getMax(PulseData.PulseStartAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getStartAvIn100Millis()
/*     */   {
/* 393 */     return getAv(PulseData.PulseStartAccessor, 100L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getEndMax()
/*     */   {
/* 398 */     return this.endMax.get();
/*     */   }
/*     */ 
/*     */   public long getEndMaxIn1Sec()
/*     */   {
/* 403 */     return getMax(PulseData.PulseEndAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getEndAv()
/*     */   {
/* 408 */     return this.endAv.get();
/*     */   }
/*     */ 
/*     */   public long getEndAvIn100Millis()
/*     */   {
/* 413 */     return getAv(PulseData.PulseEndAccessor, 100L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public void recordAnimationEnd() {
/* 417 */     if ((getEnabled()) && (this.pulseData != null))
/* 418 */       this.pulseData.recordAnimationEnd();
/*     */   }
/*     */ 
/*     */   public long getAnimationDurationMax()
/*     */   {
/* 424 */     return this.animationDurationMax.get();
/*     */   }
/*     */ 
/*     */   public long getAnimationMaxIn1Sec()
/*     */   {
/* 429 */     return getMax(PulseData.AnimationDurationAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getAnimationDurationAv()
/*     */   {
/* 434 */     return this.animationDurationAv.get();
/*     */   }
/*     */ 
/*     */   public long getAnimationDurationAvIn100Millis()
/*     */   {
/* 439 */     return getAv(PulseData.AnimationDurationAccessor, 100L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public void recordPaintingStart() {
/* 443 */     if ((getEnabled()) && (this.pulseData != null))
/* 444 */       this.pulseData.recordPaintingStart();
/*     */   }
/*     */ 
/*     */   public void recordPaintingEnd()
/*     */   {
/* 449 */     if ((getEnabled()) && (this.pulseData != null))
/* 450 */       this.pulseData.recordPaintingEnd();
/*     */   }
/*     */ 
/*     */   public long getPaintingDurationMax()
/*     */   {
/* 456 */     return this.paintingDurationMax.get();
/*     */   }
/*     */ 
/*     */   public long getPaintingDurationMaxIn1Sec()
/*     */   {
/* 461 */     return getMax(PulseData.PaintingDurationAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getPaintingDurationAv()
/*     */   {
/* 466 */     return this.paintingDurationAv.get();
/*     */   }
/*     */ 
/*     */   public long getPaitningDurationAvIn100Millis()
/*     */   {
/* 471 */     return getAv(PulseData.PaintingDurationAccessor, 100L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public void recordScenePaintingStart() {
/* 475 */     if ((getEnabled()) && (this.pulseData != null))
/* 476 */       this.pulseData.recordScenePaintingStart();
/*     */   }
/*     */ 
/*     */   public void recordScenePaintingEnd()
/*     */   {
/* 481 */     if ((getEnabled()) && (this.pulseData != null))
/* 482 */       this.pulseData.recordScenePaintingEnd();
/*     */   }
/*     */ 
/*     */   public long getScenePaintingDurationMaxIn1Sec()
/*     */   {
/* 488 */     return getMax(PulseData.ScenePaintingDurationAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getPulseDurationMax()
/*     */   {
/* 493 */     return this.pulseDurationMax.get();
/*     */   }
/*     */ 
/*     */   public long getPulseDurationMaxIn1Sec()
/*     */   {
/* 498 */     return getMax(PulseData.PulseDurationAccessor, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getPulseDurationAv()
/*     */   {
/* 503 */     return this.pulseDurationAv.get();
/*     */   }
/*     */ 
/*     */   public long getPulseDurationAvIn100Millis()
/*     */   {
/* 508 */     return getAv(PulseData.PulseDurationAccessor, 100L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getPaintingPreparationDurationMaxIn1Sec()
/*     */   {
/* 513 */     return getMax(PulseData.PaintingPreparationDuration, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   public long getPaintingFinalizationDurationMaxIn1Sec()
/*     */   {
/* 518 */     return getMax(PulseData.PaintingFinalizationDuration, 1000L, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   private static class PulseData
/*     */   {
/*     */     private final long startNanos;
/*     */     private final long scheduledNanos;
/*  49 */     private long animationEndNanos = -9223372036854775808L;
/*  50 */     private long paintingStartNanos = -9223372036854775808L;
/*  51 */     private long paintingEndNanos = -9223372036854775808L;
/*  52 */     private long scenePaintingStartNanos = -9223372036854775808L;
/*  53 */     private long scenePaintingEndNanos = -9223372036854775808L;
/*  54 */     private long endNanos = -9223372036854775808L;
/*     */ 
/* 144 */     static final Accessor PulseStartAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 147 */         return paramAnonymousPulseData.getPulseStart(paramAnonymousTimeUnit);
/*     */       }
/* 144 */     };
/*     */ 
/* 151 */     static final Accessor AnimationDurationAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 154 */         return paramAnonymousPulseData.getAnimationDuration(paramAnonymousTimeUnit);
/*     */       }
/* 151 */     };
/*     */ 
/* 158 */     static final Accessor PaintingDurationAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 161 */         return paramAnonymousPulseData.getPaintingDuration(paramAnonymousTimeUnit);
/*     */       }
/* 158 */     };
/*     */ 
/* 165 */     static final Accessor ScenePaintingDurationAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 168 */         return paramAnonymousPulseData.getScenePaintingDuration(paramAnonymousTimeUnit);
/*     */       }
/* 165 */     };
/*     */ 
/* 172 */     static final Accessor PulseDurationAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 175 */         return paramAnonymousPulseData.getPulseDuration(paramAnonymousTimeUnit);
/*     */       }
/* 172 */     };
/*     */ 
/* 179 */     static final Accessor PulseEndAccessor = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 182 */         return paramAnonymousPulseData.getPulseEnd(paramAnonymousTimeUnit);
/*     */       }
/* 179 */     };
/*     */ 
/* 186 */     static final Accessor PaintingPreparationDuration = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 189 */         return paramAnonymousPulseData.getPaintingDuration(paramAnonymousTimeUnit);
/*     */       }
/* 186 */     };
/*     */ 
/* 193 */     static final Accessor PaintingFinalizationDuration = new Accessor()
/*     */     {
/*     */       public long get(AnimationPulse.PulseData paramAnonymousPulseData, TimeUnit paramAnonymousTimeUnit) {
/* 196 */         return paramAnonymousPulseData.getPaintingFinalizationDuration(paramAnonymousTimeUnit);
/*     */       }
/* 193 */     };
/*     */ 
/*     */     PulseData(long paramLong)
/*     */     {
/*  57 */       this.startNanos = ToolkitAccessor.getMasterTimer().nanos();
/*  58 */       this.scheduledNanos = (this.startNanos + paramLong);
/*     */     }
/*     */ 
/*     */     long getPulseStart(TimeUnit paramTimeUnit)
/*     */     {
/*  63 */       return paramTimeUnit.convert(this.startNanos - this.scheduledNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     void recordAnimationEnd() {
/*  67 */       this.animationEndNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     long getAnimationDuration(TimeUnit paramTimeUnit) {
/*  71 */       return this.animationEndNanos > -9223372036854775808L ? paramTimeUnit.convert(this.animationEndNanos - this.startNanos, TimeUnit.NANOSECONDS) : 0L;
/*     */     }
/*     */ 
/*     */     void recordPaintingStart()
/*     */     {
/*  77 */       this.paintingStartNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     void recordPaintingEnd() {
/*  81 */       this.paintingEndNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     long getPaintingDuration(TimeUnit paramTimeUnit) {
/*  85 */       return (this.paintingEndNanos > -9223372036854775808L) && (this.paintingStartNanos > -9223372036854775808L) ? paramTimeUnit.convert(this.paintingEndNanos - this.paintingStartNanos, TimeUnit.NANOSECONDS) : 0L;
/*     */     }
/*     */ 
/*     */     void recordScenePaintingStart()
/*     */     {
/*  91 */       this.scenePaintingStartNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     void recordScenePaintingEnd() {
/*  95 */       this.scenePaintingEndNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     long getScenePaintingDuration(TimeUnit paramTimeUnit) {
/*  99 */       return (this.scenePaintingEndNanos > -9223372036854775808L) && (this.scenePaintingStartNanos > -9223372036854775808L) ? paramTimeUnit.convert(this.scenePaintingEndNanos - this.scenePaintingStartNanos, TimeUnit.NANOSECONDS) : 0L;
/*     */     }
/*     */ 
/*     */     long getPaintingPreparationDuration(TimeUnit paramTimeUnit)
/*     */     {
/* 105 */       return (this.paintingStartNanos > -9223372036854775808L) && (this.scenePaintingStartNanos > -9223372036854775808L) ? paramTimeUnit.convert(this.scenePaintingStartNanos - this.paintingStartNanos, TimeUnit.NANOSECONDS) : 0L;
/*     */     }
/*     */ 
/*     */     long getPaintingFinalizationDuration(TimeUnit paramTimeUnit)
/*     */     {
/* 111 */       return (this.scenePaintingEndNanos > -9223372036854775808L) && (this.paintingEndNanos > -9223372036854775808L) ? paramTimeUnit.convert(this.paintingEndNanos - this.scenePaintingEndNanos, TimeUnit.NANOSECONDS) : 0L;
/*     */     }
/*     */ 
/*     */     void recordEnd()
/*     */     {
/* 117 */       this.endNanos = ToolkitAccessor.getMasterTimer().nanos();
/*     */     }
/*     */ 
/*     */     long getPulseDuration(TimeUnit paramTimeUnit) {
/* 121 */       return paramTimeUnit.convert(this.endNanos - this.startNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     long getPulseEnd(TimeUnit paramTimeUnit)
/*     */     {
/* 126 */       return paramTimeUnit.convert(this.endNanos - this.scheduledNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     long getPulseStartFromNow(TimeUnit paramTimeUnit)
/*     */     {
/* 131 */       return paramTimeUnit.convert(ToolkitAccessor.getMasterTimer().nanos() - this.startNanos, TimeUnit.NANOSECONDS);
/*     */     }
/*     */ 
/*     */     long getSkippedPulses()
/*     */     {
/* 136 */       return getPulseEnd(TimeUnit.MILLISECONDS) / AnimationPulse.getDefaultBean().getPULSE_DURATION();
/*     */     }
/*     */ 
/*     */     static abstract interface Accessor
/*     */     {
/*     */       public abstract long get(AnimationPulse.PulseData paramPulseData, TimeUnit paramTimeUnit);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class AnimationPulseHolder
/*     */   {
/*  42 */     private static final AnimationPulse holder = new AnimationPulse();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.AnimationPulse
 * JD-Core Version:    0.6.2
 */