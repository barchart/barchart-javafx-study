/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Crop extends CoreEffect
/*     */ {
/*     */   public Crop(Effect paramEffect)
/*     */   {
/*  49 */     this(paramEffect, DefaultInput);
/*     */   }
/*     */ 
/*     */   public Crop(Effect paramEffect1, Effect paramEffect2)
/*     */   {
/*  61 */     super(paramEffect1, paramEffect2);
/*  62 */     updatePeerKey("Crop");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  71 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  82 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getBoundsInput()
/*     */   {
/*  91 */     return (Effect)getInputs().get(1);
/*     */   }
/*     */ 
/*     */   public void setBoundsInput(Effect paramEffect)
/*     */   {
/* 103 */     setInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   private Effect getBoundsInput(Effect paramEffect) {
/* 107 */     return getDefaultedInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 112 */     return getBoundsInput(paramEffect).getBounds(paramBaseTransform, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 135 */     return getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 159 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 169 */     Effect localEffect1 = getDefaultedInput(1, paramEffect);
/* 170 */     BaseBounds localBaseBounds = localEffect1.getBounds(paramBaseTransform, paramEffect);
/* 171 */     Rectangle localRectangle = new Rectangle(localBaseBounds);
/* 172 */     localRectangle.intersectWith(paramRectangle);
/* 173 */     Effect localEffect2 = getDefaultedInput(0, paramEffect);
/* 174 */     ImageData localImageData1 = localEffect2.filter(paramFilterContext, paramBaseTransform, localRectangle, null, paramEffect);
/* 175 */     if (!localImageData1.validate(paramFilterContext)) {
/* 176 */       return new ImageData(paramFilterContext, null, null);
/*     */     }
/* 178 */     ImageData localImageData2 = filterImageDatas(paramFilterContext, paramBaseTransform, localRectangle, new ImageData[] { localImageData1 });
/* 179 */     localImageData1.unref();
/* 180 */     return localImageData2;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 191 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Crop
 * JD-Core Version:    0.6.2
 */