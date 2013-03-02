/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Menu;
/*     */ import com.sun.glass.ui.MenuItem;
/*     */ import com.sun.glass.ui.MenuItem.Callback;
/*     */ import com.sun.glass.ui.Window;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ class WinMenuImpl
/*     */ {
/*     */   private static final boolean LOG_ERROR = true;
/*  33 */   private long ptr = 0L;
/*     */ 
/*     */   static native void _initIDs();
/*     */ 
/*     */   long getHMENU()
/*     */   {
/*  39 */     return this.ptr;
/*     */   }
/*     */ 
/*     */   boolean create() {
/*  43 */     this.ptr = _create();
/*  44 */     return this.ptr != 0L;
/*     */   }
/*     */ 
/*     */   void destroy() {
/*  48 */     if (this.ptr != 0L) {
/*  49 */       _destroy(this.ptr);
/*  50 */       this.ptr = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean insertSubmenu(WinMenuDelegate menu, int pos) {
/*  55 */     menu.setParent(this);
/*  56 */     if (!_insertSubmenu(this.ptr, pos, menu.getHMENU(), menu.getOwner().getTitle(), menu.getOwner().isEnabled()))
/*     */     {
/*  58 */       menu.setParent(null);
/*  59 */       return false;
/*     */     }
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   boolean insertItem(WinMenuItemDelegate item, int pos)
/*     */   {
/*  66 */     if (item == null) {
/*  67 */       return _insertSeparator(this.ptr, pos);
/*     */     }
/*     */ 
/*  70 */     item.setParent(this);
/*     */ 
/*  72 */     if (!_insertItem(this.ptr, pos, item.getCmdID(), item.getOwner().getTitle(), item.getOwner().isEnabled(), item.getOwner().isChecked(), item.getOwner().getCallback(), item.getOwner().getShortcutKey(), item.getOwner().getShortcutModifiers()))
/*     */     {
/*  79 */       item.setParent(null);
/*  80 */       return false;
/*     */     }
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */   boolean removeMenu(WinMenuDelegate submenu, int pos) {
/*  86 */     if (_removeAtPos(this.ptr, pos)) {
/*  87 */       submenu.setParent(null);
/*  88 */       return true;
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   boolean removeItem(WinMenuItemDelegate item, int pos) {
/*  94 */     if (_removeAtPos(this.ptr, pos)) {
/*  95 */       if (item != null) {
/*  96 */         item.setParent(null);
/*     */       }
/*  98 */       return true;
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   boolean setSubmenuTitle(WinMenuDelegate submenu, String title) {
/* 104 */     return _setSubmenuTitle(this.ptr, submenu.getHMENU(), title);
/*     */   }
/*     */ 
/*     */   boolean setItemTitle(WinMenuItemDelegate submenu, String title) {
/* 108 */     return _setItemTitle(this.ptr, submenu.getCmdID(), title);
/*     */   }
/*     */ 
/*     */   boolean enableSubmenu(WinMenuDelegate submenu, boolean enable) {
/* 112 */     return _enableSubmenu(this.ptr, submenu.getHMENU(), enable);
/*     */   }
/*     */ 
/*     */   boolean enableItem(WinMenuItemDelegate item, boolean enable) {
/* 116 */     return _enableItem(this.ptr, item.getCmdID(), enable);
/*     */   }
/*     */ 
/*     */   public boolean checkItem(WinMenuItemDelegate item, boolean check) {
/* 120 */     return _checkItem(this.ptr, item.getCmdID(), check);
/*     */   }
/*     */ 
/*     */   private static boolean notifyCommand(Window window, int cmdID)
/*     */   {
/* 125 */     WinMenuItemDelegate item = WinMenuItemDelegate.CommandIDManager.getHandler(cmdID);
/* 126 */     if (item != null) {
/* 127 */       MenuItem.Callback callback = item.getOwner().getCallback();
/* 128 */       if (callback != null) {
/* 129 */         callback.action();
/* 130 */         return true;
/*     */       }
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   private native long _create();
/*     */ 
/*     */   private native void _destroy(long paramLong);
/*     */ 
/*     */   private native boolean _insertItem(long paramLong, int paramInt1, int paramInt2, String paramString, boolean paramBoolean1, boolean paramBoolean2, MenuItem.Callback paramCallback, int paramInt3, int paramInt4);
/*     */ 
/*     */   private native boolean _insertSubmenu(long paramLong1, int paramInt, long paramLong2, String paramString, boolean paramBoolean);
/*     */ 
/*     */   private native boolean _insertSeparator(long paramLong, int paramInt);
/*     */ 
/*     */   private native boolean _removeAtPos(long paramLong, int paramInt);
/*     */ 
/*     */   private native boolean _setItemTitle(long paramLong, int paramInt, String paramString);
/*     */ 
/*     */   private native boolean _setSubmenuTitle(long paramLong1, long paramLong2, String paramString);
/*     */ 
/*     */   private native boolean _enableItem(long paramLong, int paramInt, boolean paramBoolean);
/*     */ 
/*     */   private native boolean _enableSubmenu(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */ 
/*     */   private native boolean _checkItem(long paramLong, int paramInt, boolean paramBoolean);
/*     */ 
/*     */   static
/*     */   {
/*  21 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  24 */         Application.loadNativeLibrary();
/*  25 */         return null;
/*     */       }
/*     */     });
/*  28 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinMenuImpl
 * JD-Core Version:    0.6.2
 */