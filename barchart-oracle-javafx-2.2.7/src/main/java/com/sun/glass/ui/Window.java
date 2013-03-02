/*      */ package com.sun.glass.ui;
/*      */ 
/*      */ import com.sun.glass.ui.delegate.MenuBarDelegate;
/*      */ import com.sun.glass.utils.Disposer;
/*      */ import com.sun.glass.utils.DisposerRecord;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ 
/*      */ public abstract class Window
/*      */ {
/*      */   private long ptr;
/*   26 */   private volatile long delegatePtr = 0L;
/*      */ 
/*   29 */   private static final List<Window> visibleWindows = new ArrayList();
/*      */   public static final int UNTITLED = 0;
/*      */   public static final int TITLED = 1;
/*      */   public static final int TRANSPARENT = 2;
/*      */   public static final int NORMAL = 0;
/*      */   public static final int UTILITY = 4;
/*      */   public static final int POPUP = 8;
/*      */   public static final int CLOSABLE = 16;
/*      */   public static final int MINIMIZABLE = 32;
/*      */   public static final int MAXIMIZABLE = 64;
/*      */   private final Window owner;
/*      */   private final long parent;
/*      */   private final WindowDisposerRecord disposerRecord;
/*      */   private final int styleMask;
/*      */   private final boolean isDecorated;
/*  112 */   private boolean shouldStartUndecoratedMove = false;
/*      */ 
/*  114 */   private View view = null;
/*  115 */   private Screen screen = null;
/*  116 */   private MenuBar menubar = null;
/*  117 */   private String title = "";
/*  118 */   private UndecoratedMoveResizeHelper helper = null;
/*      */ 
/*  120 */   private int state = 1;
/*  121 */   private int level = 1;
/*  122 */   private int x = 0;
/*  123 */   private int y = 0;
/*  124 */   private int width = 0;
/*  125 */   private int height = 0;
/*  126 */   private float alpha = 1.0F;
/*      */ 
/*  132 */   private Timer embeddedLocationTimer = null;
/*  133 */   private int lastKnownEmbeddedX = 0;
/*  134 */   private int lastKnownEmbeddedY = 0;
/*      */ 
/*  136 */   private volatile boolean isResizable = false;
/*  137 */   private volatile boolean isVisible = false;
/*  138 */   private volatile boolean isFocused = false;
/*  139 */   private volatile boolean isFocusable = true;
/*  140 */   private volatile boolean isModal = false;
/*      */ 
/*  144 */   private volatile int disableCount = 0;
/*      */ 
/*  146 */   private int minimumWidth = 0; private int minimumHeight = 0;
/*  147 */   private int maximumWidth = 2147483647; private int maximumHeight = 2147483647;
/*      */   private EventHandler eventHandler;
/*  474 */   private static volatile Window focusedWindow = null;
/*      */ 
/*      */   public static synchronized List<Window> getWindows()
/*      */   {
/*   31 */     return visibleWindows;
/*      */   }
/*      */ 
/*      */   private static synchronized void add(Window window) {
/*   35 */     visibleWindows.add(window);
/*      */   }
/*      */ 
/*      */   private static synchronized void remove(Window window) {
/*   39 */     visibleWindows.remove(window);
/*      */   }
/*      */ 
/*      */   protected abstract long _createWindow(long paramLong1, long paramLong2, int paramInt);
/*      */ 
/*      */   protected Window(Window owner, Screen screen, int styleMask)
/*      */   {
/*  153 */     switch (styleMask & 0x3) {
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*  157 */       break;
/*      */     default:
/*  159 */       throw new RuntimeException("The visual kind should be UNTITLED, TITLED, or TRANSPARENT, but not a combination of these");
/*      */     }
/*  161 */     switch (styleMask & 0xC) {
/*      */     case 0:
/*      */     case 4:
/*      */     case 8:
/*  165 */       break;
/*      */     default:
/*  167 */       throw new RuntimeException("The functional type should be NORMAL, POPUP, or UTILITY, but not a combination of these");
/*      */     }
/*      */ 
/*  170 */     this.owner = owner;
/*  171 */     this.parent = 0L;
/*  172 */     this.styleMask = styleMask;
/*  173 */     this.isDecorated = ((this.styleMask & 0x1) != 0);
/*      */ 
/*  175 */     this.screen = (screen != null ? screen : Screen.getMainScreen());
/*      */ 
/*  177 */     this.ptr = _createWindow(owner != null ? owner.getNativeHandle() : 0L, screen.getNativeScreen(), this.styleMask);
/*      */ 
/*  179 */     if (this.ptr == 0L) {
/*  180 */       throw new RuntimeException("could not create platform window");
/*      */     }
/*      */ 
/*  183 */     Disposer.addRecord(this, this.disposerRecord = new WindowDisposerRecord(this.ptr));
/*      */   }
/*      */ 
/*      */   protected abstract long _createChildWindow(long paramLong);
/*      */ 
/*      */   protected Window(long parent)
/*      */   {
/*  191 */     this.owner = null;
/*  192 */     this.parent = parent;
/*  193 */     this.styleMask = 0;
/*  194 */     this.isDecorated = false;
/*      */ 
/*  197 */     this.screen = null;
/*      */ 
/*  199 */     this.ptr = _createChildWindow(parent);
/*  200 */     if (this.ptr == 0L) {
/*  201 */       throw new RuntimeException("could not create platform window");
/*      */     }
/*      */ 
/*  204 */     Disposer.addRecord(this, this.disposerRecord = new WindowDisposerRecord(this.ptr));
/*      */   }
/*      */ 
/*      */   private void checkNotClosed() {
/*  208 */     if (this.ptr == 0L)
/*  209 */       throw new IllegalStateException("The window has already been closed");
/*      */   }
/*      */ 
/*      */   protected abstract boolean _close(long paramLong);
/*      */ 
/*      */   public void close() {
/*  215 */     if (this.view != null) {
/*  216 */       _setView(this.ptr, null);
/*  217 */       this.view.setWindow(null);
/*  218 */       this.view.close();
/*  219 */       this.view = null;
/*      */     }
/*  221 */     if (this.ptr != 0L)
/*  222 */       _close(this.ptr);
/*      */   }
/*      */ 
/*      */   private boolean isChild()
/*      */   {
/*  227 */     return this.parent != 0L;
/*      */   }
/*      */ 
/*      */   public long getNativeWindow()
/*      */   {
/*  234 */     return this.delegatePtr != 0L ? this.delegatePtr : this.ptr;
/*      */   }
/*      */ 
/*      */   public long getNativeHandle()
/*      */   {
/*  242 */     return this.delegatePtr != 0L ? this.delegatePtr : this.ptr;
/*      */   }
/*      */ 
/*      */   public Window getOwner() {
/*  246 */     return this.owner;
/*      */   }
/*      */ 
/*      */   public View getView() {
/*  250 */     return this.view;
/*      */   }
/*      */   protected abstract boolean _setView(long paramLong, View paramView);
/*      */ 
/*      */   public void setView(View view) {
/*  255 */     checkNotClosed();
/*  256 */     View oldView = getView();
/*  257 */     if (oldView == view) {
/*  258 */       return;
/*      */     }
/*      */ 
/*  261 */     if (oldView != null) {
/*  262 */       oldView.setWindow(null);
/*      */     }
/*  264 */     if (view != null) {
/*  265 */       Window host = view.getWindow();
/*  266 */       if (host != null) {
/*  267 */         host.setView(null);
/*      */       }
/*      */     }
/*      */ 
/*  271 */     if ((view != null) && (_setView(this.ptr, view))) {
/*  272 */       this.view = view;
/*  273 */       this.view.setWindow(this);
/*  274 */       if (!this.isDecorated)
/*  275 */         this.helper = new UndecoratedMoveResizeHelper();
/*      */     }
/*      */     else {
/*  278 */       _setView(this.ptr, null);
/*  279 */       this.view = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public Screen getScreen() {
/*  284 */     return this.screen;
/*      */   }
/*      */ 
/*      */   public int getStyleMask() {
/*  288 */     return this.styleMask;
/*      */   }
/*      */ 
/*      */   public MenuBar getMenuBar() {
/*  292 */     return this.menubar;
/*      */   }
/*      */   protected abstract boolean _setMenubar(long paramLong1, long paramLong2);
/*      */ 
/*      */   public void setMenuBar(MenuBar menubar) {
/*  297 */     checkNotClosed();
/*  298 */     if (_setMenubar(this.ptr, menubar.getDelegate().getNativeMenu()))
/*  299 */       this.menubar = menubar;
/*      */   }
/*      */ 
/*      */   public boolean isDecorated()
/*      */   {
/*  304 */     return this.isDecorated;
/*      */   }
/*      */ 
/*      */   public boolean isMinimized() {
/*  308 */     return this.state == 2;
/*      */   }
/*      */   protected abstract boolean _minimize(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public boolean minimize(boolean minimize) {
/*  313 */     checkNotClosed();
/*  314 */     _minimize(this.ptr, minimize);
/*      */ 
/*  316 */     return isMinimized();
/*      */   }
/*      */ 
/*      */   public boolean isMaximized() {
/*  320 */     return this.state == 3;
/*      */   }
/*      */   protected abstract boolean _maximize(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
/*      */ 
/*      */   public boolean maximize(boolean maximize) {
/*  325 */     checkNotClosed();
/*  326 */     _maximize(this.ptr, maximize, isMaximized());
/*  327 */     return isMaximized();
/*      */   }
/*      */   protected abstract int _getEmbeddedX(long paramLong);
/*      */ 
/*      */   protected abstract int _getEmbeddedY(long paramLong);
/*      */ 
/*      */   private void checkScreenLocation() {
/*  334 */     this.x = _getEmbeddedX(this.ptr);
/*  335 */     this.y = _getEmbeddedY(this.ptr);
/*  336 */     if ((this.x != this.lastKnownEmbeddedX) || (this.y != this.lastKnownEmbeddedY)) {
/*  337 */       this.lastKnownEmbeddedX = this.x;
/*  338 */       this.lastKnownEmbeddedY = this.y;
/*  339 */       handleWindowEvent(System.nanoTime(), 512);
/*      */     }
/*      */   }
/*      */ 
/*      */   public int getX() {
/*  344 */     return this.x;
/*      */   }
/*      */ 
/*      */   public int getY() {
/*  348 */     return this.y;
/*      */   }
/*      */ 
/*      */   public int getWidth() {
/*  352 */     return this.width;
/*      */   }
/*      */ 
/*      */   public int getHeight() {
/*  356 */     return this.height;
/*      */   }
/*      */ 
/*      */   protected abstract void _setBounds(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2);
/*      */ 
/*      */   public void setBounds(int x, int y, boolean xSet, boolean ySet, int w, int h, int cw, int ch, float xGravity, float yGravity)
/*      */   {
/*  393 */     checkNotClosed();
/*  394 */     _setBounds(this.ptr, x, y, xSet, ySet, w, h, cw, ch, xGravity, yGravity);
/*      */   }
/*      */ 
/*      */   public void setPosition(int x, int y) {
/*  398 */     setBounds(x, y, true, true, 0, 0, 0, 0, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   public void setSize(int w, int h) {
/*  402 */     setBounds(0, 0, false, false, w, h, 0, 0, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   public void setContentSize(int cw, int ch) {
/*  406 */     setBounds(0, 0, false, false, 0, 0, cw, ch, 0.0F, 0.0F);
/*      */   }
/*      */ 
/*      */   public boolean isVisible() {
/*  410 */     return this.isVisible;
/*      */   }
/*      */   protected abstract boolean _setVisible(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public void setVisible(boolean visible) {
/*  415 */     if (this.isVisible != visible)
/*  416 */       if (!visible) {
/*  417 */         if (getView() != null) {
/*  418 */           getView().setVisible(visible);
/*      */         }
/*      */ 
/*  421 */         if (this.ptr != 0L)
/*  422 */           this.isVisible = _setVisible(this.ptr, visible);
/*      */         else {
/*  424 */           this.isVisible = visible;
/*      */         }
/*  426 */         remove(this);
/*  427 */         if (this.parent != 0L)
/*  428 */           this.embeddedLocationTimer.stop();
/*      */       }
/*      */       else {
/*  431 */         checkNotClosed();
/*  432 */         this.isVisible = _setVisible(this.ptr, visible);
/*      */ 
/*  434 */         if (getView() != null) {
/*  435 */           getView().setVisible(this.isVisible);
/*      */         }
/*  437 */         add(this);
/*  438 */         if (this.parent != 0L) {
/*  439 */           final Runnable checkRunnable = new Runnable()
/*      */           {
/*      */             public void run() {
/*  442 */               Window.this.checkScreenLocation();
/*      */             }
/*      */           };
/*  445 */           Runnable timerRunnable = new Runnable()
/*      */           {
/*      */             public void run() {
/*  448 */               Application.invokeLater(checkRunnable);
/*      */             }
/*      */           };
/*  451 */           this.embeddedLocationTimer = Application.GetApplication().createTimer(timerRunnable);
/*      */ 
/*  453 */           this.embeddedLocationTimer.start(16);
/*      */         }
/*      */       }
/*      */   }
/*      */ 
/*      */   protected abstract boolean _setResizable(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public boolean setResizable(boolean resizable) {
/*  461 */     checkNotClosed();
/*  462 */     if ((this.isResizable != resizable) && 
/*  463 */       (_setResizable(this.ptr, resizable))) {
/*  464 */       this.isResizable = resizable;
/*      */     }
/*      */ 
/*  467 */     return this.isResizable;
/*      */   }
/*      */ 
/*      */   public boolean isResizable() {
/*  471 */     return this.isResizable;
/*      */   }
/*      */ 
/*      */   public static Window getFocusedWindow()
/*      */   {
/*  476 */     return focusedWindow;
/*      */   }
/*      */ 
/*      */   private static void setFocusedWindow(Window window) {
/*  480 */     focusedWindow = window;
/*      */   }
/*      */ 
/*      */   public boolean isFocused() {
/*  484 */     return this.isFocused;
/*      */   }
/*      */ 
/*      */   protected abstract boolean _requestFocus(long paramLong, int paramInt);
/*      */ 
/*      */   public boolean requestFocus(int event)
/*      */   {
/*  504 */     checkNotClosed();
/*      */ 
/*  506 */     if ((!isChild()) && (event != 542)) {
/*  507 */       throw new IllegalArgumentException("Invalid focus event ID for top-level window");
/*      */     }
/*      */ 
/*  510 */     if ((isChild()) && ((event < 541) || (event > 544))) {
/*  511 */       throw new IllegalArgumentException("Invalid focus event ID for child window");
/*      */     }
/*      */ 
/*  514 */     if ((event == 541) && (!isFocused()))
/*      */     {
/*  516 */       return true;
/*      */     }
/*      */ 
/*  521 */     if (!this.isFocusable)
/*      */     {
/*  523 */       return false;
/*      */     }
/*      */ 
/*  526 */     return _requestFocus(this.ptr, event);
/*      */   }
/*      */ 
/*      */   public boolean requestFocus() {
/*  530 */     return requestFocus(542);
/*      */   }
/*      */ 
/*      */   protected abstract void _setFocusable(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public void setFocusable(boolean isFocusable)
/*      */   {
/*  540 */     checkNotClosed();
/*  541 */     this.isFocusable = isFocusable;
/*  542 */     if (isEnabled())
/*  543 */       _setFocusable(this.ptr, isFocusable);
/*      */   }
/*      */ 
/*      */   protected abstract boolean _grabFocus(long paramLong);
/*      */ 
/*      */   protected abstract void _ungrabFocus(long paramLong);
/*      */ 
/*      */   public boolean grabFocus()
/*      */   {
/*  593 */     checkNotClosed();
/*      */ 
/*  595 */     if (!isFocused()) {
/*  596 */       throw new IllegalStateException("The window must be focused when calling grabFocus()");
/*      */     }
/*      */ 
/*  599 */     return _grabFocus(this.ptr);
/*      */   }
/*      */ 
/*      */   public void ungrabFocus()
/*      */   {
/*  613 */     checkNotClosed();
/*  614 */     _ungrabFocus(this.ptr);
/*      */   }
/*      */ 
/*      */   public String getTitle() {
/*  618 */     return this.title;
/*      */   }
/*      */   protected abstract boolean _setTitle(long paramLong, String paramString);
/*      */ 
/*      */   public void setTitle(String title) {
/*  623 */     checkNotClosed();
/*  624 */     if (title == null) {
/*  625 */       title = "";
/*      */     }
/*  627 */     if ((!title.equals(this.title)) && 
/*  628 */       (_setTitle(this.ptr, title)))
/*  629 */       this.title = title;
/*      */   }
/*      */ 
/*      */   protected abstract void _setLevel(long paramLong, int paramInt);
/*      */ 
/*      */   public void setLevel(int level)
/*      */   {
/*  642 */     checkNotClosed();
/*  643 */     if ((level < 1) || (level > 3)) {
/*  644 */       throw new IllegalArgumentException("Level should be in the range [1..3]");
/*      */     }
/*      */ 
/*  647 */     _setLevel(this.ptr, level);
/*      */   }
/*      */ 
/*      */   public int getLevel() {
/*  651 */     return this.level;
/*      */   }
/*      */   protected abstract void _setAlpha(long paramLong, float paramFloat);
/*      */ 
/*      */   public void setAlpha(float alpha) {
/*  656 */     checkNotClosed();
/*  657 */     if ((alpha < 0.0F) || (alpha > 1.0F)) {
/*  658 */       throw new IllegalArgumentException("Alpha should be in the range [0f..1f]");
/*      */     }
/*      */ 
/*  661 */     this.alpha = alpha;
/*  662 */     _setAlpha(this.ptr, this.alpha);
/*      */   }
/*      */ 
/*      */   public float getAlpha() {
/*  666 */     return this.alpha;
/*      */   }
/*      */ 
/*      */   protected abstract boolean _setBackground(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3);
/*      */ 
/*      */   public boolean setBackground(float r, float g, float b)
/*      */   {
/*  683 */     checkNotClosed();
/*  684 */     return _setBackground(this.ptr, r, g, b);
/*      */   }
/*      */ 
/*      */   public boolean isEnabled() {
/*  688 */     return this.disableCount == 0;
/*      */   }
/*      */ 
/*      */   protected abstract void _setEnabled(long paramLong, boolean paramBoolean);
/*      */ 
/*      */   public void setEnabled(boolean enabled)
/*      */   {
/*  719 */     checkNotClosed();
/*  720 */     if (!enabled)
/*      */     {
/*  721 */       if (++this.disableCount <= 1);
/*      */     }
/*      */     else
/*      */     {
/*  726 */       if (this.disableCount == 0)
/*      */       {
/*  728 */         return;
/*      */       }
/*  730 */       if (--this.disableCount > 0)
/*      */       {
/*  732 */         return;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  737 */     _setEnabled(this.ptr, isEnabled());
/*      */   }
/*      */ 
/*      */   public int getMinimumWidth() {
/*  741 */     return this.minimumWidth;
/*      */   }
/*      */ 
/*      */   public int getMinimumHeight() {
/*  745 */     return this.minimumHeight;
/*      */   }
/*      */ 
/*      */   public int getMaximumWidth() {
/*  749 */     return this.maximumWidth;
/*      */   }
/*      */ 
/*      */   public int getMaximumHeight() {
/*  753 */     return this.maximumHeight;
/*      */   }
/*      */ 
/*      */   protected abstract boolean _setMinimumSize(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public void setMinimumSize(int width, int height)
/*      */   {
/*  766 */     if ((width < 0) || (height < 0)) {
/*  767 */       throw new IllegalArgumentException("The width and height must be >= 0. Got: width=" + width + "; height=" + height);
/*      */     }
/*  769 */     checkNotClosed();
/*  770 */     if (_setMinimumSize(this.ptr, width, height)) {
/*  771 */       this.minimumWidth = width;
/*  772 */       this.minimumHeight = height;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract boolean _setMaximumSize(long paramLong, int paramInt1, int paramInt2);
/*      */ 
/*      */   public void setMaximumSize(int width, int height)
/*      */   {
/*  786 */     if ((width < 0) || (height < 0)) {
/*  787 */       throw new IllegalArgumentException("The width and height must be >= 0. Got: width=" + width + "; height=" + height);
/*      */     }
/*  789 */     checkNotClosed();
/*  790 */     if (_setMaximumSize(this.ptr, width == 2147483647 ? -1 : width, height == 2147483647 ? -1 : height))
/*      */     {
/*  795 */       this.maximumWidth = width;
/*  796 */       this.maximumHeight = height;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract void _setIcon(long paramLong, Pixels paramPixels);
/*      */ 
/*      */   public void setIcon(Pixels pixels)
/*      */   {
/*  806 */     checkNotClosed();
/*  807 */     _setIcon(this.ptr, pixels);
/*      */   }
/*      */ 
/*      */   protected abstract void _setCursor(long paramLong, Cursor paramCursor);
/*      */ 
/*      */   public void setCursor(Cursor cursor)
/*      */   {
/*  819 */     _setCursor(this.ptr, cursor);
/*      */   }
/*      */ 
/*      */   protected abstract void _toFront(long paramLong);
/*      */ 
/*      */   public void toFront()
/*      */   {
/*  829 */     checkNotClosed();
/*  830 */     _toFront(this.ptr);
/*      */   }
/*      */ 
/*      */   protected abstract void _toBack(long paramLong);
/*      */ 
/*      */   public void toBack()
/*      */   {
/*  841 */     checkNotClosed();
/*  842 */     _toBack(this.ptr);
/*      */   }
/*      */ 
/*      */   public boolean supportsPlatformModality() {
/*  846 */     return false;
/*      */   }
/*      */ 
/*      */   protected abstract void _enterModal(long paramLong);
/*      */ 
/*      */   public void enterModal()
/*      */   {
/*  857 */     checkNotClosed();
/*  858 */     if (!this.isModal) {
/*  859 */       this.isModal = true;
/*  860 */       _enterModal(this.ptr);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract void _enterModalWithWindow(long paramLong1, long paramLong2);
/*      */ 
/*      */   public void enterModal(Window window)
/*      */   {
/*  870 */     checkNotClosed();
/*  871 */     if (!this.isModal) {
/*  872 */       this.isModal = true;
/*  873 */       _enterModalWithWindow(this.ptr, window.getNativeHandle());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract void _exitModal(long paramLong);
/*      */ 
/*  879 */   public void exitModal() { checkNotClosed();
/*  880 */     if (this.isModal == true) {
/*  881 */       _exitModal(this.ptr);
/*  882 */       this.isModal = false;
/*      */     } }
/*      */ 
/*      */   public boolean isModal()
/*      */   {
/*  887 */     return this.isModal;
/*      */   }
/*      */ 
/*      */   public void dispatchNpapiEvent(Map eventInfo)
/*      */   {
/*  892 */     throw new RuntimeException("This operation is not supported on this platform");
/*      */   }
/*      */ 
/*      */   public EventHandler getEventHandler() {
/*  896 */     return this.eventHandler;
/*      */   }
/*      */ 
/*      */   public void setEventHandler(EventHandler eventHandler) {
/*  900 */     this.eventHandler = eventHandler;
/*      */   }
/*      */ 
/*      */   public void setShouldStartUndecoratedMove(boolean v)
/*      */   {
/*  908 */     this.shouldStartUndecoratedMove = v;
/*      */   }
/*      */ 
/*      */   protected void notifyClose()
/*      */   {
/*  915 */     handleWindowEvent(System.nanoTime(), 521);
/*      */   }
/*      */ 
/*      */   protected void notifyDestroy()
/*      */   {
/*  920 */     if (this.ptr == 0L) {
/*  921 */       return;
/*      */     }
/*      */ 
/*  924 */     handleWindowEvent(System.nanoTime(), 522);
/*      */ 
/*  926 */     this.ptr = 0L;
/*  927 */     this.disposerRecord.ptr = 0L;
/*      */ 
/*  930 */     setVisible(false);
/*      */   }
/*      */ 
/*      */   protected void notifyMove(int x, int y) {
/*  934 */     this.x = x;
/*  935 */     this.y = y;
/*  936 */     handleWindowEvent(System.nanoTime(), 512);
/*      */   }
/*      */ 
/*      */   protected void notifyMoveToAnotherScreen(long fromScreenPtr, long toScreenPtr)
/*      */   {
/*  941 */     this.screen = Screen.getScreenForPtr(toScreenPtr);
/*      */   }
/*      */ 
/*      */   protected void notifyResize(int type, int width, int height)
/*      */   {
/*  952 */     if (type == 531) {
/*  953 */       this.state = 2;
/*      */     } else {
/*  955 */       if (type == 532)
/*  956 */         this.state = 3;
/*      */       else {
/*  958 */         this.state = 1;
/*      */       }
/*  960 */       this.width = width;
/*  961 */       this.height = height;
/*      */ 
/*  964 */       if (this.helper != null) {
/*  965 */         this.helper.updateRectangles();
/*      */       }
/*      */     }
/*  968 */     handleWindowEvent(System.nanoTime(), type);
/*      */ 
/*  973 */     if ((type == 532) || (type == 533))
/*  974 */       handleWindowEvent(System.nanoTime(), 511);
/*      */   }
/*      */ 
/*      */   protected void notifyFocus(int event)
/*      */   {
/*  979 */     boolean focused = event != 541;
/*      */ 
/*  981 */     if (this.isFocused != focused) {
/*  982 */       this.isFocused = focused;
/*  983 */       if (this.isFocused)
/*  984 */         setFocusedWindow(this);
/*      */       else {
/*  986 */         setFocusedWindow(null);
/*      */       }
/*  988 */       handleWindowEvent(System.nanoTime(), event);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void notifyFocusDisabled() {
/*  993 */     handleWindowEvent(System.nanoTime(), 545);
/*      */   }
/*      */ 
/*      */   protected void notifyFocusUngrab() {
/*  997 */     handleWindowEvent(System.nanoTime(), 546);
/*      */   }
/*      */ 
/*      */   protected void notifyDelegatePtr(long ptr) {
/* 1001 */     this.delegatePtr = ptr;
/*      */   }
/*      */ 
/*      */   private void handleWindowEvent(long time, int type)
/*      */   {
/* 1008 */     if (this.eventHandler != null)
/* 1009 */       this.eventHandler.handleWindowEvent(this, time, type);
/*      */   }
/*      */ 
/*      */   public void setUndecoratedMoveRectangle(int size)
/*      */   {
/* 1023 */     if (this.isDecorated == true)
/*      */     {
/* 1025 */       System.err.println("Glass Window.setUndecoratedMoveRectangle is only valid for Undecorated Window. In the future this will be hard error.");
/* 1026 */       Thread.dumpStack();
/* 1027 */       return;
/*      */     }
/*      */ 
/* 1030 */     if (this.helper != null)
/* 1031 */       this.helper.setMoveRectangle(size);
/*      */   }
/*      */ 
/*      */   public boolean shouldStartUndecoratedMove(int x, int y)
/*      */   {
/* 1040 */     if (this.shouldStartUndecoratedMove == true) {
/* 1041 */       return true;
/*      */     }
/* 1043 */     if (this.isDecorated == true) {
/* 1044 */       return false;
/*      */     }
/*      */ 
/* 1047 */     if (this.helper != null) {
/* 1048 */       return this.helper.shouldStartMove(x, y);
/*      */     }
/* 1050 */     return false;
/*      */   }
/*      */ 
/*      */   public void setUndecoratedResizeRectangle(int size)
/*      */   {
/* 1061 */     if ((this.isDecorated == true) || (!this.isResizable))
/*      */     {
/* 1063 */       System.err.println("Glass Window.setUndecoratedResizeRectangle is only valid for Undecorated Resizable Window. In the future this will be hard error.");
/* 1064 */       Thread.dumpStack();
/* 1065 */       return;
/*      */     }
/*      */ 
/* 1068 */     if (this.helper != null)
/* 1069 */       this.helper.setResizeRectangle(size);
/*      */   }
/*      */ 
/*      */   public boolean shouldStartUndecoratedResize(int x, int y)
/*      */   {
/* 1079 */     if ((this.isDecorated == true) || (!this.isResizable)) {
/* 1080 */       return false;
/*      */     }
/*      */ 
/* 1083 */     if (this.helper != null) {
/* 1084 */       return this.helper.shouldStartResize(x, y);
/*      */     }
/* 1086 */     return false;
/*      */   }
/*      */ 
/*      */   boolean handleMouseEvent(int type, int button, int x, int y, int xAbs, int yAbs)
/*      */   {
/* 1099 */     if (!this.isDecorated) {
/* 1100 */       return this.helper.handleMouseEvent(type, button, x, y, xAbs, yAbs);
/*      */     }
/* 1102 */     return false;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1107 */     return "Window:\n    ptr: " + getNativeWindow() + "\n" + "    screen ptr: " + (this.screen != null ? Long.valueOf(this.screen.getNativeScreen()) : "null") + "\n" + "    isDecorated: " + isDecorated() + "\n" + "    title: " + getTitle() + "\n" + "    visible: " + isVisible() + "\n" + "    focused: " + isFocused() + "\n" + "    modal: " + isModal() + "\n" + "    state: " + this.state + "\n" + "    x: " + getX() + ", y: " + getY() + ", w: " + getWidth() + ", h: " + getHeight() + "\n" + "";
/*      */   }
/*      */ 
/*      */   private class UndecoratedMoveResizeHelper
/*      */   {
/* 1149 */     Window.TrackingRectangle moveRect = null;
/* 1150 */     Window.TrackingRectangle resizeRect = null;
/*      */ 
/* 1152 */     boolean inMove = false;
/* 1153 */     boolean inResize = false;
/*      */     int startMouseX;
/*      */     int startMouseY;
/*      */     int startX;
/*      */     int startY;
/*      */     int startWidth;
/*      */     int startHeight;
/*      */ 
/*      */     UndecoratedMoveResizeHelper()
/*      */     {
/* 1160 */       this.moveRect = new Window.TrackingRectangle(null);
/* 1161 */       this.resizeRect = new Window.TrackingRectangle(null);
/*      */     }
/*      */ 
/*      */     void setMoveRectangle(int size) {
/* 1165 */       this.moveRect.size = size;
/*      */ 
/* 1167 */       this.moveRect.x = 0;
/* 1168 */       this.moveRect.y = 0;
/* 1169 */       this.moveRect.width = Window.this.getWidth();
/* 1170 */       this.moveRect.height = this.moveRect.size;
/*      */     }
/*      */ 
/*      */     boolean shouldStartMove(int x, int y) {
/* 1174 */       return this.moveRect.contains(x, y);
/*      */     }
/*      */ 
/*      */     boolean inMove() {
/* 1178 */       return this.inMove;
/*      */     }
/*      */ 
/*      */     void startMove(int x, int y) {
/* 1182 */       this.inMove = true;
/*      */ 
/* 1184 */       this.startMouseX = x;
/* 1185 */       this.startMouseY = y;
/*      */ 
/* 1187 */       this.startX = Window.this.getX();
/* 1188 */       this.startY = Window.this.getY();
/*      */     }
/*      */ 
/*      */     void deltaMove(int x, int y) {
/* 1192 */       int deltaX = x - this.startMouseX;
/* 1193 */       int deltaY = y - this.startMouseY;
/*      */ 
/* 1195 */       Window.this.setPosition(this.startX + deltaX, this.startY + deltaY);
/*      */     }
/*      */ 
/*      */     void stopMove() {
/* 1199 */       this.inMove = false;
/*      */     }
/*      */ 
/*      */     void setResizeRectangle(int size) {
/* 1203 */       this.resizeRect.size = size;
/*      */ 
/* 1206 */       this.resizeRect.x = (Window.this.getWidth() - this.resizeRect.size);
/* 1207 */       this.resizeRect.y = (Window.this.getHeight() - this.resizeRect.size);
/* 1208 */       this.resizeRect.width = this.resizeRect.size;
/* 1209 */       this.resizeRect.height = this.resizeRect.size;
/*      */     }
/*      */ 
/*      */     boolean shouldStartResize(int x, int y) {
/* 1213 */       return this.resizeRect.contains(x, y);
/*      */     }
/*      */ 
/*      */     boolean inResize() {
/* 1217 */       return this.inResize;
/*      */     }
/*      */ 
/*      */     void startResize(int x, int y) {
/* 1221 */       this.inResize = true;
/*      */ 
/* 1223 */       this.startMouseX = x;
/* 1224 */       this.startMouseY = y;
/*      */ 
/* 1226 */       this.startWidth = Window.this.getWidth();
/* 1227 */       this.startHeight = Window.this.getHeight();
/*      */     }
/*      */ 
/*      */     void deltaResize(int x, int y) {
/* 1231 */       int deltaX = x - this.startMouseX;
/* 1232 */       int deltaY = y - this.startMouseY;
/*      */ 
/* 1234 */       Window.this.setSize(this.startWidth + deltaX, this.startHeight + deltaY);
/*      */     }
/*      */ 
/*      */     protected void stopResize() {
/* 1238 */       this.inResize = false;
/*      */     }
/*      */ 
/*      */     void updateRectangles() {
/* 1242 */       if (this.moveRect.size > 0) {
/* 1243 */         setMoveRectangle(this.moveRect.size);
/*      */       }
/* 1245 */       if (this.resizeRect.size > 0)
/* 1246 */         setResizeRectangle(this.resizeRect.size);
/*      */     }
/*      */ 
/*      */     boolean handleMouseEvent(int type, int button, int x, int y, int xAbs, int yAbs)
/*      */     {
/* 1251 */       switch (type) {
/*      */       case 221:
/* 1253 */         if (button == 212) {
/* 1254 */           if (Window.this.shouldStartUndecoratedMove(x, y) == true) {
/* 1255 */             startMove(xAbs, yAbs);
/* 1256 */             return true;
/* 1257 */           }if (Window.this.shouldStartUndecoratedResize(x, y) == true) {
/* 1258 */             startResize(xAbs, yAbs);
/* 1259 */             return true;
/*      */           }
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 223:
/*      */       case 224:
/* 1266 */         if (inMove() == true) {
/* 1267 */           deltaMove(xAbs, yAbs);
/* 1268 */           return true;
/* 1269 */         }if (inResize() == true) {
/* 1270 */           deltaResize(xAbs, yAbs);
/* 1271 */           return true;
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 222:
/* 1276 */         boolean wasProcessed = (inMove()) || (inResize());
/* 1277 */         stopResize();
/* 1278 */         stopMove();
/* 1279 */         return wasProcessed;
/*      */       }
/* 1281 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TrackingRectangle
/*      */   {
/* 1139 */     int size = 0;
/* 1140 */     int x = 0; int y = 0; int width = 0; int height = 0;
/*      */ 
/* 1142 */     boolean contains(int x, int y) { return (this.size > 0) && (x >= this.x) && (x < this.x + this.width) && (y >= this.y) && (y < this.y + this.height); }
/*      */ 
/*      */   }
/*      */ 
/*      */   private static class WindowDisposerRecord
/*      */     implements DisposerRecord
/*      */   {
/*      */     volatile long ptr;
/*      */ 
/*      */     WindowDisposerRecord(long ptr)
/*      */     {
/* 1125 */       this.ptr = ptr;
/*      */     }
/*      */ 
/*      */     public void dispose() {
/* 1129 */       if (this.ptr != 0L);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static final class Level
/*      */   {
/*      */     private static final int _MIN = 1;
/*      */     public static final int NORMAL = 1;
/*      */     public static final int FLOATING = 2;
/*      */     public static final int TOPMOST = 3;
/*      */     private static final int _MAX = 3;
/*      */   }
/*      */ 
/*      */   private static final class State
/*      */   {
/*      */     private static final int NORMAL = 1;
/*      */     private static final int MINIMIZED = 2;
/*      */     private static final int MAXIMIZED = 3;
/*      */   }
/*      */ 
/*      */   public static class EventHandler
/*      */   {
/*      */     public void handleWindowEvent(Window window, long time, int type)
/*      */     {
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Window
 * JD-Core Version:    0.6.2
 */