/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.RectangularShape;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.PGShape;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.sg.PGShape.StrokeType;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.shape.ShapeRep;
/*     */ import com.sun.prism.shape.ShapeRep.InvalidationType;
/*     */ 
/*     */ public abstract class NGShape extends NGNode
/*     */   implements PGShape
/*     */ {
/*     */   protected Paint fillPaint;
/*     */   protected Paint drawPaint;
/*     */   protected BasicStroke drawStroke;
/*  30 */   protected PGShape.Mode mode = PGShape.Mode.FILL;
/*     */   protected ShapeRep shapeRep;
/* 405 */   private static final int[] coordsPerSeg = { 2, 2, 4, 6, 0 };
/*     */   static final int AT_IDENT = 0;
/*     */   static final int AT_TRANS = 1;
/*     */   static final int AT_GENERAL = 2;
/*     */ 
/*     */   public void setMode(PGShape.Mode paramMode)
/*     */   {
/*  35 */     if (paramMode != this.mode) {
/*  36 */       this.mode = paramMode;
/*  37 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setAntialiased(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setFillPaint(Object paramObject)
/*     */   {
/*  50 */     if (paramObject != this.fillPaint) {
/*  51 */       this.fillPaint = ((Paint)paramObject);
/*  52 */       visualsChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDrawPaint(Object paramObject) {
/*  57 */     if (paramObject != this.drawPaint) {
/*  58 */       this.drawPaint = ((Paint)paramObject);
/*  59 */       visualsChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDrawStroke(BasicStroke paramBasicStroke) {
/*  64 */     if (this.drawStroke != paramBasicStroke) {
/*  65 */       this.drawStroke = paramBasicStroke;
/*  66 */       geometryChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static BasicStroke createDrawStroke(float paramFloat1, PGShape.StrokeType paramStrokeType, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat2, float[] paramArrayOfFloat, float paramFloat3)
/*     */   {
/*     */     int i;
/*  78 */     if (paramStrokeType == PGShape.StrokeType.CENTERED)
/*  79 */       i = 0;
/*  80 */     else if (paramStrokeType == PGShape.StrokeType.INSIDE)
/*  81 */       i = 1;
/*     */     else
/*  83 */       i = 2;
/*     */     int j;
/*  87 */     if (paramStrokeLineCap == PGShape.StrokeLineCap.BUTT)
/*  88 */       j = 0;
/*  89 */     else if (paramStrokeLineCap == PGShape.StrokeLineCap.SQUARE)
/*  90 */       j = 2;
/*     */     else
/*  92 */       j = 1;
/*     */     int k;
/*  96 */     if (paramStrokeLineJoin == PGShape.StrokeLineJoin.BEVEL)
/*  97 */       k = 2;
/*  98 */     else if (paramStrokeLineJoin == PGShape.StrokeLineJoin.MITER)
/*  99 */       k = 0;
/*     */     else {
/* 101 */       k = 1;
/*     */     }
/*     */ 
/* 104 */     BasicStroke localBasicStroke = new BasicStroke(i, paramFloat1, j, k, paramFloat2);
/*     */ 
/* 107 */     if (paramArrayOfFloat.length > 0)
/* 108 */       localBasicStroke.set(paramArrayOfFloat, paramFloat3);
/*     */     else {
/* 110 */       localBasicStroke.set(null, 0.0F);
/*     */     }
/* 112 */     return localBasicStroke;
/*     */   }
/*     */ 
/*     */   public void setDrawStroke(float paramFloat1, PGShape.StrokeType paramStrokeType, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat2, float[] paramArrayOfFloat, float paramFloat3)
/*     */   {
/*     */     int i;
/* 122 */     if (paramStrokeType == PGShape.StrokeType.CENTERED)
/* 123 */       i = 0;
/* 124 */     else if (paramStrokeType == PGShape.StrokeType.INSIDE)
/* 125 */       i = 1;
/*     */     else
/* 127 */       i = 2;
/*     */     int j;
/* 131 */     if (paramStrokeLineCap == PGShape.StrokeLineCap.BUTT)
/* 132 */       j = 0;
/* 133 */     else if (paramStrokeLineCap == PGShape.StrokeLineCap.SQUARE)
/* 134 */       j = 2;
/*     */     else
/* 136 */       j = 1;
/*     */     int k;
/* 140 */     if (paramStrokeLineJoin == PGShape.StrokeLineJoin.BEVEL)
/* 141 */       k = 2;
/* 142 */     else if (paramStrokeLineJoin == PGShape.StrokeLineJoin.MITER)
/* 143 */       k = 0;
/*     */     else {
/* 145 */       k = 1;
/*     */     }
/*     */ 
/* 148 */     if (this.drawStroke == null)
/* 149 */       this.drawStroke = new BasicStroke(i, paramFloat1, j, k, paramFloat2);
/*     */     else {
/* 151 */       this.drawStroke.set(i, paramFloat1, j, k, paramFloat2);
/*     */     }
/* 153 */     if (paramArrayOfFloat.length > 0)
/* 154 */       this.drawStroke.set(paramArrayOfFloat, paramFloat3);
/*     */     else {
/* 156 */       this.drawStroke.set(null, 0.0F);
/*     */     }
/*     */ 
/* 159 */     geometryChanged();
/*     */   }
/*     */ 
/*     */   public abstract Shape getShape();
/*     */ 
/*     */   protected ShapeRep createShapeRep(Graphics paramGraphics, boolean paramBoolean) {
/* 165 */     return paramGraphics.getResourceFactory().createPathRep(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void renderContent(Graphics paramGraphics)
/*     */   {
/* 170 */     if (this.mode == PGShape.Mode.EMPTY) {
/* 171 */       return;
/*     */     }
/* 173 */     boolean bool = !paramGraphics.getTransformNoClone().is2D();
/* 174 */     if (this.shapeRep == null) {
/* 175 */       this.shapeRep = createShapeRep(paramGraphics, bool);
/* 176 */     } else if ((bool) && (!this.shapeRep.is3DCapable()))
/*     */     {
/* 179 */       this.shapeRep.dispose();
/* 180 */       this.shapeRep = createShapeRep(paramGraphics, true);
/*     */     }
/* 182 */     Shape localShape = getShape();
/* 183 */     if (this.mode != PGShape.Mode.STROKE) {
/* 184 */       paramGraphics.setPaint(this.fillPaint);
/* 185 */       this.shapeRep.fill(paramGraphics, localShape);
/*     */     }
/* 187 */     if ((this.mode != PGShape.Mode.FILL) && (this.drawStroke.getLineWidth() > 0.0F)) {
/* 188 */       paramGraphics.setPaint(this.drawPaint);
/* 189 */       paramGraphics.setStroke(this.drawStroke);
/* 190 */       this.shapeRep.draw(paramGraphics, localShape);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean hasOverlappingContents()
/*     */   {
/* 196 */     return this.mode == PGShape.Mode.STROKE_FILL;
/*     */   }
/*     */ 
/*     */   protected Shape getStrokeShape() {
/* 200 */     return this.drawStroke.createStrokedShape(getShape());
/*     */   }
/*     */ 
/*     */   protected void geometryChanged()
/*     */   {
/* 206 */     super.geometryChanged();
/* 207 */     if (this.shapeRep != null)
/* 208 */       this.shapeRep.invalidate(ShapeRep.InvalidationType.LOCATION_AND_GEOMETRY);
/*     */   }
/*     */ 
/*     */   void locationChanged()
/*     */   {
/* 213 */     super.geometryChanged();
/* 214 */     if (this.shapeRep != null)
/* 215 */       this.shapeRep.invalidate(ShapeRep.InvalidationType.LOCATION);
/*     */   }
/*     */ 
/*     */   static BaseBounds getRectShapeBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, int paramInt, float paramFloat1, float paramFloat2, RectBounds paramRectBounds)
/*     */   {
/* 224 */     if (paramBaseBounds == null) {
/* 225 */       paramBaseBounds = new RectBounds();
/*     */     }
/* 227 */     float f1 = paramRectBounds.getWidth();
/* 228 */     float f2 = paramRectBounds.getHeight();
/* 229 */     if ((f1 < 0.0F) || (f2 < 0.0F)) {
/* 230 */       return paramBaseBounds.makeEmpty();
/*     */     }
/* 232 */     float f3 = paramRectBounds.getMinX();
/* 233 */     float f4 = paramRectBounds.getMinY();
/*     */     float f5;
/*     */     float f6;
/* 234 */     if (paramInt <= 1) {
/* 235 */       f1 += f3;
/* 236 */       f2 += f4;
/* 237 */       if (paramInt == 1) {
/* 238 */         f5 = (float)paramBaseTransform.getMxt();
/* 239 */         f6 = (float)paramBaseTransform.getMyt();
/* 240 */         f3 += f5;
/* 241 */         f4 += f6;
/* 242 */         f1 += f5;
/* 243 */         f2 += f6;
/*     */       }
/*     */ 
/* 246 */       paramFloat2 += paramFloat1;
/*     */     }
/*     */     else {
/* 249 */       f3 -= paramFloat1;
/* 250 */       f4 -= paramFloat1;
/* 251 */       f1 += paramFloat1 * 2.0F;
/* 252 */       f2 += paramFloat1 * 2.0F;
/*     */ 
/* 288 */       f5 = (float)paramBaseTransform.getMxx();
/* 289 */       f6 = (float)paramBaseTransform.getMxy();
/* 290 */       float f7 = (float)paramBaseTransform.getMyx();
/* 291 */       float f8 = (float)paramBaseTransform.getMyy();
/*     */ 
/* 293 */       float f9 = f3 * f5 + f4 * f6 + (float)paramBaseTransform.getMxt();
/* 294 */       float f10 = f3 * f7 + f4 * f8 + (float)paramBaseTransform.getMyt();
/*     */ 
/* 296 */       f5 *= f1;
/* 297 */       f6 *= f2;
/* 298 */       f7 *= f1;
/* 299 */       f8 *= f2;
/* 300 */       f3 = Math.min(Math.min(0.0F, f5), Math.min(f6, f5 + f6)) + f9;
/* 301 */       f4 = Math.min(Math.min(0.0F, f7), Math.min(f8, f7 + f8)) + f10;
/* 302 */       f1 = Math.max(Math.max(0.0F, f5), Math.max(f6, f5 + f6)) + f9;
/* 303 */       f2 = Math.max(Math.max(0.0F, f7), Math.max(f8, f7 + f8)) + f10;
/*     */     }
/* 305 */     f3 -= paramFloat2;
/* 306 */     f4 -= paramFloat2;
/* 307 */     f1 += paramFloat2;
/* 308 */     f2 += paramFloat2;
/* 309 */     return paramBaseBounds.deriveWithNewBounds(f3, f4, 0.0F, f1, f2, 0.0F);
/*     */   }
/*     */ 
/*     */   static BaseBounds getRectShapeBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, int paramInt, float paramFloat1, float paramFloat2, RectangularShape paramRectangularShape)
/*     */   {
/* 317 */     if (paramBaseBounds == null) {
/* 318 */       paramBaseBounds = new RectBounds();
/*     */     }
/* 320 */     float f1 = paramRectangularShape.getWidth();
/* 321 */     float f2 = paramRectangularShape.getHeight();
/* 322 */     if ((f1 < 0.0F) || (f2 < 0.0F)) {
/* 323 */       return paramBaseBounds.makeEmpty();
/*     */     }
/* 325 */     float f3 = paramRectangularShape.getX();
/* 326 */     float f4 = paramRectangularShape.getY();
/*     */     float f5;
/*     */     float f6;
/* 327 */     if (paramInt <= 1) {
/* 328 */       f1 += f3;
/* 329 */       f2 += f4;
/* 330 */       if (paramInt == 1) {
/* 331 */         f5 = (float)paramBaseTransform.getMxt();
/* 332 */         f6 = (float)paramBaseTransform.getMyt();
/* 333 */         f3 += f5;
/* 334 */         f4 += f6;
/* 335 */         f1 += f5;
/* 336 */         f2 += f6;
/*     */       }
/*     */ 
/* 339 */       paramFloat2 += paramFloat1;
/*     */     }
/*     */     else {
/* 342 */       f3 -= paramFloat1;
/* 343 */       f4 -= paramFloat1;
/* 344 */       f1 += paramFloat1 * 2.0F;
/* 345 */       f2 += paramFloat1 * 2.0F;
/*     */ 
/* 381 */       f5 = (float)paramBaseTransform.getMxx();
/* 382 */       f6 = (float)paramBaseTransform.getMxy();
/* 383 */       float f7 = (float)paramBaseTransform.getMyx();
/* 384 */       float f8 = (float)paramBaseTransform.getMyy();
/*     */ 
/* 386 */       float f9 = f3 * f5 + f4 * f6 + (float)paramBaseTransform.getMxt();
/* 387 */       float f10 = f3 * f7 + f4 * f8 + (float)paramBaseTransform.getMyt();
/*     */ 
/* 389 */       f5 *= f1;
/* 390 */       f6 *= f2;
/* 391 */       f7 *= f1;
/* 392 */       f8 *= f2;
/* 393 */       f3 = Math.min(Math.min(0.0F, f5), Math.min(f6, f5 + f6)) + f9;
/* 394 */       f4 = Math.min(Math.min(0.0F, f7), Math.min(f8, f7 + f8)) + f10;
/* 395 */       f1 = Math.max(Math.max(0.0F, f5), Math.max(f6, f5 + f6)) + f9;
/* 396 */       f2 = Math.max(Math.max(0.0F, f7), Math.max(f8, f7 + f8)) + f10;
/*     */     }
/* 398 */     f3 -= paramFloat2;
/* 399 */     f4 -= paramFloat2;
/* 400 */     f1 += paramFloat2;
/* 401 */     f2 += paramFloat2;
/* 402 */     return paramBaseBounds.deriveWithNewBounds(f3, f4, 0.0F, f1, f2, 0.0F);
/*     */   }
/*     */ 
/*     */   private static void accumulate(float[] paramArrayOfFloat, Shape paramShape, BaseTransform paramBaseTransform)
/*     */   {
/* 407 */     if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity()))
/*     */     {
/* 410 */       localObject = paramShape.getBounds();
/* 411 */       if (paramArrayOfFloat[0] > ((RectBounds)localObject).getMinX()) paramArrayOfFloat[0] = ((RectBounds)localObject).getMinX();
/* 412 */       if (paramArrayOfFloat[1] > ((RectBounds)localObject).getMinY()) paramArrayOfFloat[1] = ((RectBounds)localObject).getMinY();
/* 413 */       if (paramArrayOfFloat[2] < ((RectBounds)localObject).getMaxX()) paramArrayOfFloat[2] = ((RectBounds)localObject).getMaxX();
/* 414 */       if (paramArrayOfFloat[3] < ((RectBounds)localObject).getMaxY()) paramArrayOfFloat[3] = ((RectBounds)localObject).getMaxY();
/* 415 */       return;
/*     */     }
/* 417 */     Object localObject = paramShape.getPathIterator(paramBaseTransform);
/* 418 */     float[] arrayOfFloat = new float[6];
/* 419 */     while (!((PathIterator)localObject).isDone()) {
/* 420 */       int i = coordsPerSeg[localObject.currentSegment(arrayOfFloat)];
/* 421 */       for (int j = 0; j < i; j++) {
/* 422 */         float f = arrayOfFloat[j];
/* 423 */         int k = j & 0x1;
/* 424 */         if (paramArrayOfFloat[(k + 0)] > f) paramArrayOfFloat[(k + 0)] = f;
/* 425 */         if (paramArrayOfFloat[(k + 2)] < f) paramArrayOfFloat[(k + 2)] = f;
/*     */       }
/* 427 */       ((PathIterator)localObject).next();
/*     */     }
/*     */   }
/*     */ 
/*     */   static int classify(BaseTransform paramBaseTransform)
/*     */   {
/* 435 */     if (paramBaseTransform == null) return 0;
/* 436 */     switch (paramBaseTransform.getType()) {
/*     */     case 0:
/* 438 */       return 0;
/*     */     case 1:
/* 440 */       return 1;
/*     */     }
/* 442 */     return 2;
/*     */   }
/*     */ 
/*     */   static boolean shapeContains(float paramFloat1, float paramFloat2, NGShape paramNGShape, Shape paramShape)
/*     */   {
/* 450 */     PGShape.Mode localMode = paramNGShape.mode;
/* 451 */     if (localMode == PGShape.Mode.EMPTY) {
/* 452 */       return false;
/*     */     }
/* 454 */     if ((localMode != PGShape.Mode.STROKE) && 
/* 455 */       (paramShape.contains(paramFloat1, paramFloat2))) {
/* 456 */       return true;
/*     */     }
/*     */ 
/* 459 */     if (localMode != PGShape.Mode.FILL) {
/* 460 */       return paramNGShape.getStrokeShape().contains(paramFloat1, paramFloat2);
/*     */     }
/* 462 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGShape
 * JD-Core Version:    0.6.2
 */