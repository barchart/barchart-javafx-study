/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*     */ 
/*     */ public class MenuItem
/*     */ {
/*  16 */   public static final MenuItem Separator = null;
/*     */   private final MenuItemDelegate delegate;
/*     */   private String title;
/*     */   private Callback callback;
/*     */   private boolean enabled;
/*     */   private boolean checked;
/*     */   private int shortcutKey;
/*     */   private int shortcutModifiers;
/*     */ 
/*     */   protected MenuItem(String title)
/*     */   {
/*  27 */     this(title, null);
/*     */   }
/*     */ 
/*     */   protected MenuItem(String title, Callback callback) {
/*  31 */     this(title, callback, 0, 0);
/*     */   }
/*     */ 
/*     */   protected MenuItem(String title, Callback callback, int shortcutKey, int shortcutModifiers)
/*     */   {
/*  36 */     this(title, callback, shortcutKey, shortcutModifiers, null);
/*     */   }
/*     */ 
/*     */   protected MenuItem(String title, Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels)
/*     */   {
/*  41 */     this.title = title;
/*  42 */     this.callback = callback;
/*  43 */     this.shortcutKey = shortcutKey;
/*  44 */     this.shortcutModifiers = shortcutModifiers;
/*  45 */     this.enabled = true;
/*  46 */     this.checked = false;
/*  47 */     this.delegate = PlatformFactory.getPlatformFactory().createMenuItemDelegate(this);
/*  48 */     if (!this.delegate.createMenuItem(title, callback, shortcutKey, shortcutModifiers, pixels, this.enabled, this.checked))
/*     */     {
/*  50 */       throw new RuntimeException("MenuItem creation error.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getTitle() {
/*  55 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title) {
/*  59 */     if (this.delegate.setTitle(title))
/*  60 */       this.title = title;
/*     */   }
/*     */ 
/*     */   public Callback getCallback()
/*     */   {
/*  65 */     return this.callback;
/*     */   }
/*     */ 
/*     */   public void setCallback(Callback callback) {
/*  69 */     if (this.delegate.setCallback(callback))
/*  70 */       this.callback = callback;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/*  75 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled) {
/*  79 */     if (this.delegate.setEnabled(enabled))
/*  80 */       this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   public boolean isChecked()
/*     */   {
/*  85 */     return this.checked;
/*     */   }
/*     */ 
/*     */   public void setChecked(boolean checked) {
/*  89 */     if (this.delegate.setChecked(checked))
/*  90 */       this.checked = checked;
/*     */   }
/*     */ 
/*     */   public int getShortcutKey()
/*     */   {
/*  98 */     return this.shortcutKey;
/*     */   }
/*     */ 
/*     */   public int getShortcutModifiers()
/*     */   {
/* 105 */     return this.shortcutModifiers;
/*     */   }
/*     */ 
/*     */   public void setShortcut(int shortcutKey, int shortcutModifiers) {
/* 109 */     if (this.delegate.setShortcut(shortcutKey, shortcutModifiers)) {
/* 110 */       this.shortcutKey = shortcutKey;
/* 111 */       this.shortcutModifiers = shortcutModifiers;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean setPixels(Pixels pixels) {
/* 116 */     return this.delegate.setPixels(pixels);
/*     */   }
/*     */ 
/*     */   MenuItemDelegate getDelegate()
/*     */   {
/* 121 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   public static abstract interface Callback
/*     */   {
/*     */     public abstract void action();
/*     */ 
/*     */     public abstract void validate();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.MenuItem
 * JD-Core Version:    0.6.2
 */