/*     */ package com.sun.webpane.sg.theme;
/*     */ 
/*     */ import com.sun.webpane.platform.ContextMenu.ShowContext;
/*     */ import com.sun.webpane.platform.ContextMenuItem;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.control.CheckMenuItem;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.Separator;
/*     */ 
/*     */ public class ContextMenuImpl extends com.sun.webpane.platform.ContextMenu
/*     */ {
/*  25 */   private static final Logger log = Logger.getLogger(ContextMenuImpl.class.getName());
/*     */   ObservableList<ContextMenuItem> items;
/*     */ 
/*     */   public ContextMenuImpl()
/*     */   {
/*  27 */     this.items = FXCollections.observableArrayList();
/*     */   }
/*     */   public void show(final ContextMenu.ShowContext paramShowContext, int paramInt1, int paramInt2) {
/*  30 */     if (log.isLoggable(Level.FINE)) {
/*  31 */       log.log(Level.FINE, "show at [{0}, {1}]", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
/*     */     }
/*  33 */     javafx.scene.control.ContextMenu localContextMenu = new javafx.scene.control.ContextMenu();
/*     */ 
/*  35 */     localContextMenu.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  37 */         MenuItem localMenuItem = (MenuItem)paramAnonymousActionEvent.getTarget();
/*  38 */         ContextMenuImpl.log.log(Level.FINE, "onAction: item={0}", localMenuItem);
/*  39 */         paramShowContext.notifyItemSelected(((ContextMenuImpl.MenuItemPeer)localMenuItem).getItemPeer().getAction());
/*     */       }
/*     */     });
/*  43 */     localContextMenu.getItems().addAll(fillMenu());
/*  44 */     PopupMenuImpl.doShow(localContextMenu, paramShowContext.getPage(), paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void appendItem(ContextMenuItem paramContextMenuItem) {
/*  48 */     insertItem(paramContextMenuItem, this.items.size());
/*     */   }
/*     */ 
/*     */   public void insertItem(ContextMenuItem paramContextMenuItem, int paramInt) {
/*  52 */     if (log.isLoggable(Level.FINE)) {
/*  53 */       log.log(Level.FINE, "item={0}, index={1}", new Object[] { paramContextMenuItem, Integer.valueOf(paramInt) });
/*     */     }
/*  55 */     if (paramContextMenuItem == null) {
/*  56 */       return;
/*     */     }
/*  58 */     this.items.remove(paramContextMenuItem);
/*     */ 
/*  60 */     if (this.items.size() == 0)
/*  61 */       this.items.add(paramContextMenuItem);
/*     */     else
/*  63 */       this.items.add(paramInt, paramContextMenuItem);
/*     */   }
/*     */ 
/*     */   public int getItemCount()
/*     */   {
/*  68 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   MenuItem createMenuItem(ContextMenuItem paramContextMenuItem) {
/*  72 */     log.log(Level.FINE, "item={0}", paramContextMenuItem);
/*     */     Object localObject;
/*  74 */     if (paramContextMenuItem.getType() == 2) {
/*  75 */       localObject = new MenuImpl(paramContextMenuItem.getTitle());
/*  76 */       if (paramContextMenuItem.getSubmenu() != null) {
/*  77 */         ((MenuImpl)localObject).getItems().addAll(((ContextMenuImpl)paramContextMenuItem.getSubmenu()).fillMenu());
/*     */       }
/*  79 */       return localObject;
/*     */     }
/*  81 */     if (paramContextMenuItem.getType() == 0)
/*     */     {
/*  83 */       localObject = null;
/*  84 */       if (paramContextMenuItem.isChecked())
/*  85 */         localObject = new CheckMenuItemImpl(paramContextMenuItem);
/*     */       else {
/*  87 */         localObject = new MenuItemImpl(paramContextMenuItem);
/*     */       }
/*  89 */       ((MenuItem)localObject).setDisable(!paramContextMenuItem.isEnabled());
/*  90 */       return localObject;
/*     */     }
/*  92 */     if (paramContextMenuItem.getType() == 1) {
/*  93 */       return new SeparatorImpl(paramContextMenuItem);
/*     */     }
/*  95 */     throw new IllegalArgumentException("unexpected item type");
/*     */   }
/*     */ 
/*     */   ObservableList<MenuItem> fillMenu() {
/*  99 */     ObservableList localObservableList = FXCollections.observableArrayList();
/* 100 */     for (ContextMenuItem localContextMenuItem : this.items) {
/* 101 */       localObservableList.add(createMenuItem(localContextMenuItem));
/*     */     }
/* 103 */     return localObservableList;
/*     */   }
/*     */ 
/*     */   static class SeparatorImpl extends MenuItem
/*     */     implements ContextMenuImpl.MenuItemPeer
/*     */   {
/*     */     ContextMenuItem itemPeer;
/*     */ 
/*     */     SeparatorImpl(ContextMenuItem paramContextMenuItem)
/*     */     {
/* 129 */       this.itemPeer = paramContextMenuItem;
/* 130 */       setGraphic(new Separator());
/* 131 */       setDisable(true);
/*     */     }
/* 133 */     public ContextMenuItem getItemPeer() { return this.itemPeer; }
/*     */ 
/*     */   }
/*     */ 
/*     */   static class MenuImpl extends Menu
/*     */   {
/*     */     MenuImpl(String paramString)
/*     */     {
/* 123 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class CheckMenuItemImpl extends CheckMenuItem
/*     */     implements ContextMenuImpl.MenuItemPeer
/*     */   {
/*     */     ContextMenuItem itemPeer;
/*     */ 
/*     */     CheckMenuItemImpl(ContextMenuItem paramContextMenuItem)
/*     */     {
/* 118 */       this.itemPeer = paramContextMenuItem; } 
/* 119 */     public ContextMenuItem getItemPeer() { return this.itemPeer; }
/*     */ 
/*     */   }
/*     */ 
/*     */   static class MenuItemImpl extends MenuItem
/*     */     implements ContextMenuImpl.MenuItemPeer
/*     */   {
/*     */     ContextMenuItem itemPeer;
/*     */ 
/*     */     MenuItemImpl(ContextMenuItem paramContextMenuItem)
/*     */     {
/* 112 */       super(); this.itemPeer = paramContextMenuItem; } 
/* 113 */     public ContextMenuItem getItemPeer() { return this.itemPeer; }
/*     */ 
/*     */   }
/*     */ 
/*     */   static abstract interface MenuItemPeer
/*     */   {
/*     */     public abstract ContextMenuItem getItemPeer();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.theme.ContextMenuImpl
 * JD-Core Version:    0.6.2
 */