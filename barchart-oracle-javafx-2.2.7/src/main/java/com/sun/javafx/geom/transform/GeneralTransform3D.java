/*     */ package com.sun.javafx.geom.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.Vec3f;
/*     */ 
/*     */ public class GeneralTransform3D
/*     */   implements CanTransformVec3d
/*     */ {
/*  40 */   protected double[] mat = new double[16];
/*     */   private boolean identity;
/*     */   private Vec3d tempV3d;
/*     */   private static final double EPSILON_ABSOLUTE = 1.E-05D;
/*     */ 
/*     */   public GeneralTransform3D()
/*     */   {
/*  49 */     setIdentity();
/*     */   }
/*     */ 
/*     */   public boolean isAffine()
/*     */   {
/*  58 */     if ((!isInfOrNaN()) && (almostZero(this.mat[12])) && (almostZero(this.mat[13])) && (almostZero(this.mat[14])) && (almostOne(this.mat[15])))
/*     */     {
/*  64 */       return true;
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D set(GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/*  78 */     System.arraycopy(paramGeneralTransform3D.mat, 0, this.mat, 0, this.mat.length);
/*  79 */     updateState();
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D set(double[] paramArrayOfDouble)
/*     */   {
/*  93 */     System.arraycopy(paramArrayOfDouble, 0, this.mat, 0, this.mat.length);
/*  94 */     updateState();
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public double[] get(double[] paramArrayOfDouble)
/*     */   {
/* 108 */     if (paramArrayOfDouble == null) {
/* 109 */       paramArrayOfDouble = new double[this.mat.length];
/*     */     }
/* 111 */     System.arraycopy(this.mat, 0, paramArrayOfDouble, 0, this.mat.length);
/*     */ 
/* 113 */     return paramArrayOfDouble;
/*     */   }
/*     */ 
/*     */   public double get(int paramInt) {
/* 117 */     assert ((paramInt >= 0) && (paramInt < this.mat.length));
/* 118 */     return this.mat[paramInt];
/*     */   }
/*     */ 
/*     */   public BaseBounds transform(BaseBounds paramBaseBounds1, BaseBounds paramBaseBounds2)
/*     */   {
/* 124 */     if (this.tempV3d == null) {
/* 125 */       this.tempV3d = new Vec3d();
/*     */     }
/* 127 */     return TransformHelper.general3dBoundsTransform(this, paramBaseBounds1, paramBaseBounds2, this.tempV3d);
/*     */   }
/*     */ 
/*     */   public Vec3d transform(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/* 142 */     if (paramVec3d2 == null) {
/* 143 */       paramVec3d2 = new Vec3d();
/*     */     }
/*     */ 
/* 148 */     double d = (float)(this.mat[12] * paramVec3d1.x + this.mat[13] * paramVec3d1.y + this.mat[14] * paramVec3d1.z + this.mat[15]);
/*     */ 
/* 151 */     paramVec3d2.x = ((float)(this.mat[0] * paramVec3d1.x + this.mat[1] * paramVec3d1.y + this.mat[2] * paramVec3d1.z + this.mat[3]));
/*     */ 
/* 153 */     paramVec3d2.y = ((float)(this.mat[4] * paramVec3d1.x + this.mat[5] * paramVec3d1.y + this.mat[6] * paramVec3d1.z + this.mat[7]));
/*     */ 
/* 155 */     paramVec3d2.z = ((float)(this.mat[8] * paramVec3d1.x + this.mat[9] * paramVec3d1.y + this.mat[10] * paramVec3d1.z + this.mat[11]));
/*     */ 
/* 158 */     if (d != 0.0D) {
/* 159 */       paramVec3d2.x /= d;
/* 160 */       paramVec3d2.y /= d;
/* 161 */       paramVec3d2.z /= d;
/*     */     }
/*     */ 
/* 164 */     return paramVec3d2;
/*     */   }
/*     */ 
/*     */   public Vec3d transform(Vec3d paramVec3d)
/*     */   {
/* 178 */     return transform(paramVec3d, paramVec3d);
/*     */   }
/*     */ 
/*     */   public Vec3f transformNormal(Vec3f paramVec3f1, Vec3f paramVec3f2)
/*     */   {
/* 196 */     paramVec3f1.x = ((float)(this.mat[0] * paramVec3f1.x + this.mat[1] * paramVec3f1.y + this.mat[2] * paramVec3f1.z));
/*     */ 
/* 198 */     paramVec3f1.y = ((float)(this.mat[4] * paramVec3f1.x + this.mat[5] * paramVec3f1.y + this.mat[6] * paramVec3f1.z));
/*     */ 
/* 200 */     paramVec3f1.z = ((float)(this.mat[8] * paramVec3f1.x + this.mat[9] * paramVec3f1.y + this.mat[10] * paramVec3f1.z));
/*     */ 
/* 202 */     return paramVec3f2;
/*     */   }
/*     */ 
/*     */   public Vec3f transformNormal(Vec3f paramVec3f)
/*     */   {
/* 218 */     return transformNormal(paramVec3f, paramVec3f);
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D perspective(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 247 */     double d4 = paramDouble1 * 0.5D;
/*     */ 
/* 249 */     double d3 = paramDouble4 - paramDouble3;
/* 250 */     double d1 = Math.sin(d4);
/*     */ 
/* 252 */     double d2 = Math.cos(d4) / d1;
/*     */ 
/* 254 */     this.mat[0] = (d2 / paramDouble2);
/* 255 */     this.mat[5] = d2;
/*     */ 
/* 259 */     this.mat[10] = (-(paramDouble4 + paramDouble3) / d3);
/* 260 */     this.mat[11] = (-2.0D * paramDouble3 * paramDouble4 / d3);
/* 261 */     this.mat[14] = -1.0D;
/*     */     double tmp175_174 = (this.mat[3] = this.mat[4] = this.mat[6] = this.mat[7] = this.mat[8] = this.mat[9] = this.mat[12] = this.mat[13] = this.mat[15] = 0.0D); this.mat[2] = tmp175_174; this.mat[1] = tmp175_174;
/*     */ 
/* 264 */     updateState();
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D ortho(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 291 */     double d1 = 1.0D / (paramDouble2 - paramDouble1);
/* 292 */     double d2 = 1.0D / (paramDouble4 - paramDouble3);
/* 293 */     double d3 = 1.0D / (paramDouble6 - paramDouble5);
/*     */ 
/* 295 */     this.mat[0] = (2.0D * d1);
/* 296 */     this.mat[3] = (-(paramDouble2 + paramDouble1) * d1);
/* 297 */     this.mat[5] = (2.0D * d2);
/* 298 */     this.mat[7] = (-(paramDouble4 + paramDouble3) * d2);
/* 299 */     this.mat[10] = (2.0D * d3);
/* 300 */     this.mat[11] = ((paramDouble6 + paramDouble5) * d3);
/*     */     double tmp172_171 = (this.mat[4] = this.mat[6] = this.mat[8] = this.mat[9] = this.mat[12] = this.mat[13] = this.mat[14] = 0.0D); this.mat[2] = tmp172_171; this.mat[1] = tmp172_171;
/*     */ 
/* 303 */     this.mat[15] = 1.0D;
/*     */ 
/* 305 */     updateState();
/* 306 */     return this;
/*     */   }
/*     */ 
/*     */   public double computeClipZCoord() {
/* 310 */     double d1 = (1.0D - this.mat[15]) / this.mat[14];
/* 311 */     double d2 = this.mat[10] * d1 + this.mat[11];
/* 312 */     return d2;
/*     */   }
/*     */ 
/*     */   public double determinant()
/*     */   {
/* 322 */     return this.mat[0] * (this.mat[5] * (this.mat[10] * this.mat[15] - this.mat[11] * this.mat[14]) - this.mat[6] * (this.mat[9] * this.mat[15] - this.mat[11] * this.mat[13]) + this.mat[7] * (this.mat[9] * this.mat[14] - this.mat[10] * this.mat[13])) - this.mat[1] * (this.mat[4] * (this.mat[10] * this.mat[15] - this.mat[11] * this.mat[14]) - this.mat[6] * (this.mat[8] * this.mat[15] - this.mat[11] * this.mat[12]) + this.mat[7] * (this.mat[8] * this.mat[14] - this.mat[10] * this.mat[12])) + this.mat[2] * (this.mat[4] * (this.mat[9] * this.mat[15] - this.mat[11] * this.mat[13]) - this.mat[5] * (this.mat[8] * this.mat[15] - this.mat[11] * this.mat[12]) + this.mat[7] * (this.mat[8] * this.mat[13] - this.mat[9] * this.mat[12])) - this.mat[3] * (this.mat[4] * (this.mat[9] * this.mat[14] - this.mat[10] * this.mat[13]) - this.mat[5] * (this.mat[8] * this.mat[14] - this.mat[10] * this.mat[12]) + this.mat[6] * (this.mat[8] * this.mat[13] - this.mat[9] * this.mat[12]));
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D invert()
/*     */   {
/* 342 */     return invert(this);
/*     */   }
/*     */ 
/*     */   private GeneralTransform3D invert(GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/* 354 */     double[] arrayOfDouble = new double[16];
/* 355 */     int[] arrayOfInt = new int[4];
/*     */ 
/* 360 */     System.arraycopy(paramGeneralTransform3D.mat, 0, arrayOfDouble, 0, arrayOfDouble.length);
/*     */ 
/* 363 */     if (!luDecomposition(arrayOfDouble, arrayOfInt))
/*     */     {
/* 365 */       throw new SingularMatrixException();
/*     */     }
/*     */ 
/* 371 */     this.mat[0] = 1.0D; this.mat[1] = 0.0D; this.mat[2] = 0.0D; this.mat[3] = 0.0D;
/* 372 */     this.mat[4] = 0.0D; this.mat[5] = 1.0D; this.mat[6] = 0.0D; this.mat[7] = 0.0D;
/* 373 */     this.mat[8] = 0.0D; this.mat[9] = 0.0D; this.mat[10] = 1.0D; this.mat[11] = 0.0D;
/* 374 */     this.mat[12] = 0.0D; this.mat[13] = 0.0D; this.mat[14] = 0.0D; this.mat[15] = 1.0D;
/* 375 */     luBacksubstitution(arrayOfDouble, arrayOfInt, this.mat);
/*     */ 
/* 377 */     updateState();
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */   private static boolean luDecomposition(double[] paramArrayOfDouble, int[] paramArrayOfInt)
/*     */   {
/* 405 */     double[] arrayOfDouble = new double[4];
/*     */ 
/* 413 */     double d2 = 0;
/* 414 */     int j = 0;
/*     */ 
/* 417 */     double d1 = 4;
/*     */     double d3;
/* 418 */     while (d1-- != 0) {
/* 419 */       d3 = 0.0D;
/*     */ 
/* 422 */       i = 4;
/* 423 */       while (i-- != 0) {
/* 424 */         double d4 = paramArrayOfDouble[(d2++)];
/* 425 */         d4 = Math.abs(d4);
/* 426 */         if (d4 > d3) {
/* 427 */           d3 = d4;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 432 */       if (d3 == 0.0D) {
/* 433 */         return false;
/*     */       }
/* 435 */       arrayOfDouble[(j++)] = (1.0D / d3);
/*     */     }
/*     */ 
/* 443 */     int i = 0;
/*     */ 
/* 446 */     for (d1 = 0; d1 < 4; d1++)
/*     */     {
/*     */       int m;
/*     */       double d5;
/*     */       int n;
/*     */       int i1;
/* 452 */       for (d2 = 0; d2 < d1; d2++) {
/* 453 */         m = i + 4 * d2 + d1;
/* 454 */         d5 = paramArrayOfDouble[m];
/* 455 */         d3 = d2;
/* 456 */         n = i + 4 * d2;
/* 457 */         i1 = i + d1;
/* 458 */         while (d3-- != 0) {
/* 459 */           d5 -= paramArrayOfDouble[n] * paramArrayOfDouble[i1];
/* 460 */           n++;
/* 461 */           i1 += 4;
/*     */         }
/* 463 */         paramArrayOfDouble[m] = d5;
/*     */       }
/*     */ 
/* 468 */       double d6 = 0.0D;
/* 469 */       j = -1;
/*     */       double d7;
/* 470 */       for (d2 = d1; d2 < 4; d2++) {
/* 471 */         m = i + 4 * d2 + d1;
/* 472 */         d5 = paramArrayOfDouble[m];
/* 473 */         d3 = d1;
/* 474 */         n = i + 4 * d2;
/* 475 */         i1 = i + d1;
/* 476 */         while (d3-- != 0) {
/* 477 */           d5 -= paramArrayOfDouble[n] * paramArrayOfDouble[i1];
/* 478 */           n++;
/* 479 */           i1 += 4;
/*     */         }
/* 481 */         paramArrayOfDouble[m] = d5;
/*     */ 
/* 484 */         if ((d7 = arrayOfDouble[d2] * Math.abs(d5)) >= d6) {
/* 485 */           d6 = d7;
/* 486 */           j = d2;
/*     */         }
/*     */       }
/*     */ 
/* 490 */       if (j < 0) {
/* 491 */         return false;
/*     */       }
/*     */ 
/* 495 */       if (d1 != j)
/*     */       {
/* 497 */         int k = 4;
/* 498 */         n = i + 4 * j;
/* 499 */         i1 = i + 4 * d1;
/* 500 */         while (k-- != 0) {
/* 501 */           d7 = paramArrayOfDouble[n];
/* 502 */           paramArrayOfDouble[(n++)] = paramArrayOfDouble[i1];
/* 503 */           paramArrayOfDouble[(i1++)] = d7;
/*     */         }
/*     */ 
/* 507 */         arrayOfDouble[j] = arrayOfDouble[d1];
/*     */       }
/*     */ 
/* 511 */       paramArrayOfInt[d1] = j;
/*     */ 
/* 514 */       if (paramArrayOfDouble[(i + 4 * d1 + d1)] == 0.0D) {
/* 515 */         return false;
/*     */       }
/*     */ 
/* 519 */       if (d1 != 3) {
/* 520 */         d7 = 1.0D / paramArrayOfDouble[(i + 4 * d1 + d1)];
/* 521 */         m = i + 4 * (d1 + 1) + d1;
/* 522 */         d2 = 3 - d1;
/* 523 */         while (d2-- != 0) {
/* 524 */           paramArrayOfDouble[m] *= d7;
/* 525 */           m += 4;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 531 */     return true;
/*     */   }
/*     */ 
/*     */   private static void luBacksubstitution(double[] paramArrayOfDouble1, int[] paramArrayOfInt, double[] paramArrayOfDouble2)
/*     */   {
/* 562 */     int i1 = 0;
/*     */ 
/* 565 */     for (int n = 0; n < 4; n++)
/*     */     {
/* 567 */       int i2 = n;
/* 568 */       int j = -1;
/*     */ 
/* 571 */       for (int i = 0; i < 4; i++)
/*     */       {
/* 574 */         int k = paramArrayOfInt[(i1 + i)];
/* 575 */         double d = paramArrayOfDouble2[(i2 + 4 * k)];
/* 576 */         paramArrayOfDouble2[(i2 + 4 * k)] = paramArrayOfDouble2[(i2 + 4 * i)];
/* 577 */         if (j >= 0)
/*     */         {
/* 579 */           i3 = i * 4;
/* 580 */           for (int m = j; m <= i - 1; m++) {
/* 581 */             d -= paramArrayOfDouble1[(i3 + m)] * paramArrayOfDouble2[(i2 + 4 * m)];
/*     */           }
/*     */         }
/* 584 */         if (d != 0.0D) {
/* 585 */           j = i;
/*     */         }
/* 587 */         paramArrayOfDouble2[(i2 + 4 * i)] = d;
/*     */       }
/*     */ 
/* 592 */       int i3 = 12;
/* 593 */       paramArrayOfDouble2[(i2 + 12)] /= paramArrayOfDouble1[(i3 + 3)];
/*     */ 
/* 595 */       i3 -= 4;
/* 596 */       paramArrayOfDouble2[(i2 + 8)] = ((paramArrayOfDouble2[(i2 + 8)] - paramArrayOfDouble1[(i3 + 3)] * paramArrayOfDouble2[(i2 + 12)]) / paramArrayOfDouble1[(i3 + 2)]);
/*     */ 
/* 599 */       i3 -= 4;
/* 600 */       paramArrayOfDouble2[(i2 + 4)] = ((paramArrayOfDouble2[(i2 + 4)] - paramArrayOfDouble1[(i3 + 2)] * paramArrayOfDouble2[(i2 + 8)] - paramArrayOfDouble1[(i3 + 3)] * paramArrayOfDouble2[(i2 + 12)]) / paramArrayOfDouble1[(i3 + 1)]);
/*     */ 
/* 604 */       i3 -= 4;
/* 605 */       paramArrayOfDouble2[(i2 + 0)] = ((paramArrayOfDouble2[(i2 + 0)] - paramArrayOfDouble1[(i3 + 1)] * paramArrayOfDouble2[(i2 + 4)] - paramArrayOfDouble1[(i3 + 2)] * paramArrayOfDouble2[(i2 + 8)] - paramArrayOfDouble1[(i3 + 3)] * paramArrayOfDouble2[(i2 + 12)]) / paramArrayOfDouble1[(i3 + 0)]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D mul(BaseTransform paramBaseTransform)
/*     */   {
/* 627 */     double d17 = paramBaseTransform.getMxx();
/* 628 */     double d18 = paramBaseTransform.getMxy();
/* 629 */     double d19 = paramBaseTransform.getMxz();
/* 630 */     double d20 = paramBaseTransform.getMxt();
/* 631 */     double d21 = paramBaseTransform.getMyx();
/* 632 */     double d22 = paramBaseTransform.getMyy();
/* 633 */     double d23 = paramBaseTransform.getMyz();
/* 634 */     double d24 = paramBaseTransform.getMyt();
/* 635 */     double d25 = paramBaseTransform.getMzx();
/* 636 */     double d26 = paramBaseTransform.getMzy();
/* 637 */     double d27 = paramBaseTransform.getMzz();
/* 638 */     double d28 = paramBaseTransform.getMzt();
/*     */ 
/* 640 */     double d1 = this.mat[0] * d17 + this.mat[1] * d21 + this.mat[2] * d25;
/* 641 */     double d2 = this.mat[0] * d18 + this.mat[1] * d22 + this.mat[2] * d26;
/* 642 */     double d3 = this.mat[0] * d19 + this.mat[1] * d23 + this.mat[2] * d27;
/* 643 */     double d4 = this.mat[0] * d20 + this.mat[1] * d24 + this.mat[2] * d28 + this.mat[3];
/* 644 */     double d5 = this.mat[4] * d17 + this.mat[5] * d21 + this.mat[6] * d25;
/* 645 */     double d6 = this.mat[4] * d18 + this.mat[5] * d22 + this.mat[6] * d26;
/* 646 */     double d7 = this.mat[4] * d19 + this.mat[5] * d23 + this.mat[6] * d27;
/* 647 */     double d8 = this.mat[4] * d20 + this.mat[5] * d24 + this.mat[6] * d28 + this.mat[7];
/* 648 */     double d9 = this.mat[8] * d17 + this.mat[9] * d21 + this.mat[10] * d25;
/* 649 */     double d10 = this.mat[8] * d18 + this.mat[9] * d22 + this.mat[10] * d26;
/* 650 */     double d11 = this.mat[8] * d19 + this.mat[9] * d23 + this.mat[10] * d27;
/* 651 */     double d12 = this.mat[8] * d20 + this.mat[9] * d24 + this.mat[10] * d28 + this.mat[11];
/*     */     double d15;
/*     */     double d14;
/*     */     double d13;
/*     */     double d16;
/* 652 */     if (isAffine()) {
/* 653 */       d13 = d14 = d15 = 0.0D;
/* 654 */       d16 = 1.0D;
/*     */     }
/*     */     else {
/* 657 */       d13 = this.mat[12] * d17 + this.mat[13] * d21 + this.mat[14] * d25;
/* 658 */       d14 = this.mat[12] * d18 + this.mat[13] * d22 + this.mat[14] * d26;
/* 659 */       d15 = this.mat[12] * d19 + this.mat[13] * d23 + this.mat[14] * d27;
/* 660 */       d16 = this.mat[12] * d20 + this.mat[13] * d24 + this.mat[14] * d28 + this.mat[15];
/*     */     }
/*     */ 
/* 663 */     this.mat[0] = d1;
/* 664 */     this.mat[1] = d2;
/* 665 */     this.mat[2] = d3;
/* 666 */     this.mat[3] = d4;
/* 667 */     this.mat[4] = d5;
/* 668 */     this.mat[5] = d6;
/* 669 */     this.mat[6] = d7;
/* 670 */     this.mat[7] = d8;
/* 671 */     this.mat[8] = d9;
/* 672 */     this.mat[9] = d10;
/* 673 */     this.mat[10] = d11;
/* 674 */     this.mat[11] = d12;
/* 675 */     this.mat[12] = d13;
/* 676 */     this.mat[13] = d14;
/* 677 */     this.mat[14] = d15;
/* 678 */     this.mat[15] = d16;
/*     */ 
/* 680 */     updateState();
/* 681 */     return this;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D mul(GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/*     */     double d5;
/*     */     double d6;
/*     */     double d7;
/*     */     double d8;
/*     */     double d9;
/*     */     double d10;
/*     */     double d11;
/*     */     double d12;
/*     */     double d15;
/*     */     double d14;
/*     */     double d13;
/*     */     double d16;
/* 698 */     if (paramGeneralTransform3D.isAffine()) {
/* 699 */       d1 = this.mat[0] * paramGeneralTransform3D.mat[0] + this.mat[1] * paramGeneralTransform3D.mat[4] + this.mat[2] * paramGeneralTransform3D.mat[8];
/* 700 */       d2 = this.mat[0] * paramGeneralTransform3D.mat[1] + this.mat[1] * paramGeneralTransform3D.mat[5] + this.mat[2] * paramGeneralTransform3D.mat[9];
/* 701 */       d3 = this.mat[0] * paramGeneralTransform3D.mat[2] + this.mat[1] * paramGeneralTransform3D.mat[6] + this.mat[2] * paramGeneralTransform3D.mat[10];
/* 702 */       d4 = this.mat[0] * paramGeneralTransform3D.mat[3] + this.mat[1] * paramGeneralTransform3D.mat[7] + this.mat[2] * paramGeneralTransform3D.mat[11] + this.mat[3];
/* 703 */       d5 = this.mat[4] * paramGeneralTransform3D.mat[0] + this.mat[5] * paramGeneralTransform3D.mat[4] + this.mat[6] * paramGeneralTransform3D.mat[8];
/* 704 */       d6 = this.mat[4] * paramGeneralTransform3D.mat[1] + this.mat[5] * paramGeneralTransform3D.mat[5] + this.mat[6] * paramGeneralTransform3D.mat[9];
/* 705 */       d7 = this.mat[4] * paramGeneralTransform3D.mat[2] + this.mat[5] * paramGeneralTransform3D.mat[6] + this.mat[6] * paramGeneralTransform3D.mat[10];
/* 706 */       d8 = this.mat[4] * paramGeneralTransform3D.mat[3] + this.mat[5] * paramGeneralTransform3D.mat[7] + this.mat[6] * paramGeneralTransform3D.mat[11] + this.mat[7];
/* 707 */       d9 = this.mat[8] * paramGeneralTransform3D.mat[0] + this.mat[9] * paramGeneralTransform3D.mat[4] + this.mat[10] * paramGeneralTransform3D.mat[8];
/* 708 */       d10 = this.mat[8] * paramGeneralTransform3D.mat[1] + this.mat[9] * paramGeneralTransform3D.mat[5] + this.mat[10] * paramGeneralTransform3D.mat[9];
/* 709 */       d11 = this.mat[8] * paramGeneralTransform3D.mat[2] + this.mat[9] * paramGeneralTransform3D.mat[6] + this.mat[10] * paramGeneralTransform3D.mat[10];
/* 710 */       d12 = this.mat[8] * paramGeneralTransform3D.mat[3] + this.mat[9] * paramGeneralTransform3D.mat[7] + this.mat[10] * paramGeneralTransform3D.mat[11] + this.mat[11];
/* 711 */       if (isAffine()) {
/* 712 */         d13 = d14 = d15 = 0.0D;
/* 713 */         d16 = 1.0D;
/*     */       }
/*     */       else {
/* 716 */         d13 = this.mat[12] * paramGeneralTransform3D.mat[0] + this.mat[13] * paramGeneralTransform3D.mat[4] + this.mat[14] * paramGeneralTransform3D.mat[8];
/*     */ 
/* 718 */         d14 = this.mat[12] * paramGeneralTransform3D.mat[1] + this.mat[13] * paramGeneralTransform3D.mat[5] + this.mat[14] * paramGeneralTransform3D.mat[9];
/*     */ 
/* 720 */         d15 = this.mat[12] * paramGeneralTransform3D.mat[2] + this.mat[13] * paramGeneralTransform3D.mat[6] + this.mat[14] * paramGeneralTransform3D.mat[10];
/*     */ 
/* 722 */         d16 = this.mat[12] * paramGeneralTransform3D.mat[3] + this.mat[13] * paramGeneralTransform3D.mat[7] + this.mat[14] * paramGeneralTransform3D.mat[11] + this.mat[15];
/*     */       }
/*     */     }
/*     */     else {
/* 726 */       d1 = this.mat[0] * paramGeneralTransform3D.mat[0] + this.mat[1] * paramGeneralTransform3D.mat[4] + this.mat[2] * paramGeneralTransform3D.mat[8] + this.mat[3] * paramGeneralTransform3D.mat[12];
/*     */ 
/* 728 */       d2 = this.mat[0] * paramGeneralTransform3D.mat[1] + this.mat[1] * paramGeneralTransform3D.mat[5] + this.mat[2] * paramGeneralTransform3D.mat[9] + this.mat[3] * paramGeneralTransform3D.mat[13];
/*     */ 
/* 730 */       d3 = this.mat[0] * paramGeneralTransform3D.mat[2] + this.mat[1] * paramGeneralTransform3D.mat[6] + this.mat[2] * paramGeneralTransform3D.mat[10] + this.mat[3] * paramGeneralTransform3D.mat[14];
/*     */ 
/* 732 */       d4 = this.mat[0] * paramGeneralTransform3D.mat[3] + this.mat[1] * paramGeneralTransform3D.mat[7] + this.mat[2] * paramGeneralTransform3D.mat[11] + this.mat[3] * paramGeneralTransform3D.mat[15];
/*     */ 
/* 734 */       d5 = this.mat[4] * paramGeneralTransform3D.mat[0] + this.mat[5] * paramGeneralTransform3D.mat[4] + this.mat[6] * paramGeneralTransform3D.mat[8] + this.mat[7] * paramGeneralTransform3D.mat[12];
/*     */ 
/* 736 */       d6 = this.mat[4] * paramGeneralTransform3D.mat[1] + this.mat[5] * paramGeneralTransform3D.mat[5] + this.mat[6] * paramGeneralTransform3D.mat[9] + this.mat[7] * paramGeneralTransform3D.mat[13];
/*     */ 
/* 738 */       d7 = this.mat[4] * paramGeneralTransform3D.mat[2] + this.mat[5] * paramGeneralTransform3D.mat[6] + this.mat[6] * paramGeneralTransform3D.mat[10] + this.mat[7] * paramGeneralTransform3D.mat[14];
/*     */ 
/* 740 */       d8 = this.mat[4] * paramGeneralTransform3D.mat[3] + this.mat[5] * paramGeneralTransform3D.mat[7] + this.mat[6] * paramGeneralTransform3D.mat[11] + this.mat[7] * paramGeneralTransform3D.mat[15];
/*     */ 
/* 742 */       d9 = this.mat[8] * paramGeneralTransform3D.mat[0] + this.mat[9] * paramGeneralTransform3D.mat[4] + this.mat[10] * paramGeneralTransform3D.mat[8] + this.mat[11] * paramGeneralTransform3D.mat[12];
/*     */ 
/* 744 */       d10 = this.mat[8] * paramGeneralTransform3D.mat[1] + this.mat[9] * paramGeneralTransform3D.mat[5] + this.mat[10] * paramGeneralTransform3D.mat[9] + this.mat[11] * paramGeneralTransform3D.mat[13];
/*     */ 
/* 746 */       d11 = this.mat[8] * paramGeneralTransform3D.mat[2] + this.mat[9] * paramGeneralTransform3D.mat[6] + this.mat[10] * paramGeneralTransform3D.mat[10] + this.mat[11] * paramGeneralTransform3D.mat[14];
/*     */ 
/* 749 */       d12 = this.mat[8] * paramGeneralTransform3D.mat[3] + this.mat[9] * paramGeneralTransform3D.mat[7] + this.mat[10] * paramGeneralTransform3D.mat[11] + this.mat[11] * paramGeneralTransform3D.mat[15];
/*     */ 
/* 751 */       if (isAffine()) {
/* 752 */         d13 = paramGeneralTransform3D.mat[12];
/* 753 */         d14 = paramGeneralTransform3D.mat[13];
/* 754 */         d15 = paramGeneralTransform3D.mat[14];
/* 755 */         d16 = paramGeneralTransform3D.mat[15];
/*     */       } else {
/* 757 */         d13 = this.mat[12] * paramGeneralTransform3D.mat[0] + this.mat[13] * paramGeneralTransform3D.mat[4] + this.mat[14] * paramGeneralTransform3D.mat[8] + this.mat[15] * paramGeneralTransform3D.mat[12];
/*     */ 
/* 759 */         d14 = this.mat[12] * paramGeneralTransform3D.mat[1] + this.mat[13] * paramGeneralTransform3D.mat[5] + this.mat[14] * paramGeneralTransform3D.mat[9] + this.mat[15] * paramGeneralTransform3D.mat[13];
/*     */ 
/* 761 */         d15 = this.mat[12] * paramGeneralTransform3D.mat[2] + this.mat[13] * paramGeneralTransform3D.mat[6] + this.mat[14] * paramGeneralTransform3D.mat[10] + this.mat[15] * paramGeneralTransform3D.mat[14];
/*     */ 
/* 763 */         d16 = this.mat[12] * paramGeneralTransform3D.mat[3] + this.mat[13] * paramGeneralTransform3D.mat[7] + this.mat[14] * paramGeneralTransform3D.mat[11] + this.mat[15] * paramGeneralTransform3D.mat[15];
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 768 */     this.mat[0] = d1;
/* 769 */     this.mat[1] = d2;
/* 770 */     this.mat[2] = d3;
/* 771 */     this.mat[3] = d4;
/* 772 */     this.mat[4] = d5;
/* 773 */     this.mat[5] = d6;
/* 774 */     this.mat[6] = d7;
/* 775 */     this.mat[7] = d8;
/* 776 */     this.mat[8] = d9;
/* 777 */     this.mat[9] = d10;
/* 778 */     this.mat[10] = d11;
/* 779 */     this.mat[11] = d12;
/* 780 */     this.mat[12] = d13;
/* 781 */     this.mat[13] = d14;
/* 782 */     this.mat[14] = d15;
/* 783 */     this.mat[15] = d16;
/*     */ 
/* 785 */     updateState();
/* 786 */     return this;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D setIdentity()
/*     */   {
/* 795 */     this.mat[0] = 1.0D; this.mat[1] = 0.0D; this.mat[2] = 0.0D; this.mat[3] = 0.0D;
/* 796 */     this.mat[4] = 0.0D; this.mat[5] = 1.0D; this.mat[6] = 0.0D; this.mat[7] = 0.0D;
/* 797 */     this.mat[8] = 0.0D; this.mat[9] = 0.0D; this.mat[10] = 1.0D; this.mat[11] = 0.0D;
/* 798 */     this.mat[12] = 0.0D; this.mat[13] = 0.0D; this.mat[14] = 0.0D; this.mat[15] = 1.0D;
/* 799 */     this.identity = true;
/* 800 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isIdentity()
/*     */   {
/* 809 */     return this.identity;
/*     */   }
/*     */ 
/*     */   private void updateState()
/*     */   {
/* 814 */     this.identity = ((this.mat[0] == 1.0D) && (this.mat[5] == 1.0D) && (this.mat[10] == 1.0D) && (this.mat[15] == 1.0D) && (this.mat[1] == 0.0D) && (this.mat[2] == 0.0D) && (this.mat[3] == 0.0D) && (this.mat[4] == 0.0D) && (this.mat[6] == 0.0D) && (this.mat[7] == 0.0D) && (this.mat[8] == 0.0D) && (this.mat[9] == 0.0D) && (this.mat[11] == 0.0D) && (this.mat[12] == 0.0D) && (this.mat[13] == 0.0D) && (this.mat[14] == 0.0D));
/*     */   }
/*     */ 
/*     */   boolean isInfOrNaN()
/*     */   {
/* 828 */     double d = 0.0D;
/* 829 */     for (int i = 0; i < this.mat.length; i++) {
/* 830 */       d *= this.mat[i];
/*     */     }
/*     */ 
/* 833 */     return d != 0.0D;
/*     */   }
/*     */ 
/*     */   static boolean almostZero(double paramDouble)
/*     */   {
/* 839 */     return (paramDouble < 1.E-05D) && (paramDouble > -1.E-05D);
/*     */   }
/*     */ 
/*     */   static boolean almostOne(double paramDouble) {
/* 843 */     return (paramDouble < 1.00001D) && (paramDouble > 0.9999900000000001D);
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D copy() {
/* 847 */     GeneralTransform3D localGeneralTransform3D = new GeneralTransform3D();
/* 848 */     localGeneralTransform3D.set(this);
/* 849 */     return localGeneralTransform3D;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 858 */     return this.mat[0] + ", " + this.mat[1] + ", " + this.mat[2] + ", " + this.mat[3] + "\n" + this.mat[4] + ", " + this.mat[5] + ", " + this.mat[6] + ", " + this.mat[7] + "\n" + this.mat[8] + ", " + this.mat[9] + ", " + this.mat[10] + ", " + this.mat[11] + "\n" + this.mat[12] + ", " + this.mat[13] + ", " + this.mat[14] + ", " + this.mat[15] + "\n";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.transform.GeneralTransform3D
 * JD-Core Version:    0.6.2
 */