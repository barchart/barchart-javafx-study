/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class Arc2D extends RectangularShape
/*     */ {
/*     */   public static final int OPEN = 0;
/*     */   public static final int CHORD = 1;
/*     */   public static final int PIE = 2;
/*     */   private int type;
/*     */   public float x;
/*     */   public float y;
/*     */   public float width;
/*     */   public float height;
/*     */   public float start;
/*     */   public float extent;
/*     */ 
/*     */   public Arc2D()
/*     */   {
/* 129 */     this(0);
/*     */   }
/*     */ 
/*     */   public Arc2D(int paramInt)
/*     */   {
/* 142 */     setArcType(paramInt);
/*     */   }
/*     */ 
/*     */   public Arc2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
/*     */   {
/* 165 */     this(paramInt);
/* 166 */     this.x = paramFloat1;
/* 167 */     this.y = paramFloat2;
/* 168 */     this.width = paramFloat3;
/* 169 */     this.height = paramFloat4;
/* 170 */     this.start = paramFloat5;
/* 171 */     this.extent = paramFloat6;
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/* 179 */     return this.x;
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/* 187 */     return this.y;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 195 */     return this.width;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 203 */     return this.height;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 211 */     return (this.width <= 0.0F) || (this.height <= 0.0F);
/*     */   }
/*     */ 
/*     */   public void setArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
/*     */   {
/* 220 */     setArcType(paramInt);
/* 221 */     this.x = paramFloat1;
/* 222 */     this.y = paramFloat2;
/* 223 */     this.width = paramFloat3;
/* 224 */     this.height = paramFloat4;
/* 225 */     this.start = paramFloat5;
/* 226 */     this.extent = paramFloat6;
/*     */   }
/*     */ 
/*     */   public int getArcType()
/*     */   {
/* 238 */     return this.type;
/*     */   }
/*     */ 
/*     */   public Point2D getStartPoint()
/*     */   {
/* 251 */     double d1 = Math.toRadians(-this.start);
/* 252 */     double d2 = this.x + (Math.cos(d1) * 0.5D + 0.5D) * this.width;
/* 253 */     double d3 = this.y + (Math.sin(d1) * 0.5D + 0.5D) * this.height;
/* 254 */     return new Point2D((float)d2, (float)d3);
/*     */   }
/*     */ 
/*     */   public Point2D getEndPoint()
/*     */   {
/* 268 */     double d1 = Math.toRadians(-this.start - this.extent);
/* 269 */     double d2 = this.x + (Math.cos(d1) * 0.5D + 0.5D) * this.width;
/* 270 */     double d3 = this.y + (Math.sin(d1) * 0.5D + 0.5D) * this.height;
/* 271 */     return new Point2D((float)d2, (float)d3);
/*     */   }
/*     */ 
/*     */   public void setArc(Point2D paramPoint2D, Dimension2D paramDimension2D, float paramFloat1, float paramFloat2, int paramInt)
/*     */   {
/* 291 */     setArc(paramPoint2D.x, paramPoint2D.y, paramDimension2D.width, paramDimension2D.height, paramFloat1, paramFloat2, paramInt);
/*     */   }
/*     */ 
/*     */   public void setArc(Arc2D paramArc2D)
/*     */   {
/* 301 */     setArc(paramArc2D.x, paramArc2D.y, paramArc2D.width, paramArc2D.height, paramArc2D.start, paramArc2D.extent, paramArc2D.type);
/*     */   }
/*     */ 
/*     */   public void setArcByCenter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt)
/*     */   {
/* 320 */     setArc(paramFloat1 - paramFloat3, paramFloat2 - paramFloat3, paramFloat3 * 2.0F, paramFloat3 * 2.0F, paramFloat4, paramFloat5, paramInt);
/*     */   }
/*     */ 
/*     */   public void setArcByTangent(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3, float paramFloat)
/*     */   {
/* 343 */     double d1 = Math.atan2(paramPoint2D1.y - paramPoint2D2.y, paramPoint2D1.x - paramPoint2D2.x);
/*     */ 
/* 345 */     double d2 = Math.atan2(paramPoint2D3.y - paramPoint2D2.y, paramPoint2D3.x - paramPoint2D2.x);
/*     */ 
/* 347 */     double d3 = d2 - d1;
/* 348 */     if (d3 > 3.141592653589793D)
/* 349 */       d2 -= 6.283185307179586D;
/* 350 */     else if (d3 < -3.141592653589793D) {
/* 351 */       d2 += 6.283185307179586D;
/*     */     }
/* 353 */     double d4 = (d1 + d2) / 2.0D;
/* 354 */     double d5 = Math.abs(d2 - d4);
/* 355 */     double d6 = paramFloat / Math.sin(d5);
/* 356 */     double d7 = paramPoint2D2.x + d6 * Math.cos(d4);
/* 357 */     double d8 = paramPoint2D2.y + d6 * Math.sin(d4);
/*     */ 
/* 359 */     if (d1 < d2) {
/* 360 */       d1 -= 1.570796326794897D;
/* 361 */       d2 += 1.570796326794897D;
/*     */     } else {
/* 363 */       d1 += 1.570796326794897D;
/* 364 */       d2 -= 1.570796326794897D;
/*     */     }
/* 366 */     d1 = Math.toDegrees(-d1);
/* 367 */     d2 = Math.toDegrees(-d2);
/* 368 */     d3 = d2 - d1;
/* 369 */     if (d3 < 0.0D)
/* 370 */       d3 += 360.0D;
/*     */     else {
/* 372 */       d3 -= 360.0D;
/*     */     }
/* 374 */     setArcByCenter((float)d7, (float)d8, paramFloat, (float)d1, (float)d3, this.type);
/*     */   }
/*     */ 
/*     */   public void setAngleStart(Point2D paramPoint2D)
/*     */   {
/* 388 */     double d1 = this.height * (paramPoint2D.x - getCenterX());
/* 389 */     double d2 = this.width * (paramPoint2D.y - getCenterY());
/* 390 */     this.start = ((float)-Math.toDegrees(Math.atan2(d2, d1)));
/*     */   }
/*     */ 
/*     */   public void setAngles(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 409 */     double d1 = getCenterX();
/* 410 */     double d2 = getCenterY();
/* 411 */     double d3 = this.width;
/* 412 */     double d4 = this.height;
/*     */ 
/* 416 */     double d5 = Math.atan2(d3 * (d2 - paramFloat2), d4 * (paramFloat1 - d1));
/* 417 */     double d6 = Math.atan2(d3 * (d2 - paramFloat4), d4 * (paramFloat3 - d1));
/* 418 */     d6 -= d5;
/* 419 */     if (d6 <= 0.0D) {
/* 420 */       d6 += 6.283185307179586D;
/*     */     }
/* 422 */     this.start = ((float)Math.toDegrees(d5));
/* 423 */     this.extent = ((float)Math.toDegrees(d6));
/*     */   }
/*     */ 
/*     */   public void setAngles(Point2D paramPoint2D1, Point2D paramPoint2D2)
/*     */   {
/* 442 */     setAngles(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y);
/*     */   }
/*     */ 
/*     */   public void setArcType(int paramInt)
/*     */   {
/* 459 */     if ((paramInt < 0) || (paramInt > 2)) {
/* 460 */       throw new IllegalArgumentException("invalid type for Arc: " + paramInt);
/*     */     }
/* 462 */     this.type = paramInt;
/*     */   }
/*     */ 
/*     */   public void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 472 */     setArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.start, this.extent, this.type);
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 492 */     if (isEmpty()) {
/* 493 */       return new RectBounds(this.x, this.y, this.x + this.width, this.y + this.height);
/*     */     }
/*     */ 
/* 496 */     if (getArcType() == 2) {
/* 497 */       d1 = d2 = d3 = d4 = 0.0D;
/*     */     } else {
/* 499 */       d1 = d2 = 1.0D;
/* 500 */       d3 = d4 = -1.0D;
/*     */     }
/* 502 */     double d5 = 0.0D;
/* 503 */     for (int i = 0; i < 6; i++) {
/* 504 */       if (i < 4)
/*     */       {
/* 506 */         d5 += 90.0D;
/* 507 */         if (!containsAngle((float)d5))
/* 508 */           continue;
/*     */       }
/* 510 */       else if (i == 4)
/*     */       {
/* 512 */         d5 = this.start;
/*     */       }
/*     */       else {
/* 515 */         d5 += this.extent;
/*     */       }
/* 517 */       double d7 = Math.toRadians(-d5);
/* 518 */       double d9 = Math.cos(d7);
/* 519 */       double d10 = Math.sin(d7);
/* 520 */       d1 = Math.min(d1, d9);
/* 521 */       d2 = Math.min(d2, d10);
/* 522 */       d3 = Math.max(d3, d9);
/* 523 */       d4 = Math.max(d4, d10);
/*     */     }
/* 525 */     double d6 = this.width;
/* 526 */     double d8 = this.height;
/* 527 */     double d3 = this.x + (d3 * 0.5D + 0.5D) * d6;
/* 528 */     double d4 = this.y + (d4 * 0.5D + 0.5D) * d8;
/* 529 */     double d1 = this.x + (d1 * 0.5D + 0.5D) * d6;
/* 530 */     double d2 = this.y + (d2 * 0.5D + 0.5D) * d8;
/* 531 */     return new RectBounds((float)d1, (float)d2, (float)d3, (float)d4);
/*     */   }
/*     */ 
/*     */   static float normalizeDegrees(double paramDouble)
/*     */   {
/* 538 */     if (paramDouble > 180.0D) {
/* 539 */       if (paramDouble <= 540.0D) {
/* 540 */         paramDouble -= 360.0D;
/*     */       } else {
/* 542 */         paramDouble = Math.IEEEremainder(paramDouble, 360.0D);
/*     */ 
/* 544 */         if (paramDouble == -180.0D)
/* 545 */           paramDouble = 180.0D;
/*     */       }
/*     */     }
/* 548 */     else if (paramDouble <= -180.0D) {
/* 549 */       if (paramDouble > -540.0D) {
/* 550 */         paramDouble += 360.0D;
/*     */       } else {
/* 552 */         paramDouble = Math.IEEEremainder(paramDouble, 360.0D);
/*     */ 
/* 554 */         if (paramDouble == -180.0D) {
/* 555 */           paramDouble = 180.0D;
/*     */         }
/*     */       }
/*     */     }
/* 559 */     return (float)paramDouble;
/*     */   }
/*     */ 
/*     */   public boolean containsAngle(float paramFloat)
/*     */   {
/* 573 */     double d = this.extent;
/* 574 */     int i = d < 0.0D ? 1 : 0;
/* 575 */     if (i != 0) {
/* 576 */       d = -d;
/*     */     }
/* 578 */     if (d >= 360.0D) {
/* 579 */       return true;
/*     */     }
/* 581 */     paramFloat = normalizeDegrees(paramFloat) - normalizeDegrees(this.start);
/* 582 */     if (i != 0) {
/* 583 */       paramFloat = -paramFloat;
/*     */     }
/* 585 */     if (paramFloat < 0.0D) {
/* 586 */       paramFloat = (float)(paramFloat + 360.0D);
/*     */     }
/*     */ 
/* 589 */     return (paramFloat >= 0.0D) && (paramFloat < d);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 607 */     double d1 = this.width;
/* 608 */     if (d1 <= 0.0D) {
/* 609 */       return false;
/*     */     }
/* 611 */     double d2 = (paramFloat1 - this.x) / d1 - 0.5D;
/* 612 */     double d3 = this.height;
/* 613 */     if (d3 <= 0.0D) {
/* 614 */       return false;
/*     */     }
/* 616 */     double d4 = (paramFloat2 - this.y) / d3 - 0.5D;
/* 617 */     double d5 = d2 * d2 + d4 * d4;
/* 618 */     if (d5 >= 0.25D) {
/* 619 */       return false;
/*     */     }
/* 621 */     double d6 = Math.abs(this.extent);
/* 622 */     if (d6 >= 360.0D) {
/* 623 */       return true;
/*     */     }
/* 625 */     boolean bool1 = containsAngle((float)-Math.toDegrees(Math.atan2(d4, d2)));
/*     */ 
/* 627 */     if (this.type == 2) {
/* 628 */       return bool1;
/*     */     }
/*     */ 
/* 631 */     if (bool1) {
/* 632 */       if (d6 >= 180.0D) {
/* 633 */         return true;
/*     */       }
/*     */ 
/*     */     }
/* 637 */     else if (d6 <= 180.0D) {
/* 638 */       return false;
/*     */     }
/*     */ 
/* 644 */     double d7 = Math.toRadians(-this.start);
/* 645 */     double d8 = Math.cos(d7);
/* 646 */     double d9 = Math.sin(d7);
/* 647 */     d7 += Math.toRadians(-this.extent);
/* 648 */     double d10 = Math.cos(d7);
/* 649 */     double d11 = Math.sin(d7);
/* 650 */     boolean bool2 = Line2D.relativeCCW((float)d8, (float)d9, (float)d10, (float)d11, (float)(2.0D * d2), (float)(2.0D * d4)) * Line2D.relativeCCW((float)d8, (float)d9, (float)d10, (float)d11, 0.0F, 0.0F) >= 0;
/*     */ 
/* 652 */     return bool1 ? false : !bool2 ? true : bool2;
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 669 */     float f1 = this.width;
/* 670 */     float f2 = this.height;
/*     */ 
/* 672 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F) || (f1 <= 0.0F) || (f2 <= 0.0F)) {
/* 673 */       return false;
/*     */     }
/* 675 */     float f3 = this.extent;
/* 676 */     if (f3 == 0.0F) {
/* 677 */       return false;
/*     */     }
/*     */ 
/* 680 */     float f4 = this.x;
/* 681 */     float f5 = this.y;
/* 682 */     float f6 = f4 + f1;
/* 683 */     float f7 = f5 + f2;
/* 684 */     float f8 = paramFloat1 + paramFloat3;
/* 685 */     float f9 = paramFloat2 + paramFloat4;
/*     */ 
/* 688 */     if ((paramFloat1 >= f6) || (paramFloat2 >= f7) || (f8 <= f4) || (f9 <= f5)) {
/* 689 */       return false;
/*     */     }
/*     */ 
/* 693 */     float f10 = getCenterX();
/* 694 */     float f11 = getCenterY();
/* 695 */     Point2D localPoint2D1 = getStartPoint();
/* 696 */     Point2D localPoint2D2 = getEndPoint();
/* 697 */     float f12 = localPoint2D1.x;
/* 698 */     float f13 = localPoint2D1.y;
/* 699 */     float f14 = localPoint2D2.x;
/* 700 */     float f15 = localPoint2D2.y;
/*     */ 
/* 712 */     if ((f11 >= paramFloat2) && (f11 <= f9) && (
/* 713 */       ((f12 < f8) && (f14 < f8) && (f10 < f8) && (f6 > paramFloat1) && (containsAngle(0.0F))) || ((f12 > paramFloat1) && (f14 > paramFloat1) && (f10 > paramFloat1) && (f4 < f8) && (containsAngle(180.0F)))))
/*     */     {
/* 717 */       return true;
/*     */     }
/*     */ 
/* 720 */     if ((f10 >= paramFloat1) && (f10 <= f8) && (
/* 721 */       ((f13 > paramFloat2) && (f15 > paramFloat2) && (f11 > paramFloat2) && (f5 < f9) && (containsAngle(90.0F))) || ((f13 < f9) && (f15 < f9) && (f11 < f9) && (f7 > paramFloat2) && (containsAngle(270.0F)))))
/*     */     {
/* 725 */       return true;
/*     */     }
/*     */ 
/* 736 */     if ((this.type == 2) || (Math.abs(f3) > 180.0F))
/*     */     {
/* 738 */       if ((Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f10, f11, f12, f13)) || (Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f10, f11, f14, f15)))
/*     */       {
/* 741 */         return true;
/*     */       }
/*     */ 
/*     */     }
/* 745 */     else if (Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f12, f13, f14, f15)) {
/* 746 */       return true;
/*     */     }
/*     */ 
/* 751 */     if ((contains(paramFloat1, paramFloat2)) || (contains(paramFloat1 + paramFloat3, paramFloat2)) || (contains(paramFloat1, paramFloat2 + paramFloat4)) || (contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4)))
/*     */     {
/* 753 */       return true;
/*     */     }
/*     */ 
/* 756 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 773 */     if ((!contains(paramFloat1, paramFloat2)) || (!contains(paramFloat1 + paramFloat3, paramFloat2)) || (!contains(paramFloat1, paramFloat2 + paramFloat4)) || (!contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4)))
/*     */     {
/* 777 */       return false;
/*     */     }
/*     */ 
/* 782 */     if ((this.type != 2) || (Math.abs(this.extent) <= 180.0D)) {
/* 783 */       return true;
/*     */     }
/*     */ 
/* 791 */     float f1 = getWidth() / 2.0F;
/* 792 */     float f2 = getHeight() / 2.0F;
/* 793 */     float f3 = paramFloat1 + f1;
/* 794 */     float f4 = paramFloat2 + f2;
/* 795 */     float f5 = (float)Math.toRadians(-this.start);
/* 796 */     float f6 = (float)(f3 + f1 * Math.cos(f5));
/* 797 */     float f7 = (float)(f4 + f2 * Math.sin(f5));
/* 798 */     if (Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f3, f4, f6, f7)) {
/* 799 */       return false;
/*     */     }
/* 801 */     f5 += (float)Math.toRadians(-this.extent);
/* 802 */     f6 = (float)(f3 + f1 * Math.cos(f5));
/* 803 */     f7 = (float)(f4 + f2 * Math.sin(f5));
/* 804 */     return !Shape.intersectsLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f3, f4, f6, f7);
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 824 */     return new ArcIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public Arc2D copy()
/*     */   {
/* 829 */     return new Arc2D(this.x, this.y, this.width, this.height, this.start, this.extent, this.type);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 839 */     int i = Float.floatToIntBits(this.x);
/* 840 */     i += Float.floatToIntBits(this.y) * 37;
/* 841 */     i += Float.floatToIntBits(this.width) * 43;
/* 842 */     i += Float.floatToIntBits(this.height) * 47;
/* 843 */     i += Float.floatToIntBits(this.start) * 53;
/* 844 */     i += Float.floatToIntBits(this.extent) * 59;
/* 845 */     i += getArcType() * 61;
/* 846 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 865 */     if (paramObject == this) return true;
/* 866 */     if ((paramObject instanceof Arc2D)) {
/* 867 */       Arc2D localArc2D = (Arc2D)paramObject;
/* 868 */       return (this.x == localArc2D.x) && (this.y == localArc2D.y) && (this.width == localArc2D.width) && (this.height == localArc2D.height) && (this.start == localArc2D.start) && (this.extent == localArc2D.extent) && (this.type == localArc2D.type);
/*     */     }
/*     */ 
/* 876 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Arc2D
 * JD-Core Version:    0.6.2
 */