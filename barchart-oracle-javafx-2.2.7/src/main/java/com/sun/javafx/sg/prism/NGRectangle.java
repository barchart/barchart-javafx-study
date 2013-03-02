/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.RectangularShape;
/*     */ import com.sun.javafx.geom.RoundRectangle2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.PGRectangle;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.RectShadowGraphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.shape.ShapeRep;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ 
/*     */ public class NGRectangle extends NGShape
/*     */   implements PGRectangle
/*     */ {
/*  26 */   private static final Affine3D TMP_FXTX = new Affine3D();
/*  27 */   private RoundRectangle2D rrect = new RoundRectangle2D();
/*     */ 
/*  85 */   private static final double SQRT_2 = Math.sqrt(2.0D);
/*     */ 
/*     */   public void updateRectangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  32 */     this.rrect.x = paramFloat1;
/*  33 */     this.rrect.y = paramFloat2;
/*  34 */     this.rrect.width = paramFloat3;
/*  35 */     this.rrect.height = paramFloat4;
/*  36 */     this.rrect.arcWidth = paramFloat5;
/*  37 */     this.rrect.arcHeight = paramFloat6;
/*  38 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   boolean isRounded() {
/*  42 */     return (this.rrect.arcWidth > 0.0F) && (this.rrect.arcHeight > 0.0F);
/*     */   }
/*     */ 
/*     */   protected void renderEffect(Graphics paramGraphics)
/*     */   {
/*  47 */     if ((!(paramGraphics instanceof RectShadowGraphics)) || (!renderEffectDirectly(paramGraphics)))
/*  48 */       super.renderEffect(paramGraphics);
/*     */   }
/*     */ 
/*     */   private boolean renderEffectDirectly(Graphics paramGraphics)
/*     */   {
/*  53 */     if ((this.mode != PGShape.Mode.FILL) || (isRounded()))
/*     */     {
/*  55 */       return false;
/*     */     }
/*  57 */     float f = paramGraphics.getExtraAlpha();
/*  58 */     if ((this.fillPaint instanceof Color)) {
/*  59 */       f *= ((Color)this.fillPaint).getAlpha();
/*     */     }
/*     */     else {
/*  62 */       return false;
/*     */     }
/*  64 */     Effect localEffect = getEffect();
/*  65 */     if (EffectUtil.renderEffectForRectangularNode(this, paramGraphics, localEffect, f, true, this.rrect.x, this.rrect.y, this.rrect.width, this.rrect.height))
/*     */     {
/*  70 */       return true;
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   public final Shape getShape()
/*     */   {
/*  77 */     return this.rrect;
/*     */   }
/*     */ 
/*     */   protected ShapeRep createShapeRep(Graphics paramGraphics, boolean paramBoolean)
/*     */   {
/*  82 */     return paramGraphics.getResourceFactory().createRoundRectRep(paramBoolean);
/*     */   }
/*     */ 
/*     */   private static boolean hasRightAngleMiterAndNoDashes(BasicStroke paramBasicStroke)
/*     */   {
/*  87 */     return (paramBasicStroke.getLineJoin() == 0) && (paramBasicStroke.getMiterLimit() >= SQRT_2) && (paramBasicStroke.getDashArray() == null);
/*     */   }
/*     */ 
/*     */   static boolean rectContains(float paramFloat1, float paramFloat2, NGShape paramNGShape, RectangularShape paramRectangularShape)
/*     */   {
/*  96 */     double d1 = paramRectangularShape.getWidth();
/*  97 */     double d2 = paramRectangularShape.getHeight();
/*  98 */     if ((d1 < 0.0D) || (d2 < 0.0D)) {
/*  99 */       return false;
/*     */     }
/* 101 */     PGShape.Mode localMode = paramNGShape.mode;
/* 102 */     if (localMode == PGShape.Mode.EMPTY) {
/* 103 */       return false;
/*     */     }
/* 105 */     double d3 = paramRectangularShape.getX();
/* 106 */     double d4 = paramRectangularShape.getY();
/* 107 */     if (localMode == PGShape.Mode.FILL)
/*     */     {
/* 109 */       return (paramFloat1 >= d3) && (paramFloat2 >= d4) && (paramFloat1 < d3 + d1) && (paramFloat2 < d4 + d2);
/*     */     }
/*     */ 
/* 112 */     float f1 = -1.0F;
/* 113 */     float f2 = -1.0F;
/* 114 */     int i = 0;
/* 115 */     BasicStroke localBasicStroke = paramNGShape.drawStroke;
/* 116 */     int j = localBasicStroke.getType();
/* 117 */     if (j == 1) {
/* 118 */       if (localMode == PGShape.Mode.STROKE_FILL) {
/* 119 */         f1 = 0.0F;
/*     */       }
/* 121 */       else if (localBasicStroke.getDashArray() == null) {
/* 122 */         f1 = 0.0F;
/* 123 */         f2 = localBasicStroke.getLineWidth();
/*     */       } else {
/* 125 */         i = 1;
/*     */       }
/*     */     }
/* 128 */     else if (j == 2) {
/* 129 */       if (hasRightAngleMiterAndNoDashes(localBasicStroke)) {
/* 130 */         f1 = localBasicStroke.getLineWidth();
/* 131 */         if (localMode == PGShape.Mode.STROKE)
/* 132 */           f2 = 0.0F;
/*     */       }
/*     */       else {
/* 135 */         if (localMode == PGShape.Mode.STROKE_FILL) {
/* 136 */           f1 = 0.0F;
/*     */         }
/* 138 */         i = 1;
/*     */       }
/* 140 */     } else if (j == 0) {
/* 141 */       if (hasRightAngleMiterAndNoDashes(localBasicStroke)) {
/* 142 */         f1 = localBasicStroke.getLineWidth() / 2.0F;
/* 143 */         if (localMode == PGShape.Mode.STROKE)
/* 144 */           f2 = f1;
/*     */       }
/*     */       else {
/* 147 */         if (localMode == PGShape.Mode.STROKE_FILL) {
/* 148 */           f1 = 0.0F;
/*     */         }
/* 150 */         i = 1;
/*     */       }
/*     */     }
/*     */     else {
/* 154 */       if (localMode == PGShape.Mode.STROKE_FILL) {
/* 155 */         f1 = 0.0F;
/*     */       }
/* 157 */       i = 1;
/*     */     }
/* 159 */     if ((f1 >= 0.0F) && 
/* 160 */       (paramFloat1 >= d3 - f1) && (paramFloat2 >= d4 - f1) && (paramFloat1 < d3 + d1 + f1) && (paramFloat2 < d4 + d2 + f1))
/*     */     {
/* 163 */       if ((f2 >= 0.0F) && (f2 < d1 / 2.0D) && (f2 < d2 / 2.0D) && (paramFloat1 >= d3 + f2) && (paramFloat2 >= d4 + f2) && (paramFloat1 < d3 + d1 - f2) && (paramFloat2 < d4 + d2 - f2))
/*     */       {
/* 171 */         return false;
/*     */       }
/* 173 */       return true;
/*     */     }
/*     */ 
/* 176 */     if (i != 0) {
/* 177 */       return paramNGShape.getStrokeShape().contains(paramFloat1, paramFloat2);
/*     */     }
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   final boolean isRectClip(BaseTransform paramBaseTransform)
/*     */   {
/* 198 */     if ((this.mode != PGShape.Mode.FILL) || (getClipNode() != null) || (getEffect() != null) || (getOpacity() < 1.0F) || (isRounded()) || (!this.fillPaint.isOpaque()))
/*     */     {
/* 201 */       return false;
/*     */     }
/*     */ 
/* 204 */     BaseTransform localBaseTransform = getTransform();
/* 205 */     if (!localBaseTransform.isIdentity())
/*     */     {
/* 208 */       if (!paramBaseTransform.isIdentity()) {
/* 209 */         TMP_FXTX.setTransform(paramBaseTransform);
/* 210 */         TMP_FXTX.concatenate(localBaseTransform);
/* 211 */         paramBaseTransform = TMP_FXTX;
/*     */       } else {
/* 213 */         paramBaseTransform = localBaseTransform;
/*     */       }
/*     */     }
/*     */ 
/* 217 */     long l = paramBaseTransform.getType();
/* 218 */     return (l & 0xFFFFFFF0) == 0L;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGRectangle
 * JD-Core Version:    0.6.2
 */