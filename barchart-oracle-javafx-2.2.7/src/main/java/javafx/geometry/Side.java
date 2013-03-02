/*    */ package javafx.geometry;
/*    */ 
/*    */ public enum Side
/*    */ {
/* 35 */   TOP, 
/*    */ 
/* 40 */   BOTTOM, 
/*    */ 
/* 45 */   LEFT, 
/*    */ 
/* 50 */   RIGHT;
/*    */ 
/*    */   public boolean isVertical()
/*    */   {
/* 58 */     return (this == LEFT) || (this == RIGHT);
/*    */   }
/*    */ 
/*    */   public boolean isHorizontal()
/*    */   {
/* 67 */     return (this == TOP) || (this == BOTTOM);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Side
 * JD-Core Version:    0.6.2
 */