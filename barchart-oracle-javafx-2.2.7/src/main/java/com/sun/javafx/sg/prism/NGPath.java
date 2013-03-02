/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.Arc2D;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import com.sun.javafx.sg.PGPath.FillRule;
/*     */ 
/*     */ public class NGPath extends NGShape
/*     */   implements PGPath
/*     */ {
/*  18 */   private Path2D p = new Path2D();
/*     */ 
/*     */   public void reset() {
/*  21 */     this.p.reset();
/*     */   }
/*     */ 
/*     */   public void update() {
/*  25 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   private int toWindingRule(PGPath.FillRule paramFillRule) {
/*  29 */     if (paramFillRule == PGPath.FillRule.NON_ZERO) {
/*  30 */       return 1;
/*     */     }
/*  32 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setFillRule(PGPath.FillRule paramFillRule)
/*     */   {
/*  37 */     this.p.setWindingRule(toWindingRule(paramFillRule));
/*     */   }
/*     */ 
/*     */   public float getCurrentX() {
/*  41 */     return this.p.getCurrentPoint().x;
/*     */   }
/*     */ 
/*     */   public float getCurrentY() {
/*  45 */     return this.p.getCurrentPoint().y;
/*     */   }
/*     */ 
/*     */   public void addClosePath() {
/*  49 */     this.p.closePath();
/*     */   }
/*     */ 
/*     */   public void addMoveTo(float paramFloat1, float paramFloat2) {
/*  53 */     this.p.moveTo(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void addLineTo(float paramFloat1, float paramFloat2) {
/*  57 */     this.p.lineTo(paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void addQuadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/*  61 */     this.p.quadTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public void addCubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  68 */     this.p.curveTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public void addArcTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
/*     */   {
/*  74 */     Arc2D localArc2D = new Arc2D(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, 0);
/*  75 */     BaseTransform localBaseTransform = paramFloat7 == 0.0D ? null : BaseTransform.getRotateInstance(paramFloat7, localArc2D.getCenterX(), localArc2D.getCenterY());
/*     */ 
/*  78 */     PathIterator localPathIterator = localArc2D.getPathIterator(localBaseTransform);
/*     */ 
/*  82 */     localPathIterator.next();
/*  83 */     this.p.append(localPathIterator, true);
/*     */   }
/*     */ 
/*     */   public Object getGeometry() {
/*  87 */     return this.p;
/*     */   }
/*     */ 
/*     */   public Shape getShape()
/*     */   {
/*  92 */     return this.p;
/*     */   }
/*     */ 
/*     */   public boolean acceptsPath2dOnUpdate() {
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   public void updateWithPath2d(Path2D paramPath2D) {
/* 100 */     this.p.setTo(paramPath2D);
/* 101 */     geometryChanged();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGPath
 * JD-Core Version:    0.6.2
 */