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
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.IntegerPropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public class BoxBlur extends Effect
/*     */ {
/*     */   private ObjectProperty<Effect> input;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */   private IntegerProperty iterations;
/*     */ 
/*     */   public BoxBlur()
/*     */   {
/*     */   }
/*     */ 
/*     */   public BoxBlur(double paramDouble1, double paramDouble2, int paramInt)
/*     */   {
/*  87 */     setWidth(paramDouble1);
/*  88 */     setHeight(paramDouble2);
/*  89 */     setIterations(paramInt);
/*     */   }
/*     */ 
/*     */   com.sun.scenario.effect.BoxBlur impl_createImpl()
/*     */   {
/*  94 */     return new com.sun.scenario.effect.BoxBlur();
/*     */   }
/*     */ 
/*     */   public final void setInput(Effect paramEffect)
/*     */   {
/* 107 */     inputProperty().set(paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getInput() {
/* 111 */     return this.input == null ? null : (Effect)this.input.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Effect> inputProperty() {
/* 115 */     if (this.input == null) {
/* 116 */       this.input = new Effect.EffectInputProperty(this, "input");
/*     */     }
/* 118 */     return this.input;
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 123 */     Effect localEffect = getInput();
/* 124 */     if (localEffect == null)
/* 125 */       return false;
/* 126 */     if (localEffect == paramEffect)
/* 127 */       return true;
/* 128 */     return localEffect.impl_checkChainContains(paramEffect);
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 150 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 154 */     return this.width == null ? 5.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 158 */     if (this.width == null) {
/* 159 */       this.width = new DoublePropertyBase(5.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 163 */           BoxBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 164 */           BoxBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 169 */           return BoxBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 174 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 178 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 200 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 204 */     return this.height == null ? 5.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 208 */     if (this.height == null) {
/* 209 */       this.height = new DoublePropertyBase(5.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 213 */           BoxBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 214 */           BoxBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 219 */           return BoxBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 224 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 228 */     return this.height;
/*     */   }
/*     */ 
/*     */   public final void setIterations(int paramInt)
/*     */   {
/* 248 */     iterationsProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getIterations() {
/* 252 */     return this.iterations == null ? 1 : this.iterations.get();
/*     */   }
/*     */ 
/*     */   public final IntegerProperty iterationsProperty() {
/* 256 */     if (this.iterations == null) {
/* 257 */       this.iterations = new IntegerPropertyBase(1)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 261 */           BoxBlur.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 262 */           BoxBlur.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 267 */           return BoxBlur.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 272 */           return "iterations";
/*     */         }
/*     */       };
/*     */     }
/* 276 */     return this.iterations;
/*     */   }
/*     */ 
/*     */   private int getClampedWidth() {
/* 280 */     return Utils.clamp(0, (int)getWidth(), 255);
/*     */   }
/*     */ 
/*     */   private int getClampedHeight() {
/* 284 */     return Utils.clamp(0, (int)getHeight(), 255);
/*     */   }
/*     */ 
/*     */   private int getClampedIterations() {
/* 288 */     return Utils.clamp(0, getIterations(), 3);
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 293 */     Effect localEffect = getInput();
/* 294 */     if (localEffect != null) {
/* 295 */       localEffect.impl_sync();
/*     */     }
/*     */ 
/* 298 */     com.sun.scenario.effect.BoxBlur localBoxBlur = (com.sun.scenario.effect.BoxBlur)impl_getImpl();
/*     */ 
/* 300 */     localBoxBlur.setInput(localEffect == null ? null : localEffect.impl_getImpl());
/* 301 */     localBoxBlur.setHorizontalSize(getClampedWidth());
/* 302 */     localBoxBlur.setVerticalSize(getClampedHeight());
/* 303 */     localBoxBlur.setPasses(getClampedIterations());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 316 */     paramBaseBounds = EffectUtils.getInputBounds(paramBaseBounds, BaseTransform.IDENTITY_TRANSFORM, paramNode, paramBoundsAccessor, getInput());
/*     */ 
/* 321 */     int i = getClampedIterations();
/*     */ 
/* 323 */     int j = EffectUtils.getKernelSize(getClampedWidth(), i);
/* 324 */     int k = EffectUtils.getKernelSize(getClampedHeight(), i);
/*     */ 
/* 326 */     paramBaseBounds = paramBaseBounds.deriveWithPadding(j, k, 0.0F);
/*     */ 
/* 328 */     return EffectUtils.transformBounds(paramBaseTransform, paramBaseBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 339 */     BoxBlur localBoxBlur = new BoxBlur(getWidth(), getHeight(), getIterations());
/* 340 */     localBoxBlur.setInput(getInput());
/* 341 */     return localBoxBlur;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.BoxBlur
 * JD-Core Version:    0.6.2
 */