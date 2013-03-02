/*    */ package com.sun.scenario.effect;
/*    */ 
/*    */ public final class Color4f
/*    */ {
/* 34 */   public static final Color4f BLACK = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 35 */   public static final Color4f WHITE = new Color4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   private final float r;
/*    */   private final float g;
/*    */   private final float b;
/*    */   private final float a;
/*    */ 
/*    */   public Color4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*    */   {
/* 43 */     this.r = paramFloat1;
/* 44 */     this.g = paramFloat2;
/* 45 */     this.b = paramFloat3;
/* 46 */     this.a = paramFloat4;
/*    */   }
/*    */ 
/*    */   public float getRed() {
/* 50 */     return this.r;
/*    */   }
/*    */ 
/*    */   public float getGreen() {
/* 54 */     return this.g;
/*    */   }
/*    */ 
/*    */   public float getBlue() {
/* 58 */     return this.b;
/*    */   }
/*    */ 
/*    */   public float getAlpha() {
/* 62 */     return this.a;
/*    */   }
/*    */ 
/*    */   public float[] getPremultipliedRGBComponents()
/*    */   {
/* 72 */     float[] arrayOfFloat = new float[4];
/* 73 */     arrayOfFloat[0] = (this.r * this.a);
/* 74 */     arrayOfFloat[1] = (this.g * this.a);
/* 75 */     arrayOfFloat[2] = (this.b * this.a);
/* 76 */     arrayOfFloat[3] = this.a;
/* 77 */     return arrayOfFloat;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Color4f
 * JD-Core Version:    0.6.2
 */