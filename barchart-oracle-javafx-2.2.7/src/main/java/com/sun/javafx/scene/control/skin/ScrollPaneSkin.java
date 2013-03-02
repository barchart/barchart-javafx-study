/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.scene.control.behavior.ScrollPaneBehavior;
/*      */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*      */ import com.sun.javafx.scene.traversal.TraverseListener;
/*      */ import javafx.animation.Animation.Status;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventDispatcher;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.BoundingBox;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.scene.Cursor;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.control.ScrollBar;
/*      */ import javafx.scene.control.ScrollPane;
/*      */ import javafx.scene.control.ScrollPane.ScrollBarPolicy;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.ScrollEvent;
/*      */ import javafx.scene.input.TouchEvent;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public class ScrollPaneSkin extends SkinBase<ScrollPane, ScrollPaneBehavior>
/*      */   implements TraverseListener
/*      */ {
/*      */   private static final double DEFAULT_PREF_SIZE = 100.0D;
/*      */   private static final double DEFAULT_MIN_SIZE = 36.0D;
/*      */   private static final double DEFAULT_SB_BREADTH = 12.0D;
/*      */   private static final double PAN_THRESHOLD = 0.5D;
/*      */   private Node scrollNode;
/*      */   private double nodeWidth;
/*      */   private double nodeHeight;
/*      */   private double posX;
/*      */   private double posY;
/*      */   private boolean hsbvis;
/*      */   private boolean vsbvis;
/*      */   private double hsbHeight;
/*      */   private double vsbWidth;
/*      */   private StackPane viewRect;
/*      */   private double contentWidth;
/*      */   private double contentHeight;
/*      */   private StackPane corner;
/*      */   protected ScrollBar hsb;
/*      */   protected ScrollBar vsb;
/*      */   double pressX;
/*      */   double pressY;
/*      */   double ohvalue;
/*      */   double ovvalue;
/*  114 */   private Cursor saveCursor = null;
/*  115 */   private boolean dragDetected = false;
/*  116 */   private boolean touchDetected = false;
/*  117 */   private boolean mouseDown = false;
/*      */   Rectangle clipRect;
/*  142 */   private final InvalidationListener nodeListener = new InvalidationListener() {
/*      */     public void invalidated(Observable paramAnonymousObservable) {
/*  144 */       if ((ScrollPaneSkin.this.nodeWidth != -1.0D) && (ScrollPaneSkin.this.nodeHeight != -1.0D))
/*      */       {
/*  146 */         if ((ScrollPaneSkin.this.vsbvis != ScrollPaneSkin.this.determineVerticalSBVisible()) || (ScrollPaneSkin.this.hsbvis != ScrollPaneSkin.this.determineHorizontalSBVisible())) {
/*  147 */           ScrollPaneSkin.this.requestLayout();
/*      */         }
/*      */         else {
/*  150 */           ScrollPaneSkin.this.updateVerticalSB();
/*  151 */           ScrollPaneSkin.this.updateHorizontalSB();
/*      */         }
/*      */       }
/*      */     }
/*  142 */   };
/*      */ 
/*  161 */   private final ChangeListener<Bounds> boundsChangeListener = new ChangeListener()
/*      */   {
/*      */     public void changed(ObservableValue<? extends Bounds> paramAnonymousObservableValue, Bounds paramAnonymousBounds1, Bounds paramAnonymousBounds2)
/*      */     {
/*  169 */       double d1 = paramAnonymousBounds1.getHeight();
/*  170 */       double d2 = paramAnonymousBounds2.getHeight();
/*      */       double d5;
/*  171 */       if (d1 != d2) {
/*  172 */         d3 = ScrollPaneSkin.this.snapPosition(ScrollPaneSkin.this.getInsets().getTop() - ScrollPaneSkin.this.posY / (ScrollPaneSkin.this.vsb.getMax() - ScrollPaneSkin.this.vsb.getMin()) * (d1 - ScrollPaneSkin.this.contentHeight));
/*  173 */         d4 = ScrollPaneSkin.this.snapPosition(ScrollPaneSkin.this.getInsets().getTop() - ScrollPaneSkin.this.posY / (ScrollPaneSkin.this.vsb.getMax() - ScrollPaneSkin.this.vsb.getMin()) * (d2 - ScrollPaneSkin.this.contentHeight));
/*      */ 
/*  175 */         d5 = d3 / d4 * ScrollPaneSkin.this.vsb.getValue();
/*  176 */         if (d5 < 0.0D) {
/*  177 */           ScrollPaneSkin.this.vsb.setValue(0.0D);
/*      */         }
/*  179 */         else if (d5 < 1.0D) {
/*  180 */           ScrollPaneSkin.this.vsb.setValue(d5);
/*      */         }
/*  182 */         else if (d5 > 1.0D) {
/*  183 */           ScrollPaneSkin.this.vsb.setValue(1.0D);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  193 */       double d3 = paramAnonymousBounds1.getWidth();
/*  194 */       double d4 = paramAnonymousBounds2.getWidth();
/*  195 */       if (d3 != d4) {
/*  196 */         d5 = ScrollPaneSkin.this.snapPosition(ScrollPaneSkin.this.getInsets().getLeft() - ScrollPaneSkin.this.posX / (ScrollPaneSkin.this.hsb.getMax() - ScrollPaneSkin.this.hsb.getMin()) * (d3 - ScrollPaneSkin.this.contentWidth));
/*  197 */         double d6 = ScrollPaneSkin.this.snapPosition(ScrollPaneSkin.this.getInsets().getLeft() - ScrollPaneSkin.this.posX / (ScrollPaneSkin.this.hsb.getMax() - ScrollPaneSkin.this.hsb.getMin()) * (d4 - ScrollPaneSkin.this.contentWidth));
/*      */ 
/*  199 */         double d7 = d5 / d6 * ScrollPaneSkin.this.hsb.getValue();
/*  200 */         if (d7 < 0.0D) {
/*  201 */           ScrollPaneSkin.this.hsb.setValue(0.0D);
/*      */         }
/*  203 */         else if (d7 < 1.0D) {
/*  204 */           ScrollPaneSkin.this.hsb.setValue(d7);
/*      */         }
/*  206 */         else if (d7 > 1.0D)
/*  207 */           ScrollPaneSkin.this.hsb.setValue(1.0D);
/*      */       }
/*      */     }
/*  161 */   };
/*      */   Timeline sbTouchTimeline;
/*      */   KeyFrame sbTouchKF1;
/*      */   KeyFrame sbTouchKF2;
/*      */   Timeline contentsToViewTimeline;
/*      */   KeyFrame contentsToViewKF1;
/*      */   KeyFrame contentsToViewKF2;
/*      */   KeyFrame contentsToViewKF3;
/*      */   private boolean tempVisibility;
/*      */   private DoubleProperty contentPosX;
/*      */   private DoubleProperty contentPosY;
/*      */ 
/*      */   public ScrollPaneSkin(ScrollPane paramScrollPane)
/*      */   {
/*  128 */     super(paramScrollPane, new ScrollPaneBehavior(paramScrollPane));
/*  129 */     initialize();
/*      */ 
/*  131 */     registerChangeListener(paramScrollPane.contentProperty(), "NODE");
/*  132 */     registerChangeListener(paramScrollPane.fitToWidthProperty(), "FIT_TO_WIDTH");
/*  133 */     registerChangeListener(paramScrollPane.fitToHeightProperty(), "FIT_TO_HEIGHT");
/*  134 */     registerChangeListener(paramScrollPane.hbarPolicyProperty(), "HBAR_POLICY");
/*  135 */     registerChangeListener(paramScrollPane.vbarPolicyProperty(), "VBAR_POLICY");
/*  136 */     registerChangeListener(paramScrollPane.hvalueProperty(), "HVALUE");
/*  137 */     registerChangeListener(paramScrollPane.vvalueProperty(), "VVALUE");
/*  138 */     registerChangeListener(paramScrollPane.prefViewportWidthProperty(), "PREF_VIEWPORT_WIDTH");
/*  139 */     registerChangeListener(paramScrollPane.prefViewportHeightProperty(), "PREF_VIEWPORT_HEIGHT");
/*      */   }
/*      */ 
/*      */   private void initialize()
/*      */   {
/*  216 */     setManaged(false);
/*      */ 
/*  218 */     ScrollPane localScrollPane = (ScrollPane)getSkinnable();
/*  219 */     this.scrollNode = localScrollPane.getContent();
/*      */ 
/*  221 */     if (this.scrollNode != null) {
/*  222 */       this.scrollNode.layoutBoundsProperty().addListener(this.nodeListener);
/*  223 */       this.scrollNode.layoutBoundsProperty().addListener(this.boundsChangeListener);
/*      */     }
/*      */ 
/*  226 */     this.viewRect = new StackPane()
/*      */     {
/*      */       public void requestLayout() {
/*  229 */         ScrollPaneSkin.this.nodeWidth = -1.0D;
/*  230 */         ScrollPaneSkin.this.nodeHeight = -1.0D;
/*  231 */         super.requestLayout();
/*      */       }
/*      */       protected void layoutChildren() {
/*  234 */         if ((ScrollPaneSkin.this.nodeWidth == -1.0D) || (ScrollPaneSkin.this.nodeHeight == -1.0D)) {
/*  235 */           ScrollPaneSkin.this.computeScrollNodeSize(getWidth(), getHeight());
/*      */         }
/*  237 */         if ((ScrollPaneSkin.this.scrollNode != null) && (ScrollPaneSkin.this.scrollNode.isResizable())) {
/*  238 */           ScrollPaneSkin.this.scrollNode.resize(snapSize(ScrollPaneSkin.this.nodeWidth), snapSize(ScrollPaneSkin.this.nodeHeight));
/*  239 */           if ((ScrollPaneSkin.this.vsbvis != ScrollPaneSkin.this.determineVerticalSBVisible()) || (ScrollPaneSkin.this.hsbvis != ScrollPaneSkin.this.determineHorizontalSBVisible())) {
/*  240 */             ScrollPaneSkin.this.requestLayout();
/*      */           }
/*      */         }
/*  243 */         if (ScrollPaneSkin.this.scrollNode != null)
/*  244 */           ScrollPaneSkin.this.scrollNode.relocate(0.0D, 0.0D);
/*      */       }
/*      */     };
/*  249 */     this.viewRect.setManaged(false);
/*      */ 
/*  251 */     this.clipRect = new Rectangle();
/*  252 */     this.viewRect.setClip(this.clipRect);
/*      */ 
/*  254 */     this.hsb = new ScrollBar();
/*      */ 
/*  256 */     this.vsb = new ScrollBar();
/*  257 */     this.vsb.setOrientation(Orientation.VERTICAL);
/*      */ 
/*  259 */     this.corner = new StackPane();
/*  260 */     this.corner.getStyleClass().setAll(new String[] { "corner" });
/*      */ 
/*  262 */     this.viewRect.getChildren().clear();
/*  263 */     if (this.scrollNode != null) {
/*  264 */       this.viewRect.getChildren().add(this.scrollNode);
/*      */     }
/*      */ 
/*  267 */     getChildren().clear();
/*  268 */     getChildren().addAll(new Node[] { this.viewRect, this.vsb, this.hsb, this.corner });
/*      */ 
/*  273 */     InvalidationListener local4 = new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  275 */         if (!PlatformUtil.isEmbedded()) {
/*  276 */           ScrollPaneSkin.this.posY = com.sun.javafx.Utils.clamp(((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmin(), ScrollPaneSkin.this.vsb.getValue(), ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmax());
/*      */         }
/*      */         else {
/*  279 */           ScrollPaneSkin.this.posY = ScrollPaneSkin.this.vsb.getValue();
/*      */         }
/*  281 */         ScrollPaneSkin.this.updatePosY();
/*      */       }
/*      */     };
/*  284 */     this.vsb.valueProperty().addListener(local4);
/*      */ 
/*  286 */     InvalidationListener local5 = new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  288 */         if (!PlatformUtil.isEmbedded()) {
/*  289 */           ScrollPaneSkin.this.posX = com.sun.javafx.Utils.clamp(((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmin(), ScrollPaneSkin.this.hsb.getValue(), ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmax());
/*      */         }
/*      */         else {
/*  292 */           ScrollPaneSkin.this.posX = ScrollPaneSkin.this.hsb.getValue();
/*      */         }
/*  294 */         ScrollPaneSkin.this.updatePosX();
/*      */       }
/*      */     };
/*  297 */     this.hsb.valueProperty().addListener(local5);
/*      */ 
/*  299 */     this.viewRect.setOnMousePressed(new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  301 */         if (PlatformUtil.isEmbedded()) {
/*  302 */           ScrollPaneSkin.this.startSBReleasedAnimation();
/*      */         }
/*  304 */         ScrollPaneSkin.this.mouseDown = true;
/*  305 */         ScrollPaneSkin.this.pressX = (paramAnonymousMouseEvent.getX() + ScrollPaneSkin.this.viewRect.getLayoutX());
/*  306 */         ScrollPaneSkin.this.pressY = (paramAnonymousMouseEvent.getY() + ScrollPaneSkin.this.viewRect.getLayoutY());
/*  307 */         ScrollPaneSkin.this.ohvalue = ScrollPaneSkin.this.hsb.getValue();
/*  308 */         ScrollPaneSkin.this.ovvalue = ScrollPaneSkin.this.vsb.getValue();
/*      */       }
/*      */     });
/*  313 */     this.viewRect.setOnDragDetected(new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  315 */         if (PlatformUtil.isEmbedded()) {
/*  316 */           ScrollPaneSkin.this.startSBReleasedAnimation();
/*      */         }
/*  318 */         if (((ScrollPane)ScrollPaneSkin.this.getSkinnable()).isPannable()) {
/*  319 */           ScrollPaneSkin.this.dragDetected = true;
/*  320 */           if (ScrollPaneSkin.this.saveCursor == null) {
/*  321 */             ScrollPaneSkin.this.saveCursor = ScrollPaneSkin.this.getCursor();
/*  322 */             if (ScrollPaneSkin.this.saveCursor == null) {
/*  323 */               ScrollPaneSkin.this.saveCursor = Cursor.DEFAULT;
/*      */             }
/*  325 */             ScrollPaneSkin.this.setCursor(Cursor.MOVE);
/*  326 */             ScrollPaneSkin.this.requestLayout();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  332 */     this.viewRect.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  334 */         if (PlatformUtil.isEmbedded()) {
/*  335 */           ScrollPaneSkin.this.startSBReleasedAnimation();
/*      */         }
/*  337 */         ScrollPaneSkin.this.mouseDown = false;
/*      */ 
/*  339 */         if (ScrollPaneSkin.this.dragDetected == true) {
/*  340 */           if (ScrollPaneSkin.this.saveCursor != null) {
/*  341 */             ScrollPaneSkin.this.setCursor(ScrollPaneSkin.this.saveCursor);
/*  342 */             ScrollPaneSkin.this.saveCursor = null;
/*  343 */             ScrollPaneSkin.this.requestLayout();
/*      */           }
/*  345 */           ScrollPaneSkin.this.dragDetected = false;
/*      */         }
/*      */ 
/*  352 */         if (((ScrollPaneSkin.this.posY > ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmax()) || (ScrollPaneSkin.this.posY < ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmin()) || (ScrollPaneSkin.this.posX > ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmax()) || (ScrollPaneSkin.this.posX < ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmin())) && (!ScrollPaneSkin.this.touchDetected))
/*      */         {
/*  354 */           ScrollPaneSkin.this.startContentsToViewport();
/*      */         }
/*      */       }
/*      */     });
/*  358 */     this.viewRect.setOnMouseDragged(new EventHandler() {
/*      */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  360 */         if (PlatformUtil.isEmbedded()) {
/*  361 */           ScrollPaneSkin.this.startSBReleasedAnimation();
/*      */         }
/*      */ 
/*  366 */         if ((((ScrollPane)ScrollPaneSkin.this.getSkinnable()).isPannable()) || (PlatformUtil.isEmbedded())) {
/*  367 */           double d1 = ScrollPaneSkin.this.pressX - (paramAnonymousMouseEvent.getX() + ScrollPaneSkin.this.viewRect.getLayoutX());
/*  368 */           double d2 = ScrollPaneSkin.this.pressY - (paramAnonymousMouseEvent.getY() + ScrollPaneSkin.this.viewRect.getLayoutY());
/*      */           double d3;
/*  372 */           if ((ScrollPaneSkin.this.hsb.getVisibleAmount() > 0.0D) && (ScrollPaneSkin.this.hsb.getVisibleAmount() < ScrollPaneSkin.this.hsb.getMax()) && 
/*  373 */             (Math.abs(d1) > 0.5D)) {
/*  374 */             d3 = ScrollPaneSkin.this.ohvalue + d1 / (ScrollPaneSkin.this.nodeWidth - ScrollPaneSkin.this.viewRect.getWidth()) * (ScrollPaneSkin.this.hsb.getMax() - ScrollPaneSkin.this.hsb.getMin());
/*  375 */             if (!PlatformUtil.isEmbedded()) {
/*  376 */               if (d3 > ScrollPaneSkin.this.hsb.getMax()) {
/*  377 */                 d3 = ScrollPaneSkin.this.hsb.getMax();
/*      */               }
/*  379 */               else if (d3 < ScrollPaneSkin.this.hsb.getMin()) {
/*  380 */                 d3 = ScrollPaneSkin.this.hsb.getMin();
/*      */               }
/*  382 */               ScrollPaneSkin.this.hsb.setValue(d3);
/*      */             }
/*      */             else {
/*  385 */               ScrollPaneSkin.this.hsb.setValue(d3);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  392 */           if ((ScrollPaneSkin.this.vsb.getVisibleAmount() > 0.0D) && (ScrollPaneSkin.this.vsb.getVisibleAmount() < ScrollPaneSkin.this.vsb.getMax()) && 
/*  393 */             (Math.abs(d2) > 0.5D)) {
/*  394 */             d3 = ScrollPaneSkin.this.ovvalue + d2 / (ScrollPaneSkin.this.nodeHeight - ScrollPaneSkin.this.viewRect.getHeight()) * (ScrollPaneSkin.this.vsb.getMax() - ScrollPaneSkin.this.vsb.getMin());
/*  395 */             if (!PlatformUtil.isEmbedded()) {
/*  396 */               if (d3 > ScrollPaneSkin.this.vsb.getMax()) {
/*  397 */                 d3 = ScrollPaneSkin.this.vsb.getMax();
/*      */               }
/*  399 */               else if (d3 < ScrollPaneSkin.this.vsb.getMin()) {
/*  400 */                 d3 = ScrollPaneSkin.this.vsb.getMin();
/*      */               }
/*  402 */               ScrollPaneSkin.this.vsb.setValue(d3);
/*      */             }
/*      */             else {
/*  405 */               ScrollPaneSkin.this.vsb.setValue(d3);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  414 */         paramAnonymousMouseEvent.consume();
/*      */       }
/*      */     });
/*  424 */     final EventDispatcher local10 = new EventDispatcher()
/*      */     {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  427 */         return paramAnonymousEvent;
/*      */       }
/*      */     };
/*  431 */     final EventDispatcher localEventDispatcher1 = this.hsb.getEventDispatcher();
/*  432 */     this.hsb.setEventDispatcher(new EventDispatcher() {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  434 */         if ((paramAnonymousEvent.getEventType() == ScrollEvent.SCROLL) && (!((ScrollEvent)paramAnonymousEvent).isDirect()))
/*      */         {
/*  436 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(local10);
/*  437 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(localEventDispatcher1);
/*  438 */           return paramAnonymousEventDispatchChain.dispatchEvent(paramAnonymousEvent);
/*      */         }
/*  440 */         return localEventDispatcher1.dispatchEvent(paramAnonymousEvent, paramAnonymousEventDispatchChain);
/*      */       }
/*      */     });
/*  444 */     final EventDispatcher localEventDispatcher2 = this.vsb.getEventDispatcher();
/*  445 */     this.vsb.setEventDispatcher(new EventDispatcher() {
/*      */       public Event dispatchEvent(Event paramAnonymousEvent, EventDispatchChain paramAnonymousEventDispatchChain) {
/*  447 */         if ((paramAnonymousEvent.getEventType() == ScrollEvent.SCROLL) && (!((ScrollEvent)paramAnonymousEvent).isDirect()))
/*      */         {
/*  449 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(local10);
/*  450 */           paramAnonymousEventDispatchChain = paramAnonymousEventDispatchChain.prepend(localEventDispatcher2);
/*  451 */           return paramAnonymousEventDispatchChain.dispatchEvent(paramAnonymousEvent);
/*      */         }
/*  453 */         return localEventDispatcher2.dispatchEvent(paramAnonymousEvent, paramAnonymousEventDispatchChain);
/*      */       }
/*      */     });
/*  462 */     setOnScroll(new EventHandler() {
/*      */       public void handle(ScrollEvent paramAnonymousScrollEvent) {
/*  464 */         if (PlatformUtil.isEmbedded())
/*  465 */           ScrollPaneSkin.this.startSBReleasedAnimation();
/*      */         double d1;
/*      */         double d2;
/*      */         double d3;
/*  471 */         if (ScrollPaneSkin.this.vsb.getVisibleAmount() < ScrollPaneSkin.this.vsb.getMax()) {
/*  472 */           d1 = ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmax() - ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getVmin();
/*      */ 
/*  474 */           if (ScrollPaneSkin.this.nodeHeight > 0.0D) {
/*  475 */             d2 = d1 / ScrollPaneSkin.this.nodeHeight;
/*      */           }
/*      */           else {
/*  478 */             d2 = 0.0D;
/*      */           }
/*  480 */           d3 = ScrollPaneSkin.this.vsb.getValue() + -paramAnonymousScrollEvent.getDeltaY() * d2;
/*  481 */           if (!PlatformUtil.isEmbedded()) {
/*  482 */             if (((paramAnonymousScrollEvent.getDeltaY() > 0.0D) && (ScrollPaneSkin.this.vsb.getValue() > ScrollPaneSkin.this.vsb.getMin())) || ((paramAnonymousScrollEvent.getDeltaY() < 0.0D) && (ScrollPaneSkin.this.vsb.getValue() < ScrollPaneSkin.this.vsb.getMax())))
/*      */             {
/*  484 */               ScrollPaneSkin.this.vsb.setValue(d3);
/*  485 */               paramAnonymousScrollEvent.consume();
/*      */             }
/*      */ 
/*      */           }
/*  493 */           else if ((!paramAnonymousScrollEvent.isInertia()) || ((paramAnonymousScrollEvent.isInertia()) && ((ScrollPaneSkin.this.contentsToViewTimeline == null) || (ScrollPaneSkin.this.contentsToViewTimeline.getStatus() == Animation.Status.STOPPED)))) {
/*  494 */             ScrollPaneSkin.this.vsb.setValue(d3);
/*  495 */             if (((d3 > ScrollPaneSkin.this.vsb.getMax()) || (d3 < ScrollPaneSkin.this.vsb.getMin())) && (!ScrollPaneSkin.this.mouseDown) && (!ScrollPaneSkin.this.touchDetected)) {
/*  496 */               ScrollPaneSkin.this.startContentsToViewport();
/*      */             }
/*  498 */             paramAnonymousScrollEvent.consume();
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  503 */         if (ScrollPaneSkin.this.hsb.getVisibleAmount() < ScrollPaneSkin.this.hsb.getMax()) {
/*  504 */           d1 = ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmax() - ((ScrollPane)ScrollPaneSkin.this.getSkinnable()).getHmin();
/*      */ 
/*  506 */           if (ScrollPaneSkin.this.nodeWidth > 0.0D) {
/*  507 */             d2 = d1 / ScrollPaneSkin.this.nodeWidth;
/*      */           }
/*      */           else {
/*  510 */             d2 = 0.0D;
/*      */           }
/*      */ 
/*  513 */           d3 = ScrollPaneSkin.this.hsb.getValue() + -paramAnonymousScrollEvent.getDeltaX() * d2;
/*  514 */           if (!PlatformUtil.isEmbedded()) {
/*  515 */             if (((paramAnonymousScrollEvent.getDeltaX() > 0.0D) && (ScrollPaneSkin.this.hsb.getValue() > ScrollPaneSkin.this.hsb.getMin())) || ((paramAnonymousScrollEvent.getDeltaX() < 0.0D) && (ScrollPaneSkin.this.hsb.getValue() < ScrollPaneSkin.this.hsb.getMax())))
/*      */             {
/*  517 */               ScrollPaneSkin.this.hsb.setValue(d3);
/*  518 */               paramAnonymousScrollEvent.consume();
/*      */             }
/*      */ 
/*      */           }
/*  526 */           else if ((!paramAnonymousScrollEvent.isInertia()) || ((paramAnonymousScrollEvent.isInertia()) && ((ScrollPaneSkin.this.contentsToViewTimeline == null) || (ScrollPaneSkin.this.contentsToViewTimeline.getStatus() == Animation.Status.STOPPED)))) {
/*  527 */             ScrollPaneSkin.this.hsb.setValue(d3);
/*      */ 
/*  529 */             if (((d3 > ScrollPaneSkin.this.hsb.getMax()) || (d3 < ScrollPaneSkin.this.hsb.getMin())) && (!ScrollPaneSkin.this.mouseDown) && (!ScrollPaneSkin.this.touchDetected)) {
/*  530 */               ScrollPaneSkin.this.startContentsToViewport();
/*      */             }
/*  532 */             paramAnonymousScrollEvent.consume();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  543 */     setOnTouchPressed(new EventHandler() {
/*      */       public void handle(TouchEvent paramAnonymousTouchEvent) {
/*  545 */         ScrollPaneSkin.this.touchDetected = true;
/*  546 */         ScrollPaneSkin.this.startSBReleasedAnimation();
/*  547 */         paramAnonymousTouchEvent.consume();
/*      */       }
/*      */     });
/*  551 */     setOnTouchReleased(new EventHandler() {
/*      */       public void handle(TouchEvent paramAnonymousTouchEvent) {
/*  553 */         ScrollPaneSkin.this.touchDetected = false;
/*  554 */         ScrollPaneSkin.this.startSBReleasedAnimation();
/*  555 */         paramAnonymousTouchEvent.consume();
/*      */       }
/*      */     });
/*  559 */     TraversalEngine localTraversalEngine = new TraversalEngine(this, false);
/*  560 */     localTraversalEngine.addTraverseListener(this);
/*  561 */     setImpl_traversalEngine(localTraversalEngine);
/*      */ 
/*  564 */     consumeMouseEvents(false);
/*      */   }
/*      */ 
/*      */   protected void handleControlPropertyChanged(String paramString)
/*      */   {
/*  569 */     super.handleControlPropertyChanged(paramString);
/*  570 */     if (paramString == "NODE") {
/*  571 */       if (this.scrollNode != ((ScrollPane)getSkinnable()).getContent()) {
/*  572 */         if (this.scrollNode != null) {
/*  573 */           this.scrollNode.layoutBoundsProperty().removeListener(this.nodeListener);
/*  574 */           this.scrollNode.layoutBoundsProperty().removeListener(this.boundsChangeListener);
/*  575 */           this.viewRect.getChildren().remove(this.scrollNode);
/*      */         }
/*  577 */         this.scrollNode = ((ScrollPane)getSkinnable()).getContent();
/*  578 */         if (this.scrollNode != null) {
/*  579 */           this.nodeWidth = snapSize(this.scrollNode.getLayoutBounds().getWidth());
/*  580 */           this.nodeHeight = snapSize(this.scrollNode.getLayoutBounds().getHeight());
/*  581 */           this.viewRect.getChildren().setAll(new Node[] { this.scrollNode });
/*  582 */           this.scrollNode.layoutBoundsProperty().addListener(this.nodeListener);
/*  583 */           this.scrollNode.layoutBoundsProperty().addListener(this.boundsChangeListener);
/*      */         }
/*      */       }
/*  586 */       requestLayout();
/*  587 */     } else if (paramString == "FIT_TO_WIDTH") {
/*  588 */       requestLayout();
/*  589 */       this.viewRect.requestLayout();
/*  590 */     } else if (paramString == "FIT_TO_HEIGHT") {
/*  591 */       requestLayout();
/*  592 */       this.viewRect.requestLayout();
/*  593 */     } else if (paramString == "HBAR_POLICY")
/*      */     {
/*  595 */       ((ScrollPane)getSkinnable()).requestLayout();
/*  596 */       requestLayout();
/*  597 */     } else if (paramString == "VBAR_POLICY")
/*      */     {
/*  599 */       ((ScrollPane)getSkinnable()).requestLayout();
/*  600 */       requestLayout();
/*  601 */     } else if (paramString == "HVALUE") {
/*  602 */       this.hsb.setValue(((ScrollPane)getSkinnable()).getHvalue());
/*  603 */     } else if (paramString == "VVALUE") {
/*  604 */       this.vsb.setValue(((ScrollPane)getSkinnable()).getVvalue());
/*  605 */     } else if (paramString == "PREF_VIEWPORT_WIDTH")
/*      */     {
/*  607 */       ((ScrollPane)getSkinnable()).requestLayout();
/*  608 */     } else if (paramString == "PREF_VIEWPORT_HEIGHT")
/*      */     {
/*  610 */       ((ScrollPane)getSkinnable()).requestLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void onTraverse(Node paramNode, Bounds paramBounds)
/*      */   {
/*  618 */     double d1 = 0.0D;
/*  619 */     double d2 = 0.0D;
/*  620 */     int i = 0;
/*  621 */     if (paramBounds.getMaxX() > this.contentWidth) {
/*  622 */       d1 = this.contentWidth - paramBounds.getMaxX();
/*      */     }
/*  624 */     if (paramBounds.getMinX() < 0.0D) {
/*  625 */       d1 = -paramBounds.getMinX();
/*      */     }
/*  627 */     if (paramBounds.getMaxY() > this.contentHeight) {
/*  628 */       d2 = this.contentHeight - paramBounds.getMaxY();
/*      */     }
/*  630 */     if (paramBounds.getMinY() < 0.0D) {
/*  631 */       d2 = -paramBounds.getMinY();
/*      */     }
/*      */ 
/*  636 */     double d3 = this.hsb.getValue();
/*  637 */     double d4 = this.vsb.getValue();
/*      */     double d5;
/*  638 */     if (d1 != 0.0D) {
/*  639 */       d5 = -d1 * (this.hsb.getMax() - this.hsb.getMin()) / (this.nodeWidth - this.viewRect.getWidth());
/*  640 */       if (d5 < 0.0D)
/*  641 */         d5 -= this.hsb.getUnitIncrement();
/*      */       else {
/*  643 */         d5 += this.hsb.getUnitIncrement();
/*      */       }
/*  645 */       d3 = com.sun.javafx.Utils.clamp(this.hsb.getMin(), this.hsb.getValue() + d5, this.hsb.getMax());
/*  646 */       this.hsb.setValue(d3);
/*  647 */       i = 1;
/*      */     }
/*  649 */     if (d2 != 0.0D) {
/*  650 */       d5 = -d2 * (this.vsb.getMax() - this.vsb.getMin()) / (this.nodeHeight - this.viewRect.getHeight());
/*  651 */       if (d5 < 0.0D)
/*  652 */         d5 -= this.vsb.getUnitIncrement();
/*      */       else {
/*  654 */         d5 += this.vsb.getUnitIncrement();
/*      */       }
/*  656 */       d4 = com.sun.javafx.Utils.clamp(this.vsb.getMin(), this.vsb.getValue() + d5, this.vsb.getMax());
/*  657 */       this.vsb.setValue(d4);
/*  658 */       i = 1;
/*      */     }
/*      */ 
/*  661 */     if (i == 1)
/*  662 */       requestLayout();
/*      */   }
/*      */ 
/*      */   public void hsbIncrement()
/*      */   {
/*  667 */     if (this.hsb != null) this.hsb.increment(); 
/*      */   }
/*      */ 
/*  670 */   public void hsbDecrement() { if (this.hsb != null) this.hsb.decrement();
/*      */   }
/*      */ 
/*      */   public void hsbPageIncrement()
/*      */   {
/*  675 */     if (this.hsb != null) this.hsb.increment(); 
/*      */   }
/*      */ 
/*      */   public void hsbPageDecrement()
/*      */   {
/*  679 */     if (this.hsb != null) this.hsb.decrement(); 
/*      */   }
/*      */ 
/*      */   public void vsbIncrement()
/*      */   {
/*  683 */     if (this.vsb != null) this.vsb.increment(); 
/*      */   }
/*      */ 
/*  686 */   public void vsbDecrement() { if (this.vsb != null) this.vsb.decrement();
/*      */   }
/*      */ 
/*      */   public void vsbPageIncrement()
/*      */   {
/*  691 */     if (this.vsb != null) this.vsb.increment(); 
/*      */   }
/*      */ 
/*      */   public void vsbPageDecrement()
/*      */   {
/*  695 */     if (this.vsb != null) this.vsb.decrement();
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  705 */     if (((ScrollPane)getSkinnable()).getPrefViewportWidth() > 0.0D) {
/*  706 */       double d = ((ScrollPane)getSkinnable()).getVbarPolicy() == ScrollPane.ScrollBarPolicy.ALWAYS ? this.vsb.prefWidth(-1.0D) : 0.0D;
/*  707 */       return ((ScrollPane)getSkinnable()).getPrefViewportWidth() + d + getInsets().getLeft() + getInsets().getRight();
/*      */     }
/*      */ 
/*  710 */     return 100.0D;
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble)
/*      */   {
/*  715 */     if (((ScrollPane)getSkinnable()).getPrefViewportHeight() > 0.0D) {
/*  716 */       double d = ((ScrollPane)getSkinnable()).getHbarPolicy() == ScrollPane.ScrollBarPolicy.ALWAYS ? this.hsb.prefHeight(-1.0D) : 0.0D;
/*  717 */       return ((ScrollPane)getSkinnable()).getPrefViewportHeight() + d + getInsets().getTop() + getInsets().getBottom();
/*      */     }
/*      */ 
/*  720 */     return 100.0D;
/*      */   }
/*      */ 
/*      */   protected double computeMinWidth(double paramDouble)
/*      */   {
/*  725 */     double d = this.corner.minWidth(-1.0D);
/*  726 */     return d > 0.0D ? 3.0D * d : 36.0D;
/*      */   }
/*      */ 
/*      */   protected double computeMinHeight(double paramDouble) {
/*  730 */     double d = this.corner.minHeight(-1.0D);
/*  731 */     return d > 0.0D ? 3.0D * d : 36.0D;
/*      */   }
/*      */ 
/*      */   protected void layoutChildren() {
/*  735 */     ScrollPane localScrollPane = (ScrollPane)getSkinnable();
/*      */ 
/*  737 */     this.vsb.setMin(localScrollPane.getVmin());
/*  738 */     this.vsb.setMax(localScrollPane.getVmax());
/*      */ 
/*  741 */     this.hsb.setMin(localScrollPane.getHmin());
/*  742 */     this.hsb.setMax(localScrollPane.getHmax());
/*      */ 
/*  744 */     this.contentWidth = (localScrollPane.getWidth() - (getInsets().getLeft() + getInsets().getRight()));
/*  745 */     this.contentHeight = (localScrollPane.getHeight() - (getInsets().getTop() + getInsets().getBottom()));
/*      */ 
/*  750 */     double d1 = this.contentWidth + getPadding().getLeft() + getPadding().getRight();
/*  751 */     double d2 = this.contentHeight + getPadding().getTop() + getPadding().getBottom();
/*      */ 
/*  753 */     computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*  754 */     computeScrollBarSize();
/*  755 */     this.vsbvis = determineVerticalSBVisible();
/*  756 */     this.hsbvis = determineHorizontalSBVisible();
/*      */ 
/*  758 */     if (this.vsbvis) {
/*  759 */       d1 -= this.vsbWidth;
/*  760 */       if (!PlatformUtil.isEmbedded()) {
/*  761 */         this.contentWidth -= this.vsbWidth;
/*      */       }
/*      */     }
/*  764 */     if (this.hsbvis) {
/*  765 */       d2 -= this.hsbHeight;
/*  766 */       if (!PlatformUtil.isEmbedded()) {
/*  767 */         this.contentHeight -= this.hsbHeight;
/*      */       }
/*      */     }
/*  770 */     if ((this.scrollNode != null) && (this.scrollNode.isResizable()))
/*      */     {
/*  772 */       if ((this.vsbvis) && (this.hsbvis))
/*      */       {
/*  774 */         computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*      */       }
/*  776 */       else if ((this.hsbvis) && (!this.vsbvis)) {
/*  777 */         computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*  778 */         this.vsbvis = determineVerticalSBVisible();
/*  779 */         if (this.vsbvis)
/*      */         {
/*  781 */           this.contentWidth -= this.vsbWidth;
/*  782 */           computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*      */         }
/*  784 */       } else if ((this.vsbvis) && (!this.hsbvis)) {
/*  785 */         computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*  786 */         this.hsbvis = determineHorizontalSBVisible();
/*  787 */         if (this.hsbvis)
/*      */         {
/*  789 */           this.contentHeight -= this.hsbHeight;
/*  790 */           computeScrollNodeSize(this.contentWidth, this.contentHeight);
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  796 */     double d3 = getInsets().getLeft() - getPadding().getLeft();
/*  797 */     double d4 = getInsets().getTop() - getPadding().getTop();
/*      */ 
/*  799 */     this.vsb.setVisible(this.vsbvis);
/*  800 */     if (this.vsbvis)
/*      */     {
/*  813 */       if (getPadding().getRight() < 1.0D) {
/*  814 */         this.vsb.resizeRelocate(snapPosition(localScrollPane.getWidth() - (this.vsbWidth + (getInsets().getRight() - getPadding().getRight()))), snapPosition(d4), snapSize(this.vsbWidth), snapSize(d2));
/*      */       }
/*      */       else {
/*  817 */         this.vsb.resizeRelocate(snapPosition(localScrollPane.getWidth() - (this.vsbWidth + 1.0D + (getInsets().getRight() - getPadding().getRight()))), snapPosition(d4), snapSize(this.vsbWidth) + 1.0D, snapSize(d2));
/*      */       }
/*      */     }
/*  820 */     updateVerticalSB();
/*      */ 
/*  822 */     this.hsb.setVisible(this.hsbvis);
/*  823 */     if (this.hsbvis)
/*      */     {
/*  836 */       if (getPadding().getBottom() < 1.0D) {
/*  837 */         this.hsb.resizeRelocate(snapPosition(d3), snapPosition(localScrollPane.getHeight() - (this.hsbHeight + (getInsets().getBottom() - getPadding().getBottom()))), snapSize(d1), snapSize(this.hsbHeight));
/*      */       }
/*      */       else {
/*  840 */         this.hsb.resizeRelocate(snapPosition(d3), snapPosition(localScrollPane.getHeight() - (this.hsbHeight + 1.0D + (getInsets().getBottom() - getPadding().getBottom()))), snapSize(d1), snapSize(this.hsbHeight) + 1.0D);
/*      */       }
/*      */     }
/*  843 */     updateHorizontalSB();
/*      */ 
/*  845 */     this.viewRect.resize(snapSize(this.contentWidth), snapSize(this.contentHeight));
/*  846 */     resetClip();
/*      */ 
/*  848 */     if ((this.vsbvis) && (this.hsbvis)) {
/*  849 */       this.corner.setVisible(true);
/*  850 */       double d5 = this.vsbWidth;
/*  851 */       double d6 = this.hsbHeight;
/*      */ 
/*  853 */       if (getPadding().getRight() >= 1.0D) {
/*  854 */         d5 += 1.0D;
/*      */       }
/*  856 */       if (getPadding().getBottom() >= 1.0D) {
/*  857 */         d6 += 1.0D;
/*      */       }
/*  859 */       this.corner.resizeRelocate(snapPosition(this.vsb.getLayoutX()), snapPosition(this.hsb.getLayoutY()), snapSize(d5), snapSize(d6));
/*      */     } else {
/*  861 */       this.corner.setVisible(false);
/*      */     }
/*  863 */     localScrollPane.setViewportBounds(new BoundingBox(snapPosition(this.viewRect.getLayoutX()), snapPosition(this.viewRect.getLayoutY()), snapSize(this.contentWidth), snapSize(this.contentHeight)));
/*      */   }
/*      */ 
/*      */   private void computeScrollNodeSize(double paramDouble1, double paramDouble2) {
/*  867 */     if (this.scrollNode != null)
/*  868 */       if (this.scrollNode.isResizable()) {
/*  869 */         ScrollPane localScrollPane = (ScrollPane)getSkinnable();
/*  870 */         Orientation localOrientation = this.scrollNode.getContentBias();
/*  871 */         if (localOrientation == null) {
/*  872 */           this.nodeWidth = snapSize(Utils.boundedSize(localScrollPane.isFitToWidth() ? paramDouble1 : this.scrollNode.prefWidth(-1.0D), this.scrollNode.minWidth(-1.0D), this.scrollNode.maxWidth(-1.0D)));
/*      */ 
/*  874 */           this.nodeHeight = snapSize(Utils.boundedSize(localScrollPane.isFitToHeight() ? paramDouble2 : this.scrollNode.prefHeight(-1.0D), this.scrollNode.minHeight(-1.0D), this.scrollNode.maxHeight(-1.0D)));
/*      */         }
/*  877 */         else if (localOrientation == Orientation.HORIZONTAL) {
/*  878 */           this.nodeWidth = snapSize(Utils.boundedSize(localScrollPane.isFitToWidth() ? paramDouble1 : this.scrollNode.prefWidth(-1.0D), this.scrollNode.minWidth(-1.0D), this.scrollNode.maxWidth(-1.0D)));
/*      */ 
/*  880 */           this.nodeHeight = snapSize(Utils.boundedSize(localScrollPane.isFitToHeight() ? paramDouble2 : this.scrollNode.prefHeight(this.nodeWidth), this.scrollNode.minHeight(this.nodeWidth), this.scrollNode.maxHeight(this.nodeWidth)));
/*      */         }
/*      */         else
/*      */         {
/*  884 */           this.nodeHeight = snapSize(Utils.boundedSize(localScrollPane.isFitToHeight() ? paramDouble2 : this.scrollNode.prefHeight(-1.0D), this.scrollNode.minHeight(-1.0D), this.scrollNode.maxHeight(-1.0D)));
/*      */ 
/*  886 */           this.nodeWidth = snapSize(Utils.boundedSize(localScrollPane.isFitToWidth() ? paramDouble1 : this.scrollNode.prefWidth(this.nodeHeight), this.scrollNode.minWidth(this.nodeHeight), this.scrollNode.maxWidth(this.nodeHeight)));
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  891 */         this.nodeWidth = snapSize(this.scrollNode.getLayoutBounds().getWidth());
/*  892 */         this.nodeHeight = snapSize(this.scrollNode.getLayoutBounds().getHeight());
/*      */       }
/*      */   }
/*      */ 
/*      */   private boolean determineHorizontalSBVisible()
/*      */   {
/*  898 */     double d = ((ScrollPane)getSkinnable()).getWidth() - getInsets().getLeft() - getInsets().getRight();
/*  899 */     if (PlatformUtil.isEmbedded()) {
/*  900 */       return (this.tempVisibility) && (this.nodeWidth > d);
/*      */     }
/*      */ 
/*  903 */     return !((ScrollPane)getSkinnable()).getHbarPolicy().equals(ScrollPane.ScrollBarPolicy.NEVER);
/*      */   }
/*      */ 
/*      */   private boolean determineVerticalSBVisible()
/*      */   {
/*  911 */     double d = ((ScrollPane)getSkinnable()).getHeight() - getInsets().getTop() - getInsets().getBottom();
/*  912 */     if (PlatformUtil.isEmbedded()) {
/*  913 */       return (this.tempVisibility) && (this.nodeHeight > d);
/*      */     }
/*      */ 
/*  916 */     return !((ScrollPane)getSkinnable()).getVbarPolicy().equals(ScrollPane.ScrollBarPolicy.NEVER);
/*      */   }
/*      */ 
/*      */   private void computeScrollBarSize()
/*      */   {
/*  924 */     this.vsbWidth = snapSize(this.vsb.prefWidth(-1.0D));
/*  925 */     if (this.vsbWidth == 0.0D)
/*      */     {
/*  927 */       this.vsbWidth = 12.0D;
/*      */     }
/*  929 */     this.hsbHeight = snapSize(this.hsb.prefHeight(-1.0D));
/*  930 */     if (this.hsbHeight == 0.0D)
/*      */     {
/*  932 */       this.hsbHeight = 12.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateHorizontalSB() {
/*  937 */     double d = this.nodeWidth * (this.hsb.getMax() - this.hsb.getMin());
/*  938 */     if (d > 0.0D) {
/*  939 */       this.hsb.setVisibleAmount(this.contentWidth / d);
/*  940 */       this.hsb.setBlockIncrement(0.9D * this.hsb.getVisibleAmount());
/*  941 */       this.hsb.setUnitIncrement(0.1D * this.hsb.getVisibleAmount());
/*      */     }
/*      */     else {
/*  944 */       this.hsb.setVisibleAmount(0.0D);
/*  945 */       this.hsb.setBlockIncrement(0.0D);
/*  946 */       this.hsb.setUnitIncrement(0.0D);
/*      */     }
/*      */ 
/*  949 */     if (this.hsb.isVisible()) {
/*  950 */       updatePosX();
/*      */     }
/*  952 */     else if (this.nodeWidth > this.contentWidth)
/*  953 */       updatePosX();
/*      */     else
/*  955 */       this.viewRect.setLayoutX(getInsets().getLeft());
/*      */   }
/*      */ 
/*      */   private void updateVerticalSB()
/*      */   {
/*  961 */     double d = this.nodeHeight * (this.vsb.getMax() - this.vsb.getMin());
/*  962 */     if (d > 0.0D) {
/*  963 */       this.vsb.setVisibleAmount(this.contentHeight / d);
/*  964 */       this.vsb.setBlockIncrement(0.9D * this.vsb.getVisibleAmount());
/*  965 */       this.vsb.setUnitIncrement(0.1D * this.vsb.getVisibleAmount());
/*      */     }
/*      */     else {
/*  968 */       this.vsb.setVisibleAmount(0.0D);
/*  969 */       this.vsb.setBlockIncrement(0.0D);
/*  970 */       this.vsb.setUnitIncrement(0.0D);
/*      */     }
/*      */ 
/*  973 */     if (this.vsb.isVisible()) {
/*  974 */       updatePosY();
/*      */     }
/*  976 */     else if (this.nodeHeight > this.contentHeight)
/*  977 */       updatePosY();
/*      */     else
/*  979 */       this.viewRect.setLayoutY(getInsets().getTop());
/*      */   }
/*      */ 
/*      */   private double updatePosX()
/*      */   {
/*  985 */     this.viewRect.setLayoutX(snapPosition(getInsets().getLeft() - this.posX / (this.hsb.getMax() - this.hsb.getMin()) * (this.nodeWidth - this.contentWidth)));
/*  986 */     ((ScrollPane)getSkinnable()).setHvalue(com.sun.javafx.Utils.clamp(((ScrollPane)getSkinnable()).getHmin(), this.posX, ((ScrollPane)getSkinnable()).getHmax()));
/*  987 */     resetClip();
/*  988 */     return this.posX;
/*      */   }
/*      */ 
/*      */   private double updatePosY() {
/*  992 */     this.viewRect.setLayoutY(snapPosition(getInsets().getTop() - this.posY / (this.vsb.getMax() - this.vsb.getMin()) * (this.nodeHeight - this.contentHeight)));
/*  993 */     ((ScrollPane)getSkinnable()).setVvalue(com.sun.javafx.Utils.clamp(((ScrollPane)getSkinnable()).getVmin(), this.posY, ((ScrollPane)getSkinnable()).getVmax()));
/*  994 */     resetClip();
/*  995 */     return this.posY;
/*      */   }
/*      */ 
/*      */   private void resetClip() {
/*  999 */     this.clipRect.setWidth(snapSize(this.contentWidth));
/* 1000 */     this.clipRect.setHeight(snapSize(this.contentHeight));
/* 1001 */     this.clipRect.relocate(snapPosition(getInsets().getLeft() - this.viewRect.getLayoutX()), snapPosition(getInsets().getTop() - this.viewRect.getLayoutY()));
/*      */   }
/*      */ 
/*      */   void startSBReleasedAnimation()
/*      */   {
/* 1016 */     if (this.sbTouchTimeline == null)
/*      */     {
/* 1021 */       this.sbTouchTimeline = new Timeline();
/* 1022 */       this.sbTouchKF1 = new KeyFrame(Duration.millis(0.0D), new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1024 */           ScrollPaneSkin.this.tempVisibility = true;
/*      */         }
/*      */       }
/*      */       , new KeyValue[0]);
/*      */ 
/* 1028 */       this.sbTouchKF2 = new KeyFrame(Duration.millis(500.0D), new EventHandler() {
/*      */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1030 */           ScrollPaneSkin.this.tempVisibility = false;
/* 1031 */           ScrollPaneSkin.this.requestLayout();
/*      */         }
/*      */       }
/*      */       , new KeyValue[0]);
/*      */ 
/* 1034 */       this.sbTouchTimeline.getKeyFrames().addAll(new KeyFrame[] { this.sbTouchKF1, this.sbTouchKF2 });
/*      */     }
/* 1036 */     this.sbTouchTimeline.playFromStart();
/*      */   }
/*      */ 
/*      */   void startContentsToViewport()
/*      */   {
/* 1042 */     double d1 = this.posX;
/* 1043 */     double d2 = this.posY;
/* 1044 */     double d3 = ((ScrollPane)getSkinnable()).getHmax() - ((ScrollPane)getSkinnable()).getHmin();
/* 1045 */     double d4 = ((ScrollPane)getSkinnable()).getVmax() - ((ScrollPane)getSkinnable()).getVmin();
/*      */ 
/* 1047 */     setContentPosX(this.posX);
/* 1048 */     setContentPosY(this.posY);
/*      */ 
/* 1050 */     if (this.posY > ((ScrollPane)getSkinnable()).getVmax()) {
/* 1051 */       d2 = ((ScrollPane)getSkinnable()).getVmax();
/*      */     }
/* 1053 */     else if (this.posY < ((ScrollPane)getSkinnable()).getVmin()) {
/* 1054 */       d2 = ((ScrollPane)getSkinnable()).getVmin();
/*      */     }
/*      */ 
/* 1058 */     if (this.posX > ((ScrollPane)getSkinnable()).getHmax()) {
/* 1059 */       d1 = ((ScrollPane)getSkinnable()).getHmax();
/*      */     }
/* 1061 */     else if (this.posX < ((ScrollPane)getSkinnable()).getHmin()) {
/* 1062 */       d1 = ((ScrollPane)getSkinnable()).getHmin();
/*      */     }
/*      */ 
/* 1065 */     if (!PlatformUtil.isEmbedded()) {
/* 1066 */       startSBReleasedAnimation();
/*      */     }
/*      */ 
/* 1072 */     if (this.contentsToViewTimeline != null) {
/* 1073 */       this.contentsToViewTimeline.stop();
/*      */     }
/* 1075 */     this.contentsToViewTimeline = new Timeline();
/*      */ 
/* 1079 */     this.contentsToViewKF1 = new KeyFrame(Duration.millis(50.0D), new KeyValue[0]);
/*      */ 
/* 1083 */     this.contentsToViewKF2 = new KeyFrame(Duration.millis(150.0D), new EventHandler() {
/*      */       public void handle(ActionEvent paramAnonymousActionEvent) {
/* 1085 */         ScrollPaneSkin.this.requestLayout();
/*      */       }
/*      */     }
/*      */     , new KeyValue[] { new KeyValue(this.contentPosX, Double.valueOf(d1)), new KeyValue(this.contentPosY, Double.valueOf(d2)) });
/*      */ 
/* 1095 */     this.contentsToViewKF3 = new KeyFrame(Duration.millis(1500.0D), new KeyValue[0]);
/* 1096 */     this.contentsToViewTimeline.getKeyFrames().addAll(new KeyFrame[] { this.contentsToViewKF1, this.contentsToViewKF2, this.contentsToViewKF3 });
/* 1097 */     this.contentsToViewTimeline.playFromStart();
/*      */   }
/*      */ 
/*      */   private void setContentPosX(double paramDouble)
/*      */   {
/* 1102 */     contentPosXProperty().set(paramDouble); } 
/* 1103 */   private double getContentPosX() { return this.contentPosX == null ? 0.0D : this.contentPosX.get(); } 
/*      */   private DoubleProperty contentPosXProperty() {
/* 1105 */     if (this.contentPosX == null) {
/* 1106 */       this.contentPosX = new DoublePropertyBase() {
/*      */         protected void invalidated() {
/* 1108 */           ScrollPaneSkin.this.hsb.setValue(ScrollPaneSkin.this.getContentPosX());
/* 1109 */           ScrollPaneSkin.this.requestLayout();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1114 */           return ScrollPaneSkin.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1119 */           return "contentPosX";
/*      */         }
/*      */       };
/*      */     }
/* 1123 */     return this.contentPosX;
/*      */   }
/*      */ 
/*      */   private void setContentPosY(double paramDouble) {
/* 1127 */     contentPosYProperty().set(paramDouble); } 
/* 1128 */   private double getContentPosY() { return this.contentPosY == null ? 0.0D : this.contentPosY.get(); } 
/*      */   private DoubleProperty contentPosYProperty() {
/* 1130 */     if (this.contentPosY == null) {
/* 1131 */       this.contentPosY = new DoublePropertyBase() {
/*      */         protected void invalidated() {
/* 1133 */           ScrollPaneSkin.this.vsb.setValue(ScrollPaneSkin.this.getContentPosY());
/* 1134 */           ScrollPaneSkin.this.requestLayout();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1139 */           return ScrollPaneSkin.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1144 */           return "contentPosY";
/*      */         }
/*      */       };
/*      */     }
/* 1148 */     return this.contentPosY;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ScrollPaneSkin
 * JD-Core Version:    0.6.2
 */