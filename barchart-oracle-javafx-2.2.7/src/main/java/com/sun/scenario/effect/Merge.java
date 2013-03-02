/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Merge extends CoreEffect
/*     */ {
/*     */   public Merge(Effect paramEffect1, Effect paramEffect2)
/*     */   {
/*  47 */     super(paramEffect1, paramEffect2);
/*  48 */     updatePeerKey("Merge");
/*     */   }
/*     */ 
/*     */   public final Effect getBottomInput()
/*     */   {
/*  57 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setBottomInput(Effect paramEffect)
/*     */   {
/*  68 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getTopInput()
/*     */   {
/*  77 */     return (Effect)getInputs().get(1);
/*     */   }
/*     */ 
/*     */   public void setTopInput(Effect paramEffect)
/*     */   {
/*  88 */     setInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 111 */     return getDefaultedInput(1, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 135 */     return getDefaultedInput(1, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 145 */     Effect localEffect1 = getDefaultedInput(0, paramEffect);
/* 146 */     Effect localEffect2 = getDefaultedInput(1, paramEffect);
/* 147 */     ImageData localImageData1 = localEffect1.filter(paramFilterContext, paramBaseTransform, paramRectangle, paramObject, paramEffect);
/*     */ 
/* 149 */     if (localImageData1 != null) {
/* 150 */       if (!localImageData1.validate(paramFilterContext)) {
/* 151 */         return new ImageData(paramFilterContext, null, null);
/*     */       }
/* 153 */       if ((paramObject instanceof ImageDataRenderer)) {
/* 154 */         localObject = (ImageDataRenderer)paramObject;
/* 155 */         ((ImageDataRenderer)localObject).renderImage(localImageData1, BaseTransform.IDENTITY_TRANSFORM, paramFilterContext);
/* 156 */         localImageData1.unref();
/* 157 */         localImageData1 = null;
/*     */       }
/*     */     }
/* 160 */     if (localImageData1 == null) {
/* 161 */       return localEffect2.filter(paramFilterContext, paramBaseTransform, paramRectangle, paramObject, paramEffect);
/*     */     }
/*     */ 
/* 164 */     Object localObject = localEffect2.filter(paramFilterContext, paramBaseTransform, paramRectangle, null, paramEffect);
/*     */ 
/* 166 */     if (!((ImageData)localObject).validate(paramFilterContext)) {
/* 167 */       return new ImageData(paramFilterContext, null, null);
/*     */     }
/* 169 */     ImageData localImageData2 = filterImageDatas(paramFilterContext, paramBaseTransform, paramRectangle, new ImageData[] { localImageData1, localObject });
/*     */ 
/* 171 */     localImageData1.unref();
/* 172 */     ((ImageData)localObject).unref();
/* 173 */     return localImageData2;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 184 */     throw new InternalError("Merge.getInputClip should not be called");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Merge
 * JD-Core Version:    0.6.2
 */