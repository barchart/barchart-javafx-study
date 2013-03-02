/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Robot;
/*    */ 
/*    */ class GtkRobot extends Robot
/*    */ {
/*    */   protected native long _create();
/*    */ 
/*    */   protected native void _destroy(long paramLong);
/*    */ 
/*    */   protected native void _keyPress(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _keyRelease(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _mouseMove(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _mousePress(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _mouseRelease(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _mouseWheel(long paramLong, int paramInt);
/*    */ 
/*    */   protected native int _getMouseX(long paramLong);
/*    */ 
/*    */   protected native int _getMouseY(long paramLong);
/*    */ 
/*    */   protected int _getPixelColor(long ptr, int x, int y)
/*    */   {
/* 50 */     int[] result = new int[1];
/* 51 */     _getScreenCapture(ptr, x, y, 1, 1, result);
/* 52 */     return result[0];
/*    */   }
/*    */ 
/*    */   protected native void _getScreenCapture(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkRobot
 * JD-Core Version:    0.6.2
 */