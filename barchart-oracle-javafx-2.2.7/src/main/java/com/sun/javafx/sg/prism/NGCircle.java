/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Ellipse2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGCircle;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ 
/*    */ public class NGCircle extends NGShape
/*    */   implements PGCircle
/*    */ {
/* 20 */   private Ellipse2D ellipse = new Ellipse2D();
/*    */ 
/*    */   public void updateCircle(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 23 */     this.ellipse.x = (paramFloat1 - paramFloat3);
/* 24 */     this.ellipse.y = (paramFloat2 - paramFloat3);
/* 25 */     this.ellipse.width = (paramFloat3 * 2.0F);
/* 26 */     this.ellipse.height = this.ellipse.width;
/* 27 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   public Shape getShape()
/*    */   {
/* 32 */     return this.ellipse;
/*    */   }
/*    */ 
/*    */   protected ShapeRep createShapeRep(Graphics paramGraphics, boolean paramBoolean)
/*    */   {
/* 37 */     return paramGraphics.getResourceFactory().createEllipseRep(paramBoolean);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGCircle
 * JD-Core Version:    0.6.2
 */