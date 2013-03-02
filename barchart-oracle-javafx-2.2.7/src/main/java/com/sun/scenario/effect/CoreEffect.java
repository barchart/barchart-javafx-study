/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ abstract class CoreEffect extends FilterEffect
/*     */ {
/*     */   private String peerKey;
/*  40 */   private int peerCount = -1;
/*     */ 
/*     */   CoreEffect()
/*     */   {
/*     */   }
/*     */ 
/*     */   CoreEffect(Effect paramEffect) {
/*  47 */     super(paramEffect);
/*     */   }
/*     */ 
/*     */   CoreEffect(Effect paramEffect1, Effect paramEffect2) {
/*  51 */     super(paramEffect1, paramEffect2);
/*     */   }
/*     */ 
/*     */   final void updatePeerKey(String paramString) {
/*  55 */     updatePeerKey(paramString, -1);
/*     */   }
/*     */ 
/*     */   final void updatePeerKey(String paramString, int paramInt) {
/*  59 */     this.peerKey = paramString;
/*  60 */     this.peerCount = paramInt;
/*     */   }
/*     */ 
/*     */   private EffectPeer getPeer(FilterContext paramFilterContext, int paramInt1, int paramInt2) {
/*  64 */     return Renderer.getRenderer(paramFilterContext, this, paramInt1, paramInt2).getPeerInstance(paramFilterContext, this.peerKey, this.peerCount);
/*     */   }
/*     */ 
/*     */   final EffectPeer getPeer(FilterContext paramFilterContext, ImageData[] paramArrayOfImageData)
/*     */   {
/*     */     int i;
/*     */     int j;
/*  81 */     if (paramArrayOfImageData.length > 0) {
/*  82 */       Rectangle localRectangle = paramArrayOfImageData[0].getUntransformedBounds();
/*  83 */       i = localRectangle.width;
/*  84 */       j = localRectangle.height;
/*     */     }
/*     */     else
/*     */     {
/*  88 */       i = j = 500;
/*     */     }
/*  90 */     return getPeer(paramFilterContext, i, j);
/*     */   }
/*     */ 
/*     */   public ImageData filterImageDatas(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 103 */     return getPeer(paramFilterContext, paramArrayOfImageData).filter(this, paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */   }
/*     */ 
/*     */   public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */   {
/* 111 */     EffectPeer localEffectPeer = getPeer(paramFilterContext, 1024, 1024);
/* 112 */     return localEffectPeer.getAccelType();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.CoreEffect
 * JD-Core Version:    0.6.2
 */