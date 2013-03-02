/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.scenario.effect.impl.BufferUtil;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public class GaussianBlurState extends HVSeparableKernel
/*     */ {
/*     */   private float hradius;
/*     */   private float vradius;
/*     */   private FloatBuffer weights;
/*     */ 
/*     */   void checkRadius(float paramFloat)
/*     */   {
/*  39 */     if ((paramFloat < 0.0F) || (paramFloat > 63.0F))
/*  40 */       throw new IllegalArgumentException("Radius must be in the range [1,63]");
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/*  45 */     return (this.hradius + this.vradius) / 2.0F;
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat) {
/*  49 */     checkRadius(paramFloat);
/*  50 */     this.hradius = paramFloat;
/*  51 */     this.vradius = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getHRadius() {
/*  55 */     return this.hradius;
/*     */   }
/*     */ 
/*     */   public void setHRadius(float paramFloat) {
/*  59 */     checkRadius(paramFloat);
/*  60 */     this.hradius = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getVRadius() {
/*  64 */     return this.vradius;
/*     */   }
/*     */ 
/*     */   public void setVRadius(float paramFloat) {
/*  68 */     checkRadius(paramFloat);
/*  69 */     this.vradius = paramFloat;
/*     */   }
/*     */ 
/*     */   float getRadius(int paramInt) {
/*  73 */     return paramInt == 0 ? this.hradius : this.vradius;
/*     */   }
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/*  78 */     return (this.hradius == 0.0F) && (this.vradius == 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/*  83 */     return getRadius(paramInt) == 0.0F;
/*     */   }
/*     */ 
/*     */   public int getPad(int paramInt) {
/*  87 */     return (int)Math.ceil(getRadius(paramInt));
/*     */   }
/*     */ 
/*     */   public int getScaledPad(int paramInt) {
/*  91 */     return getPad(paramInt);
/*     */   }
/*     */ 
/*     */   public float getScaledRadius(int paramInt) {
/*  95 */     return getRadius(paramInt);
/*     */   }
/*     */ 
/*     */   public int getKernelSize(int paramInt)
/*     */   {
/* 100 */     return getPad(paramInt) * 2 + 1;
/*     */   }
/*     */ 
/*     */   public float getSpread() {
/* 104 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public FloatBuffer getWeights(int paramInt)
/*     */   {
/* 109 */     float f1 = getScaledRadius(0);
/* 110 */     float f2 = getScaledRadius(1);
/*     */ 
/* 114 */     int i = (f2 > 1.0F) || (f2 >= f1) ? 1 : 0;
/*     */ 
/* 116 */     float f3 = paramInt == 0 ? f1 : f2;
/* 117 */     float f4 = paramInt == i ? getSpread() : 0.0F;
/* 118 */     this.weights = getGaussianWeights(this.weights, getScaledPad(paramInt), f3, f4);
/* 119 */     return this.weights;
/*     */   }
/*     */ 
/*     */   static FloatBuffer getGaussianWeights(FloatBuffer paramFloatBuffer, int paramInt, float paramFloat1, float paramFloat2)
/*     */   {
/* 127 */     int i = paramInt;
/* 128 */     int j = i * 2 + 1;
/* 129 */     if (paramFloatBuffer == null) {
/* 130 */       paramFloatBuffer = BufferUtil.newFloatBuffer(128);
/*     */     }
/* 132 */     paramFloatBuffer.clear();
/* 133 */     float f1 = paramFloat1 / 3.0F;
/* 134 */     float f2 = 2.0F * f1 * f1;
/* 135 */     if (f2 < 1.4E-45F)
/*     */     {
/* 137 */       f2 = 1.4E-45F;
/*     */     }
/* 139 */     float f3 = 0.0F;
/* 140 */     for (int k = -i; k <= i; k++) {
/* 141 */       float f4 = (float)Math.exp(-(k * k) / f2);
/* 142 */       paramFloatBuffer.put(f4);
/* 143 */       f3 += f4;
/*     */     }
/* 145 */     f3 += (paramFloatBuffer.get(0) - f3) * paramFloat2;
/* 146 */     for (k = 0; k < j; k++) {
/* 147 */       paramFloatBuffer.put(k, paramFloatBuffer.get(k) / f3);
/*     */     }
/* 149 */     k = getPeerSize(j);
/* 150 */     while (paramFloatBuffer.position() < k) {
/* 151 */       paramFloatBuffer.put(0.0F);
/*     */     }
/* 153 */     paramFloatBuffer.limit(k);
/* 154 */     paramFloatBuffer.rewind();
/* 155 */     return paramFloatBuffer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.GaussianBlurState
 * JD-Core Version:    0.6.2
 */