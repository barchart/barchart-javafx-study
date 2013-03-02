/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.Texture;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.Reflection;
/*    */ import com.sun.scenario.effect.impl.EffectPeer;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public class PrReflectionPeer extends EffectPeer
/*    */ {
/*    */   public PrReflectionPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 23 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*    */   {
/* 32 */     FilterContext localFilterContext = getFilterContext();
/* 33 */     Reflection localReflection = (Reflection)paramEffect;
/*    */ 
/* 35 */     Rectangle localRectangle1 = paramArrayOfImageData[0].getUntransformedBounds();
/* 36 */     int i = localRectangle1.width;
/* 37 */     int j = localRectangle1.height;
/* 38 */     float f1 = j + localReflection.getTopOffset();
/* 39 */     float f2 = localReflection.getFraction() * j;
/* 40 */     int k = (int)Math.floor(f1);
/* 41 */     int m = (int)Math.ceil(f1 + f2);
/* 42 */     int n = m - k;
/*    */ 
/* 44 */     int i1 = m > j ? m : j;
/*    */ 
/* 46 */     PrDrawable localPrDrawable1 = (PrDrawable)getRenderer().getCompatibleImage(i, i1);
/* 47 */     if ((!paramArrayOfImageData[0].validate(localFilterContext)) || (localPrDrawable1 == null)) {
/* 48 */       return new ImageData(localFilterContext, null, localRectangle1);
/*    */     }
/* 50 */     PrDrawable localPrDrawable2 = (PrDrawable)paramArrayOfImageData[0].getUntransformedImage();
/* 51 */     Texture localTexture = localPrDrawable2.getTextureObject();
/*    */ 
/* 53 */     Graphics localGraphics = localPrDrawable1.createGraphics();
/* 54 */     localGraphics.transform(paramArrayOfImageData[0].getTransform());
/* 55 */     float f3 = 0.0F;
/* 56 */     float f4 = j - n;
/* 57 */     float f5 = i;
/* 58 */     float f6 = j;
/* 59 */     localGraphics.drawTextureVO(localTexture, localReflection.getBottomOpacity(), localReflection.getTopOpacity(), 0.0F, m, i, k, f3, f4, f5, f6, 0);
/*    */ 
/* 65 */     localGraphics.drawTexture(localTexture, 0.0F, 0.0F, i, j);
/*    */ 
/* 67 */     Rectangle localRectangle2 = new Rectangle(localRectangle1.x, localRectangle1.y, i, i1);
/*    */ 
/* 69 */     return new ImageData(localFilterContext, localPrDrawable1, localRectangle2);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrReflectionPeer
 * JD-Core Version:    0.6.2
 */