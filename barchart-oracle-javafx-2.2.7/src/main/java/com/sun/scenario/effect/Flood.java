/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class Flood extends CoreEffect
/*     */ {
/*     */   private Object paint;
/*  43 */   private RectBounds bounds = new RectBounds();
/*     */ 
/*     */   public Flood(Object paramObject)
/*     */   {
/*  53 */     if (paramObject == null) {
/*  54 */       throw new IllegalArgumentException("Paint must be non-null");
/*     */     }
/*  56 */     this.paint = paramObject;
/*  57 */     updatePeerKey("Flood");
/*     */   }
/*     */ 
/*     */   public Flood(Object paramObject, RectBounds paramRectBounds)
/*     */   {
/*  70 */     this(paramObject);
/*  71 */     if (paramRectBounds == null) {
/*  72 */       throw new IllegalArgumentException("Bounds must be non-null");
/*     */     }
/*  74 */     this.bounds.setBounds(paramRectBounds);
/*     */   }
/*     */ 
/*     */   public Object getPaint()
/*     */   {
/*  83 */     return this.paint;
/*     */   }
/*     */ 
/*     */   public void setPaint(Object paramObject)
/*     */   {
/*  93 */     if (paramObject == null) {
/*  94 */       throw new IllegalArgumentException("Paint must be non-null");
/*     */     }
/*  96 */     Object localObject = this.paint;
/*  97 */     this.paint = paramObject;
/*  98 */     firePropertyChange("paint", localObject, paramObject);
/*     */   }
/*     */ 
/*     */   public RectBounds getFloodBounds() {
/* 102 */     return new RectBounds(this.bounds);
/*     */   }
/*     */ 
/*     */   public void setFloodBounds(RectBounds paramRectBounds) {
/* 106 */     if (paramRectBounds == null) {
/* 107 */       throw new IllegalArgumentException("Bounds must be non-null");
/*     */     }
/* 109 */     RectBounds localRectBounds = new RectBounds(this.bounds);
/* 110 */     this.bounds.setBounds(paramRectBounds);
/* 111 */     firePropertyChange("flood bounds", localRectBounds, paramRectBounds);
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 118 */     return transformBounds(paramBaseTransform, this.bounds);
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 141 */     return new Point2D((0.0F / 0.0F), (0.0F / 0.0F));
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 163 */     return new Point2D((0.0F / 0.0F), (0.0F / 0.0F));
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 172 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Flood
 * JD-Core Version:    0.6.2
 */