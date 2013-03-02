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
/*     */ public class InnerShadow extends Effect
/*     */ {
/*     */   private boolean changeIsLocal;
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty radius;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */   private ObjectProperty<BlurType> blurType;
/*     */   private DoubleProperty choke;
/*     */   private ObjectProperty<Color> color;
/*     */   private DoubleProperty offsetX;
/*     */   private DoubleProperty offsetY;
/*     */ 
/*     */   public InnerShadow()
/*     */   {
/*     */   }
/*     */ 
/*     */   public InnerShadow(double paramDouble, Color paramColor)
/*     */   {
/*  82 */     setRadius(paramDouble);
/*  83 */     setColor(paramColor);
/*     */   }
/*     */ 
/*     */   public InnerShadow(double paramDouble1, double paramDouble2, double paramDouble3, Color paramColor)
/*     */   {
/*  95 */     setRadius(paramDouble1);
/*  96 */     setOffsetX(paramDouble2);
/*  97 */     setOffsetY(paramDouble3);
/*  98 */     setColor(paramColor);
/*     */   }
/*     */ 
/*     */   public InnerShadow(BlurType paramBlurType, Color paramColor, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 114 */     setBlurType(paramBlurType);
/* 115 */     setColor(paramColor);
/* 116 */     setRadius(paramDouble1);
/* 117 */     setChoke(paramDouble2);
/* 118 */     setOffsetX(paramDouble3);
/* 119 */     setOffsetY(paramDouble4);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.InnerShadow impl_createImpl()
/*     */   {
/* 124 */     return new com.sun.scenario.effect.InnerShadow();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 139 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 143 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 147 */     if (this.input == null) {
/* 148 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 150 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 155 */     Effect localEffect = getInput();
/* 156 */     if (localEffect == null)
/* 157 */       return false;
/* 158 */     if (localEffect == paramEffect)
/* 159 */       return true;
/* 160 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 181 */     radiusProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 185 */     return this.radius == null ? 10.0D : this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 189 */     if (this.radius == null) {
/* 190 */       this.radius = new DoublePropertyBase(10.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 195 */           double d = InnerShadow.this.getRadius();
/* 196 */           if (!InnerShadow.this.changeIsLocal) {
/* 197 */             InnerShadow.this.changeIsLocal = true;
/* 198 */             InnerShadow.this.updateRadius(d);
/* 199 */             InnerShadow.this.changeIsLocal = false;
/* 200 */             InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 201 */             InnerShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 207 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 212 */           return "radius";
/*     */         }
/*     */       };
/*     */     }
/* 216 */     return this.radius;
/*     */   }
/*     */ 
/*     */   private void updateRadius(double paramDouble) {
/* 220 */     double d = paramDouble * 2.0D + 1.0D;
/* 221 */     if ((this.width != null) && (this.width.isBound())) {
/* 222 */       if ((this.height == null) || (!this.height.isBound()))
/* 223 */         setHeight(d * 2.0D - getWidth());
/*     */     }
/* 225 */     else if ((this.height != null) && (this.height.isBound())) {
/* 226 */       setWidth(d * 2.0D - getHeight());
/*     */     } else {
/* 228 */       setWidth(d);
/* 229 */       setHeight(d);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 251 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 255 */     return this.width == null ? 21.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 259 */     if (this.width == null) {
/* 260 */       this.width = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 265 */           double d = InnerShadow.this.getWidth();
/* 266 */           if (!InnerShadow.this.changeIsLocal) {
/* 267 */             InnerShadow.this.changeIsLocal = true;
/* 268 */             InnerShadow.this.updateWidth(d);
/* 269 */             InnerShadow.this.changeIsLocal = false;
/* 270 */             InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 271 */             InnerShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 277 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 282 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 286 */     return this.width;
/*     */   }
/*     */ 
/*     */   private void updateWidth(double paramDouble)
/*     */   {
/*     */     double d;
/* 290 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 291 */       d = (paramDouble + getHeight()) / 2.0D;
/* 292 */       d = (d - 1.0D) / 2.0D;
/* 293 */       if (d < 0.0D) {
/* 294 */         d = 0.0D;
/*     */       }
/* 296 */       setRadius(d);
/*     */     }
/* 298 */     else if ((this.height == null) || (!this.height.isBound())) {
/* 299 */       d = getRadius() * 2.0D + 1.0D;
/* 300 */       setHeight(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 323 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 327 */     return this.height == null ? 21.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 331 */     if (this.height == null) {
/* 332 */       this.height = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 337 */           double d = InnerShadow.this.getHeight();
/* 338 */           if (!InnerShadow.this.changeIsLocal) {
/* 339 */             InnerShadow.this.changeIsLocal = true;
/* 340 */             InnerShadow.this.updateHeight(d);
/* 341 */             InnerShadow.this.changeIsLocal = false;
/* 342 */             InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 343 */             InnerShadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 349 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 354 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 358 */     return this.height;
/*     */   }
/*     */ 
/*     */   private void updateHeight(double paramDouble)
/*     */   {
/*     */     double d;
/* 361 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 362 */       d = (getWidth() + paramDouble) / 2.0D;
/* 363 */       d = (d - 1.0D) / 2.0D;
/* 364 */       if (d < 0.0D) {
/* 365 */         d = 0.0D;
/*     */       }
/* 367 */       setRadius(d);
/*     */     }
/* 369 */     else if ((this.width == null) || (!this.width.isBound())) {
/* 370 */       d = getRadius() * 2.0D + 1.0D;
/* 371 */       setWidth(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setBlurType(BlurType paramBlurType)
/*     */   {
/* 390 */     blurTypeProperty().set(paramBlurType);
/*     */   }
/*     */ 
/*     */   public final BlurType getBlurType() {
/* 394 */     return this.blurType == null ? BlurType.THREE_PASS_BOX : (BlurType)this.blurType.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<BlurType> blurTypeProperty() {
/* 398 */     if (this.blurType == null) {
/* 399 */       this.blurType = new ObjectPropertyBase(BlurType.THREE_PASS_BOX)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 403 */           InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 408 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 413 */           return "blurType";
/*     */         }
/*     */       };
/*     */     }
/* 417 */     return this.blurType;
/*     */   }
/*     */ 
/*     */   public final void setChoke(double paramDouble)
/*     */   {
/* 443 */     chokeProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getChoke() {
/* 447 */     return this.choke == null ? 0.0D : this.choke.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty chokeProperty() {
/* 451 */     if (this.choke == null) {
/* 452 */       this.choke = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 456 */           InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 461 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 466 */           return "choke";
/*     */         }
/*     */       };
/*     */     }
/* 470 */     return this.choke;
/*     */   }
/*     */ 
/*     */   public final void setColor(Color paramColor)
/*     */   {
/* 487 */     colorProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getColor() {
/* 491 */     return this.color == null ? Color.BLACK : (Color)this.color.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> colorProperty() {
/* 495 */     if (this.color == null) {
/* 496 */       this.color = new ObjectPropertyBase(Color.BLACK)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 500 */           InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 505 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 510 */           return "color";
/*     */         }
/*     */       };
/*     */     }
/* 514 */     return this.color;
/*     */   }
/*     */ 
/*     */   public final void setOffsetX(double paramDouble)
/*     */   {
/* 531 */     offsetXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetX() {
/* 535 */     return this.offsetX == null ? 0.0D : this.offsetX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetXProperty() {
/* 539 */     if (this.offsetX == null) {
/* 540 */       this.offsetX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 544 */           InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 545 */           InnerShadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 550 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 555 */           return "offsetX";
/*     */         }
/*     */       };
/*     */     }
/* 559 */     return this.offsetX;
/*     */   }
/*     */ 
/*     */   public final void setOffsetY(double paramDouble)
/*     */   {
/* 576 */     offsetYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getOffsetY() {
/* 580 */     return this.offsetY == null ? 0.0D : this.offsetY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty offsetYProperty() {
/* 584 */     if (this.offsetY == null) {
/* 585 */       this.offsetY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 589 */           InnerShadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 590 */           InnerShadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 595 */           return InnerShadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 600 */           return "offsetY";
/*     */         }
/*     */       };
/*     */     }
/* 604 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 609 */     Effect localEffect = getInput();
/* 610 */     if (localEffect != null) {
/* 611 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 614 */     com.sun.scenario.effect.InnerShadow localInnerShadow = (com.sun.scenario.effect.InnerShadow)impl_getImpl();
/*     */ 
/* 616 */     localInnerShadow.setShadowSourceInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 617 */     localInnerShadow.setContentInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 618 */     localInnerShadow.setGaussianWidth((float)Utils.clamp(0.0D, getWidth(), 255.0D));
/* 619 */     localInnerShadow.setGaussianHeight((float)Utils.clamp(0.0D, getHeight(), 255.0D));
/* 620 */     localInnerShadow.setShadowMode(Toolkit.getToolkit().toShadowMode(getBlurType()));
/* 621 */     localInnerShadow.setColor(Toolkit.getToolkit().toColor4f(getColor()));
/* 622 */     localInnerShadow.setChoke((float)Utils.clamp(0.0D, getChoke(), 1.0D));
/* 623 */     localInnerShadow.setOffsetX((int)getOffsetX());
/* 624 */     localInnerShadow.setOffsetY((int)getOffsetY());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 637 */     return EffectUtils.getInputBounds(paramBaseBounds, paramBaseTransform, paramNode, paramBoundsAccessor, getInput());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 650 */     InnerShadow localInnerShadow = new InnerShadow(getBlurType(), getColor(), getRadius(), getChoke(), getOffsetX(), getOffsetY());
/*     */ 
/* 653 */     localInnerShadow.setInput(getInput());
/* 654 */     localInnerShadow.setWidth(getWidth());
/* 655 */     localInnerShadow.setHeight(getHeight());
/* 656 */     return localInnerShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.InnerShadow
 * JD-Core Version:    0.6.2
 */