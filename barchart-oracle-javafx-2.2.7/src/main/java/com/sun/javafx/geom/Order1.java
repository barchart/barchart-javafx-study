/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ final class Order1 extends Curve
/*     */ {
/*     */   private double x0;
/*     */   private double y0;
/*     */   private double x1;
/*     */   private double y1;
/*     */   private double xmin;
/*     */   private double xmax;
/*     */ 
/*     */   public Order1(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt)
/*     */   {
/*  40 */     super(paramInt);
/*  41 */     this.x0 = paramDouble1;
/*  42 */     this.y0 = paramDouble2;
/*  43 */     this.x1 = paramDouble3;
/*  44 */     this.y1 = paramDouble4;
/*  45 */     if (paramDouble1 < paramDouble3) {
/*  46 */       this.xmin = paramDouble1;
/*  47 */       this.xmax = paramDouble3;
/*     */     } else {
/*  49 */       this.xmin = paramDouble3;
/*  50 */       this.xmax = paramDouble1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getOrder() {
/*  55 */     return 1;
/*     */   }
/*     */ 
/*     */   public double getXTop() {
/*  59 */     return this.x0;
/*     */   }
/*     */ 
/*     */   public double getYTop() {
/*  63 */     return this.y0;
/*     */   }
/*     */ 
/*     */   public double getXBot() {
/*  67 */     return this.x1;
/*     */   }
/*     */ 
/*     */   public double getYBot() {
/*  71 */     return this.y1;
/*     */   }
/*     */ 
/*     */   public double getXMin() {
/*  75 */     return this.xmin;
/*     */   }
/*     */ 
/*     */   public double getXMax() {
/*  79 */     return this.xmax;
/*     */   }
/*     */ 
/*     */   public double getX0() {
/*  83 */     return this.direction == 1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY0() {
/*  87 */     return this.direction == 1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double getX1() {
/*  91 */     return this.direction == -1 ? this.x0 : this.x1;
/*     */   }
/*     */ 
/*     */   public double getY1() {
/*  95 */     return this.direction == -1 ? this.y0 : this.y1;
/*     */   }
/*     */ 
/*     */   public double XforY(double paramDouble) {
/*  99 */     if ((this.x0 == this.x1) || (paramDouble <= this.y0)) {
/* 100 */       return this.x0;
/*     */     }
/* 102 */     if (paramDouble >= this.y1) {
/* 103 */       return this.x1;
/*     */     }
/*     */ 
/* 106 */     return this.x0 + (paramDouble - this.y0) * (this.x1 - this.x0) / (this.y1 - this.y0);
/*     */   }
/*     */ 
/*     */   public double TforY(double paramDouble) {
/* 110 */     if (paramDouble <= this.y0) {
/* 111 */       return 0.0D;
/*     */     }
/* 113 */     if (paramDouble >= this.y1) {
/* 114 */       return 1.0D;
/*     */     }
/* 116 */     return (paramDouble - this.y0) / (this.y1 - this.y0);
/*     */   }
/*     */ 
/*     */   public double XforT(double paramDouble) {
/* 120 */     return this.x0 + paramDouble * (this.x1 - this.x0);
/*     */   }
/*     */ 
/*     */   public double YforT(double paramDouble) {
/* 124 */     return this.y0 + paramDouble * (this.y1 - this.y0);
/*     */   }
/*     */ 
/*     */   public double dXforT(double paramDouble, int paramInt) {
/* 128 */     switch (paramInt) {
/*     */     case 0:
/* 130 */       return this.x0 + paramDouble * (this.x1 - this.x0);
/*     */     case 1:
/* 132 */       return this.x1 - this.x0;
/*     */     }
/* 134 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double dYforT(double paramDouble, int paramInt)
/*     */   {
/* 139 */     switch (paramInt) {
/*     */     case 0:
/* 141 */       return this.y0 + paramDouble * (this.y1 - this.y0);
/*     */     case 1:
/* 143 */       return this.y1 - this.y0;
/*     */     }
/* 145 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double nextVertical(double paramDouble1, double paramDouble2)
/*     */   {
/* 150 */     return paramDouble2;
/*     */   }
/*     */ 
/*     */   public boolean accumulateCrossings(Crossings paramCrossings)
/*     */   {
/* 155 */     double d1 = paramCrossings.getXLo();
/* 156 */     double d2 = paramCrossings.getYLo();
/* 157 */     double d3 = paramCrossings.getXHi();
/* 158 */     double d4 = paramCrossings.getYHi();
/* 159 */     if (this.xmin >= d3)
/* 160 */       return false;
/*     */     double d6;
/*     */     double d5;
/* 163 */     if (this.y0 < d2) {
/* 164 */       if (this.y1 <= d2) {
/* 165 */         return false;
/*     */       }
/* 167 */       d6 = d2;
/* 168 */       d5 = XforY(d2);
/*     */     } else {
/* 170 */       if (this.y0 >= d4) {
/* 171 */         return false;
/*     */       }
/* 173 */       d6 = this.y0;
/* 174 */       d5 = this.x0;
/*     */     }
/*     */     double d8;
/*     */     double d7;
/* 176 */     if (this.y1 > d4) {
/* 177 */       d8 = d4;
/* 178 */       d7 = XforY(d4);
/*     */     } else {
/* 180 */       d8 = this.y1;
/* 181 */       d7 = this.x1;
/*     */     }
/* 183 */     if ((d5 >= d3) && (d7 >= d3)) {
/* 184 */       return false;
/*     */     }
/* 186 */     if ((d5 > d1) || (d7 > d1)) {
/* 187 */       return true;
/*     */     }
/* 189 */     paramCrossings.record(d6, d8, this.direction);
/* 190 */     return false;
/*     */   }
/*     */ 
/*     */   public void enlarge(RectBounds paramRectBounds) {
/* 194 */     paramRectBounds.add((float)this.x0, (float)this.y0);
/* 195 */     paramRectBounds.add((float)this.x1, (float)this.y1);
/*     */   }
/*     */ 
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt) {
/* 199 */     if ((paramDouble1 == this.y0) && (paramDouble2 == this.y1)) {
/* 200 */       return getWithDirection(paramInt);
/*     */     }
/* 202 */     if (this.x0 == this.x1) {
/* 203 */       return new Order1(this.x0, paramDouble1, this.x1, paramDouble2, paramInt);
/*     */     }
/* 205 */     double d1 = this.x0 - this.x1;
/* 206 */     double d2 = this.y0 - this.y1;
/* 207 */     double d3 = this.x0 + (paramDouble1 - this.y0) * d1 / d2;
/* 208 */     double d4 = this.x0 + (paramDouble2 - this.y0) * d1 / d2;
/* 209 */     return new Order1(d3, paramDouble1, d4, paramDouble2, paramInt);
/*     */   }
/*     */ 
/*     */   public Curve getReversedCurve() {
/* 213 */     return new Order1(this.x0, this.y0, this.x1, this.y1, -this.direction);
/*     */   }
/*     */ 
/*     */   public int compareTo(Curve paramCurve, double[] paramArrayOfDouble)
/*     */   {
/* 218 */     if (!(paramCurve instanceof Order1)) {
/* 219 */       return super.compareTo(paramCurve, paramArrayOfDouble);
/*     */     }
/* 221 */     Order1 localOrder1 = (Order1)paramCurve;
/* 222 */     if (paramArrayOfDouble[1] <= paramArrayOfDouble[0]) {
/* 223 */       throw new InternalError("yrange already screwed up...");
/*     */     }
/* 225 */     paramArrayOfDouble[1] = Math.min(Math.min(paramArrayOfDouble[1], this.y1), localOrder1.y1);
/* 226 */     if (paramArrayOfDouble[1] <= paramArrayOfDouble[0]) {
/* 227 */       throw new InternalError("backstepping from " + paramArrayOfDouble[0] + " to " + paramArrayOfDouble[1]);
/*     */     }
/* 229 */     if (this.xmax <= localOrder1.xmin) {
/* 230 */       return this.xmin == localOrder1.xmax ? 0 : -1;
/*     */     }
/* 232 */     if (this.xmin >= localOrder1.xmax) {
/* 233 */       return 1;
/*     */     }
/*     */ 
/* 267 */     double d1 = this.x1 - this.x0;
/* 268 */     double d2 = this.y1 - this.y0;
/* 269 */     double d3 = localOrder1.x1 - localOrder1.x0;
/* 270 */     double d4 = localOrder1.y1 - localOrder1.y0;
/* 271 */     double d5 = d3 * d2 - d1 * d4;
/*     */     double d6;
/* 273 */     if (d5 != 0.0D) {
/* 274 */       double d7 = (this.x0 - localOrder1.x0) * d2 * d4 - this.y0 * d1 * d4 + localOrder1.y0 * d3 * d2;
/*     */ 
/* 277 */       d6 = d7 / d5;
/* 278 */       if (d6 <= paramArrayOfDouble[0])
/*     */       {
/* 281 */         d6 = Math.min(this.y1, localOrder1.y1);
/*     */       }
/*     */       else {
/* 284 */         if (d6 < paramArrayOfDouble[1])
/*     */         {
/* 286 */           paramArrayOfDouble[1] = d6;
/*     */         }
/*     */ 
/* 289 */         d6 = Math.max(this.y0, localOrder1.y0);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 295 */       d6 = Math.max(this.y0, localOrder1.y0);
/*     */     }
/* 297 */     return orderof(XforY(d6), localOrder1.XforY(d6));
/*     */   }
/*     */ 
/*     */   public int getSegment(float[] paramArrayOfFloat) {
/* 301 */     if (this.direction == 1) {
/* 302 */       paramArrayOfFloat[0] = ((float)this.x1);
/* 303 */       paramArrayOfFloat[1] = ((float)this.y1);
/*     */     } else {
/* 305 */       paramArrayOfFloat[0] = ((float)this.x0);
/* 306 */       paramArrayOfFloat[1] = ((float)this.y0);
/*     */     }
/* 308 */     return 1;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Order1
 * JD-Core Version:    0.6.2
 */