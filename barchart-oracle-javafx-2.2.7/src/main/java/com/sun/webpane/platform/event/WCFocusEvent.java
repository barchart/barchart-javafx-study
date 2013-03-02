/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCFocusEvent
/*    */ {
/*    */   public static final int WINDOW_ACTIVATED = 0;
/*    */   public static final int WINDOW_DEACTIVATED = 1;
/*    */   public static final int FOCUS_GAINED = 2;
/*    */   public static final int FOCUS_LOST = 3;
/*    */   public static final int UNKNOWN = -1;
/*    */   public static final int FORWARD = 0;
/*    */   public static final int BACKWARD = 1;
/*    */   private int id;
/*    */   private long when;
/*    */   private int direction;
/*    */ 
/*    */   public WCFocusEvent(int paramInt1, int paramInt2)
/*    */   {
/* 25 */     this.id = paramInt1;
/* 26 */     this.direction = paramInt2;
/*    */   }
/*    */   public int getID() {
/* 29 */     return this.id;
/*    */   }
/* 31 */   public int getDirection() { return this.direction; }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     return "WCFocusEvent(" + this.id + ", " + this.direction + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCFocusEvent
 * JD-Core Version:    0.6.2
 */