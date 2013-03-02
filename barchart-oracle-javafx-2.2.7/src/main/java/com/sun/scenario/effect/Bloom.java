/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Bloom extends DelegateEffect
/*     */ {
/*     */   private final Brightpass brightpass;
/*     */   private final GaussianBlur blur;
/*     */   private final Blend blend;
/*     */ 
/*     */   public Bloom()
/*     */   {
/*  49 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public Bloom(Effect paramEffect)
/*     */   {
/*  59 */     super(paramEffect);
/*     */ 
/*  73 */     this.brightpass = new Brightpass(paramEffect);
/*  74 */     this.blur = new GaussianBlur(10.0F, this.brightpass);
/*     */ 
/*  76 */     Crop localCrop = new Crop(this.blur, paramEffect);
/*  77 */     this.blend = new Blend(Blend.Mode.ADD, paramEffect, localCrop);
/*     */   }
/*     */ 
/*     */   protected Effect getDelegate() {
/*  81 */     return this.blend;
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  90 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/* 101 */     setInput(0, paramEffect);
/* 102 */     this.brightpass.setInput(paramEffect);
/* 103 */     this.blend.setBottomInput(paramEffect);
/*     */   }
/*     */ 
/*     */   public float getThreshold()
/*     */   {
/* 112 */     return this.brightpass.getThreshold();
/*     */   }
/*     */ 
/*     */   public void setThreshold(float paramFloat)
/*     */   {
/* 129 */     float f = this.brightpass.getThreshold();
/* 130 */     this.brightpass.setThreshold(paramFloat);
/* 131 */     firePropertyChange("threshold", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 136 */     return getDefaultedInput(0, paramEffect).transform(paramPoint2D, paramEffect);
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 141 */     return getDefaultedInput(0, paramEffect).untransform(paramPoint2D, paramEffect);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Bloom
 * JD-Core Version:    0.6.2
 */