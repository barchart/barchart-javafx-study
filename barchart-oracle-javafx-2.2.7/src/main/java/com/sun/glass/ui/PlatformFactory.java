/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import com.sun.glass.ui.delegate.ClipboardDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public abstract class PlatformFactory
/*    */ {
/*    */   private static PlatformFactory instance;
/*    */ 
/*    */   public static PlatformFactory getPlatformFactory()
/*    */   {
/* 14 */     if (instance == null) {
/*    */       try {
/* 16 */         String platform = Platform.DeterminePlatform();
/* 17 */         String factory = "com.sun.glass.ui." + platform.toLowerCase() + "." + platform + "PlatformFactory";
/*    */ 
/* 20 */         Class c = Class.forName(factory);
/* 21 */         if (c == null) {
/* 22 */           System.out.println("Failed to load factory");
/* 23 */           return null;
/*    */         }
/* 25 */         instance = (PlatformFactory)c.newInstance();
/*    */       } catch (Exception e) {
/* 27 */         System.out.println("Failed to load Glass factory class");
/*    */       }
/*    */     }
/* 30 */     return instance;
/*    */   }
/*    */ 
/*    */   public abstract Application createApplication();
/*    */ 
/*    */   public abstract MenuBarDelegate createMenuBarDelegate(MenuBar paramMenuBar);
/*    */ 
/*    */   public abstract MenuDelegate createMenuDelegate(Menu paramMenu);
/*    */ 
/*    */   public abstract MenuItemDelegate createMenuItemDelegate(MenuItem paramMenuItem);
/*    */ 
/*    */   public abstract ClipboardDelegate createClipboardDelegate();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.PlatformFactory
 * JD-Core Version:    0.6.2
 */