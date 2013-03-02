/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class InnerShadow extends DelegateEffect
/*     */ {
/*     */   private final InvertMask invert;
/*     */   private AbstractShadow shadow;
/*     */   private final Blend blend;
/*     */ 
/*     */   public InnerShadow()
/*     */   {
/*  52 */     this(DefaultInput, DefaultInput);
/*     */   }
/*     */ 
/*     */   public InnerShadow(Effect paramEffect)
/*     */   {
/*  66 */     this(paramEffect, paramEffect);
/*     */   }
/*     */ 
/*     */   public InnerShadow(Effect paramEffect1, Effect paramEffect2)
/*     */   {
/*  86 */     super(paramEffect1, paramEffect2);
/*     */ 
/*  97 */     this.invert = new InvertMask(10, paramEffect1);
/*  98 */     this.shadow = new GaussianShadow(10.0F, Color4f.BLACK, this.invert);
/*  99 */     this.blend = new Blend(Blend.Mode.SRC_ATOP, paramEffect2, this.shadow);
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode getShadowMode() {
/* 103 */     return this.shadow.getMode();
/*     */   }
/*     */ 
/*     */   public void setShadowMode(AbstractShadow.ShadowMode paramShadowMode) {
/* 107 */     AbstractShadow.ShadowMode localShadowMode = this.shadow.getMode();
/* 108 */     AbstractShadow localAbstractShadow = this.shadow.implFor(paramShadowMode);
/* 109 */     if (localAbstractShadow != this.shadow) {
/* 110 */       this.blend.setTopInput(localAbstractShadow);
/*     */     }
/* 112 */     this.shadow = localAbstractShadow;
/* 113 */     firePropertyChange("shadowmode", localShadowMode, paramShadowMode);
/*     */   }
/*     */ 
/*     */   protected Effect getDelegate() {
/* 117 */     return this.blend;
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 124 */     Effect localEffect = getDefaultedInput(getContentInput(), paramEffect);
/* 125 */     return localEffect.getBounds(paramBaseTransform, paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getShadowSourceInput()
/*     */   {
/* 134 */     return this.invert.getInput();
/*     */   }
/*     */ 
/*     */   public void setShadowSourceInput(Effect paramEffect)
/*     */   {
/* 145 */     this.invert.setInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getContentInput()
/*     */   {
/* 154 */     return this.blend.getBottomInput();
/*     */   }
/*     */ 
/*     */   public void setContentInput(Effect paramEffect)
/*     */   {
/* 165 */     this.blend.setBottomInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 174 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 191 */     float f = this.shadow.getGaussianRadius();
/* 192 */     this.invert.setPad((int)Math.ceil(paramFloat));
/* 193 */     this.shadow.setGaussianRadius(paramFloat);
/* 194 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getGaussianRadius() {
/* 198 */     return this.shadow.getGaussianRadius();
/*     */   }
/*     */ 
/*     */   public float getGaussianWidth() {
/* 202 */     return this.shadow.getGaussianWidth();
/*     */   }
/*     */ 
/*     */   public float getGaussianHeight() {
/* 206 */     return this.shadow.getGaussianHeight();
/*     */   }
/*     */ 
/*     */   public void setGaussianRadius(float paramFloat) {
/* 210 */     setRadius(paramFloat);
/*     */   }
/*     */ 
/*     */   public void setGaussianWidth(float paramFloat) {
/* 214 */     float f1 = this.shadow.getGaussianWidth();
/* 215 */     float f2 = (Math.max(paramFloat, this.shadow.getGaussianHeight()) - 1.0F) / 2.0F;
/* 216 */     this.invert.setPad((int)Math.ceil(f2));
/* 217 */     this.shadow.setGaussianWidth(paramFloat);
/* 218 */     firePropertyChange("width", Float.valueOf(f1), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public void setGaussianHeight(float paramFloat) {
/* 222 */     float f1 = this.shadow.getGaussianHeight();
/* 223 */     float f2 = (Math.max(this.shadow.getGaussianWidth(), paramFloat) - 1.0F) / 2.0F;
/* 224 */     this.invert.setPad((int)Math.ceil(f2));
/* 225 */     this.shadow.setGaussianHeight(paramFloat);
/* 226 */     firePropertyChange("height", Float.valueOf(f1), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getChoke()
/*     */   {
/* 235 */     return this.shadow.getSpread();
/*     */   }
/*     */ 
/*     */   public void setChoke(float paramFloat)
/*     */   {
/* 261 */     float f = this.shadow.getSpread();
/* 262 */     this.shadow.setSpread(paramFloat);
/* 263 */     firePropertyChange("choke", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/* 272 */     return this.shadow.getColor();
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 288 */     Color4f localColor4f = this.shadow.getColor();
/* 289 */     this.shadow.setColor(paramColor4f);
/* 290 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ 
/*     */   public int getOffsetX()
/*     */   {
/* 299 */     return this.invert.getOffsetX();
/*     */   }
/*     */ 
/*     */   public void setOffsetX(int paramInt)
/*     */   {
/* 314 */     int i = this.invert.getOffsetX();
/* 315 */     this.invert.setOffsetX(paramInt);
/* 316 */     firePropertyChange("offsetX", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getOffsetY()
/*     */   {
/* 325 */     return this.invert.getOffsetY();
/*     */   }
/*     */ 
/*     */   public void setOffsetY(int paramInt)
/*     */   {
/* 340 */     int i = this.invert.getOffsetY();
/* 341 */     this.invert.setOffsetY(paramInt);
/* 342 */     firePropertyChange("offsetY", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 347 */     return getDefaultedInput(1, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 352 */     return getDefaultedInput(1, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.InnerShadow
 * JD-Core Version:    0.6.2
 */