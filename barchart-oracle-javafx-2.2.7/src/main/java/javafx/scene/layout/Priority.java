/*    */ package javafx.scene.layout;
/*    */ 
/*    */ public enum Priority
/*    */ {
/* 41 */   ALWAYS, 
/*    */ 
/* 49 */   SOMETIMES, 
/*    */ 
/* 55 */   NEVER;
/*    */ 
/*    */   public static Priority max(Priority paramPriority1, Priority paramPriority2)
/*    */   {
/* 64 */     if ((paramPriority1 == ALWAYS) || (paramPriority2 == ALWAYS))
/* 65 */       return ALWAYS;
/* 66 */     if ((paramPriority1 == SOMETIMES) || (paramPriority2 == SOMETIMES)) {
/* 67 */       return SOMETIMES;
/*    */     }
/* 69 */     return NEVER;
/*    */   }
/*    */ 
/*    */   public static Priority min(Priority paramPriority1, Priority paramPriority2)
/*    */   {
/* 80 */     if ((paramPriority1 == NEVER) || (paramPriority2 == NEVER))
/* 81 */       return NEVER;
/* 82 */     if ((paramPriority1 == SOMETIMES) || (paramPriority2 == SOMETIMES)) {
/* 83 */       return SOMETIMES;
/*    */     }
/* 85 */     return ALWAYS;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.Priority
 * JD-Core Version:    0.6.2
 */