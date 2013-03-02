/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolveKernel;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolvePeer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PPSLinearConvolveShadowPeer extends PPSOneSamplerPeer
/*     */   implements LinearConvolvePeer
/*     */ {
/*     */   public PPSLinearConvolveShadowPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  45 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final Effect getEffect()
/*     */   {
/*  50 */     return super.getEffect();
/*     */   }
/*     */ 
/*     */   private LinearConvolveKernel getKernel()
/*     */   {
/*  55 */     return (LinearConvolveKernel)AccessHelper.getState(getEffect());
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleX(LinearConvolveKernel paramLinearConvolveKernel) {
/*  59 */     return paramLinearConvolveKernel.getPow2ScaleX();
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleY(LinearConvolveKernel paramLinearConvolveKernel) {
/*  63 */     return paramLinearConvolveKernel.getPow2ScaleY();
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  70 */     return getKernel().getScaledResultBounds(paramArrayOfImageData[0].getTransformedBounds(paramRectangle), getPass());
/*     */   }
/*     */ 
/*     */   private int getCount() {
/*  74 */     return (getKernel().getScaledKernelSize(getPass()) + 3) / 4;
/*     */   }
/*     */ 
/*     */   private float[] getOffset() {
/*  78 */     return getKernel().getVector(getInputNativeBounds(0), getInputTransform(0), getPass());
/*     */   }
/*     */ 
/*     */   private FloatBuffer getWeights() {
/*  82 */     return getKernel().getWeights(getPass());
/*     */   }
/*     */ 
/*     */   private int getWeightsArrayLength() {
/*  86 */     return getKernel().getWeightsArrayLength(getPass());
/*     */   }
/*     */ 
/*     */   private float[] getShadowColor() {
/*  90 */     return getKernel().getShadowColorComponents(getPass());
/*     */   }
/*     */ 
/*     */   protected boolean isSamplerLinear(int paramInt)
/*     */   {
/*  96 */     switch (paramInt) {
/*     */     }
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   protected Shader createShader()
/*     */   {
/* 104 */     HashMap localHashMap1 = new HashMap();
/* 105 */     localHashMap1.put("img", Integer.valueOf(0));
/*     */ 
/* 107 */     HashMap localHashMap2 = new HashMap();
/* 108 */     localHashMap2.put("count", Integer.valueOf(0));
/* 109 */     localHashMap2.put("weights", Integer.valueOf(3));
/* 110 */     localHashMap2.put("offset", Integer.valueOf(1));
/* 111 */     localHashMap2.put("shadowColor", Integer.valueOf(2));
/*     */ 
/* 113 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*     */   }
/*     */ 
/*     */   protected void updateShader(Shader paramShader)
/*     */   {
/* 119 */     paramShader.setConstant("count", getCount());
/* 120 */     paramShader.setConstants("weights", getWeights(), 0, getWeightsArrayLength());
/* 121 */     float[] arrayOfFloat1 = getOffset();
/* 122 */     paramShader.setConstant("offset", arrayOfFloat1[0], arrayOfFloat1[1], arrayOfFloat1[2], arrayOfFloat1[3]);
/* 123 */     float[] arrayOfFloat2 = getShadowColor();
/* 124 */     paramShader.setConstant("shadowColor", arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat2[3]);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSLinearConvolveShadowPeer
 * JD-Core Version:    0.6.2
 */