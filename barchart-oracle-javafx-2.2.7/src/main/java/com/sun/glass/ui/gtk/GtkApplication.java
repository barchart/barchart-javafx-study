/*     */ package com.sun.glass.ui.gtk;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.InvokeLaterDispatcher;
/*     */ import com.sun.glass.ui.InvokeLaterDispatcher.InvokeLaterSubmitter;
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
/*     */ import java.util.Map;
/*     */ 
/*     */ public class GtkApplication extends Application
/*     */   implements InvokeLaterDispatcher.InvokeLaterSubmitter
/*     */ {
/*  17 */   public static int screen = -1;
/*  18 */   public static long display = 0L;
/*  19 */   public static long visualID = 0L;
/*     */   private final InvokeLaterDispatcher invokeLaterDispatcher;
/*     */   private Object eventLoopExitEnterPassValue;
/*     */ 
/*     */   public GtkApplication()
/*     */   {
/*  23 */     this.invokeLaterDispatcher = new InvokeLaterDispatcher(this);
/*  24 */     this.invokeLaterDispatcher.start();
/*     */   }
/*     */ 
/*     */   private void initDisplay() {
/*  28 */     Map ds = getDeviceDetails();
/*  29 */     if (ds != null)
/*     */     {
/*  31 */       Object value = ds.get("XDisplay");
/*  32 */       if (value != null) {
/*  33 */         display = ((Long)value).longValue();
/*     */       }
/*  35 */       value = ds.get("XVisualID");
/*  36 */       if (value != null) {
/*  37 */         visualID = ((Long)value).longValue();
/*     */       }
/*  39 */       value = ds.get("XScreenID");
/*  40 */       if (value != null)
/*  41 */         screen = ((Integer)value).intValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  47 */     initDisplay();
/*  48 */     _init();
/*     */   }
/*     */ 
/*     */   protected void runLoop(final String[] args, final Launchable launchable) {
/*  52 */     boolean isEventThread = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Boolean run()
/*     */       {
/*  56 */         return Boolean.valueOf(Boolean.getBoolean("javafx.embed.isEventThread"));
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/*  60 */     final boolean isEmbedded = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Boolean run()
/*     */       {
/*  64 */         return Boolean.valueOf(Boolean.getBoolean("javafx.macosx.embedded"));
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/*  68 */     if (isEventThread) {
/*  69 */       init();
/*  70 */       setEventThread(Thread.currentThread());
/*  71 */       launchable.finishLaunching(args);
/*  72 */       return;
/*     */     }
/*     */ 
/*  75 */     Thread toolkitThread = (Thread)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Thread run()
/*     */       {
/*  79 */         Thread th = new Thread(new Runnable() {
/*     */           public void run() {
/*  81 */             GtkApplication.this.init();
/*  82 */             GtkApplication.this._runLoop(GtkApplication.3.this.val$args, GtkApplication.3.this.val$launchable, GtkApplication.3.this.val$isEmbedded);
/*     */           }
/*     */         }
/*     */         , "GtkNativeMainLoopThread");
/*     */ 
/*  86 */         return th;
/*     */       }
/*     */     });
/*  89 */     setEventThread(toolkitThread);
/*  90 */     toolkitThread.start();
/*     */   }
/*     */ 
/*     */   protected void finishTerminating()
/*     */   {
/*  95 */     Thread toolkitThread = getEventThread();
/*  96 */     if (toolkitThread != null) {
/*  97 */       _terminateLoop();
/*  98 */       setEventThread(null);
/*     */     }
/* 100 */     super.finishTerminating();
/*     */   }
/*     */ 
/*     */   public boolean shouldUpdateWindow() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   private native void _terminateLoop();
/*     */ 
/*     */   private native void _init();
/*     */ 
/*     */   protected native void _runLoop(String[] paramArrayOfString, Launchable paramLaunchable, boolean paramBoolean);
/*     */ 
/*     */   protected native void _invokeAndWait(Runnable paramRunnable);
/*     */ 
/*     */   public native void submitForLaterInvocation(Runnable paramRunnable);
/*     */ 
/*     */   protected void _invokeLater(Runnable runnable)
/*     */   {
/* 120 */     this.invokeLaterDispatcher.invokeLater(runnable);
/*     */   }
/*     */ 
/*     */   protected void _postOnEventQueue(Runnable runnable) {
/* 124 */     _invokeLater(runnable);
/*     */   }
/*     */ 
/*     */   private native void enterNestedEventLoopImpl();
/*     */ 
/*     */   private native void leaveNestedEventLoopImpl();
/*     */ 
/*     */   protected Object _enterNestedEventLoop()
/*     */   {
/* 135 */     this.invokeLaterDispatcher.notifyEnteringNestedEventLoop();
/*     */     try {
/* 137 */       enterNestedEventLoopImpl();
/* 138 */       Object retValue = this.eventLoopExitEnterPassValue;
/* 139 */       this.eventLoopExitEnterPassValue = null;
/* 140 */       return retValue;
/*     */     } finally {
/* 142 */       this.invokeLaterDispatcher.notifyLeftNestedEventLoop();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void _leaveNestedEventLoop(Object retValue)
/*     */   {
/* 148 */     this.invokeLaterDispatcher.notifyLeavingNestedEventLoop();
/* 149 */     this.eventLoopExitEnterPassValue = retValue;
/* 150 */     leaveNestedEventLoopImpl();
/*     */   }
/*     */ 
/*     */   public Window createWindow(Window owner, Screen screen, int styleMask)
/*     */   {
/* 155 */     return new GtkWindow(owner, screen, styleMask);
/*     */   }
/*     */ 
/*     */   public Window createWindow(long parent)
/*     */   {
/* 160 */     return new GtkChildWindow(parent);
/*     */   }
/*     */ 
/*     */   public View createView(Pen pen)
/*     */   {
/* 165 */     return new GtkView(pen);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int type)
/*     */   {
/* 170 */     return new GtkCursor(type);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int x, int y, Pixels pixels)
/*     */   {
/* 175 */     return new GtkCursor(x, y, pixels);
/*     */   }
/*     */ 
/*     */   protected void staticCursor_setVisible(boolean visible)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected Size staticCursor_getBestSize(int width, int height)
/*     */   {
/* 184 */     return GtkCursor._getBestSize(width, height);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, ByteBuffer data)
/*     */   {
/* 189 */     return new GtkPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, IntBuffer data)
/*     */   {
/* 194 */     return new GtkPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   protected int staticPixels_getNativeFormat()
/*     */   {
/* 199 */     return 1;
/*     */   }
/*     */ 
/*     */   public Robot createRobot()
/*     */   {
/* 204 */     return new GtkRobot();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getDeepestScreen()
/*     */   {
/* 209 */     List screens = staticScreen_getScreens();
/* 210 */     Screen deepest = (Screen)screens.get(0);
/* 211 */     int nScreens = screens.size();
/* 212 */     for (int i = 1; i < nScreens; i++) {
/* 213 */       if (deepest.getDepth() < ((Screen)screens.get(i)).getDepth()) {
/* 214 */         deepest = (Screen)screens.get(i);
/*     */       }
/*     */     }
/* 217 */     return deepest;
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getMainScreen()
/*     */   {
/* 226 */     List screens = staticScreen_getScreens();
/* 227 */     return (Screen)screens.get(0);
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForLocation(int x, int y)
/*     */   {
/* 232 */     List screens = staticScreen_getScreens();
/* 233 */     for (Screen screen : screens) {
/* 234 */       if ((x >= screen.getX()) && (x <= screen.getWidth() + screen.getX()) && (y >= screen.getY()) && (y <= screen.getHeight() + screen.getY()))
/*     */       {
/* 238 */         return screen;
/*     */       }
/*     */     }
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForPtr(long screenPtr)
/*     */   {
/* 246 */     List screens = staticScreen_getScreens();
/* 247 */     for (Screen screen : screens) {
/* 248 */       if (screen.getNativeScreen() == screenPtr) return screen;
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ 
/*     */   protected native List<Screen> staticScreen_getScreens();
/*     */ 
/*     */   public Timer createTimer(Runnable runnable)
/*     */   {
/* 258 */     return new GtkTimer(runnable);
/*     */   }
/*     */ 
/*     */   protected native int staticTimer_getMinPeriod();
/*     */ 
/*     */   protected native int staticTimer_getMaxPeriod();
/*     */ 
/*     */   protected String[] staticCommonDialogs_showFileChooser(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*     */   {
/* 269 */     return GtkCommonDialogs.showFileChooser(owner, folder, title, type, multipleMode, extensionFilters);
/*     */   }
/*     */ 
/*     */   protected String staticCommonDialogs_showFolderChooser(Window owner, String folder, String title)
/*     */   {
/* 274 */     return GtkCommonDialogs.showFolderChooser(owner, folder, title);
/*     */   }
/*     */ 
/*     */   protected native long staticView_getMultiClickTime();
/*     */ 
/*     */   protected native int staticView_getMultiClickMaxX();
/*     */ 
/*     */   protected native int staticView_getMultiClickMaxY();
/*     */ 
/*     */   public native boolean supportsTransparentWindows();
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkApplication
 * JD-Core Version:    0.6.2
 */