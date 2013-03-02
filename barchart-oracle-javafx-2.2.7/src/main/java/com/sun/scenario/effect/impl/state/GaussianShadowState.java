/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ 
/*     */ public class GaussianShadowState extends GaussianBlurState
/*     */ {
/*     */   private Color4f shadowColor;
/*     */   private float spread;
/*     */ 
/*     */   void checkRadius(float paramFloat)
/*     */   {
/*  41 */     if ((paramFloat < 0.0F) || (paramFloat > 127.0F))
/*  42 */       throw new IllegalArgumentException("Radius must be in the range [1,127]");
/*     */   }
/*     */ 
/*     */   private int getPow2Scale(int paramInt)
/*     */   {
/*  54 */     return getRadius(paramInt) > 63.0F ? -1 : 0;
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleX()
/*     */   {
/*  59 */     return getPow2Scale(0);
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleY()
/*     */   {
/*  64 */     return getPow2Scale(1);
/*     */   }
/*     */ 
/*     */   public float getScaledRadius(int paramInt)
/*     */   {
/*  69 */     float f = getRadius(paramInt);
/*  70 */     int i = getPow2Scale(paramInt);
/*  71 */     while (i < 0) {
/*  72 */       f /= 2.0F;
/*  73 */       i++;
/*     */     }
/*  75 */     return f;
/*     */   }
/*     */ 
/*     */   public int getScaledPad(int paramInt)
/*     */   {
/*  80 */     float f = getScaledRadius(paramInt);
/*     */ 
/*  84 */     return f > 63.0F ? 63 : (int)Math.ceil(f);
/*     */   }
/*     */ 
/*     */   public int getScaledKernelSize(int paramInt)
/*     */   {
/*  89 */     return getScaledPad(paramInt) * 2 + 1;
/*     */   }
/*     */ 
/*     */   public Color4f getShadowColor() {
/*  93 */     return this.shadowColor;
/*     */   }
/*     */ 
/*     */   public void setShadowColor(Color4f paramColor4f) {
/*  97 */     if (paramColor4f == null) {
/*  98 */       throw new IllegalArgumentException("Color must be non-null");
/*     */     }
/* 100 */     this.shadowColor = paramColor4f;
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/* 105 */     return this.spread;
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat) {
/* 109 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 110 */       throw new IllegalArgumentException("Spread must be in the range [0,1]");
/*     */     }
/* 112 */     this.spread = paramFloat;
/*     */   }
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/* 126 */     return (paramInt == 0) && (super.isNop(paramInt));
/*     */   }
/*     */ 
/*     */   public boolean isShadow()
/*     */   {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */   public float[] getShadowColorComponents(int paramInt)
/*     */   {
/* 136 */     return paramInt == 0 ? BLACK_COMPONENTS : this.shadowColor.getPremultipliedRGBComponents();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.GaussianShadowState
 * JD-Core Version:    0.6.2
 */