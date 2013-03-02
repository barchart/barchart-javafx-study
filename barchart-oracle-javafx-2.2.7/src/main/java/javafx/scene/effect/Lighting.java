/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.scenario.effect.PhongLighting;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class Lighting extends Effect
/*     */ {
/* 102 */   private ObjectProperty<Light> light = new ObjectPropertyBase(new Light.Distant())
/*     */   {
/*     */     public void invalidated() {
/* 105 */       Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 106 */       Lighting.this.effectBoundsChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 111 */       return Lighting.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 116 */       return "light";
/*     */     }
/* 102 */   };
/*     */ 
/* 133 */   private final LightChangeListener lightChangeListener = new LightChangeListener(null);
/*     */   private ObjectProperty<Effect> bumpInput;
/*     */   private ObjectProperty<Effect> contentInput;
/*     */   private DoubleProperty diffuseConstant;
/*     */   private DoubleProperty specularConstant;
/*     */   private DoubleProperty specularExponent;
/*     */   private DoubleProperty surfaceScale;
/*     */ 
/*     */   PhongLighting impl_createImpl()
/*     */   {
/*  76 */     return new PhongLighting(getLight().impl_getImpl());
/*     */   }
/*     */ 
/*     */   public Lighting()
/*     */   {
/*  83 */     Shadow localShadow = new Shadow();
/*  84 */     localShadow.setRadius(10.0D);
/*  85 */     setBumpInput(localShadow);
/*     */   }
/*     */ 
/*     */   public Lighting(Light paramLight)
/*     */   {
/*  93 */     Shadow localShadow = new Shadow();
/*  94 */     localShadow.setRadius(10.0D);
/*  95 */     setBumpInput(localShadow);
/*  96 */     setLight(paramLight);
/*     */   }
/*     */ 
/*     */   public final void setLight(Light paramLight)
/*     */   {
/* 122 */     lightProperty().set(paramLight);
/*     */   }
/*     */ 
/*     */   public final Light getLight() {
/* 126 */     return (Light)this.light.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Light> lightProperty() {
/* 130 */     return this.light;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 143 */     Lighting localLighting = new Lighting(getLight());
/* 144 */     localLighting.setBumpInput(getBumpInput());
/* 145 */     localLighting.setContentInput(getContentInput());
/* 146 */     localLighting.setDiffuseConstant(getDiffuseConstant());
/* 147 */     localLighting.setSpecularConstant(getSpecularConstant());
/* 148 */     localLighting.setSpecularExponent(getSpecularExponent());
/* 149 */     localLighting.setSurfaceScale(getSurfaceScale());
/* 150 */     return localLighting;
/*     */   }
/*     */ 
/*     */   public final void setBumpInput(Effect paramEffect)
/*     */   {
/* 181 */     bumpInputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getBumpInput() {
/* 185 */     return this.bumpInput == null ? null : (Effect)this.bumpInput.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> bumpInputProperty() {
/* 189 */     if (this.bumpInput == null) {
/* 190 */       this.bumpInput = new Effect.EffectInputProperty(this, "bumpInput");
/*     */     }
/* 192 */     return this.bumpInput;
/*     */   }
/*     */ 
/*     */   public final void setContentInput(Effect paramEffect)
/*     */   {
/* 206 */     contentInputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getContentInput() {
/* 210 */     return this.contentInput == null ? null : (Effect)this.contentInput.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> contentInputProperty() {
/* 214 */     if (this.contentInput == null) {
/* 215 */       this.contentInput = new Effect.EffectInputProperty(this, "contentInput");
/*     */     }
/* 217 */     return this.contentInput;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 222 */     Effect localEffect1 = getBumpInput();
/* 223 */     Effect localEffect2 = getContentInput();
/* 224 */     if ((localEffect2 == paramEffect) || (localEffect1 == paramEffect))
/* 225 */       return true;
/* 226 */     if ((localEffect2 != null) && (localEffect2.impl_checkChainContains(paramEffect)))
/* 227 */       return true;
/* 228 */     if ((localEffect1 != null) && (localEffect1.impl_checkChainContains(paramEffect))) {
/* 229 */       return true;
/*     */     }
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */   public final void setDiffuseConstant(double paramDouble)
/*     */   {
/* 248 */     diffuseConstantProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getDiffuseConstant() {
/* 252 */     return this.diffuseConstant == null ? 1.0D : this.diffuseConstant.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty diffuseConstantProperty() {
/* 256 */     if (this.diffuseConstant == null) {
/* 257 */       this.diffuseConstant = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 261 */           Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 266 */           return Lighting.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 271 */           return "diffuseConstant";
/*     */         }
/*     */       };
/*     */     }
/* 275 */     return this.diffuseConstant;
/*     */   }
/*     */ 
/*     */   public final void setSpecularConstant(double paramDouble)
/*     */   {
/* 292 */     specularConstantProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getSpecularConstant() {
/* 296 */     return this.specularConstant == null ? 0.3D : this.specularConstant.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty specularConstantProperty() {
/* 300 */     if (this.specularConstant == null) {
/* 301 */       this.specularConstant = new DoublePropertyBase(0.3D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 305 */           Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 310 */           return Lighting.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 315 */           return "specularConstant";
/*     */         }
/*     */       };
/*     */     }
/* 319 */     return this.specularConstant;
/*     */   }
/*     */ 
/*     */   public final void setSpecularExponent(double paramDouble)
/*     */   {
/* 336 */     specularExponentProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getSpecularExponent() {
/* 340 */     return this.specularExponent == null ? 20.0D : this.specularExponent.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty specularExponentProperty() {
/* 344 */     if (this.specularExponent == null) {
/* 345 */       this.specularExponent = new DoublePropertyBase(20.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 349 */           Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 354 */           return Lighting.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 359 */           return "specularExponent";
/*     */         }
/*     */       };
/*     */     }
/* 363 */     return this.specularExponent;
/*     */   }
/*     */ 
/*     */   public final void setSurfaceScale(double paramDouble)
/*     */   {
/* 380 */     surfaceScaleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getSurfaceScale() {
/* 384 */     return this.surfaceScale == null ? 1.5D : this.surfaceScale.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty surfaceScaleProperty() {
/* 388 */     if (this.surfaceScale == null) {
/* 389 */       this.surfaceScale = new DoublePropertyBase(1.5D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 393 */           Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 398 */           return Lighting.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 403 */           return "surfaceScale";
/*     */         }
/*     */       };
/*     */     }
/* 407 */     return this.surfaceScale;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 412 */     Effect localEffect1 = getBumpInput();
/*     */ 
/* 414 */     if (localEffect1 != null) {
/* 415 */       localEffect1.impl_sync();
/*     */     }
/*     */ 
/* 418 */     Effect localEffect2 = getContentInput();
/* 419 */     if (localEffect2 != null) {
/* 420 */       localEffect2.impl_sync();
/*     */     }
/*     */ 
/* 423 */     PhongLighting localPhongLighting = (PhongLighting)impl_getImpl();
/* 424 */     localPhongLighting.setBumpInput(localEffect1 == null ? null : localEffect1.impl_getImpl());
/* 425 */     localPhongLighting.setContentInput(localEffect2 == null ? null : localEffect2.impl_getImpl());
/* 426 */     localPhongLighting.setDiffuseConstant((float)Utils.clamp(0.0D, getDiffuseConstant(), 2.0D));
/* 427 */     localPhongLighting.setSpecularConstant((float)Utils.clamp(0.0D, getSpecularConstant(), 2.0D));
/* 428 */     localPhongLighting.setSpecularExponent((float)Utils.clamp(0.0D, getSpecularExponent(), 40.0D));
/* 429 */     localPhongLighting.setSurfaceScale((float)Utils.clamp(0.0D, getSurfaceScale(), 10.0D));
/* 430 */     Light localLight = getLight();
/* 431 */     this.lightChangeListener.register(localLight);
/* 432 */     if (localLight != null) {
/* 433 */       localLight.impl_sync();
/* 434 */       localPhongLighting.setLight(localLight.impl_getImpl());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 448 */     return EffectUtils.getInputBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor, getContentInput());
/*     */   }
/*     */ 
/*     */   private class LightChangeListener extends EffectChangeListener
/*     */   {
/*     */     Light light;
/*     */ 
/*     */     private LightChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void register(Light paramLight)
/*     */     {
/* 156 */       this.light = paramLight;
/* 157 */       super.register(this.light == null ? null : this.light.effectDirtyProperty());
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 162 */       if (this.light.impl_isEffectDirty()) {
/* 163 */         Lighting.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 164 */         Lighting.this.effectBoundsChanged();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Lighting
 * JD-Core Version:    0.6.2
 */