/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.MenuItem.Callback;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ class GtkMenuItemDelegate
/*    */   implements MenuItemDelegate
/*    */ {
/*    */   public boolean createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels, boolean enabled, boolean checked)
/*    */   {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setTitle(String title) {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setCallback(MenuItem.Callback callback) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setShortcut(int shortcutKey, int shortcutModifiers) {
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setPixels(Pixels pixels) {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setEnabled(boolean enabled) {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setChecked(boolean checked) {
/* 45 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkMenuItemDelegate
 * JD-Core Version:    0.6.2
 */