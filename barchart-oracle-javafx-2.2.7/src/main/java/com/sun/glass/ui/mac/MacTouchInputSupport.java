/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.TouchInputSupport;
/*     */ import com.sun.glass.ui.TouchInputSupport.TouchCountListener;
/*     */ import com.sun.glass.ui.View;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ class MacTouchInputSupport extends TouchInputSupport
/*     */ {
/*  20 */   private final Map<Long, WeakReference<View>> touchIdToView = new HashMap();
/*     */   private int curModifiers;
/*     */   private boolean curIsDirect;
/*     */   private List<TouchPoint> curTouchPoints;
/*     */ 
/*     */   MacTouchInputSupport(TouchInputSupport.TouchCountListener listener, boolean filterTouchCoordinates)
/*     */   {
/*  49 */     super(listener, filterTouchCoordinates);
/*     */   }
/*     */ 
/*     */   public void notifyBeginTouchEvent(View view, int modifiers, boolean isDirect, int touchEventCount)
/*     */   {
/*  55 */     this.curModifiers = modifiers;
/*  56 */     this.curIsDirect = isDirect;
/*  57 */     this.curTouchPoints = new ArrayList(touchEventCount);
/*     */   }
/*     */ 
/*     */   public void notifyEndTouchEvent(View view)
/*     */   {
/*  62 */     if (this.curTouchPoints.isEmpty()) {
/*  63 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  67 */       super.notifyBeginTouchEvent(view, this.curModifiers, this.curIsDirect, this.curTouchPoints.size());
/*     */ 
/*  70 */       for (TouchPoint tp : this.curTouchPoints) {
/*  71 */         super.notifyNextTouchEvent(view, tp.state, tp.id, tp.x, tp.y, tp.xAbs, tp.yAbs);
/*     */       }
/*     */ 
/*  75 */       super.notifyEndTouchEvent(view);
/*     */     }
/*     */     finally {
/*  78 */       this.curTouchPoints = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyNextTouchEvent(View view, int state, long id, int x, int y, int xAbs, int yAbs)
/*     */   {
/*  86 */     View storedView = null;
/*  87 */     if (state == 811) {
/*  88 */       storedView = view;
/*  89 */       this.touchIdToView.put(Long.valueOf(id), new WeakReference(view));
/*     */     } else {
/*  91 */       storedView = (View)((WeakReference)this.touchIdToView.get(Long.valueOf(id))).get();
/*  92 */       if (state == 813) {
/*  93 */         this.touchIdToView.remove(Long.valueOf(id));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     if (storedView == view) {
/* 102 */       this.curTouchPoints.add(new TouchPoint(state, id, x, y, xAbs, yAbs));
/*     */     } else {
/* 104 */       if ((storedView != null) && (storedView.isClosed()))
/*     */       {
/* 107 */         storedView = null;
/*     */       }
/*     */ 
/* 110 */       super.notifyBeginTouchEvent(storedView, this.curModifiers, this.curIsDirect, 1);
/*     */ 
/* 112 */       super.notifyNextTouchEvent(storedView, state, id, x, y, xAbs, yAbs);
/*     */ 
/* 114 */       super.notifyEndTouchEvent(storedView);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TouchPoint
/*     */   {
/*     */     final int state;
/*     */     final long id;
/*     */     final int x;
/*     */     final int y;
/*     */     final int xAbs;
/*     */     final int yAbs;
/*     */ 
/*     */     TouchPoint(int state, long id, int x, int y, int xAbs, int yAbs)
/*     */     {
/*  38 */       this.state = state;
/*  39 */       this.id = id;
/*  40 */       this.x = x;
/*  41 */       this.y = y;
/*  42 */       this.xAbs = xAbs;
/*  43 */       this.yAbs = yAbs;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacTouchInputSupport
 * JD-Core Version:    0.6.2
 */