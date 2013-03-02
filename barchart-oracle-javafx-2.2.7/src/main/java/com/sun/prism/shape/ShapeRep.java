/*    */ package com.sun.prism.shape;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ 
/*    */ public abstract interface ShapeRep
/*    */ {
/*    */   public abstract boolean is3DCapable();
/*    */ 
/*    */   public abstract void invalidate(InvalidationType paramInvalidationType);
/*    */ 
/*    */   public abstract void fill(Graphics paramGraphics, Shape paramShape);
/*    */ 
/*    */   public abstract void draw(Graphics paramGraphics, Shape paramShape);
/*    */ 
/*    */   public abstract void dispose();
/*    */ 
/*    */   public static enum InvalidationType
/*    */   {
/* 17 */     LOCATION, 
/*    */ 
/* 21 */     LOCATION_AND_GEOMETRY;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shape.ShapeRep
 * JD-Core Version:    0.6.2
 */