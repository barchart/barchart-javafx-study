/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import com.sun.glass.ui.MenuItem;
/*     */ import com.sun.glass.ui.MenuItem.Callback;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.delegate.MenuItemDelegate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class WinMenuItemDelegate
/*     */   implements MenuItemDelegate
/*     */ {
/*     */   private final MenuItem owner;
/*  19 */   private WinMenuImpl parent = null;
/*     */ 
/*  21 */   private int cmdID = -1;
/*     */ 
/*     */   public WinMenuItemDelegate(MenuItem item) {
/*  24 */     this.owner = item;
/*     */   }
/*     */ 
/*     */   public MenuItem getOwner() {
/*  28 */     return this.owner;
/*     */   }
/*     */ 
/*     */   public boolean createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels, boolean enabled, boolean checked)
/*     */   {
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setTitle(String title)
/*     */   {
/*  41 */     if (this.parent != null) {
/*  42 */       title = getTitle(title, getOwner().getShortcutKey(), getOwner().getShortcutModifiers());
/*     */ 
/*  44 */       return this.parent.setItemTitle(this, title);
/*     */     }
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setCallback(MenuItem.Callback callback)
/*     */   {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setShortcut(int shortcutKey, int shortcutModifiers) {
/*  55 */     if (this.parent != null) {
/*  56 */       String title = getTitle(getOwner().getTitle(), shortcutKey, shortcutModifiers);
/*     */ 
/*  58 */       return this.parent.setItemTitle(this, title);
/*     */     }
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setPixels(Pixels pixels)
/*     */   {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setEnabled(boolean enabled) {
/*  69 */     if (this.parent != null) {
/*  70 */       return this.parent.enableItem(this, enabled);
/*     */     }
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setChecked(boolean checked) {
/*  76 */     if (this.parent != null) {
/*  77 */       return this.parent.checkItem(this, checked);
/*     */     }
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   private String getTitle(String title, int key, int modifiers)
/*     */   {
/*  87 */     if (key == 0) {
/*  88 */       return title;
/*     */     }
/*  90 */     return title;
/*     */   }
/*     */ 
/*     */   WinMenuImpl getParent() {
/*  94 */     return this.parent;
/*     */   }
/*     */ 
/*     */   void setParent(WinMenuImpl newParent)
/*     */   {
/*  99 */     if (this.parent != null) {
/* 100 */       CommandIDManager.freeID(this.cmdID);
/* 101 */       this.cmdID = -1;
/*     */     }
/* 103 */     if (newParent != null) {
/* 104 */       this.cmdID = CommandIDManager.getID(this);
/*     */     }
/* 106 */     this.parent = newParent;
/*     */   }
/*     */ 
/*     */   int getCmdID() {
/* 110 */     return this.cmdID;
/*     */   }
/*     */ 
/*     */   static class CommandIDManager
/*     */   {
/*     */     private static final int FIRST_ID = 1;
/*     */     private static final int LAST_ID = 65535;
/* 117 */     private static List<Integer> freeList = new ArrayList();
/*     */ 
/* 119 */     private static final Map<Integer, WinMenuItemDelegate> map = new HashMap();
/*     */ 
/* 121 */     private static int nextID = 1;
/*     */ 
/*     */     public static synchronized int getID(WinMenuItemDelegate menu)
/*     */     {
/*     */       Integer id;
/* 125 */       if (freeList.isEmpty()) {
/* 126 */         if (nextID > 65535)
/*     */         {
/* 128 */           nextID = 1;
/*     */         }
/* 130 */         Integer id = Integer.valueOf(nextID);
/* 131 */         nextID += 1;
/*     */       }
/*     */       else {
/* 134 */         id = (Integer)freeList.remove(freeList.size() - 1);
/*     */       }
/* 136 */       map.put(id, menu);
/* 137 */       return id.intValue();
/*     */     }
/*     */ 
/*     */     public static synchronized void freeID(int cmdID) {
/* 141 */       Integer id = Integer.valueOf(cmdID);
/* 142 */       if (map.remove(id) != null)
/* 143 */         freeList.add(id);
/*     */     }
/*     */ 
/*     */     public static WinMenuItemDelegate getHandler(int cmdID)
/*     */     {
/* 148 */       return (WinMenuItemDelegate)map.get(Integer.valueOf(cmdID));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinMenuItemDelegate
 * JD-Core Version:    0.6.2
 */