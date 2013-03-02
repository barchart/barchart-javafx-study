/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class Effect
/*     */ {
/*     */   private com.sun.scenario.effect.Effect peer;
/*  87 */   private IntegerProperty effectDirty = new SimpleIntegerProperty(this, "effectDirty");
/*     */ 
/*     */   protected Effect()
/*     */   {
/*  64 */     markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */   }
/*     */ 
/*     */   void effectBoundsChanged() {
/*  68 */     toggleDirty(EffectDirtyBits.BOUNDS_CHANGED);
/*     */   }
/*     */ 
/*     */   abstract com.sun.scenario.effect.Effect impl_createImpl();
/*     */ 
/*     */   @Deprecated
/*     */   public com.sun.scenario.effect.Effect impl_getImpl()
/*     */   {
/*  80 */     if (this.peer == null) {
/*  81 */       this.peer = impl_createImpl();
/*     */     }
/*  83 */     return this.peer;
/*     */   }
/*     */ 
/*     */   private void setEffectDirty(int paramInt)
/*     */   {
/*  91 */     impl_effectDirtyProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final IntegerProperty impl_effectDirtyProperty()
/*     */   {
/* 100 */     return this.effectDirty;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean impl_isEffectDirty()
/*     */   {
/* 109 */     return isEffectDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */   }
/*     */ 
/*     */   final void markDirty(EffectDirtyBits paramEffectDirtyBits)
/*     */   {
/* 116 */     setEffectDirty(this.effectDirty.get() | paramEffectDirtyBits.getMask());
/*     */   }
/*     */ 
/*     */   private void toggleDirty(EffectDirtyBits paramEffectDirtyBits)
/*     */   {
/* 123 */     setEffectDirty(this.effectDirty.get() ^ paramEffectDirtyBits.getMask());
/*     */   }
/*     */ 
/*     */   private boolean isEffectDirty(EffectDirtyBits paramEffectDirtyBits)
/*     */   {
/* 130 */     return (this.effectDirty.get() & paramEffectDirtyBits.getMask()) != 0;
/*     */   }
/*     */ 
/*     */   private void clearEffectDirty(EffectDirtyBits paramEffectDirtyBits)
/*     */   {
/* 137 */     setEffectDirty(this.effectDirty.get() & (paramEffectDirtyBits.getMask() ^ 0xFFFFFFFF));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final void impl_sync()
/*     */   {
/* 146 */     if (isEffectDirty(EffectDirtyBits.EFFECT_DIRTY)) {
/* 147 */       impl_update();
/* 148 */       clearEffectDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */     }
/*     */   }
/*     */ 
/*     */   abstract void impl_update();
/*     */ 
/*     */   abstract boolean impl_checkChainContains(Effect paramEffect);
/*     */ 
/*     */   boolean impl_containsCycles(Effect paramEffect) {
/* 157 */     if ((paramEffect != null) && ((paramEffect == this) || (paramEffect.impl_checkChainContains(this))))
/*     */     {
/* 159 */       return true;
/*     */     }
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public abstract BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor);
/*     */ 
/*     */   @Deprecated
/*     */   public abstract Effect impl_copy();
/*     */ 
/*     */   class EffectInputProperty extends ObjectPropertyBase<Effect>
/*     */   {
/*     */     private final String propertyName;
/* 192 */     private Effect validInput = null;
/*     */ 
/* 194 */     private final Effect.EffectInputChangeListener effectChangeListener = new Effect.EffectInputChangeListener(Effect.this);
/*     */ 
/*     */     public EffectInputProperty(String arg2)
/*     */     {
/*     */       Object localObject;
/* 198 */       this.propertyName = localObject;
/*     */     }
/*     */ 
/*     */     public void invalidated()
/*     */     {
/* 203 */       Effect localEffect = (Effect)super.get();
/* 204 */       if (Effect.this.impl_containsCycles(localEffect)) {
/* 205 */         if (isBound()) {
/* 206 */           unbind();
/* 207 */           set(this.validInput);
/* 208 */           throw new IllegalArgumentException("Cycle in effect chain detected, binding was set to incorrect value, unbinding the input property");
/*     */         }
/*     */ 
/* 212 */         set(this.validInput);
/* 213 */         throw new IllegalArgumentException("Cycle in effect chain detected");
/*     */       }
/*     */ 
/* 216 */       this.validInput = localEffect;
/* 217 */       this.effectChangeListener.register(localEffect);
/* 218 */       Effect.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */ 
/* 225 */       Effect.this.effectBoundsChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 230 */       return Effect.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 235 */       return this.propertyName;
/*     */     }
/*     */   }
/*     */ 
/*     */   class EffectInputChangeListener extends EffectChangeListener
/*     */   {
/*     */     private int oldBits;
/*     */ 
/*     */     EffectInputChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void register(Effect paramEffect)
/*     */     {
/* 168 */       super.register(paramEffect == null ? null : paramEffect.impl_effectDirtyProperty());
/* 169 */       if (paramEffect != null)
/* 170 */         this.oldBits = paramEffect.impl_effectDirtyProperty().get();
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 176 */       int i = ((IntegerProperty)paramObservable).get();
/* 177 */       int j = i ^ this.oldBits;
/* 178 */       this.oldBits = i;
/* 179 */       if ((EffectDirtyBits.isSet(j, EffectDirtyBits.EFFECT_DIRTY)) && (EffectDirtyBits.isSet(i, EffectDirtyBits.EFFECT_DIRTY)))
/*     */       {
/* 181 */         Effect.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */       }
/* 183 */       if (EffectDirtyBits.isSet(j, EffectDirtyBits.BOUNDS_CHANGED))
/* 184 */         Effect.this.toggleDirty(EffectDirtyBits.BOUNDS_CHANGED);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Effect
 * JD-Core Version:    0.6.2
 */