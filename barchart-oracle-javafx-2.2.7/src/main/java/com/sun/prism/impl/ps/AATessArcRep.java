/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Arc2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ 
/*    */ public class AATessArcRep extends AATessShapeRep
/*    */ {
/*    */   protected void adjustLocation(Shape paramShape)
/*    */   {
/* 15 */     Arc2D localArc2D = (Arc2D)paramShape;
/* 16 */     savedX = localArc2D.x;
/* 17 */     savedY = localArc2D.y;
/* 18 */     localArc2D.x = 0.0F;
/* 19 */     localArc2D.y = 0.0F;
/*    */   }
/*    */ 
/*    */   protected void restoreLocation(Shape paramShape) {
/* 23 */     Arc2D localArc2D = (Arc2D)paramShape;
/* 24 */     localArc2D.x = savedX;
/* 25 */     localArc2D.y = savedY;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.AATessArcRep
 * JD-Core Version:    0.6.2
 */