/*    */ package com.sun.webpane.sg.prism;
/*    */ 
/*    */ import com.sun.javafx.geom.transform.Affine2D;
/*    */ import com.sun.webpane.platform.graphics.WCTransform;
/*    */ 
/*    */ public class WCTransformImpl extends WCTransform
/*    */ {
/*    */   Affine2D t;
/*    */ 
/*    */   public WCTransformImpl(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*    */   {
/* 14 */     super(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*    */   }
/*    */ 
/*    */   public synchronized Affine2D getAffineTransform() {
/* 18 */     if (this.t == null) {
/* 19 */       double[] arrayOfDouble = getMatrix();
/* 20 */       this.t = new Affine2D(arrayOfDouble[0], arrayOfDouble[1], arrayOfDouble[2], arrayOfDouble[3], arrayOfDouble[4], arrayOfDouble[5]);
/*    */     }
/* 22 */     return this.t;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCTransformImpl
 * JD-Core Version:    0.6.2
 */