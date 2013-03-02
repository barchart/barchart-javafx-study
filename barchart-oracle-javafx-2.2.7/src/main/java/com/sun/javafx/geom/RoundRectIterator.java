/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class RoundRectIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   double aw;
/*     */   double ah;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */   private static final double angle = 0.7853981633974483D;
/*  93 */   private static final double a = 1.0D - Math.cos(0.7853981633974483D);
/*  94 */   private static final double b = Math.tan(0.7853981633974483D);
/*  95 */   private static final double c = Math.sqrt(1.0D + b * b) - 1.0D + a;
/*  96 */   private static final double cv = 1.333333333333333D * a * b / c;
/*  97 */   private static final double acv = (1.0D - cv) / 2.0D;
/*     */ 
/* 103 */   private static final double[][] ctrlpts = { { 0.0D, 0.0D, 0.0D, 0.5D }, { 0.0D, 0.0D, 1.0D, -0.5D }, { 0.0D, 0.0D, 1.0D, -acv, 0.0D, acv, 1.0D, 0.0D, 0.0D, 0.5D, 1.0D, 0.0D }, { 1.0D, -0.5D, 1.0D, 0.0D }, { 1.0D, -acv, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D, -acv, 1.0D, 0.0D, 1.0D, -0.5D }, { 1.0D, 0.0D, 0.0D, 0.5D }, { 1.0D, 0.0D, 0.0D, acv, 1.0D, -acv, 0.0D, 0.0D, 1.0D, -0.5D, 0.0D, 0.0D }, { 0.0D, 0.5D, 0.0D, 0.0D }, { 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, acv, 0.0D, 0.0D, 0.0D, 0.5D }, new double[0] };
/*     */ 
/* 124 */   private static final int[] types = { 0, 1, 3, 1, 3, 1, 3, 1, 3, 4 };
/*     */ 
/*     */   RoundRectIterator(RoundRectangle2D paramRoundRectangle2D, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     this.x = paramRoundRectangle2D.x;
/*  46 */     this.y = paramRoundRectangle2D.y;
/*  47 */     this.w = paramRoundRectangle2D.width;
/*  48 */     this.h = paramRoundRectangle2D.height;
/*  49 */     this.aw = Math.min(this.w, Math.abs(paramRoundRectangle2D.arcWidth));
/*  50 */     this.ah = Math.min(this.h, Math.abs(paramRoundRectangle2D.arcHeight));
/*  51 */     this.transform = paramBaseTransform;
/*  52 */     if ((this.aw < 0.0D) || (this.ah < 0.0D))
/*     */     {
/*  54 */       this.index = ctrlpts.length;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/*  65 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/*  73 */     return this.index >= ctrlpts.length;
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/*  82 */     this.index += 1;
/*  83 */     if ((this.index < ctrlpts.length) && (this.aw == 0.0D) && (this.ah == 0.0D) && (types[this.index] == 3))
/*     */     {
/*  88 */       this.index += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/* 152 */     if (isDone()) {
/* 153 */       throw new NoSuchElementException("roundrect iterator out of bounds");
/*     */     }
/* 155 */     double[] arrayOfDouble = ctrlpts[this.index];
/* 156 */     int i = 0;
/* 157 */     for (int j = 0; j < arrayOfDouble.length; j += 4) {
/* 158 */       paramArrayOfFloat[(i++)] = ((float)(this.x + arrayOfDouble[(j + 0)] * this.w + arrayOfDouble[(j + 1)] * this.aw));
/* 159 */       paramArrayOfFloat[(i++)] = ((float)(this.y + arrayOfDouble[(j + 2)] * this.h + arrayOfDouble[(j + 3)] * this.ah));
/*     */     }
/* 161 */     if (this.transform != null) {
/* 162 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, i / 2);
/*     */     }
/* 164 */     return types[this.index];
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.RoundRectIterator
 * JD-Core Version:    0.6.2
 */