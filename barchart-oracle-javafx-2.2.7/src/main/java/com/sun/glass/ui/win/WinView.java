/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Pen;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.View;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.Map;
/*    */ 
/*    */ final class WinView extends View
/*    */ {
/* 45 */   private static final long multiClickTime = _getMultiClickTime_impl();
/* 46 */   private static final int multiClickMaxX = _getMultiClickMaxX_impl();
/* 47 */   private static final int multiClickMaxY = _getMultiClickMaxY_impl();
/*    */ 
/*    */   protected WinView(Pen pen)
/*    */   {
/* 23 */     super(pen);
/*    */   }
/*    */ 
/*    */   private static native void _initIDs();
/*    */ 
/*    */   private static native long _getMultiClickTime_impl();
/*    */ 
/*    */   private static native int _getMultiClickMaxX_impl();
/*    */ 
/*    */   private static native int _getMultiClickMaxY_impl();
/*    */ 
/*    */   static long getMultiClickTime_impl()
/*    */   {
/* 51 */     return multiClickTime;
/*    */   }
/*    */ 
/*    */   static int getMultiClickMaxX_impl() {
/* 55 */     return multiClickMaxX;
/*    */   }
/*    */ 
/*    */   static int getMultiClickMaxY_impl() {
/* 59 */     return multiClickMaxY;
/*    */   }
/*    */ 
/*    */   protected native void _enableInputMethodEvents(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native long _create(Map paramMap);
/*    */ 
/*    */   protected native long _getNativeView(long paramLong);
/*    */ 
/*    */   protected native int _getX(long paramLong);
/*    */ 
/*    */   protected native int _getY(long paramLong);
/*    */ 
/*    */   protected native void _setParent(long paramLong1, long paramLong2);
/*    */ 
/*    */   protected native boolean _close(long paramLong);
/*    */ 
/*    */   protected native void _repaint(long paramLong);
/*    */ 
/*    */   protected native boolean _begin(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native void _end(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*    */ 
/*    */   protected native void _uploadPixels(long paramLong, Pixels paramPixels);
/*    */ 
/*    */   protected native boolean _enterFullscreen(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*    */ 
/*    */   protected native void _exitFullscreen(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   static
/*    */   {
/* 36 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 39 */         Application.loadNativeLibrary();
/* 40 */         return null;
/*    */       }
/*    */     });
/* 43 */     _initIDs();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinView
 * JD-Core Version:    0.6.2
 */