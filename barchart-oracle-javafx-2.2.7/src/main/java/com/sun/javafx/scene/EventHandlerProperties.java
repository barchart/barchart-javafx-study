/*     */ package com.sun.javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.ContextMenuEvent;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseDragEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.RotateEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.input.SwipeEvent;
/*     */ import javafx.scene.input.TouchEvent;
/*     */ import javafx.scene.input.ZoomEvent;
/*     */ 
/*     */ public final class EventHandlerProperties
/*     */ {
/*     */   private final EventHandlerManager eventDispatcher;
/*     */   private final Object bean;
/*     */   private EventHandlerProperty<ContextMenuEvent> onMenuContextRequested;
/*     */   private EventHandlerProperty<MouseEvent> onMouseClicked;
/*     */   private EventHandlerProperty<MouseEvent> onMouseDragged;
/*     */   private EventHandlerProperty<MouseEvent> onMouseEntered;
/*     */   private EventHandlerProperty<MouseEvent> onMouseExited;
/*     */   private EventHandlerProperty<MouseEvent> onMouseMoved;
/*     */   private EventHandlerProperty<MouseEvent> onMousePressed;
/*     */   private EventHandlerProperty<MouseEvent> onMouseReleased;
/*     */   private EventHandlerProperty<MouseEvent> onDragDetected;
/*     */   private EventHandlerProperty<ScrollEvent> onScroll;
/*     */   private EventHandlerProperty<ScrollEvent> onScrollStarted;
/*     */   private EventHandlerProperty<ScrollEvent> onScrollFinished;
/*     */   private EventHandlerProperty<RotateEvent> onRotationStarted;
/*     */   private EventHandlerProperty<RotateEvent> onRotate;
/*     */   private EventHandlerProperty<RotateEvent> onRotationFinished;
/*     */   private EventHandlerProperty<ZoomEvent> onZoomStarted;
/*     */   private EventHandlerProperty<ZoomEvent> onZoom;
/*     */   private EventHandlerProperty<ZoomEvent> onZoomFinished;
/*     */   private EventHandlerProperty<SwipeEvent> onSwipeUp;
/*     */   private EventHandlerProperty<SwipeEvent> onSwipeDown;
/*     */   private EventHandlerProperty<SwipeEvent> onSwipeLeft;
/*     */   private EventHandlerProperty<SwipeEvent> onSwipeRight;
/*     */   private EventHandlerProperty<MouseDragEvent> onMouseDragOver;
/*     */   private EventHandlerProperty<MouseDragEvent> onMouseDragReleased;
/*     */   private EventHandlerProperty<MouseDragEvent> onMouseDragEntered;
/*     */   private EventHandlerProperty<MouseDragEvent> onMouseDragExited;
/*     */   private EventHandlerProperty<KeyEvent> onKeyPressed;
/*     */   private EventHandlerProperty<KeyEvent> onKeyReleased;
/*     */   private EventHandlerProperty<KeyEvent> onKeyTyped;
/*     */   private EventHandlerProperty<InputMethodEvent> onInputMethodTextChanged;
/*     */   private EventHandlerProperty<DragEvent> onDragEntered;
/*     */   private EventHandlerProperty<DragEvent> onDragExited;
/*     */   private EventHandlerProperty<DragEvent> onDragOver;
/*     */   private EventHandlerProperty<DragEvent> onDragDropped;
/*     */   private EventHandlerProperty<DragEvent> onDragDone;
/*     */   private EventHandlerProperty<TouchEvent> onTouchPressed;
/*     */   private EventHandlerProperty<TouchEvent> onTouchMoved;
/*     */   private EventHandlerProperty<TouchEvent> onTouchReleased;
/*     */   private EventHandlerProperty<TouchEvent> onTouchStationary;
/*     */ 
/*     */   public EventHandlerProperties(EventHandlerManager paramEventHandlerManager, Object paramObject)
/*     */   {
/*  54 */     this.eventDispatcher = paramEventHandlerManager;
/*  55 */     this.bean = paramObject;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ContextMenuEvent> onContextMenuRequested()
/*     */   {
/*  61 */     return this.onMenuContextRequested == null ? null : (EventHandler)this.onMenuContextRequested.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ContextMenuEvent>> onContextMenuRequestedProperty()
/*     */   {
/*  66 */     if (this.onMenuContextRequested == null) {
/*  67 */       this.onMenuContextRequested = new EventHandlerProperty(this.bean, "onMenuContextRequested", ContextMenuEvent.CONTEXT_MENU_REQUESTED);
/*     */     }
/*     */ 
/*  72 */     return this.onMenuContextRequested;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseClicked()
/*     */   {
/*  78 */     return this.onMouseClicked == null ? null : (EventHandler)this.onMouseClicked.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseClickedProperty()
/*     */   {
/*  83 */     if (this.onMouseClicked == null) {
/*  84 */       this.onMouseClicked = new EventHandlerProperty(this.bean, "onMouseClicked", MouseEvent.MOUSE_CLICKED);
/*     */     }
/*     */ 
/*  89 */     return this.onMouseClicked;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseDragged()
/*     */   {
/*  95 */     return this.onMouseDragged == null ? null : (EventHandler)this.onMouseDragged.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseDraggedProperty()
/*     */   {
/* 100 */     if (this.onMouseDragged == null) {
/* 101 */       this.onMouseDragged = new EventHandlerProperty(this.bean, "onMouseDragged", MouseEvent.MOUSE_DRAGGED);
/*     */     }
/*     */ 
/* 106 */     return this.onMouseDragged;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseEntered()
/*     */   {
/* 112 */     return this.onMouseEntered == null ? null : (EventHandler)this.onMouseEntered.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseEnteredProperty()
/*     */   {
/* 117 */     if (this.onMouseEntered == null) {
/* 118 */       this.onMouseEntered = new EventHandlerProperty(this.bean, "onMouseEntered", MouseEvent.MOUSE_ENTERED);
/*     */     }
/*     */ 
/* 123 */     return this.onMouseEntered;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseExited()
/*     */   {
/* 129 */     return this.onMouseExited == null ? null : (EventHandler)this.onMouseExited.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseExitedProperty()
/*     */   {
/* 134 */     if (this.onMouseExited == null) {
/* 135 */       this.onMouseExited = new EventHandlerProperty(this.bean, "onMouseExited", MouseEvent.MOUSE_EXITED);
/*     */     }
/*     */ 
/* 140 */     return this.onMouseExited;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseMoved()
/*     */   {
/* 146 */     return this.onMouseMoved == null ? null : (EventHandler)this.onMouseMoved.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseMovedProperty()
/*     */   {
/* 151 */     if (this.onMouseMoved == null) {
/* 152 */       this.onMouseMoved = new EventHandlerProperty(this.bean, "onMouseMoved", MouseEvent.MOUSE_MOVED);
/*     */     }
/*     */ 
/* 157 */     return this.onMouseMoved;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMousePressed()
/*     */   {
/* 163 */     return this.onMousePressed == null ? null : (EventHandler)this.onMousePressed.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMousePressedProperty()
/*     */   {
/* 168 */     if (this.onMousePressed == null) {
/* 169 */       this.onMousePressed = new EventHandlerProperty(this.bean, "onMousePressed", MouseEvent.MOUSE_PRESSED);
/*     */     }
/*     */ 
/* 174 */     return this.onMousePressed;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnMouseReleased()
/*     */   {
/* 180 */     return this.onMouseReleased == null ? null : (EventHandler)this.onMouseReleased.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onMouseReleasedProperty()
/*     */   {
/* 185 */     if (this.onMouseReleased == null) {
/* 186 */       this.onMouseReleased = new EventHandlerProperty(this.bean, "onMouseReleased", MouseEvent.MOUSE_RELEASED);
/*     */     }
/*     */ 
/* 191 */     return this.onMouseReleased;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseEvent> getOnDragDetected()
/*     */   {
/* 197 */     return this.onDragDetected == null ? null : (EventHandler)this.onDragDetected.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseEvent>> onDragDetectedProperty()
/*     */   {
/* 202 */     if (this.onDragDetected == null) {
/* 203 */       this.onDragDetected = new EventHandlerProperty(this.bean, "onDragDetected", MouseEvent.DRAG_DETECTED);
/*     */     }
/*     */ 
/* 208 */     return this.onDragDetected;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ScrollEvent> getOnScroll()
/*     */   {
/* 214 */     return this.onScroll == null ? null : (EventHandler)this.onScroll.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ScrollEvent>> onScrollProperty()
/*     */   {
/* 219 */     if (this.onScroll == null) {
/* 220 */       this.onScroll = new EventHandlerProperty(this.bean, "onScroll", ScrollEvent.SCROLL);
/*     */     }
/*     */ 
/* 225 */     return this.onScroll;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ScrollEvent> getOnScrollStarted()
/*     */   {
/* 231 */     return this.onScrollStarted == null ? null : (EventHandler)this.onScrollStarted.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ScrollEvent>> onScrollStartedProperty()
/*     */   {
/* 236 */     if (this.onScrollStarted == null) {
/* 237 */       this.onScrollStarted = new EventHandlerProperty(this.bean, "onScrollStarted", ScrollEvent.SCROLL_STARTED);
/*     */     }
/*     */ 
/* 242 */     return this.onScrollStarted;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ScrollEvent> getOnScrollFinished()
/*     */   {
/* 248 */     return this.onScrollFinished == null ? null : (EventHandler)this.onScrollFinished.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ScrollEvent>> onScrollFinishedProperty()
/*     */   {
/* 253 */     if (this.onScrollFinished == null) {
/* 254 */       this.onScrollFinished = new EventHandlerProperty(this.bean, "onScrollFinished", ScrollEvent.SCROLL_FINISHED);
/*     */     }
/*     */ 
/* 259 */     return this.onScrollFinished;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super RotateEvent> getOnRotationStarted()
/*     */   {
/* 265 */     return this.onRotationStarted == null ? null : (EventHandler)this.onRotationStarted.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super RotateEvent>> onRotationStartedProperty()
/*     */   {
/* 270 */     if (this.onRotationStarted == null) {
/* 271 */       this.onRotationStarted = new EventHandlerProperty(this.bean, "onRotationStarted", RotateEvent.ROTATION_STARTED);
/*     */     }
/*     */ 
/* 276 */     return this.onRotationStarted;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super RotateEvent> getOnRotate()
/*     */   {
/* 282 */     return this.onRotate == null ? null : (EventHandler)this.onRotate.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super RotateEvent>> onRotateProperty()
/*     */   {
/* 287 */     if (this.onRotate == null) {
/* 288 */       this.onRotate = new EventHandlerProperty(this.bean, "onRotate", RotateEvent.ROTATE);
/*     */     }
/*     */ 
/* 293 */     return this.onRotate;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super RotateEvent> getOnRotationFinished()
/*     */   {
/* 299 */     return this.onRotationFinished == null ? null : (EventHandler)this.onRotationFinished.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super RotateEvent>> onRotationFinishedProperty()
/*     */   {
/* 304 */     if (this.onRotationFinished == null) {
/* 305 */       this.onRotationFinished = new EventHandlerProperty(this.bean, "onRotationFinished", RotateEvent.ROTATION_FINISHED);
/*     */     }
/*     */ 
/* 310 */     return this.onRotationFinished;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ZoomEvent> getOnZoomStarted()
/*     */   {
/* 316 */     return this.onZoomStarted == null ? null : (EventHandler)this.onZoomStarted.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ZoomEvent>> onZoomStartedProperty()
/*     */   {
/* 321 */     if (this.onZoomStarted == null) {
/* 322 */       this.onZoomStarted = new EventHandlerProperty(this.bean, "onZoomStarted", ZoomEvent.ZOOM_STARTED);
/*     */     }
/*     */ 
/* 327 */     return this.onZoomStarted;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ZoomEvent> getOnZoom()
/*     */   {
/* 333 */     return this.onZoom == null ? null : (EventHandler)this.onZoom.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ZoomEvent>> onZoomProperty()
/*     */   {
/* 338 */     if (this.onZoom == null) {
/* 339 */       this.onZoom = new EventHandlerProperty(this.bean, "onZoom", ZoomEvent.ZOOM);
/*     */     }
/*     */ 
/* 344 */     return this.onZoom;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super ZoomEvent> getOnZoomFinished()
/*     */   {
/* 350 */     return this.onZoomFinished == null ? null : (EventHandler)this.onZoomFinished.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super ZoomEvent>> onZoomFinishedProperty()
/*     */   {
/* 355 */     if (this.onZoomFinished == null) {
/* 356 */       this.onZoomFinished = new EventHandlerProperty(this.bean, "onZoomFinished", ZoomEvent.ZOOM_FINISHED);
/*     */     }
/*     */ 
/* 361 */     return this.onZoomFinished;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super SwipeEvent> getOnSwipeUp()
/*     */   {
/* 367 */     return this.onSwipeUp == null ? null : (EventHandler)this.onSwipeUp.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeUpProperty()
/*     */   {
/* 372 */     if (this.onSwipeUp == null) {
/* 373 */       this.onSwipeUp = new EventHandlerProperty(this.bean, "onSwipeUp", SwipeEvent.SWIPE_UP);
/*     */     }
/*     */ 
/* 378 */     return this.onSwipeUp;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super SwipeEvent> getOnSwipeDown()
/*     */   {
/* 384 */     return this.onSwipeDown == null ? null : (EventHandler)this.onSwipeDown.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeDownProperty()
/*     */   {
/* 389 */     if (this.onSwipeDown == null) {
/* 390 */       this.onSwipeDown = new EventHandlerProperty(this.bean, "onSwipeDown", SwipeEvent.SWIPE_DOWN);
/*     */     }
/*     */ 
/* 395 */     return this.onSwipeDown;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super SwipeEvent> getOnSwipeLeft()
/*     */   {
/* 401 */     return this.onSwipeLeft == null ? null : (EventHandler)this.onSwipeLeft.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeLeftProperty()
/*     */   {
/* 406 */     if (this.onSwipeLeft == null) {
/* 407 */       this.onSwipeLeft = new EventHandlerProperty(this.bean, "onSwipeLeft", SwipeEvent.SWIPE_LEFT);
/*     */     }
/*     */ 
/* 412 */     return this.onSwipeLeft;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super SwipeEvent> getOnSwipeRight()
/*     */   {
/* 418 */     return this.onSwipeRight == null ? null : (EventHandler)this.onSwipeRight.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super SwipeEvent>> onSwipeRightProperty()
/*     */   {
/* 423 */     if (this.onSwipeRight == null) {
/* 424 */       this.onSwipeRight = new EventHandlerProperty(this.bean, "onSwipeRight", SwipeEvent.SWIPE_RIGHT);
/*     */     }
/*     */ 
/* 429 */     return this.onSwipeRight;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseDragEvent> getOnMouseDragOver()
/*     */   {
/* 435 */     return this.onMouseDragOver == null ? null : (EventHandler)this.onMouseDragOver.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragOverProperty()
/*     */   {
/* 440 */     if (this.onMouseDragOver == null) {
/* 441 */       this.onMouseDragOver = new EventHandlerProperty(this.bean, "onMouseDragOver", MouseDragEvent.MOUSE_DRAG_OVER);
/*     */     }
/*     */ 
/* 446 */     return this.onMouseDragOver;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseDragEvent> getOnMouseDragReleased()
/*     */   {
/* 452 */     return this.onMouseDragReleased == null ? null : (EventHandler)this.onMouseDragReleased.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragReleasedProperty()
/*     */   {
/* 457 */     if (this.onMouseDragReleased == null) {
/* 458 */       this.onMouseDragReleased = new EventHandlerProperty(this.bean, "onMouseDragReleased", MouseDragEvent.MOUSE_DRAG_RELEASED);
/*     */     }
/*     */ 
/* 463 */     return this.onMouseDragReleased;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseDragEvent> getOnMouseDragEntered()
/*     */   {
/* 469 */     return this.onMouseDragEntered == null ? null : (EventHandler)this.onMouseDragEntered.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragEnteredProperty()
/*     */   {
/* 474 */     if (this.onMouseDragEntered == null) {
/* 475 */       this.onMouseDragEntered = new EventHandlerProperty(this.bean, "onMouseDragEntered", MouseDragEvent.MOUSE_DRAG_ENTERED);
/*     */     }
/*     */ 
/* 480 */     return this.onMouseDragEntered;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super MouseDragEvent> getOnMouseDragExited()
/*     */   {
/* 486 */     return this.onMouseDragExited == null ? null : (EventHandler)this.onMouseDragExited.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super MouseDragEvent>> onMouseDragExitedProperty()
/*     */   {
/* 491 */     if (this.onMouseDragExited == null) {
/* 492 */       this.onMouseDragExited = new EventHandlerProperty(this.bean, "onMouseDragExited", MouseDragEvent.MOUSE_DRAG_EXITED);
/*     */     }
/*     */ 
/* 497 */     return this.onMouseDragExited;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super KeyEvent> getOnKeyPressed()
/*     */   {
/* 503 */     return this.onKeyPressed == null ? null : (EventHandler)this.onKeyPressed.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super KeyEvent>> onKeyPressedProperty()
/*     */   {
/* 508 */     if (this.onKeyPressed == null) {
/* 509 */       this.onKeyPressed = new EventHandlerProperty(this.bean, "onKeyPressed", KeyEvent.KEY_PRESSED);
/*     */     }
/*     */ 
/* 514 */     return this.onKeyPressed;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super KeyEvent> getOnKeyReleased()
/*     */   {
/* 520 */     return this.onKeyReleased == null ? null : (EventHandler)this.onKeyReleased.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super KeyEvent>> onKeyReleasedProperty()
/*     */   {
/* 525 */     if (this.onKeyReleased == null) {
/* 526 */       this.onKeyReleased = new EventHandlerProperty(this.bean, "onKeyReleased", KeyEvent.KEY_RELEASED);
/*     */     }
/*     */ 
/* 531 */     return this.onKeyReleased;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super KeyEvent> getOnKeyTyped()
/*     */   {
/* 537 */     return this.onKeyTyped == null ? null : (EventHandler)this.onKeyTyped.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super KeyEvent>> onKeyTypedProperty()
/*     */   {
/* 542 */     if (this.onKeyTyped == null) {
/* 543 */       this.onKeyTyped = new EventHandlerProperty(this.bean, "onKeyTyped", KeyEvent.KEY_TYPED);
/*     */     }
/*     */ 
/* 548 */     return this.onKeyTyped;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super InputMethodEvent> getOnInputMethodTextChanged()
/*     */   {
/* 555 */     return this.onInputMethodTextChanged == null ? null : (EventHandler)this.onInputMethodTextChanged.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super InputMethodEvent>> onInputMethodTextChangedProperty()
/*     */   {
/* 561 */     if (this.onInputMethodTextChanged == null) {
/* 562 */       this.onInputMethodTextChanged = new EventHandlerProperty(this.bean, "onInputMethodTextChanged", InputMethodEvent.INPUT_METHOD_TEXT_CHANGED);
/*     */     }
/*     */ 
/* 568 */     return this.onInputMethodTextChanged;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super DragEvent> getOnDragEntered()
/*     */   {
/* 574 */     return this.onDragEntered == null ? null : (EventHandler)this.onDragEntered.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super DragEvent>> onDragEnteredProperty()
/*     */   {
/* 579 */     if (this.onDragEntered == null) {
/* 580 */       this.onDragEntered = new EventHandlerProperty(this.bean, "onDragEntered", DragEvent.DRAG_ENTERED);
/*     */     }
/*     */ 
/* 585 */     return this.onDragEntered;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super DragEvent> getOnDragExited()
/*     */   {
/* 591 */     return this.onDragExited == null ? null : (EventHandler)this.onDragExited.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super DragEvent>> onDragExitedProperty()
/*     */   {
/* 596 */     if (this.onDragExited == null) {
/* 597 */       this.onDragExited = new EventHandlerProperty(this.bean, "onDragExited", DragEvent.DRAG_EXITED);
/*     */     }
/*     */ 
/* 602 */     return this.onDragExited;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super DragEvent> getOnDragOver()
/*     */   {
/* 608 */     return this.onDragOver == null ? null : (EventHandler)this.onDragOver.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super DragEvent>> onDragOverProperty()
/*     */   {
/* 613 */     if (this.onDragOver == null) {
/* 614 */       this.onDragOver = new EventHandlerProperty(this.bean, "onDragOver", DragEvent.DRAG_OVER);
/*     */     }
/*     */ 
/* 619 */     return this.onDragOver;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super DragEvent> getOnDragDropped()
/*     */   {
/* 642 */     return this.onDragDropped == null ? null : (EventHandler)this.onDragDropped.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super DragEvent>> onDragDroppedProperty()
/*     */   {
/* 647 */     if (this.onDragDropped == null) {
/* 648 */       this.onDragDropped = new EventHandlerProperty(this.bean, "onDragDropped", DragEvent.DRAG_DROPPED);
/*     */     }
/*     */ 
/* 653 */     return this.onDragDropped;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super DragEvent> getOnDragDone()
/*     */   {
/* 659 */     return this.onDragDone == null ? null : (EventHandler)this.onDragDone.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super DragEvent>> onDragDoneProperty()
/*     */   {
/* 664 */     if (this.onDragDone == null) {
/* 665 */       this.onDragDone = new EventHandlerProperty(this.bean, "onDragDone", DragEvent.DRAG_DONE);
/*     */     }
/*     */ 
/* 670 */     return this.onDragDone;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super TouchEvent> getOnTouchPressed()
/*     */   {
/* 693 */     return this.onTouchPressed == null ? null : (EventHandler)this.onTouchPressed.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super TouchEvent>> onTouchPressedProperty()
/*     */   {
/* 698 */     if (this.onTouchPressed == null) {
/* 699 */       this.onTouchPressed = new EventHandlerProperty(this.bean, "onTouchPressed", TouchEvent.TOUCH_PRESSED);
/*     */     }
/*     */ 
/* 704 */     return this.onTouchPressed;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super TouchEvent> getOnTouchMoved()
/*     */   {
/* 710 */     return this.onTouchMoved == null ? null : (EventHandler)this.onTouchMoved.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super TouchEvent>> onTouchMovedProperty()
/*     */   {
/* 715 */     if (this.onTouchMoved == null) {
/* 716 */       this.onTouchMoved = new EventHandlerProperty(this.bean, "onTouchMoved", TouchEvent.TOUCH_MOVED);
/*     */     }
/*     */ 
/* 721 */     return this.onTouchMoved;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super TouchEvent> getOnTouchReleased()
/*     */   {
/* 727 */     return this.onTouchReleased == null ? null : (EventHandler)this.onTouchReleased.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super TouchEvent>> onTouchReleasedProperty()
/*     */   {
/* 732 */     if (this.onTouchReleased == null) {
/* 733 */       this.onTouchReleased = new EventHandlerProperty(this.bean, "onTouchReleased", TouchEvent.TOUCH_RELEASED);
/*     */     }
/*     */ 
/* 738 */     return this.onTouchReleased;
/*     */   }
/*     */ 
/*     */   public final EventHandler<? super TouchEvent> getOnTouchStationary()
/*     */   {
/* 744 */     return this.onTouchStationary == null ? null : (EventHandler)this.onTouchStationary.get();
/*     */   }
/*     */ 
/*     */   public ObjectProperty<EventHandler<? super TouchEvent>> onTouchStationaryProperty()
/*     */   {
/* 749 */     if (this.onTouchStationary == null) {
/* 750 */       this.onTouchStationary = new EventHandlerProperty(this.bean, "onTouchStationary", TouchEvent.TOUCH_STATIONARY);
/*     */     }
/*     */ 
/* 755 */     return this.onTouchStationary;
/*     */   }
/*     */ 
/*     */   private final class EventHandlerProperty<T extends Event> extends SimpleObjectProperty<EventHandler<? super T>>
/*     */   {
/*     */     private final EventType<T> eventType;
/*     */ 
/*     */     public EventHandlerProperty(String paramEventType, EventType<T> arg3)
/*     */     {
/* 680 */       super(str);
/*     */       Object localObject;
/* 681 */       this.eventType = localObject;
/*     */     }
/*     */ 
/*     */     protected void invalidated()
/*     */     {
/* 686 */       EventHandlerProperties.this.eventDispatcher.setEventHandler(this.eventType, (EventHandler)get());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.EventHandlerProperties
 * JD-Core Version:    0.6.2
 */