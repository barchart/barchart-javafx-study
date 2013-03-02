/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.IntegerPropertyBase;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ 
/*     */ public class FloatMap
/*     */ {
/*     */   private com.sun.scenario.effect.FloatMap map;
/*     */   private float[] buf;
/*  43 */   private boolean mapBufferDirty = true;
/*     */   private BooleanProperty effectDirty;
/*     */   private IntegerProperty width;
/*     */   private IntegerProperty height;
/*     */ 
/*     */   com.sun.scenario.effect.FloatMap getImpl()
/*     */   {
/*  46 */     return this.map;
/*     */   }
/*     */ 
/*     */   private void updateBuffer() {
/*  50 */     if ((getWidth() > 0) && (getHeight() > 0)) {
/*  51 */       int i = Utils.clampMax(getWidth(), 4096);
/*  52 */       int j = Utils.clampMax(getHeight(), 4096);
/*  53 */       int k = i * j * 4;
/*  54 */       this.buf = new float[k];
/*  55 */       this.mapBufferDirty = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void impl_update() {
/*  60 */     if (this.mapBufferDirty) {
/*  61 */       this.map = new com.sun.scenario.effect.FloatMap(Utils.clamp(1, getWidth(), 4096), Utils.clamp(1, getHeight(), 4096));
/*     */ 
/*  64 */       this.mapBufferDirty = false;
/*     */     }
/*  66 */     this.map.put(this.buf);
/*     */   }
/*     */ 
/*     */   void impl_sync() {
/*  70 */     if (impl_isEffectDirty()) {
/*  71 */       impl_update();
/*  72 */       impl_clearDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setEffectDirty(boolean paramBoolean)
/*     */   {
/*  79 */     effectDirtyProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   final BooleanProperty effectDirtyProperty() {
/*  83 */     if (this.effectDirty == null) {
/*  84 */       this.effectDirty = new SimpleBooleanProperty(this, "effectDirty");
/*     */     }
/*  86 */     return this.effectDirty;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   boolean impl_isEffectDirty()
/*     */   {
/*  95 */     return this.effectDirty == null ? false : this.effectDirty.get();
/*     */   }
/*     */ 
/*     */   private void impl_markDirty() {
/*  99 */     setEffectDirty(true);
/*     */   }
/*     */ 
/*     */   private void impl_clearDirty() {
/* 103 */     setEffectDirty(false);
/*     */   }
/*     */ 
/*     */   public FloatMap()
/*     */   {
/* 110 */     updateBuffer();
/* 111 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public FloatMap(int paramInt1, int paramInt2)
/*     */   {
/* 120 */     setWidth(paramInt1);
/* 121 */     setHeight(paramInt2);
/* 122 */     updateBuffer();
/* 123 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public final void setWidth(int paramInt)
/*     */   {
/* 140 */     widthProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getWidth() {
/* 144 */     return this.width == null ? 1 : this.width.get();
/*     */   }
/*     */ 
/*     */   public final IntegerProperty widthProperty() {
/* 148 */     if (this.width == null) {
/* 149 */       this.width = new IntegerPropertyBase(1)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 153 */           FloatMap.this.updateBuffer();
/* 154 */           FloatMap.this.impl_markDirty();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 159 */           return FloatMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 164 */           return "width";
/*     */         }
/*     */       };
/*     */     }
/* 168 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final void setHeight(int paramInt)
/*     */   {
/* 185 */     heightProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getHeight() {
/* 189 */     return this.height == null ? 1 : this.height.get();
/*     */   }
/*     */ 
/*     */   public final IntegerProperty heightProperty() {
/* 193 */     if (this.height == null) {
/* 194 */       this.height = new IntegerPropertyBase(1)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 198 */           FloatMap.this.updateBuffer();
/* 199 */           FloatMap.this.impl_markDirty();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 204 */           return FloatMap.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 209 */           return "height";
/*     */         }
/*     */       };
/*     */     }
/* 213 */     return this.height;
/*     */   }
/*     */ 
/*     */   public void setSample(int paramInt1, int paramInt2, int paramInt3, float paramFloat)
/*     */   {
/* 225 */     this.buf[((paramInt1 + paramInt2 * getWidth()) * 4 + paramInt3)] = paramFloat;
/* 226 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat)
/*     */   {
/* 238 */     int i = (paramInt1 + paramInt2 * getWidth()) * 4;
/* 239 */     this.buf[(i + 0)] = paramFloat;
/* 240 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
/*     */   {
/* 253 */     int i = (paramInt1 + paramInt2 * getWidth()) * 4;
/* 254 */     this.buf[(i + 0)] = paramFloat1;
/* 255 */     this.buf[(i + 1)] = paramFloat2;
/* 256 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 270 */     int i = (paramInt1 + paramInt2 * getWidth()) * 4;
/* 271 */     this.buf[(i + 0)] = paramFloat1;
/* 272 */     this.buf[(i + 1)] = paramFloat2;
/* 273 */     this.buf[(i + 2)] = paramFloat3;
/* 274 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   public void setSamples(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 290 */     int i = (paramInt1 + paramInt2 * getWidth()) * 4;
/* 291 */     this.buf[(i + 0)] = paramFloat1;
/* 292 */     this.buf[(i + 1)] = paramFloat2;
/* 293 */     this.buf[(i + 2)] = paramFloat3;
/* 294 */     this.buf[(i + 3)] = paramFloat4;
/* 295 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public FloatMap impl_copy()
/*     */   {
/* 304 */     FloatMap localFloatMap = new FloatMap(getWidth(), getHeight());
/* 305 */     System.arraycopy(this.buf, 0, localFloatMap.buf, 0, this.buf.length);
/* 306 */     return localFloatMap;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.FloatMap
 * JD-Core Version:    0.6.2
 */