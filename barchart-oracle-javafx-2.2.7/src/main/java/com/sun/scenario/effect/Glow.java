/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Glow extends DelegateEffect
/*     */ {
/*     */   private final GaussianBlur blur;
/*     */   private final Blend blend;
/*     */ 
/*     */   public Glow()
/*     */   {
/*  48 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public Glow(Effect paramEffect)
/*     */   {
/*  57 */     super(paramEffect);
/*     */ 
/*  69 */     this.blur = new GaussianBlur(10.0F, paramEffect);
/*     */ 
/*  71 */     Crop localCrop = new Crop(this.blur, paramEffect);
/*  72 */     this.blend = new Blend(Blend.Mode.ADD, paramEffect, localCrop);
/*  73 */     this.blend.setOpacity(0.3F);
/*     */   }
/*     */ 
/*     */   protected Effect getDelegate() {
/*  77 */     return this.blend;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  86 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  97 */     setInput(0, paramEffect);
/*  98 */     this.blur.setInput(paramEffect);
/*  99 */     this.blend.setBottomInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public float getLevel()
/*     */   {
/* 108 */     return this.blend.getOpacity();
/*     */   }
/*     */ 
/*     */   public void setLevel(float paramFloat)
/*     */   {
/* 125 */     float f = this.blend.getOpacity();
/* 126 */     this.blend.setOpacity(paramFloat);
/* 127 */     firePropertyChange("level", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 132 */     return getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 137 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Glow
 * JD-Core Version:    0.6.2
 */