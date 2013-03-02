/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class DisplacementMap extends CoreEffect
/*     */ {
/*     */   private FloatMap mapData;
/*  40 */   private float scaleX = 1.0F;
/*  41 */   private float scaleY = 1.0F;
/*  42 */   private float offsetX = 0.0F;
/*  43 */   private float offsetY = 0.0F;
/*     */   private boolean wrap;
/*     */ 
/*     */   public DisplacementMap(FloatMap paramFloatMap)
/*     */   {
/*  57 */     this(paramFloatMap, DefaultInput);
/*     */   }
/*     */ 
/*     */   public DisplacementMap(FloatMap paramFloatMap, Effect paramEffect)
/*     */   {
/*  70 */     super(paramEffect);
/*  71 */     setMapData(paramFloatMap);
/*  72 */     updatePeerKey("DisplacementMap");
/*     */   }
/*     */ 
/*     */   public final FloatMap getMapData()
/*     */   {
/*  81 */     return this.mapData;
/*     */   }
/*     */ 
/*     */   public void setMapData(FloatMap paramFloatMap)
/*     */   {
/*  91 */     if (paramFloatMap == null) {
/*  92 */       throw new IllegalArgumentException("Map data must be non-null");
/*     */     }
/*  94 */     FloatMap localFloatMap = this.mapData;
/*  95 */     this.mapData = paramFloatMap;
/*  96 */     firePropertyChange("mapData", localFloatMap, paramFloatMap);
/*     */   }
/*     */ 
/*     */   public final Effect getContentInput()
/*     */   {
/* 105 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setContentInput(Effect paramEffect)
/*     */   {
/* 116 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getScaleX()
/*     */   {
/* 125 */     return this.scaleX;
/*     */   }
/*     */ 
/*     */   public void setScaleX(float paramFloat)
/*     */   {
/* 140 */     float f = this.scaleX;
/* 141 */     this.scaleX = paramFloat;
/* 142 */     firePropertyChange("scaleX", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getScaleY()
/*     */   {
/* 151 */     return this.scaleY;
/*     */   }
/*     */ 
/*     */   public void setScaleY(float paramFloat)
/*     */   {
/* 166 */     float f = this.scaleY;
/* 167 */     this.scaleY = paramFloat;
/* 168 */     firePropertyChange("scaleY", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getOffsetX()
/*     */   {
/* 177 */     return this.offsetX;
/*     */   }
/*     */ 
/*     */   public void setOffsetX(float paramFloat)
/*     */   {
/* 192 */     float f = this.offsetX;
/* 193 */     this.offsetX = paramFloat;
/* 194 */     firePropertyChange("offsetX", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getOffsetY()
/*     */   {
/* 203 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   public void setOffsetY(float paramFloat)
/*     */   {
/* 218 */     float f = this.offsetY;
/* 219 */     this.offsetY = paramFloat;
/* 220 */     firePropertyChange("offsetY", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public boolean getWrap()
/*     */   {
/* 230 */     return this.wrap;
/*     */   }
/*     */ 
/*     */   public void setWrap(boolean paramBoolean)
/*     */   {
/* 246 */     boolean bool = this.wrap;
/* 247 */     this.wrap = paramBoolean;
/* 248 */     firePropertyChange("wrap", Boolean.valueOf(bool), Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 253 */     return true;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 277 */     return new Point2D((0.0F / 0.0F), (0.0F / 0.0F));
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 301 */     BaseBounds localBaseBounds = getBounds(BaseTransform.IDENTITY_TRANSFORM, paramEffect);
/* 302 */     float f1 = localBaseBounds.getWidth();
/* 303 */     float f2 = localBaseBounds.getHeight();
/* 304 */     float f3 = (paramPoint2D.x - localBaseBounds.getMinX()) / f1;
/* 305 */     float f4 = (paramPoint2D.y - localBaseBounds.getMinY()) / f2;
/*     */ 
/* 308 */     if ((f3 >= 0.0F) && (f4 >= 0.0F) && (f3 < 1.0F) && (f4 < 1.0F)) {
/* 309 */       int i = (int)(f3 * this.mapData.getWidth());
/* 310 */       int j = (int)(f4 * this.mapData.getHeight());
/* 311 */       float f5 = this.mapData.getSample(i, j, 0);
/* 312 */       float f6 = this.mapData.getSample(i, j, 1);
/* 313 */       f3 += this.scaleX * (f5 + this.offsetX);
/* 314 */       f4 += this.scaleY * (f6 + this.offsetY);
/* 315 */       if (this.wrap) {
/* 316 */         f3 = (float)(f3 - Math.floor(f3));
/* 317 */         f4 = (float)(f4 - Math.floor(f4));
/*     */       }
/* 319 */       paramPoint2D = new Point2D(f3 * f1 + localBaseBounds.getMinX(), f4 * f2 + localBaseBounds.getMinY());
/*     */     }
/*     */ 
/* 322 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 334 */     return super.filterImageDatas(paramFilterContext, paramBaseTransform, null, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 346 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.DisplacementMap
 * JD-Core Version:    0.6.2
 */