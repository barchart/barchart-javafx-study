/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ import com.sun.prism.shape.ShapeRep.InvalidationType;
/*    */ 
/*    */ public class TessShapeRep
/*    */   implements ShapeRep
/*    */ {
/*    */   protected static float savedX;
/*    */   protected static float savedY;
/*    */   private TessShapeRepState fillState;
/*    */   private TessShapeRepState drawState;
/*    */ 
/*    */   public boolean is3DCapable()
/*    */   {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   public void invalidate(ShapeRep.InvalidationType paramInvalidationType) {
/* 39 */     if (paramInvalidationType == ShapeRep.InvalidationType.LOCATION_AND_GEOMETRY) {
/* 40 */       if (this.fillState != null) {
/* 41 */         this.fillState.invalidate();
/*    */       }
/* 43 */       if (this.drawState != null)
/* 44 */         this.drawState.invalidate();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void fill(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 50 */     adjustLocation(paramShape);
/* 51 */     if (this.fillState == null) {
/* 52 */       this.fillState = new TessShapeRepState();
/*    */     }
/* 54 */     this.fillState.render(paramGraphics, paramShape, null, savedX, savedY);
/* 55 */     restoreLocation(paramShape);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape) {
/* 59 */     adjustLocation(paramShape);
/* 60 */     if (this.drawState == null) {
/* 61 */       this.drawState = new TessShapeRepState();
/*    */     }
/* 63 */     this.drawState.render(paramGraphics, paramShape, paramGraphics.getStroke(), savedX, savedY);
/* 64 */     restoreLocation(paramShape);
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void adjustLocation(Shape paramShape)
/*    */   {
/* 76 */     savedX = TessShapeRep.savedY = 0.0F;
/*    */   }
/*    */ 
/*    */   protected void restoreLocation(Shape paramShape)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.TessShapeRep
 * JD-Core Version:    0.6.2
 */