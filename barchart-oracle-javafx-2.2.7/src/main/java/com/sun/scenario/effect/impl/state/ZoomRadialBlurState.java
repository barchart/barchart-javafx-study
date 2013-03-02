/*    */ package com.sun.scenario.effect.impl.state;
/*    */ 
/*    */ import com.sun.scenario.effect.ZoomRadialBlur;
/*    */ 
/*    */ public class ZoomRadialBlurState
/*    */ {
/* 30 */   private float dx = -1.0F;
/* 31 */   private float dy = -1.0F;
/*    */   private final ZoomRadialBlur effect;
/*    */ 
/*    */   public ZoomRadialBlurState(ZoomRadialBlur paramZoomRadialBlur)
/*    */   {
/* 35 */     this.effect = paramZoomRadialBlur;
/*    */   }
/*    */ 
/*    */   public int getRadius() {
/* 39 */     return this.effect.getRadius();
/*    */   }
/*    */ 
/*    */   public void updateDeltas(float paramFloat1, float paramFloat2)
/*    */   {
/* 46 */     this.dx = paramFloat1;
/* 47 */     this.dy = paramFloat2;
/*    */   }
/*    */ 
/*    */   public void invalidateDeltas()
/*    */   {
/* 54 */     this.dx = -1.0F;
/* 55 */     this.dy = -1.0F;
/*    */   }
/*    */ 
/*    */   public float getDx()
/*    */   {
/* 63 */     return this.dx;
/*    */   }
/*    */ 
/*    */   public float getDy()
/*    */   {
/* 71 */     return this.dy;
/*    */   }
/*    */ 
/*    */   public int getNumSteps() {
/* 75 */     int i = getRadius();
/* 76 */     return i * 2 + 1;
/*    */   }
/*    */ 
/*    */   public float getAlpha() {
/* 80 */     float f = getRadius();
/* 81 */     return 1.0F / (2.0F * f + 1.0F);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.ZoomRadialBlurState
 * JD-Core Version:    0.6.2
 */