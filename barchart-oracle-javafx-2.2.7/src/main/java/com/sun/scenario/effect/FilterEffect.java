/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ 
/*     */ public abstract class FilterEffect extends Effect
/*     */ {
/*     */   protected FilterEffect()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected FilterEffect(Effect paramEffect)
/*     */   {
/*  45 */     super(paramEffect);
/*     */   }
/*     */ 
/*     */   protected FilterEffect(Effect paramEffect1, Effect paramEffect2) {
/*  49 */     super(paramEffect1, paramEffect2);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace() {
/*  53 */     return false;
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/*  59 */     int i = getNumInputs();
/*  60 */     BaseTransform localBaseTransform = paramBaseTransform;
/*  61 */     if (operatesInUserSpace())
/*  62 */       localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     Object localObject;
/*     */     BaseBounds localBaseBounds;
/*  65 */     if (i == 1) {
/*  66 */       localObject = getDefaultedInput(0, paramEffect);
/*  67 */       localBaseBounds = ((Effect)localObject).getBounds(localBaseTransform, paramEffect);
/*     */     } else {
/*  69 */       localObject = new BaseBounds[i];
/*  70 */       for (int j = 0; j < i; j++) {
/*  71 */         Effect localEffect = getDefaultedInput(j, paramEffect);
/*  72 */         localObject[j] = localEffect.getBounds(localBaseTransform, paramEffect);
/*     */       }
/*  74 */       localBaseBounds = combineBounds((BaseBounds[])localObject);
/*     */     }
/*  76 */     if (localBaseTransform != paramBaseTransform) {
/*  77 */       localBaseBounds = transformBounds(paramBaseTransform, localBaseBounds);
/*     */     }
/*  79 */     return localBaseBounds;
/*     */   }
/*     */ 
/*     */   protected abstract Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle);
/*     */ 
/*     */   protected static Rectangle untransformClip(BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/*  89 */     if ((paramBaseTransform.isIdentity()) || (paramRectangle == null) || (paramRectangle.isEmpty())) {
/*  90 */       return paramRectangle;
/*     */     }
/*     */ 
/* 109 */     Rectangle localRectangle = new Rectangle();
/* 110 */     if (paramBaseTransform.isTranslateOrIdentity())
/*     */     {
/* 114 */       localRectangle.setBounds(paramRectangle);
/* 115 */       double d1 = -paramBaseTransform.getMxt();
/* 116 */       double d2 = -paramBaseTransform.getMyt();
/* 117 */       int i = (int)Math.floor(d1);
/* 118 */       int j = (int)Math.floor(d2);
/* 119 */       localRectangle.translate(i, j);
/* 120 */       if (i != d1)
/*     */       {
/* 122 */         localRectangle.width += 1;
/*     */       }
/* 124 */       if (j != d2)
/*     */       {
/* 126 */         localRectangle.height += 1;
/*     */       }
/* 128 */       return localRectangle;
/*     */     }
/* 130 */     RectBounds localRectBounds = new RectBounds(paramRectangle);
/*     */     try {
/* 132 */       localRectBounds.grow(-0.5F, -0.5F);
/* 133 */       localRectBounds = (RectBounds)paramBaseTransform.inverseTransform(localRectBounds, localRectBounds);
/* 134 */       localRectBounds.grow(0.5F, 0.5F);
/* 135 */       localRectangle.setBounds(localRectBounds);
/*     */     }
/*     */     catch (NoninvertibleTransformException localNoninvertibleTransformException)
/*     */     {
/*     */     }
/*     */ 
/* 144 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */   {
/* 154 */     int i = getNumInputs();
/* 155 */     ImageData[] arrayOfImageData = new ImageData[i];
/*     */     Rectangle localRectangle;
/*     */     BaseTransform localBaseTransform;
/* 158 */     if (operatesInUserSpace()) {
/* 159 */       localRectangle = untransformClip(paramBaseTransform, paramRectangle);
/* 160 */       localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     } else {
/* 162 */       localRectangle = paramRectangle;
/* 163 */       localBaseTransform = paramBaseTransform;
/*     */     }
/* 165 */     for (int j = 0; j < i; j++) {
/* 166 */       Effect localEffect = getDefaultedInput(j, paramEffect);
/* 167 */       arrayOfImageData[j] = localEffect.filter(paramFilterContext, localBaseTransform, getInputClip(j, localBaseTransform, localRectangle), null, paramEffect);
/*     */ 
/* 171 */       if (!arrayOfImageData[j].validate(paramFilterContext)) {
/* 172 */         for (int m = 0; m <= j; m++) {
/* 173 */           arrayOfImageData[m].unref();
/*     */         }
/* 175 */         return new ImageData(paramFilterContext, null, null);
/*     */       }
/*     */     }
/* 178 */     ImageData localImageData = filterImageDatas(paramFilterContext, localBaseTransform, localRectangle, arrayOfImageData);
/* 179 */     for (int k = 0; k < i; k++) {
/* 180 */       arrayOfImageData[k].unref();
/*     */     }
/* 182 */     if (localBaseTransform != paramBaseTransform) {
/* 183 */       if ((paramObject instanceof ImageDataRenderer)) {
/* 184 */         ImageDataRenderer localImageDataRenderer = (ImageDataRenderer)paramObject;
/* 185 */         localImageDataRenderer.renderImage(localImageData, paramBaseTransform, paramFilterContext);
/* 186 */         localImageData.unref();
/* 187 */         localImageData = null;
/*     */       } else {
/* 189 */         localImageData = localImageData.transform(paramBaseTransform);
/*     */       }
/*     */     }
/* 192 */     return localImageData;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 197 */     return getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 202 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   protected abstract ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.FilterEffect
 * JD-Core Version:    0.6.2
 */