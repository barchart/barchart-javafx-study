/*     */ package com.sun.prism.j2d.paint;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.NoninvertibleTransformException;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ abstract class MultipleGradientPaintContext
/*     */   implements PaintContext
/*     */ {
/*     */   protected ColorModel model;
/*  60 */   private static ColorModel xrgbmodel = new DirectColorModel(24, 16711680, 65280, 255);
/*     */   protected static ColorModel cachedModel;
/*     */   protected static WeakReference<Raster> cached;
/*     */   protected Raster saved;
/*     */   protected MultipleGradientPaint.CycleMethod cycleMethod;
/*     */   protected MultipleGradientPaint.ColorSpaceType colorSpace;
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
/* 119 */   private static final int[] SRGBtoLinearRGB = new int[256];
/* 120 */   private static final int[] LinearRGBtoSRGB = new int[256];
/*     */   protected static final int GRADIENT_SIZE = 256;
/*     */   protected static final int GRADIENT_SIZE_INDEX = 255;
/*     */   private static final int MAX_GRADIENT_ARRAY_SIZE = 5000;
/*     */ 
/*     */   protected MultipleGradientPaintContext(MultipleGradientPaint paramMultipleGradientPaint, ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType)
/*     */   {
/* 159 */     if (paramRectangle == null) {
/* 160 */       throw new NullPointerException("Device bounds cannot be null");
/*     */     }
/*     */ 
/* 163 */     if (paramRectangle2D == null) {
/* 164 */       throw new NullPointerException("User bounds cannot be null");
/*     */     }
/*     */ 
/* 167 */     if (paramAffineTransform == null) {
/* 168 */       throw new NullPointerException("Transform cannot be null");
/*     */     }
/*     */ 
/*     */     AffineTransform localAffineTransform;
/*     */     try
/*     */     {
/* 177 */       localAffineTransform = paramAffineTransform.createInverse();
/*     */     }
/*     */     catch (NoninvertibleTransformException localNoninvertibleTransformException)
/*     */     {
/* 181 */       localAffineTransform = new AffineTransform();
/*     */     }
/* 183 */     double[] arrayOfDouble = new double[6];
/* 184 */     localAffineTransform.getMatrix(arrayOfDouble);
/* 185 */     this.a00 = ((float)arrayOfDouble[0]);
/* 186 */     this.a10 = ((float)arrayOfDouble[1]);
/* 187 */     this.a01 = ((float)arrayOfDouble[2]);
/* 188 */     this.a11 = ((float)arrayOfDouble[3]);
/* 189 */     this.a02 = ((float)arrayOfDouble[4]);
/* 190 */     this.a12 = ((float)arrayOfDouble[5]);
/*     */ 
/* 193 */     this.cycleMethod = paramCycleMethod;
/* 194 */     this.colorSpace = paramColorSpaceType;
/*     */ 
/* 197 */     this.fractions = paramArrayOfFloat;
/*     */ 
/* 202 */     this.gradient = (paramMultipleGradientPaint.gradient != null ? (int[])paramMultipleGradientPaint.gradient.get() : null);
/*     */ 
/* 204 */     this.gradients = (paramMultipleGradientPaint.gradients != null ? (int[][])paramMultipleGradientPaint.gradients.get() : (int[][])null);
/*     */ 
/* 207 */     if ((this.gradient == null) && (this.gradients == null))
/*     */     {
/* 209 */       calculateLookupData(paramArrayOfColor);
/*     */ 
/* 213 */       paramMultipleGradientPaint.model = this.model;
/* 214 */       paramMultipleGradientPaint.normalizedIntervals = this.normalizedIntervals;
/* 215 */       paramMultipleGradientPaint.isSimpleLookup = this.isSimpleLookup;
/* 216 */       if (this.isSimpleLookup)
/*     */       {
/* 218 */         paramMultipleGradientPaint.fastGradientArraySize = this.fastGradientArraySize;
/* 219 */         paramMultipleGradientPaint.gradient = new SoftReference(this.gradient);
/*     */       }
/*     */       else {
/* 222 */         paramMultipleGradientPaint.gradients = new SoftReference(this.gradients);
/*     */       }
/*     */     }
/*     */     else {
/* 226 */       this.model = paramMultipleGradientPaint.model;
/* 227 */       this.normalizedIntervals = paramMultipleGradientPaint.normalizedIntervals;
/* 228 */       this.isSimpleLookup = paramMultipleGradientPaint.isSimpleLookup;
/* 229 */       this.fastGradientArraySize = paramMultipleGradientPaint.fastGradientArraySize;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void calculateLookupData(Color[] paramArrayOfColor)
/*     */   {
/*     */     Color[] arrayOfColor;
/* 240 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB)
/*     */     {
/* 242 */       arrayOfColor = new Color[paramArrayOfColor.length];
/*     */ 
/* 244 */       for (i = 0; i < paramArrayOfColor.length; i++) {
/* 245 */         j = paramArrayOfColor[i].getRGB();
/* 246 */         k = j >>> 24;
/* 247 */         int m = SRGBtoLinearRGB[(j >> 16 & 0xFF)];
/* 248 */         int n = SRGBtoLinearRGB[(j >> 8 & 0xFF)];
/* 249 */         int i1 = SRGBtoLinearRGB[(j & 0xFF)];
/* 250 */         arrayOfColor[i] = new Color(m, n, i1, k);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 255 */       arrayOfColor = paramArrayOfColor;
/*     */     }
/*     */ 
/* 259 */     this.normalizedIntervals = new float[this.fractions.length - 1];
/*     */ 
/* 262 */     for (int i = 0; i < this.normalizedIntervals.length; i++)
/*     */     {
/* 264 */       this.normalizedIntervals[i] = (this.fractions[(i + 1)] - this.fractions[i]);
/*     */     }
/*     */ 
/* 268 */     this.transparencyTest = -16777216;
/*     */ 
/* 271 */     this.gradients = new int[this.normalizedIntervals.length][];
/*     */ 
/* 274 */     float f = 1.0F;
/* 275 */     for (int j = 0; j < this.normalizedIntervals.length; j++) {
/* 276 */       f = f > this.normalizedIntervals[j] ? this.normalizedIntervals[j] : f;
/*     */     }
/*     */ 
/* 285 */     j = 0;
/* 286 */     for (int k = 0; k < this.normalizedIntervals.length; k++) {
/* 287 */       j = (int)(j + this.normalizedIntervals[k] / f * 256.0F);
/*     */     }
/*     */ 
/* 290 */     if (j > 5000)
/*     */     {
/* 292 */       calculateMultipleArrayGradient(arrayOfColor);
/*     */     }
/*     */     else {
/* 295 */       calculateSingleArrayGradient(arrayOfColor, f);
/*     */     }
/*     */ 
/* 299 */     if (this.transparencyTest >>> 24 == 255)
/* 300 */       this.model = xrgbmodel;
/*     */     else
/* 302 */       this.model = ColorModel.getRGBdefault();
/*     */   }
/*     */ 
/*     */   private void calculateSingleArrayGradient(Color[] paramArrayOfColor, float paramFloat)
/*     */   {
/* 331 */     this.isSimpleLookup = true;
/*     */ 
/* 337 */     int k = 1;
/*     */ 
/* 340 */     for (int m = 0; m < this.gradients.length; m++)
/*     */     {
/* 343 */       n = (int)(this.normalizedIntervals[m] / paramFloat * 255.0F);
/* 344 */       k += n;
/* 345 */       this.gradients[m] = new int[n];
/*     */ 
/* 348 */       int i = paramArrayOfColor[m].getRGB();
/* 349 */       int j = paramArrayOfColor[(m + 1)].getRGB();
/*     */ 
/* 352 */       interpolate(i, j, this.gradients[m]);
/*     */ 
/* 356 */       this.transparencyTest &= i;
/* 357 */       this.transparencyTest &= j;
/*     */     }
/*     */ 
/* 361 */     this.gradient = new int[k];
/* 362 */     m = 0;
/* 363 */     for (int n = 0; n < this.gradients.length; n++) {
/* 364 */       System.arraycopy(this.gradients[n], 0, this.gradient, m, this.gradients[n].length);
/*     */ 
/* 366 */       m += this.gradients[n].length;
/*     */     }
/* 368 */     this.gradient[(this.gradient.length - 1)] = paramArrayOfColor[(paramArrayOfColor.length - 1)].getRGB();
/*     */ 
/* 372 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB) {
/* 373 */       for (n = 0; n < this.gradient.length; n++) {
/* 374 */         this.gradient[n] = convertEntireColorLinearRGBtoSRGB(this.gradient[n]);
/*     */       }
/*     */     }
/*     */ 
/* 378 */     this.fastGradientArraySize = (this.gradient.length - 1);
/*     */   }
/*     */ 
/*     */   private void calculateMultipleArrayGradient(Color[] paramArrayOfColor)
/*     */   {
/* 401 */     this.isSimpleLookup = false;
/*     */ 
/* 407 */     for (int k = 0; k < this.gradients.length; k++)
/*     */     {
/* 410 */       this.gradients[k] = new int[256];
/*     */ 
/* 413 */       int i = paramArrayOfColor[k].getRGB();
/* 414 */       int j = paramArrayOfColor[(k + 1)].getRGB();
/*     */ 
/* 417 */       interpolate(i, j, this.gradients[k]);
/*     */ 
/* 421 */       this.transparencyTest &= i;
/* 422 */       this.transparencyTest &= j;
/*     */     }
/*     */ 
/* 427 */     if (this.colorSpace == MultipleGradientPaint.ColorSpaceType.LINEAR_RGB)
/* 428 */       for (k = 0; k < this.gradients.length; k++)
/* 429 */         for (int m = 0; m < this.gradients[k].length; m++)
/* 430 */           this.gradients[k][m] = convertEntireColorLinearRGBtoSRGB(this.gradients[k][m]);
/*     */   }
/*     */ 
/*     */   private void interpolate(int paramInt1, int paramInt2, int[] paramArrayOfInt)
/*     */   {
/* 450 */     float f = 1.0F / paramArrayOfInt.length;
/*     */ 
/* 453 */     int i = paramInt1 >> 24 & 0xFF;
/* 454 */     int j = paramInt1 >> 16 & 0xFF;
/* 455 */     int k = paramInt1 >> 8 & 0xFF;
/* 456 */     int m = paramInt1 & 0xFF;
/*     */ 
/* 459 */     int n = (paramInt2 >> 24 & 0xFF) - i;
/* 460 */     int i1 = (paramInt2 >> 16 & 0xFF) - j;
/* 461 */     int i2 = (paramInt2 >> 8 & 0xFF) - k;
/* 462 */     int i3 = (paramInt2 & 0xFF) - m;
/*     */ 
/* 467 */     for (int i4 = 0; i4 < paramArrayOfInt.length; i4++)
/* 468 */       paramArrayOfInt[i4] = ((int)(i + i4 * n * f + 0.5D) << 24 | (int)(j + i4 * i1 * f + 0.5D) << 16 | (int)(k + i4 * i2 * f + 0.5D) << 8 | (int)(m + i4 * i3 * f + 0.5D));
/*     */   }
/*     */ 
/*     */   private int convertEntireColorLinearRGBtoSRGB(int paramInt)
/*     */   {
/* 486 */     int i = paramInt >> 24 & 0xFF;
/* 487 */     int j = paramInt >> 16 & 0xFF;
/* 488 */     int k = paramInt >> 8 & 0xFF;
/* 489 */     int m = paramInt & 0xFF;
/*     */ 
/* 492 */     j = LinearRGBtoSRGB[j];
/* 493 */     k = LinearRGBtoSRGB[k];
/* 494 */     m = LinearRGBtoSRGB[m];
/*     */ 
/* 497 */     return i << 24 | j << 16 | k << 8 | m;
/*     */   }
/*     */ 
/*     */   protected final int indexIntoGradientsArrays(float paramFloat)
/*     */   {
/* 515 */     if (this.cycleMethod == MultipleGradientPaint.CycleMethod.NO_CYCLE) {
/* 516 */       if (paramFloat > 1.0F)
/*     */       {
/* 518 */         paramFloat = 1.0F;
/* 519 */       } else if (paramFloat < 0.0F)
/*     */       {
/* 521 */         paramFloat = 0.0F;
/*     */       }
/* 523 */     } else if (this.cycleMethod == MultipleGradientPaint.CycleMethod.REPEAT)
/*     */     {
/* 526 */       paramFloat -= (int)paramFloat;
/*     */ 
/* 529 */       if (paramFloat < 0.0F)
/*     */       {
/* 531 */         paramFloat += 1.0F;
/*     */       }
/*     */     } else {
/* 534 */       if (paramFloat < 0.0F)
/*     */       {
/* 536 */         paramFloat = -paramFloat;
/*     */       }
/*     */ 
/* 540 */       i = (int)paramFloat;
/*     */ 
/* 543 */       paramFloat -= i;
/*     */ 
/* 545 */       if ((i & 0x1) == 1)
/*     */       {
/* 547 */         paramFloat = 1.0F - paramFloat;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 553 */     if (this.isSimpleLookup)
/*     */     {
/* 555 */       return this.gradient[((int)(paramFloat * this.fastGradientArraySize))];
/*     */     }
/*     */ 
/* 560 */     for (int i = 0; i < this.gradients.length; i++) {
/* 561 */       if (paramFloat < this.fractions[(i + 1)])
/*     */       {
/* 563 */         float f = paramFloat - this.fractions[i];
/*     */ 
/* 566 */         int j = (int)(f / this.normalizedIntervals[i] * 255.0F);
/*     */ 
/* 569 */         return this.gradients[i][j];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 574 */     return this.gradients[(this.gradients.length - 1)]['ÿ'];
/*     */   }
/*     */ 
/*     */   private static int convertSRGBtoLinearRGB(int paramInt)
/*     */   {
/* 584 */     float f1 = paramInt / 255.0F;
/*     */     float f2;
/* 585 */     if (f1 <= 0.04045F)
/* 586 */       f2 = f1 / 12.92F;
/*     */     else {
/* 588 */       f2 = (float)Math.pow((f1 + 0.055D) / 1.055D, 2.4D);
/*     */     }
/*     */ 
/* 591 */     return Math.round(f2 * 255.0F);
/*     */   }
/*     */ 
/*     */   private static int convertLinearRGBtoSRGB(int paramInt)
/*     */   {
/* 601 */     float f1 = paramInt / 255.0F;
/*     */     float f2;
/* 602 */     if (f1 <= 0.0031308D)
/* 603 */       f2 = f1 * 12.92F;
/*     */     else {
/* 605 */       f2 = 1.055F * (float)Math.pow(f1, 0.4166666666666667D) - 0.055F;
/*     */     }
/*     */ 
/* 609 */     return Math.round(f2 * 255.0F);
/*     */   }
/*     */ 
/*     */   public final Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 618 */     Raster localRaster = this.saved;
/* 619 */     if ((localRaster == null) || (localRaster.getWidth() < paramInt3) || (localRaster.getHeight() < paramInt4))
/*     */     {
/* 622 */       localRaster = getCachedRaster(this.model, paramInt3, paramInt4);
/* 623 */       this.saved = localRaster;
/*     */     }
/*     */ 
/* 633 */     DataBufferInt localDataBufferInt = (DataBufferInt)localRaster.getDataBuffer();
/* 634 */     int[] arrayOfInt = localDataBufferInt.getData(0);
/* 635 */     int i = localDataBufferInt.getOffset();
/* 636 */     int j = ((SinglePixelPackedSampleModel)localRaster.getSampleModel()).getScanlineStride();
/*     */ 
/* 638 */     int k = j - paramInt3;
/*     */ 
/* 640 */     fillRaster(arrayOfInt, i, k, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */ 
/* 642 */     return localRaster;
/*     */   }
/*     */ 
/*     */   protected abstract void fillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   private static synchronized Raster getCachedRaster(ColorModel paramColorModel, int paramInt1, int paramInt2)
/*     */   {
/* 657 */     if ((paramColorModel == cachedModel) && 
/* 658 */       (cached != null)) {
/* 659 */       Raster localRaster = (Raster)cached.get();
/* 660 */       if ((localRaster != null) && (localRaster.getWidth() >= paramInt1) && (localRaster.getHeight() >= paramInt2))
/*     */       {
/* 664 */         cached = null;
/* 665 */         return localRaster;
/*     */       }
/*     */     }
/*     */ 
/* 669 */     return paramColorModel.createCompatibleWritableRaster(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private static synchronized void putCachedRaster(ColorModel paramColorModel, Raster paramRaster)
/*     */   {
/* 680 */     if (cached != null) {
/* 681 */       Raster localRaster = (Raster)cached.get();
/* 682 */       if (localRaster != null) {
/* 683 */         int i = localRaster.getWidth();
/* 684 */         int j = localRaster.getHeight();
/* 685 */         int k = paramRaster.getWidth();
/* 686 */         int m = paramRaster.getHeight();
/* 687 */         if ((i >= k) && (j >= m)) {
/* 688 */           return;
/*     */         }
/* 690 */         if (i * j >= k * m) {
/* 691 */           return;
/*     */         }
/*     */       }
/*     */     }
/* 695 */     cachedModel = paramColorModel;
/* 696 */     cached = new WeakReference(paramRaster);
/*     */   }
/*     */ 
/*     */   public final void dispose()
/*     */   {
/* 703 */     if (this.saved != null) {
/* 704 */       putCachedRaster(this.model, this.saved);
/* 705 */       this.saved = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final ColorModel getColorModel()
/*     */   {
/* 713 */     return this.model;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 124 */     for (int i = 0; i < 256; i++) {
/* 125 */       SRGBtoLinearRGB[i] = convertSRGBtoLinearRGB(i);
/* 126 */       LinearRGBtoSRGB[i] = convertLinearRGBtoSRGB(i);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.paint.MultipleGradientPaintContext
 * JD-Core Version:    0.6.2
 */