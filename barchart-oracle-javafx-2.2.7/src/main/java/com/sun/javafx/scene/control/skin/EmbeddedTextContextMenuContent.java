/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.TextArea;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class EmbeddedTextContextMenuContent extends StackPane
/*     */ {
/*     */   private ContextMenu contextMenu;
/*     */   private StackPane pointer;
/*     */   private HBox menuBox;
/*     */ 
/*     */   public EmbeddedTextContextMenuContent(ContextMenu paramContextMenu)
/*     */   {
/*  52 */     this.contextMenu = paramContextMenu;
/*  53 */     this.menuBox = new HBox();
/*  54 */     this.pointer = new StackPane();
/*  55 */     this.pointer.getStyleClass().add("pointer");
/*     */ 
/*  57 */     updateMenuItemContainer();
/*  58 */     getChildren().addAll(new Node[] { this.pointer, this.menuBox });
/*     */ 
/*  60 */     this.contextMenu.ownerNodeProperty().addListener(new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/*     */         Object localObject;
/*  62 */         if ((EmbeddedTextContextMenuContent.this.contextMenu.getOwnerNode() instanceof TextArea)) {
/*  63 */           localObject = (TextAreaSkin)((TextArea)EmbeddedTextContextMenuContent.this.contextMenu.getOwnerNode()).getSkin();
/*  64 */           ((TextAreaSkin)localObject).getProperties().addListener(new InvalidationListener() {
/*     */             public void invalidated(Observable paramAnonymous2Observable) {
/*  66 */               EmbeddedTextContextMenuContent.this.requestLayout();
/*     */             } } );
/*     */         }
/*  69 */         else if ((EmbeddedTextContextMenuContent.this.contextMenu.getOwnerNode() instanceof TextField)) {
/*  70 */           localObject = (TextFieldSkin)((TextField)EmbeddedTextContextMenuContent.this.contextMenu.getOwnerNode()).getSkin();
/*  71 */           ((TextFieldSkin)localObject).getProperties().addListener(new InvalidationListener() {
/*     */             public void invalidated(Observable paramAnonymous2Observable) {
/*  73 */               EmbeddedTextContextMenuContent.this.requestLayout();
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     });
/*  80 */     this.contextMenu.getItems().addListener(new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends MenuItem> paramAnonymousChange) {
/*  83 */         EmbeddedTextContextMenuContent.this.updateMenuItemContainer();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void updateMenuItemContainer() {
/*  89 */     this.menuBox.getChildren().clear();
/*  90 */     for (MenuItem localMenuItem : this.contextMenu.getItems()) {
/*  91 */       MenuItemContainer localMenuItemContainer = new MenuItemContainer(localMenuItem);
/*  92 */       localMenuItemContainer.visibleProperty().bind(localMenuItem.visibleProperty());
/*  93 */       this.menuBox.getChildren().add(localMenuItemContainer);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void hideAllMenus(MenuItem paramMenuItem) {
/*  98 */     this.contextMenu.hide();
/*     */     Menu localMenu;
/* 101 */     while ((localMenu = paramMenuItem.getParentMenu()) != null) {
/* 102 */       localMenu.hide();
/* 103 */       paramMenuItem = localMenu;
/*     */     }
/* 105 */     if ((localMenu == null) && (paramMenuItem.getParentPopup() != null))
/* 106 */       paramMenuItem.getParentPopup().hide();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 111 */     double d1 = snapSpace(getInsets().getTop());
/* 112 */     double d2 = snapSpace(getInsets().getBottom());
/* 113 */     double d3 = snapSize(this.pointer.prefHeight(paramDouble));
/* 114 */     double d4 = snapSize(this.menuBox.prefHeight(paramDouble));
/*     */ 
/* 116 */     return d1 + d3 + d4 + d2;
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 120 */     double d1 = snapSpace(getInsets().getLeft());
/* 121 */     double d2 = snapSpace(getInsets().getRight());
/* 122 */     double d3 = snapSize(this.menuBox.prefWidth(paramDouble));
/*     */ 
/* 124 */     return d1 + d3 + d2;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 128 */     double d1 = snapSpace(getInsets().getLeft());
/* 129 */     double d2 = snapSpace(getInsets().getRight());
/* 130 */     double d3 = snapSpace(getInsets().getTop());
/* 131 */     double d4 = snapSize(getWidth() - (d1 + d2));
/* 132 */     double d5 = snapSize(Utils.boundedSize(this.pointer.prefWidth(-1.0D), this.pointer.minWidth(-1.0D), this.pointer.maxWidth(-1.0D)));
/* 133 */     double d6 = snapSize(Utils.boundedSize(this.pointer.prefWidth(-1.0D), this.pointer.minWidth(-1.0D), this.pointer.maxWidth(-1.0D)));
/* 134 */     double d7 = snapSize(Utils.boundedSize(this.menuBox.prefWidth(-1.0D), this.menuBox.minWidth(-1.0D), this.menuBox.maxWidth(-1.0D)));
/* 135 */     double d8 = snapSize(Utils.boundedSize(this.menuBox.prefWidth(-1.0D), this.menuBox.minWidth(-1.0D), this.menuBox.maxWidth(-1.0D)));
/* 136 */     double d9 = 0.0D;
/* 137 */     double d10 = 0.0D;
/* 138 */     double d11 = 0.0D;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 141 */     if ((this.contextMenu.getOwnerNode() instanceof TextArea)) {
/* 142 */       localObject1 = (TextArea)this.contextMenu.getOwnerNode();
/* 143 */       localObject2 = (TextAreaSkin)((TextArea)localObject1).getSkin();
/* 144 */       d9 = Double.valueOf(((TextAreaSkin)localObject2).getProperties().get("CONTEXT_MENU_SCENE_X").toString()).doubleValue();
/* 145 */       d10 = Double.valueOf(((TextAreaSkin)localObject2).getProperties().get("CONTEXT_MENU_SCREEN_X").toString()).doubleValue();
/* 146 */       ((TextAreaSkin)localObject2).getProperties().clear();
/* 147 */     } else if ((this.contextMenu.getOwnerNode() instanceof TextField)) {
/* 148 */       localObject1 = (TextField)this.contextMenu.getOwnerNode();
/* 149 */       localObject2 = (TextFieldSkin)((TextField)localObject1).getSkin();
/* 150 */       d9 = Double.valueOf(((TextFieldSkin)localObject2).getProperties().get("CONTEXT_MENU_SCENE_X").toString()).doubleValue();
/* 151 */       d10 = Double.valueOf(((TextFieldSkin)localObject2).getProperties().get("CONTEXT_MENU_SCREEN_X").toString()).doubleValue();
/* 152 */       ((TextFieldSkin)localObject2).getProperties().clear();
/*     */     }
/* 154 */     if (d9 == 0.0D)
/* 155 */       d11 = d4 / 2.0D;
/*     */     else {
/* 157 */       d11 = d10 - d9 - this.contextMenu.getX() + d9;
/*     */     }
/*     */ 
/* 160 */     this.pointer.resize(d5, d6);
/* 161 */     positionInArea(this.pointer, d11, d3, d5, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/* 162 */     this.menuBox.resize(d7, d8);
/* 163 */     positionInArea(this.menuBox, d1, d3 + d6, d7, d8, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ 
/*     */   class MenuItemContainer extends Button
/*     */   {
/*     */     private MenuItem item;
/*     */ 
/*     */     public MenuItemContainer(MenuItem arg2)
/*     */     {
/*     */       Object localObject;
/* 170 */       getStyleClass().addAll(localObject.getStyleClass());
/* 171 */       setId(localObject.getId());
/* 172 */       this.item = localObject;
/* 173 */       setText(localObject.getText());
/* 174 */       setStyle(localObject.getStyle());
/*     */ 
/* 177 */       textProperty().bind(localObject.textProperty());
/*     */     }
/*     */ 
/*     */     public MenuItem getItem() {
/* 181 */       return this.item;
/*     */     }
/*     */ 
/*     */     public void fire() {
/* 185 */       Event.fireEvent(this.item, new ActionEvent());
/* 186 */       if (!Boolean.TRUE.equals((Boolean)this.item.getProperties().get("refreshMenu")))
/*     */       {
/* 189 */         EmbeddedTextContextMenuContent.this.hideAllMenus(this.item);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.EmbeddedTextContextMenuContent
 * JD-Core Version:    0.6.2
 */