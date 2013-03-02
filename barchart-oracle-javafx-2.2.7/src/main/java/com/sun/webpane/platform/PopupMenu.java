/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import com.sun.webpane.platform.graphics.WCFont;
/*    */ 
/*    */ public abstract class PopupMenu
/*    */ {
/*    */   private long pdata;
/*    */ 
/*    */   public abstract void show(WebPage paramWebPage, int paramInt1, int paramInt2, int paramInt3);
/*    */ 
/*    */   public abstract void hide();
/*    */ 
/*    */   public abstract void setSelectedItem(int paramInt);
/*    */ 
/*    */   public abstract void appendItem(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2, WCFont paramWCFont);
/*    */ 
/*    */   public void notifySelectionCommited(int paramInt)
/*    */   {
/* 21 */     twkSelectionCommited(this.pdata, paramInt);
/*    */   }
/*    */ 
/*    */   public void notifyPopupClosed() {
/* 25 */     twkPopupClosed(this.pdata);
/*    */   }
/*    */ 
/*    */   private static PopupMenu fwkCreatePopupMenu(long paramLong) {
/* 29 */     if (MenuManager.getMenuManager() != null) {
/* 30 */       PopupMenu localPopupMenu = MenuManager.getMenuManager().createPopupMenu();
/* 31 */       localPopupMenu.pdata = paramLong;
/* 32 */       return localPopupMenu;
/*    */     }
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   private void fwkShow(WebPage paramWebPage, int paramInt1, int paramInt2, int paramInt3) {
/* 38 */     assert (paramWebPage != null);
/* 39 */     show(paramWebPage, paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   private void fwkHide() {
/* 43 */     hide();
/*    */   }
/*    */ 
/*    */   private void fwkSetSelectedItem(int paramInt) {
/* 47 */     setSelectedItem(paramInt);
/*    */   }
/*    */ 
/*    */   private void fwkAppendItem(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2, WCFont paramWCFont)
/*    */   {
/* 53 */     appendItem(paramString, paramBoolean1, paramBoolean2, paramBoolean3, paramInt1, paramInt2, paramWCFont);
/*    */   }
/*    */ 
/*    */   private void fwkDestroy() {
/* 57 */     this.pdata = 0L;
/*    */   }
/*    */ 
/*    */   private native void twkSelectionCommited(long paramLong, int paramInt);
/*    */ 
/*    */   private native void twkPopupClosed(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.PopupMenu
 * JD-Core Version:    0.6.2
 */