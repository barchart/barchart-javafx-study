/*     */ package com.sun.scenario.effect.impl.state;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class BoxShadowState extends BoxBlurState
/*     */ {
/*     */   private Color4f shadowColor;
/*     */   private float spread;
/*     */ 
/*     */   public Color4f getShadowColor()
/*     */   {
/*  44 */     return this.shadowColor;
/*     */   }
/*     */ 
/*     */   public void setShadowColor(Color4f paramColor4f) {
/*  48 */     if (paramColor4f == null) {
/*  49 */       throw new IllegalArgumentException("Color must be non-null");
/*     */     }
/*  51 */     this.shadowColor = paramColor4f;
/*     */   }
/*     */ 
/*     */   public float getSpread()
/*     */   {
/*  56 */     return this.spread;
/*     */   }
/*     */ 
/*     */   public void setSpread(float paramFloat) {
/*  60 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/*  61 */       throw new IllegalArgumentException("Spread must be in the range [0,1]");
/*     */     }
/*  63 */     this.spread = paramFloat;
/*     */   }
/*     */ 
/*     */   public boolean isNop()
/*     */   {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNop(int paramInt)
/*     */   {
/*  77 */     return (paramInt == 0) && (super.isNop(paramInt));
/*     */   }
/*     */ 
/*     */   public boolean isShadow()
/*     */   {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   public float[] getShadowColorComponents(int paramInt)
/*     */   {
/*  87 */     return paramInt == 0 ? BLACK_COMPONENTS : this.shadowColor.getPremultipliedRGBComponents();
/*     */   }
/*     */ 
/*     */   public EffectPeer getPeer(Renderer paramRenderer, FilterContext paramFilterContext, int paramInt)
/*     */   {
/*  94 */     int i = getScaledKernelSize(paramInt);
/*  95 */     if ((paramInt == 0) && (i <= 1))
/*     */     {
/* 102 */       return null;
/*     */     }
/* 104 */     int j = getPeerSize(i);
/* 105 */     Effect.AccelType localAccelType = paramRenderer.getAccelType();
/*     */ 
/* 107 */     switch (1.$SwitchMap$com$sun$scenario$effect$Effect$AccelType[localAccelType.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/* 110 */       if (this.spread == 0.0F)
/* 111 */         str = "BoxShadow";
/* 112 */       break;
/*     */     }
/*     */ 
/* 116 */     String str = "LinearConvolveShadow";
/*     */ 
/* 119 */     EffectPeer localEffectPeer = paramRenderer.getPeerInstance(paramFilterContext, str, j);
/* 120 */     return localEffectPeer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.state.BoxShadowState
 * JD-Core Version:    0.6.2
 */