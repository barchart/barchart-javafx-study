/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Robot;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class WinRobot extends Robot
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
/*    */   protected native int _getPixelColor(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _getScreenCapture(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);
/*    */ 
/*    */   static
/*    */   {
/* 22 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 25 */         Application.loadNativeLibrary();
/* 26 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinRobot
 * JD-Core Version:    0.6.2
 */