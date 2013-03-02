/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.GaussianBlurState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GaussianBlur extends CoreEffect
/*     */ {
/*  41 */   private GaussianBlurState state = new GaussianBlurState();
/*     */ 
/*     */   public GaussianBlur()
/*     */   {
/*  52 */     this(10.0F, DefaultInput);
/*     */   }
/*     */ 
/*     */   public GaussianBlur(float paramFloat)
/*     */   {
/*  68 */     this(paramFloat, DefaultInput);
/*     */   }
/*     */ 
/*     */   public GaussianBlur(float paramFloat, Effect paramEffect)
/*     */   {
/*  80 */     super(paramEffect);
/*  81 */     this.state.setRadius(paramFloat);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/*  86 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/*  91 */     return Renderer.getRenderer(paramFilterContext).getAccelType();
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 100 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 110 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 119 */     return this.state.getRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 136 */     float f = this.state.getRadius();
/* 137 */     this.state.setRadius(paramFloat);
/* 138 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 143 */     BaseBounds localBaseBounds = super.getBounds(null, paramEffect);
/* 144 */     int i = this.state.getPad(0);
/* 145 */     int j = this.state.getPad(1);
/* 146 */     RectBounds localRectBounds = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/* 147 */     localRectBounds.grow(i, j);
/* 148 */     return transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 156 */     Rectangle localRectangle1 = super.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 157 */     int i = this.state.getPad(0);
/* 158 */     int j = this.state.getPad(1);
/* 159 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 160 */     localRectangle2.grow(i, j);
/* 161 */     return localRectangle2;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 170 */     return this.state.filterImageDatas(this, paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 175 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 186 */     if (paramRectangle != null) {
/* 187 */       int i = this.state.getPad(0);
/* 188 */       int j = this.state.getPad(1);
/* 189 */       if ((i | j) != 0) {
/* 190 */         paramRectangle = new Rectangle(paramRectangle);
/* 191 */         paramRectangle.grow(i, j);
/*     */       }
/*     */     }
/* 194 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.GaussianBlur
 * JD-Core Version:    0.6.2
 */