/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class ContextMenuSkin
/*     */   implements Skin<ContextMenu>
/*     */ {
/*     */   private ContextMenu popupMenu;
/*     */   private final StackPane root;
/*     */ 
/*     */   public ContextMenuSkin(final ContextMenu paramContextMenu)
/*     */   {
/*  57 */     this.popupMenu = paramContextMenu;
/*     */ 
/*  60 */     paramContextMenu.addEventHandler(Menu.ON_SHOWN, new EventHandler() {
/*     */       public void handle(Event paramAnonymousEvent) {
/*  62 */         Node localNode = paramContextMenu.getSkin().getNode();
/*  63 */         if (localNode != null) localNode.requestFocus();
/*     */       }
/*     */     });
/*  67 */     if ((PlatformUtil.isEmbedded()) && (paramContextMenu.getStyleClass().contains("text-input-context-menu")))
/*     */     {
/*  70 */       this.root = new EmbeddedTextContextMenuContent(paramContextMenu);
/*     */     }
/*  72 */     else this.root = new ContextMenuContent(paramContextMenu);
/*     */ 
/*  74 */     this.root.idProperty().bind(paramContextMenu.idProperty());
/*  75 */     this.root.styleProperty().bind(paramContextMenu.styleProperty());
/*  76 */     this.root.getStyleClass().addAll(paramContextMenu.getStyleClass());
/*     */   }
/*     */ 
/*     */   public ContextMenu getSkinnable()
/*     */   {
/* 107 */     return this.popupMenu;
/*     */   }
/*     */ 
/*     */   public Node getNode() {
/* 111 */     return this.root;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ContextMenuSkin
 * JD-Core Version:    0.6.2
 */