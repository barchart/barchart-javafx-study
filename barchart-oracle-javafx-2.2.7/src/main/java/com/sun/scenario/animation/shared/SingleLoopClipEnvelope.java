/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import javafx.animation.Animation;
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class SingleLoopClipEnvelope extends ClipEnvelope
/*     */ {
/*     */   private int cycleCount;
/*     */ 
/*     */   public void setRate(double paramDouble)
/*     */   {
/*  58 */     Animation.Status localStatus = this.animation.getStatus();
/*  59 */     if (localStatus != Animation.Status.STOPPED) {
/*  60 */       if (localStatus == Animation.Status.RUNNING) {
/*  61 */         setCurrentRate(Math.abs(this.currentRate - this.rate) < 1.0E-12D ? paramDouble : -paramDouble);
/*     */       }
/*  63 */       this.deltaTicks = (this.ticks - Math.round((this.ticks - this.deltaTicks) * paramDouble / this.rate));
/*  64 */       abortCurrentPulse();
/*     */     }
/*  66 */     this.rate = paramDouble;
/*     */   }
/*     */ 
/*     */   public void setAutoReverse(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected double calculateCurrentRate()
/*     */   {
/*  76 */     return this.rate;
/*     */   }
/*     */ 
/*     */   protected SingleLoopClipEnvelope(Animation paramAnimation) {
/*  80 */     super(paramAnimation);
/*  81 */     if (paramAnimation != null)
/*  82 */       this.cycleCount = paramAnimation.getCycleCount();
/*     */   }
/*     */ 
/*     */   public boolean wasSynched()
/*     */   {
/*  88 */     return (super.wasSynched()) && (this.cycleCount != 0);
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleDuration(Duration paramDuration)
/*     */   {
/*  93 */     if ((this.cycleCount != 1) && (!paramDuration.isIndefinite())) {
/*  94 */       return ClipEnvelopeFactory.create(this.animation);
/*     */     }
/*  96 */     updateCycleTicks(paramDuration);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleCount(int paramInt)
/*     */   {
/* 102 */     if ((paramInt != 1) && (this.cycleTicks != 9223372036854775807L)) {
/* 103 */       return ClipEnvelopeFactory.create(this.animation);
/*     */     }
/* 105 */     this.cycleCount = paramInt;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public void timePulse(long paramLong)
/*     */   {
/* 111 */     if (this.cycleTicks == 0L) {
/* 112 */       return;
/*     */     }
/* 114 */     this.aborted = false;
/* 115 */     this.inTimePulse = true;
/*     */     try
/*     */     {
/* 118 */       this.ticks = ClipEnvelope.checkBounds(this.deltaTicks + Math.round(paramLong * this.currentRate), this.cycleTicks);
/* 119 */       this.animation.impl_playTo(this.ticks, this.cycleTicks);
/*     */ 
/* 121 */       int i = this.ticks == 0L ? 1 : this.currentRate > 0.0D ? 0 : this.ticks == this.cycleTicks ? 1 : 0;
/* 122 */       if ((i != 0) && (!this.aborted))
/* 123 */         this.animation.impl_finished();
/*     */     }
/*     */     finally {
/* 126 */       this.inTimePulse = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void jumpTo(long paramLong)
/*     */   {
/* 132 */     if (this.cycleTicks == 0L) {
/* 133 */       return;
/*     */     }
/* 135 */     long l = ClipEnvelope.checkBounds(paramLong, this.cycleTicks);
/* 136 */     this.deltaTicks += l - this.ticks;
/* 137 */     this.ticks = l;
/*     */ 
/* 139 */     this.animation.impl_jumpTo(l, this.cycleTicks);
/*     */ 
/* 141 */     abortCurrentPulse();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.SingleLoopClipEnvelope
 * JD-Core Version:    0.6.2
 */