/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import com.sun.glass.ui.delegate.MenuDelegate;
/*     */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Menu
/*     */ {
/*     */   private final MenuDelegate delegate;
/*     */   private String title;
/*     */   private boolean enabled;
/*  38 */   private final List<Object> items = new ArrayList();
/*     */   private EventHandler eventHandler;
/*     */ 
/*     */   public EventHandler getEventHandler()
/*     */   {
/*  27 */     return this.eventHandler;
/*     */   }
/*     */ 
/*     */   public void setEventHandler(EventHandler eventHandler) {
/*  31 */     this.eventHandler = eventHandler;
/*     */   }
/*     */ 
/*     */   protected Menu(String title)
/*     */   {
/*  43 */     this(title, true);
/*     */   }
/*     */ 
/*     */   protected Menu(String title, boolean enabled) {
/*  47 */     this.title = title;
/*  48 */     this.enabled = enabled;
/*  49 */     this.delegate = PlatformFactory.getPlatformFactory().createMenuDelegate(this);
/*  50 */     if (!this.delegate.createMenu(title, enabled))
/*  51 */       throw new RuntimeException("Menu creation error.");
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/*  56 */     return this.title;
/*     */   }
/*     */ 
/*     */   public void setTitle(String title) {
/*  60 */     if (this.delegate.setTitle(title))
/*  61 */       this.title = title;
/*     */   }
/*     */ 
/*     */   public boolean isEnabled()
/*     */   {
/*  66 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled) {
/*  70 */     if (this.delegate.setEnabled(enabled))
/*  71 */       this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   public boolean setPixels(Pixels pixels)
/*     */   {
/*  76 */     return this.delegate.setPixels(pixels);
/*     */   }
/*     */ 
/*     */   public List<Object> getItems()
/*     */   {
/*  84 */     return Collections.unmodifiableList(this.items);
/*     */   }
/*     */ 
/*     */   public void add(Menu menu) {
/*  88 */     insert(menu, this.items.size());
/*     */   }
/*     */ 
/*     */   public void add(MenuItem item) {
/*  92 */     insert(item, this.items.size());
/*     */   }
/*     */ 
/*     */   public void insert(Menu menu, int pos) throws IndexOutOfBoundsException {
/*  96 */     if (menu == null) {
/*  97 */       throw new IllegalArgumentException();
/*     */     }
/*  99 */     synchronized (this.items) {
/* 100 */       if ((pos < 0) || (pos > this.items.size())) {
/* 101 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 103 */       MenuDelegate menuDelegate = menu.getDelegate();
/* 104 */       if (this.delegate.insert(menuDelegate, pos))
/* 105 */         this.items.add(pos, menu);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insert(MenuItem item, int pos) throws IndexOutOfBoundsException
/*     */   {
/* 111 */     synchronized (this.items) {
/* 112 */       if ((pos < 0) || (pos > this.items.size())) {
/* 113 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 115 */       MenuItemDelegate itemDelegate = item != null ? item.getDelegate() : null;
/* 116 */       if (this.delegate.insert(itemDelegate, pos))
/* 117 */         this.items.add(pos, item);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void remove(int pos) throws IndexOutOfBoundsException
/*     */   {
/* 123 */     synchronized (this.items) {
/* 124 */       Object item = this.items.get(pos);
/* 125 */       boolean success = false;
/* 126 */       if (item == MenuItem.Separator)
/* 127 */         success = this.delegate.remove((MenuItemDelegate)null, pos);
/* 128 */       else if ((item instanceof MenuItem))
/* 129 */         success = this.delegate.remove(((MenuItem)item).getDelegate(), pos);
/*     */       else {
/* 131 */         success = this.delegate.remove(((Menu)item).getDelegate(), pos);
/*     */       }
/* 133 */       if (success)
/* 134 */         this.items.remove(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   MenuDelegate getDelegate()
/*     */   {
/* 144 */     return this.delegate;
/*     */   }
/*     */ 
/*     */   protected void notifyMenuOpening()
/*     */   {
/* 151 */     if (this.eventHandler != null)
/* 152 */       this.eventHandler.handleMenuOpening(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   protected void notifyMenuClosed()
/*     */   {
/* 157 */     if (this.eventHandler != null)
/* 158 */       this.eventHandler.handleMenuClosed(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   public static class EventHandler
/*     */   {
/*     */     public void handleMenuOpening(Menu menu, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleMenuClosed(Menu menu, long time)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Menu
 * JD-Core Version:    0.6.2
 */