/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Menu;
/*     */ import com.sun.glass.ui.MenuItem.Callback;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.delegate.MenuDelegate;
/*     */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ class MacMenuDelegate
/*     */   implements MenuDelegate, MenuItemDelegate
/*     */ {
/*     */   long ptr;
/*     */   private Menu menu;
/*     */ 
/*     */   static native void _initIDs();
/*     */ 
/*     */   public MacMenuDelegate(Menu menu)
/*     */   {
/*  38 */     this.menu = menu;
/*     */   }
/*     */   public MacMenuDelegate() {
/*     */   }
/*     */ 
/*     */   private native long _createMenu(String paramString, boolean paramBoolean);
/*     */ 
/*     */   public boolean createMenu(String title, boolean enabled) {
/*  46 */     this.ptr = _createMenu(title, enabled);
/*  47 */     return this.ptr != 0L;
/*     */   }
/*     */ 
/*     */   private native long _createMenuItem(String paramString, char paramChar, int paramInt, Pixels paramPixels, boolean paramBoolean1, boolean paramBoolean2, MenuItem.Callback paramCallback);
/*     */ 
/*     */   public boolean createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels, boolean enabled, boolean checked)
/*     */   {
/*  57 */     this.ptr = _createMenuItem(title, (char)shortcutKey, shortcutModifiers, pixels, enabled, checked, callback);
/*     */ 
/*  59 */     return this.ptr != 0L;
/*     */   }
/*     */   private native void _insert(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public boolean insert(MenuDelegate menu, int pos) {
/*  64 */     MacMenuDelegate macMenu = (MacMenuDelegate)menu;
/*  65 */     _insert(this.ptr, macMenu.ptr, pos);
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean insert(MenuItemDelegate item, int pos) {
/*  70 */     MacMenuDelegate macMenu = (MacMenuDelegate)item;
/*  71 */     _insert(this.ptr, macMenu != null ? macMenu.ptr : 0L, pos);
/*  72 */     return true;
/*     */   }
/*     */   private native void _remove(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   public boolean remove(MenuDelegate menu, int pos) {
/*  77 */     MacMenuDelegate macMenu = (MacMenuDelegate)menu;
/*  78 */     _remove(this.ptr, macMenu.ptr, pos);
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean remove(MenuItemDelegate item, int pos) {
/*  83 */     MacMenuDelegate macMenu = (MacMenuDelegate)item;
/*  84 */     _remove(this.ptr, macMenu == null ? 0L : macMenu.ptr, pos);
/*  85 */     return true;
/*     */   }
/*     */   private native void _setTitle(long paramLong, String paramString);
/*     */ 
/*     */   public boolean setTitle(String title) {
/*  90 */     _setTitle(this.ptr, title);
/*  91 */     return true;
/*     */   }
/*     */   private native void _setShortcut(long paramLong, char paramChar, int paramInt);
/*     */ 
/*     */   public boolean setShortcut(int shortcutKey, int shortcutModifiers) {
/*  96 */     _setShortcut(this.ptr, (char)shortcutKey, shortcutModifiers);
/*  97 */     return true;
/*     */   }
/*     */   private native void _setPixels(long paramLong, Pixels paramPixels);
/*     */ 
/*     */   public boolean setPixels(Pixels pixels) {
/* 102 */     _setPixels(this.ptr, pixels);
/* 103 */     return true;
/*     */   }
/*     */   private native void _setEnabled(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean setEnabled(boolean enabled) {
/* 108 */     _setEnabled(this.ptr, enabled);
/* 109 */     return true;
/*     */   }
/*     */   private native void _setChecked(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   public boolean setChecked(boolean checked) {
/* 114 */     _setChecked(this.ptr, checked);
/* 115 */     return true;
/*     */   }
/*     */   private native void _setCallback(long paramLong, MenuItem.Callback paramCallback);
/*     */ 
/*     */   public boolean setCallback(MenuItem.Callback callback) {
/* 120 */     _setCallback(this.ptr, callback);
/* 121 */     return true;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  25 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  28 */         Application.loadNativeLibrary();
/*  29 */         return null;
/*     */       }
/*     */     });
/*  32 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacMenuDelegate
 * JD-Core Version:    0.6.2
 */