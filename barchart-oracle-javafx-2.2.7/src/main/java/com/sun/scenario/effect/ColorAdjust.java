/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ColorAdjust extends CoreEffect
/*     */ {
/*     */   private float hue;
/*     */   private float saturation;
/*     */   private float brightness;
/*     */   private float contrast;
/*     */ 
/*     */   public ColorAdjust()
/*     */   {
/*  52 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public ColorAdjust(Effect paramEffect)
/*     */   {
/*  62 */     super(paramEffect);
/*  63 */     this.hue = 0.0F;
/*  64 */     this.saturation = 0.0F;
/*  65 */     this.brightness = 0.0F;
/*  66 */     this.contrast = 0.0F;
/*  67 */     updatePeerKey("ColorAdjust");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  76 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  88 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getHue()
/*     */   {
/*  97 */     return this.hue;
/*     */   }
/*     */ 
/*     */   public void setHue(float paramFloat)
/*     */   {
/* 114 */     if ((paramFloat < -1.0F) || (paramFloat > 1.0F)) {
/* 115 */       throw new IllegalArgumentException("Hue must be in the range [-1, 1]");
/*     */     }
/* 117 */     float f = this.hue;
/* 118 */     this.hue = paramFloat;
/* 119 */     firePropertyChange("hue", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSaturation()
/*     */   {
/* 128 */     return this.saturation;
/*     */   }
/*     */ 
/*     */   public void setSaturation(float paramFloat)
/*     */   {
/* 145 */     if ((paramFloat < -1.0F) || (paramFloat > 1.0F)) {
/* 146 */       throw new IllegalArgumentException("Saturation must be in the range [-1, 1]");
/*     */     }
/* 148 */     float f = this.saturation;
/* 149 */     this.saturation = paramFloat;
/* 150 */     firePropertyChange("saturation", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getBrightness()
/*     */   {
/* 159 */     return this.brightness;
/*     */   }
/*     */ 
/*     */   public void setBrightness(float paramFloat)
/*     */   {
/* 176 */     if ((paramFloat < -1.0F) || (paramFloat > 1.0F)) {
/* 177 */       throw new IllegalArgumentException("Brightness must be in the range [-1, 1]");
/*     */     }
/* 179 */     float f = this.brightness;
/* 180 */     this.brightness = paramFloat;
/* 181 */     firePropertyChange("brightness", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getContrast()
/*     */   {
/* 190 */     return this.contrast;
/*     */   }
/*     */ 
/*     */   public void setContrast(float paramFloat)
/*     */   {
/* 207 */     if ((paramFloat < -1.0F) || (paramFloat > 1.0F)) {
/* 208 */       throw new IllegalArgumentException("Contrast must be in the range [-1, 1]");
/*     */     }
/* 210 */     float f = this.contrast;
/* 211 */     this.contrast = paramFloat;
/* 212 */     firePropertyChange("contrast", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 223 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.ColorAdjust
 * JD-Core Version:    0.6.2
 */