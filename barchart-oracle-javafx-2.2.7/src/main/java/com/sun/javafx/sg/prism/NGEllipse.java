/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Ellipse2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGEllipse;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ 
/*    */ public class NGEllipse extends NGShape
/*    */   implements PGEllipse
/*    */ {
/* 21 */   private Ellipse2D ellipse = new Ellipse2D();
/*    */ 
/*    */   public void updateEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 24 */     this.ellipse.x = (paramFloat1 - paramFloat3);
/* 25 */     this.ellipse.width = (paramFloat3 * 2.0F);
/* 26 */     this.ellipse.y = (paramFloat2 - paramFloat4);
/* 27 */     this.ellipse.height = (paramFloat4 * 2.0F);
/* 28 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   public final Shape getShape()
/*    */   {
/* 33 */     return this.ellipse;
/*    */   }
/*    */ 
/*    */   protected ShapeRep createShapeRep(Graphics paramGraphics, boolean paramBoolean)
/*    */   {
/* 38 */     return paramGraphics.getResourceFactory().createEllipseRep(paramBoolean);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGEllipse
 * JD-Core Version:    0.6.2
 */