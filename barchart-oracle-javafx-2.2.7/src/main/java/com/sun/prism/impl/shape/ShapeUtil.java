/*    */ package com.sun.prism.impl.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.RectBounds;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.prism.BasicStroke;
/*    */ import com.sun.prism.impl.PrismSettings;
/*    */ 
/*    */ public class ShapeUtil
/*    */ {
/* 20 */   private static final ShapeRasterizer shapeRasterizer = new OpenPiscesRasterizer();
/*    */   private static final ShapeRasterizer textRasterizer;
/*    */ 
/*    */   public static MaskData rasterizeShape(Shape paramShape, BasicStroke paramBasicStroke, RectBounds paramRectBounds, BaseTransform paramBaseTransform, boolean paramBoolean)
/*    */   {
/* 35 */     return shapeRasterizer.getMaskData(paramShape, paramBasicStroke, paramRectBounds, paramBaseTransform, paramBoolean);
/*    */   }
/*    */ 
/*    */   public static MaskData rasterizeGlyphOutline(Shape paramShape) {
/* 39 */     return textRasterizer.getMaskData(paramShape, null, null, BaseTransform.IDENTITY_TRANSFORM, true);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 22 */     if (PrismSettings.doT2KText)
/* 23 */       textRasterizer = null;
/*    */     else
/* 25 */       textRasterizer = shapeRasterizer;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.ShapeUtil
 * JD-Core Version:    0.6.2
 */