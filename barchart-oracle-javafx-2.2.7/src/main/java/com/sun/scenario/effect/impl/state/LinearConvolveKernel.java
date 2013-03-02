/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public abstract class LinearConvolveKernel
/*     */ {
/*     */   public static final int MAX_KERNEL_SIZE = 128;
/* 297 */   protected static float[] BLACK_COMPONENTS = Color4f.BLACK.getPremultipliedRGBComponents();
/*     */ 
/*     */   public static int getPeerSize(int paramInt)
/*     */   {
/* 100 */     if (paramInt < 32) return paramInt + 3 & 0xFFFFFFFC;
/* 101 */     if (paramInt <= 128) return paramInt + 31 & 0xFFFFFFE0;
/* 102 */     throw new RuntimeException("No peer available for kernel size: " + paramInt);
/*     */   }
/*     */ 
/*     */   public boolean isShadow()
/*     */   {
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract int getNumberOfPasses();
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/* 143 */     return false;
/*     */   }
/*     */ 
/*     */   public PassType getPassType(int paramInt)
/*     */   {
/* 156 */     return PassType.GENERAL_VECTOR;
/*     */   }
/*     */ 
/*     */   public abstract Rectangle getResultBounds(Rectangle paramRectangle, int paramInt);
/*     */ 
/*     */   public Rectangle getScaledResultBounds(Rectangle paramRectangle, int paramInt)
/*     */   {
/* 182 */     return getResultBounds(paramRectangle, paramInt);
/*     */   }
/*     */ 
/*     */   public abstract float[] getVector(Rectangle paramRectangle, BaseTransform paramBaseTransform, int paramInt);
/*     */ 
/*     */   public abstract int getKernelSize(int paramInt);
/*     */ 
/*     */   public int getScaledKernelSize(int paramInt)
/*     */   {
/* 230 */     return getKernelSize(paramInt);
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleX()
/*     */   {
/* 251 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleY()
/*     */   {
/* 272 */     return 0;
/*     */   }
/*     */ 
/*     */   public abstract FloatBuffer getWeights(int paramInt);
/*     */ 
/*     */   public int getWeightsArrayLength(int paramInt)
/*     */   {
/* 292 */     int i = getScaledKernelSize(paramInt);
/* 293 */     int j = getPeerSize(i);
/* 294 */     return j / 4;
/*     */   }
/*     */ 
/*     */   public float[] getShadowColorComponents(int paramInt)
/*     */   {
/* 310 */     return BLACK_COMPONENTS;
/*     */   }
/*     */ 
/*     */   public EffectPeer getPeer(Renderer paramRenderer, FilterContext paramFilterContext, int paramInt) {
/* 314 */     if (isNop(paramInt)) {
/* 315 */       return null;
/*     */     }
/* 317 */     int i = getScaledKernelSize(paramInt);
/* 318 */     int j = getPeerSize(i);
/* 319 */     String str = isShadow() ? "LinearConvolveShadow" : "LinearConvolve";
/* 320 */     return paramRenderer.getPeerInstance(paramFilterContext, str, j);
/*     */   }
/*     */ 
/*     */   public Rectangle transform(Rectangle paramRectangle, int paramInt1, int paramInt2)
/*     */   {
/* 327 */     if ((paramRectangle == null) || ((paramInt1 | paramInt2) == 0)) {
/* 328 */       return paramRectangle;
/*     */     }
/* 330 */     paramRectangle = new Rectangle(paramRectangle);
/* 331 */     if (paramInt1 < 0) {
/* 332 */       paramInt1 = -paramInt1;
/* 333 */       paramRectangle.width = (paramRectangle.width + (1 << paramInt1) - 1 >> paramInt1);
/* 334 */       paramRectangle.x >>= paramInt1;
/* 335 */     } else if (paramInt1 > 0) {
/* 336 */       paramRectangle.width <<= paramInt1;
/* 337 */       paramRectangle.x <<= paramInt1;
/*     */     }
/* 339 */     if (paramInt2 < 0) {
/* 340 */       paramInt2 = -paramInt2;
/* 341 */       paramRectangle.height = (paramRectangle.height + (1 << paramInt2) - 1 >> paramInt2);
/* 342 */       paramRectangle.y >>= paramInt2;
/* 343 */     } else if (paramInt2 > 0) {
/* 344 */       paramRectangle.height <<= paramInt2;
/* 345 */       paramRectangle.y <<= paramInt2;
/*     */     }
/* 347 */     return paramRectangle;
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(Effect paramEffect, FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 356 */     Object localObject = paramArrayOfImageData[0];
/* 357 */     ((ImageData)localObject).addref();
/* 358 */     if (isNop()) {
/* 359 */       return localObject;
/*     */     }
/* 361 */     Rectangle localRectangle1 = paramArrayOfImageData[0].getUntransformedBounds();
/* 362 */     int i = localRectangle1.width;
/* 363 */     int j = localRectangle1.height;
/* 364 */     Renderer localRenderer = Renderer.getRenderer(paramFilterContext, paramEffect, i, j);
/* 365 */     EffectPeer localEffectPeer1 = getPeer(localRenderer, paramFilterContext, 0);
/* 366 */     EffectPeer localEffectPeer2 = getPeer(localRenderer, paramFilterContext, 1);
/* 367 */     int k = 0;
/* 368 */     int m = 0;
/* 369 */     if ((localEffectPeer1 instanceof LinearConvolvePeer)) {
/* 370 */       k = ((LinearConvolvePeer)localEffectPeer1).getPow2ScaleX(this);
/*     */     }
/* 372 */     if ((localEffectPeer2 instanceof LinearConvolvePeer)) {
/* 373 */       m = ((LinearConvolvePeer)localEffectPeer2).getPow2ScaleY(this);
/*     */     }
/* 375 */     Rectangle localRectangle2 = paramRectangle;
/* 376 */     if ((k | m) != 0) {
/* 377 */       localObject = localRenderer.transform(paramFilterContext, (ImageData)localObject, k, m);
/* 378 */       if (!((ImageData)localObject).validate(paramFilterContext)) {
/* 379 */         ((ImageData)localObject).unref();
/* 380 */         return localObject;
/*     */       }
/* 382 */       localRectangle2 = transform(paramRectangle, k, m);
/*     */     }
/* 384 */     if (localRectangle2 != null)
/*     */     {
/* 390 */       int n = getScaledKernelSize(0) / 2;
/* 391 */       int i1 = getScaledKernelSize(1) / 2;
/* 392 */       if ((n | i1) != 0) {
/* 393 */         if (localRectangle2 == paramRectangle) {
/* 394 */           localRectangle2 = new Rectangle(paramRectangle);
/*     */         }
/* 396 */         localRectangle2.grow(n, i1);
/*     */       }
/*     */     }
/*     */     ImageData localImageData;
/* 399 */     if (localEffectPeer1 != null) {
/* 400 */       localEffectPeer1.setPass(0);
/* 401 */       localImageData = localEffectPeer1.filter(paramEffect, paramBaseTransform, localRectangle2, new ImageData[] { localObject });
/* 402 */       ((ImageData)localObject).unref();
/* 403 */       localObject = localImageData;
/* 404 */       if (!((ImageData)localObject).validate(paramFilterContext)) {
/* 405 */         ((ImageData)localObject).unref();
/* 406 */         return localObject;
/*     */       }
/*     */     }
/*     */ 
/* 410 */     if (localEffectPeer2 != null) {
/* 411 */       localEffectPeer2.setPass(1);
/* 412 */       localImageData = localEffectPeer2.filter(paramEffect, paramBaseTransform, localRectangle2, new ImageData[] { localObject });
/* 413 */       ((ImageData)localObject).unref();
/* 414 */       localObject = localImageData;
/* 415 */       if (!((ImageData)localObject).validate(paramFilterContext)) {
/* 416 */         ((ImageData)localObject).unref();
/* 417 */         return localObject;
/*     */       }
/*     */     }
/*     */ 
/* 421 */     if ((k | m) != 0) {
/* 422 */       localObject = localRenderer.transform(paramFilterContext, (ImageData)localObject, -k, -m);
/*     */     }
/* 424 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static enum PassType
/*     */   {
/*  59 */     HORIZONTAL_CENTERED, 
/*     */ 
/*  74 */     VERTICAL_CENTERED, 
/*     */ 
/*  82 */     GENERAL_VECTOR;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.LinearConvolveKernel
 * JD-Core Version:    0.6.2
 */