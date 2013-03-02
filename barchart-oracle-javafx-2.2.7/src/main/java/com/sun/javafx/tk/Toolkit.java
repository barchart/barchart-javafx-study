/*     */ package com.sun.javafx.tk;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.geom.ParallelCameraImpl;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PerspectiveCameraImpl;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.jmx.HighlightRegion;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.perf.PerformanceTracker;
/*     */ import com.sun.javafx.runtime.NativeLibLoader;
/*     */ import com.sun.javafx.runtime.VersionInfo;
/*     */ import com.sun.javafx.runtime.async.AsyncOperation;
/*     */ import com.sun.javafx.runtime.async.AsyncOperationListener;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import com.sun.javafx.sg.PGArc;
/*     */ import com.sun.javafx.sg.PGCanvas;
/*     */ import com.sun.javafx.sg.PGCircle;
/*     */ import com.sun.javafx.sg.PGCubicCurve;
/*     */ import com.sun.javafx.sg.PGEllipse;
/*     */ import com.sun.javafx.sg.PGGroup;
/*     */ import com.sun.javafx.sg.PGImageView;
/*     */ import com.sun.javafx.sg.PGLine;
/*     */ import com.sun.javafx.sg.PGMediaView;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import com.sun.javafx.sg.PGPolygon;
/*     */ import com.sun.javafx.sg.PGPolyline;
/*     */ import com.sun.javafx.sg.PGQuadCurve;
/*     */ import com.sun.javafx.sg.PGRectangle;
/*     */ import com.sun.javafx.sg.PGRegion;
/*     */ import com.sun.javafx.sg.PGSVGPath;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineCap;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.sg.PGShape.StrokeType;
/*     */ import com.sun.javafx.sg.PGText;
/*     */ import com.sun.scenario.DelayedRunnable;
/*     */ import com.sun.scenario.ToolkitAccessor;
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ import com.sun.scenario.effect.AbstractShadow.ShadowMode;
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import javafx.application.ConditionalFeature;
/*     */ import javafx.geometry.Dimension2D;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.effect.BlurType;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.image.WritableImage;
/*     */ import javafx.scene.input.DragEvent;
/*     */ import javafx.scene.input.Dragboard;
/*     */ import javafx.scene.input.InputMethodEvent;
/*     */ import javafx.scene.input.InputMethodRequests;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.TransferMode;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.ImagePattern;
/*     */ import javafx.scene.paint.LinearGradient;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.paint.RadialGradient;
/*     */ import javafx.scene.paint.Stop;
/*     */ import javafx.scene.shape.PathElement;
/*     */ import javafx.scene.shape.SVGPath;
/*     */ import javafx.stage.FileChooser.ExtensionFilter;
/*     */ import javafx.stage.Modality;
/*     */ import javafx.stage.StageStyle;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public abstract class Toolkit
/*     */ {
/*     */   private static String tk;
/*     */   private static Toolkit TOOLKIT;
/* 114 */   private static Thread fxUserThread = null;
/*     */   private static final String QUANTUM_TOOLKIT = "com.sun.javafx.tk.quantum.QuantumToolkit";
/*     */   private static final String DEFAULT_TOOLKIT = "com.sun.javafx.tk.quantum.QuantumToolkit";
/* 119 */   protected static Map gradientMap = new WeakHashMap();
/*     */ 
/* 333 */   private final Map<TKPulseListener, Object> stagePulseListeners = new WeakHashMap();
/*     */ 
/* 335 */   private final Map<TKPulseListener, Object> scenePulseListeners = new WeakHashMap();
/*     */ 
/* 337 */   private final Map<TKPulseListener, Object> postScenePulseListeners = new WeakHashMap();
/*     */ 
/* 339 */   private final Map<TKListener, Object> toolkitListeners = new WeakHashMap();
/*     */ 
/* 343 */   private final Set<Runnable> shutdownHooks = new HashSet();
/*     */ 
/* 345 */   private final ArrayList<TKPulseListener> stagePulseList = new ArrayList();
/* 346 */   private final ArrayList<TKPulseListener> scenePulseList = new ArrayList();
/* 347 */   private final ArrayList<TKPulseListener> postScenePulseList = new ArrayList();
/*     */ 
/* 416 */   private TKPulseListener lastTkPulseListener = null;
/*     */ 
/* 839 */   private CountDownLatch pauseScenesLatch = null;
/*     */   private Set<HighlightRegion> highlightRegions;
/* 918 */   private static SceneAccessor sceneAccessor = null;
/*     */ 
/* 929 */   private static WritableImageAccessor writableImageAccessor = null;
/*     */ 
/*     */   private static String lookupToolkitClass(String paramString)
/*     */   {
/* 122 */     if ("prism".equalsIgnoreCase(paramString))
/* 123 */       return "com.sun.javafx.tk.quantum.QuantumToolkit";
/* 124 */     if ("quantum".equalsIgnoreCase(paramString)) {
/* 125 */       return "com.sun.javafx.tk.quantum.QuantumToolkit";
/*     */     }
/* 127 */     return paramString;
/*     */   }
/*     */ 
/*     */   private static String getDefaultToolkit() {
/* 131 */     if (PlatformUtil.isWindows())
/* 132 */       return "com.sun.javafx.tk.quantum.QuantumToolkit";
/* 133 */     if (PlatformUtil.isMac())
/* 134 */       return "com.sun.javafx.tk.quantum.QuantumToolkit";
/* 135 */     if (PlatformUtil.isLinux()) {
/* 136 */       return "com.sun.javafx.tk.quantum.QuantumToolkit";
/*     */     }
/*     */ 
/* 139 */     throw new UnsupportedOperationException(System.getProperty("os.name") + " is not supported");
/*     */   }
/*     */ 
/*     */   public static synchronized Toolkit getToolkit() {
/* 143 */     if (TOOLKIT != null) {
/* 144 */       return TOOLKIT;
/*     */     }
/*     */ 
/* 147 */     boolean bool = ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Boolean run() {
/* 149 */         return Boolean.valueOf(Boolean.getBoolean("javafx.verbose"));
/*     */       }
/*     */     })).booleanValue();
/*     */ 
/* 156 */     if (PlatformUtil.isWindows()) {
/* 157 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Object run() {
/*     */           try {
/* 160 */             NativeLibLoader.loadLibrary("msvcr100");
/*     */           } catch (Throwable localThrowable) {
/* 162 */             if (this.val$verbose) {
/* 163 */               System.err.println("Error: failed to load msvcr100.dll : " + localThrowable);
/*     */             }
/*     */           }
/* 166 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/* 171 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run()
/*     */       {
/* 175 */         VersionInfo.setupSystemProperties();
/* 176 */         return null;
/*     */       }
/*     */     });
/* 180 */     int i = 1;
/*     */ 
/* 184 */     String str = null;
/*     */     try {
/* 186 */       str = System.getProperty("javafx.toolkit");
/*     */     } catch (SecurityException localSecurityException) {
/*     */     }
/* 189 */     if (str == null) {
/* 190 */       str = tk;
/*     */     }
/* 192 */     if (str == null) {
/* 193 */       i = 0;
/* 194 */       str = getDefaultToolkit();
/*     */     }
/*     */ 
/* 197 */     if (str.indexOf('.') == -1)
/*     */     {
/* 199 */       str = lookupToolkitClass(str);
/*     */     }
/*     */ 
/* 202 */     int j = (bool) || ((i != 0) && (!str.endsWith("StubToolkit"))) ? 1 : 0;
/*     */     try
/*     */     {
/* 206 */       TOOLKIT = (Toolkit)Class.forName(str).newInstance();
/* 207 */       if (TOOLKIT.init()) {
/* 208 */         if (j != 0) {
/* 209 */           System.err.println("JavaFX: using " + str);
/*     */         }
/* 211 */         return TOOLKIT;
/*     */       }
/* 213 */       TOOLKIT = null;
/*     */     } catch (Exception localException) {
/* 215 */       TOOLKIT = null;
/* 216 */       localException.printStackTrace();
/*     */     }
/*     */ 
/* 219 */     throw new RuntimeException("No toolkit found");
/*     */   }
/*     */ 
/*     */   protected static Thread getFxUserThread() {
/* 223 */     return fxUserThread;
/*     */   }
/*     */ 
/*     */   protected static void setFxUserThread(Thread paramThread) {
/* 227 */     if (fxUserThread != null) {
/* 228 */       throw new IllegalStateException("Error: FX User Thread already initialized");
/*     */     }
/*     */ 
/* 231 */     fxUserThread = paramThread;
/*     */   }
/*     */ 
/*     */   public void checkFxUserThread()
/*     */   {
/* 236 */     if (!isFxUserThread())
/* 237 */       throw new IllegalStateException("Not on FX application thread; currentThread = " + Thread.currentThread().getName());
/*     */   }
/*     */ 
/*     */   public boolean isFxUserThread()
/*     */   {
/* 244 */     return Thread.currentThread() == fxUserThread;
/*     */   }
/*     */ 
/*     */   protected Toolkit() {
/* 248 */     ToolkitAccessor.setInstance(new ToolkitAccessor()
/*     */     {
/*     */       public Map<Object, Object> getContextMapImpl() {
/* 251 */         return Toolkit.this.getContextMap();
/*     */       }
/*     */ 
/*     */       public AbstractMasterTimer getMasterTimerImpl() {
/* 255 */         return Toolkit.this.getMasterTimer();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public PlatformLogger getLogger(String paramString)
/*     */   {
/* 270 */     return PlatformLogger.getLogger(paramString);
/*     */   }
/*     */ 
/*     */   public abstract boolean init();
/*     */ 
/*     */   public abstract Object enterNestedEventLoop(Object paramObject);
/*     */ 
/*     */   public abstract void exitNestedEventLoop(Object paramObject1, Object paramObject2);
/*     */ 
/*     */   public abstract TKStage createTKStage(StageStyle paramStageStyle);
/*     */ 
/*     */   public abstract TKStage createTKStage(StageStyle paramStageStyle, boolean paramBoolean, Modality paramModality, TKStage paramTKStage);
/*     */ 
/*     */   public abstract TKStage createTKPopupStage(StageStyle paramStageStyle, Object paramObject);
/*     */ 
/*     */   public abstract TKStage createTKEmbeddedStage(HostInterface paramHostInterface);
/*     */ 
/*     */   public void firePulse()
/*     */   {
/*     */     try
/*     */     {
/* 354 */       synchronized (this) {
/* 355 */         this.stagePulseList.addAll(this.stagePulseListeners.keySet());
/* 356 */         this.scenePulseList.addAll(this.scenePulseListeners.keySet());
/* 357 */         this.postScenePulseList.addAll(this.postScenePulseListeners.keySet());
/*     */       }
/* 359 */       for (??? = this.stagePulseList.iterator(); ((Iterator)???).hasNext(); ) { localTKPulseListener = (TKPulseListener)((Iterator)???).next();
/* 360 */         localTKPulseListener.pulse();
/*     */       }
/* 362 */       TKPulseListener localTKPulseListener;
/* 362 */       for (??? = this.scenePulseList.iterator(); ((Iterator)???).hasNext(); ) { localTKPulseListener = (TKPulseListener)((Iterator)???).next();
/* 363 */         localTKPulseListener.pulse();
/*     */       }
/* 365 */       for (??? = this.postScenePulseList.iterator(); ((Iterator)???).hasNext(); ) { localTKPulseListener = (TKPulseListener)((Iterator)???).next();
/* 366 */         localTKPulseListener.pulse();
/*     */       }
/* 368 */       if (this.lastTkPulseListener != null)
/* 369 */         this.lastTkPulseListener.pulse();
/*     */     }
/*     */     finally {
/* 372 */       this.stagePulseList.clear();
/* 373 */       this.scenePulseList.clear();
/* 374 */       this.postScenePulseList.clear();
/*     */     }
/*     */   }
/*     */ 
/* 378 */   public void addStageTkPulseListener(TKPulseListener paramTKPulseListener) { synchronized (this) {
/* 379 */       this.stagePulseListeners.put(paramTKPulseListener, null);
/*     */     } }
/*     */ 
/*     */   public void removeStageTkPulseListener(TKPulseListener paramTKPulseListener) {
/* 383 */     synchronized (this) {
/* 384 */       this.stagePulseListeners.remove(paramTKPulseListener);
/*     */     }
/*     */   }
/*     */ 
/* 388 */   public void addSceneTkPulseListener(TKPulseListener paramTKPulseListener) { synchronized (this) {
/* 389 */       this.scenePulseListeners.put(paramTKPulseListener, null);
/*     */     } }
/*     */ 
/*     */   public void removeSceneTkPulseListener(TKPulseListener paramTKPulseListener) {
/* 393 */     synchronized (this) {
/* 394 */       this.scenePulseListeners.remove(paramTKPulseListener);
/*     */     }
/*     */   }
/*     */ 
/* 398 */   public void addPostSceneTkPulseListener(TKPulseListener paramTKPulseListener) { synchronized (this) {
/* 399 */       this.postScenePulseListeners.put(paramTKPulseListener, null);
/*     */     } }
/*     */ 
/*     */   public void removePostSceneTkPulseListener(TKPulseListener paramTKPulseListener) {
/* 403 */     synchronized (this) {
/* 404 */       this.postScenePulseListeners.remove(paramTKPulseListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addTkListener(TKListener paramTKListener) {
/* 409 */     this.toolkitListeners.put(paramTKListener, null);
/*     */   }
/*     */ 
/*     */   public void removeTkListener(TKListener paramTKListener) {
/* 413 */     this.toolkitListeners.remove(paramTKListener);
/*     */   }
/*     */ 
/*     */   public void setLastTkPulseListener(TKPulseListener paramTKPulseListener)
/*     */   {
/* 418 */     this.lastTkPulseListener = paramTKPulseListener;
/*     */   }
/*     */ 
/*     */   public void addShutdownHook(Runnable paramRunnable) {
/* 422 */     synchronized (this.shutdownHooks) {
/* 423 */       this.shutdownHooks.add(paramRunnable);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeShutdownHook(Runnable paramRunnable) {
/* 428 */     synchronized (this.shutdownHooks) {
/* 429 */       this.shutdownHooks.remove(paramRunnable);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifyShutdownHooks()
/*     */   {
/*     */     ArrayList localArrayList;
/* 435 */     synchronized (this.shutdownHooks) {
/* 436 */       localArrayList = new ArrayList(this.shutdownHooks);
/* 437 */       this.shutdownHooks.clear();
/*     */     }
/*     */ 
/* 440 */     for (??? = localArrayList.iterator(); ((Iterator)???).hasNext(); ) { Runnable localRunnable = (Runnable)((Iterator)???).next();
/* 441 */       localRunnable.run(); }
/*     */   }
/*     */ 
/*     */   public void notifyWindowListeners(List<TKStage> paramList)
/*     */   {
/* 446 */     for (TKListener localTKListener : this.toolkitListeners.keySet())
/* 447 */       localTKListener.changedTopLevelWindows(paramList);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void triggerNextPulse()
/*     */   {
/* 457 */     getMasterTimer().notifyJobsReady();
/*     */   }
/*     */ 
/*     */   public abstract void requestNextPulse();
/*     */ 
/*     */   public InputStream getInputStream(String paramString, Class paramClass)
/*     */     throws IOException
/*     */   {
/* 466 */     return (paramString.startsWith("http:")) || (paramString.startsWith("https:")) || (paramString.startsWith("file:")) || (paramString.startsWith("jar:")) ? new URL(paramString).openStream() : paramClass.getResource(paramString).openStream();
/*     */   }
/*     */ 
/*     */   public abstract ImageLoader loadImage(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   public abstract ImageLoader loadImage(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   public abstract AsyncOperation loadImageAsync(AsyncOperationListener<? extends ImageLoader> paramAsyncOperationListener, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   public abstract ImageLoader loadPlatformImage(Object paramObject);
/*     */ 
/*     */   public abstract PlatformImage createPlatformImage(int paramInt1, int paramInt2);
/*     */ 
/*     */   public boolean getDefaultImageSmooth()
/*     */   {
/* 504 */     return true; } 
/*     */   public abstract void startup(Runnable paramRunnable);
/*     */ 
/*     */   public abstract void defer(Runnable paramRunnable);
/*     */ 
/* 509 */   public void exit() { fxUserThread = null; } 
/*     */   public abstract Map<Object, Object> getContextMap();
/*     */ 
/*     */   public abstract int getRefreshRate();
/*     */ 
/*     */   public abstract void setAnimationRunnable(DelayedRunnable paramDelayedRunnable);
/*     */ 
/*     */   public abstract PerformanceTracker getPerformanceTracker();
/*     */ 
/*     */   public abstract PerformanceTracker createPerformanceTracker();
/*     */ 
/*     */   public abstract void waitFor(Task paramTask);
/*     */ 
/*     */   public abstract PerspectiveCameraImpl createPerspectiveCamera();
/*     */ 
/*     */   public abstract ParallelCameraImpl createParallelCamera();
/*     */ 
/* 525 */   private Object checkSingleColor(List<Stop> paramList) { if (paramList.size() == 2) {
/* 526 */       Color localColor = ((Stop)paramList.get(0)).getColor();
/* 527 */       if (localColor.equals(((Stop)paramList.get(1)).getColor())) {
/* 528 */         return localColor.impl_getPlatformPaint();
/*     */       }
/*     */     }
/* 531 */     return null; }
/*     */ 
/*     */   private Object getPaint(LinearGradient paramLinearGradient)
/*     */   {
/* 535 */     Object localObject = gradientMap.get(paramLinearGradient);
/* 536 */     if (localObject != null) {
/* 537 */       return localObject;
/*     */     }
/* 539 */     localObject = checkSingleColor(paramLinearGradient.getStops());
/* 540 */     if (localObject == null) {
/* 541 */       localObject = createLinearGradientPaint(paramLinearGradient);
/*     */     }
/* 543 */     gradientMap.put(paramLinearGradient, localObject);
/* 544 */     return localObject;
/*     */   }
/*     */ 
/*     */   private Object getPaint(RadialGradient paramRadialGradient) {
/* 548 */     Object localObject = gradientMap.get(paramRadialGradient);
/* 549 */     if (localObject != null) {
/* 550 */       return localObject;
/*     */     }
/* 552 */     localObject = checkSingleColor(paramRadialGradient.getStops());
/* 553 */     if (localObject == null) {
/* 554 */       localObject = createRadialGradientPaint(paramRadialGradient);
/*     */     }
/* 556 */     gradientMap.put(paramRadialGradient, localObject);
/* 557 */     return localObject;
/*     */   }
/*     */ 
/*     */   public Object getPaint(Paint paramPaint) {
/* 561 */     if ((paramPaint instanceof Color)) {
/* 562 */       return createColorPaint((Color)paramPaint);
/*     */     }
/*     */ 
/* 565 */     if ((paramPaint instanceof LinearGradient)) {
/* 566 */       return getPaint((LinearGradient)paramPaint);
/*     */     }
/*     */ 
/* 569 */     if ((paramPaint instanceof RadialGradient)) {
/* 570 */       return getPaint((RadialGradient)paramPaint);
/*     */     }
/*     */ 
/* 573 */     if ((paramPaint instanceof ImagePattern)) {
/* 574 */       return createImagePatternPaint((ImagePattern)paramPaint);
/*     */     }
/*     */ 
/* 577 */     return null;
/*     */   }
/*     */ 
/*     */   protected static final double clampStopOffset(double paramDouble) {
/* 581 */     return paramDouble < 0.0D ? 0.0D : paramDouble > 1.0D ? 1.0D : paramDouble;
/*     */   }
/*     */   protected abstract Object createColorPaint(Color paramColor);
/*     */ 
/*     */   protected abstract Object createLinearGradientPaint(LinearGradient paramLinearGradient);
/*     */ 
/*     */   protected abstract Object createRadialGradientPaint(RadialGradient paramRadialGradient);
/*     */ 
/*     */   protected abstract Object createImagePatternPaint(ImagePattern paramImagePattern);
/*     */ 
/*     */   public abstract void accumulateStrokeBounds(Shape paramShape, float[] paramArrayOfFloat, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat, BaseTransform paramBaseTransform);
/*     */ 
/*     */   public abstract boolean strokeContains(Shape paramShape, double paramDouble1, double paramDouble2, PGShape.StrokeType paramStrokeType, double paramDouble3, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat);
/*     */ 
/*     */   public abstract Shape createStrokedShape(Shape paramShape, PGShape.StrokeType paramStrokeType, double paramDouble, PGShape.StrokeLineCap paramStrokeLineCap, PGShape.StrokeLineJoin paramStrokeLineJoin, float paramFloat1, float[] paramArrayOfFloat, float paramFloat2);
/*     */ 
/*     */   public abstract int getKeyCodeForChar(String paramString);
/*     */ 
/*     */   public abstract MouseEvent convertMouseEventToFX(Object paramObject);
/*     */ 
/*     */   public abstract KeyEvent convertKeyEventToFX(Object paramObject);
/*     */ 
/*     */   public abstract DragEvent convertDragRecognizedEventToFX(Object paramObject, Dragboard paramDragboard);
/*     */ 
/*     */   public abstract DragEvent convertDragSourceEventToFX(Object paramObject, Dragboard paramDragboard);
/*     */ 
/*     */   public abstract DragEvent convertDropTargetEventToFX(Object paramObject, Dragboard paramDragboard);
/*     */ 
/*     */   public abstract InputMethodEvent convertInputMethodEventToFX(Object paramObject);
/*     */ 
/*     */   public abstract Dimension2D getBestCursorSize(int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract int getMaximumCursorColors();
/*     */ 
/*     */   public abstract PathElement[] convertShapeToFXPath(Object paramObject);
/*     */ 
/*     */   public abstract HitInfo convertHitInfoToFX(Object paramObject);
/*     */ 
/*     */   public abstract Filterable toFilterable(Image paramImage);
/*     */ 
/*     */   public abstract FilterContext getFilterContext(Object paramObject);
/*     */ 
/*     */   public abstract boolean isForwardTraversalKey(KeyEvent paramKeyEvent);
/*     */ 
/*     */   public abstract boolean isBackwardTraversalKey(KeyEvent paramKeyEvent);
/*     */ 
/*     */   public abstract AbstractMasterTimer getMasterTimer();
/*     */ 
/*     */   public abstract FontLoader getFontLoader();
/*     */ 
/*     */   public abstract PGArc createPGArc();
/*     */ 
/*     */   public abstract PGCircle createPGCircle();
/*     */ 
/*     */   public abstract PGCubicCurve createPGCubicCurve();
/*     */ 
/*     */   public abstract PGEllipse createPGEllipse();
/*     */ 
/*     */   public abstract PGLine createPGLine();
/*     */ 
/*     */   public abstract PGPath createPGPath();
/*     */ 
/*     */   public abstract PGSVGPath createPGSVGPath();
/*     */ 
/*     */   public abstract PGPolygon createPGPolygon();
/*     */ 
/*     */   public abstract PGPolyline createPGPolyline();
/*     */ 
/*     */   public abstract PGQuadCurve createPGQuadCurve();
/*     */ 
/*     */   public abstract PGRectangle createPGRectangle();
/*     */ 
/*     */   public abstract PGImageView createPGImageView();
/*     */ 
/*     */   public abstract PGMediaView createPGMediaView();
/*     */ 
/*     */   public abstract PGGroup createPGGroup();
/*     */ 
/*     */   public abstract PGText createPGText();
/*     */ 
/*     */   public abstract PGRegion createPGRegion();
/*     */ 
/*     */   public abstract PGCanvas createPGCanvas();
/*     */ 
/*     */   public abstract Object createSVGPathObject(SVGPath paramSVGPath);
/*     */ 
/*     */   public abstract Path2D createSVGPath2D(SVGPath paramSVGPath);
/*     */ 
/*     */   public abstract boolean imageContains(Object paramObject, float paramFloat1, float paramFloat2);
/*     */ 
/*     */   public abstract TKClipboard getSystemClipboard();
/*     */ 
/*     */   public abstract TKSystemMenu getSystemMenu();
/*     */ 
/*     */   public abstract TKClipboard getNamedClipboard(String paramString);
/*     */ 
/*     */   public abstract Dragboard createDragboard();
/*     */ 
/*     */   public boolean isSupported(ConditionalFeature paramConditionalFeature) {
/* 680 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract ScreenConfigurationAccessor setScreenConfigurationListener(TKScreenConfigurationListener paramTKScreenConfigurationListener);
/*     */ 
/*     */   public abstract Object getPrimaryScreen();
/*     */ 
/*     */   public abstract List<?> getScreens();
/*     */ 
/*     */   public abstract void registerDragGestureListener(TKScene paramTKScene, Set<TransferMode> paramSet, TKDragGestureListener paramTKDragGestureListener);
/*     */ 
/*     */   public abstract void startDrag(Object paramObject, Set<TransferMode> paramSet, TKDragSourceListener paramTKDragSourceListener, Dragboard paramDragboard);
/*     */ 
/*     */   public void stopDrag(Dragboard paramDragboard)
/*     */   {
/*     */   }
/*     */ 
/*     */   public abstract void enableDrop(TKScene paramTKScene, TKDropTargetListener paramTKDropTargetListener);
/*     */ 
/*     */   public Color4f toColor4f(Color paramColor)
/*     */   {
/* 713 */     return new Color4f((float)paramColor.getRed(), (float)paramColor.getGreen(), (float)paramColor.getBlue(), (float)paramColor.getOpacity());
/*     */   }
/*     */ 
/*     */   public AbstractShadow.ShadowMode toShadowMode(BlurType paramBlurType)
/*     */   {
/* 718 */     switch (5.$SwitchMap$javafx$scene$effect$BlurType[paramBlurType.ordinal()]) {
/*     */     case 1:
/* 720 */       return AbstractShadow.ShadowMode.ONE_PASS_BOX;
/*     */     case 2:
/* 722 */       return AbstractShadow.ShadowMode.TWO_PASS_BOX;
/*     */     case 3:
/* 724 */       return AbstractShadow.ShadowMode.THREE_PASS_BOX;
/*     */     }
/* 726 */     return AbstractShadow.ShadowMode.GAUSSIAN;
/*     */   }
/*     */ 
/*     */   public abstract void installInputMethodRequests(TKScene paramTKScene, InputMethodRequests paramInputMethodRequests);
/*     */ 
/*     */   public abstract Object renderToImage(ImageRenderingContext paramImageRenderingContext);
/*     */ 
/*     */   public abstract boolean isExternalFormatSupported(Class paramClass);
/*     */ 
/*     */   public abstract Object toExternalImage(Object paramObject1, Object paramObject2);
/*     */ 
/*     */   public abstract KeyCode getPlatformShortcutKey();
/*     */ 
/*     */   public abstract List<File> showFileChooser(TKStage paramTKStage, String paramString, File paramFile, FileChooserType paramFileChooserType, List<FileChooser.ExtensionFilter> paramList);
/*     */ 
/*     */   public abstract File showDirectoryChooser(TKStage paramTKStage, String paramString, File paramFile);
/*     */ 
/*     */   public abstract long getMultiClickTime();
/*     */ 
/*     */   public abstract int getMultiClickMaxX();
/*     */ 
/*     */   public abstract int getMultiClickMaxY();
/*     */ 
/*     */   public void pauseScenes()
/*     */   {
/* 846 */     this.pauseScenesLatch = new CountDownLatch(1);
/* 847 */     Iterator localIterator = Window.impl_getWindows();
/* 848 */     while (localIterator.hasNext()) {
/* 849 */       Window localWindow = (Window)localIterator.next();
/* 850 */       Scene localScene = localWindow.getScene();
/* 851 */       if (localScene != null) {
/* 852 */         removeSceneTkPulseListener(localScene.impl_getScenePulseListener());
/*     */       }
/*     */     }
/* 855 */     getMasterTimer().pause();
/* 856 */     if (sceneAccessor != null)
/* 857 */       sceneAccessor.setPaused(true);
/*     */   }
/*     */ 
/*     */   public void resumeScenes()
/*     */   {
/* 866 */     if (sceneAccessor != null) {
/* 867 */       sceneAccessor.setPaused(false);
/*     */     }
/* 869 */     getMasterTimer().resume();
/* 870 */     Iterator localIterator = Window.impl_getWindows();
/* 871 */     while (localIterator.hasNext()) {
/* 872 */       Window localWindow = (Window)localIterator.next();
/* 873 */       Scene localScene = localWindow.getScene();
/* 874 */       if (localScene != null) {
/* 875 */         addSceneTkPulseListener(localScene.impl_getScenePulseListener());
/*     */       }
/*     */     }
/* 878 */     this.pauseScenesLatch.countDown();
/* 879 */     this.pauseScenesLatch = null;
/*     */   }
/*     */ 
/*     */   public void pauseCurrentThread()
/*     */   {
/* 888 */     CountDownLatch localCountDownLatch = this.pauseScenesLatch;
/* 889 */     if (localCountDownLatch == null)
/* 890 */       return;
/*     */     try
/*     */     {
/* 893 */       localCountDownLatch.await();
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public Set<HighlightRegion> getHighlightedRegions()
/*     */   {
/* 906 */     if (this.highlightRegions == null) {
/* 907 */       this.highlightRegions = new HashSet();
/*     */     }
/* 909 */     return this.highlightRegions;
/*     */   }
/*     */ 
/*     */   public static void setSceneAccessor(SceneAccessor paramSceneAccessor)
/*     */   {
/* 921 */     sceneAccessor = paramSceneAccessor;
/*     */   }
/*     */ 
/*     */   public static void setWritableImageAccessor(WritableImageAccessor paramWritableImageAccessor)
/*     */   {
/* 932 */     writableImageAccessor = paramWritableImageAccessor;
/*     */   }
/*     */ 
/*     */   public static WritableImageAccessor getWritableImageAccessor() {
/* 936 */     return writableImageAccessor;
/*     */   }
/*     */ 
/*     */   public static abstract interface WritableImageAccessor
/*     */   {
/*     */     public abstract void loadTkImage(WritableImage paramWritableImage, Object paramObject);
/*     */ 
/*     */     public abstract Object getTkImageLoader(WritableImage paramWritableImage);
/*     */   }
/*     */ 
/*     */   public static abstract interface SceneAccessor
/*     */   {
/*     */     public abstract void setPaused(boolean paramBoolean);
/*     */   }
/*     */ 
/*     */   public static class ImageRenderingContext
/*     */   {
/*     */     public PGNode root;
/*     */     public int x;
/*     */     public int y;
/*     */     public int width;
/*     */     public int height;
/*     */     public BaseTransform transform;
/*     */     public boolean depthBuffer;
/*     */     public Object platformPaint;
/*     */     public CameraImpl camera;
/*     */     public Object platformImage;
/*     */   }
/*     */ 
/*     */   public static abstract interface Task
/*     */   {
/*     */     public abstract boolean isFinished();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.Toolkit
 * JD-Core Version:    0.6.2
 */