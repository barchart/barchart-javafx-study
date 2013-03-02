/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.impl.EffectPeer;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public abstract class PPSEffectPeer extends EffectPeer
/*    */ {
/*    */   protected PPSEffectPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 41 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public final ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*    */   {
/* 50 */     setEffect(paramEffect);
/* 51 */     setDestBounds(getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData));
/* 52 */     return filterImpl(paramArrayOfImageData);
/*    */   }
/*    */ 
/*    */   abstract ImageData filterImpl(ImageData[] paramArrayOfImageData);
/*    */ 
/*    */   protected abstract boolean isSamplerLinear(int paramInt);
/*    */ 
/*    */   protected abstract Shader createShader();
/*    */ 
/*    */   protected abstract void updateShader(Shader paramShader);
/*    */ 
/*    */   public abstract void dispose();
/*    */ 
/*    */   protected final PPSRenderer getRenderer()
/*    */   {
/* 72 */     return (PPSRenderer)super.getRenderer();
/*    */   }
/*    */ 
/*    */   protected final String getShaderName() {
/* 76 */     return getUniqueName();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSEffectPeer
 * JD-Core Version:    0.6.2
 */