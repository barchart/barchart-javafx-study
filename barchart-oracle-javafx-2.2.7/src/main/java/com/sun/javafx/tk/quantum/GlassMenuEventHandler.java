/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ import com.sun.glass.ui.Menu;
/*    */ import com.sun.glass.ui.Menu.EventHandler;
/*    */ import com.sun.javafx.menu.MenuBase;
/*    */ 
/*    */ public class GlassMenuEventHandler extends Menu.EventHandler
/*    */ {
/*    */   private MenuBase menuBase;
/*    */   private boolean menuOpen;
/*    */ 
/*    */   public GlassMenuEventHandler(MenuBase paramMenuBase)
/*    */   {
/* 21 */     this.menuBase = paramMenuBase;
/*    */   }
/*    */ 
/*    */   public void handleMenuOpening(Menu paramMenu, long paramLong) {
/* 25 */     this.menuBase.show();
/* 26 */     this.menuOpen = true;
/*    */   }
/*    */ 
/*    */   public void handleMenuClosed(Menu paramMenu, long paramLong) {
/* 30 */     this.menuBase.hide();
/* 31 */     this.menuOpen = false;
/*    */   }
/*    */ 
/*    */   protected boolean isMenuOpen() {
/* 35 */     return this.menuOpen;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassMenuEventHandler
 * JD-Core Version:    0.6.2
 */