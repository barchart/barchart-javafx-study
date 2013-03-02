/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCMouseEvent
/*    */ {
/*    */   public static final int MOUSE_PRESSED = 0;
/*    */   public static final int MOUSE_RELEASED = 1;
/*    */   public static final int MOUSE_MOVED = 2;
/*    */   public static final int MOUSE_DRAGGED = 3;
/*    */   public static final int MOUSE_WHEEL = 4;
/*    */   public static final int NOBUTTON = 0;
/*    */   public static final int BUTTON1 = 1;
/*    */   public static final int BUTTON2 = 2;
/*    */   public static final int BUTTON3 = 4;
/*    */   private int id;
/*    */   private long when;
/*    */   private int button;
/*    */   private int clickCount;
/*    */   private int x;
/*    */   private int y;
/*    */   private int screenX;
/*    */   private int screenY;
/*    */   private boolean shift;
/*    */   private boolean control;
/*    */   private boolean alt;
/*    */   private boolean meta;
/*    */   private boolean popupTrigger;
/*    */ 
/*    */   public WCMouseEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
/*    */   {
/* 42 */     this.id = paramInt1;
/* 43 */     this.button = paramInt2;
/* 44 */     this.clickCount = paramInt3;
/* 45 */     this.x = paramInt4;
/* 46 */     this.y = paramInt5;
/* 47 */     this.screenX = paramInt6;
/* 48 */     this.screenY = paramInt7;
/* 49 */     this.when = paramLong;
/* 50 */     this.shift = paramBoolean1;
/* 51 */     this.control = paramBoolean2;
/* 52 */     this.alt = paramBoolean3;
/* 53 */     this.meta = paramBoolean4;
/* 54 */     this.popupTrigger = paramBoolean5;
/*    */   }
/*    */   public int getID() {
/* 57 */     return this.id; } 
/* 58 */   public long getWhen() { return this.when; } 
/*    */   public int getButton() {
/* 60 */     return this.button; } 
/* 61 */   public int getClickCount() { return this.clickCount; } 
/*    */   public int getX() {
/* 63 */     return this.x; } 
/* 64 */   public int getY() { return this.y; } 
/* 65 */   public int getScreenX() { return this.screenX; } 
/* 66 */   public int getScreenY() { return this.screenY; } 
/*    */   public boolean isShiftDown() {
/* 68 */     return this.shift; } 
/* 69 */   public boolean isControlDown() { return this.control; } 
/* 70 */   public boolean isAltDown() { return this.alt; } 
/* 71 */   public boolean isMetaDown() { return this.meta; } 
/*    */   public boolean isPopupTrigger() {
/* 73 */     return this.popupTrigger;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCMouseEvent
 * JD-Core Version:    0.6.2
 */