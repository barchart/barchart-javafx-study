/*    */ package com.sun.glass.ui.mac;
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
/*    */ public class MacPlatformFactory extends PlatformFactory
/*    */ {
/*    */   public Application createApplication()
/*    */   {
/* 33 */     return new MacApplication();
/*    */   }
/*    */ 
/*    */   public MenuBarDelegate createMenuBarDelegate(MenuBar menubar) {
/* 37 */     return new MacMenuBarDelegate();
/*    */   }
/*    */ 
/*    */   public MenuDelegate createMenuDelegate(Menu menu) {
/* 41 */     return new MacMenuDelegate(menu);
/*    */   }
/*    */ 
/*    */   public MenuItemDelegate createMenuItemDelegate(MenuItem item) {
/* 45 */     return new MacMenuDelegate();
/*    */   }
/*    */ 
/*    */   public ClipboardDelegate createClipboardDelegate() {
/* 49 */     return new MacClipboardDelegate();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 23 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 26 */         Application.loadNativeLibrary();
/* 27 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacPlatformFactory
 * JD-Core Version:    0.6.2
 */