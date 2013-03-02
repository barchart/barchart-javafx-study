/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class PerspectiveTransform extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty ulx;
/*     */   private DoubleProperty uly;
/*     */   private DoubleProperty urx;
/*     */   private DoubleProperty ury;
/*     */   private DoubleProperty lrx;
/*     */   private DoubleProperty lry;
/*     */   private DoubleProperty llx;
/*     */   private DoubleProperty lly;
/* 502 */   private float[] devcoords = new float[8];
/*     */ 
/*     */   public PerspectiveTransform()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PerspectiveTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8)
/*     */   {
/* 117 */     setUlx(paramDouble1); setUly(paramDouble2);
/* 118 */     setUrx(paramDouble3); setUry(paramDouble4);
/* 119 */     setLlx(paramDouble7); setLly(paramDouble8);
/* 120 */     setLrx(paramDouble5); setLry(paramDouble6);
/*     */   }
/*     */ 
/*     */   private void updateXform() {
/* 124 */     ((com.sun.scenario.effect.PerspectiveTransform)impl_getImpl()).setQuadMapping((float)getUlx(), (float)getUly(), (float)getUrx(), (float)getUry(), (float)getLrx(), (float)getLry(), (float)getLlx(), (float)getLly());
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.PerspectiveTransform impl_createImpl()
/*     */   {
/* 133 */     return new com.sun.scenario.effect.PerspectiveTransform();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 146 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 150 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 154 */     if (this.input == null) {
/* 155 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 157 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 162 */     Effect localEffect = getInput();
/* 163 */     if (localEffect == null)
/* 164 */       return false;
/* 165 */     if (localEffect == paramEffect)
/* 166 */       return true;
/* 167 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setUlx(double paramDouble)
/*     */   {
/* 179 */     ulxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getUlx() {
/* 183 */     return this.ulx == null ? 0.0D : this.ulx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty ulxProperty() {
/* 187 */     if (this.ulx == null) {
/* 188 */       this.ulx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 192 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 193 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 198 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 203 */           return "ulx";
/*     */         }
/*     */       };
/*     */     }
/* 207 */     return this.ulx;
/*     */   }
/*     */ 
/*     */   public final void setUly(double paramDouble)
/*     */   {
/* 219 */     ulyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getUly() {
/* 223 */     return this.uly == null ? 0.0D : this.uly.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty ulyProperty() {
/* 227 */     if (this.uly == null) {
/* 228 */       this.uly = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 232 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 233 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 238 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 243 */           return "uly";
/*     */         }
/*     */       };
/*     */     }
/* 247 */     return this.uly;
/*     */   }
/*     */ 
/*     */   public final void setUrx(double paramDouble)
/*     */   {
/* 259 */     urxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getUrx() {
/* 263 */     return this.urx == null ? 0.0D : this.urx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty urxProperty() {
/* 267 */     if (this.urx == null) {
/* 268 */       this.urx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 272 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 273 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 278 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 283 */           return "urx";
/*     */         }
/*     */       };
/*     */     }
/* 287 */     return this.urx;
/*     */   }
/*     */ 
/*     */   public final void setUry(double paramDouble)
/*     */   {
/* 299 */     uryProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getUry() {
/* 303 */     return this.ury == null ? 0.0D : this.ury.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty uryProperty() {
/* 307 */     if (this.ury == null) {
/* 308 */       this.ury = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 312 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 313 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 318 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 323 */           return "ury";
/*     */         }
/*     */       };
/*     */     }
/* 327 */     return this.ury;
/*     */   }
/*     */ 
/*     */   public final void setLrx(double paramDouble)
/*     */   {
/* 339 */     lrxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLrx() {
/* 343 */     return this.lrx == null ? 0.0D : this.lrx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty lrxProperty() {
/* 347 */     if (this.lrx == null) {
/* 348 */       this.lrx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 352 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 353 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 358 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 363 */           return "lrx";
/*     */         }
/*     */       };
/*     */     }
/* 367 */     return this.lrx;
/*     */   }
/*     */ 
/*     */   public final void setLry(double paramDouble)
/*     */   {
/* 379 */     lryProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLry() {
/* 383 */     return this.lry == null ? 0.0D : this.lry.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty lryProperty() {
/* 387 */     if (this.lry == null) {
/* 388 */       this.lry = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 392 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 393 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 398 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 403 */           return "lry";
/*     */         }
/*     */       };
/*     */     }
/* 407 */     return this.lry;
/*     */   }
/*     */ 
/*     */   public final void setLlx(double paramDouble)
/*     */   {
/* 419 */     llxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLlx() {
/* 423 */     return this.llx == null ? 0.0D : this.llx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty llxProperty() {
/* 427 */     if (this.llx == null) {
/* 428 */       this.llx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 432 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 433 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 438 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 443 */           return "llx";
/*     */         }
/*     */       };
/*     */     }
/* 447 */     return this.llx;
/*     */   }
/*     */ 
/*     */   public final void setLly(double paramDouble)
/*     */   {
/* 459 */     llyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLly() {
/* 463 */     return this.lly == null ? 0.0D : this.lly.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty llyProperty() {
/* 467 */     if (this.lly == null) {
/* 468 */       this.lly = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 472 */           PerspectiveTransform.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 473 */           PerspectiveTransform.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 478 */           return PerspectiveTransform.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 483 */           return "lly";
/*     */         }
/*     */       };
/*     */     }
/* 487 */     return this.lly;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 492 */     Effect localEffect = getInput();
/* 493 */     if (localEffect != null) {
/* 494 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 497 */     ((com.sun.scenario.effect.PerspectiveTransform)impl_getImpl()).setInput(localEffect == null ? null : localEffect.impl_getImpl());
/*     */ 
/* 499 */     updateXform();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 514 */     setupDevCoords(paramBaseTransform);
/*     */     float f3;
/* 517 */     float f1 = f3 = this.devcoords[0];
/*     */     float f4;
/* 518 */     float f2 = f4 = this.devcoords[1];
/* 519 */     for (int i = 2; i < this.devcoords.length; i += 2) {
/* 520 */       if (f1 > this.devcoords[i]) f1 = this.devcoords[i];
/* 521 */       else if (f3 < this.devcoords[i]) f3 = this.devcoords[i];
/* 522 */       if (f2 > this.devcoords[(i + 1)]) f2 = this.devcoords[(i + 1)];
/* 523 */       else if (f4 < this.devcoords[(i + 1)]) f4 = this.devcoords[(i + 1)];
/*     */     }
/*     */ 
/* 526 */     return new RectBounds(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   private void setupDevCoords(BaseTransform paramBaseTransform) {
/* 530 */     this.devcoords[0] = ((float)getUlx());
/* 531 */     this.devcoords[1] = ((float)getUly());
/* 532 */     this.devcoords[2] = ((float)getUrx());
/* 533 */     this.devcoords[3] = ((float)getUry());
/* 534 */     this.devcoords[4] = ((float)getLrx());
/* 535 */     this.devcoords[5] = ((float)getLry());
/* 536 */     this.devcoords[6] = ((float)getLlx());
/* 537 */     this.devcoords[7] = ((float)getLly());
/* 538 */     paramBaseTransform.transform(this.devcoords, 0, this.devcoords, 0, 4);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 549 */     return new PerspectiveTransform(getUlx(), getUly(), getUrx(), getUry(), getLrx(), getLry(), getLlx(), getLly());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.PerspectiveTransform
 * JD-Core Version:    0.6.2
 */