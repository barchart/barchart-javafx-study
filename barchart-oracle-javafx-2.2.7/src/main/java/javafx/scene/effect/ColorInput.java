/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.scenario.effect.Flood;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public class ColorInput extends Effect
/*     */ {
/*     */   private ObjectProperty<Paint> paint;
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */   private DoubleProperty width;
/*     */   private DoubleProperty height;
/*     */ 
/*     */   public ColorInput()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ColorInput(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Paint paramPaint)
/*     */   {
/*  70 */     setX(paramDouble1);
/*  71 */     setY(paramDouble2);
/*  72 */     setWidth(paramDouble3);
/*  73 */     setHeight(paramDouble4);
/*  74 */     setPaint(paramPaint);
/*     */   }
/*     */ 
/*     */   Flood impl_createImpl()
/*     */   {
/*  79 */     return new Flood(Color.RED.impl_getPlatformPaint());
/*     */   }
/*     */ 
/*     */   public final void setPaint(Paint paramPaint)
/*     */   {
/*  95 */     paintProperty().set(paramPaint);
/*     */   }
/*     */ 
/*     */   public final Paint getPaint() {
/*  99 */     return this.paint == null ? Color.RED : (Paint)this.paint.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Paint> paintProperty() {
/* 103 */     if (this.paint == null) {
/* 104 */       this.paint = new ObjectPropertyBase(Color.RED)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 108 */           ColorInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 113 */           return ColorInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 118 */           return "paint";
/*     */         }
/*     */       };
/*     */     }
/* 122 */     return this.paint;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 140 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/* 144 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 148 */     if (this.x == null) {
/* 149 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 153 */           ColorInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 154 */           ColorInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 159 */           return ColorInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 164 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 168 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 186 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/* 190 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 194 */     if (this.y == null) {
/* 195 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 199 */           ColorInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 200 */           ColorInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 205 */           return ColorInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 210 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 214 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final void setWidth(double paramDouble)
/*     */   {
/* 232 */     widthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getWidth() {
/* 236 */     return this.width == null ? 0.0D : this.width.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty widthProperty() {
/* 240 */     if (this.width == null) {
/* 241 */       this.width = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 245 */           ColorInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 246 */           ColorInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 251 */           return ColorInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 256 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 260 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final void setHeight(double paramDouble)
/*     */   {
/* 278 */     heightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getHeight() {
/* 282 */     return this.height == null ? 0.0D : this.height.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty heightProperty() {
/* 286 */     if (this.height == null) {
/* 287 */       this.height = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 291 */           ColorInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 292 */           ColorInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 297 */           return ColorInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 302 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 306 */     return this.height;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 311 */     Flood localFlood = (Flood)impl_getImpl();
/*     */ 
/* 313 */     localFlood.setPaint(getPaint().impl_getPlatformPaint());
/* 314 */     localFlood.setFloodBounds(new RectBounds((float)getX(), (float)getY(), (float)(getX() + getWidth()), (float)(getY() + getHeight())));
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 322 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 335 */     RectBounds localRectBounds = new RectBounds((float)getX(), (float)getY(), (float)(getX() + getWidth()), (float)(getY() + getHeight()));
/*     */ 
/* 339 */     return EffectUtils.transformBounds(paramBaseTransform, localRectBounds);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 350 */     return new ColorInput(getX(), getY(), getWidth(), getHeight(), getPaint());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ColorInput
 * JD-Core Version:    0.6.2
 */