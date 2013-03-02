/*      */ package com.sun.javafx.tk.quantum;
/*      */ 
/*      */ import com.sun.glass.ui.Application;
/*      */ import com.sun.glass.ui.Application.EventHandler;
/*      */ import com.sun.glass.ui.ClipboardAssistance;
/*      */ import com.sun.glass.ui.CommonDialogs;
/*      */ import com.sun.glass.ui.CommonDialogs.ExtensionFilter;
/*      */ import com.sun.glass.ui.EventLoop;
/*      */ import com.sun.glass.ui.Launchable;
/*      */ import com.sun.glass.ui.Screen;
/*      */ import com.sun.glass.ui.Screen.SettingsChangedCallback;
/*      */ import com.sun.glass.ui.Timer;
/*      */ import com.sun.glass.ui.View;
/*      */ import com.sun.glass.ui.Window;
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.embed.HostInterface;
/*      */ import com.sun.javafx.font.PrismFontLoader;
/*      */ import com.sun.javafx.geom.Path2D;
/*      */ import com.sun.javafx.geom.PathIterator;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.perf.PerformanceTracker;
/*      */ import com.sun.javafx.runtime.async.AbstractRemoteResource;
/*      */ import com.sun.javafx.runtime.async.AsyncOperationListener;
/*      */ import com.sun.javafx.scene.text.HitInfo;
/*      */ import com.sun.javafx.sg.PGArc;
/*      */ import com.sun.javafx.sg.PGCanvas;
/*      */ import com.sun.javafx.sg.PGCircle;
/*      */ import com.sun.javafx.sg.PGCubicCurve;
/*      */ import com.sun.javafx.sg.PGEllipse;
/*      */ import com.sun.javafx.sg.PGGroup;
/*      */ import com.sun.javafx.sg.PGImageView;
/*      */ import com.sun.javafx.sg.PGLine;
/*      */ import com.sun.javafx.sg.PGMediaView;
/*      */ import com.sun.javafx.sg.PGPath;
/*      */ import com.sun.javafx.sg.PGPolygon;
/*      */ import com.sun.javafx.sg.PGPolyline;
/*      */ import com.sun.javafx.sg.PGQuadCurve;
/*      */ import com.sun.javafx.sg.PGRectangle;
/*      */ import com.sun.javafx.sg.PGRegion;
/*      */ import com.sun.javafx.sg.PGSVGPath;
/*      */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*      */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*      */ import com.sun.javafx.sg.PGShape.StrokeType;
/*      */ import com.sun.javafx.sg.PGText;
/*      */ import com.sun.javafx.sg.prism.NGArc;
/*      */ import com.sun.javafx.sg.prism.NGCanvas;
/*      */ import com.sun.javafx.sg.prism.NGCircle;
/*      */ import com.sun.javafx.sg.prism.NGCubicCurve;
/*      */ import com.sun.javafx.sg.prism.NGEllipse;
/*      */ import com.sun.javafx.sg.prism.NGGroup;
/*      */ import com.sun.javafx.sg.prism.NGImageView;
/*      */ import com.sun.javafx.sg.prism.NGLine;
/*      */ import com.sun.javafx.sg.prism.NGMediaView;
/*      */ import com.sun.javafx.sg.prism.NGNode;
/*      */ import com.sun.javafx.sg.prism.NGPath;
/*      */ import com.sun.javafx.sg.prism.NGPolygon;
/*      */ import com.sun.javafx.sg.prism.NGPolyline;
/*      */ import com.sun.javafx.sg.prism.NGQuadCurve;
/*      */ import com.sun.javafx.sg.prism.NGRectangle;
/*      */ import com.sun.javafx.sg.prism.NGRegion;
/*      */ import com.sun.javafx.sg.prism.NGSVGPath;
/*      */ import com.sun.javafx.sg.prism.NGText;
/*      */ import com.sun.javafx.tk.FileChooserType;
/*      */ import com.sun.javafx.tk.FontLoader;
/*      */ import com.sun.javafx.tk.ImageLoader;
/*      */ import com.sun.javafx.tk.PlatformImage;
/*      */ import com.sun.javafx.tk.ScreenConfigurationAccessor;
/*      */ import com.sun.javafx.tk.TKClipboard;
/*      */ import com.sun.javafx.tk.TKDragGestureListener;
/*      */ import com.sun.javafx.tk.TKDragSourceListener;
/*      */ import com.sun.javafx.tk.TKDropTargetListener;
/*      */ import com.sun.javafx.tk.TKScene;
/*      */ import com.sun.javafx.tk.TKScreenConfigurationListener;
/*      */ import com.sun.javafx.tk.TKStage;
/*      */ import com.sun.javafx.tk.TKSystemMenu;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import com.sun.javafx.tk.Toolkit.ImageRenderingContext;
/*      */ import com.sun.javafx.tk.Toolkit.Task;
/*      */ import com.sun.javafx.tk.desktop.AppletWindow;
/*      */ import com.sun.javafx.tk.desktop.DesktopToolkit;
/*      */ import com.sun.javafx.tk.desktop.MasterTimer;
/*      */ import com.sun.prism.BasicStroke;
/*      */ import com.sun.prism.ExternalImageTools;
/*      */ import com.sun.prism.ExternalImageTools.IExporter;
/*      */ import com.sun.prism.ExternalImageTools.IImporter;
/*      */ import com.sun.prism.Graphics;
/*      */ import com.sun.prism.GraphicsPipeline;
/*      */ import com.sun.prism.PixelFormat;
/*      */ import com.sun.prism.RTTexture;
/*      */ import com.sun.prism.RenderTarget;
/*      */ import com.sun.prism.RenderingContext;
/*      */ import com.sun.prism.ResourceFactory;
/*      */ import com.sun.prism.ResourceFactoryListener;
/*      */ import com.sun.prism.camera.PrismCameraImpl;
/*      */ import com.sun.prism.camera.PrismParallelCameraImpl;
/*      */ import com.sun.prism.camera.PrismPerspectiveCameraImpl;
/*      */ import com.sun.prism.impl.Disposer;
/*      */ import com.sun.prism.impl.PrismSettings;
/*      */ import com.sun.prism.paint.Paint;
/*      */ import com.sun.prism.paint.Paint.Type;
/*      */ import com.sun.prism.render.CompletionListener;
/*      */ import com.sun.prism.render.RenderJob;
/*      */ import com.sun.prism.render.ToolkitInterface;
/*      */ import com.sun.scenario.DelayedRunnable;
/*      */ import com.sun.scenario.animation.AbstractMasterTimer;
/*      */ import com.sun.scenario.effect.FilterContext;
/*      */ import com.sun.scenario.effect.Filterable;
/*      */ import com.sun.scenario.effect.impl.prism.PrFilterContext;
/*      */ import com.sun.scenario.effect.impl.prism.PrImage;
/*      */ import java.io.File;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import javafx.application.ConditionalFeature;
/*      */ import javafx.geometry.Dimension2D;
/*      */ import javafx.scene.input.DragEvent;
/*      */ import javafx.scene.input.Dragboard;
/*      */ import javafx.scene.input.InputMethodEvent;
/*      */ import javafx.scene.input.InputMethodRequests;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.TransferMode;
/*      */ import javafx.scene.paint.CycleMethod;
/*      */ import javafx.scene.shape.ClosePath;
/*      */ import javafx.scene.shape.CubicCurveTo;
/*      */ import javafx.scene.shape.FillRule;
/*      */ import javafx.scene.shape.LineTo;
/*      */ import javafx.scene.shape.MoveTo;
/*      */ import javafx.scene.shape.PathElement;
/*      */ import javafx.scene.shape.QuadCurveTo;
/*      */ import javafx.scene.shape.SVGPath;
/*      */ import javafx.stage.FileChooser.ExtensionFilter;
/*      */ import javafx.stage.Modality;
/*      */ import javafx.stage.StageStyle;
/*      */ 
/*      */ public class QuantumToolkit extends DesktopToolkit
/*      */   implements Launchable, ToolkitInterface
/*      */ {
/*  155 */   private static boolean verbose = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */   {
/*      */     public Boolean run() {
/*  158 */       return Boolean.valueOf(Boolean.getBoolean("quantum.verbose"));
/*      */     }
/*      */   })).booleanValue();
/*      */ 
/*  161 */   private static boolean debug = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */   {
/*      */     public Boolean run() {
/*  164 */       return Boolean.valueOf(Boolean.getBoolean("quantum.debug"));
/*      */     }
/*      */   })).booleanValue();
/*      */ 
/*  167 */   private static Integer pulseHZ = (Integer)AccessController.doPrivileged(new PrivilegedAction()
/*      */   {
/*      */     public Integer run() {
/*  170 */       return Integer.getInteger("javafx.animation.pulse");
/*      */     }
/*      */   });
/*      */   private AtomicBoolean toolkitRunning;
/*      */   private AtomicBoolean animationRunning;
/*      */   private AtomicBoolean nextPulseRequested;
/*      */   private AtomicBoolean pulseRunning;
/*      */   private CountDownLatch launchLatch;
/*      */   protected final int PULSE_INTERVAL;
/*      */   protected final int FULLSPEED_INTERVAL = 1;
/*      */   private Runnable pulseRunnable;
/*      */   private Runnable userRunnable;
/*      */   private Runnable timerRunnable;
/*      */   private Timer pulseTimer;
/*      */   private Thread shutdownHook;
/*      */   private PaintCollector collector;
/*      */   private QuantumRenderer renderer;
/*      */   private GraphicsPipeline pipeline;
/*      */   private ClassLoader ccl;
/*      */   private HashMap<Object, EventLoop> eventLoopMap;
/*  548 */   private static ScreenConfigurationAccessor screenAccessor = new ScreenConfigurationAccessor()
/*      */   {
/*      */     public int getMinX(Object paramAnonymousObject) {
/*  551 */       return ((Screen)paramAnonymousObject).getX();
/*      */     }
/*      */     public int getMinY(Object paramAnonymousObject) {
/*  554 */       return ((Screen)paramAnonymousObject).getY();
/*      */     }
/*      */     public int getWidth(Object paramAnonymousObject) {
/*  557 */       return ((Screen)paramAnonymousObject).getWidth();
/*      */     }
/*      */     public int getHeight(Object paramAnonymousObject) {
/*  560 */       return ((Screen)paramAnonymousObject).getHeight();
/*      */     }
/*      */     public int getVisualMinX(Object paramAnonymousObject) {
/*  563 */       return ((Screen)paramAnonymousObject).getVisibleX();
/*      */     }
/*      */     public int getVisualMinY(Object paramAnonymousObject) {
/*  566 */       return ((Screen)paramAnonymousObject).getVisibleY();
/*      */     }
/*      */     public int getVisualWidth(Object paramAnonymousObject) {
/*  569 */       return ((Screen)paramAnonymousObject).getVisibleWidth();
/*      */     }
/*      */     public int getVisualHeight(Object paramAnonymousObject) {
/*  572 */       return ((Screen)paramAnonymousObject).getVisibleHeight();
/*      */     }
/*      */     public float getDPI(Object paramAnonymousObject) {
/*  575 */       return ((Screen)paramAnonymousObject).getResolutionX();
/*      */     }
/*  548 */   };
/*      */   private Map contextMap;
/*      */   private DelayedRunnable animationRunnable;
/*  772 */   static BasicStroke tmpStroke = new BasicStroke();
/*      */   private QuantumClipboard clipboard;
/*      */   private GlassSystemMenu systemMenu;
/*      */   private QuantumClipboard dragSourceClipboard;
/*      */   private GlassScene dragSourceScene;
/*      */ 
/*      */   public QuantumToolkit()
/*      */   {
/*  173 */     this.toolkitRunning = new AtomicBoolean(false);
/*  174 */     this.animationRunning = new AtomicBoolean(false);
/*  175 */     this.nextPulseRequested = new AtomicBoolean(false);
/*  176 */     this.pulseRunning = new AtomicBoolean(false);
/*  177 */     this.launchLatch = new CountDownLatch(1);
/*      */ 
/*  179 */     this.PULSE_INTERVAL = ((int)(TimeUnit.SECONDS.toMillis(1L) / getRefreshRate()));
/*  180 */     this.FULLSPEED_INTERVAL = 1;
/*      */ 
/*  182 */     this.pulseTimer = null;
/*  183 */     this.shutdownHook = null;
/*      */ 
/*  190 */     this.eventLoopMap = null;
/*      */ 
/*  662 */     this.contextMap = Collections.synchronizedMap(new HashMap());
/*      */ 
/* 1126 */     this.systemMenu = new GlassSystemMenu();
/*      */   }
/*      */ 
/*      */   public boolean init()
/*      */   {
/*  216 */     if (PlatformUtil.isMac()) {
/*  217 */       String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*      */         public String run() {
/*  219 */           return System.getProperty("os.version");
/*      */         }
/*      */       });
/*  222 */       if ((str.startsWith("10.4")) || (str.startsWith("10.5"))) {
/*  223 */         throw new RuntimeException("JavaFX requires Mac OSX 10.6 or higher to run");
/*      */       }
/*      */ 
/*  226 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */         public Void run() {
/*  228 */           System.setProperty("java.awt.headless", "true");
/*  229 */           return null;
/*      */         }
/*      */ 
/*      */       });
/*      */     }
/*      */ 
/*  236 */     this.renderer = QuantumRenderer.getInstance();
/*  237 */     this.collector = PaintCollector.createInstance(this);
/*  238 */     this.pipeline = GraphicsPipeline.getPipeline();
/*  239 */     if (PrismSettings.shutdownHook) {
/*  240 */       this.shutdownHook = new Thread("Glass/Prism Shutdown Hook") {
/*      */         public void run() {
/*  242 */           QuantumToolkit.this.dispose();
/*      */         }
/*      */       };
/*  245 */       AccessController.doPrivileged(new PrivilegedAction() {
/*      */         public Void run() {
/*  247 */           Runtime.getRuntime().addShutdownHook(QuantumToolkit.this.shutdownHook);
/*  248 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*  252 */     return true;
/*      */   }
/*      */ 
/*      */   public void startup(Runnable paramRunnable)
/*      */   {
/*  266 */     this.ccl = Thread.currentThread().getContextClassLoader();
/*      */     try
/*      */     {
/*  269 */       this.userRunnable = paramRunnable;
/*      */ 
/*  271 */       Application.Run(null, this);
/*      */     } catch (RuntimeException localRuntimeException) {
/*  273 */       if (verbose) {
/*  274 */         localRuntimeException.printStackTrace();
/*      */       }
/*  276 */       throw localRuntimeException;
/*      */     } catch (Throwable localThrowable) {
/*  278 */       if (verbose) {
/*  279 */         localThrowable.printStackTrace();
/*      */       }
/*  281 */       throw new RuntimeException(localThrowable);
/*      */     }
/*      */     try
/*      */     {
/*  285 */       this.launchLatch.await();
/*      */     } catch (InterruptedException localInterruptedException) {
/*  287 */       localInterruptedException.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void assertToolkitRunning()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void finishLaunching(String[] paramArrayOfString)
/*      */   {
/*  304 */     Thread localThread = Thread.currentThread();
/*      */ 
/*  306 */     if (!this.toolkitRunning.getAndSet(true)) {
/*  307 */       localThread.setName("JavaFX Application Thread");
/*      */ 
/*  310 */       localThread.setContextClassLoader(this.ccl);
/*      */ 
/*  313 */       localThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
/*      */         public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable) {
/*  315 */           System.out.println(paramAnonymousThread.getName() + " uncaught: " + paramAnonymousThrowable.getClass().getName());
/*  316 */           paramAnonymousThrowable.printStackTrace();
/*      */         }
/*      */       });
/*  319 */       setFxUserThread(localThread);
/*      */ 
/*  325 */       this.renderer.createResourceFactory();
/*      */ 
/*  327 */       this.pulseRunnable = new Runnable() {
/*      */         public void run() {
/*  329 */           QuantumToolkit.this.pulse();
/*      */         }
/*      */       };
/*  332 */       this.timerRunnable = new Runnable() {
/*      */         public void run() {
/*      */           try {
/*  335 */             QuantumToolkit.this.postPulse();
/*      */           } catch (Throwable localThrowable) {
/*  337 */             localThrowable.printStackTrace(System.err);
/*      */           }
/*      */         }
/*      */       };
/*  343 */       this.pulseTimer = Application.GetApplication().createTimer(this.timerRunnable);
/*      */ 
/*  345 */       Application.GetApplication().setEventHandler(new Application.EventHandler() {
/*      */         public void handleQuitAction(Application paramAnonymousApplication, long paramAnonymousLong) {
/*  347 */           GlassStage.requestClosingAllWindows();
/*      */         }
/*      */       });
/*      */     }
/*  351 */     this.launchLatch.countDown();
/*      */     try {
/*  353 */       Application.invokeAndWait(this.userRunnable);
/*      */ 
/*  355 */       if (getMasterTimer().isFullspeed())
/*      */       {
/*  361 */         this.pulseTimer.start(1);
/*      */       }
/*  363 */       else this.pulseTimer.start(this.PULSE_INTERVAL); 
/*      */     }
/*      */     catch (Throwable localThrowable)
/*      */     {
/*  366 */       localThrowable.printStackTrace(System.err);
/*      */     } finally {
/*  368 */       if (verbose) {
/*  369 */         System.err.println(new StringBuilder().append(" vsync: ").append(PrismSettings.isVsyncEnabled).append(" vpipe: ").append(this.pipeline.isVsyncSupported()).toString());
/*      */       }
/*      */ 
/*  372 */       PerformanceTracker.logEvent("Toolkit.startup - finished");
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isVsyncEnabled() {
/*  377 */     return (PrismSettings.isVsyncEnabled) && (this.pipeline.isVsyncSupported()) && (windowVisible(Window.getWindows()));
/*      */   }
/*      */ 
/*      */   private boolean windowVisible(List<Window> paramList)
/*      */   {
/*  383 */     if (paramList.isEmpty()) {
/*  384 */       return false;
/*      */     }
/*  386 */     for (int i = 0; i < paramList.size(); i++) {
/*  387 */       Window localWindow = (Window)paramList.get(i);
/*  388 */       if ((localWindow.isVisible()) && (!localWindow.isMinimized())) {
/*  389 */         return true;
/*      */       }
/*      */     }
/*  392 */     return false;
/*      */   }
/*      */ 
/*      */   public void checkFxUserThread()
/*      */   {
/*  397 */     super.checkFxUserThread();
/*  398 */     this.renderer.checkRendererIdle();
/*      */   }
/*      */ 
/*      */   protected static Thread getFxUserThread() {
/*  402 */     return Toolkit.getFxUserThread();
/*      */   }
/*      */ 
/*      */   public Future addRenderJob(RenderJob paramRenderJob)
/*      */   {
/*  408 */     return this.renderer.submitRenderJob(paramRenderJob);
/*      */   }
/*      */ 
/*      */   protected void postPulse() {
/*  412 */     if ((this.toolkitRunning.get()) && ((this.animationRunning.get()) || (this.nextPulseRequested.get()) || (this.collector.hasDirty().get())) && (!setPulseRunning()))
/*      */     {
/*  416 */       Application.postOnEventQueue(this.pulseRunnable);
/*      */ 
/*  418 */       if (debug)
/*  419 */         System.err.println(new StringBuilder().append("QT.postPulse@(").append(System.nanoTime()).append("): ").append(pulseString()).toString());
/*      */     }
/*  421 */     else if (debug) {
/*  422 */       System.err.println(new StringBuilder().append("QT.postPulse#(").append(System.nanoTime()).append(") DROP: ").append(pulseString()).toString());
/*      */     }
/*      */   }
/*      */ 
/*      */   private String pulseString() {
/*  427 */     return new StringBuilder().append(this.toolkitRunning.get() ? "T" : "t").append(this.animationRunning.get() ? "A" : "a").append(this.pulseRunning.get() ? "P" : "p").append(this.nextPulseRequested.get() ? "N" : "n").append(this.collector.hasDirty().get() ? "D" : "d").toString();
/*      */   }
/*      */ 
/*      */   private boolean setPulseRunning()
/*      */   {
/*  435 */     return this.pulseRunning.getAndSet(true);
/*      */   }
/*      */ 
/*      */   protected void endPulseRunning() {
/*  439 */     this.pulseRunning.set(false);
/*  440 */     if (debug)
/*  441 */       System.err.println(new StringBuilder().append("QT.endPulse: ").append(System.nanoTime()).toString());
/*      */   }
/*      */ 
/*      */   protected void pulse()
/*      */   {
/*  446 */     if (debug) {
/*  447 */       System.err.println(new StringBuilder().append("PULSE:(").append(System.nanoTime()).append("): ").append(pulseString()).toString());
/*      */     }
/*  449 */     if (!this.toolkitRunning.get())
/*  450 */       return;
/*      */     try
/*      */     {
/*  453 */       this.nextPulseRequested.set(false);
/*  454 */       if (this.animationRunnable != null) {
/*  455 */         this.animationRunning.set(true);
/*  456 */         this.animationRunnable.run();
/*      */       } else {
/*  458 */         this.animationRunning.set(false);
/*      */       }
/*  460 */       firePulse();
/*  461 */       this.collector.renderAll();
/*  462 */       View.notifyRenderingEnd();
/*      */     } catch (Throwable localThrowable) {
/*  464 */       localThrowable.printStackTrace(System.err);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void vsyncHint() {
/*  469 */     if (isVsyncEnabled()) {
/*  470 */       if (debug) {
/*  471 */         System.err.println(new StringBuilder().append("QT.vsyncHint: postPulse: ").append(System.nanoTime()).toString());
/*      */       }
/*  473 */       postPulse();
/*      */     }
/*      */   }
/*      */ 
/*      */   public AppletWindow createAppletWindow(long paramLong, String paramString) {
/*  478 */     GlassAppletWindow localGlassAppletWindow = new GlassAppletWindow(paramLong, paramString);
/*      */ 
/*  480 */     WindowStage.setAppletWindow(localGlassAppletWindow);
/*  481 */     return localGlassAppletWindow;
/*      */   }
/*      */ 
/*      */   public void closeAppletWindow() {
/*  485 */     GlassAppletWindow localGlassAppletWindow = WindowStage.getAppletWindow();
/*  486 */     if (null != localGlassAppletWindow) {
/*  487 */       localGlassAppletWindow.dispose();
/*  488 */       WindowStage.setAppletWindow(null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public TKStage createTKStage(StageStyle paramStageStyle)
/*      */   {
/*  494 */     assertToolkitRunning();
/*  495 */     return new WindowStage(verbose, paramStageStyle).init(this.systemMenu);
/*      */   }
/*      */ 
/*      */   public TKStage createTKStage(StageStyle paramStageStyle, boolean paramBoolean, Modality paramModality, TKStage paramTKStage)
/*      */   {
/*  500 */     assertToolkitRunning();
/*  501 */     return new WindowStage(verbose, paramStageStyle, paramBoolean, paramModality, paramTKStage).init(this.systemMenu);
/*      */   }
/*      */ 
/*      */   public Object enterNestedEventLoop(Object paramObject) {
/*  505 */     checkFxUserThread();
/*      */ 
/*  507 */     if (paramObject == null) {
/*  508 */       throw new NullPointerException();
/*      */     }
/*  510 */     if (this.eventLoopMap == null) {
/*  511 */       this.eventLoopMap = new HashMap();
/*      */     }
/*  513 */     if (this.eventLoopMap.containsKey(paramObject)) {
/*  514 */       throw new IllegalArgumentException(new StringBuilder().append("Key already associated with a running event loop: ").append(paramObject).toString());
/*      */     }
/*      */ 
/*  517 */     EventLoop localEventLoop = Application.GetApplication().createEventLoop();
/*  518 */     this.eventLoopMap.put(paramObject, localEventLoop);
/*      */ 
/*  520 */     return localEventLoop.enter();
/*      */   }
/*      */ 
/*      */   public void exitNestedEventLoop(Object paramObject1, Object paramObject2) {
/*  524 */     checkFxUserThread();
/*      */ 
/*  526 */     if (paramObject1 == null) {
/*  527 */       throw new NullPointerException();
/*      */     }
/*  529 */     if ((this.eventLoopMap == null) || (!this.eventLoopMap.containsKey(paramObject1))) {
/*  530 */       throw new IllegalArgumentException(new StringBuilder().append("Key not associated with a running event loop: ").append(paramObject1).toString());
/*      */     }
/*      */ 
/*  533 */     EventLoop localEventLoop = (EventLoop)this.eventLoopMap.get(paramObject1);
/*  534 */     this.eventLoopMap.remove(paramObject1);
/*  535 */     localEventLoop.leave(paramObject2);
/*      */   }
/*      */ 
/*      */   public TKStage createTKPopupStage(StageStyle paramStageStyle, Object paramObject) {
/*  539 */     assertToolkitRunning();
/*  540 */     return new PopupStage(verbose, paramObject).init(this.systemMenu);
/*      */   }
/*      */ 
/*      */   public TKStage createTKEmbeddedStage(HostInterface paramHostInterface) {
/*  544 */     assertToolkitRunning();
/*  545 */     return new EmbeddedStage(paramHostInterface);
/*      */   }
/*      */ 
/*      */   public ScreenConfigurationAccessor setScreenConfigurationListener(final TKScreenConfigurationListener paramTKScreenConfigurationListener)
/*      */   {
/*  581 */     Screen.setCallback(new Screen.SettingsChangedCallback() {
/*      */       public void settingsChanged() {
/*  583 */         QuantumToolkit.notifyScreenListener(paramTKScreenConfigurationListener);
/*      */       }
/*      */     });
/*  586 */     return screenAccessor;
/*      */   }
/*      */ 
/*      */   private static void notifyScreenListener(TKScreenConfigurationListener paramTKScreenConfigurationListener) {
/*  590 */     paramTKScreenConfigurationListener.screenConfigurationChanged();
/*      */   }
/*      */ 
/*      */   public Object getPrimaryScreen() {
/*  594 */     return Screen.getMainScreen();
/*      */   }
/*      */ 
/*      */   public List<?> getScreens() {
/*  598 */     return Screen.getScreens();
/*      */   }
/*      */ 
/*      */   public ImageLoader loadImage(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/*  602 */     return new PrismImageLoader2(paramString, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   public ImageLoader loadImage(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  607 */     return new PrismImageLoader2(paramInputStream, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   public AbstractRemoteResource<? extends ImageLoader> loadImageAsync(AsyncOperationListener paramAsyncOperationListener, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  613 */     return new PrismImageLoader2.AsyncImageLoader(paramAsyncOperationListener, paramString, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   public void defer(Runnable paramRunnable) {
/*  617 */     if (!this.toolkitRunning.get()) {
/*  618 */       throw new IllegalStateException("Attempt to call defer when toolkit not running");
/*      */     }
/*  620 */     Application.invokeLater(paramRunnable);
/*      */   }
/*      */ 
/*      */   public void exit() {
/*  624 */     notifyShutdownHooks();
/*  625 */     this.pulseTimer.stop();
/*      */ 
/*  628 */     Application localApplication = Application.GetApplication();
/*  629 */     localApplication.terminate();
/*      */ 
/*  631 */     dispose();
/*      */ 
/*  633 */     super.exit();
/*      */   }
/*      */ 
/*      */   public void dispose() {
/*  637 */     if (this.toolkitRunning.compareAndSet(true, false)) {
/*  638 */       this.renderer.stopRenderer();
/*      */ 
/*  640 */       if (PrismSettings.shutdownHook)
/*      */         try {
/*  642 */           Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
/*      */         }
/*      */         catch (IllegalStateException localIllegalStateException)
/*      */         {
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isForwardTraversalKey(javafx.scene.input.KeyEvent paramKeyEvent) {
/*  651 */     return (paramKeyEvent.getCode() == KeyCode.TAB) && (paramKeyEvent.getEventType() == javafx.scene.input.KeyEvent.KEY_PRESSED) && (!paramKeyEvent.isShiftDown());
/*      */   }
/*      */ 
/*      */   public boolean isBackwardTraversalKey(javafx.scene.input.KeyEvent paramKeyEvent)
/*      */   {
/*  657 */     return (paramKeyEvent.getCode() == KeyCode.TAB) && (paramKeyEvent.getEventType() == javafx.scene.input.KeyEvent.KEY_PRESSED) && (paramKeyEvent.isShiftDown());
/*      */   }
/*      */ 
/*      */   public Map<Object, Object> getContextMap()
/*      */   {
/*  664 */     return this.contextMap;
/*      */   }
/*      */ 
/*      */   public int getRefreshRate() {
/*  668 */     if (pulseHZ == null) {
/*  669 */       return 60;
/*      */     }
/*  671 */     return pulseHZ.intValue();
/*      */   }
/*      */ 
/*      */   public void setAnimationRunnable(DelayedRunnable paramDelayedRunnable)
/*      */   {
/*  677 */     if (paramDelayedRunnable != null) {
/*  678 */       this.animationRunning.set(true);
/*      */     }
/*  680 */     this.animationRunnable = paramDelayedRunnable;
/*      */   }
/*      */ 
/*      */   public void requestNextPulse() {
/*  684 */     this.nextPulseRequested.set(true);
/*      */   }
/*      */ 
/*      */   public void waitFor(Toolkit.Task paramTask) {
/*  688 */     if (paramTask.isFinished());
/*      */   }
/*      */ 
/*      */   public PrismPerspectiveCameraImpl createPerspectiveCamera()
/*      */   {
/*  694 */     return new PrismPerspectiveCameraImpl();
/*      */   }
/*      */ 
/*      */   public PrismParallelCameraImpl createParallelCamera() {
/*  698 */     return PrismParallelCameraImpl.getInstance();
/*      */   }
/*      */ 
/*      */   protected Object createColorPaint(javafx.scene.paint.Color paramColor) {
/*  702 */     return new com.sun.prism.paint.Color((float)paramColor.getRed(), (float)paramColor.getGreen(), (float)paramColor.getBlue(), (float)paramColor.getOpacity());
/*      */   }
/*      */ 
/*      */   private com.sun.prism.paint.Color toPrismColor(javafx.scene.paint.Color paramColor)
/*      */   {
/*  708 */     return (com.sun.prism.paint.Color)paramColor.impl_getPlatformPaint();
/*      */   }
/*      */ 
/*      */   private List<com.sun.prism.paint.Stop> convertStops(List<javafx.scene.paint.Stop> paramList) {
/*  712 */     ArrayList localArrayList = new ArrayList(paramList.size());
/*      */ 
/*  714 */     for (javafx.scene.paint.Stop localStop : paramList) {
/*  715 */       localArrayList.add(new com.sun.prism.paint.Stop(toPrismColor(localStop.getColor()), (float)localStop.getOffset()));
/*      */     }
/*      */ 
/*  718 */     return localArrayList;
/*      */   }
/*      */ 
/*      */   protected Object createLinearGradientPaint(javafx.scene.paint.LinearGradient paramLinearGradient) {
/*  722 */     int i = 2;
/*  723 */     CycleMethod localCycleMethod = paramLinearGradient.getCycleMethod();
/*  724 */     if (localCycleMethod == CycleMethod.NO_CYCLE)
/*  725 */       i = 0;
/*  726 */     else if (localCycleMethod == CycleMethod.REFLECT) {
/*  727 */       i = 1;
/*      */     }
/*      */ 
/*  730 */     List localList = convertStops(paramLinearGradient.getStops());
/*  731 */     return new com.sun.prism.paint.LinearGradient((float)paramLinearGradient.getStartX(), (float)paramLinearGradient.getStartY(), (float)paramLinearGradient.getEndX(), (float)paramLinearGradient.getEndY(), null, paramLinearGradient.isProportional(), i, localList);
/*      */   }
/*      */ 
/*      */   protected Object createRadialGradientPaint(javafx.scene.paint.RadialGradient paramRadialGradient)
/*      */   {
/*  738 */     float f1 = (float)paramRadialGradient.getCenterX();
/*  739 */     float f2 = (float)paramRadialGradient.getCenterY();
/*  740 */     float f3 = (float)paramRadialGradient.getFocusAngle();
/*  741 */     float f4 = (float)paramRadialGradient.getFocusDistance();
/*      */ 
/*  743 */     int i = 0;
/*  744 */     if (paramRadialGradient.getCycleMethod() == CycleMethod.NO_CYCLE)
/*  745 */       i = 0;
/*  746 */     else if (paramRadialGradient.getCycleMethod() == CycleMethod.REFLECT)
/*  747 */       i = 1;
/*      */     else {
/*  749 */       i = 2;
/*      */     }
/*      */ 
/*  753 */     List localList = convertStops(paramRadialGradient.getStops());
/*  754 */     return new com.sun.prism.paint.RadialGradient(f1, f2, f3, f4, (float)paramRadialGradient.getRadius(), null, paramRadialGradient.isProportional(), i, localList);
/*      */   }
/*      */ 
/*      */   protected Object createImagePatternPaint(javafx.scene.paint.ImagePattern paramImagePattern)
/*      */   {
/*  760 */     if (paramImagePattern.getImage() == null) {
/*  761 */       return com.sun.prism.paint.Color.TRANSPARENT;
/*      */     }
/*  763 */     return new com.sun.prism.paint.ImagePattern((com.sun.prism.Image)paramImagePattern.getImage().impl_getPlatformImage(), (float)paramImagePattern.getX(), (float)paramImagePattern.getY(), (float)paramImagePattern.getWidth(), (float)paramImagePattern.getHeight(), paramImagePattern.isProportional());
/*      */   }
/*      */ 
/*      */   private void initStroke(PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat1, float[] paramArrayOfFloat, float paramFloat2)
/*      */   {
/*      */     int i;
/*  779 */     if (paramStrokeType == PGShape.StrokeType.CENTERED)
/*  780 */       i = 0;
/*  781 */     else if (paramStrokeType == PGShape.StrokeType.INSIDE)
/*  782 */       i = 1;
/*      */     else
/*  784 */       i = 2;
/*      */     int j;
/*  788 */     if (paramStrokeLineCap == PGShape.StrokeLineCap.BUTT)
/*  789 */       j = 0;
/*  790 */     else if (paramStrokeLineCap == PGShape.StrokeLineCap.SQUARE)
/*  791 */       j = 2;
/*      */     else
/*  793 */       j = 1;
/*      */     int k;
/*  797 */     if (paramStrokeLineJoin == PGShape.StrokeLineJoin.BEVEL)
/*  798 */       k = 2;
/*  799 */     else if (paramStrokeLineJoin == PGShape.StrokeLineJoin.MITER)
/*  800 */       k = 0;
/*      */     else {
/*  802 */       k = 1;
/*      */     }
/*      */ 
/*  805 */     tmpStroke.set(i, (float)paramDouble, j, k, paramFloat1);
/*  806 */     if ((paramArrayOfFloat != null) && (paramArrayOfFloat.length > 0))
/*  807 */       tmpStroke.set(paramArrayOfFloat, paramFloat2);
/*      */     else
/*  809 */       tmpStroke.set(null, 0.0F);
/*      */   }
/*      */ 
/*      */   public void accumulateStrokeBounds(Shape paramShape, float[] paramArrayOfFloat, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat, BaseTransform paramBaseTransform)
/*      */   {
/*  823 */     initStroke(paramStrokeType, paramDouble, paramStrokeLineCap, paramStrokeLineJoin, paramFloat, null, 0.0F);
/*  824 */     if (paramBaseTransform.isTranslateOrIdentity())
/*  825 */       tmpStroke.accumulateShapeBounds(paramArrayOfFloat, paramShape, paramBaseTransform);
/*      */     else
/*  827 */       Shape.accumulate(paramArrayOfFloat, tmpStroke.createStrokedShape(paramShape), paramBaseTransform);
/*      */   }
/*      */ 
/*      */   public boolean strokeContains(Shape paramShape, double paramDouble1, double paramDouble2, PGShape.StrokeType paramStrokeType, double paramDouble3, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat)
/*      */   {
/*  839 */     initStroke(paramStrokeType, paramDouble3, paramStrokeLineCap, paramStrokeLineJoin, paramFloat, null, 0.0F);
/*      */ 
/*  841 */     return tmpStroke.createStrokedShape(paramShape).contains((float)paramDouble1, (float)paramDouble2);
/*      */   }
/*      */ 
/*      */   public Shape createStrokedShape(Shape paramShape, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat1, float[] paramArrayOfFloat, float paramFloat2)
/*      */   {
/*  853 */     initStroke(paramStrokeType, paramDouble, paramStrokeLineCap, paramStrokeLineJoin, paramFloat1, paramArrayOfFloat, paramFloat2);
/*      */ 
/*  855 */     return tmpStroke.createStrokedShape(paramShape);
/*      */   }
/*      */ 
/*      */   public Dimension2D getBestCursorSize(int paramInt1, int paramInt2) {
/*  859 */     return CursorUtils.getBestCursorSize(paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public int getMaximumCursorColors() {
/*  863 */     return 2;
/*      */   }
/*      */ 
/*      */   public int getKeyCodeForChar(String paramString)
/*      */   {
/*  868 */     return paramString.length() == 1 ? com.sun.glass.events.KeyEvent.getKeyCodeForChar(paramString.charAt(0)) : 0;
/*      */   }
/*      */ 
/*      */   public MouseEvent convertMouseEventToFX(Object paramObject)
/*      */   {
/*  875 */     if ((paramObject instanceof GlassPrismMouseEvent))
/*  876 */       return PrismEventUtils.glassMouseEventToFX((GlassPrismMouseEvent)paramObject);
/*  877 */     if ((paramObject instanceof MouseEvent)) {
/*  878 */       return (MouseEvent)paramObject;
/*      */     }
/*  880 */     return null;
/*      */   }
/*      */ 
/*      */   public javafx.scene.input.KeyEvent convertKeyEventToFX(Object paramObject) {
/*  884 */     if ((paramObject instanceof GlassPrismKeyEvent))
/*  885 */       return PrismEventUtils.glassKeyEventToFX((GlassPrismKeyEvent)paramObject);
/*  886 */     if ((paramObject instanceof javafx.scene.input.KeyEvent)) {
/*  887 */       return (javafx.scene.input.KeyEvent)paramObject;
/*      */     }
/*  889 */     return null;
/*      */   }
/*      */ 
/*      */   public InputMethodEvent convertInputMethodEventToFX(Object paramObject) {
/*  893 */     if ((paramObject instanceof GlassPrismInputMethodEvent))
/*  894 */       return PrismEventUtils.glassInputMethodEventToFX((GlassPrismInputMethodEvent)paramObject);
/*  895 */     if ((paramObject instanceof InputMethodEvent)) {
/*  896 */       return (InputMethodEvent)paramObject;
/*      */     }
/*      */ 
/*  899 */     return null;
/*      */   }
/*      */ 
/*      */   public PathElement[] convertShapeToFXPath(Object paramObject) {
/*  903 */     if (paramObject == null) {
/*  904 */       return new PathElement[0];
/*      */     }
/*  906 */     ArrayList localArrayList = new ArrayList();
/*      */ 
/*  909 */     Shape localShape = (Shape)paramObject;
/*  910 */     PathIterator localPathIterator = localShape.getPathIterator(null);
/*  911 */     PathIteratorHelper localPathIteratorHelper = new PathIteratorHelper(localPathIterator);
/*  912 */     PathIteratorHelper.Struct localStruct = new PathIteratorHelper.Struct();
/*      */ 
/*  914 */     while (!localPathIteratorHelper.isDone())
/*      */     {
/*  917 */       int i = localPathIteratorHelper.getWindingRule() == 0 ? 1 : 0;
/*  918 */       int j = localPathIteratorHelper.currentSegment(localStruct);
/*      */       Object localObject;
/*  920 */       if (j == 0)
/*  921 */         localObject = new MoveTo(localStruct.f0, localStruct.f1);
/*  922 */       else if (j == 1)
/*  923 */         localObject = new LineTo(localStruct.f0, localStruct.f1);
/*  924 */       else if (j == 2) {
/*  925 */         localObject = new QuadCurveTo(localStruct.f0, localStruct.f1, localStruct.f2, localStruct.f3);
/*      */       }
/*  930 */       else if (j == 3) {
/*  931 */         localObject = new CubicCurveTo(localStruct.f0, localStruct.f1, localStruct.f2, localStruct.f3, localStruct.f4, localStruct.f5);
/*      */       }
/*  938 */       else if (j == 4)
/*  939 */         localObject = new ClosePath();
/*      */       else {
/*  941 */         throw new IllegalStateException(new StringBuilder().append("Invalid element type: ").append(j).toString());
/*      */       }
/*  943 */       localPathIteratorHelper.next();
/*  944 */       localArrayList.add(localObject);
/*      */     }
/*      */ 
/*  947 */     return (PathElement[])localArrayList.toArray(new PathElement[localArrayList.size()]);
/*      */   }
/*      */ 
/*      */   public HitInfo convertHitInfoToFX(Object paramObject) {
/*  951 */     Integer localInteger = (Integer)paramObject;
/*  952 */     HitInfo localHitInfo = new HitInfo();
/*  953 */     localHitInfo.setCharIndex(localInteger.intValue());
/*  954 */     localHitInfo.setLeading(true);
/*  955 */     return localHitInfo;
/*      */   }
/*      */ 
/*      */   public Filterable toFilterable(javafx.scene.image.Image paramImage) {
/*  959 */     return PrImage.create((com.sun.prism.Image)paramImage.impl_getPlatformImage());
/*      */   }
/*      */ 
/*      */   public FilterContext getFilterContext(Object paramObject) {
/*  963 */     if ((paramObject == null) || (!(paramObject instanceof Screen))) {
/*  964 */       return PrFilterContext.getDefaultInstance();
/*      */     }
/*  966 */     Screen localScreen = (Screen)paramObject;
/*  967 */     return PrFilterContext.getInstance(localScreen);
/*      */   }
/*      */ 
/*      */   public AbstractMasterTimer getMasterTimer() {
/*  971 */     return MasterTimer.getInstance();
/*      */   }
/*      */ 
/*      */   public FontLoader getFontLoader() {
/*  975 */     return PrismFontLoader.getInstance();
/*      */   }
/*      */ 
/*      */   public PGArc createPGArc() {
/*  979 */     return new NGArc();
/*      */   }
/*      */ 
/*      */   public PGCircle createPGCircle() {
/*  983 */     return new NGCircle();
/*      */   }
/*      */ 
/*      */   public PGCubicCurve createPGCubicCurve() {
/*  987 */     return new NGCubicCurve();
/*      */   }
/*      */ 
/*      */   public PGEllipse createPGEllipse() {
/*  991 */     return new NGEllipse();
/*      */   }
/*      */ 
/*      */   public PGLine createPGLine() {
/*  995 */     return new NGLine();
/*      */   }
/*      */ 
/*      */   public PGPath createPGPath() {
/*  999 */     return new NGPath();
/*      */   }
/*      */ 
/*      */   public PGSVGPath createPGSVGPath() {
/* 1003 */     return new NGSVGPath();
/*      */   }
/*      */ 
/*      */   public PGPolygon createPGPolygon() {
/* 1007 */     return new NGPolygon();
/*      */   }
/*      */ 
/*      */   public PGPolyline createPGPolyline() {
/* 1011 */     return new NGPolyline();
/*      */   }
/*      */ 
/*      */   public PGQuadCurve createPGQuadCurve() {
/* 1015 */     return new NGQuadCurve();
/*      */   }
/*      */ 
/*      */   public PGRectangle createPGRectangle() {
/* 1019 */     return new NGRectangle();
/*      */   }
/*      */ 
/*      */   public PGImageView createPGImageView() {
/* 1023 */     return new NGImageView();
/*      */   }
/*      */ 
/*      */   public PGMediaView createPGMediaView() {
/* 1027 */     return new NGMediaView();
/*      */   }
/*      */ 
/*      */   public PGGroup createPGGroup() {
/* 1031 */     return new NGGroup();
/*      */   }
/*      */ 
/*      */   public PGText createPGText() {
/* 1035 */     return new NGText();
/*      */   }
/*      */ 
/*      */   public PGRegion createPGRegion() {
/* 1039 */     return new NGRegion();
/*      */   }
/*      */ 
/*      */   public PGCanvas createPGCanvas() {
/* 1043 */     return new NGCanvas();
/*      */   }
/*      */ 
/*      */   public Object createSVGPathObject(SVGPath paramSVGPath) {
/* 1047 */     int i = paramSVGPath.getFillRule() == FillRule.NON_ZERO ? 1 : 0;
/* 1048 */     Path2D localPath2D = new Path2D(i);
/* 1049 */     localPath2D.appendSVGPath(paramSVGPath.getContent());
/* 1050 */     return localPath2D;
/*      */   }
/*      */ 
/*      */   public Path2D createSVGPath2D(SVGPath paramSVGPath) {
/* 1054 */     int i = paramSVGPath.getFillRule() == FillRule.NON_ZERO ? 1 : 0;
/* 1055 */     Path2D localPath2D = new Path2D(i);
/* 1056 */     localPath2D.appendSVGPath(paramSVGPath.getContent());
/* 1057 */     return localPath2D;
/*      */   }
/*      */ 
/*      */   public boolean imageContains(Object paramObject, float paramFloat1, float paramFloat2) {
/* 1061 */     if (paramObject == null) {
/* 1062 */       return false;
/*      */     }
/*      */ 
/* 1065 */     com.sun.prism.Image localImage = (com.sun.prism.Image)paramObject;
/* 1066 */     int i = (int)paramFloat1 + localImage.getMinX();
/* 1067 */     int j = (int)paramFloat2 + localImage.getMinY();
/*      */ 
/* 1069 */     if (localImage.getPixelFormat().isOpaque())
/* 1070 */       return true;
/*      */     Object localObject;
/*      */     int k;
/* 1073 */     if (localImage.getPixelFormat() == PixelFormat.INT_ARGB_PRE) {
/* 1074 */       localObject = (IntBuffer)localImage.getPixelBuffer();
/* 1075 */       k = i + j * localImage.getRowLength();
/* 1076 */       if (k >= ((IntBuffer)localObject).limit()) {
/* 1077 */         return false;
/*      */       }
/* 1079 */       return (((IntBuffer)localObject).get(k) & 0xFF000000) != 0;
/*      */     }
/* 1081 */     if (localImage.getPixelFormat() == PixelFormat.BYTE_BGRA_PRE) {
/* 1082 */       localObject = (ByteBuffer)localImage.getPixelBuffer();
/* 1083 */       k = i * localImage.getBytesPerPixelUnit() + j * localImage.getScanlineStride() + 3;
/* 1084 */       if (k >= ((ByteBuffer)localObject).limit()) {
/* 1085 */         return false;
/*      */       }
/* 1087 */       return (((ByteBuffer)localObject).get(k) & 0xFF) != 0;
/*      */     }
/* 1089 */     if (localImage.getPixelFormat() == PixelFormat.BYTE_ALPHA) {
/* 1090 */       localObject = (ByteBuffer)localImage.getPixelBuffer();
/* 1091 */       k = i * localImage.getBytesPerPixelUnit() + j * localImage.getScanlineStride();
/* 1092 */       if (k >= ((ByteBuffer)localObject).limit()) {
/* 1093 */         return false;
/*      */       }
/* 1095 */       return (((ByteBuffer)localObject).get(k) & 0xFF) != 0;
/*      */     }
/*      */ 
/* 1098 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isSupported(ConditionalFeature paramConditionalFeature)
/*      */   {
/* 1103 */     if (paramConditionalFeature == ConditionalFeature.SCENE3D)
/* 1104 */       return GraphicsPipeline.getPipeline().is3DSupported();
/* 1105 */     if (paramConditionalFeature == ConditionalFeature.EFFECT)
/* 1106 */       return GraphicsPipeline.getPipeline().isEffectSupported();
/* 1107 */     if (paramConditionalFeature == ConditionalFeature.SHAPE_CLIP)
/* 1108 */       return true;
/* 1109 */     if (paramConditionalFeature == ConditionalFeature.INPUT_METHOD)
/* 1110 */       return false;
/* 1111 */     if (paramConditionalFeature == ConditionalFeature.TRANSPARENT_WINDOW) {
/* 1112 */       return Application.GetApplication().supportsTransparentWindows();
/*      */     }
/* 1114 */     return false;
/*      */   }
/*      */ 
/*      */   public TKClipboard getSystemClipboard()
/*      */   {
/* 1120 */     if (this.clipboard == null) {
/* 1121 */       this.clipboard = QuantumClipboard.getClipboardInstance(new ClipboardAssistance("SYSTEM"));
/*      */     }
/* 1123 */     return this.clipboard;
/*      */   }
/*      */ 
/*      */   public TKSystemMenu getSystemMenu()
/*      */   {
/* 1128 */     return this.systemMenu;
/*      */   }
/*      */ 
/*      */   public TKClipboard getNamedClipboard(String paramString)
/*      */   {
/* 1133 */     return null;
/*      */   }
/*      */ 
/*      */   public DragEvent convertDragRecognizedEventToFX(Object paramObject, Dragboard paramDragboard) {
/* 1137 */     return PrismEventUtils.glassDragGestureToFX((GlassDragEvent)paramObject, paramDragboard);
/*      */   }
/*      */ 
/*      */   public DragEvent convertDragSourceEventToFX(Object paramObject, Dragboard paramDragboard) {
/* 1141 */     return PrismEventUtils.glassDragSourceEventToFX((GlassDragEvent)paramObject, paramDragboard);
/*      */   }
/*      */ 
/*      */   public DragEvent convertDropTargetEventToFX(Object paramObject, Dragboard paramDragboard) {
/* 1145 */     return PrismEventUtils.glassDropTargetEventToFX((GlassDragEvent)paramObject, paramDragboard);
/*      */   }
/*      */ 
/*      */   public Dragboard createDragboard()
/*      */   {
/* 1152 */     QuantumClipboard localQuantumClipboard = QuantumClipboard.getDragboardInstance(new ClipboardAssistance("DND") {
/*      */       public void actionPerformed(final int paramAnonymousInt) {
/* 1154 */         AccessController.doPrivileged(new PrivilegedAction()
/*      */         {
/*      */           public Void run() {
/* 1157 */             if ((QuantumToolkit.this.dragSourceClipboard != null) && (QuantumToolkit.this.dragSourceScene != null) && (QuantumToolkit.this.dragSourceScene.dragSourceListener != null)) {
/* 1158 */               Dragboard localDragboard = Dragboard.impl_create(QuantumToolkit.this.dragSourceClipboard);
/* 1159 */               GlassDragEvent localGlassDragEvent = new GlassDragEvent(2, 0, 0, 0, 0, localDragboard, 0, QuantumToolkit.this.dragSourceScene, paramAnonymousInt);
/*      */ 
/* 1161 */               QuantumToolkit.this.dragSourceScene.dragSourceListener.dragDropEnd(localGlassDragEvent);
/*      */             }
/* 1163 */             QuantumToolkit.this.dragSourceClipboard = null;
/* 1164 */             QuantumToolkit.this.dragSourceScene = null;
/* 1165 */             return null;
/*      */           }
/*      */         }
/*      */         , QuantumToolkit.this.dragSourceScene.getAccessControlContext());
/*      */       }
/*      */     });
/* 1170 */     return Dragboard.impl_create(localQuantumClipboard);
/*      */   }
/*      */ 
/*      */   public void startDrag(Object paramObject, Set<TransferMode> paramSet, TKDragSourceListener paramTKDragSourceListener, Dragboard paramDragboard) {
/* 1174 */     if (paramDragboard == null)
/* 1175 */       throw new IllegalArgumentException("dragboard should not be null");
/*      */     GlassScene localGlassScene;
/* 1179 */     if ((paramObject instanceof GlassDragEvent)) {
/* 1180 */       localObject = (GlassDragEvent)paramObject;
/* 1181 */       localGlassScene = ((GlassDragEvent)localObject).getGlassScene();
/* 1182 */     } else if ((paramObject instanceof GlassScene)) {
/* 1183 */       localGlassScene = (GlassScene)paramObject;
/*      */     } else {
/* 1185 */       throw new IllegalArgumentException("o should be either a GlassScene, or a GlassDragEvent instance");
/*      */     }
/* 1187 */     localGlassScene.setTKDragSourceListener(paramTKDragSourceListener);
/*      */ 
/* 1189 */     Object localObject = (QuantumClipboard)paramDragboard.impl_getPeer();
/*      */ 
/* 1191 */     this.dragSourceClipboard = ((QuantumClipboard)localObject);
/* 1192 */     this.dragSourceScene = localGlassScene;
/*      */ 
/* 1194 */     ((QuantumClipboard)localObject).setSupportedTransferMode(paramSet);
/* 1195 */     ((QuantumClipboard)localObject).flush();
/*      */ 
/* 1198 */     ((QuantumClipboard)localObject).close();
/*      */   }
/*      */ 
/*      */   public void enableDrop(TKScene paramTKScene, TKDropTargetListener paramTKDropTargetListener)
/*      */   {
/* 1203 */     assert ((paramTKScene instanceof GlassScene));
/*      */ 
/* 1205 */     GlassScene localGlassScene = (GlassScene)paramTKScene;
/* 1206 */     localGlassScene.setTKDropTargetListener(paramTKDropTargetListener);
/*      */   }
/*      */ 
/*      */   public void registerDragGestureListener(TKScene paramTKScene, Set<TransferMode> paramSet, TKDragGestureListener paramTKDragGestureListener)
/*      */   {
/* 1211 */     assert ((paramTKScene instanceof GlassScene));
/*      */ 
/* 1213 */     GlassScene localGlassScene = (GlassScene)paramTKScene;
/* 1214 */     localGlassScene.setTKDragGestureListener(paramTKDragGestureListener);
/*      */   }
/*      */ 
/*      */   public boolean isAppletDragSupported()
/*      */   {
/* 1220 */     return false;
/*      */   }
/*      */ 
/*      */   public void installInputMethodRequests(TKScene paramTKScene, InputMethodRequests paramInputMethodRequests)
/*      */   {
/* 1226 */     assert ((paramTKScene instanceof GlassScene));
/*      */ 
/* 1228 */     GlassScene localGlassScene = (GlassScene)paramTKScene;
/* 1229 */     localGlassScene.setInputMethodRequests(paramInputMethodRequests);
/*      */   }
/*      */ 
/*      */   public boolean isExternalFormatSupported(Class paramClass)
/*      */   {
/* 1299 */     return ExternalImageTools.isFormatSupported(paramClass);
/*      */   }
/*      */ 
/*      */   public ImageLoader loadPlatformImage(Object paramObject) {
/* 1303 */     if ((paramObject instanceof QuantumImage)) {
/* 1304 */       return (QuantumImage)paramObject;
/*      */     }
/*      */ 
/* 1307 */     if ((paramObject instanceof com.sun.prism.Image)) {
/* 1308 */       return new QuantumImage((com.sun.prism.Image)paramObject);
/*      */     }
/*      */ 
/* 1311 */     ExternalImageTools.IImporter localIImporter = ExternalImageTools.getImporter(paramObject);
/*      */ 
/* 1313 */     if (localIImporter != null) {
/* 1314 */       return new QuantumImage(localIImporter.loadExternalImage(paramObject));
/*      */     }
/*      */ 
/* 1317 */     throw new UnsupportedOperationException("unsupported class for loadPlatformImage");
/*      */   }
/*      */ 
/*      */   public PlatformImage createPlatformImage(int paramInt1, int paramInt2)
/*      */   {
/* 1322 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt1 * paramInt2 * 4);
/* 1323 */     return com.sun.prism.Image.fromByteBgraPreData(localByteBuffer, paramInt1, paramInt2);
/*      */   }
/*      */ 
/*      */   public Object renderToImage(Toolkit.ImageRenderingContext paramImageRenderingContext)
/*      */   {
/* 1328 */     Object localObject1 = paramImageRenderingContext.platformImage;
/* 1329 */     final Toolkit.ImageRenderingContext localImageRenderingContext = paramImageRenderingContext;
/* 1330 */     final Paint localPaint = (paramImageRenderingContext.platformPaint instanceof Paint) ? (Paint)paramImageRenderingContext.platformPaint : null;
/*      */ 
/* 1333 */     RenderJob localRenderJob = new RenderJob(new Runnable()
/*      */     {
/*      */       private com.sun.prism.paint.Color getClearColor() {
/* 1336 */         if (localPaint == null)
/* 1337 */           return com.sun.prism.paint.Color.WHITE;
/* 1338 */         if (localPaint.getType() == Paint.Type.COLOR)
/* 1339 */           return (com.sun.prism.paint.Color)localPaint;
/* 1340 */         if (localPaint.isOpaque()) {
/* 1341 */           return com.sun.prism.paint.Color.TRANSPARENT;
/*      */         }
/* 1343 */         return com.sun.prism.paint.Color.WHITE;
/*      */       }
/*      */ 
/*      */       private void draw(Graphics paramAnonymousGraphics, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
/*      */       {
/* 1348 */         paramAnonymousGraphics.setDepthBuffer(localImageRenderingContext.depthBuffer);
/*      */ 
/* 1350 */         paramAnonymousGraphics.clear(getClearColor());
/* 1351 */         if ((localPaint != null) && (localPaint.getType() != Paint.Type.COLOR))
/*      */         {
/* 1353 */           paramAnonymousGraphics.getRenderTarget().setOpaque(localPaint.isOpaque());
/* 1354 */           paramAnonymousGraphics.setPaint(localPaint);
/* 1355 */           paramAnonymousGraphics.fillQuad(0.0F, 0.0F, paramAnonymousInt3, paramAnonymousInt4);
/*      */         }
/*      */ 
/* 1359 */         if ((paramAnonymousInt1 != 0) || (paramAnonymousInt2 != 0)) {
/* 1360 */           paramAnonymousGraphics.translate(-paramAnonymousInt1, -paramAnonymousInt2);
/*      */         }
/* 1362 */         if (localImageRenderingContext.transform != null) {
/* 1363 */           paramAnonymousGraphics.transform(localImageRenderingContext.transform);
/*      */         }
/*      */ 
/* 1366 */         if (localImageRenderingContext.root != null) {
/* 1367 */           if ((localImageRenderingContext.camera instanceof PrismCameraImpl)) {
/* 1368 */             paramAnonymousGraphics.setCamera((PrismCameraImpl)localImageRenderingContext.camera);
/*      */           }
/*      */ 
/* 1371 */           NGNode localNGNode = (NGNode)localImageRenderingContext.root;
/* 1372 */           localNGNode.render(paramAnonymousGraphics);
/*      */         }
/*      */       }
/*      */ 
/*      */       public void run()
/*      */       {
/* 1380 */         ResourceFactory localResourceFactory = GraphicsPipeline.getDefaultResourceFactory();
/*      */ 
/* 1382 */         if (!localResourceFactory.isDeviceReady()) {
/* 1383 */           return;
/*      */         }
/*      */ 
/* 1386 */         int i = localImageRenderingContext.x;
/* 1387 */         int j = localImageRenderingContext.y;
/* 1388 */         int k = localImageRenderingContext.width;
/* 1389 */         int m = localImageRenderingContext.height;
/*      */ 
/* 1391 */         if ((k <= 0) || (m <= 0)) {
/* 1392 */           return;
/*      */         }
/*      */ 
/* 1395 */         RenderingContext localRenderingContext = localResourceFactory.createRenderingContext(null);
/*      */         try
/*      */         {
/* 1398 */           localRenderingContext.begin();
/*      */ 
/* 1400 */           QuantumToolkit.QuantumImage localQuantumImage = (localImageRenderingContext.platformImage instanceof QuantumToolkit.QuantumImage) ? (QuantumToolkit.QuantumImage)localImageRenderingContext.platformImage : new QuantumToolkit.QuantumImage(null);
/*      */ 
/* 1403 */           RTTexture localRTTexture = localQuantumImage.getRT(k, m, localResourceFactory);
/*      */ 
/* 1405 */           if (localRTTexture == null)
/*      */           {
/*      */             return;
/*      */           }
/* 1409 */           Graphics localGraphics = localRTTexture.createGraphics();
/*      */ 
/* 1411 */           draw(localGraphics, i, j, k, m);
/*      */ 
/* 1413 */           int[] arrayOfInt = localQuantumImage.rt.getPixels();
/*      */ 
/* 1415 */           if (arrayOfInt != null) {
/* 1416 */             localQuantumImage.setImage(com.sun.prism.Image.fromIntArgbPreData(arrayOfInt, k, m));
/*      */           } else {
/* 1418 */             IntBuffer localIntBuffer = IntBuffer.allocate(k * m);
/* 1419 */             if (localQuantumImage.rt.readPixels(localIntBuffer)) {
/* 1420 */               localQuantumImage.setImage(com.sun.prism.Image.fromIntArgbPreData(localIntBuffer, k, m));
/*      */             } else {
/* 1422 */               localQuantumImage.dispose();
/* 1423 */               localQuantumImage = null;
/*      */             }
/*      */           }
/*      */ 
/* 1427 */           localImageRenderingContext.platformImage = localQuantumImage;
/*      */         }
/*      */         catch (Throwable localThrowable) {
/* 1430 */           localThrowable.printStackTrace(System.err);
/*      */         } finally {
/* 1432 */           Disposer.cleanUp();
/* 1433 */           localRenderingContext.end();
/*      */         }
/*      */       }
/*      */     });
/* 1438 */     final CountDownLatch localCountDownLatch = new CountDownLatch(1);
/* 1439 */     localRenderJob.setCompletionListener(new CompletionListener() {
/*      */       public void done(RenderJob paramAnonymousRenderJob) {
/* 1441 */         localCountDownLatch.countDown();
/*      */       }
/*      */     });
/* 1444 */     Future localFuture = addRenderJob(localRenderJob);
/*      */     while (true) {
/*      */       try
/*      */       {
/* 1448 */         localCountDownLatch.await();
/*      */       }
/*      */       catch (InterruptedException localInterruptedException) {
/* 1451 */         localInterruptedException.printStackTrace();
/*      */       }
/*      */     }
/*      */ 
/* 1455 */     Object localObject2 = localImageRenderingContext.platformImage;
/* 1456 */     localImageRenderingContext.platformImage = localObject1;
/*      */ 
/* 1458 */     return localObject2;
/*      */   }
/*      */ 
/*      */   public Object toExternalImage(Object paramObject1, Object paramObject2)
/*      */   {
/* 1463 */     boolean bool = paramObject1 instanceof com.sun.prism.Image;
/*      */ 
/* 1465 */     Object localObject = (bool) && (paramObject2 != null) ? ExternalImageTools.getExporter(paramObject2) : null;
/*      */ 
/* 1468 */     return localObject != null ? localObject.exportPrismImage((com.sun.prism.Image)paramObject1, paramObject2) : null;
/*      */   }
/*      */ 
/*      */   public List<File> showFileChooser(TKStage paramTKStage, String paramString, File paramFile, FileChooserType paramFileChooserType, List<FileChooser.ExtensionFilter> paramList)
/*      */   {
/* 1479 */     WindowStage localWindowStage = null;
/*      */     try
/*      */     {
/* 1484 */       localWindowStage = blockOwnerStage(paramTKStage);
/*      */ 
/* 1486 */       return CommonDialogs.showFileChooser((paramTKStage instanceof WindowStage) ? ((WindowStage)paramTKStage).getPlatformWindow() : null, paramFile, paramString, paramFileChooserType == FileChooserType.SAVE ? 1 : 0, paramFileChooserType == FileChooserType.OPEN_MULTIPLE, convertExtensionFilters(paramList));
/*      */     }
/*      */     finally
/*      */     {
/* 1498 */       if (localWindowStage != null)
/* 1499 */         localWindowStage.setEnabled(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   public File showDirectoryChooser(TKStage paramTKStage, String paramString, File paramFile)
/*      */   {
/* 1508 */     WindowStage localWindowStage = null;
/*      */     try
/*      */     {
/* 1513 */       localWindowStage = blockOwnerStage(paramTKStage);
/*      */ 
/* 1515 */       return CommonDialogs.showFolderChooser((paramTKStage instanceof WindowStage) ? ((WindowStage)paramTKStage).getPlatformWindow() : null, paramFile, paramString);
/*      */     }
/*      */     finally
/*      */     {
/* 1521 */       if (localWindowStage != null)
/* 1522 */         localWindowStage.setEnabled(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private WindowStage blockOwnerStage(TKStage paramTKStage)
/*      */   {
/* 1528 */     if ((paramTKStage instanceof WindowStage)) {
/* 1529 */       TKStage localTKStage = ((WindowStage)paramTKStage).getOwner();
/* 1530 */       if ((localTKStage instanceof WindowStage)) {
/* 1531 */         WindowStage localWindowStage = (WindowStage)localTKStage;
/* 1532 */         localWindowStage.setEnabled(false);
/* 1533 */         return localWindowStage;
/*      */       }
/*      */     }
/*      */ 
/* 1537 */     return null;
/*      */   }
/*      */ 
/*      */   private static List<CommonDialogs.ExtensionFilter> convertExtensionFilters(List<FileChooser.ExtensionFilter> paramList)
/*      */   {
/* 1543 */     CommonDialogs.ExtensionFilter[] arrayOfExtensionFilter = new CommonDialogs.ExtensionFilter[paramList.size()];
/*      */ 
/* 1546 */     int i = 0;
/*      */ 
/* 1548 */     for (FileChooser.ExtensionFilter localExtensionFilter : paramList) {
/* 1549 */       arrayOfExtensionFilter[(i++)] = new CommonDialogs.ExtensionFilter(localExtensionFilter.getDescription(), localExtensionFilter.getExtensions());
/*      */     }
/*      */ 
/* 1555 */     return Arrays.asList(arrayOfExtensionFilter);
/*      */   }
/*      */ 
/*      */   public long getMultiClickTime()
/*      */   {
/* 1560 */     return View.getMultiClickTime();
/*      */   }
/*      */ 
/*      */   public int getMultiClickMaxX()
/*      */   {
/* 1565 */     return View.getMultiClickMaxX();
/*      */   }
/*      */ 
/*      */   public int getMultiClickMaxY()
/*      */   {
/* 1570 */     return View.getMultiClickMaxY();
/*      */   }
/*      */ 
/*      */   static class QuantumImage
/*      */     implements ImageLoader, ResourceFactoryListener
/*      */   {
/*      */     private RTTexture rt;
/*      */     private com.sun.prism.Image image;
/*      */     private ResourceFactory rf;
/*      */ 
/*      */     QuantumImage(com.sun.prism.Image paramImage)
/*      */     {
/* 1240 */       this.image = paramImage;
/*      */     }
/*      */ 
/*      */     RTTexture getRT(int paramInt1, int paramInt2, ResourceFactory paramResourceFactory) {
/* 1244 */       int i = (this.rt != null) && (this.rf == paramResourceFactory) && (this.rt.getContentWidth() == paramInt1) && (this.rt.getContentHeight() == paramInt2) ? 1 : 0;
/*      */ 
/* 1247 */       if (i == 0) {
/* 1248 */         if (this.rt != null) {
/* 1249 */           this.rt.dispose();
/*      */         }
/* 1251 */         if (this.rf != null) {
/* 1252 */           this.rf.removeFactoryListener(this);
/* 1253 */           this.rf = null;
/*      */         }
/* 1255 */         this.rt = paramResourceFactory.createRTTexture(paramInt1, paramInt2);
/* 1256 */         if (this.rt != null) {
/* 1257 */           this.rf = paramResourceFactory;
/* 1258 */           this.rf.addFactoryListener(this);
/*      */         }
/*      */       }
/*      */ 
/* 1262 */       return this.rt;
/*      */     }
/*      */ 
/*      */     void dispose() {
/* 1266 */       if (this.rt != null) {
/* 1267 */         this.rt.dispose();
/* 1268 */         this.rt = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     void setImage(com.sun.prism.Image paramImage) {
/* 1273 */       this.image = paramImage;
/*      */     }
/*      */ 
/*      */     public boolean getError() {
/* 1277 */       return this.image == null;
/*      */     }
/* 1279 */     public int getFrameCount() { return 1; } 
/*      */     public PlatformImage[] getFrames() {
/* 1281 */       return new PlatformImage[] { this.image };
/*      */     }
/* 1283 */     public PlatformImage getFrame(int paramInt) { return this.image; } 
/*      */     public int getFrameDelay(int paramInt) {
/* 1285 */       return 0;
/*      */     }
/* 1287 */     public int getWidth() { return this.image.getWidth(); } 
/*      */     public int getHeight() {
/* 1289 */       return this.image.getHeight();
/*      */     }
/* 1291 */     public void factoryReset() { dispose(); } 
/*      */     public void factoryReleased() {
/* 1293 */       dispose();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.QuantumToolkit
 * JD-Core Version:    0.6.2
 */