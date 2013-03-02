/*     */ package javafx.scene;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventDispatcher;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Point3D;
/*     */ import javafx.scene.effect.BlendMode;
/*     */ import javafx.scene.effect.Effect;
/*     */ import javafx.scene.input.ContextMenuEvent;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseDragEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.RotateEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.input.SwipeEvent;
/*     */ import javafx.scene.input.TouchEvent;
/*     */ import javafx.scene.input.ZoomEvent;
/*     */ import javafx.scene.transform.Transform;
/*     */ 
/*     */ public abstract class NodeBuilder<B extends NodeBuilder<B>>
/*     */ {
/*  13 */   BitSet __set = new BitSet();
/*     */   private BlendMode blendMode;
/*     */   private boolean cache;
/*     */   private CacheHint cacheHint;
/*     */   private Node clip;
/*     */   private Cursor cursor;
/*     */   private DepthTest depthTest;
/*     */   private boolean disable;
/*     */   private Effect effect;
/*     */   private EventDispatcher eventDispatcher;
/*     */   private boolean focusTraversable;
/*     */   private String id;
/*     */   private InputMethodRequests inputMethodRequests;
/*     */   private double layoutX;
/*     */   private double layoutY;
/*     */   private boolean managed;
/*     */   private boolean mouseTransparent;
/*     */   private EventHandler<? super ContextMenuEvent> onContextMenuRequested;
/*     */   private EventHandler<? super MouseEvent> onDragDetected;
/*     */   private EventHandler<? super DragEvent> onDragDone;
/*     */   private EventHandler<? super DragEvent> onDragDropped;
/*     */   private EventHandler<? super DragEvent> onDragEntered;
/*     */   private EventHandler<? super DragEvent> onDragExited;
/*     */   private EventHandler<? super DragEvent> onDragOver;
/*     */   private EventHandler<? super InputMethodEvent> onInputMethodTextChanged;
/*     */   private EventHandler<? super KeyEvent> onKeyPressed;
/*     */   private EventHandler<? super KeyEvent> onKeyReleased;
/*     */   private EventHandler<? super KeyEvent> onKeyTyped;
/*     */   private EventHandler<? super MouseEvent> onMouseClicked;
/*     */   private EventHandler<? super MouseDragEvent> onMouseDragEntered;
/*     */   private EventHandler<? super MouseDragEvent> onMouseDragExited;
/*     */   private EventHandler<? super MouseEvent> onMouseDragged;
/*     */   private EventHandler<? super MouseDragEvent> onMouseDragOver;
/*     */   private EventHandler<? super MouseDragEvent> onMouseDragReleased;
/*     */   private EventHandler<? super MouseEvent> onMouseEntered;
/*     */   private EventHandler<? super MouseEvent> onMouseExited;
/*     */   private EventHandler<? super MouseEvent> onMouseMoved;
/*     */   private EventHandler<? super MouseEvent> onMousePressed;
/*     */   private EventHandler<? super MouseEvent> onMouseReleased;
/*     */   private EventHandler<? super RotateEvent> onRotate;
/*     */   private EventHandler<? super RotateEvent> onRotationFinished;
/*     */   private EventHandler<? super RotateEvent> onRotationStarted;
/*     */   private EventHandler<? super ScrollEvent> onScroll;
/*     */   private EventHandler<? super ScrollEvent> onScrollFinished;
/*     */   private EventHandler<? super ScrollEvent> onScrollStarted;
/*     */   private EventHandler<? super SwipeEvent> onSwipeDown;
/*     */   private EventHandler<? super SwipeEvent> onSwipeLeft;
/*     */   private EventHandler<? super SwipeEvent> onSwipeRight;
/*     */   private EventHandler<? super SwipeEvent> onSwipeUp;
/*     */   private EventHandler<? super TouchEvent> onTouchMoved;
/*     */   private EventHandler<? super TouchEvent> onTouchPressed;
/*     */   private EventHandler<? super TouchEvent> onTouchReleased;
/*     */   private EventHandler<? super TouchEvent> onTouchStationary;
/*     */   private EventHandler<? super ZoomEvent> onZoom;
/*     */   private EventHandler<? super ZoomEvent> onZoomFinished;
/*     */   private EventHandler<? super ZoomEvent> onZoomStarted;
/*     */   private double opacity;
/*     */   private boolean pickOnBounds;
/*     */   private double rotate;
/*     */   private Point3D rotationAxis;
/*     */   private double scaleX;
/*     */   private double scaleY;
/*     */   private double scaleZ;
/*     */   private String style;
/*     */   private Collection<? extends String> styleClass;
/*     */   private Collection<? extends Transform> transforms;
/*     */   private double translateX;
/*     */   private double translateY;
/*     */   private double translateZ;
/*     */   private Object userData;
/*     */   private boolean visible;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set.set(paramInt);
/*     */   }
/*     */   public void applyTo(Node paramNode) {
/*  18 */     BitSet localBitSet = this.__set;
/*  19 */     for (int i = -1; (i = localBitSet.nextSetBit(i + 1)) >= 0; )
/*  20 */       switch (i) { case 0:
/*  21 */         paramNode.setBlendMode(this.blendMode); break;
/*     */       case 1:
/*  22 */         paramNode.setCache(this.cache); break;
/*     */       case 2:
/*  23 */         paramNode.setCacheHint(this.cacheHint); break;
/*     */       case 3:
/*  24 */         paramNode.setClip(this.clip); break;
/*     */       case 4:
/*  25 */         paramNode.setCursor(this.cursor); break;
/*     */       case 5:
/*  26 */         paramNode.setDepthTest(this.depthTest); break;
/*     */       case 6:
/*  27 */         paramNode.setDisable(this.disable); break;
/*     */       case 7:
/*  28 */         paramNode.setEffect(this.effect); break;
/*     */       case 8:
/*  29 */         paramNode.setEventDispatcher(this.eventDispatcher); break;
/*     */       case 9:
/*  30 */         paramNode.setFocusTraversable(this.focusTraversable); break;
/*     */       case 10:
/*  31 */         paramNode.setId(this.id); break;
/*     */       case 11:
/*  32 */         paramNode.setInputMethodRequests(this.inputMethodRequests); break;
/*     */       case 12:
/*  33 */         paramNode.setLayoutX(this.layoutX); break;
/*     */       case 13:
/*  34 */         paramNode.setLayoutY(this.layoutY); break;
/*     */       case 14:
/*  35 */         paramNode.setManaged(this.managed); break;
/*     */       case 15:
/*  36 */         paramNode.setMouseTransparent(this.mouseTransparent); break;
/*     */       case 16:
/*  37 */         paramNode.setOnContextMenuRequested(this.onContextMenuRequested); break;
/*     */       case 17:
/*  38 */         paramNode.setOnDragDetected(this.onDragDetected); break;
/*     */       case 18:
/*  39 */         paramNode.setOnDragDone(this.onDragDone); break;
/*     */       case 19:
/*  40 */         paramNode.setOnDragDropped(this.onDragDropped); break;
/*     */       case 20:
/*  41 */         paramNode.setOnDragEntered(this.onDragEntered); break;
/*     */       case 21:
/*  42 */         paramNode.setOnDragExited(this.onDragExited); break;
/*     */       case 22:
/*  43 */         paramNode.setOnDragOver(this.onDragOver); break;
/*     */       case 23:
/*  44 */         paramNode.setOnInputMethodTextChanged(this.onInputMethodTextChanged); break;
/*     */       case 24:
/*  45 */         paramNode.setOnKeyPressed(this.onKeyPressed); break;
/*     */       case 25:
/*  46 */         paramNode.setOnKeyReleased(this.onKeyReleased); break;
/*     */       case 26:
/*  47 */         paramNode.setOnKeyTyped(this.onKeyTyped); break;
/*     */       case 27:
/*  48 */         paramNode.setOnMouseClicked(this.onMouseClicked); break;
/*     */       case 28:
/*  49 */         paramNode.setOnMouseDragEntered(this.onMouseDragEntered); break;
/*     */       case 29:
/*  50 */         paramNode.setOnMouseDragExited(this.onMouseDragExited); break;
/*     */       case 30:
/*  51 */         paramNode.setOnMouseDragged(this.onMouseDragged); break;
/*     */       case 31:
/*  52 */         paramNode.setOnMouseDragOver(this.onMouseDragOver); break;
/*     */       case 32:
/*  53 */         paramNode.setOnMouseDragReleased(this.onMouseDragReleased); break;
/*     */       case 33:
/*  54 */         paramNode.setOnMouseEntered(this.onMouseEntered); break;
/*     */       case 34:
/*  55 */         paramNode.setOnMouseExited(this.onMouseExited); break;
/*     */       case 35:
/*  56 */         paramNode.setOnMouseMoved(this.onMouseMoved); break;
/*     */       case 36:
/*  57 */         paramNode.setOnMousePressed(this.onMousePressed); break;
/*     */       case 37:
/*  58 */         paramNode.setOnMouseReleased(this.onMouseReleased); break;
/*     */       case 38:
/*  59 */         paramNode.setOnRotate(this.onRotate); break;
/*     */       case 39:
/*  60 */         paramNode.setOnRotationFinished(this.onRotationFinished); break;
/*     */       case 40:
/*  61 */         paramNode.setOnRotationStarted(this.onRotationStarted); break;
/*     */       case 41:
/*  62 */         paramNode.setOnScroll(this.onScroll); break;
/*     */       case 42:
/*  63 */         paramNode.setOnScrollFinished(this.onScrollFinished); break;
/*     */       case 43:
/*  64 */         paramNode.setOnScrollStarted(this.onScrollStarted); break;
/*     */       case 44:
/*  65 */         paramNode.setOnSwipeDown(this.onSwipeDown); break;
/*     */       case 45:
/*  66 */         paramNode.setOnSwipeLeft(this.onSwipeLeft); break;
/*     */       case 46:
/*  67 */         paramNode.setOnSwipeRight(this.onSwipeRight); break;
/*     */       case 47:
/*  68 */         paramNode.setOnSwipeUp(this.onSwipeUp); break;
/*     */       case 48:
/*  69 */         paramNode.setOnTouchMoved(this.onTouchMoved); break;
/*     */       case 49:
/*  70 */         paramNode.setOnTouchPressed(this.onTouchPressed); break;
/*     */       case 50:
/*  71 */         paramNode.setOnTouchReleased(this.onTouchReleased); break;
/*     */       case 51:
/*  72 */         paramNode.setOnTouchStationary(this.onTouchStationary); break;
/*     */       case 52:
/*  73 */         paramNode.setOnZoom(this.onZoom); break;
/*     */       case 53:
/*  74 */         paramNode.setOnZoomFinished(this.onZoomFinished); break;
/*     */       case 54:
/*  75 */         paramNode.setOnZoomStarted(this.onZoomStarted); break;
/*     */       case 55:
/*  76 */         paramNode.setOpacity(this.opacity); break;
/*     */       case 56:
/*  77 */         paramNode.setPickOnBounds(this.pickOnBounds); break;
/*     */       case 57:
/*  78 */         paramNode.setRotate(this.rotate); break;
/*     */       case 58:
/*  79 */         paramNode.setRotationAxis(this.rotationAxis); break;
/*     */       case 59:
/*  80 */         paramNode.setScaleX(this.scaleX); break;
/*     */       case 60:
/*  81 */         paramNode.setScaleY(this.scaleY); break;
/*     */       case 61:
/*  82 */         paramNode.setScaleZ(this.scaleZ); break;
/*     */       case 62:
/*  83 */         paramNode.setStyle(this.style); break;
/*     */       case 63:
/*  84 */         paramNode.getStyleClass().addAll(this.styleClass); break;
/*     */       case 64:
/*  85 */         paramNode.getTransforms().addAll(this.transforms); break;
/*     */       case 65:
/*  86 */         paramNode.setTranslateX(this.translateX); break;
/*     */       case 66:
/*  87 */         paramNode.setTranslateY(this.translateY); break;
/*     */       case 67:
/*  88 */         paramNode.setTranslateZ(this.translateZ); break;
/*     */       case 68:
/*  89 */         paramNode.setUserData(this.userData); break;
/*     */       case 69:
/*  90 */         paramNode.setVisible(this.visible);
/*     */       }
/*     */   }
/*     */ 
/*     */   public B blendMode(BlendMode paramBlendMode)
/*     */   {
/* 101 */     this.blendMode = paramBlendMode;
/* 102 */     __set(0);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   public B cache(boolean paramBoolean)
/*     */   {
/* 112 */     this.cache = paramBoolean;
/* 113 */     __set(1);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public B cacheHint(CacheHint paramCacheHint)
/*     */   {
/* 123 */     this.cacheHint = paramCacheHint;
/* 124 */     __set(2);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   public B clip(Node paramNode)
/*     */   {
/* 134 */     this.clip = paramNode;
/* 135 */     __set(3);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public B cursor(Cursor paramCursor)
/*     */   {
/* 145 */     this.cursor = paramCursor;
/* 146 */     __set(4);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public B depthTest(DepthTest paramDepthTest)
/*     */   {
/* 156 */     this.depthTest = paramDepthTest;
/* 157 */     __set(5);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public B disable(boolean paramBoolean)
/*     */   {
/* 167 */     this.disable = paramBoolean;
/* 168 */     __set(6);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public B effect(Effect paramEffect)
/*     */   {
/* 178 */     this.effect = paramEffect;
/* 179 */     __set(7);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public B eventDispatcher(EventDispatcher paramEventDispatcher)
/*     */   {
/* 189 */     this.eventDispatcher = paramEventDispatcher;
/* 190 */     __set(8);
/* 191 */     return this;
/*     */   }
/*     */ 
/*     */   public B focusTraversable(boolean paramBoolean)
/*     */   {
/* 200 */     this.focusTraversable = paramBoolean;
/* 201 */     __set(9);
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */   public B id(String paramString)
/*     */   {
/* 211 */     this.id = paramString;
/* 212 */     __set(10);
/* 213 */     return this;
/*     */   }
/*     */ 
/*     */   public B inputMethodRequests(InputMethodRequests paramInputMethodRequests)
/*     */   {
/* 222 */     this.inputMethodRequests = paramInputMethodRequests;
/* 223 */     __set(11);
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */   public B layoutX(double paramDouble)
/*     */   {
/* 233 */     this.layoutX = paramDouble;
/* 234 */     __set(12);
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */   public B layoutY(double paramDouble)
/*     */   {
/* 244 */     this.layoutY = paramDouble;
/* 245 */     __set(13);
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   public B managed(boolean paramBoolean)
/*     */   {
/* 255 */     this.managed = paramBoolean;
/* 256 */     __set(14);
/* 257 */     return this;
/*     */   }
/*     */ 
/*     */   public B mouseTransparent(boolean paramBoolean)
/*     */   {
/* 266 */     this.mouseTransparent = paramBoolean;
/* 267 */     __set(15);
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public B onContextMenuRequested(EventHandler<? super ContextMenuEvent> paramEventHandler)
/*     */   {
/* 277 */     this.onContextMenuRequested = paramEventHandler;
/* 278 */     __set(16);
/* 279 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDetected(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 288 */     this.onDragDetected = paramEventHandler;
/* 289 */     __set(17);
/* 290 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDone(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 299 */     this.onDragDone = paramEventHandler;
/* 300 */     __set(18);
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDropped(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 310 */     this.onDragDropped = paramEventHandler;
/* 311 */     __set(19);
/* 312 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragEntered(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 321 */     this.onDragEntered = paramEventHandler;
/* 322 */     __set(20);
/* 323 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragExited(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 332 */     this.onDragExited = paramEventHandler;
/* 333 */     __set(21);
/* 334 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragOver(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 343 */     this.onDragOver = paramEventHandler;
/* 344 */     __set(22);
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */   public B onInputMethodTextChanged(EventHandler<? super InputMethodEvent> paramEventHandler)
/*     */   {
/* 354 */     this.onInputMethodTextChanged = paramEventHandler;
/* 355 */     __set(23);
/* 356 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyPressed(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 365 */     this.onKeyPressed = paramEventHandler;
/* 366 */     __set(24);
/* 367 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyReleased(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 376 */     this.onKeyReleased = paramEventHandler;
/* 377 */     __set(25);
/* 378 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyTyped(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 387 */     this.onKeyTyped = paramEventHandler;
/* 388 */     __set(26);
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseClicked(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 398 */     this.onMouseClicked = paramEventHandler;
/* 399 */     __set(27);
/* 400 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragEntered(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 409 */     this.onMouseDragEntered = paramEventHandler;
/* 410 */     __set(28);
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragExited(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 420 */     this.onMouseDragExited = paramEventHandler;
/* 421 */     __set(29);
/* 422 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragged(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 431 */     this.onMouseDragged = paramEventHandler;
/* 432 */     __set(30);
/* 433 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragOver(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 442 */     this.onMouseDragOver = paramEventHandler;
/* 443 */     __set(31);
/* 444 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragReleased(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 453 */     this.onMouseDragReleased = paramEventHandler;
/* 454 */     __set(32);
/* 455 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseEntered(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 464 */     this.onMouseEntered = paramEventHandler;
/* 465 */     __set(33);
/* 466 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseExited(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 475 */     this.onMouseExited = paramEventHandler;
/* 476 */     __set(34);
/* 477 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseMoved(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 486 */     this.onMouseMoved = paramEventHandler;
/* 487 */     __set(35);
/* 488 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMousePressed(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 497 */     this.onMousePressed = paramEventHandler;
/* 498 */     __set(36);
/* 499 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseReleased(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 508 */     this.onMouseReleased = paramEventHandler;
/* 509 */     __set(37);
/* 510 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotate(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 519 */     this.onRotate = paramEventHandler;
/* 520 */     __set(38);
/* 521 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotationFinished(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 530 */     this.onRotationFinished = paramEventHandler;
/* 531 */     __set(39);
/* 532 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotationStarted(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 541 */     this.onRotationStarted = paramEventHandler;
/* 542 */     __set(40);
/* 543 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScroll(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 552 */     this.onScroll = paramEventHandler;
/* 553 */     __set(41);
/* 554 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScrollFinished(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 563 */     this.onScrollFinished = paramEventHandler;
/* 564 */     __set(42);
/* 565 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScrollStarted(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 574 */     this.onScrollStarted = paramEventHandler;
/* 575 */     __set(43);
/* 576 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeDown(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 585 */     this.onSwipeDown = paramEventHandler;
/* 586 */     __set(44);
/* 587 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeLeft(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 596 */     this.onSwipeLeft = paramEventHandler;
/* 597 */     __set(45);
/* 598 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeRight(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 607 */     this.onSwipeRight = paramEventHandler;
/* 608 */     __set(46);
/* 609 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeUp(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 618 */     this.onSwipeUp = paramEventHandler;
/* 619 */     __set(47);
/* 620 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchMoved(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 629 */     this.onTouchMoved = paramEventHandler;
/* 630 */     __set(48);
/* 631 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchPressed(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 640 */     this.onTouchPressed = paramEventHandler;
/* 641 */     __set(49);
/* 642 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchReleased(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 651 */     this.onTouchReleased = paramEventHandler;
/* 652 */     __set(50);
/* 653 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchStationary(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 662 */     this.onTouchStationary = paramEventHandler;
/* 663 */     __set(51);
/* 664 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoom(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 673 */     this.onZoom = paramEventHandler;
/* 674 */     __set(52);
/* 675 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoomFinished(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 684 */     this.onZoomFinished = paramEventHandler;
/* 685 */     __set(53);
/* 686 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoomStarted(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 695 */     this.onZoomStarted = paramEventHandler;
/* 696 */     __set(54);
/* 697 */     return this;
/*     */   }
/*     */ 
/*     */   public B opacity(double paramDouble)
/*     */   {
/* 706 */     this.opacity = paramDouble;
/* 707 */     __set(55);
/* 708 */     return this;
/*     */   }
/*     */ 
/*     */   public B pickOnBounds(boolean paramBoolean)
/*     */   {
/* 717 */     this.pickOnBounds = paramBoolean;
/* 718 */     __set(56);
/* 719 */     return this;
/*     */   }
/*     */ 
/*     */   public B rotate(double paramDouble)
/*     */   {
/* 728 */     this.rotate = paramDouble;
/* 729 */     __set(57);
/* 730 */     return this;
/*     */   }
/*     */ 
/*     */   public B rotationAxis(Point3D paramPoint3D)
/*     */   {
/* 739 */     this.rotationAxis = paramPoint3D;
/* 740 */     __set(58);
/* 741 */     return this;
/*     */   }
/*     */ 
/*     */   public B scaleX(double paramDouble)
/*     */   {
/* 750 */     this.scaleX = paramDouble;
/* 751 */     __set(59);
/* 752 */     return this;
/*     */   }
/*     */ 
/*     */   public B scaleY(double paramDouble)
/*     */   {
/* 761 */     this.scaleY = paramDouble;
/* 762 */     __set(60);
/* 763 */     return this;
/*     */   }
/*     */ 
/*     */   public B scaleZ(double paramDouble)
/*     */   {
/* 772 */     this.scaleZ = paramDouble;
/* 773 */     __set(61);
/* 774 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(String paramString)
/*     */   {
/* 783 */     this.style = paramString;
/* 784 */     __set(62);
/* 785 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(Collection<? extends String> paramCollection)
/*     */   {
/* 794 */     this.styleClass = paramCollection;
/* 795 */     __set(63);
/* 796 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(String[] paramArrayOfString)
/*     */   {
/* 803 */     return styleClass(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public B transforms(Collection<? extends Transform> paramCollection)
/*     */   {
/* 812 */     this.transforms = paramCollection;
/* 813 */     __set(64);
/* 814 */     return this;
/*     */   }
/*     */ 
/*     */   public B transforms(Transform[] paramArrayOfTransform)
/*     */   {
/* 821 */     return transforms(Arrays.asList(paramArrayOfTransform));
/*     */   }
/*     */ 
/*     */   public B translateX(double paramDouble)
/*     */   {
/* 830 */     this.translateX = paramDouble;
/* 831 */     __set(65);
/* 832 */     return this;
/*     */   }
/*     */ 
/*     */   public B translateY(double paramDouble)
/*     */   {
/* 841 */     this.translateY = paramDouble;
/* 842 */     __set(66);
/* 843 */     return this;
/*     */   }
/*     */ 
/*     */   public B translateZ(double paramDouble)
/*     */   {
/* 852 */     this.translateZ = paramDouble;
/* 853 */     __set(67);
/* 854 */     return this;
/*     */   }
/*     */ 
/*     */   public B userData(Object paramObject)
/*     */   {
/* 863 */     this.userData = paramObject;
/* 864 */     __set(68);
/* 865 */     return this;
/*     */   }
/*     */ 
/*     */   public B visible(boolean paramBoolean)
/*     */   {
/* 874 */     this.visible = paramBoolean;
/* 875 */     __set(69);
/* 876 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.NodeBuilder
 * JD-Core Version:    0.6.2
 */