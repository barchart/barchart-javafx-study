/*    */ package com.sun.javafx.geom;
/*    */ 
/*    */ public class Vec4d
/*    */ {
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   public double w;
/*    */ 
/*    */   public Vec4d()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Vec4d(Vec4d paramVec4d)
/*    */   {
/* 40 */     this.x = paramVec4d.x;
/* 41 */     this.y = paramVec4d.y;
/* 42 */     this.z = paramVec4d.z;
/* 43 */     this.w = paramVec4d.w;
/*    */   }
/*    */ 
/*    */   public Vec4d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 47 */     this.x = paramDouble1;
/* 48 */     this.y = paramDouble2;
/* 49 */     this.z = paramDouble3;
/* 50 */     this.w = paramDouble4;
/*    */   }
/*    */ 
/*    */   public void set(Vec4d paramVec4d) {
/* 54 */     this.x = paramVec4d.x;
/* 55 */     this.y = paramVec4d.y;
/* 56 */     this.z = paramVec4d.z;
/* 57 */     this.w = paramVec4d.w;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec4d
 * JD-Core Version:    0.6.2
 */