/*     */ package com.sun.prism.impl.paint;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.LinearGradient;
/*     */ 
/*     */ final class LinearGradientContext extends MultipleGradientContext
/*     */ {
/*     */   private float dgdX;
/*     */   private float dgdY;
/*     */   private float gc;
/*     */ 
/*     */   LinearGradientContext(LinearGradient paramLinearGradient, BaseTransform paramBaseTransform, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat, Color[] paramArrayOfColor, int paramInt)
/*     */   {
/*  70 */     super(paramLinearGradient, paramBaseTransform, paramArrayOfFloat, paramArrayOfColor, paramInt);
/*     */ 
/*  82 */     float f1 = paramFloat3 - paramFloat1;
/*  83 */     float f2 = paramFloat4 - paramFloat2;
/*  84 */     float f3 = f1 * f1 + f2 * f2;
/*     */ 
/*  87 */     float f4 = f1 / f3;
/*  88 */     float f5 = f2 / f3;
/*     */ 
/*  91 */     this.dgdX = (this.a00 * f4 + this.a10 * f5);
/*     */ 
/*  93 */     this.dgdY = (this.a01 * f4 + this.a11 * f5);
/*     */ 
/*  96 */     this.gc = ((this.a02 - paramFloat1) * f4 + (this.a12 - paramFloat2) * f5);
/*     */   }
/*     */ 
/*     */   protected void fillRaster(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 111 */     float f1 = 0.0F;
/*     */ 
/* 114 */     int i = paramInt1 + paramInt5;
/*     */ 
/* 117 */     float f2 = this.dgdX * paramInt3 + this.gc;
/*     */ 
/* 119 */     for (int j = 0; j < paramInt6; j++)
/*     */     {
/* 122 */       f1 = f2 + this.dgdY * (paramInt4 + j);
/*     */ 
/* 124 */       while (paramInt1 < i)
/*     */       {
/* 126 */         paramArrayOfInt[(paramInt1++)] = indexIntoGradientsArrays(f1);
/*     */ 
/* 129 */         f1 += this.dgdX;
/*     */       }
/*     */ 
/* 133 */       paramInt1 += paramInt2;
/*     */ 
/* 136 */       i = paramInt1 + paramInt5;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.paint.LinearGradientContext
 * JD-Core Version:    0.6.2
 */