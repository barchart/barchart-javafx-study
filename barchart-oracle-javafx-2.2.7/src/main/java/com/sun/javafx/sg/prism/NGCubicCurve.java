/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.CubicCurve2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGCubicCurve;
/*    */ 
/*    */ public class NGCubicCurve extends NGShape
/*    */   implements PGCubicCurve
/*    */ {
/* 15 */   private CubicCurve2D curve = new CubicCurve2D();
/*    */ 
/* 17 */   public final Shape getShape() { return this.curve; }
/*    */ 
/*    */   public void updateCubicCurve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*    */   {
/* 21 */     this.curve.x1 = paramFloat1;
/* 22 */     this.curve.y1 = paramFloat2;
/* 23 */     this.curve.x2 = paramFloat3;
/* 24 */     this.curve.y2 = paramFloat4;
/* 25 */     this.curve.ctrlx1 = paramFloat5;
/* 26 */     this.curve.ctrly1 = paramFloat6;
/* 27 */     this.curve.ctrlx2 = paramFloat7;
/* 28 */     this.curve.ctrly2 = paramFloat8;
/* 29 */     geometryChanged();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGCubicCurve
 * JD-Core Version:    0.6.2
 */