/*    */ package com.sun.prism.impl.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ import com.sun.prism.shape.ShapeRep.InvalidationType;
/*    */ 
/*    */ public class BasicShapeRep
/*    */   implements ShapeRep
/*    */ {
/*    */   public boolean is3DCapable()
/*    */   {
/* 19 */     return false;
/*    */   }
/*    */ 
/*    */   public void invalidate(ShapeRep.InvalidationType paramInvalidationType) {
/*    */   }
/*    */ 
/*    */   public void fill(Graphics paramGraphics, Shape paramShape) {
/* 26 */     paramGraphics.fill(paramShape);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape) {
/* 30 */     paramGraphics.draw(paramShape);
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.BasicShapeRep
 * JD-Core Version:    0.6.2
 */