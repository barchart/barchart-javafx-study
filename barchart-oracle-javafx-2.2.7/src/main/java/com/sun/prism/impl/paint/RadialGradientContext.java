/*     */ package com.sun.prism.impl.paint;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.RadialGradient;
/*     */ 
/*     */ final class RadialGradientContext extends MultipleGradientContext
/*     */ {
/*  43 */   private boolean isSimpleFocus = false;
/*     */ 
/*  46 */   private boolean isNonCyclic = false;
/*     */   private float radius;
/*     */   private float centerX;
/*     */   private float centerY;
/*     */   private float focusX;
/*     */   private float focusY;
/*     */   private float radiusSq;
/*     */   private float constA;
/*     */   private float constB;
/*     */   private float gDeltaDelta;
/*     */   private float trivial;
/*     */   private static final float SCALEBACK = 0.99F;
/*     */   private static final int SQRT_LUT_SIZE = 2048;
/* 285 */   private static float[] sqrtLut = new float[2049];
/*     */ 
/*     */   RadialGradientContext(RadialGradient paramRadialGradient, BaseTransform paramBaseTransform, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float[] paramArrayOfFloat, Color[] paramArrayOfColor, int paramInt)
/*     */   {
/* 105 */     super(paramRadialGradient, paramBaseTransform, paramArrayOfFloat, paramArrayOfColor, paramInt);
/*     */ 
/* 108 */     this.centerX = paramFloat1;
/* 109 */     this.centerY = paramFloat2;
/* 110 */     this.focusX = paramFloat4;
/* 111 */     this.focusY = paramFloat5;
/* 112 */     this.radius = paramFloat3;
/*     */ 
/* 114 */     this.isSimpleFocus = ((this.focusX == this.centerX) && (this.focusY == this.centerY));
/* 115 */     this.isNonCyclic = (paramInt == 0);
/*     */ 
/* 118 */     this.radiusSq = (this.radius * this.radius);
/*     */ 
/* 120 */     float f1 = this.focusX - this.centerX;
/* 121 */     float f2 = this.focusY - this.centerY;
/*     */ 
/* 123 */     double d = f1 * f1 + f2 * f2;
/*     */ 
/* 126 */     if (d > this.radiusSq * 0.99F)
/*     */     {
/* 128 */       float f3 = (float)Math.sqrt(this.radiusSq * 0.99F / d);
/* 129 */       f1 *= f3;
/* 130 */       f2 *= f3;
/* 131 */       this.focusX = (this.centerX + f1);
/* 132 */       this.focusY = (this.centerY + f2);
/*     */     }
/*     */ 
/* 137 */     this.trivial = ((float)Math.sqrt(this.radiusSq - f1 * f1));
/*     */ 
/* 140 */     this.constA = (this.a02 - this.centerX);
/* 141 */     this.constB = (this.a12 - this.centerY);
/*     */ 
/* 144 */     this.gDeltaDelta = (2.0F * (this.a00 * this.a00 + this.a10 * this.a10) / this.radiusSq);
/*     */   }
/*     */ 
/*     */   protected void fillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 157 */     if ((this.isSimpleFocus) && (this.isNonCyclic) && (this.isSimpleLookup))
/* 158 */       simpleNonCyclicFillRaster(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     else
/* 160 */       cyclicCircularGradientFillRaster(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   private void simpleNonCyclicFillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 207 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.constA;
/* 208 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.constB;
/*     */ 
/* 211 */     float f3 = this.gDeltaDelta;
/*     */ 
/* 214 */     paramInt2 += paramInt5;
/*     */ 
/* 217 */     int i = this.gradient[this.fastGradientArraySize];
/*     */ 
/* 219 */     for (int j = 0; j < paramInt6; j++)
/*     */     {
/* 221 */       float f4 = (f1 * f1 + f2 * f2) / this.radiusSq;
/* 222 */       float f5 = 2.0F * (this.a00 * f1 + this.a10 * f2) / this.radiusSq + f3 / 2.0F;
/*     */ 
/* 240 */       int k = 0;
/*     */ 
/* 242 */       while ((k < paramInt5) && (f4 >= 1.0F)) {
/* 243 */         paramArrayOfInt[(paramInt1 + k)] = i;
/* 244 */         f4 += f5;
/* 245 */         f5 += f3;
/* 246 */         k++;
/*     */       }
/*     */ 
/* 249 */       while ((k < paramInt5) && (f4 < 1.0F))
/*     */       {
/*     */         int m;
/* 252 */         if (f4 <= 0.0F) {
/* 253 */           m = 0;
/*     */         } else {
/* 255 */           float f6 = f4 * 2048.0F;
/* 256 */           int n = (int)f6;
/* 257 */           float f7 = sqrtLut[n];
/* 258 */           float f8 = sqrtLut[(n + 1)] - f7;
/* 259 */           f6 = f7 + (f6 - n) * f8;
/* 260 */           m = (int)(f6 * this.fastGradientArraySize);
/*     */         }
/*     */ 
/* 264 */         paramArrayOfInt[(paramInt1 + k)] = this.gradient[m];
/*     */ 
/* 267 */         f4 += f5;
/* 268 */         f5 += f3;
/* 269 */         k++;
/*     */       }
/*     */ 
/* 272 */       while (k < paramInt5) {
/* 273 */         paramArrayOfInt[(paramInt1 + k)] = i;
/* 274 */         k++;
/*     */       }
/*     */ 
/* 277 */       paramInt1 += paramInt2;
/* 278 */       f1 += this.a01;
/* 279 */       f2 += this.a11;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cyclicCircularGradientFillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 317 */     double d1 = -this.radiusSq + this.centerX * this.centerX + this.centerY * this.centerY;
/*     */ 
/* 330 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.a02;
/* 331 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.a12;
/*     */ 
/* 334 */     float f3 = 2.0F * this.centerY;
/* 335 */     float f4 = -2.0F * this.centerX;
/*     */ 
/* 353 */     int i = paramInt1;
/*     */ 
/* 356 */     int j = paramInt5 + paramInt2;
/*     */ 
/* 359 */     for (int k = 0; k < paramInt6; k++)
/*     */     {
/* 362 */       float f11 = this.a01 * k + f1;
/* 363 */       float f12 = this.a11 * k + f2;
/*     */ 
/* 366 */       for (int m = 0; m < paramInt5; m++)
/*     */       {
/*     */         double d7;
/*     */         double d8;
/* 368 */         if (f11 == this.focusX)
/*     */         {
/* 370 */           d7 = this.focusX;
/* 371 */           d8 = this.centerY;
/* 372 */           d8 += (f12 > this.focusY ? this.trivial : -this.trivial);
/*     */         }
/*     */         else {
/* 375 */           double d5 = (f12 - this.focusY) / (f11 - this.focusX);
/* 376 */           double d6 = f12 - d5 * f11;
/*     */ 
/* 380 */           double d2 = d5 * d5 + 1.0D;
/* 381 */           double d3 = f4 + -2.0D * d5 * (this.centerY - d6);
/* 382 */           double d4 = d1 + d6 * (d6 - f3);
/*     */ 
/* 384 */           float f6 = (float)Math.sqrt(d3 * d3 - 4.0D * d2 * d4);
/* 385 */           d7 = -d3;
/*     */ 
/* 389 */           d7 += (f11 < this.focusX ? -f6 : f6);
/* 390 */           d7 /= 2.0D * d2;
/* 391 */           d8 = d5 * d7 + d6;
/*     */         }
/*     */ 
/* 399 */         float f9 = f11 - this.focusX;
/* 400 */         f9 *= f9;
/*     */ 
/* 402 */         float f10 = f12 - this.focusY;
/* 403 */         f10 *= f10;
/*     */ 
/* 405 */         float f7 = f9 + f10;
/*     */ 
/* 407 */         f9 = (float)d7 - this.focusX;
/* 408 */         f9 *= f9;
/*     */ 
/* 410 */         f10 = (float)d8 - this.focusY;
/* 411 */         f10 *= f10;
/*     */ 
/* 413 */         float f8 = f9 + f10;
/*     */ 
/* 417 */         float f5 = (float)Math.sqrt(f7 / f8);
/*     */ 
/* 420 */         paramArrayOfInt[(i + m)] = indexIntoGradientsArrays(f5);
/*     */ 
/* 423 */         f11 += this.a00;
/* 424 */         f12 += this.a10;
/*     */       }
/*     */ 
/* 427 */       i += j;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 287 */     for (int i = 0; i < sqrtLut.length; i++)
/* 288 */       sqrtLut[i] = ((float)Math.sqrt(i / 2048.0F));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.paint.RadialGradientContext
 * JD-Core Version:    0.6.2
 */