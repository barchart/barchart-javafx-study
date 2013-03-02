/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public abstract class BaseBounds
/*     */ {
/*     */   public abstract BaseBounds copy();
/*     */ 
/*     */   public abstract boolean is2D();
/*     */ 
/*     */   public abstract BoundsType getBoundsType();
/*     */ 
/*     */   public abstract float getWidth();
/*     */ 
/*     */   public abstract float getHeight();
/*     */ 
/*     */   public abstract float getDepth();
/*     */ 
/*     */   public abstract float getMinX();
/*     */ 
/*     */   public abstract float getMinY();
/*     */ 
/*     */   public abstract float getMinZ();
/*     */ 
/*     */   public abstract float getMaxX();
/*     */ 
/*     */   public abstract float getMaxY();
/*     */ 
/*     */   public abstract float getMaxZ();
/*     */ 
/*     */   public abstract Vec2f getMin(Vec2f paramVec2f);
/*     */ 
/*     */   public abstract Vec2f getMax(Vec2f paramVec2f);
/*     */ 
/*     */   public abstract Vec3f getMin(Vec3f paramVec3f);
/*     */ 
/*     */   public abstract Vec3f getMax(Vec3f paramVec3f);
/*     */ 
/*     */   public abstract BaseBounds deriveWithUnion(BaseBounds paramBaseBounds);
/*     */ 
/*     */   public abstract BaseBounds deriveWithNewBounds(Rectangle paramRectangle);
/*     */ 
/*     */   public abstract BaseBounds deriveWithNewBounds(BaseBounds paramBaseBounds);
/*     */ 
/*     */   public abstract BaseBounds deriveWithNewBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*     */ 
/*     */   public abstract BaseBounds deriveWithNewBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*     */ 
/*     */   public abstract BaseBounds deriveWithPadding(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ 
/*     */   public abstract void intersectWith(Rectangle paramRectangle);
/*     */ 
/*     */   public abstract void intersectWith(BaseBounds paramBaseBounds);
/*     */ 
/*     */   public abstract void intersectWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*     */ 
/*     */   public abstract void setBoundsAndSort(Point2D paramPoint2D1, Point2D paramPoint2D2);
/*     */ 
/*     */   public abstract void setBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*     */ 
/*     */   public abstract void add(Point2D paramPoint2D);
/*     */ 
/*     */   public abstract void add(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ 
/*     */   public abstract boolean contains(Point2D paramPoint2D);
/*     */ 
/*     */   public abstract boolean contains(float paramFloat1, float paramFloat2);
/*     */ 
/*     */   public abstract boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   public abstract boolean isEmpty();
/*     */ 
/*     */   public abstract void roundOut();
/*     */ 
/*     */   public abstract BaseBounds makeEmpty();
/*     */ 
/*     */   public abstract boolean disjoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   protected abstract void sortMinMax();
/*     */ 
/*     */   public static BaseBounds getInstance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 122 */     if ((paramFloat3 == 0.0F) && (paramFloat6 == 0.0F)) {
/* 123 */       return getInstance(paramFloat1, paramFloat2, paramFloat4, paramFloat5);
/*     */     }
/* 125 */     return new BoxBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public static BaseBounds getInstance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 131 */     return new RectBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public static enum BoundsType
/*     */   {
/*  18 */     RECTANGLE, 
/*  19 */     BOX;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.BaseBounds
 * JD-Core Version:    0.6.2
 */