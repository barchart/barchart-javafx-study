/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public abstract class RectangularShape extends Shape
/*     */ {
/*     */   public abstract float getX();
/*     */ 
/*     */   public abstract float getY();
/*     */ 
/*     */   public abstract float getWidth();
/*     */ 
/*     */   public abstract float getHeight();
/*     */ 
/*     */   public float getMinX()
/*     */   {
/* 100 */     return getX();
/*     */   }
/*     */ 
/*     */   public float getMinY()
/*     */   {
/* 112 */     return getY();
/*     */   }
/*     */ 
/*     */   public float getMaxX()
/*     */   {
/* 124 */     return getX() + getWidth();
/*     */   }
/*     */ 
/*     */   public float getMaxY()
/*     */   {
/* 136 */     return getY() + getHeight();
/*     */   }
/*     */ 
/*     */   public float getCenterX()
/*     */   {
/* 148 */     return getX() + getWidth() / 2.0F;
/*     */   }
/*     */ 
/*     */   public float getCenterY()
/*     */   {
/* 160 */     return getY() + getHeight() / 2.0F;
/*     */   }
/*     */ 
/*     */   public abstract boolean isEmpty();
/*     */ 
/*     */   public abstract void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   public void setFrame(Point2D paramPoint2D, Dimension2D paramDimension2D)
/*     */   {
/* 200 */     setFrame(paramPoint2D.x, paramPoint2D.y, paramDimension2D.width, paramDimension2D.height);
/*     */   }
/*     */ 
/*     */   public void setFrameFromDiagonal(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*     */     float f;
/* 216 */     if (paramFloat3 < paramFloat1) {
/* 217 */       f = paramFloat1;
/* 218 */       paramFloat1 = paramFloat3;
/* 219 */       paramFloat3 = f;
/*     */     }
/* 221 */     if (paramFloat4 < paramFloat2) {
/* 222 */       f = paramFloat2;
/* 223 */       paramFloat2 = paramFloat4;
/* 224 */       paramFloat4 = f;
/*     */     }
/* 226 */     setFrame(paramFloat1, paramFloat2, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
/*     */   }
/*     */ 
/*     */   public void setFrameFromDiagonal(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 240 */     setFrameFromDiagonal(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y);
/*     */   }
/*     */ 
/*     */   public void setFrameFromCenter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 258 */     float f1 = Math.abs(paramFloat3 - paramFloat1);
/* 259 */     float f2 = Math.abs(paramFloat4 - paramFloat2);
/* 260 */     setFrame(paramFloat1 - f1, paramFloat2 - f2, f1 * 2.0F, f2 * 2.0F);
/*     */   }
/*     */ 
/*     */   public void setFrameFromCenter(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 273 */     setFrameFromCenter(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y);
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/* 281 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 289 */     float f1 = getWidth();
/* 290 */     float f2 = getHeight();
/* 291 */     if ((f1 < 0.0F) || (f2 < 0.0F)) {
/* 292 */       return new RectBounds();
/*     */     }
/* 294 */     float f3 = getX();
/* 295 */     float f4 = getY();
/* 296 */     float f5 = (float)Math.floor(f3);
/* 297 */     float f6 = (float)Math.floor(f4);
/* 298 */     float f7 = (float)Math.ceil(f3 + f1);
/* 299 */     float f8 = (float)Math.ceil(f4 + f2);
/* 300 */     return new RectBounds(f5, f6, f7, f8);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 330 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 335 */     return getClass().getName() + "[x=" + getX() + ",y=" + getY() + ",w=" + getWidth() + ",h=" + getHeight() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.RectangularShape
 * JD-Core Version:    0.6.2
 */