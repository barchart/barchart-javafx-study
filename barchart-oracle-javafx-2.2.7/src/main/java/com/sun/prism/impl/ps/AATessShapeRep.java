/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ import com.sun.prism.shape.ShapeRep.InvalidationType;
/*    */ 
/*    */ public class AATessShapeRep
/*    */   implements ShapeRep
/*    */ {
/*    */   protected static float savedX;
/*    */   protected static float savedY;
/*    */   private AATessShapeRepState fillState;
/*    */   private AATessShapeRepState drawState;
/*    */ 
/*    */   public boolean is3DCapable()
/*    */   {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   public void invalidate(ShapeRep.InvalidationType paramInvalidationType) {
/* 40 */     if (paramInvalidationType == ShapeRep.InvalidationType.LOCATION_AND_GEOMETRY) {
/* 41 */       if (this.fillState != null) {
/* 42 */         this.fillState.invalidate();
/*    */       }
/* 44 */       if (this.drawState != null)
/* 45 */         this.drawState.invalidate();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void fill(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 51 */     adjustLocation(paramShape);
/* 52 */     if (this.fillState == null) {
/* 53 */       this.fillState = new AATessShapeRepState();
/*    */     }
/* 55 */     this.fillState.render(paramGraphics, paramShape, null, savedX, savedY);
/* 56 */     restoreLocation(paramShape);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape) {
/* 60 */     adjustLocation(paramShape);
/* 61 */     if (this.drawState == null) {
/* 62 */       this.drawState = new AATessShapeRepState();
/*    */     }
/* 64 */     this.drawState.render(paramGraphics, paramShape, paramGraphics.getStroke(), savedX, savedY);
/* 65 */     restoreLocation(paramShape);
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void adjustLocation(Shape paramShape)
/*    */   {
/* 77 */     savedX = AATessShapeRep.savedY = 0.0F;
/*    */   }
/*    */ 
/*    */   protected void restoreLocation(Shape paramShape)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.AATessShapeRep
 * JD-Core Version:    0.6.2
 */