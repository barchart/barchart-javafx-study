/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Blend extends CoreEffect
/*     */ {
/*     */   private Mode mode;
/*     */   private float opacity;
/*     */ 
/*     */   public Blend(Mode paramMode, Effect paramEffect1, Effect paramEffect2)
/*     */   {
/* 422 */     super(paramEffect1, paramEffect2);
/* 423 */     setMode(paramMode);
/* 424 */     setOpacity(1.0F);
/*     */   }
/*     */ 
/*     */   public final Effect getBottomInput()
/*     */   {
/* 433 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setBottomInput(Effect paramEffect)
/*     */   {
/* 444 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public final Effect getTopInput()
/*     */   {
/* 453 */     return (Effect)getInputs().get(1);
/*     */   }
/*     */ 
/*     */   public void setTopInput(Effect paramEffect)
/*     */   {
/* 464 */     setInput(1, paramEffect);
/*     */   }
/*     */ 
/*     */   public Mode getMode()
/*     */   {
/* 473 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public void setMode(Mode paramMode)
/*     */   {
/* 489 */     if (paramMode == null) {
/* 490 */       throw new IllegalArgumentException("Mode must be non-null");
/*     */     }
/* 492 */     Mode localMode = this.mode;
/* 493 */     this.mode = paramMode;
/* 494 */     updatePeerKey("Blend_" + paramMode.name());
/* 495 */     firePropertyChange("mode", localMode, paramMode);
/*     */   }
/*     */ 
/*     */   public float getOpacity()
/*     */   {
/* 505 */     return this.opacity;
/*     */   }
/*     */ 
/*     */   public void setOpacity(float paramFloat)
/*     */   {
/* 523 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 524 */       throw new IllegalArgumentException("Opacity must be in the range [0,1]");
/*     */     }
/* 526 */     float f = this.opacity;
/* 527 */     this.opacity = paramFloat;
/* 528 */     firePropertyChange("opacity", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 551 */     return getDefaultedInput(1, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 575 */     return getDefaultedInput(1, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 589 */     return paramRectangle;
/*     */   }
/*     */ 
/*     */   public static enum Mode
/*     */   {
/*  55 */     SRC_OVER, 
/*     */ 
/*  68 */     SRC_IN, 
/*     */ 
/*  82 */     SRC_OUT, 
/*     */ 
/*  95 */     SRC_ATOP, 
/*     */ 
/* 117 */     ADD, 
/*     */ 
/* 144 */     MULTIPLY, 
/*     */ 
/* 172 */     SCREEN, 
/*     */ 
/* 196 */     OVERLAY, 
/*     */ 
/* 220 */     DARKEN, 
/*     */ 
/* 244 */     LIGHTEN, 
/*     */ 
/* 258 */     COLOR_DODGE, 
/*     */ 
/* 273 */     COLOR_BURN, 
/*     */ 
/* 295 */     HARD_LIGHT, 
/*     */ 
/* 300 */     SOFT_LIGHT, 
/*     */ 
/* 326 */     DIFFERENCE, 
/*     */ 
/* 353 */     EXCLUSION, 
/*     */ 
/* 370 */     RED, 
/*     */ 
/* 387 */     GREEN, 
/*     */ 
/* 404 */     BLUE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Blend
 * JD-Core Version:    0.6.2
 */