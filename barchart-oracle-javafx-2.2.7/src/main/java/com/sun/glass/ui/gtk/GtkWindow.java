/*     */ package com.sun.glass.ui.gtk;
/*     */ 
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ 
/*     */ class GtkWindow extends Window
/*     */ {
/*     */   public GtkWindow(Window owner, Screen screen, int styleMask)
/*     */   {
/*  21 */     super(owner, screen, styleMask);
/*     */   }
/*     */ 
/*     */   protected GtkWindow(long parent) {
/*  25 */     super(parent);
/*     */   }
/*     */ 
/*     */   protected native long _createWindow(long paramLong1, long paramLong2, int paramInt);
/*     */ 
/*     */   protected native long _createChildWindow(long paramLong);
/*     */ 
/*     */   protected native boolean _close(long paramLong);
/*     */ 
/*     */   protected native boolean _setView(long paramLong, View paramView);
/*     */ 
/*     */   protected boolean _setMenubar(long ptr, long menubarPtr)
/*     */   {
/*  43 */     return true;
/*     */   }
/*     */ 
/*     */   private native void minimizeImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private native void maximizeImpl(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   private native void setBoundsImpl(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   private native void setVisibleImpl(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native boolean _setResizable(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native boolean _requestFocus(long paramLong, int paramInt);
/*     */ 
/*     */   protected native void _setFocusable(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native boolean _grabFocus(long paramLong);
/*     */ 
/*     */   protected native void _ungrabFocus(long paramLong);
/*     */ 
/*     */   protected native boolean _setTitle(long paramLong, String paramString);
/*     */ 
/*     */   protected native void _setLevel(long paramLong, int paramInt);
/*     */ 
/*     */   protected native void _setAlpha(long paramLong, float paramFloat);
/*     */ 
/*     */   protected native boolean _setBackground(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3);
/*     */ 
/*     */   protected native void _setEnabled(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected native boolean _setMinimumSize(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   protected native boolean _setMaximumSize(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   protected native void _setIcon(long paramLong, Pixels paramPixels);
/*     */ 
/*     */   protected native void _toFront(long paramLong);
/*     */ 
/*     */   protected native void _toBack(long paramLong);
/*     */ 
/*     */   protected native void _enterModal(long paramLong);
/*     */ 
/*     */   protected native void _enterModalWithWindow(long paramLong1, long paramLong2);
/*     */ 
/*     */   protected native void _exitModal(long paramLong);
/*     */ 
/*     */   protected native long _getNativeWindowImpl(long paramLong);
/*     */ 
/*     */   public boolean supportsPlatformModality()
/*     */   {
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   private native boolean isVisible(long paramLong);
/*     */ 
/*     */   protected boolean _setVisible(long ptr, boolean visible)
/*     */   {
/* 119 */     setVisibleImpl(ptr, visible);
/* 120 */     return isVisible(ptr);
/*     */   }
/*     */ 
/*     */   protected boolean _minimize(long ptr, boolean minimize)
/*     */   {
/* 125 */     minimizeImpl(ptr, minimize);
/* 126 */     notifyStateChanged(531);
/* 127 */     return minimize;
/*     */   }
/*     */ 
/*     */   protected boolean _maximize(long ptr, boolean maximize, boolean wasMaximized)
/*     */   {
/* 133 */     maximizeImpl(ptr, maximize, wasMaximized);
/* 134 */     notifyStateChanged(532);
/* 135 */     return maximize;
/*     */   }
/*     */ 
/*     */   private native void _showOrHideChildren(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   protected void notifyStateChanged(int stateChangeEvent) {
/* 141 */     if (stateChangeEvent == 531)
/* 142 */       _showOrHideChildren(getNativeHandle(), false);
/* 143 */     else if (stateChangeEvent == 533) {
/* 144 */       _showOrHideChildren(getNativeHandle(), true);
/*     */     }
/* 146 */     switch (stateChangeEvent) {
/*     */     case 531:
/*     */     case 532:
/*     */     case 533:
/* 150 */       notifyResize(stateChangeEvent, getWidth(), getHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void _setCursor(long ptr, Cursor cursor)
/*     */   {
/* 157 */     if (cursor.getType() == 0)
/* 158 */       _setCustomCursor(ptr, cursor);
/*     */     else
/* 160 */       _setCursorType(ptr, cursor.getType());
/*     */   }
/*     */ 
/*     */   private native void _setCursorType(long paramLong, int paramInt);
/*     */ 
/*     */   private native void _setCustomCursor(long paramLong, Cursor paramCursor);
/*     */ 
/*     */   protected native int _getEmbeddedX(long paramLong);
/*     */ 
/*     */   protected native int _getEmbeddedY(long paramLong);
/*     */ 
/*     */   public long getNativeWindow()
/*     */   {
/* 180 */     return _getNativeWindowImpl(super.getNativeWindow());
/*     */   }
/*     */ 
/*     */   protected void _setBounds(long ptr, int x, int y, boolean xSet, boolean ySet, int w, int h, int cw, int ch, float xGravity, float yGravity)
/*     */   {
/* 185 */     _setGravity(ptr, xGravity, yGravity);
/* 186 */     setBoundsImpl(ptr, x, y, xSet, ySet, w, h, cw, ch);
/*     */ 
/* 188 */     if (((w <= 0) && (cw > 0)) || ((h <= 0) && (ch > 0))) {
/* 189 */       int[] extarr = new int[4];
/* 190 */       getFrameExtents(ptr, extarr);
/*     */ 
/* 193 */       notifyResize(511, (w <= 0) && (cw > 0) ? cw + extarr[0] + extarr[1] : w, (h <= 0) && (ch > 0) ? ch + extarr[2] + extarr[3] : h);
/*     */     }
/*     */   }
/*     */ 
/*     */   private native void getFrameExtents(long paramLong, int[] paramArrayOfInt);
/*     */ 
/*     */   protected native void _setGravity(long paramLong, float paramFloat1, float paramFloat2);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkWindow
 * JD-Core Version:    0.6.2
 */