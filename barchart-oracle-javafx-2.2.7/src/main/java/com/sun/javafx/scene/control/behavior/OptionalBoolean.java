/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ public enum OptionalBoolean
/*    */ {
/* 32 */   TRUE, 
/* 33 */   FALSE, 
/* 34 */   ANY;
/*    */ 
/*    */   public boolean equals(boolean paramBoolean) {
/* 37 */     if (this == ANY) return true;
/* 38 */     if ((paramBoolean) && (this == TRUE)) return true;
/* 39 */     if ((!paramBoolean) && (this == FALSE)) return true;
/* 40 */     return false;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.OptionalBoolean
 * JD-Core Version:    0.6.2
 */