/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.GestureSupport;
/*     */ import com.sun.glass.ui.TouchInputSupport;
/*     */ import com.sun.glass.ui.View;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public class MacGestureSupport
/*     */ {
/*     */   private static final int GESTURE_ROTATE = 100;
/*     */   private static final int GESTURE_MAGNIFY = 101;
/*     */   private static final int GESTURE_SWIPE = 102;
/*     */   private static final int SCROLL_SRC_WHEEL = 50;
/*     */   private static final int SCROLL_SRC_GESTURE = 51;
/*     */   private static final int SCROLL_SRC_INERTIA = 52;
/*     */   private static final boolean isDirect = false;
/*  28 */   private static final GestureSupport gestures = new GestureSupport(false);
/*  29 */   private static final TouchInputSupport touches = new MacTouchInputSupport(gestures.createTouchCountListener(), false);
/*     */ 
/*     */   public static void notifyBeginTouchEvent(View view, int modifiers, int touchEventCount)
/*     */   {
/*  34 */     touches.notifyBeginTouchEvent(view, modifiers, false, touchEventCount);
/*     */   }
/*     */ 
/*     */   public static void notifyNextTouchEvent(View view, int state, long id, float x, float y)
/*     */   {
/*  47 */     int intX = (int)(10000.0F * x);
/*  48 */     int intY = 10000 - (int)(10000.0F * y);
/*  49 */     touches.notifyNextTouchEvent(view, state, id, intX, intY, intX, intY);
/*     */   }
/*     */ 
/*     */   public static void notifyEndTouchEvent(View view) {
/*  53 */     touches.notifyEndTouchEvent(view);
/*     */   }
/*     */ 
/*     */   public static void rotateGesturePerformed(View view, int modifiers, int x, int y, int xAbs, int yAbs, float rotation)
/*     */   {
/*  59 */     gestures.handleDeltaRotation(view, modifiers, false, false, x, y, xAbs, yAbs, -rotation);
/*     */   }
/*     */ 
/*     */   public static void scrollGesturePerformed(View view, int modifiers, int sender, int x, int y, int xAbs, int yAbs, float dx, float dy)
/*     */   {
/*  67 */     int touchCount = touches.getTouchCount();
/*  68 */     boolean isInertia = sender == 52;
/*  69 */     switch (sender)
/*     */     {
/*     */     case 50:
/*     */     case 52:
/*  82 */       GestureSupport.handleScrollingPerformed(view, modifiers, false, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy);
/*     */ 
/*  85 */       break;
/*     */     case 51:
/*  87 */       gestures.handleDeltaScrolling(view, modifiers, false, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void swipeGesturePerformed(View view, int modifiers, int dir, int x, int y, int xAbs, int yAbs)
/*     */   {
/*  99 */     GestureSupport.handleSwipePerformed(view, modifiers, false, false, touches.getTouchCount(), dir, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   public static void magnifyGesturePerformed(View view, int modifiers, int x, int y, int xAbs, int yAbs, float scale)
/*     */   {
/* 106 */     gestures.handleDeltaZooming(view, modifiers, false, false, x, y, xAbs, yAbs, scale, (0.0D / 0.0D));
/*     */   }
/*     */ 
/*     */   public static void gestureFinished(View view, int modifiers, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 112 */     if (gestures.isScrolling()) {
/* 113 */       gestures.handleScrollingEnd(view, modifiers, touches.getTouchCount(), false, false, x, y, xAbs, yAbs);
/*     */     }
/*     */ 
/* 117 */     if (gestures.isRotating()) {
/* 118 */       gestures.handleRotationEnd(view, modifiers, false, false, x, y, xAbs, yAbs);
/*     */     }
/*     */ 
/* 122 */     if (gestures.isZooming())
/* 123 */       gestures.handleZoomingEnd(view, modifiers, false, false, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   private static native void _initIDs();
/*     */ 
/*     */   static
/*     */   {
/* 132 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 135 */         Application.loadNativeLibrary();
/* 136 */         return null;
/*     */       }
/*     */     });
/* 139 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacGestureSupport
 * JD-Core Version:    0.6.2
 */