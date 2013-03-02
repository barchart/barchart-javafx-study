/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.glass.ui.Screen;
/*    */ import com.sun.prism.GraphicsPipeline;
/*    */ import com.sun.prism.GraphicsPipeline.ShaderModel;
/*    */ import com.sun.prism.RTTexture;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class PrRenderer extends Renderer
/*    */ {
/* 29 */   private static final Set<String> intrinsicPeerNames = new HashSet(4);
/*    */ 
/*    */   public abstract PrDrawable createDrawable(RTTexture paramRTTexture);
/*    */ 
/*    */   public static Renderer createRenderer(FilterContext paramFilterContext)
/*    */   {
/* 45 */     Object localObject = paramFilterContext.getReferent();
/* 46 */     GraphicsPipeline localGraphicsPipeline = GraphicsPipeline.getPipeline();
/* 47 */     if ((localGraphicsPipeline == null) || (!(localObject instanceof Screen))) {
/* 48 */       return null;
/*    */     }
/* 50 */     String str = localGraphicsPipeline.supportsShaderModel(GraphicsPipeline.ShaderModel.SM3) ? "com.sun.scenario.effect.impl.prism.ps.PPSRenderer" : "com.sun.scenario.effect.impl.prism.sw.PSWRenderer";
/*    */     try
/*    */     {
/* 54 */       Class localClass = Class.forName(str);
/* 55 */       Method localMethod = localClass.getMethod("createRenderer", new Class[] { FilterContext.class });
/* 56 */       return (Renderer)localMethod.invoke(null, new Object[] { paramFilterContext }); } catch (Throwable localThrowable) {
/*    */     }
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public static boolean isIntrinsicPeer(String paramString) {
/* 62 */     return intrinsicPeerNames.contains(paramString);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 30 */     intrinsicPeerNames.add("Crop");
/* 31 */     intrinsicPeerNames.add("Flood");
/* 32 */     intrinsicPeerNames.add("Merge");
/* 33 */     intrinsicPeerNames.add("Reflection");
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrRenderer
 * JD-Core Version:    0.6.2
 */