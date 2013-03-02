/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.menu.MenuBase;
/*     */ import com.sun.javafx.scene.control.GlobalMenuAdapter;
/*     */ import com.sun.javafx.scene.control.WeakEventHandler;
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import com.sun.javafx.scene.traversal.Direction;
/*     */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*     */ import com.sun.javafx.scene.traversal.TraverseListener;
/*     */ import com.sun.javafx.stage.StageHelper;
/*     */ import com.sun.javafx.tk.TKSystemMenu;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.CustomMenuItem;
/*     */ import javafx.scene.control.Menu;
/*     */ import javafx.scene.control.MenuBar;
/*     */ import javafx.scene.control.MenuButton;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.SeparatorMenuItem;
/*     */ import javafx.scene.input.KeyCombination;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.stage.Stage;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class MenuBarSkin extends SkinBase<MenuBar, BehaviorBase<MenuBar>>
/*     */   implements TraverseListener
/*     */ {
/*     */   private final HBox container;
/*     */   private Menu openMenu;
/*     */   private MenuBarButton openMenuButton;
/*  87 */   private int focusedMenuIndex = -1;
/*     */   private TraversalEngine engine;
/*     */   private Direction direction;
/*  90 */   private boolean firstF10 = true;
/*     */   private static WeakHashMap<Stage, MenuBarSkin> systemMenuMap;
/*  93 */   private static List<MenuBase> wrappedDefaultMenus = new ArrayList();
/*     */   private static Stage currentMenuBarStage;
/*     */   private List<MenuBase> wrappedMenus;
/*     */   private WeakEventHandler weakSceneKeyEventHandler;
/*     */   private WeakEventHandler weakSceneMouseEventHandler;
/*     */   private EventHandler keyEventHandler;
/*     */   private EventHandler mouseEventHandler;
/* 351 */   Runnable firstMenuRunnable = new Runnable()
/*     */   {
/*     */     public void run()
/*     */     {
/* 358 */       if ((MenuBarSkin.this.container.getChildren().size() > 0) && 
/* 359 */         ((MenuBarSkin.this.container.getChildren().get(0) instanceof MenuButton)))
/*     */       {
/* 361 */         if (MenuBarSkin.this.firstF10) {
/* 362 */           MenuBarSkin.this.firstF10 = false;
/* 363 */           MenuBarSkin.this.unSelectMenus();
/* 364 */           MenuBarSkin.this.focusedMenuIndex = 0;
/* 365 */           MenuBarSkin.this.openMenuButton = ((MenuBarSkin.MenuBarButton)MenuBarSkin.this.container.getChildren().get(0));
/* 366 */           MenuBarSkin.this.openMenu = ((Menu)((MenuBar)MenuBarSkin.this.getSkinnable()).getMenus().get(0));
/* 367 */           MenuBarSkin.this.openMenuButton.setHover();
/*     */         } else {
/* 369 */           MenuBarSkin.this.firstF10 = true;
/* 370 */           MenuBarSkin.this.unSelectMenus();
/*     */         }
/*     */       }
/*     */     }
/* 351 */   };
/*     */ 
/* 378 */   private boolean pendingDismiss = false;
/*     */ 
/* 426 */   private EventHandler<ActionEvent> menuActionEventHandler = new EventHandler()
/*     */   {
/*     */     public void handle(ActionEvent paramAnonymousActionEvent) {
/* 429 */       MenuBarSkin.this.unSelectMenus();
/*     */     }
/* 426 */   };
/*     */ 
/*     */   public static void setDefaultSystemMenuBar(MenuBar paramMenuBar)
/*     */   {
/*  98 */     if (Toolkit.getToolkit().getSystemMenu().isSupported()) {
/*  99 */       wrappedDefaultMenus.clear();
/* 100 */       for (Menu localMenu : paramMenuBar.getMenus()) {
/* 101 */         wrappedDefaultMenus.add(GlobalMenuAdapter.adapt(localMenu));
/*     */       }
/* 103 */       paramMenuBar.getMenus().addListener(new ListChangeListener() {
/*     */         public void onChanged(ListChangeListener.Change<? extends Menu> paramAnonymousChange) {
/* 105 */           MenuBarSkin.wrappedDefaultMenus.clear();
/* 106 */           for (Menu localMenu : this.val$menuBar.getMenus())
/* 107 */             MenuBarSkin.wrappedDefaultMenus.add(GlobalMenuAdapter.adapt(localMenu));
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setSystemMenu(Stage paramStage)
/*     */   {
/* 115 */     if ((paramStage != null) && (paramStage.isFocused()));
/*     */     Object localObject;
/* 116 */     while ((paramStage != null) && ((paramStage.getOwner() instanceof Stage))) {
/* 117 */       localObject = (MenuBarSkin)systemMenuMap.get(paramStage);
/* 118 */       if ((localObject == null) || (((MenuBarSkin)localObject).wrappedMenus == null))
/*     */       {
/* 125 */         paramStage = (Stage)paramStage.getOwner();
/*     */ 
/* 127 */         continue;
/*     */ 
/* 129 */         paramStage = null;
/*     */       }
/*     */     }
/* 132 */     if (paramStage != currentMenuBarStage) {
/* 133 */       localObject = null;
/* 134 */       if (paramStage != null) {
/* 135 */         MenuBarSkin localMenuBarSkin = (MenuBarSkin)systemMenuMap.get(paramStage);
/* 136 */         if (localMenuBarSkin != null) {
/* 137 */           localObject = localMenuBarSkin.wrappedMenus;
/*     */         }
/*     */       }
/* 140 */       if (localObject == null) {
/* 141 */         localObject = wrappedDefaultMenus;
/*     */       }
/* 143 */       Toolkit.getToolkit().getSystemMenu().setMenus((List)localObject);
/* 144 */       currentMenuBarStage = paramStage;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void initSystemMenuBar() {
/* 149 */     systemMenuMap = new WeakHashMap();
/*     */ 
/* 151 */     InvalidationListener local2 = new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 153 */         MenuBarSkin.setSystemMenu((Stage)((ReadOnlyProperty)paramAnonymousObservable).getBean());
/*     */       }
/*     */     };
/* 157 */     ObservableList localObservableList = StageHelper.getStages();
/* 158 */     for (Stage localStage : localObservableList) {
/* 159 */       localStage.focusedProperty().addListener(local2);
/*     */     }
/* 161 */     localObservableList.addListener(new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends Stage> paramAnonymousChange)
/*     */       {
/*     */         Iterator localIterator;
/*     */         Stage localStage;
/* 163 */         while (paramAnonymousChange.next()) {
/* 164 */           for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localStage = (Stage)localIterator.next();
/* 165 */             localStage.focusedProperty().removeListener(this.val$focusedStageListener);
/*     */           }
/* 167 */           for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localStage = (Stage)localIterator.next();
/* 168 */             localStage.focusedProperty().addListener(this.val$focusedStageListener);
/* 169 */             MenuBarSkin.setSystemMenu(localStage);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public MenuBarSkin(final MenuBar paramMenuBar)
/*     */   {
/* 188 */     super(paramMenuBar, new BehaviorBase(paramMenuBar));
/*     */ 
/* 190 */     this.container = new HBox();
/* 191 */     getChildren().add(this.container);
/*     */ 
/* 194 */     this.keyEventHandler = new EventHandler()
/*     */     {
/*     */       public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 197 */         if (MenuBarSkin.this.openMenu != null)
/* 198 */           switch (MenuBarSkin.23.$SwitchMap$javafx$scene$input$KeyCode[paramAnonymousKeyEvent.getCode().ordinal()]) {
/*     */           case 1:
/* 200 */             if (paramMenuBar.getScene().getWindow().isFocused()) {
/* 201 */               if (MenuBarSkin.this.openMenu == null) return;
/* 202 */               if (!MenuBarSkin.this.openMenu.isShowing()) {
/* 203 */                 MenuBarSkin.this.selectPrevMenu();
/* 204 */                 paramAnonymousKeyEvent.consume();
/* 205 */                 return;
/*     */               }
/* 207 */               MenuBarSkin.this.showPrevMenu();
/*     */             }
/* 209 */             paramAnonymousKeyEvent.consume();
/* 210 */             break;
/*     */           case 2:
/*     */           case 3:
/* 214 */             if (paramMenuBar.getScene().getWindow().isFocused()) {
/* 215 */               if (MenuBarSkin.this.openMenu == null) return;
/* 216 */               if (!MenuBarSkin.this.openMenu.isShowing()) {
/* 217 */                 MenuBarSkin.this.selectNextMenu();
/* 218 */                 paramAnonymousKeyEvent.consume();
/* 219 */                 return;
/*     */               }
/* 221 */               MenuBarSkin.this.showNextMenu();
/*     */             }
/* 223 */             paramAnonymousKeyEvent.consume();
/* 224 */             break;
/*     */           case 4:
/* 230 */             if ((paramMenuBar.getScene().getWindow().isFocused()) && 
/* 231 */               (MenuBarSkin.this.focusedMenuIndex != -1) && (MenuBarSkin.this.openMenu != null)) {
/* 232 */               if (!MenuBarSkin.this.isMenuEmpty((Menu)((MenuBar)MenuBarSkin.this.getSkinnable()).getMenus().get(MenuBarSkin.this.focusedMenuIndex))) {
/* 233 */                 MenuBarSkin.this.openMenu = ((Menu)((MenuBar)MenuBarSkin.this.getSkinnable()).getMenus().get(MenuBarSkin.this.focusedMenuIndex));
/* 234 */                 MenuBarSkin.this.openMenu.show();
/*     */               } else {
/* 236 */                 MenuBarSkin.this.openMenu = null;
/*     */               }
/* 238 */               paramAnonymousKeyEvent.consume(); } break;
/*     */           case 5:
/* 243 */             MenuBarSkin.this.unSelectMenus();
/* 244 */             paramAnonymousKeyEvent.consume();
/*     */           }
/*     */       }
/*     */     };
/* 250 */     this.weakSceneKeyEventHandler = new WeakEventHandler(paramMenuBar.getScene(), KeyEvent.KEY_PRESSED, this.keyEventHandler);
/*     */ 
/* 252 */     paramMenuBar.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this.weakSceneKeyEventHandler);
/*     */ 
/* 255 */     this.mouseEventHandler = new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 257 */         if (!MenuBarSkin.this.container.localToScene(MenuBarSkin.this.container.getLayoutBounds()).contains(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY())) {
/* 258 */           MenuBarSkin.this.unSelectMenus();
/* 259 */           MenuBarSkin.this.firstF10 = true;
/*     */         }
/*     */       }
/*     */     };
/* 263 */     this.weakSceneMouseEventHandler = new WeakEventHandler(paramMenuBar.getScene(), MouseEvent.MOUSE_CLICKED, this.mouseEventHandler);
/*     */ 
/* 265 */     paramMenuBar.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, this.weakSceneMouseEventHandler);
/*     */ 
/* 268 */     paramMenuBar.getScene().getWindow().focusedProperty().addListener(new WeakChangeListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 271 */         if (!paramAnonymousBoolean2.booleanValue()) {
/* 272 */           MenuBarSkin.this.unSelectMenus();
/* 273 */           MenuBarSkin.this.firstF10 = true;
/*     */         }
/*     */       }
/*     */     }));
/* 278 */     rebuildUI();
/* 279 */     paramMenuBar.getMenus().addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change<? extends Menu> paramAnonymousChange) {
/* 281 */         MenuBarSkin.this.rebuildUI();
/*     */       }
/*     */     });
/* 284 */     for (Object localObject1 = ((MenuBar)getSkinnable()).getMenus().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Menu)((Iterator)localObject1).next();
/* 285 */       ((Menu)localObject2).visibleProperty().addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 288 */           MenuBarSkin.this.rebuildUI();
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/* 293 */     if (Toolkit.getToolkit().getSystemMenu().isSupported()) {
/* 294 */       paramMenuBar.useSystemMenuBarProperty().addListener(new InvalidationListener() {
/*     */         public void invalidated(Observable paramAnonymousObservable) {
/* 296 */           MenuBarSkin.this.rebuildUI();
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/* 317 */     Object localObject2 = System.getProperty("os.name");
/* 318 */     if ((localObject2 != null) && (((String)localObject2).startsWith("Mac")))
/* 319 */       localObject1 = KeyCombination.keyCombination("ctrl+F10");
/*     */     else {
/* 321 */       localObject1 = KeyCombination.keyCombination("F10");
/*     */     }
/* 323 */     ((MenuBar)getSkinnable()).getParent().getScene().getAccelerators().put(localObject1, this.firstMenuRunnable);
/* 324 */     this.engine = new TraversalEngine(this, false) {
/*     */       public void trav(Node paramAnonymousNode, Direction paramAnonymousDirection) {
/* 326 */         MenuBarSkin.this.direction = paramAnonymousDirection;
/* 327 */         super.trav(paramAnonymousNode, paramAnonymousDirection);
/*     */       }
/*     */     };
/* 330 */     this.engine.addTraverseListener(this);
/* 331 */     setImpl_traversalEngine(this.engine);
/*     */ 
/* 333 */     paramMenuBar.sceneProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Scene> paramAnonymousObservableValue, Scene paramAnonymousScene1, Scene paramAnonymousScene2) {
/* 336 */         if (MenuBarSkin.this.weakSceneKeyEventHandler != null)
/*     */         {
/* 338 */           if (paramAnonymousScene1 != null)
/* 339 */             paramAnonymousScene1.removeEventFilter(KeyEvent.KEY_PRESSED, MenuBarSkin.this.weakSceneKeyEventHandler);
/*     */         }
/* 341 */         if (MenuBarSkin.this.weakSceneMouseEventHandler != null)
/*     */         {
/* 343 */           if (paramAnonymousScene1 != null)
/* 344 */             paramAnonymousScene1.removeEventFilter(MouseEvent.MOUSE_CLICKED, MenuBarSkin.this.weakSceneMouseEventHandler);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   MenuButton getNodeForMenu(int paramInt)
/*     */   {
/* 382 */     if (paramInt < this.container.getChildren().size()) {
/* 383 */       return (MenuBarButton)this.container.getChildren().get(paramInt);
/*     */     }
/* 385 */     return null;
/*     */   }
/*     */ 
/*     */   int getFocusedMenuIndex() {
/* 389 */     return this.focusedMenuIndex;
/*     */   }
/*     */ 
/*     */   private boolean menusContainCustomMenuItem() {
/* 393 */     for (Menu localMenu : ((MenuBar)getSkinnable()).getMenus()) {
/* 394 */       if (menuContainsCustomMenuItem(localMenu)) {
/* 395 */         System.err.println("Warning: MenuBar ignored property useSystemMenuBar because menus contain CustomMenuItem");
/* 396 */         return true;
/*     */       }
/*     */     }
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean menuContainsCustomMenuItem(Menu paramMenu) {
/* 403 */     for (MenuItem localMenuItem : paramMenu.getItems()) {
/* 404 */       if (((localMenuItem instanceof CustomMenuItem)) && (!(localMenuItem instanceof SeparatorMenuItem)))
/* 405 */         return true;
/* 406 */       if (((localMenuItem instanceof Menu)) && 
/* 407 */         (menuContainsCustomMenuItem((Menu)localMenuItem))) {
/* 408 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 412 */     return false;
/*     */   }
/*     */ 
/*     */   private int getMenuBarButtonIndex(MenuBarButton paramMenuBarButton) {
/* 416 */     for (int i = 0; i < this.container.getChildren().size(); i++) {
/* 417 */       MenuBarButton localMenuBarButton = (MenuBarButton)this.container.getChildren().get(i);
/* 418 */       if (paramMenuBarButton == localMenuBarButton) {
/* 419 */         return i;
/*     */       }
/*     */     }
/* 422 */     return -1;
/*     */   }
/*     */ 
/*     */   private void updateActionListeners(Menu paramMenu, boolean paramBoolean)
/*     */   {
/* 434 */     for (MenuItem localMenuItem : paramMenu.getItems())
/* 435 */       if ((localMenuItem instanceof Menu)) {
/* 436 */         updateActionListeners((Menu)localMenuItem, paramBoolean);
/*     */       }
/* 438 */       else if (paramBoolean)
/* 439 */         localMenuItem.addEventHandler(ActionEvent.ACTION, this.menuActionEventHandler);
/*     */       else
/* 441 */         localMenuItem.removeEventHandler(ActionEvent.ACTION, this.menuActionEventHandler);
/*     */   }
/*     */ 
/*     */   private void rebuildUI()
/*     */   {
/* 448 */     int i = 0;
/* 449 */     for (Object localObject1 = ((MenuBar)getSkinnable()).getMenus().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Menu)((Iterator)localObject1).next();
/*     */ 
/* 451 */       updateActionListeners((Menu)((MenuBar)getSkinnable()).getMenus().get(i), false);
/*     */     }
/* 453 */     Object localObject2;
/* 453 */     for (localObject1 = this.container.getChildren().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Node)((Iterator)localObject1).next();
/*     */ 
/* 456 */       localMenuBarButton = (MenuBarButton)localObject2;
/* 457 */       localMenuBarButton.hide();
/* 458 */       localMenuBarButton.menu.showingProperty().removeListener(localMenuBarButton.menuListener);
/* 459 */       localMenuBarButton.disableProperty().unbind();
/* 460 */       localMenuBarButton.textProperty().unbind();
/* 461 */       localMenuBarButton.graphicProperty().unbind();
/* 462 */       localMenuBarButton.styleProperty().unbind();
/* 463 */       i++;
/*     */     }
/*     */     final MenuBarButton localMenuBarButton;
/* 465 */     this.container.getChildren().clear();
/*     */ 
/* 468 */     if ((Toolkit.getToolkit().getSystemMenu().isSupported()) && (((MenuBar)getSkinnable()).getScene() != null)) {
/* 469 */       localObject1 = ((MenuBar)getSkinnable()).getScene();
/* 470 */       if ((((Scene)localObject1).getWindow() instanceof Stage)) {
/* 471 */         localObject2 = (Stage)((Scene)localObject1).getWindow();
/* 472 */         localMenuBarButton = systemMenuMap != null ? (MenuBarSkin)systemMenuMap.get(localObject2) : null;
/* 473 */         if ((((MenuBar)getSkinnable()).isUseSystemMenuBar()) && (!menusContainCustomMenuItem())) {
/* 474 */           if ((localMenuBarButton != null) && ((localMenuBarButton.getScene() == null) || (localMenuBarButton.getScene().getWindow() == null)))
/*     */           {
/* 477 */             systemMenuMap.remove(localObject2);
/* 478 */             localMenuBarButton = null;
/*     */           }
/*     */ 
/* 483 */           if ((systemMenuMap == null) || (localMenuBarButton == null) || (localMenuBarButton == this)) {
/* 484 */             if (systemMenuMap == null) {
/* 485 */               initSystemMenuBar();
/*     */             }
/* 487 */             if (this.wrappedMenus == null) {
/* 488 */               this.wrappedMenus = new ArrayList();
/* 489 */               systemMenuMap.put(localObject2, this);
/*     */             } else {
/* 491 */               this.wrappedMenus.clear();
/*     */             }
/* 493 */             for (Menu localMenu : ((MenuBar)getSkinnable()).getMenus()) {
/* 494 */               this.wrappedMenus.add(GlobalMenuAdapter.adapt(localMenu));
/*     */             }
/* 496 */             currentMenuBarStage = null;
/* 497 */             setSystemMenu((Stage)localObject2);
/*     */ 
/* 499 */             requestLayout();
/* 500 */             Platform.runLater(new Runnable() {
/*     */               public void run() {
/* 502 */                 MenuBarSkin.this.requestLayout();
/*     */               }
/*     */             });
/* 505 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 509 */         if (localMenuBarButton == this)
/*     */         {
/* 512 */           this.wrappedMenus = null;
/* 513 */           systemMenuMap.remove(localObject2);
/* 514 */           currentMenuBarStage = null;
/* 515 */           setSystemMenu((Stage)localObject2);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 521 */     for (localObject1 = ((MenuBar)getSkinnable()).getMenus().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Menu)((Iterator)localObject1).next();
/* 522 */       if (((Menu)localObject2).isVisible()) {
/* 523 */         localMenuBarButton = new MenuBarButton(((Menu)localObject2).getText(), ((Menu)localObject2).getGraphic());
/* 524 */         localMenuBarButton.setFocusTraversable(false);
/* 525 */         localMenuBarButton.getStyleClass().add("menu");
/* 526 */         localMenuBarButton.setStyle(((Menu)localObject2).getStyle());
/*     */ 
/* 528 */         localMenuBarButton.getItems().setAll(((Menu)localObject2).getItems());
/* 529 */         this.container.getChildren().add(localMenuBarButton);
/*     */ 
/* 531 */         ((Menu)localObject2).getItems().addListener(new ListChangeListener() {
/*     */           public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 533 */             while (paramAnonymousChange.next()) {
/* 534 */               localMenuBarButton.getItems().removeAll(paramAnonymousChange.getRemoved());
/* 535 */               localMenuBarButton.getItems().addAll(paramAnonymousChange.getFrom(), paramAnonymousChange.getAddedSubList());
/*     */             }
/*     */           }
/*     */         });
/* 539 */         ((Menu)localObject2).getStyleClass().addListener(new ListChangeListener()
/*     */         {
/*     */           public void onChanged(ListChangeListener.Change<? extends String> paramAnonymousChange) {
/* 542 */             while (paramAnonymousChange.next()) {
/* 543 */               for (int i = paramAnonymousChange.getFrom(); i < paramAnonymousChange.getTo(); i++) {
/* 544 */                 localMenuBarButton.getStyleClass().add(this.val$menu.getStyleClass().get(i));
/*     */               }
/* 546 */               for (String str : paramAnonymousChange.getRemoved())
/* 547 */                 localMenuBarButton.getStyleClass().remove(str);
/*     */             }
/*     */           }
/*     */         });
/* 552 */         localMenuBarButton.menuListener = new ChangeListener()
/*     */         {
/*     */           public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 555 */             if (this.val$menu.isShowing())
/* 556 */               localMenuBarButton.show();
/*     */             else
/* 558 */               localMenuBarButton.hide();
/*     */           }
/*     */         };
/* 563 */         localMenuBarButton.menu = ((Menu)localObject2);
/* 564 */         ((Menu)localObject2).showingProperty().addListener(localMenuBarButton.menuListener);
/* 565 */         localMenuBarButton.disableProperty().bindBidirectional(((Menu)localObject2).disableProperty());
/* 566 */         localMenuBarButton.textProperty().bind(((Menu)localObject2).textProperty());
/* 567 */         localMenuBarButton.graphicProperty().bind(((Menu)localObject2).graphicProperty());
/* 568 */         localMenuBarButton.styleProperty().bind(((Menu)localObject2).styleProperty());
/* 569 */         localMenuBarButton.getProperties().addListener(new MapChangeListener()
/*     */         {
/*     */           public void onChanged(MapChangeListener.Change<? extends Object, ? extends Object> paramAnonymousChange) {
/* 572 */             if ((paramAnonymousChange.wasAdded()) && ("autoHide".equals(paramAnonymousChange.getKey()))) {
/* 573 */               localMenuBarButton.getProperties().remove("autoHide");
/* 574 */               this.val$menu.hide();
/*     */             }
/*     */           }
/*     */         });
/* 578 */         localMenuBarButton.showingProperty().addListener(new ChangeListener()
/*     */         {
/*     */           public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 581 */             if (paramAnonymousBoolean2.booleanValue()) {
/* 582 */               if ((MenuBarSkin.this.openMenuButton != null) && (MenuBarSkin.this.openMenuButton != localMenuBarButton)) {
/* 583 */                 MenuBarSkin.this.openMenuButton.hide();
/*     */               }
/* 585 */               MenuBarSkin.this.openMenuButton = localMenuBarButton;
/* 586 */               MenuBarSkin.this.openMenu = this.val$menu;
/* 587 */               if (!this.val$menu.isShowing()) this.val$menu.show();
/*     */             }
/*     */           }
/*     */         });
/* 592 */         localMenuBarButton.setOnMousePressed(new EventHandler() {
/*     */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 594 */             MenuBarSkin.this.pendingDismiss = localMenuBarButton.isShowing();
/*     */ 
/* 597 */             if (localMenuBarButton.getScene().getWindow().isFocused()) {
/* 598 */               if (!MenuBarSkin.this.isMenuEmpty(this.val$menu)) {
/* 599 */                 MenuBarSkin.this.openMenu = this.val$menu;
/* 600 */                 MenuBarSkin.this.openMenu.show();
/*     */               } else {
/* 602 */                 MenuBarSkin.this.openMenu = null;
/*     */               }
/*     */ 
/* 605 */               MenuBarSkin.this.focusedMenuIndex = MenuBarSkin.this.getMenuBarButtonIndex(localMenuBarButton);
/*     */             }
/*     */           }
/*     */         });
/* 610 */         localMenuBarButton.setOnMouseReleased(new EventHandler()
/*     */         {
/*     */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 613 */             if ((localMenuBarButton.getScene().getWindow().isFocused()) && 
/* 614 */               (MenuBarSkin.this.pendingDismiss)) {
/* 615 */               MenuBarSkin.this.resetOpenMenu();
/*     */             }
/*     */ 
/* 619 */             MenuBarSkin.this.pendingDismiss = false;
/*     */           }
/*     */         });
/* 685 */         localMenuBarButton.setOnMouseEntered(new EventHandler()
/*     */         {
/*     */           public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 688 */             if ((localMenuBarButton.getScene() != null) && (localMenuBarButton.getScene().getWindow() != null) && (localMenuBarButton.getScene().getWindow().isFocused()))
/*     */             {
/* 690 */               if ((MenuBarSkin.this.openMenuButton != null) && (MenuBarSkin.this.openMenuButton != localMenuBarButton)) {
/* 691 */                 MenuBarSkin.this.openMenuButton.clearHover();
/* 692 */                 MenuBarSkin.this.openMenuButton = null;
/* 693 */                 MenuBarSkin.this.openMenuButton = localMenuBarButton;
/*     */               }
/* 695 */               if (MenuBarSkin.this.openMenu == null) return;
/* 696 */               MenuBarSkin.this.updateFocusedIndex();
/* 697 */               if (MenuBarSkin.this.openMenu.isShowing())
/*     */               {
/* 699 */                 MenuBarSkin.this.openMenu.hide();
/* 700 */                 if (!MenuBarSkin.this.isMenuEmpty(this.val$menu)) {
/* 701 */                   MenuBarSkin.this.openMenu = this.val$menu;
/* 702 */                   MenuBarSkin.this.updateFocusedIndex();
/* 703 */                   MenuBarSkin.this.openMenu.show();
/*     */                 } else {
/* 705 */                   MenuBarSkin.this.openMenu = null;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         });
/* 711 */         updateActionListeners((Menu)localObject2, true);
/*     */       } }
/* 713 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private boolean isMenuEmpty(Menu paramMenu)
/*     */   {
/* 727 */     boolean bool = true;
/* 728 */     for (MenuItem localMenuItem : paramMenu.getItems()) {
/* 729 */       if (localMenuItem.isVisible()) bool = false;
/*     */     }
/* 731 */     return bool;
/*     */   }
/*     */ 
/*     */   private void resetOpenMenu() {
/* 735 */     if (this.openMenu != null) {
/* 736 */       this.openMenu.hide();
/* 737 */       this.openMenu = null;
/* 738 */       this.openMenuButton = ((MenuBarButton)this.container.getChildren().get(this.focusedMenuIndex));
/* 739 */       this.openMenuButton.clearHover();
/* 740 */       this.openMenuButton = null;
/* 741 */       this.focusedMenuIndex = -1;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void unSelectMenus() {
/* 746 */     if (this.focusedMenuIndex == -1) return;
/* 747 */     clearMenuButtonHover();
/* 748 */     if (this.openMenu != null) {
/* 749 */       this.openMenu.hide();
/* 750 */       this.openMenu = null;
/*     */     }
/* 752 */     if (this.openMenuButton != null) {
/* 753 */       this.openMenuButton.clearHover();
/* 754 */       this.openMenuButton = null;
/*     */     }
/* 756 */     this.focusedMenuIndex = -1;
/*     */   }
/*     */ 
/*     */   private void selectNextMenu() {
/* 760 */     Menu localMenu = findNextSibling();
/* 761 */     if ((localMenu != null) && (this.focusedMenuIndex != -1)) {
/* 762 */       this.openMenuButton = ((MenuBarButton)this.container.getChildren().get(this.focusedMenuIndex));
/* 763 */       this.openMenuButton.setHover();
/* 764 */       this.openMenu = localMenu;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void selectPrevMenu() {
/* 769 */     Menu localMenu = findPreviousSibling();
/* 770 */     if ((localMenu != null) && (this.focusedMenuIndex != -1)) {
/* 771 */       this.openMenuButton = ((MenuBarButton)this.container.getChildren().get(this.focusedMenuIndex));
/* 772 */       this.openMenuButton.setHover();
/* 773 */       this.openMenu = localMenu;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showNextMenu() {
/* 778 */     Menu localMenu = findNextSibling();
/*     */ 
/* 780 */     if (this.openMenu != null) this.openMenu.hide();
/* 781 */     if (!isMenuEmpty(localMenu)) {
/* 782 */       this.openMenu = localMenu;
/* 783 */       this.openMenu.show();
/*     */     } else {
/* 785 */       this.openMenu = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void showPrevMenu() {
/* 790 */     Menu localMenu = findPreviousSibling();
/*     */ 
/* 792 */     if (this.openMenu != null) this.openMenu.hide();
/* 793 */     if (!isMenuEmpty(localMenu)) {
/* 794 */       this.openMenu = localMenu;
/* 795 */       this.openMenu.show();
/*     */     } else {
/* 797 */       this.openMenu = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isAnyMenuSelected() {
/* 802 */     if (this.container != null) {
/* 803 */       for (Node localNode : this.container.getChildren()) {
/* 804 */         if (((MenuButton)localNode).isFocused()) return true;
/*     */       }
/*     */     }
/* 807 */     return false;
/*     */   }
/*     */   private Menu findPreviousSibling() {
/* 810 */     if (this.focusedMenuIndex == -1) return null;
/* 811 */     if (this.focusedMenuIndex == 0)
/* 812 */       this.focusedMenuIndex = (this.container.getChildren().size() - 1);
/*     */     else {
/* 814 */       this.focusedMenuIndex -= 1;
/*     */     }
/*     */ 
/* 817 */     if (((Menu)((MenuBar)getSkinnable()).getMenus().get(this.focusedMenuIndex)).isDisable()) return findPreviousSibling();
/* 818 */     clearMenuButtonHover();
/* 819 */     return (Menu)((MenuBar)getSkinnable()).getMenus().get(this.focusedMenuIndex);
/*     */   }
/*     */ 
/*     */   private Menu findNextSibling() {
/* 823 */     if (this.focusedMenuIndex == -1) return null;
/* 824 */     if (this.focusedMenuIndex == this.container.getChildren().size() - 1)
/* 825 */       this.focusedMenuIndex = 0;
/*     */     else {
/* 827 */       this.focusedMenuIndex += 1;
/*     */     }
/*     */ 
/* 830 */     if (((Menu)((MenuBar)getSkinnable()).getMenus().get(this.focusedMenuIndex)).isDisable()) return findNextSibling();
/* 831 */     clearMenuButtonHover();
/* 832 */     return (Menu)((MenuBar)getSkinnable()).getMenus().get(this.focusedMenuIndex);
/*     */   }
/*     */ 
/*     */   private void updateFocusedIndex() {
/* 836 */     int i = 0;
/* 837 */     for (Node localNode : this.container.getChildren()) {
/* 838 */       if (localNode.isHover()) {
/* 839 */         this.focusedMenuIndex = i;
/* 840 */         return;
/*     */       }
/* 842 */       i++;
/*     */     }
/* 844 */     this.focusedMenuIndex = -1;
/*     */   }
/*     */ 
/*     */   private void clearMenuButtonHover() {
/* 848 */     for (Node localNode : this.container.getChildren())
/* 849 */       if (localNode.isHover()) {
/* 850 */         ((MenuBarButton)localNode).clearHover();
/* 851 */         return;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void onTraverse(Node paramNode, Bounds paramBounds)
/*     */   {
/* 858 */     if (this.direction.equals(Direction.NEXT)) {
/* 859 */       if (this.openMenu != null) this.openMenu.hide();
/* 860 */       this.focusedMenuIndex = 0;
/* 861 */       new TraversalEngine(getSkinnable(), false).trav(getSkinnable(), Direction.NEXT);
/* 862 */     } else if (!this.direction.equals(Direction.DOWN));
/*     */   }
/*     */ 
/*     */   public Insets getInsets()
/*     */   {
/* 902 */     if (this.container.getChildren().size() == 0) {
/* 903 */       return new Insets(0.0D, 0.0D, 0.0D, 0.0D);
/*     */     }
/* 905 */     return super.getInsets();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 915 */     double d1 = getInsets().getLeft();
/* 916 */     double d2 = getInsets().getTop();
/* 917 */     double d3 = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/* 918 */     double d4 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/* 920 */     this.container.resizeRelocate(d1, d2, d3, d4);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble) {
/* 924 */     return this.container.minWidth(paramDouble) + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 928 */     return this.container.prefWidth(paramDouble) + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/* 932 */     return this.container.minHeight(paramDouble) + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 936 */     return this.container.prefHeight(paramDouble) + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble)
/*     */   {
/* 941 */     return ((MenuBar)getSkinnable()).prefHeight(-1.0D);
/*     */   }
/*     */ 
/*     */   class MenuBarButton extends MenuButton
/*     */   {
/*     */     private ChangeListener<Boolean> menuListener;
/*     */     private Menu menu;
/*     */ 
/*     */     public MenuBarButton()
/*     */     {
/*     */     }
/*     */ 
/*     */     public MenuBarButton(String arg2)
/*     */     {
/* 876 */       super();
/*     */     }
/*     */ 
/*     */     public MenuBarButton(String paramNode, Node arg3) {
/* 880 */       super(localNode);
/*     */     }
/*     */ 
/*     */     private void clearHover() {
/* 884 */       setHover(false);
/*     */     }
/*     */ 
/*     */     private void setHover() {
/* 888 */       setHover(true);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.MenuBarSkin
 * JD-Core Version:    0.6.2
 */