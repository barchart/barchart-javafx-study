/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Vec2f
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */ 
/*     */   public Vec2f()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vec2f(float paramFloat1, float paramFloat2)
/*     */   {
/*  30 */     this.x = paramFloat1;
/*  31 */     this.y = paramFloat2;
/*     */   }
/*     */ 
/*     */   public Vec2f(Vec2f paramVec2f) {
/*  35 */     this.x = paramVec2f.x;
/*  36 */     this.y = paramVec2f.y;
/*     */   }
/*     */ 
/*     */   public void set(Vec2f paramVec2f)
/*     */   {
/*  47 */     this.x = paramVec2f.x;
/*  48 */     this.y = paramVec2f.y;
/*     */   }
/*     */ 
/*     */   public void set(float paramFloat1, float paramFloat2)
/*     */   {
/*  59 */     this.x = paramFloat1;
/*  60 */     this.y = paramFloat2;
/*     */   }
/*     */ 
/*     */   public static float distanceSq(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  74 */     paramFloat1 -= paramFloat3;
/*  75 */     paramFloat2 -= paramFloat4;
/*  76 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2;
/*     */   }
/*     */ 
/*     */   public static float distance(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  90 */     paramFloat1 -= paramFloat3;
/*  91 */     paramFloat2 -= paramFloat4;
/*  92 */     return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */   }
/*     */ 
/*     */   public float distanceSq(float paramFloat1, float paramFloat2)
/*     */   {
/* 107 */     paramFloat1 -= this.x;
/* 108 */     paramFloat2 -= this.y;
/* 109 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2;
/*     */   }
/*     */ 
/*     */   public float distanceSq(Vec2f paramVec2f)
/*     */   {
/* 122 */     float f1 = paramVec2f.x - this.x;
/* 123 */     float f2 = paramVec2f.y - this.y;
/* 124 */     return f1 * f1 + f2 * f2;
/*     */   }
/*     */ 
/*     */   public float distance(float paramFloat1, float paramFloat2)
/*     */   {
/* 139 */     paramFloat1 -= this.x;
/* 140 */     paramFloat2 -= this.y;
/* 141 */     return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*     */   }
/*     */ 
/*     */   public float distance(Vec2f paramVec2f)
/*     */   {
/* 154 */     float f1 = paramVec2f.x - this.x;
/* 155 */     float f2 = paramVec2f.y - this.y;
/* 156 */     return (float)Math.sqrt(f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 165 */     int i = 7;
/* 166 */     i = 31 * i + Float.floatToIntBits(this.x);
/* 167 */     i = 31 * i + Float.floatToIntBits(this.y);
/* 168 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 183 */     if (paramObject == this) return true;
/* 184 */     if ((paramObject instanceof Vec2f)) {
/* 185 */       Vec2f localVec2f = (Vec2f)paramObject;
/* 186 */       return (this.x == localVec2f.x) && (this.y == localVec2f.y);
/*     */     }
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     return "Vec2f[" + this.x + ", " + this.y + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec2f
 * JD-Core Version:    0.6.2
 */