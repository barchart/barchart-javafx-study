/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public class WCSize
/*    */ {
/*    */   float width;
/*    */   float height;
/*    */ 
/*    */   public WCSize(float paramFloat1, float paramFloat2)
/*    */   {
/* 11 */     this.width = paramFloat1;
/* 12 */     this.height = paramFloat2;
/*    */   }
/*    */ 
/*    */   public float getWidth() {
/* 16 */     return this.width;
/*    */   }
/*    */ 
/*    */   public float getHeight() {
/* 20 */     return this.height;
/*    */   }
/*    */ 
/*    */   public int getIntWidth() {
/* 24 */     return (int)this.width;
/*    */   }
/*    */ 
/*    */   public int getIntHeight() {
/* 28 */     return (int)this.height;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCSize
 * JD-Core Version:    0.6.2
 */