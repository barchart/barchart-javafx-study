/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class EllipseIterator
/*     */   implements PathIterator
/*     */ {
/*     */   double x;
/*     */   double y;
/*     */   double w;
/*     */   double h;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */   public static final double CtrlVal = 0.5522847498307933D;
/*     */   private static final double pcv = 0.7761423749153966D;
/*     */   private static final double ncv = 0.2238576250846033D;
/*  92 */   private static final double[][] ctrlpts = { { 1.0D, 0.7761423749153966D, 0.7761423749153966D, 1.0D, 0.5D, 1.0D }, { 0.2238576250846033D, 1.0D, 0.0D, 0.7761423749153966D, 0.0D, 0.5D }, { 0.0D, 0.2238576250846033D, 0.2238576250846033D, 0.0D, 0.5D, 0.0D }, { 0.7761423749153966D, 0.0D, 1.0D, 0.2238576250846033D, 1.0D, 0.5D } };
/*     */ 
/*     */   EllipseIterator(Ellipse2D paramEllipse2D, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     this.x = paramEllipse2D.x;
/*  46 */     this.y = paramEllipse2D.y;
/*  47 */     this.w = paramEllipse2D.width;
/*  48 */     this.h = paramEllipse2D.height;
/*  49 */     this.transform = paramBaseTransform;
/*  50 */     if ((this.w < 0.0D) || (this.h < 0.0D))
/*  51 */       this.index = 6;
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/*  62 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/*  70 */     return this.index > 5;
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/*  79 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/* 118 */     if (isDone()) {
/* 119 */       throw new NoSuchElementException("ellipse iterator out of bounds");
/*     */     }
/* 121 */     if (this.index == 5) {
/* 122 */       return 4;
/*     */     }
/* 124 */     if (this.index == 0) {
/* 125 */       arrayOfDouble = ctrlpts[3];
/* 126 */       paramArrayOfFloat[0] = ((float)(this.x + arrayOfDouble[4] * this.w));
/* 127 */       paramArrayOfFloat[1] = ((float)(this.y + arrayOfDouble[5] * this.h));
/* 128 */       if (this.transform != null) {
/* 129 */         this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 1);
/*     */       }
/* 131 */       return 0;
/*     */     }
/* 133 */     double[] arrayOfDouble = ctrlpts[(this.index - 1)];
/* 134 */     paramArrayOfFloat[0] = ((float)(this.x + arrayOfDouble[0] * this.w));
/* 135 */     paramArrayOfFloat[1] = ((float)(this.y + arrayOfDouble[1] * this.h));
/* 136 */     paramArrayOfFloat[2] = ((float)(this.x + arrayOfDouble[2] * this.w));
/* 137 */     paramArrayOfFloat[3] = ((float)(this.y + arrayOfDouble[3] * this.h));
/* 138 */     paramArrayOfFloat[4] = ((float)(this.x + arrayOfDouble[4] * this.w));
/* 139 */     paramArrayOfFloat[5] = ((float)(this.y + arrayOfDouble[5] * this.h));
/* 140 */     if (this.transform != null) {
/* 141 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 3);
/*     */     }
/* 143 */     return 3;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.EllipseIterator
 * JD-Core Version:    0.6.2
 */