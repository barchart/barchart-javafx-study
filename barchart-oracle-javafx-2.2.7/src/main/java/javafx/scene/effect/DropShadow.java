/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public class DropShadow extends Effect
/*     */ {
/*     */   private boolean changeIsLocal;
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty radius;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */   private ObjectProperty<BlurType> blurType;
/*     */   private DoubleProperty spread;
/*     */   private ObjectProperty<Color> color;
/*     */   private DoubleProperty offsetX;
/*     */   private DoubleProperty offsetY;
/*     */ 
/*     */   public DropShadow()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DropShadow(double paramDouble, Color paramColor)
/*     */   {
/* 101 */     setRadius(paramDouble);
/* 102 */     setColor(paramColor);
/*     */   }
/*     */ 
/*     */   public DropShadow(double paramDouble1, double paramDouble2, double paramDouble3, Color paramColor)
/*     */   {
/* 114 */     setRadius(paramDouble1);
/* 115 */     setOffsetX(paramDouble2);
/* 116 */     setOffsetY(paramDouble3);
/* 117 */     setColor(paramColor);
/*     */   }
/*     */ 
/*     */   public DropShadow(BlurType paramBlurType, Color paramColor, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 133 */     setBlurType(paramBlurType);
/* 134 */     setColor(paramColor);
/* 135 */     setRadius(paramDouble1);
/* 136 */     setSpread(paramDouble2);
/* 137 */     setOffsetX(paramDouble3);
/* 138 */     setOffsetY(paramDouble4);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.DropShadow impl_createImpl()
/*     */   {
/* 143 */     return new com.sun.scenario.effect.DropShadow();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 157 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 161 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 165 */     if (this.input == null) {
/* 166 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 168 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 173 */     Effect localEffect = getInput();
/* 174 */     if (localEffect == null)
/* 175 */       return false;
/* 176 */     if (localEffect == paramEffect)
/* 177 */       return true;
/* 178 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 199 */     radiusProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 203 */     return this.radius == null ? 10.0D : this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 207 */     if (this.radius == null) {
/* 208 */       this.radius = new DoublePropertyBase(10.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 213 */           double d = DropShadow.this.getRadius();
/* 214 */           if (!DropShadow.this.changeIsLocal) {
/* 215 */             DropShadow.this.changeIsLocal = true;
/* 216 */             DropShadow.this.updateRadius(d);
/* 217 */             DropShadow.this.changeIsLocal = false;
/* 218 */             DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 219 */             DropShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 225 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 230 */           return "radius";
/*     */         }
/*     */       };
/*     */     }
/* 234 */     return this.radius;
/*     */   }
/*     */ 
/*     */   private void updateRadius(double paramDouble) {
/* 238 */     double d = paramDouble * 2.0D + 1.0D;
/* 239 */     if ((this.width != null) && (this.width.isBound()))
/*     */     {
/* 253 */       if ((this.height == null) || (!this.height.isBound()))
/* 254 */         setHeight(d * 2.0D - getWidth());
/*     */     }
/* 256 */     else if ((this.height != null) && (this.height.isBound())) {
/* 257 */       setWidth(d * 2.0D - getHeight());
/*     */     } else {
/* 259 */       setWidth(d);
/* 260 */       setHeight(d);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 282 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 286 */     return this.width == null ? 21.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 290 */     if (this.width == null) {
/* 291 */       this.width = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 296 */           double d = DropShadow.this.getWidth();
/* 297 */           if (!DropShadow.this.changeIsLocal) {
/* 298 */             DropShadow.this.changeIsLocal = true;
/* 299 */             DropShadow.this.updateWidth(d);
/* 300 */             DropShadow.this.changeIsLocal = false;
/* 301 */             DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 302 */             DropShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 308 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 313 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 317 */     return this.width;
/*     */   }
/*     */ 
/*     */   private void updateWidth(double paramDouble)
/*     */   {
/*     */     double d;
/* 321 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 322 */       d = (paramDouble + getHeight()) / 2.0D;
/* 323 */       d = (d - 1.0D) / 2.0D;
/* 324 */       if (d < 0.0D) {
/* 325 */         d = 0.0D;
/*     */       }
/* 327 */       setRadius(d);
/*     */     }
/* 329 */     else if ((this.height == null) || (!this.height.isBound())) {
/* 330 */       d = getRadius() * 2.0D + 1.0D;
/* 331 */       setHeight(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 354 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 358 */     return this.height == null ? 21.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 362 */     if (this.height == null) {
/* 363 */       this.height = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 368 */           double d = DropShadow.this.getHeight();
/* 369 */           if (!DropShadow.this.changeIsLocal) {
/* 370 */             DropShadow.this.changeIsLocal = true;
/* 371 */             DropShadow.this.updateHeight(d);
/* 372 */             DropShadow.this.changeIsLocal = false;
/* 373 */             DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 374 */             DropShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 380 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 385 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 389 */     return this.height;
/*     */   }
/*     */ 
/*     */   private void updateHeight(double paramDouble)
/*     */   {
/*     */     double d;
/* 393 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 394 */       d = (getWidth() + paramDouble) / 2.0D;
/* 395 */       d = (d - 1.0D) / 2.0D;
/* 396 */       if (d < 0.0D) {
/* 397 */         d = 0.0D;
/*     */       }
/* 399 */       setRadius(d);
/*     */     }
/* 401 */     else if ((this.width == null) || (!this.width.isBound())) {
/* 402 */       d = getRadius() * 2.0D + 1.0D;
/* 403 */       setWidth(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setBlurType(BlurType paramBlurType)
/*     */   {
/* 422 */     blurTypeProperty().set(paramBlurType);
/*     */   }
/*     */ 
/*     */   public final BlurType getBlurType() {
/* 426 */     return this.blurType == null ? BlurType.THREE_PASS_BOX : (BlurType)this.blurType.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<BlurType> blurTypeProperty() {
/* 430 */     if (this.blurType == null) {
/* 431 */       this.blurType = new ObjectPropertyBase(BlurType.THREE_PASS_BOX)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 435 */           DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 436 */           DropShadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 441 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 446 */           return "blurType";
/*     */         }
/*     */       };
/*     */     }
/* 450 */     return this.blurType;
/*     */   }
/*     */ 
/*     */   public final void setSpread(double paramDouble)
/*     */   {
/* 476 */     spreadProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getSpread() {
/* 480 */     return this.spread == null ? 0.0D : this.spread.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty spreadProperty() {
/* 484 */     if (this.spread == null) {
/* 485 */       this.spread = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 489 */           DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 494 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 499 */           return "spread";
/*     */         }
/*     */       };
/*     */     }
/* 503 */     return this.spread;
/*     */   }
/*     */ 
/*     */   public final void setColor(Color paramColor)
/*     */   {
/* 520 */     colorProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getColor() {
/* 524 */     return this.color == null ? Color.BLACK : (Color)this.color.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> colorProperty() {
/* 528 */     if (this.color == null) {
/* 529 */       this.color = new ObjectPropertyBase(Color.BLACK)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 533 */           DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 538 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 543 */           return "color";
/*     */         }
/*     */       };
/*     */     }
/* 547 */     return this.color;
/*     */   }
/*     */ 
/*     */   public final void setOffsetX(double paramDouble)
/*     */   {
/* 563 */     offsetXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetX()
/*     */   {
/* 568 */     return this.offsetX == null ? 0.0D : this.offsetX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetXProperty() {
/* 572 */     if (this.offsetX == null) {
/* 573 */       this.offsetX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 577 */           DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 578 */           DropShadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 583 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 588 */           return "offsetX";
/*     */         }
/*     */       };
/*     */     }
/* 592 */     return this.offsetX;
/*     */   }
/*     */ 
/*     */   public final void setOffsetY(double paramDouble)
/*     */   {
/* 609 */     offsetYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetY() {
/* 613 */     return this.offsetY == null ? 0.0D : this.offsetY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetYProperty() {
/* 617 */     if (this.offsetY == null) {
/* 618 */       this.offsetY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 622 */           DropShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 623 */           DropShadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 628 */           return DropShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 633 */           return "offsetY";
/*     */         }
/*     */       };
/*     */     }
/* 637 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   private float getClampedWidth() {
/* 641 */     return (float)Utils.clamp(0.0D, getWidth(), 255.0D);
/*     */   }
/*     */ 
/*     */   private float getClampedHeight() {
/* 645 */     return (float)Utils.clamp(0.0D, getHeight(), 255.0D);
/*     */   }
/*     */ 
/*     */   private float getClampedSpread() {
/* 649 */     return (float)Utils.clamp(0.0D, getSpread(), 1.0D);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 654 */     Effect localEffect = getInput();
/* 655 */     if (localEffect != null) {
/* 656 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 659 */     com.sun.scenario.effect.DropShadow localDropShadow = (com.sun.scenario.effect.DropShadow)impl_getImpl();
/*     */ 
/* 661 */     localDropShadow.setShadowSourceInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 662 */     localDropShadow.setContentInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 663 */     localDropShadow.setGaussianWidth(getClampedWidth());
/* 664 */     localDropShadow.setGaussianHeight(getClampedHeight());
/* 665 */     localDropShadow.setSpread(getClampedSpread());
/* 666 */     localDropShadow.setShadowMode(Toolkit.getToolkit().toShadowMode(getBlurType()));
/* 667 */     localDropShadow.setColor(Toolkit.getToolkit().toColor4f(getColor()));
/* 668 */     localDropShadow.setOffsetX((int)getOffsetX());
/* 669 */     localDropShadow.setOffsetY((int)getOffsetY());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 682 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 687 */     int i = (int)getOffsetX();
/* 688 */     int j = (int)getOffsetY();
/*     */ 
/* 690 */     BaseBounds localBaseBounds1 = BaseBounds.getInstance(paramBaseBounds.getMinX() + i, paramBaseBounds.getMinY() + j, paramBaseBounds.getMinZ(), paramBaseBounds.getMaxX() + i, paramBaseBounds.getMaxY() + j, paramBaseBounds.getMaxZ());
/*     */ 
/* 697 */     localBaseBounds1 = EffectUtils.getShadowBounds(localBaseBounds1, paramBaseTransform, getClampedWidth(), getClampedHeight(), getBlurType());
/*     */ 
/* 701 */     BaseBounds localBaseBounds2 = EffectUtils.transformBounds(paramBaseTransform, paramBaseBounds);
/* 702 */     BaseBounds localBaseBounds3 = localBaseBounds2.deriveWithUnion(localBaseBounds1);
/*     */ 
/* 704 */     return localBaseBounds3;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 715 */     DropShadow localDropShadow = new DropShadow(getBlurType(), getColor(), getRadius(), getSpread(), getOffsetX(), getOffsetY());
/*     */ 
/* 718 */     localDropShadow.setInput(getInput());
/* 719 */     localDropShadow.setWidth(getWidth());
/* 720 */     localDropShadow.setHeight(getHeight());
/* 721 */     return localDropShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.DropShadow
 * JD-Core Version:    0.6.2
 */