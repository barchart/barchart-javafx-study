/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public class WCPoint
/*    */ {
/*    */   float x;
/*    */   float y;
/*    */ 
/*    */   public WCPoint(float paramFloat1, float paramFloat2)
/*    */   {
/* 10 */     this.x = paramFloat1;
/* 11 */     this.y = paramFloat2;
/*    */   }
/*    */ 
/*    */   public float getX() {
/* 15 */     return this.x;
/*    */   }
/*    */ 
/*    */   public float getY() {
/* 19 */     return this.y;
/*    */   }
/*    */ 
/*    */   public int getIntX() {
/* 23 */     return (int)this.x;
/*    */   }
/*    */ 
/*    */   public int getIntY() {
/* 27 */     return (int)this.y;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCPoint
 * JD-Core Version:    0.6.2
 */