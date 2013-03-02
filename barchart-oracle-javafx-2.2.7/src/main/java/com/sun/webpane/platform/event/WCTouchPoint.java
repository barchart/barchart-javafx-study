/*    */ package com.sun.webpane.platform.event;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class WCTouchPoint
/*    */ {
/*    */   public static final int STATE_RELEASED = 0;
/*    */   public static final int STATE_PRESSED = 1;
/*    */   public static final int STATE_MOVED = 2;
/*    */   public static final int STATE_STATIONARY = 3;
/*    */   public static final int STATE_CANCELLED = 4;
/*    */   private final int id;
/*    */   private int state;
/*    */   private double x;
/*    */   private double y;
/*    */   private double screenX;
/*    */   private double screenY;
/*    */ 
/*    */   public WCTouchPoint(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*    */   {
/* 25 */     this.id = paramInt1;
/* 26 */     this.state = paramInt2;
/* 27 */     this.x = paramDouble1;
/* 28 */     this.y = paramDouble2;
/* 29 */     this.screenX = paramDouble3;
/* 30 */     this.screenY = paramDouble4;
/*    */   }
/*    */   public int getID() {
/* 33 */     return this.id; } 
/* 34 */   public int getState() { return this.state; } 
/*    */   public double getX() {
/* 36 */     return this.x; } 
/* 37 */   public double getY() { return this.y; } 
/*    */   public double getScreenX() {
/* 39 */     return this.screenX; } 
/* 40 */   public double getScreenY() { return this.screenY; }
/*    */ 
/*    */   public void update(int paramInt) {
/* 43 */     this.state = paramInt;
/*    */   }
/*    */ 
/*    */   public void update(double paramDouble1, double paramDouble2) {
/* 47 */     paramDouble1 -= this.x;
/* 48 */     paramDouble2 -= this.y;
/* 49 */     this.x += paramDouble1;
/* 50 */     this.y += paramDouble2;
/* 51 */     this.screenX += paramDouble1;
/* 52 */     this.screenY += paramDouble2;
/* 53 */     this.state = 2;
/*    */   }
/*    */ 
/*    */   void putTo(ByteBuffer paramByteBuffer) {
/* 57 */     paramByteBuffer.putInt(this.id);
/* 58 */     paramByteBuffer.putInt(this.state);
/* 59 */     paramByteBuffer.putInt(Math.round((float)this.x));
/* 60 */     paramByteBuffer.putInt(Math.round((float)this.y));
/* 61 */     paramByteBuffer.putInt(Math.round((float)this.screenX));
/* 62 */     paramByteBuffer.putInt(Math.round((float)this.screenY));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.event.WCTouchPoint
 * JD-Core Version:    0.6.2
 */