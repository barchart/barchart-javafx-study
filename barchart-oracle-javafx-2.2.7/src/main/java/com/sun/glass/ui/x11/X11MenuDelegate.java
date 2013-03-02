/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.MenuItem.Callback;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ public class X11MenuDelegate
/*    */   implements MenuDelegate, MenuItemDelegate
/*    */ {
/*    */   public boolean createMenu(String title, boolean enabled)
/*    */   {
/* 20 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setTitle(String title)
/*    */   {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setEnabled(boolean enabled)
/*    */   {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos)
/*    */   {
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuItemDelegate item, int pos)
/*    */   {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos)
/*    */   {
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuItemDelegate item, int pos)
/*    */   {
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels, boolean enabled, boolean checked)
/*    */   {
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setCallback(MenuItem.Callback callback)
/*    */   {
/* 60 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setShortcut(int shortcutKey, int shortcutModifiers)
/*    */   {
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setPixels(Pixels pixels)
/*    */   {
/* 70 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setChecked(boolean checked)
/*    */   {
/* 75 */     return true;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11MenuDelegate
 * JD-Core Version:    0.6.2
 */