/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.QuadCurve2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGQuadCurve;
/*    */ 
/*    */ public class NGQuadCurve extends NGShape
/*    */   implements PGQuadCurve
/*    */ {
/* 15 */   private QuadCurve2D curve = new QuadCurve2D();
/*    */ 
/* 17 */   public final Shape getShape() { return this.curve; } 
/*    */   public void updateQuadCurve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
/* 19 */     this.curve.x1 = paramFloat1;
/* 20 */     this.curve.y1 = paramFloat2;
/* 21 */     this.curve.x2 = paramFloat3;
/* 22 */     this.curve.y2 = paramFloat4;
/* 23 */     this.curve.ctrlx = paramFloat5;
/* 24 */     this.curve.ctrly = paramFloat6;
/* 25 */     geometryChanged();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGQuadCurve
 * JD-Core Version:    0.6.2
 */