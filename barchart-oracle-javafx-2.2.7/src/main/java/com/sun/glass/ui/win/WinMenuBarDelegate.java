/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.MenuBar;
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ 
/*    */ public class WinMenuBarDelegate extends WinMenuImpl
/*    */   implements MenuBarDelegate
/*    */ {
/*    */   private final MenuBar owner;
/*    */ 
/*    */   WinMenuBarDelegate(MenuBar menubar)
/*    */   {
/* 16 */     this.owner = menubar;
/*    */   }
/*    */ 
/*    */   public MenuBar getOwner() {
/* 20 */     return this.owner;
/*    */   }
/*    */ 
/*    */   public boolean createMenuBar() {
/* 24 */     return create();
/*    */   }
/*    */ 
/*    */   public boolean insert(MenuDelegate menuDelegate, int pos) {
/* 28 */     WinMenuDelegate menu = (WinMenuDelegate)menuDelegate;
/* 29 */     if (menu.getParent() != null);
/* 32 */     return insertSubmenu(menu, pos);
/*    */   }
/*    */ 
/*    */   public boolean remove(MenuDelegate menuDelegate, int pos) {
/* 36 */     WinMenuDelegate menu = (WinMenuDelegate)menuDelegate;
/* 37 */     return removeMenu(menu, pos);
/*    */   }
/*    */ 
/*    */   public long getNativeMenu() {
/* 41 */     return getHMENU();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinMenuBarDelegate
 * JD-Core Version:    0.6.2
 */