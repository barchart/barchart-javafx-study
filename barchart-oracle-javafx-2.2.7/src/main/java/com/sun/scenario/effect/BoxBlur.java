/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.BoxBlurState;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BoxBlur extends CoreEffect
/*     */ {
/*  42 */   private final BoxBlurState state = new BoxBlurState();
/*     */ 
/*     */   public BoxBlur()
/*     */   {
/*  55 */     this(1, 1);
/*     */   }
/*     */ 
/*     */   public BoxBlur(int paramInt1, int paramInt2)
/*     */   {
/*  74 */     this(paramInt1, paramInt2, 1, DefaultInput);
/*     */   }
/*     */ 
/*     */   public BoxBlur(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  95 */     this(paramInt1, paramInt2, paramInt3, DefaultInput);
/*     */   }
/*     */ 
/*     */   public BoxBlur(int paramInt1, int paramInt2, int paramInt3, Effect paramEffect)
/*     */   {
/* 113 */     super(paramEffect);
/* 114 */     setHorizontalSize(paramInt1);
/* 115 */     setVerticalSize(paramInt2);
/* 116 */     setPasses(paramInt3);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/* 121 */     return this.state;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 130 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 142 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public int getHorizontalSize()
/*     */   {
/* 151 */     return this.state.getHsize();
/*     */   }
/*     */ 
/*     */   public void setHorizontalSize(int paramInt)
/*     */   {
/* 168 */     int i = this.state.getHsize();
/* 169 */     this.state.setHsize(paramInt);
/* 170 */     firePropertyChange("hsize", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getVerticalSize()
/*     */   {
/* 179 */     return this.state.getVsize();
/*     */   }
/*     */ 
/*     */   public void setVerticalSize(int paramInt)
/*     */   {
/* 196 */     int i = this.state.getVsize();
/* 197 */     this.state.setVsize(paramInt);
/* 198 */     firePropertyChange("vsize", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getPasses()
/*     */   {
/* 208 */     return this.state.getBlurPasses();
/*     */   }
/*     */ 
/*     */   public void setPasses(int paramInt)
/*     */   {
/* 228 */     int i = this.state.getBlurPasses();
/* 229 */     this.state.setBlurPasses(paramInt);
/* 230 */     firePropertyChange("passes", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 235 */     return Renderer.getRenderer(paramFilterContext).getAccelType();
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 240 */     BaseBounds localBaseBounds = super.getBounds(null, paramEffect);
/* 241 */     int i = this.state.getKernelSize(0) / 2;
/* 242 */     int j = this.state.getKernelSize(1) / 2;
/* 243 */     RectBounds localRectBounds = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/* 244 */     localRectBounds.grow(i, j);
/* 245 */     return transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 253 */     Rectangle localRectangle = paramArrayOfImageData[0].getTransformedBounds(null);
/* 254 */     localRectangle = this.state.getResultBounds(localRectangle, 0);
/* 255 */     localRectangle = this.state.getResultBounds(localRectangle, 1);
/* 256 */     localRectangle.intersectWith(paramRectangle);
/* 257 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 266 */     return this.state.filterImageDatas(this, paramFilterContext, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 282 */     if (paramRectangle != null) {
/* 283 */       int i = this.state.getKernelSize(0) / 2;
/* 284 */       int j = this.state.getKernelSize(1) / 2;
/* 285 */       if ((i | j) != 0) {
/* 286 */         paramRectangle = new Rectangle(paramRectangle);
/* 287 */         paramRectangle.grow(i, j);
/*     */       }
/*     */     }
/* 290 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.BoxBlur
 * JD-Core Version:    0.6.2
 */