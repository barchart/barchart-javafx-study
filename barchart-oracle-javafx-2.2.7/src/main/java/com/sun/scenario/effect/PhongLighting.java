/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.light.Light;
/*     */ import com.sun.scenario.effect.light.Light.Type;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.util.List;
/*     */ 
/*     */ public class PhongLighting extends CoreEffect
/*     */ {
/*     */   private float surfaceScale;
/*     */   private float diffuseConstant;
/*     */   private float specularConstant;
/*     */   private float specularExponent;
/*     */   private Light light;
/*  48 */   private final PropertyChangeListener lightListener = new LightChangeListener(null);
/*     */ 
/*     */   public PhongLighting(Light paramLight)
/*     */   {
/*  61 */     this(paramLight, new GaussianShadow(10.0F), DefaultInput);
/*     */   }
/*     */ 
/*     */   public PhongLighting(Light paramLight, Effect paramEffect1, Effect paramEffect2)
/*     */   {
/*  75 */     super(paramEffect1, paramEffect2);
/*     */ 
/*  77 */     this.surfaceScale = 1.0F;
/*  78 */     this.diffuseConstant = 1.0F;
/*  79 */     this.specularConstant = 1.0F;
/*  80 */     this.specularExponent = 1.0F;
/*     */ 
/*  82 */     setLight(paramLight);
/*     */   }
/*     */ 
/*     */   public final Effect getBumpInput()
/*     */   {
/*  91 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setBumpInput(Effect paramEffect)
/*     */   {
/* 102 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getContentInput()
/*     */   {
/* 111 */     return (Effect)getInputs().get(1);
/*     */   }
/*     */ 
/*     */   private Effect getContentInput(Effect paramEffect) {
/* 115 */     return getDefaultedInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   public void setContentInput(Effect paramEffect)
/*     */   {
/* 126 */     setInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   public Light getLight()
/*     */   {
/* 135 */     return this.light;
/*     */   }
/*     */ 
/*     */   public void setLight(Light paramLight)
/*     */   {
/* 145 */     if (paramLight == null) {
/* 146 */       throw new IllegalArgumentException("Light must be non-null");
/*     */     }
/* 148 */     Light localLight = this.light;
/* 149 */     if (localLight != null) {
/* 150 */       localLight.removePropertyChangeListener(this.lightListener);
/*     */     }
/* 152 */     this.light = paramLight;
/* 153 */     this.light.addPropertyChangeListener(this.lightListener);
/* 154 */     updatePeerKey("PhongLighting_" + paramLight.getType().name());
/* 155 */     firePropertyChange("light", localLight, paramLight);
/*     */   }
/*     */ 
/*     */   public float getDiffuseConstant()
/*     */   {
/* 175 */     return this.diffuseConstant;
/*     */   }
/*     */ 
/*     */   public void setDiffuseConstant(float paramFloat)
/*     */   {
/* 192 */     if ((paramFloat < 0.0F) || (paramFloat > 2.0F)) {
/* 193 */       throw new IllegalArgumentException("Diffuse constant must be in the range [0,2]");
/*     */     }
/* 195 */     float f = this.diffuseConstant;
/* 196 */     this.diffuseConstant = paramFloat;
/* 197 */     firePropertyChange("diffuseConstant", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpecularConstant()
/*     */   {
/* 206 */     return this.specularConstant;
/*     */   }
/*     */ 
/*     */   public void setSpecularConstant(float paramFloat)
/*     */   {
/* 223 */     if ((paramFloat < 0.0F) || (paramFloat > 2.0F)) {
/* 224 */       throw new IllegalArgumentException("Specular constant must be in the range [0,2]");
/*     */     }
/* 226 */     float f = this.specularConstant;
/* 227 */     this.specularConstant = paramFloat;
/* 228 */     firePropertyChange("specularConstant", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpecularExponent()
/*     */   {
/* 237 */     return this.specularExponent;
/*     */   }
/*     */ 
/*     */   public void setSpecularExponent(float paramFloat)
/*     */   {
/* 254 */     if ((paramFloat < 0.0F) || (paramFloat > 40.0F)) {
/* 255 */       throw new IllegalArgumentException("Specular exponent must be in the range [0,40]");
/*     */     }
/* 257 */     float f = this.specularExponent;
/* 258 */     this.specularExponent = paramFloat;
/* 259 */     firePropertyChange("specularExponent", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSurfaceScale()
/*     */   {
/* 268 */     return this.surfaceScale;
/*     */   }
/*     */ 
/*     */   public void setSurfaceScale(float paramFloat)
/*     */   {
/* 285 */     if ((paramFloat < 0.0F) || (paramFloat > 10.0F)) {
/* 286 */       throw new IllegalArgumentException("Surface scale must be in the range [0,10]");
/*     */     }
/* 288 */     float f = this.surfaceScale;
/* 289 */     this.surfaceScale = paramFloat;
/* 290 */     firePropertyChange("surfaceScale", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 298 */     return getContentInput(paramEffect).getBounds(paramBaseTransform, paramEffect);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 307 */     return super.getResultBounds(paramBaseTransform, paramRectangle, new ImageData[] { paramArrayOfImageData[1] });
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 312 */     return getContentInput(paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 317 */     return getContentInput(paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 328 */     return paramRectangle;
/*     */   }
/*     */ 
/*     */   private class LightChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private LightChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*     */     {
/* 165 */       PhongLighting.this.firePropertyChange("light", null, PhongLighting.this.light);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.PhongLighting
 * JD-Core Version:    0.6.2
 */