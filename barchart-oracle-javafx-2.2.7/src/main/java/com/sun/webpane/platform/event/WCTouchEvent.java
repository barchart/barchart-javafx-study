/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.util.List;
/*    */ 
/*    */ public class WCTouchEvent
/*    */ {
/*    */   public static final int TOUCH_START = 0;
/*    */   public static final int TOUCH_MOVE = 1;
/*    */   public static final int TOUCH_END = 2;
/*    */   public static final int TOUCH_CANCEL = 3;
/*    */   private final int id;
/*    */   private final long when;
/*    */   private final boolean shift;
/*    */   private final boolean control;
/*    */   private final boolean alt;
/*    */   private final boolean meta;
/*    */   private final ByteBuffer data;
/*    */ 
/*    */   public WCTouchEvent(int paramInt, List<WCTouchPoint> paramList, long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*    */   {
/* 29 */     this.id = paramInt;
/* 30 */     this.when = paramLong;
/* 31 */     this.shift = paramBoolean1;
/* 32 */     this.control = paramBoolean2;
/* 33 */     this.alt = paramBoolean3;
/* 34 */     this.meta = paramBoolean4;
/*    */ 
/* 36 */     this.data = ByteBuffer.allocateDirect(24 * paramList.size());
/* 37 */     this.data.order(ByteOrder.nativeOrder());
/* 38 */     for (WCTouchPoint localWCTouchPoint : paramList)
/* 39 */       localWCTouchPoint.putTo(this.data);
/*    */   }
/*    */ 
/*    */   public int getID() {
/* 43 */     return this.id; } 
/* 44 */   public long getWhen() { return this.when; } 
/*    */   public boolean isShiftDown() {
/* 46 */     return this.shift; } 
/* 47 */   public boolean isControlDown() { return this.control; } 
/* 48 */   public boolean isAltDown() { return this.alt; } 
/* 49 */   public boolean isMetaDown() { return this.meta; } 
/*    */   public ByteBuffer getTouchData() {
/* 51 */     return this.data;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCTouchEvent
 * JD-Core Version:    0.6.2
 */