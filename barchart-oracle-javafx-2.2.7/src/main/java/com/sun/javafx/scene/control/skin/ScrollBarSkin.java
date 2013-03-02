/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.scene.control.behavior.ScrollBarBehavior;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ScrollBar;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class ScrollBarSkin extends SkinBase<ScrollBar, ScrollBarBehavior>
/*     */ {
/*  51 */   public static int DEFAULT_LENGTH = 100;
/*  52 */   public static int DEFAULT_WIDTH = 20;
/*     */   private StackPane thumb;
/*     */   private StackPane trackBackground;
/*     */   private StackPane track;
/*     */   private EndButton incButton;
/*     */   private EndButton decButton;
/*     */   private double trackLength;
/*     */   private double thumbLength;
/*     */   private double preDragThumbPos;
/*     */   private Point2D dragStart;
/*     */   private double trackPos;
/*     */ 
/*     */   public ScrollBarSkin(ScrollBar paramScrollBar)
/*     */   {
/*  75 */     super(paramScrollBar, new ScrollBarBehavior(paramScrollBar));
/*  76 */     initialize();
/*  77 */     requestLayout();
/*     */ 
/*  79 */     registerChangeListener(paramScrollBar.minProperty(), "MIN");
/*  80 */     registerChangeListener(paramScrollBar.maxProperty(), "MAX");
/*  81 */     registerChangeListener(paramScrollBar.valueProperty(), "VALUE");
/*  82 */     registerChangeListener(paramScrollBar.orientationProperty(), "ORIENTATION");
/*  83 */     registerChangeListener(paramScrollBar.visibleAmountProperty(), "VISIBLE_AMOUNT");
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  92 */     this.track = new StackPane();
/*  93 */     this.track.getStyleClass().setAll(new String[] { "track" });
/*     */ 
/*  95 */     this.trackBackground = new StackPane();
/*  96 */     this.trackBackground.getStyleClass().setAll(new String[] { "track-background" });
/*     */ 
/*  98 */     this.thumb = new StackPane();
/*  99 */     this.thumb.getStyleClass().setAll(new String[] { "thumb" });
/*     */ 
/* 102 */     if (!PlatformUtil.isEmbedded())
/*     */     {
/* 104 */       this.incButton = new EndButton("increment-button", "increment-arrow");
/* 105 */       this.incButton.setOnMousePressed(new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */         {
/* 110 */           if ((!ScrollBarSkin.this.thumb.isVisible()) || (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength)) {
/* 111 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).incButtonPressed(paramAnonymousMouseEvent);
/*     */           }
/* 113 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/* 116 */       this.incButton.setOnMouseReleased(new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */         {
/* 121 */           if ((!ScrollBarSkin.this.thumb.isVisible()) || (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength)) {
/* 122 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).incButtonReleased(paramAnonymousMouseEvent);
/*     */           }
/* 124 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/* 128 */       this.decButton = new EndButton("decrement-button", "decrement-arrow");
/* 129 */       this.decButton.setOnMousePressed(new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */         {
/* 134 */           if ((!ScrollBarSkin.this.thumb.isVisible()) || (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength)) {
/* 135 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).decButtonPressed(paramAnonymousMouseEvent);
/*     */           }
/* 137 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/* 140 */       this.decButton.setOnMouseReleased(new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */         {
/* 145 */           if ((!ScrollBarSkin.this.thumb.isVisible()) || (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength)) {
/* 146 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).decButtonReleased(paramAnonymousMouseEvent);
/*     */           }
/* 148 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/* 154 */     this.track.setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 156 */         if (!ScrollBarSkin.this.thumb.isPressed())
/* 157 */           if (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 158 */             if (ScrollBarSkin.this.trackLength != 0.0D) {
/* 159 */               ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).trackPress(paramAnonymousMouseEvent, paramAnonymousMouseEvent.getY() / ScrollBarSkin.this.trackLength);
/* 160 */               paramAnonymousMouseEvent.consume();
/*     */             }
/*     */           }
/* 163 */           else if (ScrollBarSkin.this.trackLength != 0.0D) {
/* 164 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).trackPress(paramAnonymousMouseEvent, paramAnonymousMouseEvent.getX() / ScrollBarSkin.this.trackLength);
/* 165 */             paramAnonymousMouseEvent.consume();
/*     */           }
/*     */       }
/*     */     });
/* 172 */     this.track.setOnMouseReleased(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 174 */         ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).trackRelease(paramAnonymousMouseEvent, 0.0D);
/* 175 */         paramAnonymousMouseEvent.consume();
/*     */       }
/*     */     });
/* 179 */     this.thumb.setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 181 */         if (paramAnonymousMouseEvent.isSynthesized())
/*     */         {
/* 183 */           paramAnonymousMouseEvent.consume();
/* 184 */           return;
/*     */         }
/*     */ 
/* 189 */         if (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() > ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()) {
/* 190 */           ScrollBarSkin.this.dragStart = ScrollBarSkin.this.thumb.localToParent(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 191 */           double d = Utils.clamp(((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin(), ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getValue(), ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax());
/* 192 */           ScrollBarSkin.this.preDragThumbPos = ((d - ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()) / (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() - ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()));
/* 193 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       }
/*     */     });
/* 199 */     this.thumb.setOnMouseDragged(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 201 */         if (paramAnonymousMouseEvent.isSynthesized())
/*     */         {
/* 203 */           paramAnonymousMouseEvent.consume();
/* 204 */           return;
/*     */         }
/*     */ 
/* 209 */         if (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() > ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin())
/*     */         {
/* 213 */           if (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength) {
/* 214 */             Point2D localPoint2D = ScrollBarSkin.this.thumb.localToParent(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY());
/* 215 */             double d = ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getOrientation() == Orientation.VERTICAL ? localPoint2D.getY() - ScrollBarSkin.this.dragStart.getY() : localPoint2D.getX() - ScrollBarSkin.this.dragStart.getX();
/* 216 */             ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).thumbDragged(paramAnonymousMouseEvent, ScrollBarSkin.this.preDragThumbPos + d / (ScrollBarSkin.this.trackLength - ScrollBarSkin.this.thumbLength));
/*     */           }
/*     */ 
/* 219 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       }
/*     */     });
/* 224 */     this.thumb.setOnScrollStarted(new EventHandler() {
/*     */       public void handle(ScrollEvent paramAnonymousScrollEvent) {
/* 226 */         if (paramAnonymousScrollEvent.isDirect())
/*     */         {
/* 230 */           if (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() > ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()) {
/* 231 */             ScrollBarSkin.this.dragStart = ScrollBarSkin.this.thumb.localToParent(paramAnonymousScrollEvent.getX(), paramAnonymousScrollEvent.getY());
/* 232 */             double d = Utils.clamp(((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin(), ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getValue(), ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax());
/* 233 */             ScrollBarSkin.this.preDragThumbPos = ((d - ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()) / (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() - ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin()));
/* 234 */             paramAnonymousScrollEvent.consume();
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 240 */     this.thumb.setOnScroll(new EventHandler() {
/*     */       public void handle(ScrollEvent paramAnonymousScrollEvent) {
/* 242 */         if (paramAnonymousScrollEvent.isDirect())
/*     */         {
/* 246 */           if (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMax() > ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getMin())
/*     */           {
/* 250 */             if (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength) {
/* 251 */               Point2D localPoint2D = ScrollBarSkin.this.thumb.localToParent(paramAnonymousScrollEvent.getX(), paramAnonymousScrollEvent.getY());
/* 252 */               double d = ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getOrientation() == Orientation.VERTICAL ? localPoint2D.getY() - ScrollBarSkin.this.dragStart.getY() : localPoint2D.getX() - ScrollBarSkin.this.dragStart.getX();
/* 253 */               ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).thumbDragged(null, ScrollBarSkin.this.preDragThumbPos + d / (ScrollBarSkin.this.trackLength - ScrollBarSkin.this.thumbLength));
/*     */             }
/*     */ 
/* 256 */             paramAnonymousScrollEvent.consume();
/* 257 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 264 */     setOnScroll(new EventHandler()
/*     */     {
/*     */       public void handle(ScrollEvent paramAnonymousScrollEvent)
/*     */       {
/* 269 */         if (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength)
/*     */         {
/* 271 */           double d1 = paramAnonymousScrollEvent.getDeltaX();
/* 272 */           double d2 = paramAnonymousScrollEvent.getDeltaY();
/*     */ 
/* 278 */           d1 = Math.abs(d1) < Math.abs(d2) ? d2 : d1;
/*     */ 
/* 283 */           ScrollBar localScrollBar = (ScrollBar)ScrollBarSkin.this.getSkinnable();
/*     */ 
/* 285 */           double d3 = ((ScrollBar)ScrollBarSkin.this.getSkinnable()).getOrientation() == Orientation.VERTICAL ? d2 : d1;
/*     */ 
/* 294 */           if (paramAnonymousScrollEvent.isDirect()) {
/* 295 */             if (ScrollBarSkin.this.trackLength > ScrollBarSkin.this.thumbLength) {
/* 296 */               ((ScrollBarBehavior)ScrollBarSkin.this.getBehavior()).thumbDragged(null, (((ScrollBar)ScrollBarSkin.this.getSkinnable()).getOrientation() == Orientation.VERTICAL ? paramAnonymousScrollEvent.getY() : paramAnonymousScrollEvent.getX()) / ScrollBarSkin.this.trackLength);
/* 297 */               paramAnonymousScrollEvent.consume();
/*     */             }
/*     */ 
/*     */           }
/* 301 */           else if ((d3 > 0.0D) && (localScrollBar.getValue() > localScrollBar.getMin())) {
/* 302 */             localScrollBar.decrement();
/* 303 */             paramAnonymousScrollEvent.consume();
/* 304 */           } else if ((d3 < 0.0D) && (localScrollBar.getValue() < localScrollBar.getMax())) {
/* 305 */             localScrollBar.increment();
/* 306 */             paramAnonymousScrollEvent.consume();
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 313 */     getChildren().clear();
/* 314 */     if (!PlatformUtil.isEmbedded()) {
/* 315 */       getChildren().addAll(new Node[] { this.trackBackground, this.incButton, this.decButton, this.track, this.thumb });
/*     */     }
/*     */     else
/* 318 */       getChildren().addAll(new Node[] { this.track, this.thumb });
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 325 */     super.handleControlPropertyChanged(paramString);
/* 326 */     if (paramString == "ORIENTATION") {
/* 327 */       requestLayout();
/* 328 */     } else if ((paramString == "MIN") || (paramString == "MAX") || (paramString == "VALUE") || (paramString == "VISIBLE_AMOUNT")) {
/* 329 */       positionThumb();
/* 330 */       requestLayout();
/*     */     }
/*     */   }
/*     */ 
/*     */   double getBreadth()
/*     */   {
/* 346 */     if (!PlatformUtil.isEmbedded()) {
/* 347 */       if (((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 348 */         return Math.max(this.decButton.prefWidth(-1.0D) + getInsets().getLeft() + getInsets().getRight(), this.incButton.prefWidth(-1.0D) + getInsets().getLeft() + getInsets().getRight());
/*     */       }
/* 350 */       return Math.max(this.decButton.prefHeight(-1.0D) + getInsets().getTop() + getInsets().getBottom(), this.incButton.prefHeight(-1.0D) + getInsets().getTop() + getInsets().getBottom());
/*     */     }
/*     */ 
/* 354 */     if (((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 355 */       return Math.max(getInsets().getLeft() + getInsets().getRight(), getInsets().getLeft() + getInsets().getRight());
/*     */     }
/* 357 */     return Math.max(getInsets().getTop() + getInsets().getBottom(), getInsets().getTop() + getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   double minThumbLength()
/*     */   {
/* 363 */     return 1.5D * getBreadth();
/*     */   }
/*     */ 
/*     */   double minTrackLength() {
/* 367 */     return 2.0D * getBreadth();
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble)
/*     */   {
/* 377 */     if (((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 378 */       return getBreadth();
/*     */     }
/* 380 */     if (!PlatformUtil.isEmbedded()) {
/* 381 */       return this.decButton.minWidth(-1.0D) + this.incButton.minWidth(-1.0D) + minTrackLength() + getInsets().getLeft() + getInsets().getRight();
/*     */     }
/*     */ 
/* 384 */     return minTrackLength() + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble)
/*     */   {
/* 390 */     if (((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 391 */       if (!PlatformUtil.isEmbedded()) {
/* 392 */         return this.decButton.minHeight(-1.0D) + this.incButton.minHeight(-1.0D) + minTrackLength() + getInsets().getTop() + getInsets().getBottom();
/*     */       }
/*     */ 
/* 395 */       return minTrackLength() + getInsets().getTop() + getInsets().getBottom();
/*     */     }
/*     */ 
/* 398 */     return getBreadth();
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 410 */     return ((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL ? getBreadth() : DEFAULT_LENGTH + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 414 */     return ((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL ? DEFAULT_LENGTH + getInsets().getTop() + getInsets().getBottom() : getBreadth();
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble) {
/* 418 */     return ((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL ? ((ScrollBar)getSkinnable()).prefWidth(-1.0D) : 1.7976931348623157E+308D;
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble) {
/* 422 */     return ((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL ? 1.7976931348623157E+308D : ((ScrollBar)getSkinnable()).prefHeight(-1.0D);
/*     */   }
/*     */ 
/*     */   void positionThumb()
/*     */   {
/* 429 */     ScrollBar localScrollBar = (ScrollBar)getSkinnable();
/* 430 */     double d = Utils.clamp(localScrollBar.getMin(), localScrollBar.getValue(), localScrollBar.getMax());
/* 431 */     this.trackPos = (localScrollBar.getMax() - localScrollBar.getMin() > 0.0D ? (this.trackLength - this.thumbLength) * (d - localScrollBar.getMin()) / (localScrollBar.getMax() - localScrollBar.getMin()) : 0.0D);
/*     */ 
/* 433 */     if (!PlatformUtil.isEmbedded()) {
/* 434 */       if (localScrollBar.getOrientation() == Orientation.VERTICAL)
/* 435 */         this.trackPos += this.decButton.prefHeight(-1.0D);
/*     */       else {
/* 437 */         this.trackPos += this.decButton.prefWidth(-1.0D);
/*     */       }
/*     */     }
/*     */ 
/* 441 */     this.thumb.setTranslateX(localScrollBar.getOrientation() == Orientation.VERTICAL ? getInsets().getLeft() : this.trackPos + getInsets().getLeft());
/* 442 */     this.thumb.setTranslateY(localScrollBar.getOrientation() == Orientation.VERTICAL ? this.trackPos + getInsets().getTop() : getInsets().getTop());
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 447 */     double d1 = getInsets().getLeft();
/* 448 */     double d2 = getInsets().getTop();
/* 449 */     double d3 = snapSize(getWidth());
/* 450 */     double d4 = snapSize(getHeight());
/* 451 */     double d5 = snapSize(d3 - (getInsets().getLeft() + getInsets().getRight()));
/* 452 */     double d6 = snapSize(d4 - (getInsets().getTop() + getInsets().getBottom()));
/*     */     double d7;
/* 459 */     if (((ScrollBar)getSkinnable()).getMax() > ((ScrollBar)getSkinnable()).getMin()) {
/* 460 */       d7 = ((ScrollBar)getSkinnable()).getVisibleAmount() / (((ScrollBar)getSkinnable()).getMax() - ((ScrollBar)getSkinnable()).getMin());
/*     */     }
/*     */     else
/* 463 */       d7 = 1.0D;
/*     */     double d8;
/*     */     double d9;
/* 466 */     if (((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) {
/* 467 */       if (!PlatformUtil.isEmbedded()) {
/* 468 */         d8 = snapSize(this.decButton.prefHeight(-1.0D));
/* 469 */         d9 = snapSize(this.incButton.prefHeight(-1.0D));
/*     */ 
/* 471 */         this.decButton.resize(d5, d8);
/* 472 */         this.incButton.resize(d5, d9);
/*     */ 
/* 474 */         this.trackLength = snapSize(d6 - (d8 + d9));
/* 475 */         this.thumbLength = snapSize(Utils.clamp(minThumbLength(), this.trackLength * d7, this.trackLength));
/*     */ 
/* 477 */         this.trackBackground.resizeRelocate(snapPosition(d1), snapPosition(d2), d5, this.trackLength + d8 + d9);
/* 478 */         this.decButton.relocate(snapPosition(d1), snapPosition(d2));
/* 479 */         this.incButton.relocate(snapPosition(d1), snapPosition(d2 + d6 - d9));
/* 480 */         this.track.resizeRelocate(snapPosition(d1), snapPosition(d2 + d8), d5, this.trackLength);
/* 481 */         this.thumb.resize(snapSize(d1 >= 0.0D ? d5 : d5 + d1), this.thumbLength);
/* 482 */         positionThumb();
/*     */       }
/*     */       else {
/* 485 */         this.trackLength = snapSize(d6);
/* 486 */         this.thumbLength = snapSize(Utils.clamp(minThumbLength(), this.trackLength * d7, this.trackLength));
/*     */ 
/* 488 */         this.track.resizeRelocate(snapPosition(d1), snapPosition(d2), d5, this.trackLength);
/* 489 */         this.thumb.resize(snapSize(d1 >= 0.0D ? d5 : d5 + d1), this.thumbLength);
/* 490 */         positionThumb();
/*     */       }
/*     */     } else {
/* 493 */       if (!PlatformUtil.isEmbedded()) {
/* 494 */         d8 = snapSize(this.decButton.prefWidth(-1.0D));
/* 495 */         d9 = snapSize(this.incButton.prefWidth(-1.0D));
/*     */ 
/* 497 */         this.decButton.resize(d8, d6);
/* 498 */         this.incButton.resize(d9, d6);
/*     */ 
/* 500 */         this.trackLength = snapSize(d5 - (d8 + d9));
/* 501 */         this.thumbLength = snapSize(Utils.clamp(minThumbLength(), this.trackLength * d7, this.trackLength));
/*     */ 
/* 503 */         this.trackBackground.resizeRelocate(snapPosition(d1), snapPosition(d2), this.trackLength + d8 + d9, d6);
/* 504 */         this.decButton.relocate(snapPosition(d1), snapPosition(d2));
/* 505 */         this.incButton.relocate(snapPosition(d1 + d5 - d9), snapPosition(d2));
/* 506 */         this.track.resizeRelocate(snapPosition(d1 + d8), snapPosition(d2), this.trackLength, d6);
/* 507 */         this.thumb.resize(this.thumbLength, snapSize(d2 >= 0.0D ? d6 : d6 + d2));
/* 508 */         positionThumb();
/*     */       }
/*     */       else {
/* 511 */         this.trackLength = snapSize(d5);
/* 512 */         this.thumbLength = snapSize(Utils.clamp(minThumbLength(), this.trackLength * d7, this.trackLength));
/*     */ 
/* 514 */         this.track.resizeRelocate(snapPosition(d1), snapPosition(d2), this.trackLength, d6);
/* 515 */         this.thumb.resize(this.thumbLength, snapSize(d2 >= 0.0D ? d6 : d6 + d2));
/* 516 */         positionThumb();
/*     */       }
/*     */ 
/* 519 */       resize(snapSize(getWidth()), snapSize(getHeight()));
/*     */     }
/*     */ 
/* 523 */     if (((((ScrollBar)getSkinnable()).getOrientation() == Orientation.VERTICAL) && (d6 >= computeMinHeight(-1.0D) - (getInsets().getTop() + getInsets().getBottom()))) || ((((ScrollBar)getSkinnable()).getOrientation() == Orientation.HORIZONTAL) && (d5 >= computeMinWidth(-1.0D) - (getInsets().getLeft() + getInsets().getRight()))))
/*     */     {
/* 525 */       this.trackBackground.setVisible(true);
/* 526 */       this.track.setVisible(true);
/* 527 */       this.thumb.setVisible(true);
/* 528 */       if (!PlatformUtil.isEmbedded()) {
/* 529 */         this.incButton.setVisible(true);
/* 530 */         this.decButton.setVisible(true);
/*     */       }
/*     */     }
/*     */     else {
/* 534 */       this.trackBackground.setVisible(false);
/* 535 */       this.track.setVisible(false);
/* 536 */       this.thumb.setVisible(false);
/*     */ 
/* 538 */       if (!PlatformUtil.isEmbedded())
/*     */       {
/* 543 */         if (d6 >= this.decButton.computeMinWidth(-1.0D)) {
/* 544 */           this.decButton.setVisible(true);
/*     */         }
/*     */         else {
/* 547 */           this.decButton.setVisible(false);
/*     */         }
/* 549 */         if (d6 >= this.incButton.computeMinWidth(-1.0D)) {
/* 550 */           this.incButton.setVisible(true);
/*     */         }
/*     */         else
/* 553 */           this.incButton.setVisible(false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ScrollBarSkin
 * JD-Core Version:    0.6.2
 */