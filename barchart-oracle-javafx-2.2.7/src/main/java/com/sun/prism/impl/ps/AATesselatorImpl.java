/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Line2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.Vec3f;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.util.tess.Tess;
/*     */ import com.sun.prism.util.tess.Tessellator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ final class AATesselatorImpl
/*     */ {
/*     */   private static final float EPSILON = 1.0E-04F;
/*     */   private final AATessCallbacks listener;
/*     */   private final Tessellator tess;
/*  28 */   private final float[] coords = new float[6];
/*     */ 
/*     */   AATesselatorImpl() {
/*  31 */     this.listener = new AATessCallbacks();
/*  32 */     this.tess = Tess.newTess();
/*  33 */     Tess.tessCallback(this.tess, 100100, this.listener);
/*  34 */     Tess.tessCallback(this.tess, 100101, this.listener);
/*  35 */     Tess.tessCallback(this.tess, 100102, this.listener);
/*  36 */     Tess.tessCallback(this.tess, 100105, this.listener);
/*  37 */     Tess.tessCallback(this.tess, 100103, this.listener);
/*     */ 
/*  40 */     Tess.tessCallback(this.tess, 100104, this.listener);
/*     */   }
/*     */ 
/*     */   int[] generate(Shape paramShape, VertexBuffer paramVertexBuffer1, VertexBuffer paramVertexBuffer2)
/*     */   {
/*  50 */     this.listener.setVertexBuffer(paramVertexBuffer1);
/*     */ 
/*  52 */     BaseTransform localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  53 */     PathIterator localPathIterator = paramShape.getPathIterator(localBaseTransform);
/*     */ 
/*  55 */     ArrayList localArrayList = new ArrayList();
/*  56 */     int i = 0;
/*  57 */     float f1 = 0.0F;
/*  58 */     float f2 = 0.0F;
/*  59 */     float f3 = 0.0F;
/*  60 */     float f4 = 0.0F;
/*  61 */     float f5 = 0.0F;
/*  62 */     float f6 = 0.0F;
/*  63 */     float f7 = 0.0F;
/*  64 */     float f8 = 0.0F;
/*  65 */     float f9 = 0.0F;
/*  66 */     int j = 0;
/*  67 */     while (!localPathIterator.isDone()) {
/*  68 */       k = localPathIterator.currentSegment(this.coords);
/*     */       int n;
/*  69 */       switch (k) {
/*     */       case 0:
/*  71 */         if (j != 0)
/*     */         {
/*  74 */           return null;
/*     */         }
/*  76 */         j = 1;
/*  77 */         f2 = f6 = f4 = this.coords[0];
/*  78 */         f3 = f7 = f5 = this.coords[1];
/*  79 */         localArrayList.add(new MoveTo(f4, f5));
/*  80 */         break;
/*     */       case 1:
/*  82 */         if (!hasInfOrNaN(this.coords, 2)) {
/*  83 */           j = 1;
/*  84 */           f4 = this.coords[0];
/*  85 */           f5 = this.coords[1];
/*  86 */           if ((f4 != f6) || (f5 != f7)) {
/*  87 */             f1 += f4 * f7 - f6 * f5;
/*  88 */             localArrayList.add(new LineTo(f6, f7, f4, f5));
/*     */           }
/*  90 */           f6 = f4;
/*  91 */           f7 = f5; } break;
/*     */       case 2:
/*  95 */         float f10 = this.coords[0];
/*  96 */         float f11 = this.coords[1];
/*  97 */         this.coords[4] = this.coords[2];
/*  98 */         this.coords[5] = this.coords[3];
/*  99 */         this.coords[0] = ((f6 + 2.0F * f10) / 3.0F);
/* 100 */         this.coords[1] = ((f7 + 2.0F * f11) / 3.0F);
/* 101 */         this.coords[2] = ((this.coords[4] + 2.0F * f10) / 3.0F);
/* 102 */         this.coords[3] = ((this.coords[5] + 2.0F * f11) / 3.0F);
/* 103 */         if (!hasInfOrNaN(this.coords, 6)) {
/* 104 */           j = 1;
/* 105 */           f8 = f6;
/* 106 */           f9 = f7;
/* 107 */           for (n = 0; n < 6; n += 2) {
/* 108 */             f4 = this.coords[n];
/* 109 */             f5 = this.coords[(n + 1)];
/* 110 */             f1 += f4 * f7 - f6 * f5;
/* 111 */             f6 = f4;
/* 112 */             f7 = f5;
/*     */           }
/* 114 */           localArrayList.add(new CubicTo(f8, f9, this.coords[0], this.coords[1], this.coords[2], this.coords[3], f4, f5)); } break;
/*     */       case 3:
/* 139 */         if (!hasInfOrNaN(this.coords, 6)) {
/* 140 */           j = 1;
/* 141 */           f8 = f6;
/* 142 */           f9 = f7;
/* 143 */           for (n = 0; n < 6; n += 2) {
/* 144 */             f4 = this.coords[n];
/* 145 */             f5 = this.coords[(n + 1)];
/* 146 */             f1 += f4 * f7 - f6 * f5;
/* 147 */             f6 = f4;
/* 148 */             f7 = f5;
/*     */           }
/* 150 */           localArrayList.add(new CubicTo(f8, f9, this.coords[0], this.coords[1], this.coords[2], this.coords[3], f4, f5)); } break;
/*     */       case 4:
/* 157 */         if (j != 0) {
/* 158 */           j = 0;
/* 159 */           f4 = f2;
/* 160 */           f5 = f3;
/* 161 */           localArrayList.add(new Close(f6, f7, f2, f3)); } break;
/*     */       default:
/* 165 */         throw new InternalError("Unknown segment type");
/*     */       }
/* 167 */       localPathIterator.next();
/*     */     }
/* 169 */     if (j != 0)
/* 170 */       localArrayList.add(new Close(f6, f7, f2, f3));
/*     */     Segment localSegment1;
/* 174 */     for (int k = 0; k < localArrayList.size(); k++) {
/* 175 */       localSegment1 = (Segment)localArrayList.get(k);
/* 176 */       for (int m = k + 1; m < localArrayList.size(); m++) {
/* 177 */         Segment localSegment2 = (Segment)localArrayList.get(m);
/* 178 */         if (localSegment1.overlaps(localSegment2))
/*     */         {
/* 180 */           return null;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 187 */     Tess.tessProperty(this.tess, 100140, localPathIterator.getWindingRule() == 1 ? 100131.0D : 100130.0D);
/*     */ 
/* 190 */     Tess.tessBeginPolygon(this.tess, null);
/* 191 */     for (k = 0; k < localArrayList.size(); k++) {
/* 192 */       localSegment1 = (Segment)localArrayList.get(k);
/* 193 */       if (f1 < 0.0F)
/*     */       {
/* 195 */         localSegment1.convex = (!localSegment1.convex);
/*     */       }
/* 197 */       i += localSegment1.emitVertices(this.tess, paramVertexBuffer2);
/*     */     }
/* 199 */     Tess.tessEndPolygon(this.tess);
/*     */ 
/* 201 */     int[] arrayOfInt = new int[2];
/* 202 */     arrayOfInt[0] = this.listener.getNumVerts();
/* 203 */     arrayOfInt[1] = i;
/* 204 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   static boolean pointOnLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 825 */     paramFloat5 -= paramFloat3;
/* 826 */     paramFloat6 -= paramFloat4;
/*     */ 
/* 828 */     paramFloat1 -= paramFloat3;
/* 829 */     paramFloat2 -= paramFloat4;
/* 830 */     float f1 = paramFloat1 * paramFloat5 + paramFloat2 * paramFloat6;
/*     */ 
/* 834 */     float f2 = f1 * f1 / (paramFloat5 * paramFloat5 + paramFloat6 * paramFloat6);
/*     */ 
/* 837 */     float f3 = paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 - f2;
/* 838 */     return f3 < 1.0E-04F;
/*     */   }
/*     */ 
/*     */   static boolean linesCross(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 852 */     if ((pointOnLine(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat7, paramFloat8)) || (pointOnLine(paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8)) || (pointOnLine(paramFloat5, paramFloat6, paramFloat1, paramFloat2, paramFloat3, paramFloat4)) || (pointOnLine(paramFloat7, paramFloat8, paramFloat1, paramFloat2, paramFloat3, paramFloat4)))
/*     */     {
/* 859 */       return false;
/*     */     }
/*     */ 
/* 862 */     float f1 = paramFloat3 - paramFloat1;
/* 863 */     float f2 = paramFloat4 - paramFloat2;
/* 864 */     float f3 = paramFloat5 - paramFloat7;
/* 865 */     float f4 = paramFloat6 - paramFloat8;
/* 866 */     float f5 = f2 * f3 - f1 * f4;
/* 867 */     if (isCloseToZero(f5))
/*     */     {
/* 869 */       return false;
/*     */     }
/* 871 */     float f6 = paramFloat1 - paramFloat5;
/* 872 */     float f7 = paramFloat2 - paramFloat6;
/* 873 */     float f8 = f4 * f6 - f3 * f7;
/* 874 */     if (f5 > 0.0F) {
/* 875 */       if ((f8 < 0.0F) || (f8 > f5)) {
/* 876 */         return false;
/*     */       }
/*     */     }
/* 879 */     else if ((f8 > 0.0F) || (f8 < f5)) {
/* 880 */       return false;
/*     */     }
/*     */ 
/* 883 */     float f9 = f1 * f7 - f2 * f6;
/* 884 */     if (f5 > 0.0F) {
/* 885 */       if ((f9 < 0.0F) || (f9 > f5)) {
/* 886 */         return false;
/*     */       }
/*     */     }
/* 889 */     else if ((f9 > 0.0F) || (f9 < f5)) {
/* 890 */       return false;
/*     */     }
/*     */ 
/* 893 */     return true;
/*     */   }
/*     */ 
/*     */   static boolean triangleContainsPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 909 */     paramFloat1 -= paramFloat3;
/* 910 */     paramFloat2 -= paramFloat4;
/* 911 */     paramFloat5 -= paramFloat3;
/* 912 */     paramFloat6 -= paramFloat4;
/* 913 */     paramFloat7 -= paramFloat3;
/* 914 */     paramFloat8 -= paramFloat4;
/*     */ 
/* 916 */     float f1 = paramFloat5 * paramFloat8 - paramFloat7 * paramFloat6;
/* 917 */     if (isCloseToZero(f1))
/*     */     {
/* 919 */       return false;
/*     */     }
/*     */ 
/* 922 */     float f2 = (paramFloat1 * paramFloat8 - paramFloat2 * paramFloat7) / f1;
/* 923 */     float f3 = (paramFloat1 * paramFloat6 - paramFloat2 * paramFloat5) / -f1;
/*     */ 
/* 925 */     return (f2 > 0.0F) && (f3 > 0.0F) && (f2 + f3 < 1.0F);
/*     */   }
/*     */ 
/*     */   static boolean isCloseToZero(float paramFloat) {
/* 929 */     return (paramFloat < 1.0E-04F) && (paramFloat > -1.0E-04F);
/*     */   }
/*     */ 
/*     */   static boolean hasInfOrNaN(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 940 */     for (int i = 0; i < paramInt; i++) {
/* 941 */       if ((Float.isInfinite(paramArrayOfFloat[i])) || (Float.isNaN(paramArrayOfFloat[i]))) {
/* 942 */         return true;
/*     */       }
/*     */     }
/* 945 */     return false;
/*     */   }
/*     */ 
/*     */   static void emitVert(Tessellator paramTessellator, float paramFloat1, float paramFloat2) {
/* 949 */     double[] arrayOfDouble = { paramFloat1, paramFloat2, 0.0D };
/* 950 */     Tess.tessVertex(paramTessellator, arrayOfDouble, 0, arrayOfDouble);
/*     */   }
/*     */ 
/*     */   private static class Close extends AATesselatorImpl.Segment
/*     */   {
/*     */     private final float x1;
/*     */     private final float y1;
/*     */     private final float x2;
/*     */     private final float y2;
/*     */ 
/*     */     Close(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 792 */       super();
/* 793 */       this.x1 = paramFloat1;
/* 794 */       this.y1 = paramFloat2;
/* 795 */       this.x2 = paramFloat3;
/* 796 */       this.y2 = paramFloat4;
/*     */     }
/*     */     int getEdges(float[] paramArrayOfFloat) {
/* 799 */       paramArrayOfFloat[0] = this.x1;
/* 800 */       paramArrayOfFloat[1] = this.y1;
/* 801 */       paramArrayOfFloat[2] = this.x2;
/* 802 */       paramArrayOfFloat[3] = this.y2;
/* 803 */       return 1;
/*     */     }
/*     */ 
/*     */     int emitVertices(Tessellator paramTessellator, VertexBuffer paramVertexBuffer)
/*     */     {
/* 808 */       Tess.tessEndContour(paramTessellator);
/* 809 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class CubicTo extends AATesselatorImpl.Segment
/*     */   {
/*     */     private final float x1;
/*     */     private final float y1;
/*     */     private final float ctrlx1;
/*     */     private final float ctrly1;
/*     */     private final float ctrlx2;
/*     */     private final float ctrly2;
/*     */     private final float x2;
/*     */     private final float y2;
/*     */     private final int ccw1;
/*     */     private final int ccw2;
/* 403 */     private int hullType = -1;
/*     */ 
/* 528 */     private static final Vec3f b0 = new Vec3f();
/* 529 */     private static final Vec3f b1 = new Vec3f();
/* 530 */     private static final Vec3f b2 = new Vec3f();
/* 531 */     private static final Vec3f b3 = new Vec3f();
/* 532 */     private static final Vec3f m0 = new Vec3f();
/* 533 */     private static final Vec3f m1 = new Vec3f();
/* 534 */     private static final Vec3f m2 = new Vec3f();
/* 535 */     private static final Vec3f m3 = new Vec3f();
/* 536 */     private static final Vec3f tmp = new Vec3f();
/*     */     private static final float ONE_THIRD = 0.3333333F;
/*     */     private static final float TWO_THIRDS = 0.6666667F;
/*     */ 
/*     */     CubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */     {
/* 410 */       super();
/* 411 */       this.x1 = paramFloat1;
/* 412 */       this.y1 = paramFloat2;
/* 413 */       this.ctrlx1 = paramFloat3;
/* 414 */       this.ctrly1 = paramFloat4;
/* 415 */       this.ctrlx2 = paramFloat5;
/* 416 */       this.ctrly2 = paramFloat6;
/* 417 */       this.x2 = paramFloat7;
/* 418 */       this.y2 = paramFloat8;
/* 419 */       this.ccw1 = Line2D.relativeCCW(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8);
/* 420 */       this.ccw2 = Line2D.relativeCCW(paramFloat1, paramFloat2, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/* 421 */       this.convex = ((this.ccw1 > 0) && (this.ccw2 > 0));
/*     */     }
/*     */ 
/*     */     private int calculateHullType() {
/* 425 */       if (AATesselatorImpl.triangleContainsPoint(this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2))
/*     */       {
/* 427 */         return 2;
/* 428 */       }if (AATesselatorImpl.triangleContainsPoint(this.x2, this.y2, this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2))
/*     */       {
/* 430 */         return 3;
/* 431 */       }if (AATesselatorImpl.triangleContainsPoint(this.ctrlx1, this.ctrly1, this.x1, this.y1, this.ctrlx2, this.ctrly2, this.x2, this.y2))
/*     */       {
/* 433 */         return 4;
/* 434 */       }if (AATesselatorImpl.triangleContainsPoint(this.ctrlx2, this.ctrly2, this.x1, this.y1, this.ctrlx1, this.ctrly1, this.x2, this.y2))
/*     */       {
/* 436 */         return 5;
/*     */       }
/* 438 */       if (this.ccw1 == this.ccw2)
/*     */       {
/* 440 */         return 0;
/*     */       }
/*     */ 
/* 443 */       return 1;
/*     */     }
/*     */ 
/*     */     int getEdges(float[] paramArrayOfFloat)
/*     */     {
/* 450 */       if (this.hullType < 0) {
/* 451 */         this.hullType = calculateHullType();
/*     */       }
/*     */ 
/* 456 */       switch (this.hullType)
/*     */       {
/*     */       case 0:
/* 459 */         paramArrayOfFloat[0] = this.x1;
/* 460 */         paramArrayOfFloat[1] = this.y1;
/* 461 */         paramArrayOfFloat[2] = this.ctrlx1;
/* 462 */         paramArrayOfFloat[3] = this.ctrly1;
/* 463 */         paramArrayOfFloat[4] = this.ctrlx2;
/* 464 */         paramArrayOfFloat[5] = this.ctrly2;
/* 465 */         paramArrayOfFloat[6] = this.x1;
/* 466 */         paramArrayOfFloat[7] = this.y1;
/* 467 */         paramArrayOfFloat[8] = this.ctrlx2;
/* 468 */         paramArrayOfFloat[9] = this.ctrly2;
/* 469 */         paramArrayOfFloat[10] = this.x2;
/* 470 */         paramArrayOfFloat[11] = this.y2;
/* 471 */         return 6;
/*     */       case 1:
/* 474 */         paramArrayOfFloat[0] = this.x1;
/* 475 */         paramArrayOfFloat[1] = this.y1;
/* 476 */         paramArrayOfFloat[2] = this.ctrlx1;
/* 477 */         paramArrayOfFloat[3] = this.ctrly1;
/* 478 */         paramArrayOfFloat[4] = this.ctrlx2;
/* 479 */         paramArrayOfFloat[5] = this.ctrly2;
/* 480 */         paramArrayOfFloat[6] = this.ctrlx1;
/* 481 */         paramArrayOfFloat[7] = this.ctrly1;
/* 482 */         paramArrayOfFloat[8] = this.ctrlx2;
/* 483 */         paramArrayOfFloat[9] = this.ctrly2;
/* 484 */         paramArrayOfFloat[10] = this.x2;
/* 485 */         paramArrayOfFloat[11] = this.y2;
/* 486 */         return 6;
/*     */       case 2:
/* 489 */         paramArrayOfFloat[0] = this.ctrlx1;
/* 490 */         paramArrayOfFloat[1] = this.ctrly1;
/* 491 */         paramArrayOfFloat[2] = this.ctrlx2;
/* 492 */         paramArrayOfFloat[3] = this.ctrly2;
/* 493 */         paramArrayOfFloat[4] = this.x2;
/* 494 */         paramArrayOfFloat[5] = this.y2;
/* 495 */         return 3;
/*     */       case 3:
/* 498 */         paramArrayOfFloat[0] = this.x1;
/* 499 */         paramArrayOfFloat[1] = this.y1;
/* 500 */         paramArrayOfFloat[2] = this.ctrlx1;
/* 501 */         paramArrayOfFloat[3] = this.ctrly1;
/* 502 */         paramArrayOfFloat[4] = this.ctrlx2;
/* 503 */         paramArrayOfFloat[5] = this.ctrly2;
/* 504 */         return 3;
/*     */       case 4:
/* 507 */         paramArrayOfFloat[0] = this.x1;
/* 508 */         paramArrayOfFloat[1] = this.y1;
/* 509 */         paramArrayOfFloat[2] = this.ctrlx2;
/* 510 */         paramArrayOfFloat[3] = this.ctrly2;
/* 511 */         paramArrayOfFloat[4] = this.x2;
/* 512 */         paramArrayOfFloat[5] = this.y2;
/* 513 */         return 3;
/*     */       case 5:
/* 516 */         paramArrayOfFloat[0] = this.x1;
/* 517 */         paramArrayOfFloat[1] = this.y1;
/* 518 */         paramArrayOfFloat[2] = this.ctrlx1;
/* 519 */         paramArrayOfFloat[3] = this.ctrly1;
/* 520 */         paramArrayOfFloat[4] = this.x2;
/* 521 */         paramArrayOfFloat[5] = this.y2;
/* 522 */         return 3;
/*     */       }
/* 524 */       throw new InternalError("Unknown hull type");
/*     */     }
/*     */ 
/*     */     int emitVertices(Tessellator paramTessellator, VertexBuffer paramVertexBuffer)
/*     */     {
/* 540 */       b0.set(this.x1, this.y1, 1.0F);
/* 541 */       b1.set(this.ctrlx1, this.ctrly1, 1.0F);
/* 542 */       b2.set(this.ctrlx2, this.ctrly2, 1.0F);
/* 543 */       b3.set(this.x2, this.y2, 1.0F);
/*     */ 
/* 545 */       tmp.cross(b3, b2);
/* 546 */       float f1 = b0.dot(tmp);
/* 547 */       tmp.cross(b0, b3);
/* 548 */       float f2 = b1.dot(tmp);
/* 549 */       tmp.cross(b1, b0);
/* 550 */       float f3 = b2.dot(tmp);
/*     */ 
/* 552 */       float f4 = 3.0F * f3;
/* 553 */       float f5 = -f2 + f4;
/* 554 */       float f6 = f1 - 2.0F * f2 + f4;
/*     */ 
/* 556 */       tmp.set(f6, f5, f4);
/* 557 */       tmp.normalize();
/* 558 */       f6 = tmp.x;
/* 559 */       f5 = tmp.y;
/* 560 */       f4 = tmp.z;
/*     */ 
/* 562 */       float f7 = 3.0F * f5 * f5 - 4.0F * f6 * f4;
/* 563 */       float f8 = f6 * f6 * f7;
/*     */ 
/* 567 */       if (AATesselatorImpl.isCloseToZero(f6)) f6 = 0.0F;
/* 568 */       if (AATesselatorImpl.isCloseToZero(f5)) f5 = 0.0F;
/* 569 */       if (AATesselatorImpl.isCloseToZero(f4)) f4 = 0.0F;
/* 570 */       if (AATesselatorImpl.isCloseToZero(f8)) f8 = 0.0F;
/*     */ 
/* 573 */       if (f8 > 0.0F)
/*     */       {
/* 576 */         return emitSerpent(paramTessellator, paramVertexBuffer, f6, f5, f7);
/* 577 */       }if (f8 < 0.0F)
/*     */       {
/* 580 */         return emitLoop(paramTessellator, paramVertexBuffer, f6, f5, f7);
/*     */       }
/* 582 */       if ((f6 == 0.0F) && (f5 == 0.0F)) {
/* 583 */         if (f4 != 0.0F)
/*     */         {
/* 586 */           return emitQuadratic(paramTessellator, paramVertexBuffer);
/*     */         }
/*     */ 
/* 589 */         throw new InternalError("Line/point segment not yet supported");
/*     */       }
/*     */ 
/* 593 */       throw new InternalError("Cusp segment not yet supported");
/*     */     }
/*     */ 
/*     */     private int emitQuadratic(Tessellator paramTessellator, VertexBuffer paramVertexBuffer)
/*     */     {
/*     */       float f1;
/* 600 */       if (this.convex)
/*     */       {
/* 602 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 603 */         f1 = 1.0F;
/*     */       }
/*     */       else {
/* 606 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx1, this.ctrly1);
/* 607 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx2, this.ctrly2);
/* 608 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 609 */         f1 = -1.0F;
/*     */       }
/*     */ 
/* 612 */       float f2 = 0.3333333F;
/* 613 */       float f3 = 0.6666667F;
/*     */ 
/* 615 */       paramVertexBuffer.addVert(this.x1, this.y1, 0.0F, 0.0F, 0.0F, f1);
/* 616 */       paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, f2, 0.0F, f2, f1);
/* 617 */       paramVertexBuffer.addVert(this.x2, this.y2, 1.0F, 1.0F, 1.0F, f1);
/* 618 */       paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, f2, 0.0F, f2, f1);
/* 619 */       paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, f3, f2, f3, f1);
/* 620 */       paramVertexBuffer.addVert(this.x2, this.y2, 1.0F, 1.0F, 1.0F, f1);
/* 621 */       return 6;
/*     */     }
/*     */ 
/*     */     private int emitSerpent(Tessellator paramTessellator, VertexBuffer paramVertexBuffer, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */     {
/* 628 */       float f3 = (float)Math.sqrt(3.0F * paramFloat3);
/* 629 */       float f4 = 3.0F * paramFloat2 - f3;
/* 630 */       float f5 = 6.0F * paramFloat1;
/* 631 */       float f6 = 3.0F * paramFloat2 + f3;
/* 632 */       float f7 = f5;
/*     */ 
/* 634 */       m0.x = (f4 * f6);
/* 635 */       m0.y = (f4 * f4 * f4);
/* 636 */       m0.z = (f6 * f6 * f6);
/*     */ 
/* 638 */       m1.x = (0.3333333F * (3.0F * f4 * f6 - f4 * f7 - f5 * f6));
/* 639 */       m1.y = (f4 * f4 * (f4 - f5));
/* 640 */       m1.z = (f6 * f6 * (f6 - f7));
/*     */ 
/* 642 */       float f1 = f5 - f4;
/* 643 */       float f2 = f7 - f6;
/* 644 */       m2.x = (0.3333333F * (f5 * (f7 - 2.0F * f6) + f4 * (3.0F * f6 - 2.0F * f7)));
/* 645 */       m2.y = (f4 * f1 * f1);
/* 646 */       m2.z = (f6 * f2 * f2);
/*     */ 
/* 648 */       m3.x = (f1 * f2);
/* 649 */       m3.y = (-(f1 * f1 * f1));
/* 650 */       m3.z = (-(f2 * f2 * f2));
/*     */       float f8;
/* 653 */       if (this.ccw1 != this.ccw2)
/*     */       {
/* 655 */         if (this.ccw1 > 0)
/* 656 */           AATesselatorImpl.emitVert(paramTessellator, this.ctrlx1, this.ctrly1);
/*     */         else {
/* 658 */           AATesselatorImpl.emitVert(paramTessellator, this.ctrlx2, this.ctrly2);
/*     */         }
/* 660 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/*     */ 
/* 662 */         f8 = -1.0F;
/*     */ 
/* 664 */         paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 665 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 666 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 667 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 668 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 669 */         paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 670 */         return 6;
/*     */       }
/*     */ 
/* 673 */       if (this.hullType == 4)
/*     */       {
/* 675 */         if (this.convex) {
/* 676 */           AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 677 */           f8 = -1.0F;
/*     */         } else {
/* 679 */           AATesselatorImpl.emitVert(paramTessellator, this.ctrlx2, this.ctrly2);
/* 680 */           AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 681 */           f8 = 1.0F;
/*     */         }
/* 683 */         paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 684 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 685 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 686 */         paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 687 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 688 */         paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 689 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 690 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 691 */         paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 692 */         return 9;
/* 693 */       }if (this.hullType == 5)
/*     */       {
/* 695 */         if (this.convex) {
/* 696 */           AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 697 */           f8 = -1.0F;
/*     */         } else {
/* 699 */           AATesselatorImpl.emitVert(paramTessellator, this.ctrlx1, this.ctrly1);
/* 700 */           AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 701 */           f8 = 1.0F;
/*     */         }
/* 703 */         paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 704 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 705 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 706 */         paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 707 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 708 */         paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 709 */         paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 710 */         paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 711 */         paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 712 */         return 9;
/*     */       }
/*     */ 
/* 715 */       if (this.convex) {
/* 716 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 717 */         f8 = -1.0F;
/*     */       } else {
/* 719 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx1, this.ctrly1);
/* 720 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx2, this.ctrly2);
/* 721 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 722 */         f8 = 1.0F;
/*     */       }
/* 724 */       paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 725 */       paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 726 */       paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 727 */       paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 728 */       paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 729 */       paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 730 */       return 6;
/*     */     }
/*     */ 
/*     */     private int emitLoop(Tessellator paramTessellator, VertexBuffer paramVertexBuffer, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */     {
/* 740 */       float f3 = (float)Math.sqrt(-paramFloat3);
/* 741 */       float f4 = paramFloat2 - f3;
/* 742 */       float f5 = 2.0F * paramFloat1;
/* 743 */       float f6 = paramFloat2 + f3;
/* 744 */       float f7 = f5;
/*     */ 
/* 746 */       m0.x = (f4 * f6);
/* 747 */       m0.y = (f4 * f4 * f6);
/* 748 */       m0.z = (f4 * f6 * f6);
/*     */ 
/* 751 */       m1.x = (0.3333333F * (-(f4 * f7) - f5 * f6 + 3.0F * f4 * f6));
/* 752 */       m1.y = (-0.3333333F * f4 * (f4 * (f7 - 3.0F * f6) + 2.0F * f5 * f6));
/* 753 */       m1.z = (-0.3333333F * f6 * (f4 * (2.0F * f7 - 3.0F * f6) + f5 * f6));
/*     */ 
/* 755 */       float f1 = f5 - f4;
/* 756 */       float f2 = f7 - f6;
/* 757 */       m2.x = (0.3333333F * (f5 * (f7 - 2.0F * f6) + f4 * (3.0F * f6 - 2.0F * f7)));
/* 758 */       m2.y = (0.3333333F * f1 * (f4 * (2.0F * f7 - 3.0F * f6) + f5 * f6));
/* 759 */       m2.z = (0.3333333F * f2 * (f4 * (f7 - 3.0F * f6) + 2.0F * f5 * f6));
/*     */ 
/* 761 */       m3.x = (f1 * f2);
/* 762 */       m3.y = (-(f1 * f1 * f2));
/* 763 */       m3.z = (-(f1 * f2 * f2));
/*     */       float f8;
/* 766 */       if (this.convex) {
/* 767 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 768 */         f8 = -1.0F;
/*     */       } else {
/* 770 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx1, this.ctrly1);
/* 771 */         AATesselatorImpl.emitVert(paramTessellator, this.ctrlx2, this.ctrly2);
/* 772 */         AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 773 */         f8 = 1.0F;
/*     */       }
/*     */ 
/* 776 */       paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 777 */       paramVertexBuffer.addVert(this.ctrlx1, this.ctrly1, m1.x, m1.y, m1.z, f8);
/* 778 */       paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 779 */       paramVertexBuffer.addVert(this.x1, this.y1, m0.x, m0.y, m0.z, f8);
/* 780 */       paramVertexBuffer.addVert(this.ctrlx2, this.ctrly2, m2.x, m2.y, m2.z, f8);
/* 781 */       paramVertexBuffer.addVert(this.x2, this.y2, m3.x, m3.y, m3.z, f8);
/* 782 */       return 6;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class LineTo extends AATesselatorImpl.Segment
/*     */   {
/*     */     private final float x1;
/*     */     private final float y1;
/*     */     private final float x2;
/*     */     private final float y2;
/*     */ 
/*     */     LineTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 321 */       super();
/* 322 */       this.x1 = paramFloat1;
/* 323 */       this.y1 = paramFloat2;
/* 324 */       this.x2 = paramFloat3;
/* 325 */       this.y2 = paramFloat4;
/*     */     }
/*     */     int getEdges(float[] paramArrayOfFloat) {
/* 328 */       paramArrayOfFloat[0] = this.x1;
/* 329 */       paramArrayOfFloat[1] = this.y1;
/* 330 */       paramArrayOfFloat[2] = this.x2;
/* 331 */       paramArrayOfFloat[3] = this.y2;
/* 332 */       return 1;
/*     */     }
/*     */ 
/*     */     int emitVertices(Tessellator paramTessellator, VertexBuffer paramVertexBuffer) {
/* 336 */       AATesselatorImpl.emitVert(paramTessellator, this.x2, this.y2);
/* 337 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MoveTo extends AATesselatorImpl.Segment
/*     */   {
/*     */     private final float x;
/*     */     private final float y;
/*     */ 
/*     */     MoveTo(float paramFloat1, float paramFloat2)
/*     */     {
/* 301 */       super();
/* 302 */       this.x = paramFloat1;
/* 303 */       this.y = paramFloat2;
/*     */     }
/*     */     int getEdges(float[] paramArrayOfFloat) {
/* 306 */       return 0;
/*     */     }
/*     */     int emitVertices(Tessellator paramTessellator, VertexBuffer paramVertexBuffer) {
/* 309 */       Tess.tessBeginContour(paramTessellator);
/* 310 */       AATesselatorImpl.emitVert(paramTessellator, this.x, this.y);
/* 311 */       return 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class Segment
/*     */   {
/*     */     final Type type;
/*     */     boolean convex;
/* 218 */     private static final int[] triOffsets = { 0, 2, 2, 4, 4, 0, 6, 8, 8, 10, 10, 6 };
/*     */ 
/* 222 */     private static final float[] tmpThis = new float[12];
/* 223 */     private static final float[] tmpThat = new float[12];
/*     */ 
/*     */     protected Segment(Type paramType)
/*     */     {
/* 212 */       this.type = paramType;
/*     */     }
/*     */ 
/*     */     abstract int getEdges(float[] paramArrayOfFloat);
/*     */ 
/*     */     abstract int emitVertices(Tessellator paramTessellator, VertexBuffer paramVertexBuffer);
/*     */ 
/*     */     boolean overlaps(Segment paramSegment)
/*     */     {
/* 225 */       int i = getEdges(tmpThis);
/* 226 */       int j = paramSegment.getEdges(tmpThat);
/*     */ 
/* 228 */       if ((i < 1) || (j < 1)) {
/* 229 */         return false;
/*     */       }
/*     */ 
/* 232 */       if ((i == 1) && (j == 1))
/*     */       {
/* 234 */         return AATesselatorImpl.linesCross(tmpThis[0], tmpThis[1], tmpThis[2], tmpThis[3], tmpThat[0], tmpThat[1], tmpThat[2], tmpThat[3]);
/*     */       }
/*     */ 
/* 243 */       int k = i * 2;
/* 244 */       int m = j * 2;
/*     */       int i1;
/* 247 */       for (int n = 0; n < k; n += 2) {
/* 248 */         i1 = triOffsets[n];
/* 249 */         int i2 = triOffsets[(n + 1)];
/* 250 */         for (int i3 = 0; i3 < m; i3 += 2) {
/* 251 */           int i4 = triOffsets[i3];
/* 252 */           int i5 = triOffsets[(i3 + 1)];
/* 253 */           if (AATesselatorImpl.linesCross(tmpThis[(i1 + 0)], tmpThis[(i1 + 1)], tmpThis[(i2 + 0)], tmpThis[(i2 + 1)], tmpThat[(i4 + 0)], tmpThat[(i4 + 1)], tmpThat[(i5 + 0)], tmpThat[(i5 + 1)]))
/*     */           {
/* 259 */             return true;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 270 */       for (n = 0; n < i * 2; n += 6) {
/* 271 */         for (i1 = 0; i1 < j * 2; i1 += 6) {
/* 272 */           if ((j > 1) && 
/* 273 */             (AATesselatorImpl.triangleContainsPoint(tmpThis[(n + 0)], tmpThis[(n + 1)], tmpThat[(i1 + 0)], tmpThat[(i1 + 1)], tmpThat[(i1 + 2)], tmpThat[(i1 + 3)], tmpThat[(i1 + 4)], tmpThat[(i1 + 5)])))
/*     */           {
/* 278 */             return true;
/*     */           }
/*     */ 
/* 281 */           if ((i > 1) && 
/* 282 */             (AATesselatorImpl.triangleContainsPoint(tmpThat[(i1 + 0)], tmpThat[(i1 + 1)], tmpThis[(n + 0)], tmpThis[(n + 1)], tmpThis[(n + 2)], tmpThis[(n + 3)], tmpThis[(n + 4)], tmpThis[(n + 5)])))
/*     */           {
/* 287 */             return true;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 293 */       return false;
/*     */     }
/*     */ 
/*     */     static enum Type
/*     */     {
/* 208 */       MOVETO, LINETO, QUADTO, CUBICTO, CLOSE;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.AATesselatorImpl
 * JD-Core Version:    0.6.2
 */