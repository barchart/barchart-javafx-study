/*     */ package com.sun.scenario.effect.light;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ 
/*     */ public class DistantLight extends Light
/*     */ {
/*     */   private float azimuth;
/*     */   private float elevation;
/*     */ 
/*     */   public DistantLight()
/*     */   {
/*  43 */     this(0.0F, 0.0F, Color4f.WHITE);
/*     */   }
/*     */ 
/*     */   public DistantLight(float paramFloat1, float paramFloat2, Color4f paramColor4f)
/*     */   {
/*  56 */     super(Light.Type.DISTANT, paramColor4f);
/*  57 */     this.azimuth = paramFloat1;
/*  58 */     this.elevation = paramFloat2;
/*     */   }
/*     */ 
/*     */   public float getAzimuth()
/*     */   {
/*  68 */     return this.azimuth;
/*     */   }
/*     */ 
/*     */   public void setAzimuth(float paramFloat)
/*     */   {
/*  84 */     float f = this.azimuth;
/*  85 */     this.azimuth = paramFloat;
/*  86 */     firePropertyChange("azimuth", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getElevation()
/*     */   {
/*  96 */     return this.elevation;
/*     */   }
/*     */ 
/*     */   public void setElevation(float paramFloat)
/*     */   {
/* 112 */     float f = this.elevation;
/* 113 */     this.elevation = paramFloat;
/* 114 */     firePropertyChange("elevation", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float[] getNormalizedLightPosition()
/*     */   {
/* 119 */     double d1 = Math.toRadians(this.azimuth);
/* 120 */     double d2 = Math.toRadians(this.elevation);
/* 121 */     float f1 = (float)(Math.cos(d1) * Math.cos(d2));
/* 122 */     float f2 = (float)(Math.sin(d1) * Math.cos(d2));
/* 123 */     float f3 = (float)Math.sin(d2);
/*     */ 
/* 125 */     float f4 = (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
/* 126 */     if (f4 == 0.0F) f4 = 1.0F;
/* 127 */     float[] arrayOfFloat = { f1 / f4, f2 / f4, f3 / f4 };
/* 128 */     return arrayOfFloat;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.light.DistantLight
 * JD-Core Version:    0.6.2
 */