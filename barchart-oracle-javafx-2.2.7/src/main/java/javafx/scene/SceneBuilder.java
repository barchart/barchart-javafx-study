/*     */ package javafx.scene;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventDispatcher;
/*     */ import javafx.event.EventHandler;
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
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class SceneBuilder<B extends SceneBuilder<B>>
/*     */   implements Builder<Scene>
/*     */ {
/*     */   private long __set;
/*     */   private Camera camera;
/*     */   private Cursor cursor;
/*     */   private boolean depthBuffer;
/*     */   private EventDispatcher eventDispatcher;
/*     */   private Paint fill;
/* 130 */   private double height = -1.0D;
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
/*     */   private Parent root;
/*     */   private Collection<? extends String> stylesheets;
/* 597 */   private double width = -1.0D;
/*     */ 
/*     */   public static SceneBuilder<?> create()
/*     */   {
/*  15 */     return new SceneBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1L << paramInt;
/*     */   }
/*     */   public void applyTo(Scene paramScene) {
/*  23 */     long l = this.__set;
/*  24 */     while (l != 0L) {
/*  25 */       int i = Long.numberOfTrailingZeros(l);
/*  26 */       l &= (1L << i ^ 0xFFFFFFFF);
/*  27 */       switch (i) { case 0:
/*  28 */         paramScene.setCamera(this.camera); break;
/*     */       case 1:
/*  29 */         paramScene.setCursor(this.cursor); break;
/*     */       case 2:
/*  30 */         paramScene.setEventDispatcher(this.eventDispatcher); break;
/*     */       case 3:
/*  31 */         paramScene.setFill(this.fill); break;
/*     */       case 4:
/*  32 */         paramScene.setOnContextMenuRequested(this.onContextMenuRequested); break;
/*     */       case 5:
/*  33 */         paramScene.setOnDragDetected(this.onDragDetected); break;
/*     */       case 6:
/*  34 */         paramScene.setOnDragDone(this.onDragDone); break;
/*     */       case 7:
/*  35 */         paramScene.setOnDragDropped(this.onDragDropped); break;
/*     */       case 8:
/*  36 */         paramScene.setOnDragEntered(this.onDragEntered); break;
/*     */       case 9:
/*  37 */         paramScene.setOnDragExited(this.onDragExited); break;
/*     */       case 10:
/*  38 */         paramScene.setOnDragOver(this.onDragOver); break;
/*     */       case 11:
/*  39 */         paramScene.setOnInputMethodTextChanged(this.onInputMethodTextChanged); break;
/*     */       case 12:
/*  40 */         paramScene.setOnKeyPressed(this.onKeyPressed); break;
/*     */       case 13:
/*  41 */         paramScene.setOnKeyReleased(this.onKeyReleased); break;
/*     */       case 14:
/*  42 */         paramScene.setOnKeyTyped(this.onKeyTyped); break;
/*     */       case 15:
/*  43 */         paramScene.setOnMouseClicked(this.onMouseClicked); break;
/*     */       case 16:
/*  44 */         paramScene.setOnMouseDragEntered(this.onMouseDragEntered); break;
/*     */       case 17:
/*  45 */         paramScene.setOnMouseDragExited(this.onMouseDragExited); break;
/*     */       case 18:
/*  46 */         paramScene.setOnMouseDragged(this.onMouseDragged); break;
/*     */       case 19:
/*  47 */         paramScene.setOnMouseDragOver(this.onMouseDragOver); break;
/*     */       case 20:
/*  48 */         paramScene.setOnMouseDragReleased(this.onMouseDragReleased); break;
/*     */       case 21:
/*  49 */         paramScene.setOnMouseEntered(this.onMouseEntered); break;
/*     */       case 22:
/*  50 */         paramScene.setOnMouseExited(this.onMouseExited); break;
/*     */       case 23:
/*  51 */         paramScene.setOnMouseMoved(this.onMouseMoved); break;
/*     */       case 24:
/*  52 */         paramScene.setOnMousePressed(this.onMousePressed); break;
/*     */       case 25:
/*  53 */         paramScene.setOnMouseReleased(this.onMouseReleased); break;
/*     */       case 26:
/*  54 */         paramScene.setOnRotate(this.onRotate); break;
/*     */       case 27:
/*  55 */         paramScene.setOnRotationFinished(this.onRotationFinished); break;
/*     */       case 28:
/*  56 */         paramScene.setOnRotationStarted(this.onRotationStarted); break;
/*     */       case 29:
/*  57 */         paramScene.setOnScroll(this.onScroll); break;
/*     */       case 30:
/*  58 */         paramScene.setOnScrollFinished(this.onScrollFinished); break;
/*     */       case 31:
/*  59 */         paramScene.setOnScrollStarted(this.onScrollStarted); break;
/*     */       case 32:
/*  60 */         paramScene.setOnSwipeDown(this.onSwipeDown); break;
/*     */       case 33:
/*  61 */         paramScene.setOnSwipeLeft(this.onSwipeLeft); break;
/*     */       case 34:
/*  62 */         paramScene.setOnSwipeRight(this.onSwipeRight); break;
/*     */       case 35:
/*  63 */         paramScene.setOnSwipeUp(this.onSwipeUp); break;
/*     */       case 36:
/*  64 */         paramScene.setOnTouchMoved(this.onTouchMoved); break;
/*     */       case 37:
/*  65 */         paramScene.setOnTouchPressed(this.onTouchPressed); break;
/*     */       case 38:
/*  66 */         paramScene.setOnTouchReleased(this.onTouchReleased); break;
/*     */       case 39:
/*  67 */         paramScene.setOnTouchStationary(this.onTouchStationary); break;
/*     */       case 40:
/*  68 */         paramScene.setOnZoom(this.onZoom); break;
/*     */       case 41:
/*  69 */         paramScene.setOnZoomFinished(this.onZoomFinished); break;
/*     */       case 42:
/*  70 */         paramScene.setOnZoomStarted(this.onZoomStarted); break;
/*     */       case 43:
/*  71 */         paramScene.getStylesheets().addAll(this.stylesheets);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B camera(Camera paramCamera)
/*     */   {
/*  82 */     this.camera = paramCamera;
/*  83 */     __set(0);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public B cursor(Cursor paramCursor)
/*     */   {
/*  93 */     this.cursor = paramCursor;
/*  94 */     __set(1);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public B depthBuffer(boolean paramBoolean)
/*     */   {
/* 104 */     this.depthBuffer = paramBoolean;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public B eventDispatcher(EventDispatcher paramEventDispatcher)
/*     */   {
/* 114 */     this.eventDispatcher = paramEventDispatcher;
/* 115 */     __set(2);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public B fill(Paint paramPaint)
/*     */   {
/* 125 */     this.fill = paramPaint;
/* 126 */     __set(3);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public B height(double paramDouble)
/*     */   {
/* 136 */     this.height = paramDouble;
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public B onContextMenuRequested(EventHandler<? super ContextMenuEvent> paramEventHandler)
/*     */   {
/* 146 */     this.onContextMenuRequested = paramEventHandler;
/* 147 */     __set(4);
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDetected(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 157 */     this.onDragDetected = paramEventHandler;
/* 158 */     __set(5);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDone(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 168 */     this.onDragDone = paramEventHandler;
/* 169 */     __set(6);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragDropped(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 179 */     this.onDragDropped = paramEventHandler;
/* 180 */     __set(7);
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragEntered(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 190 */     this.onDragEntered = paramEventHandler;
/* 191 */     __set(8);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragExited(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 201 */     this.onDragExited = paramEventHandler;
/* 202 */     __set(9);
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   public B onDragOver(EventHandler<? super DragEvent> paramEventHandler)
/*     */   {
/* 212 */     this.onDragOver = paramEventHandler;
/* 213 */     __set(10);
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */   public B onInputMethodTextChanged(EventHandler<? super InputMethodEvent> paramEventHandler)
/*     */   {
/* 223 */     this.onInputMethodTextChanged = paramEventHandler;
/* 224 */     __set(11);
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyPressed(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 234 */     this.onKeyPressed = paramEventHandler;
/* 235 */     __set(12);
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyReleased(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 245 */     this.onKeyReleased = paramEventHandler;
/* 246 */     __set(13);
/* 247 */     return this;
/*     */   }
/*     */ 
/*     */   public B onKeyTyped(EventHandler<? super KeyEvent> paramEventHandler)
/*     */   {
/* 256 */     this.onKeyTyped = paramEventHandler;
/* 257 */     __set(14);
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseClicked(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 267 */     this.onMouseClicked = paramEventHandler;
/* 268 */     __set(15);
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragEntered(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 278 */     this.onMouseDragEntered = paramEventHandler;
/* 279 */     __set(16);
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragExited(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 289 */     this.onMouseDragExited = paramEventHandler;
/* 290 */     __set(17);
/* 291 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragged(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 300 */     this.onMouseDragged = paramEventHandler;
/* 301 */     __set(18);
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragOver(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 311 */     this.onMouseDragOver = paramEventHandler;
/* 312 */     __set(19);
/* 313 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseDragReleased(EventHandler<? super MouseDragEvent> paramEventHandler)
/*     */   {
/* 322 */     this.onMouseDragReleased = paramEventHandler;
/* 323 */     __set(20);
/* 324 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseEntered(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 333 */     this.onMouseEntered = paramEventHandler;
/* 334 */     __set(21);
/* 335 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseExited(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 344 */     this.onMouseExited = paramEventHandler;
/* 345 */     __set(22);
/* 346 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseMoved(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 355 */     this.onMouseMoved = paramEventHandler;
/* 356 */     __set(23);
/* 357 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMousePressed(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 366 */     this.onMousePressed = paramEventHandler;
/* 367 */     __set(24);
/* 368 */     return this;
/*     */   }
/*     */ 
/*     */   public B onMouseReleased(EventHandler<? super MouseEvent> paramEventHandler)
/*     */   {
/* 377 */     this.onMouseReleased = paramEventHandler;
/* 378 */     __set(25);
/* 379 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotate(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 388 */     this.onRotate = paramEventHandler;
/* 389 */     __set(26);
/* 390 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotationFinished(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 399 */     this.onRotationFinished = paramEventHandler;
/* 400 */     __set(27);
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */   public B onRotationStarted(EventHandler<? super RotateEvent> paramEventHandler)
/*     */   {
/* 410 */     this.onRotationStarted = paramEventHandler;
/* 411 */     __set(28);
/* 412 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScroll(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 421 */     this.onScroll = paramEventHandler;
/* 422 */     __set(29);
/* 423 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScrollFinished(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 432 */     this.onScrollFinished = paramEventHandler;
/* 433 */     __set(30);
/* 434 */     return this;
/*     */   }
/*     */ 
/*     */   public B onScrollStarted(EventHandler<? super ScrollEvent> paramEventHandler)
/*     */   {
/* 443 */     this.onScrollStarted = paramEventHandler;
/* 444 */     __set(31);
/* 445 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeDown(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 454 */     this.onSwipeDown = paramEventHandler;
/* 455 */     __set(32);
/* 456 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeLeft(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 465 */     this.onSwipeLeft = paramEventHandler;
/* 466 */     __set(33);
/* 467 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeRight(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 476 */     this.onSwipeRight = paramEventHandler;
/* 477 */     __set(34);
/* 478 */     return this;
/*     */   }
/*     */ 
/*     */   public B onSwipeUp(EventHandler<? super SwipeEvent> paramEventHandler)
/*     */   {
/* 487 */     this.onSwipeUp = paramEventHandler;
/* 488 */     __set(35);
/* 489 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchMoved(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 498 */     this.onTouchMoved = paramEventHandler;
/* 499 */     __set(36);
/* 500 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchPressed(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 509 */     this.onTouchPressed = paramEventHandler;
/* 510 */     __set(37);
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchReleased(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 520 */     this.onTouchReleased = paramEventHandler;
/* 521 */     __set(38);
/* 522 */     return this;
/*     */   }
/*     */ 
/*     */   public B onTouchStationary(EventHandler<? super TouchEvent> paramEventHandler)
/*     */   {
/* 531 */     this.onTouchStationary = paramEventHandler;
/* 532 */     __set(39);
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoom(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 542 */     this.onZoom = paramEventHandler;
/* 543 */     __set(40);
/* 544 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoomFinished(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 553 */     this.onZoomFinished = paramEventHandler;
/* 554 */     __set(41);
/* 555 */     return this;
/*     */   }
/*     */ 
/*     */   public B onZoomStarted(EventHandler<? super ZoomEvent> paramEventHandler)
/*     */   {
/* 564 */     this.onZoomStarted = paramEventHandler;
/* 565 */     __set(42);
/* 566 */     return this;
/*     */   }
/*     */ 
/*     */   public B root(Parent paramParent)
/*     */   {
/* 575 */     this.root = paramParent;
/* 576 */     return this;
/*     */   }
/*     */ 
/*     */   public B stylesheets(Collection<? extends String> paramCollection)
/*     */   {
/* 585 */     this.stylesheets = paramCollection;
/* 586 */     __set(43);
/* 587 */     return this;
/*     */   }
/*     */ 
/*     */   public B stylesheets(String[] paramArrayOfString)
/*     */   {
/* 594 */     return stylesheets(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public B width(double paramDouble)
/*     */   {
/* 603 */     this.width = paramDouble;
/* 604 */     return this;
/*     */   }
/*     */ 
/*     */   public Scene build()
/*     */   {
/* 611 */     Scene localScene = new Scene(this.root, this.width, this.height, this.depthBuffer);
/* 612 */     applyTo(localScene);
/* 613 */     return localScene;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.SceneBuilder
 * JD-Core Version:    0.6.2
 */