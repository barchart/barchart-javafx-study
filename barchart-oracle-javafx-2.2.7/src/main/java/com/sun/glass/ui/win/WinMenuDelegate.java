/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Menu;
/*    */ import com.sun.glass.ui.Pixels;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ public class WinMenuDelegate extends WinMenuImpl
/*    */   implements MenuDelegate
/*    */ {
/*    */   private final Menu owner;
/* 14 */   private WinMenuImpl parent = null;
/*    */ 
/*    */   public WinMenuDelegate(Menu menu) {
/* 17 */     this.owner = menu;
/*    */   }
/*    */ 
/*    */   public Menu getOwner()
/*    */   {
/* 24 */     return this.owner;
/*    */   }
/*    */ 
/*    */   public boolean createMenu(String title, boolean enabled) {
/* 28 */     return create();
/*    */   }
/*    */ 
/*    */   public void dispose() {
/* 32 */     destroy();
/*    */   }
/*    */ 
/*    */   public boolean setTitle(String title)
/*    */   {
/* 38 */     if (this.parent != null) {
/* 39 */       return this.parent.setSubmenuTitle(this, title);
/*    */     }
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setEnabled(boolean enabled) {
/* 45 */     if (this.parent != null) {
/* 46 */       return this.parent.enableSubmenu(this, enabled);
/*    */     }
/* 48 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean setPixels(Pixels pixels)
/*    */   {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos) {
/* 57 */     return insertSubmenu((WinMenuDelegate)menu, pos);
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuItemDelegate item, int pos)
/*    */   {
/* 62 */     return insertItem((WinMenuItemDelegate)item, pos);
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos)
/*    */   {
/* 67 */     return removeMenu((WinMenuDelegate)menu, pos);
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuItemDelegate item, int pos)
/*    */   {
/* 72 */     return removeItem((WinMenuItemDelegate)item, pos);
/*    */   }
/*    */ 
/*    */   WinMenuImpl getParent() {
/* 76 */     return this.parent;
/*    */   }
/*    */ 
/*    */   void setParent(WinMenuImpl newParent) {
/* 80 */     this.parent = newParent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinMenuDelegate
 * JD-Core Version:    0.6.2
 */