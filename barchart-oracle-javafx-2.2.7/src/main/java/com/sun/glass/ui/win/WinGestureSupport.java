/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.GestureSupport;
/*     */ import com.sun.glass.ui.TouchInputSupport;
/*     */ import com.sun.glass.ui.View;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public class WinGestureSupport
/*     */ {
/*  19 */   private static final GestureSupport gestures = new GestureSupport(true);
/*  20 */   private static final TouchInputSupport touches = new TouchInputSupport(gestures.createTouchCountListener(), true);
/*     */   private static int modifiers;
/*     */   private static boolean isDirect;
/*     */ 
/*     */   public static void notifyBeginTouchEvent(View view, int modifiers, boolean isDirect, int touchEventCount)
/*     */   {
/*  28 */     touches.notifyBeginTouchEvent(view, modifiers, isDirect, touchEventCount);
/*     */   }
/*     */ 
/*     */   public static void notifyNextTouchEvent(View view, int state, long id, int x, int y, int xAbs, int yAbs)
/*     */   {
/*  33 */     touches.notifyNextTouchEvent(view, state, id, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   public static void notifyEndTouchEvent(View view) {
/*  37 */     touches.notifyEndTouchEvent(view);
/*  38 */     gestureFinished(view, touches.getTouchCount(), false);
/*     */   }
/*     */ 
/*     */   private static void gestureFinished(View view, int touchCount, boolean isInertia)
/*     */   {
/*  43 */     if ((gestures.isScrolling()) && (touchCount == 0)) {
/*  44 */       gestures.handleScrollingEnd(view, modifiers, touchCount, isDirect, isInertia, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */     }
/*     */ 
/*  52 */     if ((gestures.isRotating()) && (touchCount < 2)) {
/*  53 */       gestures.handleRotationEnd(view, modifiers, isDirect, isInertia, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */     }
/*     */ 
/*  60 */     if ((gestures.isZooming()) && (touchCount < 2))
/*  61 */       gestures.handleZoomingEnd(view, modifiers, isDirect, isInertia, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */   }
/*     */ 
/*     */   public static void inertiaGestureFinished(View view)
/*     */   {
/*  70 */     gestureFinished(view, 0, true);
/*     */   }
/*     */ 
/*     */   public static void gesturePerformed(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, float dx, float dy, float totaldx, float totaldy, float totalscale, float totalexpansion, float totalrotation)
/*     */   {
/*  80 */     modifiers = modifiers;
/*  81 */     isDirect = isDirect;
/*     */ 
/*  83 */     int touchCount = touches.getTouchCount();
/*     */ 
/*  85 */     if (touchCount >= 2) {
/*  86 */       gestures.handleTotalZooming(view, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, totalscale, totalexpansion);
/*     */ 
/*  90 */       gestures.handleTotalRotation(view, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, Math.toDegrees(totalrotation));
/*     */     }
/*     */ 
/*  95 */     gestures.handleTotalScrolling(view, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, totaldx, totaldy);
/*     */   }
/*     */ 
/*     */   private static native void _initIDs();
/*     */ 
/*     */   static
/*     */   {
/* 104 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 107 */         Application.loadNativeLibrary();
/* 108 */         return null;
/*     */       }
/*     */     });
/* 111 */     _initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinGestureSupport
 * JD-Core Version:    0.6.2
 */