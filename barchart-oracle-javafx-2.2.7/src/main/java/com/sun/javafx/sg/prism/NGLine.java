/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Line2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.javafx.sg.PGLine;
/*    */ import com.sun.javafx.sg.PGShape.Mode;
/*    */ import com.sun.prism.BasicStroke;
/*    */ import com.sun.prism.Graphics;
/*    */ 
/*    */ public class NGLine extends NGShape
/*    */   implements PGLine
/*    */ {
/* 21 */   private Line2D line = new Line2D();
/*    */ 
/*    */   public void updateLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 24 */     this.line.x1 = paramFloat1;
/* 25 */     this.line.y1 = paramFloat2;
/* 26 */     this.line.x2 = paramFloat3;
/* 27 */     this.line.y2 = paramFloat4;
/* 28 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   protected void renderContent(Graphics paramGraphics)
/*    */   {
/* 33 */     if (!paramGraphics.getTransformNoClone().is2D()) {
/* 34 */       super.renderContent(paramGraphics);
/* 35 */       return;
/*    */     }
/* 37 */     if (((this.mode == PGShape.Mode.STROKE) || (this.mode == PGShape.Mode.STROKE_FILL)) && (this.drawStroke.getLineWidth() > 0.0F) && (this.drawStroke.getType() != 1))
/*    */     {
/* 41 */       paramGraphics.setPaint(this.drawPaint);
/* 42 */       paramGraphics.setStroke(this.drawStroke);
/* 43 */       paramGraphics.drawLine(this.line.x1, this.line.y1, this.line.x2, this.line.y2);
/*    */     }
/*    */   }
/*    */ 
/*    */   public final Shape getShape()
/*    */   {
/* 49 */     return this.line;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGLine
 * JD-Core Version:    0.6.2
 */