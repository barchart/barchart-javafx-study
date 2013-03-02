/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.Utils;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.css.StyleableProperty;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javafx.animation.Animation.Status;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableBooleanValue;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.geometry.Side;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.control.CheckMenuItem;
/*      */ import javafx.scene.control.ContextMenu;
/*      */ import javafx.scene.control.CustomMenuItem;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.Menu;
/*      */ import javafx.scene.control.MenuItem;
/*      */ import javafx.scene.control.RadioMenuItem;
/*      */ import javafx.scene.control.SeparatorMenuItem;
/*      */ import javafx.scene.control.Skin;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.ScrollEvent;
/*      */ import javafx.scene.input.ScrollEvent.VerticalTextScrollUnits;
/*      */ import javafx.scene.layout.Pane;
/*      */ import javafx.scene.layout.Region;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.layout.VBox;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.stage.Screen;
/*      */ import javafx.stage.Window;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public class ContextMenuContent extends StackPane
/*      */ {
/*      */   private ContextMenu contextMenu;
/*   91 */   private double maxGraphicWidth = 0.0D;
/*   92 */   private double maxRightWidth = 0.0D;
/*   93 */   private double maxLabelWidth = 0.0D;
/*   94 */   private double maxRowHeight = 0.0D;
/*   95 */   private double maxLeftWidth = 0.0D;
/*      */   private Rectangle clipRect;
/*      */   MenuBox itemsContainer;
/*      */   private ArrowMenuItem upArrow;
/*      */   private ArrowMenuItem downArrow;
/*  106 */   private int currentFocusedIndex = -1;
/*      */ 
/*  362 */   private boolean isFirstShow = true;
/*      */   private double ty;
/*      */   private Menu openSubmenu;
/*      */   private ContextMenu submenu;
/*      */   private Region selectedBackground;
/* 1334 */   private static final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*      */ 
/* 1336 */   private static final long DISABLED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("disabled");
/*      */ 
/* 1338 */   private static final long CHECKED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("checked");
/*      */ 
/*      */   public ContextMenuContent(ContextMenu paramContextMenu)
/*      */   {
/*  111 */     this.contextMenu = paramContextMenu;
/*  112 */     this.clipRect = new Rectangle();
/*  113 */     this.clipRect.setSmooth(false);
/*  114 */     this.itemsContainer = new MenuBox();
/*      */ 
/*  116 */     this.itemsContainer.setClip(this.clipRect);
/*      */ 
/*  118 */     this.upArrow = new ArrowMenuItem(this);
/*  119 */     this.upArrow.setUp(true);
/*  120 */     this.upArrow.setFocusTraversable(false);
/*      */ 
/*  122 */     this.downArrow = new ArrowMenuItem(this);
/*  123 */     this.downArrow.setUp(false);
/*  124 */     this.downArrow.setFocusTraversable(false);
/*  125 */     getChildren().add(this.itemsContainer);
/*  126 */     getChildren().add(this.upArrow);
/*  127 */     getChildren().add(this.downArrow);
/*  128 */     updateVisualItems();
/*  129 */     initialize();
/*  130 */     setUpBinds();
/*      */   }
/*      */ 
/*      */   public VBox getItemsContainer()
/*      */   {
/*  135 */     return this.itemsContainer;
/*      */   }
/*      */ 
/*      */   int getCurrentFocusIndex() {
/*  139 */     return this.currentFocusedIndex;
/*      */   }
/*      */ 
/*      */   void setCurrentFocusedIndex(int paramInt) {
/*  143 */     if (paramInt < this.itemsContainer.getChildren().size())
/*  144 */       this.currentFocusedIndex = paramInt;
/*      */   }
/*      */ 
/*      */   private void computeVisualMetrics()
/*      */   {
/*  149 */     this.maxRightWidth = 0.0D;
/*  150 */     this.maxLabelWidth = 0.0D;
/*  151 */     this.maxRowHeight = 0.0D;
/*  152 */     this.maxGraphicWidth = 0.0D;
/*  153 */     this.maxLeftWidth = 0.0D;
/*      */ 
/*  155 */     for (int i = 0; i < this.itemsContainer.getChildren().size(); i++) {
/*  156 */       Node localNode1 = (Node)this.itemsContainer.getChildren().get(i);
/*  157 */       if ((localNode1 instanceof MenuItemContainer)) {
/*  158 */         MenuItemContainer localMenuItemContainer = (MenuItemContainer)this.itemsContainer.getChildren().get(i);
/*      */ 
/*  160 */         if (localMenuItemContainer.isVisible())
/*      */         {
/*  162 */           double d = -1.0D;
/*  163 */           Node localNode2 = localMenuItemContainer.left;
/*  164 */           if (localNode2 != null) {
/*  165 */             if (localNode2.getContentBias() == Orientation.VERTICAL)
/*  166 */               d = snapSize(localNode2.prefHeight(-1.0D));
/*  167 */             else d = -1.0D;
/*  168 */             this.maxLeftWidth = Math.max(this.maxLeftWidth, snapSize(localNode2.prefWidth(d)));
/*  169 */             this.maxRowHeight = Math.max(this.maxRowHeight, localNode2.prefHeight(-1.0D));
/*      */           }
/*      */ 
/*  172 */           localNode2 = localMenuItemContainer.graphic;
/*  173 */           if (localNode2 != null) {
/*  174 */             if (localNode2.getContentBias() == Orientation.VERTICAL)
/*  175 */               d = snapSize(localNode2.prefHeight(-1.0D));
/*  176 */             else d = -1.0D;
/*  177 */             this.maxGraphicWidth = Math.max(this.maxGraphicWidth, snapSize(localNode2.prefWidth(d)));
/*  178 */             this.maxRowHeight = Math.max(this.maxRowHeight, localNode2.prefHeight(-1.0D));
/*      */           }
/*      */ 
/*  181 */           localNode2 = localMenuItemContainer.label;
/*  182 */           if (localNode2 != null) {
/*  183 */             if (localNode2.getContentBias() == Orientation.VERTICAL)
/*  184 */               d = snapSize(localNode2.prefHeight(-1.0D));
/*  185 */             else d = -1.0D;
/*  186 */             this.maxLabelWidth = Math.max(this.maxLabelWidth, snapSize(localNode2.prefWidth(d)));
/*  187 */             this.maxRowHeight = Math.max(this.maxRowHeight, localNode2.prefHeight(-1.0D));
/*      */           }
/*      */ 
/*  190 */           localNode2 = localMenuItemContainer.right;
/*  191 */           if (localNode2 != null) {
/*  192 */             if (localNode2.getContentBias() == Orientation.VERTICAL)
/*  193 */               d = snapSize(localNode2.prefHeight(-1.0D));
/*  194 */             else d = -1.0D;
/*  195 */             this.maxRightWidth = Math.max(this.maxRightWidth, snapSize(localNode2.prefWidth(d)));
/*  196 */             this.maxRowHeight = Math.max(this.maxRowHeight, localNode2.prefHeight(-1.0D));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*  203 */   private void updateVisualItems() { this.itemsContainer.getChildren().clear();
/*  204 */     for (int i = 0; i < getItems().size(); i++) {
/*  205 */       MenuItem localMenuItem2 = (MenuItem)getItems().get(i);
/*  206 */       if ((!(localMenuItem2 instanceof CustomMenuItem)) || (((CustomMenuItem)localMenuItem2).getContent() != null)) {
/*  207 */         MenuItemContainer localMenuItemContainer = new MenuItemContainer(localMenuItem2);
/*  208 */         localMenuItemContainer.visibleProperty().bind(localMenuItem2.visibleProperty());
/*  209 */         this.itemsContainer.getChildren().add(localMenuItemContainer);
/*  210 */         if ((localMenuItem2 instanceof SeparatorMenuItem))
/*      */         {
/*  216 */           Node localNode = ((CustomMenuItem)localMenuItem2).getContent();
/*  217 */           this.itemsContainer.getChildren().remove(localMenuItemContainer);
/*  218 */           this.itemsContainer.getChildren().add(localNode);
/*      */ 
/*  222 */           localNode.getProperties().put(MenuItem.class, localMenuItem2);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  227 */     if (getItems().size() > 0) {
/*  228 */       MenuItem localMenuItem1 = (MenuItem)getItems().get(0);
/*  229 */       getProperties().put(Menu.class, localMenuItem1.getParentMenu());
/*      */     } }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/*  234 */     if (this.itemsContainer.getChildren().size() == 0) return;
/*  235 */     double d1 = snapSpace(getInsets().getLeft());
/*  236 */     double d2 = snapSpace(getInsets().getTop());
/*  237 */     double d3 = snapSize(getWidth()) - (snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight()));
/*  238 */     double d4 = snapSize(getHeight()) - (snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom()));
/*  239 */     double d5 = snapSize(getContentHeight());
/*      */ 
/*  241 */     this.itemsContainer.resize(d3, d5);
/*  242 */     this.itemsContainer.relocate(snapSpace(getInsets().getLeft()), d2);
/*      */ 
/*  244 */     if ((this.isFirstShow) && (this.ty == 0.0D)) {
/*  245 */       this.upArrow.setVisible(false);
/*  246 */       this.isFirstShow = false;
/*      */     } else {
/*  248 */       this.upArrow.setVisible((this.ty < d2) && (this.ty < 0.0D));
/*      */     }
/*  250 */     this.downArrow.setVisible(this.ty + d5 > d2 + d4);
/*      */ 
/*  252 */     this.clipRect.setX(0.0D);
/*  253 */     this.clipRect.setY(0.0D);
/*  254 */     this.clipRect.setWidth(d3);
/*  255 */     this.clipRect.setHeight(d4);
/*      */     double d6;
/*  257 */     if (this.upArrow.isVisible()) {
/*  258 */       d6 = snapSize(this.upArrow.prefHeight(-1.0D));
/*  259 */       this.clipRect.setHeight(snapSize(this.clipRect.getHeight() - d6));
/*  260 */       this.clipRect.setY(snapSize(this.clipRect.getY()) + d6);
/*  261 */       this.upArrow.resize(snapSize(this.upArrow.prefWidth(-1.0D)), d6);
/*  262 */       positionInArea(this.upArrow, d1, d2, d3, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*      */ 
/*  266 */     if (this.downArrow.isVisible()) {
/*  267 */       d6 = snapSize(this.downArrow.prefHeight(-1.0D));
/*  268 */       this.clipRect.setHeight(snapSize(this.clipRect.getHeight()) - d6);
/*  269 */       this.downArrow.resize(snapSize(this.downArrow.prefWidth(-1.0D)), d6);
/*  270 */       positionInArea(this.downArrow, d1, d2 + d4 - d6, d3, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  276 */     computeVisualMetrics();
/*  277 */     double d = 0.0D;
/*  278 */     if (this.itemsContainer.getChildren().size() == 0) return 0.0D;
/*  279 */     for (Node localNode : this.itemsContainer.getChildren()) {
/*  280 */       if (localNode.isVisible())
/*  281 */         d = Math.max(d, snapSize(localNode.prefWidth(-1.0D)));
/*      */     }
/*  283 */     return snapSize(getInsets().getLeft()) + snapSize(d) + snapSize(getInsets().getRight());
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble) {
/*  287 */     if (this.itemsContainer.getChildren().size() == 0) return 0.0D;
/*  288 */     double d1 = getScreenHeight();
/*  289 */     double d2 = getContentHeight();
/*  290 */     double d3 = snapSpace(getInsets().getTop()) + snapSize(d2) + snapSpace(getInsets().getBottom());
/*      */ 
/*  293 */     double d4 = d1 <= 0.0D ? d3 : Math.min(d3, d1);
/*  294 */     return d4;
/*      */   }
/*      */ 
/*      */   protected double computeMinHeight(double paramDouble) {
/*  298 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   protected double computeMaxHeight(double paramDouble) {
/*  302 */     return getScreenHeight();
/*      */   }
/*      */ 
/*      */   private double getScreenHeight() {
/*  306 */     if ((this.contextMenu == null) || (this.contextMenu.getOwnerWindow() == null) || (this.contextMenu.getOwnerWindow().getScene() == null))
/*      */     {
/*  308 */       return -1.0D;
/*      */     }
/*  310 */     return snapSize(Utils.getScreen(this.contextMenu.getOwnerWindow().getScene().getRoot()).getVisualBounds().getHeight());
/*      */   }
/*      */ 
/*      */   private double getContentHeight()
/*      */   {
/*  316 */     double d = 0.0D;
/*  317 */     for (Node localNode : this.itemsContainer.getChildren()) {
/*  318 */       if (localNode.isVisible()) {
/*  319 */         d += snapSize(localNode.prefHeight(-1.0D));
/*      */       }
/*      */     }
/*  322 */     return d;
/*      */   }
/*      */ 
/*      */   protected ObservableList<MenuItem> getItems()
/*      */   {
/*  346 */     return this.contextMenu.getItems();
/*      */   }
/*      */ 
/*      */   private int findFocusedIndex()
/*      */   {
/*  353 */     for (int i = 0; i < this.itemsContainer.getChildren().size(); i++) {
/*  354 */       Node localNode = (Node)this.itemsContainer.getChildren().get(i);
/*  355 */       if (localNode.isFocused()) {
/*  356 */         return i;
/*      */       }
/*      */     }
/*  359 */     return -1;
/*      */   }
/*      */ 
/*      */   private void setTy(double paramDouble)
/*      */   {
/*  365 */     if (this.ty == paramDouble) return;
/*  366 */     this.ty = paramDouble;
/*  367 */     this.itemsContainer.requestLayout();
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/*  389 */     this.contextMenu.focusedProperty().addListener(new ChangeListener() {
/*      */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  391 */         if (paramAnonymousBoolean2.booleanValue())
/*      */         {
/*  393 */           ContextMenuContent.this.currentFocusedIndex = -1;
/*  394 */           ContextMenuContent.this.requestFocus();
/*      */         }
/*      */       }
/*      */     });
/*  400 */     this.contextMenu.addEventHandler(Menu.ON_SHOWN, new EventHandler() {
/*      */       public void handle(Event paramAnonymousEvent) {
/*  402 */         for (Iterator localIterator = ContextMenuContent.this.itemsContainer.getChildren().iterator(); localIterator.hasNext(); ) { localNode = (Node)localIterator.next();
/*  403 */           if ((localNode instanceof ContextMenuContent.MenuItemContainer)) {
/*  404 */             MenuItem localMenuItem = ((ContextMenuContent.MenuItemContainer)localNode).item;
/*      */ 
/*  407 */             if (("choice-box-menu-item".equals(localMenuItem.getId())) && 
/*  408 */               (((RadioMenuItem)localMenuItem).isSelected())) {
/*  409 */               localNode.requestFocus();
/*  410 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         Node localNode;
/*      */       }
/*      */     });
/*  421 */     setOnKeyPressed(new EventHandler()
/*      */     {
/*      */       public void handle(KeyEvent paramAnonymousKeyEvent) {
/*  424 */         switch (ContextMenuContent.10.$SwitchMap$javafx$scene$input$KeyCode[paramAnonymousKeyEvent.getCode().ordinal()]) {
/*      */         case 1:
/*  426 */           ContextMenuContent.this.processLeftKey(paramAnonymousKeyEvent);
/*  427 */           break;
/*      */         case 2:
/*  429 */           ContextMenuContent.this.processRightKey(paramAnonymousKeyEvent);
/*  430 */           break;
/*      */         case 3:
/*  432 */           paramAnonymousKeyEvent.consume();
/*      */         case 4:
/*  436 */           break;
/*      */         case 5:
/*  439 */           ContextMenuContent.this.moveToNextSibling();
/*  440 */           paramAnonymousKeyEvent.consume();
/*  441 */           break;
/*      */         case 6:
/*  444 */           ContextMenuContent.this.moveToPreviousSibling();
/*  445 */           paramAnonymousKeyEvent.consume();
/*  446 */           break;
/*      */         case 7:
/*      */         case 8:
/*  450 */           ContextMenuContent.this.selectMenuItem();
/*  451 */           paramAnonymousKeyEvent.consume();
/*      */         }
/*      */       }
/*      */     });
/*  457 */     setOnScroll(new EventHandler()
/*      */     {
/*      */       public void handle(ScrollEvent paramAnonymousScrollEvent)
/*      */       {
/*  463 */         if (((ContextMenuContent.this.downArrow.isVisible()) && ((paramAnonymousScrollEvent.getTextDeltaY() < 0.0D) || (paramAnonymousScrollEvent.getDeltaY() < 0.0D))) || ((ContextMenuContent.this.upArrow.isVisible()) && ((paramAnonymousScrollEvent.getTextDeltaY() > 0.0D) || (paramAnonymousScrollEvent.getDeltaY() > 0.0D))))
/*      */         {
/*  466 */           switch (ContextMenuContent.10.$SwitchMap$javafx$scene$input$ScrollEvent$VerticalTextScrollUnits[paramAnonymousScrollEvent.getTextDeltaYUnits().ordinal()])
/*      */           {
/*      */           case 1:
/*  472 */             int i = ContextMenuContent.this.findFocusedIndex();
/*  473 */             if (i == -1) {
/*  474 */               i = 0;
/*      */             }
/*  476 */             double d = ((Node)ContextMenuContent.this.itemsContainer.getChildren().get(i)).prefHeight(-1.0D);
/*  477 */             ContextMenuContent.this.scroll(paramAnonymousScrollEvent.getTextDeltaY() * d);
/*  478 */             break;
/*      */           case 2:
/*  483 */             ContextMenuContent.this.scroll(paramAnonymousScrollEvent.getTextDeltaY() * ContextMenuContent.this.itemsContainer.getHeight());
/*  484 */             break;
/*      */           case 3:
/*  489 */             ContextMenuContent.this.scroll(paramAnonymousScrollEvent.getDeltaY());
/*      */           }
/*      */ 
/*  492 */           paramAnonymousScrollEvent.consume();
/*      */         }
/*      */       }
/*      */     });
/*  497 */     addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler()
/*      */     {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*      */       {
/*  503 */         if ((ContextMenuContent.this.submenu != null) && (ContextMenuContent.this.submenu.isShowing())) return;
/*      */ 
/*  509 */         Iterator localIterator = Window.impl_getWindows();
/*  510 */         while (localIterator.hasNext()) {
/*  511 */           Window localWindow1 = (Window)localIterator.next();
/*  512 */           if (((localWindow1 instanceof ContextMenu)) && (ContextMenuContent.this.contextMenu != localWindow1)) {
/*  513 */             Window localWindow2 = ((ContextMenu)localWindow1).getOwnerWindow();
/*  514 */             if ((ContextMenuContent.this.contextMenu == localWindow2) && (localWindow2.isShowing())) {
/*  515 */               return;
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  521 */         ContextMenuContent.this.requestFocus();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void processLeftKey(KeyEvent paramKeyEvent)
/*      */   {
/*  533 */     if (this.currentFocusedIndex != -1) {
/*  534 */       Node localNode = (Node)this.itemsContainer.getChildren().get(this.currentFocusedIndex);
/*  535 */       if ((localNode instanceof MenuItemContainer)) {
/*  536 */         MenuItem localMenuItem = ((MenuItemContainer)localNode).item;
/*  537 */         if ((localMenuItem instanceof Menu)) {
/*  538 */           Menu localMenu = (Menu)localMenuItem;
/*  539 */           if ((localMenu == this.openSubmenu) && 
/*  540 */             (this.submenu != null) && (this.submenu.isShowing())) {
/*  541 */             hideSubmenu();
/*  542 */             paramKeyEvent.consume();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void processRightKey(KeyEvent paramKeyEvent)
/*      */   {
/*  551 */     if (this.currentFocusedIndex != -1) {
/*  552 */       Node localNode = (Node)this.itemsContainer.getChildren().get(this.currentFocusedIndex);
/*  553 */       if ((localNode instanceof MenuItemContainer)) {
/*  554 */         MenuItem localMenuItem = ((MenuItemContainer)localNode).item;
/*  555 */         if ((localMenuItem instanceof Menu)) {
/*  556 */           Menu localMenu = (Menu)localMenuItem;
/*  557 */           if (localMenu.isDisable()) return;
/*  558 */           this.selectedBackground = ((MenuItemContainer)localNode);
/*      */ 
/*  562 */           if ((this.openSubmenu == localMenu) && (this.submenu.isShowing())) return;
/*  563 */           localMenu.show();
/*      */ 
/*  565 */           ContextMenuContent localContextMenuContent = (ContextMenuContent)this.submenu.getSkin().getNode();
/*  566 */           if (localContextMenuContent != null) {
/*  567 */             if (localContextMenuContent.itemsContainer.getChildren().size() > 0)
/*  568 */               ((MenuItemContainer)localContextMenuContent.itemsContainer.getChildren().get(0)).requestFocus();
/*      */             else {
/*  570 */               localContextMenuContent.requestFocus();
/*      */             }
/*      */           }
/*  573 */           paramKeyEvent.consume();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void selectMenuItem() {
/*  580 */     if (this.currentFocusedIndex != -1) {
/*  581 */       Node localNode = (Node)this.itemsContainer.getChildren().get(this.currentFocusedIndex);
/*  582 */       if ((localNode instanceof MenuItemContainer)) {
/*  583 */         MenuItem localMenuItem = ((MenuItemContainer)localNode).item;
/*  584 */         if ((localMenuItem instanceof Menu)) {
/*  585 */           Menu localMenu = (Menu)localMenuItem;
/*  586 */           if (this.openSubmenu != null) {
/*  587 */             hideSubmenu();
/*      */           }
/*  589 */           if (localMenu.isDisable()) return;
/*  590 */           this.selectedBackground = ((MenuItemContainer)localNode);
/*  591 */           localMenu.show();
/*      */         } else {
/*  593 */           ((MenuItemContainer)localNode).doSelect();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int findNext(int paramInt)
/*      */   {
/*      */     Node localNode;
/*  602 */     for (int i = paramInt; i < this.itemsContainer.getChildren().size(); i++) {
/*  603 */       localNode = (Node)this.itemsContainer.getChildren().get(i);
/*  604 */       if ((localNode instanceof MenuItemContainer)) {
/*  605 */         return i;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  611 */     for (i = 0; i < paramInt; i++) {
/*  612 */       localNode = (Node)this.itemsContainer.getChildren().get(i);
/*  613 */       if ((localNode instanceof MenuItemContainer)) {
/*  614 */         return i;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  619 */     return -1;
/*      */   }
/*      */ 
/*      */   private void moveToNextSibling() {
/*  623 */     this.currentFocusedIndex = findFocusedIndex();
/*      */ 
/*  627 */     if (this.currentFocusedIndex != -1)
/*  628 */       this.currentFocusedIndex = findNext(this.currentFocusedIndex + 1);
/*  629 */     else if ((this.currentFocusedIndex == -1) || (this.currentFocusedIndex == this.itemsContainer.getChildren().size() - 1)) {
/*  630 */       this.currentFocusedIndex = findNext(0);
/*      */     }
/*      */ 
/*  633 */     if (this.currentFocusedIndex != -1)
/*  634 */       ((MenuItemContainer)this.itemsContainer.getChildren().get(this.currentFocusedIndex)).requestFocus();
/*      */   }
/*      */ 
/*      */   private int findPrevious(int paramInt)
/*      */   {
/*      */     Node localNode;
/*  642 */     for (int i = paramInt; i >= 0; i--) {
/*  643 */       localNode = (Node)this.itemsContainer.getChildren().get(i);
/*  644 */       if ((localNode instanceof MenuItemContainer)) {
/*  645 */         return i;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  650 */     for (i = this.itemsContainer.getChildren().size() - 1; i > paramInt; i--) {
/*  651 */       localNode = (Node)this.itemsContainer.getChildren().get(i);
/*  652 */       if ((localNode instanceof MenuItemContainer)) {
/*  653 */         return i;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  658 */     return -1;
/*      */   }
/*      */ 
/*      */   private void moveToPreviousSibling() {
/*  662 */     this.currentFocusedIndex = findFocusedIndex();
/*      */ 
/*  666 */     if (this.currentFocusedIndex != -1)
/*  667 */       this.currentFocusedIndex = findPrevious(this.currentFocusedIndex - 1);
/*  668 */     else if ((this.currentFocusedIndex == -1) || (this.currentFocusedIndex == 0)) {
/*  669 */       this.currentFocusedIndex = findPrevious(this.itemsContainer.getChildren().size() - 1);
/*      */     }
/*      */ 
/*  672 */     if (this.currentFocusedIndex != -1)
/*  673 */       ((MenuItemContainer)this.itemsContainer.getChildren().get(this.currentFocusedIndex)).requestFocus();
/*      */   }
/*      */ 
/*      */   private Menu getRootMenu(Menu paramMenu)
/*      */   {
/*  678 */     if ((paramMenu == null) || (paramMenu.getParentMenu() == null)) {
/*  679 */       return paramMenu;
/*      */     }
/*  681 */     Menu localMenu = paramMenu.getParentMenu();
/*  682 */     while (localMenu.getParentMenu() != null) {
/*  683 */       localMenu = localMenu.getParentMenu();
/*      */     }
/*  685 */     return localMenu;
/*      */   }
/*      */ 
/*      */   double getMenuYOffset(int paramInt)
/*      */   {
/*  693 */     double d = 0.0D;
/*  694 */     if (this.itemsContainer.getChildren().size() >= paramInt) {
/*  695 */       d = getInsets().getTop();
/*  696 */       Node localNode = (Node)this.itemsContainer.getChildren().get(paramInt);
/*  697 */       d += localNode.getLayoutY() + localNode.prefHeight(-1.0D);
/*      */     }
/*  699 */     return d;
/*      */   }
/*      */ 
/*      */   private void setUpBinds() {
/*  703 */     updateMenuShowingListeners(this.contextMenu.getItems());
/*  704 */     this.contextMenu.getItems().addListener(new ListChangeListener()
/*      */     {
/*      */       public void onChanged(ListChangeListener.Change<? extends MenuItem> paramAnonymousChange)
/*      */       {
/*  712 */         while (paramAnonymousChange.next()) {
/*  713 */           ContextMenuContent.this.updateMenuShowingListeners(paramAnonymousChange.getAddedSubList());
/*      */         }
/*      */ 
/*  717 */         ContextMenuContent.this.updateVisualItems();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void updateMenuShowingListeners(List<? extends MenuItem> paramList) {
/*  723 */     for (MenuItem localMenuItem : paramList) {
/*  724 */       if ((localMenuItem instanceof Menu)) {
/*  725 */         final Menu localMenu = (Menu)localMenuItem;
/*  726 */         localMenu.showingProperty().addListener(new ChangeListener()
/*      */         {
/*      */           public void changed(ObservableValue paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  729 */             if ((paramAnonymousBoolean1.booleanValue()) && (!paramAnonymousBoolean2.booleanValue()))
/*      */             {
/*  731 */               ContextMenuContent.this.hideSubmenu();
/*  732 */             } else if ((!paramAnonymousBoolean1.booleanValue()) && (paramAnonymousBoolean2.booleanValue()))
/*      */             {
/*  734 */               ContextMenuContent.this.showSubmenu(localMenu);
/*      */             }
/*      */           }
/*      */         });
/*      */       }
/*      */ 
/*  740 */       localMenuItem.visibleProperty().addListener(new ChangeListener()
/*      */       {
/*      */         public void changed(ObservableValue paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
/*      */         {
/*  744 */           ContextMenuContent.this.requestLayout();
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   ContextMenu getSubMenu()
/*      */   {
/*  752 */     return this.submenu;
/*      */   }
/*      */ 
/*      */   private void showSubmenu(Menu paramMenu) {
/*  756 */     this.openSubmenu = paramMenu;
/*      */ 
/*  758 */     if (this.submenu == null) {
/*  759 */       this.submenu = new ContextMenu();
/*  760 */       this.submenu.showingProperty().addListener(new ChangeListener()
/*      */       {
/*      */         public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  763 */           if (!ContextMenuContent.this.submenu.isShowing())
/*      */           {
/*  766 */             for (Node localNode : ContextMenuContent.this.itemsContainer.getChildren()) {
/*  767 */               if (((localNode instanceof ContextMenuContent.MenuItemContainer)) && ((((ContextMenuContent.MenuItemContainer)localNode).item instanceof Menu)))
/*      */               {
/*  769 */                 Menu localMenu = (Menu)((ContextMenuContent.MenuItemContainer)localNode).item;
/*  770 */                 if (localMenu.isShowing()) {
/*  771 */                   localMenu.hide();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*  780 */     this.submenu.getItems().setAll(paramMenu.getItems());
/*  781 */     this.submenu.show(this.selectedBackground, Side.RIGHT, 0.0D, 0.0D);
/*      */   }
/*      */ 
/*      */   private void hideSubmenu() {
/*  785 */     if (this.submenu == null) return;
/*      */ 
/*  787 */     this.submenu.hide();
/*      */   }
/*      */ 
/*      */   private void hideAllMenus(MenuItem paramMenuItem) {
/*  791 */     this.contextMenu.hide();
/*      */     Menu localMenu;
/*  794 */     while ((localMenu = paramMenuItem.getParentMenu()) != null) {
/*  795 */       localMenu.hide();
/*  796 */       paramMenuItem = localMenu;
/*      */     }
/*  798 */     if ((localMenu == null) && (paramMenuItem.getParentPopup() != null))
/*  799 */       paramMenuItem.getParentPopup().hide();
/*      */   }
/*      */ 
/*      */   void scroll(double paramDouble)
/*      */   {
/*  812 */     setTy(this.ty + paramDouble);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public static List<StyleableProperty> impl_CSS_STYLEABLES()
/*      */   {
/*  852 */     return StyleableProperties.STYLEABLES;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public List<StyleableProperty> impl_getStyleableProperties()
/*      */   {
/*  862 */     return impl_CSS_STYLEABLES();
/*      */   }
/*      */ 
/*      */   protected Label getLabelAt(int paramInt)
/*      */   {
/*  867 */     return ((MenuItemContainer)this.itemsContainer.getChildren().get(paramInt)).getLabel();
/*      */   }
/*      */ 
/*      */   private class MenuLabel extends Label
/*      */   {
/*      */     final MenuItem menuitem;
/*      */     ContextMenuContent.MenuItemContainer menuItemContainer;
/*      */ 
/*      */     public MenuLabel(MenuItem paramMenuItemContainer, ContextMenuContent.MenuItemContainer arg3)
/*      */     {
/* 1346 */       super();
/* 1347 */       setMnemonicParsing(paramMenuItemContainer.isMnemonicParsing());
/* 1348 */       setFocusTraversable(true);
/*      */       Node localNode;
/* 1349 */       setLabelFor(localNode);
/*      */ 
/* 1351 */       this.menuitem = paramMenuItemContainer;
/* 1352 */       this.menuItemContainer = localNode;
/*      */ 
/* 1354 */       addEventHandler(ActionEvent.ACTION, new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1356 */           Event.fireEvent(ContextMenuContent.MenuLabel.this.menuitem, new ActionEvent());
/* 1357 */           paramAnonymousActionEvent.consume();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void fire()
/*      */     {
/* 1366 */       this.menuItemContainer.doSelect();
/*      */     }
/*      */   }
/*      */ 
/*      */   public class MenuItemContainer extends Pane
/*      */   {
/*      */     private final MenuItem item;
/*      */     private Node left;
/*      */     private Node graphic;
/*      */     private Node label;
/*      */     private Node right;
/*      */     private static final String PSEUDO_CLASS_SELECTED = "selected";
/*      */     private static final String PSEUDO_CLASS_DISABLED = "disabled";
/*      */     private static final String PSEUDO_CLASS_CHECKED = "checked";
/*      */ 
/*      */     protected Label getLabel()
/*      */     {
/*  994 */       return (Label)this.label;
/*      */     }
/*      */ 
/*      */     public MenuItem getItem() {
/*  998 */       return this.item;
/*      */     }
/*      */ 
/*      */     public MenuItemContainer(MenuItem arg2)
/*      */     {
/*      */       Object localObject;
/* 1002 */       if (localObject == null) {
/* 1003 */         throw new NullPointerException("MenuItem can not be null");
/*      */       }
/*      */ 
/* 1006 */       getStyleClass().addAll(localObject.getStyleClass());
/* 1007 */       setId(localObject.getId());
/* 1008 */       this.item = localObject;
/*      */ 
/* 1010 */       createChildren();
/*      */ 
/* 1013 */       if ((localObject instanceof Menu))
/* 1014 */         listen(((Menu)localObject).showingProperty(), "selected");
/* 1015 */       else if ((localObject instanceof RadioMenuItem))
/* 1016 */         listen(((RadioMenuItem)localObject).selectedProperty(), "checked");
/* 1017 */       else if ((localObject instanceof CheckMenuItem)) {
/* 1018 */         listen(((CheckMenuItem)localObject).selectedProperty(), "checked");
/*      */       }
/* 1020 */       listen(localObject.disableProperty(), "disabled");
/*      */ 
/* 1023 */       getProperties().put(MenuItem.class, localObject);
/*      */     }
/*      */ 
/*      */     private void createChildren()
/*      */     {
/* 1030 */       if ((this.item instanceof CustomMenuItem)) {
/* 1031 */         createNodeMenuItemChildren((CustomMenuItem)this.item);
/* 1032 */         setOnMouseEntered(new EventHandler() {
/*      */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1034 */             ContextMenuContent.MenuItemContainer.this.requestFocus();
/*      */           }
/*      */         });
/*      */       }
/*      */       else {
/* 1039 */         Node localNode = getLeftGraphic(this.item);
/*      */         Object localObject1;
/* 1040 */         if (localNode != null) {
/* 1041 */           localObject1 = new StackPane();
/* 1042 */           ((StackPane)localObject1).getStyleClass().add("left-container");
/* 1043 */           ((StackPane)localObject1).getChildren().add(localNode);
/* 1044 */           this.left = ((Node)localObject1);
/* 1045 */           getChildren().add(this.left);
/*      */         }
/*      */         Object localObject2;
/* 1048 */         if (this.item.getGraphic() != null) {
/* 1049 */           localObject1 = this.item.getGraphic();
/* 1050 */           localObject2 = new StackPane();
/* 1051 */           ((StackPane)localObject2).getStyleClass().add("graphic-container");
/* 1052 */           ((StackPane)localObject2).getChildren().add(localObject1);
/* 1053 */           this.graphic = ((Node)localObject2);
/* 1054 */           getChildren().add(this.graphic);
/*      */         }
/*      */ 
/* 1058 */         this.label = new ContextMenuContent.MenuLabel(ContextMenuContent.this, this.item, this);
/* 1059 */         this.label.setStyle(this.item.getStyle());
/*      */ 
/* 1062 */         ((Label)this.label).textProperty().bind(this.item.textProperty());
/*      */ 
/* 1064 */         this.label.setMouseTransparent(true);
/* 1065 */         getChildren().add(this.label);
/* 1066 */         setOnMouseEntered(new EventHandler() {
/*      */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1068 */             ContextMenuContent.MenuItemContainer.this.requestFocus();
/*      */           }
/*      */         });
/* 1076 */         if ((this.item instanceof Menu)) {
/* 1077 */           localObject1 = (Menu)this.item;
/*      */ 
/* 1080 */           localObject2 = new Region();
/* 1081 */           ((Region)localObject2).setMouseTransparent(true);
/* 1082 */           ((Region)localObject2).getStyleClass().add("arrow");
/*      */ 
/* 1084 */           StackPane localStackPane = new StackPane();
/* 1085 */           localStackPane.setMaxWidth(Math.max(((Region)localObject2).prefWidth(-1.0D), 10.0D));
/* 1086 */           localStackPane.setMouseTransparent(true);
/* 1087 */           localStackPane.getStyleClass().add("right-container");
/* 1088 */           localStackPane.getChildren().add(localObject2);
/* 1089 */           this.right = localStackPane;
/* 1090 */           getChildren().add(localStackPane);
/*      */ 
/* 1093 */           setOnMouseEntered(new EventHandler() {
/*      */             public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1095 */               if ((ContextMenuContent.this.openSubmenu != null) && (ContextMenuContent.MenuItemContainer.this.item != ContextMenuContent.this.openSubmenu))
/*      */               {
/* 1098 */                 ContextMenuContent.this.hideSubmenu();
/*      */               }
/*      */ 
/* 1101 */               if (this.val$menu.isDisable()) return;
/* 1102 */               ContextMenuContent.this.selectedBackground = ContextMenuContent.MenuItemContainer.this;
/* 1103 */               this.val$menu.show();
/* 1104 */               ContextMenuContent.MenuItemContainer.this.requestFocus();
/*      */             }
/*      */ 
/*      */           });
/*      */         }
/*      */         else
/*      */         {
/* 1121 */           updateAccelerator();
/* 1122 */           this.item.acceleratorProperty().addListener(new InvalidationListener()
/*      */           {
/*      */             public void invalidated(Observable paramAnonymousObservable) {
/* 1125 */               ContextMenuContent.MenuItemContainer.this.updateAccelerator();
/*      */             }
/*      */           });
/* 1129 */           setOnMouseEntered(new EventHandler() {
/*      */             public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1131 */               if (ContextMenuContent.this.openSubmenu != null) {
/* 1132 */                 ContextMenuContent.this.openSubmenu.hide();
/*      */               }
/* 1134 */               ContextMenuContent.MenuItemContainer.this.requestFocus();
/*      */             }
/*      */           });
/* 1137 */           setOnMouseReleased(new EventHandler() {
/*      */             public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1139 */               ContextMenuContent.MenuItemContainer.this.doSelect();
/*      */             }
/*      */           });
/* 1145 */           focusedProperty().addListener(new ChangeListener()
/*      */           {
/*      */             public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 1148 */               if ((paramAnonymousBoolean2.booleanValue()) && (!paramAnonymousBoolean1.booleanValue()))
/* 1149 */                 ContextMenuContent.this.currentFocusedIndex = ContextMenuContent.this.itemsContainer.getChildren().indexOf(ContextMenuContent.MenuItemContainer.this);
/*      */             }
/*      */           });
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     private void updateAccelerator()
/*      */     {
/* 1159 */       if (this.item.getAccelerator() != null) {
/* 1160 */         String str = KeystrokeUtils.toString(this.item.getAccelerator());
/* 1161 */         this.right = new Label(str);
/* 1162 */         this.right.setStyle(this.item.getStyle());
/* 1163 */         this.right.getStyleClass().add("accelerator-text");
/* 1164 */         getChildren().add(this.right);
/*      */       } else {
/* 1166 */         getChildren().remove(this.right);
/*      */       }
/*      */     }
/*      */ 
/*      */     void doSelect()
/*      */     {
/* 1172 */       if ((this.item == null) || (this.item.isDisable()))
/*      */         return;
/*      */       Object localObject;
/* 1175 */       if ((this.item instanceof CheckMenuItem)) {
/* 1176 */         localObject = (CheckMenuItem)this.item;
/* 1177 */         ((CheckMenuItem)localObject).setSelected(!((CheckMenuItem)localObject).isSelected());
/* 1178 */       } else if ((this.item instanceof RadioMenuItem))
/*      */       {
/* 1183 */         localObject = (RadioMenuItem)this.item;
/* 1184 */         ((RadioMenuItem)localObject).setSelected(((RadioMenuItem)localObject).getToggleGroup() != null);
/*      */       }
/*      */ 
/* 1188 */       this.item.fire();
/* 1189 */       ContextMenuContent.this.hideAllMenus(this.item);
/*      */     }
/*      */ 
/*      */     private void createNodeMenuItemChildren(final CustomMenuItem paramCustomMenuItem) {
/* 1193 */       Node localNode = paramCustomMenuItem.getContent();
/* 1194 */       getChildren().add(localNode);
/* 1195 */       localNode.getStyleClass().addAll(paramCustomMenuItem.getStyleClass());
/*      */ 
/* 1197 */       localNode.setOnMouseClicked(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1199 */           if ((paramCustomMenuItem == null) || (paramCustomMenuItem.isDisable())) return;
/*      */ 
/* 1201 */           paramCustomMenuItem.fire();
/* 1202 */           if (paramCustomMenuItem.isHideOnClick())
/* 1203 */             ContextMenuContent.this.hideAllMenus(paramCustomMenuItem);
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     protected void layoutChildren()
/*      */     {
/* 1212 */       double d2 = prefHeight(-1.0D);
/*      */       double d1;
/* 1213 */       if (this.left != null) {
/* 1214 */         d1 = getInsets().getLeft();
/* 1215 */         this.left.resize(this.left.prefWidth(-1.0D), this.left.prefHeight(-1.0D));
/* 1216 */         positionInArea(this.left, d1, 0.0D, ContextMenuContent.this.maxLeftWidth, d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*      */       }
/*      */ 
/* 1219 */       if (this.graphic != null) {
/* 1220 */         d1 = getInsets().getLeft() + ContextMenuContent.this.maxLeftWidth;
/* 1221 */         this.graphic.resize(this.graphic.prefWidth(-1.0D), this.graphic.prefHeight(-1.0D));
/* 1222 */         positionInArea(this.graphic, d1, 0.0D, ContextMenuContent.this.maxGraphicWidth, d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*      */       }
/*      */ 
/* 1226 */       if (this.label != null) {
/* 1227 */         d1 = getInsets().getLeft() + ContextMenuContent.this.maxLeftWidth + ContextMenuContent.this.maxGraphicWidth;
/* 1228 */         this.label.resize(this.label.prefWidth(-1.0D), this.label.prefHeight(-1.0D));
/* 1229 */         positionInArea(this.label, d1, 0.0D, ContextMenuContent.this.maxLabelWidth, d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*      */       }
/*      */ 
/* 1233 */       if (this.right != null) {
/* 1234 */         d1 = getInsets().getLeft() + ContextMenuContent.this.maxLeftWidth + ContextMenuContent.this.maxGraphicWidth + ContextMenuContent.this.maxLabelWidth;
/* 1235 */         this.right.resize(this.right.prefWidth(-1.0D), this.right.prefHeight(-1.0D));
/* 1236 */         positionInArea(this.right, d1, 0.0D, ContextMenuContent.this.maxRightWidth, d2, 0.0D, HPos.RIGHT, VPos.CENTER);
/*      */       }
/*      */ 
/* 1240 */       if ((this.item instanceof CustomMenuItem)) {
/* 1241 */         Node localNode = ((CustomMenuItem)this.item).getContent();
/* 1242 */         if ((this.item instanceof SeparatorMenuItem)) {
/* 1243 */           double d3 = prefWidth(-1.0D) - (getInsets().getLeft() + ContextMenuContent.this.maxGraphicWidth + getInsets().getRight());
/* 1244 */           localNode.resize(d3, localNode.prefHeight(-1.0D));
/* 1245 */           positionInArea(localNode, getInsets().getLeft() + ContextMenuContent.this.maxGraphicWidth, 0.0D, prefWidth(-1.0D), d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*      */         } else {
/* 1247 */           localNode.resize(localNode.prefWidth(-1.0D), localNode.prefHeight(-1.0D));
/*      */ 
/* 1249 */           positionInArea(localNode, getInsets().getLeft(), 0.0D, getWidth(), d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public long impl_getPseudoClassState()
/*      */     {
/* 1256 */       long l = super.impl_getPseudoClassState();
/* 1257 */       if (((this.item instanceof Menu)) && (this.item.equals(ContextMenuContent.this.openSubmenu)) && (ContextMenuContent.this.submenu.isShowing()))
/* 1258 */         l |= ContextMenuContent.SELECTED_PSEUDOCLASS_STATE;
/* 1259 */       else if (((this.item instanceof RadioMenuItem)) && (((RadioMenuItem)this.item).isSelected()))
/* 1260 */         l |= ContextMenuContent.CHECKED_PSEUDOCLASS_STATE;
/* 1261 */       else if (((this.item instanceof CheckMenuItem)) && (((CheckMenuItem)this.item).isSelected())) {
/* 1262 */         l |= ContextMenuContent.CHECKED_PSEUDOCLASS_STATE;
/*      */       }
/* 1264 */       if (this.item.isDisable()) l |= ContextMenuContent.DISABLED_PSEUDOCLASS_STATE;
/* 1265 */       return l;
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble)
/*      */     {
/* 1271 */       double d = 0.0D;
/* 1272 */       if (((this.item instanceof CustomMenuItem)) || ((this.item instanceof SeparatorMenuItem))) {
/* 1273 */         d = ((Node)getChildren().get(0)).prefHeight(-1.0D);
/*      */       } else {
/* 1275 */         d = Math.max(d, this.left != null ? this.left.prefHeight(-1.0D) : 0.0D);
/* 1276 */         d = Math.max(d, this.graphic != null ? this.graphic.prefHeight(-1.0D) : 0.0D);
/* 1277 */         d = Math.max(d, this.label != null ? this.label.prefHeight(-1.0D) : 0.0D);
/* 1278 */         d = Math.max(d, this.right != null ? this.right.prefHeight(-1.0D) : 0.0D);
/*      */       }
/* 1280 */       return getInsets().getTop() + d + getInsets().getBottom();
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble) {
/* 1284 */       double d = 0.0D;
/* 1285 */       if (((this.item instanceof CustomMenuItem)) && (!(this.item instanceof SeparatorMenuItem))) {
/* 1286 */         d = getInsets().getLeft() + ((CustomMenuItem)this.item).getContent().prefWidth(-1.0D) + getInsets().getRight();
/*      */       }
/*      */ 
/* 1289 */       return Math.max(d, getInsets().getLeft() + ContextMenuContent.this.maxLeftWidth + ContextMenuContent.this.maxGraphicWidth + ContextMenuContent.this.maxLabelWidth + ContextMenuContent.this.maxRightWidth + getInsets().getRight());
/*      */     }
/*      */ 
/*      */     private void listen(ObservableBooleanValue paramObservableBooleanValue, final String paramString)
/*      */     {
/* 1295 */       paramObservableBooleanValue.addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1297 */           ContextMenuContent.MenuItemContainer.this.impl_pseudoClassStateChanged(paramString);
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     private Node getLeftGraphic(MenuItem paramMenuItem)
/*      */     {
/*      */       StackPane localStackPane;
/* 1306 */       if ((paramMenuItem instanceof RadioMenuItem)) {
/* 1307 */         localStackPane = new StackPane();
/* 1308 */         localStackPane.getStyleClass().add("radio");
/* 1309 */         return localStackPane;
/* 1310 */       }if ((paramMenuItem instanceof CheckMenuItem)) {
/* 1311 */         localStackPane = new StackPane();
/* 1312 */         localStackPane.getStyleClass().add("check");
/* 1313 */         return localStackPane;
/*      */       }
/*      */ 
/* 1316 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   class ArrowMenuItem extends StackPane
/*      */   {
/*      */     private StackPane upDownArrow;
/*      */     private ContextMenuContent popupMenuContent;
/*  893 */     private boolean up = false;
/*      */     private Timeline scrollTimeline;
/*      */ 
/*      */     public final boolean isUp()
/*      */     {
/*  894 */       return this.up;
/*      */     }
/*  896 */     public void setUp(boolean paramBoolean) { this.up = paramBoolean;
/*  897 */       this.upDownArrow.getStyleClass().setAll(new String[] { isUp() ? "menu-up-arrow" : "menu-down-arrow" });
/*      */     }
/*      */ 
/*      */     public ArrowMenuItem(ContextMenuContent arg2)
/*      */     {
/*  905 */       getStyleClass().setAll(new String[] { "scroll-arrow" });
/*  906 */       this.upDownArrow = new StackPane();
/*      */       Object localObject;
/*  907 */       this.popupMenuContent = localObject;
/*  908 */       this.upDownArrow.setMouseTransparent(true);
/*  909 */       this.upDownArrow.getStyleClass().setAll(new String[] { isUp() ? "menu-up-arrow" : "menu-down-arrow" });
/*      */ 
/*  911 */       setOnMouseEntered(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  913 */           if ((ContextMenuContent.ArrowMenuItem.this.scrollTimeline != null) && (ContextMenuContent.ArrowMenuItem.this.scrollTimeline.getStatus() != Animation.Status.STOPPED)) {
/*  914 */             return;
/*      */           }
/*  916 */           ContextMenuContent.ArrowMenuItem.this.startTimeline();
/*      */         }
/*      */       });
/*  919 */       setOnMouseExited(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  921 */           ContextMenuContent.ArrowMenuItem.this.stopTimeline();
/*      */         }
/*      */       });
/*  924 */       setVisible(false);
/*  925 */       setManaged(false);
/*  926 */       getChildren().add(this.upDownArrow);
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble)
/*      */     {
/*  931 */       return ContextMenuContent.this.itemsContainer.getWidth();
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble) {
/*  935 */       return snapSize(getInsets().getTop()) + this.upDownArrow.prefHeight(-1.0D) + snapSize(getInsets().getBottom());
/*      */     }
/*      */ 
/*      */     protected void layoutChildren() {
/*  939 */       double d1 = snapSize(this.upDownArrow.prefWidth(-1.0D));
/*  940 */       double d2 = snapSize(this.upDownArrow.prefHeight(-1.0D));
/*  941 */       double d3 = (snapSize(getWidth()) - d1) / 2.0D;
/*  942 */       double d4 = (snapSize(getHeight()) - d2) / 2.0D;
/*      */ 
/*  944 */       this.upDownArrow.resize(d1, d2);
/*  945 */       positionInArea(this.upDownArrow, 0.0D, 0.0D, getWidth(), getHeight(), 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*      */ 
/*      */     public Region getArrowRegion()
/*      */     {
/*  950 */       return this.upDownArrow;
/*      */     }
/*      */ 
/*      */     private void adjust() {
/*  954 */       if (this.up) this.popupMenuContent.scroll(12.0D); else this.popupMenuContent.scroll(-12.0D); 
/*      */     }
/*      */ 
/*      */     private void startTimeline()
/*      */     {
/*  958 */       this.scrollTimeline = new Timeline();
/*  959 */       this.scrollTimeline.setCycleCount(-1);
/*  960 */       KeyFrame localKeyFrame = new KeyFrame(Duration.millis(60.0D), new EventHandler()
/*      */       {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent)
/*      */         {
/*  964 */           ContextMenuContent.ArrowMenuItem.this.adjust();
/*      */         }
/*      */       }
/*      */       , new KeyValue[0]);
/*      */ 
/*  968 */       this.scrollTimeline.getKeyFrames().clear();
/*  969 */       this.scrollTimeline.getKeyFrames().add(localKeyFrame);
/*  970 */       this.scrollTimeline.play();
/*      */     }
/*      */ 
/*      */     private void stopTimeline() {
/*  974 */       this.scrollTimeline.stop();
/*  975 */       this.scrollTimeline = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   class MenuBox extends VBox
/*      */   {
/*      */     MenuBox()
/*      */     {
/*      */     }
/*      */ 
/*      */     protected void layoutChildren()
/*      */     {
/*  878 */       double d1 = ContextMenuContent.this.ty;
/*  879 */       for (Node localNode : ContextMenuContent.this.itemsContainer.getChildren())
/*  880 */         if (localNode.isVisible()) {
/*  881 */           double d2 = snapSize(localNode.prefHeight(-1.0D));
/*  882 */           localNode.resize(snapSize(getWidth()), d2);
/*  883 */           localNode.relocate(snapSpace(getInsets().getLeft()), d1);
/*  884 */           d1 += d2;
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class StyleableProperties
/*      */   {
/*  842 */     private static final List<StyleableProperty> STYLEABLES = Collections.unmodifiableList(localArrayList);
/*      */ 
/*      */     static
/*      */     {
/*  827 */       ArrayList localArrayList = new ArrayList(SkinBase.impl_CSS_STYLEABLES());
/*      */ 
/*  834 */       List localList = Node.impl_CSS_STYLEABLES();
/*  835 */       int i = 0; for (int j = localList.size(); i < j; i++) {
/*  836 */         StyleableProperty localStyleableProperty = (StyleableProperty)localList.get(i);
/*  837 */         if ("effect".equals(localStyleableProperty.getProperty())) {
/*  838 */           localArrayList.add(localStyleableProperty);
/*  839 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ContextMenuContent
 * JD-Core Version:    0.6.2
 */