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
/*     */ public class GaussianBlur extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty radius;
/*     */ 
/*     */   public GaussianBlur()
/*     */   {
/*     */   }
/*     */ 
/*     */   public GaussianBlur(double paramDouble)
/*     */   {
/*  75 */     setRadius(paramDouble);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.GaussianBlur impl_createImpl()
/*     */   {
/*  80 */     return new com.sun.scenario.effect.GaussianBlur();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/*  93 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/*  97 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 101 */     if (this.input == null) {
/* 102 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 104 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 109 */     Effect localEffect = getInput();
/* 110 */     if (localEffect == null)
/* 111 */       return false;
/* 112 */     if (localEffect == paramEffect)
/* 113 */       return true;
/* 114 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 131 */     radiusProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 135 */     return this.radius == null ? 10.0D : this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 139 */     if (this.radius == null) {
/* 140 */       this.radius = new DoublePropertyBase(10.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 144 */           GaussianBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 145 */           GaussianBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 150 */           return GaussianBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 155 */           return "radius";
/*     */         }
/*     */       };
/*     */     }
/* 159 */     return this.radius;
/*     */   }
/*     */ 
/*     */   private float getClampedRadius() {
/* 163 */     return (float)Utils.clamp(0.0D, getRadius(), 63.0D);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 168 */     Effect localEffect = getInput();
/* 169 */     if (localEffect != null) {
/* 170 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 173 */     com.sun.scenario.effect.GaussianBlur localGaussianBlur = (com.sun.scenario.effect.GaussianBlur)impl_getImpl();
/*     */ 
/* 175 */     localGaussianBlur.setRadius(getClampedRadius());
/* 176 */     localGaussianBlur.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 189 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 193 */     float f = getClampedRadius();
/* 194 */     paramBaseBounds = paramBaseBounds.deriveWithPadding(f, f, 0.0F);
/* 195 */     return EffectUtils.transformBounds(paramBaseTransform, paramBaseBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 206 */     return new GaussianBlur(getRadius());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.GaussianBlur
 * JD-Core Version:    0.6.2
 */