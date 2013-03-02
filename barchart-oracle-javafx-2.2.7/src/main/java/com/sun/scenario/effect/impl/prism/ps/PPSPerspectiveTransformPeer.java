/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.PerspectiveTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper;
/*     */ import com.sun.scenario.effect.impl.state.PerspectiveTransformState;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PPSPerspectiveTransformPeer extends PPSOneSamplerPeer
/*     */ {
/*     */   public PPSPerspectiveTransformPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  45 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final PerspectiveTransform getEffect()
/*     */   {
/*  50 */     return (PerspectiveTransform)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float[][] getITX()
/*     */   {
/*  55 */     PerspectiveTransformState localPerspectiveTransformState = (PerspectiveTransformState)AccessHelper.getState(getEffect());
/*     */ 
/*  57 */     return localPerspectiveTransformState.getITX();
/*     */   }
/*     */ 
/*     */   private float[] getTx0() {
/*  61 */     Rectangle localRectangle1 = getInputBounds(0);
/*  62 */     Rectangle localRectangle2 = getInputNativeBounds(0);
/*  63 */     float f = localRectangle1.width / localRectangle2.width;
/*  64 */     float[] arrayOfFloat = getITX()[0];
/*  65 */     return new float[] { arrayOfFloat[0] * f, arrayOfFloat[1] * f, arrayOfFloat[2] * f };
/*     */   }
/*     */   private float[] getTx1() {
/*  68 */     Rectangle localRectangle1 = getInputBounds(0);
/*  69 */     Rectangle localRectangle2 = getInputNativeBounds(0);
/*  70 */     float f = localRectangle1.height / localRectangle2.height;
/*  71 */     float[] arrayOfFloat = getITX()[1];
/*  72 */     return new float[] { arrayOfFloat[0] * f, arrayOfFloat[1] * f, arrayOfFloat[2] * f };
/*     */   }
/*     */   private float[] getTx2() {
/*  75 */     return getITX()[2];
/*     */   }
/*     */ 
/*     */   public int getTextureCoordinates(int paramInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/*  86 */     paramArrayOfFloat[0] = paramRectangle.x;
/*  87 */     paramArrayOfFloat[1] = paramRectangle.y;
/*  88 */     paramArrayOfFloat[2] = (paramRectangle.x + paramRectangle.width);
/*  89 */     paramArrayOfFloat[3] = (paramRectangle.y + paramRectangle.height);
/*  90 */     return 4;
/*     */   }
/*     */ 
/*     */   protected boolean isSamplerLinear(int paramInt)
/*     */   {
/*  96 */     switch (paramInt) {
/*     */     case 0:
/*  98 */       return true;
/*     */     }
/*     */ 
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */   protected Shader createShader()
/*     */   {
/* 107 */     HashMap localHashMap1 = new HashMap();
/* 108 */     localHashMap1.put("baseImg", Integer.valueOf(0));
/*     */ 
/* 110 */     HashMap localHashMap2 = new HashMap();
/* 111 */     localHashMap2.put("tx2", Integer.valueOf(2));
/* 112 */     localHashMap2.put("tx1", Integer.valueOf(1));
/* 113 */     localHashMap2.put("tx0", Integer.valueOf(0));
/*     */ 
/* 115 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*     */   }
/*     */ 
/*     */   protected void updateShader(Shader paramShader)
/*     */   {
/* 121 */     float[] arrayOfFloat1 = getTx2();
/* 122 */     paramShader.setConstant("tx2", arrayOfFloat1[0], arrayOfFloat1[1], arrayOfFloat1[2]);
/* 123 */     float[] arrayOfFloat2 = getTx1();
/* 124 */     paramShader.setConstant("tx1", arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2]);
/* 125 */     float[] arrayOfFloat3 = getTx0();
/* 126 */     paramShader.setConstant("tx0", arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2]);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSPerspectiveTransformPeer
 * JD-Core Version:    0.6.2
 */