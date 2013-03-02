/*    */ package com.sun.prism.image;
/*    */ 
/*    */ public class ViewPort
/*    */ {
/*    */   public float u0;
/*    */   public float v0;
/*    */   public float u1;
/*    */   public float v1;
/*    */ 
/*    */   public ViewPort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*    */   {
/* 20 */     this.u0 = paramFloat1; this.u1 = (paramFloat1 + paramFloat3);
/* 21 */     this.v0 = paramFloat2; this.v1 = (paramFloat2 + paramFloat4);
/*    */   }
/*    */ 
/*    */   public float getRelX(float paramFloat)
/*    */   {
/* 31 */     return (paramFloat - this.u0) / (this.u1 - this.u0); } 
/* 32 */   public float getRelY(float paramFloat) { return (paramFloat - this.v0) / (this.v1 - this.v0); }
/*    */ 
/*    */   public Coords getClippedCoords(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 35 */     Coords localCoords = new Coords(paramFloat3, paramFloat4, this);
/*    */ 
/* 37 */     if ((this.u1 > paramFloat1) || (this.u0 < 0.0F)) {
/* 38 */       if ((this.u0 >= paramFloat1) || (this.u1 <= 0.0F)) return null;
/*    */ 
/* 40 */       if (this.u1 > paramFloat1) {
/* 41 */         localCoords.x1 = (paramFloat3 * getRelX(paramFloat1));
/* 42 */         localCoords.u1 = paramFloat1;
/*    */       }
/* 44 */       if (this.u0 < 0.0F) {
/* 45 */         localCoords.x0 = (paramFloat3 * getRelX(0.0F));
/* 46 */         localCoords.u0 = 0.0F;
/*    */       }
/*    */     }
/*    */ 
/* 50 */     if ((this.v1 > paramFloat2) || (this.v0 < 0.0F)) {
/* 51 */       if ((this.v0 >= paramFloat2) || (this.v1 <= 0.0F)) return null;
/*    */ 
/* 53 */       if (this.v1 > paramFloat2) {
/* 54 */         localCoords.y1 = (paramFloat4 * getRelY(paramFloat2));
/* 55 */         localCoords.v1 = paramFloat2;
/*    */       }
/* 57 */       if (this.v0 < 0.0F) {
/* 58 */         localCoords.y0 = (paramFloat4 * getRelY(0.0F));
/* 59 */         localCoords.v0 = 0.0F;
/*    */       }
/*    */     }
/*    */ 
/* 63 */     return localCoords;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.image.ViewPort
 * JD-Core Version:    0.6.2
 */