/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SepiaTone extends CoreEffect
/*     */ {
/*     */   private float level;
/*     */ 
/*     */   public SepiaTone()
/*     */   {
/*  47 */     this(DefaultInput);
/*     */   }
/*     */ 
/*     */   public SepiaTone(Effect paramEffect)
/*     */   {
/*  57 */     super(paramEffect);
/*  58 */     setLevel(1.0F);
/*  59 */     updatePeerKey("SepiaTone");
/*     */   }
/*     */ 
/*     */   public final Effect getInput()
/*     */   {
/*  68 */     return (Effect)getInputs().get(0);
/*     */   }
/*     */ 
/*     */   public void setInput(Effect paramEffect)
/*     */   {
/*  79 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   public float getLevel()
/*     */   {
/*  89 */     return this.level;
/*     */   }
/*     */ 
/*     */   public void setLevel(float paramFloat)
/*     */   {
/* 106 */     if ((paramFloat < 0.0F) || (paramFloat > 1.0F)) {
/* 107 */       throw new IllegalArgumentException("Level must be in the range [0,1]");
/*     */     }
/* 109 */     float f = this.level;
/* 110 */     this.level = paramFloat;
/* 111 */     firePropertyChange("level", Float.valueOf(f), Float.valueOf(paramFloat));
/*     */   }
/*     */ 
/*     */   protected Rectangle getInputClip(int paramInt, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 122 */     return paramRectangle;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.SepiaTone
 * JD-Core Version:    0.6.2
 */