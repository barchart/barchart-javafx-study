/*     */ package com.sun.scenario.effect.light;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ 
/*     */ public class SpotLight extends PointLight
/*     */ {
/*     */   private float pointsAtX;
/*     */   private float pointsAtY;
/*     */   private float pointsAtZ;
/*     */   private float specularExponent;
/*     */ 
/*     */   public SpotLight()
/*     */   {
/*  47 */     this(0.0F, 0.0F, 0.0F, Color4f.WHITE);
/*     */   }
/*     */ 
/*     */   public SpotLight(float paramFloat1, float paramFloat2, float paramFloat3, Color4f paramColor4f)
/*     */   {
/*  62 */     super(Light.Type.SPOT, paramFloat1, paramFloat2, paramFloat3, paramColor4f);
/*  63 */     this.pointsAtX = 0.0F;
/*  64 */     this.pointsAtY = 0.0F;
/*  65 */     this.pointsAtZ = 0.0F;
/*  66 */     this.specularExponent = 1.0F;
/*     */   }
/*     */ 
/*     */   public float getPointsAtX()
/*     */   {
/*  75 */     return this.pointsAtX;
/*     */   }
/*     */ 
/*     */   public void setPointsAtX(float paramFloat)
/*     */   {
/*  90 */     float f = this.pointsAtX;
/*  91 */     this.pointsAtX = paramFloat;
/*  92 */     firePropertyChange("pointsAtX", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getPointsAtY()
/*     */   {
/* 101 */     return this.pointsAtY;
/*     */   }
/*     */ 
/*     */   public void setPointsAtY(float paramFloat)
/*     */   {
/* 116 */     float f = this.pointsAtY;
/* 117 */     this.pointsAtY = paramFloat;
/* 118 */     firePropertyChange("pointsAtY", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getPointsAtZ()
/*     */   {
/* 127 */     return this.pointsAtZ;
/*     */   }
/*     */ 
/*     */   public void setPointsAtZ(float paramFloat)
/*     */   {
/* 142 */     float f = this.pointsAtZ;
/* 143 */     this.pointsAtZ = paramFloat;
/* 144 */     firePropertyChange("pointsAtZ", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpecularExponent()
/*     */   {
/* 154 */     return this.specularExponent;
/*     */   }
/*     */ 
/*     */   public void setSpecularExponent(float paramFloat)
/*     */   {
/* 170 */     if ((paramFloat < 0.0F) || (paramFloat > 4.0F)) {
/* 171 */       throw new IllegalArgumentException("Specular exponent must be in the range [0,4]");
/*     */     }
/* 173 */     float f = this.specularExponent;
/* 174 */     this.specularExponent = paramFloat;
/* 175 */     firePropertyChange("specularExponent", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float[] getNormalizedLightPosition()
/*     */   {
/* 181 */     float f1 = getX();
/* 182 */     float f2 = getY();
/* 183 */     float f3 = getZ();
/* 184 */     float f4 = (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
/* 185 */     if (f4 == 0.0F) f4 = 1.0F;
/* 186 */     float[] arrayOfFloat = { f1 / f4, f2 / f4, f3 / f4 };
/* 187 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   public float[] getNormalizedLightDirection()
/*     */   {
/* 197 */     float f1 = this.pointsAtX - getX();
/* 198 */     float f2 = this.pointsAtY - getY();
/* 199 */     float f3 = this.pointsAtZ - getZ();
/*     */ 
/* 201 */     float f4 = (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
/* 202 */     if (f4 == 0.0F) f4 = 1.0F;
/* 203 */     float[] arrayOfFloat = { f1 / f4, f2 / f4, f3 / f4 };
/* 204 */     return arrayOfFloat;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.light.SpotLight
 * JD-Core Version:    0.6.2
 */