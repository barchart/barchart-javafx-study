/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ 
/*     */ public abstract class TransformedShape extends Shape
/*     */ {
/*     */   protected final Shape delegate;
/*     */   private Shape cachedTransformedShape;
/*     */ 
/*     */   public static TransformedShape transformedShape(Shape paramShape, BaseTransform paramBaseTransform)
/*     */   {
/*  45 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/*  46 */       return translatedShape(paramShape, paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*     */     }
/*  48 */     return new General(paramShape, paramBaseTransform.copy());
/*     */   }
/*     */ 
/*     */   public static TransformedShape translatedShape(Shape paramShape, double paramDouble1, double paramDouble2)
/*     */   {
/*  66 */     return new Translate(paramShape, (float)paramDouble1, (float)paramDouble2);
/*     */   }
/*     */ 
/*     */   protected TransformedShape(Shape paramShape)
/*     */   {
/*  72 */     this.delegate = paramShape;
/*     */   }
/*     */ 
/*     */   public Shape getDelegateNoClone() {
/*  76 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   public abstract BaseTransform getTransformNoClone();
/*     */ 
/*     */   public abstract BaseTransform adjust(BaseTransform paramBaseTransform);
/*     */ 
/*     */   protected Point2D untransform(float paramFloat1, float paramFloat2)
/*     */   {
/*  90 */     Point2D localPoint2D = new Point2D(paramFloat1, paramFloat2);
/*     */     try {
/*  92 */       localPoint2D = getTransformNoClone().inverseTransform(localPoint2D, localPoint2D);
/*     */     }
/*     */     catch (NoninvertibleTransformException localNoninvertibleTransformException)
/*     */     {
/*     */     }
/*  97 */     return localPoint2D;
/*     */   }
/*     */ 
/*     */   protected BaseBounds untransformedBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 101 */     RectBounds localRectBounds = new RectBounds(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/*     */     try {
/* 103 */       return getTransformNoClone().inverseTransform(localRectBounds, localRectBounds); } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*     */     }
/* 105 */     return localRectBounds.makeEmpty();
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 111 */     float[] arrayOfFloat = new float[4];
/* 112 */     Shape.accumulate(arrayOfFloat, this.delegate, getTransformNoClone());
/* 113 */     return new RectBounds(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 121 */     return this.delegate.contains(untransform(paramFloat1, paramFloat2));
/*     */   }
/*     */ 
/*     */   private Shape getCachedTransformedShape()
/*     */   {
/* 131 */     if (this.cachedTransformedShape == null) {
/* 132 */       this.cachedTransformedShape = copy();
/*     */     }
/* 134 */     return this.cachedTransformedShape;
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 140 */     return getCachedTransformedShape().intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 146 */     return getCachedTransformedShape().contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 151 */     return this.delegate.getPathIterator(adjust(paramBaseTransform));
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 158 */     return this.delegate.getPathIterator(adjust(paramBaseTransform), paramFloat);
/*     */   }
/*     */ 
/*     */   public Shape copy()
/*     */   {
/* 163 */     return getTransformNoClone().createTransformedShape(this.delegate);
/*     */   }
/*     */ 
/*     */   static final class Translate extends TransformedShape
/*     */   {
/*     */     private final float tx;
/*     */     private final float ty;
/*     */     private BaseTransform cachedTx;
/*     */ 
/*     */     public Translate(Shape paramShape, float paramFloat1, float paramFloat2)
/*     */     {
/* 192 */       super();
/* 193 */       this.tx = paramFloat1;
/* 194 */       this.ty = paramFloat2;
/*     */     }
/*     */ 
/*     */     public BaseTransform getTransformNoClone()
/*     */     {
/* 199 */       if (this.cachedTx == null) {
/* 200 */         this.cachedTx = BaseTransform.getTranslateInstance(this.tx, this.ty);
/*     */       }
/* 202 */       return this.cachedTx;
/*     */     }
/*     */ 
/*     */     public BaseTransform adjust(BaseTransform paramBaseTransform) {
/* 206 */       if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/* 207 */         return BaseTransform.getTranslateInstance(this.tx, this.ty);
/*     */       }
/* 209 */       return paramBaseTransform.copy().deriveWithTranslation(this.tx, this.ty);
/*     */     }
/*     */ 
/*     */     public RectBounds getBounds()
/*     */     {
/* 215 */       RectBounds localRectBounds = this.delegate.getBounds();
/* 216 */       return new RectBounds(localRectBounds.getMinX() + this.tx, localRectBounds.getMinY() + this.ty, localRectBounds.getMaxX() + this.tx, localRectBounds.getMaxY() + this.ty);
/*     */     }
/*     */ 
/*     */     public boolean contains(float paramFloat1, float paramFloat2)
/*     */     {
/* 222 */       return this.delegate.contains(paramFloat1 - this.tx, paramFloat2 - this.ty);
/*     */     }
/*     */ 
/*     */     public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 227 */       return this.delegate.intersects(paramFloat1 - this.tx, paramFloat2 - this.ty, paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 232 */       return this.delegate.contains(paramFloat1 - this.tx, paramFloat2 - this.ty, paramFloat3, paramFloat4);
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class General extends TransformedShape
/*     */   {
/*     */     BaseTransform transform;
/*     */ 
/*     */     General(Shape paramShape, BaseTransform paramBaseTransform)
/*     */     {
/* 170 */       super();
/* 171 */       this.transform = paramBaseTransform;
/*     */     }
/*     */ 
/*     */     public BaseTransform getTransformNoClone() {
/* 175 */       return this.transform;
/*     */     }
/*     */ 
/*     */     public BaseTransform adjust(BaseTransform paramBaseTransform) {
/* 179 */       if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/* 180 */         return this.transform.copy();
/*     */       }
/* 182 */       return paramBaseTransform.copy().deriveWithConcatenation(this.transform);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.TransformedShape
 * JD-Core Version:    0.6.2
 */