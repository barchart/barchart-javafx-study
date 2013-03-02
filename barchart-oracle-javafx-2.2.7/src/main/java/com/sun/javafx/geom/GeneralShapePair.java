/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class GeneralShapePair extends ShapePair
/*     */ {
/*     */   private final Shape outer;
/*     */   private final Shape inner;
/*     */   private final int combinationType;
/*     */ 
/*     */   public GeneralShapePair(Shape paramShape1, Shape paramShape2, int paramInt)
/*     */   {
/*  47 */     this.outer = paramShape1;
/*  48 */     this.inner = paramShape2;
/*  49 */     this.combinationType = paramInt;
/*     */   }
/*     */ 
/*     */   public final int getCombinationType()
/*     */   {
/*  54 */     return this.combinationType;
/*     */   }
/*     */ 
/*     */   public final Shape getOuterShape()
/*     */   {
/*  59 */     return this.outer;
/*     */   }
/*     */ 
/*     */   public final Shape getInnerShape()
/*     */   {
/*  64 */     return this.inner;
/*     */   }
/*     */ 
/*     */   public Shape copy()
/*     */   {
/*  69 */     return new GeneralShapePair(this.outer.copy(), this.inner.copy(), this.combinationType);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/*  74 */     if (this.combinationType == 4) {
/*  75 */       return (this.outer.contains(paramFloat1, paramFloat2)) && (this.inner.contains(paramFloat1, paramFloat2));
/*     */     }
/*  77 */     return (this.outer.contains(paramFloat1, paramFloat2)) && (!this.inner.contains(paramFloat1, paramFloat2));
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  83 */     if (this.combinationType == 4) {
/*  84 */       return (this.outer.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (this.inner.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */     }
/*  86 */     return (this.outer.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (!this.inner.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  92 */     if (this.combinationType == 4) {
/*  93 */       return (this.outer.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (this.inner.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */     }
/*  95 */     return (this.outer.contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) && (!this.inner.intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4));
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 101 */     RectBounds localRectBounds = this.outer.getBounds();
/* 102 */     if (this.combinationType == 4) {
/* 103 */       localRectBounds.intersectWith(this.inner.getBounds());
/*     */     }
/* 105 */     return localRectBounds;
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 110 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 115 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.GeneralShapePair
 * JD-Core Version:    0.6.2
 */