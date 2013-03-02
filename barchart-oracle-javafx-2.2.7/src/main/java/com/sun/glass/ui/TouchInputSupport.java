/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class TouchInputSupport
/*     */ {
/*  17 */   private int touchCount = 0;
/*     */   private boolean filterTouchCoordinates;
/*     */   private Map<Long, Integer> touchX;
/*     */   private Map<Long, Integer> touchY;
/*     */   private TouchCountListener listener;
/*     */   private int curTouchCount;
/*     */   private View curView;
/*     */   private int curModifiers;
/*     */   private boolean curIsDirect;
/*     */ 
/*     */   public TouchInputSupport(TouchCountListener listener, boolean filterTouchCoordinates)
/*     */   {
/*  37 */     this.listener = listener;
/*  38 */     this.filterTouchCoordinates = filterTouchCoordinates;
/*  39 */     if (filterTouchCoordinates) {
/*  40 */       this.touchX = new HashMap();
/*  41 */       this.touchY = new HashMap();
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getTouchCount() {
/*  46 */     return this.touchCount;
/*     */   }
/*     */ 
/*     */   public void notifyBeginTouchEvent(View view, int modifiers, boolean isDirect, int touchEventCount)
/*     */   {
/*  51 */     this.curTouchCount = this.touchCount;
/*  52 */     this.curView = view;
/*  53 */     this.curModifiers = modifiers;
/*  54 */     this.curIsDirect = isDirect;
/*  55 */     if (view != null)
/*  56 */       view.notifyBeginTouchEvent(modifiers, isDirect, touchEventCount);
/*     */   }
/*     */ 
/*     */   public void notifyEndTouchEvent(View view)
/*     */   {
/*  61 */     if (view == null) {
/*  62 */       return;
/*     */     }
/*     */ 
/*  65 */     view.notifyEndTouchEvent();
/*     */ 
/*  68 */     if ((this.curTouchCount != 0) && (this.touchCount != 0) && (this.curTouchCount != this.touchCount) && (this.listener != null))
/*     */     {
/*  70 */       this.listener.touchCountChanged(this, this.curView, this.curModifiers, this.curIsDirect);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void notifyNextTouchEvent(View view, int state, long id, int x, int y, int xAbs, int yAbs)
/*     */   {
/*  76 */     switch (state) {
/*     */     case 813:
/*  78 */       this.touchCount -= 1;
/*  79 */       break;
/*     */     case 811:
/*  81 */       this.touchCount += 1;
/*     */     }
/*     */ 
/*  85 */     if (this.filterTouchCoordinates) {
/*  86 */       state = filterTouchInputState(state, id, x, y);
/*     */     }
/*     */ 
/*  89 */     if (view != null)
/*  90 */       view.notifyNextTouchEvent(state, id, x, y, xAbs, yAbs);
/*     */   }
/*     */ 
/*     */   private int filterTouchInputState(int state, long id, int x, int y)
/*     */   {
/*  95 */     switch (state) {
/*     */     case 813:
/*  97 */       this.touchX.remove(Long.valueOf(id));
/*  98 */       this.touchY.remove(Long.valueOf(id));
/*  99 */       break;
/*     */     case 812:
/* 101 */       if ((x == ((Integer)this.touchX.get(Long.valueOf(id))).intValue()) && (y == ((Integer)this.touchY.get(Long.valueOf(id))).intValue()))
/* 102 */         state = 814;
/* 103 */       break;
/*     */     case 811:
/* 107 */       this.touchX.put(Long.valueOf(id), Integer.valueOf(x));
/* 108 */       this.touchY.put(Long.valueOf(id), Integer.valueOf(y));
/*     */     }
/*     */ 
/* 111 */     return state;
/*     */   }
/*     */ 
/*     */   public static abstract interface TouchCountListener
/*     */   {
/*     */     public abstract void touchCountChanged(TouchInputSupport paramTouchInputSupport, View paramView, int paramInt, boolean paramBoolean);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.TouchInputSupport
 * JD-Core Version:    0.6.2
 */