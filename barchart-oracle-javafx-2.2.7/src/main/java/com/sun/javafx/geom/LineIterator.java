/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ class LineIterator
/*     */   implements PathIterator
/*     */ {
/*     */   Line2D line;
/*     */   BaseTransform transform;
/*     */   int index;
/*     */ 
/*     */   LineIterator(Line2D paramLine2D, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     this.line = paramLine2D;
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
/*  96 */       throw new NoSuchElementException("line iterator out of bounds");
/*     */     int i;
/*  99 */     if (this.index == 0) {
/* 100 */       paramArrayOfFloat[0] = this.line.x1;
/* 101 */       paramArrayOfFloat[1] = this.line.y1;
/* 102 */       i = 0;
/*     */     } else {
/* 104 */       paramArrayOfFloat[0] = this.line.x2;
/* 105 */       paramArrayOfFloat[1] = this.line.y2;
/* 106 */       i = 1;
/*     */     }
/* 108 */     if (this.transform != null) {
/* 109 */       this.transform.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 1);
/*     */     }
/* 111 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.LineIterator
 * JD-Core Version:    0.6.2
 */