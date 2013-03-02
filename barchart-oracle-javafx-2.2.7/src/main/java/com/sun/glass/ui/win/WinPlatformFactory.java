/*    */ package com.sun.glass.ui.win;
/*    */ 
/*    */ import com.sun.glass.ui.Menu;
/*    */ import com.sun.glass.ui.MenuBar;
/*    */ import com.sun.glass.ui.MenuItem;
/*    */ import com.sun.glass.ui.PlatformFactory;
/*    */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ public class WinPlatformFactory extends PlatformFactory
/*    */ {
/*    */   public WinApplication createApplication()
/*    */   {
/* 20 */     return new WinApplication();
/*    */   }
/*    */ 
/*    */   public MenuBarDelegate createMenuBarDelegate(MenuBar menubar) {
/* 24 */     return new WinMenuBarDelegate(menubar);
/*    */   }
/*    */ 
/*    */   public MenuDelegate createMenuDelegate(Menu menu) {
/* 28 */     return new WinMenuDelegate(menu);
/*    */   }
/*    */ 
/*    */   public MenuItemDelegate createMenuItemDelegate(MenuItem item) {
/* 32 */     return new WinMenuItemDelegate(item);
/*    */   }
/*    */ 
/*    */   public ClipboardDelegate createClipboardDelegate() {
/* 36 */     return new WinClipboardDelegate();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinPlatformFactory
 * JD-Core Version:    0.6.2
 */