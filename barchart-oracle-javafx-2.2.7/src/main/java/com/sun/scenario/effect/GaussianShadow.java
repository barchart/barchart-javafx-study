/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.GaussianShadowState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GaussianShadow extends AbstractShadow
/*     */ {
/*  44 */   private GaussianShadowState state = new GaussianShadowState();
/*     */ 
/*     */   public GaussianShadow()
/*     */   {
/*  56 */     this(10.0F);
/*     */   }
/*     */ 
/*     */   public GaussianShadow(float paramFloat)
/*     */   {
/*  73 */     this(paramFloat, Color4f.BLACK);
/*     */   }
/*     */ 
/*     */   public GaussianShadow(float paramFloat, Color4f paramColor4f)
/*     */   {
/*  90 */     this(paramFloat, paramColor4f, DefaultInput);
/*     */   }
/*     */ 
/*     */   public GaussianShadow(float paramFloat, Color4f paramColor4f, Effect paramEffect)
/*     */   {
/* 104 */     super(paramEffect);
/* 105 */     this.state.setRadius(paramFloat);
/* 106 */     this.state.setShadowColor(paramColor4f);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/* 112 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 117 */     return Renderer.getRenderer(paramFilterContext).getAccelType();
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 126 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 136 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 145 */     return this.state.getRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 162 */     float f = this.state.getRadius();
/* 163 */     this.state.setRadius(paramFloat);
/* 164 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getHRadius()
/*     */   {
/* 173 */     return this.state.getHRadius();
/*     */   }
/*     */ 
/*     */   public void setHRadius(float paramFloat)
/*     */   {
/* 190 */     float f = this.state.getHRadius();
/* 191 */     this.state.setHRadius(paramFloat);
/* 192 */     firePropertyChange("hradius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getVRadius()
/*     */   {
/* 201 */     return this.state.getVRadius();
/*     */   }
/*     */ 
/*     */   public void setVRadius(float paramFloat)
/*     */   {
/* 218 */     float f = this.state.getVRadius();
/* 219 */     this.state.setVRadius(paramFloat);
/* 220 */     firePropertyChange("vradius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/* 229 */     return this.state.getSpread();
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat)
/*     */   {
/* 255 */     float f = this.state.getSpread();
/* 256 */     this.state.setSpread(paramFloat);
/* 257 */     firePropertyChange("spread", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/* 266 */     return this.state.getShadowColor();
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 282 */     Color4f localColor4f = this.state.getShadowColor();
/* 283 */     this.state.setShadowColor(paramColor4f);
/* 284 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ 
/*     */   public float getGaussianRadius() {
/* 288 */     return getRadius();
/*     */   }
/*     */ 
/*     */   public float getGaussianWidth() {
/* 292 */     return getHRadius() * 2.0F + 1.0F;
/*     */   }
/*     */ 
/*     */   public float getGaussianHeight() {
/* 296 */     return getVRadius() * 2.0F + 1.0F;
/*     */   }
/*     */ 
/*     */   public void setGaussianRadius(float paramFloat) {
/* 300 */     setRadius(paramFloat);
/*     */   }
/*     */ 
/*     */   public void setGaussianWidth(float paramFloat) {
/* 304 */     setHRadius(paramFloat < 1.0F ? 0.0F : (paramFloat - 1.0F) / 2.0F);
/*     */   }
/*     */ 
/*     */   public void setGaussianHeight(float paramFloat) {
/* 308 */     setVRadius(paramFloat < 1.0F ? 0.0F : (paramFloat - 1.0F) / 2.0F);
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode getMode() {
/* 312 */     return AbstractShadow.ShadowMode.GAUSSIAN;
/*     */   }
/*     */ 
/*     */   public AbstractShadow implFor(AbstractShadow.ShadowMode paramShadowMode) {
/* 316 */     int i = 0;
/* 317 */     switch (1.$SwitchMap$com$sun$scenario$effect$AbstractShadow$ShadowMode[paramShadowMode.ordinal()]) {
/*     */     case 1:
/* 319 */       return this;
/*     */     case 2:
/* 321 */       i = 1;
/* 322 */       break;
/*     */     case 3:
/* 324 */       i = 2;
/* 325 */       break;
/*     */     case 4:
/* 327 */       i = 3;
/*     */     }
/*     */ 
/* 330 */     BoxShadow localBoxShadow = new BoxShadow();
/* 331 */     localBoxShadow.setInput(getInput());
/* 332 */     localBoxShadow.setGaussianWidth(getGaussianWidth());
/* 333 */     localBoxShadow.setGaussianHeight(getGaussianHeight());
/* 334 */     localBoxShadow.setColor(getColor());
/* 335 */     localBoxShadow.setPasses(i);
/* 336 */     localBoxShadow.setSpread(getSpread());
/* 337 */     return localBoxShadow;
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 342 */     BaseBounds localBaseBounds = super.getBounds(null, paramEffect);
/* 343 */     int i = this.state.getPad(0);
/* 344 */     int j = this.state.getPad(1);
/* 345 */     RectBounds localRectBounds = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/* 346 */     localRectBounds.grow(i, j);
/* 347 */     return transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 355 */     Rectangle localRectangle1 = super.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 356 */     int i = this.state.getPad(0);
/* 357 */     int j = this.state.getPad(1);
/* 358 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 359 */     localRectangle2.grow(i, j);
/* 360 */     return localRectangle2;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 369 */     return this.state.filterImageDatas(this, paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 374 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 385 */     if (paramRectangle != null) {
/* 386 */       int i = this.state.getPad(0);
/* 387 */       int j = this.state.getPad(1);
/* 388 */       if ((i | j) != 0) {
/* 389 */         paramRectangle = new Rectangle(paramRectangle);
/* 390 */         paramRectangle.grow(i, j);
/*     */       }
/*     */     }
/* 393 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.GaussianShadow
 * JD-Core Version:    0.6.2
 */