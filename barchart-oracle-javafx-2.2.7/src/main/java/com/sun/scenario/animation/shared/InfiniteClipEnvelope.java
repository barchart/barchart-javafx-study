/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import javafx.animation.Animation;
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class InfiniteClipEnvelope extends ClipEnvelope
/*     */ {
/*     */   private boolean autoReverse;
/*     */   private long pos;
/*     */ 
/*     */   protected InfiniteClipEnvelope(Animation paramAnimation)
/*     */   {
/*  58 */     super(paramAnimation);
/*  59 */     if (paramAnimation != null)
/*  60 */       this.autoReverse = paramAnimation.isAutoReverse();
/*     */   }
/*     */ 
/*     */   public void setAutoReverse(boolean paramBoolean)
/*     */   {
/*  66 */     this.autoReverse = paramBoolean;
/*     */   }
/*     */ 
/*     */   protected double calculateCurrentRate()
/*     */   {
/*  71 */     return this.ticks % (2L * this.cycleTicks) < this.cycleTicks ? this.rate : !this.autoReverse ? this.rate : -this.rate;
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleDuration(Duration paramDuration)
/*     */   {
/*  77 */     if (paramDuration.isIndefinite()) {
/*  78 */       return ClipEnvelopeFactory.create(this.animation);
/*     */     }
/*  80 */     updateCycleTicks(paramDuration);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleCount(int paramInt)
/*     */   {
/*  86 */     return paramInt != -1 ? ClipEnvelopeFactory.create(this.animation) : this;
/*     */   }
/*     */ 
/*     */   public void setRate(double paramDouble)
/*     */   {
/*  91 */     Animation.Status localStatus = this.animation.getStatus();
/*  92 */     if (localStatus != Animation.Status.STOPPED) {
/*  93 */       if (localStatus == Animation.Status.RUNNING) {
/*  94 */         setCurrentRate(Math.abs(this.currentRate - this.rate) < 1.0E-12D ? paramDouble : -paramDouble);
/*     */       }
/*  96 */       this.deltaTicks = (this.ticks - Math.round((this.ticks - this.deltaTicks) * Math.abs(paramDouble / this.rate)));
/*  97 */       if (paramDouble * this.rate < 0.0D) {
/*  98 */         long l = 2L * this.cycleTicks - this.pos;
/*  99 */         this.deltaTicks += l;
/* 100 */         this.ticks += l;
/*     */       }
/* 102 */       abortCurrentPulse();
/*     */     }
/* 104 */     this.rate = paramDouble;
/*     */   }
/*     */ 
/*     */   public void timePulse(long paramLong)
/*     */   {
/* 109 */     if (this.cycleTicks == 0L) {
/* 110 */       return;
/*     */     }
/* 112 */     this.aborted = false;
/* 113 */     this.inTimePulse = true;
/*     */     try
/*     */     {
/* 116 */       long l1 = this.ticks;
/* 117 */       this.ticks = Math.max(0L, this.deltaTicks + Math.round(paramLong * Math.abs(this.rate)));
/*     */ 
/* 119 */       long l2 = this.ticks - l1;
/* 120 */       if (l2 == 0L)
/*     */       {
/*     */         return;
/*     */       }
/* 124 */       long l3 = this.currentRate > 0.0D ? this.cycleTicks - this.pos : this.pos;
/*     */ 
/* 126 */       while (l2 >= l3) {
/* 127 */         if (l3 > 0L) {
/* 128 */           this.pos = (this.currentRate > 0.0D ? this.cycleTicks : 0L);
/* 129 */           l2 -= l3;
/* 130 */           if (l2 > 0L) {
/* 131 */             this.animation.impl_playTo(this.pos, this.cycleTicks);
/* 132 */             if (this.aborted) {
/*     */               return;
/*     */             }
/*     */           }
/*     */         }
/* 137 */         if (this.autoReverse) {
/* 138 */           setCurrentRate(-this.currentRate);
/*     */         } else {
/* 140 */           this.pos = (this.currentRate > 0.0D ? 0L : this.cycleTicks);
/* 141 */           this.animation.impl_jumpTo(this.pos, this.cycleTicks);
/*     */         }
/* 143 */         l3 = this.cycleTicks;
/*     */       }
/*     */ 
/* 146 */       this.pos += (this.currentRate > 0.0D ? l2 : -l2);
/* 147 */       this.animation.impl_playTo(this.pos, this.cycleTicks);
/*     */     }
/*     */     finally {
/* 150 */       this.inTimePulse = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void jumpTo(long paramLong)
/*     */   {
/* 156 */     if (this.cycleTicks == 0L) {
/* 157 */       return;
/*     */     }
/* 159 */     long l1 = this.ticks;
/* 160 */     this.ticks = (Math.max(0L, paramLong) % (2L * this.cycleTicks));
/* 161 */     long l2 = this.ticks - l1;
/* 162 */     if (l2 != 0L) {
/* 163 */       this.deltaTicks += l2;
/* 164 */       if (this.autoReverse) {
/* 165 */         if (this.ticks > this.cycleTicks) {
/* 166 */           this.pos = (2L * this.cycleTicks - this.ticks);
/* 167 */           if (this.animation.getStatus() == Animation.Status.RUNNING)
/* 168 */             setCurrentRate(-this.rate);
/*     */         }
/*     */         else {
/* 171 */           this.pos = this.ticks;
/* 172 */           if (this.animation.getStatus() == Animation.Status.RUNNING)
/* 173 */             setCurrentRate(this.rate);
/*     */         }
/*     */       }
/*     */       else {
/* 177 */         this.pos = (this.ticks % this.cycleTicks);
/* 178 */         if (this.pos == 0L) {
/* 179 */           this.pos = this.ticks;
/*     */         }
/*     */       }
/* 182 */       this.animation.impl_jumpTo(this.pos, this.cycleTicks);
/* 183 */       abortCurrentPulse();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.InfiniteClipEnvelope
 * JD-Core Version:    0.6.2
 */