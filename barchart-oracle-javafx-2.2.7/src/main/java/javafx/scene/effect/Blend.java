/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.scenario.effect.Blend.Mode;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Blend extends Effect
/*     */ {
/*     */   private ObjectProperty<BlendMode> mode;
/*     */   private DoubleProperty opacity;
/*     */   private ObjectProperty<Effect> bottomInput;
/*     */   private ObjectProperty<Effect> topInput;
/*     */ 
/*     */   private static Blend.Mode toPGMode(BlendMode paramBlendMode)
/*     */   {
/*  87 */     if (paramBlendMode == null)
/*  88 */       return Blend.Mode.SRC_OVER;
/*  89 */     if (paramBlendMode == BlendMode.SRC_OVER)
/*  90 */       return Blend.Mode.SRC_OVER;
/*  91 */     if (paramBlendMode == BlendMode.SRC_ATOP)
/*  92 */       return Blend.Mode.SRC_ATOP;
/*  93 */     if (paramBlendMode == BlendMode.ADD)
/*  94 */       return Blend.Mode.ADD;
/*  95 */     if (paramBlendMode == BlendMode.MULTIPLY)
/*  96 */       return Blend.Mode.MULTIPLY;
/*  97 */     if (paramBlendMode == BlendMode.SCREEN)
/*  98 */       return Blend.Mode.SCREEN;
/*  99 */     if (paramBlendMode == BlendMode.OVERLAY)
/* 100 */       return Blend.Mode.OVERLAY;
/* 101 */     if (paramBlendMode == BlendMode.DARKEN)
/* 102 */       return Blend.Mode.DARKEN;
/* 103 */     if (paramBlendMode == BlendMode.LIGHTEN)
/* 104 */       return Blend.Mode.LIGHTEN;
/* 105 */     if (paramBlendMode == BlendMode.COLOR_DODGE)
/* 106 */       return Blend.Mode.COLOR_DODGE;
/* 107 */     if (paramBlendMode == BlendMode.COLOR_BURN)
/* 108 */       return Blend.Mode.COLOR_BURN;
/* 109 */     if (paramBlendMode == BlendMode.HARD_LIGHT)
/* 110 */       return Blend.Mode.HARD_LIGHT;
/* 111 */     if (paramBlendMode == BlendMode.SOFT_LIGHT)
/* 112 */       return Blend.Mode.SOFT_LIGHT;
/* 113 */     if (paramBlendMode == BlendMode.DIFFERENCE)
/* 114 */       return Blend.Mode.DIFFERENCE;
/* 115 */     if (paramBlendMode == BlendMode.EXCLUSION)
/* 116 */       return Blend.Mode.EXCLUSION;
/* 117 */     if (paramBlendMode == BlendMode.RED)
/* 118 */       return Blend.Mode.RED;
/* 119 */     if (paramBlendMode == BlendMode.GREEN)
/* 120 */       return Blend.Mode.GREEN;
/* 121 */     if (paramBlendMode == BlendMode.BLUE) {
/* 122 */       return Blend.Mode.BLUE;
/*     */     }
/* 124 */     throw new AssertionError("Unrecognized blend mode: {mode}");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static Blend.Mode impl_getToolkitMode(BlendMode paramBlendMode)
/*     */   {
/* 135 */     return toPGMode(paramBlendMode);
/*     */   }
/*     */ 
/*     */   public Blend()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Blend(BlendMode paramBlendMode)
/*     */   {
/* 148 */     setMode(paramBlendMode);
/*     */   }
/*     */ 
/*     */   public Blend(BlendMode paramBlendMode, Effect paramEffect1, Effect paramEffect2)
/*     */   {
/* 159 */     setMode(paramBlendMode);
/* 160 */     setBottomInput(paramEffect1);
/* 161 */     setTopInput(paramEffect2);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.Blend impl_createImpl()
/*     */   {
/* 166 */     return new com.sun.scenario.effect.Blend(toPGMode(BlendMode.SRC_OVER), com.sun.scenario.effect.Effect.DefaultInput, com.sun.scenario.effect.Effect.DefaultInput);
/*     */   }
/*     */ 
/*     */   public final void setMode(BlendMode paramBlendMode)
/*     */   {
/* 186 */     modeProperty().set(paramBlendMode);
/*     */   }
/*     */ 
/*     */   public final BlendMode getMode() {
/* 190 */     return this.mode == null ? BlendMode.SRC_OVER : (BlendMode)this.mode.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<BlendMode> modeProperty() {
/* 194 */     if (this.mode == null) {
/* 195 */       this.mode = new ObjectPropertyBase(BlendMode.SRC_OVER)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 199 */           Blend.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 204 */           return Blend.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 209 */           return "mode";
/*     */         }
/*     */       };
/*     */     }
/* 213 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public final void setOpacity(double paramDouble)
/*     */   {
/* 231 */     opacityProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOpacity() {
/* 235 */     return this.opacity == null ? 1.0D : this.opacity.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty opacityProperty() {
/* 239 */     if (this.opacity == null) {
/* 240 */       this.opacity = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 244 */           Blend.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 249 */           return Blend.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 254 */           return "opacity";
/*     */         }
/*     */       };
/*     */     }
/* 258 */     return this.opacity;
/*     */   }
/*     */ 
/*     */   public final void setBottomInput(Effect paramEffect)
/*     */   {
/* 272 */     bottomInputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getBottomInput() {
/* 276 */     return this.bottomInput == null ? null : (Effect)this.bottomInput.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> bottomInputProperty() {
/* 280 */     if (this.bottomInput == null) {
/* 281 */       this.bottomInput = new Effect.EffectInputProperty(this, "bottomInput");
/*     */     }
/* 283 */     return this.bottomInput;
/*     */   }
/*     */ 
/*     */   public final void setTopInput(Effect paramEffect)
/*     */   {
/* 297 */     topInputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getTopInput() {
/* 301 */     return this.topInput == null ? null : (Effect)this.topInput.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> topInputProperty() {
/* 305 */     if (this.topInput == null) {
/* 306 */       this.topInput = new Effect.EffectInputProperty(this, "topInput");
/*     */     }
/* 308 */     return this.topInput;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 313 */     Effect localEffect1 = getTopInput();
/* 314 */     Effect localEffect2 = getBottomInput();
/* 315 */     if ((localEffect1 == paramEffect) || (localEffect2 == paramEffect))
/* 316 */       return true;
/* 317 */     if ((localEffect1 != null) && (localEffect1.impl_checkChainContains(paramEffect)))
/* 318 */       return true;
/* 319 */     if ((localEffect2 != null) && (localEffect2.impl_checkChainContains(paramEffect))) {
/* 320 */       return true;
/*     */     }
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 327 */     Effect localEffect1 = getBottomInput();
/* 328 */     Effect localEffect2 = getTopInput();
/*     */ 
/* 330 */     if (localEffect2 != null) {
/* 331 */       localEffect2.impl_sync();
/*     */     }
/* 333 */     if (localEffect1 != null) {
/* 334 */       localEffect1.impl_sync();
/*     */     }
/*     */ 
/* 337 */     com.sun.scenario.effect.Blend localBlend = (com.sun.scenario.effect.Blend)impl_getImpl();
/*     */ 
/* 339 */     localBlend.setTopInput(localEffect2 == null ? null : localEffect2.impl_getImpl());
/* 340 */     localBlend.setBottomInput(localEffect1 == null ? null : localEffect1.impl_getImpl());
/* 341 */     localBlend.setOpacity((float)Utils.clamp(0.0D, getOpacity(), 1.0D));
/* 342 */     localBlend.setMode(toPGMode(getMode()));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 355 */     Object localObject1 = new RectBounds();
/* 356 */     Object localObject2 = new RectBounds();
/* 357 */     localObject2 = EffectUtils.getInputBounds((BaseBounds)localObject2, paramBaseTransform, paramNode, paramBoundsAccessor, getBottomInput());
/*     */ 
/* 360 */     localObject1 = EffectUtils.getInputBounds((BaseBounds)localObject1, paramBaseTransform, paramNode, paramBoundsAccessor, getTopInput());
/*     */ 
/* 363 */     BaseBounds localBaseBounds = ((BaseBounds)localObject1).deriveWithUnion((BaseBounds)localObject2);
/* 364 */     return localBaseBounds;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 375 */     return new Blend(getMode(), getBottomInput(), getTopInput());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Blend
 * JD-Core Version:    0.6.2
 */