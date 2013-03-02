/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.SepiaTone;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PPSSepiaTonePeer extends PPSOneSamplerPeer
/*    */ {
/*    */   public PPSSepiaTonePeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 45 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   protected final SepiaTone getEffect()
/*    */   {
/* 50 */     return (SepiaTone)super.getEffect();
/*    */   }
/*    */ 
/*    */   private float getLevel()
/*    */   {
/* 55 */     return getEffect().getLevel();
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
/* 70 */     localHashMap1.put("baseImg", Integer.valueOf(0));
/*    */ 
/* 72 */     HashMap localHashMap2 = new HashMap();
/* 73 */     localHashMap2.put("level", Integer.valueOf(0));
/*    */ 
/* 75 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*    */   }
/*    */ 
/*    */   protected void updateShader(Shader paramShader)
/*    */   {
/* 81 */     paramShader.setConstant("level", getLevel());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSSepiaTonePeer
 * JD-Core Version:    0.6.2
 */