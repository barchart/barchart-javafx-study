/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Menu;
/*    */ import com.sun.glass.ui.MenuBar;
/*    */ import com.sun.glass.ui.MenuItem;
/*    */ import com.sun.glass.ui.PlatformFactory;
/*    */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ 
/*    */ public class X11PlatformFactory extends PlatformFactory
/*    */ {
/*    */   public Application createApplication()
/*    */   {
/* 26 */     return new X11Application();
/*    */   }
/*    */ 
/*    */   public MenuBarDelegate createMenuBarDelegate(MenuBar menubar) {
/* 30 */     return new X11MenuBarDelegate();
/*    */   }
/*    */ 
/*    */   public MenuDelegate createMenuDelegate(Menu menu) {
/* 34 */     return new X11MenuDelegate();
/*    */   }
/*    */ 
/*    */   public MenuItemDelegate createMenuItemDelegate(MenuItem item) {
/* 38 */     return new X11MenuDelegate();
/*    */   }
/*    */ 
/*    */   public ClipboardDelegate createClipboardDelegate() {
/* 42 */     return new X11ClipboardDelegate();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 22 */     X11Application.initLibrary();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11PlatformFactory
 * JD-Core Version:    0.6.2
 */