/*    */ package com.sun.webpane.sg;
/*    */ 
/*    */ import com.sun.webpane.platform.ContextMenu;
/*    */ import com.sun.webpane.platform.MenuManager;
/*    */ import com.sun.webpane.platform.PopupMenu;
/*    */ import com.sun.webpane.sg.theme.ContextMenuImpl;
/*    */ import com.sun.webpane.sg.theme.PopupMenuImpl;
/*    */ 
/*    */ public class MenuManagerImpl extends MenuManager
/*    */ {
/*    */   public PopupMenu createPopupMenu()
/*    */   {
/* 16 */     return new PopupMenuImpl();
/*    */   }
/*    */ 
/*    */   public ContextMenu createContextMenu() {
/* 20 */     return new ContextMenuImpl();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.MenuManagerImpl
 * JD-Core Version:    0.6.2
 */