/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public class WCTransform extends Ref
/*    */ {
/*    */   double[] m;
/*    */ 
/*    */   public WCTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*    */   {
/* 12 */     this.m = new double[6];
/* 13 */     this.m[0] = paramDouble1;
/* 14 */     this.m[1] = paramDouble2;
/* 15 */     this.m[2] = paramDouble3;
/* 16 */     this.m[3] = paramDouble4;
/* 17 */     this.m[4] = paramDouble5;
/* 18 */     this.m[5] = paramDouble6;
/*    */   }
/*    */ 
/*    */   public double[] getMatrix() {
/* 22 */     return this.m;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCTransform
 * JD-Core Version:    0.6.2
 */