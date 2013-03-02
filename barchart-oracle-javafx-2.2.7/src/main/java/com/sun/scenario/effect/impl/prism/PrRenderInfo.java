/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.Texture;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.ImageDataRenderer;
/*    */ 
/*    */ public class PrRenderInfo
/*    */   implements ImageDataRenderer
/*    */ {
/*    */   private Graphics g;
/*    */ 
/*    */   public PrRenderInfo(Graphics paramGraphics)
/*    */   {
/* 22 */     this.g = paramGraphics;
/*    */   }
/*    */ 
/*    */   public Graphics getGraphics() {
/* 26 */     return this.g;
/*    */   }
/*    */ 
/*    */   public void renderImage(ImageData paramImageData, BaseTransform paramBaseTransform, FilterContext paramFilterContext)
/*    */   {
/* 35 */     if (paramImageData.validate(paramFilterContext)) {
/* 36 */       Rectangle localRectangle = paramImageData.getUntransformedBounds();
/*    */ 
/* 40 */       Texture localTexture = ((PrTexture)paramImageData.getUntransformedImage()).getTextureObject();
/* 41 */       BaseTransform localBaseTransform1 = null;
/* 42 */       if (!paramBaseTransform.isIdentity()) {
/* 43 */         localBaseTransform1 = this.g.getTransformNoClone().copy();
/* 44 */         this.g.transform(paramBaseTransform);
/*    */       }
/* 46 */       BaseTransform localBaseTransform2 = paramImageData.getTransform();
/* 47 */       if (!localBaseTransform2.isIdentity()) {
/* 48 */         if (localBaseTransform1 == null) localBaseTransform1 = this.g.getTransformNoClone().copy();
/* 49 */         this.g.transform(localBaseTransform2);
/*    */       }
/* 51 */       this.g.drawTexture(localTexture, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/* 52 */       if (localBaseTransform1 != null)
/* 53 */         this.g.setTransform(localBaseTransform1);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrRenderInfo
 * JD-Core Version:    0.6.2
 */