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
/*     */ public class Glow extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty level;
/*     */ 
/*     */   public Glow()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Glow(double paramDouble)
/*     */   {
/*  77 */     setLevel(paramDouble);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.Glow impl_createImpl()
/*     */   {
/*  82 */     return new com.sun.scenario.effect.Glow();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/*  95 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/*  99 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 103 */     if (this.input == null) {
/* 104 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 106 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 111 */     Effect localEffect = getInput();
/* 112 */     if (localEffect == null)
/* 113 */       return false;
/* 114 */     if (localEffect == paramEffect)
/* 115 */       return true;
/* 116 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setLevel(double paramDouble)
/*     */   {
/* 133 */     levelProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLevel() {
/* 137 */     return this.level == null ? 0.3D : this.level.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty levelProperty() {
/* 141 */     if (this.level == null) {
/* 142 */       this.level = new DoublePropertyBase(0.3D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 146 */           Glow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 151 */           return Glow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 156 */           return "level";
/*     */         }
/*     */       };
/*     */     }
/* 160 */     return this.level;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 165 */     Effect localEffect = getInput();
/* 166 */     if (localEffect != null) {
/* 167 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 170 */     com.sun.scenario.effect.Glow localGlow = (com.sun.scenario.effect.Glow)impl_getImpl();
/*     */ 
/* 172 */     localGlow.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 173 */     localGlow.setLevel((float)Utils.clamp(0.0D, getLevel(), 1.0D));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 186 */     return EffectUtils.getInputBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor, getInput());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 199 */     return new Glow(getLevel());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Glow
 * JD-Core Version:    0.6.2
 */