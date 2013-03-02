/*    */ package com.sun.glass.events;
/*    */ 
/*    */ public class ViewEvent
/*    */ {
/*    */   public static final int LOCKED = 411;
/*    */   public static final int UNLOCKED = 412;
/*    */   public static final int ADD = 421;
/*    */   public static final int REMOVE = 422;
/*    */   public static final int REPAINT = 431;
/*    */   public static final int RESIZE = 432;
/*    */   public static final int MOVE = 433;
/*    */   public static final int FULLSCREEN_ENTER = 441;
/*    */   public static final int FULLSCREEN_EXIT = 442;
/*    */ 
/*    */   public static String GetTypeString(int type)
/*    */   {
/* 21 */     String string = "UNKNOWN";
/* 22 */     switch (type) { case 411:
/* 23 */       string = "LOCKED"; break;
/*    */     case 412:
/* 24 */       string = "UNLOCKED"; break;
/*    */     case 421:
/* 26 */       string = "ADD"; break;
/*    */     case 422:
/* 27 */       string = "REMOVE"; break;
/*    */     case 431:
/* 29 */       string = "REPAINT"; break;
/*    */     case 432:
/* 30 */       string = "RESIZE";
/*    */     }
/* 32 */     return string;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.events.ViewEvent
 * JD-Core Version:    0.6.2
 */