/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ final class Order0 extends Curve
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public Order0(double paramDouble1, double paramDouble2)
/*     */   {
/*  33 */     super(1);
/*  34 */     this.x = paramDouble1;
/*  35 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */   public int getOrder() {
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */   public double getXTop() {
/*  43 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getYTop() {
/*  47 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double getXBot() {
/*  51 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getYBot() {
/*  55 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double getXMin() {
/*  59 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getXMax() {
/*  63 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getX0() {
/*  67 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getY0() {
/*  71 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double getX1() {
/*  75 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double getY1() {
/*  79 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double XforY(double paramDouble) {
/*  83 */     return paramDouble;
/*     */   }
/*     */ 
/*     */   public double TforY(double paramDouble) {
/*  87 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double XforT(double paramDouble) {
/*  91 */     return this.x;
/*     */   }
/*     */ 
/*     */   public double YforT(double paramDouble) {
/*  95 */     return this.y;
/*     */   }
/*     */ 
/*     */   public double dXforT(double paramDouble, int paramInt) {
/*  99 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double dYforT(double paramDouble, int paramInt) {
/* 103 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public double nextVertical(double paramDouble1, double paramDouble2) {
/* 107 */     return paramDouble2;
/*     */   }
/*     */ 
/*     */   public int crossingsFor(double paramDouble1, double paramDouble2)
/*     */   {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean accumulateCrossings(Crossings paramCrossings)
/*     */   {
/* 117 */     return (this.x > paramCrossings.getXLo()) && (this.x < paramCrossings.getXHi()) && (this.y > paramCrossings.getYLo()) && (this.y < paramCrossings.getYHi());
/*     */   }
/*     */ 
/*     */   public void enlarge(RectBounds paramRectBounds)
/*     */   {
/* 124 */     paramRectBounds.add((float)this.x, (float)this.y);
/*     */   }
/*     */ 
/*     */   public Curve getSubCurve(double paramDouble1, double paramDouble2, int paramInt) {
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public Curve getReversedCurve() {
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */   public int getSegment(float[] paramArrayOfFloat) {
/* 136 */     paramArrayOfFloat[0] = ((float)this.x);
/* 137 */     paramArrayOfFloat[1] = ((float)this.y);
/* 138 */     return 0;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Order0
 * JD-Core Version:    0.6.2
 */