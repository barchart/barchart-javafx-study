/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ 
/*    */ class GtkMenuBarDelegate
/*    */   implements MenuBarDelegate
/*    */ {
/*    */   public boolean createMenuBar()
/*    */   {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos) {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos) {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   public long getNativeMenu() {
/* 32 */     return 0L;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkMenuBarDelegate
 * JD-Core Version:    0.6.2
 */