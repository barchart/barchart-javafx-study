/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import javafx.animation.Animation;
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class FiniteClipEnvelope extends ClipEnvelope
/*     */ {
/*     */   private boolean autoReverse;
/*     */   private int cycleCount;
/*     */   private long totalTicks;
/*     */   private long pos;
/*     */ 
/*     */   protected FiniteClipEnvelope(Animation paramAnimation)
/*     */   {
/*  60 */     super(paramAnimation);
/*  61 */     if (paramAnimation != null) {
/*  62 */       this.autoReverse = paramAnimation.isAutoReverse();
/*  63 */       this.cycleCount = paramAnimation.getCycleCount();
/*     */     }
/*  65 */     updateTotalTicks();
/*     */   }
/*     */ 
/*     */   public void setAutoReverse(boolean paramBoolean)
/*     */   {
/*  70 */     this.autoReverse = paramBoolean;
/*     */   }
/*     */ 
/*     */   protected double calculateCurrentRate()
/*     */   {
/*  75 */     return (this.ticks % (2L * this.cycleTicks) < this.cycleTicks ? 1 : 0) == (this.rate > 0.0D ? 1 : 0) ? this.rate : !this.autoReverse ? this.rate : -this.rate;
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleDuration(Duration paramDuration)
/*     */   {
/*  81 */     if (paramDuration.isIndefinite()) {
/*  82 */       return ClipEnvelopeFactory.create(this.animation);
/*     */     }
/*  84 */     updateCycleTicks(paramDuration);
/*  85 */     updateTotalTicks();
/*  86 */     return this;
/*     */   }
/*     */ 
/*     */   public ClipEnvelope setCycleCount(int paramInt)
/*     */   {
/*  91 */     if ((paramInt == 1) || (paramInt == -1)) {
/*  92 */       return ClipEnvelopeFactory.create(this.animation);
/*     */     }
/*  94 */     this.cycleCount = paramInt;
/*  95 */     updateTotalTicks();
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   public void setRate(double paramDouble)
/*     */   {
/* 101 */     int i = paramDouble * this.rate < 0.0D ? 1 : 0;
/* 102 */     long l = i != 0 ? this.totalTicks - this.ticks : this.ticks;
/* 103 */     Animation.Status localStatus = this.animation.getStatus();
/* 104 */     if (localStatus != Animation.Status.STOPPED) {
/* 105 */       if (localStatus == Animation.Status.RUNNING) {
/* 106 */         setCurrentRate(Math.abs(this.currentRate - this.rate) < 1.0E-12D ? paramDouble : -paramDouble);
/*     */       }
/* 108 */       this.deltaTicks = (l - Math.round((this.ticks - this.deltaTicks) * Math.abs(paramDouble / this.rate)));
/* 109 */       abortCurrentPulse();
/*     */     }
/* 111 */     this.ticks = l;
/* 112 */     this.rate = paramDouble;
/*     */   }
/*     */ 
/*     */   private void updateTotalTicks() {
/* 116 */     this.totalTicks = (this.cycleCount * this.cycleTicks);
/*     */   }
/*     */ 
/*     */   public void timePulse(long paramLong)
/*     */   {
/* 121 */     if (this.cycleTicks == 0L) {
/* 122 */       return;
/*     */     }
/* 124 */     this.aborted = false;
/* 125 */     this.inTimePulse = true;
/*     */     try
/*     */     {
/* 128 */       long l1 = this.ticks;
/* 129 */       this.ticks = ClipEnvelope.checkBounds(this.deltaTicks + Math.round(paramLong * Math.abs(this.rate)), this.totalTicks);
/*     */ 
/* 131 */       int i = this.ticks >= this.totalTicks ? 1 : 0;
/*     */ 
/* 133 */       long l2 = this.ticks - l1;
/* 134 */       if (l2 == 0L)
/*     */       {
/*     */         return;
/*     */       }
/* 138 */       long l3 = this.currentRate > 0.0D ? this.cycleTicks - this.pos : this.pos;
/*     */ 
/* 140 */       while (l2 >= l3) {
/* 141 */         if (l3 > 0L) {
/* 142 */           this.pos = (this.currentRate > 0.0D ? this.cycleTicks : 0L);
/* 143 */           l2 -= l3;
/* 144 */           if ((l2 > 0L) || ((!this.autoReverse) && (i == 0))) {
/* 145 */             this.animation.impl_playTo(this.pos, this.cycleTicks);
/* 146 */             if (this.aborted)
/*     */             {
/*     */               return;
/*     */             }
/*     */           }
/*     */         }
/* 152 */         if ((i == 0) || (l2 > 0L)) {
/* 153 */           if (this.autoReverse) {
/* 154 */             setCurrentRate(-this.currentRate);
/*     */           } else {
/* 156 */             this.pos = (this.currentRate > 0.0D ? 0L : this.cycleTicks);
/* 157 */             this.animation.impl_jumpTo(this.pos, this.cycleTicks);
/*     */           }
/*     */         }
/* 160 */         l3 = this.cycleTicks;
/*     */       }
/*     */ 
/* 163 */       this.pos += (this.currentRate > 0.0D ? l2 : -l2);
/* 164 */       this.animation.impl_playTo(this.pos, this.cycleTicks);
/*     */ 
/* 166 */       if ((i != 0) && (!this.aborted))
/* 167 */         this.animation.impl_finished();
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       this.inTimePulse = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void jumpTo(long paramLong)
/*     */   {
/* 177 */     if (this.cycleTicks == 0L) {
/* 178 */       return;
/*     */     }
/*     */ 
/* 181 */     long l1 = this.ticks;
/* 182 */     if (this.rate < 0.0D) {
/* 183 */       paramLong = this.totalTicks - paramLong;
/*     */     }
/* 185 */     this.ticks = ClipEnvelope.checkBounds(paramLong, this.totalTicks);
/* 186 */     long l2 = this.ticks - l1;
/* 187 */     if (l2 != 0L) {
/* 188 */       this.deltaTicks += l2;
/* 189 */       if (this.autoReverse) {
/* 190 */         int i = this.ticks % (2L * this.cycleTicks) < this.cycleTicks ? 1 : 0;
/* 191 */         if (i == (this.rate > 0.0D ? 1 : 0)) {
/* 192 */           this.pos = (this.ticks % this.cycleTicks);
/* 193 */           if (this.animation.getStatus() == Animation.Status.RUNNING)
/* 194 */             setCurrentRate(Math.abs(this.rate));
/*     */         }
/*     */         else {
/* 197 */           this.pos = (this.cycleTicks - this.ticks % this.cycleTicks);
/* 198 */           if (this.animation.getStatus() == Animation.Status.RUNNING)
/* 199 */             setCurrentRate(-Math.abs(this.rate));
/*     */         }
/*     */       }
/*     */       else {
/* 203 */         this.pos = (this.ticks % this.cycleTicks);
/* 204 */         if (this.rate < 0.0D) {
/* 205 */           this.pos = (this.cycleTicks - this.pos);
/*     */         }
/* 207 */         if ((this.pos == 0L) && (this.ticks > 0L)) {
/* 208 */           this.pos = this.cycleTicks;
/*     */         }
/*     */       }
/*     */ 
/* 212 */       this.animation.impl_jumpTo(this.pos, this.cycleTicks);
/* 213 */       abortCurrentPulse();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.FiniteClipEnvelope
 * JD-Core Version:    0.6.2
 */