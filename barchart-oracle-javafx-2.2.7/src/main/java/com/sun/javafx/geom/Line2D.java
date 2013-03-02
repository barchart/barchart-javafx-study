/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class Line2D extends Shape
/*     */ {
/*     */   public float x1;
/*     */   public float y1;
/*     */   public float x2;
/*     */   public float y2;
/*     */ 
/*     */   public Line2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Line2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  76 */     setLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public Line2D(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/*  86 */     setLine(paramPoint2D1, paramPoint2D2);
/*     */   }
/*     */ 
/*     */   public void setLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  98 */     this.x1 = paramFloat1;
/*  99 */     this.y1 = paramFloat2;
/* 100 */     this.x2 = paramFloat3;
/* 101 */     this.y2 = paramFloat4;
/*     */   }
/*     */ 
/*     */   public void setLine(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 112 */     setLine(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y);
/*     */   }
/*     */ 
/*     */   public void setLine(Line2D paramLine2D)
/*     */   {
/* 122 */     setLine(paramLine2D.x1, paramLine2D.y1, paramLine2D.x2, paramLine2D.y2);
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 129 */     RectBounds localRectBounds = new RectBounds();
/* 130 */     localRectBounds.setBoundsAndSort(this.x1, this.y1, this.x2, this.y2);
/* 131 */     return localRectBounds;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/* 150 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*     */     int j;
/* 158 */     if ((j = outcode(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.x2, this.y2)) == 0) {
/* 159 */       return true;
/*     */     }
/* 161 */     float f1 = this.x1;
/* 162 */     float f2 = this.y1;
/*     */     int i;
/* 163 */     while ((i = outcode(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f1, f2)) != 0) {
/* 164 */       if ((i & j) != 0) {
/* 165 */         return false;
/*     */       }
/* 167 */       if ((i & 0x5) != 0) {
/* 168 */         f1 = paramFloat1;
/* 169 */         if ((i & 0x4) != 0) {
/* 170 */           f1 += paramFloat3;
/*     */         }
/* 172 */         f2 = this.y1 + (f1 - this.x1) * (this.y2 - this.y1) / (this.x2 - this.x1);
/*     */       } else {
/* 174 */         f2 = paramFloat2;
/* 175 */         if ((i & 0x8) != 0) {
/* 176 */           f2 += paramFloat4;
/*     */         }
/* 178 */         f1 = this.x1 + (f2 - this.y1) * (this.x2 - this.x1) / (this.y2 - this.y1);
/*     */       }
/*     */     }
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   public static int relativeCCW(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 229 */     paramFloat3 -= paramFloat1;
/* 230 */     paramFloat4 -= paramFloat2;
/* 231 */     paramFloat5 -= paramFloat1;
/* 232 */     paramFloat6 -= paramFloat2;
/* 233 */     float f = paramFloat5 * paramFloat4 - paramFloat6 * paramFloat3;
/* 234 */     if (f == 0.0F)
/*     */     {
/* 241 */       f = paramFloat5 * paramFloat3 + paramFloat6 * paramFloat4;
/* 242 */       if (f > 0.0F)
/*     */       {
/* 250 */         paramFloat5 -= paramFloat3;
/* 251 */         paramFloat6 -= paramFloat4;
/* 252 */         f = paramFloat5 * paramFloat3 + paramFloat6 * paramFloat4;
/* 253 */         if (f < 0.0F) {
/* 254 */           f = 0.0F;
/*     */         }
/*     */       }
/*     */     }
/* 258 */     return f > 0.0F ? 1 : f < 0.0F ? -1 : 0;
/*     */   }
/*     */ 
/*     */   public int relativeCCW(float paramFloat1, float paramFloat2)
/*     */   {
/* 277 */     return relativeCCW(this.x1, this.y1, this.x2, this.y2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public int relativeCCW(Point2D paramPoint2D)
/*     */   {
/* 293 */     return relativeCCW(this.x1, this.y1, this.x2, this.y2, paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public static boolean linesIntersect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 327 */     return (relativeCCW(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6) * relativeCCW(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8) <= 0) && (relativeCCW(paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat1, paramFloat2) * relativeCCW(paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat3, paramFloat4) <= 0);
/*     */   }
/*     */ 
/*     */   public boolean intersectsLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 350 */     return linesIntersect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.x1, this.y1, this.x2, this.y2);
/*     */   }
/*     */ 
/*     */   public boolean intersectsLine(Line2D paramLine2D)
/*     */   {
/* 362 */     return linesIntersect(paramLine2D.x1, paramLine2D.y1, paramLine2D.x2, paramLine2D.y2, this.x1, this.y1, this.x2, this.y2);
/*     */   }
/*     */ 
/*     */   public static float ptSegDistSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 394 */     paramFloat3 -= paramFloat1;
/* 395 */     paramFloat4 -= paramFloat2;
/*     */ 
/* 397 */     paramFloat5 -= paramFloat1;
/* 398 */     paramFloat6 -= paramFloat2;
/* 399 */     float f1 = paramFloat5 * paramFloat3 + paramFloat6 * paramFloat4;
/*     */     float f2;
/* 401 */     if (f1 <= 0.0F)
/*     */     {
/* 405 */       f2 = 0.0F;
/*     */     }
/*     */     else
/*     */     {
/* 412 */       paramFloat5 = paramFloat3 - paramFloat5;
/* 413 */       paramFloat6 = paramFloat4 - paramFloat6;
/* 414 */       f1 = paramFloat5 * paramFloat3 + paramFloat6 * paramFloat4;
/* 415 */       if (f1 <= 0.0F)
/*     */       {
/* 419 */         f2 = 0.0F;
/*     */       }
/*     */       else
/*     */       {
/* 425 */         f2 = f1 * f1 / (paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 432 */     float f3 = paramFloat5 * paramFloat5 + paramFloat6 * paramFloat6 - f2;
/* 433 */     if (f3 < 0.0F) {
/* 434 */       f3 = 0.0F;
/*     */     }
/* 436 */     return f3;
/*     */   }
/*     */ 
/*     */   public static float ptSegDist(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 466 */     return (float)Math.sqrt(ptSegDistSq(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6));
/*     */   }
/*     */ 
/*     */   public float ptSegDistSq(float paramFloat1, float paramFloat2)
/*     */   {
/* 485 */     return ptSegDistSq(this.x1, this.y1, this.x2, this.y2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public float ptSegDistSq(Point2D paramPoint2D)
/*     */   {
/* 504 */     return ptSegDistSq(this.x1, this.y1, this.x2, this.y2, paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public double ptSegDist(float paramFloat1, float paramFloat2)
/*     */   {
/* 524 */     return ptSegDist(this.x1, this.y1, this.x2, this.y2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public float ptSegDist(Point2D paramPoint2D)
/*     */   {
/* 542 */     return ptSegDist(this.x1, this.y1, this.x2, this.y2, paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public static float ptLineDistSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 570 */     paramFloat3 -= paramFloat1;
/* 571 */     paramFloat4 -= paramFloat2;
/*     */ 
/* 573 */     paramFloat5 -= paramFloat1;
/* 574 */     paramFloat6 -= paramFloat2;
/* 575 */     float f1 = paramFloat5 * paramFloat3 + paramFloat6 * paramFloat4;
/*     */ 
/* 579 */     float f2 = f1 * f1 / (paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4);
/*     */ 
/* 582 */     float f3 = paramFloat5 * paramFloat5 + paramFloat6 * paramFloat6 - f2;
/* 583 */     if (f3 < 0.0F) {
/* 584 */       f3 = 0.0F;
/*     */     }
/* 586 */     return f3;
/*     */   }
/*     */ 
/*     */   public static float ptLineDist(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 612 */     return (float)Math.sqrt(ptLineDistSq(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6));
/*     */   }
/*     */ 
/*     */   public float ptLineDistSq(float paramFloat1, float paramFloat2)
/*     */   {
/* 632 */     return ptLineDistSq(this.x1, this.y1, this.x2, this.y2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public float ptLineDistSq(Point2D paramPoint2D)
/*     */   {
/* 650 */     return ptLineDistSq(this.x1, this.y1, this.x2, this.y2, paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public float ptLineDist(float paramFloat1, float paramFloat2)
/*     */   {
/* 670 */     return ptLineDist(this.x1, this.y1, this.x2, this.y2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public float ptLineDist(Point2D paramPoint2D)
/*     */   {
/* 686 */     return ptLineDist(this.x1, this.y1, this.x2, this.y2, paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 702 */     return new LineIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 723 */     return new LineIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public Line2D copy()
/*     */   {
/* 728 */     return new Line2D(this.x1, this.y1, this.x2, this.y2);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 733 */     int i = Float.floatToIntBits(this.x1);
/* 734 */     i += Float.floatToIntBits(this.y1) * 37;
/* 735 */     i += Float.floatToIntBits(this.x2) * 43;
/* 736 */     i += Float.floatToIntBits(this.y2) * 47;
/* 737 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 742 */     if (paramObject == this) {
/* 743 */       return true;
/*     */     }
/* 745 */     if ((paramObject instanceof Line2D)) {
/* 746 */       Line2D localLine2D = (Line2D)paramObject;
/* 747 */       return (this.x1 == localLine2D.x1) && (this.y1 == localLine2D.y1) && (this.x2 == localLine2D.x2) && (this.y2 == localLine2D.y2);
/*     */     }
/*     */ 
/* 750 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Line2D
 * JD-Core Version:    0.6.2
 */