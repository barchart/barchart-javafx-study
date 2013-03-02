/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.RoundRectangle2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.impl.shape.BasicRoundRectRep;
/*    */ 
/*    */ class CachingRoundRectRepState extends CachingShapeRepState
/*    */ {
/*    */   void fillNoCache(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 28 */     BasicRoundRectRep.fillRoundRect(paramGraphics, (RoundRectangle2D)paramShape);
/*    */   }
/*    */ 
/*    */   void drawNoCache(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 33 */     BasicRoundRectRep.drawRoundRect(paramGraphics, (RoundRectangle2D)paramShape);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.CachingRoundRectRepState
 * JD-Core Version:    0.6.2
 */