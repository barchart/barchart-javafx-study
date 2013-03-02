/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.RoundRectangle2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ 
/*    */ public class TessRoundRectRep extends TessShapeRep
/*    */ {
/*    */   protected void adjustLocation(Shape paramShape)
/*    */   {
/* 15 */     RoundRectangle2D localRoundRectangle2D = (RoundRectangle2D)paramShape;
/* 16 */     savedX = localRoundRectangle2D.x;
/* 17 */     savedY = localRoundRectangle2D.y;
/* 18 */     localRoundRectangle2D.x = 0.0F;
/* 19 */     localRoundRectangle2D.y = 0.0F;
/*    */   }
/*    */ 
/*    */   protected void restoreLocation(Shape paramShape) {
/* 23 */     RoundRectangle2D localRoundRectangle2D = (RoundRectangle2D)paramShape;
/* 24 */     localRoundRectangle2D.x = savedX;
/* 25 */     localRoundRectangle2D.y = savedY;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.TessRoundRectRep
 * JD-Core Version:    0.6.2
 */