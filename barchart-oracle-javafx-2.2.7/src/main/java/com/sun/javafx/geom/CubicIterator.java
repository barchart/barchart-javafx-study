/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class CubicIterator
/*     */   implements PathIterator
/*     */ {
/*     */   CubicCurve2D cubic;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */ 
/*     */   CubicIterator(CubicCurve2D paramCubicCurve2D, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     this.cubic = paramCubicCurve2D;
/*  46 */     this.transform = paramBaseTransform;
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/*  56 */     return 1;
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/*  64 */     return this.index > 1;
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/*  73 */     this.index += 1;
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/*  95 */     if (isDone())
/*  96 */       throw new NoSuchElementException("cubic iterator iterator out of bounds");
/*     */     int i;
/*  99 */     if (this.index == 0) {
/* 100 */       paramArrayOfFloat[0] = this.cubic.x1;
/* 101 */       paramArrayOfFloat[1] = this.cubic.y1;
/* 102 */       i = 0;
/*     */     } else {
/* 104 */       paramArrayOfFloat[0] = this.cubic.ctrlx1;
/* 105 */       paramArrayOfFloat[1] = this.cubic.ctrly1;
/* 106 */       paramArrayOfFloat[2] = this.cubic.ctrlx2;
/* 107 */       paramArrayOfFloat[3] = this.cubic.ctrly2;
/* 108 */       paramArrayOfFloat[4] = this.cubic.x2;
/* 109 */       paramArrayOfFloat[5] = this.cubic.y2;
/* 110 */       i = 3;
/*     */     }
/* 112 */     if (this.transform != null) {
/* 113 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, this.index == 0 ? 1 : 3);
/*     */     }
/* 115 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.CubicIterator
 * JD-Core Version:    0.6.2
 */