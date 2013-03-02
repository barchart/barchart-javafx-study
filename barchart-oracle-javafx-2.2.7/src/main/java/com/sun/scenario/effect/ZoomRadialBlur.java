/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.state.ZoomRadialBlurState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ZoomRadialBlur extends CoreEffect
/*     */ {
/*     */   private int r;
/*     */   private float centerX;
/*     */   private float centerY;
/*  40 */   private final ZoomRadialBlurState state = new ZoomRadialBlurState(this);
/*     */ 
/*     */   public ZoomRadialBlur()
/*     */   {
/*  51 */     this(1);
/*     */   }
/*     */ 
/*     */   public ZoomRadialBlur(int paramInt)
/*     */   {
/*  67 */     this(paramInt, DefaultInput);
/*     */   }
/*     */ 
/*     */   public ZoomRadialBlur(int paramInt, Effect paramEffect)
/*     */   {
/*  79 */     super(paramEffect);
/*  80 */     setRadius(paramInt);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/*  85 */     return this.state;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  94 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 105 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public int getRadius()
/*     */   {
/* 114 */     return this.r;
/*     */   }
/*     */ 
/*     */   public void setRadius(int paramInt)
/*     */   {
/* 131 */     if ((paramInt < 1) || (paramInt > 64)) {
/* 132 */       throw new IllegalArgumentException("Radius must be in the range [1,64]");
/*     */     }
/* 134 */     int i = this.r;
/* 135 */     this.r = paramInt;
/* 136 */     this.state.invalidateDeltas();
/* 137 */     firePropertyChange("radius", Integer.valueOf(i), Integer.valueOf(paramInt));
/* 138 */     updatePeer();
/*     */   }
/*     */ 
/*     */   private void updatePeer()
/*     */   {
/* 145 */     int i = 4 + this.r - this.r % 4;
/* 146 */     updatePeerKey("ZoomRadialBlur", i);
/*     */   }
/*     */ 
/*     */   public float getCenterX()
/*     */   {
/* 155 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public void setCenterX(float paramFloat)
/*     */   {
/* 164 */     float f = this.centerX;
/* 165 */     this.centerX = paramFloat;
/* 166 */     firePropertyChange("centerX", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getCenterY()
/*     */   {
/* 175 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public void setCenterY(float paramFloat)
/*     */   {
/* 184 */     float f = this.centerY;
/* 185 */     this.centerY = paramFloat;
/* 186 */     firePropertyChange("centerY", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 195 */     Rectangle localRectangle = paramArrayOfImageData[0].getUntransformedBounds();
/* 196 */     this.state.updateDeltas(1.0F / localRectangle.width, 1.0F / localRectangle.height);
/* 197 */     return super.filterImageDatas(paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 202 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 213 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.ZoomRadialBlur
 * JD-Core Version:    0.6.2
 */