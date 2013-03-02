/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.MenuButtonBehaviorBase;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Labeled;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.MenuButton;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public abstract class MenuButtonSkinBase<C extends MenuButton, B extends MenuButtonBehaviorBase<C>> extends SkinBase<C, B>
/*     */ {
/*     */   protected final LabeledImpl label;
/*     */   protected final StackPane arrow;
/*     */   protected final StackPane arrowButton;
/*     */   protected final ContextMenu popup;
/*  69 */   protected boolean behaveLikeButton = false;
/*     */ 
/*     */   public MenuButtonSkinBase(C paramC, B paramB)
/*     */   {
/*  78 */     super(paramC, paramB);
/*     */ 
/*  80 */     if (paramC.getOnMousePressed() == null) {
/*  81 */       paramC.setOnMousePressed(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  83 */           ((MenuButtonBehaviorBase)MenuButtonSkinBase.this.getBehavior()).mousePressed(paramAnonymousMouseEvent, MenuButtonSkinBase.this.behaveLikeButton);
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*  88 */     if (paramC.getOnMouseReleased() == null) {
/*  89 */       paramC.setOnMouseReleased(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  91 */           ((MenuButtonBehaviorBase)MenuButtonSkinBase.this.getBehavior()).mouseReleased(paramAnonymousMouseEvent, MenuButtonSkinBase.this.behaveLikeButton);
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/*  99 */     this.label = new MenuLabeledImpl((MenuButton)getSkinnable());
/* 100 */     this.label.setMnemonicParsing(paramC.isMnemonicParsing());
/* 101 */     this.label.setLabelFor(paramC);
/* 102 */     this.label.setFocusTraversable(true);
/*     */ 
/* 104 */     this.arrow = new StackPane();
/* 105 */     this.arrow.getStyleClass().setAll(new String[] { "arrow" });
/* 106 */     this.arrow.setMaxWidth((-1.0D / 0.0D));
/* 107 */     this.arrow.setMaxHeight((-1.0D / 0.0D));
/*     */ 
/* 109 */     this.arrowButton = new StackPane();
/* 110 */     this.arrowButton.getStyleClass().setAll(new String[] { "arrow-button" });
/* 111 */     this.arrowButton.getChildren().add(this.arrow);
/*     */ 
/* 113 */     this.popup = new ContextMenu();
/* 114 */     this.popup.getItems().clear();
/* 115 */     this.popup.getItems().addAll(((MenuButton)getSkinnable()).getItems());
/*     */ 
/* 117 */     getChildren().clear();
/* 118 */     getChildren().addAll(new Node[] { this.label, this.arrowButton });
/*     */ 
/* 120 */     requestLayout();
/*     */ 
/* 122 */     paramC.getItems().addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 124 */         while (paramAnonymousChange.next()) {
/* 125 */           MenuButtonSkinBase.this.popup.getItems().removeAll(paramAnonymousChange.getRemoved());
/* 126 */           MenuButtonSkinBase.this.popup.getItems().addAll(paramAnonymousChange.getFrom(), paramAnonymousChange.getAddedSubList());
/*     */         }
/*     */       }
/*     */     });
/* 131 */     if (((MenuButton)getSkinnable()).getScene() != null) {
/* 132 */       addAccelerators(((MenuButton)getSkinnable()).getItems());
/*     */     }
/* 134 */     paramC.sceneProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Scene> paramAnonymousObservableValue, Scene paramAnonymousScene1, Scene paramAnonymousScene2) {
/* 137 */         if (((MenuButton)MenuButtonSkinBase.this.getSkinnable()).getScene() != null)
/* 138 */           MenuButtonSkinBase.this.addAccelerators(((MenuButton)MenuButtonSkinBase.this.getSkinnable()).getItems());
/*     */       }
/*     */     });
/* 157 */     registerChangeListener(paramC.showingProperty(), "SHOWING");
/* 158 */     registerChangeListener(paramC.focusedProperty(), "FOCUSED");
/* 159 */     registerChangeListener(paramC.mnemonicParsingProperty(), "MNEMONIC_PARSING");
/* 160 */     registerChangeListener(this.popup.showingProperty(), "POPUP_VISIBLE");
/*     */   }
/*     */ 
/*     */   private void show()
/*     */   {
/* 170 */     if (!this.popup.isShowing())
/* 171 */       this.popup.show(getSkinnable(), ((MenuButton)getSkinnable()).getPopupSide(), 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   private void hide()
/*     */   {
/* 185 */     if (this.popup.isShowing())
/* 186 */       this.popup.hide();
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 195 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 197 */     if (paramString == "SHOWING") {
/* 198 */       if (((MenuButton)getSkinnable()).isShowing())
/* 199 */         show();
/*     */       else
/* 201 */         hide();
/*     */     }
/* 203 */     else if (paramString == "FOCUSED")
/*     */     {
/* 205 */       if ((!((MenuButton)getSkinnable()).isFocused()) && (((MenuButton)getSkinnable()).isShowing())) {
/* 206 */         hide();
/*     */       }
/* 208 */       if ((!((MenuButton)getSkinnable()).isFocused()) && (this.popup.isShowing()))
/* 209 */         hide();
/*     */     }
/* 211 */     else if (paramString == "POPUP_VISIBLE") {
/* 212 */       if ((!this.popup.isShowing()) && (((MenuButton)getSkinnable()).isShowing()))
/*     */       {
/* 215 */         ((MenuButton)getSkinnable()).hide();
/*     */       }
/*     */ 
/* 218 */       if (this.popup.isShowing()) {
/* 219 */         Utils.addMnemonics(this.popup, ((MenuButton)getSkinnable()).getScene());
/*     */       }
/*     */       else {
/* 222 */         Utils.removeMnemonics(this.popup, ((MenuButton)getSkinnable()).getScene());
/*     */       }
/*     */ 
/*     */     }
/* 226 */     else if (paramString == "MNEMONIC_PARSING") {
/* 227 */       this.label.setMnemonicParsing(((MenuButton)getSkinnable()).isMnemonicParsing());
/* 228 */       requestLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 239 */     Insets localInsets1 = getInsets();
/* 240 */     Insets localInsets2 = this.arrowButton.getInsets();
/* 241 */     return localInsets1.getLeft() + this.label.prefWidth(paramDouble) + localInsets2.getLeft() + this.arrow.prefWidth(paramDouble) + localInsets2.getRight() + localInsets1.getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 250 */     Insets localInsets1 = getInsets();
/* 251 */     Insets localInsets2 = this.arrowButton.getInsets();
/* 252 */     return localInsets1.getTop() + Math.max(this.label.prefHeight(paramDouble), localInsets2.getTop() + this.arrow.prefHeight(-1.0D) + localInsets2.getBottom()) + localInsets1.getBottom();
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble)
/*     */   {
/* 258 */     return ((MenuButton)getSkinnable()).prefWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble) {
/* 262 */     return ((MenuButton)getSkinnable()).prefHeight(paramDouble);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 266 */     Insets localInsets1 = getInsets();
/* 267 */     Insets localInsets2 = this.arrowButton.getInsets();
/*     */ 
/* 270 */     double d1 = localInsets1.getLeft();
/* 271 */     double d2 = localInsets1.getTop();
/* 272 */     double d3 = getWidth() - (localInsets1.getLeft() + localInsets1.getRight());
/* 273 */     double d4 = getHeight() - (localInsets1.getTop() + localInsets1.getBottom());
/*     */ 
/* 275 */     double d5 = this.arrow.prefWidth(-1.0D);
/* 276 */     double d6 = localInsets2.getLeft() + d5 + localInsets2.getRight();
/*     */ 
/* 278 */     this.label.resizeRelocate(d1, d2, d3 - d6, d4);
/*     */ 
/* 280 */     this.arrowButton.resize(d6, d4);
/* 281 */     positionInArea(this.arrowButton, getWidth() - localInsets1.getRight() - d6, d2, d6, d4, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ 
/*     */   private void addAccelerators(ObservableList<MenuItem> paramObservableList)
/*     */   {
/* 289 */     for (final MenuItem localMenuItem : paramObservableList)
/* 290 */       if ((localMenuItem instanceof Menu))
/*     */       {
/* 292 */         addAccelerators(((Menu)localMenuItem).getItems());
/*     */       }
/* 297 */       else if ((localMenuItem.getAccelerator() != null) && 
/* 298 */         (((MenuButton)getSkinnable()).getScene().getAccelerators() != null))
/*     */       {
/* 300 */         Runnable local5 = new Runnable() {
/*     */           public void run() {
/* 302 */             if (localMenuItem.getOnMenuValidation() != null) {
/* 303 */               Event.fireEvent(localMenuItem, new Event(localMenuItem.MENU_VALIDATION_EVENT));
/*     */             }
/* 305 */             Menu localMenu = localMenuItem.getParentMenu();
/* 306 */             if (localMenu.getOnMenuValidation() != null) {
/* 307 */               Event.fireEvent(localMenu, new Event(localMenu.MENU_VALIDATION_EVENT));
/*     */             }
/* 309 */             if (!localMenuItem.isDisable()) localMenuItem.fire();
/*     */           }
/*     */         };
/* 312 */         ((MenuButton)getSkinnable()).getScene().getAccelerators().put(localMenuItem.getAccelerator(), local5);
/*     */       }
/*     */   }
/*     */ 
/*     */   private class MenuLabeledImpl extends LabeledImpl
/*     */   {
/*     */     MenuButton button;
/*     */ 
/*     */     public MenuLabeledImpl(MenuButton arg2)
/*     */     {
/* 345 */       super();
/* 346 */       this.button = localLabeled;
/* 347 */       addEventHandler(ActionEvent.ACTION, new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 349 */           MenuButtonSkinBase.MenuLabeledImpl.this.button.fireEvent(new ActionEvent());
/* 350 */           paramAnonymousActionEvent.consume();
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     public void fire()
/*     */     {
/* 359 */       this.button.fire();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.MenuButtonSkinBase
 * JD-Core Version:    0.6.2
 */