/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import com.sun.glass.ui.delegate.MenuDelegate;
/*    */ 
/*    */ class MacMenuBarDelegate
/*    */   implements MenuBarDelegate
/*    */ {
/*    */   long ptr;
/*    */ 
/*    */   private native long _createMenuBar();
/*    */ 
/*    */   public boolean createMenuBar()
/*    */   {
/* 16 */     this.ptr = _createMenuBar();
/* 17 */     return this.ptr != 0L;
/*    */   }
/*    */   private native void _insert(long paramLong1, long paramLong2, int paramInt);
/*    */ 
/*    */   public boolean insert(MenuDelegate menu, int pos) {
/* 22 */     MacMenuDelegate macMenu = (MacMenuDelegate)menu;
/* 23 */     _insert(this.ptr, macMenu.ptr, pos);
/* 24 */     return true;
/*    */   }
/*    */   private native void _remove(long paramLong1, long paramLong2, int paramInt);
/*    */ 
/*    */   public boolean remove(MenuDelegate menu, int pos) {
/* 29 */     MacMenuDelegate macMenu = (MacMenuDelegate)menu;
/* 30 */     _remove(this.ptr, macMenu.ptr, pos);
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   public long getNativeMenu() {
/* 35 */     return this.ptr;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacMenuBarDelegate
 * JD-Core Version:    0.6.2
 */