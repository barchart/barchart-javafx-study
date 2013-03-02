/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Vec3f
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */ 
/*     */   public Vec3f()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vec3f(float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  35 */     this.x = paramFloat1;
/*  36 */     this.y = paramFloat2;
/*  37 */     this.z = paramFloat3;
/*     */   }
/*     */ 
/*     */   public Vec3f(Vec3f paramVec3f) {
/*  41 */     this.x = paramVec3f.x;
/*  42 */     this.y = paramVec3f.y;
/*  43 */     this.z = paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public void set(Vec3f paramVec3f) {
/*  47 */     this.x = paramVec3f.x;
/*  48 */     this.y = paramVec3f.y;
/*  49 */     this.z = paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public void set(float paramFloat1, float paramFloat2, float paramFloat3) {
/*  53 */     this.x = paramFloat1;
/*  54 */     this.y = paramFloat2;
/*  55 */     this.z = paramFloat3;
/*     */   }
/*     */ 
/*     */   public void sub(Vec3f paramVec3f1, Vec3f paramVec3f2)
/*     */   {
/*  65 */     paramVec3f1.x -= paramVec3f2.x;
/*  66 */     paramVec3f1.y -= paramVec3f2.y;
/*  67 */     paramVec3f1.z -= paramVec3f2.z;
/*     */   }
/*     */ 
/*     */   public void sub(Vec3f paramVec3f)
/*     */   {
/*  76 */     this.x -= paramVec3f.x;
/*  77 */     this.y -= paramVec3f.y;
/*  78 */     this.z -= paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public void add(Vec3f paramVec3f1, Vec3f paramVec3f2)
/*     */   {
/*  88 */     paramVec3f1.x += paramVec3f2.x;
/*  89 */     paramVec3f1.y += paramVec3f2.y;
/*  90 */     paramVec3f1.z += paramVec3f2.z;
/*     */   }
/*     */ 
/*     */   public void add(Vec3f paramVec3f)
/*     */   {
/*  99 */     this.x += paramVec3f.x;
/* 100 */     this.y += paramVec3f.y;
/* 101 */     this.z += paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public float length()
/*     */   {
/* 109 */     return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 116 */     float f = 1.0F / length();
/* 117 */     this.x *= f;
/* 118 */     this.y *= f;
/* 119 */     this.z *= f;
/*     */   }
/*     */ 
/*     */   public void cross(Vec3f paramVec3f1, Vec3f paramVec3f2)
/*     */   {
/* 131 */     float f1 = paramVec3f1.y * paramVec3f2.z - paramVec3f1.z * paramVec3f2.y;
/* 132 */     float f2 = paramVec3f2.x * paramVec3f1.z - paramVec3f2.z * paramVec3f1.x;
/* 133 */     this.z = (paramVec3f1.x * paramVec3f2.y - paramVec3f1.y * paramVec3f2.x);
/* 134 */     this.x = f1;
/* 135 */     this.y = f2;
/*     */   }
/*     */ 
/*     */   public float dot(Vec3f paramVec3f)
/*     */   {
/* 144 */     return this.x * paramVec3f.x + this.y * paramVec3f.y + this.z * paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 153 */     int i = 7;
/* 154 */     i = 31 * i + Float.floatToIntBits(this.x);
/* 155 */     i = 31 * i + Float.floatToIntBits(this.y);
/* 156 */     i = 31 * i + Float.floatToIntBits(this.z);
/* 157 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 172 */     if (paramObject == this) {
/* 173 */       return true;
/*     */     }
/* 175 */     if ((paramObject instanceof Vec3f)) {
/* 176 */       Vec3f localVec3f = (Vec3f)paramObject;
/* 177 */       return (this.x == localVec3f.x) && (this.y == localVec3f.y) && (this.z == localVec3f.z);
/*     */     }
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 189 */     return "Vec3f[" + this.x + ", " + this.y + ", " + this.z + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec3f
 * JD-Core Version:    0.6.2
 */