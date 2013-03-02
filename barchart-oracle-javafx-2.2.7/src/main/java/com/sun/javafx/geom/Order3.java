/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ final class Order3 extends Curve
/*     */ {
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double cx0;
/*     */   private double cy0;
/*     */   private double cx1;
/*     */   private double cy1;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private double xmin;
/*     */   private double xmax;
/*     */   private double xcoeff0;
/*     */   private double xcoeff1;
/*     */   private double xcoeff2;
/*     */   private double xcoeff3;
/*     */   private double ycoeff0;
/*     */   private double ycoeff1;
/*     */   private double ycoeff2;
/*     */   private double ycoeff3;
/*     */   private double TforY1;
/*     */   private double YforT1;
/*     */   private double TforY2;
/*     */   private double YforT2;
/*     */   private double TforY3;
/*     */   private double YforT3;
/*     */ 
/*     */   public static void insert(Vector paramVector, double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt)
/*     */   {
/*  60 */     int i = getHorizontalParams(paramDouble2, paramDouble4, paramDouble6, paramDouble8, paramArrayOfDouble);
/*  61 */     if (i == 0)
/*     */     {
/*  64 */       addInstance(paramVector, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramInt);
/*  65 */       return;
/*     */     }
/*     */ 
/*  68 */     paramArrayOfDouble[3] = paramDouble1; paramArrayOfDouble[4] = paramDouble2;
/*  69 */     paramArrayOfDouble[5] = paramDouble3; paramArrayOfDouble[6] = paramDouble4;
/*  70 */     paramArrayOfDouble[7] = paramDouble5; paramArrayOfDouble[8] = paramDouble6;
/*  71 */     paramArrayOfDouble[9] = paramDouble7; paramArrayOfDouble[10] = paramDouble8;
/*  72 */     double d = paramArrayOfDouble[0];
/*  73 */     if ((i > 1) && (d > paramArrayOfDouble[1]))
/*     */     {
/*  75 */       paramArrayOfDouble[0] = paramArrayOfDouble[1];
/*  76 */       paramArrayOfDouble[1] = d;
/*  77 */       d = paramArrayOfDouble[0];
/*     */     }
/*  79 */     split(paramArrayOfDouble, 3, d);
/*  80 */     if (i > 1)
/*     */     {
/*  82 */       d = (paramArrayOfDouble[1] - d) / (1.0D - d);
/*  83 */       split(paramArrayOfDouble, 9, d);
/*     */     }
/*  85 */     int j = 3;
/*  86 */     if (paramInt == -1) {
/*  87 */       j += i * 6;
/*     */     }
/*  89 */     while (i >= 0) {
/*  90 */       addInstance(paramVector, paramArrayOfDouble[(j + 0)], paramArrayOfDouble[(j + 1)], paramArrayOfDouble[(j + 2)], paramArrayOfDouble[(j + 3)], paramArrayOfDouble[(j + 4)], paramArrayOfDouble[(j + 5)], paramArrayOfDouble[(j + 6)], paramArrayOfDouble[(j + 7)], paramInt);
/*     */ 
/*  96 */       i--;
/*  97 */       if (paramInt == 1)
/*  98 */         j += 6;
/*     */       else
/* 100 */         j -= 6;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addInstance(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt)
/*     */   {
/* 111 */     if (paramDouble2 > paramDouble8) {
/* 112 */       paramVector.add(new Order3(paramDouble7, paramDouble8, paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -paramInt));
/*     */     }
/* 114 */     else if (paramDouble8 > paramDouble2)
/* 115 */       paramVector.add(new Order3(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramInt));
/*     */   }
/*     */ 
/*     */   public static int solveQuadratic(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
/*     */   {
/* 142 */     double d1 = paramArrayOfDouble1[2];
/* 143 */     double d2 = paramArrayOfDouble1[1];
/* 144 */     double d3 = paramArrayOfDouble1[0];
/* 145 */     int i = 0;
/* 146 */     if (d1 == 0.0D)
/*     */     {
/* 148 */       if (d2 == 0.0D)
/*     */       {
/* 150 */         return -1;
/*     */       }
/* 152 */       paramArrayOfDouble2[(i++)] = (-d3 / d2);
/*     */     }
/*     */     else {
/* 155 */       double d4 = d2 * d2 - 4.0D * d1 * d3;
/* 156 */       if (d4 < 0.0D)
/*     */       {
/* 158 */         return 0;
/*     */       }
/* 160 */       d4 = Math.sqrt(d4);
/*     */ 
/* 166 */       if (d2 < 0.0D) {
/* 167 */         d4 = -d4;
/*     */       }
/* 169 */       double d5 = (d2 + d4) / -2.0D;
/*     */ 
/* 171 */       paramArrayOfDouble2[(i++)] = (d5 / d1);
/* 172 */       if (d5 != 0.0D) {
/* 173 */         paramArrayOfDouble2[(i++)] = (d3 / d5);
/*     */       }
/*     */     }
/* 176 */     return i;
/*     */   }
/*     */ 
/*     */   public static int getHorizontalParams(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double[] paramArrayOfDouble)
/*     */   {
/* 220 */     if ((paramDouble1 <= paramDouble2) && (paramDouble2 <= paramDouble3) && (paramDouble3 <= paramDouble4)) {
/* 221 */       return 0;
/*     */     }
/* 223 */     paramDouble4 -= paramDouble3;
/* 224 */     paramDouble3 -= paramDouble2;
/* 225 */     paramDouble2 -= paramDouble1;
/* 226 */     paramArrayOfDouble[0] = paramDouble2;
/* 227 */     paramArrayOfDouble[1] = ((paramDouble3 - paramDouble2) * 2.0D);
/* 228 */     paramArrayOfDouble[2] = (paramDouble4 - paramDouble3 - paramDouble3 + paramDouble2);
/* 229 */     int i = solveQuadratic(paramArrayOfDouble, paramArrayOfDouble);
/* 230 */     int j = 0;
/* 231 */     for (int k = 0; k < i; k++) {
/* 232 */       double d = paramArrayOfDouble[k];
/*     */ 
/* 234 */       if ((d > 0.0D) && (d < 1.0D)) {
/* 235 */         if (j < k) {
/* 236 */           paramArrayOfDouble[j] = d;
/*     */         }
/* 238 */         j++;
/*     */       }
/*     */     }
/* 241 */     return j;
/*     */   }
/*     */ 
/*     */   public static void split(double[] paramArrayOfDouble, int paramInt, double paramDouble)
/*     */   {
/*     */     double tmp11_10 = paramArrayOfDouble[(paramInt + 6)]; double d7 = tmp11_10; paramArrayOfDouble[(paramInt + 12)] = tmp11_10;
/*     */     double tmp26_25 = paramArrayOfDouble[(paramInt + 7)]; double d8 = tmp26_25; paramArrayOfDouble[(paramInt + 13)] = tmp26_25;
/* 254 */     double d5 = paramArrayOfDouble[(paramInt + 4)];
/* 255 */     double d6 = paramArrayOfDouble[(paramInt + 5)];
/* 256 */     d7 = d5 + (d7 - d5) * paramDouble;
/* 257 */     d8 = d6 + (d8 - d6) * paramDouble;
/* 258 */     double d1 = paramArrayOfDouble[(paramInt + 0)];
/* 259 */     double d2 = paramArrayOfDouble[(paramInt + 1)];
/* 260 */     double d3 = paramArrayOfDouble[(paramInt + 2)];
/* 261 */     double d4 = paramArrayOfDouble[(paramInt + 3)];
/* 262 */     d1 += (d3 - d1) * paramDouble;
/* 263 */     d2 += (d4 - d2) * paramDouble;
/* 264 */     d3 += (d5 - d3) * paramDouble;
/* 265 */     d4 += (d6 - d4) * paramDouble;
/* 266 */     d5 = d3 + (d7 - d3) * paramDouble;
/* 267 */     d6 = d4 + (d8 - d4) * paramDouble;
/* 268 */     d3 = d1 + (d3 - d1) * paramDouble;
/* 269 */     d4 = d2 + (d4 - d2) * paramDouble;
/* 270 */     paramArrayOfDouble[(paramInt + 2)] = d1;
/* 271 */     paramArrayOfDouble[(paramInt + 3)] = d2;
/* 272 */     paramArrayOfDouble[(paramInt + 4)] = d3;
/* 273 */     paramArrayOfDouble[(paramInt + 5)] = d4;
/* 274 */     paramArrayOfDouble[(paramInt + 6)] = (d3 + (d5 - d3) * paramDouble);
/* 275 */     paramArrayOfDouble[(paramInt + 7)] = (d4 + (d6 - d4) * paramDouble);
/* 276 */     paramArrayOfDouble[(paramInt + 8)] = d5;
/* 277 */     paramArrayOfDouble[(paramInt + 9)] = d6;
/* 278 */     paramArrayOfDouble[(paramInt + 10)] = d7;
/* 279 */     paramArrayOfDouble[(paramInt + 11)] = d8;
/*     */   }
/*     */ 
/*     */   public Order3(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, int paramInt)
/*     */   {
/* 288 */     super(paramInt);
/*     */ 
/* 292 */     if (paramDouble4 < paramDouble2) paramDouble4 = paramDouble2;
/* 293 */     if (paramDouble6 > paramDouble8) paramDouble6 = paramDouble8;
/* 294 */     this.x0 = paramDouble1;
/* 295 */     this.y0 = paramDouble2;
/* 296 */     this.cx0 = paramDouble3;
/* 297 */     this.cy0 = paramDouble4;
/* 298 */     this.cx1 = paramDouble5;
/* 299 */     this.cy1 = paramDouble6;
/* 300 */     this.x1 = paramDouble7;
/* 301 */     this.y1 = paramDouble8;
/* 302 */     this.xmin = Math.min(Math.min(paramDouble1, paramDouble7), Math.min(paramDouble3, paramDouble5));
/* 303 */     this.xmax = Math.max(Math.max(paramDouble1, paramDouble7), Math.max(paramDouble3, paramDouble5));
/* 304 */     this.xcoeff0 = paramDouble1;
/* 305 */     this.xcoeff1 = ((paramDouble3 - paramDouble1) * 3.0D);
/* 306 */     this.xcoeff2 = ((paramDouble5 - paramDouble3 - paramDouble3 + paramDouble1) * 3.0D);
/* 307 */     this.xcoeff3 = (paramDouble7 - (paramDouble5 - paramDouble3) * 3.0D - paramDouble1);
/* 308 */     this.ycoeff0 = paramDouble2;
/* 309 */     this.ycoeff1 = ((paramDouble4 - paramDouble2) * 3.0D);
/* 310 */     this.ycoeff2 = ((paramDouble6 - paramDouble4 - paramDouble4 + paramDouble2) * 3.0D);
/* 311 */     this.ycoeff3 = (paramDouble8 - (paramDouble6 - paramDouble4) * 3.0D - paramDouble2);
/* 312 */     this.YforT1 = (this.YforT2 = this.YforT3 = paramDouble2);
/*     */   }
/*     */ 
/*     */   public int getOrder() {
/* 316 */     return 3;
/*     */   }
/*     */ 
/*     */   public double getXTop() {
/* 320 */     return this.x0;
/*     */   }
/*     */ 
/*     */   public double getYTop() {
/* 324 */     return this.y0;
/*     */   }
/*     */ 
/*     */   public double getXBot() {
/* 328 */     return this.x1;
/*     */   }
/*     */ 
/*     */   public double getYBot() {
/* 332 */     return this.y1;
/*     */   }
/*     */ 
/*     */   public double getXMin() {
/* 336 */     return this.xmin;
/*     */   }
/*     */ 
/*     */   public double getXMax() {
/* 340 */     return this.xmax;
/*     */   }
/*     */ 
/*     */   public double getX0() {
/* 344 */     return this.direction == 1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY0() {
/* 348 */     return this.direction == 1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double getCX0() {
/* 352 */     return this.direction == 1 ? this.cx0 : this.cx1;
/*     */   }
/*     */ 
/*     */   public double getCY0() {
/* 356 */     return this.direction == 1 ? this.cy0 : this.cy1;
/*     */   }
/*     */ 
/*     */   public double getCX1() {
/* 360 */     return this.direction == -1 ? this.cx0 : this.cx1;
/*     */   }
/*     */ 
/*     */   public double getCY1() {
/* 364 */     return this.direction == -1 ? this.cy0 : this.cy1;
/*     */   }
/*     */ 
/*     */   public double getX1() {
/* 368 */     return this.direction == -1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY1() {
/* 372 */     return this.direction == -1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double TforY(double paramDouble)
/*     */   {
/* 390 */     if (paramDouble <= this.y0) return 0.0D;
/* 391 */     if (paramDouble >= this.y1) return 1.0D;
/* 392 */     if (paramDouble == this.YforT1) return this.TforY1;
/* 393 */     if (paramDouble == this.YforT2) return this.TforY2;
/* 394 */     if (paramDouble == this.YforT3) return this.TforY3;
/*     */ 
/* 396 */     if (this.ycoeff3 == 0.0D)
/*     */     {
/* 398 */       return Order2.TforY(paramDouble, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     }
/* 400 */     double d1 = this.ycoeff2 / this.ycoeff3;
/* 401 */     double d2 = this.ycoeff1 / this.ycoeff3;
/* 402 */     double d3 = (this.ycoeff0 - paramDouble) / this.ycoeff3;
/* 403 */     int i = 0;
/* 404 */     double d4 = (d1 * d1 - 3.0D * d2) / 9.0D;
/* 405 */     double d5 = (2.0D * d1 * d1 * d1 - 9.0D * d1 * d2 + 27.0D * d3) / 54.0D;
/* 406 */     double d6 = d5 * d5;
/* 407 */     double d7 = d4 * d4 * d4;
/* 408 */     double d8 = d1 / 3.0D;
/*     */     double d9;
/* 410 */     if (d6 < d7) {
/* 411 */       double d10 = Math.acos(d5 / Math.sqrt(d7));
/* 412 */       d4 = -2.0D * Math.sqrt(d4);
/* 413 */       d9 = refine(d1, d2, d3, paramDouble, d4 * Math.cos(d10 / 3.0D) - d8);
/* 414 */       if (d9 < 0.0D) {
/* 415 */         d9 = refine(d1, d2, d3, paramDouble, d4 * Math.cos((d10 + 6.283185307179586D) / 3.0D) - d8);
/*     */       }
/*     */ 
/* 418 */       if (d9 < 0.0D)
/* 419 */         d9 = refine(d1, d2, d3, paramDouble, d4 * Math.cos((d10 - 6.283185307179586D) / 3.0D) - d8);
/*     */     }
/*     */     else
/*     */     {
/* 423 */       int j = d5 < 0.0D ? 1 : 0;
/* 424 */       double d12 = Math.sqrt(d6 - d7);
/* 425 */       if (j != 0) {
/* 426 */         d5 = -d5;
/*     */       }
/* 428 */       double d14 = Math.pow(d5 + d12, 0.3333333333333333D);
/* 429 */       if (j == 0) {
/* 430 */         d14 = -d14;
/*     */       }
/* 432 */       double d16 = d14 == 0.0D ? 0.0D : d4 / d14;
/* 433 */       d9 = refine(d1, d2, d3, paramDouble, d14 + d16 - d8);
/*     */     }
/* 435 */     if (d9 < 0.0D)
/*     */     {
/* 437 */       double d11 = 0.0D;
/* 438 */       double d13 = 1.0D;
/*     */       while (true) {
/* 440 */         d9 = (d11 + d13) / 2.0D;
/* 441 */         if ((d9 == d11) || (d9 == d13)) {
/*     */           break;
/*     */         }
/* 444 */         double d15 = YforT(d9);
/* 445 */         if (d15 < paramDouble) {
/* 446 */           d11 = d9; } else {
/* 447 */           if (d15 <= paramDouble) break;
/* 448 */           d13 = d9;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 454 */     if (d9 >= 0.0D) {
/* 455 */       this.TforY3 = this.TforY2;
/* 456 */       this.YforT3 = this.YforT2;
/* 457 */       this.TforY2 = this.TforY1;
/* 458 */       this.YforT2 = this.YforT1;
/* 459 */       this.TforY1 = d9;
/* 460 */       this.YforT1 = paramDouble;
/*     */     }
/* 462 */     return d9;
/*     */   }
/*     */ 
/*     */   public double refine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5)
/*     */   {
/* 468 */     if ((paramDouble5 < -0.1D) || (paramDouble5 > 1.1D)) {
/* 469 */       return -1.0D;
/*     */     }
/* 471 */     double d1 = YforT(paramDouble5);
/*     */     double d2;
/*     */     double d3;
/* 473 */     if (d1 < paramDouble4) {
/* 474 */       d2 = paramDouble5;
/* 475 */       d3 = 1.0D;
/*     */     } else {
/* 477 */       d2 = 0.0D;
/* 478 */       d3 = paramDouble5;
/*     */     }
/* 480 */     double d4 = paramDouble5;
/* 481 */     double d5 = d1;
/* 482 */     int i = 1;
/* 483 */     while (d1 != paramDouble4)
/*     */     {
/*     */       double d6;
/* 484 */       if (i == 0) {
/* 485 */         d6 = (d2 + d3) / 2.0D;
/* 486 */         if ((d6 == d2) || (d6 == d3)) {
/*     */           break;
/*     */         }
/* 489 */         paramDouble5 = d6;
/*     */       } else {
/* 491 */         d6 = dYforT(paramDouble5, 1);
/* 492 */         if (d6 == 0.0D) {
/* 493 */           i = 0;
/* 494 */           continue;
/*     */         }
/* 496 */         double d7 = paramDouble5 + (paramDouble4 - d1) / d6;
/* 497 */         if ((d7 == paramDouble5) || (d7 <= d2) || (d7 >= d3)) {
/* 498 */           i = 0;
/* 499 */           continue;
/*     */         }
/* 501 */         paramDouble5 = d7;
/*     */       }
/* 503 */       d1 = YforT(paramDouble5);
/* 504 */       if (d1 < paramDouble4) {
/* 505 */         d2 = paramDouble5; } else {
/* 506 */         if (d1 <= paramDouble4) break;
/* 507 */         d3 = paramDouble5;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 512 */     int j = 0;
/*     */ 
/* 536 */     return paramDouble5 > 1.0D ? -1.0D : paramDouble5;
/*     */   }
/*     */ 
/*     */   public double XforY(double paramDouble) {
/* 540 */     if (paramDouble <= this.y0) {
/* 541 */       return this.x0;
/*     */     }
/* 543 */     if (paramDouble >= this.y1) {
/* 544 */       return this.x1;
/*     */     }
/* 546 */     return XforT(TforY(paramDouble));
/*     */   }
/*     */ 
/*     */   public double XforT(double paramDouble) {
/* 550 */     return ((this.xcoeff3 * paramDouble + this.xcoeff2) * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */   }
/*     */ 
/*     */   public double YforT(double paramDouble) {
/* 554 */     return ((this.ycoeff3 * paramDouble + this.ycoeff2) * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */   }
/*     */ 
/*     */   public double dXforT(double paramDouble, int paramInt) {
/* 558 */     switch (paramInt) {
/*     */     case 0:
/* 560 */       return ((this.xcoeff3 * paramDouble + this.xcoeff2) * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */     case 1:
/* 562 */       return (3.0D * this.xcoeff3 * paramDouble + 2.0D * this.xcoeff2) * paramDouble + this.xcoeff1;
/*     */     case 2:
/* 564 */       return 6.0D * this.xcoeff3 * paramDouble + 2.0D * this.xcoeff2;
/*     */     case 3:
/* 566 */       return 6.0D * this.xcoeff3;
/*     */     }
/* 568 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double dYforT(double paramDouble, int paramInt)
/*     */   {
/* 573 */     switch (paramInt) {
/*     */     case 0:
/* 575 */       return ((this.ycoeff3 * paramDouble + this.ycoeff2) * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */     case 1:
/* 577 */       return (3.0D * this.ycoeff3 * paramDouble + 2.0D * this.ycoeff2) * paramDouble + this.ycoeff1;
/*     */     case 2:
/* 579 */       return 6.0D * this.ycoeff3 * paramDouble + 2.0D * this.ycoeff2;
/*     */     case 3:
/* 581 */       return 6.0D * this.ycoeff3;
/*     */     }
/* 583 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double nextVertical(double paramDouble1, double paramDouble2)
/*     */   {
/* 588 */     double[] arrayOfDouble = { this.xcoeff1, 2.0D * this.xcoeff2, 3.0D * this.xcoeff3 };
/* 589 */     int i = solveQuadratic(arrayOfDouble, arrayOfDouble);
/* 590 */     for (int j = 0; j < i; j++) {
/* 591 */       if ((arrayOfDouble[j] > paramDouble1) && (arrayOfDouble[j] < paramDouble2)) {
/* 592 */         paramDouble2 = arrayOfDouble[j];
/*     */       }
/*     */     }
/* 595 */     return paramDouble2;
/*     */   }
/*     */ 
/*     */   public void enlarge(RectBounds paramRectBounds) {
/* 599 */     paramRectBounds.add((float)this.x0, (float)this.y0);
/* 600 */     double[] arrayOfDouble = { this.xcoeff1, 2.0D * this.xcoeff2, 3.0D * this.xcoeff3 };
/* 601 */     int i = solveQuadratic(arrayOfDouble, arrayOfDouble);
/* 602 */     for (int j = 0; j < i; j++) {
/* 603 */       double d = arrayOfDouble[j];
/* 604 */       if ((d > 0.0D) && (d < 1.0D)) {
/* 605 */         paramRectBounds.add((float)XforT(d), (float)YforT(d));
/*     */       }
/*     */     }
/* 608 */     paramRectBounds.add((float)this.x1, (float)this.y1);
/*     */   }
/*     */ 
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt) {
/* 612 */     if ((paramDouble1 <= this.y0) && (paramDouble2 >= this.y1)) {
/* 613 */       return getWithDirection(paramInt);
/*     */     }
/* 615 */     double[] arrayOfDouble = new double[14];
/*     */ 
/* 617 */     double d1 = TforY(paramDouble1);
/* 618 */     double d2 = TforY(paramDouble2);
/* 619 */     arrayOfDouble[0] = this.x0;
/* 620 */     arrayOfDouble[1] = this.y0;
/* 621 */     arrayOfDouble[2] = this.cx0;
/* 622 */     arrayOfDouble[3] = this.cy0;
/* 623 */     arrayOfDouble[4] = this.cx1;
/* 624 */     arrayOfDouble[5] = this.cy1;
/* 625 */     arrayOfDouble[6] = this.x1;
/* 626 */     arrayOfDouble[7] = this.y1;
/* 627 */     if (d1 > d2)
/*     */     {
/* 642 */       double d3 = d1;
/* 643 */       d1 = d2;
/* 644 */       d2 = d3;
/*     */     }
/* 646 */     if (d2 < 1.0D)
/* 647 */       split(arrayOfDouble, 0, d2);
/*     */     int i;
/* 650 */     if (d1 <= 0.0D) {
/* 651 */       i = 0;
/*     */     } else {
/* 653 */       split(arrayOfDouble, 0, d1 / d2);
/* 654 */       i = 6;
/*     */     }
/* 656 */     return new Order3(arrayOfDouble[(i + 0)], paramDouble1, arrayOfDouble[(i + 2)], arrayOfDouble[(i + 3)], arrayOfDouble[(i + 4)], arrayOfDouble[(i + 5)], arrayOfDouble[(i + 6)], paramDouble2, paramInt);
/*     */   }
/*     */ 
/*     */   public Curve getReversedCurve()
/*     */   {
/* 664 */     return new Order3(this.x0, this.y0, this.cx0, this.cy0, this.cx1, this.cy1, this.x1, this.y1, -this.direction);
/*     */   }
/*     */ 
/*     */   public int getSegment(float[] paramArrayOfFloat) {
/* 668 */     if (this.direction == 1) {
/* 669 */       paramArrayOfFloat[0] = ((float)this.cx0);
/* 670 */       paramArrayOfFloat[1] = ((float)this.cy0);
/* 671 */       paramArrayOfFloat[2] = ((float)this.cx1);
/* 672 */       paramArrayOfFloat[3] = ((float)this.cy1);
/* 673 */       paramArrayOfFloat[4] = ((float)this.x1);
/* 674 */       paramArrayOfFloat[5] = ((float)this.y1);
/*     */     } else {
/* 676 */       paramArrayOfFloat[0] = ((float)this.cx1);
/* 677 */       paramArrayOfFloat[1] = ((float)this.cy1);
/* 678 */       paramArrayOfFloat[2] = ((float)this.cx0);
/* 679 */       paramArrayOfFloat[3] = ((float)this.cy0);
/* 680 */       paramArrayOfFloat[4] = ((float)this.x0);
/* 681 */       paramArrayOfFloat[5] = ((float)this.y0);
/*     */     }
/* 683 */     return 3;
/*     */   }
/*     */ 
/*     */   public String controlPointString()
/*     */   {
/* 688 */     return "(" + round(getCX0()) + ", " + round(getCY0()) + "), " + "(" + round(getCX1()) + ", " + round(getCY1()) + "), ";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Order3
 * JD-Core Version:    0.6.2
 */