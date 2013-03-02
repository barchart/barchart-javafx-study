/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class Vec3d
/*     */ {
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */ 
/*     */   public Vec3d()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Vec3d(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  35 */     this.x = paramDouble1;
/*  36 */     this.y = paramDouble2;
/*  37 */     this.z = paramDouble3;
/*     */   }
/*     */ 
/*     */   public Vec3d(Vec3d paramVec3d) {
/*  41 */     set(paramVec3d);
/*     */   }
/*     */ 
/*     */   public Vec3d(Vec3f paramVec3f) {
/*  45 */     set(paramVec3f);
/*     */   }
/*     */ 
/*     */   public void set(Vec3f paramVec3f) {
/*  49 */     this.x = paramVec3f.x;
/*  50 */     this.y = paramVec3f.y;
/*  51 */     this.z = paramVec3f.z;
/*     */   }
/*     */ 
/*     */   public void set(Vec3d paramVec3d) {
/*  55 */     this.x = paramVec3d.x;
/*  56 */     this.y = paramVec3d.y;
/*  57 */     this.z = paramVec3d.z;
/*     */   }
/*     */ 
/*     */   public void set(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  61 */     this.x = paramDouble1;
/*  62 */     this.y = paramDouble2;
/*  63 */     this.z = paramDouble3;
/*     */   }
/*     */ 
/*     */   public void mul(double paramDouble)
/*     */   {
/*  71 */     this.x *= paramDouble;
/*  72 */     this.y *= paramDouble;
/*  73 */     this.z *= paramDouble;
/*     */   }
/*     */ 
/*     */   public void sub(Vec3f paramVec3f1, Vec3f paramVec3f2)
/*     */   {
/*  83 */     this.x = (paramVec3f1.x - paramVec3f2.x);
/*  84 */     this.y = (paramVec3f1.y - paramVec3f2.y);
/*  85 */     this.z = (paramVec3f1.z - paramVec3f2.z);
/*     */   }
/*     */ 
/*     */   public void sub(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/*  95 */     paramVec3d1.x -= paramVec3d2.x;
/*  96 */     paramVec3d1.y -= paramVec3d2.y;
/*  97 */     paramVec3d1.z -= paramVec3d2.z;
/*     */   }
/*     */ 
/*     */   public void sub(Vec3d paramVec3d)
/*     */   {
/* 106 */     this.x -= paramVec3d.x;
/* 107 */     this.y -= paramVec3d.y;
/* 108 */     this.z -= paramVec3d.z;
/*     */   }
/*     */ 
/*     */   public void add(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/* 118 */     paramVec3d1.x += paramVec3d2.x;
/* 119 */     paramVec3d1.y += paramVec3d2.y;
/* 120 */     paramVec3d1.z += paramVec3d2.z;
/*     */   }
/*     */ 
/*     */   public void add(Vec3d paramVec3d)
/*     */   {
/* 129 */     this.x += paramVec3d.x;
/* 130 */     this.y += paramVec3d.y;
/* 131 */     this.z += paramVec3d.z;
/*     */   }
/*     */ 
/*     */   public float length()
/*     */   {
/* 139 */     return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 146 */     double d = 1.0D / length();
/* 147 */     this.x *= d;
/* 148 */     this.y *= d;
/* 149 */     this.z *= d;
/*     */   }
/*     */ 
/*     */   public void cross(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/* 161 */     double d1 = paramVec3d1.y * paramVec3d2.z - paramVec3d1.z * paramVec3d2.y;
/* 162 */     double d2 = paramVec3d2.x * paramVec3d1.z - paramVec3d2.z * paramVec3d1.x;
/* 163 */     this.z = (paramVec3d1.x * paramVec3d2.y - paramVec3d1.y * paramVec3d2.x);
/* 164 */     this.x = d1;
/* 165 */     this.y = d2;
/*     */   }
/*     */ 
/*     */   public double dot(Vec3d paramVec3d)
/*     */   {
/* 174 */     return this.x * paramVec3d.x + this.y * paramVec3d.y + this.z * paramVec3d.z;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 183 */     long l = 7L;
/* 184 */     l = 31L * l + Double.doubleToLongBits(this.x);
/* 185 */     l = 31L * l + Double.doubleToLongBits(this.y);
/* 186 */     l = 31L * l + Double.doubleToLongBits(this.z);
/* 187 */     return (int)(l ^ l >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 202 */     if (paramObject == this) {
/* 203 */       return true;
/*     */     }
/* 205 */     if ((paramObject instanceof Vec3d)) {
/* 206 */       Vec3d localVec3d = (Vec3d)paramObject;
/* 207 */       return (this.x == localVec3d.x) && (this.y == localVec3d.y) && (this.z == localVec3d.z);
/*     */     }
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 219 */     return "Vec3d[" + this.x + ", " + this.y + ", " + this.z + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec3d
 * JD-Core Version:    0.6.2
 */