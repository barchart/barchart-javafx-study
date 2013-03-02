/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.effect.EffectDirtyBits;
/*     */ import com.sun.javafx.effect.EffectUtils;
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.BoundsAccessor;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.scenario.effect.Identity;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public class ImageInput extends Effect
/*     */ {
/*     */   private ObjectProperty<Image> source;
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public ImageInput()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ImageInput(Image paramImage)
/*     */   {
/*  59 */     setSource(paramImage);
/*     */   }
/*     */ 
/*     */   public ImageInput(Image paramImage, double paramDouble1, double paramDouble2)
/*     */   {
/*  69 */     setSource(paramImage);
/*  70 */     setX(paramDouble1);
/*  71 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   Identity impl_createImpl()
/*     */   {
/*  76 */     return new Identity(null);
/*     */   }
/*     */ 
/*     */   public final void setSource(Image paramImage)
/*     */   {
/*  85 */     sourceProperty().set(paramImage);
/*     */   }
/*     */ 
/*     */   public final Image getSource() {
/*  89 */     return this.source == null ? null : (Image)this.source.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Image> sourceProperty() {
/*  93 */     if (this.source == null) {
/*  94 */       this.source = new ObjectPropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  98 */           ImageInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/*  99 */           ImageInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 104 */           return ImageInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 109 */           return "source";
/*     */         }
/*     */       };
/*     */     }
/* 113 */     return this.source;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 131 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/* 135 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 139 */     if (this.x == null) {
/* 140 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 144 */           ImageInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 145 */           ImageInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 150 */           return ImageInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 155 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 159 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 177 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/* 181 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 185 */     if (this.y == null) {
/* 186 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 190 */           ImageInput.this.markDirty(EffectDirtyBits.EFFECT_DIRTY);
/* 191 */           ImageInput.this.effectBoundsChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 196 */           return ImageInput.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 201 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 205 */     return this.y;
/*     */   }
/*     */ 
/*     */   void impl_update()
/*     */   {
/* 210 */     Identity localIdentity = (Identity)impl_getImpl();
/*     */ 
/* 212 */     Image localImage = getSource();
/* 213 */     if ((localImage != null) && (localImage.impl_getPlatformImage() != null))
/* 214 */       localIdentity.setSource(Toolkit.getToolkit().toFilterable(localImage));
/*     */     else {
/* 216 */       localIdentity.setSource(null);
/*     */     }
/* 218 */     localIdentity.setLocation(new Point2D((float)getX(), (float)getY()));
/*     */   }
/*     */ 
/*     */   boolean impl_checkChainContains(Effect paramEffect)
/*     */   {
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_getBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform, Node paramNode, BoundsAccessor paramBoundsAccessor)
/*     */   {
/* 236 */     Image localImage = getSource();
/* 237 */     if ((localImage != null) && (localImage.impl_getPlatformImage() != null)) {
/* 238 */       float f1 = (float)getX();
/* 239 */       float f2 = (float)getY();
/* 240 */       float f3 = (float)localImage.getWidth();
/* 241 */       float f4 = (float)localImage.getHeight();
/* 242 */       RectBounds localRectBounds = new RectBounds(f1, f2, f1 + f3, f2 + f4);
/*     */ 
/* 245 */       return EffectUtils.transformBounds(paramBaseTransform, localRectBounds);
/*     */     }
/* 247 */     return new RectBounds();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Effect impl_copy()
/*     */   {
/* 259 */     return new ImageInput(getSource(), getX(), getY());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.ImageInput
 * JD-Core Version:    0.6.2
 */