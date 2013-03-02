/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.Merge;
/*    */ import com.sun.scenario.effect.impl.EffectPeer;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public class PrMergePeer extends EffectPeer
/*    */ {
/*    */   public PrMergePeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 42 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*    */   {
/* 51 */     FilterContext localFilterContext = getFilterContext();
/* 52 */     Merge localMerge = (Merge)paramEffect;
/*    */ 
/* 54 */     Rectangle localRectangle = localMerge.getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 55 */     PrDrawable localPrDrawable = (PrDrawable)getRenderer().getCompatibleImage(localRectangle.width, localRectangle.height);
/*    */ 
/* 57 */     if (localPrDrawable == null) {
/* 58 */       return new ImageData(localFilterContext, null, localRectangle);
/*    */     }
/*    */ 
/* 61 */     Graphics localGraphics = localPrDrawable.createGraphics();
/* 62 */     for (ImageData localImageData : paramArrayOfImageData) {
/* 63 */       PrEffectHelper.renderImageData(localGraphics, localImageData, localRectangle);
/*    */     }
/*    */ 
/* 66 */     return new ImageData(localFilterContext, localPrDrawable, localRectangle);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrMergePeer
 * JD-Core Version:    0.6.2
 */