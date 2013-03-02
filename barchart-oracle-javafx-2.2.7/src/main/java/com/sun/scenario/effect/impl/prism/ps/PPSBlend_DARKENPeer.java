/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.scenario.effect.Blend;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PPSBlend_DARKENPeer extends PPSTwoSamplerPeer
/*    */ {
/*    */   public PPSBlend_DARKENPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 45 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   protected final Blend getEffect()
/*    */   {
/* 50 */     return (Blend)super.getEffect();
/*    */   }
/*    */ 
/*    */   private float getOpacity()
/*    */   {
/* 55 */     return getEffect().getOpacity();
/*    */   }
/*    */ 
/*    */   protected boolean isSamplerLinear(int paramInt)
/*    */   {
/* 61 */     switch (paramInt) {
/*    */     }
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   protected Shader createShader()
/*    */   {
/* 69 */     HashMap localHashMap1 = new HashMap();
/* 70 */     localHashMap1.put("topImg", Integer.valueOf(1));
/* 71 */     localHashMap1.put("botImg", Integer.valueOf(0));
/*    */ 
/* 73 */     HashMap localHashMap2 = new HashMap();
/* 74 */     localHashMap2.put("opacity", Integer.valueOf(0));
/*    */ 
/* 76 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*    */   }
/*    */ 
/*    */   protected void updateShader(Shader paramShader)
/*    */   {
/* 82 */     paramShader.setConstant("opacity", getOpacity());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSBlend_DARKENPeer
 * JD-Core Version:    0.6.2
 */