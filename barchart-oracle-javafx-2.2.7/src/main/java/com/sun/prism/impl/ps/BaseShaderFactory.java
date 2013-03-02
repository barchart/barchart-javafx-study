/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.prism.impl.BaseResourceFactory;
/*    */ import com.sun.prism.impl.PrismSettings;
/*    */ import com.sun.prism.impl.shape.BasicEllipseRep;
/*    */ import com.sun.prism.impl.shape.BasicRoundRectRep;
/*    */ import com.sun.prism.impl.shape.BasicShapeRep;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ 
/*    */ public abstract class BaseShaderFactory extends BaseResourceFactory
/*    */   implements ShaderFactory
/*    */ {
/*    */   public ShapeRep createPathRep(boolean paramBoolean)
/*    */   {
/* 21 */     if ((paramBoolean) || (PrismSettings.tessShapes)) {
/* 22 */       return PrismSettings.tessShapesAA ? new AATessShapeRep() : new TessShapeRep();
/*    */     }
/*    */ 
/* 25 */     return PrismSettings.cacheComplexShapes ? new CachingShapeRep() : new BasicShapeRep();
/*    */   }
/*    */ 
/*    */   public ShapeRep createRoundRectRep(boolean paramBoolean)
/*    */   {
/* 31 */     if ((paramBoolean) || (PrismSettings.tessShapes)) {
/* 32 */       return PrismSettings.tessShapesAA ? new AATessRoundRectRep() : new TessRoundRectRep();
/*    */     }
/*    */ 
/* 35 */     return PrismSettings.cacheSimpleShapes ? new CachingRoundRectRep() : new BasicRoundRectRep();
/*    */   }
/*    */ 
/*    */   public ShapeRep createEllipseRep(boolean paramBoolean)
/*    */   {
/* 41 */     if ((paramBoolean) || (PrismSettings.tessShapes)) {
/* 42 */       return PrismSettings.tessShapesAA ? new AATessEllipseRep() : new TessEllipseRep();
/*    */     }
/*    */ 
/* 45 */     return PrismSettings.cacheSimpleShapes ? new CachingEllipseRep() : new BasicEllipseRep();
/*    */   }
/*    */ 
/*    */   public ShapeRep createArcRep(boolean paramBoolean)
/*    */   {
/* 51 */     if ((paramBoolean) || (PrismSettings.tessShapes)) {
/* 52 */       return PrismSettings.tessShapesAA ? new AATessArcRep() : new TessArcRep();
/*    */     }
/*    */ 
/* 55 */     return PrismSettings.cacheComplexShapes ? new CachingShapeRep() : new BasicShapeRep();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.BaseShaderFactory
 * JD-Core Version:    0.6.2
 */