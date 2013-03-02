/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Point2D
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */ 
/*     */   public Point2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Point2D(float paramFloat1, float paramFloat2)
/*     */   {
/*  60 */     this.x = paramFloat1;
/*  61 */     this.y = paramFloat2;
/*     */   }
/*     */ 
/*     */   public void setLocation(float paramFloat1, float paramFloat2)
/*     */   {
/*  72 */     this.x = paramFloat1;
/*  73 */     this.y = paramFloat2;
/*     */   }
/*     */ 
/*     */   public void setLocation(Point2D paramPoint2D)
/*     */   {
/*  83 */     setLocation(paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public static float distanceSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  97 */     paramFloat1 -= paramFloat3;
/*  98 */     paramFloat2 -= paramFloat4;
/*  99 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2;
/*     */   }
/*     */ 
/*     */   public static float distance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 113 */     paramFloat1 -= paramFloat3;
/* 114 */     paramFloat2 -= paramFloat4;
/* 115 */     return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */   }
/*     */ 
/*     */   public float distanceSq(float paramFloat1, float paramFloat2)
/*     */   {
/* 130 */     paramFloat1 -= this.x;
/* 131 */     paramFloat2 -= this.y;
/* 132 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2;
/*     */   }
/*     */ 
/*     */   public float distanceSq(Point2D paramPoint2D)
/*     */   {
/* 145 */     float f1 = paramPoint2D.x - this.x;
/* 146 */     float f2 = paramPoint2D.y - this.y;
/* 147 */     return f1 * f1 + f2 * f2;
/*     */   }
/*     */ 
/*     */   public float distance(float paramFloat1, float paramFloat2)
/*     */   {
/* 162 */     paramFloat1 -= this.x;
/* 163 */     paramFloat2 -= this.y;
/* 164 */     return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */   }
/*     */ 
/*     */   public float distance(Point2D paramPoint2D)
/*     */   {
/* 177 */     float f1 = paramPoint2D.x - this.x;
/* 178 */     float f2 = paramPoint2D.y - this.y;
/* 179 */     return (float)Math.sqrt(f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 187 */     int i = Float.floatToIntBits(this.x);
/* 188 */     i ^= Float.floatToIntBits(this.y) * 31;
/* 189 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 203 */     if (paramObject == this) return true;
/* 204 */     if ((paramObject instanceof Point2D)) {
/* 205 */       Point2D localPoint2D = (Point2D)paramObject;
/* 206 */       return (this.x == localPoint2D.x) && (this.y == localPoint2D.y);
/*     */     }
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 217 */     return "Point2D[" + this.x + ", " + this.y + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Point2D
 * JD-Core Version:    0.6.2
 */