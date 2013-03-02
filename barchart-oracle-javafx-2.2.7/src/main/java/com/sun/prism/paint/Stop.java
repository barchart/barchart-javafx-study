/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ public class Stop
/*    */ {
/*    */   private final Color color;
/*    */   private final float offset;
/*    */ 
/*    */   public Stop(Color paramColor, float paramFloat)
/*    */   {
/* 16 */     this.color = paramColor;
/* 17 */     this.offset = paramFloat;
/*    */   }
/*    */ 
/*    */   public Color getColor() {
/* 21 */     return this.color;
/*    */   }
/*    */ 
/*    */   public float getOffset() {
/* 25 */     return this.offset;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.Stop
 * JD-Core Version:    0.6.2
 */