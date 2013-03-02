/*     */ package com.sun.scenario.effect.light;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ 
/*     */ public class PointLight extends Light
/*     */ {
/*     */   private float x;
/*     */   private float y;
/*     */   private float z;
/*     */ 
/*     */   public PointLight()
/*     */   {
/*  44 */     this(0.0F, 0.0F, 0.0F, Color4f.WHITE);
/*     */   }
/*     */ 
/*     */   public PointLight(float paramFloat1, float paramFloat2, float paramFloat3, Color4f paramColor4f)
/*     */   {
/*  57 */     this(Light.Type.POINT, paramFloat1, paramFloat2, paramFloat3, paramColor4f);
/*     */   }
/*     */ 
/*     */   PointLight(Light.Type paramType, float paramFloat1, float paramFloat2, float paramFloat3, Color4f paramColor4f)
/*     */   {
/*  71 */     super(paramType, paramColor4f);
/*  72 */     this.x = paramFloat1;
/*  73 */     this.y = paramFloat2;
/*  74 */     this.z = paramFloat3;
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/*  83 */     return this.x;
/*     */   }
/*     */ 
/*     */   public void setX(float paramFloat)
/*     */   {
/*  98 */     float f = this.x;
/*  99 */     this.x = paramFloat;
/* 100 */     firePropertyChange("x", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/* 109 */     return this.y;
/*     */   }
/*     */ 
/*     */   public void setY(float paramFloat)
/*     */   {
/* 124 */     float f = this.y;
/* 125 */     this.y = paramFloat;
/* 126 */     firePropertyChange("y", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getZ()
/*     */   {
/* 135 */     return this.z;
/*     */   }
/*     */ 
/*     */   public void setZ(float paramFloat)
/*     */   {
/* 150 */     float f = this.z;
/* 151 */     this.z = paramFloat;
/* 152 */     firePropertyChange("z", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float[] getNormalizedLightPosition()
/*     */   {
/* 158 */     float f = (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/* 159 */     if (f == 0.0F) f = 1.0F;
/* 160 */     float[] arrayOfFloat = { this.x / f, this.y / f, this.z / f };
/* 161 */     return arrayOfFloat;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.light.PointLight
 * JD-Core Version:    0.6.2
 */