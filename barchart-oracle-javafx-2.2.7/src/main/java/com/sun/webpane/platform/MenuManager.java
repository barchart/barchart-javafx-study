/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ public abstract class MenuManager
/*    */ {
/*    */   private static MenuManager instance;
/*    */ 
/*    */   public static void setMenuManager(MenuManager paramMenuManager)
/*    */   {
/* 11 */     instance = paramMenuManager;
/*    */   }
/*    */ 
/*    */   public static MenuManager getMenuManager() {
/* 15 */     return instance;
/*    */   }
/*    */ 
/*    */   public abstract PopupMenu createPopupMenu();
/*    */ 
/*    */   public abstract ContextMenu createContextMenu();
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.MenuManager
 * JD-Core Version:    0.6.2
 */