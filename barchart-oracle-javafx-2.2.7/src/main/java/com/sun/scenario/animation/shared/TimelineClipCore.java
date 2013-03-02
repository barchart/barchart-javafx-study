/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class TimelineClipCore
/*     */ {
/*     */   private static final int UNDEFINED_KEYFRAME = -1;
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   Timeline timeline;
/*  63 */   private KeyFrame[] keyFrames = new KeyFrame[0];
/*     */   private ClipInterpolator clipInterpolator;
/*  97 */   private boolean aborted = false;
/*     */ 
/* 100 */   private int lastKF = -1;
/*     */ 
/* 103 */   private double curTime = 0.0D;
/*     */ 
/*     */   public TimelineClipCore(Timeline paramTimeline)
/*     */   {
/*  68 */     this.timeline = paramTimeline;
/*  69 */     this.clipInterpolator = ClipInterpolator.create(this.keyFrames);
/*     */   }
/*     */ 
/*     */   public void setKeyFrames(KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/*  76 */     this.keyFrames = paramArrayOfKeyFrame;
/*  77 */     this.clipInterpolator = this.clipInterpolator.setKeyFrames(paramArrayOfKeyFrame);
/*     */   }
/*     */ 
/*     */   public void notifyCurrentRateChanged()
/*     */   {
/*  83 */     if (this.timeline.getStatus() != Animation.Status.RUNNING)
/*  84 */       clearLastKeyFrame();
/*     */   }
/*     */ 
/*     */   public void abort()
/*     */   {
/*  94 */     this.aborted = true;
/*     */   }
/*     */ 
/*     */   private void clearLastKeyFrame()
/*     */   {
/* 106 */     this.lastKF = -1;
/*     */   }
/*     */ 
/*     */   public void jumpTo(long paramLong)
/*     */   {
/* 111 */     double d = paramLong / 6.0D;
/* 112 */     this.lastKF = -1;
/* 113 */     this.curTime = d;
/* 114 */     if (this.timeline.getStatus() != Animation.Status.STOPPED)
/* 115 */       this.clipInterpolator.interpolate(d);
/*     */   }
/*     */ 
/*     */   public void start(boolean paramBoolean)
/*     */   {
/* 120 */     clearLastKeyFrame();
/* 121 */     this.clipInterpolator.validate(paramBoolean);
/* 122 */     if (this.curTime > 0.0D)
/* 123 */       this.clipInterpolator.interpolate(this.curTime);
/*     */   }
/*     */ 
/*     */   public void playTo(long paramLong)
/*     */   {
/* 141 */     double d1 = paramLong / 6.0D;
/*     */ 
/* 143 */     this.aborted = false;
/* 144 */     int i = this.curTime <= d1 ? 1 : 0;
/*     */     int j;
/*     */     int k;
/* 146 */     if (i != 0) {
/* 147 */       j = this.keyFrames[this.lastKF].getTime().toMillis() <= this.curTime ? this.lastKF + 1 : this.lastKF == -1 ? 0 : this.lastKF;
/*     */ 
/* 150 */       k = this.keyFrames.length;
/* 151 */       for (int m = j; m < k; m++) {
/* 152 */         double d3 = this.keyFrames[m].getTime().toMillis();
/* 153 */         if (d3 > d1) {
/* 154 */           this.lastKF = (m - 1);
/*     */         }
/* 157 */         else if (d3 >= this.curTime) {
/* 158 */           visitKeyFrame(m, d3);
/* 159 */           if (this.aborted)
/*     */             break;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 165 */       j = this.keyFrames[this.lastKF].getTime().toMillis() >= this.curTime ? this.lastKF - 1 : this.lastKF == -1 ? this.keyFrames.length - 1 : this.lastKF;
/*     */ 
/* 168 */       for (k = j; k >= 0; k--) {
/* 169 */         double d2 = this.keyFrames[k].getTime().toMillis();
/* 170 */         if (d2 < d1) {
/* 171 */           this.lastKF = (k + 1);
/*     */         }
/* 174 */         else if (d2 <= this.curTime) {
/* 175 */           visitKeyFrame(k, d2);
/* 176 */           if (this.aborted) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 182 */     if ((!this.aborted) && ((this.lastKF == -1) || (Math.abs(this.keyFrames[this.lastKF].getTime().toMillis() - d1) > 1.0E-12D) || (this.keyFrames[this.lastKF].getOnFinished() == null)))
/*     */     {
/* 187 */       setTime(d1);
/* 188 */       this.clipInterpolator.interpolate(d1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setTime(double paramDouble) {
/* 193 */     this.curTime = paramDouble;
/* 194 */     this.timeline.impl_setCurrentTicks(Math.round(6.0D * paramDouble));
/*     */   }
/*     */ 
/*     */   private void visitKeyFrame(int paramInt, double paramDouble)
/*     */   {
/* 206 */     if (paramInt != this.lastKF)
/*     */     {
/* 208 */       this.lastKF = paramInt;
/*     */ 
/* 210 */       KeyFrame localKeyFrame = this.keyFrames[paramInt];
/* 211 */       EventHandler localEventHandler = localKeyFrame.getOnFinished();
/*     */ 
/* 213 */       if (localEventHandler != null)
/*     */       {
/* 215 */         setTime(paramDouble);
/* 216 */         this.clipInterpolator.interpolate(paramDouble);
/*     */         try {
/* 218 */           localEventHandler.handle(new ActionEvent(localKeyFrame, null));
/*     */         } catch (Throwable localThrowable) {
/* 220 */           localThrowable.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.TimelineClipCore
 * JD-Core Version:    0.6.2
 */