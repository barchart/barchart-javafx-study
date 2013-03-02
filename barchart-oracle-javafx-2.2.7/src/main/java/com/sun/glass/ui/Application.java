/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import com.sun.glass.utils.Disposer;
/*     */ import com.sun.glass.utils.NativeLibLoader;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public abstract class Application extends ApplicationBase
/*     */ {
/*     */   private EventHandler eventHandler;
/*  62 */   private boolean initialActiveEventReceived = false;
/*  63 */   private String[] initialOpenedFiles = null;
/*     */ 
/*  65 */   private static boolean loaded = false;
/*     */   private static Application application;
/*     */   private Thread eventThread;
/*  83 */   private static Map deviceDetails = null;
/*     */ 
/* 246 */   private boolean terminateWhenLastWindowClosed = true;
/*     */ 
/* 359 */   private static int nestedEventLoopCounter = 0;
/*     */ 
/*     */   public static synchronized void loadNativeLibrary(String libname)
/*     */   {
/*  72 */     if (!loaded) {
/*  73 */       NativeLibLoader.loadLibrary(libname);
/*  74 */       loaded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static synchronized void loadNativeLibrary()
/*     */   {
/*  80 */     loadNativeLibrary("glass");
/*     */   }
/*     */ 
/*     */   public static void setDeviceDetails(Map details)
/*     */   {
/*  89 */     deviceDetails = details;
/*     */   }
/*     */ 
/*     */   protected static Map getDeviceDetails() {
/*  93 */     return deviceDetails;
/*     */   }
/*     */ 
/*     */   public static void Run(String[] args, String title, Launchable launchable) {
/*  97 */     System.err.println("com.sun.glass.ui.Application.Run(String args[], String title, final Launchable launchable) is deprecated and will go away soon");
/*  98 */     Run(args, launchable);
/*     */   }
/*     */ 
/*     */   public static void Run(String[] args, Launchable launchable) {
/* 102 */     Disposer.init();
/* 103 */     application = PlatformFactory.getPlatformFactory().createApplication();
/* 104 */     application.platform = Platform.DeterminePlatform();
/* 105 */     application.args = args;
/*     */ 
/* 111 */     application.name = "java";
/*     */ 
/* 113 */     application.launchable = launchable;
/* 114 */     application.run();
/*     */   }
/*     */ 
/*     */   private void run() {
/*     */     try {
/* 119 */       runLoop(this.args, this.launchable);
/*     */     } catch (Throwable t) {
/* 121 */       t.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void runLoop(String[] paramArrayOfString, Launchable paramLaunchable);
/*     */ 
/*     */   protected void finishTerminating()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void notifyWillFinishLaunching()
/*     */   {
/* 134 */     EventHandler handler = getEventHandler();
/* 135 */     if (handler != null)
/* 136 */       handler.handleWillFinishLaunchingAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyDidFinishLaunching()
/*     */   {
/* 141 */     EventHandler handler = getEventHandler();
/* 142 */     if (handler != null)
/* 143 */       handler.handleDidFinishLaunchingAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyWillBecomeActive()
/*     */   {
/* 148 */     EventHandler handler = getEventHandler();
/* 149 */     if (handler != null)
/* 150 */       handler.handleWillBecomeActiveAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyDidBecomeActive()
/*     */   {
/* 155 */     this.initialActiveEventReceived = true;
/* 156 */     EventHandler handler = getEventHandler();
/* 157 */     if (handler != null)
/* 158 */       handler.handleDidBecomeActiveAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyWillResignActive()
/*     */   {
/* 163 */     EventHandler handler = getEventHandler();
/* 164 */     if (handler != null)
/* 165 */       handler.handleWillResignActiveAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyDidResignActive()
/*     */   {
/* 170 */     EventHandler handler = getEventHandler();
/* 171 */     if (handler != null)
/* 172 */       handler.handleDidResignActiveAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyWillHide()
/*     */   {
/* 177 */     EventHandler handler = getEventHandler();
/* 178 */     if (handler != null)
/* 179 */       handler.handleWillHideAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyDidHide()
/*     */   {
/* 184 */     EventHandler handler = getEventHandler();
/* 185 */     if (handler != null)
/* 186 */       handler.handleDidHideAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyWillUnhide()
/*     */   {
/* 191 */     EventHandler handler = getEventHandler();
/* 192 */     if (handler != null)
/* 193 */       handler.handleWillUnhideAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyDidUnhide()
/*     */   {
/* 198 */     EventHandler handler = getEventHandler();
/* 199 */     if (handler != null)
/* 200 */       handler.handleDidUnhideAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   private void notifyOpenFiles(String[] files)
/*     */   {
/* 206 */     if ((!this.initialActiveEventReceived) && (this.initialOpenedFiles == null))
/*     */     {
/* 208 */       this.initialOpenedFiles = files;
/*     */     }
/* 210 */     EventHandler handler = getEventHandler();
/* 211 */     if ((handler != null) && (files != null))
/* 212 */       handler.handleOpenFilesAction(this, System.nanoTime(), files);
/*     */   }
/*     */ 
/*     */   private void notifyWillQuit()
/*     */   {
/* 217 */     EventHandler handler = getEventHandler();
/* 218 */     if (handler != null)
/* 219 */       handler.handleQuitAction(this, System.nanoTime());
/*     */   }
/*     */ 
/*     */   public void installDefaultMenus(MenuBar menubar)
/*     */   {
/*     */   }
/*     */ 
/*     */   public EventHandler getEventHandler()
/*     */   {
/* 234 */     return this.eventHandler;
/*     */   }
/*     */ 
/*     */   public void setEventHandler(EventHandler eventHandler) {
/* 238 */     boolean resendOpenFiles = (this.eventHandler != null) && (this.initialOpenedFiles != null);
/* 239 */     this.eventHandler = eventHandler;
/* 240 */     if (resendOpenFiles == true)
/*     */     {
/* 242 */       notifyOpenFiles(this.initialOpenedFiles);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean shouldTerminateWhenLastWindowClosed()
/*     */   {
/* 248 */     return this.terminateWhenLastWindowClosed;
/*     */   }
/*     */   public final void setTerminateWhenLastWindowClosed(boolean b) {
/* 251 */     this.terminateWhenLastWindowClosed = b;
/*     */   }
/*     */ 
/*     */   public boolean shouldUpdateWindow() {
/* 255 */     return false;
/*     */   }
/*     */ 
/*     */   public void terminate() {
/*     */     try {
/* 260 */       synchronized (this) {
/* 261 */         Vector windows = new Vector(Window.getWindows());
/* 262 */         for (Window window : windows)
/*     */         {
/* 266 */           window.setVisible(false);
/*     */         }
/* 268 */         for (Window window : windows)
/*     */         {
/* 272 */           window.close();
/*     */         }
/*     */       }
/*     */     } catch (Throwable t) {
/* 276 */       t.printStackTrace();
/*     */     } finally {
/* 278 */       finishTerminating();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Application GetApplication() {
/* 283 */     return application;
/*     */   }
/*     */ 
/*     */   protected final void setEventThread(Thread thread) {
/* 287 */     this.eventThread = thread;
/*     */   }
/*     */ 
/*     */   protected final Thread getEventThread() {
/* 291 */     return this.eventThread;
/*     */   }
/*     */ 
/*     */   public static boolean checkEventThread(boolean throwException, String msg) {
/* 295 */     if (Thread.currentThread().equals(GetApplication().getEventThread())) {
/* 296 */       return true;
/*     */     }
/* 298 */     if (throwException) {
/* 299 */       throw new RuntimeException(msg != null ? msg : "This operation is permitted on the event thread only");
/*     */     }
/*     */ 
/* 302 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean checkEventThread(boolean throwException) {
/* 306 */     return checkEventThread(throwException, null);
/*     */   }
/*     */ 
/*     */   public static void checkEventThread() {
/* 310 */     checkEventThread(true);
/*     */   }
/*     */ 
/*     */   protected abstract void _invokeAndWait(Runnable paramRunnable);
/*     */ 
/*     */   public static void invokeAndWait(Runnable runnable)
/*     */   {
/* 319 */     if (runnable == null) {
/* 320 */       return;
/*     */     }
/* 322 */     if (checkEventThread(false, ""))
/* 323 */       runnable.run();
/*     */     else
/* 325 */       GetApplication()._invokeAndWait(runnable);
/*     */   }
/*     */ 
/*     */   protected abstract void _invokeLater(Runnable paramRunnable);
/*     */ 
/*     */   public static void invokeLater(Runnable runnable)
/*     */   {
/* 335 */     if (runnable == null) {
/* 336 */       return;
/*     */     }
/* 338 */     GetApplication()._invokeLater(runnable);
/*     */   }
/*     */ 
/*     */   protected abstract void _postOnEventQueue(Runnable paramRunnable);
/*     */ 
/*     */   public static void postOnEventQueue(Runnable runnable)
/*     */   {
/* 350 */     if (runnable == null) {
/* 351 */       return;
/*     */     }
/* 353 */     GetApplication()._postOnEventQueue(runnable);
/*     */   }
/*     */ 
/*     */   protected abstract Object _enterNestedEventLoop();
/*     */ 
/*     */   protected abstract void _leaveNestedEventLoop(Object paramObject);
/*     */ 
/*     */   public static Object enterNestedEventLoop()
/*     */   {
/* 379 */     checkEventThread();
/*     */ 
/* 381 */     nestedEventLoopCounter += 1;
/*     */     try {
/* 383 */       return GetApplication()._enterNestedEventLoop();
/*     */     } finally {
/* 385 */       nestedEventLoopCounter -= 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void leaveNestedEventLoop(Object retValue)
/*     */   {
/* 406 */     checkEventThread();
/*     */ 
/* 408 */     if (nestedEventLoopCounter == 0) {
/* 409 */       throw new IllegalStateException("Not in a nested event loop");
/*     */     }
/*     */ 
/* 412 */     GetApplication()._leaveNestedEventLoop(retValue);
/*     */   }
/*     */ 
/*     */   public void menuAboutAction() {
/* 416 */     System.err.println("about");
/*     */   }
/*     */ 
/*     */   public abstract Window createWindow(Window paramWindow, Screen paramScreen, int paramInt);
/*     */ 
/*     */   public Window createWindow(Screen screen, int styleMask)
/*     */   {
/* 445 */     return createWindow(null, screen, styleMask);
/*     */   }
/*     */ 
/*     */   public abstract Window createWindow(long paramLong);
/*     */ 
/*     */   public abstract View createView(Pen paramPen);
/*     */ 
/*     */   protected Clipboard createClipboard(String name) {
/* 453 */     return Clipboard.get(name);
/*     */   }
/*     */   public abstract Cursor createCursor(int paramInt);
/*     */ 
/*     */   public abstract Cursor createCursor(int paramInt1, int paramInt2, Pixels paramPixels);
/*     */ 
/*     */   protected abstract void staticCursor_setVisible(boolean paramBoolean);
/*     */ 
/*     */   protected abstract Size staticCursor_getBestSize(int paramInt1, int paramInt2);
/*     */ 
/* 463 */   public Cursor createCursor(Pixels pixels) { return createCursor(pixels.getWidth() / 2, pixels.getHeight() / 2, pixels); }
/*     */ 
/*     */ 
/*     */   public Menu createMenu(String title)
/*     */   {
/* 469 */     return new Menu(title);
/*     */   }
/*     */ 
/*     */   public Menu createMenu(String title, boolean enabled) {
/* 473 */     return new Menu(title, enabled);
/*     */   }
/*     */ 
/*     */   public MenuBar createMenuBar() {
/* 477 */     return new MenuBar();
/*     */   }
/*     */ 
/*     */   public MenuItem createMenuItem(String title) {
/* 481 */     return createMenuItem(title, null);
/*     */   }
/*     */ 
/*     */   public MenuItem createMenuItem(String title, MenuItem.Callback callback) {
/* 485 */     return createMenuItem(title, callback, 0, 0);
/*     */   }
/*     */ 
/*     */   public MenuItem createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers)
/*     */   {
/* 490 */     return createMenuItem(title, callback, shortcutKey, shortcutModifiers, null);
/*     */   }
/*     */ 
/*     */   public MenuItem createMenuItem(String title, MenuItem.Callback callback, int shortcutKey, int shortcutModifiers, Pixels pixels)
/*     */   {
/* 495 */     return new MenuItem(title, callback, shortcutKey, shortcutModifiers, pixels); } 
/*     */   public abstract Pixels createPixels(int paramInt1, int paramInt2, ByteBuffer paramByteBuffer);
/*     */ 
/*     */   public abstract Pixels createPixels(int paramInt1, int paramInt2, IntBuffer paramIntBuffer);
/*     */ 
/*     */   protected abstract int staticPixels_getNativeFormat();
/*     */ 
/*     */   public abstract Robot createRobot();
/*     */ 
/*     */   protected abstract Screen staticScreen_getDeepestScreen();
/*     */ 
/*     */   protected abstract Screen staticScreen_getMainScreen();
/*     */ 
/*     */   protected abstract Screen staticScreen_getScreenForLocation(int paramInt1, int paramInt2);
/*     */ 
/*     */   protected abstract Screen staticScreen_getScreenForPtr(long paramLong);
/*     */ 
/*     */   protected abstract List<Screen> staticScreen_getScreens();
/*     */ 
/*     */   public abstract Timer createTimer(Runnable paramRunnable);
/*     */ 
/*     */   protected abstract int staticTimer_getMinPeriod();
/*     */ 
/*     */   protected abstract int staticTimer_getMaxPeriod();
/*     */ 
/* 516 */   public final EventLoop createEventLoop() { return new EventLoop(); }
/*     */ 
/*     */ 
/*     */   protected abstract String[] staticCommonDialogs_showFileChooser(Window paramWindow, String paramString1, String paramString2, int paramInt, boolean paramBoolean, CommonDialogs.ExtensionFilter[] paramArrayOfExtensionFilter);
/*     */ 
/*     */   protected abstract String staticCommonDialogs_showFolderChooser(Window paramWindow, String paramString1, String paramString2);
/*     */ 
/*     */   protected abstract long staticView_getMultiClickTime();
/*     */ 
/*     */   protected abstract int staticView_getMultiClickMaxX();
/*     */ 
/*     */   protected abstract int staticView_getMultiClickMaxY();
/*     */ 
/*     */   protected void staticView_notifyRenderingEnd()
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract boolean supportsTransparentWindows();
/*     */ 
/*     */   public static class EventHandler
/*     */   {
/*     */     public void handleWillFinishLaunchingAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleDidFinishLaunchingAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleWillBecomeActiveAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleDidBecomeActiveAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleWillResignActiveAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleDidResignActiveAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleWillHideAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleDidHideAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleWillUnhideAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleDidUnhideAction(Application app, long time)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleOpenFilesAction(Application app, long time, String[] files)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void handleQuitAction(Application app, long time)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Application
 * JD-Core Version:    0.6.2
 */