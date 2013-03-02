/*      */ package com.sun.javafx.geom;
/*      */ 
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import java.util.Arrays;
/*      */ 
/*      */ public class CubicCurve2D extends Shape
/*      */ {
/*      */   public float x1;
/*      */   public float y1;
/*      */   public float ctrlx1;
/*      */   public float ctrly1;
/*      */   public float ctrlx2;
/*      */   public float ctrly2;
/*      */   public float x2;
/*      */   public float y2;
/*      */   private static final int BELOW = -2;
/*      */   private static final int LOWEDGE = -1;
/*      */   private static final int INSIDE = 0;
/*      */   private static final int HIGHEDGE = 1;
/*      */   private static final int ABOVE = 2;
/*      */ 
/*      */   public CubicCurve2D()
/*      */   {
/*      */   }
/*      */ 
/*      */   public CubicCurve2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  126 */     setCurve(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */   }
/*      */ 
/*      */   public void setCurve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  155 */     this.x1 = paramFloat1;
/*  156 */     this.y1 = paramFloat2;
/*  157 */     this.ctrlx1 = paramFloat3;
/*  158 */     this.ctrly1 = paramFloat4;
/*  159 */     this.ctrlx2 = paramFloat5;
/*  160 */     this.ctrly2 = paramFloat6;
/*  161 */     this.x2 = paramFloat7;
/*  162 */     this.y2 = paramFloat8;
/*      */   }
/*      */ 
/*      */   public RectBounds getBounds()
/*      */   {
/*  169 */     float f1 = Math.min(Math.min(this.x1, this.x2), Math.min(this.ctrlx1, this.ctrlx2));
/*      */ 
/*  171 */     float f2 = Math.min(Math.min(this.y1, this.y2), Math.min(this.ctrly1, this.ctrly2));
/*      */ 
/*  173 */     float f3 = Math.max(Math.max(this.x1, this.x2), Math.max(this.ctrlx1, this.ctrlx2));
/*      */ 
/*  175 */     float f4 = Math.max(Math.max(this.y1, this.y2), Math.max(this.ctrly1, this.ctrly2));
/*      */ 
/*  177 */     return new RectBounds(f1, f2, f3, f4);
/*      */   }
/*      */ 
/*      */   public Point2D eval(float paramFloat)
/*      */   {
/*  190 */     Point2D localPoint2D = new Point2D();
/*  191 */     eval(paramFloat, localPoint2D);
/*  192 */     return localPoint2D;
/*      */   }
/*      */ 
/*      */   public void eval(float paramFloat, Point2D paramPoint2D)
/*      */   {
/*  206 */     float f1 = paramFloat;
/*  207 */     float f2 = 1.0F - f1;
/*  208 */     float f3 = f2 * f2 * f2 * this.x1 + 3.0F * (f1 * f2 * f2 * this.ctrlx1 + f1 * f1 * f2 * this.ctrlx2) + f1 * f1 * f1 * this.x2;
/*      */ 
/*  212 */     float f4 = f2 * f2 * f2 * this.y1 + 3.0F * (f1 * f2 * f2 * this.ctrly1 + f1 * f1 * f2 * this.ctrly2) + f1 * f1 * f1 * this.y2;
/*      */ 
/*  216 */     paramPoint2D.setLocation(f3, f4);
/*      */   }
/*      */ 
/*      */   public Point2D evalDt(float paramFloat)
/*      */   {
/*  232 */     Point2D localPoint2D = new Point2D();
/*  233 */     evalDt(paramFloat, localPoint2D);
/*  234 */     return localPoint2D;
/*      */   }
/*      */ 
/*      */   public void evalDt(float paramFloat, Point2D paramPoint2D)
/*      */   {
/*  250 */     float f1 = paramFloat;
/*  251 */     float f2 = 1.0F - f1;
/*  252 */     float f3 = 3.0F * ((this.ctrlx1 - this.x1) * f2 * f2 + 2.0F * (this.ctrlx2 - this.ctrlx1) * f2 * f1 + (this.x2 - this.ctrlx2) * f1 * f1);
/*      */ 
/*  255 */     float f4 = 3.0F * ((this.ctrly1 - this.y1) * f2 * f2 + 2.0F * (this.ctrly2 - this.ctrly1) * f2 * f1 + (this.y2 - this.ctrly2) * f1 * f1);
/*      */ 
/*  258 */     paramPoint2D.setLocation(f3, f4);
/*      */   }
/*      */ 
/*      */   public void setCurve(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  272 */     setCurve(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)], paramArrayOfFloat[(paramInt + 6)], paramArrayOfFloat[(paramInt + 7)]);
/*      */   }
/*      */ 
/*      */   public void setCurve(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3, Point2D paramPoint2D4)
/*      */   {
/*  292 */     setCurve(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y, paramPoint2D3.x, paramPoint2D3.y, paramPoint2D4.x, paramPoint2D4.y);
/*      */   }
/*      */ 
/*      */   public void setCurve(Point2D[] paramArrayOfPoint2D, int paramInt)
/*      */   {
/*  306 */     setCurve(paramArrayOfPoint2D[(paramInt + 0)].x, paramArrayOfPoint2D[(paramInt + 0)].y, paramArrayOfPoint2D[(paramInt + 1)].x, paramArrayOfPoint2D[(paramInt + 1)].y, paramArrayOfPoint2D[(paramInt + 2)].x, paramArrayOfPoint2D[(paramInt + 2)].y, paramArrayOfPoint2D[(paramInt + 3)].x, paramArrayOfPoint2D[(paramInt + 3)].y);
/*      */   }
/*      */ 
/*      */   public void setCurve(CubicCurve2D paramCubicCurve2D)
/*      */   {
/*  319 */     setCurve(paramCubicCurve2D.x1, paramCubicCurve2D.y1, paramCubicCurve2D.ctrlx1, paramCubicCurve2D.ctrly1, paramCubicCurve2D.ctrlx2, paramCubicCurve2D.ctrly2, paramCubicCurve2D.x2, paramCubicCurve2D.y2);
/*      */   }
/*      */ 
/*      */   public static float getFlatnessSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  350 */     return Math.max(Line2D.ptSegDistSq(paramFloat1, paramFloat2, paramFloat7, paramFloat8, paramFloat3, paramFloat4), Line2D.ptSegDistSq(paramFloat1, paramFloat2, paramFloat7, paramFloat8, paramFloat5, paramFloat6));
/*      */   }
/*      */ 
/*      */   public static float getFlatness(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  384 */     return (float)Math.sqrt(getFlatnessSq(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8));
/*      */   }
/*      */ 
/*      */   public static float getFlatnessSq(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  402 */     return getFlatnessSq(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)], paramArrayOfFloat[(paramInt + 6)], paramArrayOfFloat[(paramInt + 7)]);
/*      */   }
/*      */ 
/*      */   public static float getFlatness(float[] paramArrayOfFloat, int paramInt)
/*      */   {
/*  421 */     return getFlatness(paramArrayOfFloat[(paramInt + 0)], paramArrayOfFloat[(paramInt + 1)], paramArrayOfFloat[(paramInt + 2)], paramArrayOfFloat[(paramInt + 3)], paramArrayOfFloat[(paramInt + 4)], paramArrayOfFloat[(paramInt + 5)], paramArrayOfFloat[(paramInt + 6)], paramArrayOfFloat[(paramInt + 7)]);
/*      */   }
/*      */ 
/*      */   public float getFlatnessSq()
/*      */   {
/*  434 */     return getFlatnessSq(this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2);
/*      */   }
/*      */ 
/*      */   public float getFlatness()
/*      */   {
/*  445 */     return getFlatness(this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2);
/*      */   }
/*      */ 
/*      */   public void subdivide(float paramFloat, CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2)
/*      */   {
/*  462 */     if ((paramCubicCurve2D1 == null) && (paramCubicCurve2D2 == null)) return;
/*      */ 
/*  464 */     Point2D localPoint2D = eval(paramFloat);
/*      */ 
/*  466 */     float f1 = this.x1;
/*  467 */     float f2 = this.y1;
/*  468 */     float f3 = this.ctrlx1;
/*  469 */     float f4 = this.ctrly1;
/*  470 */     float f5 = this.ctrlx2;
/*  471 */     float f6 = this.ctrly2;
/*  472 */     float f7 = this.x2;
/*  473 */     float f8 = this.y2;
/*  474 */     float f9 = 1.0F - paramFloat;
/*  475 */     float f10 = f9 * f3 + paramFloat * f5;
/*  476 */     float f11 = f9 * f4 + paramFloat * f6;
/*      */     float f12;
/*      */     float f13;
/*      */     float f14;
/*      */     float f15;
/*      */     float f16;
/*      */     float f17;
/*      */     float f18;
/*      */     float f19;
/*  478 */     if (paramCubicCurve2D1 != null) {
/*  479 */       f12 = f1;
/*  480 */       f13 = f2;
/*  481 */       f14 = f9 * f1 + paramFloat * f3;
/*  482 */       f15 = f9 * f2 + paramFloat * f4;
/*  483 */       f16 = f9 * f14 + paramFloat * f10;
/*  484 */       f17 = f9 * f15 + paramFloat * f11;
/*  485 */       f18 = localPoint2D.x;
/*  486 */       f19 = localPoint2D.y;
/*  487 */       paramCubicCurve2D1.setCurve(f12, f13, f14, f15, f16, f17, f18, f19);
/*      */     }
/*      */ 
/*  493 */     if (paramCubicCurve2D2 != null) {
/*  494 */       f12 = localPoint2D.x;
/*  495 */       f13 = localPoint2D.y;
/*  496 */       f14 = f9 * f5 + paramFloat * f7;
/*  497 */       f15 = f9 * f6 + paramFloat * f8;
/*  498 */       f16 = f9 * f10 + paramFloat * f14;
/*  499 */       f17 = f9 * f11 + paramFloat * f15;
/*  500 */       f18 = f7;
/*  501 */       f19 = f8;
/*  502 */       paramCubicCurve2D2.setCurve(f12, f13, f16, f17, f14, f15, f18, f19);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void subdivide(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2)
/*      */   {
/*  521 */     subdivide(this, paramCubicCurve2D1, paramCubicCurve2D2);
/*      */   }
/*      */ 
/*      */   public static void subdivide(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2, CubicCurve2D paramCubicCurve2D3)
/*      */   {
/*  540 */     float f1 = paramCubicCurve2D1.x1;
/*  541 */     float f2 = paramCubicCurve2D1.y1;
/*  542 */     float f3 = paramCubicCurve2D1.ctrlx1;
/*  543 */     float f4 = paramCubicCurve2D1.ctrly1;
/*  544 */     float f5 = paramCubicCurve2D1.ctrlx2;
/*  545 */     float f6 = paramCubicCurve2D1.ctrly2;
/*  546 */     float f7 = paramCubicCurve2D1.x2;
/*  547 */     float f8 = paramCubicCurve2D1.y2;
/*  548 */     float f9 = (f3 + f5) / 2.0F;
/*  549 */     float f10 = (f4 + f6) / 2.0F;
/*  550 */     f3 = (f1 + f3) / 2.0F;
/*  551 */     f4 = (f2 + f4) / 2.0F;
/*  552 */     f5 = (f7 + f5) / 2.0F;
/*  553 */     f6 = (f8 + f6) / 2.0F;
/*  554 */     float f11 = (f3 + f9) / 2.0F;
/*  555 */     float f12 = (f4 + f10) / 2.0F;
/*  556 */     float f13 = (f5 + f9) / 2.0F;
/*  557 */     float f14 = (f6 + f10) / 2.0F;
/*  558 */     f9 = (f11 + f13) / 2.0F;
/*  559 */     f10 = (f12 + f14) / 2.0F;
/*  560 */     if (paramCubicCurve2D2 != null) {
/*  561 */       paramCubicCurve2D2.setCurve(f1, f2, f3, f4, f11, f12, f9, f10);
/*      */     }
/*      */ 
/*  564 */     if (paramCubicCurve2D3 != null)
/*  565 */       paramCubicCurve2D3.setCurve(f9, f10, f13, f14, f5, f6, f7, f8);
/*      */   }
/*      */ 
/*      */   public static void subdivide(float[] paramArrayOfFloat1, int paramInt1, float[] paramArrayOfFloat2, int paramInt2, float[] paramArrayOfFloat3, int paramInt3)
/*      */   {
/*  601 */     float f1 = paramArrayOfFloat1[(paramInt1 + 0)];
/*  602 */     float f2 = paramArrayOfFloat1[(paramInt1 + 1)];
/*  603 */     float f3 = paramArrayOfFloat1[(paramInt1 + 2)];
/*  604 */     float f4 = paramArrayOfFloat1[(paramInt1 + 3)];
/*  605 */     float f5 = paramArrayOfFloat1[(paramInt1 + 4)];
/*  606 */     float f6 = paramArrayOfFloat1[(paramInt1 + 5)];
/*  607 */     float f7 = paramArrayOfFloat1[(paramInt1 + 6)];
/*  608 */     float f8 = paramArrayOfFloat1[(paramInt1 + 7)];
/*  609 */     if (paramArrayOfFloat2 != null) {
/*  610 */       paramArrayOfFloat2[(paramInt2 + 0)] = f1;
/*  611 */       paramArrayOfFloat2[(paramInt2 + 1)] = f2;
/*      */     }
/*  613 */     if (paramArrayOfFloat3 != null) {
/*  614 */       paramArrayOfFloat3[(paramInt3 + 6)] = f7;
/*  615 */       paramArrayOfFloat3[(paramInt3 + 7)] = f8;
/*      */     }
/*  617 */     f1 = (f1 + f3) / 2.0F;
/*  618 */     f2 = (f2 + f4) / 2.0F;
/*  619 */     f7 = (f7 + f5) / 2.0F;
/*  620 */     f8 = (f8 + f6) / 2.0F;
/*  621 */     float f9 = (f3 + f5) / 2.0F;
/*  622 */     float f10 = (f4 + f6) / 2.0F;
/*  623 */     f3 = (f1 + f9) / 2.0F;
/*  624 */     f4 = (f2 + f10) / 2.0F;
/*  625 */     f5 = (f7 + f9) / 2.0F;
/*  626 */     f6 = (f8 + f10) / 2.0F;
/*  627 */     f9 = (f3 + f5) / 2.0F;
/*  628 */     f10 = (f4 + f6) / 2.0F;
/*  629 */     if (paramArrayOfFloat2 != null) {
/*  630 */       paramArrayOfFloat2[(paramInt2 + 2)] = f1;
/*  631 */       paramArrayOfFloat2[(paramInt2 + 3)] = f2;
/*  632 */       paramArrayOfFloat2[(paramInt2 + 4)] = f3;
/*  633 */       paramArrayOfFloat2[(paramInt2 + 5)] = f4;
/*  634 */       paramArrayOfFloat2[(paramInt2 + 6)] = f9;
/*  635 */       paramArrayOfFloat2[(paramInt2 + 7)] = f10;
/*      */     }
/*  637 */     if (paramArrayOfFloat3 != null) {
/*  638 */       paramArrayOfFloat3[(paramInt3 + 0)] = f9;
/*  639 */       paramArrayOfFloat3[(paramInt3 + 1)] = f10;
/*  640 */       paramArrayOfFloat3[(paramInt3 + 2)] = f5;
/*  641 */       paramArrayOfFloat3[(paramInt3 + 3)] = f6;
/*  642 */       paramArrayOfFloat3[(paramInt3 + 4)] = f7;
/*  643 */       paramArrayOfFloat3[(paramInt3 + 5)] = f8;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static int solveCubic(float[] paramArrayOfFloat)
/*      */   {
/*  664 */     return solveCubic(paramArrayOfFloat, paramArrayOfFloat);
/*      */   }
/*      */ 
/*      */   public static int solveCubic(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/*  686 */     float f1 = paramArrayOfFloat1[3];
/*  687 */     if (f1 == 0.0F)
/*      */     {
/*  689 */       return QuadCurve2D.solveQuadratic(paramArrayOfFloat1, paramArrayOfFloat2);
/*      */     }
/*  691 */     float f2 = paramArrayOfFloat1[2] / f1;
/*  692 */     float f3 = paramArrayOfFloat1[1] / f1;
/*  693 */     float f4 = paramArrayOfFloat1[0] / f1;
/*  694 */     int i = 0;
/*  695 */     float f5 = (f2 * f2 - 3.0F * f3) / 9.0F;
/*  696 */     float f6 = (2.0F * f2 * f2 * f2 - 9.0F * f2 * f3 + 27.0F * f4) / 54.0F;
/*  697 */     float f7 = f6 * f6;
/*  698 */     float f8 = f5 * f5 * f5;
/*  699 */     f2 /= 3.0F;
/*  700 */     if (f7 < f8) {
/*  701 */       float f9 = (float)Math.acos(f6 / Math.sqrt(f8));
/*  702 */       f5 = (float)(-2.0D * Math.sqrt(f5));
/*  703 */       if (paramArrayOfFloat2 == paramArrayOfFloat1)
/*      */       {
/*  707 */         paramArrayOfFloat1 = new float[4];
/*  708 */         System.arraycopy(paramArrayOfFloat2, 0, paramArrayOfFloat1, 0, 4);
/*      */       }
/*  710 */       paramArrayOfFloat2[(i++)] = ((float)(f5 * Math.cos(f9 / 3.0F) - f2));
/*  711 */       paramArrayOfFloat2[(i++)] = ((float)(f5 * Math.cos((f9 + 6.283185307179586D) / 3.0D) - f2));
/*  712 */       paramArrayOfFloat2[(i++)] = ((float)(f5 * Math.cos((f9 - 6.283185307179586D) / 3.0D) - f2));
/*  713 */       fixRoots(paramArrayOfFloat2, paramArrayOfFloat1);
/*      */     } else {
/*  715 */       int j = f6 < 0.0F ? 1 : 0;
/*  716 */       float f10 = (float)Math.sqrt(f7 - f8);
/*  717 */       if (j != 0) {
/*  718 */         f6 = -f6;
/*      */       }
/*  720 */       float f11 = (float)Math.pow(f6 + f10, 0.333333343267441D);
/*  721 */       if (j == 0) {
/*  722 */         f11 = -f11;
/*      */       }
/*  724 */       float f12 = f11 == 0.0F ? 0.0F : f5 / f11;
/*  725 */       paramArrayOfFloat2[(i++)] = (f11 + f12 - f2);
/*      */     }
/*  727 */     return i;
/*      */   }
/*      */ 
/*      */   private static void fixRoots(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
/*      */   {
/*  766 */     for (int i = 0; i < 3; i++) {
/*  767 */       float f = paramArrayOfFloat1[i];
/*  768 */       if (Math.abs(f) < 1.0E-05F)
/*  769 */         paramArrayOfFloat1[i] = findZero(f, 0.0F, paramArrayOfFloat2);
/*  770 */       else if (Math.abs(f - 1.0F) < 1.0E-05F)
/*  771 */         paramArrayOfFloat1[i] = findZero(f, 1.0F, paramArrayOfFloat2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private static float solveEqn(float[] paramArrayOfFloat, int paramInt, float paramFloat)
/*      */   {
/*  777 */     float f = paramArrayOfFloat[paramInt];
/*      */     while (true) { paramInt--; if (paramInt < 0) break;
/*  779 */       f = f * paramFloat + paramArrayOfFloat[paramInt];
/*      */     }
/*  781 */     return f;
/*      */   }
/*      */ 
/*      */   private static float findZero(float paramFloat1, float paramFloat2, float[] paramArrayOfFloat) {
/*  785 */     float[] arrayOfFloat = { paramArrayOfFloat[1], 2.0F * paramArrayOfFloat[2], 3.0F * paramArrayOfFloat[3] };
/*      */ 
/*  787 */     float f2 = 0.0F;
/*  788 */     float f3 = paramFloat1;
/*      */     while (true) {
/*  790 */       float f1 = solveEqn(arrayOfFloat, 2, paramFloat1);
/*  791 */       if (f1 == 0.0F)
/*      */       {
/*  793 */         return paramFloat1;
/*      */       }
/*  795 */       float f4 = solveEqn(paramArrayOfFloat, 3, paramFloat1);
/*  796 */       if (f4 == 0.0F)
/*      */       {
/*  798 */         return paramFloat1;
/*      */       }
/*      */ 
/*  801 */       float f5 = -(f4 / f1);
/*      */ 
/*  803 */       if (f2 == 0.0F) {
/*  804 */         f2 = f5;
/*      */       }
/*  806 */       if (paramFloat1 < paramFloat2) {
/*  807 */         if (f5 < 0.0F) return paramFloat1; 
/*      */       }
/*  808 */       else if (paramFloat1 > paramFloat2) {
/*  809 */         if (f5 > 0.0F) return paramFloat1; 
/*      */       }
/*      */       else {
/*  811 */         return f5 > 0.0F ? paramFloat2 + 1.4E-45F : paramFloat2 - 1.4E-45F;
/*      */       }
/*      */ 
/*  815 */       float f6 = paramFloat1 + f5;
/*  816 */       if (paramFloat1 == f6)
/*      */       {
/*  818 */         return paramFloat1;
/*      */       }
/*  820 */       if (f5 * f2 < 0.0F)
/*      */       {
/*  822 */         int i = f3 < paramFloat1 ? getTag(paramFloat2, f3, paramFloat1) : getTag(paramFloat2, paramFloat1, f3);
/*      */ 
/*  825 */         if (i != 0)
/*      */         {
/*  827 */           return (f3 + paramFloat1) / 2.0F;
/*      */         }
/*      */ 
/*  831 */         paramFloat1 = paramFloat2;
/*      */       } else {
/*  833 */         paramFloat1 = f6;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean contains(float paramFloat1, float paramFloat2)
/*      */   {
/*  843 */     if (paramFloat1 * 0.0F + paramFloat2 * 0.0F != 0.0F)
/*      */     {
/*  849 */       return false;
/*      */     }
/*      */ 
/*  853 */     int i = Shape.pointCrossingsForLine(paramFloat1, paramFloat2, this.x1, this.y1, this.x2, this.y2) + Shape.pointCrossingsForCubic(paramFloat1, paramFloat2, this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2, 0);
/*      */ 
/*  860 */     return (i & 0x1) == 1;
/*      */   }
/*      */ 
/*      */   public boolean contains(Point2D paramPoint2D)
/*      */   {
/*  868 */     return contains(paramPoint2D.x, paramPoint2D.y);
/*      */   }
/*      */ 
/*      */   private static void fillEqn(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*      */   {
/*  894 */     paramArrayOfFloat[0] = (paramFloat2 - paramFloat1);
/*  895 */     paramArrayOfFloat[1] = ((paramFloat3 - paramFloat2) * 3.0F);
/*  896 */     paramArrayOfFloat[2] = ((paramFloat4 - paramFloat3 - paramFloat3 + paramFloat2) * 3.0F);
/*  897 */     paramArrayOfFloat[3] = (paramFloat5 + (paramFloat3 - paramFloat4) * 3.0F - paramFloat2);
/*      */   }
/*      */ 
/*      */   private static int evalCubic(float[] paramArrayOfFloat1, int paramInt, boolean paramBoolean1, boolean paramBoolean2, float[] paramArrayOfFloat2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  915 */     int i = 0;
/*  916 */     for (int j = 0; j < paramInt; j++) {
/*  917 */       float f1 = paramArrayOfFloat1[j];
/*  918 */       if ((paramBoolean1 ? f1 >= 0.0F : f1 > 0.0F) && (paramBoolean2 ? f1 <= 1.0F : f1 < 1.0F) && ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2[1] + (2.0F * paramArrayOfFloat2[2] + 3.0F * paramArrayOfFloat2[3] * f1) * f1 != 0.0F)))
/*      */       {
/*  923 */         float f2 = 1.0F - f1;
/*  924 */         paramArrayOfFloat1[(i++)] = (paramFloat1 * f2 * f2 * f2 + 3.0F * paramFloat2 * f1 * f2 * f2 + 3.0F * paramFloat3 * f1 * f1 * f2 + paramFloat4 * f1 * f1 * f1);
/*      */       }
/*      */     }
/*  927 */     return i;
/*      */   }
/*      */ 
/*      */   private static int getTag(float paramFloat1, float paramFloat2, float paramFloat3)
/*      */   {
/*  943 */     if (paramFloat1 <= paramFloat2) {
/*  944 */       return paramFloat1 < paramFloat2 ? -2 : -1;
/*      */     }
/*  946 */     if (paramFloat1 >= paramFloat3) {
/*  947 */       return paramFloat1 > paramFloat3 ? 2 : 1;
/*      */     }
/*  949 */     return 0;
/*      */   }
/*      */ 
/*      */   private static boolean inwards(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  960 */     switch (paramInt1) {
/*      */     case -2:
/*      */     case 2:
/*      */     default:
/*  964 */       return false;
/*      */     case -1:
/*  966 */       return (paramInt2 >= 0) || (paramInt3 >= 0);
/*      */     case 0:
/*  968 */       return true;
/*      */     case 1:
/*  970 */     }return (paramInt2 <= 0) || (paramInt3 <= 0);
/*      */   }
/*      */ 
/*      */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  980 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/*  981 */       return false;
/*      */     }
/*      */ 
/*  988 */     float f1 = this.x1;
/*  989 */     float f2 = this.y1;
/*  990 */     int i = getTag(f1, paramFloat1, paramFloat1 + paramFloat3);
/*  991 */     int j = getTag(f2, paramFloat2, paramFloat2 + paramFloat4);
/*  992 */     if ((i == 0) && (j == 0)) {
/*  993 */       return true;
/*      */     }
/*  995 */     float f3 = this.x2;
/*  996 */     float f4 = this.y2;
/*  997 */     int k = getTag(f3, paramFloat1, paramFloat1 + paramFloat3);
/*  998 */     int m = getTag(f4, paramFloat2, paramFloat2 + paramFloat4);
/*  999 */     if ((k == 0) && (m == 0)) {
/* 1000 */       return true;
/*      */     }
/*      */ 
/* 1003 */     float f5 = this.ctrlx1;
/* 1004 */     float f6 = this.ctrly1;
/* 1005 */     float f7 = this.ctrlx2;
/* 1006 */     float f8 = this.ctrly2;
/* 1007 */     int n = getTag(f5, paramFloat1, paramFloat1 + paramFloat3);
/* 1008 */     int i1 = getTag(f6, paramFloat2, paramFloat2 + paramFloat4);
/* 1009 */     int i2 = getTag(f7, paramFloat1, paramFloat1 + paramFloat3);
/* 1010 */     int i3 = getTag(f8, paramFloat2, paramFloat2 + paramFloat4);
/*      */ 
/* 1014 */     if ((i < 0) && (k < 0) && (n < 0) && (i2 < 0))
/*      */     {
/* 1017 */       return false;
/*      */     }
/* 1019 */     if ((j < 0) && (m < 0) && (i1 < 0) && (i3 < 0))
/*      */     {
/* 1022 */       return false;
/*      */     }
/* 1024 */     if ((i > 0) && (k > 0) && (n > 0) && (i2 > 0))
/*      */     {
/* 1027 */       return false;
/*      */     }
/* 1029 */     if ((j > 0) && (m > 0) && (i1 > 0) && (i3 > 0))
/*      */     {
/* 1032 */       return false;
/*      */     }
/*      */ 
/* 1040 */     if ((inwards(i, k, n)) && (inwards(j, m, i1)))
/*      */     {
/* 1044 */       return true;
/*      */     }
/* 1046 */     if ((inwards(k, i, i2)) && (inwards(m, j, i3)))
/*      */     {
/* 1050 */       return true;
/*      */     }
/*      */ 
/* 1054 */     int i4 = i * k <= 0 ? 1 : 0;
/* 1055 */     int i5 = j * m <= 0 ? 1 : 0;
/* 1056 */     if ((i == 0) && (k == 0) && (i5 != 0)) {
/* 1057 */       return true;
/*      */     }
/* 1059 */     if ((j == 0) && (m == 0) && (i4 != 0)) {
/* 1060 */       return true;
/*      */     }
/*      */ 
/* 1069 */     float[] arrayOfFloat1 = new float[4];
/* 1070 */     float[] arrayOfFloat2 = new float[4];
/*      */     int i6;
/* 1071 */     if (i5 == 0)
/*      */     {
/* 1077 */       fillEqn(arrayOfFloat1, j < 0 ? paramFloat2 : paramFloat2 + paramFloat4, f2, f6, f8, f4);
/* 1078 */       i6 = solveCubic(arrayOfFloat1, arrayOfFloat2);
/* 1079 */       i6 = evalCubic(arrayOfFloat2, i6, true, true, null, f1, f5, f7, f3);
/*      */ 
/* 1084 */       return (i6 == 2) && (getTag(arrayOfFloat2[0], paramFloat1, paramFloat1 + paramFloat3) * getTag(arrayOfFloat2[1], paramFloat1, paramFloat1 + paramFloat3) <= 0);
/*      */     }
/*      */ 
/* 1089 */     if (i4 == 0)
/*      */     {
/* 1095 */       fillEqn(arrayOfFloat1, i < 0 ? paramFloat1 : paramFloat1 + paramFloat3, f1, f5, f7, f3);
/* 1096 */       i6 = solveCubic(arrayOfFloat1, arrayOfFloat2);
/* 1097 */       i6 = evalCubic(arrayOfFloat2, i6, true, true, null, f2, f6, f8, f4);
/*      */ 
/* 1102 */       return (i6 == 2) && (getTag(arrayOfFloat2[0], paramFloat2, paramFloat2 + paramFloat4) * getTag(arrayOfFloat2[1], paramFloat2, paramFloat2 + paramFloat4) <= 0);
/*      */     }
/*      */ 
/* 1109 */     float f9 = f3 - f1;
/* 1110 */     float f10 = f4 - f2;
/* 1111 */     float f11 = f4 * f1 - f3 * f2;
/*      */ 
/* 1113 */     if (j == 0)
/* 1114 */       i7 = i;
/*      */     else
/* 1116 */       i7 = getTag((f11 + f9 * (j < 0 ? paramFloat2 : paramFloat2 + paramFloat4)) / f10, paramFloat1, paramFloat1 + paramFloat3);
/*      */     int i8;
/* 1118 */     if (m == 0)
/* 1119 */       i8 = k;
/*      */     else {
/* 1121 */       i8 = getTag((f11 + f9 * (m < 0 ? paramFloat2 : paramFloat2 + paramFloat4)) / f10, paramFloat1, paramFloat1 + paramFloat3);
/*      */     }
/*      */ 
/* 1125 */     if (i7 * i8 <= 0) {
/* 1126 */       return true;
/*      */     }
/*      */ 
/* 1156 */     int i7 = i7 * i <= 0 ? j : m;
/*      */ 
/* 1166 */     fillEqn(arrayOfFloat1, i8 < 0 ? paramFloat1 : paramFloat1 + paramFloat3, f1, f5, f7, f3);
/* 1167 */     int i9 = solveCubic(arrayOfFloat1, arrayOfFloat2);
/* 1168 */     i9 = evalCubic(arrayOfFloat2, i9, true, true, null, f2, f6, f8, f4);
/*      */ 
/* 1173 */     int[] arrayOfInt = new int[i9 + 1];
/* 1174 */     for (int i10 = 0; i10 < i9; i10++) {
/* 1175 */       arrayOfInt[i10] = getTag(arrayOfFloat2[i10], paramFloat2, paramFloat2 + paramFloat4);
/*      */     }
/* 1177 */     arrayOfInt[i9] = i7;
/* 1178 */     Arrays.sort(arrayOfInt);
/* 1179 */     return ((i9 >= 1) && (arrayOfInt[0] * arrayOfInt[1] <= 0)) || ((i9 >= 3) && (arrayOfInt[2] * arrayOfInt[3] <= 0));
/*      */   }
/*      */ 
/*      */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/* 1188 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 1189 */       return false;
/*      */     }
/*      */ 
/* 1194 */     if ((!contains(paramFloat1, paramFloat2)) || (!contains(paramFloat1 + paramFloat3, paramFloat2)) || (!contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4)) || (!contains(paramFloat1, paramFloat2 + paramFloat4)))
/*      */     {
/* 1198 */       return false;
/*      */     }
/*      */ 
/* 1203 */     return !Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.x1, this.y1, this.x2, this.y2);
/*      */   }
/*      */ 
/*      */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*      */   {
/* 1223 */     return new CubicIterator(this, paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat)
/*      */   {
/* 1246 */     return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*      */   }
/*      */ 
/*      */   public CubicCurve2D copy()
/*      */   {
/* 1251 */     return new CubicCurve2D(this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2);
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1256 */     int i = Float.floatToIntBits(this.x1);
/* 1257 */     i += Float.floatToIntBits(this.y1) * 37;
/* 1258 */     i += Float.floatToIntBits(this.x2) * 43;
/* 1259 */     i += Float.floatToIntBits(this.y2) * 47;
/* 1260 */     i += Float.floatToIntBits(this.ctrlx1) * 53;
/* 1261 */     i += Float.floatToIntBits(this.ctrly1) * 59;
/* 1262 */     i += Float.floatToIntBits(this.ctrlx2) * 61;
/* 1263 */     i += Float.floatToIntBits(this.ctrly2) * 101;
/* 1264 */     return i ^ i >> 32;
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1269 */     if (paramObject == this) {
/* 1270 */       return true;
/*      */     }
/* 1272 */     if ((paramObject instanceof CubicCurve2D)) {
/* 1273 */       CubicCurve2D localCubicCurve2D = (CubicCurve2D)paramObject;
/* 1274 */       return (this.x1 == localCubicCurve2D.x1) && (this.y1 == localCubicCurve2D.y1) && (this.x2 == localCubicCurve2D.x2) && (this.y2 == localCubicCurve2D.y2) && (this.ctrlx1 == localCubicCurve2D.ctrlx1) && (this.ctrly1 == localCubicCurve2D.ctrly1) && (this.ctrlx2 == localCubicCurve2D.ctrlx2) && (this.ctrly2 == localCubicCurve2D.ctrly2);
/*      */     }
/*      */ 
/* 1279 */     return false;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.CubicCurve2D
 * JD-Core Version:    0.6.2
 */