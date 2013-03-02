/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Brightpass extends CoreEffect
/*     */ {
/*     */   private float threshold;
/*     */ 
/*     */   public Brightpass()
/*     */   {
/*  48 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public Brightpass(Effect paramEffect)
/*     */   {
/*  59 */     super(paramEffect);
/*  60 */     setThreshold(0.3F);
/*  61 */     updatePeerKey("Brightpass");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  70 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  80 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getThreshold()
/*     */   {
/*  91 */     return this.threshold;
/*     */   }
/*     */ 
/*     */   public void setThreshold(float paramFloat)
/*     */   {
/* 110 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 111 */       throw new IllegalArgumentException("Threshold must be in the range [0,1]");
/*     */     }
/* 113 */     float f = this.threshold;
/* 114 */     this.threshold = paramFloat;
/* 115 */     firePropertyChange("threshold", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 126 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Brightpass
 * JD-Core Version:    0.6.2
 */