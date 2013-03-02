/*     */ package com.sun.prism.j2d.paint;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ 
/*     */ final class RadialGradientPaintContext extends MultipleGradientPaintContext
/*     */ {
/*  47 */   private boolean isSimpleFocus = false;
/*     */ 
/*  50 */   private boolean isNonCyclic = false;
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
/* 305 */   private static float[] sqrtLut = new float[2049];
/*     */ 
/*     */   RadialGradientPaintContext(RadialGradientPaint paramRadialGradientPaint, ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType)
/*     */   {
/* 124 */     super(paramRadialGradientPaint, paramColorModel, paramRectangle, paramRectangle2D, paramAffineTransform, paramRenderingHints, paramArrayOfFloat, paramArrayOfColor, paramCycleMethod, paramColorSpaceType);
/*     */ 
/* 128 */     this.centerX = paramFloat1;
/* 129 */     this.centerY = paramFloat2;
/* 130 */     this.focusX = paramFloat4;
/* 131 */     this.focusY = paramFloat5;
/* 132 */     this.radius = paramFloat3;
/*     */ 
/* 134 */     this.isSimpleFocus = ((this.focusX == this.centerX) && (this.focusY == this.centerY));
/* 135 */     this.isNonCyclic = (paramCycleMethod == MultipleGradientPaint.CycleMethod.NO_CYCLE);
/*     */ 
/* 138 */     this.radiusSq = (this.radius * this.radius);
/*     */ 
/* 140 */     float f1 = this.focusX - this.centerX;
/* 141 */     float f2 = this.focusY - this.centerY;
/*     */ 
/* 143 */     double d = f1 * f1 + f2 * f2;
/*     */ 
/* 146 */     if (d > this.radiusSq * 0.99F)
/*     */     {
/* 148 */       float f3 = (float)Math.sqrt(this.radiusSq * 0.99F / d);
/* 149 */       f1 *= f3;
/* 150 */       f2 *= f3;
/* 151 */       this.focusX = (this.centerX + f1);
/* 152 */       this.focusY = (this.centerY + f2);
/*     */     }
/*     */ 
/* 157 */     this.trivial = ((float)Math.sqrt(this.radiusSq - f1 * f1));
/*     */ 
/* 160 */     this.constA = (this.a02 - this.centerX);
/* 161 */     this.constB = (this.a12 - this.centerY);
/*     */ 
/* 164 */     this.gDeltaDelta = (2.0F * (this.a00 * this.a00 + this.a10 * this.a10) / this.radiusSq);
/*     */   }
/*     */ 
/*     */   protected void fillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 177 */     if ((this.isSimpleFocus) && (this.isNonCyclic) && (this.isSimpleLookup))
/* 178 */       simpleNonCyclicFillRaster(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     else
/* 180 */       cyclicCircularGradientFillRaster(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */   }
/*     */ 
/*     */   private void simpleNonCyclicFillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 227 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.constA;
/* 228 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.constB;
/*     */ 
/* 231 */     float f3 = this.gDeltaDelta;
/*     */ 
/* 234 */     paramInt2 += paramInt5;
/*     */ 
/* 237 */     int i = this.gradient[this.fastGradientArraySize];
/*     */ 
/* 239 */     for (int j = 0; j < paramInt6; j++)
/*     */     {
/* 241 */       float f4 = (f1 * f1 + f2 * f2) / this.radiusSq;
/* 242 */       float f5 = 2.0F * (this.a00 * f1 + this.a10 * f2) / this.radiusSq + f3 / 2.0F;
/*     */ 
/* 260 */       int k = 0;
/*     */ 
/* 262 */       while ((k < paramInt5) && (f4 >= 1.0F)) {
/* 263 */         paramArrayOfInt[(paramInt1 + k)] = i;
/* 264 */         f4 += f5;
/* 265 */         f5 += f3;
/* 266 */         k++;
/*     */       }
/*     */ 
/* 269 */       while ((k < paramInt5) && (f4 < 1.0F))
/*     */       {
/*     */         int m;
/* 272 */         if (f4 <= 0.0F) {
/* 273 */           m = 0;
/*     */         } else {
/* 275 */           float f6 = f4 * 2048.0F;
/* 276 */           int n = (int)f6;
/* 277 */           float f7 = sqrtLut[n];
/* 278 */           float f8 = sqrtLut[(n + 1)] - f7;
/* 279 */           f6 = f7 + (f6 - n) * f8;
/* 280 */           m = (int)(f6 * this.fastGradientArraySize);
/*     */         }
/*     */ 
/* 284 */         paramArrayOfInt[(paramInt1 + k)] = this.gradient[m];
/*     */ 
/* 287 */         f4 += f5;
/* 288 */         f5 += f3;
/* 289 */         k++;
/*     */       }
/*     */ 
/* 292 */       while (k < paramInt5) {
/* 293 */         paramArrayOfInt[(paramInt1 + k)] = i;
/* 294 */         k++;
/*     */       }
/*     */ 
/* 297 */       paramInt1 += paramInt2;
/* 298 */       f1 += this.a01;
/* 299 */       f2 += this.a11;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void cyclicCircularGradientFillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 337 */     double d1 = -this.radiusSq + this.centerX * this.centerX + this.centerY * this.centerY;
/*     */ 
/* 350 */     float f1 = this.a00 * paramInt3 + this.a01 * paramInt4 + this.a02;
/* 351 */     float f2 = this.a10 * paramInt3 + this.a11 * paramInt4 + this.a12;
/*     */ 
/* 354 */     float f3 = 2.0F * this.centerY;
/* 355 */     float f4 = -2.0F * this.centerX;
/*     */ 
/* 373 */     int i = paramInt1;
/*     */ 
/* 376 */     int j = paramInt5 + paramInt2;
/*     */ 
/* 379 */     if (this.trivial == 0.0F)
/*     */     {
/* 382 */       k = indexIntoGradientsArrays(0.0F);
/* 383 */       for (int m = 0; m < paramInt6; m++) {
/* 384 */         for (int n = 0; n < paramInt5; n++) {
/* 385 */           paramArrayOfInt[(i + n)] = k;
/*     */         }
/* 387 */         i += j;
/*     */       }
/* 389 */       return;
/*     */     }
/* 391 */     for (int k = 0; k < paramInt6; k++)
/*     */     {
/* 394 */       float f11 = this.a01 * k + f1;
/* 395 */       float f12 = this.a11 * k + f2;
/*     */ 
/* 398 */       for (int i1 = 0; i1 < paramInt5; i1++)
/*     */       {
/*     */         double d7;
/*     */         double d8;
/* 400 */         if (f11 == this.focusX)
/*     */         {
/* 402 */           d7 = this.focusX;
/* 403 */           d8 = this.centerY;
/* 404 */           d8 += (f12 > this.focusY ? this.trivial : -this.trivial);
/*     */         }
/*     */         else {
/* 407 */           double d5 = (f12 - this.focusY) / (f11 - this.focusX);
/* 408 */           double d6 = f12 - d5 * f11;
/*     */ 
/* 412 */           double d2 = d5 * d5 + 1.0D;
/* 413 */           double d3 = f4 + -2.0D * d5 * (this.centerY - d6);
/* 414 */           double d4 = d1 + d6 * (d6 - f3);
/*     */ 
/* 416 */           float f6 = (float)Math.sqrt(d3 * d3 - 4.0D * d2 * d4);
/* 417 */           d7 = -d3;
/*     */ 
/* 421 */           d7 += (f11 < this.focusX ? -f6 : f6);
/* 422 */           d7 /= 2.0D * d2;
/* 423 */           d8 = d5 * d7 + d6;
/*     */         }
/*     */ 
/* 431 */         float f9 = f11 - this.focusX;
/* 432 */         f9 *= f9;
/*     */ 
/* 434 */         float f10 = f12 - this.focusY;
/* 435 */         f10 *= f10;
/*     */ 
/* 437 */         float f7 = f9 + f10;
/*     */ 
/* 439 */         f9 = (float)d7 - this.focusX;
/* 440 */         f9 *= f9;
/*     */ 
/* 442 */         f10 = (float)d8 - this.focusY;
/* 443 */         f10 *= f10;
/*     */ 
/* 445 */         float f8 = f9 + f10;
/* 446 */         if (f8 == 0.0F) {
/* 447 */           f8 = d8 >= this.focusY ? this.trivial : -this.trivial;
/*     */         }
/*     */ 
/* 453 */         float f5 = (float)Math.sqrt(f7 / f8);
/*     */ 
/* 456 */         paramArrayOfInt[(i + i1)] = indexIntoGradientsArrays(f5);
/*     */ 
/* 459 */         f11 += this.a00;
/* 460 */         f12 += this.a10;
/*     */       }
/*     */ 
/* 463 */       i += j;
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 307 */     for (int i = 0; i < sqrtLut.length; i++)
/* 308 */       sqrtLut[i] = ((float)Math.sqrt(i / 2048.0F));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.paint.RadialGradientPaintContext
 * JD-Core Version:    0.6.2
 */