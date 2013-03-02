/*     */ package com.sun.scenario.animation;
/*     */ 
/*     */ import javafx.animation.Interpolator;
/*     */ 
/*     */ public class SplineInterpolator extends Interpolator
/*     */ {
/*     */   private final double x1;
/*     */   private final double y1;
/*     */   private final double x2;
/*     */   private final double y2;
/*     */   private final boolean isCurveLinear;
/*     */   private static final int SAMPLE_SIZE = 16;
/*     */   private static final double SAMPLE_INCREMENT = 0.0625D;
/*  99 */   private final double[] xSamples = new double[17];
/*     */ 
/*     */   public SplineInterpolator(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 116 */     if ((paramDouble1 < 0.0D) || (paramDouble1 > 1.0D) || (paramDouble2 < 0.0D) || (paramDouble2 > 1.0D) || (paramDouble3 < 0.0D) || (paramDouble3 > 1.0D) || (paramDouble4 < 0.0D) || (paramDouble4 > 1.0D))
/*     */     {
/* 118 */       throw new IllegalArgumentException("Control point coordinates must all be in range [0,1]");
/*     */     }
/*     */ 
/* 123 */     this.x1 = paramDouble1;
/* 124 */     this.y1 = paramDouble2;
/* 125 */     this.x2 = paramDouble3;
/* 126 */     this.y2 = paramDouble4;
/*     */ 
/* 129 */     this.isCurveLinear = ((this.x1 == this.y1) && (this.x2 == this.y2));
/*     */ 
/* 132 */     if (!this.isCurveLinear)
/* 133 */       for (int i = 0; i < 17; i++)
/* 134 */         this.xSamples[i] = eval(i * 0.0625D, this.x1, this.x2);
/*     */   }
/*     */ 
/*     */   public double curve(double paramDouble)
/*     */   {
/* 150 */     if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
/* 151 */       throw new IllegalArgumentException("x must be in range [0,1]");
/*     */     }
/*     */ 
/* 155 */     if ((this.isCurveLinear) || (paramDouble == 0.0D) || (paramDouble == 1.0D)) {
/* 156 */       return paramDouble;
/*     */     }
/*     */ 
/* 161 */     return eval(findTForX(paramDouble), this.y1, this.y2);
/*     */   }
/*     */ 
/*     */   private double eval(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 183 */     double d = 1.0D - paramDouble1;
/* 184 */     return paramDouble1 * (3.0D * d * (d * paramDouble2 + paramDouble1 * paramDouble3) + paramDouble1 * paramDouble1);
/*     */   }
/*     */ 
/*     */   private double evalDerivative(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 207 */     double d = 1.0D - paramDouble1;
/* 208 */     return 3.0D * (d * (d * paramDouble2 + 2.0D * paramDouble1 * (paramDouble3 - paramDouble2)) + paramDouble1 * paramDouble1 * (1.0D - paramDouble3));
/*     */   }
/*     */ 
/*     */   private double getInitialGuessForT(double paramDouble)
/*     */   {
/* 226 */     for (int i = 1; i < 17; i++) {
/* 227 */       if (this.xSamples[i] >= paramDouble) {
/* 228 */         double d = this.xSamples[i] - this.xSamples[(i - 1)];
/* 229 */         if (d == 0.0D)
/*     */         {
/* 231 */           return (i - 1) * 0.0625D;
/*     */         }
/*     */ 
/* 234 */         return (i - 1 + (paramDouble - this.xSamples[(i - 1)]) / d) * 0.0625D;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 243 */     return 1.0D;
/*     */   }
/*     */ 
/*     */   private double findTForX(double paramDouble)
/*     */   {
/* 257 */     double d1 = getInitialGuessForT(paramDouble);
/*     */ 
/* 264 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 266 */       double d2 = eval(d1, this.x1, this.x2) - paramDouble;
/* 267 */       if (d2 == 0.0D)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 272 */       double d3 = evalDerivative(d1, this.x1, this.x2);
/* 273 */       if (d3 == 0.0D)
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/* 278 */       d1 -= d2 / d3;
/*     */     }
/*     */ 
/* 281 */     return d1;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 286 */     return "SplineInterpolator [x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.SplineInterpolator
 * JD-Core Version:    0.6.2
 */