/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Ellipse2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ 
/*    */ class CachingEllipseRepState extends CachingShapeRepState
/*    */ {
/*    */   void fillNoCache(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 27 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 28 */     paramGraphics.fillEllipse(localEllipse2D.x, localEllipse2D.y, localEllipse2D.width, localEllipse2D.height);
/*    */   }
/*    */ 
/*    */   void drawNoCache(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 33 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 34 */     paramGraphics.drawEllipse(localEllipse2D.x, localEllipse2D.y, localEllipse2D.width, localEllipse2D.height);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.CachingEllipseRepState
 * JD-Core Version:    0.6.2
 */