/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.TitledPaneBehavior;
/*     */ import com.sun.javafx.scene.traversal.Direction;
/*     */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*     */ import com.sun.javafx.scene.traversal.TraverseListener;
/*     */ import java.util.List;
/*     */ import javafx.animation.Animation.Status;
/*     */ import javafx.animation.Interpolator;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.control.ContentDisplay;
/*     */ import javafx.scene.control.Labeled;
/*     */ import javafx.scene.control.TitledPane;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public class TitledPaneSkin extends LabeledSkinBase<TitledPane, TitledPaneBehavior>
/*     */ {
/*     */   public static final int MIN_HEADER_HEIGHT = 22;
/*  61 */   public static final Duration TRANSITION_DURATION = new Duration(350.0D);
/*     */   private final TitleRegion titleRegion;
/*     */   private final ContentContainer contentContainer;
/*     */   private final Content contentRegion;
/*     */   private Timeline timeline;
/*     */   private double transitionStartValue;
/*     */   private Rectangle clipRect;
/*     */   private Pos pos;
/*     */   private HPos hpos;
/*     */   private VPos vpos;
/*     */   private DoubleProperty transition;
/* 252 */   private double prefHeightFromAccordion = 0.0D;
/*     */ 
/*     */   public TitledPaneSkin(TitledPane paramTitledPane)
/*     */   {
/*  74 */     super(paramTitledPane, new TitledPaneBehavior(paramTitledPane));
/*     */ 
/*  76 */     this.clipRect = new Rectangle();
/*  77 */     setClip(this.clipRect);
/*     */ 
/*  79 */     this.transitionStartValue = 0.0D;
/*  80 */     this.titleRegion = new TitleRegion();
/*     */ 
/*  82 */     this.contentRegion = new Content(((TitledPane)getSkinnable()).getContent());
/*  83 */     this.contentContainer = new ContentContainer();
/*  84 */     this.contentContainer.getChildren().setAll(new Node[] { this.contentRegion });
/*     */ 
/*  86 */     if (paramTitledPane.isExpanded())
/*  87 */       setExpanded(paramTitledPane.isExpanded());
/*     */     else {
/*  89 */       setTransition(0.0D);
/*     */     }
/*     */ 
/*  92 */     getChildren().setAll(new Node[] { this.contentContainer, this.titleRegion });
/*     */ 
/*  94 */     registerChangeListener(paramTitledPane.contentProperty(), "CONTENT");
/*  95 */     registerChangeListener(paramTitledPane.expandedProperty(), "EXPANDED");
/*  96 */     registerChangeListener(paramTitledPane.collapsibleProperty(), "COLLAPSIBLE");
/*  97 */     registerChangeListener(paramTitledPane.alignmentProperty(), "ALIGNMENT");
/*  98 */     registerChangeListener(this.titleRegion.alignmentProperty(), "TITLE_REGION_ALIGNMENT");
/*     */ 
/* 100 */     this.pos = paramTitledPane.getAlignment();
/* 101 */     this.hpos = paramTitledPane.getAlignment().getHpos();
/* 102 */     this.vpos = paramTitledPane.getAlignment().getVpos();
/*     */   }
/*     */ 
/*     */   public StackPane getContentRegion() {
/* 106 */     return this.contentRegion;
/*     */   }
/*     */ 
/*     */   protected void setWidth(double paramDouble) {
/* 110 */     super.setWidth(paramDouble);
/* 111 */     this.clipRect.setWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected void setHeight(double paramDouble) {
/* 115 */     super.setHeight(paramDouble);
/* 116 */     this.clipRect.setHeight(paramDouble);
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 121 */     super.handleControlPropertyChanged(paramString);
/* 122 */     if (paramString == "CONTENT") {
/* 123 */       this.contentRegion.setContent(((TitledPane)getSkinnable()).getContent());
/* 124 */     } else if (paramString == "EXPANDED") {
/* 125 */       setExpanded(((TitledPane)getSkinnable()).isExpanded());
/* 126 */     } else if (paramString == "COLLAPSIBLE") {
/* 127 */       this.titleRegion.update();
/* 128 */     } else if (paramString == "ALIGNMENT") {
/* 129 */       this.pos = ((TitledPane)getSkinnable()).getAlignment();
/* 130 */       this.hpos = this.pos.getHpos();
/* 131 */       this.vpos = this.pos.getVpos();
/* 132 */     } else if (paramString == "TITLE_REGION_ALIGNMENT") {
/* 133 */       this.pos = this.titleRegion.getAlignment();
/* 134 */       this.hpos = this.pos.getHpos();
/* 135 */       this.vpos = this.pos.getVpos();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateChildren()
/*     */   {
/* 143 */     if (this.titleRegion != null)
/* 144 */       this.titleRegion.update();
/*     */   }
/*     */ 
/*     */   private void setExpanded(boolean paramBoolean)
/*     */   {
/* 149 */     if (!((TitledPane)getSkinnable()).isCollapsible()) {
/* 150 */       setTransition(1.0D);
/* 151 */       return;
/*     */     }
/*     */ 
/* 155 */     if (((TitledPane)getSkinnable()).isAnimated()) {
/* 156 */       this.transitionStartValue = getTransition();
/* 157 */       doAnimationTransition();
/*     */     } else {
/* 159 */       if (paramBoolean)
/* 160 */         setTransition(1.0D);
/*     */       else {
/* 162 */         setTransition(0.0D);
/*     */       }
/* 164 */       this.contentRegion.setVisible(paramBoolean);
/* 165 */       requestLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setTransition(double paramDouble) {
/* 170 */     transitionProperty().set(paramDouble); } 
/* 171 */   private double getTransition() { return this.transition == null ? 0.0D : this.transition.get(); } 
/*     */   private DoubleProperty transitionProperty() {
/* 173 */     if (this.transition == null) {
/* 174 */       this.transition = new DoublePropertyBase() {
/*     */         protected void invalidated() {
/* 176 */           TitledPaneSkin.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 181 */           return TitledPaneSkin.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 186 */           return "transition";
/*     */         }
/*     */       };
/*     */     }
/* 190 */     return this.transition;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 194 */     double d1 = snapSize(getWidth()) - (snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight()));
/* 195 */     double d2 = snapSize(getHeight()) - (snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom()));
/*     */ 
/* 198 */     double d3 = Math.max(22.0D, snapSize(this.titleRegion.prefHeight(-1.0D)));
/*     */ 
/* 200 */     this.titleRegion.resize(d1, d3);
/* 201 */     positionInArea(this.titleRegion, snapSpace(getInsets().getLeft()), snapSpace(getInsets().getTop()), d1, d3, 0.0D, HPos.LEFT, VPos.CENTER);
/*     */ 
/* 205 */     double d4 = d1;
/* 206 */     double d5 = d2 - d3;
/* 207 */     if ((((TitledPane)getSkinnable()).getParent() != null) && ((((TitledPane)getSkinnable()).getParent() instanceof AccordionSkin)) && 
/* 208 */       (this.prefHeightFromAccordion != 0.0D)) {
/* 209 */       d5 = this.prefHeightFromAccordion - d3;
/*     */     }
/*     */ 
/* 213 */     double d6 = snapSpace(getInsets().getTop()) + snapSpace(d3) - d5 * (1.0D - getTransition());
/* 214 */     double d7 = d5 * (1.0D - getTransition());
/* 215 */     ((Rectangle)this.contentContainer.getClip()).setY(d7);
/*     */ 
/* 217 */     this.contentContainer.resize(d4, d5);
/* 218 */     positionInArea(this.contentContainer, snapSpace(getInsets().getLeft()), d6, d1, d5, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble)
/*     */   {
/* 223 */     return computePrefWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/* 227 */     return Math.max(22.0D, snapSize(this.titleRegion.prefHeight(-1.0D)));
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 231 */     double d1 = snapSize(this.titleRegion.prefWidth(paramDouble));
/* 232 */     double d2 = snapSize(this.contentContainer.prefWidth(paramDouble));
/*     */ 
/* 234 */     return Math.max(d1, d2) + snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight());
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 238 */     double d1 = Math.max(22.0D, snapSize(this.titleRegion.prefHeight(-1.0D)));
/* 239 */     double d2 = 0.0D;
/* 240 */     if ((((TitledPane)getSkinnable()).getParent() != null) && ((((TitledPane)getSkinnable()).getParent() instanceof AccordionSkin)))
/* 241 */       d2 = this.contentContainer.prefHeight(-1.0D);
/*     */     else {
/* 243 */       d2 = this.contentContainer.prefHeight(-1.0D) * getTransition();
/*     */     }
/* 245 */     return d1 + snapSize(d2) + snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble) {
/* 249 */     return 1.7976931348623157E+308D;
/*     */   }
/*     */ 
/*     */   void setMaxTitledPaneHeightForAccordion(double paramDouble)
/*     */   {
/* 254 */     this.prefHeightFromAccordion = paramDouble;
/*     */   }
/*     */ 
/*     */   double getTitledPaneHeightForAccordion() {
/* 258 */     double d1 = Math.max(22.0D, snapSize(this.titleRegion.prefHeight(-1.0D)));
/* 259 */     double d2 = (this.prefHeightFromAccordion - d1) * getTransition();
/* 260 */     return d1 + snapSize(d2) + snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   private void doAnimationTransition()
/*     */   {
/* 266 */     if (this.contentRegion.getContent() == null)
/*     */       return;
/*     */     Duration localDuration;
/* 270 */     if ((this.timeline != null) && (this.timeline.getStatus() != Animation.Status.STOPPED)) {
/* 271 */       localDuration = this.timeline.getCurrentTime();
/* 272 */       this.timeline.stop();
/*     */     } else {
/* 274 */       localDuration = TRANSITION_DURATION;
/*     */     }
/*     */ 
/* 277 */     this.timeline = new Timeline();
/* 278 */     this.timeline.setCycleCount(1);
/*     */     KeyFrame localKeyFrame1;
/*     */     KeyFrame localKeyFrame2;
/* 282 */     if (((TitledPane)getSkinnable()).isExpanded()) {
/* 283 */       localKeyFrame1 = new KeyFrame(Duration.ZERO, new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 288 */           TitledPaneSkin.this.contentRegion.getContent().setCache(true);
/* 289 */           TitledPaneSkin.this.contentRegion.getContent().setVisible(true);
/*     */         }
/*     */       }
/*     */       , new KeyValue[] { new KeyValue(transitionProperty(), Double.valueOf(this.transitionStartValue)) });
/*     */ 
/* 295 */       localKeyFrame2 = new KeyFrame(localDuration, new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 300 */           TitledPaneSkin.this.contentRegion.getContent().setCache(false);
/*     */         }
/*     */       }
/*     */       , new KeyValue[] { new KeyValue(transitionProperty(), Integer.valueOf(1), Interpolator.EASE_OUT) });
/*     */     }
/*     */     else
/*     */     {
/* 307 */       localKeyFrame1 = new KeyFrame(Duration.ZERO, new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 312 */           TitledPaneSkin.this.contentRegion.getContent().setCache(true);
/*     */         }
/*     */       }
/*     */       , new KeyValue[] { new KeyValue(transitionProperty(), Double.valueOf(this.transitionStartValue)) });
/*     */ 
/* 318 */       localKeyFrame2 = new KeyFrame(localDuration, new EventHandler()
/*     */       {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent)
/*     */         {
/* 323 */           TitledPaneSkin.this.contentRegion.getContent().setVisible(false);
/* 324 */           TitledPaneSkin.this.contentRegion.getContent().setCache(false);
/*     */         }
/*     */       }
/*     */       , new KeyValue[] { new KeyValue(transitionProperty(), Integer.valueOf(0), Interpolator.EASE_IN) });
/*     */     }
/*     */ 
/* 331 */     this.timeline.getKeyFrames().setAll(new KeyFrame[] { localKeyFrame1, localKeyFrame2 });
/* 332 */     this.timeline.play();
/*     */   }
/*     */ 
/*     */   class ContentContainer extends StackPane
/*     */   {
/*     */     private Rectangle clipRect;
/*     */ 
/*     */     public ContentContainer()
/*     */     {
/* 574 */       getStyleClass().setAll(new String[] { "content" });
/* 575 */       this.clipRect = new Rectangle();
/* 576 */       setClip(this.clipRect);
/*     */ 
/* 581 */       setAlignment(Pos.BOTTOM_CENTER);
/*     */     }
/*     */ 
/*     */     protected void setWidth(double paramDouble) {
/* 585 */       super.setWidth(paramDouble);
/* 586 */       this.clipRect.setWidth(paramDouble);
/*     */     }
/*     */ 
/*     */     protected void setHeight(double paramDouble) {
/* 590 */       super.setHeight(paramDouble);
/* 591 */       this.clipRect.setHeight(paramDouble);
/*     */     }
/*     */   }
/*     */ 
/*     */   class Content extends StackPane
/*     */     implements TraverseListener
/*     */   {
/*     */     private Node content;
/*     */     private TraversalEngine engine;
/*     */     private Direction direction;
/*     */ 
/*     */     public Content(Node arg2)
/*     */     {
/*     */       Node localNode;
/* 525 */       setContent(localNode);
/*     */ 
/* 527 */       this.engine = new TraversalEngine(this, false) {
/*     */         public void trav(Node paramAnonymousNode, Direction paramAnonymousDirection) {
/* 529 */           TitledPaneSkin.Content.this.direction = paramAnonymousDirection;
/* 530 */           super.trav(paramAnonymousNode, paramAnonymousDirection);
/*     */         }
/*     */       };
/* 533 */       this.engine.addTraverseListener(this);
/* 534 */       setImpl_traversalEngine(this.engine);
/*     */     }
/*     */ 
/*     */     public final void setContent(Node paramNode) {
/* 538 */       this.content = paramNode;
/* 539 */       getChildren().clear();
/* 540 */       if (paramNode != null)
/* 541 */         getChildren().setAll(new Node[] { paramNode });
/*     */     }
/*     */ 
/*     */     public final Node getContent()
/*     */     {
/* 546 */       return this.content;
/*     */     }
/*     */ 
/*     */     public void onTraverse(Node paramNode, Bounds paramBounds)
/*     */     {
/* 551 */       int i = this.engine.registeredNodes.indexOf(paramNode);
/*     */ 
/* 553 */       if ((i == -1) && (this.direction.equals(Direction.PREVIOUS)))
/*     */       {
/* 556 */         if ((((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent() != null) && ((((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent() instanceof AccordionSkin))) {
/* 557 */           new TraversalEngine(TitledPaneSkin.this.getSkinnable(), false).trav(((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent(), Direction.PREVIOUS);
/*     */         }
/*     */       }
/* 560 */       if ((i == -1) && (this.direction.equals(Direction.NEXT)))
/*     */       {
/* 563 */         if ((((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent() != null) && ((((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent() instanceof AccordionSkin)))
/* 564 */           new TraversalEngine(TitledPaneSkin.this.getSkinnable(), false).trav(((TitledPane)TitledPaneSkin.this.getSkinnable()).getParent(), Direction.NEXT);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   class TitleRegion extends StackPane
/*     */   {
/*     */     private final StackPane arrowRegion;
/*     */ 
/*     */     public TitleRegion()
/*     */     {
/* 339 */       getStyleClass().setAll(new String[] { "title" });
/* 340 */       this.arrowRegion = new StackPane();
/* 341 */       this.arrowRegion.setId("arrowRegion");
/* 342 */       this.arrowRegion.getStyleClass().setAll(new String[] { "arrow-button" });
/*     */ 
/* 344 */       StackPane localStackPane = new StackPane();
/* 345 */       localStackPane.setId("arrow");
/* 346 */       localStackPane.getStyleClass().setAll(new String[] { "arrow" });
/* 347 */       this.arrowRegion.getChildren().setAll(new Node[] { localStackPane });
/*     */ 
/* 349 */       setAlignment(Pos.CENTER_LEFT);
/*     */ 
/* 351 */       setOnMouseReleased(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 353 */           if ((((TitledPane)TitledPaneSkin.this.getSkinnable()).isCollapsible()) && (((TitledPane)TitledPaneSkin.this.getSkinnable()).isFocused()))
/* 354 */             ((TitledPaneBehavior)TitledPaneSkin.this.getBehavior()).toggle();
/*     */         }
/*     */       });
/* 360 */       update();
/*     */     }
/*     */ 
/*     */     private void update() {
/* 364 */       getChildren().clear();
/* 365 */       TitledPane localTitledPane = (TitledPane)TitledPaneSkin.this.getSkinnable();
/*     */ 
/* 367 */       if (localTitledPane.isCollapsible()) {
/* 368 */         getChildren().add(this.arrowRegion);
/*     */       }
/*     */ 
/* 374 */       if (TitledPaneSkin.this.graphic != null) {
/* 375 */         TitledPaneSkin.this.graphic.layoutBoundsProperty().removeListener(TitledPaneSkin.this.graphicPropertyChangedListener);
/*     */       }
/*     */ 
/* 378 */       TitledPaneSkin.this.graphic = localTitledPane.getGraphic();
/*     */ 
/* 380 */       if (TitledPaneSkin.this.isIgnoreGraphic()) {
/* 381 */         if (localTitledPane.getContentDisplay() == ContentDisplay.GRAPHIC_ONLY) {
/* 382 */           getChildren().clear();
/* 383 */           getChildren().add(this.arrowRegion);
/*     */         } else {
/* 385 */           getChildren().add(TitledPaneSkin.this.text);
/*     */         }
/*     */       } else {
/* 388 */         TitledPaneSkin.this.graphic.layoutBoundsProperty().addListener(TitledPaneSkin.this.graphicPropertyChangedListener);
/* 389 */         if (TitledPaneSkin.this.isIgnoreText())
/* 390 */           getChildren().add(TitledPaneSkin.this.graphic);
/*     */         else {
/* 392 */           getChildren().addAll(new Node[] { TitledPaneSkin.this.graphic, TitledPaneSkin.this.text });
/*     */         }
/*     */       }
/* 395 */       setCursor(((TitledPane)TitledPaneSkin.this.getSkinnable()).isCollapsible() ? Cursor.HAND : Cursor.DEFAULT);
/*     */     }
/*     */ 
/*     */     protected double computePrefWidth(double paramDouble) {
/* 399 */       double d1 = snapSpace(getInsets().getLeft());
/* 400 */       double d2 = snapSpace(getInsets().getRight());
/* 401 */       double d3 = 0.0D;
/* 402 */       double d4 = labelPrefWidth(paramDouble);
/*     */ 
/* 404 */       if (this.arrowRegion != null) {
/* 405 */         d3 = snapSize(this.arrowRegion.prefWidth(paramDouble));
/*     */       }
/*     */ 
/* 408 */       return d1 + d3 + d4 + d2;
/*     */     }
/*     */ 
/*     */     protected double computePrefHeight(double paramDouble) {
/* 412 */       double d1 = snapSpace(getInsets().getTop());
/* 413 */       double d2 = snapSpace(getInsets().getBottom());
/* 414 */       double d3 = 0.0D;
/* 415 */       double d4 = labelPrefHeight(paramDouble);
/*     */ 
/* 417 */       if (this.arrowRegion != null) {
/* 418 */         d3 = snapSize(this.arrowRegion.prefHeight(paramDouble));
/*     */       }
/*     */ 
/* 421 */       return d1 + Math.max(d3, d4) + d2;
/*     */     }
/*     */ 
/*     */     protected void layoutChildren() {
/* 425 */       double d1 = snapSpace(getInsets().getTop());
/* 426 */       double d2 = snapSpace(getInsets().getBottom());
/* 427 */       double d3 = snapSpace(getInsets().getLeft());
/* 428 */       double d4 = snapSpace(getInsets().getRight());
/* 429 */       double d5 = getWidth() - (d3 + d4);
/* 430 */       double d6 = getHeight() - (d1 + d2);
/* 431 */       double d7 = snapSize(this.arrowRegion.prefWidth(-1.0D));
/* 432 */       double d8 = snapSize(this.arrowRegion.prefHeight(-1.0D));
/* 433 */       double d9 = snapSize(labelPrefWidth(-1.0D));
/* 434 */       double d10 = snapSize(labelPrefHeight(-1.0D));
/*     */ 
/* 436 */       double d11 = d3 + d7 + Utils.computeXOffset(d5 - d7, d9, TitledPaneSkin.this.hpos);
/* 437 */       if (HPos.CENTER == TitledPaneSkin.this.hpos)
/*     */       {
/* 439 */         d11 = d3 + Utils.computeXOffset(d5, d9, TitledPaneSkin.this.hpos);
/*     */       }
/* 441 */       double d12 = d1 + Utils.computeYOffset(d6, Math.max(d8, d10), TitledPaneSkin.this.vpos);
/*     */ 
/* 443 */       this.arrowRegion.resize(d7, d8);
/* 444 */       positionInArea(this.arrowRegion, d3, d1, d7, d6, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */ 
/* 447 */       TitledPaneSkin.this.layoutLabelInArea(d11, d12, d9, d6, TitledPaneSkin.this.pos);
/*     */     }
/*     */ 
/*     */     private double labelPrefWidth(double paramDouble)
/*     */     {
/* 454 */       Labeled localLabeled = (Labeled)TitledPaneSkin.this.getSkinnable();
/* 455 */       Font localFont = TitledPaneSkin.this.text.getFont();
/* 456 */       String str = localLabeled.getText();
/* 457 */       int i = (str == null) || (str.isEmpty()) ? 1 : 0;
/* 458 */       Insets localInsets = localLabeled.getLabelPadding();
/* 459 */       double d1 = localInsets.getLeft() + localInsets.getRight();
/* 460 */       double d2 = i != 0 ? 0.0D : Utils.computeTextWidth(localFont, str, 0.0D);
/*     */ 
/* 463 */       Node localNode = localLabeled.getGraphic();
/* 464 */       if (TitledPaneSkin.this.isIgnoreGraphic())
/* 465 */         return d2 + d1;
/* 466 */       if (TitledPaneSkin.this.isIgnoreText())
/* 467 */         return localNode.prefWidth(-1.0D) + d1;
/* 468 */       if ((localLabeled.getContentDisplay() == ContentDisplay.LEFT) || (localLabeled.getContentDisplay() == ContentDisplay.RIGHT))
/*     */       {
/* 470 */         return d2 + localLabeled.getGraphicTextGap() + localNode.prefWidth(-1.0D) + d1;
/*     */       }
/* 472 */       return Math.max(d2, localNode.prefWidth(-1.0D)) + d1;
/*     */     }
/*     */ 
/*     */     private double labelPrefHeight(double paramDouble)
/*     */     {
/* 479 */       Labeled localLabeled = (Labeled)TitledPaneSkin.this.getSkinnable();
/* 480 */       Font localFont = TitledPaneSkin.this.text.getFont();
/* 481 */       ContentDisplay localContentDisplay = localLabeled.getContentDisplay();
/* 482 */       double d1 = localLabeled.getGraphicTextGap();
/* 483 */       Insets localInsets1 = getInsets();
/* 484 */       Insets localInsets2 = localLabeled.getLabelPadding();
/* 485 */       double d2 = localInsets1.getLeft() + localInsets1.getRight() + localInsets2.getLeft() + localInsets2.getRight();
/*     */ 
/* 487 */       String str = localLabeled.getText();
/* 488 */       if ((str != null) && (str.endsWith("\n")))
/*     */       {
/* 490 */         str = str.substring(0, str.length() - 1);
/*     */       }
/*     */ 
/* 493 */       if ((!TitledPaneSkin.this.isIgnoreGraphic()) && ((localContentDisplay == ContentDisplay.LEFT) || (localContentDisplay == ContentDisplay.RIGHT)))
/*     */       {
/* 495 */         paramDouble -= TitledPaneSkin.this.graphic.prefWidth(-1.0D) + d1;
/*     */       }
/*     */ 
/* 498 */       paramDouble -= d2;
/*     */ 
/* 501 */       double d3 = Utils.computeTextHeight(localFont, str, localLabeled.isWrapText() ? paramDouble : 0.0D);
/*     */ 
/* 505 */       double d4 = d3;
/* 506 */       if (!TitledPaneSkin.this.isIgnoreGraphic()) {
/* 507 */         Node localNode = localLabeled.getGraphic();
/* 508 */         if ((localContentDisplay == ContentDisplay.TOP) || (localContentDisplay == ContentDisplay.BOTTOM))
/* 509 */           d4 = localNode.prefHeight(-1.0D) + d1 + d3;
/*     */         else {
/* 511 */           d4 = Math.max(d3, localNode.prefHeight(-1.0D));
/*     */         }
/*     */       }
/*     */ 
/* 515 */       return d4 + localInsets2.getTop() + localInsets2.getBottom();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TitledPaneSkin
 * JD-Core Version:    0.6.2
 */