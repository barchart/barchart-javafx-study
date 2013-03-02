/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.paint.Paint;
/*    */ import com.sun.scenario.effect.Effect;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.Flood;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.impl.EffectPeer;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public class PrFloodPeer extends EffectPeer
/*    */ {
/*    */   public PrFloodPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 45 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*    */   {
/* 54 */     FilterContext localFilterContext = getFilterContext();
/* 55 */     Flood localFlood = (Flood)paramEffect;
/* 56 */     BaseBounds localBaseBounds1 = localFlood.getBounds();
/* 57 */     int i = (int)localBaseBounds1.getMinX();
/* 58 */     int j = (int)localBaseBounds1.getMinY();
/* 59 */     int k = (int)localBaseBounds1.getWidth();
/* 60 */     int m = (int)localBaseBounds1.getHeight();
/*    */ 
/* 62 */     BaseBounds localBaseBounds2 = Effect.transformBounds(paramBaseTransform, localBaseBounds1);
/* 63 */     Rectangle localRectangle = new Rectangle(localBaseBounds2);
/* 64 */     localRectangle.intersectWith(paramRectangle);
/* 65 */     int n = localRectangle.width;
/* 66 */     int i1 = localRectangle.height;
/* 67 */     PrDrawable localPrDrawable = (PrDrawable)getRenderer().getCompatibleImage(n, i1);
/* 68 */     if (localPrDrawable != null) {
/* 69 */       Graphics localGraphics = localPrDrawable.createGraphics();
/* 70 */       localGraphics.translate(-localRectangle.x, -localRectangle.y);
/* 71 */       if ((paramBaseTransform != null) && (!paramBaseTransform.isIdentity())) {
/* 72 */         localGraphics.transform(paramBaseTransform);
/*    */       }
/* 74 */       localGraphics.setPaint((Paint)localFlood.getPaint());
/* 75 */       localGraphics.fillQuad(i, j, i + k, j + m);
/*    */     }
/*    */ 
/* 78 */     return new ImageData(localFilterContext, localPrDrawable, localRectangle);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrFloodPeer
 * JD-Core Version:    0.6.2
 */