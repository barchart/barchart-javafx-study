/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ final class Order2 extends Curve
/*     */ {
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double cx0;
/*     */   private double cy0;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private double xmin;
/*     */   private double xmax;
/*     */   private double xcoeff0;
/*     */   private double xcoeff1;
/*     */   private double xcoeff2;
/*     */   private double ycoeff0;
/*     */   private double ycoeff1;
/*     */   private double ycoeff2;
/*     */ 
/*     */   public static void insert(Vector paramVector, double[] paramArrayOfDouble, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt)
/*     */   {
/*  53 */     int i = getHorizontalParams(paramDouble2, paramDouble4, paramDouble6, paramArrayOfDouble);
/*  54 */     if (i == 0)
/*     */     {
/*  57 */       addInstance(paramVector, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramInt);
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     double d = paramArrayOfDouble[0];
/*  62 */     paramArrayOfDouble[0] = paramDouble1; paramArrayOfDouble[1] = paramDouble2;
/*  63 */     paramArrayOfDouble[2] = paramDouble3; paramArrayOfDouble[3] = paramDouble4;
/*  64 */     paramArrayOfDouble[4] = paramDouble5; paramArrayOfDouble[5] = paramDouble6;
/*  65 */     split(paramArrayOfDouble, 0, d);
/*  66 */     int j = paramInt == 1 ? 0 : 4;
/*  67 */     int k = 4 - j;
/*  68 */     addInstance(paramVector, paramArrayOfDouble[j], paramArrayOfDouble[(j + 1)], paramArrayOfDouble[(j + 2)], paramArrayOfDouble[(j + 3)], paramArrayOfDouble[(j + 4)], paramArrayOfDouble[(j + 5)], paramInt);
/*     */ 
/*  70 */     addInstance(paramVector, paramArrayOfDouble[k], paramArrayOfDouble[(k + 1)], paramArrayOfDouble[(k + 2)], paramArrayOfDouble[(k + 3)], paramArrayOfDouble[(k + 4)], paramArrayOfDouble[(k + 5)], paramInt);
/*     */   }
/*     */ 
/*     */   public static void addInstance(Vector paramVector, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt)
/*     */   {
/*  79 */     if (paramDouble2 > paramDouble6)
/*  80 */       paramVector.add(new Order2(paramDouble5, paramDouble6, paramDouble3, paramDouble4, paramDouble1, paramDouble2, -paramInt));
/*  81 */     else if (paramDouble6 > paramDouble2)
/*  82 */       paramVector.add(new Order2(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramInt));
/*     */   }
/*     */ 
/*     */   public static int getHorizontalParams(double paramDouble1, double paramDouble2, double paramDouble3, double[] paramArrayOfDouble)
/*     */   {
/* 110 */     if ((paramDouble1 <= paramDouble2) && (paramDouble2 <= paramDouble3)) {
/* 111 */       return 0;
/*     */     }
/* 113 */     paramDouble1 -= paramDouble2;
/* 114 */     paramDouble3 -= paramDouble2;
/* 115 */     double d1 = paramDouble1 + paramDouble3;
/*     */ 
/* 117 */     if (d1 == 0.0D) {
/* 118 */       return 0;
/*     */     }
/* 120 */     double d2 = paramDouble1 / d1;
/*     */ 
/* 122 */     if ((d2 <= 0.0D) || (d2 >= 1.0D)) {
/* 123 */       return 0;
/*     */     }
/* 125 */     paramArrayOfDouble[0] = d2;
/* 126 */     return 1;
/*     */   }
/*     */ 
/*     */   public static void split(double[] paramArrayOfDouble, int paramInt, double paramDouble)
/*     */   {
/*     */     double tmp10_9 = paramArrayOfDouble[(paramInt + 4)]; double d5 = tmp10_9; paramArrayOfDouble[(paramInt + 8)] = tmp10_9;
/*     */     double tmp24_23 = paramArrayOfDouble[(paramInt + 5)]; double d6 = tmp24_23; paramArrayOfDouble[(paramInt + 9)] = tmp24_23;
/* 139 */     double d3 = paramArrayOfDouble[(paramInt + 2)];
/* 140 */     double d4 = paramArrayOfDouble[(paramInt + 3)];
/* 141 */     d5 = d3 + (d5 - d3) * paramDouble;
/* 142 */     d6 = d4 + (d6 - d4) * paramDouble;
/* 143 */     double d1 = paramArrayOfDouble[(paramInt + 0)];
/* 144 */     double d2 = paramArrayOfDouble[(paramInt + 1)];
/* 145 */     d1 += (d3 - d1) * paramDouble;
/* 146 */     d2 += (d4 - d2) * paramDouble;
/* 147 */     d3 = d1 + (d5 - d1) * paramDouble;
/* 148 */     d4 = d2 + (d6 - d2) * paramDouble;
/* 149 */     paramArrayOfDouble[(paramInt + 2)] = d1;
/* 150 */     paramArrayOfDouble[(paramInt + 3)] = d2;
/* 151 */     paramArrayOfDouble[(paramInt + 4)] = d3;
/* 152 */     paramArrayOfDouble[(paramInt + 5)] = d4;
/* 153 */     paramArrayOfDouble[(paramInt + 6)] = d5;
/* 154 */     paramArrayOfDouble[(paramInt + 7)] = d6;
/*     */   }
/*     */ 
/*     */   public Order2(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, int paramInt)
/*     */   {
/* 162 */     super(paramInt);
/*     */ 
/* 166 */     if (paramDouble4 < paramDouble2)
/* 167 */       paramDouble4 = paramDouble2;
/* 168 */     else if (paramDouble4 > paramDouble6) {
/* 169 */       paramDouble4 = paramDouble6;
/*     */     }
/* 171 */     this.x0 = paramDouble1;
/* 172 */     this.y0 = paramDouble2;
/* 173 */     this.cx0 = paramDouble3;
/* 174 */     this.cy0 = paramDouble4;
/* 175 */     this.x1 = paramDouble5;
/* 176 */     this.y1 = paramDouble6;
/* 177 */     this.xmin = Math.min(Math.min(paramDouble1, paramDouble5), paramDouble3);
/* 178 */     this.xmax = Math.max(Math.max(paramDouble1, paramDouble5), paramDouble3);
/* 179 */     this.xcoeff0 = paramDouble1;
/* 180 */     this.xcoeff1 = (paramDouble3 + paramDouble3 - paramDouble1 - paramDouble1);
/* 181 */     this.xcoeff2 = (paramDouble1 - paramDouble3 - paramDouble3 + paramDouble5);
/* 182 */     this.ycoeff0 = paramDouble2;
/* 183 */     this.ycoeff1 = (paramDouble4 + paramDouble4 - paramDouble2 - paramDouble2);
/* 184 */     this.ycoeff2 = (paramDouble2 - paramDouble4 - paramDouble4 + paramDouble6);
/*     */   }
/*     */ 
/*     */   public int getOrder() {
/* 188 */     return 2;
/*     */   }
/*     */ 
/*     */   public double getXTop() {
/* 192 */     return this.x0;
/*     */   }
/*     */ 
/*     */   public double getYTop() {
/* 196 */     return this.y0;
/*     */   }
/*     */ 
/*     */   public double getXBot() {
/* 200 */     return this.x1;
/*     */   }
/*     */ 
/*     */   public double getYBot() {
/* 204 */     return this.y1;
/*     */   }
/*     */ 
/*     */   public double getXMin() {
/* 208 */     return this.xmin;
/*     */   }
/*     */ 
/*     */   public double getXMax() {
/* 212 */     return this.xmax;
/*     */   }
/*     */ 
/*     */   public double getX0() {
/* 216 */     return this.direction == 1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY0() {
/* 220 */     return this.direction == 1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double getCX0() {
/* 224 */     return this.cx0;
/*     */   }
/*     */ 
/*     */   public double getCY0() {
/* 228 */     return this.cy0;
/*     */   }
/*     */ 
/*     */   public double getX1() {
/* 232 */     return this.direction == -1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY1() {
/* 236 */     return this.direction == -1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double XforY(double paramDouble) {
/* 240 */     if (paramDouble <= this.y0) {
/* 241 */       return this.x0;
/*     */     }
/* 243 */     if (paramDouble >= this.y1) {
/* 244 */       return this.x1;
/*     */     }
/* 246 */     return XforT(TforY(paramDouble));
/*     */   }
/*     */ 
/*     */   public double TforY(double paramDouble) {
/* 250 */     if (paramDouble <= this.y0) {
/* 251 */       return 0.0D;
/*     */     }
/* 253 */     if (paramDouble >= this.y1) {
/* 254 */       return 1.0D;
/*     */     }
/* 256 */     return TforY(paramDouble, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */   }
/*     */ 
/*     */   public static double TforY(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 264 */     paramDouble2 -= paramDouble1;
/* 265 */     if (paramDouble4 == 0.0D)
/*     */     {
/* 271 */       d1 = -paramDouble2 / paramDouble3;
/* 272 */       if ((d1 >= 0.0D) && (d1 <= 1.0D))
/* 273 */         return d1;
/*     */     }
/*     */     else
/*     */     {
/* 277 */       d1 = paramDouble3 * paramDouble3 - 4.0D * paramDouble4 * paramDouble2;
/*     */ 
/* 279 */       if (d1 >= 0.0D) {
/* 280 */         d1 = Math.sqrt(d1);
/*     */ 
/* 287 */         if (paramDouble3 < 0.0D) {
/* 288 */           d1 = -d1;
/*     */         }
/* 290 */         d2 = (paramDouble3 + d1) / -2.0D;
/*     */ 
/* 292 */         double d3 = d2 / paramDouble4;
/* 293 */         if ((d3 >= 0.0D) && (d3 <= 1.0D)) {
/* 294 */           return d3;
/*     */         }
/* 296 */         if (d2 != 0.0D) {
/* 297 */           d3 = paramDouble2 / d2;
/* 298 */           if ((d3 >= 0.0D) && (d3 <= 1.0D)) {
/* 299 */             return d3;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 336 */     double d1 = paramDouble2;
/* 337 */     double d2 = paramDouble2 + paramDouble3 + paramDouble4;
/* 338 */     return 0.0D < (d1 + d2) / 2.0D ? 0.0D : 1.0D;
/*     */   }
/*     */ 
/*     */   public double XforT(double paramDouble) {
/* 342 */     return (this.xcoeff2 * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */   }
/*     */ 
/*     */   public double YforT(double paramDouble) {
/* 346 */     return (this.ycoeff2 * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */   }
/*     */ 
/*     */   public double dXforT(double paramDouble, int paramInt) {
/* 350 */     switch (paramInt) {
/*     */     case 0:
/* 352 */       return (this.xcoeff2 * paramDouble + this.xcoeff1) * paramDouble + this.xcoeff0;
/*     */     case 1:
/* 354 */       return 2.0D * this.xcoeff2 * paramDouble + this.xcoeff1;
/*     */     case 2:
/* 356 */       return 2.0D * this.xcoeff2;
/*     */     }
/* 358 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double dYforT(double paramDouble, int paramInt)
/*     */   {
/* 363 */     switch (paramInt) {
/*     */     case 0:
/* 365 */       return (this.ycoeff2 * paramDouble + this.ycoeff1) * paramDouble + this.ycoeff0;
/*     */     case 1:
/* 367 */       return 2.0D * this.ycoeff2 * paramDouble + this.ycoeff1;
/*     */     case 2:
/* 369 */       return 2.0D * this.ycoeff2;
/*     */     }
/* 371 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double nextVertical(double paramDouble1, double paramDouble2)
/*     */   {
/* 376 */     double d = -this.xcoeff1 / (2.0D * this.xcoeff2);
/* 377 */     if ((d > paramDouble1) && (d < paramDouble2)) {
/* 378 */       return d;
/*     */     }
/* 380 */     return paramDouble2;
/*     */   }
/*     */ 
/*     */   public void enlarge(RectBounds paramRectBounds) {
/* 384 */     paramRectBounds.add((float)this.x0, (float)this.y0);
/* 385 */     double d = -this.xcoeff1 / (2.0D * this.xcoeff2);
/* 386 */     if ((d > 0.0D) && (d < 1.0D)) {
/* 387 */       paramRectBounds.add((float)XforT(d), (float)YforT(d));
/*     */     }
/* 389 */     paramRectBounds.add((float)this.x1, (float)this.y1);
/*     */   }
/*     */ 
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt)
/*     */   {
/*     */     double d1;
/* 394 */     if (paramDouble1 <= this.y0) {
/* 395 */       if (paramDouble2 >= this.y1) {
/* 396 */         return getWithDirection(paramInt);
/*     */       }
/* 398 */       d1 = 0.0D;
/*     */     } else {
/* 400 */       d1 = TforY(paramDouble1, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     }
/*     */     double d2;
/* 402 */     if (paramDouble2 >= this.y1)
/* 403 */       d2 = 1.0D;
/*     */     else {
/* 405 */       d2 = TforY(paramDouble2, this.ycoeff0, this.ycoeff1, this.ycoeff2);
/*     */     }
/* 407 */     double[] arrayOfDouble = new double[10];
/* 408 */     arrayOfDouble[0] = this.x0;
/* 409 */     arrayOfDouble[1] = this.y0;
/* 410 */     arrayOfDouble[2] = this.cx0;
/* 411 */     arrayOfDouble[3] = this.cy0;
/* 412 */     arrayOfDouble[4] = this.x1;
/* 413 */     arrayOfDouble[5] = this.y1;
/* 414 */     if (d2 < 1.0D)
/* 415 */       split(arrayOfDouble, 0, d2);
/*     */     int i;
/* 418 */     if (d1 <= 0.0D) {
/* 419 */       i = 0;
/*     */     } else {
/* 421 */       split(arrayOfDouble, 0, d1 / d2);
/* 422 */       i = 4;
/*     */     }
/* 424 */     return new Order2(arrayOfDouble[(i + 0)], paramDouble1, arrayOfDouble[(i + 2)], arrayOfDouble[(i + 3)], arrayOfDouble[(i + 4)], paramDouble2, paramInt);
/*     */   }
/*     */ 
/*     */   public Curve getReversedCurve()
/*     */   {
/* 431 */     return new Order2(this.x0, this.y0, this.cx0, this.cy0, this.x1, this.y1, -this.direction);
/*     */   }
/*     */ 
/*     */   public int getSegment(float[] paramArrayOfFloat) {
/* 435 */     paramArrayOfFloat[0] = ((float)this.cx0);
/* 436 */     paramArrayOfFloat[1] = ((float)this.cy0);
/* 437 */     if (this.direction == 1) {
/* 438 */       paramArrayOfFloat[2] = ((float)this.x1);
/* 439 */       paramArrayOfFloat[3] = ((float)this.y1);
/*     */     } else {
/* 441 */       paramArrayOfFloat[2] = ((float)this.x0);
/* 442 */       paramArrayOfFloat[3] = ((float)this.y0);
/*     */     }
/* 444 */     return 2;
/*     */   }
/*     */ 
/*     */   public String controlPointString()
/*     */   {
/* 449 */     return "(" + round(this.cx0) + ", " + round(this.cy0) + "), ";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Order2
 * JD-Core Version:    0.6.2
 */