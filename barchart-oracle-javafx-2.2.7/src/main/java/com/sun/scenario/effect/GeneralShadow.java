/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ public class GeneralShadow extends DelegateEffect
/*     */ {
/*     */   private AbstractShadow shadow;
/*     */ 
/*     */   public GeneralShadow()
/*     */   {
/*  48 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public GeneralShadow(Effect paramEffect)
/*     */   {
/*  61 */     super(paramEffect);
/*  62 */     this.shadow = new GaussianShadow(10.0F, Color4f.BLACK, paramEffect);
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode getShadowMode() {
/*  66 */     return this.shadow.getMode();
/*     */   }
/*     */ 
/*     */   public void setShadowMode(AbstractShadow.ShadowMode paramShadowMode) {
/*  70 */     AbstractShadow.ShadowMode localShadowMode = this.shadow.getMode();
/*  71 */     this.shadow = this.shadow.implFor(paramShadowMode);
/*  72 */     firePropertyChange("shadowmode", localShadowMode, paramShadowMode);
/*     */   }
/*     */ 
/*     */   protected Effect getDelegate() {
/*  76 */     return this.shadow;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  85 */     return this.shadow.getInput();
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  95 */     this.shadow.setInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 104 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 121 */     float f = this.shadow.getGaussianRadius();
/* 122 */     this.shadow.setGaussianRadius(paramFloat);
/* 123 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getGaussianRadius() {
/* 127 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public float getGaussianWidth() {
/* 131 */     return this.shadow.getGaussianWidth();
/*     */   }
/*     */ 
/*     */   public float getGaussianHeight() {
/* 135 */     return this.shadow.getGaussianHeight();
/*     */   }
/*     */ 
/*     */   public void setGaussianRadius(float paramFloat) {
/* 139 */     setRadius(paramFloat);
/*     */   }
/*     */ 
/*     */   public void setGaussianWidth(float paramFloat) {
/* 143 */     float f = this.shadow.getGaussianWidth();
/* 144 */     this.shadow.setGaussianWidth(paramFloat);
/* 145 */     firePropertyChange("width", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public void setGaussianHeight(float paramFloat) {
/* 149 */     float f = this.shadow.getGaussianHeight();
/* 150 */     this.shadow.setGaussianHeight(paramFloat);
/* 151 */     firePropertyChange("height", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/* 160 */     return this.shadow.getSpread();
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat)
/*     */   {
/* 186 */     float f = this.shadow.getSpread();
/* 187 */     this.shadow.setSpread(paramFloat);
/* 188 */     firePropertyChange("spread", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/* 197 */     return this.shadow.getColor();
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 213 */     Color4f localColor4f = this.shadow.getColor();
/* 214 */     this.shadow.setColor(paramColor4f);
/* 215 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.GeneralShadow
 * JD-Core Version:    0.6.2
 */