/*     */ package com.sun.prism.j2d.paint;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.PaintContext;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Point2D.Double;
/*     */ import java.awt.geom.Point2D.Float;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.ColorModel;
/*     */ 
/*     */ public final class RadialGradientPaint extends MultipleGradientPaint
/*     */ {
/*     */   private final Point2D focus;
/*     */   private final Point2D center;
/*     */   private final float radius;
/*     */ 
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat, Color[] paramArrayOfColor)
/*     */   {
/* 178 */     this(paramFloat1, paramFloat2, paramFloat3, paramFloat1, paramFloat2, paramArrayOfFloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(Point2D paramPoint2D, float paramFloat, float[] paramArrayOfFloat, Color[] paramArrayOfColor)
/*     */   {
/* 215 */     this(paramPoint2D, paramFloat, paramPoint2D, paramArrayOfFloat, paramArrayOfColor, MultipleGradientPaint.CycleMethod.NO_CYCLE);
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod)
/*     */   {
/* 258 */     this(paramFloat1, paramFloat2, paramFloat3, paramFloat1, paramFloat2, paramArrayOfFloat, paramArrayOfColor, paramCycleMethod);
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(Point2D paramPoint2D, float paramFloat, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod)
/*     */   {
/* 298 */     this(paramPoint2D, paramFloat, paramPoint2D, paramArrayOfFloat, paramArrayOfColor, paramCycleMethod);
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod)
/*     */   {
/* 346 */     this(new Point2D.Float(paramFloat1, paramFloat2), paramFloat3, new Point2D.Float(paramFloat4, paramFloat5), paramArrayOfFloat, paramArrayOfColor, paramCycleMethod);
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(Point2D paramPoint2D1, float paramFloat, Point2D paramPoint2D2, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod)
/*     */   {
/* 389 */     this(paramPoint2D1, paramFloat, paramPoint2D2, paramArrayOfFloat, paramArrayOfColor, paramCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform());
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(Point2D paramPoint2D1, float paramFloat, Point2D paramPoint2D2, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod, MultipleGradientPaint.ColorSpaceType paramColorSpaceType, AffineTransform paramAffineTransform)
/*     */   {
/* 441 */     super(paramArrayOfFloat, paramArrayOfColor, paramCycleMethod, paramColorSpaceType, paramAffineTransform);
/*     */ 
/* 444 */     if (paramPoint2D1 == null) {
/* 445 */       throw new NullPointerException("Center point must be non-null");
/*     */     }
/*     */ 
/* 448 */     if (paramPoint2D2 == null) {
/* 449 */       throw new NullPointerException("Focus point must be non-null");
/*     */     }
/*     */ 
/* 452 */     if (paramFloat < 0.0F) {
/* 453 */       throw new IllegalArgumentException("Radius must be non-negative");
/*     */     }
/*     */ 
/* 457 */     this.center = new Point2D.Double(paramPoint2D1.getX(), paramPoint2D1.getY());
/* 458 */     this.focus = new Point2D.Double(paramPoint2D2.getX(), paramPoint2D2.getY());
/* 459 */     this.radius = paramFloat;
/*     */   }
/*     */ 
/*     */   public RadialGradientPaint(Rectangle2D paramRectangle2D, float[] paramArrayOfFloat, Color[] paramArrayOfColor, MultipleGradientPaint.CycleMethod paramCycleMethod)
/*     */   {
/* 520 */     this(new Point2D.Double(paramRectangle2D.getCenterX(), paramRectangle2D.getCenterY()), 1.0F, new Point2D.Double(paramRectangle2D.getCenterX(), paramRectangle2D.getCenterY()), paramArrayOfFloat, paramArrayOfColor, paramCycleMethod, MultipleGradientPaint.ColorSpaceType.SRGB, createGradientTransform(paramRectangle2D));
/*     */ 
/* 531 */     if (paramRectangle2D.isEmpty())
/* 532 */       throw new IllegalArgumentException("Gradient bounds must be non-empty");
/*     */   }
/*     */ 
/*     */   private static AffineTransform createGradientTransform(Rectangle2D paramRectangle2D)
/*     */   {
/* 538 */     double d1 = paramRectangle2D.getCenterX();
/* 539 */     double d2 = paramRectangle2D.getCenterY();
/* 540 */     AffineTransform localAffineTransform = AffineTransform.getTranslateInstance(d1, d2);
/* 541 */     localAffineTransform.scale(paramRectangle2D.getWidth() / 2.0D, paramRectangle2D.getHeight() / 2.0D);
/* 542 */     localAffineTransform.translate(-d1, -d2);
/* 543 */     return localAffineTransform;
/*     */   }
/*     */ 
/*     */   public PaintContext createContext(ColorModel paramColorModel, Rectangle paramRectangle, Rectangle2D paramRectangle2D, AffineTransform paramAffineTransform, RenderingHints paramRenderingHints)
/*     */   {
/* 556 */     paramAffineTransform = new AffineTransform(paramAffineTransform);
/*     */ 
/* 558 */     paramAffineTransform.concatenate(this.gradientTransform);
/*     */ 
/* 560 */     return new RadialGradientPaintContext(this, paramColorModel, paramRectangle, paramRectangle2D, paramAffineTransform, paramRenderingHints, (float)this.center.getX(), (float)this.center.getY(), this.radius, (float)this.focus.getX(), (float)this.focus.getY(), this.fractions, this.colors, this.cycleMethod, this.colorSpace);
/*     */   }
/*     */ 
/*     */   public Point2D getCenterPoint()
/*     */   {
/* 578 */     return new Point2D.Double(this.center.getX(), this.center.getY());
/*     */   }
/*     */ 
/*     */   public Point2D getFocusPoint()
/*     */   {
/* 587 */     return new Point2D.Double(this.focus.getX(), this.focus.getY());
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 596 */     return this.radius;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.paint.RadialGradientPaint
 * JD-Core Version:    0.6.2
 */