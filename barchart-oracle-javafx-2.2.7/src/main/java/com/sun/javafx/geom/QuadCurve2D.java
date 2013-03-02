/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class QuadCurve2D extends Shape
/*     */ {
/*     */   public float x1;
/*     */   public float y1;
/*     */   public float ctrlx;
/*     */   public float ctrly;
/*     */   public float x2;
/*     */   public float y2;
/*     */   private static final int BELOW = -2;
/*     */   private static final int LOWEDGE = -1;
/*     */   private static final int INSIDE = 0;
/*     */   private static final int HIGHEDGE = 1;
/*     */   private static final int ABOVE = 2;
/*     */ 
/*     */   public QuadCurve2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public QuadCurve2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 101 */     setCurve(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public void setCurve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 120 */     this.x1 = paramFloat1;
/* 121 */     this.y1 = paramFloat2;
/* 122 */     this.ctrlx = paramFloat3;
/* 123 */     this.ctrly = paramFloat4;
/* 124 */     this.x2 = paramFloat5;
/* 125 */     this.y2 = paramFloat6;
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 132 */     float f1 = Math.min(Math.min(this.x1, this.x2), this.ctrlx);
/* 133 */     float f2 = Math.min(Math.min(this.y1, this.y2), this.ctrly);
/* 134 */     float f3 = Math.max(Math.max(this.x1, this.x2), this.ctrlx);
/* 135 */     float f4 = Math.max(Math.max(this.y1, this.y2), this.ctrly);
/* 136 */     return new RectBounds(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public CubicCurve2D toCubic()
/*     */   {
/* 144 */     return new CubicCurve2D(this.x1, this.y1, (this.x1 + 2.0F * this.ctrlx) / 3.0F, (this.y1 + 2.0F * this.ctrly) / 3.0F, (2.0F * this.ctrlx + this.x2) / 3.0F, (2.0F * this.ctrly + this.y2) / 3.0F, this.x2, this.y2);
/*     */   }
/*     */ 
/*     */   public void setCurve(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 161 */     setCurve(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)]);
/*     */   }
/*     */ 
/*     */   public void setCurve(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3)
/*     */   {
/* 176 */     setCurve(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y, paramPoint2D3.x, paramPoint2D3.y);
/*     */   }
/*     */ 
/*     */   public void setCurve(Point2D[] paramArrayOfPoint2D, int paramInt)
/*     */   {
/* 192 */     setCurve(paramArrayOfPoint2D[(paramInt + 0)].x, paramArrayOfPoint2D[(paramInt + 0)].y, paramArrayOfPoint2D[(paramInt + 1)].x, paramArrayOfPoint2D[(paramInt + 1)].y, paramArrayOfPoint2D[(paramInt + 2)].x, paramArrayOfPoint2D[(paramInt + 2)].y);
/*     */   }
/*     */ 
/*     */   public void setCurve(QuadCurve2D paramQuadCurve2D)
/*     */   {
/* 205 */     setCurve(paramQuadCurve2D.x1, paramQuadCurve2D.y1, paramQuadCurve2D.ctrlx, paramQuadCurve2D.ctrly, paramQuadCurve2D.x2, paramQuadCurve2D.y2);
/*     */   }
/*     */ 
/*     */   public static float getFlatnessSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 226 */     return Line2D.ptSegDistSq(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public static float getFlatness(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 247 */     return Line2D.ptSegDist(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public static float getFlatnessSq(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 263 */     return Line2D.ptSegDistSq(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)]);
/*     */   }
/*     */ 
/*     */   public static float getFlatness(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 281 */     return Line2D.ptSegDist(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)]);
/*     */   }
/*     */ 
/*     */   public float getFlatnessSq()
/*     */   {
/* 295 */     return Line2D.ptSegDistSq(this.x1, this.y1, this.x2, this.y2, this.ctrlx, this.ctrly);
/*     */   }
/*     */ 
/*     */   public float getFlatness()
/*     */   {
/* 306 */     return Line2D.ptSegDist(this.x1, this.y1, this.x2, this.y2, this.ctrlx, this.ctrly);
/*     */   }
/*     */ 
/*     */   public void subdivide(QuadCurve2D paramQuadCurve2D1, QuadCurve2D paramQuadCurve2D2)
/*     */   {
/* 323 */     subdivide(this, paramQuadCurve2D1, paramQuadCurve2D2);
/*     */   }
/*     */ 
/*     */   public static void subdivide(QuadCurve2D paramQuadCurve2D1, QuadCurve2D paramQuadCurve2D2, QuadCurve2D paramQuadCurve2D3)
/*     */   {
/* 344 */     float f1 = paramQuadCurve2D1.x1;
/* 345 */     float f2 = paramQuadCurve2D1.y1;
/* 346 */     float f3 = paramQuadCurve2D1.ctrlx;
/* 347 */     float f4 = paramQuadCurve2D1.ctrly;
/* 348 */     float f5 = paramQuadCurve2D1.x2;
/* 349 */     float f6 = paramQuadCurve2D1.y2;
/* 350 */     float f7 = (f1 + f3) / 2.0F;
/* 351 */     float f8 = (f2 + f4) / 2.0F;
/* 352 */     float f9 = (f5 + f3) / 2.0F;
/* 353 */     float f10 = (f6 + f4) / 2.0F;
/* 354 */     f3 = (f7 + f9) / 2.0F;
/* 355 */     f4 = (f8 + f10) / 2.0F;
/* 356 */     if (paramQuadCurve2D2 != null) {
/* 357 */       paramQuadCurve2D2.setCurve(f1, f2, f7, f8, f3, f4);
/*     */     }
/* 359 */     if (paramQuadCurve2D3 != null)
/* 360 */       paramQuadCurve2D3.setCurve(f3, f4, f9, f10, f5, f6);
/*     */   }
/*     */ 
/*     */   public static void subdivide(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*     */   {
/* 396 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/* 397 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/* 398 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/* 399 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/* 400 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/* 401 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/* 402 */     if (paramArrayOfFloat2 != null) {
/* 403 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/* 404 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*     */     }
/* 406 */     if (paramArrayOfFloat3 != null) {
/* 407 */       paramArrayOfFloat3[(paramInt3 + 4)] = f5;
/* 408 */       paramArrayOfFloat3[(paramInt3 + 5)] = f6;
/*     */     }
/* 410 */     f1 = (f1 + f3) / 2.0F;
/* 411 */     f2 = (f2 + f4) / 2.0F;
/* 412 */     f5 = (f5 + f3) / 2.0F;
/* 413 */     f6 = (f6 + f4) / 2.0F;
/* 414 */     f3 = (f1 + f5) / 2.0F;
/* 415 */     f4 = (f2 + f6) / 2.0F;
/* 416 */     if (paramArrayOfFloat2 != null) {
/* 417 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/* 418 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/* 419 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/* 420 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/*     */     }
/* 422 */     if (paramArrayOfFloat3 != null) {
/* 423 */       paramArrayOfFloat3[(paramInt3 + 0)] = f3;
/* 424 */       paramArrayOfFloat3[(paramInt3 + 1)] = f4;
/* 425 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/* 426 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int solveQuadratic(float[] paramArrayOfFloat)
/*     */   {
/* 448 */     return solveQuadratic(paramArrayOfFloat, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   public static int solveQuadratic(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*     */   {
/* 472 */     float f1 = paramArrayOfFloat1[2];
/* 473 */     float f2 = paramArrayOfFloat1[1];
/* 474 */     float f3 = paramArrayOfFloat1[0];
/* 475 */     int i = 0;
/* 476 */     if (f1 == 0.0F)
/*     */     {
/* 478 */       if (f2 == 0.0F)
/*     */       {
/* 480 */         return -1;
/*     */       }
/* 482 */       paramArrayOfFloat2[(i++)] = (-f3 / f2);
/*     */     }
/*     */     else {
/* 485 */       float f4 = f2 * f2 - 4.0F * f1 * f3;
/* 486 */       if (f4 < 0.0F)
/*     */       {
/* 488 */         return 0;
/*     */       }
/* 490 */       f4 = (float)Math.sqrt(f4);
/*     */ 
/* 496 */       if (f2 < 0.0F) {
/* 497 */         f4 = -f4;
/*     */       }
/* 499 */       float f5 = (f2 + f4) / -2.0F;
/*     */ 
/* 501 */       paramArrayOfFloat2[(i++)] = (f5 / f1);
/* 502 */       if (f5 != 0.0F) {
/* 503 */         paramArrayOfFloat2[(i++)] = (f3 / f5);
/*     */       }
/*     */     }
/* 506 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 514 */     float f1 = this.x1;
/* 515 */     float f2 = this.y1;
/* 516 */     float f3 = this.ctrlx;
/* 517 */     float f4 = this.ctrly;
/* 518 */     float f5 = this.x2;
/* 519 */     float f6 = this.y2;
/*     */ 
/* 587 */     float f7 = f1 - 2.0F * f3 + f5;
/* 588 */     float f8 = f2 - 2.0F * f4 + f6;
/* 589 */     float f9 = paramFloat1 - f1;
/* 590 */     float f10 = paramFloat2 - f2;
/* 591 */     float f11 = f5 - f1;
/* 592 */     float f12 = f6 - f2;
/*     */ 
/* 594 */     float f13 = (f9 * f8 - f10 * f7) / (f11 * f8 - f12 * f7);
/* 595 */     if ((f13 < 0.0F) || (f13 > 1.0F) || (f13 != f13)) {
/* 596 */       return false;
/*     */     }
/*     */ 
/* 599 */     float f14 = f7 * f13 * f13 + 2.0F * (f3 - f1) * f13 + f1;
/* 600 */     float f15 = f8 * f13 * f13 + 2.0F * (f4 - f2) * f13 + f2;
/* 601 */     float f16 = f11 * f13 + f1;
/* 602 */     float f17 = f12 * f13 + f2;
/*     */ 
/* 604 */     return ((paramFloat1 >= f14) && (paramFloat1 < f16)) || ((paramFloat1 >= f16) && (paramFloat1 < f14)) || ((paramFloat2 >= f15) && (paramFloat2 < f17)) || ((paramFloat2 >= f17) && (paramFloat2 < f15));
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/* 615 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   private static void fillEqn(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 633 */     paramArrayOfFloat[0] = (paramFloat2 - paramFloat1);
/* 634 */     paramArrayOfFloat[1] = (paramFloat3 + paramFloat3 - paramFloat2 - paramFloat2);
/* 635 */     paramArrayOfFloat[2] = (paramFloat2 - paramFloat3 - paramFloat3 + paramFloat4);
/*     */   }
/*     */ 
/*     */   private static int evalQuadratic(float[] paramArrayOfFloat1, int paramInt, boolean paramBoolean1, boolean paramBoolean2, float[] paramArrayOfFloat2, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 652 */     int i = 0;
/* 653 */     for (int j = 0; j < paramInt; j++) {
/* 654 */       float f1 = paramArrayOfFloat1[j];
/* 655 */       if ((paramBoolean1 ? f1 >= 0.0F : f1 > 0.0F) && (paramBoolean2 ? f1 <= 1.0F : f1 < 1.0F) && ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2[1] + 2.0F * paramArrayOfFloat2[2] * f1 != 0.0F)))
/*     */       {
/* 660 */         float f2 = 1.0F - f1;
/* 661 */         paramArrayOfFloat1[(i++)] = (paramFloat1 * f2 * f2 + 2.0F * paramFloat2 * f1 * f2 + paramFloat3 * f1 * f1);
/*     */       }
/*     */     }
/* 664 */     return i;
/*     */   }
/*     */ 
/*     */   private static int getTag(float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 680 */     if (paramFloat1 <= paramFloat2) {
/* 681 */       return paramFloat1 < paramFloat2 ? -2 : -1;
/*     */     }
/* 683 */     if (paramFloat1 >= paramFloat3) {
/* 684 */       return paramFloat1 > paramFloat3 ? 2 : 1;
/*     */     }
/* 686 */     return 0;
/*     */   }
/*     */ 
/*     */   private static boolean inwards(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 697 */     switch (paramInt1) {
/*     */     case -2:
/*     */     case 2:
/*     */     default:
/* 701 */       return false;
/*     */     case -1:
/* 703 */       return (paramInt2 >= 0) || (paramInt3 >= 0);
/*     */     case 0:
/* 705 */       return true;
/*     */     case 1:
/* 707 */     }return (paramInt2 <= 0) || (paramInt3 <= 0);
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 717 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 718 */       return false;
/*     */     }
/*     */ 
/* 725 */     float f1 = this.x1;
/* 726 */     float f2 = this.y1;
/* 727 */     int i = getTag(f1, paramFloat1, paramFloat1 + paramFloat3);
/* 728 */     int j = getTag(f2, paramFloat2, paramFloat2 + paramFloat4);
/* 729 */     if ((i == 0) && (j == 0)) {
/* 730 */       return true;
/*     */     }
/* 732 */     float f3 = this.x2;
/* 733 */     float f4 = this.y2;
/* 734 */     int k = getTag(f3, paramFloat1, paramFloat1 + paramFloat3);
/* 735 */     int m = getTag(f4, paramFloat2, paramFloat2 + paramFloat4);
/* 736 */     if ((k == 0) && (m == 0)) {
/* 737 */       return true;
/*     */     }
/* 739 */     float f5 = this.ctrlx;
/* 740 */     float f6 = this.ctrly;
/* 741 */     int n = getTag(f5, paramFloat1, paramFloat1 + paramFloat3);
/* 742 */     int i1 = getTag(f6, paramFloat2, paramFloat2 + paramFloat4);
/*     */ 
/* 746 */     if ((i < 0) && (k < 0) && (n < 0)) {
/* 747 */       return false;
/*     */     }
/* 749 */     if ((j < 0) && (m < 0) && (i1 < 0)) {
/* 750 */       return false;
/*     */     }
/* 752 */     if ((i > 0) && (k > 0) && (n > 0)) {
/* 753 */       return false;
/*     */     }
/* 755 */     if ((j > 0) && (m > 0) && (i1 > 0)) {
/* 756 */       return false;
/*     */     }
/*     */ 
/* 764 */     if ((inwards(i, k, n)) && (inwards(j, m, i1)))
/*     */     {
/* 768 */       return true;
/*     */     }
/* 770 */     if ((inwards(k, i, n)) && (inwards(m, j, i1)))
/*     */     {
/* 774 */       return true;
/*     */     }
/*     */ 
/* 778 */     int i2 = i * k <= 0 ? 1 : 0;
/* 779 */     int i3 = j * m <= 0 ? 1 : 0;
/* 780 */     if ((i == 0) && (k == 0) && (i3 != 0)) {
/* 781 */       return true;
/*     */     }
/* 783 */     if ((j == 0) && (m == 0) && (i2 != 0)) {
/* 784 */       return true;
/*     */     }
/*     */ 
/* 793 */     float[] arrayOfFloat1 = new float[3];
/* 794 */     float[] arrayOfFloat2 = new float[3];
/* 795 */     if (i3 == 0)
/*     */     {
/* 801 */       fillEqn(arrayOfFloat1, j < 0 ? paramFloat2 : paramFloat2 + paramFloat4, f2, f6, f4);
/* 802 */       return (solveQuadratic(arrayOfFloat1, arrayOfFloat2) == 2) && (evalQuadratic(arrayOfFloat2, 2, true, true, null, f1, f5, f3) == 2) && (getTag(arrayOfFloat2[0], paramFloat1, paramFloat1 + paramFloat3) * getTag(arrayOfFloat2[1], paramFloat1, paramFloat1 + paramFloat3) <= 0);
/*     */     }
/*     */ 
/* 809 */     if (i2 == 0)
/*     */     {
/* 815 */       fillEqn(arrayOfFloat1, i < 0 ? paramFloat1 : paramFloat1 + paramFloat3, f1, f5, f3);
/* 816 */       return (solveQuadratic(arrayOfFloat1, arrayOfFloat2) == 2) && (evalQuadratic(arrayOfFloat2, 2, true, true, null, f2, f6, f4) == 2) && (getTag(arrayOfFloat2[0], paramFloat2, paramFloat2 + paramFloat4) * getTag(arrayOfFloat2[1], paramFloat2, paramFloat2 + paramFloat4) <= 0);
/*     */     }
/*     */ 
/* 825 */     float f7 = f3 - f1;
/* 826 */     float f8 = f4 - f2;
/* 827 */     float f9 = f4 * f1 - f3 * f2;
/*     */ 
/* 829 */     if (j == 0)
/* 830 */       i4 = i;
/*     */     else {
/* 832 */       i4 = getTag((f9 + f7 * (j < 0 ? paramFloat2 : paramFloat2 + paramFloat4)) / f8, paramFloat1, paramFloat1 + paramFloat3);
/*     */     }
/* 834 */     if (m == 0)
/* 835 */       i5 = k;
/*     */     else {
/* 837 */       i5 = getTag((f9 + f7 * (m < 0 ? paramFloat2 : paramFloat2 + paramFloat4)) / f8, paramFloat1, paramFloat1 + paramFloat3);
/*     */     }
/*     */ 
/* 841 */     if (i4 * i5 <= 0) {
/* 842 */       return true;
/*     */     }
/*     */ 
/* 871 */     int i4 = i4 * i <= 0 ? j : m;
/*     */ 
/* 878 */     fillEqn(arrayOfFloat1, i5 < 0 ? paramFloat1 : paramFloat1 + paramFloat3, f1, f5, f3);
/* 879 */     int i6 = solveQuadratic(arrayOfFloat1, arrayOfFloat2);
/*     */ 
/* 884 */     evalQuadratic(arrayOfFloat2, i6, true, true, null, f2, f6, f4);
/*     */ 
/* 888 */     int i5 = getTag(arrayOfFloat2[0], paramFloat2, paramFloat2 + paramFloat4);
/*     */ 
/* 892 */     return i4 * i5 <= 0;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 900 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 901 */       return false;
/*     */     }
/*     */ 
/* 905 */     return (contains(paramFloat1, paramFloat2)) && (contains(paramFloat1 + paramFloat3, paramFloat2)) && (contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4)) && (contains(paramFloat1, paramFloat2 + paramFloat4));
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 926 */     return new QuadIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*     */   {
/* 948 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*     */   }
/*     */ 
/*     */   public QuadCurve2D copy()
/*     */   {
/* 953 */     return new QuadCurve2D(this.x1, this.y1, this.ctrlx, this.ctrly, this.x2, this.y2);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 958 */     int i = Float.floatToIntBits(this.x1);
/* 959 */     i += Float.floatToIntBits(this.y1) * 37;
/* 960 */     i += Float.floatToIntBits(this.x2) * 43;
/* 961 */     i += Float.floatToIntBits(this.y2) * 47;
/* 962 */     i += Float.floatToIntBits(this.ctrlx) * 53;
/* 963 */     i += Float.floatToIntBits(this.ctrly) * 59;
/* 964 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 969 */     if (paramObject == this) {
/* 970 */       return true;
/*     */     }
/* 972 */     if ((paramObject instanceof QuadCurve2D)) {
/* 973 */       QuadCurve2D localQuadCurve2D = (QuadCurve2D)paramObject;
/* 974 */       return (this.x1 == localQuadCurve2D.x1) && (this.y1 == localQuadCurve2D.y1) && (this.x2 == localQuadCurve2D.x2) && (this.y2 == localQuadCurve2D.y2) && (this.ctrlx == localQuadCurve2D.ctrlx) && (this.ctrly == localQuadCurve2D.ctrly);
/*     */     }
/*     */ 
/* 978 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.QuadCurve2D
 * JD-Core Version:    0.6.2
 */