/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Reflection extends CoreEffect
/*     */ {
/*     */   private float topOffset;
/*     */   private float topOpacity;
/*     */   private float bottomOpacity;
/*     */   private float fraction;
/*     */ 
/*     */   public Reflection()
/*     */   {
/*  54 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public Reflection(Effect paramEffect)
/*     */   {
/*  63 */     super(paramEffect);
/*  64 */     this.topOffset = 0.0F;
/*  65 */     this.topOpacity = 0.5F;
/*  66 */     this.bottomOpacity = 0.0F;
/*  67 */     this.fraction = 0.75F;
/*  68 */     updatePeerKey("Reflection");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  77 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  88 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getTopOffset()
/*     */   {
/*  98 */     return this.topOffset;
/*     */   }
/*     */ 
/*     */   public void setTopOffset(float paramFloat)
/*     */   {
/* 114 */     float f = this.topOffset;
/* 115 */     this.topOffset = paramFloat;
/* 116 */     firePropertyChange("topOffset", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getTopOpacity()
/*     */   {
/* 126 */     return this.topOpacity;
/*     */   }
/*     */ 
/*     */   public void setTopOpacity(float paramFloat)
/*     */   {
/* 144 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 145 */       throw new IllegalArgumentException("Top opacity must be in the range [0,1]");
/*     */     }
/* 147 */     float f = this.topOpacity;
/* 148 */     this.topOpacity = paramFloat;
/* 149 */     firePropertyChange("topOpacity", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getBottomOpacity()
/*     */   {
/* 159 */     return this.bottomOpacity;
/*     */   }
/*     */ 
/*     */   public void setBottomOpacity(float paramFloat)
/*     */   {
/* 177 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 178 */       throw new IllegalArgumentException("Bottom opacity must be in the range [0,1]");
/*     */     }
/* 180 */     float f = this.bottomOpacity;
/* 181 */     this.bottomOpacity = paramFloat;
/* 182 */     firePropertyChange("bottomOpacity", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public float getFraction()
/*     */   {
/* 191 */     return this.fraction;
/*     */   }
/*     */ 
/*     */   public void setFraction(float paramFloat)
/*     */   {
/* 211 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 212 */       throw new IllegalArgumentException("Fraction must be in the range [0,1]");
/*     */     }
/* 214 */     float f = this.fraction;
/* 215 */     this.fraction = paramFloat;
/* 216 */     firePropertyChange("fraction", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */   {
/* 223 */     Effect localEffect = getDefaultedInput(0, paramEffect);
/* 224 */     BaseBounds localBaseBounds = localEffect.getBounds(BaseTransform.IDENTITY_TRANSFORM, paramEffect);
/* 225 */     localBaseBounds.roundOut();
/* 226 */     float f1 = localBaseBounds.getMinX();
/* 227 */     float f2 = localBaseBounds.getMaxY() + this.topOffset;
/* 228 */     float f3 = localBaseBounds.getMaxX();
/* 229 */     float f4 = f2 + this.fraction * localBaseBounds.getHeight();
/* 230 */     Object localObject = new RectBounds(f1, f2, f3, f4);
/* 231 */     localObject = ((BaseBounds)localObject).deriveWithUnion(localBaseBounds);
/* 232 */     return transformBounds(paramBaseTransform, (BaseBounds)localObject);
/*     */   }
/*     */ 
/*     */   public boolean operatesInUserSpace()
/*     */   {
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 242 */     return getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 247 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 258 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Reflection
 * JD-Core Version:    0.6.2
 */