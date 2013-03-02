/*      */ package com.sun.glass.ui;
/*      */ 
/*      */ import com.sun.glass.utils.Disposer;
/*      */ import com.sun.glass.utils.DisposerRecord;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.Map;
/*      */ 
/*      */ public abstract class View
/*      */ {
/*      */   public static final int GESTURE_NO_VALUE = 2147483647;
/*      */   public static final double GESTURE_NO_DOUBLE_VALUE = (0.0D / 0.0D);
/*      */   private long ptr;
/*      */   private Window window;
/*      */   private final Pen pen;
/*      */   private EventHandler eventHandler;
/*  338 */   private int width = -1;
/*  339 */   private int height = -1;
/*      */ 
/*  341 */   private boolean isValid = false;
/*  342 */   private boolean isVisible = false;
/*  343 */   private boolean inFullscreen = false;
/*      */ 
/*  345 */   protected int dirtyX = 0;
/*  346 */   protected int dirtyY = 0;
/*  347 */   protected int dirtyWidth = 0;
/*  348 */   protected int dirtyHeight = 0;
/*      */   private final ViewDisposerRecord disposerRecord;
/*  880 */   private static WeakReference<View> lastClickedView = null;
/*      */   private static int lastClickedButton;
/*      */   private static long lastClickedTime;
/*      */   private static int lastClickedX;
/*      */   private static int lastClickedY;
/*      */   private static int clickCount;
/*  885 */   private static boolean dragProcessed = false;
/*      */   private ClipboardAssistance dropSourceAssistant;
/*      */   ClipboardAssistance dropTargetAssistant;
/*      */ 
/*      */   public static long getMultiClickTime()
/*      */   {
/*  316 */     return Application.GetApplication().staticView_getMultiClickTime();
/*      */   }
/*      */ 
/*      */   public static int getMultiClickMaxX() {
/*  320 */     return Application.GetApplication().staticView_getMultiClickMaxX();
/*      */   }
/*      */ 
/*      */   public static int getMultiClickMaxY() {
/*  324 */     return Application.GetApplication().staticView_getMultiClickMaxY();
/*      */   }
/*      */ 
/*      */   public static void notifyRenderingEnd() {
/*  328 */     Application.GetApplication().staticView_notifyRenderingEnd();
/*      */   }
/*      */ 
/*      */   protected abstract void _enableInputMethodEvents(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   protected abstract long _create(Map paramMap);
/*      */ 
/*      */   protected View(Pen pen)
/*      */   {
/*  369 */     Map capabilities = pen.getCapabilities();
/*  370 */     Application.GetApplication(); Map deviceCapabilities = Application.getDeviceDetails();
/*  371 */     if (deviceCapabilities != null) {
/*  372 */       capabilities.putAll(deviceCapabilities);
/*      */     }
/*  374 */     this.ptr = _create(capabilities);
/*  375 */     if (this.ptr == 0L) {
/*  376 */       throw new RuntimeException("could not create platform view");
/*      */     }
/*      */ 
/*  379 */     this.pen = pen;
/*  380 */     this.pen.setView(this);
/*      */ 
/*  382 */     Disposer.addRecord(this, this.disposerRecord = new ViewDisposerRecord(this.ptr));
/*      */   }
/*      */ 
/*      */   private void checkNotClosed()
/*      */   {
/*  401 */     if (this.ptr == 0L)
/*  402 */       throw new IllegalStateException("The view has already been closed");
/*      */   }
/*      */ 
/*      */   public boolean isClosed()
/*      */   {
/*  407 */     return this.ptr == 0L;
/*      */   }
/*      */ 
/*      */   protected abstract long _getNativeView(long paramLong);
/*      */ 
/*      */   public long getNativeView()
/*      */   {
/*  417 */     checkNotClosed();
/*  418 */     return _getNativeView(this.ptr);
/*      */   }
/*      */ 
/*      */   public int getNativeRemoteLayerId(String serverName)
/*      */   {
/*  423 */     throw new RuntimeException("This operation is not supported on this platform");
/*      */   }
/*      */ 
/*      */   public Window getWindow() {
/*  427 */     return this.window;
/*      */   }
/*      */ 
/*      */   public Pen getPen() {
/*  431 */     return this.pen;
/*      */   }
/*      */ 
/*      */   protected abstract int _getX(long paramLong);
/*      */ 
/*      */   public int getX() {
/*  437 */     checkNotClosed();
/*  438 */     return _getX(this.ptr);
/*      */   }
/*      */ 
/*      */   protected abstract int _getY(long paramLong);
/*      */ 
/*      */   public int getY() {
/*  444 */     checkNotClosed();
/*  445 */     return _getY(this.ptr);
/*      */   }
/*      */ 
/*      */   public int getWidth() {
/*  449 */     return this.width;
/*      */   }
/*      */ 
/*      */   public int getHeight() {
/*  453 */     return this.height;
/*      */   }
/*      */ 
/*      */   protected abstract void _setParent(long paramLong1, long paramLong2);
/*      */ 
/*      */   void setWindow(Window window)
/*      */   {
/*  460 */     checkNotClosed();
/*  461 */     this.window = window;
/*  462 */     _setParent(this.ptr, window == null ? 0L : window.getNativeHandle());
/*  463 */     setValid((this.ptr != 0L) && (window != null));
/*      */   }
/*      */ 
/*      */   void setVisible(boolean visible)
/*      */   {
/*  468 */     if (this.isVisible != visible)
/*  469 */       this.isVisible = visible;
/*      */   }
/*      */ 
/*      */   protected void setValid(boolean valid)
/*      */   {
/*  475 */     this.isValid = valid;
/*      */   }
/*      */   protected boolean isValid() {
/*  478 */     return this.isValid;
/*      */   }
/*      */   protected abstract boolean _close(long paramLong);
/*      */ 
/*      */   public void close() {
/*  483 */     if (this.ptr == 0L) {
/*  484 */       return;
/*      */     }
/*  486 */     if (isInFullscreen() == true) {
/*  487 */       _exitFullscreen(this.ptr, false);
/*      */     }
/*  489 */     Window host = getWindow();
/*  490 */     if (host != null) {
/*  491 */       host.setView(null);
/*      */     }
/*  493 */     this.isValid = false;
/*  494 */     _close(this.ptr);
/*  495 */     this.ptr = 0L;
/*  496 */     this.disposerRecord.ptr = 0L;
/*      */   }
/*      */ 
/*      */   public EventHandler getEventHandler() {
/*  500 */     return this.eventHandler;
/*      */   }
/*      */ 
/*      */   public void setEventHandler(EventHandler eventHandler) {
/*  504 */     this.eventHandler = eventHandler;
/*      */   }
/*      */ 
/*      */   private void handleViewEvent(long time, int type)
/*      */   {
/*  510 */     if (this.eventHandler != null)
/*  511 */       this.eventHandler.handleViewEvent(this, time, type);
/*      */   }
/*      */ 
/*      */   private void handleKeyEvent(long time, int action, int keyCode, char[] keyChars, int modifiers)
/*      */   {
/*  517 */     if (this.eventHandler != null)
/*  518 */       this.eventHandler.handleKeyEvent(this, time, action, keyCode, keyChars, modifiers);
/*      */   }
/*      */ 
/*      */   private void handleMouseEvent(long time, int type, int button, int x, int y, int xAbs, int yAbs, int clickCount, int modifiers, boolean isPopupTrigger, boolean isSynthesized)
/*      */   {
/*  526 */     EventHandler h = this.eventHandler;
/*  527 */     if (this.eventHandler != null)
/*  528 */       this.eventHandler.handleMouseEvent(this, time, type, button, x, y, xAbs, yAbs, clickCount, modifiers, isPopupTrigger, isSynthesized);
/*      */   }
/*      */ 
/*      */   private void handleMenuEvent(int x, int y, int xAbs, int yAbs, boolean isKeyboardTrigger)
/*      */   {
/*  535 */     if (this.eventHandler != null)
/*  536 */       this.eventHandler.handleMenuEvent(this, x, y, xAbs, yAbs, isKeyboardTrigger);
/*      */   }
/*      */ 
/*      */   public void handleBeginTouchEvent(View view, long time, int modifiers, boolean isDirect, int touchEventCount)
/*      */   {
/*  543 */     EventHandler h = this.eventHandler;
/*  544 */     if (h != null)
/*  545 */       h.handleBeginTouchEvent(view, time, modifiers, isDirect, touchEventCount);
/*      */   }
/*      */ 
/*      */   public void handleNextTouchEvent(View view, long time, int type, long touchId, int x, int y, int xAbs, int yAbs)
/*      */   {
/*  556 */     EventHandler h = this.eventHandler;
/*  557 */     if (h != null)
/*  558 */       h.handleNextTouchEvent(view, time, type, touchId, x, y, xAbs, yAbs);
/*      */   }
/*      */ 
/*      */   public void handleEndTouchEvent(View view, long time)
/*      */   {
/*  564 */     EventHandler h = this.eventHandler;
/*  565 */     if (h != null)
/*  566 */       h.handleEndTouchEvent(view, time);
/*      */   }
/*      */ 
/*      */   public void handleScrollGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy, double totaldx, double totaldy)
/*      */   {
/*  583 */     EventHandler h = this.eventHandler;
/*  584 */     if (h != null)
/*  585 */       h.handleScrollGestureEvent(view, time, type, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy, totaldx, totaldy);
/*      */   }
/*      */ 
/*      */   public void handleZoomGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int originx, int originy, int originxAbs, int originyAbs, double scale, double expansion, double totalscale, double totalexpansion)
/*      */   {
/*  605 */     EventHandler h = this.eventHandler;
/*  606 */     if (h != null)
/*  607 */       h.handleZoomGestureEvent(view, time, type, modifiers, isDirect, isInertia, originx, originy, originxAbs, originyAbs, scale, expansion, totalscale, totalexpansion);
/*      */   }
/*      */ 
/*      */   public void handleRotateGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int originx, int originy, int originxAbs, int originyAbs, double dangle, double totalangle)
/*      */   {
/*  626 */     EventHandler h = this.eventHandler;
/*  627 */     if (h != null)
/*  628 */       h.handleRotateGestureEvent(view, time, type, modifiers, isDirect, isInertia, originx, originy, originxAbs, originyAbs, dangle, totalangle);
/*      */   }
/*      */ 
/*      */   public void handleSwipeGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int dir, int originx, int originy, int originxAbs, int originyAbs)
/*      */   {
/*  646 */     EventHandler h = this.eventHandler;
/*  647 */     if (h != null)
/*  648 */       h.handleSwipeGestureEvent(view, time, type, modifiers, isDirect, isInertia, touchCount, dir, originx, originy, originxAbs, originyAbs);
/*      */   }
/*      */ 
/*      */   private void handleInputMethodEvent(long time, String text, int[] clauseBoundary, int[] attrBoundary, byte[] attrValue, int commitCount, int cursorPos)
/*      */   {
/*  657 */     if (this.eventHandler != null)
/*  658 */       this.eventHandler.handleInputMethodEvent(time, text, clauseBoundary, attrBoundary, attrValue, commitCount, cursorPos);
/*      */   }
/*      */ 
/*      */   public void enableInputMethodEvents(boolean enable)
/*      */   {
/*  665 */     checkNotClosed();
/*  666 */     _enableInputMethodEvents(this.ptr, enable);
/*      */   }
/*      */ 
/*      */   private double[] getInputMethodCandidatePos(int offset) {
/*  670 */     if (this.eventHandler != null) {
/*  671 */       return this.eventHandler.getInputMethodCandidatePos(offset);
/*      */     }
/*  673 */     return null;
/*      */   }
/*      */ 
/*      */   private void handleDragStart(int button, int x, int y, int xAbs, int yAbs, ClipboardAssistance dropSourceAssistant)
/*      */   {
/*  678 */     if (this.eventHandler != null)
/*  679 */       this.eventHandler.handleDragStart(this, button, x, y, xAbs, yAbs, dropSourceAssistant);
/*      */   }
/*      */ 
/*      */   private void handleDragEnd(int performedAction)
/*      */   {
/*  684 */     if (this.eventHandler != null)
/*  685 */       this.eventHandler.handleDragEnd(this, performedAction);
/*      */   }
/*      */ 
/*      */   private int handleDragEnter(int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant)
/*      */   {
/*  691 */     if (this.eventHandler != null) {
/*  692 */       return this.eventHandler.handleDragEnter(this, x, y, xAbs, yAbs, recommendedDropAction, dropTargetAssistant);
/*      */     }
/*  694 */     return recommendedDropAction;
/*      */   }
/*      */ 
/*      */   private int handleDragOver(int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant)
/*      */   {
/*  700 */     if (this.eventHandler != null) {
/*  701 */       return this.eventHandler.handleDragOver(this, x, y, xAbs, yAbs, recommendedDropAction, dropTargetAssistant);
/*      */     }
/*  703 */     return recommendedDropAction;
/*      */   }
/*      */ 
/*      */   private void handleDragLeave(ClipboardAssistance dropTargetAssistant)
/*      */   {
/*  708 */     if (this.eventHandler != null)
/*  709 */       this.eventHandler.handleDragLeave(this, dropTargetAssistant);
/*      */   }
/*      */ 
/*      */   private int handleDragDrop(int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant)
/*      */   {
/*  715 */     if (this.eventHandler != null) {
/*  716 */       return this.eventHandler.handleDragDrop(this, x, y, xAbs, yAbs, recommendedDropAction, dropTargetAssistant);
/*      */     }
/*  718 */     return 0;
/*      */   }
/*      */ 
/*      */   protected abstract void _repaint(long paramLong);
/*      */ 
/*      */   public void scheduleRepaint()
/*      */   {
/*  727 */     checkNotClosed();
/*  728 */     _repaint(this.ptr);
/*      */   }
/*      */ 
/*      */   protected abstract boolean _begin(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public void lock()
/*      */   {
/*  735 */     checkNotClosed();
/*  736 */     _begin(this.ptr, isValid());
/*      */   }
/*      */ 
/*      */   protected abstract void _end(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*      */ 
/*      */   public void unlock(boolean flush)
/*      */   {
/*  744 */     checkNotClosed();
/*  745 */     _end(this.ptr, isValid(), flush);
/*      */   }
/*      */ 
/*      */   protected abstract void _uploadPixels(long paramLong, Pixels paramPixels);
/*      */ 
/*      */   public void uploadPixels(Pixels pixels)
/*      */   {
/*  756 */     checkNotClosed();
/*  757 */     lock();
/*      */     try {
/*  759 */       _uploadPixels(this.ptr, pixels);
/*      */     } finally {
/*  761 */       unlock(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract boolean _enterFullscreen(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
/*      */ 
/*      */   public boolean enterFullscreen(boolean animate, boolean keepRatio, boolean hideCursor)
/*      */   {
/*  770 */     checkNotClosed();
/*  771 */     if (_enterFullscreen(this.ptr, animate, keepRatio, hideCursor)) {
/*  772 */       return true;
/*      */     }
/*  774 */     return false;
/*      */   }
/*      */   protected abstract void _exitFullscreen(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public void exitFullscreen(boolean animate) {
/*  779 */     checkNotClosed();
/*  780 */     _exitFullscreen(this.ptr, animate);
/*      */   }
/*      */ 
/*      */   public boolean isInFullscreen() {
/*  784 */     return this.inFullscreen;
/*      */   }
/*      */ 
/*      */   public boolean toggleFullscreen(boolean animate, boolean keepRatio, boolean hideCursor) {
/*  788 */     checkNotClosed();
/*  789 */     if (!this.inFullscreen)
/*  790 */       enterFullscreen(animate, keepRatio, hideCursor);
/*      */     else {
/*  792 */       exitFullscreen(animate);
/*      */     }
/*      */ 
/*  795 */     _repaint(this.ptr);
/*      */ 
/*  797 */     return this.inFullscreen;
/*      */   }
/*      */ 
/*      */   protected void handleViewEvent(int type)
/*      */   {
/*  806 */     if (type == 431) {
/*  807 */       if (isValid() == true) {
/*  808 */         handleViewEvent(System.nanoTime(), type);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  813 */       boolean synthesizeMOVE = false;
/*      */ 
/*  815 */       switch (type) {
/*      */       case 422:
/*  817 */         setValid(false);
/*  818 */         synthesizeMOVE = true;
/*  819 */         break;
/*      */       case 421:
/*  821 */         setValid(true);
/*  822 */         synthesizeMOVE = true;
/*  823 */         break;
/*      */       case 441:
/*  825 */         this.inFullscreen = true;
/*  826 */         synthesizeMOVE = true;
/*  827 */         break;
/*      */       case 442:
/*  829 */         this.inFullscreen = false;
/*  830 */         synthesizeMOVE = true;
/*      */       }
/*      */ 
/*  834 */       handleViewEvent(System.nanoTime(), type);
/*      */ 
/*  836 */       if (synthesizeMOVE)
/*      */       {
/*  839 */         handleViewEvent(System.nanoTime(), 433);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void notifyResize(int width, int height) {
/*  845 */     if ((this.width == width) && (this.height == height)) {
/*  846 */       return;
/*      */     }
/*      */ 
/*  849 */     this.width = width;
/*  850 */     this.height = height;
/*  851 */     handleViewEvent(System.nanoTime(), 432);
/*      */   }
/*      */ 
/*      */   protected void notifyRepaint(int x, int y, int width, int height)
/*      */   {
/*  863 */     this.dirtyX = x;
/*  864 */     this.dirtyY = y;
/*  865 */     this.dirtyWidth = width;
/*  866 */     this.dirtyHeight = height;
/*  867 */     handleViewEvent(431);
/*      */   }
/*      */ 
/*      */   protected void notifyMenu(int x, int y, int xAbs, int yAbs, boolean isKeyboardTrigger)
/*      */   {
/*  874 */     handleMenuEvent(x, y, xAbs, yAbs, isKeyboardTrigger);
/*      */   }
/*      */ 
/*      */   protected void notifyMouse(int type, int button, int x, int y, int xAbs, int yAbs, int modifiers, boolean isPopupTrigger, boolean isSynthesized)
/*      */   {
/*  891 */     if (this.window != null)
/*      */     {
/*  893 */       if (this.window.handleMouseEvent(type, button, x, y, xAbs, yAbs))
/*      */       {
/*  895 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  899 */     long now = System.nanoTime();
/*  900 */     if (type == 221) {
/*  901 */       View lastClickedView = lastClickedView == null ? null : (View)lastClickedView.get();
/*      */ 
/*  903 */       if ((lastClickedView == this) && (lastClickedButton == button) && (now - lastClickedTime <= 1000000L * getMultiClickTime()) && (Math.abs(x - lastClickedX) <= getMultiClickMaxX()) && (Math.abs(y - lastClickedY) <= getMultiClickMaxY()))
/*      */       {
/*  909 */         clickCount += 1;
/*      */       } else {
/*  911 */         clickCount = 1;
/*      */ 
/*  913 */         lastClickedView = new WeakReference(this);
/*  914 */         lastClickedButton = button;
/*  915 */         lastClickedX = x;
/*  916 */         lastClickedY = y;
/*      */       }
/*      */ 
/*  919 */       lastClickedTime = now;
/*      */     }
/*      */ 
/*  922 */     handleMouseEvent(now, type, button, x, y, xAbs, yAbs, clickCount, modifiers, isPopupTrigger, isSynthesized);
/*      */ 
/*  925 */     if (type == 223)
/*      */     {
/*  927 */       if (!dragProcessed) {
/*  928 */         notifyDragStart(button, x, y, xAbs, yAbs);
/*  929 */         dragProcessed = true;
/*      */       }
/*      */     }
/*  932 */     else dragProcessed = false;
/*      */   }
/*      */ 
/*      */   protected void notifyScroll(int x, int y, int xAbs, int yAbs, double deltaX, double deltaY, int modifiers, int lines, int chars, int defaultLines, int defaultChars, double xMultiplier, double yMultiplier)
/*      */   {
/*  943 */     if (this.eventHandler != null)
/*  944 */       this.eventHandler.handleScrollEvent(this, System.nanoTime(), x, y, xAbs, yAbs, deltaX, deltaY, modifiers, lines, chars, defaultLines, defaultChars, xMultiplier, yMultiplier);
/*      */   }
/*      */ 
/*      */   protected void notifyKey(int type, int keyCode, char[] keyChars, int modifiers)
/*      */   {
/*  951 */     handleKeyEvent(System.nanoTime(), type, keyCode, keyChars, modifiers);
/*      */   }
/*      */ 
/*      */   protected void notifyInputMethod(String text, int[] clauseBoundary, int[] attrBoundary, byte[] attrValue, int committedTextLength, int caretPos, int visiblePos)
/*      */   {
/*  957 */     handleInputMethodEvent(System.nanoTime(), text, clauseBoundary, attrBoundary, attrValue, committedTextLength, caretPos);
/*      */   }
/*      */ 
/*      */   protected double[] notifyInputMethodCandidatePosRequest(int offset)
/*      */   {
/*  962 */     double[] ret = getInputMethodCandidatePos(offset);
/*  963 */     if (ret == null) {
/*  964 */       ret = new double[2];
/*  965 */       ret[0] = 0.0D;
/*  966 */       ret[1] = 0.0D;
/*      */     }
/*  968 */     return ret;
/*      */   }
/*      */ 
/*      */   protected void notifyDragStart(int button, int x, int y, int xAbs, int yAbs)
/*      */   {
/*  973 */     this.dropSourceAssistant = new ClipboardAssistance("DND")
/*      */     {
/*      */       public void actionPerformed(int performedAction)
/*      */       {
/*  977 */         View.this.notifyDragEnd(performedAction);
/*      */       }
/*      */     };
/*  981 */     handleDragStart(button, x, y, xAbs, yAbs, this.dropSourceAssistant);
/*      */ 
/*  983 */     if (this.dropSourceAssistant != null) {
/*  984 */       this.dropSourceAssistant.close();
/*  985 */       this.dropSourceAssistant = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void notifyDragEnd(int performedAction) {
/*  990 */     handleDragEnd(performedAction);
/*  991 */     if (this.dropSourceAssistant != null) {
/*  992 */       this.dropSourceAssistant.close();
/*  993 */       this.dropSourceAssistant = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected int notifyDragEnter(int x, int y, int xAbs, int yAbs, int recommendedDropAction)
/*      */   {
/* 1000 */     this.dropTargetAssistant = new ClipboardAssistance("DND") {
/*      */       public void flush() {
/* 1002 */         throw new UnsupportedOperationException("Flush is forbidden from target!");
/*      */       }
/*      */     };
/* 1005 */     return handleDragEnter(x, y, xAbs, yAbs, recommendedDropAction, this.dropTargetAssistant);
/*      */   }
/*      */ 
/*      */   protected int notifyDragOver(int x, int y, int xAbs, int yAbs, int recommendedDropAction)
/*      */   {
/* 1010 */     return handleDragOver(x, y, xAbs, yAbs, recommendedDropAction, this.dropTargetAssistant);
/*      */   }
/*      */ 
/*      */   protected void notifyDragLeave()
/*      */   {
/* 1015 */     handleDragLeave(this.dropTargetAssistant);
/* 1016 */     this.dropTargetAssistant.close();
/*      */   }
/*      */ 
/*      */   protected int notifyDragDrop(int x, int y, int xAbs, int yAbs, int recommendedDropAction)
/*      */   {
/* 1022 */     int performedAction = handleDragDrop(x, y, xAbs, yAbs, recommendedDropAction, this.dropTargetAssistant);
/* 1023 */     this.dropTargetAssistant.close();
/* 1024 */     return performedAction;
/*      */   }
/*      */ 
/*      */   public void notifyBeginTouchEvent(int modifiers, boolean isDirect, int touchEventCount)
/*      */   {
/* 1029 */     handleBeginTouchEvent(this, System.nanoTime(), modifiers, isDirect, touchEventCount);
/*      */   }
/*      */ 
/*      */   public void notifyNextTouchEvent(int type, long touchId, int x, int y, int xAbs, int yAbs)
/*      */   {
/* 1035 */     handleNextTouchEvent(this, System.nanoTime(), type, touchId, x, y, xAbs, yAbs);
/*      */   }
/*      */ 
/*      */   public void notifyEndTouchEvent()
/*      */   {
/* 1040 */     handleEndTouchEvent(this, System.nanoTime());
/*      */   }
/*      */ 
/*      */   public void notifyScrollGestureEvent(int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy, double totaldx, double totaldy)
/*      */   {
/* 1048 */     handleScrollGestureEvent(this, System.nanoTime(), type, modifiers, isDirect, isInertia, touchCount, x, y, xAbs, yAbs, dx, dy, totaldx, totaldy);
/*      */   }
/*      */ 
/*      */   public void notifyZoomGestureEvent(int type, int modifiers, boolean isDirect, boolean isInertia, int originx, int originy, int originxAbs, int originyAbs, double scale, double expansion, double totalscale, double totalexpansion)
/*      */   {
/* 1059 */     handleZoomGestureEvent(this, System.nanoTime(), type, modifiers, isDirect, isInertia, originx, originy, originxAbs, originyAbs, scale, expansion, totalscale, totalexpansion);
/*      */   }
/*      */ 
/*      */   public void notifyRotateGestureEvent(int type, int modifiers, boolean isDirect, boolean isInertia, int originx, int originy, int originxAbs, int originyAbs, double dangle, double totalangle)
/*      */   {
/* 1070 */     handleRotateGestureEvent(this, System.nanoTime(), type, modifiers, isDirect, isInertia, originx, originy, originxAbs, originyAbs, dangle, totalangle);
/*      */   }
/*      */ 
/*      */   public void notifySwipeGestureEvent(int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int dir, int originx, int originy, int originxAbs, int originyAbs)
/*      */   {
/* 1080 */     handleSwipeGestureEvent(this, System.nanoTime(), type, modifiers, isDirect, isInertia, touchCount, dir, originx, originy, originxAbs, originyAbs);
/*      */   }
/*      */ 
/*      */   private static class ViewDisposerRecord
/*      */     implements DisposerRecord
/*      */   {
/*      */     public volatile long ptr;
/*      */ 
/*      */     public ViewDisposerRecord(long ptr)
/*      */     {
/*  390 */       this.ptr = ptr;
/*      */     }
/*      */ 
/*      */     public void dispose() {
/*  394 */       if (this.ptr != 0L);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static final class Capability
/*      */   {
/*      */     public static final int k3dKeyValue = 0;
/*      */     public static final int kSyncKeyValue = 1;
/*      */     public static final int k3dProjectionKeyValue = 2;
/*      */     public static final int k3dProjectionAngleKeyValue = 3;
/*      */     public static final int k3dDepthKeyValue = 4;
/*  358 */     public static final Object k3dKey = Integer.valueOf(0);
/*  359 */     public static final Object kSyncKey = Integer.valueOf(1);
/*  360 */     public static final Object k3dProjectionKey = Integer.valueOf(2);
/*  361 */     public static final Object k3dProjectionAngleKey = Integer.valueOf(3);
/*  362 */     public static final Object k3dDepthKey = Integer.valueOf(4);
/*      */   }
/*      */ 
/*      */   public static class EventHandler
/*      */   {
/*      */     public void handleViewEvent(View view, long time, int type)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleKeyEvent(View view, long time, int action, int keyCode, char[] keyChars, int modifiers)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleMenuEvent(View view, int x, int y, int xAbs, int yAbs, boolean isKeyboardTrigger)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleMouseEvent(View view, long time, int type, int button, int x, int y, int xAbs, int yAbs, int clickCount, int modifiers, boolean isPopupTrigger, boolean isSynthesized)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleScrollEvent(View view, long time, int x, int y, int xAbs, int yAbs, double deltaX, double deltaY, int modifiers, int lines, int chars, int defaultLines, int defaultChars, double xMultiplier, double yMultiplier)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleInputMethodEvent(long time, String text, int[] clauseBoundary, int[] attrBoundary, byte[] attrValue, int commitCount, int cursorPos)
/*      */     {
/*      */     }
/*      */ 
/*      */     public double[] getInputMethodCandidatePos(int offset)
/*      */     {
/*   74 */       return null;
/*      */     }
/*      */ 
/*      */     public void handleDragStart(View view, int button, int x, int y, int xAbs, int yAbs, ClipboardAssistance dropSourceAssistant)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleDragEnd(View view, int performedAction)
/*      */     {
/*      */     }
/*      */ 
/*      */     public int handleDragEnter(View view, int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant) {
/*   86 */       return recommendedDropAction;
/*      */     }
/*      */ 
/*      */     public int handleDragOver(View view, int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant)
/*      */     {
/*   91 */       return recommendedDropAction;
/*      */     }
/*      */ 
/*      */     public void handleDragLeave(View view, ClipboardAssistance dropTargetAssistant)
/*      */     {
/*      */     }
/*      */ 
/*      */     public int handleDragDrop(View view, int x, int y, int xAbs, int yAbs, int recommendedDropAction, ClipboardAssistance dropTargetAssistant) {
/*   99 */       return 0;
/*      */     }
/*      */ 
/*      */     public void handleBeginTouchEvent(View view, long time, int modifiers, boolean isDirect, int touchEventCount)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleNextTouchEvent(View view, long time, int type, long touchId, int x, int y, int xAbs, int yAbs)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleEndTouchEvent(View view, long time)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleScrollGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int x, int y, int xAbs, int yAbs, double dx, double dy, double totaldx, double totaldy)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleZoomGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double scale, double expansion, double totalscale, double totalexpansion)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleRotateGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int x, int y, int xAbs, int yAbs, double dangle, double totalangle)
/*      */     {
/*      */     }
/*      */ 
/*      */     public void handleSwipeGestureEvent(View view, long time, int type, int modifiers, boolean isDirect, boolean isInertia, int touchCount, int dir, int x, int y, int xAbs, int yAbs)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.View
 * JD-Core Version:    0.6.2
 */