/*     */ package com.sun.prism.impl.paint;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ 
/*     */ abstract class MultipleGradientContext
/*     */ {
/*     */   protected int cycleMethod;
/*     */   protected float a00;
/*     */   protected float a01;
/*     */   protected float a10;
/*     */   protected float a11;
/*     */   protected float a02;
/*     */   protected float a12;
/*     */   protected boolean isSimpleLookup;
/*     */   protected int fastGradientArraySize;
/*     */   protected int[] gradient;
/*     */   private int[][] gradients;
/*     */   private float[] normalizedIntervals;
/*     */   private float[] fractions;
/*     */   private int transparencyTest;
/*     */   protected static final int GRADIENT_SIZE = 256;
/*     */   protected static final int GRADIENT_SIZE_INDEX = 255;
/*     */   private static final int MAX_GRADIENT_ARRAY_SIZE = 5000;
/*     */ 
/*     */   protected MultipleGradientContext(Gradient paramGradient, BaseTransform paramBaseTransform, float[] paramArrayOfFloat, Color[] paramArrayOfColor, int paramInt)
/*     */   {
/* 108 */     if (paramBaseTransform == null) {
/* 109 */       throw new NullPointerException("Transform cannot be null");
/*     */     }
/*     */ 
/*     */     BaseTransform localBaseTransform;
/*     */     try
/*     */     {
/* 118 */       localBaseTransform = paramBaseTransform.createInverse();
/*     */     }
/*     */     catch (NoninvertibleTransformException localNoninvertibleTransformException)
/*     */     {
/* 122 */       localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     }
/* 124 */     this.a00 = ((float)localBaseTransform.getMxx());
/* 125 */     this.a10 = ((float)localBaseTransform.getMyx());
/* 126 */     this.a01 = ((float)localBaseTransform.getMxy());
/* 127 */     this.a11 = ((float)localBaseTransform.getMyy());
/* 128 */     this.a02 = ((float)localBaseTransform.getMxt());
/* 129 */     this.a12 = ((float)localBaseTransform.getMyt());
/*     */ 
/* 132 */     this.cycleMethod = paramInt;
/*     */ 
/* 135 */     this.fractions = paramArrayOfFloat;
/*     */ 
/* 137 */     calculateLookupData(paramArrayOfColor);
/*     */   }
/*     */ 
/*     */   private void calculateLookupData(Color[] paramArrayOfColor)
/*     */   {
/* 181 */     Color[] arrayOfColor = paramArrayOfColor;
/*     */ 
/* 184 */     this.normalizedIntervals = new float[this.fractions.length - 1];
/*     */ 
/* 187 */     for (int i = 0; i < this.normalizedIntervals.length; i++)
/*     */     {
/* 189 */       this.normalizedIntervals[i] = (this.fractions[(i + 1)] - this.fractions[i]);
/*     */     }
/*     */ 
/* 193 */     this.transparencyTest = -16777216;
/*     */ 
/* 196 */     this.gradients = new int[this.normalizedIntervals.length][];
/*     */ 
/* 199 */     float f = 1.0F;
/* 200 */     for (int j = 0; j < this.normalizedIntervals.length; j++) {
/* 201 */       f = f > this.normalizedIntervals[j] ? this.normalizedIntervals[j] : f;
/*     */     }
/*     */ 
/* 210 */     j = 0;
/* 211 */     for (int k = 0; k < this.normalizedIntervals.length; k++) {
/* 212 */       j = (int)(j + this.normalizedIntervals[k] / f * 256.0F);
/*     */     }
/*     */ 
/* 215 */     if (j > 5000)
/*     */     {
/* 217 */       calculateMultipleArrayGradient(arrayOfColor);
/*     */     }
/*     */     else
/* 220 */       calculateSingleArrayGradient(arrayOfColor, f);
/*     */   }
/*     */ 
/*     */   private void calculateSingleArrayGradient(Color[] paramArrayOfColor, float paramFloat)
/*     */   {
/* 249 */     this.isSimpleLookup = true;
/*     */ 
/* 255 */     int k = 1;
/*     */ 
/* 258 */     for (int m = 0; m < this.gradients.length; m++)
/*     */     {
/* 261 */       n = (int)(this.normalizedIntervals[m] / paramFloat * 255.0F);
/* 262 */       k += n;
/* 263 */       this.gradients[m] = new int[n];
/*     */ 
/* 266 */       int i = paramArrayOfColor[m].getIntArgbPre();
/* 267 */       int j = paramArrayOfColor[(m + 1)].getIntArgbPre();
/*     */ 
/* 270 */       interpolate(i, j, this.gradients[m]);
/*     */ 
/* 274 */       this.transparencyTest &= i;
/* 275 */       this.transparencyTest &= j;
/*     */     }
/*     */ 
/* 279 */     this.gradient = new int[k];
/* 280 */     m = 0;
/* 281 */     for (int n = 0; n < this.gradients.length; n++) {
/* 282 */       System.arraycopy(this.gradients[n], 0, this.gradient, m, this.gradients[n].length);
/*     */ 
/* 284 */       m += this.gradients[n].length;
/*     */     }
/* 286 */     this.gradient[(this.gradient.length - 1)] = paramArrayOfColor[(paramArrayOfColor.length - 1)].getIntArgbPre();
/*     */ 
/* 288 */     this.fastGradientArraySize = (this.gradient.length - 1);
/*     */   }
/*     */ 
/*     */   private void calculateMultipleArrayGradient(Color[] paramArrayOfColor)
/*     */   {
/* 311 */     this.isSimpleLookup = false;
/*     */ 
/* 317 */     for (int k = 0; k < this.gradients.length; k++)
/*     */     {
/* 320 */       this.gradients[k] = new int[256];
/*     */ 
/* 323 */       int i = paramArrayOfColor[k].getIntArgbPre();
/* 324 */       int j = paramArrayOfColor[(k + 1)].getIntArgbPre();
/*     */ 
/* 327 */       interpolate(i, j, this.gradients[k]);
/*     */ 
/* 331 */       this.transparencyTest &= i;
/* 332 */       this.transparencyTest &= j;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void interpolate(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*     */   {
/* 349 */     float f = 1.0F / paramArrayOfInt.length;
/*     */ 
/* 352 */     int i = paramInt1 >> 24 & 0xFF;
/* 353 */     int j = paramInt1 >> 16 & 0xFF;
/* 354 */     int k = paramInt1 >> 8 & 0xFF;
/* 355 */     int m = paramInt1 & 0xFF;
/*     */ 
/* 358 */     int n = (paramInt2 >> 24 & 0xFF) - i;
/* 359 */     int i1 = (paramInt2 >> 16 & 0xFF) - j;
/* 360 */     int i2 = (paramInt2 >> 8 & 0xFF) - k;
/* 361 */     int i3 = (paramInt2 & 0xFF) - m;
/*     */ 
/* 366 */     for (int i4 = 0; i4 < paramArrayOfInt.length; i4++)
/* 367 */       paramArrayOfInt[i4] = ((int)(i + i4 * n * f + 0.5D) << 24 | (int)(j + i4 * i1 * f + 0.5D) << 16 | (int)(k + i4 * i2 * f + 0.5D) << 8 | (int)(m + i4 * i3 * f + 0.5D));
/*     */   }
/*     */ 
/*     */   protected final int indexIntoGradientsArrays(float paramFloat)
/*     */   {
/* 387 */     if (this.cycleMethod == 0) {
/* 388 */       if (paramFloat > 1.0F)
/*     */       {
/* 390 */         paramFloat = 1.0F;
/* 391 */       } else if (paramFloat < 0.0F)
/*     */       {
/* 393 */         paramFloat = 0.0F;
/*     */       }
/* 395 */     } else if (this.cycleMethod == 2)
/*     */     {
/* 398 */       paramFloat -= (int)paramFloat;
/*     */ 
/* 401 */       if (paramFloat < 0.0F)
/*     */       {
/* 403 */         paramFloat += 1.0F;
/*     */       }
/*     */     } else {
/* 406 */       if (paramFloat < 0.0F)
/*     */       {
/* 408 */         paramFloat = -paramFloat;
/*     */       }
/*     */ 
/* 412 */       i = (int)paramFloat;
/*     */ 
/* 415 */       paramFloat -= i;
/*     */ 
/* 417 */       if ((i & 0x1) == 1)
/*     */       {
/* 419 */         paramFloat = 1.0F - paramFloat;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 425 */     if (this.isSimpleLookup)
/*     */     {
/* 427 */       return this.gradient[((int)(paramFloat * this.fastGradientArraySize))];
/*     */     }
/*     */ 
/* 431 */     if (paramFloat < this.fractions[0]) {
/* 432 */       return this.gradients[0][0];
/*     */     }
/*     */ 
/* 436 */     for (int i = 0; i < this.gradients.length; i++) {
/* 437 */       if (paramFloat < this.fractions[(i + 1)])
/*     */       {
/* 439 */         float f = paramFloat - this.fractions[i];
/*     */ 
/* 442 */         int j = (int)(f / this.normalizedIntervals[i] * 255.0F);
/*     */ 
/* 445 */         return this.gradients[i][j];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 450 */     return this.gradients[(this.gradients.length - 1)]['ÿ'];
/*     */   }
/*     */ 
/*     */   protected abstract void fillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.paint.MultipleGradientContext
 * JD-Core Version:    0.6.2
 */