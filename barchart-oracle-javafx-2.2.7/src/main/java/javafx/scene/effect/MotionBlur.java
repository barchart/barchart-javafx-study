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
/*     */ public class MotionBlur extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty radius;
/*     */   private DoubleProperty angle;
/*     */ 
/*     */   public MotionBlur()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MotionBlur(double paramDouble1, double paramDouble2)
/*     */   {
/*  79 */     setAngle(paramDouble1);
/*  80 */     setRadius(paramDouble2);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.MotionBlur impl_createImpl()
/*     */   {
/*  85 */     return new com.sun.scenario.effect.MotionBlur();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/*  98 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 102 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 106 */     if (this.input == null) {
/* 107 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 109 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 114 */     Effect localEffect = getInput();
/* 115 */     if (localEffect == null)
/* 116 */       return false;
/* 117 */     if (localEffect == paramEffect)
/* 118 */       return true;
/* 119 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 136 */     radiusProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 140 */     return this.radius == null ? 10.0D : this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 144 */     if (this.radius == null) {
/* 145 */       this.radius = new DoublePropertyBase(10.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 149 */           MotionBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 150 */           MotionBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 155 */           return MotionBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 160 */           return "radius";
/*     */         }
/*     */       };
/*     */     }
/* 164 */     return this.radius;
/*     */   }
/*     */ 
/*     */   public final void setAngle(double paramDouble)
/*     */   {
/* 181 */     angleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getAngle() {
/* 185 */     return this.angle == null ? 0.0D : this.angle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty angleProperty() {
/* 189 */     if (this.angle == null) {
/* 190 */       this.angle = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 194 */           MotionBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 195 */           MotionBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 200 */           return MotionBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 205 */           return "angle";
/*     */         }
/*     */       };
/*     */     }
/* 209 */     return this.angle;
/*     */   }
/*     */ 
/*     */   private float getClampedRadius() {
/* 213 */     return (float)Utils.clamp(0.0D, getRadius(), 63.0D);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 218 */     Effect localEffect = getInput();
/* 219 */     if (localEffect != null) {
/* 220 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 223 */     com.sun.scenario.effect.MotionBlur localMotionBlur = (com.sun.scenario.effect.MotionBlur)impl_getImpl();
/*     */ 
/* 225 */     localMotionBlur.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 226 */     localMotionBlur.setRadius(getClampedRadius());
/* 227 */     localMotionBlur.setAngle((float)Math.toRadians(getAngle()));
/*     */   }
/*     */ 
/*     */   private int getHPad() {
/* 231 */     return (int)Math.ceil(Math.abs(Math.cos(Math.toRadians(getAngle()))) * getClampedRadius());
/*     */   }
/*     */ 
/*     */   private int getVPad()
/*     */   {
/* 236 */     return (int)Math.ceil(Math.abs(Math.sin(Math.toRadians(getAngle()))) * getClampedRadius());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 250 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 255 */     int i = getHPad();
/* 256 */     int j = getVPad();
/* 257 */     paramBaseBounds = paramBaseBounds.deriveWithPadding(i, j, 0.0F);
/*     */ 
/* 259 */     return EffectUtils.transformBounds(paramBaseTransform, paramBaseBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 270 */     MotionBlur localMotionBlur = new MotionBlur(getAngle(), getRadius());
/* 271 */     localMotionBlur.setInput(localMotionBlur.getInput());
/* 272 */     return localMotionBlur;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.MotionBlur
 * JD-Core Version:    0.6.2
 */