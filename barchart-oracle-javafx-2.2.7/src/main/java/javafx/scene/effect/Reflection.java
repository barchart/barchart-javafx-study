/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Reflection extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty topOffset;
/*     */   private DoubleProperty topOpacity;
/*     */   private DoubleProperty bottomOpacity;
/*     */   private DoubleProperty fraction;
/*     */ 
/*     */   public Reflection()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Reflection(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  85 */     setBottomOpacity(paramDouble4);
/*  86 */     setTopOffset(paramDouble1);
/*  87 */     setTopOpacity(paramDouble3);
/*  88 */     setFraction(paramDouble2);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.Reflection impl_createImpl()
/*     */   {
/*  93 */     return new com.sun.scenario.effect.Reflection();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 106 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 110 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 114 */     if (this.input == null) {
/* 115 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 117 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 122 */     Effect localEffect = getInput();
/* 123 */     if (localEffect == null)
/* 124 */       return false;
/* 125 */     if (localEffect == paramEffect)
/* 126 */       return true;
/* 127 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setTopOffset(double paramDouble)
/*     */   {
/* 145 */     topOffsetProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getTopOffset() {
/* 149 */     return this.topOffset == null ? 0.0D : this.topOffset.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty topOffsetProperty() {
/* 153 */     if (this.topOffset == null) {
/* 154 */       this.topOffset = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 158 */           Reflection.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 159 */           Reflection.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 164 */           return Reflection.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 169 */           return "topOffset";
/*     */         }
/*     */       };
/*     */     }
/* 173 */     return this.topOffset;
/*     */   }
/*     */ 
/*     */   public final void setTopOpacity(double paramDouble)
/*     */   {
/* 191 */     topOpacityProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getTopOpacity() {
/* 195 */     return this.topOpacity == null ? 0.5D : this.topOpacity.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty topOpacityProperty() {
/* 199 */     if (this.topOpacity == null) {
/* 200 */       this.topOpacity = new DoublePropertyBase(0.5D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 204 */           Reflection.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 209 */           return Reflection.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 214 */           return "topOpacity";
/*     */         }
/*     */       };
/*     */     }
/* 218 */     return this.topOpacity;
/*     */   }
/*     */ 
/*     */   public final void setBottomOpacity(double paramDouble)
/*     */   {
/* 236 */     bottomOpacityProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getBottomOpacity() {
/* 240 */     return this.bottomOpacity == null ? 0.0D : this.bottomOpacity.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty bottomOpacityProperty() {
/* 244 */     if (this.bottomOpacity == null) {
/* 245 */       this.bottomOpacity = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 249 */           Reflection.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 254 */           return Reflection.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 259 */           return "bottomOpacity";
/*     */         }
/*     */       };
/*     */     }
/* 263 */     return this.bottomOpacity;
/*     */   }
/*     */ 
/*     */   public final void setFraction(double paramDouble)
/*     */   {
/* 282 */     fractionProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFraction() {
/* 286 */     return this.fraction == null ? 0.75D : this.fraction.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fractionProperty() {
/* 290 */     if (this.fraction == null) {
/* 291 */       this.fraction = new DoublePropertyBase(0.75D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 295 */           Reflection.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 296 */           Reflection.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 301 */           return Reflection.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 306 */           return "fraction";
/*     */         }
/*     */       };
/*     */     }
/* 310 */     return this.fraction;
/*     */   }
/*     */ 
/*     */   private float getClampedFraction() {
/* 314 */     return (float)Utils.clamp(0.0D, getFraction(), 1.0D);
/*     */   }
/*     */ 
/*     */   private float getClampedBottomOpacity() {
/* 318 */     return (float)Utils.clamp(0.0D, getBottomOpacity(), 1.0D);
/*     */   }
/*     */ 
/*     */   private float getClampedTopOpacity() {
/* 322 */     return (float)Utils.clamp(0.0D, getTopOpacity(), 1.0D);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 327 */     Effect localEffect = getInput();
/* 328 */     if (localEffect != null) {
/* 329 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 332 */     com.sun.scenario.effect.Reflection localReflection = (com.sun.scenario.effect.Reflection)impl_getImpl();
/*     */ 
/* 334 */     localReflection.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 335 */     localReflection.setFraction(getClampedFraction());
/* 336 */     localReflection.setTopOffset((float)getTopOffset());
/* 337 */     localReflection.setBottomOpacity(getClampedBottomOpacity());
/* 338 */     localReflection.setTopOpacity(getClampedTopOpacity());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 351 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 355 */     paramBaseBounds.roundOut();
/*     */ 
/* 357 */     float f1 = paramBaseBounds.getMinX();
/* 358 */     float f2 = paramBaseBounds.getMaxY() + (float)getTopOffset();
/* 359 */     float f3 = paramBaseBounds.getMinZ();
/* 360 */     float f4 = paramBaseBounds.getMaxX();
/* 361 */     float f5 = f2 + getClampedFraction() * paramBaseBounds.getHeight();
/* 362 */     float f6 = paramBaseBounds.getMaxZ();
/*     */ 
/* 364 */     BaseBounds localBaseBounds = BaseBounds.getInstance(f1, f2, f3, f4, f5, f6);
/* 365 */     localBaseBounds = localBaseBounds.deriveWithUnion(paramBaseBounds);
/*     */ 
/* 367 */     return EffectUtils.transformBounds(paramBaseTransform, localBaseBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 378 */     Reflection localReflection = new Reflection(getTopOffset(), getFraction(), getTopOpacity(), getBottomOpacity());
/*     */ 
/* 380 */     localReflection.setInput(localReflection.getInput());
/* 381 */     return localReflection;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Reflection
 * JD-Core Version:    0.6.2
 */