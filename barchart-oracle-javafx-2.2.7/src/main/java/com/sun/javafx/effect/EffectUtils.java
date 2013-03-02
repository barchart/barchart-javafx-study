/*     */ package com.sun.javafx.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.effect.BlurType;
/*     */ import javafx.scene.effect.Effect;
/*     */ 
/*     */ public class EffectUtils
/*     */ {
/*     */   public static BaseBounds transformBounds(BaseTransform paramBaseTransform, BaseBounds paramBaseBounds)
/*     */   {
/*  38 */     if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/*  39 */       return paramBaseBounds;
/*     */     }
/*  41 */     Object localObject = new RectBounds();
/*  42 */     localObject = paramBaseTransform.transform(paramBaseBounds, (BaseBounds)localObject);
/*  43 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static int getKernelSize(int paramInt1, int paramInt2)
/*     */   {
/*  48 */     if (paramInt1 < 1) paramInt1 = 1;
/*  49 */     paramInt1 = (paramInt1 - 1) * paramInt2 + 1;
/*  50 */     paramInt1 |= 1;
/*  51 */     return paramInt1 / 2;
/*     */   }
/*     */ 
/*     */   public static BaseBounds getShadowBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, float paramFloat1, float paramFloat2, BlurType paramBlurType)
/*     */   {
/*  60 */     int i = 0;
/*  61 */     int j = 0;
/*     */ 
/*  63 */     switch (1.$SwitchMap$javafx$scene$effect$BlurType[paramBlurType.ordinal()]) {
/*     */     case 1:
/*  65 */       float f1 = paramFloat1 < 1.0F ? 0.0F : (paramFloat1 - 1.0F) / 2.0F;
/*  66 */       float f2 = paramFloat2 < 1.0F ? 0.0F : (paramFloat2 - 1.0F) / 2.0F;
/*  67 */       i = (int)Math.ceil(f1);
/*  68 */       j = (int)Math.ceil(f2);
/*  69 */       break;
/*     */     case 2:
/*  71 */       i = getKernelSize(Math.round(paramFloat1 / 3.0F), 1);
/*  72 */       j = getKernelSize(Math.round(paramFloat2 / 3.0F), 1);
/*  73 */       break;
/*     */     case 3:
/*  75 */       i = getKernelSize(Math.round(paramFloat1 / 3.0F), 2);
/*  76 */       j = getKernelSize(Math.round(paramFloat2 / 3.0F), 2);
/*  77 */       break;
/*     */     case 4:
/*  79 */       i = getKernelSize(Math.round(paramFloat1 / 3.0F), 3);
/*  80 */       j = getKernelSize(Math.round(paramFloat2 / 3.0F), 3);
/*     */     }
/*     */ 
/*  84 */     paramBaseBounds = paramBaseBounds.deriveWithPadding(i, j, 0.0F);
/*     */ 
/*  86 */     return transformBounds(paramBaseTransform, paramBaseBounds);
/*     */   }
/*     */ 
/*     */   public static BaseBounds getInputBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor, Effect paramEffect)
/*     */   {
/*  96 */     if (paramEffect != null)
/*  97 */       paramBaseBounds = paramEffect.impl_getBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor);
/*     */     else {
/*  99 */       paramBaseBounds = paramBoundsAccessor.getGeomBounds(paramBaseBounds, paramBaseTransform, paramNode);
/*     */     }
/*     */ 
/* 102 */     return paramBaseBounds;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.effect.EffectUtils
 * JD-Core Version:    0.6.2
 */