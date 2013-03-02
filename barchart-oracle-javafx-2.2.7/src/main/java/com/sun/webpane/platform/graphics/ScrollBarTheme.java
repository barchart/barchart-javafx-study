/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ public abstract class ScrollBarTheme extends Ref
/*    */ {
/*    */   public static final int NO_PART = 0;
/*    */   public static final int BACK_BUTTON_START_PART = 1;
/*    */   public static final int FORWARD_BUTTON_START_PART = 2;
/*    */   public static final int BACK_TRACK_PART = 4;
/*    */   public static final int THUMB_PART = 8;
/*    */   public static final int FORWARD_TRACK_PART = 16;
/*    */   public static final int BACK_BUTTON_END_PART = 32;
/*    */   public static final int FORWARD_BUTTON_END_PART = 64;
/*    */   public static final int HORIZONTAL_SCROLLBAR = 0;
/*    */   public static final int VERTICAL_SCROLLBAR = 1;
/*    */   private static int thickness;
/*    */ 
/*    */   public static int getThickness()
/*    */   {
/* 24 */     return thickness > 0 ? thickness : 12;
/*    */   }
/*    */ 
/*    */   public static void setThickness(int paramInt) {
/* 28 */     thickness = paramInt;
/*    */   }
/*    */ 
/*    */   public abstract Ref createWidget(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*    */ 
/*    */   public abstract void paint(WCGraphicsContext paramWCGraphicsContext, Ref paramRef, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */ 
/*    */   public abstract int hitTest(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);
/*    */ 
/*    */   public abstract int getThumbPosition(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*    */ 
/*    */   public abstract int getThumbLength(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*    */ 
/*    */   public abstract int getTrackPosition(int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */   public abstract int getTrackLength(int paramInt1, int paramInt2, int paramInt3);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.ScrollBarTheme
 * JD-Core Version:    0.6.2
 */