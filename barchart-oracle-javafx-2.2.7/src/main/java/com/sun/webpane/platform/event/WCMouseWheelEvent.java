/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ public class WCMouseWheelEvent
/*    */ {
/*    */   private long when;
/*    */   private int x;
/*    */   private int y;
/*    */   private int screenX;
/*    */   private int screenY;
/*    */   private float deltaX;
/*    */   private float deltaY;
/*    */   private boolean shift;
/*    */   private boolean control;
/*    */   private boolean alt;
/*    */   private boolean meta;
/*    */ 
/*    */   public WCMouseWheelEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat1, float paramFloat2)
/*    */   {
/* 27 */     this.x = paramInt1;
/* 28 */     this.y = paramInt2;
/* 29 */     this.screenX = paramInt3;
/* 30 */     this.screenY = paramInt4;
/* 31 */     this.when = paramLong;
/* 32 */     this.shift = paramBoolean1;
/* 33 */     this.control = paramBoolean2;
/* 34 */     this.alt = paramBoolean3;
/* 35 */     this.meta = paramBoolean4;
/* 36 */     this.deltaX = paramFloat1;
/* 37 */     this.deltaY = paramFloat2;
/*    */   }
/*    */   public long getWhen() {
/* 40 */     return this.when;
/*    */   }
/* 42 */   public int getX() { return this.x; } 
/* 43 */   public int getY() { return this.y; } 
/* 44 */   public int getScreenX() { return this.screenX; } 
/* 45 */   public int getScreenY() { return this.screenY; } 
/*    */   public boolean isShiftDown() {
/* 47 */     return this.shift; } 
/* 48 */   public boolean isControlDown() { return this.control; } 
/* 49 */   public boolean isAltDown() { return this.alt; } 
/* 50 */   public boolean isMetaDown() { return this.meta; } 
/*    */   public float getDeltaX() {
/* 52 */     return this.deltaX; } 
/* 53 */   public float getDeltaY() { return this.deltaY; }
/*    */ 
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCMouseWheelEvent
 * JD-Core Version:    0.6.2
 */