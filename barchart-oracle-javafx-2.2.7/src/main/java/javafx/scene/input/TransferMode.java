/*    */ package javafx.scene.input;
/*    */ 
/*    */ public enum TransferMode
/*    */ {
/* 38 */   COPY, 
/*    */ 
/* 43 */   MOVE, 
/*    */ 
/* 48 */   LINK;
/*    */ 
/* 56 */   public static final TransferMode[] ANY = { COPY, MOVE, LINK };
/*    */ 
/* 63 */   public static final TransferMode[] COPY_OR_MOVE = { COPY, MOVE };
/*    */ 
/* 70 */   public static final TransferMode[] NONE = new TransferMode[0];
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.TransferMode
 * JD-Core Version:    0.6.2
 */