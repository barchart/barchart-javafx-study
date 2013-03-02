/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Pixels;
/*    */ 
/*    */ final class WinChildWindow extends WinWindow
/*    */ {
/*    */   protected WinChildWindow(long parent)
/*    */   {
/* 18 */     super(parent);
/*    */   }
/*    */ 
/*    */   protected long _createWindow(long ownerPtr, long screenPtr, int mask) {
/* 22 */     return 0L; } 
/* 23 */   protected boolean _setMenubar(long ptr, long menubarPtr) { return false; } 
/* 24 */   protected boolean _minimize(long ptr, boolean minimize) { return false; } 
/* 25 */   protected boolean _maximize(long ptr, boolean maximize, boolean wasMaximized) { return false; } 
/* 26 */   protected boolean _setResizable(long ptr, boolean resizable) { return false; } 
/* 27 */   protected boolean _setTitle(long ptr, String title) { return false; } 
/*    */   protected void _setLevel(long ptr, int level) {  } 
/*    */   protected void _setAlpha(long ptr, float alpha) {  } 
/* 30 */   protected boolean _setMinimumSize(long ptr, int width, int height) { return false; } 
/* 31 */   protected boolean _setMaximumSize(long ptr, int width, int height) { return false; }
/*    */ 
/*    */ 
/*    */   protected void _setIcon(long ptr, Pixels pixels)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _enterModal(long ptr)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _enterModalWithWindow(long dialog, long window)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _exitModal(long ptr)
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinChildWindow
 * JD-Core Version:    0.6.2
 */