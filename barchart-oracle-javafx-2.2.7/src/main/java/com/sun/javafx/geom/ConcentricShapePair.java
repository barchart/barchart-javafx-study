/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public final class ConcentricShapePair extends ShapePair
/*     */ {
/*     */   private final Shape outer;
/*     */   private final Shape inner;
/*     */ 
/*     */   public ConcentricShapePair(Shape paramShape1, Shape paramShape2)
/*     */   {
/*  47 */     this.outer = paramShape1;
/*  48 */     this.inner = paramShape2;
/*     */   }
/*     */ 
/*     */   public int getCombinationType()
/*     */   {
/*  53 */     return 1;
/*     */   }
/*     */ 
/*     */   public Shape getOuterShape()
/*     */   {
/*  58 */     return this.outer;
/*     */   }
/*     */ 
/*     */   public Shape getInnerShape()
/*     */   {
/*  63 */     return this.inner;
/*     */   }
/*     */ 
/*     */   public Shape copy()
/*     */   {
/*  68 */     return new ConcentricShapePair(this.outer.copy(), this.inner.copy());
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/*  73 */     return (this.outer.contains(paramFloat1, paramFloat2)) && (!this.inner.contains(paramFloat1, paramFloat2));
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  78 */     return (this.outer.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (!this.inner.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  83 */     return (this.outer.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (!this.inner.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/*  88 */     return this.outer.getBounds();
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/*  93 */     return new PairIterator(this.outer.getPathIterator(paramBaseTransform), this.inner.getPathIterator(paramBaseTransform));
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/*  99 */     return new PairIterator(this.outer.getPathIterator(paramBaseTransform, paramFloat), this.inner.getPathIterator(paramBaseTransform, paramFloat));
/*     */   }
/*     */ 
/*     */   static class PairIterator implements PathIterator {
/*     */     PathIterator outer;
/*     */     PathIterator inner;
/*     */ 
/*     */     PairIterator(PathIterator paramPathIterator1, PathIterator paramPathIterator2) {
/* 108 */       this.outer = paramPathIterator1;
/* 109 */       this.inner = paramPathIterator2;
/*     */     }
/*     */ 
/*     */     public int getWindingRule() {
/* 113 */       return 0;
/*     */     }
/*     */ 
/*     */     public int currentSegment(float[] paramArrayOfFloat) {
/* 117 */       if (this.outer.isDone()) {
/* 118 */         return this.inner.currentSegment(paramArrayOfFloat);
/*     */       }
/* 120 */       return this.outer.currentSegment(paramArrayOfFloat);
/*     */     }
/*     */ 
/*     */     public boolean isDone()
/*     */     {
/* 125 */       return (this.outer.isDone()) && (this.inner.isDone());
/*     */     }
/*     */ 
/*     */     public void next() {
/* 129 */       if (this.outer.isDone())
/* 130 */         this.inner.next();
/*     */       else
/* 132 */         this.outer.next();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.ConcentricShapePair
 * JD-Core Version:    0.6.2
 */