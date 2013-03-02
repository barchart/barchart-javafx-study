/*    */ package com.sun.glass.ui;
/*    */ 
/*    */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MenuBar
/*    */ {
/*    */   private final MenuBarDelegate delegate;
/* 18 */   private final List<Menu> menus = new ArrayList();
/*    */ 
/*    */   long getNativeMenu()
/*    */   {
/* 15 */     return this.delegate.getNativeMenu();
/*    */   }
/*    */ 
/*    */   protected MenuBar()
/*    */   {
/* 21 */     this.delegate = PlatformFactory.getPlatformFactory().createMenuBarDelegate(this);
/* 22 */     if (!this.delegate.createMenuBar())
/* 23 */       throw new RuntimeException("MenuBar creation error.");
/*    */   }
/*    */ 
/*    */   public void add(Menu menu)
/*    */   {
/* 28 */     insert(menu, this.menus.size());
/*    */   }
/*    */ 
/*    */   public void insert(Menu menu, int pos) {
/* 32 */     synchronized (this.menus) {
/* 33 */       if (this.delegate.insert(menu.getDelegate(), pos))
/* 34 */         this.menus.add(pos, menu);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void remove(int pos)
/*    */   {
/* 40 */     synchronized (this.menus) {
/* 41 */       Menu menu = (Menu)this.menus.get(pos);
/* 42 */       if (this.delegate.remove(menu.getDelegate(), pos))
/* 43 */         this.menus.remove(pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void remove(Menu menu)
/*    */   {
/* 49 */     synchronized (this.menus) {
/* 50 */       int pos = this.menus.indexOf(menu);
/* 51 */       if ((pos >= 0) && 
/* 52 */         (this.delegate.remove(menu.getDelegate(), pos)))
/* 53 */         this.menus.remove(pos);
/*    */     }
/*    */   }
/*    */ 
/*    */   public List<Menu> getMenus()
/*    */   {
/* 62 */     return Collections.unmodifiableList(this.menus);
/*    */   }
/*    */ 
/*    */   MenuBarDelegate getDelegate()
/*    */   {
/* 67 */     return this.delegate;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.MenuBar
 * JD-Core Version:    0.6.2
 */