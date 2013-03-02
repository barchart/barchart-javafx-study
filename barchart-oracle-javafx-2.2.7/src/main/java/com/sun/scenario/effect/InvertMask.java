/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class InvertMask extends CoreEffect
/*     */ {
/*     */   private int pad;
/*     */   private int xoff;
/*     */   private int yoff;
/*     */ 
/*     */   public InvertMask()
/*     */   {
/*  53 */     this(10);
/*     */   }
/*     */ 
/*     */   public InvertMask(Effect paramEffect)
/*     */   {
/*  67 */     this(10, paramEffect);
/*     */   }
/*     */ 
/*     */   public InvertMask(int paramInt)
/*     */   {
/*  78 */     this(paramInt, DefaultInput);
/*     */   }
/*     */ 
/*     */   public InvertMask(int paramInt, Effect paramEffect)
/*     */   {
/*  90 */     super(paramEffect);
/*  91 */     setPad(paramInt);
/*  92 */     updatePeerKey("InvertMask");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/* 101 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 112 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public int getPad()
/*     */   {
/* 122 */     return this.pad;
/*     */   }
/*     */ 
/*     */   public void setPad(int paramInt)
/*     */   {
/* 139 */     if (paramInt < 0) {
/* 140 */       throw new IllegalArgumentException("Pad value must be non-negative");
/*     */     }
/* 142 */     int i = this.pad;
/* 143 */     this.pad = paramInt;
/* 144 */     firePropertyChange("pad", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getOffsetX()
/*     */   {
/* 153 */     return this.xoff;
/*     */   }
/*     */ 
/*     */   public void setOffsetX(int paramInt)
/*     */   {
/* 168 */     int i = this.xoff;
/* 169 */     this.xoff = paramInt;
/* 170 */     firePropertyChange("offsetX", Integer.valueOf(i), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public int getOffsetY()
/*     */   {
/* 179 */     return this.yoff;
/*     */   }
/*     */ 
/*     */   public void setOffsetY(int paramInt)
/*     */   {
/* 194 */     float f = this.yoff;
/* 195 */     this.yoff = paramInt;
/* 196 */     firePropertyChange("offsetY", Float.valueOf(f), Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 201 */     BaseBounds localBaseBounds = super.getBounds(BaseTransform.IDENTITY_TRANSFORM, paramEffect);
/* 202 */     Object localObject = new RectBounds(localBaseBounds.getMinX(), localBaseBounds.getMinY(), localBaseBounds.getMaxX(), localBaseBounds.getMaxY());
/*     */ 
/* 204 */     ((RectBounds)localObject).grow(this.pad, this.pad);
/* 205 */     if (!paramBaseTransform.isIdentity()) {
/* 206 */       localObject = transformBounds(paramBaseTransform, (BaseBounds)localObject);
/*     */     }
/* 208 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 216 */     Rectangle localRectangle1 = super.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 217 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 218 */     localRectangle2.grow(this.pad, this.pad);
/* 219 */     return localRectangle2;
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 236 */     if ((paramRectangle != null) && 
/* 237 */       (this.pad != 0)) {
/* 238 */       paramRectangle = new Rectangle(paramRectangle);
/* 239 */       paramRectangle.grow(this.pad, this.pad);
/*     */     }
/*     */ 
/* 242 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.InvertMask
 * JD-Core Version:    0.6.2
 */