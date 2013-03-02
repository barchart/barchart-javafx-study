/*    */ package com.sun.prism.impl.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.Ellipse2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ 
/*    */ public class BasicEllipseRep extends BasicShapeRep
/*    */ {
/*    */   public void fill(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 20 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 21 */     paramGraphics.fillEllipse(localEllipse2D.x, localEllipse2D.y, localEllipse2D.width, localEllipse2D.height);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 26 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 27 */     paramGraphics.drawEllipse(localEllipse2D.x, localEllipse2D.y, localEllipse2D.width, localEllipse2D.height);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.BasicEllipseRep
 * JD-Core Version:    0.6.2
 */