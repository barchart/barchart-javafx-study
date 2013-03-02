/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.MotionBlurState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MotionBlur extends CoreEffect
/*     */ {
/*  41 */   private MotionBlurState state = new MotionBlurState();
/*     */ 
/*     */   public MotionBlur()
/*     */   {
/*  53 */     this(10.0F, 0.0F, DefaultInput);
/*     */   }
/*     */ 
/*     */   public MotionBlur(float paramFloat1, float paramFloat2)
/*     */   {
/*  70 */     this(paramFloat1, paramFloat2, DefaultInput);
/*     */   }
/*     */ 
/*     */   public MotionBlur(float paramFloat1, float paramFloat2, Effect paramEffect)
/*     */   {
/*  84 */     super(paramEffect);
/*  85 */     setRadius(paramFloat1);
/*  86 */     setAngle(paramFloat2);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/*  91 */     return this.state;
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/*  96 */     return Renderer.getRenderer(paramFilterContext).getAccelType();
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 105 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 115 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getRadius()
/*     */   {
/* 124 */     return this.state.getRadius();
/*     */   }
/*     */ 
/*     */   public void setRadius(float paramFloat)
/*     */   {
/* 141 */     float f = this.state.getRadius();
/* 142 */     this.state.setRadius(paramFloat);
/* 143 */     firePropertyChange("radius", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getAngle()
/*     */   {
/* 152 */     return this.state.getAngle();
/*     */   }
/*     */ 
/*     */   public void setAngle(float paramFloat)
/*     */   {
/* 167 */     float f = this.state.getAngle();
/* 168 */     this.state.setAngle(paramFloat);
/* 169 */     firePropertyChange("angle", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 174 */     BaseBounds localBaseBounds = super.getBounds(null, paramEffect);
/* 175 */     int i = this.state.getHPad();
/* 176 */     int j = this.state.getVPad();
/* 177 */     RectBounds localRectBounds = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/* 178 */     ((RectBounds)localRectBounds).grow(i, j);
/* 179 */     return transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 187 */     Rectangle localRectangle1 = super.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 188 */     int i = this.state.getHPad();
/* 189 */     int j = this.state.getVPad();
/* 190 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 191 */     localRectangle2.grow(i, j);
/* 192 */     return localRectangle2;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 201 */     return this.state.filterImageDatas(this, paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 217 */     if (paramRectangle != null) {
/* 218 */       int i = this.state.getHPad();
/* 219 */       int j = this.state.getVPad();
/* 220 */       if ((i | j) != 0) {
/* 221 */         paramRectangle = new Rectangle(paramRectangle);
/* 222 */         paramRectangle.grow(i, j);
/*     */       }
/*     */     }
/* 225 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.MotionBlur
 * JD-Core Version:    0.6.2
 */