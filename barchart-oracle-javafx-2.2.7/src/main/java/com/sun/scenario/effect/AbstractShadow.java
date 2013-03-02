/*    */ package com.sun.scenario.effect;
/*    */ 
/*    */ public abstract class AbstractShadow extends CoreEffect
/*    */ {
/*    */   public AbstractShadow(Effect paramEffect)
/*    */   {
/* 42 */     super(paramEffect); } 
/*    */   public abstract ShadowMode getMode();
/*    */ 
/*    */   public abstract AbstractShadow implFor(ShadowMode paramShadowMode);
/*    */ 
/*    */   public abstract float getGaussianRadius();
/*    */ 
/*    */   public abstract void setGaussianRadius(float paramFloat);
/*    */ 
/*    */   public abstract float getGaussianWidth();
/*    */ 
/*    */   public abstract void setGaussianWidth(float paramFloat);
/*    */ 
/*    */   public abstract float getGaussianHeight();
/*    */ 
/*    */   public abstract void setGaussianHeight(float paramFloat);
/*    */ 
/*    */   public abstract float getSpread();
/*    */ 
/*    */   public abstract void setSpread(float paramFloat);
/*    */ 
/*    */   public abstract Color4f getColor();
/*    */ 
/*    */   public abstract void setColor(Color4f paramColor4f);
/*    */ 
/*    */   public abstract Effect getInput();
/*    */ 
/*    */   public abstract void setInput(Effect paramEffect);
/*    */ 
/* 46 */   public static enum ShadowMode { ONE_PASS_BOX, 
/* 47 */     TWO_PASS_BOX, 
/* 48 */     THREE_PASS_BOX, 
/* 49 */     GAUSSIAN;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.AbstractShadow
 * JD-Core Version:    0.6.2
 */