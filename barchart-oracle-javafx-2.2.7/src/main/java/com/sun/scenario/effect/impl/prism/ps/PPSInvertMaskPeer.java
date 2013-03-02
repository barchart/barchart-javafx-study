/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.InvertMask;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class PPSInvertMaskPeer extends PPSOneSamplerPeer
/*    */ {
/*    */   public PPSInvertMaskPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 45 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   protected final InvertMask getEffect()
/*    */   {
/* 50 */     return (InvertMask)super.getEffect();
/*    */   }
/*    */ 
/*    */   private float[] getOffset()
/*    */   {
/* 55 */     float f1 = getEffect().getOffsetX();
/* 56 */     float f2 = getEffect().getOffsetY();
/* 57 */     return new float[] { f1 / getInputNativeBounds(0).width, f2 / getInputNativeBounds(0).height };
/*    */   }
/*    */ 
/*    */   protected boolean isSamplerLinear(int paramInt)
/*    */   {
/* 66 */     switch (paramInt) {
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   protected Shader createShader()
/*    */   {
/* 74 */     HashMap localHashMap1 = new HashMap();
/* 75 */     localHashMap1.put("baseImg", Integer.valueOf(0));
/*    */ 
/* 77 */     HashMap localHashMap2 = new HashMap();
/* 78 */     localHashMap2.put("offset", Integer.valueOf(0));
/*    */ 
/* 80 */     return getRenderer().createShader(getShaderName(), localHashMap1, localHashMap2, false);
/*    */   }
/*    */ 
/*    */   protected void updateShader(Shader paramShader)
/*    */   {
/* 86 */     float[] arrayOfFloat = getOffset();
/* 87 */     paramShader.setConstant("offset", arrayOfFloat[0], arrayOfFloat[1]);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSInvertMaskPeer
 * JD-Core Version:    0.6.2
 */