/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Pixels;
/*    */ 
/*    */ class GtkChildWindow extends GtkWindow
/*    */ {
/*    */   public GtkChildWindow(long parent)
/*    */   {
/* 12 */     super(parent);
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
/*    */ 
/*    */   protected boolean _maximize(long ptr, boolean maximize, boolean wasMaximized)
/*    */   {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean _minimize(long ptr, boolean minimize)
/*    */   {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   protected void _setEnabled(long ptr, boolean enabled)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _setFocusable(long ptr, boolean isFocusable)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _setGravity(long ptr, float xGravity, float yGravity)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _setIcon(long ptr, Pixels pixels)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _setLevel(long ptr, int level)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void _setAlpha(long ptr, float alpha)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected boolean _setMaximumSize(long ptr, int width, int height)
/*    */   {
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean _setMinimumSize(long ptr, int width, int height)
/*    */   {
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean _setResizable(long ptr, boolean resizable)
/*    */   {
/* 79 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean _setMenubar(long ptr, long menubarPtr)
/*    */   {
/* 84 */     return true;
/*    */   }
/*    */ 
/*    */   protected boolean _setTitle(long ptr, String title)
/*    */   {
/* 89 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkChildWindow
 * JD-Core Version:    0.6.2
 */