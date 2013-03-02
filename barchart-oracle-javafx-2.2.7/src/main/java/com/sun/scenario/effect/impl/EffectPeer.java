/*     */ package com.sun.scenario.effect.impl;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ 
/*     */ public abstract class EffectPeer
/*     */ {
/*     */   private final FilterContext fctx;
/*     */   private final Renderer renderer;
/*     */   private final String uniqueName;
/*     */   private Effect effect;
/*     */   private int pass;
/* 116 */   private final Rectangle[] inputBounds = new Rectangle[2];
/*     */ 
/* 136 */   private final BaseTransform[] inputTransforms = new BaseTransform[2];
/*     */ 
/* 144 */   private final Rectangle[] inputNativeBounds = new Rectangle[2];
/*     */   private Rectangle destBounds;
/* 377 */   private final Rectangle destNativeBounds = new Rectangle();
/*     */ 
/*     */   protected EffectPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  48 */     if (paramFilterContext == null) {
/*  49 */       throw new IllegalArgumentException("FilterContext must be non-null");
/*     */     }
/*  51 */     this.fctx = paramFilterContext;
/*  52 */     this.renderer = paramRenderer;
/*  53 */     this.uniqueName = paramString;
/*     */   }
/*     */ 
/*     */   public boolean isImageDataCompatible(ImageData paramImageData) {
/*  57 */     return getRenderer().isImageDataCompatible(paramImageData);
/*     */   }
/*     */ 
/*     */   public abstract ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData);
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType()
/*     */   {
/*  73 */     return this.renderer.getAccelType();
/*     */   }
/*     */ 
/*     */   protected final FilterContext getFilterContext() {
/*  77 */     return this.fctx;
/*     */   }
/*     */ 
/*     */   protected Renderer getRenderer() {
/*  81 */     return this.renderer;
/*     */   }
/*     */ 
/*     */   public String getUniqueName()
/*     */   {
/*  93 */     return this.uniqueName;
/*     */   }
/*     */ 
/*     */   protected Effect getEffect() {
/*  97 */     return this.effect;
/*     */   }
/*     */ 
/*     */   protected void setEffect(Effect paramEffect) {
/* 101 */     this.effect = paramEffect;
/*     */   }
/*     */ 
/*     */   public final int getPass() {
/* 105 */     return this.pass;
/*     */   }
/*     */ 
/*     */   public void setPass(int paramInt) {
/* 109 */     this.pass = paramInt;
/*     */   }
/*     */ 
/*     */   protected final Rectangle getInputBounds(int paramInt)
/*     */   {
/* 130 */     return this.inputBounds[paramInt];
/*     */   }
/*     */   protected final void setInputBounds(int paramInt, Rectangle paramRectangle) {
/* 133 */     this.inputBounds[paramInt] = paramRectangle;
/*     */   }
/*     */ 
/*     */   protected final BaseTransform getInputTransform(int paramInt)
/*     */   {
/* 138 */     return this.inputTransforms[paramInt];
/*     */   }
/*     */   protected final void setInputTransform(int paramInt, BaseTransform paramBaseTransform) {
/* 141 */     this.inputTransforms[paramInt] = paramBaseTransform;
/*     */   }
/*     */ 
/*     */   protected final Rectangle getInputNativeBounds(int paramInt)
/*     */   {
/* 165 */     return this.inputNativeBounds[paramInt];
/*     */   }
/*     */   protected final void setInputNativeBounds(int paramInt, Rectangle paramRectangle) {
/* 168 */     this.inputNativeBounds[paramInt] = paramRectangle;
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 175 */     return getEffect().getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   protected float[] getSourceRegion(int paramInt)
/*     */   {
/* 201 */     return getSourceRegion(getInputBounds(paramInt), getInputNativeBounds(paramInt), getDestBounds());
/*     */   }
/*     */ 
/*     */   static float[] getSourceRegion(Rectangle paramRectangle1, Rectangle paramRectangle2, Rectangle paramRectangle3)
/*     */   {
/* 231 */     float f1 = paramRectangle3.x - paramRectangle1.x;
/* 232 */     float f2 = paramRectangle3.y - paramRectangle1.y;
/* 233 */     float f3 = f1 + paramRectangle3.width;
/* 234 */     float f4 = f2 + paramRectangle3.height;
/* 235 */     float f5 = paramRectangle2.width;
/* 236 */     float f6 = paramRectangle2.height;
/* 237 */     return new float[] { f1 / f5, f2 / f6, f3 / f5, f4 / f6 };
/*     */   }
/*     */ 
/*     */   public int getTextureCoordinates(int paramInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/* 289 */     return getTextureCoordinates(paramArrayOfFloat, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramRectangle, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public static int getTextureCoordinates(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/* 340 */     paramArrayOfFloat[0] = paramRectangle.x;
/* 341 */     paramArrayOfFloat[1] = paramRectangle.y;
/* 342 */     paramArrayOfFloat[2] = (paramArrayOfFloat[0] + paramRectangle.width);
/* 343 */     paramArrayOfFloat[3] = (paramArrayOfFloat[1] + paramRectangle.height);
/*     */     int i;
/* 345 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/* 346 */       paramFloat1 += (float)paramBaseTransform.getMxt();
/* 347 */       paramFloat2 += (float)paramBaseTransform.getMyt();
/* 348 */       i = 4;
/*     */     } else {
/* 350 */       paramArrayOfFloat[4] = paramArrayOfFloat[2];
/* 351 */       paramArrayOfFloat[5] = paramArrayOfFloat[1];
/* 352 */       paramArrayOfFloat[6] = paramArrayOfFloat[0];
/* 353 */       paramArrayOfFloat[7] = paramArrayOfFloat[3];
/* 354 */       i = 8;
/*     */       try {
/* 356 */         paramBaseTransform.inverseTransform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, 4);
/*     */       }
/*     */       catch (NoninvertibleTransformException localNoninvertibleTransformException)
/*     */       {
/*     */         float tmp134_133 = (paramArrayOfFloat[2] = paramArrayOfFloat[4] = 0.0F); paramArrayOfFloat[1] = tmp134_133; paramArrayOfFloat[0] = tmp134_133;
/* 359 */         return 4;
/*     */       }
/*     */     }
/* 362 */     for (int j = 0; j < i; j += 2) {
/* 363 */       paramArrayOfFloat[j] = ((paramArrayOfFloat[j] - paramFloat1) / paramFloat3);
/* 364 */       paramArrayOfFloat[(j + 1)] = ((paramArrayOfFloat[(j + 1)] - paramFloat2) / paramFloat4);
/*     */     }
/* 366 */     return i;
/*     */   }
/*     */ 
/*     */   protected final void setDestBounds(Rectangle paramRectangle)
/*     */   {
/* 371 */     this.destBounds = paramRectangle;
/*     */   }
/*     */   protected final Rectangle getDestBounds() {
/* 374 */     return this.destBounds;
/*     */   }
/*     */ 
/*     */   protected final Rectangle getDestNativeBounds()
/*     */   {
/* 379 */     return this.destNativeBounds;
/*     */   }
/*     */   protected final void setDestNativeBounds(int paramInt1, int paramInt2) {
/* 382 */     this.destNativeBounds.width = paramInt1;
/* 383 */     this.destNativeBounds.height = paramInt2;
/*     */   }
/*     */ 
/*     */   protected Object getSamplerData(int paramInt) {
/* 387 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean isOriginUpperLeft()
/*     */   {
/* 402 */     return getAccelType() != Effect.AccelType.OPENGL;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.EffectPeer
 * JD-Core Version:    0.6.2
 */