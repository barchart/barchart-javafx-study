/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Arc2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGArc;
/*    */ import com.sun.javafx.sg.PGArc.ArcType;
/*    */ import com.sun.prism.Graphics;
/*    */ import com.sun.prism.ResourceFactory;
/*    */ import com.sun.prism.shape.ShapeRep;
/*    */ 
/*    */ public class NGArc extends NGShape
/*    */   implements PGArc
/*    */ {
/* 17 */   private Arc2D arc = new Arc2D();
/*    */ 
/* 19 */   public Shape getShape() { return this.arc; }
/*    */ 
/*    */   public void updateArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, PGArc.ArcType paramArcType) {
/* 22 */     this.arc.x = (paramFloat1 - paramFloat3);
/* 23 */     this.arc.width = (paramFloat3 * 2.0F);
/* 24 */     this.arc.y = (paramFloat2 - paramFloat4);
/* 25 */     this.arc.height = (paramFloat4 * 2.0F);
/* 26 */     this.arc.start = paramFloat5;
/* 27 */     this.arc.extent = paramFloat6;
/*    */ 
/* 29 */     if (paramArcType == PGArc.ArcType.CHORD)
/* 30 */       this.arc.setArcType(1);
/* 31 */     else if (paramArcType == PGArc.ArcType.OPEN)
/* 32 */       this.arc.setArcType(0);
/* 33 */     else if (paramArcType == PGArc.ArcType.ROUND)
/* 34 */       this.arc.setArcType(2);
/*    */     else {
/* 36 */       throw new AssertionError("Unknown arc type specified");
/*    */     }
/* 38 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   protected ShapeRep createShapeRep(Graphics paramGraphics, boolean paramBoolean)
/*    */   {
/* 43 */     return paramGraphics.getResourceFactory().createArcRep(paramBoolean);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGArc
 * JD-Core Version:    0.6.2
 */