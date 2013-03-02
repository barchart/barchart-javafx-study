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
/*     */ public class ColorAdjust extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty hue;
/*     */   private DoubleProperty saturation;
/*     */   private DoubleProperty brightness;
/*     */   private DoubleProperty contrast;
/*     */ 
/*     */   public ColorAdjust()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ColorAdjust(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  87 */     setBrightness(paramDouble3);
/*  88 */     setContrast(paramDouble4);
/*  89 */     setHue(paramDouble1);
/*  90 */     setSaturation(paramDouble2);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.ColorAdjust impl_createImpl()
/*     */   {
/*  95 */     return new com.sun.scenario.effect.ColorAdjust();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 108 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 112 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 116 */     if (this.input == null) {
/* 117 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 119 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 124 */     Effect localEffect = getInput();
/* 125 */     if (localEffect == null)
/* 126 */       return false;
/* 127 */     if (localEffect == paramEffect)
/* 128 */       return true;
/* 129 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setHue(double paramDouble)
/*     */   {
/* 146 */     hueProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHue() {
/* 150 */     return this.hue == null ? 0.0D : this.hue.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty hueProperty() {
/* 154 */     if (this.hue == null) {
/* 155 */       this.hue = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 159 */           ColorAdjust.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 160 */           ColorAdjust.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 165 */           return ColorAdjust.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 170 */           return "hue";
/*     */         }
/*     */       };
/*     */     }
/* 174 */     return this.hue;
/*     */   }
/*     */ 
/*     */   public final void setSaturation(double paramDouble)
/*     */   {
/* 191 */     saturationProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getSaturation() {
/* 195 */     return this.saturation == null ? 0.0D : this.saturation.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty saturationProperty() {
/* 199 */     if (this.saturation == null) {
/* 200 */       this.saturation = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 204 */           ColorAdjust.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 205 */           ColorAdjust.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 210 */           return ColorAdjust.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 215 */           return "saturation";
/*     */         }
/*     */       };
/*     */     }
/* 219 */     return this.saturation;
/*     */   }
/*     */ 
/*     */   public final void setBrightness(double paramDouble)
/*     */   {
/* 236 */     brightnessProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getBrightness() {
/* 240 */     return this.brightness == null ? 0.0D : this.brightness.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty brightnessProperty() {
/* 244 */     if (this.brightness == null) {
/* 245 */       this.brightness = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 249 */           ColorAdjust.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 250 */           ColorAdjust.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 255 */           return ColorAdjust.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 260 */           return "brightness";
/*     */         }
/*     */       };
/*     */     }
/* 264 */     return this.brightness;
/*     */   }
/*     */ 
/*     */   public final void setContrast(double paramDouble)
/*     */   {
/* 281 */     contrastProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getContrast() {
/* 285 */     return this.contrast == null ? 0.0D : this.contrast.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty contrastProperty() {
/* 289 */     if (this.contrast == null) {
/* 290 */       this.contrast = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 294 */           ColorAdjust.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 295 */           ColorAdjust.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 300 */           return ColorAdjust.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 305 */           return "contrast";
/*     */         }
/*     */       };
/*     */     }
/* 309 */     return this.contrast;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 314 */     Effect localEffect = getInput();
/* 315 */     if (localEffect != null) {
/* 316 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 319 */     com.sun.scenario.effect.ColorAdjust localColorAdjust = (com.sun.scenario.effect.ColorAdjust)impl_getImpl();
/*     */ 
/* 321 */     localColorAdjust.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 322 */     localColorAdjust.setHue((float)Utils.clamp(-1.0D, getHue(), 1.0D));
/* 323 */     localColorAdjust.setSaturation((float)Utils.clamp(-1.0D, getSaturation(), 1.0D));
/* 324 */     localColorAdjust.setBrightness((float)Utils.clamp(-1.0D, getBrightness(), 1.0D));
/* 325 */     localColorAdjust.setContrast((float)Utils.clamp(-1.0D, getContrast(), 1.0D));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 338 */     return EffectUtils.getInputBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor, getInput());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 351 */     ColorAdjust localColorAdjust = new ColorAdjust(getHue(), getSaturation(), getBrightness(), getContrast());
/*     */ 
/* 353 */     localColorAdjust.setInput(localColorAdjust.getInput());
/* 354 */     return localColorAdjust;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ColorAdjust
 * JD-Core Version:    0.6.2
 */