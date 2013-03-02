/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class DisplacementMap extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private ObjectProperty<FloatMap> mapData;
/* 226 */   private final MapDataChangeListener mapDataChangeListener = new MapDataChangeListener(null);
/*     */   private DoubleProperty scaleX;
/*     */   private DoubleProperty scaleY;
/*     */   private DoubleProperty offsetX;
/*     */   private DoubleProperty offsetY;
/*     */   private BooleanProperty wrap;
/*     */ 
/*     */   com.sun.scenario.effect.DisplacementMap impl_createImpl()
/*     */   {
/* 106 */     return new com.sun.scenario.effect.DisplacementMap(new com.sun.scenario.effect.FloatMap(1, 1), com.sun.scenario.effect.Effect.DefaultInput);
/*     */   }
/*     */ 
/*     */   public DisplacementMap()
/*     */   {
/* 115 */     FloatMap localFloatMap = new FloatMap();
/* 116 */     localFloatMap.setWidth(1);
/* 117 */     localFloatMap.setHeight(1);
/* 118 */     setMapData(localFloatMap);
/*     */   }
/*     */ 
/*     */   public DisplacementMap(FloatMap paramFloatMap)
/*     */   {
/* 126 */     setMapData(paramFloatMap);
/*     */   }
/*     */ 
/*     */   public DisplacementMap(FloatMap paramFloatMap, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 145 */     setMapData(paramFloatMap);
/* 146 */     setOffsetX(paramDouble1);
/* 147 */     setOffsetY(paramDouble2);
/* 148 */     setScaleX(paramDouble3);
/* 149 */     setScaleY(paramDouble4);
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 163 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 167 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 171 */     if (this.input == null) {
/* 172 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 174 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 179 */     Effect localEffect = getInput();
/* 180 */     if (localEffect == null)
/* 181 */       return false;
/* 182 */     if (localEffect == paramEffect)
/* 183 */       return true;
/* 184 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setMapData(FloatMap paramFloatMap)
/*     */   {
/* 195 */     mapDataProperty().set(paramFloatMap);
/*     */   }
/*     */ 
/*     */   public final FloatMap getMapData() {
/* 199 */     return this.mapData == null ? null : (FloatMap)this.mapData.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<FloatMap> mapDataProperty() {
/* 203 */     if (this.mapData == null) {
/* 204 */       this.mapData = new ObjectPropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 208 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 209 */           DisplacementMap.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 214 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 219 */           return "mapData";
/*     */         }
/*     */       };
/*     */     }
/* 223 */     return this.mapData;
/*     */   }
/*     */ 
/*     */   public final void setScaleX(double paramDouble)
/*     */   {
/* 260 */     scaleXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getScaleX() {
/* 264 */     return this.scaleX == null ? 1.0D : this.scaleX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty scaleXProperty() {
/* 268 */     if (this.scaleX == null) {
/* 269 */       this.scaleX = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 273 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 278 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 283 */           return "scaleX";
/*     */         }
/*     */       };
/*     */     }
/* 287 */     return this.scaleX;
/*     */   }
/*     */ 
/*     */   public final void setScaleY(double paramDouble)
/*     */   {
/* 305 */     scaleYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getScaleY() {
/* 309 */     return this.scaleY == null ? 1.0D : this.scaleY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty scaleYProperty() {
/* 313 */     if (this.scaleY == null) {
/* 314 */       this.scaleY = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 318 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 323 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 328 */           return "scaleY";
/*     */         }
/*     */       };
/*     */     }
/* 332 */     return this.scaleY;
/*     */   }
/*     */ 
/*     */   public final void setOffsetX(double paramDouble)
/*     */   {
/* 350 */     offsetXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetX() {
/* 354 */     return this.offsetX == null ? 0.0D : this.offsetX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetXProperty() {
/* 358 */     if (this.offsetX == null) {
/* 359 */       this.offsetX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 363 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 368 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 373 */           return "offsetX";
/*     */         }
/*     */       };
/*     */     }
/* 377 */     return this.offsetX;
/*     */   }
/*     */ 
/*     */   public final void setOffsetY(double paramDouble)
/*     */   {
/* 395 */     offsetYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetY() {
/* 399 */     return this.offsetY == null ? 0.0D : this.offsetY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetYProperty() {
/* 403 */     if (this.offsetY == null) {
/* 404 */       this.offsetY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 408 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 413 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 418 */           return "offsetY";
/*     */         }
/*     */       };
/*     */     }
/* 422 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   public final void setWrap(boolean paramBoolean)
/*     */   {
/* 440 */     wrapProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isWrap() {
/* 444 */     return this.wrap == null ? false : this.wrap.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty wrapProperty() {
/* 448 */     if (this.wrap == null) {
/* 449 */       this.wrap = new BooleanPropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 453 */           DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 458 */           return DisplacementMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 463 */           return "wrap";
/*     */         }
/*     */       };
/*     */     }
/* 467 */     return this.wrap;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 472 */     Effect localEffect = getInput();
/* 473 */     if (localEffect != null) {
/* 474 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 477 */     com.sun.scenario.effect.DisplacementMap localDisplacementMap = (com.sun.scenario.effect.DisplacementMap)impl_getImpl();
/*     */ 
/* 479 */     localDisplacementMap.setContentInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 480 */     FloatMap localFloatMap = getMapData();
/* 481 */     this.mapDataChangeListener.register(localFloatMap);
/* 482 */     if (localFloatMap != null) {
/* 483 */       localFloatMap.impl_sync();
/* 484 */       localDisplacementMap.setMapData(localFloatMap.getImpl());
/*     */     } else {
/* 486 */       localDisplacementMap.setMapData(null);
/*     */     }
/* 488 */     localDisplacementMap.setScaleX((float)getScaleX());
/* 489 */     localDisplacementMap.setScaleY((float)getScaleY());
/* 490 */     localDisplacementMap.setOffsetX((float)getOffsetX());
/* 491 */     localDisplacementMap.setOffsetY((float)getOffsetY());
/* 492 */     localDisplacementMap.setWrap(isWrap());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 505 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 509 */     return EffectUtils.transformBounds(paramBaseTransform, paramBaseBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 520 */     DisplacementMap localDisplacementMap = new DisplacementMap(getMapData().impl_copy(), getOffsetX(), getOffsetY(), getScaleX(), getScaleY());
/*     */ 
/* 523 */     localDisplacementMap.setInput(getInput());
/* 524 */     return localDisplacementMap;
/*     */   }
/*     */ 
/*     */   private class MapDataChangeListener extends EffectChangeListener
/*     */   {
/*     */     FloatMap mapData;
/*     */ 
/*     */     private MapDataChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void register(FloatMap paramFloatMap)
/*     */     {
/* 232 */       this.mapData = paramFloatMap;
/* 233 */       super.register(this.mapData == null ? null : this.mapData.effectDirtyProperty());
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 238 */       if (this.mapData.impl_isEffectDirty()) {
/* 239 */         DisplacementMap.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 240 */         DisplacementMap.this.effectBoundsChanged();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.DisplacementMap
 * JD-Core Version:    0.6.2
 */