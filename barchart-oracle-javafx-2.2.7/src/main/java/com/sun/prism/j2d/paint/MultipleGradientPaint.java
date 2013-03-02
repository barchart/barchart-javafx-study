/*     */ package com.sun.prism.j2d.paint;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.lang.ref.SoftReference;
/*     */ 
/*     */ public abstract class MultipleGradientPaint
/*     */   implements Paint
/*     */ {
/*     */   final int transparency;
/*     */   final float[] fractions;
/*     */   final Color[] colors;
/*     */   final AffineTransform gradientTransform;
/*     */   final CycleMethod cycleMethod;
/*     */   final ColorSpaceType colorSpace;
/*     */   ColorModel model;
/*     */   float[] normalizedIntervals;
/*     */   boolean isSimpleLookup;
/*     */   SoftReference<int[][]> gradients;
/*     */   SoftReference<int[]> gradient;
/*     */   int fastGradientArraySize;
/*     */ 
/*     */   MultipleGradientPaint(float[] paramArrayOfFloat, Color[] paramArrayOfColor, CycleMethod paramCycleMethod, ColorSpaceType paramColorSpaceType, AffineTransform paramAffineTransform)
/*     */   {
/* 141 */     if (paramArrayOfFloat == null) {
/* 142 */       throw new NullPointerException("Fractions array cannot be null");
/*     */     }
/*     */ 
/* 145 */     if (paramArrayOfColor == null) {
/* 146 */       throw new NullPointerException("Colors array cannot be null");
/*     */     }
/*     */ 
/* 149 */     if (paramCycleMethod == null) {
/* 150 */       throw new NullPointerException("Cycle method cannot be null");
/*     */     }
/*     */ 
/* 153 */     if (paramColorSpaceType == null) {
/* 154 */       throw new NullPointerException("Color space cannot be null");
/*     */     }
/*     */ 
/* 157 */     if (paramAffineTransform == null) {
/* 158 */       throw new NullPointerException("Gradient transform cannot be null");
/*     */     }
/*     */ 
/* 162 */     if (paramArrayOfFloat.length != paramArrayOfColor.length) {
/* 163 */       throw new IllegalArgumentException("Colors and fractions must have equal size");
/*     */     }
/*     */ 
/* 167 */     if (paramArrayOfColor.length < 2) {
/* 168 */       throw new IllegalArgumentException("User must specify at least 2 colors");
/*     */     }
/*     */ 
/* 174 */     float f1 = -1.0F;
/* 175 */     for (float f2 : paramArrayOfFloat) {
/* 176 */       if ((f2 < 0.0F) || (f2 > 1.0F)) {
/* 177 */         throw new IllegalArgumentException("Fraction values must be in the range 0 to 1: " + f2);
/*     */       }
/*     */ 
/* 182 */       if (f2 <= f1) {
/* 183 */         throw new IllegalArgumentException("Keyframe fractions must be increasing: " + f2);
/*     */       }
/*     */ 
/* 188 */       f1 = f2;
/*     */     }
/*     */ 
/* 195 */     int i = 0;
/* 196 */     ??? = 0;
/* 197 */     ??? = paramArrayOfFloat.length;
/* 198 */     int m = 0;
/*     */ 
/* 200 */     if (paramArrayOfFloat[0] != 0.0F)
/*     */     {
/* 202 */       i = 1;
/* 203 */       ???++;
/* 204 */       m++;
/*     */     }
/* 206 */     if (paramArrayOfFloat[(paramArrayOfFloat.length - 1)] != 1.0F)
/*     */     {
/* 208 */       ??? = 1;
/* 209 */       ???++;
/*     */     }
/*     */ 
/* 212 */     this.fractions = new float[???];
/* 213 */     System.arraycopy(paramArrayOfFloat, 0, this.fractions, m, paramArrayOfFloat.length);
/* 214 */     this.colors = new Color[???];
/* 215 */     System.arraycopy(paramArrayOfColor, 0, this.colors, m, paramArrayOfColor.length);
/*     */ 
/* 217 */     if (i != 0) {
/* 218 */       this.fractions[0] = 0.0F;
/* 219 */       this.colors[0] = paramArrayOfColor[0];
/*     */     }
/* 221 */     if (??? != 0) {
/* 222 */       this.fractions[(??? - 1)] = 1.0F;
/* 223 */       this.colors[(??? - 1)] = paramArrayOfColor[(paramArrayOfColor.length - 1)];
/*     */     }
/*     */ 
/* 227 */     this.colorSpace = paramColorSpaceType;
/* 228 */     this.cycleMethod = paramCycleMethod;
/*     */ 
/* 231 */     this.gradientTransform = new AffineTransform(paramAffineTransform);
/*     */ 
/* 234 */     int n = 1;
/* 235 */     for (int i1 = 0; i1 < paramArrayOfColor.length; i1++) {
/* 236 */       n = (n != 0) && (paramArrayOfColor[i1].getAlpha() == 255) ? 1 : 0;
/*     */     }
/* 238 */     this.transparency = (n != 0 ? 1 : 3);
/*     */   }
/*     */ 
/*     */   public final float[] getFractions()
/*     */   {
/* 251 */     float[] arrayOfFloat = new float[this.fractions.length];
/* 252 */     System.arraycopy(this.fractions, 0, arrayOfFloat, 0, this.fractions.length);
/* 253 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   public final Color[] getColors()
/*     */   {
/* 264 */     Color[] arrayOfColor = new Color[this.fractions.length];
/* 265 */     System.arraycopy(this.fractions, 0, arrayOfColor, 0, this.fractions.length);
/* 266 */     return arrayOfColor;
/*     */   }
/*     */ 
/*     */   public final CycleMethod getCycleMethod()
/*     */   {
/* 275 */     return this.cycleMethod;
/*     */   }
/*     */ 
/*     */   public final ColorSpaceType getColorSpace()
/*     */   {
/* 286 */     return this.colorSpace;
/*     */   }
/*     */ 
/*     */   public final AffineTransform getTransform()
/*     */   {
/* 295 */     return new AffineTransform(this.gradientTransform);
/*     */   }
/*     */ 
/*     */   public final int getTransparency()
/*     */   {
/* 306 */     return this.transparency;
/*     */   }
/*     */ 
/*     */   public static enum ColorSpaceType
/*     */   {
/*  72 */     SRGB, 
/*     */ 
/*  78 */     LINEAR_RGB;
/*     */   }
/*     */ 
/*     */   public static enum CycleMethod
/*     */   {
/*  50 */     NO_CYCLE, 
/*     */ 
/*  56 */     REFLECT, 
/*     */ 
/*  62 */     REPEAT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.paint.MultipleGradientPaint
 * JD-Core Version:    0.6.2
 */