/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Ellipse2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ 
/*    */ public class TessEllipseRep extends TessShapeRep
/*    */ {
/*    */   protected void adjustLocation(Shape paramShape)
/*    */   {
/* 15 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 16 */     savedX = localEllipse2D.x;
/* 17 */     savedY = localEllipse2D.y;
/* 18 */     localEllipse2D.x = 0.0F;
/* 19 */     localEllipse2D.y = 0.0F;
/*    */   }
/*    */ 
/*    */   protected void restoreLocation(Shape paramShape) {
/* 23 */     Ellipse2D localEllipse2D = (Ellipse2D)paramShape;
/* 24 */     localEllipse2D.x = savedX;
/* 25 */     localEllipse2D.y = savedY;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.TessEllipseRep
 * JD-Core Version:    0.6.2
 */