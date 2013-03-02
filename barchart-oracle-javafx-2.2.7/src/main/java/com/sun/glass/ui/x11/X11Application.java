/*     */ package com.sun.glass.ui.x11;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.Launchable;
/*     */ import com.sun.glass.ui.Menu;
/*     */ import com.sun.glass.ui.MenuBar;
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
/*     */ import java.util.Map;
/*     */ 
/*     */ public class X11Application extends Application
/*     */ {
/*     */   Menu windowMenu;
/*     */   Menu editMenu;
/*     */   Menu fileMenu;
/*  46 */   long display = 0L;
/*     */ 
/*     */   protected static void initLibrary()
/*     */   {
/*  37 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  40 */         Application.loadNativeLibrary("glass-x11");
/*  41 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private native void _runLoop(String[] paramArrayOfString, Launchable paramLaunchable, long paramLong1, int paramInt, long paramLong2);
/*     */ 
/*     */   protected void runLoop(final String[] args, final Launchable launchable)
/*     */   {
/*  53 */     Thread toolkitThread = (Thread)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Thread run() {
/*  56 */         Thread th = new Thread(new Runnable() {
/*     */           public void run() {
/*  58 */             int screen = -1;
/*  59 */             long visualID = 0L;
/*     */ 
/*  61 */             Map ds = X11Application.access$000();
/*  62 */             if (ds != null)
/*     */             {
/*  64 */               Object value = ds.get("XDisplay");
/*  65 */               if (value != null) {
/*  66 */                 X11Application.this.display = ((Long)value).longValue();
/*     */               }
/*  68 */               value = ds.get("XVisualID");
/*  69 */               if (value != null) {
/*  70 */                 visualID = ((Long)value).longValue();
/*     */               }
/*  72 */               value = ds.get("XScreenID");
/*  73 */               if (value != null) {
/*  74 */                 screen = ((Integer)value).intValue();
/*     */               }
/*     */             }
/*  77 */             X11Application.this._runLoop(X11Application.2.this.val$args, X11Application.2.this.val$launchable, X11Application.this.display, screen, visualID);
/*     */           }
/*     */         }
/*     */         , "X11NativeRunloopThread");
/*     */ 
/*  81 */         return th;
/*     */       }
/*     */     });
/*  84 */     setEventThread(toolkitThread);
/*  85 */     toolkitThread.start();
/*     */   }
/*     */ 
/*     */   native void _terminate(boolean paramBoolean);
/*     */ 
/*     */   protected void finishTerminating() {
/*  91 */     Thread toolkitThread = getEventThread();
/*  92 */     if (toolkitThread != null) {
/*  93 */       _terminate(Thread.currentThread().equals(toolkitThread));
/*  94 */       setEventThread(null);
/*     */     }
/*  96 */     super.finishTerminating();
/*     */   }
/*     */   protected Object _enterNestedEventLoop() {
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   protected void _leaveNestedEventLoop(Object retValue) {
/*     */   }
/*     */ 
/*     */   public void installDefaultMenus(MenuBar menubar) {
/*     */   }
/*     */ 
/*     */   public Window createWindow(Window owner, Screen screen, int styleMask) {
/* 109 */     return new X11Window(owner, screen, styleMask);
/*     */   }
/*     */ 
/*     */   public Window createWindow(long parent) {
/* 113 */     return new X11Window(parent);
/*     */   }
/*     */ 
/*     */   public View createView(Pen pen) {
/* 117 */     return new X11View(pen);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int type) {
/* 121 */     return new X11Cursor(type);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int x, int y, Pixels pixels) {
/* 125 */     return new X11Cursor(x, y, pixels);
/*     */   }
/*     */ 
/*     */   protected void staticCursor_setVisible(boolean visible) {
/* 129 */     X11Cursor.setVisible_impl(visible);
/*     */   }
/*     */ 
/*     */   protected Size staticCursor_getBestSize(int width, int height) {
/* 133 */     return X11Cursor.getBestSize_impl(width, height);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, ByteBuffer data) {
/* 137 */     return new X11Pixels(width, height, data);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, IntBuffer data) {
/* 141 */     return new X11Pixels(width, height, data);
/*     */   }
/*     */ 
/*     */   protected int staticPixels_getNativeFormat() {
/* 145 */     return X11Pixels.getNativeFormat_impl();
/*     */   }
/*     */ 
/*     */   public Robot createRobot() {
/* 149 */     throw new UnsupportedOperationException("not implmented");
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getDeepestScreen()
/*     */   {
/* 154 */     return X11Screen.getDeepestScreen_impl();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getMainScreen() {
/* 158 */     return X11Screen.getMainScreen_impl();
/*     */   }
/*     */   protected Screen staticScreen_getScreenForLocation(int x, int y) {
/* 161 */     return X11Screen.getScreenForLocation_impl(x, y);
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForPtr(long screenPtr) {
/* 165 */     return X11Screen.getScreenForPtr_impl(screenPtr);
/*     */   }
/*     */ 
/*     */   protected List<Screen> staticScreen_getScreens() {
/* 169 */     return X11Screen.getScreens_impl();
/*     */   }
/*     */ 
/*     */   public Timer createTimer(Runnable runnable) {
/* 173 */     return new X11Timer(runnable);
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMinPeriod() {
/* 177 */     return X11Timer.getMinPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMaxPeriod() {
/* 181 */     return X11Timer.getMaxPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected String[] staticCommonDialogs_showFileChooser(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*     */   {
/* 187 */     return X11CommonDialogs.showFileChooser_impl(folder, title, type, multipleMode, extensionFilters);
/*     */   }
/*     */ 
/*     */   protected String staticCommonDialogs_showFolderChooser(Window owner, String folder, String title)
/*     */   {
/* 192 */     return X11CommonDialogs.showFolderChooser_impl();
/*     */   }
/*     */ 
/*     */   protected long staticView_getMultiClickTime() {
/* 196 */     return X11View.getMultiClickTime_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxX() {
/* 200 */     return X11View.getMultiClickMaxX_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxY() {
/* 204 */     return X11View.getMultiClickMaxY_impl();
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
/* 215 */     return true;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Application
 * JD-Core Version:    0.6.2
 */