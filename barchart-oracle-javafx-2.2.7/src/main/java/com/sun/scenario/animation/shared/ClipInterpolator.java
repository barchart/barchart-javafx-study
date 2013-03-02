/*    */ package com.sun.scenario.animation.shared;
/*    */ 
/*    */ import javafx.animation.KeyFrame;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public abstract class ClipInterpolator
/*    */ {
/*    */   static ClipInterpolator create(KeyFrame[] paramArrayOfKeyFrame)
/*    */   {
/* 36 */     return getRealKeyFrameCount(paramArrayOfKeyFrame) == 2 ? new SimpleClipInterpolator(paramArrayOfKeyFrame[0], paramArrayOfKeyFrame[1]) : paramArrayOfKeyFrame.length == 1 ? new SimpleClipInterpolator(paramArrayOfKeyFrame[0]) : new GeneralClipInterpolator(paramArrayOfKeyFrame);
/*    */   }
/*    */ 
/*    */   static int getRealKeyFrameCount(KeyFrame[] paramArrayOfKeyFrame)
/*    */   {
/* 49 */     int i = paramArrayOfKeyFrame.length;
/* 50 */     return paramArrayOfKeyFrame[0].getTime().greaterThan(Duration.ZERO) ? i + 1 : i == 0 ? 0 : i;
/*    */   }
/*    */ 
/*    */   abstract ClipInterpolator setKeyFrames(KeyFrame[] paramArrayOfKeyFrame);
/*    */ 
/*    */   abstract void interpolate(double paramDouble);
/*    */ 
/*    */   abstract void validate(boolean paramBoolean);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.ClipInterpolator
 * JD-Core Version:    0.6.2
 */