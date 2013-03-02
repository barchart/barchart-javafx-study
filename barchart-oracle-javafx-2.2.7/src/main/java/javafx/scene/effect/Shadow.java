/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.scenario.effect.GeneralShadow;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public class Shadow extends Effect
/*     */ {
/*     */   private boolean changeIsLocal;
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty radius;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */   private ObjectProperty<BlurType> blurType;
/*     */   private ObjectProperty<Color> color;
/*     */ 
/*     */   public Shadow()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Shadow(double paramDouble, Color paramColor)
/*     */   {
/*  70 */     setRadius(paramDouble);
/*  71 */     setColor(paramColor);
/*     */   }
/*     */ 
/*     */   public Shadow(BlurType paramBlurType, Color paramColor, double paramDouble)
/*     */   {
/*  82 */     setBlurType(paramBlurType);
/*  83 */     setColor(paramColor);
/*  84 */     setRadius(paramDouble);
/*     */   }
/*     */ 
/*     */   GeneralShadow impl_createImpl()
/*     */   {
/*  89 */     return new GeneralShadow();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 102 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 106 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 110 */     if (this.input == null) {
/* 111 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 113 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 118 */     Effect localEffect = getInput();
/* 119 */     if (localEffect == null)
/* 120 */       return false;
/* 121 */     if (localEffect == paramEffect)
/* 122 */       return true;
/* 123 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 144 */     radiusProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 148 */     return this.radius == null ? 10.0D : this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 152 */     if (this.radius == null) {
/* 153 */       this.radius = new DoublePropertyBase(10.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 158 */           double d = Shadow.this.getRadius();
/* 159 */           if (!Shadow.this.changeIsLocal) {
/* 160 */             Shadow.this.changeIsLocal = true;
/* 161 */             Shadow.this.updateRadius(d);
/* 162 */             Shadow.this.changeIsLocal = false;
/* 163 */             Shadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 164 */             Shadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 170 */           return Shadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 175 */           return "radius";
/*     */         }
/*     */       };
/*     */     }
/* 179 */     return this.radius;
/*     */   }
/*     */ 
/*     */   private void updateRadius(double paramDouble) {
/* 183 */     double d = paramDouble * 2.0D + 1.0D;
/* 184 */     if ((this.width != null) && (this.width.isBound())) {
/* 185 */       if ((this.height == null) || (!this.height.isBound()))
/* 186 */         setHeight(d * 2.0D - getWidth());
/*     */     }
/* 188 */     else if ((this.height != null) && (this.height.isBound())) {
/* 189 */       setWidth(d * 2.0D - getHeight());
/*     */     } else {
/* 191 */       setWidth(d);
/* 192 */       setHeight(d);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 214 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 218 */     return this.width == null ? 21.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 222 */     if (this.width == null) {
/* 223 */       this.width = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 228 */           double d = Shadow.this.getWidth();
/* 229 */           if (!Shadow.this.changeIsLocal) {
/* 230 */             Shadow.this.changeIsLocal = true;
/* 231 */             Shadow.this.updateWidth(d);
/* 232 */             Shadow.this.changeIsLocal = false;
/* 233 */             Shadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 234 */             Shadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 240 */           return Shadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 245 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 249 */     return this.width;
/*     */   }
/*     */ 
/*     */   private void updateWidth(double paramDouble)
/*     */   {
/*     */     double d;
/* 253 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 254 */       d = (paramDouble + getHeight()) / 2.0D;
/* 255 */       d = (d - 1.0D) / 2.0D;
/* 256 */       if (d < 0.0D) {
/* 257 */         d = 0.0D;
/*     */       }
/* 259 */       setRadius(d);
/*     */     }
/* 262 */     else if ((this.height == null) || (!this.height.isBound())) {
/* 263 */       d = getRadius() * 2.0D + 1.0D;
/* 264 */       setHeight(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 287 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 291 */     return this.height == null ? 21.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 295 */     if (this.height == null) {
/* 296 */       this.height = new DoublePropertyBase(21.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 301 */           double d = Shadow.this.getHeight();
/* 302 */           if (!Shadow.this.changeIsLocal) {
/* 303 */             Shadow.this.changeIsLocal = true;
/* 304 */             Shadow.this.updateHeight(d);
/* 305 */             Shadow.this.changeIsLocal = false;
/* 306 */             Shadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 307 */             Shadow.this.effectBoundsChanged();
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 313 */           return Shadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 318 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 322 */     return this.height;
/*     */   }
/*     */ 
/*     */   private void updateHeight(double paramDouble)
/*     */   {
/*     */     double d;
/* 326 */     if ((this.radius == null) || (!this.radius.isBound())) {
/* 327 */       d = (getWidth() + paramDouble) / 2.0D;
/* 328 */       d = (d - 1.0D) / 2.0D;
/* 329 */       if (d < 0.0D) {
/* 330 */         d = 0.0D;
/*     */       }
/* 332 */       setRadius(d);
/*     */     }
/* 334 */     else if ((this.width == null) || (!this.width.isBound())) {
/* 335 */       d = getRadius() * 2.0D + 1.0D;
/* 336 */       setWidth(d * 2.0D - paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void setBlurType(BlurType paramBlurType)
/*     */   {
/* 355 */     blurTypeProperty().set(paramBlurType);
/*     */   }
/*     */ 
/*     */   public final BlurType getBlurType() {
/* 359 */     return this.blurType == null ? BlurType.THREE_PASS_BOX : (BlurType)this.blurType.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<BlurType> blurTypeProperty() {
/* 363 */     if (this.blurType == null) {
/* 364 */       this.blurType = new ObjectPropertyBase(BlurType.THREE_PASS_BOX)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 368 */           Shadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 369 */           Shadow.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 374 */           return Shadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 379 */           return "blurType";
/*     */         }
/*     */       };
/*     */     }
/* 383 */     return this.blurType;
/*     */   }
/*     */ 
/*     */   public final void setColor(Color paramColor)
/*     */   {
/* 400 */     colorProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getColor() {
/* 404 */     return this.color == null ? Color.BLACK : (Color)this.color.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> colorProperty() {
/* 408 */     if (this.color == null) {
/* 409 */       this.color = new ObjectPropertyBase(Color.BLACK)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 413 */           Shadow.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 418 */           return Shadow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 423 */           return "color";
/*     */         }
/*     */       };
/*     */     }
/* 427 */     return this.color;
/*     */   }
/*     */ 
/*     */   private float getClampedWidth() {
/* 431 */     return (float)Utils.clamp(0.0D, getWidth(), 255.0D);
/*     */   }
/*     */ 
/*     */   private float getClampedHeight() {
/* 435 */     return (float)Utils.clamp(0.0D, getHeight(), 255.0D);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 440 */     Effect localEffect = getInput();
/* 441 */     if (localEffect != null) {
/* 442 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 445 */     GeneralShadow localGeneralShadow = (GeneralShadow)impl_getImpl();
/*     */ 
/* 447 */     localGeneralShadow.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 448 */     localGeneralShadow.setGaussianWidth(getClampedWidth());
/* 449 */     localGeneralShadow.setGaussianHeight(getClampedHeight());
/* 450 */     localGeneralShadow.setShadowMode(Toolkit.getToolkit().toShadowMode(getBlurType()));
/* 451 */     localGeneralShadow.setColor(Toolkit.getToolkit().toColor4f(getColor()));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 464 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 468 */     return EffectUtils.getShadowBounds(paramBaseBounds, paramBaseTransform, getClampedWidth(), getClampedHeight(), getBlurType());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 482 */     Shadow localShadow = new Shadow(getBlurType(), getColor(), getRadius());
/* 483 */     localShadow.setInput(getInput());
/* 484 */     localShadow.setHeight(getHeight());
/* 485 */     localShadow.setWidth(getWidth());
/* 486 */     return localShadow;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Shadow
 * JD-Core Version:    0.6.2
 */