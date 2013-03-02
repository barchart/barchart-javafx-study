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
/*     */ public class Bloom extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty threshold;
/*     */ 
/*     */   public Bloom()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Bloom(double paramDouble)
/*     */   {
/*  83 */     setThreshold(paramDouble);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.Bloom impl_createImpl()
/*     */   {
/*  88 */     return new com.sun.scenario.effect.Bloom();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 100 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 104 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 108 */     if (this.input == null) {
/* 109 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 111 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 116 */     Effect localEffect = getInput();
/* 117 */     if (localEffect == null)
/* 118 */       return false;
/* 119 */     if (localEffect == paramEffect)
/* 120 */       return true;
/* 121 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setThreshold(double paramDouble)
/*     */   {
/* 139 */     thresholdProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getThreshold() {
/* 143 */     return this.threshold == null ? 0.3D : this.threshold.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty thresholdProperty() {
/* 147 */     if (this.threshold == null) {
/* 148 */       this.threshold = new DoublePropertyBase(0.3D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 152 */           Bloom.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 157 */           return Bloom.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 162 */           return "threshold";
/*     */         }
/*     */       };
/*     */     }
/* 166 */     return this.threshold;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 171 */     Effect localEffect = getInput();
/* 172 */     if (localEffect != null) {
/* 173 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 176 */     com.sun.scenario.effect.Bloom localBloom = (com.sun.scenario.effect.Bloom)impl_getImpl();
/*     */ 
/* 178 */     localBloom.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 179 */     localBloom.setThreshold((float)Utils.clamp(0.0D, getThreshold(), 1.0D));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 192 */     return EffectUtils.getInputBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor, getInput());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 205 */     Bloom localBloom = new Bloom(getThreshold());
/* 206 */     localBloom.setInput(getInput());
/* 207 */     return localBloom;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Bloom
 * JD-Core Version:    0.6.2
 */