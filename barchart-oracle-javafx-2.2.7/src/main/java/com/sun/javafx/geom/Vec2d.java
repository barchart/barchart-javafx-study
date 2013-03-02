/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Vec2d
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */ 
/*     */   public Vec2d()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vec2d(double paramDouble1, double paramDouble2)
/*     */   {
/*  30 */     this.x = paramDouble1;
/*  31 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */   public Vec2d(Vec2d paramVec2d) {
/*  35 */     set(paramVec2d);
/*     */   }
/*     */ 
/*     */   public Vec2d(Vec2f paramVec2f) {
/*  39 */     set(paramVec2f);
/*     */   }
/*     */ 
/*     */   public void set(Vec2d paramVec2d) {
/*  43 */     this.x = paramVec2d.x;
/*  44 */     this.y = paramVec2d.y;
/*     */   }
/*     */ 
/*     */   public void set(Vec2f paramVec2f) {
/*  48 */     this.x = paramVec2f.x;
/*  49 */     this.y = paramVec2f.y;
/*     */   }
/*     */ 
/*     */   public void set(double paramDouble1, double paramDouble2) {
/*  53 */     this.x = paramDouble1;
/*  54 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */   public static double distanceSq(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  68 */     paramDouble1 -= paramDouble3;
/*  69 */     paramDouble2 -= paramDouble4;
/*  70 */     return paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2;
/*     */   }
/*     */ 
/*     */   public static double distance(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  84 */     paramDouble1 -= paramDouble3;
/*  85 */     paramDouble2 -= paramDouble4;
/*  86 */     return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/*     */   }
/*     */ 
/*     */   public double distanceSq(double paramDouble1, double paramDouble2)
/*     */   {
/* 101 */     paramDouble1 -= this.x;
/* 102 */     paramDouble2 -= this.y;
/* 103 */     return paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2;
/*     */   }
/*     */ 
/*     */   public double distanceSq(Vec2d paramVec2d)
/*     */   {
/* 116 */     double d1 = paramVec2d.x - this.x;
/* 117 */     double d2 = paramVec2d.y - this.y;
/* 118 */     return d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   public double distance(double paramDouble1, double paramDouble2)
/*     */   {
/* 133 */     paramDouble1 -= this.x;
/* 134 */     paramDouble2 -= this.y;
/* 135 */     return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
/*     */   }
/*     */ 
/*     */   public double distance(Vec2d paramVec2d)
/*     */   {
/* 148 */     double d1 = paramVec2d.x - this.x;
/* 149 */     double d2 = paramVec2d.y - this.y;
/* 150 */     return Math.sqrt(d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 159 */     long l = 7L;
/* 160 */     l = 31L * l + Double.doubleToLongBits(this.x);
/* 161 */     l = 31L * l + Double.doubleToLongBits(this.y);
/* 162 */     return (int)(l ^ l >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 177 */     if (paramObject == this) {
/* 178 */       return true;
/*     */     }
/* 180 */     if ((paramObject instanceof Vec2d)) {
/* 181 */       Vec2d localVec2d = (Vec2d)paramObject;
/* 182 */       return (this.x == localVec2d.x) && (this.y == localVec2d.y);
/*     */     }
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 194 */     return "Vec2d[" + this.x + ", " + this.y + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec2d
 * JD-Core Version:    0.6.2
 */