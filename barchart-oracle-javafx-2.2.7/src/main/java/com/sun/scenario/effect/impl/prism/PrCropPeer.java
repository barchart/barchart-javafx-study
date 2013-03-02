/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.impl.EffectPeer;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public class PrCropPeer extends EffectPeer
/*    */ {
/*    */   public PrCropPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 21 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*    */   {
/* 30 */     FilterContext localFilterContext = getFilterContext();
/* 31 */     ImageData localImageData = paramArrayOfImageData[0];
/* 32 */     Rectangle localRectangle1 = localImageData.getTransformedBounds(null);
/* 33 */     if (paramRectangle.contains(localRectangle1)) {
/* 34 */       localImageData.addref();
/* 35 */       return localImageData;
/*    */     }
/*    */ 
/* 38 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 39 */     localRectangle2.intersectWith(paramRectangle);
/* 40 */     int i = localRectangle2.width;
/* 41 */     int j = localRectangle2.height;
/* 42 */     PrDrawable localPrDrawable = (PrDrawable)getRenderer().getCompatibleImage(i, j);
/*    */ 
/* 44 */     if ((!localImageData.validate(localFilterContext)) || (localPrDrawable == null)) {
/* 45 */       localPrDrawable = null;
/*    */     } else {
/* 47 */       Graphics localGraphics = localPrDrawable.createGraphics();
/* 48 */       PrEffectHelper.renderImageData(localGraphics, localImageData, localRectangle2);
/*    */     }
/*    */ 
/* 51 */     return new ImageData(localFilterContext, localPrDrawable, localRectangle2);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrCropPeer
 * JD-Core Version:    0.6.2
 */