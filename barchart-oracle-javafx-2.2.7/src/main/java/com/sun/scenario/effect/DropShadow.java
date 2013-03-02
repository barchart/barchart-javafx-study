/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ public class DropShadow extends DelegateEffect
/*     */ {
/*     */   private AbstractShadow shadow;
/*     */   private final Offset offset;
/*     */   private final Merge merge;
/*     */ 
/*     */   public DropShadow()
/*     */   {
/*  48 */     this(DefaultInput, DefaultInput);
/*     */   }
/*     */ 
/*     */   public DropShadow(Effect paramEffect)
/*     */   {
/*  62 */     this(paramEffect, paramEffect);
/*     */   }
/*     */ 
/*     */   public DropShadow(Effect paramEffect1, Effect paramEffect2)
/*     */   {
/*  82 */     super(paramEffect1, paramEffect2);
/*     */ 
/*  93 */     this.shadow = new GaussianShadow(10.0F, Color4f.BLACK, paramEffect1);
/*  94 */     this.offset = new Offset(0, 0, this.shadow);
/*  95 */     this.merge = new Merge(this.offset, paramEffect2);
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode getShadowMode() {
/*  99 */     return this.shadow.getMode();
/*     */   }
/*     */ 
/*     */   public void setShadowMode(AbstractShadow.ShadowMode paramShadowMode) {
/* 103 */     AbstractShadow.ShadowMode localShadowMode = this.shadow.getMode();
/* 104 */     AbstractShadow localAbstractShadow = this.shadow.implFor(paramShadowMode);
/* 105 */     if (localAbstractShadow != this.shadow) {
/* 106 */       this.offset.setInput(localAbstractShadow);
/*     */     }
/* 108 */     this.shadow = localAbstractShadow;
/* 109 */     firePropertyChange("shadowmode", localShadowMode, paramShadowMode);
/*     */   }
/*     */ 
/*     */   protected Effect getDelegate() {
/* 113 */     return this.merge;
/*     */   }
/*     */ 
/*     */   public final Effect getShadowSourceInput()
/*     */   {
/* 122 */     return this.shadow.getInput();
/*     */   }
/*     */ 
/*     */   public void setShadowSourceInput(Effect paramEffect)
/*     */   {
/* 132 */     this.shadow.setInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getContentInput()
/*     */   {
/* 141 */     return this.merge.getTopInput();
/*     */   }
/*     */ 
/*     */   public void setContentInput(Effect paramEffect)
/*     */   {
/* 151 */     this.merge.setTopInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 160 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 177 */     float f = this.shadow.getGaussianRadius();
/* 178 */     this.shadow.setGaussianRadius(paramFloat);
/* 179 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getGaussianRadius() {
/* 183 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public float getGaussianWidth() {
/* 187 */     return this.shadow.getGaussianWidth();
/*     */   }
/*     */ 
/*     */   public float getGaussianHeight() {
/* 191 */     return this.shadow.getGaussianHeight();
/*     */   }
/*     */ 
/*     */   public void setGaussianRadius(float paramFloat) {
/* 195 */     setRadius(paramFloat);
/*     */   }
/*     */ 
/*     */   public void setGaussianWidth(float paramFloat) {
/* 199 */     float f = this.shadow.getGaussianWidth();
/* 200 */     this.shadow.setGaussianWidth(paramFloat);
/* 201 */     firePropertyChange("width", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public void setGaussianHeight(float paramFloat) {
/* 205 */     float f = this.shadow.getGaussianHeight();
/* 206 */     this.shadow.setGaussianHeight(paramFloat);
/* 207 */     firePropertyChange("height", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/* 216 */     return this.shadow.getSpread();
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat)
/*     */   {
/* 242 */     float f = this.shadow.getSpread();
/* 243 */     this.shadow.setSpread(paramFloat);
/* 244 */     firePropertyChange("spread", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/* 253 */     return this.shadow.getColor();
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 269 */     Color4f localColor4f = this.shadow.getColor();
/* 270 */     this.shadow.setColor(paramColor4f);
/* 271 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ 
/*     */   public int getOffsetX()
/*     */   {
/* 280 */     return this.offset.getX();
/*     */   }
/*     */ 
/*     */   public void setOffsetX(int paramInt)
/*     */   {
/* 295 */     int i = this.offset.getX();
/* 296 */     this.offset.setX(paramInt);
/* 297 */     firePropertyChange("offsetX", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getOffsetY()
/*     */   {
/* 306 */     return this.offset.getY();
/*     */   }
/*     */ 
/*     */   public void setOffsetY(int paramInt)
/*     */   {
/* 321 */     int i = this.offset.getY();
/* 322 */     this.offset.setY(paramInt);
/* 323 */     firePropertyChange("offsetY", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 328 */     return this.shadow.getAccelType(paramFilterContext);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.DropShadow
 * JD-Core Version:    0.6.2
 */