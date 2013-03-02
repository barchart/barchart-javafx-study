/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ public abstract class Cursor
/*    */ {
/*    */   public static final int CURSOR_NONE = -1;
/*    */   public static final int CURSOR_CUSTOM = 0;
/*    */   public static final int CURSOR_DEFAULT = 1;
/*    */   public static final int CURSOR_TEXT = 2;
/*    */   public static final int CURSOR_CROSSHAIR = 3;
/*    */   public static final int CURSOR_CLOSED_HAND = 4;
/*    */   public static final int CURSOR_OPEN_HAND = 5;
/*    */   public static final int CURSOR_POINTING_HAND = 6;
/*    */   public static final int CURSOR_RESIZE_LEFT = 7;
/*    */   public static final int CURSOR_RESIZE_RIGHT = 8;
/*    */   public static final int CURSOR_RESIZE_UP = 9;
/*    */   public static final int CURSOR_RESIZE_DOWN = 10;
/*    */   public static final int CURSOR_RESIZE_LEFTRIGHT = 11;
/*    */   public static final int CURSOR_RESIZE_UPDOWN = 12;
/*    */   public static final int CURSOR_DISAPPEAR = 13;
/*    */   public static final int CURSOR_WAIT = 14;
/*    */   public static final int CURSOR_RESIZE_SOUTHWEST = 15;
/*    */   public static final int CURSOR_RESIZE_SOUTHEAST = 16;
/*    */   public static final int CURSOR_RESIZE_NORTHWEST = 17;
/*    */   public static final int CURSOR_RESIZE_NORTHEAST = 18;
/*    */   public static final int CURSOR_MOVE = 19;
/*    */   protected int type;
/* 33 */   protected long ptr = 0L;
/*    */ 
/*    */   protected Cursor(int type) {
/* 36 */     this.type = type;
/*    */   }
/*    */ 
/*    */   protected Cursor(int x, int y, Pixels pixels) {
/* 40 */     this(0);
/* 41 */     this.ptr = _createCursor(x, y, pixels);
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 45 */     return this.type;
/*    */   }
/*    */ 
/*    */   protected long getNativeCursor() {
/* 49 */     return this.ptr;
/*    */   }
/*    */ 
/*    */   public static void setVisible(boolean visible)
/*    */   {
/* 58 */     Application.GetApplication().staticCursor_setVisible(visible);
/*    */   }
/*    */ 
/*    */   public static Size getBestSize(int width, int height)
/*    */   {
/* 65 */     return Application.GetApplication().staticCursor_getBestSize(width, height);
/*    */   }
/*    */ 
/*    */   protected abstract long _createCursor(int paramInt1, int paramInt2, Pixels paramPixels);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Cursor
 * JD-Core Version:    0.6.2
 */