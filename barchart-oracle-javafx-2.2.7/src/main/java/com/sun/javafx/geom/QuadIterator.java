/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class QuadIterator
/*     */   implements PathIterator
/*     */ {
/*     */   QuadCurve2D quad;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */ 
/*     */   QuadIterator(QuadCurve2D paramQuadCurve2D, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     this.quad = paramQuadCurve2D;
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
/*  96 */       throw new NoSuchElementException("quad iterator iterator out of bounds");
/*     */     int i;
/*  99 */     if (this.index == 0) {
/* 100 */       paramArrayOfFloat[0] = this.quad.x1;
/* 101 */       paramArrayOfFloat[1] = this.quad.y1;
/* 102 */       i = 0;
/*     */     } else {
/* 104 */       paramArrayOfFloat[0] = this.quad.ctrlx;
/* 105 */       paramArrayOfFloat[1] = this.quad.ctrly;
/* 106 */       paramArrayOfFloat[2] = this.quad.x2;
/* 107 */       paramArrayOfFloat[3] = this.quad.y2;
/* 108 */       i = 2;
/*     */     }
/* 110 */     if (this.transform != null) {
/* 111 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, this.index == 0 ? 1 : 2);
/*     */     }
/* 113 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.QuadIterator
 * JD-Core Version:    0.6.2
 */