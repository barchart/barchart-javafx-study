/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.scenario.effect.ColorAdjust;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PPSColorAdjustPeer extends PPSOneSamplerPeer
/*     */ {
/*     */   public PPSColorAdjustPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  45 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final ColorAdjust getEffect()
/*     */   {
/*  50 */     return (ColorAdjust)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getHue()
/*     */   {
/*  55 */     return getEffect().getHue() / 2.0F;
/*     */   }
/*     */ 
/*     */   private float getSaturation() {
/*  59 */     return getEffect().getSaturation() + 1.0F;
/*     */   }
/*     */ 
/*     */   private float getBrightness() {
/*  63 */     return getEffect().getBrightness() + 1.0F;
/*     */   }
/*     */ 
/*     */   private float getContrast() {
/*  67 */     float f = getEffect().getContrast();
/*  68 */     if (f > 0.0F) f *= 3.0F;
/*  69 */     return f + 1.0F;
/*     */   }
/*     */ 
/*     */   protected boolean isSamplerLinear(int paramInt)
/*     */   {
/*  75 */     switch (paramInt) {
/*     */     }
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   protected Shader createShader()
/*     */   {
/*  83 */     HashMap localHashMap1 = new HashMap();
/*  84 */     localHashMap1.put("baseImg", Integer.valueOf(0));
/*     */ 
/*  86 */     HashMap localHashMap2 = new HashMap();
/*  87 */     localHashMap2.put("contrast", Integer.valueOf(3));
/*  88 */     localHashMap2.put("brightness", Integer.valueOf(2));
/*  89 */     localHashMap2.put("saturation", Integer.valueOf(1));
/*  90 */     localHashMap2.put("hue", Integer.valueOf(0));
/*     */ 
/*  92 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*     */   }
/*     */ 
/*     */   protected void updateShader(Shader paramShader)
/*     */   {
/*  98 */     paramShader.setConstant("contrast", getContrast());
/*  99 */     paramShader.setConstant("brightness", getBrightness());
/* 100 */     paramShader.setConstant("saturation", getSaturation());
/* 101 */     paramShader.setConstant("hue", getHue());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSColorAdjustPeer
 * JD-Core Version:    0.6.2
 */