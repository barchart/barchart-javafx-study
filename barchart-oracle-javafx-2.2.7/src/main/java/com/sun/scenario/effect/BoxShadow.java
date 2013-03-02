/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.BoxShadowState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BoxShadow extends AbstractShadow
/*     */ {
/*  42 */   private final BoxShadowState state = new BoxShadowState();
/*     */ 
/*     */   public BoxShadow()
/*     */   {
/*  55 */     this(1, 1);
/*     */   }
/*     */ 
/*     */   public BoxShadow(int paramInt1, int paramInt2)
/*     */   {
/*  74 */     this(paramInt1, paramInt2, 1, DefaultInput);
/*     */   }
/*     */ 
/*     */   public BoxShadow(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  95 */     this(paramInt1, paramInt2, paramInt3, DefaultInput);
/*     */   }
/*     */ 
/*     */   public BoxShadow(int paramInt1, int paramInt2, int paramInt3, Effect paramEffect)
/*     */   {
/* 113 */     super(paramEffect);
/* 114 */     setHorizontalSize(paramInt1);
/* 115 */     setVerticalSize(paramInt2);
/* 116 */     setPasses(paramInt3);
/* 117 */     setColor(Color4f.BLACK);
/* 118 */     setSpread(0.0F);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/* 123 */     return this.state;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 132 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 144 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public int getHorizontalSize()
/*     */   {
/* 153 */     return this.state.getHsize();
/*     */   }
/*     */ 
/*     */   public void setHorizontalSize(int paramInt)
/*     */   {
/* 170 */     int i = this.state.getHsize();
/* 171 */     this.state.setHsize(paramInt);
/* 172 */     firePropertyChange("hsize", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getVerticalSize()
/*     */   {
/* 181 */     return this.state.getVsize();
/*     */   }
/*     */ 
/*     */   public void setVerticalSize(int paramInt)
/*     */   {
/* 198 */     int i = this.state.getVsize();
/* 199 */     this.state.setVsize(paramInt);
/* 200 */     firePropertyChange("vsize", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getPasses()
/*     */   {
/* 210 */     return this.state.getBlurPasses();
/*     */   }
/*     */ 
/*     */   public void setPasses(int paramInt)
/*     */   {
/* 230 */     int i = this.state.getBlurPasses();
/* 231 */     this.state.setBlurPasses(paramInt);
/* 232 */     firePropertyChange("passes", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/* 241 */     return this.state.getShadowColor();
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 257 */     Color4f localColor4f = this.state.getShadowColor();
/* 258 */     this.state.setShadowColor(paramColor4f);
/* 259 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/* 268 */     return this.state.getSpread();
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat)
/*     */   {
/* 294 */     float f = this.state.getSpread();
/* 295 */     this.state.setSpread(paramFloat);
/* 296 */     firePropertyChange("spread", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getGaussianRadius() {
/* 300 */     float f = (getHorizontalSize() + getVerticalSize()) / 2.0F;
/* 301 */     f *= 3.0F;
/* 302 */     return f < 1.0F ? 0.0F : (f - 1.0F) / 2.0F;
/*     */   }
/*     */ 
/*     */   public float getGaussianWidth() {
/* 306 */     return getHorizontalSize() * 3.0F;
/*     */   }
/*     */ 
/*     */   public float getGaussianHeight() {
/* 310 */     return getVerticalSize() * 3.0F;
/*     */   }
/*     */ 
/*     */   public void setGaussianRadius(float paramFloat) {
/* 314 */     float f = paramFloat * 2.0F + 1.0F;
/* 315 */     setGaussianWidth(f);
/* 316 */     setGaussianHeight(f);
/*     */   }
/*     */ 
/*     */   public void setGaussianWidth(float paramFloat) {
/* 320 */     paramFloat /= 3.0F;
/* 321 */     setHorizontalSize(Math.round(paramFloat));
/*     */   }
/*     */ 
/*     */   public void setGaussianHeight(float paramFloat) {
/* 325 */     paramFloat /= 3.0F;
/* 326 */     setVerticalSize(Math.round(paramFloat));
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode getMode() {
/* 330 */     switch (getPasses()) {
/*     */     case 1:
/* 332 */       return AbstractShadow.ShadowMode.ONE_PASS_BOX;
/*     */     case 2:
/* 334 */       return AbstractShadow.ShadowMode.TWO_PASS_BOX;
/*     */     }
/* 336 */     return AbstractShadow.ShadowMode.THREE_PASS_BOX;
/*     */   }
/*     */ 
/*     */   public AbstractShadow implFor(AbstractShadow.ShadowMode paramShadowMode)
/*     */   {
/* 341 */     switch (1.$SwitchMap$com$sun$scenario$effect$AbstractShadow$ShadowMode[paramShadowMode.ordinal()]) {
/*     */     case 1:
/* 343 */       GaussianShadow localGaussianShadow = new GaussianShadow();
/* 344 */       localGaussianShadow.setInput(getInput());
/* 345 */       localGaussianShadow.setGaussianWidth(getGaussianWidth());
/* 346 */       localGaussianShadow.setGaussianHeight(getGaussianHeight());
/* 347 */       localGaussianShadow.setColor(getColor());
/* 348 */       localGaussianShadow.setSpread(getSpread());
/* 349 */       return localGaussianShadow;
/*     */     case 2:
/* 351 */       setPasses(1);
/* 352 */       break;
/*     */     case 3:
/* 354 */       setPasses(2);
/* 355 */       break;
/*     */     case 4:
/* 357 */       setPasses(3);
/*     */     }
/*     */ 
/* 360 */     return this;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 365 */     return Renderer.getRenderer(paramFilterContext).getAccelType();
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 370 */     BaseBounds localBaseBounds = super.getBounds(null, paramEffect);
/* 371 */     int i = this.state.getKernelSize(0) / 2;
/* 372 */     int j = this.state.getKernelSize(1) / 2;
/* 373 */     RectBounds localRectBounds = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/* 374 */     localRectBounds.grow(i, j);
/* 375 */     return transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 383 */     Rectangle localRectangle = paramArrayOfImageData[0].getUntransformedBounds();
/* 384 */     localRectangle = this.state.getResultBounds(localRectangle, 0);
/* 385 */     localRectangle = this.state.getResultBounds(localRectangle, 1);
/* 386 */     localRectangle.intersectWith(paramRectangle);
/* 387 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 396 */     return this.state.filterImageDatas(this, paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 401 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 412 */     if (paramRectangle != null) {
/* 413 */       int i = this.state.getKernelSize(0) / 2;
/* 414 */       int j = this.state.getKernelSize(1) / 2;
/* 415 */       if ((i | j) != 0) {
/* 416 */         paramRectangle = new Rectangle(paramRectangle);
/* 417 */         paramRectangle.grow(i, j);
/*     */       }
/*     */     }
/* 420 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.BoxShadow
 * JD-Core Version:    0.6.2
 */