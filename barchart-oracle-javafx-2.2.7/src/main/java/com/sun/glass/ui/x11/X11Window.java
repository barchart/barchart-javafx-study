/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Cursor;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.Screen;
/*    */ import com.sun.glass.ui.View;
/*    */ import com.sun.glass.ui.Window;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class X11Window extends Window
/*    */ {
/*    */   protected X11Window(Window owner, Screen screen, int styleMask)
/*    */   {
/* 29 */     super(owner, screen, styleMask);
/*    */   }
/*    */ 
/*    */   protected X11Window(long parent) {
/* 33 */     super(parent); } 
/*    */   protected native void _setSize(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _setContentSize(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _move(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _setBounds(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2);
/*    */ 
/*    */   protected native long _createWindow(long paramLong1, long paramLong2, int paramInt);
/*    */ 
/*    */   protected native long _createChildWindow(long paramLong);
/*    */ 
/*    */   protected native boolean _close(long paramLong);
/*    */ 
/*    */   protected native boolean _setView(long paramLong, View paramView);
/*    */ 
/*    */   protected native boolean _setMenubar(long paramLong1, long paramLong2);
/*    */ 
/*    */   protected native boolean _minimize(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native boolean _maximize(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*    */ 
/*    */   protected native boolean _setVisible(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native boolean _setResizable(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native boolean _requestFocus(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _setFocusable(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native boolean _setTitle(long paramLong, String paramString);
/*    */ 
/*    */   protected native void _setLevel(long paramLong, int paramInt);
/*    */ 
/*    */   protected native void _setAlpha(long paramLong, float paramFloat);
/*    */ 
/*    */   protected native boolean _setBackground(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3);
/*    */ 
/*    */   protected native void _setEnabled(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   protected native boolean _setMinimumSize(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native boolean _setMaximumSize(long paramLong, int paramInt1, int paramInt2);
/*    */ 
/*    */   protected native void _setIcon(long paramLong, Pixels paramPixels);
/*    */ 
/*    */   protected native void _toFront(long paramLong);
/*    */ 
/*    */   protected native void _toBack(long paramLong);
/*    */ 
/*    */   protected native void _enterModal(long paramLong);
/*    */ 
/*    */   protected native void _enterModalWithWindow(long paramLong1, long paramLong2);
/*    */ 
/*    */   protected native void _exitModal(long paramLong);
/*    */ 
/* 69 */   protected boolean _grabFocus(long ptr) { return false; } 
/*    */   protected void _ungrabFocus(long ptr) {
/*    */   }
/*    */ 
/*    */   protected int _getEmbeddedX(long ptr) {
/* 74 */     return 0;
/*    */   }
/*    */ 
/*    */   protected int _getEmbeddedY(long ptr) {
/* 78 */     return 0;
/*    */   }
/*    */ 
/*    */   protected void _setCursor(long ptr, Cursor cursor)
/*    */   {
/* 83 */     System.err.println("WARNING: X11Window._setCursor() not implemented");
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 25 */     X11Application.initLibrary();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Window
 * JD-Core Version:    0.6.2
 */