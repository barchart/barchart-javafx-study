/*    */ package com.sun.javafx.geom;
/*    */ 
/*    */ public class Vec4f
/*    */ {
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   public float w;
/*    */ 
/*    */   public Vec4f()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Vec4f(Vec4f paramVec4f)
/*    */   {
/* 40 */     this.x = paramVec4f.x;
/* 41 */     this.y = paramVec4f.y;
/* 42 */     this.z = paramVec4f.z;
/* 43 */     this.w = paramVec4f.w;
/*    */   }
/*    */ 
/*    */   public Vec4f(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 47 */     this.x = paramFloat1;
/* 48 */     this.y = paramFloat2;
/* 49 */     this.z = paramFloat3;
/* 50 */     this.w = paramFloat4;
/*    */   }
/*    */ 
/*    */   public void set(Vec4f paramVec4f) {
/* 54 */     this.x = paramVec4f.x;
/* 55 */     this.y = paramVec4f.y;
/* 56 */     this.z = paramVec4f.z;
/* 57 */     this.w = paramVec4f.w;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 62 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Vec4f
 * JD-Core Version:    0.6.2
 */