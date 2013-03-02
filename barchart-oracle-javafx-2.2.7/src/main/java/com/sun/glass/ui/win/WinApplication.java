/*     */ package com.sun.glass.ui.win;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.Launchable;
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.Robot;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.Size;
/*     */ import com.sun.glass.ui.Timer;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ 
/*     */ public class WinApplication extends Application
/*     */ {
/*     */   private static native void initIDs();
/*     */ 
/*     */   private native long _init();
/*     */ 
/*     */   private native void _setClassLoader(ClassLoader paramClassLoader);
/*     */ 
/*     */   private native void _runLoop(String[] paramArrayOfString, Launchable paramLaunchable);
/*     */ 
/*     */   private native void _terminateLoop();
/*     */ 
/*     */   protected void runLoop(final String[] args, final Launchable launchable)
/*     */   {
/*  50 */     boolean isEventThread = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Boolean run() {
/*  53 */         Boolean result = Boolean.valueOf(Boolean.getBoolean("javafx.embed.isEventThread"));
/*  54 */         return Boolean.valueOf(result == null ? false : result.booleanValue());
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/*  58 */     ClassLoader classLoader = WinApplication.class.getClassLoader();
/*  59 */     _setClassLoader(classLoader);
/*     */ 
/*  61 */     if (isEventThread) {
/*  62 */       _init();
/*  63 */       setEventThread(Thread.currentThread());
/*  64 */       launchable.finishLaunching(args);
/*  65 */       return;
/*     */     }
/*  67 */     Thread toolkitThread = (Thread)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Thread run() {
/*  70 */         Thread th = new Thread(new Runnable() {
/*     */           public void run() {
/*  72 */             WinApplication.this._init();
/*  73 */             WinApplication.this._runLoop(WinApplication.3.this.val$args, WinApplication.3.this.val$launchable);
/*     */           }
/*     */         }
/*     */         , "WindowsNativeRunloopThread");
/*     */ 
/*  76 */         return th;
/*     */       }
/*     */     });
/*  79 */     setEventThread(toolkitThread);
/*  80 */     toolkitThread.start();
/*     */   }
/*     */ 
/*     */   protected void finishTerminating() {
/*  84 */     Thread toolkitThread = getEventThread();
/*  85 */     if (toolkitThread != null) {
/*  86 */       _terminateLoop();
/*  87 */       setEventThread(null);
/*     */     }
/*  89 */     super.finishTerminating();
/*     */   }
/*     */ 
/*     */   public boolean shouldUpdateWindow() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   protected native Object _enterNestedEventLoop();
/*     */ 
/*     */   protected native void _leaveNestedEventLoop(Object paramObject);
/*     */ 
/*     */   public Window createWindow(Window owner, Screen screen, int styleMask)
/*     */   {
/* 103 */     return new WinWindow(owner, screen, styleMask);
/*     */   }
/*     */ 
/*     */   public Window createWindow(long parent) {
/* 107 */     return new WinChildWindow(parent);
/*     */   }
/*     */ 
/*     */   public View createView(Pen pen) {
/* 111 */     return new WinView(pen);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int type) {
/* 115 */     return new WinCursor(type);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int x, int y, Pixels pixels) {
/* 119 */     return new WinCursor(x, y, pixels);
/*     */   }
/*     */ 
/*     */   protected void staticCursor_setVisible(boolean visible) {
/* 123 */     WinCursor.setVisible_impl(visible);
/*     */   }
/*     */ 
/*     */   protected Size staticCursor_getBestSize(int width, int height) {
/* 127 */     return WinCursor.getBestSize_impl(width, height);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, ByteBuffer data) {
/* 131 */     return new WinPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, IntBuffer data) {
/* 135 */     return new WinPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   protected int staticPixels_getNativeFormat() {
/* 139 */     return WinPixels.getNativeFormat_impl();
/*     */   }
/*     */ 
/*     */   public Robot createRobot() {
/* 143 */     return new WinRobot();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getDeepestScreen() {
/* 147 */     return WinScreen.getDeepestScreen_impl();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getMainScreen() {
/* 151 */     return WinScreen.getMainScreen_impl();
/*     */   }
/*     */   protected Screen staticScreen_getScreenForLocation(int x, int y) {
/* 154 */     return WinScreen.getScreenForLocation_impl(x, y);
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForPtr(long screenPtr) {
/* 158 */     return WinScreen.getScreenForPtr_impl(screenPtr);
/*     */   }
/*     */ 
/*     */   protected List<Screen> staticScreen_getScreens() {
/* 162 */     return WinScreen.getScreens_impl();
/*     */   }
/*     */ 
/*     */   public Timer createTimer(Runnable runnable) {
/* 166 */     return new WinTimer(runnable);
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMinPeriod() {
/* 170 */     return WinTimer.getMinPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMaxPeriod() {
/* 174 */     return WinTimer.getMaxPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected String[] staticCommonDialogs_showFileChooser(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*     */   {
/* 179 */     return WinCommonDialogs.showFileChooser_impl(owner, folder, title, type, multipleMode, extensionFilters);
/*     */   }
/*     */ 
/*     */   protected String staticCommonDialogs_showFolderChooser(Window owner, String folder, String title) {
/* 183 */     return WinCommonDialogs.showFolderChooser_impl(owner, folder, title);
/*     */   }
/*     */ 
/*     */   protected long staticView_getMultiClickTime() {
/* 187 */     return WinView.getMultiClickTime_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxX() {
/* 191 */     return WinView.getMultiClickMaxX_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxY() {
/* 195 */     return WinView.getMultiClickMaxY_impl();
/*     */   }
/*     */ 
/*     */   protected native void _invokeAndWait(Runnable paramRunnable);
/*     */ 
/*     */   protected native void _invokeLater(Runnable paramRunnable);
/*     */ 
/*     */   protected native void _postOnEventQueue(Runnable paramRunnable);
/*     */ 
/*     */   public boolean supportsTransparentWindows()
/*     */   {
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  33 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  36 */         Application.loadNativeLibrary();
/*  37 */         return null;
/*     */       }
/*     */     });
/*  40 */     initIDs();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.win.WinApplication
 * JD-Core Version:    0.6.2
 */