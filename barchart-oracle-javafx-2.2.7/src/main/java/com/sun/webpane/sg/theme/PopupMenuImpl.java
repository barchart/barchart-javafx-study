/*     */ package com.sun.webpane.sg.theme;
/*     */ 
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ import com.sun.webpane.platform.PopupMenu;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import com.sun.webpane.platform.graphics.WCFont;
/*     */ import com.sun.webpane.platform.graphics.WCPoint;
/*     */ import com.sun.webpane.sg.WebPageClientImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.web.WebView;
/*     */ import javafx.stage.WindowEvent;
/*     */ 
/*     */ public class PopupMenuImpl extends PopupMenu
/*     */ {
/*  24 */   private static final Logger log = Logger.getLogger(PopupMenuImpl.class.getName());
/*     */   ContextMenu popupMenu;
/*     */ 
/*     */   public PopupMenuImpl()
/*     */   {
/*  29 */     this.popupMenu = new ContextMenu();
/*     */ 
/*  31 */     this.popupMenu.setOnHidden(new EventHandler() {
/*     */       public void handle(WindowEvent paramAnonymousWindowEvent) {
/*  33 */         PopupMenuImpl.log.finer("onHidden");
/*     */ 
/*  39 */         Invoker.getInvoker().postOnEventThread(new Runnable() {
/*     */           public void run() {
/*  41 */             PopupMenuImpl.log.finer("onHidden: notifying");
/*  42 */             PopupMenuImpl.this.notifyPopupClosed();
/*     */           }
/*     */         });
/*     */       }
/*     */     });
/*  47 */     this.popupMenu.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  49 */         MenuItem localMenuItem = (MenuItem)paramAnonymousActionEvent.getTarget();
/*  50 */         PopupMenuImpl.log.log(Level.FINE, "onAction: item={0}", localMenuItem);
/*  51 */         PopupMenuImpl.this.notifySelectionCommited(PopupMenuImpl.this.popupMenu.getItems().indexOf(localMenuItem));
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void show(WebPage paramWebPage, int paramInt1, int paramInt2, int paramInt3) {
/*  57 */     if (log.isLoggable(Level.FINE)) {
/*  58 */       log.log(Level.FINE, "show at [{0}, {1}], width={2}", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
/*     */     }
/*     */ 
/*  61 */     this.popupMenu.setPrefWidth(paramInt3);
/*  62 */     this.popupMenu.setPrefHeight(this.popupMenu.getHeight());
/*  63 */     doShow(this.popupMenu, paramWebPage, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void hide() {
/*  67 */     log.fine("hiding");
/*  68 */     this.popupMenu.hide();
/*     */   }
/*     */ 
/*     */   public void appendItem(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2, WCFont paramWCFont)
/*     */   {
/*  74 */     if (log.isLoggable(Level.FINEST))
/*  75 */       log.log(Level.FINEST, "itemText={0}, isLabel={1}, isSeparator={2}, isEnabled={3}, bgColor={4}, fgColor={5}, font={6}", new Object[] { paramString, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2), Boolean.valueOf(paramBoolean3), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramWCFont });
/*     */     Object localObject;
/*  81 */     if (paramBoolean2) {
/*  82 */       localObject = new ContextMenuImpl.SeparatorImpl(null);
/*     */     } else {
/*  84 */       localObject = new MenuItem(paramString);
/*  85 */       ((MenuItem)localObject).setDisable(!paramBoolean3);
/*     */     }
/*     */ 
/*  88 */     this.popupMenu.getItems().add(localObject);
/*     */   }
/*     */ 
/*     */   public void setSelectedItem(int paramInt) {
/*  92 */     log.log(Level.FINEST, "index={0}", Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   static void doShow(ContextMenu paramContextMenu, WebPage paramWebPage, int paramInt1, int paramInt2)
/*     */   {
/*  98 */     WebPageClientImpl localWebPageClientImpl = (WebPageClientImpl)paramWebPage.getPageClient();
/*  99 */     assert (localWebPageClientImpl != null);
/* 100 */     WCPoint localWCPoint = localWebPageClientImpl.windowToScreen(new WCPoint(paramInt1, paramInt2));
/* 101 */     paramContextMenu.show(localWebPageClientImpl.getContainer().getScene().getWindow(), localWCPoint.getX(), localWCPoint.getY());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.theme.PopupMenuImpl
 * JD-Core Version:    0.6.2
 */