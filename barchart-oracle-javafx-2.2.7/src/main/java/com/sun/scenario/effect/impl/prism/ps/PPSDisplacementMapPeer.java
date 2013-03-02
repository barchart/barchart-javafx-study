/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.scenario.effect.DisplacementMap;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PPSDisplacementMapPeer extends PPSTwoSamplerPeer
/*     */ {
/*     */   public PPSDisplacementMapPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  45 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final DisplacementMap getEffect()
/*     */   {
/*  50 */     return (DisplacementMap)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float[] getSampletx()
/*     */   {
/*  55 */     return new float[] { getEffect().getOffsetX(), getEffect().getOffsetY(), getEffect().getScaleX(), getEffect().getScaleY() };
/*     */   }
/*     */ 
/*     */   private float[] getImagetx()
/*     */   {
/*  63 */     float f = getEffect().getWrap() ? 0.5F : 0.0F;
/*  64 */     return new float[] { f / getInputNativeBounds(0).width, f / getInputNativeBounds(0).height, (getInputBounds(0).width - 2.0F * f) / getInputNativeBounds(0).width, (getInputBounds(0).height - 2.0F * f) / getInputNativeBounds(0).height };
/*     */   }
/*     */ 
/*     */   private float getWrap()
/*     */   {
/*  72 */     return getEffect().getWrap() ? 1.0F : 0.0F;
/*     */   }
/*     */ 
/*     */   protected Object getSamplerData(int paramInt)
/*     */   {
/*  77 */     return getEffect().getMapData();
/*     */   }
/*     */ 
/*     */   public int getTextureCoordinates(int paramInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/*     */     float tmp5_4 = 0.0F; paramArrayOfFloat[1] = tmp5_4; paramArrayOfFloat[0] = tmp5_4;
/*     */     float tmp13_12 = 1.0F; paramArrayOfFloat[3] = tmp13_12; paramArrayOfFloat[2] = tmp13_12;
/*  90 */     return 4;
/*     */   }
/*     */ 
/*     */   protected boolean isSamplerLinear(int paramInt)
/*     */   {
/*  96 */     switch (paramInt) {
/*     */     case 1:
/*  98 */       return true;
/*     */     case 0:
/* 100 */       return true;
/*     */     }
/*     */ 
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   protected Shader createShader()
/*     */   {
/* 109 */     HashMap localHashMap1 = new HashMap();
/* 110 */     localHashMap1.put("mapImg", Integer.valueOf(1));
/* 111 */     localHashMap1.put("origImg", Integer.valueOf(0));
/*     */ 
/* 113 */     HashMap localHashMap2 = new HashMap();
/* 114 */     localHashMap2.put("wrap", Integer.valueOf(2));
/* 115 */     localHashMap2.put("imagetx", Integer.valueOf(1));
/* 116 */     localHashMap2.put("sampletx", Integer.valueOf(0));
/*     */ 
/* 118 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*     */   }
/*     */ 
/*     */   protected void updateShader(Shader paramShader)
/*     */   {
/* 124 */     paramShader.setConstant("wrap", getWrap());
/* 125 */     float[] arrayOfFloat1 = getImagetx();
/* 126 */     paramShader.setConstant("imagetx", arrayOfFloat1[0], arrayOfFloat1[1], arrayOfFloat1[2], arrayOfFloat1[3]);
/* 127 */     float[] arrayOfFloat2 = getSampletx();
/* 128 */     paramShader.setConstant("sampletx", arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat2[3]);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSDisplacementMapPeer
 * JD-Core Version:    0.6.2
 */