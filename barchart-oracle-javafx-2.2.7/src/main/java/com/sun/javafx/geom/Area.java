/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class Area extends Shape
/*     */ {
/*  98 */   private static final Vector EmptyCurves = new Vector();
/*     */   private Vector curves;
/*     */   private RectBounds cachedBounds;
/*     */ 
/*     */   public Area()
/*     */   {
/* 107 */     this.curves = EmptyCurves;
/*     */   }
/*     */ 
/*     */   public Area(Shape paramShape)
/*     */   {
/* 121 */     if ((paramShape instanceof Area))
/* 122 */       this.curves = ((Area)paramShape).curves;
/*     */     else
/* 124 */       this.curves = pathToCurves(paramShape.getPathIterator(null));
/*     */   }
/*     */ 
/*     */   public Area(PathIterator paramPathIterator)
/*     */   {
/* 129 */     this.curves = pathToCurves(paramPathIterator);
/*     */   }
/*     */ 
/*     */   private static Vector pathToCurves(PathIterator paramPathIterator) {
/* 133 */     Vector localVector = new Vector();
/* 134 */     int i = paramPathIterator.getWindingRule();
/*     */ 
/* 149 */     float[] arrayOfFloat = new float[6];
/* 150 */     double[] arrayOfDouble = new double[23];
/* 151 */     double d1 = 0.0D; double d2 = 0.0D;
/* 152 */     double d3 = 0.0D; double d4 = 0.0D;
/*     */ 
/* 154 */     while (!paramPathIterator.isDone())
/*     */     {
/*     */       double d5;
/*     */       double d6;
/* 155 */       switch (paramPathIterator.currentSegment(arrayOfFloat)) {
/*     */       case 0:
/* 157 */         Curve.insertLine(localVector, d3, d4, d1, d2);
/* 158 */         d3 = d1 = arrayOfFloat[0];
/* 159 */         d4 = d2 = arrayOfFloat[1];
/* 160 */         Curve.insertMove(localVector, d1, d2);
/* 161 */         break;
/*     */       case 1:
/* 163 */         d5 = arrayOfFloat[0];
/* 164 */         d6 = arrayOfFloat[1];
/* 165 */         Curve.insertLine(localVector, d3, d4, d5, d6);
/* 166 */         d3 = d5;
/* 167 */         d4 = d6;
/* 168 */         break;
/*     */       case 2:
/* 170 */         d5 = arrayOfFloat[2];
/* 171 */         d6 = arrayOfFloat[3];
/* 172 */         Curve.insertQuad(localVector, arrayOfDouble, d3, d4, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */ 
/* 176 */         d3 = d5;
/* 177 */         d4 = d6;
/* 178 */         break;
/*     */       case 3:
/* 180 */         d5 = arrayOfFloat[4];
/* 181 */         d6 = arrayOfFloat[5];
/* 182 */         Curve.insertCubic(localVector, arrayOfDouble, d3, d4, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5]);
/*     */ 
/* 187 */         d3 = d5;
/* 188 */         d4 = d6;
/* 189 */         break;
/*     */       case 4:
/* 191 */         Curve.insertLine(localVector, d3, d4, d1, d2);
/* 192 */         d3 = d1;
/* 193 */         d4 = d2;
/*     */       }
/*     */ 
/* 196 */       paramPathIterator.next();
/*     */     }
/* 198 */     Curve.insertLine(localVector, d3, d4, d1, d2);
/*     */     Object localObject;
/* 200 */     if (i == 0)
/* 201 */       localObject = new AreaOp.EOWindOp();
/*     */     else {
/* 203 */       localObject = new AreaOp.NZWindOp();
/*     */     }
/* 205 */     return ((AreaOp)localObject).calculate(localVector, EmptyCurves);
/*     */   }
/*     */ 
/*     */   public void add(Area paramArea)
/*     */   {
/* 237 */     this.curves = new AreaOp.AddOp().calculate(this.curves, paramArea.curves);
/* 238 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public void subtract(Area paramArea)
/*     */   {
/* 270 */     this.curves = new AreaOp.SubOp().calculate(this.curves, paramArea.curves);
/* 271 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public void intersect(Area paramArea)
/*     */   {
/* 303 */     this.curves = new AreaOp.IntOp().calculate(this.curves, paramArea.curves);
/* 304 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public void exclusiveOr(Area paramArea)
/*     */   {
/* 337 */     this.curves = new AreaOp.XorOp().calculate(this.curves, paramArea.curves);
/* 338 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 347 */     this.curves = new Vector();
/* 348 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 358 */     return this.curves.size() == 0;
/*     */   }
/*     */ 
/*     */   public boolean isPolygonal()
/*     */   {
/* 370 */     Enumeration localEnumeration = this.curves.elements();
/* 371 */     while (localEnumeration.hasMoreElements()) {
/* 372 */       if (((Curve)localEnumeration.nextElement()).getOrder() > 1) {
/* 373 */         return false;
/*     */       }
/*     */     }
/* 376 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isRectangular()
/*     */   {
/* 387 */     int i = this.curves.size();
/* 388 */     if (i == 0) {
/* 389 */       return true;
/*     */     }
/* 391 */     if (i > 3) {
/* 392 */       return false;
/*     */     }
/* 394 */     Curve localCurve1 = (Curve)this.curves.get(1);
/* 395 */     Curve localCurve2 = (Curve)this.curves.get(2);
/* 396 */     if ((localCurve1.getOrder() != 1) || (localCurve2.getOrder() != 1)) {
/* 397 */       return false;
/*     */     }
/* 399 */     if ((localCurve1.getXTop() != localCurve1.getXBot()) || (localCurve2.getXTop() != localCurve2.getXBot())) {
/* 400 */       return false;
/*     */     }
/* 402 */     if ((localCurve1.getYTop() != localCurve2.getYTop()) || (localCurve1.getYBot() != localCurve2.getYBot()))
/*     */     {
/* 404 */       return false;
/*     */     }
/* 406 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isSingular()
/*     */   {
/* 421 */     if (this.curves.size() < 3) {
/* 422 */       return true;
/*     */     }
/* 424 */     Enumeration localEnumeration = this.curves.elements();
/* 425 */     localEnumeration.nextElement();
/* 426 */     while (localEnumeration.hasMoreElements()) {
/* 427 */       if (((Curve)localEnumeration.nextElement()).getOrder() == 0) {
/* 428 */         return false;
/*     */       }
/*     */     }
/* 431 */     return true;
/*     */   }
/*     */ 
/*     */   private void invalidateBounds()
/*     */   {
/* 436 */     this.cachedBounds = null;
/*     */   }
/*     */   private RectBounds getCachedBounds() {
/* 439 */     if (this.cachedBounds != null) {
/* 440 */       return this.cachedBounds;
/*     */     }
/* 442 */     RectBounds localRectBounds = new RectBounds();
/* 443 */     if (this.curves.size() > 0) {
/* 444 */       Curve localCurve = (Curve)this.curves.get(0);
/*     */ 
/* 446 */       localRectBounds.setBounds((float)localCurve.getX0(), (float)localCurve.getY0(), 0.0F, 0.0F);
/* 447 */       for (int i = 1; i < this.curves.size(); i++) {
/* 448 */         ((Curve)this.curves.get(i)).enlarge(localRectBounds);
/*     */       }
/*     */     }
/* 451 */     return this.cachedBounds = localRectBounds;
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 468 */     return new RectBounds(getCachedBounds());
/*     */   }
/*     */ 
/*     */   public boolean equals(Area paramArea)
/*     */   {
/* 485 */     if (paramArea == this) {
/* 486 */       return true;
/*     */     }
/* 488 */     if (paramArea == null) {
/* 489 */       return false;
/*     */     }
/* 491 */     Vector localVector = new AreaOp.XorOp().calculate(this.curves, paramArea.curves);
/* 492 */     return localVector.isEmpty();
/*     */   }
/*     */ 
/*     */   public void transform(BaseTransform paramBaseTransform)
/*     */   {
/* 504 */     if (paramBaseTransform == null) {
/* 505 */       throw new NullPointerException("transform must not be null");
/*     */     }
/*     */ 
/* 509 */     this.curves = pathToCurves(getPathIterator(paramBaseTransform));
/* 510 */     invalidateBounds();
/*     */   }
/*     */ 
/*     */   public Area createTransformedArea(BaseTransform paramBaseTransform)
/*     */   {
/* 526 */     Area localArea = new Area(this);
/* 527 */     localArea.transform(paramBaseTransform);
/* 528 */     return localArea;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 536 */     if (!getCachedBounds().contains(paramFloat1, paramFloat2)) {
/* 537 */       return false;
/*     */     }
/* 539 */     Enumeration localEnumeration = this.curves.elements();
/* 540 */     int i = 0;
/* 541 */     while (localEnumeration.hasMoreElements()) {
/* 542 */       Curve localCurve = (Curve)localEnumeration.nextElement();
/* 543 */       i += localCurve.crossingsFor(paramFloat1, paramFloat2);
/*     */     }
/* 545 */     return (i & 0x1) == 1;
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/* 554 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 562 */     if ((paramFloat3 < 0.0F) || (paramFloat4 < 0.0F)) {
/* 563 */       return false;
/*     */     }
/* 565 */     if (!getCachedBounds().contains(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) {
/* 566 */       return false;
/*     */     }
/* 568 */     Crossings localCrossings = Crossings.findCrossings(this.curves, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 569 */     return (localCrossings != null) && (localCrossings.covers(paramFloat2, paramFloat2 + paramFloat4));
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 577 */     if ((paramFloat3 < 0.0F) || (paramFloat4 < 0.0F)) {
/* 578 */       return false;
/*     */     }
/* 580 */     if (!getCachedBounds().intersects(paramFloat1, paramFloat2, paramFloat3, paramFloat4)) {
/* 581 */       return false;
/*     */     }
/* 583 */     Crossings localCrossings = Crossings.findCrossings(this.curves, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/* 584 */     return (localCrossings == null) || (!localCrossings.isEmpty());
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 599 */     return new AreaIterator(this.curves, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 621 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*     */   }
/*     */ 
/*     */   public Area copy()
/*     */   {
/* 626 */     return new Area(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Area
 * JD-Core Version:    0.6.2
 */