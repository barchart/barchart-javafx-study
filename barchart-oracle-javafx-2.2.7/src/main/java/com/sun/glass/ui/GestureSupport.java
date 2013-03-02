/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ public class GestureSupport
/*     */ {
/*     */   private static final double THRESHOLD_SCROLL = 1.0D;
/*     */   private static final double THRESHOLD_SCALE = 0.01D;
/*     */   private static final double THRESHOLD_EXPANSION = 0.01D;
/*  56 */   private static final double THRESHOLD_ROTATE = Math.toDegrees(0.0174532925199433D);
/*     */ 
/*  58 */   private final GestureState scrolling = new GestureState(null);
/*  59 */   private final GestureState rotating = new GestureState(null);
/*  60 */   private final GestureState zooming = new GestureState(null);
/*  61 */   private final GestureState swiping = new GestureState(null);
/*     */ 
/*  63 */   private double totalScrollX = (0.0D / 0.0D);
/*  64 */   private double totalScrollY = (0.0D / 0.0D);
/*  65 */   private double totalScale = 1.0D;
/*  66 */   private double totalExpansion = (0.0D / 0.0D);
/*  67 */   private double totalRotation = 0.0D;
/*     */   private boolean zoomWithExpansion;
/*     */ 
/*     */   private static double multiplicativeDelta(double from, double to)
/*     */   {
/*  72 */     if (from == 0.0D) {
/*  73 */       return (0.0D / 0.0D);
/*     */     }
/*  75 */     return to / from;
/*     */   }
/*     */ 
/*     */   private int setScrolling(boolean isInertia) {
/*  79 */     return this.scrolling.updateProgress(isInertia);
/*     */   }
/*     */ 
/*     */   private int setRotating(boolean isInertia) {
/*  83 */     return this.rotating.updateProgress(isInertia);
/*     */   }
/*     */ 
/*     */   private int setZooming(boolean isInertia) {
/*  87 */     return this.zooming.updateProgress(isInertia);
/*     */   }
/*     */ 
/*     */   private int setSwiping(boolean isInertia) {
/*  91 */     return this.swiping.updateProgress(isInertia);
/*     */   }
/*     */ 
/*     */   public GestureSupport(boolean zoomWithExpansion) {
/*  95 */     this.zoomWithExpansion = zoomWithExpansion;
/*     */   }
/*     */ 
/*     */   public boolean isScrolling() {
/*  99 */     return !this.scrolling.isIdle();
/*     */   }
/*     */ 
/*     */   public boolean isRotating() {
/* 103 */     return !this.rotating.isIdle();
/*     */   }
/*     */ 
/*     */   public boolean isZooming() {
/* 107 */     return !this.zooming.isIdle();
/*     */   }
/*     */ 
/*     */   public boolean isSwiping() {
/* 111 */     return !this.swiping.isIdle();
/*     */   }
/*     */ 
/*     */   public void handleScrollingEnd(View view, int modifiers, int touchCount, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 117 */     this.scrolling.setIdle();
/* 118 */     if (isInertia) {
/* 119 */       return;
/*     */     }
/* 121 */     view.notifyScrollGestureEvent(3, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, 0.0D, 0.0D, this.totalScrollX, this.totalScrollY);
/*     */   }
/*     */ 
/*     */   public void handleRotationEnd(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 130 */     this.rotating.setIdle();
/* 131 */     if (isInertia) {
/* 132 */       return;
/*     */     }
/* 134 */     view.notifyRotateGestureEvent(3, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, 0.0D, this.totalRotation);
/*     */   }
/*     */ 
/*     */   public void handleZoomingEnd(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 142 */     this.zooming.setIdle();
/* 143 */     if (isInertia) {
/* 144 */       return;
/*     */     }
/* 146 */     view.notifyZoomGestureEvent(3, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, (0.0D / 0.0D), 0.0D, this.totalScale, this.totalExpansion);
/*     */   }
/*     */ 
/*     */   public void handleSwipeEnd(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 155 */     this.swiping.setIdle();
/* 156 */     if (isInertia) {
/* 157 */       return;
/*     */     }
/* 159 */     view.notifySwipeGestureEvent(3, modifiers, isDirect, isInertia, 2147483647, 2147483647, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   public void handleTotalZooming(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double scale, double expansion)
/*     */   {
/* 168 */     double baseScale = this.totalScale;
/* 169 */     double baseExpansion = this.totalExpansion;
/* 170 */     if (this.zooming.doesGestureStart(isInertia)) {
/* 171 */       baseScale = 1.0D;
/* 172 */       baseExpansion = 0.0D;
/*     */     }
/*     */ 
/* 175 */     if ((Math.abs(scale - baseScale) < 0.01D) && ((!this.zoomWithExpansion) || (Math.abs(expansion - baseExpansion) < 0.01D)))
/*     */     {
/* 178 */       return;
/*     */     }
/*     */ 
/* 181 */     double deltaExpansion = (0.0D / 0.0D);
/* 182 */     if (this.zoomWithExpansion)
/* 183 */       deltaExpansion = expansion - baseExpansion;
/*     */     else {
/* 185 */       expansion = (0.0D / 0.0D);
/*     */     }
/*     */ 
/* 188 */     this.totalScale = scale;
/* 189 */     this.totalExpansion = expansion;
/* 190 */     int eventID = setZooming(isInertia);
/*     */ 
/* 192 */     view.notifyZoomGestureEvent(eventID, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, multiplicativeDelta(baseScale, this.totalScale), deltaExpansion, scale, expansion);
/*     */   }
/*     */ 
/*     */   public void handleTotalRotation(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double rotation)
/*     */   {
/* 202 */     double baseRotation = this.totalRotation;
/* 203 */     if (this.rotating.doesGestureStart(isInertia)) {
/* 204 */       baseRotation = 0.0D;
/*     */     }
/*     */ 
/* 207 */     if (Math.abs(rotation - baseRotation) < THRESHOLD_ROTATE) {
/* 208 */       return;
/*     */     }
/*     */ 
/* 211 */     this.totalRotation = rotation;
/* 212 */     int eventID = setRotating(isInertia);
/*     */ 
/* 214 */     view.notifyRotateGestureEvent(eventID, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, rotation - baseRotation, rotation);
/*     */   }
/*     */ 
/*     */   public void handleTotalScrolling(View view, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy)
/*     */   {
/* 223 */     double baseScrollX = this.totalScrollX;
/* 224 */     double baseScrollY = this.totalScrollY;
/* 225 */     if (this.scrolling.doesGestureStart(isInertia)) {
/* 226 */       baseScrollX = 0.0D;
/* 227 */       baseScrollY = 0.0D;
/*     */     }
/*     */ 
/* 230 */     if ((Math.abs(dx - this.totalScrollX) < 1.0D) && (Math.abs(dy - this.totalScrollY) < 1.0D))
/*     */     {
/* 232 */       return;
/*     */     }
/*     */ 
/* 235 */     this.totalScrollX = dx;
/* 236 */     this.totalScrollY = dy;
/* 237 */     int eventID = setScrolling(isInertia);
/*     */ 
/* 239 */     view.notifyScrollGestureEvent(eventID, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, dx - baseScrollX, dy - baseScrollY, dx, dy);
/*     */   }
/*     */ 
/*     */   public void handleDeltaZooming(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double scale, double expansion)
/*     */   {
/* 249 */     double baseScale = this.totalScale;
/* 250 */     double baseExpansion = this.totalExpansion;
/* 251 */     if (this.zooming.doesGestureStart(isInertia)) {
/* 252 */       baseScale = 1.0D;
/* 253 */       baseExpansion = 0.0D;
/*     */     }
/*     */ 
/* 256 */     this.totalScale = (baseScale + scale);
/* 257 */     if (this.zoomWithExpansion)
/* 258 */       this.totalExpansion = (baseExpansion + expansion);
/*     */     else {
/* 260 */       this.totalExpansion = (0.0D / 0.0D);
/*     */     }
/*     */ 
/* 263 */     int eventID = setZooming(isInertia);
/*     */ 
/* 265 */     view.notifyZoomGestureEvent(eventID, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, multiplicativeDelta(baseScale, this.totalScale), expansion, this.totalScale, this.totalExpansion);
/*     */   }
/*     */ 
/*     */   public void handleDeltaRotation(View view, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double rotation)
/*     */   {
/* 275 */     double baseRotation = this.totalRotation;
/* 276 */     if (this.rotating.doesGestureStart(isInertia)) {
/* 277 */       baseRotation = 0.0D;
/*     */     }
/*     */ 
/* 280 */     this.totalRotation = (baseRotation + rotation);
/* 281 */     int eventID = setRotating(isInertia);
/*     */ 
/* 283 */     view.notifyRotateGestureEvent(eventID, modifiers, isDirect, isInertia, x, y, xAbs, yAbs, rotation, this.totalRotation);
/*     */   }
/*     */ 
/*     */   public void handleDeltaScrolling(View view, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy)
/*     */   {
/* 291 */     double baseScrollX = this.totalScrollX;
/* 292 */     double baseScrollY = this.totalScrollY;
/* 293 */     if (this.scrolling.doesGestureStart(isInertia)) {
/* 294 */       baseScrollX = 0.0D;
/* 295 */       baseScrollY = 0.0D;
/*     */     }
/*     */ 
/* 298 */     this.totalScrollX = (baseScrollX + dx);
/* 299 */     this.totalScrollY = (baseScrollY + dy);
/* 300 */     int eventID = setScrolling(isInertia);
/*     */ 
/* 302 */     view.notifyScrollGestureEvent(eventID, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy, this.totalScrollX, this.totalScrollY);
/*     */   }
/*     */ 
/*     */   public void handleSwipe(View view, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int dir, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 310 */     int eventID = setSwiping(isInertia);
/* 311 */     view.notifySwipeGestureEvent(eventID, modifiers, isDirect, isInertia, touchCount, dir, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   public static void handleSwipePerformed(View view, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int dir, int x, int y, int xAbs, int yAbs)
/*     */   {
/* 319 */     view.notifySwipeGestureEvent(2, modifiers, isDirect, isInertia, touchCount, dir, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   public static void handleScrollingPerformed(View view, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy)
/*     */   {
/* 330 */     view.notifyScrollGestureEvent(2, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy, dx, dy);
/*     */   }
/*     */ 
/*     */   public TouchInputSupport.TouchCountListener createTouchCountListener()
/*     */   {
/* 336 */     return new TouchInputSupport.TouchCountListener()
/*     */     {
/*     */       public void touchCountChanged(TouchInputSupport sender, View view, int modifiers, boolean isDirect)
/*     */       {
/* 340 */         boolean isInertia = false;
/*     */ 
/* 342 */         if (GestureSupport.this.isScrolling()) {
/* 343 */           GestureSupport.this.handleScrollingEnd(view, modifiers, sender.getTouchCount(), isDirect, false, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */         }
/*     */ 
/* 351 */         if (GestureSupport.this.isRotating()) {
/* 352 */           GestureSupport.this.handleRotationEnd(view, modifiers, isDirect, false, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */         }
/*     */ 
/* 359 */         if (GestureSupport.this.isZooming())
/* 360 */           GestureSupport.this.handleZoomingEnd(view, modifiers, isDirect, false, 2147483647, 2147483647, 2147483647, 2147483647);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static class GestureState
/*     */   {
/*  20 */     private StateId id = StateId.Idle;
/*     */ 
/*     */     void setIdle() {
/*  23 */       this.id = StateId.Idle;
/*     */     }
/*     */ 
/*     */     boolean isIdle() {
/*  27 */       return this.id == StateId.Idle;
/*     */     }
/*     */ 
/*     */     int updateProgress(boolean isInertia) {
/*  31 */       int eventID = 2;
/*     */ 
/*  33 */       if ((doesGestureStart(isInertia)) && (!isInertia)) {
/*  34 */         eventID = 1;
/*     */       }
/*     */ 
/*  37 */       this.id = (isInertia ? StateId.Inertia : StateId.Running);
/*     */ 
/*  39 */       return eventID;
/*     */     }
/*     */ 
/*     */     boolean doesGestureStart(boolean isInertia) {
/*  43 */       switch (GestureSupport.2.$SwitchMap$com$sun$glass$ui$GestureSupport$GestureState$StateId[this.id.ordinal()]) {
/*     */       case 1:
/*  45 */         return isInertia;
/*     */       case 2:
/*  47 */         return !isInertia;
/*     */       }
/*  49 */       return true;
/*     */     }
/*     */ 
/*     */     static enum StateId
/*     */     {
/*  17 */       Idle, Running, Inertia;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.GestureSupport
 * JD-Core Version:    0.6.2
 */