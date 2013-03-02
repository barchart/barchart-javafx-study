/*    */ package com.sun.javafx.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.Path2D;
/*    */ import com.sun.javafx.geom.Shape;
/*    */ import com.sun.javafx.sg.PGPolyline;
/*    */ 
/*    */ public class NGPolyline extends NGShape
/*    */   implements PGPolyline
/*    */ {
/* 16 */   private Path2D path = new Path2D();
/*    */ 
/*    */   public void updatePolyline(float[] paramArrayOfFloat) {
/* 19 */     this.path.reset();
/* 20 */     if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0) || (paramArrayOfFloat.length % 2 != 0)) {
/* 21 */       return;
/*    */     }
/* 23 */     this.path.moveTo(paramArrayOfFloat[0], paramArrayOfFloat[1]);
/* 24 */     for (int i = 1; i < paramArrayOfFloat.length / 2; i++) {
/* 25 */       float f1 = paramArrayOfFloat[(i * 2 + 0)];
/* 26 */       float f2 = paramArrayOfFloat[(i * 2 + 1)];
/* 27 */       this.path.lineTo(f1, f2);
/*    */     }
/* 29 */     geometryChanged();
/*    */   }
/*    */ 
/*    */   public Shape getShape()
/*    */   {
/* 34 */     return this.path;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGPolyline
 * JD-Core Version:    0.6.2
 */