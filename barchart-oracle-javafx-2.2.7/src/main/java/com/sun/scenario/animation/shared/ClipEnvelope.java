/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import javafx.animation.Animation;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public abstract class ClipEnvelope
/*     */ {
/*     */   protected static final long INDEFINITE = 9223372036854775807L;
/*     */   protected static final double EPSILON = 1.0E-12D;
/*     */   protected Animation animation;
/*  54 */   protected double rate = 1.0D;
/*  55 */   protected long cycleTicks = 0L;
/*     */ 
/*  58 */   protected long deltaTicks = 0L;
/*  59 */   protected long ticks = 0L;
/*  60 */   protected double currentRate = this.rate;
/*  61 */   protected boolean inTimePulse = false;
/*  62 */   protected boolean aborted = false;
/*     */ 
/*     */   protected ClipEnvelope(Animation paramAnimation) {
/*  65 */     this.animation = paramAnimation;
/*  66 */     if (paramAnimation != null) {
/*  67 */       Duration localDuration = paramAnimation.getCycleDuration();
/*  68 */       this.cycleTicks = Math.round(6.0D * localDuration.toMillis());
/*  69 */       this.rate = paramAnimation.getRate(); } 
/*     */   }
/*     */   public abstract ClipEnvelope setCycleDuration(Duration paramDuration);
/*     */ 
/*     */   public abstract void setRate(double paramDouble);
/*     */ 
/*     */   public abstract void setAutoReverse(boolean paramBoolean);
/*     */ 
/*     */   public abstract ClipEnvelope setCycleCount(int paramInt);
/*     */ 
/*  79 */   protected void updateCycleTicks(Duration paramDuration) { this.cycleTicks = Math.round(6.0D * paramDuration.toMillis()); }
/*     */ 
/*     */   public boolean wasSynched()
/*     */   {
/*  83 */     return this.cycleTicks != 0L;
/*     */   }
/*     */ 
/*     */   public void start() {
/*  87 */     setCurrentRate(calculateCurrentRate());
/*  88 */     this.deltaTicks = this.ticks;
/*     */   }
/*     */ 
/*     */   public abstract void timePulse(long paramLong);
/*     */ 
/*     */   public abstract void jumpTo(long paramLong);
/*     */ 
/*     */   public void abortCurrentPulse() {
/*  96 */     if (this.inTimePulse) {
/*  97 */       this.aborted = true;
/*  98 */       this.inTimePulse = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract double calculateCurrentRate();
/*     */ 
/*     */   protected void setCurrentRate(double paramDouble) {
/* 105 */     this.currentRate = paramDouble;
/* 106 */     this.animation.impl_setCurrentRate(paramDouble);
/*     */   }
/*     */ 
/*     */   protected static long checkBounds(long paramLong1, long paramLong2) {
/* 110 */     return Math.max(0L, Math.min(paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   public double getCurrentRate() {
/* 114 */     return this.currentRate;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.ClipEnvelope
 * JD-Core Version:    0.6.2
 */