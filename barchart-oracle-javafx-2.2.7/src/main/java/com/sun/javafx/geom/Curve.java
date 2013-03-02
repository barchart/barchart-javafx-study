/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class Curve
/*     */ {
/*     */   public static final int INCREASING = 1;
/*     */   public static final int DECREASING = -1;
/*     */   protected int direction;
/*     */   public static final double TMIN = 0.001D;
/*     */ 
/*     */   public static void insertMove(Vector paramVector, double paramDouble1, double paramDouble2)
/*     */   {
/*  37 */     paramVector.add(new Order0(paramDouble1, paramDouble2));
/*     */   }
/*     */ 
/*     */   public static void insertLine(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  44 */     if (paramDouble2 < paramDouble4) {
/*  45 */       paramVector.add(new Order1(paramDouble1, paramDouble2, paramDouble3, paramDouble4, 1));
/*     */     }
/*  48 */     else if (paramDouble2 > paramDouble4)
/*  49 */       paramVector.add(new Order1(paramDouble3, paramDouble4, paramDouble1, paramDouble2, -1));
/*     */   }
/*     */ 
/*     */   public static void insertQuad(Vector paramVector, double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  62 */     if (paramDouble2 > paramDouble6) {
/*  63 */       Order2.insert(paramVector, paramArrayOfDouble, paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -1);
/*     */     }
/*     */     else {
/*  66 */       if ((paramDouble2 == paramDouble6) && (paramDouble2 == paramDouble4))
/*     */       {
/*  68 */         return;
/*     */       }
/*  70 */       Order2.insert(paramVector, paramArrayOfDouble, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void insertCubic(Vector paramVector, double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8)
/*     */   {
/*  82 */     if (paramDouble2 > paramDouble8) {
/*  83 */       Order3.insert(paramVector, paramArrayOfDouble, paramDouble7, paramDouble8, paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -1);
/*     */     }
/*     */     else {
/*  86 */       if ((paramDouble2 == paramDouble8) && (paramDouble2 == paramDouble4) && (paramDouble2 == paramDouble6))
/*     */       {
/*  88 */         return;
/*     */       }
/*  90 */       Order3.insert(paramVector, paramArrayOfDouble, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Curve(int paramInt)
/*     */   {
/*  97 */     this.direction = paramInt;
/*     */   }
/*     */ 
/*     */   public final int getDirection() {
/* 101 */     return this.direction;
/*     */   }
/*     */ 
/*     */   public final Curve getWithDirection(int paramInt) {
/* 105 */     return this.direction == paramInt ? this : getReversedCurve();
/*     */   }
/*     */ 
/*     */   public static double round(double paramDouble)
/*     */   {
/* 110 */     return paramDouble;
/*     */   }
/*     */ 
/*     */   public static int orderof(double paramDouble1, double paramDouble2) {
/* 114 */     if (paramDouble1 < paramDouble2) {
/* 115 */       return -1;
/*     */     }
/* 117 */     if (paramDouble1 > paramDouble2) {
/* 118 */       return 1;
/*     */     }
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   public static long signeddiffbits(double paramDouble1, double paramDouble2) {
/* 124 */     return Double.doubleToLongBits(paramDouble1) - Double.doubleToLongBits(paramDouble2);
/*     */   }
/*     */   public static long diffbits(double paramDouble1, double paramDouble2) {
/* 127 */     return Math.abs(Double.doubleToLongBits(paramDouble1) - Double.doubleToLongBits(paramDouble2));
/*     */   }
/*     */ 
/*     */   public static double prev(double paramDouble) {
/* 131 */     return Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) - 1L);
/*     */   }
/*     */   public static double next(double paramDouble) {
/* 134 */     return Double.longBitsToDouble(Double.doubleToLongBits(paramDouble) + 1L);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 139 */     return new StringBuilder().append("Curve[").append(getOrder()).append(", ").append("(").append(round(getX0())).append(", ").append(round(getY0())).append("), ").append(controlPointString()).append("(").append(round(getX1())).append(", ").append(round(getY1())).append("), ").append(this.direction == 1 ? "D" : "U").append("]").toString();
/*     */   }
/*     */ 
/*     */   public String controlPointString()
/*     */   {
/* 149 */     return ""; } 
/*     */   public abstract int getOrder();
/*     */ 
/*     */   public abstract double getXTop();
/*     */ 
/*     */   public abstract double getYTop();
/*     */ 
/*     */   public abstract double getXBot();
/*     */ 
/*     */   public abstract double getYBot();
/*     */ 
/*     */   public abstract double getXMin();
/*     */ 
/*     */   public abstract double getXMax();
/*     */ 
/*     */   public abstract double getX0();
/*     */ 
/*     */   public abstract double getY0();
/*     */ 
/*     */   public abstract double getX1();
/*     */ 
/*     */   public abstract double getY1();
/*     */ 
/*     */   public abstract double XforY(double paramDouble);
/*     */ 
/*     */   public abstract double TforY(double paramDouble);
/*     */ 
/*     */   public abstract double XforT(double paramDouble);
/*     */ 
/*     */   public abstract double YforT(double paramDouble);
/*     */ 
/*     */   public abstract double dXforT(double paramDouble, int paramInt);
/*     */ 
/*     */   public abstract double dYforT(double paramDouble, int paramInt);
/*     */ 
/*     */   public abstract double nextVertical(double paramDouble1, double paramDouble2);
/*     */ 
/* 177 */   public int crossingsFor(double paramDouble1, double paramDouble2) { if ((paramDouble2 >= getYTop()) && (paramDouble2 < getYBot()) && 
/* 178 */       (paramDouble1 < getXMax()) && (
/* 178 */       (paramDouble1 < getXMin()) || (paramDouble1 < XforY(paramDouble2)))) {
/* 179 */       return 1;
/*     */     }
/*     */ 
/* 182 */     return 0; }
/*     */ 
/*     */   public boolean accumulateCrossings(Crossings paramCrossings)
/*     */   {
/* 186 */     double d1 = paramCrossings.getXHi();
/* 187 */     if (getXMin() >= d1) {
/* 188 */       return false;
/*     */     }
/* 190 */     double d2 = paramCrossings.getXLo();
/* 191 */     double d3 = paramCrossings.getYLo();
/* 192 */     double d4 = paramCrossings.getYHi();
/* 193 */     double d5 = getYTop();
/* 194 */     double d6 = getYBot();
/*     */     double d8;
/*     */     double d7;
/* 196 */     if (d5 < d3) {
/* 197 */       if (d6 <= d3) {
/* 198 */         return false;
/*     */       }
/* 200 */       d8 = d3;
/* 201 */       d7 = TforY(d3);
/*     */     } else {
/* 203 */       if (d5 >= d4) {
/* 204 */         return false;
/*     */       }
/* 206 */       d8 = d5;
/* 207 */       d7 = 0.0D;
/*     */     }
/*     */     double d10;
/*     */     double d9;
/* 209 */     if (d6 > d4) {
/* 210 */       d10 = d4;
/* 211 */       d9 = TforY(d4);
/*     */     } else {
/* 213 */       d10 = d6;
/* 214 */       d9 = 1.0D;
/*     */     }
/* 216 */     int i = 0;
/* 217 */     int j = 0;
/*     */     while (true) {
/* 219 */       double d11 = XforT(d7);
/* 220 */       if (d11 < d1) {
/* 221 */         if ((j != 0) || (d11 > d2)) {
/* 222 */           return true;
/*     */         }
/* 224 */         i = 1;
/*     */       } else {
/* 226 */         if (i != 0) {
/* 227 */           return true;
/*     */         }
/* 229 */         j = 1;
/*     */       }
/* 231 */       if (d7 >= d9) {
/*     */         break;
/*     */       }
/* 234 */       d7 = nextVertical(d7, d9);
/*     */     }
/* 236 */     if (i != 0) {
/* 237 */       paramCrossings.record(d8, d10, this.direction);
/*     */     }
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract void enlarge(RectBounds paramRectBounds);
/*     */ 
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2) {
/* 245 */     return getSubCurve(paramDouble1, paramDouble2, this.direction);
/*     */   }
/*     */ 
/*     */   public abstract Curve getReversedCurve();
/*     */ 
/*     */   public abstract Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt);
/*     */ 
/*     */   public int compareTo(Curve paramCurve, double[] paramArrayOfDouble)
/*     */   {
/* 256 */     double d1 = paramArrayOfDouble[0];
/* 257 */     double d2 = paramArrayOfDouble[1];
/* 258 */     d2 = Math.min(Math.min(d2, getYBot()), paramCurve.getYBot());
/* 259 */     if (d2 <= paramArrayOfDouble[0]) {
/* 260 */       System.err.println(new StringBuilder().append("this == ").append(this).toString());
/* 261 */       System.err.println(new StringBuilder().append("that == ").append(paramCurve).toString());
/* 262 */       System.out.println(new StringBuilder().append("target range = ").append(paramArrayOfDouble[0]).append("=>").append(paramArrayOfDouble[1]).toString());
/* 263 */       throw new InternalError(new StringBuilder().append("backstepping from ").append(paramArrayOfDouble[0]).append(" to ").append(d2).toString());
/*     */     }
/* 265 */     paramArrayOfDouble[1] = d2;
/* 266 */     if (getXMax() <= paramCurve.getXMin()) {
/* 267 */       if (getXMin() == paramCurve.getXMax()) {
/* 268 */         return 0;
/*     */       }
/* 270 */       return -1;
/*     */     }
/* 272 */     if (getXMin() >= paramCurve.getXMax()) {
/* 273 */       return 1;
/*     */     }
/*     */ 
/* 281 */     double d3 = TforY(d1);
/* 282 */     double d4 = YforT(d3);
/* 283 */     if (d4 < d1) {
/* 284 */       d3 = refineTforY(d3, d4, d1);
/* 285 */       d4 = YforT(d3);
/*     */     }
/* 287 */     double d5 = TforY(d2);
/* 288 */     if (YforT(d5) < d1) {
/* 289 */       d5 = refineTforY(d5, YforT(d5), d1);
/*     */     }
/*     */ 
/* 292 */     double d6 = paramCurve.TforY(d1);
/* 293 */     double d7 = paramCurve.YforT(d6);
/* 294 */     if (d7 < d1) {
/* 295 */       d6 = paramCurve.refineTforY(d6, d7, d1);
/* 296 */       d7 = paramCurve.YforT(d6);
/*     */     }
/* 298 */     double d8 = paramCurve.TforY(d2);
/* 299 */     if (paramCurve.YforT(d8) < d1) {
/* 300 */       d8 = paramCurve.refineTforY(d8, paramCurve.YforT(d8), d1);
/*     */     }
/*     */ 
/* 303 */     double d9 = XforT(d3);
/* 304 */     double d10 = paramCurve.XforT(d6);
/* 305 */     double d11 = Math.max(Math.abs(d1), Math.abs(d2));
/* 306 */     double d12 = Math.max(d11 * 1.0E-14D, 1.0E-300D);
/*     */     double d14;
/*     */     double d15;
/*     */     double d16;
/* 307 */     if (fairlyClose(d9, d10)) {
/* 308 */       d13 = d12;
/* 309 */       d14 = Math.min(d12 * 10000000000000.0D, (d2 - d1) * 0.1D);
/* 310 */       d15 = d1 + d13;
/* 311 */       while (d15 <= d2) {
/* 312 */         if (fairlyClose(XforY(d15), paramCurve.XforY(d15))) {
/* 313 */           if (d13 *= 2.0D > d14)
/* 314 */             d13 = d14;
/*     */         }
/*     */         else {
/* 317 */           d15 -= d13;
/*     */           while (true) {
/* 319 */             d13 /= 2.0D;
/* 320 */             d16 = d15 + d13;
/* 321 */             if (d16 <= d15) {
/*     */               break;
/*     */             }
/* 324 */             if (fairlyClose(XforY(d16), paramCurve.XforY(d16))) {
/* 325 */               d15 = d16;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 330 */         d15 += d13;
/*     */       }
/* 332 */       if (d15 > d1) {
/* 333 */         if (d15 < d2) {
/* 334 */           paramArrayOfDouble[1] = d15;
/*     */         }
/* 336 */         return 0;
/*     */       }
/*     */     }
/*     */ 
/* 340 */     if (d12 <= 0.0D) {
/* 341 */       System.out.println(new StringBuilder().append("ymin = ").append(d12).toString());
/*     */     }
/*     */ 
/* 347 */     while ((d3 < d5) && (d6 < d8)) {
/* 348 */       d13 = nextVertical(d3, d5);
/* 349 */       d14 = XforT(d13);
/* 350 */       d15 = YforT(d13);
/* 351 */       d16 = paramCurve.nextVertical(d6, d8);
/* 352 */       double d17 = paramCurve.XforT(d16);
/* 353 */       double d18 = paramCurve.YforT(d16);
/*     */       try
/*     */       {
/* 359 */         if (findIntersect(paramCurve, paramArrayOfDouble, d12, 0, 0, d3, d9, d4, d13, d14, d15, d6, d10, d7, d16, d17, d18))
/*     */         {
/* 362 */           break;
/*     */         }
/*     */       } catch (Throwable localThrowable) {
/* 365 */         System.err.println(new StringBuilder().append("Error: ").append(localThrowable).toString());
/* 366 */         System.err.println(new StringBuilder().append("y range was ").append(paramArrayOfDouble[0]).append("=>").append(paramArrayOfDouble[1]).toString());
/* 367 */         System.err.println(new StringBuilder().append("s y range is ").append(d4).append("=>").append(d15).toString());
/* 368 */         System.err.println(new StringBuilder().append("t y range is ").append(d7).append("=>").append(d18).toString());
/* 369 */         System.err.println(new StringBuilder().append("ymin is ").append(d12).toString());
/* 370 */         return 0;
/*     */       }
/* 372 */       if (d15 < d18) {
/* 373 */         if (d15 > paramArrayOfDouble[0]) {
/* 374 */           if (d15 >= paramArrayOfDouble[1]) break;
/* 375 */           paramArrayOfDouble[1] = d15; break;
/*     */         }
/*     */ 
/* 379 */         d3 = d13;
/* 380 */         d9 = d14;
/* 381 */         d4 = d15;
/*     */       } else {
/* 383 */         if (d18 > paramArrayOfDouble[0]) {
/* 384 */           if (d18 >= paramArrayOfDouble[1]) break;
/* 385 */           paramArrayOfDouble[1] = d18; break;
/*     */         }
/*     */ 
/* 389 */         d6 = d16;
/* 390 */         d10 = d17;
/* 391 */         d7 = d18;
/*     */       }
/*     */     }
/* 394 */     double d13 = (paramArrayOfDouble[0] + paramArrayOfDouble[1]) / 2.0D;
/*     */ 
/* 410 */     return orderof(XforY(d13), paramCurve.XforY(d13));
/*     */   }
/*     */ 
/*     */   public boolean findIntersect(Curve paramCurve, double[] paramArrayOfDouble, double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13)
/*     */   {
/* 435 */     if ((paramDouble4 > paramDouble13) || (paramDouble10 > paramDouble7)) {
/* 436 */       return false;
/*     */     }
/* 438 */     if ((Math.min(paramDouble3, paramDouble6) > Math.max(paramDouble9, paramDouble12)) || (Math.max(paramDouble3, paramDouble6) < Math.min(paramDouble9, paramDouble12)))
/*     */     {
/* 441 */       return false;
/*     */     }
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/*     */     double d5;
/*     */     double d6;
/* 447 */     if (paramDouble5 - paramDouble2 > 0.001D) {
/* 448 */       d1 = (paramDouble2 + paramDouble5) / 2.0D;
/* 449 */       d2 = XforT(d1);
/* 450 */       d3 = YforT(d1);
/* 451 */       if ((d1 == paramDouble2) || (d1 == paramDouble5)) {
/* 452 */         System.out.println(new StringBuilder().append("s0 = ").append(paramDouble2).toString());
/* 453 */         System.out.println(new StringBuilder().append("s1 = ").append(paramDouble5).toString());
/* 454 */         throw new InternalError("no s progress!");
/*     */       }
/* 456 */       if (paramDouble11 - paramDouble8 > 0.001D) {
/* 457 */         d4 = (paramDouble8 + paramDouble11) / 2.0D;
/* 458 */         d5 = paramCurve.XforT(d4);
/* 459 */         d6 = paramCurve.YforT(d4);
/* 460 */         if ((d4 == paramDouble8) || (d4 == paramDouble11)) {
/* 461 */           System.out.println(new StringBuilder().append("t0 = ").append(paramDouble8).toString());
/* 462 */           System.out.println(new StringBuilder().append("t1 = ").append(paramDouble11).toString());
/* 463 */           throw new InternalError("no t progress!");
/*     */         }
/* 465 */         if ((d3 >= paramDouble10) && (d6 >= paramDouble4) && 
/* 466 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2 + 1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, paramDouble8, paramDouble9, paramDouble10, d4, d5, d6)))
/*     */         {
/* 469 */           return true;
/*     */         }
/*     */ 
/* 472 */         if ((d3 >= d6) && 
/* 473 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2 + 1, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, d4, d5, d6, paramDouble11, paramDouble12, paramDouble13)))
/*     */         {
/* 476 */           return true;
/*     */         }
/*     */ 
/* 479 */         if ((d6 >= d3) && 
/* 480 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2 + 1, d1, d2, d3, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, d4, d5, d6)))
/*     */         {
/* 483 */           return true;
/*     */         }
/*     */ 
/* 486 */         if ((paramDouble7 >= d6) && (paramDouble13 >= d3) && 
/* 487 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2 + 1, d1, d2, d3, paramDouble5, paramDouble6, paramDouble7, d4, d5, d6, paramDouble11, paramDouble12, paramDouble13)))
/*     */         {
/* 490 */           return true;
/*     */         }
/*     */       }
/*     */       else {
/* 494 */         if ((d3 >= paramDouble10) && 
/* 495 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2, paramDouble2, paramDouble3, paramDouble4, d1, d2, d3, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12, paramDouble13)))
/*     */         {
/* 498 */           return true;
/*     */         }
/*     */ 
/* 501 */         if ((paramDouble13 >= d3) && 
/* 502 */           (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1 + 1, paramInt2, d1, d2, d3, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12, paramDouble13)))
/*     */         {
/* 505 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 509 */     else if (paramDouble11 - paramDouble8 > 0.001D) {
/* 510 */       d1 = (paramDouble8 + paramDouble11) / 2.0D;
/* 511 */       d2 = paramCurve.XforT(d1);
/* 512 */       d3 = paramCurve.YforT(d1);
/* 513 */       if ((d1 == paramDouble8) || (d1 == paramDouble11)) {
/* 514 */         System.out.println(new StringBuilder().append("t0 = ").append(paramDouble8).toString());
/* 515 */         System.out.println(new StringBuilder().append("t1 = ").append(paramDouble11).toString());
/* 516 */         throw new InternalError("no t progress!");
/*     */       }
/* 518 */       if ((d3 >= paramDouble4) && 
/* 519 */         (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1, paramInt2 + 1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, d1, d2, d3)))
/*     */       {
/* 522 */         return true;
/*     */       }
/*     */ 
/* 525 */       if ((paramDouble7 >= d3) && 
/* 526 */         (findIntersect(paramCurve, paramArrayOfDouble, paramDouble1, paramInt1, paramInt2 + 1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, d1, d2, d3, paramDouble11, paramDouble12, paramDouble13)))
/*     */       {
/* 529 */         return true;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 534 */       d1 = paramDouble6 - paramDouble3;
/* 535 */       d2 = paramDouble7 - paramDouble4;
/* 536 */       d3 = paramDouble12 - paramDouble9;
/* 537 */       d4 = paramDouble13 - paramDouble10;
/* 538 */       d5 = paramDouble9 - paramDouble3;
/* 539 */       d6 = paramDouble10 - paramDouble4;
/* 540 */       double d7 = d3 * d2 - d4 * d1;
/* 541 */       if (d7 != 0.0D) {
/* 542 */         double d8 = 1.0D / d7;
/* 543 */         double d9 = (d3 * d6 - d4 * d5) * d8;
/* 544 */         double d10 = (d1 * d6 - d2 * d5) * d8;
/* 545 */         if ((d9 >= 0.0D) && (d9 <= 1.0D) && (d10 >= 0.0D) && (d10 <= 1.0D)) {
/* 546 */           d9 = paramDouble2 + d9 * (paramDouble5 - paramDouble2);
/* 547 */           d10 = paramDouble8 + d10 * (paramDouble11 - paramDouble8);
/* 548 */           if ((d9 < 0.0D) || (d9 > 1.0D) || (d10 < 0.0D) || (d10 > 1.0D)) {
/* 549 */             System.out.println("Uh oh!");
/*     */           }
/* 551 */           double d11 = (YforT(d9) + paramCurve.YforT(d10)) / 2.0D;
/* 552 */           if ((d11 <= paramArrayOfDouble[1]) && (d11 > paramArrayOfDouble[0])) {
/* 553 */             paramArrayOfDouble[1] = d11;
/* 554 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 560 */     return false;
/*     */   }
/*     */ 
/*     */   public double refineTforY(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 564 */     double d1 = 1.0D;
/*     */     while (true) {
/* 566 */       double d2 = (paramDouble1 + d1) / 2.0D;
/* 567 */       if ((d2 == paramDouble1) || (d2 == d1)) {
/* 568 */         return d1;
/*     */       }
/* 570 */       double d3 = YforT(d2);
/* 571 */       if (d3 < paramDouble3) {
/* 572 */         paramDouble1 = d2;
/* 573 */         paramDouble2 = d3;
/* 574 */       } else if (d3 > paramDouble3) {
/* 575 */         d1 = d2;
/*     */       } else {
/* 577 */         return d1;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean fairlyClose(double paramDouble1, double paramDouble2) {
/* 583 */     return Math.abs(paramDouble1 - paramDouble2) < Math.max(Math.abs(paramDouble1), Math.abs(paramDouble2)) * 1.0E-10D;
/*     */   }
/*     */ 
/*     */   public abstract int getSegment(float[] paramArrayOfFloat);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Curve
 * JD-Core Version:    0.6.2
 */