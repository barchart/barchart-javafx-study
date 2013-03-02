/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.ClipboardAssistance;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.View.EventHandler;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.tk.TKSceneListener;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ import javafx.scene.input.RotateEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ import javafx.scene.input.SwipeEvent;
/*     */ import javafx.scene.input.TouchPoint.State;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javafx.scene.input.ZoomEvent;
/*     */ 
/*     */ public class GlassViewEventHandler extends View.EventHandler
/*     */ {
/*  37 */   private static boolean verbose = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */   {
/*     */     public Boolean run() {
/*  40 */       return Boolean.valueOf(Boolean.getBoolean("quantum.verbose"));
/*     */     }
/*     */   })).booleanValue();
/*     */   private ViewScene scene;
/*     */   private int initialWidth;
/*     */   private int initialHeight;
/*     */   private final GlassSceneDnDEventHandler dndHandler;
/*  49 */   private GestureRecognizers gestures = new GestureRecognizers();
/*     */ 
/*  92 */   private final QuantumToolkit toolkit = (QuantumToolkit)QuantumToolkit.getToolkit();
/*  93 */   private final PaintCollector collector = PaintCollector.getInstance();
/*     */ 
/*  99 */   private final KeyEventNotification keyNotification = new KeyEventNotification(null);
/*     */ 
/* 167 */   private boolean dragPerformed = false;
/*     */ 
/* 170 */   private int mouseButtonPressedMask = 0;
/*     */ 
/* 172 */   private final MouseEventNotification mouseNotification = new MouseEventNotification(null);
/*     */   private ClipboardAssistance dropSourceAssistant;
/* 444 */   private final ViewEventNotification viewNotification = new ViewEventNotification(null);
/*     */ 
/*     */   public GlassViewEventHandler(ViewScene paramViewScene)
/*     */   {
/*  52 */     this.scene = paramViewScene;
/*  53 */     this.initialWidth = 0;
/*  54 */     this.initialHeight = 0;
/*     */ 
/*  56 */     this.dndHandler = new GlassSceneDnDEventHandler(paramViewScene, null, verbose);
/*     */ 
/*  58 */     if (PlatformUtil.isWindows())
/*  59 */       this.gestures.add(new SwipeGestureRecognizer(paramViewScene));
/*     */   }
/*     */ 
/*     */   private static boolean allowableFullScreenKeys(int paramInt)
/*     */   {
/*  68 */     switch (paramInt) {
/*     */     case 9:
/*     */     case 10:
/*     */     case 32:
/*     */     case 33:
/*     */     case 34:
/*     */     case 35:
/*     */     case 36:
/*     */     case 37:
/*     */     case 38:
/*     */     case 39:
/*     */     case 40:
/*  80 */       return true;
/*     */     case 11:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 16:
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/*     */     case 20:
/*     */     case 21:
/*     */     case 22:
/*     */     case 23:
/*     */     case 24:
/*     */     case 25:
/*     */     case 26:
/*     */     case 27:
/*     */     case 28:
/*     */     case 29:
/*     */     case 30:
/*  82 */     case 31: } return false;
/*     */   }
/*     */ 
/*     */   private boolean checkFullScreenKeyEvent(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3) {
/*  86 */     if (!this.scene.getWindowStage().isTrustedFullScreen()) {
/*  87 */       return allowableFullScreenKeys(paramInt2);
/*     */     }
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   private boolean toolkit()
/*     */   {
/*  96 */     return QuantumToolkit.getFxUserThread() != null;
/*     */   }
/*     */ 
/*     */   public void handleKeyEvent(View paramView, long paramLong, int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3)
/*     */   {
/* 156 */     this.keyNotification.view = paramView;
/* 157 */     this.keyNotification.time = paramLong;
/* 158 */     this.keyNotification.type = paramInt1;
/* 159 */     this.keyNotification.key = paramInt2;
/* 160 */     this.keyNotification.chars = paramArrayOfChar;
/* 161 */     this.keyNotification.modifiers = paramInt3;
/*     */ 
/* 163 */     AccessController.doPrivileged(this.keyNotification, this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleMouseEvent(View paramView, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 279 */     this.mouseNotification.view = paramView;
/* 280 */     this.mouseNotification.time = paramLong;
/* 281 */     this.mouseNotification.type = paramInt1;
/* 282 */     this.mouseNotification.button = paramInt2;
/* 283 */     this.mouseNotification.x = paramInt3;
/* 284 */     this.mouseNotification.y = paramInt4;
/* 285 */     this.mouseNotification.xAbs = paramInt5;
/* 286 */     this.mouseNotification.yAbs = paramInt6;
/* 287 */     this.mouseNotification.clickCount = paramInt7;
/* 288 */     this.mouseNotification.modifiers = paramInt8;
/* 289 */     this.mouseNotification.isPopupTrigger = paramBoolean1;
/* 290 */     this.mouseNotification.isSynthesized = paramBoolean2;
/*     */ 
/* 292 */     AccessController.doPrivileged(this.mouseNotification, this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleMenuEvent(View paramView, final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final boolean paramBoolean)
/*     */   {
/* 298 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 300 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 301 */           return null;
/*     */         }
/* 303 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 305 */           if (localWindowStage != null) {
/* 306 */             localWindowStage.setInEventHandler(true);
/*     */           }
/* 308 */           if (GlassViewEventHandler.this.scene.sceneListener != null)
/* 309 */             GlassViewEventHandler.this.scene.sceneListener.menuEvent(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
/*     */         }
/*     */         finally {
/* 312 */           if (localWindowStage != null) {
/* 313 */             localWindowStage.setInEventHandler(false);
/*     */           }
/*     */         }
/* 316 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleScrollEvent(View paramView, long paramLong, final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4, final double paramDouble1, double paramDouble2, final int paramInt5, int paramInt6, final int paramInt7, int paramInt8, final int paramInt9, final double paramDouble3, double paramDouble4)
/*     */   {
/* 328 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 330 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 331 */           return null;
/*     */         }
/*     */ 
/* 334 */         Object localObject1 = null;
/* 335 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 337 */           if (localWindowStage != null) {
/* 338 */             localWindowStage.setInEventHandler(true);
/*     */           }
/* 340 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 341 */             GlassViewEventHandler.this.scene.sceneListener.scrollEvent(ScrollEvent.SCROLL, paramDouble1, paramDouble3, 0.0D, 0.0D, paramInt7, paramInt9, 0, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, this.val$y, this.val$xAbs, this.val$yAbs, (this.val$modifiers & 0x1) != 0, (this.val$modifiers & 0x4) != 0, (this.val$modifiers & 0x8) != 0, (this.val$modifiers & 0x10) != 0, false, false);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 355 */           if (localWindowStage != null) {
/* 356 */             localWindowStage.setInEventHandler(false);
/*     */           }
/*     */         }
/* 359 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleInputMethodEvent(long paramLong, final String paramString, final int[] paramArrayOfInt1, final int[] paramArrayOfInt2, final byte[] paramArrayOfByte, final int paramInt1, final int paramInt2)
/*     */   {
/* 369 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 371 */         if ((GlassViewEventHandler.this.scene.sceneListener != null) && (GlassViewEventHandler.this.toolkit())) {
/* 372 */           GlassPrismInputMethodEvent localGlassPrismInputMethodEvent = new GlassPrismInputMethodEvent(GlassViewEventHandler.this.scene, paramString, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfByte, paramInt1, paramInt2);
/*     */ 
/* 375 */           GlassViewEventHandler.this.scene.sceneListener.inputMethodEvent(localGlassPrismInputMethodEvent);
/*     */         }
/* 377 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public double[] getInputMethodCandidatePos(int paramInt)
/*     */   {
/* 384 */     Point2D localPoint2D = this.scene.inputMethodRequests.getTextLocation(paramInt);
/* 385 */     double[] arrayOfDouble = new double[2];
/* 386 */     arrayOfDouble[0] = localPoint2D.getX();
/* 387 */     arrayOfDouble[1] = localPoint2D.getY();
/* 388 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */   public int handleDragEnter(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 395 */     TransferMode localTransferMode = this.dndHandler.handleDragEnter(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramClipboardAssistance);
/*     */ 
/* 399 */     return GlassSceneDnDEventHandler.TransferModeToAction(localTransferMode);
/*     */   }
/*     */ 
/*     */   public void handleDragLeave(View paramView, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 405 */     this.dndHandler.handleDragLeave(paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public int handleDragDrop(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 411 */     TransferMode localTransferMode = this.dndHandler.handleDragDrop(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramClipboardAssistance);
/*     */ 
/* 415 */     return GlassSceneDnDEventHandler.TransferModeToAction(localTransferMode);
/*     */   }
/*     */ 
/*     */   public int handleDragOver(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 422 */     TransferMode localTransferMode = this.dndHandler.handleDragOver(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramClipboardAssistance);
/*     */ 
/* 426 */     return GlassSceneDnDEventHandler.TransferModeToAction(localTransferMode);
/*     */   }
/*     */ 
/*     */   public void handleDragStart(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, ClipboardAssistance paramClipboardAssistance)
/*     */   {
/* 433 */     this.dropSourceAssistant = paramClipboardAssistance;
/* 434 */     this.dndHandler.handleDragStart(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramClipboardAssistance);
/*     */   }
/*     */ 
/*     */   public void handleDragEnd(View paramView, int paramInt) {
/* 438 */     this.dndHandler.handleDragEnd(paramInt, this.dropSourceAssistant);
/*     */   }
/*     */ 
/*     */   public void handleViewEvent(View paramView, long paramLong, int paramInt)
/*     */   {
/* 500 */     this.viewNotification.view = paramView;
/* 501 */     this.viewNotification.time = paramLong;
/* 502 */     this.viewNotification.type = paramInt;
/*     */ 
/* 504 */     AccessController.doPrivileged(this.viewNotification, this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleScrollGestureEvent(View paramView, long paramLong, final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2, final int paramInt3, int paramInt4, final int paramInt5, int paramInt6, final int paramInt7, final double paramDouble1, double paramDouble2, final double paramDouble3, double paramDouble4)
/*     */   {
/* 513 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 515 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 516 */           return null;
/*     */         }
/*     */ 
/* 519 */         Object localObject1 = null;
/* 520 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 522 */           localWindowStage.setInEventHandler(true);
/* 523 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 524 */             EventType localEventType = ScrollEvent.SCROLL;
/* 525 */             switch (paramInt1) {
/*     */             case 1:
/* 527 */               localEventType = ScrollEvent.SCROLL_STARTED;
/* 528 */               break;
/*     */             case 2:
/* 530 */               localEventType = ScrollEvent.SCROLL;
/* 531 */               break;
/*     */             case 3:
/* 533 */               localEventType = ScrollEvent.SCROLL_FINISHED;
/* 534 */               break;
/*     */             default:
/* 536 */               throw new RuntimeException("Unknown scroll event type: " + paramInt1);
/*     */             }
/* 538 */             GlassViewEventHandler.this.scene.sceneListener.scrollEvent(localEventType, paramDouble1, paramDouble3, paramInt3, paramInt5, 1.0D, 1.0D, paramInt7, 0, 0, 0, 0, paramInt2 == 2147483647 ? (0.0D / 0.0D) : paramInt2, paramBoolean1 == 2147483647 ? (0.0D / 0.0D) : paramBoolean1, paramBoolean2 == 2147483647 ? (0.0D / 0.0D) : paramBoolean2, this.val$yAbs == 2147483647 ? (0.0D / 0.0D) : this.val$yAbs, (this.val$modifiers & 0x1) != 0, (this.val$modifiers & 0x4) != 0, (this.val$modifiers & 0x8) != 0, (this.val$modifiers & 0x10) != 0, this.val$isDirect, this.val$isInertia);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 553 */           localWindowStage.setInEventHandler(false);
/*     */         }
/* 555 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleZoomGestureEvent(View paramView, long paramLong, final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2, final int paramInt3, int paramInt4, final int paramInt5, final int paramInt6, final double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 568 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 570 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 571 */           return null;
/*     */         }
/*     */ 
/* 574 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 576 */           localWindowStage.setInEventHandler(true);
/* 577 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 578 */             EventType localEventType = ZoomEvent.ZOOM;
/* 579 */             switch (paramInt1) {
/*     */             case 1:
/* 581 */               localEventType = ZoomEvent.ZOOM_STARTED;
/* 582 */               break;
/*     */             case 2:
/* 584 */               localEventType = ZoomEvent.ZOOM;
/* 585 */               break;
/*     */             case 3:
/* 587 */               localEventType = ZoomEvent.ZOOM_FINISHED;
/* 588 */               break;
/*     */             default:
/* 590 */               throw new RuntimeException("Unknown scroll event type: " + paramInt1);
/*     */             }
/* 592 */             GlassViewEventHandler.this.scene.sceneListener.zoomEvent(localEventType, paramDouble1, paramInt3, paramInt5 == 2147483647 ? (0.0D / 0.0D) : paramInt5, paramInt6 == 2147483647 ? (0.0D / 0.0D) : paramInt6, paramInt2 == 2147483647 ? (0.0D / 0.0D) : paramInt2, paramBoolean1 == 2147483647 ? (0.0D / 0.0D) : paramBoolean1, (paramBoolean2 & 0x1) != 0, (paramBoolean2 & 0x4) != 0, (paramBoolean2 & 0x8) != 0, (paramBoolean2 & 0x10) != 0, this.val$isDirect, this.val$isInertia);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 604 */           localWindowStage.setInEventHandler(false);
/*     */         }
/* 606 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleRotateGestureEvent(View paramView, long paramLong, final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2, final int paramInt3, int paramInt4, final int paramInt5, final int paramInt6, final double paramDouble1, double paramDouble2)
/*     */   {
/* 618 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 620 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 621 */           return null;
/*     */         }
/*     */ 
/* 624 */         Object localObject1 = null;
/* 625 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 627 */           localWindowStage.setInEventHandler(true);
/* 628 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 629 */             EventType localEventType = RotateEvent.ROTATE;
/* 630 */             switch (paramInt1) {
/*     */             case 1:
/* 632 */               localEventType = RotateEvent.ROTATION_STARTED;
/* 633 */               break;
/*     */             case 2:
/* 635 */               localEventType = RotateEvent.ROTATE;
/* 636 */               break;
/*     */             case 3:
/* 638 */               localEventType = RotateEvent.ROTATION_FINISHED;
/* 639 */               break;
/*     */             default:
/* 641 */               throw new RuntimeException("Unknown scroll event type: " + paramInt1);
/*     */             }
/* 643 */             GlassViewEventHandler.this.scene.sceneListener.rotateEvent(localEventType, paramDouble1, paramInt3, paramInt5 == 2147483647 ? (0.0D / 0.0D) : paramInt5, paramInt6 == 2147483647 ? (0.0D / 0.0D) : paramInt6, paramInt2 == 2147483647 ? (0.0D / 0.0D) : paramInt2, paramBoolean1 == 2147483647 ? (0.0D / 0.0D) : paramBoolean1, (paramBoolean2 & 0x1) != 0, (paramBoolean2 & 0x4) != 0, (paramBoolean2 & 0x8) != 0, (paramBoolean2 & 0x10) != 0, this.val$isDirect, this.val$isInertia);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 655 */           localWindowStage.setInEventHandler(false);
/*     */         }
/* 657 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleSwipeGestureEvent(View paramView, long paramLong, int paramInt1, final int paramInt2, final boolean paramBoolean1, boolean paramBoolean2, final int paramInt3, final int paramInt4, final int paramInt5, final int paramInt6, final int paramInt7, final int paramInt8)
/*     */   {
/* 668 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 670 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 671 */           return null;
/*     */         }
/*     */ 
/* 674 */         Object localObject1 = null;
/* 675 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 677 */           localWindowStage.setInEventHandler(true);
/* 678 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 679 */             EventType localEventType = null;
/* 680 */             switch (paramInt4) {
/*     */             case 1:
/* 682 */               localEventType = SwipeEvent.SWIPE_UP;
/* 683 */               break;
/*     */             case 2:
/* 685 */               localEventType = SwipeEvent.SWIPE_DOWN;
/* 686 */               break;
/*     */             case 3:
/* 688 */               localEventType = SwipeEvent.SWIPE_LEFT;
/* 689 */               break;
/*     */             case 4:
/* 691 */               localEventType = SwipeEvent.SWIPE_RIGHT;
/* 692 */               break;
/*     */             default:
/* 694 */               throw new RuntimeException("Unknown swipe event direction: " + paramInt4);
/*     */             }
/*     */ 
/* 697 */             GlassViewEventHandler.this.scene.sceneListener.swipeEvent(localEventType, paramInt3, paramInt5 == 2147483647 ? (0.0D / 0.0D) : paramInt5, paramInt6 == 2147483647 ? (0.0D / 0.0D) : paramInt6, paramInt7 == 2147483647 ? (0.0D / 0.0D) : paramInt7, paramInt8 == 2147483647 ? (0.0D / 0.0D) : paramInt8, (paramInt2 & 0x1) != 0, (paramInt2 & 0x4) != 0, (paramInt2 & 0x8) != 0, (paramInt2 & 0x10) != 0, paramBoolean1);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 709 */           localWindowStage.setInEventHandler(false);
/*     */         }
/* 711 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleBeginTouchEvent(View paramView, final long paramLong, final int paramInt1, final boolean paramBoolean, int paramInt2)
/*     */   {
/* 720 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 722 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 723 */           return null;
/*     */         }
/*     */ 
/* 726 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 728 */           localWindowStage.setInEventHandler(true);
/* 729 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 730 */             GlassViewEventHandler.this.scene.sceneListener.touchEventBegin(paramLong, paramBoolean, paramInt1, (this.val$modifiers & 0x1) != 0, (this.val$modifiers & 0x4) != 0, (this.val$modifiers & 0x8) != 0, (this.val$modifiers & 0x10) != 0);
/*     */           }
/*     */ 
/*     */         }
/*     */         finally
/*     */         {
/* 738 */           localWindowStage.setInEventHandler(false);
/*     */         }
/*     */ 
/* 741 */         GlassViewEventHandler.this.gestures.notifyBeginTouchEvent(paramLong, this.val$modifiers, paramInt1, paramBoolean);
/* 742 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleNextTouchEvent(View paramView, final long paramLong1, final int paramInt1, final long paramLong2, int paramInt2, final int paramInt3, final int paramInt4, final int paramInt5)
/*     */   {
/* 751 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 753 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 754 */           return null;
/*     */         }
/*     */ 
/* 757 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 759 */           localWindowStage.setInEventHandler(true);
/* 760 */           if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 761 */             TouchPoint.State localState = null;
/* 762 */             switch (paramInt1) {
/*     */             case 811:
/* 764 */               localState = TouchPoint.State.PRESSED;
/* 765 */               break;
/*     */             case 812:
/* 767 */               localState = TouchPoint.State.MOVED;
/* 768 */               break;
/*     */             case 814:
/* 770 */               localState = TouchPoint.State.STATIONARY;
/* 771 */               break;
/*     */             case 813:
/* 773 */               localState = TouchPoint.State.RELEASED;
/* 774 */               break;
/*     */             default:
/* 776 */               throw new RuntimeException("Unknown touch state: " + localState);
/*     */             }
/*     */ 
/* 779 */             GlassViewEventHandler.this.scene.sceneListener.touchEventNext(localState, paramLong2, paramInt3, paramInt4, paramInt5, paramLong1);
/*     */           }
/*     */         }
/*     */         finally {
/* 783 */           localWindowStage.setInEventHandler(false);
/*     */         }
/*     */ 
/* 786 */         GlassViewEventHandler.this.gestures.notifyNextTouchEvent(this.val$time, paramInt1, paramLong2, paramInt3, paramInt4, paramInt5, paramLong1);
/* 787 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void handleEndTouchEvent(View paramView, final long paramLong)
/*     */   {
/* 793 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Void run() {
/* 795 */         if (!GlassViewEventHandler.this.toolkit()) {
/* 796 */           return null;
/*     */         }
/*     */ 
/* 799 */         WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */         try {
/* 801 */           localWindowStage.setInEventHandler(true);
/* 802 */           if (GlassViewEventHandler.this.scene.sceneListener != null)
/* 803 */             GlassViewEventHandler.this.scene.sceneListener.touchEventEnd();
/*     */         }
/*     */         catch (RuntimeException localRuntimeException) {
/* 806 */           if (GlassViewEventHandler.verbose) {
/* 807 */             localRuntimeException.printStackTrace();
/*     */           }
/* 809 */           throw localRuntimeException;
/*     */         } catch (Throwable localThrowable) {
/* 811 */           if (GlassViewEventHandler.verbose) {
/* 812 */             localThrowable.printStackTrace();
/*     */           }
/* 814 */           throw new RuntimeException(localThrowable);
/*     */         }
/*     */         finally {
/* 817 */           localWindowStage.setInEventHandler(false);
/*     */         }
/*     */ 
/* 820 */         GlassViewEventHandler.this.gestures.notifyEndTouchEvent(paramLong);
/* 821 */         return null;
/*     */       }
/*     */     }
/*     */     , this.scene.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   private class ViewEventNotification
/*     */     implements PrivilegedAction<Void>
/*     */   {
/*     */     View view;
/*     */     long time;
/*     */     int type;
/*     */ 
/*     */     private ViewEventNotification()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Void run()
/*     */     {
/* 452 */       if (GlassViewEventHandler.verbose) {
/* 453 */         System.err.println("handleViewEvent(" + System.nanoTime() + "): " + GlassEventUtils.getViewEventString(this.type) + PaintCollector.sceneSize(GlassViewEventHandler.this.scene) + " closed: " + this.view.isClosed());
/*     */       }
/*     */ 
/* 457 */       if ((GlassViewEventHandler.this.scene.sceneListener == null) || (!GlassViewEventHandler.this.toolkit())) {
/* 458 */         return null;
/*     */       }
/* 460 */       switch (this.type) {
/*     */       case 431:
/* 462 */         Window localWindow = this.view.getWindow();
/* 463 */         if ((localWindow == null) || (localWindow.getMinimumWidth() != this.view.getWidth()) || (localWindow.isVisible()))
/*     */         {
/* 468 */           GlassViewEventHandler.this.scene.entireSceneNeedsRepaint();
/* 469 */           if (PlatformUtil.isMac())
/* 470 */             GlassViewEventHandler.this.collector.liveRepaintRenderJob(GlassViewEventHandler.this.scene);  } break;
/*     */       case 432:
/* 474 */         GlassViewEventHandler.this.scene.sceneListener.changedSize(this.view.getWidth(), this.view.getHeight());
/* 475 */         GlassViewEventHandler.this.scene.entireSceneNeedsRepaint();
/* 476 */         break;
/*     */       case 433:
/* 478 */         GlassViewEventHandler.this.scene.sceneListener.changedLocation(this.view.getX(), this.view.getY());
/* 479 */         break;
/*     */       case 441:
/*     */       case 442:
/* 482 */         if (GlassViewEventHandler.this.scene.getWindowStage() != null)
/* 483 */           GlassViewEventHandler.this.scene.getWindowStage().fullscreenChanged(this.type == 441); break;
/*     */       case 411:
/*     */       case 412:
/*     */       case 421:
/*     */       case 422:
/* 491 */         break;
/*     */       case 413:
/*     */       case 414:
/*     */       case 415:
/*     */       case 416:
/*     */       case 417:
/*     */       case 418:
/*     */       case 419:
/*     */       case 420:
/*     */       case 423:
/*     */       case 424:
/*     */       case 425:
/*     */       case 426:
/*     */       case 427:
/*     */       case 428:
/*     */       case 429:
/*     */       case 430:
/*     */       case 434:
/*     */       case 435:
/*     */       case 436:
/*     */       case 437:
/*     */       case 438:
/*     */       case 439:
/*     */       case 440:
/*     */       default:
/* 493 */         throw new RuntimeException("handleViewEvent: unhandled type: " + this.type);
/*     */       }
/* 495 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class MouseEventNotification
/*     */     implements PrivilegedAction<Void>
/*     */   {
/*     */     View view;
/*     */     long time;
/*     */     int type;
/*     */     int button;
/*     */     int x;
/*     */     int y;
/*     */     int xAbs;
/*     */     int yAbs;
/*     */     int clickCount;
/*     */     int modifiers;
/*     */     boolean isPopupTrigger;
/*     */     boolean isSynthesized;
/*     */ 
/*     */     private MouseEventNotification()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Void run()
/*     */     {
/* 186 */       if (!GlassViewEventHandler.this.toolkit()) {
/* 187 */         return null;
/*     */       }
/*     */ 
/* 190 */       boolean bool = GlassViewEventHandler.this.dragPerformed;
/* 191 */       GlassPrismMouseEvent localGlassPrismMouseEvent = null;
/*     */       int i;
/* 194 */       switch (this.button) {
/*     */       case 212:
/* 196 */         i = 32;
/* 197 */         break;
/*     */       case 214:
/* 199 */         i = 128;
/* 200 */         break;
/*     */       case 213:
/* 202 */         i = 64;
/* 203 */         break;
/*     */       default:
/* 205 */         i = 0;
/*     */       }
/*     */ 
/* 209 */       switch (this.type) {
/*     */       case 224:
/* 211 */         if (this.button != 211)
/*     */         {
/* 213 */           return null;
/*     */         }
/*     */         break;
/*     */       case 222:
/* 217 */         if ((GlassViewEventHandler.this.mouseButtonPressedMask & i) == 0)
/*     */         {
/* 219 */           return null;
/*     */         }
/* 221 */         GlassViewEventHandler.access$772(GlassViewEventHandler.this, i ^ 0xFFFFFFFF);
/* 222 */         break;
/*     */       case 223:
/* 225 */         GlassViewEventHandler.this.dragPerformed = true;
/* 226 */         break;
/*     */       case 221:
/* 229 */         GlassViewEventHandler.this.dragPerformed = false;
/* 230 */         GlassViewEventHandler.access$776(GlassViewEventHandler.this, i);
/* 231 */         break;
/*     */       case 225:
/*     */       case 226:
/* 234 */         break;
/*     */       default:
/* 236 */         if (GlassViewEventHandler.verbose) {
/* 237 */           System.out.println("handleMouseEvent: unhandled type: " + this.type);
/*     */         }
/*     */         break;
/*     */       }
/* 241 */       WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */       try {
/* 243 */         if (localWindowStage != null) {
/* 244 */           localWindowStage.setInEventHandler(true);
/*     */         }
/* 246 */         if (GlassViewEventHandler.this.scene.sceneListener != null) {
/* 247 */           localGlassPrismMouseEvent = new GlassPrismMouseEvent(GlassViewEventHandler.this.scene, this.type, this.button, this.x, this.y, this.xAbs, this.yAbs, this.clickCount, this.modifiers, this.isPopupTrigger, this.isSynthesized, 0.0D);
/*     */ 
/* 250 */           GlassViewEventHandler.this.scene.sceneListener.mouseEvent(localGlassPrismMouseEvent);
/*     */         }
/*     */ 
/* 253 */         if ((this.type == 222) && (!bool))
/*     */         {
/* 256 */           if ((GlassViewEventHandler.this.scene.sceneListener != null) && (GlassViewEventHandler.this.toolkit())) {
/* 257 */             localGlassPrismMouseEvent = new GlassPrismMouseEvent(GlassViewEventHandler.this.scene, 227, this.button, this.x, this.y, this.xAbs, this.yAbs, this.clickCount, this.modifiers, this.isPopupTrigger, this.isSynthesized, 0.0D);
/*     */ 
/* 261 */             GlassViewEventHandler.this.scene.sceneListener.mouseEvent(localGlassPrismMouseEvent);
/*     */           }
/*     */         }
/*     */       } finally {
/* 265 */         if (localWindowStage != null) {
/* 266 */           localWindowStage.setInEventHandler(false);
/*     */         }
/*     */       }
/* 269 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class KeyEventNotification
/*     */     implements PrivilegedAction<Void>
/*     */   {
/*     */     View view;
/*     */     long time;
/*     */     int type;
/*     */     int key;
/*     */     char[] chars;
/*     */     int modifiers;
/*     */ 
/*     */     private KeyEventNotification()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Void run()
/*     */     {
/* 110 */       if (!GlassViewEventHandler.this.toolkit()) {
/* 111 */         return null;
/*     */       }
/*     */ 
/* 114 */       GlassPrismKeyEvent localGlassPrismKeyEvent = null;
/* 115 */       WindowStage localWindowStage = GlassViewEventHandler.this.scene.getWindowStage();
/*     */       try {
/* 117 */         if (localWindowStage != null) {
/* 118 */           localWindowStage.setInEventHandler(true);
/*     */         }
/* 120 */         switch (this.type) {
/*     */         case 111:
/* 122 */           if ((this.key == 27) && (this.view.isInFullscreen()) && (localWindowStage != null))
/*     */           {
/* 124 */             localWindowStage.exitFullScreen();
/*     */           }
/*     */ 
/*     */         case 112:
/*     */         case 113:
/* 129 */           if ((this.view.isInFullscreen()) && 
/* 130 */             (!GlassViewEventHandler.this.checkFullScreenKeyEvent(this.type, this.key, this.chars, this.modifiers)))
/*     */           {
/*     */             break label227;
/*     */           }
/* 134 */           if (GlassViewEventHandler.this.scene.sceneListener == null) break label227;
/* 135 */           localGlassPrismKeyEvent = new GlassPrismKeyEvent(GlassViewEventHandler.this.scene, this.type, this.key, this.chars, this.modifiers);
/* 136 */           GlassViewEventHandler.this.scene.sceneListener.keyEvent(localGlassPrismKeyEvent); break;
/*     */         }
/*     */ 
/* 140 */         if (GlassViewEventHandler.verbose)
/* 141 */           System.out.println("handleKeyEvent: unhandled type: " + this.type);
/*     */       }
/*     */       finally
/*     */       {
/* 145 */         label227: if (localWindowStage != null) {
/* 146 */           localWindowStage.setInEventHandler(false);
/*     */         }
/*     */       }
/* 149 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassViewEventHandler
 * JD-Core Version:    0.6.2
 */