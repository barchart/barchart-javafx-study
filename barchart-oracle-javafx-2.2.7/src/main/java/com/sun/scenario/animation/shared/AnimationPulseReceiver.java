/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import javafx.animation.Animation;
/*     */ 
/*     */ public class AnimationPulseReceiver
/*     */   implements PulseReceiver
/*     */ {
/*     */   private static final double NANO_2_TICKS = 6.E-06D;
/*     */   private final Animation animation;
/*     */   private final AbstractMasterTimer timer;
/*     */   private long startTime;
/*     */   private long pauseTime;
/*  61 */   private boolean paused = false;
/*     */ 
/*     */   public AnimationPulseReceiver(Animation paramAnimation, AbstractMasterTimer paramAbstractMasterTimer) {
/*  64 */     this.animation = paramAnimation;
/*  65 */     this.timer = paramAbstractMasterTimer;
/*     */   }
/*     */ 
/*     */   protected void addPulseReceiver()
/*     */   {
/*  70 */     this.timer.addPulseReceiver(this);
/*     */   }
/*     */ 
/*     */   protected void removePulseReceiver() {
/*  74 */     this.timer.removePulseReceiver(this);
/*     */   }
/*     */ 
/*     */   private long now() {
/*  78 */     return Math.round(this.timer.nanos() * 6.E-06D);
/*     */   }
/*     */ 
/*     */   public void start(long paramLong) {
/*  82 */     this.paused = false;
/*  83 */     this.startTime = (now() + paramLong);
/*  84 */     addPulseReceiver();
/*     */   }
/*     */ 
/*     */   public void stop() {
/*  88 */     if (!this.paused)
/*  89 */       removePulseReceiver();
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */   {
/*  94 */     if (!this.paused) {
/*  95 */       this.pauseTime = now();
/*  96 */       this.paused = true;
/*  97 */       removePulseReceiver();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resume() {
/* 102 */     if (this.paused) {
/* 103 */       long l = now() - this.pauseTime;
/* 104 */       this.startTime += l;
/* 105 */       this.paused = false;
/* 106 */       addPulseReceiver();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void timePulse(long paramLong)
/*     */   {
/* 112 */     long l = paramLong - this.startTime;
/* 113 */     if (l < 0L) {
/* 114 */       return;
/*     */     }
/*     */ 
/* 117 */     this.animation.impl_timePulse(l);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.AnimationPulseReceiver
 * JD-Core Version:    0.6.2
 */