/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ public final class Size
/*    */ {
/*    */   public int width;
/*    */   public int height;
/*    */ 
/*    */   public Size(int width, int height)
/*    */   {
/* 14 */     this.width = width;
/* 15 */     this.height = height;
/*    */   }
/*    */ 
/*    */   public Size() {
/* 19 */     this(0, 0);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 23 */     return "Size(" + this.width + ", " + this.height + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Size
 * JD-Core Version:    0.6.2
 */