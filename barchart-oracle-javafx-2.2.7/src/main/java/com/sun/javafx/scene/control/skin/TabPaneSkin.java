/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.css.StyleManager;
/*      */ import com.sun.javafx.scene.control.behavior.TabPaneBehavior;
/*      */ import com.sun.javafx.scene.traversal.Direction;
/*      */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*      */ import com.sun.javafx.scene.traversal.TraverseListener;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import javafx.animation.Interpolator;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.SimpleDoubleProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Point2D;
/*      */ import javafx.geometry.Pos;
/*      */ import javafx.geometry.Side;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.control.ContextMenu;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.MenuItem;
/*      */ import javafx.scene.control.RadioMenuItem;
/*      */ import javafx.scene.control.SingleSelectionModel;
/*      */ import javafx.scene.control.Tab;
/*      */ import javafx.scene.control.TabPane;
/*      */ import javafx.scene.control.TabPane.TabClosingPolicy;
/*      */ import javafx.scene.control.ToggleGroup;
/*      */ import javafx.scene.control.Tooltip;
/*      */ import javafx.scene.effect.DropShadow;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.input.ContextMenuEvent;
/*      */ import javafx.scene.input.MouseButton;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.SwipeEvent;
/*      */ import javafx.scene.layout.Pane;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.scene.transform.Rotate;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public class TabPaneSkin extends SkinBase<TabPane, TabPaneBehavior>
/*      */ {
/*      */   private static final double ANIMATION_SPEED = 300.0D;
/*      */   private static final int SPACER = 10;
/*      */   private TabHeaderArea tabHeaderArea;
/*      */   private ObservableList<TabContentRegion> tabContentRegions;
/*      */   private Rectangle clipRect;
/*      */   private Rectangle tabHeaderAreaClipRect;
/*  130 */   boolean focusTraversable = true;
/*      */   private Tab selectedTab;
/*      */   private Tab previousSelectedTab;
/*      */   private boolean isSelectingTab;
/*  198 */   private Map<Tab, Timeline> closedTab = new HashMap();
/*      */ 
/*  356 */   private double maxw = 0.0D;
/*      */ 
/*  367 */   private double maxh = 0.0D;
/*      */ 
/*  871 */   static int CLOSE_BTN_SIZE = 16;
/*      */ 
/* 1246 */   private static final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*      */ 
/* 1248 */   private static final long TOP_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("top");
/*      */ 
/* 1250 */   private static final long BOTTOM_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("bottom");
/*      */ 
/* 1252 */   private static final long LEFT_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("left");
/*      */ 
/* 1254 */   private static final long RIGHT_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("right");
/*      */ 
/* 1256 */   private static final long DISABLED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("disabled");
/*      */ 
/*      */   private static int getRotation(Side paramSide)
/*      */   {
/*   88 */     switch (4.$SwitchMap$javafx$geometry$Side[paramSide.ordinal()]) {
/*      */     case 1:
/*   90 */       return 0;
/*      */     case 2:
/*   92 */       return 180;
/*      */     case 3:
/*   94 */       return -90;
/*      */     case 4:
/*   96 */       return 90;
/*      */     }
/*   98 */     return 0;
/*      */   }
/*      */ 
/*      */   private static Node clone(Node paramNode)
/*      */   {
/*  107 */     if (paramNode == null)
/*  108 */       return null;
/*      */     Object localObject1;
/*      */     Object localObject2;
/*  110 */     if ((paramNode instanceof ImageView)) {
/*  111 */       localObject1 = (ImageView)paramNode;
/*  112 */       localObject2 = new ImageView();
/*  113 */       ((ImageView)localObject2).setImage(((ImageView)localObject1).getImage());
/*  114 */       return localObject2;
/*      */     }
/*  116 */     if ((paramNode instanceof Label)) {
/*  117 */       localObject1 = (Label)paramNode;
/*  118 */       localObject2 = new Label(((Label)localObject1).getText(), ((Label)localObject1).getGraphic());
/*  119 */       return localObject2;
/*      */     }
/*  121 */     return null;
/*      */   }
/*      */ 
/*      */   public TabPaneSkin(TabPane paramTabPane)
/*      */   {
/*  136 */     super(paramTabPane, new TabPaneBehavior(paramTabPane));
/*      */ 
/*  138 */     this.clipRect = new Rectangle();
/*  139 */     setClip(this.clipRect);
/*      */ 
/*  141 */     this.tabContentRegions = FXCollections.observableArrayList();
/*      */ 
/*  143 */     for (Tab localTab : ((TabPane)getSkinnable()).getTabs()) {
/*  144 */       addTabContent(localTab);
/*      */     }
/*      */ 
/*  147 */     this.tabHeaderAreaClipRect = new Rectangle();
/*  148 */     this.tabHeaderArea = new TabHeaderArea();
/*  149 */     this.tabHeaderArea.setClip(this.tabHeaderAreaClipRect);
/*  150 */     getChildren().add(this.tabHeaderArea);
/*  151 */     if (((TabPane)getSkinnable()).getTabs().size() == 0) {
/*  152 */       this.tabHeaderArea.setVisible(false);
/*      */     }
/*      */ 
/*  155 */     initializeTabListener();
/*      */ 
/*  157 */     registerChangeListener(paramTabPane.getSelectionModel().selectedItemProperty(), "SELECTED_TAB");
/*  158 */     registerChangeListener(paramTabPane.sideProperty(), "SIDE");
/*      */ 
/*  160 */     this.previousSelectedTab = null;
/*  161 */     this.selectedTab = ((Tab)((TabPane)getSkinnable()).getSelectionModel().getSelectedItem());
/*      */ 
/*  163 */     if ((this.selectedTab == null) && (((TabPane)getSkinnable()).getSelectionModel().getSelectedIndex() != -1)) {
/*  164 */       ((TabPane)getSkinnable()).getSelectionModel().select(((TabPane)getSkinnable()).getSelectionModel().getSelectedIndex());
/*  165 */       this.selectedTab = ((Tab)((TabPane)getSkinnable()).getSelectionModel().getSelectedItem());
/*      */     }
/*  167 */     if (this.selectedTab == null)
/*      */     {
/*  169 */       ((TabPane)getSkinnable()).getSelectionModel().selectFirst();
/*      */     }
/*  171 */     this.selectedTab = ((Tab)((TabPane)getSkinnable()).getSelectionModel().getSelectedItem());
/*  172 */     this.isSelectingTab = false;
/*      */ 
/*  174 */     initializeSwipeHandlers();
/*      */   }
/*      */ 
/*      */   public StackPane getSelectedTabContentRegion() {
/*  178 */     for (TabContentRegion localTabContentRegion : this.tabContentRegions) {
/*  179 */       if (localTabContentRegion.getTab().equals(this.selectedTab)) {
/*  180 */         return localTabContentRegion;
/*      */       }
/*      */     }
/*  183 */     return null;
/*      */   }
/*      */ 
/*      */   protected void handleControlPropertyChanged(String paramString) {
/*  187 */     super.handleControlPropertyChanged(paramString);
/*  188 */     if (paramString == "SELECTED_TAB") {
/*  189 */       this.isSelectingTab = true;
/*  190 */       this.previousSelectedTab = this.selectedTab;
/*  191 */       this.selectedTab = ((Tab)((TabPane)getSkinnable()).getSelectionModel().getSelectedItem());
/*  192 */       requestLayout();
/*  193 */     } else if (paramString == "SIDE") {
/*  194 */       updateTabPosition();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void initializeTabListener()
/*      */   {
/*  201 */     ((TabPane)getSkinnable()).getTabs().addListener(new ListChangeListener()
/*      */     {
/*      */       public void onChanged(ListChangeListener.Change<? extends Tab> paramAnonymousChange)
/*      */       {
/*      */         Object localObject1;
/*      */         Object localObject2;
/*      */         Timeline localTimeline;
/*      */         int i;
/*  203 */         while (paramAnonymousChange.next()) {
/*  204 */           for (Iterator localIterator1 = paramAnonymousChange.getRemoved().iterator(); localIterator1.hasNext(); ) { localObject1 = (Tab)localIterator1.next();
/*      */ 
/*  206 */             localObject2 = TabPaneSkin.this.tabHeaderArea.getTabHeaderSkin((Tab)localObject1);
/*  207 */             localTimeline = null;
/*  208 */             if (localObject2 != null) {
/*  209 */               ((TabPaneSkin.TabHeaderSkin)localObject2).animating = true;
/*  210 */               localTimeline = TabPaneSkin.this.createTimeline((TabPaneSkin.TabHeaderSkin)localObject2, Duration.millis(450.0D), 0.0D, new EventHandler()
/*      */               {
/*      */                 public void handle(ActionEvent paramAnonymous2ActionEvent)
/*      */                 {
/*  214 */                   TabPaneSkin.this.removeTab(this.val$tab);
/*  215 */                   TabPaneSkin.this.closedTab.remove(this.val$tab);
/*  216 */                   if (((TabPane)TabPaneSkin.this.getSkinnable()).getTabs().isEmpty())
/*  217 */                     TabPaneSkin.this.tabHeaderArea.setVisible(false);
/*      */                 }
/*      */               });
/*  221 */               localTimeline.play();
/*      */             }
/*  223 */             TabPaneSkin.this.closedTab.put(localObject1, localTimeline);
/*      */           }
/*      */ 
/*  226 */           i = 0;
/*  227 */           for (localObject1 = paramAnonymousChange.getAddedSubList().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Tab)((Iterator)localObject1).next();
/*      */ 
/*  229 */             localTimeline = (Timeline)TabPaneSkin.this.closedTab.get(localObject2);
/*  230 */             if (localTimeline != null) {
/*  231 */               localTimeline.stop();
/*  232 */               Iterator localIterator2 = TabPaneSkin.this.closedTab.keySet().iterator();
/*  233 */               while (localIterator2.hasNext()) {
/*  234 */                 localObject3 = (Tab)localIterator2.next();
/*  235 */                 if (localObject2.equals(localObject3)) {
/*  236 */                   TabPaneSkin.this.removeTab((Tab)localObject3);
/*  237 */                   localIterator2.remove();
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/*  242 */             if (!TabPaneSkin.this.tabHeaderArea.isVisible()) {
/*  243 */               TabPaneSkin.this.tabHeaderArea.setVisible(true);
/*      */             }
/*  245 */             int j = paramAnonymousChange.getFrom() + i++;
/*  246 */             TabPaneSkin.this.tabHeaderArea.addTab((Tab)localObject2, j, false);
/*  247 */             TabPaneSkin.this.addTabContent((Tab)localObject2);
/*  248 */             Object localObject3 = TabPaneSkin.this.tabHeaderArea.getTabHeaderSkin((Tab)localObject2);
/*  249 */             if (localObject3 != null)
/*  250 */               ((TabPaneSkin.TabHeaderSkin)localObject3).animateNewTab = new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/*  254 */                   double d = TabPaneSkin.this.snapSize(this.val$tabRegion.prefWidth(-1.0D));
/*  255 */                   this.val$tabRegion.animating = true;
/*  256 */                   this.val$tabRegion.prefWidth.set(0.0D);
/*  257 */                   this.val$tabRegion.setVisible(true);
/*  258 */                   TabPaneSkin.this.createTimeline(this.val$tabRegion, Duration.millis(300.0D), d, new EventHandler()
/*      */                   {
/*      */                     public void handle(ActionEvent paramAnonymous3ActionEvent)
/*      */                     {
/*  262 */                       TabPaneSkin.1.2.this.val$tabRegion.animating = false;
/*  263 */                       TabPaneSkin.1.2.this.val$tabRegion.inner.requestLayout();
/*      */                     }
/*      */                   }).play();
/*      */                 }
/*      */               };
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void addTabContent(Tab paramTab)
/*      */   {
/*  276 */     TabContentRegion localTabContentRegion = new TabContentRegion(paramTab);
/*  277 */     localTabContentRegion.setClip(new Rectangle());
/*  278 */     this.tabContentRegions.add(localTabContentRegion);
/*      */ 
/*  280 */     getChildren().add(0, localTabContentRegion);
/*      */   }
/*      */ 
/*      */   private void removeTabContent(Tab paramTab) {
/*  284 */     for (TabContentRegion localTabContentRegion : this.tabContentRegions)
/*  285 */       if (localTabContentRegion.getTab().equals(paramTab)) {
/*  286 */         localTabContentRegion.removeListeners(paramTab);
/*  287 */         getChildren().remove(localTabContentRegion);
/*  288 */         this.tabContentRegions.remove(localTabContentRegion);
/*  289 */         break;
/*      */       }
/*      */   }
/*      */ 
/*      */   private void removeTab(Tab paramTab)
/*      */   {
/*  295 */     TabHeaderSkin localTabHeaderSkin = this.tabHeaderArea.getTabHeaderSkin(paramTab);
/*  296 */     this.tabHeaderArea.removeTab(paramTab);
/*  297 */     removeTabContent(paramTab);
/*  298 */     localTabHeaderSkin.animating = false;
/*  299 */     this.tabHeaderArea.requestLayout();
/*  300 */     paramTab = null;
/*      */   }
/*      */ 
/*      */   private void updateTabPosition() {
/*  304 */     this.tabHeaderArea.setScrollOffset(0.0D);
/*  305 */     impl_reapplyCSS();
/*  306 */     requestLayout();
/*      */   }
/*      */ 
/*      */   private Timeline createTimeline(TabHeaderSkin paramTabHeaderSkin, Duration paramDuration, double paramDouble, EventHandler<ActionEvent> paramEventHandler) {
/*  310 */     Timeline localTimeline = new Timeline();
/*  311 */     localTimeline.setCycleCount(1);
/*      */ 
/*  313 */     KeyValue localKeyValue = new KeyValue(paramTabHeaderSkin.prefWidth, Double.valueOf(paramDouble), Interpolator.LINEAR);
/*      */ 
/*  315 */     localTimeline.getKeyFrames().clear();
/*  316 */     localTimeline.getKeyFrames().add(new KeyFrame(paramDuration, paramEventHandler, new KeyValue[] { localKeyValue }));
/*  317 */     return localTimeline;
/*      */   }
/*      */ 
/*      */   private boolean isHorizontal() {
/*  321 */     Side localSide = ((TabPane)getSkinnable()).getSide();
/*  322 */     return (Side.TOP.equals(localSide)) || (Side.BOTTOM.equals(localSide));
/*      */   }
/*      */ 
/*      */   private void initializeSwipeHandlers() {
/*  326 */     if (PlatformUtil.isEmbedded()) {
/*  327 */       setOnSwipeLeft(new EventHandler() {
/*      */         public void handle(SwipeEvent paramAnonymousSwipeEvent) {
/*  329 */           ((TabPaneBehavior)TabPaneSkin.this.getBehavior()).selectNextTab();
/*      */         }
/*      */       });
/*  333 */       setOnSwipeRight(new EventHandler() {
/*      */         public void handle(SwipeEvent paramAnonymousSwipeEvent) {
/*  335 */           ((TabPaneBehavior)TabPaneSkin.this.getBehavior()).selectPreviousTab();
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean isFloatingStyleClass()
/*      */   {
/*  343 */     return ((TabPane)getSkinnable()).getStyleClass().contains("floating");
/*      */   }
/*      */ 
/*      */   protected void setWidth(double paramDouble) {
/*  347 */     super.setWidth(paramDouble);
/*  348 */     this.clipRect.setWidth(paramDouble);
/*      */   }
/*      */ 
/*      */   protected void setHeight(double paramDouble) {
/*  352 */     super.setHeight(paramDouble);
/*  353 */     this.clipRect.setHeight(paramDouble);
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  359 */     for (TabContentRegion localTabContentRegion : this.tabContentRegions) {
/*  360 */       this.maxw = Math.max(this.maxw, snapSize(localTabContentRegion.prefWidth(-1.0D)));
/*      */     }
/*  362 */     double d = isHorizontal() ? this.maxw : this.maxw + snapSize(this.tabHeaderArea.prefHeight(-1.0D));
/*      */ 
/*  364 */     return snapSize(d) + snapSize(getInsets().getRight()) + snapSize(getInsets().getLeft());
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble)
/*      */   {
/*  370 */     for (TabContentRegion localTabContentRegion : this.tabContentRegions) {
/*  371 */       this.maxh = Math.max(this.maxh, snapSize(localTabContentRegion.prefHeight(-1.0D)));
/*      */     }
/*  373 */     double d = isHorizontal() ? this.maxh + snapSize(this.tabHeaderArea.prefHeight(-1.0D)) : this.maxh;
/*      */ 
/*  375 */     return snapSize(d) + snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */   }
/*      */ 
/*      */   public double getBaselineOffset() {
/*  379 */     return this.tabHeaderArea.getBaselineOffset() + this.tabHeaderArea.getLayoutY();
/*      */   }
/*      */ 
/*      */   protected void layoutChildren() {
/*  383 */     TabPane localTabPane = (TabPane)getSkinnable();
/*  384 */     Side localSide = localTabPane.getSide();
/*  385 */     Insets localInsets = getInsets();
/*      */ 
/*  387 */     double d1 = snapSize(getWidth()) - snapSize(localInsets.getLeft()) - snapSize(localInsets.getRight());
/*  388 */     double d2 = snapSize(getHeight()) - snapSize(localInsets.getTop()) - snapSize(localInsets.getBottom());
/*  389 */     double d3 = snapSize(localInsets.getLeft());
/*  390 */     double d4 = snapSize(localInsets.getTop());
/*      */ 
/*  392 */     double d5 = snapSize(this.tabHeaderArea.prefHeight(-1.0D));
/*  393 */     double d6 = localSide.equals(Side.RIGHT) ? d3 + d1 - d5 : d3;
/*  394 */     double d7 = localSide.equals(Side.BOTTOM) ? d4 + d2 - d5 : d4;
/*      */ 
/*  396 */     if (localSide.equals(Side.TOP)) {
/*  397 */       this.tabHeaderArea.resize(d1, d5);
/*  398 */       this.tabHeaderArea.relocate(d6, d7);
/*  399 */       this.tabHeaderArea.getTransforms().clear();
/*  400 */       this.tabHeaderArea.getTransforms().add(new Rotate(getRotation(Side.TOP)));
/*  401 */     } else if (localSide.equals(Side.BOTTOM)) {
/*  402 */       this.tabHeaderArea.resize(d1, d5);
/*  403 */       this.tabHeaderArea.relocate(d1, d7 - d5);
/*  404 */       this.tabHeaderArea.getTransforms().clear();
/*  405 */       this.tabHeaderArea.getTransforms().add(new Rotate(getRotation(Side.BOTTOM), 0.0D, d5));
/*  406 */     } else if (localSide.equals(Side.LEFT)) {
/*  407 */       this.tabHeaderArea.resize(d2, d5);
/*  408 */       this.tabHeaderArea.relocate(d6 + d5, d2 - d5);
/*  409 */       this.tabHeaderArea.getTransforms().clear();
/*  410 */       this.tabHeaderArea.getTransforms().add(new Rotate(getRotation(Side.LEFT), 0.0D, d5));
/*  411 */     } else if (localSide.equals(Side.RIGHT)) {
/*  412 */       this.tabHeaderArea.resize(d2, d5);
/*  413 */       this.tabHeaderArea.relocate(d6, d4 - d5);
/*  414 */       this.tabHeaderArea.getTransforms().clear();
/*  415 */       this.tabHeaderArea.getTransforms().add(new Rotate(getRotation(Side.RIGHT), 0.0D, d5));
/*      */     }
/*      */ 
/*  418 */     this.tabHeaderAreaClipRect.setX(0.0D);
/*  419 */     this.tabHeaderAreaClipRect.setY(0.0D);
/*  420 */     if (isHorizontal())
/*  421 */       this.tabHeaderAreaClipRect.setWidth(d1);
/*      */     else {
/*  423 */       this.tabHeaderAreaClipRect.setWidth(d2);
/*      */     }
/*  425 */     this.tabHeaderAreaClipRect.setHeight(d5);
/*      */ 
/*  431 */     double d8 = 0.0D;
/*  432 */     double d9 = 0.0D;
/*      */ 
/*  434 */     if (localSide.equals(Side.TOP)) {
/*  435 */       d8 = d3;
/*  436 */       d9 = d4 + d5;
/*  437 */       if (isFloatingStyleClass())
/*      */       {
/*  439 */         d9 -= 1.0D;
/*      */       }
/*  441 */     } else if (localSide.equals(Side.BOTTOM)) {
/*  442 */       d8 = d3;
/*  443 */       d9 = d4;
/*  444 */       if (isFloatingStyleClass())
/*      */       {
/*  446 */         d9 = 1.0D;
/*      */       }
/*  448 */     } else if (localSide.equals(Side.LEFT)) {
/*  449 */       d8 = d3 + d5;
/*  450 */       d9 = d4;
/*  451 */       if (isFloatingStyleClass())
/*      */       {
/*  453 */         d8 -= 1.0D;
/*      */       }
/*  455 */     } else if (localSide.equals(Side.RIGHT)) {
/*  456 */       d8 = d3;
/*  457 */       d9 = d4;
/*  458 */       if (isFloatingStyleClass())
/*      */       {
/*  460 */         d8 = 1.0D;
/*      */       }
/*      */     }
/*      */ 
/*  464 */     double d10 = d1 - (isHorizontal() ? 0.0D : d5);
/*  465 */     double d11 = d2 - (isHorizontal() ? d5 : 0.0D);
/*  466 */     for (TabContentRegion localTabContentRegion : this.tabContentRegions)
/*      */     {
/*      */       Node localNode;
/*  467 */       if (localTabContentRegion.getTab().equals(this.selectedTab)) {
/*  468 */         localTabContentRegion.setAlignment(Pos.TOP_LEFT);
/*  469 */         if (localTabContentRegion.getClip() != null) {
/*  470 */           ((Rectangle)localTabContentRegion.getClip()).setWidth(d10);
/*  471 */           ((Rectangle)localTabContentRegion.getClip()).setHeight(d11);
/*      */         }
/*  473 */         localTabContentRegion.resize(d10, d11);
/*  474 */         localTabContentRegion.relocate(d8, d9);
/*  475 */         localNode = localTabContentRegion.getTab().getContent();
/*  476 */         if (localNode != null) localNode.setVisible(true); 
/*      */       }
/*  478 */       else { localNode = localTabContentRegion.getTab().getContent();
/*  479 */         if (localNode != null) localNode.setVisible(false);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class TabMenuItem extends RadioMenuItem
/*      */   {
/*      */     Tab tab;
/*      */ 
/*      */     public TabMenuItem(Tab arg2)
/*      */     {
/* 1649 */       super(TabPaneSkin.clone(localTab.getGraphic()));
/* 1650 */       this.tab = localTab;
/* 1651 */       setDisable(localTab.isDisable());
/* 1652 */       localTab.disableProperty().addListener(new InvalidationListener()
/*      */       {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1655 */           TabPaneSkin.TabMenuItem.this.setDisable(localTab.isDisable());
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public Tab getTab() {
/* 1661 */       return this.tab;
/*      */     }
/*      */   }
/*      */ 
/*      */   class TabControlButtons extends StackPane
/*      */   {
/*      */     private StackPane inner;
/*      */     private StackPane downArrow;
/*      */     private Pane downArrowBtn;
/*      */     private boolean showControlButtons;
/*      */     private ContextMenu popup;
/*      */     private boolean showTabsMenu;
/*      */     private final DoubleProperty controlTabHeight;
/*      */     private boolean animationLock;
/*      */     double maxArrowWidth;
/*      */     double innerPrefWidth;
/*      */     private double prefWidth;
/*      */ 
/*      */     public TabControlButtons()
/*      */     {
/* 1478 */       this.showTabsMenu = false;
/*      */ 
/* 1496 */       this.controlTabHeight = new SimpleDoubleProperty(this, "controlTabHeight");
/*      */ 
/* 1498 */       this.controlTabHeight.addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1500 */           TabPaneSkin.TabControlButtons.this.requestLayout();
/*      */         }
/*      */       });
/* 1513 */       this.animationLock = false;
/*      */ 
/* 1357 */       getStyleClass().setAll(new String[] { "control-buttons-tab" });
/*      */ 
/* 1359 */       TabPane localTabPane = (TabPane)TabPaneSkin.this.getSkinnable();
/*      */ 
/* 1361 */       this.downArrowBtn = new Pane();
/* 1362 */       this.downArrowBtn.getStyleClass().setAll(new String[] { "tab-down-button" });
/* 1363 */       this.downArrowBtn.setVisible(isShowTabsMenu());
/* 1364 */       this.downArrow = new StackPane();
/* 1365 */       this.downArrow.setManaged(false);
/* 1366 */       this.downArrow.getStyleClass().setAll(new String[] { "arrow" });
/* 1367 */       this.downArrow.setRotate(localTabPane.getSide().equals(Side.BOTTOM) ? 180.0D : 0.0D);
/* 1368 */       this.downArrowBtn.getChildren().add(this.downArrow);
/* 1369 */       this.downArrowBtn.setOnMouseClicked(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1371 */           TabPaneSkin.TabControlButtons.this.showPopupMenu();
/*      */         }
/*      */       });
/* 1375 */       setupPopupMenu();
/*      */ 
/* 1377 */       this.inner = new StackPane() {
/*      */         private double getArrowBtnWidth() {
/* 1379 */           if (TabPaneSkin.TabControlButtons.this.animationLock) return TabPaneSkin.TabControlButtons.this.maxArrowWidth;
/* 1380 */           if (TabPaneSkin.TabControlButtons.this.isShowTabsMenu()) {
/* 1381 */             TabPaneSkin.TabControlButtons.this.maxArrowWidth = Math.max(TabPaneSkin.TabControlButtons.this.maxArrowWidth, snapSize(TabPaneSkin.TabControlButtons.this.downArrow.prefWidth(getHeight())) + snapSize(TabPaneSkin.TabControlButtons.this.downArrowBtn.prefWidth(getHeight())));
/*      */           }
/* 1383 */           return TabPaneSkin.TabControlButtons.this.maxArrowWidth;
/*      */         }
/*      */ 
/*      */         protected double computePrefWidth(double paramAnonymousDouble) {
/* 1387 */           if (TabPaneSkin.TabControlButtons.this.animationLock) return TabPaneSkin.TabControlButtons.this.innerPrefWidth;
/* 1388 */           TabPaneSkin.TabControlButtons.this.innerPrefWidth = getActualPrefWidth();
/* 1389 */           return TabPaneSkin.TabControlButtons.this.innerPrefWidth;
/*      */         }
/*      */ 
/*      */         public double getActualPrefWidth()
/*      */         {
/* 1394 */           double d2 = getArrowBtnWidth();
/* 1395 */           double d1 = 0.0D;
/* 1396 */           if (TabPaneSkin.TabControlButtons.this.isShowTabsMenu()) {
/* 1397 */             d1 += d2;
/*      */           }
/* 1399 */           if (d1 > 0.0D) {
/* 1400 */             d1 += snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight());
/*      */           }
/* 1402 */           return d1;
/*      */         }
/*      */ 
/*      */         protected double computePrefHeight(double paramAnonymousDouble) {
/* 1406 */           double d = 0.0D;
/* 1407 */           if (TabPaneSkin.TabControlButtons.this.isShowTabsMenu()) {
/* 1408 */             d = Math.max(d, snapSize(TabPaneSkin.TabControlButtons.this.downArrowBtn.prefHeight(paramAnonymousDouble)));
/*      */           }
/* 1410 */           if (d > 0.0D) {
/* 1411 */             d += snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */           }
/* 1413 */           return d;
/*      */         }
/*      */ 
/*      */         protected void layoutChildren() {
/* 1417 */           Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/* 1418 */           double d1 = 0.0D;
/*      */ 
/* 1420 */           double d2 = snapSize(getInsets().getTop());
/* 1421 */           double d3 = snapSize(getHeight()) - snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */ 
/* 1424 */           if ((localSide.equals(Side.BOTTOM)) || (localSide.equals(Side.LEFT)))
/* 1425 */             d1 += positionTabsMenu(d1, d2, d3, true);
/*      */           else
/* 1427 */             d1 += positionTabsMenu(d1, d2, d3, false);
/*      */         }
/*      */ 
/*      */         private double positionTabsMenu(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, boolean paramAnonymousBoolean)
/*      */         {
/* 1432 */           double d = paramAnonymousDouble1;
/* 1433 */           if (TabPaneSkin.TabControlButtons.this.isShowTabsMenu())
/*      */           {
/* 1435 */             positionArrow(TabPaneSkin.TabControlButtons.this.downArrowBtn, TabPaneSkin.TabControlButtons.this.downArrow, d, paramAnonymousDouble2, TabPaneSkin.TabControlButtons.this.maxArrowWidth, paramAnonymousDouble3);
/* 1436 */             d += TabPaneSkin.TabControlButtons.this.maxArrowWidth;
/*      */           }
/* 1438 */           return d;
/*      */         }
/*      */ 
/*      */         private void positionArrow(Pane paramAnonymousPane, StackPane paramAnonymousStackPane, double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4) {
/* 1442 */           paramAnonymousPane.resize(paramAnonymousDouble3, paramAnonymousDouble4);
/* 1443 */           positionInArea(paramAnonymousPane, paramAnonymousDouble1, paramAnonymousDouble2, paramAnonymousDouble3, paramAnonymousDouble4, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */ 
/* 1446 */           double d1 = snapSize(paramAnonymousStackPane.prefWidth(-1.0D));
/* 1447 */           double d2 = snapSize(paramAnonymousStackPane.prefHeight(-1.0D));
/* 1448 */           paramAnonymousStackPane.resize(d1, d2);
/* 1449 */           positionInArea(paramAnonymousStackPane, snapSize(paramAnonymousPane.getInsets().getLeft()), snapSize(paramAnonymousPane.getInsets().getTop()), paramAnonymousDouble3 - snapSize(paramAnonymousPane.getInsets().getLeft()) - snapSize(paramAnonymousPane.getInsets().getRight()), paramAnonymousDouble4 - snapSize(paramAnonymousPane.getInsets().getTop()) - snapSize(paramAnonymousPane.getInsets().getBottom()), 0.0D, HPos.CENTER, VPos.CENTER);
/*      */         }
/*      */       };
/* 1455 */       this.inner.getChildren().add(this.downArrowBtn);
/*      */ 
/* 1457 */       getChildren().add(this.inner);
/*      */ 
/* 1459 */       localTabPane.sideProperty().addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1461 */           Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/* 1462 */           TabPaneSkin.TabControlButtons.this.downArrow.setRotate(localSide.equals(Side.BOTTOM) ? 180.0D : 0.0D);
/*      */         }
/*      */       });
/* 1465 */       localTabPane.getTabs().addListener(new ListChangeListener() {
/*      */         public void onChanged(ListChangeListener.Change<? extends Tab> paramAnonymousChange) {
/* 1467 */           TabPaneSkin.TabControlButtons.this.setupPopupMenu();
/*      */         }
/*      */       });
/* 1470 */       this.showControlButtons = false;
/* 1471 */       if (isShowTabsMenu()) {
/* 1472 */         this.showControlButtons = true;
/* 1473 */         requestLayout();
/*      */       }
/* 1475 */       getProperties().put(ContextMenu.class, this.popup);
/*      */     }
/*      */ 
/*      */     private void showTabsMenu(boolean paramBoolean)
/*      */     {
/* 1481 */       if ((paramBoolean) && (!this.showTabsMenu)) {
/* 1482 */         this.downArrowBtn.setVisible(true);
/* 1483 */         this.showControlButtons = true;
/* 1484 */         this.inner.requestLayout();
/* 1485 */         TabPaneSkin.this.tabHeaderArea.requestLayout();
/* 1486 */       } else if ((!paramBoolean) && (this.showTabsMenu)) {
/* 1487 */         hideControlButtons();
/*      */       }
/* 1489 */       this.showTabsMenu = paramBoolean;
/*      */     }
/*      */ 
/*      */     private boolean isShowTabsMenu() {
/* 1493 */       return this.showTabsMenu;
/*      */     }
/*      */ 
/*      */     public double getControlTabHeight()
/*      */     {
/* 1507 */       return this.controlTabHeight.get();
/*      */     }
/*      */     public void setControlTabHeight(double paramDouble) {
/* 1510 */       this.controlTabHeight.set(paramDouble);
/*      */     }
/*      */ 
/*      */     private void setAnimationLock(boolean paramBoolean)
/*      */     {
/* 1515 */       this.animationLock = paramBoolean;
/* 1516 */       TabPaneSkin.this.tabHeaderArea.requestLayout();
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble)
/*      */     {
/* 1530 */       if (this.animationLock) {
/* 1531 */         return this.prefWidth;
/*      */       }
/* 1533 */       this.prefWidth = getActualPrefWidth(paramDouble);
/* 1534 */       return this.prefWidth;
/*      */     }
/*      */ 
/*      */     private double getActualPrefWidth(double paramDouble) {
/* 1538 */       double d = snapSize(this.inner.prefWidth(paramDouble));
/* 1539 */       if (d > 0.0D) {
/* 1540 */         d += snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight());
/*      */       }
/* 1542 */       return d;
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble) {
/* 1546 */       return Math.max(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMinHeight(), snapSize(this.inner.prefHeight(paramDouble))) + snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */     }
/*      */ 
/*      */     protected void layoutChildren()
/*      */     {
/* 1551 */       double d1 = snapSize(getInsets().getLeft());
/* 1552 */       double d2 = snapSize(getInsets().getTop());
/* 1553 */       double d3 = snapSize(getWidth()) - snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight());
/* 1554 */       double d4 = snapSize(getHeight()) - snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */ 
/* 1556 */       if (this.showControlButtons) {
/* 1557 */         showControlButtons();
/* 1558 */         this.showControlButtons = false;
/*      */       }
/*      */ 
/* 1561 */       this.inner.resize(d3, d4);
/* 1562 */       positionInArea(this.inner, d1, d2, d3, d4, 0.0D, HPos.CENTER, VPos.BOTTOM);
/*      */     }
/*      */ 
/*      */     private void showControlButtons() {
/* 1566 */       double d = snapSize(prefHeight(-1.0D));
/* 1567 */       Timeline localTimeline = new Timeline();
/* 1568 */       KeyValue localKeyValue = new KeyValue(this.controlTabHeight, Double.valueOf(d), Interpolator.EASE_OUT);
/*      */ 
/* 1570 */       setVisible(true);
/* 1571 */       localTimeline.getKeyFrames().clear();
/* 1572 */       localTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1574 */           if (TabPaneSkin.TabControlButtons.this.popup == null) {
/* 1575 */             TabPaneSkin.TabControlButtons.this.setupPopupMenu();
/*      */           }
/* 1577 */           TabPaneSkin.TabControlButtons.this.requestLayout();
/*      */         }
/*      */       }
/*      */       , new KeyValue[] { localKeyValue }));
/*      */ 
/* 1580 */       localTimeline.play();
/*      */     }
/*      */ 
/*      */     private void hideControlButtons() {
/* 1584 */       setAnimationLock(true);
/* 1585 */       Timeline localTimeline = new Timeline();
/* 1586 */       KeyValue localKeyValue = new KeyValue(this.controlTabHeight, Double.valueOf(0.0D), Interpolator.EASE_IN);
/*      */ 
/* 1588 */       localTimeline.getKeyFrames().clear();
/* 1589 */       localTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(300.0D), new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1591 */           if (!TabPaneSkin.TabControlButtons.this.isShowTabsMenu()) {
/* 1592 */             TabPaneSkin.TabControlButtons.this.downArrowBtn.setVisible(false);
/*      */           }
/*      */ 
/* 1596 */           if (TabPaneSkin.TabControlButtons.this.isShowTabsMenu()) {
/* 1597 */             TabPaneSkin.TabControlButtons.this.showControlButtons = true;
/*      */           } else {
/* 1599 */             TabPaneSkin.TabControlButtons.this.setVisible(false);
/* 1600 */             TabPaneSkin.TabControlButtons.this.popup.getItems().clear();
/* 1601 */             TabPaneSkin.TabControlButtons.this.popup = null;
/*      */           }
/* 1603 */           TabPaneSkin.TabControlButtons.this.setAnimationLock(false);
/*      */ 
/* 1607 */           TabPaneSkin.TabControlButtons.this.requestLayout();
/*      */         }
/*      */       }
/*      */       , new KeyValue[] { localKeyValue }));
/*      */ 
/* 1610 */       localTimeline.play();
/*      */     }
/*      */ 
/*      */     private void setupPopupMenu() {
/* 1614 */       if (this.popup == null) {
/* 1615 */         this.popup = new ContextMenu();
/*      */       }
/*      */ 
/* 1618 */       this.popup.getItems().clear();
/* 1619 */       ToggleGroup localToggleGroup = new ToggleGroup();
/* 1620 */       ObservableList localObservableList = FXCollections.observableArrayList();
/* 1621 */       for (final Tab localTab : ((TabPane)TabPaneSkin.this.getSkinnable()).getTabs()) {
/* 1622 */         TabPaneSkin.TabMenuItem localTabMenuItem = new TabPaneSkin.TabMenuItem(TabPaneSkin.this, localTab);
/* 1623 */         localTabMenuItem.setToggleGroup(localToggleGroup);
/* 1624 */         localTabMenuItem.setOnAction(new EventHandler() {
/*      */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1626 */             ((TabPane)TabPaneSkin.this.getSkinnable()).getSelectionModel().select(localTab);
/*      */           }
/*      */         });
/* 1629 */         localObservableList.add(localTabMenuItem);
/*      */       }
/* 1631 */       this.popup.getItems().addAll(localObservableList);
/*      */     }
/*      */ 
/*      */     private void showPopupMenu() {
/* 1635 */       for (MenuItem localMenuItem : this.popup.getItems()) {
/* 1636 */         TabPaneSkin.TabMenuItem localTabMenuItem = (TabPaneSkin.TabMenuItem)localMenuItem;
/* 1637 */         if (TabPaneSkin.this.selectedTab.equals(localTabMenuItem.getTab())) {
/* 1638 */           localTabMenuItem.setSelected(true);
/* 1639 */           break;
/*      */         }
/*      */       }
/* 1642 */       this.popup.show(this.downArrowBtn, Side.BOTTOM, 0.0D, 0.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */   class TabContentRegion extends StackPane
/*      */     implements TraverseListener
/*      */   {
/*      */     private TraversalEngine engine;
/*      */     private Direction direction;
/*      */     private Tab tab;
/*      */     private InvalidationListener tabListener;
/*      */ 
/*      */     public Tab getTab()
/*      */     {
/* 1273 */       return this.tab;
/*      */     }
/*      */ 
/*      */     public TabContentRegion(Tab arg2) {
/* 1277 */       getStyleClass().setAll(new String[] { "tab-content-area" });
/* 1278 */       setManaged(false);
/*      */       Object localObject;
/* 1279 */       this.tab = localObject;
/* 1280 */       updateContent();
/*      */ 
/* 1282 */       this.tabListener = new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1284 */           if (paramAnonymousObservable == TabPaneSkin.TabContentRegion.this.getTab().selectedProperty()) {
/* 1285 */             TabPaneSkin.TabContentRegion.this.setVisible(TabPaneSkin.TabContentRegion.this.getTab().isSelected());
/* 1286 */           } else if (paramAnonymousObservable == TabPaneSkin.TabContentRegion.this.getTab().contentProperty()) {
/* 1287 */             TabPaneSkin.TabContentRegion.this.getChildren().clear();
/* 1288 */             TabPaneSkin.TabContentRegion.this.updateContent();
/*      */           }
/*      */         }
/*      */       };
/* 1292 */       localObject.selectedProperty().addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1294 */           TabPaneSkin.TabContentRegion.this.setVisible(TabPaneSkin.TabContentRegion.this.getTab().isSelected());
/*      */         }
/*      */       });
/* 1297 */       localObject.contentProperty().addListener(new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1299 */           TabPaneSkin.TabContentRegion.this.getChildren().clear();
/* 1300 */           TabPaneSkin.TabContentRegion.this.updateContent();
/*      */         }
/*      */       });
/* 1304 */       localObject.selectedProperty().addListener(this.tabListener);
/* 1305 */       localObject.contentProperty().addListener(this.tabListener);
/*      */ 
/* 1307 */       this.engine = new TraversalEngine(this, false) {
/*      */         public void trav(Node paramAnonymousNode, Direction paramAnonymousDirection) {
/* 1309 */           TabPaneSkin.TabContentRegion.this.direction = paramAnonymousDirection;
/* 1310 */           super.trav(paramAnonymousNode, paramAnonymousDirection);
/*      */         }
/*      */       };
/* 1313 */       this.engine.addTraverseListener(this);
/* 1314 */       setImpl_traversalEngine(this.engine);
/* 1315 */       setVisible(localObject.isSelected());
/*      */     }
/*      */ 
/*      */     private void updateContent() {
/* 1319 */       if (getTab().getContent() != null)
/* 1320 */         getChildren().add(getTab().getContent());
/*      */     }
/*      */ 
/*      */     private void removeListeners(Tab paramTab)
/*      */     {
/* 1325 */       paramTab.selectedProperty().removeListener(this.tabListener);
/* 1326 */       paramTab.contentProperty().removeListener(this.tabListener);
/* 1327 */       this.engine.removeTraverseListener(this);
/*      */     }
/*      */ 
/*      */     public void onTraverse(Node paramNode, Bounds paramBounds) {
/* 1331 */       int i = this.engine.registeredNodes.indexOf(paramNode);
/*      */ 
/* 1333 */       if ((i == -1) && (this.direction.equals(Direction.PREVIOUS)))
/*      */       {
/* 1335 */         ((TabPane)TabPaneSkin.this.getSkinnable()).requestFocus();
/*      */       }
/* 1337 */       if ((i == -1) && (this.direction.equals(Direction.NEXT)))
/*      */       {
/* 1339 */         new TraversalEngine(TabPaneSkin.this.getSkinnable(), false).trav(TabPaneSkin.this.getSkinnable(), Direction.NEXT);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   class TabHeaderSkin extends StackPane
/*      */   {
/*      */     private final Tab tab;
/*      */     private Label label;
/*      */     private StackPane closeBtn;
/*      */     private StackPane inner;
/*      */     private Tooltip tooltip;
/*      */     private Rectangle clip;
/*      */     private InvalidationListener tabListener;
/*      */     private InvalidationListener controlListener;
/* 1109 */     private final DoubleProperty prefWidth = new DoublePropertyBase()
/*      */     {
/*      */       protected void invalidated() {
/* 1112 */         TabPaneSkin.TabHeaderSkin.this.requestLayout();
/*      */       }
/*      */ 
/*      */       public Object getBean()
/*      */       {
/* 1117 */         return TabPaneSkin.TabHeaderSkin.this;
/*      */       }
/*      */ 
/*      */       public String getName()
/*      */       {
/* 1122 */         return "prefWidth";
/*      */       }
/* 1109 */     };
/*      */ 
/* 1148 */     private boolean animating = false;
/*      */ 
/* 1191 */     private Runnable animateNewTab = null;
/*      */ 
/*      */     public Tab getTab()
/*      */     {
/*  882 */       return this.tab;
/*      */     }
/*      */ 
/*      */     public TabHeaderSkin(Tab arg2)
/*      */     {
/*      */       final Tab localTab;
/*  893 */       getStyleClass().setAll(localTab.getStyleClass());
/*  894 */       setId(localTab.getId());
/*  895 */       setStyle(localTab.getStyle());
/*      */ 
/*  897 */       this.tab = localTab;
/*  898 */       this.clip = new Rectangle();
/*  899 */       setClip(this.clip);
/*      */ 
/*  901 */       this.label = new Label(localTab.getText(), localTab.getGraphic());
/*  902 */       this.label.getStyleClass().setAll(new String[] { "tab-label" });
/*      */ 
/*  904 */       this.closeBtn = new StackPane() {
/*      */         protected double computePrefWidth(double paramAnonymousDouble) {
/*  906 */           return TabPaneSkin.CLOSE_BTN_SIZE;
/*      */         }
/*      */         protected double computePrefHeight(double paramAnonymousDouble) {
/*  909 */           return TabPaneSkin.CLOSE_BTN_SIZE;
/*      */         }
/*      */       };
/*  912 */       this.closeBtn.getStyleClass().setAll(new String[] { "tab-close-button" });
/*  913 */       this.closeBtn.setOnMousePressed(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  915 */           TabPaneSkin.TabHeaderSkin.this.removeListeners(TabPaneSkin.TabHeaderSkin.this.getTab());
/*  916 */           ((TabPaneBehavior)TabPaneSkin.this.getBehavior()).closeTab(TabPaneSkin.TabHeaderSkin.this.getTab());
/*  917 */           TabPaneSkin.TabHeaderSkin.this.setOnMousePressed(null);
/*      */         }
/*      */       });
/*  921 */       updateGraphicRotation();
/*      */ 
/*  923 */       this.inner = new StackPane() {
/*      */         protected void layoutChildren() {
/*  925 */           Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/*  926 */           double d1 = snapSize(getInsets().getTop());
/*  927 */           double d2 = snapSize(getInsets().getRight());
/*  928 */           double d3 = snapSize(getInsets().getBottom());
/*  929 */           double d4 = snapSize(getInsets().getLeft());
/*  930 */           double d5 = getWidth() - d4 + d2;
/*  931 */           double d6 = getHeight() - d1 + d3;
/*      */ 
/*  933 */           double d7 = snapSize(TabPaneSkin.TabHeaderSkin.this.label.prefWidth(-1.0D));
/*  934 */           double d8 = snapSize(TabPaneSkin.TabHeaderSkin.this.label.prefHeight(-1.0D));
/*  935 */           double d9 = TabPaneSkin.TabHeaderSkin.this.showCloseButton() ? snapSize(TabPaneSkin.TabHeaderSkin.this.closeBtn.prefWidth(-1.0D)) : 0.0D;
/*  936 */           double d10 = TabPaneSkin.TabHeaderSkin.this.showCloseButton() ? snapSize(TabPaneSkin.TabHeaderSkin.this.closeBtn.prefHeight(-1.0D)) : 0.0D;
/*  937 */           double d11 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMinWidth());
/*  938 */           double d12 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMaxWidth());
/*  939 */           double d13 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMinHeight());
/*  940 */           double d14 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMaxHeight());
/*      */ 
/*  942 */           double d15 = d7 + d9;
/*  943 */           double d16 = Math.max(d8, d10);
/*      */ 
/*  945 */           if ((d15 > d12) && (d12 != 1.7976931348623157E+308D))
/*  946 */             d7 = d12 - d9;
/*  947 */           else if (d15 < d11) {
/*  948 */             d7 = d11 - d9;
/*      */           }
/*      */ 
/*  951 */           if ((d16 > d14) && (d14 != 1.7976931348623157E+308D))
/*  952 */             d8 = d14;
/*  953 */           else if (d16 < d13) {
/*  954 */             d8 = d13;
/*      */           }
/*      */ 
/*  957 */           if (TabPaneSkin.TabHeaderSkin.this.animating) {
/*  958 */             if (TabPaneSkin.TabHeaderSkin.this.prefWidth.getValue().doubleValue() < d7) {
/*  959 */               d7 = TabPaneSkin.TabHeaderSkin.this.prefWidth.getValue().doubleValue();
/*      */             }
/*  961 */             TabPaneSkin.TabHeaderSkin.this.closeBtn.setVisible(false);
/*      */           } else {
/*  963 */             TabPaneSkin.TabHeaderSkin.this.closeBtn.setVisible(TabPaneSkin.TabHeaderSkin.this.showCloseButton());
/*      */           }
/*      */ 
/*  966 */           TabPaneSkin.TabHeaderSkin.this.label.resize(d7, d8);
/*      */ 
/*  968 */           double d17 = d4;
/*  969 */           double d18 = (d12 != 1.7976931348623157E+308D ? d12 : d5) - d2 - d9;
/*      */ 
/*  971 */           positionInArea(TabPaneSkin.TabHeaderSkin.this.label, d17, d1, d7, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */ 
/*  974 */           if (TabPaneSkin.TabHeaderSkin.this.closeBtn.isVisible()) {
/*  975 */             TabPaneSkin.TabHeaderSkin.this.closeBtn.resize(d9, d10);
/*  976 */             positionInArea(TabPaneSkin.TabHeaderSkin.this.closeBtn, d18, d1, d9, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/*      */           }
/*      */         }
/*      */       };
/*  981 */       this.inner.setRotate(((TabPane)TabPaneSkin.this.getSkinnable()).getSide().equals(Side.BOTTOM) ? 180.0D : 0.0D);
/*  982 */       this.inner.getChildren().addAll(new Node[] { this.label, this.closeBtn });
/*      */ 
/*  984 */       getChildren().addAll(new Node[] { this.inner });
/*      */ 
/*  986 */       this.tooltip = localTab.getTooltip();
/*  987 */       if (this.tooltip != null) {
/*  988 */         Tooltip.install(this, this.tooltip);
/*      */       }
/*      */ 
/*  991 */       this.tabListener = new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/*  993 */           if (paramAnonymousObservable == localTab.selectedProperty()) {
/*  994 */             TabPaneSkin.TabHeaderSkin.this.impl_pseudoClassStateChanged("selected");
/*      */ 
/*  998 */             TabPaneSkin.TabHeaderSkin.this.inner.requestLayout();
/*  999 */             TabPaneSkin.TabHeaderSkin.this.requestLayout();
/* 1000 */           } else if (paramAnonymousObservable == localTab.textProperty()) {
/* 1001 */             TabPaneSkin.TabHeaderSkin.this.label.setText(TabPaneSkin.TabHeaderSkin.this.getTab().getText());
/* 1002 */           } else if (paramAnonymousObservable == localTab.graphicProperty()) {
/* 1003 */             TabPaneSkin.TabHeaderSkin.this.label.setGraphic(TabPaneSkin.TabHeaderSkin.this.getTab().getGraphic());
/* 1004 */           } else if (paramAnonymousObservable != localTab.contextMenuProperty())
/*      */           {
/* 1006 */             if (paramAnonymousObservable == localTab.tooltipProperty()) { TabPaneSkin.TabHeaderSkin.this.getChildren().remove(TabPaneSkin.TabHeaderSkin.this.tooltip);
/* 1008 */               TabPaneSkin.TabHeaderSkin.this.tooltip = localTab.getTooltip();
/* 1009 */               if (TabPaneSkin.TabHeaderSkin.this.tooltip == null);
/*      */             }
/* 1012 */             else if (paramAnonymousObservable == localTab.styleProperty()) {
/* 1013 */               TabPaneSkin.TabHeaderSkin.this.setStyle(localTab.getStyle());
/* 1014 */             } else if (paramAnonymousObservable == localTab.disableProperty()) {
/* 1015 */               TabPaneSkin.TabHeaderSkin.this.impl_pseudoClassStateChanged("disabled");
/* 1016 */               TabPaneSkin.TabHeaderSkin.this.inner.requestLayout();
/* 1017 */               TabPaneSkin.TabHeaderSkin.this.requestLayout();
/* 1018 */             } else if (paramAnonymousObservable == localTab.closableProperty()) {
/* 1019 */               TabPaneSkin.TabHeaderSkin.this.inner.requestLayout();
/* 1020 */               TabPaneSkin.TabHeaderSkin.this.requestLayout();
/*      */             }
/*      */           }
/*      */         }
/*      */       };
/* 1025 */       localTab.closableProperty().addListener(this.tabListener);
/* 1026 */       localTab.selectedProperty().addListener(this.tabListener);
/* 1027 */       localTab.textProperty().addListener(this.tabListener);
/* 1028 */       localTab.graphicProperty().addListener(this.tabListener);
/* 1029 */       localTab.contextMenuProperty().addListener(this.tabListener);
/* 1030 */       localTab.tooltipProperty().addListener(this.tabListener);
/* 1031 */       localTab.disableProperty().addListener(this.tabListener);
/* 1032 */       localTab.styleProperty().addListener(this.tabListener);
/* 1033 */       localTab.getStyleClass().addListener(new ListChangeListener()
/*      */       {
/*      */         public void onChanged(ListChangeListener.Change<? extends String> paramAnonymousChange) {
/* 1036 */           TabPaneSkin.TabHeaderSkin.this.getStyleClass().setAll(localTab.getStyleClass());
/*      */         }
/*      */       });
/* 1040 */       this.controlListener = new InvalidationListener() {
/*      */         public void invalidated(Observable paramAnonymousObservable) {
/* 1042 */           if (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).tabClosingPolicyProperty()) {
/* 1043 */             TabPaneSkin.TabHeaderSkin.this.inner.requestLayout();
/* 1044 */             TabPaneSkin.TabHeaderSkin.this.requestLayout();
/* 1045 */           } else if (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).sideProperty()) {
/* 1046 */             TabPaneSkin.TabHeaderSkin.this.inner.setRotate(((TabPane)TabPaneSkin.this.getSkinnable()).getSide().equals(Side.BOTTOM) ? 180.0D : 0.0D);
/* 1047 */             if (((TabPane)TabPaneSkin.this.getSkinnable()).isRotateGraphic())
/* 1048 */               TabPaneSkin.TabHeaderSkin.this.updateGraphicRotation();
/*      */           }
/* 1050 */           else if (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).rotateGraphicProperty()) {
/* 1051 */             TabPaneSkin.TabHeaderSkin.this.updateGraphicRotation();
/* 1052 */           } else if ((paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinWidthProperty()) || (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxWidthProperty()) || (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinHeightProperty()) || (paramAnonymousObservable == ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxHeightProperty()))
/*      */           {
/* 1056 */             TabPaneSkin.TabHeaderSkin.this.requestLayout();
/*      */           }
/*      */         }
/*      */       };
/* 1060 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabClosingPolicyProperty().addListener(this.controlListener);
/* 1061 */       ((TabPane)TabPaneSkin.this.getSkinnable()).sideProperty().addListener(this.controlListener);
/* 1062 */       ((TabPane)TabPaneSkin.this.getSkinnable()).rotateGraphicProperty().addListener(this.controlListener);
/* 1063 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinWidthProperty().addListener(this.controlListener);
/* 1064 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxWidthProperty().addListener(this.controlListener);
/* 1065 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinHeightProperty().addListener(this.controlListener);
/* 1066 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxHeightProperty().addListener(this.controlListener);
/* 1067 */       getProperties().put(Tab.class, localTab);
/* 1068 */       getProperties().put(ContextMenu.class, localTab.getContextMenu());
/*      */ 
/* 1070 */       setOnContextMenuRequested(new EventHandler() {
/*      */         public void handle(ContextMenuEvent paramAnonymousContextMenuEvent) {
/* 1072 */           if (TabPaneSkin.TabHeaderSkin.this.getTab().getContextMenu() != null) {
/* 1073 */             TabPaneSkin.TabHeaderSkin.this.getTab().getContextMenu().show(TabPaneSkin.TabHeaderSkin.this.inner, paramAnonymousContextMenuEvent.getScreenX(), paramAnonymousContextMenuEvent.getScreenY());
/* 1074 */             paramAnonymousContextMenuEvent.consume();
/*      */           }
/*      */         }
/*      */       });
/* 1078 */       setOnMousePressed(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 1080 */           if (TabPaneSkin.TabHeaderSkin.this.getTab().isDisable()) {
/* 1081 */             return;
/*      */           }
/* 1083 */           if (paramAnonymousMouseEvent.getButton().equals(MouseButton.MIDDLE)) {
/* 1084 */             if (TabPaneSkin.TabHeaderSkin.this.showCloseButton()) {
/* 1085 */               TabPaneSkin.TabHeaderSkin.this.removeListeners(TabPaneSkin.TabHeaderSkin.this.getTab());
/* 1086 */               ((TabPaneBehavior)TabPaneSkin.this.getBehavior()).closeTab(TabPaneSkin.TabHeaderSkin.this.getTab());
/*      */             }
/* 1088 */           } else if (paramAnonymousMouseEvent.getButton().equals(MouseButton.PRIMARY))
/* 1089 */             ((TabPaneBehavior)TabPaneSkin.this.getBehavior()).selectTab(TabPaneSkin.TabHeaderSkin.this.getTab());
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     private void updateGraphicRotation()
/*      */     {
/* 1096 */       if (this.label.getGraphic() != null)
/* 1097 */         this.label.getGraphic().setRotate(((TabPane)TabPaneSkin.this.getSkinnable()).isRotateGraphic() ? 0.0D : ((TabPane)TabPaneSkin.this.getSkinnable()).getSide().equals(Side.LEFT) ? 90.0F : ((TabPane)TabPaneSkin.this.getSkinnable()).getSide().equals(Side.RIGHT) ? -90.0F : 0.0F);
/*      */     }
/*      */ 
/*      */     private boolean showCloseButton()
/*      */     {
/* 1104 */       return (this.tab.isClosable()) && ((((TabPane)TabPaneSkin.this.getSkinnable()).getTabClosingPolicy().equals(TabPane.TabClosingPolicy.ALL_TABS)) || ((((TabPane)TabPaneSkin.this.getSkinnable()).getTabClosingPolicy().equals(TabPane.TabClosingPolicy.SELECTED_TAB)) && (this.tab.isSelected())));
/*      */     }
/*      */ 
/*      */     private void removeListeners(Tab paramTab)
/*      */     {
/* 1127 */       paramTab.selectedProperty().removeListener(this.tabListener);
/* 1128 */       paramTab.textProperty().removeListener(this.tabListener);
/* 1129 */       paramTab.graphicProperty().removeListener(this.tabListener);
/* 1130 */       ContextMenu localContextMenu = paramTab.getContextMenu();
/* 1131 */       if (localContextMenu != null) {
/* 1132 */         localContextMenu.getItems().clear();
/*      */       }
/* 1134 */       paramTab.contextMenuProperty().removeListener(this.tabListener);
/* 1135 */       paramTab.tooltipProperty().removeListener(this.tabListener);
/* 1136 */       paramTab.styleProperty().removeListener(this.tabListener);
/* 1137 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabClosingPolicyProperty().removeListener(this.controlListener);
/* 1138 */       ((TabPane)TabPaneSkin.this.getSkinnable()).sideProperty().removeListener(this.controlListener);
/* 1139 */       ((TabPane)TabPaneSkin.this.getSkinnable()).rotateGraphicProperty().removeListener(this.controlListener);
/* 1140 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinWidthProperty().removeListener(this.controlListener);
/* 1141 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxWidthProperty().removeListener(this.controlListener);
/* 1142 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMinHeightProperty().removeListener(this.controlListener);
/* 1143 */       ((TabPane)TabPaneSkin.this.getSkinnable()).tabMaxHeightProperty().removeListener(this.controlListener);
/* 1144 */       this.inner.getChildren().clear();
/* 1145 */       getChildren().clear();
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble)
/*      */     {
/* 1151 */       if (this.animating) {
/* 1152 */         return this.prefWidth.getValue().doubleValue();
/*      */       }
/* 1154 */       double d1 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMinWidth());
/* 1155 */       double d2 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMaxWidth());
/* 1156 */       double d3 = snapSize(getInsets().getRight());
/* 1157 */       double d4 = snapSize(getInsets().getLeft());
/* 1158 */       double d5 = snapSize(this.label.prefWidth(-1.0D));
/*      */ 
/* 1161 */       if (showCloseButton()) {
/* 1162 */         d5 += snapSize(this.closeBtn.prefWidth(-1.0D));
/*      */       }
/*      */ 
/* 1165 */       if (d5 > d2)
/* 1166 */         d5 = d2;
/* 1167 */       else if (d5 < d1) {
/* 1168 */         d5 = d1;
/*      */       }
/* 1170 */       d5 += d3 + d4;
/* 1171 */       this.prefWidth.setValue(Double.valueOf(d5));
/* 1172 */       return d5;
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble) {
/* 1176 */       double d1 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMinHeight());
/* 1177 */       double d2 = snapSize(((TabPane)TabPaneSkin.this.getSkinnable()).getTabMaxHeight());
/* 1178 */       double d3 = snapSize(getInsets().getTop());
/* 1179 */       double d4 = snapSize(getInsets().getBottom());
/* 1180 */       double d5 = snapSize(this.label.prefHeight(paramDouble));
/*      */ 
/* 1182 */       if (d5 > d2)
/* 1183 */         d5 = d2;
/* 1184 */       else if (d5 < d1) {
/* 1185 */         d5 = d1;
/*      */       }
/* 1187 */       d5 += d3 + d4;
/* 1188 */       return d5;
/*      */     }
/*      */ 
/*      */     protected void layoutChildren()
/*      */     {
/* 1194 */       Insets localInsets = getInsets();
/* 1195 */       this.inner.resize(snapSize(getWidth()) - snapSize(localInsets.getRight()) - snapSize(localInsets.getLeft()), snapSize(getHeight()) - snapSize(localInsets.getTop()) - snapSize(localInsets.getBottom()));
/*      */ 
/* 1197 */       this.inner.relocate(snapSize(localInsets.getLeft()), snapSize(localInsets.getTop()));
/*      */ 
/* 1199 */       if (this.animateNewTab != null) {
/* 1200 */         this.animateNewTab.run();
/* 1201 */         this.animateNewTab = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     protected void setWidth(double paramDouble) {
/* 1206 */       super.setWidth(paramDouble);
/* 1207 */       this.clip.setWidth(paramDouble);
/*      */     }
/*      */ 
/*      */     protected void setHeight(double paramDouble) {
/* 1211 */       super.setHeight(paramDouble);
/* 1212 */       this.clip.setHeight(paramDouble);
/*      */     }
/*      */ 
/*      */     public long impl_getPseudoClassState()
/*      */     {
/* 1218 */       long l = super.impl_getPseudoClassState();
/*      */ 
/* 1220 */       if (getTab().isDisable())
/* 1221 */         l |= TabPaneSkin.DISABLED_PSEUDOCLASS_STATE;
/* 1222 */       else if (getTab().isSelected()) {
/* 1223 */         l |= TabPaneSkin.SELECTED_PSEUDOCLASS_STATE;
/*      */       }
/*      */ 
/* 1226 */       switch (TabPaneSkin.4.$SwitchMap$javafx$geometry$Side[((TabPane)TabPaneSkin.this.getSkinnable()).getSide().ordinal()]) {
/*      */       case 1:
/* 1228 */         l |= TabPaneSkin.TOP_PSEUDOCLASS_STATE;
/* 1229 */         break;
/*      */       case 4:
/* 1231 */         l |= TabPaneSkin.RIGHT_PSEUDOCLASS_STATE;
/* 1232 */         break;
/*      */       case 2:
/* 1234 */         l |= TabPaneSkin.BOTTOM_PSEUDOCLASS_STATE;
/* 1235 */         break;
/*      */       case 3:
/* 1237 */         l |= TabPaneSkin.LEFT_PSEUDOCLASS_STATE;
/*      */       }
/*      */ 
/* 1241 */       return l;
/*      */     }
/*      */   }
/*      */ 
/*      */   class TabHeaderArea extends StackPane
/*      */   {
/*      */     private Rectangle headerClip;
/*      */     private StackPane headersRegion;
/*      */     private StackPane headerBackground;
/*      */     private TabPaneSkin.TabControlButtons controlButtons;
/*      */     private double lastDragPos;
/*      */     private double scrollOffset;
/*      */     private Point2D dragAnchor;
/*  692 */     private List<TabPaneSkin.TabHeaderSkin> removeTab = new ArrayList();
/*      */     private Timeline scroller;
/*      */ 
/*      */     public double getScrollOffset()
/*      */     {
/*  501 */       return this.scrollOffset;
/*      */     }
/*      */     public void setScrollOffset(double paramDouble) {
/*  504 */       this.scrollOffset = paramDouble;
/*  505 */       this.headersRegion.requestLayout();
/*      */     }
/*      */ 
/*      */     public TabHeaderArea() {
/*  509 */       getStyleClass().setAll(new String[] { "tab-header-area" });
/*  510 */       setManaged(false);
/*  511 */       TabPane localTabPane = (TabPane)TabPaneSkin.this.getSkinnable();
/*      */ 
/*  513 */       this.headerClip = new Rectangle();
/*      */ 
/*  515 */       this.headersRegion = new StackPane() {
/*      */         protected double computePrefWidth(double paramAnonymousDouble) {
/*  517 */           double d = 0.0D;
/*  518 */           for (Node localNode : getChildren()) {
/*  519 */             TabPaneSkin.TabHeaderSkin localTabHeaderSkin = (TabPaneSkin.TabHeaderSkin)localNode;
/*  520 */             if (localTabHeaderSkin.isVisible()) {
/*  521 */               d += localTabHeaderSkin.prefWidth(paramAnonymousDouble);
/*      */             }
/*      */           }
/*  524 */           return snapSize(d) + snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight());
/*      */         }
/*      */ 
/*      */         protected double computePrefHeight(double paramAnonymousDouble) {
/*  528 */           double d = 0.0D;
/*  529 */           for (Node localNode : getChildren()) {
/*  530 */             TabPaneSkin.TabHeaderSkin localTabHeaderSkin = (TabPaneSkin.TabHeaderSkin)localNode;
/*  531 */             d = Math.max(d, localTabHeaderSkin.prefHeight(paramAnonymousDouble));
/*      */           }
/*  533 */           return snapSize(d) + snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */         }
/*      */ 
/*      */         protected void layoutChildren()
/*      */         {
/*      */           double d1;
/*      */           double d3;
/*      */           double d6;
/*  537 */           if (TabPaneSkin.TabHeaderArea.this.tabsFit()) {
/*  538 */             TabPaneSkin.TabHeaderArea.this.setScrollOffset(0.0D);
/*      */           }
/*  540 */           else if (!TabPaneSkin.TabHeaderArea.this.removeTab.isEmpty()) {
/*  541 */             d1 = 0.0D;
/*  542 */             d3 = TabPaneSkin.this.tabHeaderArea.getWidth() - snapSize(TabPaneSkin.TabHeaderArea.this.controlButtons.prefWidth(-1.0D)) - TabPaneSkin.TabHeaderArea.this.firstTabIndent() - 10.0D;
/*  543 */             Iterator localIterator1 = getChildren().iterator();
/*  544 */             while (localIterator1.hasNext()) {
/*  545 */               localObject = (TabPaneSkin.TabHeaderSkin)localIterator1.next();
/*  546 */               d6 = snapSize(((TabPaneSkin.TabHeaderSkin)localObject).prefWidth(-1.0D));
/*  547 */               if (TabPaneSkin.TabHeaderArea.this.removeTab.contains(localObject)) {
/*  548 */                 if (d1 < d3) {
/*  549 */                   TabPaneSkin.this.isSelectingTab = true;
/*      */                 }
/*  551 */                 localIterator1.remove();
/*  552 */                 TabPaneSkin.TabHeaderArea.this.removeTab.remove(localObject);
/*  553 */                 if (TabPaneSkin.TabHeaderArea.this.removeTab.isEmpty()) {
/*      */                   break;
/*      */                 }
/*      */               }
/*  557 */               d1 += d6;
/*      */             }
/*      */           } else {
/*  560 */             TabPaneSkin.this.isSelectingTab = true;
/*      */           }
/*      */           double d7;
/*  564 */           if (TabPaneSkin.this.isSelectingTab) {
/*  565 */             d1 = 0.0D;
/*  566 */             d3 = 0.0D;
/*  567 */             double d5 = 0.0D;
/*  568 */             d6 = 0.0D;
/*  569 */             d7 = 0.0D;
/*  570 */             for (Node localNode2 : getChildren()) {
/*  571 */               TabPaneSkin.TabHeaderSkin localTabHeaderSkin2 = (TabPaneSkin.TabHeaderSkin)localNode2;
/*      */ 
/*  573 */               double d10 = snapSize(localTabHeaderSkin2.prefWidth(-1.0D));
/*  574 */               if ((TabPaneSkin.this.selectedTab != null) && (TabPaneSkin.this.selectedTab.equals(localTabHeaderSkin2.getTab()))) {
/*  575 */                 d3 = d1;
/*  576 */                 d5 = d10;
/*      */               }
/*  578 */               if ((TabPaneSkin.this.previousSelectedTab != null) && (TabPaneSkin.this.previousSelectedTab.equals(localTabHeaderSkin2.getTab()))) {
/*  579 */                 d6 = d1;
/*  580 */                 d7 = d10;
/*      */               }
/*  582 */               d1 += d10;
/*      */             }
/*  584 */             if (d3 > d6)
/*  585 */               TabPaneSkin.TabHeaderArea.this.scrollToSelectedTab(d3 + d5, d6);
/*      */             else {
/*  587 */               TabPaneSkin.TabHeaderArea.this.scrollToSelectedTab(d3, d6);
/*      */             }
/*  589 */             TabPaneSkin.this.isSelectingTab = false;
/*      */           }
/*      */ 
/*  592 */           Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/*  593 */           double d2 = snapSize(prefHeight(-1.0D));
/*  594 */           double d4 = (localSide.equals(Side.LEFT)) || (localSide.equals(Side.BOTTOM)) ? snapSize(getWidth()) - TabPaneSkin.TabHeaderArea.this.getScrollOffset() : TabPaneSkin.TabHeaderArea.this.getScrollOffset();
/*      */ 
/*  597 */           TabPaneSkin.TabHeaderArea.this.updateHeaderClip();
/*  598 */           for (Object localObject = getChildren().iterator(); ((Iterator)localObject).hasNext(); ) { Node localNode1 = (Node)((Iterator)localObject).next();
/*  599 */             TabPaneSkin.TabHeaderSkin localTabHeaderSkin1 = (TabPaneSkin.TabHeaderSkin)localNode1;
/*      */ 
/*  601 */             d7 = snapSize(localTabHeaderSkin1.prefWidth(-1.0D));
/*  602 */             double d8 = snapSize(localTabHeaderSkin1.prefHeight(-1.0D));
/*  603 */             localTabHeaderSkin1.resize(d7, d8);
/*      */ 
/*  606 */             double d9 = localSide.equals(Side.BOTTOM) ? 0.0D : d2 - d8 - snapSize(getInsets().getBottom());
/*      */ 
/*  608 */             if ((localSide.equals(Side.LEFT)) || (localSide.equals(Side.BOTTOM)))
/*      */             {
/*  610 */               d4 -= d7;
/*  611 */               localTabHeaderSkin1.relocate(d4, d9);
/*      */             }
/*      */             else {
/*  614 */               localTabHeaderSkin1.relocate(d4, d9);
/*  615 */               d4 += d7;
/*      */             }
/*      */           }
/*      */         }
/*      */       };
/*  620 */       this.headersRegion.getStyleClass().setAll(new String[] { "headers-region" });
/*  621 */       this.headersRegion.setClip(this.headerClip);
/*      */ 
/*  623 */       this.headerBackground = new StackPane();
/*  624 */       this.headerBackground.getStyleClass().setAll(new String[] { "tab-header-background" });
/*      */ 
/*  626 */       int i = 0;
/*  627 */       for (Tab localTab : localTabPane.getTabs()) {
/*  628 */         addTab(localTab, i++, true);
/*      */       }
/*      */ 
/*  631 */       this.controlButtons = new TabPaneSkin.TabControlButtons(TabPaneSkin.this);
/*  632 */       this.controlButtons.setVisible(false);
/*  633 */       if (this.controlButtons.isVisible()) {
/*  634 */         this.controlButtons.setVisible(true);
/*      */       }
/*  636 */       getChildren().addAll(new Node[] { this.headerBackground, this.headersRegion, this.controlButtons });
/*      */     }
/*      */ 
/*      */     private void updateHeaderClip()
/*      */     {
/*  641 */       Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/*      */ 
/*  643 */       double d1 = 0.0D;
/*  644 */       double d2 = 0.0D;
/*  645 */       double d3 = 0.0D;
/*  646 */       double d4 = 0.0D;
/*  647 */       double d5 = 0.0D;
/*  648 */       double d6 = 0.0D;
/*  649 */       double d7 = firstTabIndent();
/*  650 */       double d8 = snapSize(this.controlButtons.prefWidth(-1.0D));
/*  651 */       double d9 = snapSize(this.headersRegion.prefWidth(-1.0D));
/*  652 */       double d10 = snapSize(this.headersRegion.prefHeight(-1.0D));
/*      */ 
/*  655 */       if (d8 > 0.0D) {
/*  656 */         d8 += 10.0D;
/*      */       }
/*      */ 
/*  659 */       if ((this.headersRegion.getEffect() instanceof DropShadow)) {
/*  660 */         DropShadow localDropShadow = (DropShadow)this.headersRegion.getEffect();
/*  661 */         d6 = localDropShadow.getRadius();
/*      */       }
/*      */ 
/*  664 */       d5 = snapSize(getWidth()) - d8 - d7;
/*  665 */       if ((localSide.equals(Side.LEFT)) || (localSide.equals(Side.BOTTOM))) {
/*  666 */         if (d9 < d5) {
/*  667 */           d3 = d9 + d6;
/*      */         } else {
/*  669 */           d1 = d9 - d5;
/*  670 */           d3 = d5 + d6;
/*      */         }
/*  672 */         d4 = d10;
/*      */       }
/*      */       else {
/*  675 */         d1 = -d6;
/*  676 */         d3 = (d9 < d5 ? d9 : d5) + d6;
/*  677 */         d4 = d10;
/*      */       }
/*      */ 
/*  680 */       this.headerClip.setX(d1);
/*  681 */       this.headerClip.setY(d2);
/*  682 */       this.headerClip.setWidth(d3);
/*  683 */       this.headerClip.setHeight(d4);
/*      */     }
/*      */ 
/*      */     private void addTab(Tab paramTab, int paramInt, boolean paramBoolean) {
/*  687 */       TabPaneSkin.TabHeaderSkin localTabHeaderSkin = new TabPaneSkin.TabHeaderSkin(TabPaneSkin.this, paramTab);
/*  688 */       localTabHeaderSkin.setVisible(paramBoolean);
/*  689 */       this.headersRegion.getChildren().add(paramInt, localTabHeaderSkin);
/*      */     }
/*      */ 
/*      */     private void removeTab(Tab paramTab)
/*      */     {
/*  694 */       TabPaneSkin.TabHeaderSkin localTabHeaderSkin = getTabHeaderSkin(paramTab);
/*  695 */       if (localTabHeaderSkin != null)
/*  696 */         if (tabsFit()) {
/*  697 */           this.headersRegion.getChildren().remove(localTabHeaderSkin);
/*      */         }
/*      */         else
/*      */         {
/*  701 */           this.removeTab.add(localTabHeaderSkin);
/*  702 */           localTabHeaderSkin.removeListeners(paramTab);
/*      */         }
/*      */     }
/*      */ 
/*      */     private TabPaneSkin.TabHeaderSkin getTabHeaderSkin(Tab paramTab)
/*      */     {
/*  708 */       for (Node localNode : this.headersRegion.getChildren()) {
/*  709 */         TabPaneSkin.TabHeaderSkin localTabHeaderSkin = (TabPaneSkin.TabHeaderSkin)localNode;
/*  710 */         if (localTabHeaderSkin.getTab().equals(paramTab)) {
/*  711 */           return localTabHeaderSkin;
/*      */         }
/*      */       }
/*  714 */       return null;
/*      */     }
/*      */ 
/*      */     private void createScrollTimeline(final double paramDouble)
/*      */     {
/*  722 */       scroll(paramDouble);
/*  723 */       this.scroller = new Timeline();
/*  724 */       this.scroller.setCycleCount(-1);
/*  725 */       this.scroller.getKeyFrames().add(new KeyFrame(Duration.millis(150.0D), new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/*  727 */           TabPaneSkin.TabHeaderArea.this.scroll(paramDouble);
/*      */         }
/*      */       }
/*      */       , new KeyValue[0]));
/*      */     }
/*      */ 
/*      */     private void scroll(double paramDouble)
/*      */     {
/*  734 */       if (tabsFit()) {
/*  735 */         return;
/*      */       }
/*  737 */       Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/*  738 */       double d1 = snapSize(this.headersRegion.prefWidth(-1.0D));
/*  739 */       double d2 = snapSize(this.controlButtons.prefWidth(-1.0D));
/*  740 */       double d3 = getWidth() - d1 - d2;
/*  741 */       double d4 = (localSide.equals(Side.LEFT)) || (localSide.equals(Side.BOTTOM)) ? -paramDouble : paramDouble;
/*  742 */       double d5 = getScrollOffset() + d4;
/*  743 */       setScrollOffset(d5 <= d3 ? d3 : d5 >= 0.0D ? 0.0D : d5);
/*      */     }
/*      */ 
/*      */     private boolean tabsFit() {
/*  747 */       double d1 = snapSize(this.headersRegion.prefWidth(-1.0D));
/*  748 */       double d2 = snapSize(this.controlButtons.prefWidth(-1.0D));
/*  749 */       double d3 = d1 + d2 + firstTabIndent() + 10.0D;
/*  750 */       return d3 < getWidth();
/*      */     }
/*      */ 
/*      */     private void scrollToSelectedTab(double paramDouble1, double paramDouble2)
/*      */     {
/*      */       double d1;
/*  754 */       if (paramDouble1 > paramDouble2)
/*      */       {
/*  756 */         d1 = paramDouble1 - paramDouble2;
/*  757 */         double d2 = paramDouble2 + getScrollOffset() + d1;
/*  758 */         double d3 = snapSize(getWidth()) - snapSize(this.controlButtons.prefWidth(-1.0D)) - firstTabIndent() - 10.0D;
/*  759 */         if (d2 > d3)
/*  760 */           setScrollOffset(getScrollOffset() - (d2 - d3));
/*      */       }
/*      */       else
/*      */       {
/*  764 */         d1 = paramDouble1 + getScrollOffset();
/*  765 */         if (d1 < 0.0D)
/*  766 */           setScrollOffset(getScrollOffset() - d1);
/*      */       }
/*      */     }
/*      */ 
/*      */     private double firstTabIndent()
/*      */     {
/*  772 */       switch (TabPaneSkin.4.$SwitchMap$javafx$geometry$Side[((TabPane)TabPaneSkin.this.getSkinnable()).getSide().ordinal()]) {
/*      */       case 1:
/*      */       case 2:
/*  775 */         return snapSize(getInsets().getLeft());
/*      */       case 3:
/*      */       case 4:
/*  778 */         return snapSize(getInsets().getTop());
/*      */       }
/*  780 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble)
/*      */     {
/*  785 */       double d = TabPaneSkin.this.isHorizontal() ? snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight()) : snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom());
/*      */ 
/*  788 */       return snapSize(this.headersRegion.prefWidth(-1.0D)) + d;
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble) {
/*  792 */       double d = TabPaneSkin.this.isHorizontal() ? snapSize(getInsets().getTop()) + snapSize(getInsets().getBottom()) : snapSize(getInsets().getLeft()) + snapSize(getInsets().getRight());
/*      */ 
/*  795 */       return snapSize(this.headersRegion.prefHeight(-1.0D)) + d;
/*      */     }
/*      */ 
/*      */     public double getBaselineOffset() {
/*  799 */       return this.headersRegion.getBaselineOffset() + this.headersRegion.getLayoutY();
/*      */     }
/*      */ 
/*      */     protected void layoutChildren() {
/*  803 */       TabPane localTabPane = (TabPane)TabPaneSkin.this.getSkinnable();
/*  804 */       Insets localInsets = getInsets();
/*  805 */       double d1 = snapSize(getWidth()) - (TabPaneSkin.this.isHorizontal() ? snapSize(localInsets.getLeft()) + snapSize(localInsets.getRight()) : snapSize(localInsets.getTop()) + snapSize(localInsets.getBottom()));
/*      */ 
/*  807 */       double d2 = snapSize(getHeight()) - (TabPaneSkin.this.isHorizontal() ? snapSize(localInsets.getTop()) + snapSize(localInsets.getBottom()) : snapSize(localInsets.getLeft()) + snapSize(localInsets.getRight()));
/*      */ 
/*  809 */       double d3 = snapSize(prefHeight(-1.0D));
/*  810 */       double d4 = snapSize(this.headersRegion.prefWidth(-1.0D));
/*  811 */       double d5 = snapSize(this.headersRegion.prefHeight(-1.0D));
/*      */ 
/*  813 */       if (tabsFit())
/*  814 */         this.controlButtons.showTabsMenu(false);
/*      */       else {
/*  816 */         this.controlButtons.showTabsMenu(true);
/*      */       }
/*      */ 
/*  819 */       updateHeaderClip();
/*      */ 
/*  822 */       double d6 = snapSize(this.controlButtons.prefWidth(-1.0D));
/*  823 */       this.controlButtons.resize(d6, this.controlButtons.getControlTabHeight());
/*      */ 
/*  825 */       this.headersRegion.resize(d4, d5);
/*      */ 
/*  827 */       if (TabPaneSkin.this.isFloatingStyleClass()) {
/*  828 */         this.headerBackground.setVisible(false);
/*      */       } else {
/*  830 */         this.headerBackground.resize(snapSize(getWidth()), snapSize(getHeight()));
/*  831 */         this.headerBackground.setVisible(true);
/*      */       }
/*      */ 
/*  834 */       double d7 = 0.0D;
/*  835 */       double d8 = 0.0D;
/*  836 */       double d9 = 0.0D;
/*  837 */       double d10 = 0.0D;
/*  838 */       Side localSide = ((TabPane)TabPaneSkin.this.getSkinnable()).getSide();
/*      */ 
/*  840 */       if (localSide.equals(Side.TOP)) {
/*  841 */         d7 = snapSize(localInsets.getLeft());
/*  842 */         d8 = d3 - d5 - snapSize(localInsets.getBottom());
/*  843 */         d9 = d1 - d6 + snapSize(localInsets.getLeft());
/*  844 */         d10 = snapSize(getHeight()) - this.controlButtons.getControlTabHeight() - snapSize(localInsets.getBottom());
/*  845 */       } else if (localSide.equals(Side.RIGHT)) {
/*  846 */         d7 = snapSize(localInsets.getTop());
/*  847 */         d8 = d3 - d5 - snapSize(localInsets.getLeft());
/*  848 */         d9 = d1 - d6 + snapSize(localInsets.getTop());
/*  849 */         d10 = snapSize(getHeight()) - this.controlButtons.getControlTabHeight() - snapSize(localInsets.getLeft());
/*  850 */       } else if (localSide.equals(Side.BOTTOM)) {
/*  851 */         d7 = snapSize(getWidth()) - d4 - snapSize(getInsets().getLeft());
/*  852 */         d8 = d3 - d5 - snapSize(localInsets.getTop());
/*  853 */         d9 = snapSize(localInsets.getRight());
/*  854 */         d10 = snapSize(getHeight()) - this.controlButtons.getControlTabHeight() - snapSize(localInsets.getTop());
/*  855 */       } else if (localSide.equals(Side.LEFT)) {
/*  856 */         d7 = snapSize(getWidth()) - d4 - snapSize(getInsets().getTop());
/*  857 */         d8 = d3 - d5 - snapSize(localInsets.getRight());
/*  858 */         d9 = snapSize(localInsets.getLeft());
/*  859 */         d10 = snapSize(getHeight()) - this.controlButtons.getControlTabHeight() - snapSize(localInsets.getRight());
/*      */       }
/*  861 */       if (this.headerBackground.isVisible()) {
/*  862 */         positionInArea(this.headerBackground, 0.0D, 0.0D, snapSize(getWidth()), snapSize(getHeight()), 0.0D, HPos.CENTER, VPos.CENTER);
/*      */       }
/*      */ 
/*  865 */       positionInArea(this.headersRegion, d7, d8, d1, d2, 0.0D, HPos.LEFT, VPos.CENTER);
/*  866 */       positionInArea(this.controlButtons, d9, d10, d6, this.controlButtons.getControlTabHeight(), 0.0D, HPos.CENTER, VPos.CENTER);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TabPaneSkin
 * JD-Core Version:    0.6.2
 */