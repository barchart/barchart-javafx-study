/*    */ package com.sun.glass.ui.gtk;
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
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class GtkPlatformFactory extends PlatformFactory
/*    */ {
/*    */   public Application createApplication()
/*    */   {
/* 35 */     return new GtkApplication();
/*    */   }
/*    */ 
/*    */   public MenuBarDelegate createMenuBarDelegate(MenuBar menubar) {
/* 39 */     return new GtkMenuBarDelegate();
/*    */   }
/*    */ 
/*    */   public MenuDelegate createMenuDelegate(Menu menu) {
/* 43 */     return new GtkMenuDelegate();
/*    */   }
/*    */ 
/*    */   public MenuItemDelegate createMenuItemDelegate(MenuItem item) {
/* 47 */     return new GtkMenuItemDelegate();
/*    */   }
/*    */ 
/*    */   public ClipboardDelegate createClipboardDelegate() {
/* 51 */     return new GtkClipboardDelegate();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 25 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 28 */         Application.loadNativeLibrary();
/* 29 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkPlatformFactory
 * JD-Core Version:    0.6.2
 */