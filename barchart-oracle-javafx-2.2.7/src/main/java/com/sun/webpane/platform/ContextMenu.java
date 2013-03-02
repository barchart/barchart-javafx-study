/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ public abstract class ContextMenu
/*    */ {
/*    */   public abstract void show(ShowContext paramShowContext, int paramInt1, int paramInt2);
/*    */ 
/*    */   public abstract void appendItem(ContextMenuItem paramContextMenuItem);
/*    */ 
/*    */   public abstract void insertItem(ContextMenuItem paramContextMenuItem, int paramInt);
/*    */ 
/*    */   public abstract int getItemCount();
/*    */ 
/*    */   private static ContextMenu fwkCreateContextMenu()
/*    */   {
/* 35 */     if (MenuManager.getMenuManager() != null) {
/* 36 */       return MenuManager.getMenuManager().createContextMenu();
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   private void fwkShow(WebPage paramWebPage, long paramLong, int paramInt1, int paramInt2) {
/* 42 */     show(new ShowContext(paramWebPage, paramLong, null), paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   private void fwkAppendItem(ContextMenuItem paramContextMenuItem) {
/* 46 */     appendItem(paramContextMenuItem);
/*    */   }
/*    */ 
/*    */   private void fwkInsertItem(ContextMenuItem paramContextMenuItem, int paramInt) {
/* 50 */     insertItem(paramContextMenuItem, paramInt);
/*    */   }
/*    */ 
/*    */   private int fwkGetItemCount() {
/* 54 */     return getItemCount();
/*    */   }
/*    */ 
/*    */   private native void twkHandleItemSelected(long paramLong, int paramInt);
/*    */ 
/*    */   public class ShowContext
/*    */   {
/*    */     private WebPage page;
/*    */     private long pdata;
/*    */ 
/*    */     private ShowContext(WebPage paramLong, long arg3)
/*    */     {
/* 21 */       this.page = paramLong;
/*    */       Object localObject;
/* 22 */       this.pdata = localObject;
/*    */     }
/*    */ 
/*    */     public WebPage getPage() {
/* 26 */       return this.page;
/*    */     }
/*    */ 
/*    */     public void notifyItemSelected(int paramInt) {
/* 30 */       ContextMenu.this.twkHandleItemSelected(this.pdata, paramInt);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.ContextMenu
 * JD-Core Version:    0.6.2
 */