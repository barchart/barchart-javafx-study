/*    */ package com.sun.scenario.animation.shared;
/*    */ 
/*    */ import javafx.animation.Animation;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public class ClipEnvelopeFactory
/*    */ {
/*    */   public static ClipEnvelope create(Animation paramAnimation)
/*    */   {
/* 55 */     if ((paramAnimation.getCycleCount() == 1) || (paramAnimation.getCycleDuration().isIndefinite()))
/* 56 */       return new SingleLoopClipEnvelope(paramAnimation);
/* 57 */     if (paramAnimation.getCycleCount() == -1) {
/* 58 */       return new InfiniteClipEnvelope(paramAnimation);
/*    */     }
/* 60 */     return new FiniteClipEnvelope(paramAnimation);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.ClipEnvelopeFactory
 * JD-Core Version:    0.6.2
 */