/*     */ package com.sun.glass.ui.mac;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Application.EventHandler;
/*     */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*     */ import com.sun.glass.ui.Cursor;
/*     */ import com.sun.glass.ui.InvokeLaterDispatcher;
/*     */ import com.sun.glass.ui.InvokeLaterDispatcher.InvokeLaterSubmitter;
/*     */ import com.sun.glass.ui.Launchable;
/*     */ import com.sun.glass.ui.Menu;
/*     */ import com.sun.glass.ui.MenuBar;
/*     */ import com.sun.glass.ui.MenuItem;
/*     */ import com.sun.glass.ui.MenuItem.Callback;
/*     */ import com.sun.glass.ui.Pen;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.glass.ui.Robot;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.Size;
/*     */ import com.sun.glass.ui.Timer;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.View.Capability;
/*     */ import com.sun.glass.ui.Window;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class MacApplication extends Application
/*     */   implements InvokeLaterDispatcher.InvokeLaterSubmitter
/*     */ {
/*     */   private boolean isEmbedded;
/*     */   private boolean isTaskbarApplication;
/*     */   private final InvokeLaterDispatcher invokeLaterDispatcher;
/*     */   Menu appleMenu;
/*     */   static final long BROWSER_PARENT_ID = -1L;
/*     */ 
/*     */   public MacApplication()
/*     */   {
/*  21 */     this.isEmbedded = false;
/*  22 */     this.isTaskbarApplication = false;
/*     */ 
/*  38 */     this.invokeLaterDispatcher = new InvokeLaterDispatcher(this);
/*  39 */     this.invokeLaterDispatcher.start();
/*     */   }
/*     */ 
/*     */   private static native void _initIDs();
/*     */ 
/*     */   native void _runLoop(String[] paramArrayOfString, ClassLoader paramClassLoader, Launchable paramLaunchable, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   protected void runLoop(String[] args, Launchable launchable) {
/*  47 */     this.isEmbedded = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Boolean run() {
/*  50 */         return Boolean.valueOf(Boolean.getBoolean("javafx.macosx.embedded"));
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/*  53 */     this.isTaskbarApplication = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Boolean run() {
/*  56 */         String taskbarAppProp = System.getProperty("com.sun.glass.taskbarApplication");
/*  57 */         return Boolean.valueOf(!"false".equalsIgnoreCase(taskbarAppProp));
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/*  61 */     ClassLoader classLoader = MacApplication.class.getClassLoader();
/*  62 */     _runLoop(args, classLoader, launchable, this.isEmbedded, this.isTaskbarApplication);
/*     */   }
/*     */ 
/*     */   private native void _finishTerminating();
/*     */ 
/*     */   protected void finishTerminating() {
/*  68 */     setEventThread(null);
/*  69 */     if (!this.isEmbedded) {
/*  70 */       _finishTerminating();
/*     */     }
/*     */ 
/*  73 */     super.finishTerminating();
/*     */   }
/*     */ 
/*     */   private void setEventThread()
/*     */   {
/*  78 */     setEventThread(Thread.currentThread());
/*     */   }
/*     */   private native Object _enterNestedEventLoopImpl();
/*     */ 
/*     */   protected Object _enterNestedEventLoop() {
/*  83 */     this.invokeLaterDispatcher.notifyEnteringNestedEventLoop();
/*     */     try {
/*  85 */       return _enterNestedEventLoopImpl();
/*     */     } finally {
/*  87 */       this.invokeLaterDispatcher.notifyLeftNestedEventLoop();
/*     */     }
/*     */   }
/*     */ 
/*     */   private native void _leaveNestedEventLoopImpl(Object paramObject);
/*     */ 
/*  93 */   protected void _leaveNestedEventLoop(Object retValue) { this.invokeLaterDispatcher.notifyLeavingNestedEventLoop();
/*  94 */     _leaveNestedEventLoopImpl(retValue); }
/*     */ 
/*     */   public void installAppleMenu(MenuBar menubar)
/*     */   {
/*  98 */     this.appleMenu = createMenu("Apple");
/*     */ 
/* 100 */     MenuItem quitMenu = createMenuItem("Quit " + getName(), new MenuItem.Callback() {
/*     */       public void action() {
/* 102 */         Application.EventHandler eh = MacApplication.this.getEventHandler();
/* 103 */         if (eh != null)
/* 104 */           eh.handleQuitAction(Application.GetApplication(), System.nanoTime());
/*     */       }
/*     */ 
/*     */       public void validate()
/*     */       {
/*     */       }
/*     */     }
/*     */     , 113, 16);
/*     */ 
/* 110 */     this.appleMenu.add(quitMenu);
/*     */ 
/* 112 */     menubar.add(this.appleMenu);
/*     */   }
/*     */ 
/*     */   public Menu getAppleMenu() {
/* 116 */     return this.appleMenu;
/*     */   }
/*     */ 
/*     */   public void installDefaultMenus(MenuBar menubar) {
/* 120 */     installAppleMenu(menubar);
/*     */   }
/*     */ 
/*     */   public Window createWindow(Window owner, Screen screen, int styleMask)
/*     */   {
/* 127 */     return new MacWindow(owner, screen, styleMask);
/*     */   }
/*     */ 
/*     */   public Window createWindow(long parent)
/*     */   {
/* 132 */     Window window = new MacWindow(parent);
/* 133 */     if (parent == -1L)
/*     */     {
/* 136 */       window.setView(createView(new TempPen()));
/*     */     }
/* 138 */     return window;
/*     */   }
/*     */ 
/*     */   public View createView(Pen pen) {
/* 142 */     return new MacView(pen);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int type) {
/* 146 */     return new MacCursor(type);
/*     */   }
/*     */ 
/*     */   public Cursor createCursor(int x, int y, Pixels pixels) {
/* 150 */     return new MacCursor(x, y, pixels);
/*     */   }
/*     */ 
/*     */   protected void staticCursor_setVisible(boolean visible) {
/* 154 */     MacCursor.setVisible_impl(visible);
/*     */   }
/*     */ 
/*     */   protected Size staticCursor_getBestSize(int width, int height) {
/* 158 */     return MacCursor.getBestSize_impl(width, height);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, ByteBuffer data) {
/* 162 */     return new MacPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   public Pixels createPixels(int width, int height, IntBuffer data) {
/* 166 */     return new MacPixels(width, height, data);
/*     */   }
/*     */ 
/*     */   protected int staticPixels_getNativeFormat() {
/* 170 */     return MacPixels.getNativeFormat_impl();
/*     */   }
/*     */ 
/*     */   public Robot createRobot() {
/* 174 */     return new MacRobot();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getDeepestScreen() {
/* 178 */     return MacScreen.getDeepestScreen_impl();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getMainScreen() {
/* 182 */     return MacScreen.getMainScreen_impl();
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForLocation(int x, int y) {
/* 186 */     return MacScreen.getScreenForLocation_impl(x, y);
/*     */   }
/*     */ 
/*     */   protected Screen staticScreen_getScreenForPtr(long screenPtr) {
/* 190 */     return MacScreen.getScreenForPtr_impl(screenPtr);
/*     */   }
/*     */ 
/*     */   protected List<Screen> staticScreen_getScreens() {
/* 194 */     return MacScreen.getScreens_impl();
/*     */   }
/*     */ 
/*     */   public Timer createTimer(Runnable runnable) {
/* 198 */     return new MacTimer(runnable);
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMinPeriod() {
/* 202 */     return MacTimer.getMinPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected int staticTimer_getMaxPeriod() {
/* 206 */     return MacTimer.getMaxPeriod_impl();
/*     */   }
/*     */ 
/*     */   protected String[] staticCommonDialogs_showFileChooser(Window owner, String folder, String title, int type, boolean multipleMode, CommonDialogs.ExtensionFilter[] extensionFilters)
/*     */   {
/* 211 */     return MacCommonDialogs.showFileChooser_impl(owner, folder, title, type, multipleMode, extensionFilters);
/*     */   }
/*     */ 
/*     */   protected String staticCommonDialogs_showFolderChooser(Window owner, String folder, String title)
/*     */   {
/* 216 */     return MacCommonDialogs.showFolderChooser_impl();
/*     */   }
/*     */ 
/*     */   protected long staticView_getMultiClickTime() {
/* 220 */     return MacView.getMultiClickTime_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxX() {
/* 224 */     return MacView.getMultiClickMaxX_impl();
/*     */   }
/*     */ 
/*     */   protected int staticView_getMultiClickMaxY() {
/* 228 */     return MacView.getMultiClickMaxY_impl();
/*     */   }
/*     */ 
/*     */   protected native void _invokeAndWait(Runnable paramRunnable);
/*     */ 
/*     */   public native void submitForLaterInvocation(Runnable paramRunnable);
/*     */ 
/*     */   protected void _invokeLater(Runnable runnable)
/*     */   {
/* 237 */     this.invokeLaterDispatcher.invokeLater(runnable);
/*     */   }
/*     */ 
/*     */   protected void _postOnEventQueue(Runnable runnable) {
/* 241 */     _invokeLater(runnable);
/*     */   }
/*     */ 
/*     */   public boolean supportsTransparentWindows()
/*     */   {
/* 246 */     return true;
/*     */   }
/*     */   protected native String _getRemoteLayerServerName();
/*     */ 
/*     */   public String getRemoteLayerServerName() {
/* 251 */     return _getRemoteLayerServerName();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  27 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  30 */         Application.loadNativeLibrary();
/*  31 */         return null;
/*     */       }
/*     */     });
/*  34 */     _initIDs();
/*     */   }
/*     */ 
/*     */   static class TempPen extends Pen
/*     */   {
/* 256 */     private static final Map capabilities = new HashMap();
/*     */ 
/*     */     public Map getCapabilities()
/*     */     {
/* 264 */       return capabilities;
/*     */     }
/*     */ 
/*     */     public void paint(long time, int width, int height)
/*     */     {
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 259 */       capabilities.put(View.Capability.k3dKey, new Boolean(true));
/* 260 */       capabilities.put(View.Capability.kSyncKey, new Boolean(true));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacApplication
 * JD-Core Version:    0.6.2
 */