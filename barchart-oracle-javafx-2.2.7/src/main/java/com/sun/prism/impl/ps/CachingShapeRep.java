/*    */ package com.sun.prism.impl.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ import com.sun.prism.shape.ShapeRep.InvalidationType;
/*    */ 
/*    */ public class CachingShapeRep
/*    */   implements ShapeRep
/*    */ {
/*    */   private CachingShapeRepState fillState;
/*    */   private CachingShapeRepState drawState;
/*    */ 
/*    */   CachingShapeRepState createState()
/*    */   {
/* 61 */     return new CachingShapeRepState();
/*    */   }
/*    */ 
/*    */   public boolean is3DCapable() {
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   public void invalidate(ShapeRep.InvalidationType paramInvalidationType)
/*    */   {
/* 71 */     if (this.fillState != null) {
/* 72 */       this.fillState.invalidate();
/*    */     }
/* 74 */     if (this.drawState != null)
/* 75 */       this.drawState.invalidate();
/*    */   }
/*    */ 
/*    */   public void fill(Graphics paramGraphics, Shape paramShape)
/*    */   {
/* 80 */     if (this.fillState == null) {
/* 81 */       this.fillState = createState();
/*    */     }
/* 83 */     this.fillState.render(paramGraphics, paramShape, null);
/*    */   }
/*    */ 
/*    */   public void draw(Graphics paramGraphics, Shape paramShape) {
/* 87 */     if (this.drawState == null) {
/* 88 */       this.drawState = createState();
/*    */     }
/* 90 */     this.drawState.render(paramGraphics, paramShape, paramGraphics.getStroke());
/*    */   }
/*    */ 
/*    */   public void dispose() {
/* 94 */     if (this.fillState != null) {
/* 95 */       this.fillState.dispose();
/* 96 */       this.fillState = null;
/*    */     }
/* 98 */     if (this.drawState != null) {
/* 99 */       this.drawState.dispose();
/* 100 */       this.drawState = null;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.CachingShapeRep
 * JD-Core Version:    0.6.2
 */