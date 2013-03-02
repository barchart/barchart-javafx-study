/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ class GtkMenuDelegate
/*    */   implements MenuDelegate
/*    */ {
/*    */   public boolean createMenu(String title, boolean enabled)
/*    */   {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setTitle(String title) {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setEnabled(boolean enabled) {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setPixels(Pixels pixels) {
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos) {
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuItemDelegate item, int pos) {
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos) {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuItemDelegate item, int pos) {
/* 49 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkMenuDelegate
 * JD-Core Version:    0.6.2
 */